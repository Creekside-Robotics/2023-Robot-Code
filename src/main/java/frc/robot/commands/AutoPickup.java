package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;

public class AutoPickup extends SequentialCommandGroup {
    public AutoPickup(Drivetrain drive, Intake intake) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
                new SetIntake(intake, true, 0.5),
                new DriveToPosePID(drive, drive::getClosestPickupPosition, 0.5, 0.01, false),
                new SetIntake(intake, false, 0)
        );
    }
}