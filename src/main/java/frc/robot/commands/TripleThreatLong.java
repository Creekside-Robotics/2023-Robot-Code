package frc.robot.commands;


import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.*;

public class TripleThreatLong extends SequentialCommandGroup {
    public TripleThreatLong(Drivetrain drivetrain, Arm lower, Arm upper, Claw claw, Intake intake, Indexer indexer, VisionPoseAPI poseAPI) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
                new SetPose(poseAPI, drivetrain, Constants.AutoScorePositions.getScoringPositions()[1]),
                new ParallelDeadlineGroup(
                        new SecondLevelScore(lower, upper, claw),
                        new SetDrivetrainOutput(drivetrain, new Pose2d(0.05, 0, new Rotation2d()), false)
                ),
                new RetractArms(lower, upper)
        );
    }
}