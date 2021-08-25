package lt.codeacademy.restbagiamasis.dto.request.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAnswerRequest {

    private Long questionId;

    @NotEmpty
    private String answer;
}
