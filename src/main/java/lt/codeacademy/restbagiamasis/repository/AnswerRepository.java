package lt.codeacademy.restbagiamasis.repository;

import lt.codeacademy.restbagiamasis.entity.question.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> getAllByQuestionId(Long id);
}
