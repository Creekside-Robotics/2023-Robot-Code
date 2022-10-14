// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drivetrain;

/** A command that resets the drivetrain's gyro to a heading of zero */
public class ResetGyro extends InstantCommand {
  private Drivetrain drivetrain;

  public ResetGyro(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.drivetrain.resetGyro();
  }
}