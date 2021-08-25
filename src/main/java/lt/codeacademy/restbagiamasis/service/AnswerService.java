package lt.codeacademy.restbagiamasis.service;

import lt.codeacademy.restbagiamasis.entity.question.Answer;
import lt.codeacademy.restbagiamasis.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    public final AnswerRepository answerRepository;

    public final QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    public List<Answer> getAllAnswers(){
        return answerRepository.findAll();
    }

    public Answer getAnswer(Long id){
        return answerRepository.getById(id);
    }

    public List<Answer> getAllByQuestionId(Long id){
        return answerRepository.getAllByQuestionId(id);
    }

    public Answer createAnswer(Answer answer, Long questionId) {
        answer.setQuestion(questionId != null ? questionService.getQuestion(questionId) : null);
        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Answer updatedAnswer, Long questionId) {

        updatedAnswer.setQuestion(questionId != null ? questionService.getQuestion(questionId) : null);

        return answerRepository.save(updatedAnswer);
    }

    public void deleteAnswer(Long id){
        answerRepository.deleteById(getAnswer(id).getId());
    }
}
