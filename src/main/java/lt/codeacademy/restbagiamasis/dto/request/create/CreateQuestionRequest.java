package lt.codeacademy.restbagiamasis.dto.request.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.codeacademy.restbagiamasis.entity.question.Answer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuestionRequest {

    private Long formId;

    @NotBlank
    private String questionContent;

    @NotEmpty
    private Set<Answer> answers;
}
