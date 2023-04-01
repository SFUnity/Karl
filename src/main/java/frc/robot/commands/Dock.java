package frc.robot.commands;

import java.util.Map;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class Dock extends CommandBase {
    private Timer dockedTimer;
    private final DriveSubsystem m_drive;
    private double encoderDistanceAtStart;
    private final double direction;
    private final PIDController dockPID;
    public final double dockP;
    private double last_pitch = 0;
    private final double MaxTravelDistance;
    public final double BalancingTolerance; // tolerance in degrees from 0 to decide if the robot is balanced/docked
    private GenericEntry balanced = Shuffleboard.getTab("Main")
            .add("Balanced?", false)
            .withWidget("Boolean Box")
            .withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "maroon"))
            .getEntry();

    public Dock(DriveSubsystem drivetrain, double direction) {
        this.direction = direction;
        MaxTravelDistance = 1.5 * direction;
        BalancingTolerance = 2;
        dockP = 0.02;
        dockPID = new PIDController(dockP, 0, dockP / 5);
        m_drive = drivetrain;
        dockedTimer = new Timer();
    }

    @Override
    public void initialize() {
        last_pitch = m_drive.getPitchDeg();
        encoderDistanceAtStart = m_drive.getEncoderDistance();
    }

    @Override
    public void execute() {
        double pitch = m_drive.getPitchDeg();
        // PID loop tries to go towards the setpoint, so in general, a positive
        // currentValue and a 0 setpoint will return negative output
        // this is why it actually runs at the opposite of what the PID loop says
        // if direction is -1, then the pid loop will just be reversed and forward will
        // be negative without needing to be multiplied by direction
        double forward = -dockPID.calculate(pitch, 0);
        System.out.println("Docking @ " + forward + " m/s");

        if (getDistanceFromStart() * direction < MaxTravelDistance * direction && Math.abs(pitch - last_pitch) < 1) {
            m_drive.tankDrive(forward, forward);
        } else {
            System.out.println("Stopped docking - too close to edge!");
            m_drive.kinematicDrive(new ChassisSpeeds(0, 0, 0));
        }

        if (Math.abs(pitch) < BalancingTolerance) {
            dockedTimer.start();
            balanced.setBoolean(true);
        } else {
            dockedTimer.reset();
        }
        last_pitch = pitch;
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            System.out.println("docking interrupted");
        } else {
            System.out.println("Successfully docked!");
        }

        System.out.println("end angle: " + m_drive.getPitchDeg() + " degrees");
        System.out.println("distance from start: " + getDistanceFromStart() + " meters");
        m_drive.kinematicDrive(new ChassisSpeeds(0, 0, 0));
    }

    @Override
    public boolean isFinished() {
        // finish if docked for more than the minimum dock time
        if (dockedTimer.get() > 0.32) {
            return true;
        }
        return false;
    }

    private double getDistanceFromStart() {
        return m_drive.getEncoderDistance() - encoderDistanceAtStart;
    }
}