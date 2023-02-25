package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Indexer extends SubsystemBase {
    private final CANSparkMax motor;
    private double spinSpeed = 0;

    public Indexer(int canId, boolean reverse){
        this.motor = new CANSparkMax(canId, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.motor.setInverted(reverse);
    }

    public void setSpeed(double speed){
        this.spinSpeed = speed;
    }

    @Override
    public void periodic() {
        this.motor.set(this.spinSpeed);
    }
}
