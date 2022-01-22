package io.coin.ccbc.support.alerter;


public interface AlerterFactory {

  Alerter create(String channel);

  Alerter create(String channel, boolean mute);
}
