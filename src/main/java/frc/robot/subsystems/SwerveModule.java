// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import frc.robot.Constants;

public class SwerveModule {

  private final CANSparkMax m_driveMotor;
  private final CANSparkMax m_turningMotor;

  private final RelativeEncoder m_driveEncoder;
  private final CANCoder m_turningEncoder;
  
  private final PIDController m_drivePIDController = new PIDController(
    Constants.driveKp, 
    Constants.driveKi, 
    Constants.driveKd
  );

  // Gains are for example purposes only - must be determined for your own robot!
  private final ProfiledPIDController m_turningPIDController =
      new ProfiledPIDController(
          Constants.turnKp,
          Constants.turnKi,
          Constants.turnKd,
          new TrapezoidProfile.Constraints(
              Constants.moduleMaxAngularVelocity, Constants.moduleMaxAngularAcceleration));

  // Gains are for example purposes only - must be determined for your own robot!
  private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(Constants.driveKs, Constants.driveKv);
  private final SimpleMotorFeedforward m_turnFeedforward = new SimpleMotorFeedforward(Constants.turnKs, Constants.turnKv);

  public SwerveModule(
      int driveMotorChannel,
      int turningMotorChannel,
      int turningEncoderChannel) {

    this.m_driveMotor = new CANSparkMax(driveMotorChannel, MotorType.kBrushless);
    this.m_driveMotor.setInverted(false);
    this.m_turningMotor = new CANSparkMax(turningMotorChannel, MotorType.kBrushless);
    this.m_turningMotor.setInverted(true);

    this.m_driveEncoder = this.m_driveMotor.getEncoder();
    this.m_turningEncoder = new CANCoder(turningEncoderChannel);

    // Set the distance per pulse for the drive encoder. We can simply use the
    // distance traveled for one rotation of the wheel divided by the encoder
    // resolution.
    m_driveEncoder.setPositionConversionFactor(1);
    m_driveEncoder.setVelocityConversionFactor(1);

    // Set the distance (in this case, angle) per pulse for the turning encoder.
    // This is the the angle through an entire rotation (2 * pi) divided by the
    // encoder resolution.

    // Limit the PID Controller's input range between -pi and pi and set the input
    // to be continuous.
    m_turningPIDController.enableContinuousInput(-Math.PI, Math.PI);
  }

  public double getVelocity(){
    return this.m_driveEncoder.getVelocity() * (6.0/39) * (14.0/50);
  }

  /**
   * Returns the current state of the module.
   *
   * @return The current state of the module.
   */
  public SwerveModuleState getState() {
    return new SwerveModuleState(getVelocity(), Rotation2d.fromDegrees(m_turningEncoder.getAbsolutePosition()));
  }

  /**
   * Sets the desired state for the module.
   *
   * @param desiredState Desired state with speed and angle.
   */
  public void setDesiredState(SwerveModuleState desiredState) {
    // Optimize the reference state to avoid spinning further than 90 degrees
    SwerveModuleState state =
        SwerveModuleState.optimize(desiredState, Rotation2d.fromDegrees(m_turningEncoder.getAbsolutePosition()));

    final double driveOutput = 
        m_driveFeedforward.calculate(state.speedMetersPerSecond)
        + m_drivePIDController.calculate(getVelocity(), state.speedMetersPerSecond);

    // Calculate the turning motor output from the turning PID controller.
    final double turnOutput =
        m_turningPIDController.calculate(Rotation2d.fromDegrees(m_turningEncoder.getAbsolutePosition()).getRadians(), state.angle.getRadians())
        + m_turnFeedforward.calculate(m_turningPIDController.getSetpoint().velocity);

    m_driveMotor.setVoltage(driveOutput);
    m_turningMotor.setVoltage(turnOutput);
  }
}
