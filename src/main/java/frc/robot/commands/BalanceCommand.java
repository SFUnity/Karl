package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

/**
 * Command to balance the charge station
 */
public class BalanceCommand extends CommandBase {

    private final DriveSubsystem m_drive;
    private final boolean m_isReverse;
    private boolean done;

    public BalanceCommand(DriveSubsystem drivetrainSubsystem, boolean isReverse) {
        m_drive = drivetrainSubsystem;
        m_isReverse = isReverse;

        addRequirements(m_drive);
    }

    @Override
    public void initialize() {
        done = false;
    }

    @Override
    public void execute() {
        var yAccel = m_drive.getGyroAccelX();
        if (m_isReverse) {
            yAccel *= -1;
        }
        if (yAccel > 10 || done) {
            done = true;
            m_drive.tankDrive(0, 0);
        } else {
            var speed = 0.5;
            if (m_isReverse) {
                speed *= -1;
            }
            m_drive.kinematicDrive(new ChassisSpeeds(speed, 0.0, 0.0));
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_drive.tankDrive(0, 0);
    }

}