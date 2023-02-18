package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VisionSubsystem;
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
    boolean HasTarget = result.hasTargets();
    if (HasTarget) {
      HasTarget = true;
      if (result.getBestTarget().getYaw() < -3) {
        System.out.print("I can move!");
        new TurnToAngle(m_drive, result.getBestTarget().getYaw());
        }
      else if (result.getBestTarget().getYaw() > 3) {
        System.out.print("I can move!");
        new TurnToAngle(m_drive, result.getBestTarget().getYaw());
        }
      else {
        System.out.print("All done!");
        done = true;
        }
      }
    else {
      HasTarget = false;
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