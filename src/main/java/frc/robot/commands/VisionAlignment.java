package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.Constants;

public class VisionAlignment extends CommandBase {

  public VisionAlignment() {}

  @Override
  public void initialize() {
  }

  public void execute() {
    var result1 = getLastResult();
  }

  @Override
  public void end(boolean interrupted) {
  }
}