package edu.wpi.cs3733.D22.teamF.controllers.general;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class AudioPlayer {

  // static String filePath;
  // to store current position
  Long currentFrame;
  Clip clip;
  // current status of clip
  String status;
  AudioInputStream audioInputStream;

  // constructor to initialize streams and clip
  public AudioPlayer(String filePath)
      throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    // create AudioInputStream object
    audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

    // create clip reference
    clip = AudioSystem.getClip();

    // open audioInputStream to the clip
    clip.open(audioInputStream);

    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  public void play() {
    clip.start();
  }

  public boolean playFor(int sec) {
    clip.start();
    while (clip.getMicrosecondPosition() <= (sec * 1000)) {}

    clip.stop();
    return true;
  }

  public void stop() {
    clip.stop();
  }
}
