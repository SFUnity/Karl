package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveDistance extends CommandBase {
    private final DriveSubsystem m_drive;
    private final double m_distance;
    private final double m_speed;

    public DriveDistance(DriveSubsystem drive, double distance, double speed) {
        m_drive = drive;
        m_distance = distance;
        m_speed = speed;

        addRequirements(drive);
    }

    @Override
    public void initialize() {
        m_drive.resetEncoders();
        if (m_speed < 0) {
            m_drive.reverseEncoders();
        }
        m_drive.tankDrive(0, 0);
    }

    @Override
    public void execute() {
        m_drive.tankDrive(m_speed, m_speed);
        System.out.println("Distance from start:" + m_drive.getEncoderDistance());
    }

    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return m_drive.getEncoderDistance() <= m_distance;
    }
}
