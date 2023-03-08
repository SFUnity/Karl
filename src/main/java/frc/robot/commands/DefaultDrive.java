// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.DoubleSupplier;
import frc.robot.Constants.DriveConstants;

public class DefaultDrive extends CommandBase {
  private final DriveSubsystem m_drive;
  private final DoubleSupplier m_left;
  private final DoubleSupplier m_right;
  private final DoubleSupplier m_forward;
  private final DoubleSupplier m_backward;
  private final double m_speed;
  private final double kP = 1;
  private static double heading;

  /**
   * Creates a new DefaultDrive.
   *
   * @param subsystem The drive subsystem this command wil run on.
   * @param left      The control input for the left motors
   * @param right     The control input for the right motors
   * @param forward   The control input for the right motors
   * @param backward  The control input for the right motors
   */
  public DefaultDrive(DriveSubsystem subsystem, DoubleSupplier left, DoubleSupplier right, DoubleSupplier forward, DoubleSupplier backward, double speed) {
    m_drive = subsystem;
    m_left = left;
    m_right = right;
    m_forward = forward;
    m_backward = backward;
    m_speed = speed;
    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    DriveConstants.kSpeed = m_speed;
    heading = m_drive.getHeading();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.tankDrive((m_left.getAsDouble() + m_forward.getAsDouble() + (m_backward.getAsDouble() * 3) / 3.0),
        (m_right.getAsDouble() + m_forward.getAsDouble() + (m_backward.getAsDouble() * 3) / 3.0));
    /*
    double error = heading - m_drive.getHeading();
    if (m_forward.getAsDouble() > 0) {
      m_drive.tankDrive(m_forward.getAsDouble() + kP * error, m_forward.getAsDouble() - kP * error);
    } else {
      m_drive.tankDrive(m_backward.getAsDouble() + kP * error, m_backward.getAsDouble() - kP * error);
    }
    */
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
