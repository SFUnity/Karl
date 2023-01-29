package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class StraightForward extends CommandBase {
    private final DriveSubsystem m_drive;

    /**
     * @param drive The drive subsystem on which this command will run
     */
    public StraightForward(DriveSubsystem drive) {
        m_drive = drive;
        addRequirements(m_drive);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_drive.tankDrive(Constants.DriveConstants.KControllerSensitivity, Constants.DriveConstants.KControllerSensitivity);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
