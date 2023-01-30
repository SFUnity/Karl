package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import java.util.function.DoubleSupplier;

public class StraightBack extends CommandBase {
    private final DriveSubsystem m_drive;
    private final DoubleSupplier m_left;
    private final DoubleSupplier m_right;

    /**
     * Creates a new DefaultDrive.
     *
     * @param subsystem The drive subsystem this command wil run on.
     * @param left      The control input for the left motors
     * @param right     The control input for the right motors
     */
    public StraightBack(DriveSubsystem drive, DoubleSupplier left, DoubleSupplier right) {
        m_drive = drive;
        m_left = left;
        m_right = right;
        addRequirements(m_drive);
    }

    // Make not interruptible
    public static final InterruptionBehavior kCancelIncoming = InterruptionBehavior.kCancelIncoming;

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_drive.tankDrive(-m_left.getAsDouble() * Constants.DriveConstants.KControllerSensitivity,
                -m_right.getAsDouble() * Constants.DriveConstants.KControllerSensitivity);
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