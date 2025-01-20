package dev.angryl1on.library.core.models.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@MappedSuperclass
public abstract class Base {
    private UUID id;

    @Id
    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
