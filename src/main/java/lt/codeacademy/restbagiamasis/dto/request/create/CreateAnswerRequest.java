package lt.codeacademy.restbagiamasis.dto.request.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAnswerRequest {

    private Long questionId;

    @NotBlank
    private String answer;
}
