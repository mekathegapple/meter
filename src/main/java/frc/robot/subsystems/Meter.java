package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.EncoderConstants;
import frc.robot.Constants.OperatorConstants;

/**
 * Meter
 */
public class Meter extends SubsystemBase {
    private Encoder m_encoder;
    private WPI_VictorSPX m_armMotor;
    private PowerDistribution m_pdp;
    
    public Meter() {
        m_encoder = new Encoder(2, 3);
        m_armMotor = new WPI_VictorSPX(1);
        m_pdp = new PowerDistribution();
        m_armMotor.setNeutralMode(NeutralMode.Coast);
        configureEncoder(m_encoder, true);
        initDashboard();

    }

    // * Motor Methods * //

    public void invert() { //Inverts The Motors
        boolean invertion = m_armMotor.getInverted();
        m_armMotor.setInverted(!invertion);
        SmartDashboard.putBoolean("Invertion", !invertion);
    }

    public void setVoltage(double Voltage) {//Sets The Voltage Of The Motors
        m_armMotor.setVoltage(Voltage);
    }

    public boolean isPowered() { //Returns If The Motors Are Powered
        return m_armMotor.getMotorOutputVoltage() != 0 ?  true :  false;
    }

    // * Encoder Methods * //

    public void configureEncoder(Encoder encoder, boolean isInverted) { // Encoder configuration
        encoder.setDistancePerPulse(EncoderConstants.kDistancePerPulse);
        encoder.setReverseDirection(isInverted);
        encoder.reset();
    }

    public double getDistance() { // Get Distance Value From Encoders
        return m_encoder.getDistance();
    }

    public double getAngle() { // Get Angle Value From Encoders
        return (Math.toDegrees(m_encoder.getDistance()) + OperatorConstants.startingAngle);
    }

    // * Other Methods * //

    public double getFeedForward(double angleInRadian) { // Calculates The Feed Forward Value
        double direction = Math.cos(angleInRadian) < 0 ? -1 : 1;
        return direction * (3.91 * Math.abs(Math.cos(angleInRadian)) + 1.8);
    }

    public void initDashboard() { //!Temporary Method
        SmartDashboard.putNumber("Voltage", 0);
        SmartDashboard.putNumber("P Value", 0);
        SmartDashboard.putNumber("I Value", 0);
        SmartDashboard.putNumber("D Value", 0);
        SmartDashboard.putNumber("Set Point", 0);
    }

    public void debug() { //Prints The Values To Debug To The Dashboard
        SmartDashboard.putNumber("Encoder Value", m_encoder.getDistance());
        SmartDashboard.putNumber("Encoder Raw Value", m_encoder.get());
        SmartDashboard.putNumber("Encoder Value + Starting Angle",
                getDistance() + Math.toRadians(OperatorConstants.startingAngle));
        SmartDashboard.putNumber("Total Current", m_pdp.getTotalCurrent());
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Motor Voltage", m_armMotor.getMotorOutputVoltage());
    }
}