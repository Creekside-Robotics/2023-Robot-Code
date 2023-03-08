package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class AwayFromPickupIn extends SequentialCommandGroup {
    public AwayFromPickupIn(Drivetrain drivetrain) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
                new DriveToPosePID(drivetrain, Constants.AutoScorePositions::getCycleThree, 2, 0.1, false, 0),
                new DriveToPosePID(drivetrain, Constants.AutoScorePositions::getCycleTwo, 2, 0.1, false, 0),
                new DriveToPosePID(drivetrain, Constants.AutoScorePositions::getCycleOne, 2, 0.1, false, 0)
        );
    }
}