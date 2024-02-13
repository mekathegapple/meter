package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Meter;

/**
 * SetAngle
 * Sets the angle of the meter using PID and Feed Forward Control
 */
public class SetAngle extends Command {
    private final Meter m_meter;
    private double m_setPoint;
    private PIDController m_pidController;

    public SetAngle(Meter meter) {
        m_meter = meter;
        addRequirements(meter);
    }

    @Override
    public void initialize() {
        double kP = SmartDashboard.getNumber("P Value", 0);
        double kI = SmartDashboard.getNumber("I Value", 0);
        double kD = SmartDashboard.getNumber("D Value", 0);
        m_setPoint = SmartDashboard.getNumber("Set Point", 0);
        m_pidController = new PIDController(kP, kI, kD);
        m_pidController.setSetpoint(m_setPoint);
    }

    @Override
    public void execute() {
        double FeedForward = m_meter
                .getFeedForward(m_meter.getDistance() + Math.toRadians(OperatorConstants.startingAngle));
        double vol = FeedForward + m_pidController.calculate(m_meter.getAngle());
        SmartDashboard.putNumber("FeedForward Voltage", FeedForward);
        SmartDashboard.putNumber("PID Voltage", m_pidController.calculate(m_meter.getAngle()));
        m_meter.setVoltage(vol);
    }

    @Override
    public void end(boolean interrupted) {
        m_meter.setVoltage(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}