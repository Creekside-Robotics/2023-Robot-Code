// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  private XboxController xboxController;
  private XboxController assistantController;
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
  private FieldDisplay fieldDisplay;


  // Commands
  private ManualDrive manualDrive;
  private SetArmPosition holdLowerArm;
  private SetArmPosition holdUpperArm;
  private AutoScore autoScoreOne;
  private AutoScore autoScoreTwo;
  private AutoScore autoScoreThree;
  private RetractArms retractArms;
  private AutoPickup autoPickup;
  private IndexObject indexObject;
  private AwayFromPickupIn driveAwayIn;
  private AwayFromPickupOut driveAwayOut;
  private ToPickupIn toPickupIn;
  private ToPickupOut toPickupOut;
  private SetPose resetPose;
  private DriveToPosePID autoBalance;
  private SendableChooser<Command> autoCommandChooser;


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
    this.assistantController = new XboxController(1);
    this.frontRight = new SwerveModule(1, 2, 1);
    this.frontLeft = new SwerveModule(3, 4, 2);
    this.backLeft = new SwerveModule(5, 6, 3);
    this.backRight = new SwerveModule(7, 8, 4);
    this.poseAPI = new VisionPoseAPI();
    this.objectAPI = new VisionObjectAPI();
    this.drivetrain = new Drivetrain(frontRight, frontLeft, backRight, backLeft, poseAPI, objectAPI);
    this.indexer = new Indexer(12, false);
    this.intake = new Intake(13, 14, true, true, 2, 3);
    this.lowerArm = new Arm(new int[]{9, 10}, 0, -.13, new boolean[]{false, true}, true);
    this.upperArm = new Arm(new int[]{11}, 1, .36, new boolean[]{false}, false);
    this.claw = new Claw();
    this.fieldDisplay = new FieldDisplay(this.drivetrain);
  }

  private void createCommands(){
    this.manualDrive = new ManualDrive(this.drivetrain, this.xboxController);
    this.holdLowerArm = new SetArmPosition(this.lowerArm, this.lowerArm::getPosition, 0.1, true);
    this.holdUpperArm = new SetArmPosition(this.upperArm, this.upperArm::getPosition, 0.1, true);

    this.autoScoreOne = new AutoScore(drivetrain, lowerArm, upperArm, claw, 1);
    this.autoScoreTwo = new AutoScore(drivetrain, lowerArm, upperArm, claw, 2);
    this.autoScoreThree = new AutoScore(drivetrain, lowerArm, upperArm, claw, 3);
    this.retractArms = new RetractArms(lowerArm, upperArm);

    this.autoPickup = new AutoPickup(drivetrain, intake);
    this.indexObject = new IndexObject(lowerArm, upperArm, claw, intake, indexer);

    this.driveAwayIn = new AwayFromPickupIn(drivetrain);
    this.driveAwayOut = new AwayFromPickupOut(drivetrain);
    this.toPickupIn = new ToPickupIn(drivetrain);
    this.toPickupOut = new ToPickupOut(drivetrain);

    this.resetPose = new SetPose(this.poseAPI, this.drivetrain, null);
    this.autoBalance = new DriveToPosePID(drivetrain, drivetrain::getBestBalancePosition, 1, 0.02, true, 0.1);

    this.autoCommandChooser = new SendableChooser<Command>();
    this.autoCommandChooser.setDefaultOption("Long: Single Pickup, Double Score, Tilt", new TripleThreatLong(drivetrain, lowerArm, upperArm, claw, intake, indexer, poseAPI));
    this.autoCommandChooser.addOption("Long: Double Pickup, Double Score", new TripleTreatObject(drivetrain, lowerArm, upperArm, claw, intake, indexer, poseAPI));
    SmartDashboard.putData(this.autoCommandChooser);
  }

  private void createButtons(){
    Button aButton = new JoystickButton(this.xboxController, 1);
    Button bButton = new JoystickButton(this.xboxController, 2);
    Button xButton = new JoystickButton(this.xboxController, 3);
    Button yButton = new JoystickButton(this.xboxController, 4);
    Button leftBumper = new JoystickButton(this.xboxController, 5);
    Button rightBumper = new JoystickButton(this.xboxController, 6);
    Button resetButton = new JoystickButton(this.xboxController, 7);
    Button endgameButton = new JoystickButton(this.xboxController, 8);

    Button aButtonAlternate = new JoystickButton(this.assistantController, 1);
    Button bButtonAlternate = new JoystickButton(this.assistantController, 2);
    Button xButtonAlternate = new JoystickButton(this.assistantController, 3);
    Button yButtonAlternate = new JoystickButton(this.assistantController, 4);
    Button leftBumperAlternate = new JoystickButton(this.assistantController, 5);
    Button rightBumperAlternate = new JoystickButton(this.assistantController, 6);
    Button resetButtonAlternate = new JoystickButton(this.assistantController, 7);
    Button endgameButtonAlternate = new JoystickButton(this.assistantController, 8);

    this.drivetrain.setDefaultCommand(this.manualDrive);
    this.lowerArm.setDefaultCommand(this.holdLowerArm);
    this.upperArm.setDefaultCommand(this.holdUpperArm);

    aButton.whileHeld(this.autoScoreOne);
    aButton.whenReleased(this.retractArms);
    xButton.whileHeld(this.autoScoreTwo);
    xButton.whenReleased(this.retractArms);
    yButton.whileHeld(this.autoScoreThree);
    yButton.whenReleased(this.retractArms);

    bButton.whileHeld(this.autoPickup);
    bButton.whenReleased(this.indexObject);

    rightBumper.whileHeld(
            new ConditionalCommand(
                    this.driveAwayIn,
                    this.driveAwayOut,
                    () -> this.claw.getState() == DoubleSolenoid.Value.kForward
            )
    );
    leftBumper.whileHeld(
            new ConditionalCommand(
                    this.toPickupIn,
                    this.toPickupOut,
                    () -> this.claw.getState() == DoubleSolenoid.Value.kForward
            )
    );

    resetButton.whenPressed(this.resetPose);
    endgameButton.whenPressed(this.autoBalance);

    aButtonAlternate.whileHeld(new FirstLevelScore(lowerArm, upperArm, claw));
    aButtonAlternate.whenReleased(this.retractArms);
    xButtonAlternate.whileHeld(new SecondLevelScore(lowerArm, upperArm, claw));
    xButtonAlternate.whenReleased(this.retractArms);
    yButtonAlternate.whileHeld(new ThirdLevelScore(lowerArm, upperArm, claw));
    yButtonAlternate.whenReleased(this.retractArms);

    bButtonAlternate.whenPressed(new SetIntake(intake, true, 1));
    bButtonAlternate.whenReleased(new IndexObject(lowerArm, upperArm, claw, intake, indexer));

    leftBumperAlternate.whenPressed(new IndexObject(lowerArm, upperArm, claw, intake, indexer));
  }

  private void configureButtonBindings() {

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return this.autoCommandChooser.getSelected();
  }
}