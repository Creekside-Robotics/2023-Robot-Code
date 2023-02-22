package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionPoseAPI extends SubsystemBase {
    NetworkTableInstance networkTableInstance;
    NetworkTable poseTable;
    int latestPoseId = 0;

    public VisionPoseAPI(){
        this.networkTableInstance = NetworkTableInstance.getDefault();
        this.poseTable = this.networkTableInstance.getTable("Pose");
    }

    public Pose2d getPose(){
        var x = this.poseTable.getEntry("xPos").getDouble(0);
        var y = this.poseTable.getEntry("yPos").getDouble(0);
        var r = this.poseTable.getEntry("rPos").getDouble(0);

        if(x == 0 && y == 0 && r == 0){
            return null;
        } else {
            return new Pose2d(x, y, new Rotation2d(r));
        }
    }

    public int getPoseId(){
        return (int) this.poseTable.getEntry("Count").getDouble(0);
    }

    public Pose2d getNewPose(){
        var pose = this.getPose();
        var id = this.getPoseId();

        if(id > this.latestPoseId){
            this.latestPoseId = id;
            return pose;
        }
        return null;
    }
}
