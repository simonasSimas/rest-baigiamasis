package lt.codeacademy.restbagiamasis.dto.response.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.codeacademy.restbagiamasis.entity.Form;
import lt.codeacademy.restbagiamasis.entity.question.Question;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
public class UpdateFormResponse {

    private Long id;

    private Long creatorId;

    private String formTitle;

    private String formDescription;

    private Integer numberOfQuestions;

    private Integer maximumScore;

    private Set<Question> questions;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;

    public UpdateFormResponse(Form form) {
        this.id = form.getId();
        this.creatorId = form.getCreator().getId();
        this.formTitle = form.getFormTitle();
        this.formDescription = form.getFormDescription();
        this.numberOfQuestions = form.getNumberOfQuestions();
        this.maximumScore = form.getMaximumScore();
        this.questions = form.getQuestions();
        this.created = form.getCreated();
        this.updated = form.getUpdated();
    }
}
