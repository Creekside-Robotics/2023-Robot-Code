package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.VisionPoseAPI;


public class SetPose extends InstantCommand {
    private final VisionPoseAPI visionPoseAPI;
    private final Pose2d defaultPose;
    private final Drivetrain drivetrain;

    public SetPose(VisionPoseAPI visionPoseAPI, Drivetrain drivetrain, Pose2d defaultPose) {
        this.visionPoseAPI = visionPoseAPI;
        this.drivetrain = drivetrain;
        this.defaultPose = defaultPose;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.visionPoseAPI);
    }

    @Override
    public void initialize() {
        var pose = this.visionPoseAPI.getNewPose();
        if (pose == null){
            this.drivetrain.setPose(this.defaultPose);
        } else if (this.defaultPose != null) {
            this.drivetrain.setPose(pose);
        }
    }
}
