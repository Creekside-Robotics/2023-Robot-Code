// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;

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
    public static final double maxAngularSpeed = 0.5*Math.PI;  // 1/2 rotation per second

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

    public static final double moduleMaxAngularVelocity = 8 * Math.PI;
    public static final double moduleMaxAngularAcceleration = 8 * Math.PI; // radians per second squared

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
    public static final int clawForwardChannel = 1;
    public static final int clawReverseChannel = 0;

    public static class ArmPositions{
        public static double lowerIndexer = (float) -0.138;
        public static double upperIndexer = (float) -0.039;

        public static double lowerHover = (float) -0.23;
        public static double upperHover = (float) 0.09;

        public static double lowerTranscendentalOne = (float) -0.198;
        public static double upperTranscendentalOne = (float) 0.149;

        public static double lowerOne = (float) -0.057;
        public static double upperOne = (float) -0.033;

        public static double lowerTranscendentalTwo = (float) 0.014;
        public static double upperTranscendentalTwo = (float) 0.103;

        public static double lowerTwo = (float) 0.036;
        public static double upperTwo = (float) 0.027;

        public static double lowerTranscendentalThree = (float) 0.234;
        public static double upperTranscendentalThree = (float) -0.120;

        public static double lowerThree = (float) 0.216;
        public static double upperThree = (float) -0.180;
    }

    public static class AutoScorePositions{
        private static Pose2d[] redScorePositions = {
                new Pose2d(new Translation2d(Units.inchesToMeters(71.6), Units.inchesToMeters(22.29)), new Rotation2d(Math.PI)),
                new Pose2d(new Translation2d(Units.inchesToMeters(71.6), Units.inchesToMeters(43.8)), new Rotation2d(Math.PI)),
                new Pose2d(new Translation2d(Units.inchesToMeters(71.6), Units.inchesToMeters(65.76)), new Rotation2d(Math.PI)),
                new Pose2d(new Translation2d(Units.inchesToMeters(71.6), Units.inchesToMeters(87.64)), new Rotation2d(Math.PI)),
                new Pose2d(new Translation2d(Units.inchesToMeters(71.6), Units.inchesToMeters(109.7)), new Rotation2d(Math.PI)),
                new Pose2d(new Translation2d(Units.inchesToMeters(71.6), Units.inchesToMeters(131.2)), new Rotation2d(Math.PI)),
                new Pose2d(new Translation2d(Units.inchesToMeters(71.6), Units.inchesToMeters(153.5)), new Rotation2d(Math.PI)),
                new Pose2d(new Translation2d(Units.inchesToMeters(71.6), Units.inchesToMeters(175.4)), new Rotation2d(Math.PI)),
                new Pose2d(new Translation2d(Units.inchesToMeters(71.6), Units.inchesToMeters(191.36)), new Rotation2d(Math.PI))
        };

        private static Pose2d[] blueScorePositions = {
                new Pose2d(new Translation2d(Units.inchesToMeters(578), Units.inchesToMeters(22.29)), new Rotation2d()),
                new Pose2d(new Translation2d(Units.inchesToMeters(578), Units.inchesToMeters(43.8)), new Rotation2d()),
                new Pose2d(new Translation2d(Units.inchesToMeters(578), Units.inchesToMeters(65.76)), new Rotation2d()),
                new Pose2d(new Translation2d(Units.inchesToMeters(578), Units.inchesToMeters(87.64)), new Rotation2d()),
                new Pose2d(new Translation2d(Units.inchesToMeters(578), Units.inchesToMeters(109.7)), new Rotation2d()),
                new Pose2d(new Translation2d(Units.inchesToMeters(578), Units.inchesToMeters(131.2)), new Rotation2d()),
                new Pose2d(new Translation2d(Units.inchesToMeters(578), Units.inchesToMeters(153.5)), new Rotation2d()),
                new Pose2d(new Translation2d(Units.inchesToMeters(578), Units.inchesToMeters(175.4)), new Rotation2d()),
                new Pose2d(new Translation2d(Units.inchesToMeters(578), Units.inchesToMeters(191.36)), new Rotation2d())
        };

        private static Pose2d[] cycleDownBluePoses = {
                new Pose2d(new Translation2d(Units.inchesToMeters(224.34), Units.inchesToMeters(35.07)), new Rotation2d(Math.PI)),
                new Pose2d(new Translation2d(Units.inchesToMeters(87.92), Units.inchesToMeters(33.1)), new Rotation2d(Math.PI)),
                new Pose2d(new Translation2d(Units.inchesToMeters(87.92), Units.inchesToMeters(185)), new Rotation2d(Math.PI)),
                new Pose2d(new Translation2d(Units.inchesToMeters(227), Units.inchesToMeters(187)), new Rotation2d(Math.PI))
        };

        private static Pose2d[] cycleDownRedPoses = {
                new Pose2d(new Translation2d(Units.inchesToMeters(650 - 224.34), Units.inchesToMeters(35.07)), new Rotation2d()),
                new Pose2d(new Translation2d(Units.inchesToMeters(650 - 87.92), Units.inchesToMeters(33.1)), new Rotation2d()),
                new Pose2d(new Translation2d(Units.inchesToMeters(650 - 87.92), Units.inchesToMeters(185)), new Rotation2d()),
                new Pose2d(new Translation2d(Units.inchesToMeters(650 - 227), Units.inchesToMeters(187)), new Rotation2d())
        };

        private static Pose2d[] specialBlueAutoPoses = {
                new Pose2d(new Translation2d(Units.inchesToMeters(224.34), Units.inchesToMeters(35.07)), new Rotation2d(Math.PI/4)),
                new Pose2d(new Translation2d(Units.inchesToMeters(227), Units.inchesToMeters(187)), new Rotation2d(-Math.PI/4)),
                new Pose2d(new Translation2d(Units.inchesToMeters(227), Units.inchesToMeters(107)), new Rotation2d(Math.PI)),
                new Pose2d(new Translation2d(Units.inchesToMeters(87.92), Units.inchesToMeters(107)), new Rotation2d(0)),
                new Pose2d(new Translation2d(Units.inchesToMeters(153), Units.inchesToMeters(107)), new Rotation2d(Math.PI))
        };

        private static Pose2d[] specialRedAutoPoses = {
                new Pose2d(new Translation2d(Units.inchesToMeters(650 - 224.34), Units.inchesToMeters(35.07)), new Rotation2d(3*Math.PI/4)),
                new Pose2d(new Translation2d(Units.inchesToMeters(650 - 227), Units.inchesToMeters(187)), new Rotation2d(-3*Math.PI/4)),
                new Pose2d(new Translation2d(Units.inchesToMeters(650 - 227), Units.inchesToMeters(107)), new Rotation2d(0)),
                new Pose2d(new Translation2d(Units.inchesToMeters(650 - 87.92), Units.inchesToMeters(107)), new Rotation2d(Math.PI)),
                new Pose2d(new Translation2d(Units.inchesToMeters(650 - 153), Units.inchesToMeters(107)), new Rotation2d(0))
        };

        public static Pose2d[] getScoringPositions(){
            if (DriverStation.getAlliance() == DriverStation.Alliance.Blue){
                return blueScorePositions;
            }
            return redScorePositions;
        }

        public static Pose2d[] getCycleDownPositions(){
            if (DriverStation.getAlliance() == DriverStation.Alliance.Blue){
                return cycleDownBluePoses;
            }
            return cycleDownRedPoses;
        }

        public static Pose2d getCycleZero(){
            return getCycleDownPositions()[0];
        }

        public static Pose2d getCycleOne(){
            return getCycleDownPositions()[1];
        }

        public static Pose2d getCycleTwo(){
            return getCycleDownPositions()[2];
        }

        public static Pose2d getCycleThree(){
            return getCycleDownPositions()[3];
        }

        public static Pose2d[] getAutoPositions(){
            if (DriverStation.getAlliance() == DriverStation.Alliance.Blue){
                return specialBlueAutoPoses;
            }
            return specialRedAutoPoses;
        }
    }


}
