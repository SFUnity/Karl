package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

public class MaxDriveSpeed extends CommandBase {

  public MaxDriveSpeed() {}

  @Override
  public void initialize() {
    Constants.DriveConstants.kSpeed = 2.0;
  }

  @Override
  public void end(boolean interrupted) {
    Constants.DriveConstants.kSpeed = 0.75;
  }
}
