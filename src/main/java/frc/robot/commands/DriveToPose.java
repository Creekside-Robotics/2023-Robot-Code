package frc.robot.commands;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

import java.util.concurrent.Callable;

public class DriveToPose extends CommandBase {
    private final HolonomicDriveController controller;
    private final Drivetrain drivetrain;
    private final Callable<Pose2d> poseCallable;
    private Pose2d goalPose;

    public DriveToPose(Drivetrain drivetrain, Callable<Pose2d> callable){
        this.drivetrain = drivetrain;
        this.controller = new HolonomicDriveController(
                new PIDController(1, 0, 0), new PIDController(1, 0, 0),
                new ProfiledPIDController(1, 0, 0,
                    new TrapezoidProfile.Constraints(6.28, 3.14))
        );
        this.controller.setTolerance(new Pose2d(0.03, 0.03, new Rotation2d(0.01)));
        this.poseCallable = callable;
        addRequirements(this.drivetrain);
    }

    @Override
    public void initialize() {
        try {
            this.goalPose = (Pose2d) poseCallable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute() {
        this.controller.calculate(drivetrain.getPose(), this.goalPose, 0, Rotation2d.fromDegrees(0));
    }

    @Override
    public boolean isFinished() {
        return this.controller.atReference();
    }
}
