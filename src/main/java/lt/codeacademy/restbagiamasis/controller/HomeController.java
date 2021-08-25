package lt.codeacademy.restbagiamasis.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lt.codeacademy.restbagiamasis.dto.UserDTO;
import lt.codeacademy.restbagiamasis.dto.request.RegisterRequest;
import lt.codeacademy.restbagiamasis.dto.response.LoginResponse;
import lt.codeacademy.restbagiamasis.entity.User;
import lt.codeacademy.restbagiamasis.service.JwtService;
import lt.codeacademy.restbagiamasis.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
public class HomeController {

    private final JwtService jwtService;

    private final UserService userService;

    public HomeController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping
    public String home() {
        return "OK";
    }

    @ApiOperation(value = "Login", tags = "login", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User successfully logged in")
    })
    @PostMapping("/login")
    public LoginResponse login(@AuthenticationPrincipal User user) throws ParseException {
        return new LoginResponse(jwtService.createToken(user));
    }

    @ApiOperation(value = "Register new user", tags = "register", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User successfully created")
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@Valid @RequestBody RegisterRequest registerRequest) {
        return new UserDTO(userService.createUser(
                new User(registerRequest)));
    }
}
