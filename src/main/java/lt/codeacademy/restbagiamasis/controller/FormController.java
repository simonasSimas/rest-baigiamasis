package lt.codeacademy.restbagiamasis.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lt.codeacademy.restbagiamasis.dto.FormDTO;
import lt.codeacademy.restbagiamasis.dto.request.create.CreateFormRequest;
import lt.codeacademy.restbagiamasis.dto.request.update.UpdateFormRequest;
import lt.codeacademy.restbagiamasis.dto.response.create.CreateFormResponse;
import lt.codeacademy.restbagiamasis.dto.response.update.UpdateFormResponse;
import lt.codeacademy.restbagiamasis.entity.Form;
import lt.codeacademy.restbagiamasis.entity.User;
import lt.codeacademy.restbagiamasis.entity.question.Question;
import lt.codeacademy.restbagiamasis.service.AnswerService;
import lt.codeacademy.restbagiamasis.service.FormService;
import lt.codeacademy.restbagiamasis.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/forms")
public class FormController {

    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @ApiOperation(value = "Get all forms", tags = "getForms", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got forms"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<FormDTO> getForms() {
        return formService.getAllForms().stream().map(FormDTO::new).collect(toList());
    }

    @ApiOperation(value = "Get form by id", tags = "getForm", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get form by id"),
            @ApiResponse(code = 404, message = "Form not found error"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/{id}")
    public FormDTO getForm(@PathVariable("id") Long id) {
        return new FormDTO(formService.getForm(id));
    }

    @ApiOperation(value = "Create form", tags = "createForm", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created car record"),
            @ApiResponse(code = 400, message = "Validation failed"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PostMapping("/create")
    public CreateFormResponse createForm(@Valid @RequestBody CreateFormRequest createFormRequest, @AuthenticationPrincipal User user) {
        return new CreateFormResponse(formService.createForm(
                new Form(createFormRequest), user.getId()));
    }

    @ApiOperation(value = "Update form", tags = "updateForm", httpMethod = "PUT")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated car record"),
            @ApiResponse(code = 400, message = "Validation failed"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PutMapping("/{id}")
    public UpdateFormResponse updateForm(@PathVariable("id") Long id, @Valid @RequestBody UpdateFormRequest updateFormRequest,
                                        @AuthenticationPrincipal User user) {
        return new UpdateFormResponse(formService.updateForm(
                new Form(id, updateFormRequest), user.isAdmin() ? updateFormRequest.getCreatorId() : user.getId()));
    }

    @ApiOperation(value = "Delete form by id", tags = "deleteForm", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted form by id"),
            @ApiResponse(code = 404, message = "Form not found error"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') || (form.id==#id && form.creator.id==principal.id)")
    public void deleteForm(@PathVariable("id") Long id, @AuthenticationPrincipal Form form) {
        formService.deleteForm(id);
    }
}
