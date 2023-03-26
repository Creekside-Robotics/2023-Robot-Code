package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Utils;

import java.util.ArrayList;
import java.util.Objects;

public class VisionObjectAPI extends SubsystemBase {
    NetworkTableInstance networkTableInstance;
    NetworkTable objectTable;

    public VisionObjectAPI() {
        this.networkTableInstance = NetworkTableInstance.getDefault();
        this.objectTable = this.networkTableInstance.getTable("Objects");
    }

    public Utils.DynamicObject getNearestObject(String objectType) {
        // Two other ways to filter objectType:
        // Figure out a way to filter out all but a certain objectType when obtaining data from the NetworkTable
        // Convert the Arrays to ArrayLists (or just have them start as lists) in order to be able to delete all but a certain
        // objectType (name, x, and y) after obtaining the data from the NetworkTable
        // Current way just skips those entries in the for loop

        var name = this.objectTable.getEntry("Name").getStringArray(new String[]{});
        var x = this.objectTable.getEntry("xPos").getDoubleArray(new double[]{});
        var y = this.objectTable.getEntry("yPos").getDoubleArray(new double[]{});

        var maxTranslation = new Translation2d();
        int maxObjectId = -1;

        for (int i = 0; i < name.length; i++) {
            if (!Objects.equals(objectType, name[i]) && objectType != null) {
                continue;
            }
            var translation = new Translation2d(x[i], y[i]);
            var distance = translation.getNorm();

            if (distance < maxTranslation.getNorm()) {
                maxTranslation = translation;
                maxObjectId = i;
            }
        }

        if(maxObjectId != -1){
            return new Utils.DynamicObject(name[maxObjectId], x[maxObjectId], y[maxObjectId]);
        } else {
            return null;
        }
    }

    public ArrayList<Utils.DynamicObject> getAllObjects() {
        var name = this.objectTable.getEntry("Name").getStringArray(new String[]{});
        var x = this.objectTable.getEntry("xPos").getDoubleArray(new double[]{});
        var y = this.objectTable.getEntry("yPos").getDoubleArray(new double[]{});
        
        var list = new ArrayList<Utils.DynamicObject>();

        for (int i = 0; i < name.length; i++) {
            list.add(new Utils.DynamicObject(name[i], x[i], y[i]));
        }
        
        return list;
    }
}
