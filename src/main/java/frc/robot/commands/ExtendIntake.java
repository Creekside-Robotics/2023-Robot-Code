package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Intake;

public class ExtendIntake extends SequentialCommandGroup {
    public ExtendIntake(Intake intake) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
                new SetIntake(intake, true, -1),
                new WaitCommand(1),
                new SetIntake(intake, true, 1)
        );
    }
}