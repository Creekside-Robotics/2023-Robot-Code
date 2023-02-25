package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Claw;

public class CloseClaw extends InstantCommand {
    private final Claw claw;

    public CloseClaw(Claw claw){
        this.claw = claw;
        addRequirements(this.claw);
    }

    @Override
    public void initialize() {
        this.claw.closeClaw();
    }
}
