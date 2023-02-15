package frc.robot.commands;

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class VisionAlignment extends CommandBase {

  private final VisionSubsystem m_vision;
  private final DriveSubsystem m_drive;
  private boolean done;
  public VisionAlignment(VisionSubsystem subsystem, DriveSubsystem midsystem, boolean finish) {
    m_vision = subsystem;
    m_drive = midsystem;
    done = finish;
    addRequirements(subsystem, midsystem);
  }
  
  @Override
  public void initialize() {
    done=false;
  }

  public void execute() {
    var result = m_vision.getLastResult();
    if (result.getBestTarget().getYaw() < -3) {
      System.out.print("I can move!");
      m_drive.tankDrive(-.5, .5);
            }
    else if (result.getBestTarget().getYaw() > 3) {
      System.out.print("I can move!");
      m_drive.tankDrive(.5, -.5);
            }
    else {
      System.out.print("All done!");
      done = true;
            }
  }


  public boolean isFinished() {
    return done;

  }
  
  @Override
  public void end(boolean interrupted) {
  }
}