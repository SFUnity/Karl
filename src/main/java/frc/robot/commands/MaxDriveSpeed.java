// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

public class MaxDriveSpeed extends CommandBase {

  public MaxDriveSpeed() {}

  @Override
  public void initialize() {
    Constants.DriveConstants.KControllerSensitivity = 1.0;
  }

  @Override
  public void end(boolean interrupted) {
    Constants.DriveConstants.KControllerSensitivity = 0.75;
  }
}
