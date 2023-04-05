package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class ComplexAuto extends SequentialCommandGroup {

    public ComplexAuto(DriveSubsystem drive, ArmSubsystem arm, String selected, String piece) {
        Command PlacePiece = new PlacePieceSimple(arm, ArmConstants.lastGamePiece);
        Command DriveBack8 = new DriveDistance(drive, 8, -0.5);
        Command DriveSlow2Forwards = new DriveDistance(drive, 2, 0.25);
        Command TurnAround = new TurnToAngle(drive, 180);
        Command Turn90 = new TurnToAngle(drive, 90);
        Command BalanceForwards = new PIDDock(drive, false);

        if (selected == "nothing") {
        } else if (selected == "bump") {
            addCommands(PlacePiece, DriveBack8, Turn90);
        } else if (selected == "flat") {
            addCommands(PlacePiece, DriveBack8, TurnAround);
        } else if (selected == "balance") {
            addCommands(PlacePiece, TurnAround, DriveSlow2Forwards, BalanceForwards);
        }
        addRequirements(drive, arm);
    }
}
