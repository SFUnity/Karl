// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.DefaultDrive;
import frc.robot.commands.PIDDock;
import frc.robot.commands.Auto;
import frc.robot.commands.CopySignDock;
import frc.robot.commands.BalanceBangBangCommand;
import frc.robot.commands.AccelDock;
import frc.robot.commands.DefaultArm;
import frc.robot.commands.Turn;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
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

  // Auto piece chooser
  private final Command kNormalAuto = new Auto(m_robotArm, m_robotDrive, 1);
  private final Command kMiddleAuto = new Auto(m_robotArm, m_robotDrive, 0);
  private final Command kBumpAuto = new Auto(m_robotArm, m_robotDrive, 2);
  private final Command kAccelDock = new AccelDock(m_robotDrive, false);
  private final Command kPIDDock = new PIDDock(m_robotDrive, 0);
  private final Command kCopySignDock = new CopySignDock(m_robotDrive);
  private final Command kBangBangBalance = new BalanceBangBangCommand(m_robotDrive);
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
    m_chooser.setDefaultOption("normal", kNormalAuto);
    m_chooser.addOption("middle", kMiddleAuto);
    m_chooser.addOption("bump", kBumpAuto);
    m_chooser.addOption("balance forwards", kAccelDock);
    m_chooser.addOption("knight dock", kPIDDock);
    m_chooser.addOption("Copysign dock", kCopySignDock);
    m_chooser.addOption("BangBang Balance", kBangBangBalance);

    // Put the chooser on the dashboard
    Shuffleboard.getTab("Main").add("Auto Options", m_chooser);
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
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    System.out.print("getAuto ran");
    return m_chooser.getSelected();
  }
}