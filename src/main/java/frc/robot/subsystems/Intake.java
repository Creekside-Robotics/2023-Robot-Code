package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private final CANSparkMax motorOne;
    private final CANSparkMax motorTwo;
    private final DoubleSolenoid piston;
    private double spinSpeed = 0;

    public Intake(int canIdOne, int canIdTwo, boolean reverseOne, boolean reverseTwo, int solenoidIdOne, int solenoidIdTwo){
        this.motorOne = new CANSparkMax(canIdOne, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.motorOne.setInverted(reverseOne);
        this.motorTwo = new CANSparkMax(canIdTwo, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.motorTwo.setInverted(reverseTwo);
        this.piston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, solenoidIdTwo, solenoidIdTwo);
    }

    public void setSpeed(double speed){
        this.spinSpeed = speed;
    }

    public void setExtended(boolean extended){
        if(extended){
            this.piston.set(DoubleSolenoid.Value.kForward);
        } else {
            this.piston.set(DoubleSolenoid.Value.kReverse);
        }
    }

    @Override
    public void periodic() {
        this.motorOne.set(this.spinSpeed);
        this.motorTwo.set(this.spinSpeed);
    }
}
