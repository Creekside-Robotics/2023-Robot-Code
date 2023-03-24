// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Utils.DynamicObject;

public class FieldDisplay extends SubsystemBase {
  private final Field2d m_field = new Field2d();
  private final Drivetrain drivetrain;
  private final VisionObjectAPI visionObjectAPI;


  /** Creates a new FieldDisplay. */
  public FieldDisplay(Drivetrain drivetrain, VisionObjectAPI visionObjectAPI) {
    this.drivetrain = drivetrain;
    this.visionObjectAPI = visionObjectAPI;

    SmartDashboard.putData("Field", m_field);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // Set the robot's pose
    var pose = this.drivetrain.getPose();

    if (pose != null) {
      m_field.setRobotPose(pose);
    }

    // Set poses from vision
    var objects = this.visionObjectAPI.getAllObjects();
    var map = objects.stream().collect(Collectors.groupingBy(obj -> obj.getType()));

    map.forEach((String type, List<DynamicObject> objs) -> {
      var poses = objs.stream().map(obj -> new Pose2d(obj.getPose(), new Rotation2d())).collect(Collectors.toList());
      this.m_field.getObject(type).setPoses(poses);
    });
  }
}
