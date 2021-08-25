package lt.codeacademy.restbagiamasis.service;

import lt.codeacademy.restbagiamasis.entity.question.Answer;
import lt.codeacademy.restbagiamasis.entity.question.Question;
import lt.codeacademy.restbagiamasis.repository.AnswerRepository;
import lt.codeacademy.restbagiamasis.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    public final QuestionRepository questionRepository;

    public final AnswerRepository answerRepository;

    public final FormService formService;

    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository, FormService formService) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.formService = formService;
    }

    public List<Question> getAllQuestions(){
        return questionRepository.findAll();
    }

    public Question getQuestion(Long id){
        return questionRepository.getById(id);
    }

    public List<Question> getAllByFormId(Long id){
        return questionRepository.getAllByFormId(id);
    }

    public Question createQuestion(Question question, Long formId) {
        question.setForm(formId != null ? formService.getForm(formId) : null);
        return questionRepository.save(question);
    }

    public Question updateQuestion(Question updateQuestion, Long formId) {

        updateQuestion.setForm(formId != null ? formService.getForm(formId) : null);

        return questionRepository.save(updateQuestion);
    }

    public void deleteQuestion(Long id){
        questionRepository.deleteById(getQuestion(id).getId());
        answerRepository.deleteAll(answerRepository.getAllByQuestionId(id));
    }
}
