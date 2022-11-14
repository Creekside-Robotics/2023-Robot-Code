package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/*** Create a new swerve drive style drivetrain */
public class Drivetrain  extends SubsystemBase{

    private SwerveModule frontLeft;
    private SwerveModule frontRight;
    private SwerveModule backLeft;
    private SwerveModule backRight;

    private ADXRS450_Gyro gyro;
    private SwerveDriveKinematics kinematics;
    private SwerveDriveOdometry odometry;

    public Drivetrain(SwerveModule frontRight, SwerveModule frontLeft, SwerveModule backRight, SwerveModule backLeft) {
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;
        this.backRight = backRight;
        this.backLeft = backLeft;
        
        gyro = new ADXRS450_Gyro();
        gyro.reset();

        kinematics = new SwerveDriveKinematics(
            Constants.frontLeftLocation,
            Constants.frontRightLocation,
            Constants.backLeftLocation,
            Constants.backRightLocation
        );
        odometry = new SwerveDriveOdometry(kinematics, gyro.getRotation2d());
    }

    /**
     * Method to drive the robot using joystick info.
     *
     * @param xSpeed Speed of the robot in the x direction (forward).
     * @param ySpeed Speed of the robot in the y direction (sideways).
     * @param rot Angular rate of the robot.
     * @param fieldRelative Whether the provided x and y speeds are relative to the field.
     */
    public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
        SwerveModuleState[] swerveModuleStates =
            kinematics.toSwerveModuleStates(
                fieldRelative
                    ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, gyro.getRotation2d())
                    : new ChassisSpeeds(xSpeed, ySpeed, rot));
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.maxSpeed);
        frontLeft.setDesiredState(swerveModuleStates[0]);
        frontRight.setDesiredState(swerveModuleStates[1]);
        backLeft.setDesiredState(swerveModuleStates[2]);
        backRight.setDesiredState(swerveModuleStates[3]);
    }

    /** Updates the field relative position of the robot. */
    public void updateOdometry() {
        odometry.update(
            gyro.getRotation2d(),
            frontLeft.getState(),
            frontRight.getState(),
            backLeft.getState(),
            backRight.getState());
    }

    public void resetGyro() {
        this.gyro.reset();
    }

}
