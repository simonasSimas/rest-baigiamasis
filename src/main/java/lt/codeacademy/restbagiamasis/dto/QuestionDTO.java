package lt.codeacademy.restbagiamasis.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.codeacademy.restbagiamasis.entity.Form;
import lt.codeacademy.restbagiamasis.entity.question.Answer;
import lt.codeacademy.restbagiamasis.entity.question.Question;

import java.util.Set;

@Data
@NoArgsConstructor
public class QuestionDTO {

    private Long id;

    private String questionContent;

    @JsonIgnore
    private Form form;

    private Set<Answer> answers;

    public QuestionDTO (Question question){
        this.id=question.getId();
        this.questionContent=question.getQuestionContent();
        this.form=question.getForm();
        this.answers=question.getAnswers();
    }
}
