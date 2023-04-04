// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ArmConstants;

public class Auto extends CommandBase {
    private final ArmSubsystem m_arm;
    private final DriveSubsystem m_drive;
    private final double m_select;
    private double autoStartTime;
    private static double timeElapsed;

    /**
     * Creates a new DefaultDrive.
     *
     * @param subsystem The drive subsystem this command wil run on.
     */
    public Auto(ArmSubsystem subsystem, DriveSubsystem subsystem2, double select) {
        m_arm = subsystem;
        m_drive = subsystem2;
        m_select = select;
        addRequirements(m_arm, m_drive);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        autoStartTime = Timer.getFPGATimestamp();

        DriveConstants.kSpeed = 1.0;

        if (m_select == 1) {
            ArmConstants.AUTO_DRIVE_SPEED = -0.5;
        } else if (m_select == 2) {
            ArmConstants.AUTO_DRIVE_SPEED = -0.5;
            ArmConstants.AUTO_DRIVE_TIME = 5.5;
        } else {
            ArmConstants.AUTO_DRIVE_TIME = 0.0;
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        timeElapsed = Timer.getFPGATimestamp() - autoStartTime;

        if (m_select == 3) {
            timeElapsed = 15;
        }

        if (timeElapsed < ArmConstants.ARM_EXTEND_TIME_S) {
            m_arm.raiseArm();
            m_arm.holdPiece();
            m_drive.tankDrive(0, 0);
        } else if (timeElapsed < ArmConstants.ARM_EXTEND_TIME_S + ArmConstants.AUTO_THROW_TIME_S) {
            m_arm.idleArm();
            m_arm.cubeInConeOut();
            m_drive.tankDrive(0, 0);
        } else if (timeElapsed < ArmConstants.ARM_EXTEND_TIME_S + ArmConstants.AUTO_THROW_TIME_S
                + ArmConstants.ARM_EXTEND_TIME_S) {
            m_arm.lowerArm();
            m_arm.holdPiece();
            m_drive.tankDrive(0, 0);
        } else if (timeElapsed < ArmConstants.ARM_EXTEND_TIME_S + ArmConstants.AUTO_THROW_TIME_S
                + ArmConstants.ARM_EXTEND_TIME_S + ArmConstants.AUTO_DRIVE_TIME) {
            m_arm.idleArm();
            m_arm.holdPiece();
            m_drive.tankDrive(ArmConstants.AUTO_DRIVE_SPEED, ArmConstants.AUTO_DRIVE_SPEED);
        } else {
            m_arm.idleArm();
            m_arm.holdPiece();
            m_drive.tankDrive(0, 0);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return timeElapsed == ArmConstants.ARM_EXTEND_TIME_S + ArmConstants.AUTO_THROW_TIME_S
                + ArmConstants.ARM_EXTEND_TIME_S + ArmConstants.AUTO_DRIVE_TIME;
    }
}