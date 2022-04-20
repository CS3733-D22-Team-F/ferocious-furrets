package edu.wpi.cs3733.D22.teamF.observers;

public class Time {
  private int hour;
  private int minute;
  private int second;

  public Time(int hour, int minute, int second) {
    this.second = second;
    this.hour = hour;
    this.minute = minute;
  }

  public String getCurrentTime() {
    return hour + ":" + minute + ":" + second;
  }

  public Time(String currentTime) {
    String[] time = currentTime.split(":");
    hour = Integer.parseInt(time[0]);
    minute = Integer.parseInt(time[1]);
    second = Integer.parseInt(time[2]);
  }

  public void onSecondPass() {
    second++;
    if (second == 60) {
      minute++;
      second = 0;
      if (minute == 60) {
        minute = 0;
        hour++;
        if (hour == 24) {
          hour = 0;
        }
      }
    }
  }
}
