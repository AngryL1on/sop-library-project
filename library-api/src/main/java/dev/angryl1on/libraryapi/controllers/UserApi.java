package dev.angryl1on.libraryapi.controllers;

import dev.angryl1on.libraryapi.models.dtos.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "users")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface UserApi {
    @Operation(summary = "Создать пользователя")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    void registerUser(@RequestBody UserDTO userDTO);

    @Operation(summary = "Получить информацию о пользователе по идентификатору")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDTO> getUserById(@RequestParam UUID id);

    @Operation(summary = "Получить список пользователей")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    CollectionModel<UserDTO> getAllUsers();

    @Operation(summary = "Обновить информацию о пользователе")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDTO> updateUser(@RequestParam UUID id, @RequestBody UserDTO userDTO);

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteUser(@RequestParam UUID id);
}
