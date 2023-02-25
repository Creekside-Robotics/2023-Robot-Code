package frc.robot.commands;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

import java.util.List;
import java.util.concurrent.Callable;

public class DriveToPose extends CommandBase {
    private final HolonomicDriveController controller;
    private final Drivetrain drivetrain;
    private final Callable<Pose2d> poseCallable;
    private final Callable<List<Translation2d>> keyPointsCallable;
    private Trajectory trajectory;
    private final Timer timer;

    public DriveToPose(Drivetrain drivetrain, Callable<Pose2d> poseCallable, Callable<List<Translation2d>> keyPointsCallable){
        this.drivetrain = drivetrain;
        this.controller = new HolonomicDriveController(
                new PIDController(1, 0, 0), new PIDController(1, 0, 0),
                new ProfiledPIDController(1, 0, 0,
                    new TrapezoidProfile.Constraints(6.28, 3.14))
        );
        this.controller.setTolerance(new Pose2d(0.03, 0.03, new Rotation2d(0.01)));
        this.poseCallable = poseCallable;
        this.keyPointsCallable = keyPointsCallable;
        this.timer = new Timer();
        addRequirements(this.drivetrain);
    }

    @Override
    public void initialize() {
        try {
            this.trajectory = this.getTrajectoryToPose();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.timer.reset();
        this.timer.start();
    }

    private Trajectory getTrajectoryToPose() throws Exception {
        var initial = this.drivetrain.getPose();
        var config = new TrajectoryConfig(Constants.maxTranslationalSpeed, Constants.maxTranslationalAcceleration);
        //more code may be needed later for reversing
        return TrajectoryGenerator.generateTrajectory(initial, this.keyPointsCallable.call(), this.poseCallable.call(), config).relativeTo(initial); //Converting field relative pose to robot relative pose.
    }

    @Override
    public void execute() {
        var time = this.timer.get();
        var trajectoryState = this.trajectory.sample(time);
        var output = this.controller.calculate(this.drivetrain.getPose(), trajectoryState, trajectoryState.poseMeters.getRotation());
        this.drivetrain.drive(
                output.vxMetersPerSecond,
                output.vyMetersPerSecond,
                output.omegaRadiansPerSecond,
                true
        );
    }

    @Override
    public boolean isFinished() {
        return this.controller.atReference() && this.timer.get() > this.trajectory.getTotalTimeSeconds();
    }
}
