// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

/** Drive the robot with the joystick. Adapted from the mecanum drive  */
public class ManualDrive extends CommandBase {
  private final Drivetrain drivetrain;
  private final XboxController xboxController;
  private boolean fieldOriented;

  /** 
   * Creates a new ManualDrive. 
   * 
   * @param drivetrain The drivetrain moving the robot
   * @param xboxController The joystick controlling the movement
   */
  public ManualDrive(Drivetrain drivetrain, XboxController xboxController) {
    this.drivetrain = drivetrain;
    this.xboxController = xboxController;
    this.fieldOriented = true;  // start as relative driving

    // todo ask if it should start as relative driving or not

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // working under the assumption that the coordinates are still different
    double xSpeed = -this.xboxController.getLeftY() * Constants.maxTranslationalSpeed;
    double ySpeed = -this.xboxController.getLeftX() * Constants.maxTranslationalSpeed;
    double rotSpeed = -this.xboxController.getRightX() * Constants.maxAngularSpeed;
    if (DriverStation.getAlliance() == DriverStation.Alliance.Blue) {
      this.drivetrain.drive(xSpeed, ySpeed, rotSpeed, this.fieldOriented);
    } else {
      this.drivetrain.drive(-xSpeed, -ySpeed, rotSpeed, this.fieldOriented);
    }
  }
}
