package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.Objects;

public class VisionObjectAPI extends SubsystemBase {
    NetworkTableInstance networkTableInstance;
    NetworkTable objectTable;

    public VisionObjectAPI() {
        this.networkTableInstance = NetworkTableInstance.getDefault();
        this.objectTable = this.networkTableInstance.getTable("Object");
    }

    public Translation2d getNearestObject(String objectType) {
        // Two other ways to filter objectType:
        // Figure out a way to filter out all but a certain objectType when obtaining data from the NetworkTable
        // Delete those entries and their corresponding x and y values after obtaining the data

        var name = this.objectTable.getEntry("Name").getStringArray(null);
        var x = this.objectTable.getEntry("xPos").getDoubleArray(new double[]{});
        var y = this.objectTable.getEntry("yPos").getDoubleArray(new double[]{});

        var maxTranslation = new Translation2d();

        for (int i = 0; i < name.length; i++) {
            if (!Objects.equals(objectType, name[i])) {
                continue;
            }
            var translation = new Translation2d(x[i], y[i]);
            var distance = translation.getNorm();

            if (distance > maxTranslation.getNorm()) {
                maxTranslation = translation;
            }
        }

        return maxTranslation;
    }

}