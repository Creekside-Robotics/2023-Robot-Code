package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private final CANSparkMax motorOne;
    private final CANSparkMax motorTwo;
    private final DoubleSolenoid pistonOne;
    private final DoubleSolenoid pistonTwo;
    private double spinSpeed = 0;

    public Intake(
            int canIdOne,
            int canIdTwo,
            boolean reverseOne,
            boolean reverseTwo,
            int solenoidIdOne,
            int solenoidIdTwo,
            int solenoidIdThree,
            int solenoidIdFour
    ){
        this.motorOne = new CANSparkMax(canIdOne, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.motorOne.setInverted(reverseOne);
        this.motorTwo = new CANSparkMax(canIdTwo, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.motorTwo.setInverted(reverseTwo);
        this.pistonOne = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, solenoidIdOne, solenoidIdTwo);
        this.pistonTwo = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, solenoidIdThree, solenoidIdFour);
    }

    public void setSpeed(double speed){
        this.spinSpeed = speed;
    }

    public void setExtended(boolean extended){
        if(extended){
            this.pistonOne.set(DoubleSolenoid.Value.kForward);
            this.pistonTwo.set(DoubleSolenoid.Value.kForward);
        } else {
            this.pistonOne.set(DoubleSolenoid.Value.kReverse);
            this.pistonTwo.set(DoubleSolenoid.Value.kReverse);
        }
    }

    @Override
    public void periodic() {
        this.motorOne.set(this.spinSpeed);
        this.motorTwo.set(this.spinSpeed);
    }
}
