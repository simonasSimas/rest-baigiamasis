package lt.codeacademy.restbagiamasis.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.codeacademy.restbagiamasis.entity.question.Answer;
import lt.codeacademy.restbagiamasis.entity.question.Question;

@Data
@NoArgsConstructor
public class AnswerDTO {

    private Long id;

    private String answer;

    @JsonIgnore
    private Question question;

    public AnswerDTO (Answer answer){
        this.id=answer.getId();
        this.answer=answer.getAnswer();
        this.question=answer.getQuestion();
    }
}
