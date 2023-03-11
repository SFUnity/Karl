// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;

public class Auto extends CommandBase {
    private final ArmSubsystem m_arm;
    private final DriveSubsystem m_drive;
    private double autoStartTime;

    /**
     * Creates a new DefaultDrive.
     *
     * @param subsystem The drive subsystem this command wil run on.
     */
    public Auto(ArmSubsystem subsystem, DriveSubsystem drive) {
        m_arm = subsystem;
        m_drive = drive;
        addRequirements(m_arm);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        autoStartTime = Timer.getFPGATimestamp();
        System.out.print("autoStartTime" + autoStartTime);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        
        double timeElapsed = Timer.getFPGATimestamp() - autoStartTime;
        System.out.print(timeElapsed);

        if (timeElapsed < ArmConstants.ARM_EXTEND_TIME_S) {
            m_arm.raiseArm();
            m_arm.holdPiece();
            m_drive.tankDrive(0, 0);
        } else if (timeElapsed < ArmConstants.ARM_EXTEND_TIME_S + ArmConstants.AUTO_THROW_TIME_S) {
            m_arm.idleArm();
            m_arm.cubeInConeOut();
            m_drive.tankDrive(0, 0);
        } else if (timeElapsed < ArmConstants.ARM_EXTEND_TIME_S + ArmConstants.AUTO_THROW_TIME_S + ArmConstants.ARM_EXTEND_TIME_S) {
            m_arm.lowerArm();
            m_arm.holdPiece();
            m_drive.tankDrive(0, 0);
        } else if (timeElapsed < ArmConstants.ARM_EXTEND_TIME_S + ArmConstants.AUTO_THROW_TIME_S + ArmConstants.ARM_EXTEND_TIME_S + ArmConstants.AUTO_DRIVE_TIME) {
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
        return true;
    }
}