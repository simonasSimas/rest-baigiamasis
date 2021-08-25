package lt.codeacademy.restbagiamasis.dto.response.create;

import lombok.Data;
import lombok.NoArgsConstructor;
import lt.codeacademy.restbagiamasis.entity.question.Answer;
import lt.codeacademy.restbagiamasis.entity.question.Question;

import java.util.Set;

@Data
@NoArgsConstructor
public class CreateQuestionResponse {

    private Long id;

    private Long formId;

    private String questionContent;

    private Set<Answer> answers;

    public CreateQuestionResponse(Question question){
        this.id= question.getId();
        this.formId= question.getForm().getId();
        this.questionContent = question.getQuestionContent();
        this.answers=question.getAnswers();
    }
}
