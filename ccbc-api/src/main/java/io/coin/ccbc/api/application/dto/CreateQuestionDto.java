package io.coin.ccbc.api.application.dto;

import lombok.Getter;

/**
 * @author Bomi
 * @date 2021/08/28
 */

@Getter
public class CreateQuestionDto {

  private String title;
  private String contents;
  private int priority;
}
