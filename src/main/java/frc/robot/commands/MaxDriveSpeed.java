// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MaxDriveSpeed extends CommandBase {
  private final DriveSubsystem m_drive;

  public MaxDriveSpeed(DriveSubsystem drive) {
    m_drive = drive;
  }

  @Override
  public void initialize() {
    m_drive.setMaxOutput(1.0);
  }

  @Override
  public void end(boolean interrupted) {
    m_drive.setMaxOutput(0.75);
  }
}
