package dev.angryl1on.library.core.services.impl;

import dev.angryl1on.library.core.exceptions.BookNotFoundException;
import dev.angryl1on.library.core.exceptions.BorrowingNotFoundByIdException;
import dev.angryl1on.library.core.exceptions.BorrowingNotFoundException;
import dev.angryl1on.library.core.exceptions.UserNotFoundException;
import dev.angryl1on.library.core.models.entity.Book;
import dev.angryl1on.library.core.models.entity.Borrowing;
import dev.angryl1on.library.core.models.entity.User;
import dev.angryl1on.library.core.repositories.BookRepository;
import dev.angryl1on.library.core.repositories.BorrowingRepository;
import dev.angryl1on.library.core.repositories.UserRepository;
import dev.angryl1on.library.core.services.BorrowingService;
import dev.angryl1on.libraryapi.models.dtos.BorrowingDTO;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BorrowingServiceImpl implements BorrowingService {
    private final BorrowingRepository borrowingRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public BorrowingServiceImpl(BorrowingRepository borrowingRepository,
                                UserRepository userRepository,
                                BookRepository bookRepository,
                                ModelMapper modelMapper,
                                RabbitTemplate rabbitTemplate) {
        this.borrowingRepository = borrowingRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public BorrowingDTO borrowBook(UUID userId, UUID bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        Borrowing borrowing = new Borrowing(user, book, LocalDate.now(), null);
        Borrowing savedBorrowing = borrowingRepository.save(borrowing);

        rabbitTemplate.convertAndSend("audit_logs_queue",
                "User with ID " + userId + " borrowed book with ID " + bookId);

        return modelMapper.map(savedBorrowing, BorrowingDTO.class);
    }

    @Override
    public void returnBook(UUID userId, UUID bookId) {
        Borrowing borrowing = borrowingRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new BorrowingNotFoundByIdException(userId, bookId));

        borrowing.setReturnDate(LocalDate.now());
        borrowingRepository.save(borrowing);

        rabbitTemplate.convertAndSend("audit_logs_queue",
                "User with ID " + userId + " returned book with ID " + bookId);
    }

    @Override
    public BorrowingDTO getBorrowingById(UUID id) {
        Borrowing borrowing = borrowingRepository.findById(id)
                .orElseThrow(() -> new BorrowingNotFoundException(id));

        rabbitTemplate.convertAndSend("audit_logs_queue",
                "Fetched borrowing with ID " + id);

        return modelMapper.map(borrowing, BorrowingDTO.class);
    }

    @Override
    public List<BorrowingDTO> getAllBorrowings() {
        List<Borrowing> borrowings = borrowingRepository.findAll();

        rabbitTemplate.convertAndSend("audit_logs_queue",
                "Fetched all borrowings");

        return borrowings.stream()
                .map(borrowing -> modelMapper.map(borrowing, BorrowingDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowingDTO> getBorrowingsByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<Borrowing> borrowings = borrowingRepository.findByUser(user);

        rabbitTemplate.convertAndSend("audit_logs_queue",
                "Fetched borrowings for user with ID " + userId);

        return borrowings.stream()
                .map(borrowing -> modelMapper.map(borrowing, BorrowingDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowingDTO> getActiveBorrowingsByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<Borrowing> activeBorrowings = borrowingRepository.findActiveBorrowingsByUser(user);

        rabbitTemplate.convertAndSend("audit_logs_queue",
                "Fetched active borrowings for user with ID " + userId);

        return activeBorrowings.stream()
                .map(borrowing -> modelMapper.map(borrowing, BorrowingDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowingDTO> getActiveBorrowingsByBook(UUID bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        List<Borrowing> activeBorrowings = borrowingRepository.findActiveBorrowingsByBook(book);

        rabbitTemplate.convertAndSend("audit_logs_queue",
                "Fetched active borrowings for book with ID " + bookId);

        return activeBorrowings.stream()
                .map(borrowing -> modelMapper.map(borrowing, BorrowingDTO.class))
                .collect(Collectors.toList());
    }
}
