package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Meter;

/**
 * Calibration
 * Used to calibrate the meter to form a linear equation of voltages and torques
 */
public class Calibration extends Command {
    private final Meter m_meter;

    public Calibration(Meter meter) {
        m_meter = meter;
        addRequirements(meter);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double vol = SmartDashboard.getNumber("Voltage", 0.0);
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