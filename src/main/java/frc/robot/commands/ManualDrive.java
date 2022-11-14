// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

/** Drive the robot with the joystick. Adapted from the mechanum drive  */
public class ManualDrive extends CommandBase {
  private Drivetrain drivetrain;
  private Joystick joystick;
  private boolean fieldOriented;

  /** 
   * Creates a new ManualDrive. 
   * 
   * @param drivetrain The drivetrain moving the robot
   * @param joystick The joystick controlling the movement
   */
  public ManualDrive(Drivetrain drivetrain, Joystick joystick) {
    this.drivetrain = drivetrain;
    this.joystick = joystick;
    this.fieldOriented = false;  // start as relative driving

    // todo ask if it should start as relative driving or not

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Translation2d joystickVector = new Translation2d(-this.joystick.getY(), -this.joystick.getX());

    // working under the assumption that the coordinates are still different
    double xSpeed = -this.joystick.getY() * Constants.joystickSpeedScalar;
    double ySpeed = -this.joystick.getX() * Constants.joystickSpeedScalar;
    double rotSpeed = -this.joystick.getTwist() * Constants.joystickRotScalar;

    if (this.joystick.getRawButtonPressed(Constants.fieldOrientedDriveToggle)) {  // only toggle when the button is pressed and not held
      this.fieldOriented = !this.fieldOriented;  // invert the toggle

      SmartDashboard.putString(  // update the dashboard
        "Drive Mode",
        this.fieldOriented ? "Field Oriented" : "Relative"
      );

    }

    this.drivetrain.drive(xSpeed, ySpeed, rotSpeed, this.fieldOriented);

  }
}
