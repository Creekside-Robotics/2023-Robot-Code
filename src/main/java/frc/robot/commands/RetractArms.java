package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;

public class RetractArms extends SequentialCommandGroup {
    public RetractArms(Arm lower, Arm upper) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
                new MoveArmsToPosition(lower, upper, Constants.ArmPositions.lowerTranscendentalOne, Constants.ArmPositions.upperTranscendentalOne, 0.3),
                new MoveArmsToPosition(lower, upper, Constants.ArmPositions.lowerHover, Constants.ArmPositions.upperHover, 0.1)
        );
    }
}