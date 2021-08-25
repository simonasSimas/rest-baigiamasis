package lt.codeacademy.restbagiamasis.dto.response.create;

import lombok.Data;
import lombok.NoArgsConstructor;
import lt.codeacademy.restbagiamasis.entity.question.Answer;

@Data
@NoArgsConstructor
public class CreateAnswerResponse {

    private Long id;

    private Long questionId;

    private String answer;

    public CreateAnswerResponse(Answer answer){
        this.id=answer.getId();
        this.questionId=answer.getQuestion().getId();
        this.answer=answer.getAnswer();
    }
}
