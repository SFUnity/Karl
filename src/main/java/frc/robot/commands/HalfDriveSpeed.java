package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

public class HalfDriveSpeed extends CommandBase {

  public HalfDriveSpeed() {}

  @Override
  public void initialize() {
    Constants.DriveConstants.kSpeed = 0.6;
  }

  @Override
  public void end(boolean interrupted) {
    Constants.DriveConstants.kSpeed = 0.75;
  }
}
