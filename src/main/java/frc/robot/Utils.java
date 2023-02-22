package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

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
}
