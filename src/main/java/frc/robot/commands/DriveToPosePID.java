package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

import java.util.concurrent.Callable;


public class DriveToPosePID extends CommandBase {
    private final Drivetrain drivetrain;
    private final Callable<Pose2d> poseCallable;
    private final boolean hold;
    private Pose2d finalPose;
    private final PIDController distanceController;
    private final PIDController angleController;

    public DriveToPosePID(Drivetrain drivetrain, Callable<Pose2d> poseCallable, boolean hold) {
        this.drivetrain = drivetrain;
        this.poseCallable = poseCallable;
        this.hold = hold;
        this.distanceController = new PIDController(1, 0, 0.2);
        this.distanceController.setTolerance(0.05);
        this.angleController = new PIDController(1, 0, 0.5);
        this.angleController.setTolerance(0.05);
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.drivetrain);
    }

    @Override
    public void initialize() {
        try {
            this.finalPose = this.poseCallable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute() {
        Pose2d currentPose = this.drivetrain.getPose();
        Translation2d translationalDifference = new Translation2d(
                this.finalPose.getX() - currentPose.getX(),
                this.finalPose.getY() - currentPose.getY()
        );
        Rotation2d translationalDifferenceAngle = new Rotation2d(
                translationalDifference.getX(),
                translationalDifference.getY()
        );
        Rotation2d rotationalDifference = this.finalPose.getRotation().minus(currentPose.getRotation());

        double driveSpeed = getConstrainedOutput(1, this.distanceController, translationalDifference.getNorm());
        double xOutput = driveSpeed * translationalDifferenceAngle.getCos();
        double yOutput = driveSpeed * translationalDifferenceAngle.getSin();
        double rotOutput = this.angleController.calculate(rotationalDifference.getRadians());

        this.drivetrain.drive(xOutput, yOutput, rotOutput, true);
    }

    public double getConstrainedOutput(double speed, PIDController controller, double difference){
        double output = controller.calculate(difference);
        return Math.min(Math.max(output, -speed), speed);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return this.distanceController.atSetpoint() && this.angleController.atSetpoint() && !this.hold;
    }

    @Override
    public void end(boolean interrupted) {}
}
