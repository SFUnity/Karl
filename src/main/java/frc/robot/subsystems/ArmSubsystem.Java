// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmSubsystem extends SubsystemBase {
  // The motors for the arm and the intake
    CANSparkMax arm = new CANSparkMax(5, MotorType.kBrushless);
    CANSparkMax intake = new CANSparkMax(6, MotorType.kBrushless);

  // Magic numbers. Use these to adjust settings.

  // How many amps the arm motor can use.
  static final int ARM_CURRENT_LIMIT_A = 20;

  // Percent output to run the arm up/down at
  static final double ARM_OUTPUT_POWER = 0.4;

  // How many amps the intake can use while picking up
  static final int INTAKE_CURRENT_LIMIT_A = 25;

  // How many amps the intake can use while holding
  static final int INTAKE_HOLD_CURRENT_LIMIT_A = 5;

  // Percent output for intaking
  static final double INTAKE_OUTPUT_POWER = 1.0;

  // Percent output for holding
  static final double INTAKE_HOLD_POWER = 0.07;

  // Time to extend or retract arm in auto
  static final double ARM_EXTEND_TIME_S = 2.0;

  // Time to throw game piece in auto
  static final double AUTO_THROW_TIME_S = 0.375;

  // Time to drive back in auto
  static final double AUTO_DRIVE_TIME = 6.0;

  // Speed to drive backwards in auto
  static final double AUTO_DRIVE_SPEED = -0.25;

  // Creates a new ArmSubsystem
  public ArmSubsystem() {
    /*
     * Set the arm and intake to brake mode to help hold position.
     * If either one is reversed, change that here too. Arm out is defined
     * as positive, arm in is negative.
     */
    arm.setInverted(true);
    arm.setIdleMode(IdleMode.kBrake);
    arm.setSmartCurrentLimit(ARM_CURRENT_LIMIT_A);
    intake.setInverted(false);
    intake.setIdleMode(IdleMode.kBrake);
  }

  /**
   * Set the arm output power. Positive is out, negative is in.
   * 
   * @param percent
   */
  public void setArmMotor(double percent) {
    arm.set(percent);
    SmartDashboard.putNumber("arm power (%)", percent);
    SmartDashboard.putNumber("arm motor current (amps)", arm.getOutputCurrent());
    SmartDashboard.putNumber("arm motor temperature (C)", arm.getMotorTemperature());
  }

  /**
   * Set the arm output power.
   * 
   * @param percent desired speed
   * @param amps current limit
   */
  public void setIntakeMotor(double percent, int amps) {
    intake.set(percent);
    intake.setSmartCurrentLimit(amps);
    SmartDashboard.putNumber("intake power (%)", percent);
    SmartDashboard.putNumber("intake motor current (amps)", intake.getOutputCurrent());
    SmartDashboard.putNumber("intake motor temperature (C)", intake.getMotorTemperature());
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}