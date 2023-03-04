package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.Constants.ArmPositions;

public class FirstLevelScore extends SequentialCommandGroup {
    public FirstLevelScore(Arm lower, Arm upper, Claw claw) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
                new MoveArmsToPosition(lower, upper, ArmPositions.lowerTranscendentalOne, ArmPositions.upperTranscendentalOne, 0.1),
                new MoveArmsToPosition(lower, upper, ArmPositions.lowerOne, ArmPositions.upperOne, 0.1),
                new OpenClaw(claw),
                new MoveArmsToPosition(lower, upper, ArmPositions.lowerTranscendentalOne, ArmPositions.upperTranscendentalOne, 0.3),
                new MoveArmsToPosition(lower, upper, ArmPositions.lowerHover, ArmPositions.upperHover, 0.1)
        );
    }
}