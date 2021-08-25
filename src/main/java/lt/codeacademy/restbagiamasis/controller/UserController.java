package lt.codeacademy.restbagiamasis.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lt.codeacademy.restbagiamasis.dto.FormDTO;
import lt.codeacademy.restbagiamasis.dto.UserDTO;
import lt.codeacademy.restbagiamasis.service.FormService;
import lt.codeacademy.restbagiamasis.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final FormService formService;

    public UserController(UserService userService, FormService formService) {
        this.userService = userService;
        this.formService = formService;
    }

    @ApiOperation(value = "Get all users", tags = "getUsers", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got all users"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(UserDTO::new).collect(toList());
    }


    @ApiOperation(value = "Get user by id", tags = "getUser", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got user by id"),
            @ApiResponse(code = 404, message = "User not found error"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || principal.id == #id")
    public UserDTO getUser(@PathVariable("id") Long id) {
        return new UserDTO(userService.getUser(id));
    }

    @ApiOperation(value = "Get all forms by user id", tags = "getUserForms", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got list of forms"),
            @ApiResponse(code = 404, message = "User not found error"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/{id}/forms")
    @PreAuthorize("hasRole('ADMIN') || principal.id == #id")
    public Set<FormDTO> getUserForms(@PathVariable("id") Long id) {
        return formService.getAllFormsByCreatorId(id).stream().map(FormDTO::new).collect(toSet());
    }

    @ApiOperation(value = "Delete user by id", tags = "deleteUser", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted user by id"),
            @ApiResponse(code = 404, message = "User not found error"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') || principal.id == #id")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}
