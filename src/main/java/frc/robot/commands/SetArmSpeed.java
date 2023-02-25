package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class SetArmSpeed extends CommandBase {
    private final Arm arm;
    private final double speed;

    public SetArmSpeed(Arm arm, double speed) {
        this.arm = arm;
        this.speed = speed;
        addRequirements(this.arm);
    }

    @Override
    public void execute() {
        this.arm.setSpeed(speed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}