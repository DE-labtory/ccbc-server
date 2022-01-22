package io.coin.ccbc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Bomi
 * @date 2021/08/26
 */

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Entity
@Table(name = "release_notes")
public class ReleaseNote extends DomainEntity {

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "contents", nullable = false, columnDefinition = "TEXT")
  private String contents;

  public static ReleaseNote of(String title, String contents) {
    return new ReleaseNote(title, contents);
  }
}
