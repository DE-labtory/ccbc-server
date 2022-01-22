package io.coin.ccbc.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bomi
 * @date 2021/08/26
 */

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {

  List<Question> findAllByOrderByPriority();

  List<Question> findAllByPriority(int priority);
}
