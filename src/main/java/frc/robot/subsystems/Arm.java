package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.ArrayList;

public class Arm extends SubsystemBase {
    private final ArrayList<CANSparkMax> motors;
    private final DutyCycleEncoder encoder;
    private final boolean encoderDirection;
    private final double encoderOffset;

    public Arm(int[] canIds, int encoderPort, double encoderOffset, boolean[] motorForwards, boolean encoderForwards){
        this.motors = new ArrayList<>();
        for(int i = 0; i < canIds.length; i++){
            var motor = new CANSparkMax(canIds[i], CANSparkMaxLowLevel.MotorType.kBrushless);
            motor.setInverted(!motorForwards[i]);
            this.motors.add(motor);
        }
        this.encoder = new DutyCycleEncoder(encoderPort);
        this.encoderOffset = encoderOffset;
        this.encoderDirection = encoderForwards;
    }

    private double getEncoderMultiplier(){
        if(this.encoderDirection){
            return 1;
        } else {
            return -1;
        }
    }

    public void setSpeed(double speed){
        this.motors.forEach((motor) -> motor.set(speed));
    }

    public double getPosition(){
        return (this.encoder.get() + this.encoderOffset) * getEncoderMultiplier() % 1;
    }

    @Override
    public void periodic() {
    }
}
