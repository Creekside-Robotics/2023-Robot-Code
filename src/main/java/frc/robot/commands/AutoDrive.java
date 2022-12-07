package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Communications;
import frc.robot.subsystems.Drivetrain;

public class AutoDrive extends CommandBase {
    Communications communication;
    Drivetrain drivetrain;

    public AutoDrive(Communications communications, Drivetrain drivetrain){
        this.communication = communications;
        this.drivetrain = drivetrain;
        addRequirements(this.drivetrain);
    }

    @Override
    public void initialize() {
        this.communication.setRobotMode("Auto");
    }

    @Override
    public void execute() {
        var velocity = this.communication.getRobotOutput();
        this.drivetrain.drive(velocity[0], velocity[1], velocity[2], true);
    }

    @Override
    public void end(boolean interrupted) {
        this.communication.setRobotMode("Manual");
    }
}
