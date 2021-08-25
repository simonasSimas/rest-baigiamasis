package lt.codeacademy.restbagiamasis.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lt.codeacademy.restbagiamasis.dto.request.create.CreateFormRequest;
import lt.codeacademy.restbagiamasis.dto.request.update.UpdateFormRequest;
import lt.codeacademy.restbagiamasis.entity.question.Question;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "form")
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String formTitle;

    @Column(name = "description")
    private String formDescription;

    @Column(name = "question_amount")
    private Integer numberOfQuestions;

    @Column(name = "score")
    private Integer maximumScore;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "form", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    private Set<Question> questions;

    @CreationTimestamp
    @Column(name = "created")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;

    public Form() {
    }

    public Form(Long id, UpdateFormRequest updateFormRequest){
        this.id=id;
        this.formTitle=updateFormRequest.getFormTitle();
        this.formDescription=updateFormRequest.getFormDescription();
        this.numberOfQuestions= updateFormRequest.getNumberOfQuestions();
        this.questions=updateFormRequest.getQuestions();
        this.maximumScore= updateFormRequest.getNumberOfQuestions();
    }

    public Form(String formTitle, String formDescription, Integer numberOfQuestions, Integer maximumScore, User creator, Set<Question> questions) {
        this.formTitle = formTitle;
        this.formDescription = formDescription;
        this.numberOfQuestions = numberOfQuestions;
        this.maximumScore = maximumScore;
        this.creator = creator;
        this.questions = questions;
    }

    public Form(CreateFormRequest createFormRequest) {
        this.formTitle = createFormRequest.getFormTitle();
        this.formDescription = createFormRequest.getFormDescription();
        this.numberOfQuestions = createFormRequest.getNumberOfQuestions();
        this.questions = createFormRequest.getQuestions();
        this.maximumScore = createFormRequest.getMaximumScore();
    }
//
//    public String getCreated() {
//        return created.toString();
//    }
//
//    public String getUpdated() {
//        return updated.toString();
//    }

}
