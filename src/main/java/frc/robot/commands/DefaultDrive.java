// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.DoubleSupplier;

public class DefaultDrive extends CommandBase {
  private final DriveSubsystem m_drive;
  private final DoubleSupplier m_left;
  private final DoubleSupplier m_right;
  private final DoubleSupplier m_forward;
  private final DoubleSupplier m_backward;
  private double leftFinal;
  private double rightFinal;

  /**
   * Creates a new DefaultDrive.
   *
   * @param subsystem The drive subsystem this command wil run on.
   * @param left      The control input for the left motors
   * @param right     The control input for the right motors
   * @param forward     The control input for the right motors
   * @param backward     The control input for the right motors
   */
  public DefaultDrive(DriveSubsystem subsystem, DoubleSupplier left, DoubleSupplier right, DoubleSupplier forward, DoubleSupplier backward) {
    m_drive = subsystem;
    m_left = left;
    m_right = right;
    m_forward = forward;
    m_backward = backward;
    leftFinal = (m_left.getAsDouble() * (m_forward.getAsDouble() * m_backward.getAsDouble() + 1) - 1) / 3.0;
    rightFinal = (m_right.getAsDouble() * (m_forward.getAsDouble() * m_backward.getAsDouble() + 1) - 1) / 3.0;
    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.tankDrive(leftFinal, rightFinal);
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
