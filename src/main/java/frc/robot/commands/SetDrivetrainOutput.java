package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;


public class SetDrivetrainOutput extends CommandBase {
    private final Drivetrain drivetrain;
    private final Pose2d movementVector;
    private final boolean fieldRelative;

    public SetDrivetrainOutput(Drivetrain drivetrain, Pose2d pose2d, boolean fieldRelative) {
        this.drivetrain = drivetrain;
        this.movementVector = pose2d;
        this.fieldRelative = fieldRelative;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.drivetrain);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        this.drivetrain.drive(
                this.movementVector.getX(),
                this.movementVector.getY(),
                this.movementVector.getRotation().getRadians(),
                this.fieldRelative
        );
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
