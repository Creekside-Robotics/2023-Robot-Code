package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.Constants.ArmPositions;

public class SecondLevelScore extends SequentialCommandGroup {
    public SecondLevelScore(Arm lower, Arm upper, Claw claw) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
                new MoveArmsToPosition(lower, upper, ArmPositions.lowerTranscendentalOne, ArmPositions.upperTranscendentalOne, 0.1),
                new MoveArmsToPosition(lower, upper, ArmPositions.lowerTranscendentalTwo, ArmPositions.upperTranscendentalTwo, 0.3),
                new MoveArmsToPosition(lower, upper, ArmPositions.lowerTwo, ArmPositions.upperTwo, 0.1),
                new OpenClaw(claw)
        );
    }
}