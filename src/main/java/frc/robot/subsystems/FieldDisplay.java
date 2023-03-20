// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FieldDisplay extends SubsystemBase {
  private final Field2d m_field = new Field2d();
  private final Drivetrain drivetrain;
  // private final VisionObjectAPI visionObjectAPI;


  /** Creates a new FieldDisplay. */
  public FieldDisplay(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
    // this.visionObjectAPI = objectAPI;

    SmartDashboard.putData("Field", m_field);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    var pose = this.drivetrain.getPose();

    if (pose != null) {
      m_field.setRobotPose(pose);
    }
  }
}
