// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveDistance extends CommandBase {
  private final DriveSubsystem m_drive;
  private final double m_speed;
  private final PIDController pidController;

  /**
   * Creates a new DriveDistance.
   *
   * @param setpoint The number of feet the robot will drive
   * @param drive    The drive subsystem on which this command will run
   */
  public DriveDistance(double setpoint, DriveSubsystem drive) {
    m_drive = drive;
    pidController = new PIDController(0.5, 0.5, 0.1);
    pidController.setSetpoint(setpoint);
    m_speed = pidController.calculate(m_drive.getAverageEncoderDistance());
    addRequirements(m_drive);
  }

  @Override
  public void initialize() {
    m_drive.resetEncoders();
    pidController.reset();
    m_drive.tankDrive(m_speed, m_speed);
  }

  @Override
  public void execute() {
    m_drive.tankDrive(m_speed, m_speed);
  }

  // Sets speed to 0 when ended
  @Override
  public void end(boolean interrupted) {
    m_drive.tankDrive(0, 0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
