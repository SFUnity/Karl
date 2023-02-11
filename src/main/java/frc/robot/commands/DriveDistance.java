// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveDistance extends CommandBase {
  private final DriveSubsystem m_drive;
  private final double m_setpoint;
  private final double m_speed;
  private double kP = 0.5;
  private double kI = 0.5;
  private static double error;
  private static double errorSum;
  private static double lastTimestamp;
  private static double dt;

  /**
   * Creates a new DriveDistance.
   *
   * @param feet The number of feet the robot will drive
   * @param drive  The drive subsystem on which this command will run
   */
  public DriveDistance(double feet, DriveSubsystem drive) {
    m_setpoint = feet;
    m_speed = (kP * error + kI * errorSum) / 12;
    m_drive = drive;
    addRequirements(m_drive);
  }

  @Override
  public void initialize() {
    m_drive.resetEncoders();
    errorSum = 0;
    lastTimestamp = Timer.getFPGATimestamp();
    m_drive.tankDrive(m_speed, m_speed);
  }

  @Override
  public void execute() {
    error = m_setpoint - m_drive.getAverageEncoderDistance();
    dt = Timer.getFPGATimestamp() - lastTimestamp;

    if (Math.abs(error) < 1) {
      errorSum += error * dt;
    }

    m_drive.tankDrive(m_speed, m_speed);

    lastTimestamp = Timer.getFPGATimestamp();
  }

  // Sets speed to 0 when ended
  @Override
  public void end(boolean interrupted) {
      m_drive.tankDrive(0, 0);
  }

  @Override
  public boolean isFinished() {
    return Math.abs(m_drive.getAverageEncoderDistance()) >= m_setpoint;
  }
}
