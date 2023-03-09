package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Utils;

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
    private final VisionPoseAPI poseAPI;
    private final VisionObjectAPI objectAPI;

    private String onBoardObjectType;

    public Drivetrain(SwerveModule frontRight, SwerveModule frontLeft, SwerveModule backRight, SwerveModule backLeft, VisionPoseAPI poseAPI, VisionObjectAPI objectAPI) {
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
        this.poseAPI = poseAPI;
        this.objectAPI = objectAPI;
        this.onBoardObjectType = "Unknown";
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
                    ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, this.getPose().getRotation()) :
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
        this.updateWithMergedPose();
    }

    public void updateWithMergedPose() {
        var visionPose = this.poseAPI.getNewPose();
        var encoderPose = this.odometry.getPoseMeters();
        if (visionPose != null) {
            this.setPose(Utils.averagePoses(
                    new double[]{0.9, 0.1},
                    new Pose2d[]{encoderPose, visionPose}
            ));
        }
    }

    public void setPose(Pose2d pose) {
        this.odometry.resetPosition(pose, this.gyro.getRotation2d());
    }

    public Pose2d getPose() {
        return this.odometry.getPoseMeters();
    }
    
    public ChassisSpeeds getKinematics() {
        var modules = new ArrayList<>(Arrays.asList(frontLeft, frontRight, backLeft, backRight));
        var states = (SwerveModuleState[]) modules.stream().map(SwerveModule::getState).toArray();
        return kinematics.toChassisSpeeds(states);
    }

    public void setOnboardObjectType(String onBoardObjectType){
        this.onBoardObjectType = onBoardObjectType;
    }

    public String getOnboardObjectType(){
        return this.onBoardObjectType;
    }

    @Override
    public void periodic() {
        this.updateOdometry();
        SmartDashboard.putNumber("xPos", this.getPose().getX());
        SmartDashboard.putNumber("yPos", this.getPose().getY());
        SmartDashboard.putNumber("rPos", this.getPose().getRotation().getRadians());
    }

    public Pose2d getClosestScoringPosition(){
        var currentPose = getPose();
        var scoringPoses = Constants.AutoScorePositions.getScoringPositions();

        Pose2d closestPose = null;
        for (Pose2d pose: scoringPoses){
            if (closestPose == null){
                closestPose = pose;
                continue;
            }
            if (pose.getTranslation().minus(currentPose.getTranslation()).getNorm() < closestPose.getTranslation().minus(currentPose.getTranslation()).getNorm()){
                closestPose = pose;
            }
        }
        return closestPose;
    }

    public Pose2d getClosestPickupPosition(){
        var closestObject = objectAPI.getNearestObject("");
        var fieldRelativeTranslation = closestObject.getPose().rotateBy(getPose().getRotation()).plus(getPose().getTranslation());
        return new Pose2d(fieldRelativeTranslation, new Rotation2d(closestObject.getPose().getX(), closestObject.getPose().getY()).plus(getPose().getRotation()));
    }

    public Pose2d getBestBalancePosition(){
        var defaultBalancePose = Constants.AutoScorePositions.getAutoPositions()[4];
        if(Math.abs(this.getPose().getRotation().minus(defaultBalancePose.getRotation()).getRadians()) < Math.PI/2){
            return new Pose2d(defaultBalancePose.getX(), this.getPose().getY(), defaultBalancePose.getRotation());
        } else {
            return new Pose2d(defaultBalancePose.getX(), this.getPose().getY(), defaultBalancePose.getRotation().plus(new Rotation2d(Math.PI)));
        }
    }

}
