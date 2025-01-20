package dev.angryl1on.libraryapi.controllers;

import dev.angryl1on.libraryapi.models.dtos.BorrowingDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "borrowings")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface BorrowingApi {
    @Operation(summary = "Получить книгу")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BorrowingDTO> borrowBook(@RequestParam UUID userId, @RequestParam UUID bookId);

    @Operation(summary = "Вернуть книгу")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> returnBook(@RequestParam UUID userId, @RequestParam UUID bookId);

    @Operation(summary = "Получить информацию о аренде по идентификатору")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BorrowingDTO> getBorrowingById(@RequestParam UUID id);

    @Operation(summary = "Получить список всех аренд")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    CollectionModel<BorrowingDTO> getAllBorrowings();

    @Operation(summary = "Получить список аренд по идентификатору пользователя")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    CollectionModel<BorrowingDTO> getBorrowingsByUser(@RequestParam UUID userId);

    @Operation(summary = "Получить список активных аренд по идентификатору пользователя")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    CollectionModel<BorrowingDTO> getActiveBorrowingsByUser(@RequestParam UUID userId);

    @Operation(summary = "Получить список активных аренд по идентификатору книги")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    CollectionModel<BorrowingDTO> getActiveBorrowingsByBook(@RequestParam UUID bookId);
}
