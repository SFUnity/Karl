// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.DefaultDrive;
import frc.robot.commands.DefaultArm;
import frc.robot.commands.Turn;
import frc.robot.commands.TurnToAngle;
import frc.robot.commands.VisionAlignment;
import frc.robot.commands.Auto.DefaultAuto;
import frc.robot.commands.Auto.DriveDistance;
import frc.robot.commands.Auto.PIDDock;
import frc.robot.commands.Auto.PlacePieceSimple;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
        // The robot's subsystems and commands are defined here...
        private final DriveSubsystem m_robotDrive = new DriveSubsystem();
        private final ArmSubsystem m_robotArm = new ArmSubsystem();
        private final VisionSubsystem m_robotVision = new VisionSubsystem();

        public static final int CONE = 1;
        public static final int CUBE = 2;
        public final SendableChooser<Integer> m_chooser2 = new SendableChooser<>();

        // Auto piece chooser
        private final Command kNormalAuto = new DefaultAuto(m_robotArm, m_robotDrive, 1);
        private final Command kMiddleAuto = new DefaultAuto(m_robotArm, m_robotDrive, 0);
        private final Command kBumpAuto = new DefaultAuto(m_robotArm, m_robotDrive, 2);
        private final Command kNothingAuto = new DefaultAuto(m_robotArm, m_robotDrive, 3);
        private final Command kPIDDock = new PIDDock(m_robotDrive, false);
        // Gen2
        private final Command kPlacePiece = new PlacePieceSimple(m_robotArm, CONE);
        private final Command kDriveForward2 = new DriveDistance(m_robotDrive, 2, 0.5);
        private final Command kTurnAround = new TurnToAngle(m_robotDrive, 180);
        private final Command kAutoDock = Commands.sequence(kPlacePiece, kTurnAround, kDriveForward2, kPIDDock);
        private final Command kNothing2 = Commands.sequence();
        private final SendableChooser<Command> m_chooser = new SendableChooser<>();

        // Replace with CommandPS4Controller or CommandJoystick if needed
        private final CommandXboxController m_driverController = new CommandXboxController(
                        OperatorConstants.kDriverControllerPort);

        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {
                // Configure the trigger bindings
                configureBindings();

                // Configure default commands
                // Set the default drive command to split-stick tank drive
                m_robotDrive.setDefaultCommand(
                                // A split-stick tank drive command, with the left stick controlling the speed
                                // of the left motor and right stick conrtolling the speed of the right motor.
                                new DefaultDrive(
                                                m_robotDrive,
                                                () -> -m_driverController.getLeftY(),
                                                () -> -m_driverController.getRightY(),
                                                () -> m_driverController.getRightTriggerAxis(),
                                                () -> -m_driverController.getLeftTriggerAxis(),
                                                0.75));

                // Set the default arm command
                m_robotArm.setDefaultCommand(
                                new DefaultArm(
                                                m_robotArm,
                                                m_driverController.x(),
                                                m_driverController.a(),
                                                m_driverController.b(),
                                                m_driverController.y()));

                // Add commands to the autonomous piece chooser
                m_chooser.setDefaultOption("nothing", kNothingAuto);
                m_chooser.addOption("normal", kNormalAuto);
                m_chooser.addOption("middle", kMiddleAuto);
                m_chooser.addOption("bump", kBumpAuto);
                m_chooser.addOption("dock only", kPIDDock);
                m_chooser.addOption("nothing2", kNothing2);
                m_chooser.addOption("place cone", kPlacePiece);
                m_chooser.addOption("auto dock", kAutoDock);

                m_chooser2.addOption("cube", CUBE);
                m_chooser2.addOption("cone", CONE);

                // Put the chooser on the dashboard
                Shuffleboard.getTab("Main").add("Auto Options", m_chooser);
                Shuffleboard.getTab("Main").add("Piece Options", m_chooser2);
        }

        /**
         * Use this method to define your trigger->command mappings. Triggers can be
         * created via the
         * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
         * an arbitrary
         * predicate, or via the named factories in {@link
         * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
         * {@link
         * CommandXboxController
         * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
         * PS4} controllers or
         * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
         * joysticks}.
         */
        private void configureBindings() {
                // While holding the right shoulder button, drive at 115% speed
                new Trigger(m_driverController.rightBumper())
                                .whileTrue(new DefaultDrive(
                                                m_robotDrive,
                                                () -> -m_driverController.getLeftY(),
                                                () -> -m_driverController.getRightY(),
                                                () -> m_driverController.getRightTriggerAxis(),
                                                () -> -m_driverController.getLeftTriggerAxis(),
                                                1.15));
                // While holding the left shoulder button, drive at 50% speed
                new Trigger(m_driverController.leftBumper())
                                .whileTrue(new DefaultDrive(
                                                m_robotDrive,
                                                () -> -m_driverController.getLeftY(),
                                                () -> -m_driverController.getRightY(),
                                                () -> m_driverController.getRightTriggerAxis(),
                                                () -> -m_driverController.getLeftTriggerAxis(),
                                                0.5));

                new Trigger(m_driverController.pov(90)).whileTrue(new Turn(
                                m_robotDrive, 90));

                new Trigger(m_driverController.pov(270)).whileTrue(new Turn(
                                m_robotDrive, 270));

                // new Trigger(m_driverController.rightStick())
                // .onTrue(new TurnToAngle(m_robotDrive, 180));

                // new Trigger(m_driverController.leftStick())
                // .onTrue(new TurnToAngle(m_robotDrive, 90));

                new Trigger(m_driverController.rightStick())
                                .onTrue(new VisionAlignment(m_robotVision, m_robotDrive, true));
        }

        /**
         * Use this to pass the autonomous command to the main {@link Robot} class.
         *
         * @return the command to run in autonomous
         */
        public Command getAutonomousCommand() {
                return m_chooser.getSelected();
        }
}