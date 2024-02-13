// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Calibration;
import frc.robot.commands.Hold;
import frc.robot.commands.SetAngle;
import frc.robot.subsystems.Meter;

public class RobotContainer {
  private final Meter m_meter = new Meter();
  private final Calibration m_calibration = new Calibration(m_meter);
  private final Hold m_hold = new Hold(m_meter);
  private final SetAngle m_setangle = new SetAngle(m_meter);
  private final Joystick m_Joystick = new Joystick(0);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    JoystickButton button[] = { // Joystick Buttons Array
        new JoystickButton(m_Joystick, 1), // *Calibrate
        new JoystickButton(m_Joystick, 2), // *Hold
        new JoystickButton(m_Joystick, 5), // *SetAngle
        new JoystickButton(m_Joystick, 7), // *Invert
        new JoystickButton(m_Joystick, 8) // *ResetEncoder
    };
    // Joystick Buttons Binding
    button[0].toggleOnTrue(m_calibration);
    button[1].toggleOnTrue(m_hold);
    button[2].toggleOnTrue(m_setangle);
    button[3].toggleOnTrue(new InstantCommand(() -> m_meter.invert())); // todo test this
    button[4].toggleOnTrue(new InstantCommand(() -> m_meter.resetEncoder())); // todo test this
  }

}