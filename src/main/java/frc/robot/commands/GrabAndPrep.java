package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;

public class GrabAndPrep extends SequentialCommandGroup {
    public GrabAndPrep(Arm lower, Arm upper, Claw claw) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
                new MoveArmsToPosition(lower, upper, Constants.ArmPositions.lowerIndexer, Constants.ArmPositions.upperIndexer, 0.1),
                new CloseClaw(claw),
                new MoveArmsToPosition(lower, upper, Constants.ArmPositions.lowerTranscendentalOne, Constants.ArmPositions.upperTranscendentalOne, 0.3)
        );
    }
}