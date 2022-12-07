package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Communications extends SubsystemBase {
    private final NetworkTable robotDataTable;
    private final NetworkTable robotOutputTable;
    private final NetworkTable gameDataTable;
    private boolean aprilTagPoseDataIsNew = false;

    public Communications(){
        NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
        this.robotDataTable = networkTableInstance.getTable("Robot Data");
        this.robotOutputTable = networkTableInstance.getTable("Robot Output");
        this.gameDataTable = networkTableInstance.getTable("Game Data");

        this.robotDataTable.addEntryListener(
                "AprilTag Pose",
                (table, key, entry, value, flags) -> this.aprilTagPoseDataIsNew = true,
                EntryListenerFlags.kNew | EntryListenerFlags.kUpdate
        );
    }

    public Pose2d getAprilTagPoseData(){
        if(this.aprilTagPoseDataIsNew){
            this.aprilTagPoseDataIsNew = false;
            double[] poseArray = robotDataTable.getEntry("AprilTag Pose").getDoubleArray(
                    new double[]{0, 0, 0}
            );
            return new Pose2d(new Translation2d(poseArray[0], poseArray[1]), new Rotation2d(poseArray[2]));
        } else {
            return null;
        }
    }

    public void setKinematics(ChassisSpeeds kinematics){
        this.robotDataTable.getEntry("Kinematics").setDoubleArray(
                new double[]{kinematics.vxMetersPerSecond, kinematics.vyMetersPerSecond, kinematics.omegaRadiansPerSecond}
        );
    }

    public void setPose(Pose2d pose){
        this.robotDataTable.getEntry("Fused Pose").setDoubleArray(
                new double[]{pose.getX(), pose.getY(), pose.getRotation().getRadians()}
        );
    }

    public void setRobotMode(String mode){
        this.robotOutputTable.getEntry("Mode").setString(mode);
    }

    public double[] getRobotOutput(){
        return this.robotOutputTable.getEntry("Output").getDoubleArray(
                new double[]{0, 0, 0}
        );
    }

    public void setTeamColor(DriverStation.Alliance alliance){
        this.gameDataTable.getEntry("Team Color").setString(
                alliance == DriverStation.Alliance.Red ? "Red" : "Blue"
        );
    }

    public void setGameTime(double time){
        this.gameDataTable.getEntry("Game Time").setDouble(time);
    }

    @Override
    public void periodic() {
        this.setGameTime(DriverStation.getMatchTime());
        this.setTeamColor(DriverStation.getAlliance());
    }
}
