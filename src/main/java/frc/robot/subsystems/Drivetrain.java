package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import java.util.ArrayList;
import java.util.Arrays;

/*** Create a new swerve drive style drivetrain */
public class Drivetrain extends SubsystemBase{

    private final SwerveModule frontLeft;
    private final SwerveModule frontRight;
    private final SwerveModule backLeft;
    private final SwerveModule backRight;
    private final ADXRS450_Gyro gyro;
    private final SwerveDriveKinematics kinematics;
    private final SwerveDriveOdometry odometry;
    private final Communications communications;

    public Drivetrain(SwerveModule frontRight, SwerveModule frontLeft, SwerveModule backRight, SwerveModule backLeft, Communications communications) {
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;
        this.backRight = backRight;
        this.backLeft = backLeft;
        this.communications = communications;
        
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
                    ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, this.odometry.getPoseMeters().getRotation()) :
                        new ChassisSpeeds(xSpeed, ySpeed, rot));
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.maxWheelSpeed);
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
            backRight.getState()
        );

        Pose2d aprilTagPose = communications.getAprilTagPoseData();
        if (aprilTagPose != null && aprilTagPose.getX() != 0){
            this.setPose(averagePoses(
                    new Pose2d[]{aprilTagPose, odometry.getPoseMeters()},
                    new double[]{0.1, 0.9}
            ));
        }
    }

    static Pose2d averagePoses(Pose2d[] poses, double[] weights){
        double x = 0;
        double y = 0;
        double[] theta = {0, 0};

        for(int i = 0; i < poses.length; i++){
            x += poses[i].getX() * weights[i];
            y += poses[i].getY() * weights[i];
            theta[0] += Math.cos(poses[i].getRotation().getRadians()) * weights[i];
            theta[1] += Math.sin(poses[i].getRotation().getRadians()) * weights[i];
        }

        return new Pose2d(new Translation2d(x, y), new Rotation2d(theta[0], theta[1]));
    }

    public void setPose(Pose2d pose) {
        this.odometry.resetPosition(pose, this.gyro.getRotation2d());
    }

    public Pose2d getPose() {
        return this.odometry.getPoseMeters();
    }
    
    public ChassisSpeeds getKinematics() {
        var modules = new ArrayList<SwerveModule>(Arrays.asList(frontLeft, frontRight, backLeft, backRight));
        var states = (SwerveModuleState[]) modules.stream().map(module -> module.getState()).toArray();
        return kinematics.toChassisSpeeds(states);
    }

    @Override
    public void periodic() {
        this.updateOdometry();
    }
}
