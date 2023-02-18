// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class TurnToAngle extends CommandBase {
  private final DriveSubsystem m_drive;
  private final DoubleSupplier m_setpoint;
  private final double kP = 0.05;

  /**
   * @param subsystem The subsystem used by this command.
   */
  public TurnToAngle(DriveSubsystem subsystem, DoubleSupplier setpoint) {
    m_drive = subsystem;
    m_setpoint = setpoint;
    addRequirements(m_drive);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    // Find the heading error; setpoint is 90
    double error = m_setpoint.getAsDouble() - m_drive.getHeading();

    // Turns the robot to face the desired direction
    m_drive.tankDrive(kP * error, -kP * error);
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