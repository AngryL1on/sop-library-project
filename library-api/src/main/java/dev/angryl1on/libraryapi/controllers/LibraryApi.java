package dev.angryl1on.libraryapi.controllers;

import dev.angryl1on.libraryapi.models.dtos.LibraryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "borrowings")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface LibraryApi {
    @Operation(summary = "Добавить библиотеку")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<LibraryDTO> addLibrary(@RequestBody LibraryDTO libraryDTO);

    @Operation(summary = "Получить библиотеку по идентификатору")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<LibraryDTO> getLibraryById(@RequestParam UUID id);

    @Operation(summary = "Получить список библиотек")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    CollectionModel<LibraryDTO> getAllLibraries();

    @Operation(summary = "Получить список библиотек по названию")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CollectionModel<LibraryDTO>> getLibrariesByName(@RequestParam String name);
}
