package frc.robot.commands;


import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.*;

public class TripleTreatObject extends SequentialCommandGroup {
    public TripleTreatObject(Drivetrain drivetrain, Arm lower, Arm upper, Claw claw, Intake intake, Indexer indexer, VisionPoseAPI poseAPI) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
                new SetPose(poseAPI, drivetrain, Constants.AutoScorePositions.getScoringPositions()[0]),
                new ParallelDeadlineGroup(
                        new ThirdLevelScore(lower, upper, claw),
                        new SetDrivetrainOutput(drivetrain, new Pose2d(0.05, 0, new Rotation2d()), false)
                ),
                new ParallelDeadlineGroup(
                        new DriveToPosePID(drivetrain, Constants.AutoScorePositions.getAutoPositions()[0], 2, 0.1, false, 0.5),
                        new RetractArms(lower, upper)
                ),
                new AutoPickup(drivetrain, intake),
                new ParallelDeadlineGroup(
                        new SequentialCommandGroup(
                                new DriveToPosePID(drivetrain, Constants.AutoScorePositions.getCycleDownPositions()[0], 2, 0.1, false, 0.5),
                                new DriveToPosePID(drivetrain, Constants.AutoScorePositions.getCycleDownPositions()[1], 2, 0.1, false, 0.5),
                                new DriveToPosePID(drivetrain, Constants.AutoScorePositions.getScoringPositions()[1], 2, 0.1, false, 0.5)
                        ),
                        new IndexObject(lower, upper, claw, intake, indexer)
                ),
                new ParallelDeadlineGroup(
                        new ThirdLevelScore(lower, upper, claw),
                        new SetDrivetrainOutput(drivetrain, new Pose2d(0.05, 0, new Rotation2d()), false)
                ),
                new ParallelDeadlineGroup(
                        new SequentialCommandGroup(
                                new DriveToPosePID(drivetrain, Constants.AutoScorePositions.getCycleDownPositions()[1], 2, 0.1, false, 0.5),
                                new DriveToPosePID(drivetrain, Constants.AutoScorePositions.getAutoPositions()[0], 2, 0.1, false, 0.5)
                        ),
                        new RetractArms(lower, upper)
                ),
                new AutoPickup(drivetrain, intake)
        );
    }
}