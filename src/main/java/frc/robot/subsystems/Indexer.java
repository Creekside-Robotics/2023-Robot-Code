package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Indexer extends SubsystemBase {
    private final CANSparkMax motor;
    private Mode mode;

    public Indexer(int canId, boolean reverse){
        this.motor = new CANSparkMax(canId, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.motor.setInverted(reverse);
        this.mode = Mode.Stopped;
    }

    public void setMode(Mode mode){
        this.mode = mode;
    }

    @Override
    public void periodic() {
        switch (this.mode){
            case Stopped:
                this.motor.set(0);
                break;
            case Clockwise:
                this.motor.set(0.25);
                break;
            case CounterClockwise:
                this.motor.set(-0.25);
                break;
        }
    }

    public enum Mode{
        Clockwise,
        CounterClockwise,
        Stopped
    }
}
