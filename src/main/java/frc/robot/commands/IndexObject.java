package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;

public class IndexObject extends SequentialCommandGroup {
    public IndexObject(Arm lower, Arm upper, Claw claw, Intake intake, Indexer indexer) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
                new SetIntake(intake, false, 0.2),
                new HoverClaw(upper, lower, claw),
                new SetIndexerMode(indexer, Indexer.Mode.Clockwise),
                new WaitCommand(1),
                new SetIndexerMode(indexer, Indexer.Mode.CounterClockwise),
                new GrabAndPrep(lower, upper, claw)

        );
    }
}