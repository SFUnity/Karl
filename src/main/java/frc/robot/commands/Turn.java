// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class Turn extends CommandBase {
  private final DriveSubsystem m_drive;
  private final double m_setpoint;

  /**
   * @param subsystem The subsystem used by this command.
   */
  public Turn(DriveSubsystem subsystem, double setpoint) {
    m_drive = subsystem;
    m_setpoint = setpoint;
    addRequirements(m_drive);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    // Turns the robot to face the desired direction
    if (m_setpoint == 90) {
      m_drive.arcadeDrive(0, -1.0);
    } else if (m_setpoint == 270) {
      m_drive.arcadeDrive(0, 1.0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}