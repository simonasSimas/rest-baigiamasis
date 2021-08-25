package lt.codeacademy.restbagiamasis.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lt.codeacademy.restbagiamasis.dto.AnswerDTO;
import lt.codeacademy.restbagiamasis.dto.request.create.CreateAnswerRequest;
import lt.codeacademy.restbagiamasis.dto.request.update.UpdateAnswerRequest;
import lt.codeacademy.restbagiamasis.dto.response.create.CreateAnswerResponse;
import lt.codeacademy.restbagiamasis.dto.response.update.UpdateAnswerResponse;
import lt.codeacademy.restbagiamasis.entity.User;
import lt.codeacademy.restbagiamasis.entity.question.Answer;
import lt.codeacademy.restbagiamasis.entity.question.Question;
import lt.codeacademy.restbagiamasis.service.AnswerService;
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
@RequestMapping("/answer")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @ApiOperation(value = "Get all answers", tags = "getAnswers", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got list of answers")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') || principal.id==answer.question.form.creator.id")
    public List<AnswerDTO> getAllAnswers(@AuthenticationPrincipal Answer answer) {
        return answerService.getAllAnswers().stream().map(AnswerDTO::new).collect(toList());
    }

    @ApiOperation(value = "Get answer by id", tags = "getAnswer", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got answer by id"),
            @ApiResponse(code = 404, message = "Answer not found error"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || (principal.id==answer.question.form.creator.id && answer.id==#id)")
    public AnswerDTO getAnswer(@PathVariable("id") Long id, @AuthenticationPrincipal Answer answer) {
        return new AnswerDTO(answerService.getAnswer(id));
    }

    @ApiOperation(value = "Create Answer", tags = "createAnswer", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created answer"),
            @ApiResponse(code = 400, message = "Validation failed"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PostMapping("/{id}/create")
    public CreateAnswerResponse createAnswer(@PathVariable("id") Long id,@Valid @RequestBody CreateAnswerRequest createAnswerRequest) {
        return new CreateAnswerResponse(answerService.createAnswer(
                new Answer(createAnswerRequest), id));
    }

    @ApiOperation(value = "Update Answer", tags = "updateAnswer", httpMethod = "PUT")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated car record"),
            @ApiResponse(code = 400, message = "Validation failed"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PutMapping("/{answerId}/update/{questionId}")
    @PreAuthorize("hasRole('ADMIN') || principal.id==answer.question.form.creator.id")
    public UpdateAnswerResponse updateAnswer(@PathVariable("questionId") Long questionId, @PathVariable("answerId") Long answerId, @Valid @RequestBody UpdateAnswerRequest updateAnswerRequest) {
        return new UpdateAnswerResponse(answerService.updateAnswer(
                new Answer(answerId, updateAnswerRequest), questionId));
    }

    @ApiOperation(value = "Delete answer by id", tags = "deleteAnswer", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted answer by id"),
            @ApiResponse(code = 404, message = "Answer not found error"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') || principal.id==answer.question.form.creator.id")
    public void deleteAnswer(@PathVariable("id") Long id, @AuthenticationPrincipal Answer answer) {
        answerService.deleteAnswer(id);
    }
}
