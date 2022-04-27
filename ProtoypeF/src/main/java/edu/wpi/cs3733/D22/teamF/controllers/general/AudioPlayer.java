package edu.wpi.cs3733.D22.teamF.controllers.general;

import edu.wpi.cs3733.D22.teamF.Fapp;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;

public class AudioPlayer {

  // static String filePath;
  // to store current position
  Long currentFrame;
  static Clip clip;
  // current status of clip
  String status;
  AudioInputStream audioInputStream;

  private static AudioPlayer m_AudioPlayer = null;

  /**
   * if instance is null creates
   *
   * @return the sole instance
   */
  public static AudioPlayer getInstance() {
    // separation for ease of control i.e initialization method
    if (m_AudioPlayer == null) m_AudioPlayer = new AudioPlayer();
    return m_AudioPlayer;
  }

  // constructor to initialize streams and clip
  public AudioPlayer() {}

  public void setAudioInputStream(String filePath) {
    try {
      InputStream in = Fapp.class.getResourceAsStream(filePath);
      BufferedInputStream bin = new BufferedInputStream(in);
      // System.out.println(in.getAbsolutePath());

      audioInputStream = AudioSystem.getAudioInputStream(bin);

      // create clip reference
      clip = AudioSystem.getClip();

      // open audioInputStream to the clip
      clip.open(audioInputStream);

    } catch (UnsupportedAudioFileException e) {
      e.printStackTrace();
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void play() {
    clip.start();
  }

  public void playFrom(long secs) {
    clip.setMicrosecondPosition(secs * 1000);
    clip.start();
  }

  public boolean playFor(int sec) {
    clip.start();
    while (clip.getMicrosecondPosition() <= (sec * 1000)) {}

    clip.stop();
    return true;
  }

  public void stop() {
    if (clip != null && clip.isRunning()) {
      clip.stop();
    }
  }

  public boolean getPlaying() {
    return clip.isRunning();
  }
}
