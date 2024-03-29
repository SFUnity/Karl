// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  // Defines the various motor ports
  public static final class DriveConstants {
    public static final int kLeftMotor1Port = 0;
    public static final int kLeftMotor2Port = 1;
    public static final int kRightMotor1Port = 2;
    public static final int kRightMotor2Port = 3;

    public static double KControllerSensitivity = 0.75;

    public static final int[] kLeftEncoderPorts = new int[] { 0, 1 };
    public static final int[] kRightEncoderPorts = new int[] { 2, 3 };
    public static final boolean kLeftEncoderReversed = false;
    public static final boolean kRightEncoderReversed = true;

    public static double kSpeed = 0.75;

    public static final int kEncoderCPR = 1024;
    public static final double kWheelDiameterInches = 6;
    public static final double kEncoderDistancePerPulse =
        // Assumes the encoders are directly mounted on the wheel shafts
        (kWheelDiameterInches * Math.PI) / (double) kEncoderCPR;
  }
  
  public static final class ArmConstants {
    // How many amps the arm motor can use.
    public static final int ARM_CURRENT_LIMIT_A = 20;

    // Percent output to run the arm up/down at
    public static final double ARM_OUTPUT_POWER = 0.5;

    // How many amps the intake can use while picking up
    public static final int INTAKE_CURRENT_LIMIT_A = 25;

    // How many amps the intake can use while holding
    public static final int INTAKE_HOLD_CURRENT_LIMIT_A = 5;

    // Percent output for intaking
    public static final double INTAKE_OUTPUT_POWER = 0.75;

    // Percent output for holding
    public static final double INTAKE_HOLD_POWER = 0.07;

    // Time to extend or retract arm in auto
    public static final double ARM_EXTEND_TIME_S = 2.0;

    // Time to throw game piece in auto
    public static final double AUTO_THROW_TIME_S = 0.5;

    // Time to drive back in auto
    public static double AUTO_DRIVE_TIME = 4.0;

    // Speed to drive backwards in auto
    public static double AUTO_DRIVE_SPEED = -0.5;

    /**
     * Used to remember the last game piece picked up to apply some holding power.
     */
    public static final int CONE = 1;
    public static final int CUBE = 2;
    public static final int NOTHING = 3;
    public static int lastGamePiece;

    public static double intakePower;
    public static int intakeAmps;
  }

  public static final class AutoConstants {
    public static final double kAutoDriveDistanceFeet = 10;
  }
  
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
}