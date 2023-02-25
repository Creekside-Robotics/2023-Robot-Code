package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class SetIntake extends InstantCommand {
    private final Intake intake;
    private final boolean extended;
    private final double speed;

    public SetIntake(Intake intake, boolean extended, double speed){
        this.intake = intake;
        this.extended = extended;
        this.speed = speed;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        this.intake.setSpeed(this.speed);
        this.intake.setExtended(this.extended);
    }
}
