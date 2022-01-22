package io.coin.ccbc.api.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.coin.ccbc.domain.Question;
import io.coin.ccbc.domain.ReleaseNote;
import io.coin.ccbc.support.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bomi
 * @date 2021/08/26
 */

@Getter
@AllArgsConstructor
public class SettingContentsDto {

  private final String id;

  private final String title;

  private final String contents;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private final LocalDateTime createdAt;

  public static SettingContentsDto from(Question question) {
    return new SettingContentsDto(
        question.getId(),
        question.getTitle(),
        question.getContents(),
        question.getCreatedAt()
    );
  }

  public static SettingContentsDto from(ReleaseNote releaseNote) {
    return new SettingContentsDto(
        releaseNote.getId(),
        releaseNote.getTitle(),
        releaseNote.getContents(),
        releaseNote.getCreatedAt()
    );
  }
}
