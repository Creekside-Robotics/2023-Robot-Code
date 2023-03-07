package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;

public class HoverClaw extends SequentialCommandGroup {
    public HoverClaw(Arm upper, Arm lower, Claw claw) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
                new MoveArmsToPosition(lower, upper, Constants.ArmPositions.lowerHover, Constants.ArmPositions.upperHover, 0.1),
                new OpenClaw(claw)
        );
    }
}