package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class AutoBalanceCommand extends CommandBase {

    // Instantiate variables here
    DriveSubsystem m_drive;
    double pitchError = 4;
    double defaultSpeed = .05;

    private Timer timer;

    public AutoBalanceCommand(DriveSubsystem driveSubsystem) {
        m_drive = driveSubsystem;

        addRequirements(m_drive);

        timer = new Timer();

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        timer.reset();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double speed = -Math.copySign(defaultSpeed, m_drive.getPitchDeg());

        if (Math.abs(m_drive.getPitchDeg()) > pitchError) {
            m_drive.tankDrive(speed, speed);
            timer.reset();
        } else {
            m_drive.kinematicDrive(new ChassisSpeeds(0, 0, 0));
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_drive.tankDrive(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (Math.abs(m_drive.getPitchDeg()) < pitchError && timer.get() > 2) {
            return true;
        }

        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}