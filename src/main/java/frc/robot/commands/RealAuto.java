package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class RealAuto extends SequentialCommandGroup {

    public RealAuto(DriveSubsystem drive, ArmSubsystem arm) {
        addCommands(
            new Auto(arm, drive),

            new TurnToAngle(drive, -90));
    }
}
