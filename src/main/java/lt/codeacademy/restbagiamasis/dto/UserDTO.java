package lt.codeacademy.restbagiamasis.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lt.codeacademy.restbagiamasis.entity.Form;
import lt.codeacademy.restbagiamasis.entity.Role;
import lt.codeacademy.restbagiamasis.entity.User;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private Set<Form> forms;

    private Set<Role> roles;

    private String created;

    private String updated;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.roles = user.getRoles();
        this.created = user.getCreated().toString();
        this.updated = user.getUpdated().toString();
    }
}
