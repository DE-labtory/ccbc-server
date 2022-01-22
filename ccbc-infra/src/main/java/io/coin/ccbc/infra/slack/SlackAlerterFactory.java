package io.coin.ccbc.infra.slack;


import com.slack.api.Slack;
import io.coin.ccbc.support.alerter.AlerterFactory;


public class SlackAlerterFactory implements AlerterFactory {

  private final Slack slack;
  private final boolean defaultMute;
  private final String token;

  public SlackAlerterFactory(Slack slack, String token, boolean defaultMute) {
    this.slack = slack;
    this.token = token;
    this.defaultMute = defaultMute;
  }

  public SlackAlerter create(String channel) {
    return this.create(channel, this.defaultMute);
  }

  public SlackAlerter create(String channel, boolean mute) {
    SlackAlerter slackAlerter = new SlackAlerter(this.slack, this.token, channel);
    if (mute) {
      slackAlerter.mute();
    }
    return slackAlerter;
  }
}
