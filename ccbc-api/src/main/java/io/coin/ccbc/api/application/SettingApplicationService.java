package io.coin.ccbc.api.application;

import io.coin.ccbc.api.application.dto.CreateQuestionDto;
import io.coin.ccbc.api.application.dto.CreateReleaseNoteDto;
import io.coin.ccbc.api.application.dto.LinkDto;
import io.coin.ccbc.api.application.dto.SettingContentsDto;
import io.coin.ccbc.domain.Question;
import io.coin.ccbc.domain.QuestionRepository;
import io.coin.ccbc.domain.ReleaseNote;
import io.coin.ccbc.domain.ReleaseNoteRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Bomi
 * @date 2021/08/26
 */

@Service
public class SettingApplicationService {

  private final QuestionRepository questionRepository;
  private final ReleaseNoteRepository releaseNoteRepository;
  @Value("${app.kakao.open-chat-url}")
  private String openChatLink;

  public SettingApplicationService(
      final QuestionRepository questionRepository,
      final ReleaseNoteRepository releaseNoteRepository
  ) {
    this.questionRepository = questionRepository;
    this.releaseNoteRepository = releaseNoteRepository;
  }

  public LinkDto getKakaoOpenChatLink() {
    return LinkDto.from(openChatLink);
  }

  @Transactional(readOnly = true)
  public List<SettingContentsDto> getAllQuestionsSortedByPriority() {
    return this.questionRepository.findAllByOrderByPriority()
        .stream()
        .map(SettingContentsDto::from)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<SettingContentsDto> getAllReleaseNotesSortedByCreatedAtDescending() {
    return this.releaseNoteRepository.findAllByOrderByCreatedAtDesc()
        .stream()
        .map(SettingContentsDto::from)
        .collect(Collectors.toList());
  }

  @Transactional
  public SettingContentsDto createQuestion(CreateQuestionDto createQuestionDto) {
    return SettingContentsDto.from(
        this.questionRepository.save(
            Question.of(
                createQuestionDto.getTitle(),
                createQuestionDto.getContents(),
                createQuestionDto.getPriority())
        )
    );
  }

  @Transactional
  public SettingContentsDto createReleaseNote(CreateReleaseNoteDto createReleaseNoteDto) {
    return SettingContentsDto.from(
        this.releaseNoteRepository.save(
            ReleaseNote.of(
                createReleaseNoteDto.getTitle(),
                createReleaseNoteDto.getContents())
        )
    );
  }

}
