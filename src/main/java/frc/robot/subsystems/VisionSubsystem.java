// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {
  PhotonCamera camera = new PhotonCamera("Arducam_OV9281_USB_Camera");
  public VisionSubsystem() {
  }

    public PhotonPipelineResult getLastResult() {
        var result = camera.getLatestResult();
        return result;
    }

    public boolean booltargets(PhotonPipelineResult result) {
        return result.hasTargets();
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

