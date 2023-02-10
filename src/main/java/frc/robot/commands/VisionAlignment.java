package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.Constants;

public class VisionAlignment extends CommandBase {

  private final VisionSubsystem m_vision;

  public VisionAlignment(VisionSubsystem subsystem) {
    m_vision = subsystem;
  }

  @Override
  public void initialize() {
  }

  public void execute() {
    var result = m_vision.getLastResult();
    if (result.hasTargets()) {
      turnpower = -turnController.Calculate(result.getBestTarget();)
    }
  }

  @Override
  public void end(boolean interrupted) {
  }
}