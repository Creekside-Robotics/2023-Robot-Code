// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutoDrive;
import frc.robot.commands.ManualDrive;
import frc.robot.subsystems.Communications;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.SwerveModule;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  XboxController xboxController = new XboxController(0);
  private final JoystickButton autoDriveButton = new JoystickButton(xboxController, 1);
  private final JoystickButton manualDriveButton = new JoystickButton(xboxController, 2);
  private final JoystickButton resetPoseButton = new JoystickButton(xboxController, 3);

  private final Communications communications = new Communications();
  private final SwerveModule frontRight = new SwerveModule(1, 2, 1);
  private final SwerveModule frontLeft = new SwerveModule(3, 4, 2);
  private final SwerveModule backRight = new SwerveModule(5, 6, 3);
  private final SwerveModule backLeft = new SwerveModule(7, 8, 4);
  private final Drivetrain drivetrain = new Drivetrain(frontRight, frontLeft, backRight, backLeft, communications);

  private final AutoDrive autoDrive = new AutoDrive(communications, drivetrain);
  private final ManualDrive manualDrive = new ManualDrive(drivetrain, communications, xboxController);
  private final Command resetPoseCommand = new InstantCommand(
          () -> drivetrain.setPose(Constants.startingPose)
  );

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    this.drivetrain.setDefaultCommand(this.manualDrive);

    this.autoDriveButton.whenPressed(this.autoDrive);
    this.manualDriveButton.cancelWhenPressed(this.autoDrive);
    this.resetPoseButton.whenPressed(this.resetPoseCommand);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return this.autoDrive;
  }
}