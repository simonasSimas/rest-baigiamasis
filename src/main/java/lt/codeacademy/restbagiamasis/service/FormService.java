package lt.codeacademy.restbagiamasis.service;

import lt.codeacademy.restbagiamasis.entity.Form;
import lt.codeacademy.restbagiamasis.entity.User;
import lt.codeacademy.restbagiamasis.entity.question.Question;
import lt.codeacademy.restbagiamasis.exception.ResourceNotFoundException;
import lt.codeacademy.restbagiamasis.repository.AnswerRepository;
import lt.codeacademy.restbagiamasis.repository.FormRepository;
import lt.codeacademy.restbagiamasis.repository.QuestionRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormService {

    private final FormRepository formRepository;

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    private final UserService userService;

    public FormService(FormRepository formRepository, QuestionRepository questionRepository,
                       AnswerRepository answerRepository, UserService userService) {
        this.formRepository = formRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.userService = userService;
    }

    public List<Form> getAllForms(){
        return formRepository.findAll();
    }

    public List<Form> getAllFormsByCreatorId(Long id){
        return formRepository.getAllByCreatorId(id);
    }

    public Form createForm(Form form, Long creatorId) {
        form.setCreator(creatorId != null ? userService.getUser(creatorId) : null);
        return formRepository.save(form);
    }

    public Form updateForm(Form updatedForm, Long creatorId) {
        Form form = getForm(updatedForm.getId());

        updatedForm.setCreator(creatorId != null ? userService.getUser(creatorId) : null);
        updatedForm.setCreated(form.getCreated());

        return formRepository.save(updatedForm);
    }

    public Form getForm(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Form form = formRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        if (user.isAdmin() || (form.getCreator().getId() != null && form.getCreator().getId().equals(user.getId()))) {
            return form;
        }

        throw new AccessDeniedException("Access denied: user.id = " + user.getId() + ", form.id = " + form.getId());
    }

    public void deleteForm(Long id) {
        formRepository.deleteById(getForm(id).getId());
        for(Question question : questionRepository.getAllByFormId(id)) {
            answerRepository.deleteAll(answerRepository.getAllByQuestionId(question.getId()));
            questionRepository.delete(question);
        }
    }
}
