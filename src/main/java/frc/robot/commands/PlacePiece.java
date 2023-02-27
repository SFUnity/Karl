// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ArmSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;

public class PlacePiece extends CommandBase {
    private final ArmSubsystem m_arm;
    private final double m_autonomousStartTime;

    /**
     * Creates a new DefaultDrive.
     *
     * @param subsystem The drive subsystem this command wil run on.
     */
    public PlacePiece(ArmSubsystem subsystem) {
        m_arm = subsystem;
        m_autonomousStartTime = Timer.getFPGATimestamp();
        addRequirements(m_arm);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        
        double timeElapsed = Timer.getFPGATimestamp() - m_autonomousStartTime;

        if (timeElapsed < ArmConstants.ARM_EXTEND_TIME_S) {
            m_arm.raiseArm();
            m_arm.holdPiece();
        } else if (timeElapsed < ArmConstants.ARM_EXTEND_TIME_S + ArmConstants.AUTO_THROW_TIME_S) {
            m_arm.idleArm();
            if (ArmConstants.lastGamePiece == ArmConstants.CUBE) {
                m_arm.coneInCubeOut();
            } else {
                m_arm.cubeInConeOut();
            }
        } else if (timeElapsed < ArmConstants.ARM_EXTEND_TIME_S + ArmConstants.AUTO_THROW_TIME_S + ArmConstants.ARM_EXTEND_TIME_S) {
            m_arm.lowerArm();
            m_arm.holdPiece();
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