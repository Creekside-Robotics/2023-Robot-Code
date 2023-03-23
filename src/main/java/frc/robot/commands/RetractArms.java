package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;

public class RetractArms extends SequentialCommandGroup {
    public RetractArms(Arm lower, Arm upper) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
                new SetArmPosition(upper, Constants.ArmPositions.upperTranscendentalOne, 0.1, false),
                new SetArmPosition(lower, Constants.ArmPositions.lowerTranscendentalOne, 0.1, false),
                new MoveArmsToPosition(lower, upper, Constants.ArmPositions.lowerHover, Constants.ArmPositions.upperHover, 0.1)
        );
    }
}