// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class BalanceBangBangCommand extends CommandBase {
    int m_count;
    DriveSubsystem m_drive;
    private final double DRIVE_BANG_BANG_FWD = .225;
    private final double DRIVE_BANG_BANG_BACK = -.16;
    BangBangController m_forward_bang_bang, m_reverse_bang_bang;
    private final int DRIVE_BANG_BANG_SP = 10;

    /** Creates a new BalanceBangBangCommand. */
    public BalanceBangBangCommand(DriveSubsystem driveSubsystem) {
        m_drive = driveSubsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_drive);

        m_forward_bang_bang = new BangBangController();
        m_forward_bang_bang.setSetpoint(DRIVE_BANG_BANG_SP);
        m_reverse_bang_bang = new BangBangController();
        m_reverse_bang_bang.setSetpoint(-DRIVE_BANG_BANG_SP);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_count = 0;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double roll = m_drive.getPitchDeg();

        double speed = (DRIVE_BANG_BANG_FWD * get_fwd_bang_bang(roll)) +
                (DRIVE_BANG_BANG_BACK * get_rev_bang_bang(roll));

        m_drive.tankDrive(speed, speed);

        if (speed == 0) {
            m_count++;
        } else {
            m_count = 0;
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return m_count > 25; // half second
    }

    public double get_fwd_bang_bang(double measurement) {
        double ret = 0;

        // 1 if > setpoint
        if (m_forward_bang_bang.calculate(measurement) == 0) {
            ret = 1;
        }

        return ret;
    }

    public double get_rev_bang_bang(double measurement) {
        return m_reverse_bang_bang.calculate(measurement);
    }
}