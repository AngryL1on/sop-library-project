package dev.angryl1on.libraryapi.controllers;

import dev.angryl1on.libraryapi.models.dtos.BookDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "books")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface BookApi {
    @Operation(summary = "Создать книгу")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO);

    @Operation(summary = "Получить информацию о книге по идентификатору")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BookDTO> getBookById(@PathVariable("id") UUID id);

    @Operation(summary = "Получить список всех книг")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    CollectionModel<BookDTO> getAllBooks();

    @Operation(summary = "Получить список книг по названию")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    CollectionModel<BookDTO> getBooksByTitle(@RequestParam String title);

    @Operation(summary = "Получить список книг по автору")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    CollectionModel<BookDTO> getBooksByAuthor(@RequestParam String author);

    @Operation(summary = "Получить список доступных книг")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    CollectionModel<BookDTO> getAvailableBooks();

    @Operation(summary = "Назначить книгу библиотеке")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BookDTO> assignBookToLibrary(
            @PathVariable UUID bookId,
            @RequestParam UUID libraryId);

    @Operation(summary = "Удалить книгу из библиотеки")
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteBook(@RequestParam UUID id);

}
