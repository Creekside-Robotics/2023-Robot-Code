package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Claw;

public class CloseClaw extends CommandBase {
    private final Claw claw;
    private final Timer timer;

    public CloseClaw(Claw claw){
        this.claw = claw;
        this.timer = new Timer();
        addRequirements(this.claw);
    }

    @Override
    public void initialize() {
        this.claw.closeClaw();
        this.timer.reset();
        this.timer.start();
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return this.timer.get() > 0.5;
    }
}
