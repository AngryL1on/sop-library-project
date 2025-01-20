package dev.angryl1on.libraryapi.models.dtos;

import dev.angryl1on.libraryapi.models.entity.enums.UserRoles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.UUID;

public class UserDTO extends RepresentationModel<UserDTO> {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private UserRoles role;
    private List<BorrowingDTO> borrowings;

    public UserDTO() { /* Do nothing */ }

    public UserDTO(UUID id, String name, String email, String password, UserRoles role, List<BorrowingDTO> borrowings) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.borrowings = borrowings;
    }

    public UserDTO(UUID id, String name, String email, String password, UserRoles role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserDTO(UUID id, String name, String email, UserRoles role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @NotBlank(message = "Имя пользователя обязательно")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Email(message = "Неверный формат электронной почты")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotBlank(message = "Пароль обязателен")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public List<BorrowingDTO> getBorrowings() {
        return borrowings;
    }

    public void setBorrowings(List<BorrowingDTO> borrowings) {
        this.borrowings = borrowings;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", borrowings=" + borrowings +
                '}';
    }
}
