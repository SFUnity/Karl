// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.AnalogInput;

public class DriveSubsystem extends SubsystemBase {
  // The motors on the left side of the drive.
  private final MotorControllerGroup m_leftMotors = new MotorControllerGroup(
      new CANSparkMax(1, MotorType.kBrushed),
      new CANSparkMax(4, MotorType.kBrushed));

  // The motors on the right side of the drive.
  private final MotorControllerGroup m_rightMotors = new MotorControllerGroup(
      new CANSparkMax(2, MotorType.kBrushed),
      new CANSparkMax(3, MotorType.kBrushed));

  // The robot's drive
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);

  // The left-side drive encoder
  private final Encoder m_leftEncoder = new Encoder(
      DriveConstants.kLeftEncoderPorts[0],
      DriveConstants.kLeftEncoderPorts[1],
      DriveConstants.kLeftEncoderReversed);

  // The right-side drive encoder
  private final Encoder m_rightEncoder = new Encoder(
      DriveConstants.kRightEncoderPorts[0],
      DriveConstants.kRightEncoderPorts[1],
      DriveConstants.kRightEncoderReversed);

  // ADIS16470 plugged into the MXP port
  private final ADIS16470_IMU gyro = new ADIS16470_IMU();

  // Ultrasonic sensor
  private final AnalogInput ultrasonic = new AnalogInput(0);

  // Creating my odometry object. Here,
  // our starting pose is 5 meters along the long end of the field and in the
  // center of the field along the short end, facing forward.
  DifferentialDriveOdometry m_odometry = new DifferentialDriveOdometry(
      Rotation2d.fromRadians(gyro.getAngle()),
      m_leftEncoder.getDistance(), m_rightEncoder.getDistance(),
      new Pose2d(5.0, 13.5, new Rotation2d()));
  
  private Pose2d m_pose;

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_leftMotors.setInverted(true);

    // Sets the distance per pulse for the encoders
    m_leftEncoder.setDistancePerPulse(DriveConstants.kEncoderDistancePerPulse);
    m_rightEncoder.setDistancePerPulse(DriveConstants.kEncoderDistancePerPulse);

    // Places a compass indicator for the gyro heading on the dashboard
    Shuffleboard.getTab("Heading").add(gyro);
  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param left  the commanded left motor movement
   * @param right the commanded right motor movement
   */
  public void tankDrive(double left, double right) {
    m_drive.tankDrive(left * DriveConstants.kSpeed,
        right * DriveConstants.kSpeed);
  }

  /** Resets the drive encoders to currently read a position of 0. */
  public void resetEncoders() {
    m_leftEncoder.reset();
    m_rightEncoder.reset();
  }

  public double getDistance() {
    double rawValue = ultrasonic.getValue();
    double voltage_scale_factor = 5 / RobotController.getVoltage5V();
    double currentDistanceInches = rawValue * voltage_scale_factor * 0.0492;
    return currentDistanceInches;
  }

  /**
   * Gets the average distance of the TWO encoders.
   *
   * @return the average of the TWO encoder readings
   */
  public double getAverageEncoderDistance() {
    return (m_leftEncoder.getDistance() + m_rightEncoder.getDistance()) / 2.0;
  }

  /**
   * Gets the left drive encoder.
   *
   * @return the left drive encoder
   */
  public Encoder getLeftEncoder() {
    return m_leftEncoder;
  }

  /**
   * Gets the right drive encoder.
   *
   * @return the right drive encoder
   */
  public Encoder getRightEncoder() {
    return m_rightEncoder;
  }

  public double getHeading() {
    return gyro.getAngle();
  }

  public Pose2d getPose() {
    return m_pose;
  }

  /**
   * Sets the max output of the drive. Useful for scaling the drive to drive more
   * slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Get the rotation of the robot from the gyro.
    var gyroAngle = Rotation2d.fromRadians(gyro.getAngle());

    // Update the pose
    m_pose = m_odometry.update(gyroAngle,
        m_leftEncoder.getDistance(),
        m_rightEncoder.getDistance());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
