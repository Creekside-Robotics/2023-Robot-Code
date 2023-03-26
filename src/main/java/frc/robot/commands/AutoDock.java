// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoDock extends SequentialCommandGroup {
  /** Creates a new AutoDock. */
  public AutoDock(Drivetrain drive) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new DriveToPosePID(drive, Constants.AutoScorePositions::getDockingPosition, 0.5, 0.1, false, 0),
      new SetDrivetrainOutput(drive, new Pose2d(0.1, 0, new Rotation2d()), false)
    );
  }
}
