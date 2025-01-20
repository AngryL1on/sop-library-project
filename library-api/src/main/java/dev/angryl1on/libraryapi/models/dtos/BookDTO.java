package dev.angryl1on.libraryapi.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public class BookDTO extends RepresentationModel<BookDTO> {
    private UUID id;
    private String title;
    private String author;
    private String isbn;
    private int publicationYear;
    private boolean available;
    private UUID libraryId;

    private LibraryDTO library;

    public BookDTO() { /* Do nothing */ }

    public BookDTO(UUID id, String title, String author, String isbn, int publicationYear, boolean available, UUID libraryId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.available = available;
        this.libraryId = libraryId;
    }

    public BookDTO(UUID id, String title, String author, String isbn, int publicationYear, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.available = available;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @NotBlank(message = "Название книги обязательно")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotBlank(message = "Автор книги обязателен")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @NotBlank(message = "ISBN обязателен")
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Max(value = 2024, message = "Год издания не может быть из будущего")
    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public UUID getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(UUID libraryId) {
        this.libraryId = libraryId;
    }

    @JsonIgnore
    public LibraryDTO getLibrary() {
        return library;
    }

    public void setLibrary(LibraryDTO library) {
        this.library = library;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publicationYear=" + publicationYear +
                ", available=" + available +
                ", library=" + library +
                '}';
    }
}
