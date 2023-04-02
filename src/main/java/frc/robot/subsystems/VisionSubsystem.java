package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;

public class VisionSubsystem {
    private final PhotonCamera camera = new PhotonCamera("foggyFocus");
    private final double CAMERA_HEIGHT_METERS = 0;
    private final double TARGET_HEIGHT_METERS = 0;
    private final double CAMERA_PITCH_RADIANS = 0;

    public VisionSubsystem() {

    }
}