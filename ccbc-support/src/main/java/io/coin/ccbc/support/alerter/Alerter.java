package io.coin.ccbc.support.alerter;


public interface Alerter {

  void alert(String title, String message, String color);

  void mute();

  void unMute();
}