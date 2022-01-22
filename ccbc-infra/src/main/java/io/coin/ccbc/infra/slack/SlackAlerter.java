package io.coin.ccbc.infra.slack;


import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Attachment;
import io.coin.ccbc.support.alerter.Alerter;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlackAlerter implements Alerter {

  private final Slack client;
  private final String channel;
  private final String token;
  private boolean isMuted;

  public SlackAlerter(
      Slack client,
      String token,
      String channel
  ) {
    this.client = client;
    this.token = token;
    this.channel = channel;
    this.isMuted = false;
  }

  @Override
  public void alert(String title, String message, String color) {
    if (this.isMuted) {
      return;
    }

    try {
      ChatPostMessageResponse response = this.client.methods(this.token).chatPostMessage(
          ChatPostMessageRequest.builder()
              .channel(this.channel)
              .token(this.token)
              .attachments(List.of(
                  Attachment.builder()
                      .title(title)
                      .text(message)
                      .color(color)
                      .build()
              ))
              .build()
      );
      if (!response.isOk()) {
        log.error("sending slack is not ok : " + response.getError());
      }
    } catch (IOException | SlackApiException e) {
      log.error("error while sending slack message : " + e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  public void mute() {
    this.isMuted = true;
  }

  @Override
  public void unMute() {
    this.isMuted = false;
  }

}
