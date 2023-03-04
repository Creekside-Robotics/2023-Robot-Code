package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Arm;


public class MoveArmsToPosition extends ParallelCommandGroup {
    public MoveArmsToPosition(Arm lower, Arm upper, double lowerPosition, double upperPosition, double speed) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
                new SetArmPosition(lower, lowerPosition, speed, false),
                new SetArmPosition(upper, upperPosition, speed, false)
        );
    }
}
