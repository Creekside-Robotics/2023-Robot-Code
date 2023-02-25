package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class Utils {
    public static Pose2d averagePoses(double[] weights, Pose2d[] poses){
        double x = 0;
        double y = 0;
        double rotX = 0;
        double rotY = 0;

        for(int i = 0; i < weights.length; i++){
            x += poses[i].getX() * weights[i];
            y += poses[i].getY() * weights[i];
            rotX += poses[i].getRotation().getCos() * weights[i];
            rotY += poses[i].getRotation().getSin() * weights[i];
        }

        return new Pose2d(x, y, new Rotation2d(rotX, rotY));
    }

    public static class DynamicObject{
        private String type;
        private Translation2d pose;

        public DynamicObject(String type, double x, double y){
            this.type = type;
            this.pose = new Translation2d(x, y);
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Translation2d getPose() {
            return pose;
        }

        public void setPose(Translation2d pose) {
            this.pose = pose;
        }
    }
}
