package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Claw extends SubsystemBase {
    private final DoubleSolenoid clawCylinder;
    public Claw(){
        this.clawCylinder = new DoubleSolenoid(
                PneumaticsModuleType.CTREPCM,
                Constants.clawForwardChannel,
                Constants.clawReverseChannel
        );
    }

    public void closeClaw(){
        clawCylinder.set(DoubleSolenoid.Value.kForward);
    }

    public void openClaw(){
        clawCylinder.set(DoubleSolenoid.Value.kReverse);
    }

    public DoubleSolenoid.Value getState(){
        return clawCylinder.get();
    }
}
