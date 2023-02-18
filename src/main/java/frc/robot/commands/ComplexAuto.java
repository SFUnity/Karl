package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;

/** A complex auto command that drives forward, releases a hatch, and then drives backward. */
public class ComplexAuto extends SequentialCommandGroup {
    /**
     * Creates a new ComplexAuto.
     *
     * @param drive The drive subsystem this command will run on
     * @param arm The hatch subsystem this command will run on
     */
    public ComplexAuto(DriveSubsystem drive, ArmSubsystem arm) {
        // Place cone or cube
        new PlacePiece(arm);

        // Drive forward the specified distance
        new DriveDistance(-10, drive);
    }
}