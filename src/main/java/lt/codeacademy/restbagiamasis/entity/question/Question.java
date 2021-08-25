package lt.codeacademy.restbagiamasis.entity.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.codeacademy.restbagiamasis.dto.request.create.CreateQuestionRequest;
import lt.codeacademy.restbagiamasis.dto.request.update.UpdateQuestionRequest;
import lt.codeacademy.restbagiamasis.entity.Form;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question")
    private String questionContent;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    private Set<Answer> answers;

    public Question (Long id, UpdateQuestionRequest updateQuestionRequest){
        this.id=id;
        this.questionContent= updateQuestionRequest.getQuestionContent();
        this.answers=updateQuestionRequest.getAnswers();
    }

    public Question (CreateQuestionRequest createQuestionRequest){
        this.questionContent=createQuestionRequest.getQuestionContent();
    }
}
