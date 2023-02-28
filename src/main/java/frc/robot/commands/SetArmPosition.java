// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

// Sets arm to inputted position
public class SetArmPosition extends CommandBase {
    /** Creates a new SetArmPosition. */
    private final Arm arm;
    private final boolean hold;
    private final double endPosition;
    final private PIDController controller;
    final private double speed;

    public SetArmPosition(Arm arm, double position, double speed, boolean hold) {
        // Use addRequirements() here to declare subsystem dependencies.
        this.arm = arm;
        this.hold = hold;
        this.controller = new PIDController(2, 0, 0);
        this.controller.enableContinuousInput(-0.5, 0.5);
        this.controller.setTolerance(0.03);
        this.endPosition = position;
        this.speed = speed;
        addRequirements(this.arm);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double difference = this.arm.getPosition() - this.endPosition;
        double output = this.controller.calculate(difference);
        double constrainedOutput = Math.min(Math.max(output, -this.speed), this.speed);
        this.arm.setSpeed(constrainedOutput);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return this.controller.atSetpoint() && !this.hold;
    }
}
