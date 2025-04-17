package dev.angryl1on.library.core.configs;

import dev.angryl1on.library.core.models.entity.Borrowing;
import dev.angryl1on.libraryapi.models.dtos.BorrowingDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for defining beans.
 */
@Configuration
public class BeanConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Borrowing, BorrowingDTO>() {
            @Override
            protected void configure() {
                map(source.getDueDate(), destination.getDueDate());
                map(source.getFee(), destination.getFee());
            }
        });
        return modelMapper;
    }

}
