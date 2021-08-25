package lt.codeacademy.restbagiamasis.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lt.codeacademy.restbagiamasis.dto.QuestionDTO;
import lt.codeacademy.restbagiamasis.dto.request.create.CreateQuestionRequest;
import lt.codeacademy.restbagiamasis.dto.request.update.UpdateQuestionRequest;
import lt.codeacademy.restbagiamasis.dto.response.create.CreateQuestionResponse;
import lt.codeacademy.restbagiamasis.dto.response.update.UpdateQuestionResponse;
import lt.codeacademy.restbagiamasis.entity.Form;
import lt.codeacademy.restbagiamasis.entity.User;
import lt.codeacademy.restbagiamasis.entity.question.Question;
import lt.codeacademy.restbagiamasis.service.FormService;
import lt.codeacademy.restbagiamasis.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @ApiOperation(value = "Get all questions", tags = "getQuestions", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully got questions"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<QuestionDTO> getAllQuestions(@AuthenticationPrincipal Question question) {
        return questionService.getAllQuestions().stream().map(QuestionDTO::new).collect(toList());
    }

    @ApiOperation(value = "Get question by id", tags = "getQuestion", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully got question"),
            @ApiResponse(code = 404, message = "Question does not exist"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || (question.id==#id && question.form.creator.id==principal.id)")
    public QuestionDTO getQuestion(@PathVariable("id") Long id, @AuthenticationPrincipal Question question) {
        return new QuestionDTO(questionService.getQuestion(id));
    }

    @ApiOperation(value = "Get forms questions", tags = "getFormsQuestions", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully got questions"),
            @ApiResponse(code = 404, message = "Form does not exist"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/{id}/form")
    @PreAuthorize("hasRole('ADMIN') || (form.id==#id && question.form.creator.id==principal.id)")
    public Set<QuestionDTO> getFormsQuestions(@PathVariable("id") Long id, @AuthenticationPrincipal Form form) {
        return questionService.getAllByFormId(id).stream().map(QuestionDTO::new).collect(toSet());
    }

    @ApiOperation(value = "Create question", tags = "createQuestion", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created question"),
            @ApiResponse(code = 400, message = "Validation failed"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PostMapping("/{formId}/create")
    public CreateQuestionResponse createQuestion(@PathVariable("formId") Long formId, @Valid @RequestBody CreateQuestionRequest createQuestionRequest) {
        return new CreateQuestionResponse(questionService.createQuestion(
                new Question(createQuestionRequest), formId));
    }

    @ApiOperation(value = "Update question", tags = "updateQuestion", httpMethod = "PUT")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated car record"),
            @ApiResponse(code = 400, message = "Validation failed"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PutMapping("/{questionId}/update/{formId}")
    @PreAuthorize("hasRole('ADMIN') || (question.id==#id && question.form.creator.id==principal.id)")
    public UpdateQuestionResponse updateQuestion(@PathVariable("formId") Long formId, @PathVariable("questionId") Long questionId,@Valid @RequestBody UpdateQuestionRequest updateQuestionRequest) {
        return new UpdateQuestionResponse(questionService.updateQuestion(
                new Question(questionId, updateQuestionRequest), formId));
    }

    @ApiOperation(value = "Delete question by id", tags = "deleteQuestion", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted question by id"),
            @ApiResponse(code = 404, message = "Question not found error"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') || (question.id==#id && question.form.creator.id==principal.id)")
    public void deleteQuestion(@PathVariable("id") Long id, @AuthenticationPrincipal Question question) {
        questionService.deleteQuestion(id);
    }
}
