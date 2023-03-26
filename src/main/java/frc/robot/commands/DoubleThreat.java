// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.VisionPoseAPI;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DoubleThreat extends SequentialCommandGroup {
  /** Creates a new DoubleThreat. */
  public DoubleThreat(Drivetrain drivetrain, Arm lower, Arm upper, Claw claw, Intake intake, Indexer indexer, VisionPoseAPI poseAPI) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new SetPose(poseAPI, drivetrain, Constants.AutoScorePositions.getScoringPositions()[7]),
        new ParallelDeadlineGroup(
                new SecondLevelScore(lower, upper, claw),
                new SetDrivetrainOutput(drivetrain, new Pose2d(0.05, 0, new Rotation2d()), false)
        ),
        new ParallelDeadlineGroup(
                new DriveToPosePID(drivetrain, Constants.AutoScorePositions.getAutoPositions()[1], 2, 0.1, false, 0.5),
                new RetractArms(lower, upper)
        ),
        new AutoPickup(drivetrain, intake)
        );
  }
}
