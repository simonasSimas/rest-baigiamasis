package lt.codeacademy.restbagiamasis.entity.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.codeacademy.restbagiamasis.dto.request.create.CreateAnswerRequest;
import lt.codeacademy.restbagiamasis.dto.request.update.UpdateAnswerRequest;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer")
    private String answer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Answer (Long id, UpdateAnswerRequest updateAnswerRequest){
        this.id=id;
        this.answer= updateAnswerRequest.getAnswer();
    }

    public Answer(CreateAnswerRequest createAnswerRequest){
        this.answer= createAnswerRequest.getAnswer();
    }
}
