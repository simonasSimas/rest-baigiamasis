package lt.codeacademy.restbagiamasis.repository;

import lt.codeacademy.restbagiamasis.entity.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> getAllByFormId(Long id);
}
