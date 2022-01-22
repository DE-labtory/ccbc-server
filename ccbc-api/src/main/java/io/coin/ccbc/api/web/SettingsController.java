package io.coin.ccbc.api.web;

import io.coin.ccbc.api.application.SettingApplicationService;
import io.coin.ccbc.api.application.dto.CreateQuestionDto;
import io.coin.ccbc.api.application.dto.CreateReleaseNoteDto;
import io.coin.ccbc.api.application.dto.LinkDto;
import io.coin.ccbc.api.application.dto.SettingContentsDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bomi
 * @date 2021/08/26
 */

@RestController
@RequestMapping("api/v1/operation/settings")
public class SettingsController {

  private final SettingApplicationService settingApplicationService;

  public SettingsController(
      final SettingApplicationService settingApplicationService
  ) {
    this.settingApplicationService = settingApplicationService;
  }

  @GetMapping("/open-chat")
  @ResponseStatus(value = HttpStatus.OK)
  public LinkDto getKakaoOpenChatLink() {
    return this.settingApplicationService.getKakaoOpenChatLink();
  }

  @GetMapping("/questions")
  @ResponseStatus(value = HttpStatus.OK)
  public List<SettingContentsDto> getAllQuestions() {
    return this.settingApplicationService.getAllQuestionsSortedByPriority();
  }

  @PostMapping("/questions")
  @ResponseStatus(value = HttpStatus.CREATED)
  public SettingContentsDto createNewQuestion(
      @RequestBody CreateQuestionDto createQuestionDto
  ) {
    return this.settingApplicationService.createQuestion(createQuestionDto);
  }

  @GetMapping("/release-notes")
  @ResponseStatus(value = HttpStatus.OK)
  public List<SettingContentsDto> getAllReleaseNotes() {
    return this.settingApplicationService.getAllReleaseNotesSortedByCreatedAtDescending();
  }

  @PostMapping("/release-notes")
  @ResponseStatus(value = HttpStatus.CREATED)
  public SettingContentsDto createNewReleaseNote(
      @RequestBody CreateReleaseNoteDto createReleaseNoteDto
  ) {
    return this.settingApplicationService.createReleaseNote(createReleaseNoteDto);
  }
}
