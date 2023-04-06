package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class TurnToAngle extends CommandBase {
    private final DriveSubsystem m_drive;
    private final double m_setpoint;
    private final double kP = 0.03;
    private final double kI = 0;
    private final double kD = 0.0055;
    private final PIDController turnPID = new PIDController(kP, kI, kD);

    /**
     * @param subsystem The subsystem used by this command.
     */
    public TurnToAngle(DriveSubsystem subsystem, double setpoint) {
        m_drive = subsystem;
        m_setpoint = setpoint;
        addRequirements(m_drive);
    }

    @Override
    public void initialize() {
        m_drive.resetGyro();
    }

    @Override
    public void execute() {
        // Turns the robot to face the desired direction
        m_drive.arcadeDrive(0.0, turnPID.calculate(m_drive.getHeading(), m_setpoint));
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        // return false;
        return (m_setpoint - 5) < m_drive.getHeading() && m_drive.getHeading() < (m_setpoint + 5);
    }
}