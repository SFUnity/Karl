package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ArmConstants;
import frc.robot.commands.TurnToAngle;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;

public class ComplexAuto extends SequentialCommandGroup {

    public ComplexAuto(DriveSubsystem drive, ArmSubsystem arm, Integer selected) {
        Command PlacePiece = new PlacePieceSimple(arm, ArmConstants.lastGamePiece);
        Command DriveBack8 = new DriveDistance(drive, -8, -0.5);
        Command DriveSlow2Forwards = new DriveDistance(drive, 2, 0.25);
        Command TurnAround = new TurnToAngle(drive, 180);
        Command Turn90 = new TurnToAngle(drive, 90);
        Command BalanceForwards = new PIDDock(drive, false);

        if (selected == 1) {
            addCommands(PlacePiece, DriveBack8);
        } else if (selected == 2) {
            addCommands(PlacePiece, DriveBack8);
        } else if (selected == 3) {
            addCommands(PlacePiece, TurnAround, DriveSlow2Forwards, BalanceForwards);
        } else {
            return;
        }
        addRequirements(drive, arm);
    }
}