package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Meter;

/**
 * Hold
 * Holds the meter in its position giving feedforward voltage
 */
public class Hold extends Command {
    private final Meter m_meter;

    public Hold(Meter meter) {
        m_meter = meter;
        addRequirements(meter);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double vol = m_meter.getFeedForward(m_meter.getDistance() + Math.toRadians(OperatorConstants.startingAngle));
        SmartDashboard.putNumber("FeedForward Voltage", vol);
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