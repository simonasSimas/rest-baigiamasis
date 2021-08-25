package lt.codeacademy.restbagiamasis.dto.request.create;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.codeacademy.restbagiamasis.entity.question.Question;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFormRequest {

    private Long creatorId;

    @NotBlank
    private String formTitle;

    @NotBlank
    private String formDescription;

    @NotNull
    private Integer numberOfQuestions;

    @NotEmpty
    private Set<Question> questions;

    @NotNull
    private Integer maximumScore;
}
