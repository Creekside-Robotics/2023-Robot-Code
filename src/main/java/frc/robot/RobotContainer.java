// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

import java.util.ArrayList;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  private XboxController xboxController;
  private SwerveModule frontRight;
  private SwerveModule frontLeft;
  private SwerveModule backRight;
  private SwerveModule backLeft;
  private VisionPoseAPI poseAPI;
  private VisionObjectAPI objectAPI;
  private Drivetrain drivetrain;
  private Indexer indexer;
  private Intake intake;
  private Arm lowerArm;
  private Arm upperArm;
  private Claw claw;

  // Commands
  private ManualDrive manualDrive;
  private ResetPose resetPose;
  private SetIndexerMode runIndexerClockwise;
  private SetIndexerMode runIndexerCounterclockwise;
  private SetIndexerMode stopIndexer;
  private SetArmSpeed stopLowerArm;
  private SetArmSpeed stopUpperArm;
  private SetArmPosition testSetLowerArm;
  private SetArmPosition testSetUpperArm;
  private OpenClaw openClaw;
  private CloseClaw closeClaw;
  private SetIntake runIntake;
  private SetIntake stopIntake;

  // Buttons



  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    createSubsystems();
    createCommands();
    createButtons();
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */

  private void createSubsystems(){
    this.xboxController = new XboxController(0);
    this.frontRight = new SwerveModule(1, 2, 1);
    this.frontLeft = new SwerveModule(3, 4, 2);
    this.backLeft = new SwerveModule(5, 6, 3);
    this.backRight = new SwerveModule(7, 8, 4);
    this.poseAPI = new VisionPoseAPI();
    this.objectAPI = new VisionObjectAPI();
    this.drivetrain = new Drivetrain(frontRight, frontLeft, backRight, backLeft, poseAPI);
    this.indexer = new Indexer(12, false);
    this.intake = new Intake(13, 14, false, false, 3, 4, 5, 6);
    this.lowerArm = new Arm(new int[]{9, 10}, 0, -0.1, new boolean[]{false, true}, true);
    this.upperArm = new Arm(new int[]{11}, 1, 0, new boolean[]{true}, true);
    this.claw = new Claw();
  }

  private void createCommands(){
    this.manualDrive = new ManualDrive(this.drivetrain, this.xboxController);
    this.resetPose = new ResetPose(this.drivetrain, new Pose2d(0, 0, new Rotation2d(0)));
    this.runIndexerClockwise = new SetIndexerMode(this.indexer, Indexer.Mode.Clockwise);
    this.runIndexerCounterclockwise = new SetIndexerMode(this.indexer, Indexer.Mode.CounterClockwise);
    this.stopIndexer = new SetIndexerMode(this.indexer, Indexer.Mode.Stopped);
    this.stopLowerArm = new SetArmSpeed(this.lowerArm, 0);
    this.stopUpperArm = new SetArmSpeed(this.upperArm, 0);
    this.testSetLowerArm = new SetArmPosition(this.lowerArm, 0.25, 0.1, true);
    this.testSetUpperArm = new SetArmPosition(this.upperArm, 0, 0.1, false);
    this.openClaw = new OpenClaw(this.claw);
    this.closeClaw = new CloseClaw(this.claw);
    this.runIntake =  new SetIntake(this.intake, true, 1);
    this.stopIntake = new SetIntake(this.intake, false, 0);
  }

  private void createButtons(){}

  private void configureButtonBindings() {}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}