// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    // todo fill out values correctly, most were taken from the examples

    // Drivetrain
    public static final double maxWheelSpeed = 10;  // 3 meters per second
    public static final double maxTranslationalSpeed = 1;
    public static final double maxTranslationalAcceleration = 1;
    public static final double maxAngularSpeed = Math.PI;  // 1/2 rotation per second

    // SwerveModule
    public static final double wheelDiameter = 0.098;
    public static final double gearRatio = 1/8.14;
    public static final double wheelCircumference = Math.PI * wheelDiameter;

    // Drive-motor Constants
    public static final double driveKs = 0.0888;
    public static final double driveKv = 0.1214 / gearRatio / wheelCircumference;
    public static final double driveKa = 0.00314 / gearRatio / wheelCircumference;
    public static final double driveKp = 0;
    public static final double driveKd = 0;
    public static final double driveKi = 0;

    //Turn motor Constants
    public static final double turnKs = 0.0807;
    public static final double turnKv = 2.5952 / (2*Math.PI);
    public static final double turnKa = 0.0849 / (2*Math.PI);
    public static final double turnKp = 1.7544 / (2*Math.PI);
    public static final double turnKd = 0;
    public static final double turnKi = 0;

    public static final double moduleMaxAngularVelocity = 2 * Math.PI;
    public static final double moduleMaxAngularAcceleration = 2 * Math.PI; // radians per second squared

    // Joystick layout
    public static final int fieldOrientedDriveToggle = 3;

    // Wheel locations
    public static final Translation2d frontLeftLocation = new Translation2d(0.3115, 0.3115);
    public static final Translation2d frontRightLocation = new Translation2d(0.3115, -0.3115);
    public static final Translation2d backLeftLocation = new Translation2d(-0.3115, 0.3115);
    public static final Translation2d backRightLocation = new Translation2d(-0.3115, -0.3115);

    // Starting Positions
    public static final Pose2d startingPose = new Pose2d(
            1,
            1,
            new Rotation2d(0)
    );

    // Claw Constants
    public static final int clawForwardChannel = 0;
    public static final int clawReverseChannel = 1;
}
