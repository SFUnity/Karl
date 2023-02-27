// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveDistance extends CommandBase {
  private final DriveSubsystem m_drive;
  private final double kP = 1;
  private static double heading;
  private final double m_distance;

  /**
   * Creates a new DriveDistance.
   *
   * @param distance The number of feet the robot will drive
   * @param drive    The drive subsystem on which this command will run
   */
  public DriveDistance(double distance, DriveSubsystem drive) {
    m_drive = drive;
    m_distance = distance;
    addRequirements(m_drive);
  }

  @Override
  public void initialize() {
    m_drive.resetEncoders();
    heading = m_drive.getHeading();
    m_drive.tankDrive(0, 0);
  }

  @Override
  public void execute() {
    double error = heading - m_drive.getHeading();
    m_drive.tankDrive(.5 + kP * error, .5 - kP * error);
  }

  // Sets speed to 0 when ended
  @Override
  public void end(boolean interrupted) {
    m_drive.tankDrive(0, 0);
  }

  @Override
  public boolean isFinished() {
    return Math.abs(m_drive.getAverageEncoderDistance()) >= m_distance;
  }
}
