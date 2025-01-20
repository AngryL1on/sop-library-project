package dev.angryl1on.library.api.rest.controllers;

import dev.angryl1on.library.api.rest.hateoas.assemblers.LibraryAssembler;
import dev.angryl1on.library.core.services.LibraryService;
import dev.angryl1on.libraryapi.controllers.LibraryApi;
import dev.angryl1on.libraryapi.models.dtos.LibraryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/libraries")
public class LibraryController implements LibraryApi {
    private final LibraryService libraryService;
    private final LibraryAssembler libraryAssembler;

    @Autowired
    public LibraryController(LibraryService libraryService, LibraryAssembler libraryAssembler) {
        this.libraryService = libraryService;
        this.libraryAssembler = libraryAssembler;
    }

    @Override
    @PostMapping
    public ResponseEntity<LibraryDTO> addLibrary(@RequestBody LibraryDTO libraryDTO) {
        LibraryDTO newLibrary = libraryService.addLibrary(libraryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryAssembler.toModel(newLibrary));
    }

    @Override
    @GetMapping("/find")
    public ResponseEntity<LibraryDTO> getLibraryById(@RequestParam UUID id) {
        LibraryDTO libraryDTO = libraryService.getLibraryById(id);
        return ResponseEntity.ok(libraryAssembler.toModel(libraryDTO));
    }

    @Override
    @GetMapping
    public CollectionModel<LibraryDTO> getAllLibraries() {
        List<LibraryDTO> libraries = libraryService.getAllLibraries();
        return libraryAssembler.toCollectionModel(libraries);
    }

    @Override
    @GetMapping("/find/name")
    public ResponseEntity<CollectionModel<LibraryDTO>> getLibrariesByName(@RequestParam String name) {
        List<LibraryDTO> libraries = libraryService.getLibrariesByName(name);
        return ResponseEntity.ok(libraryAssembler.toCollectionModel(libraries));
    }
}
