package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drivetrain;

public class ResetPose extends InstantCommand {
    private final Drivetrain drivetrain;
    private final Pose2d pose;

    public ResetPose(Drivetrain drivetrain, Pose2d pose){
        this.drivetrain = drivetrain;
        this.pose = pose;
    }

    @Override
    public void initialize() {
        this.drivetrain.setPose(this.pose);
    }
}
