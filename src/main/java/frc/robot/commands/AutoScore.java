package frc.robot.commands;


import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;

public class AutoScore extends SequentialCommandGroup {
    public AutoScore(Drivetrain drive, Arm lower, Arm upper, Claw claw, int level) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super();

        Command scoreCommand = null;
        switch(level){
            case(1):
                scoreCommand = new FirstLevelScore(lower, upper, claw);
                break;
            case(2):
                scoreCommand = new SecondLevelScore(lower, upper, claw);
                break;
            case(3):
                scoreCommand = new ThirdLevelScore(lower, upper, claw);
                break;
        }

        addCommands(
                new DriveToPosePID(drive, drive::getClosestScoringPosition, 0.5, 0.05, false, 0),
                new ParallelDeadlineGroup(
                        scoreCommand,
                        new SetDrivetrainOutput(drive, new Pose2d(0.05, 0, new Rotation2d()), false)
                )

        );

    }
}