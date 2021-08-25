package lt.codeacademy.restbagiamasis.dto.response.update;

import lombok.Data;
import lombok.NoArgsConstructor;
import lt.codeacademy.restbagiamasis.entity.question.Answer;

@Data
@NoArgsConstructor
public class UpdateAnswerResponse {

    public Long id;

    public Long questionId;

    public String answer;

    public UpdateAnswerResponse(Answer answer){
        this.id=answer.getId();
        this.questionId=answer.getQuestion().getId();
        this.answer=answer.getAnswer();
    }
}
