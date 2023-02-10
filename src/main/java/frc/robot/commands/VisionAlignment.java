package frc.robot.commands;

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class VisionAlignment extends CommandBase {

  private final VisionSubsystem m_vision;
  private final DriveSubsystem m_drive;

  public VisionAlignment(VisionSubsystem subsystem, DriveSubsystem midsystem) {
    m_vision = subsystem;
    m_drive = midsystem;
  }

  @Override
  public void initialize() {
  }

  public void execute() {
    var result = m_vision.getLastResult();
    if (result.hasTargets()) {
      if (result.getBestTarget().getYaw() > 3) {
        m_drive.tankDrive(-.75, .75);
      }
      else if (result.getBestTarget().getYaw() < -3) {
        m_drive.tankDrive(.75, -.75);
      }
      else {
        isFinished();
      }
    }
  }


  public boolean isFinished() {
    return true;
  }
  
  @Override
  public void end(boolean interrupted) {
  }
}