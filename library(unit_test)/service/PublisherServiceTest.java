package com.project.library.service;

import com.project.library.dto.PublisherDto;
import com.project.library.mapper.PublisherMapper;
import com.project.library.model.Publisher;
import com.project.library.repository.PublisherRepository;
import com.project.library.service.impl.PublisherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID; // UUID kullanımı

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PublisherServiceTest {

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private PublisherMapper publisherMapper;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    private UUID publisherId; // UUID olarak tanımlandı

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        publisherId = UUID.randomUUID(); // UUID oluşturuluyor
    }

    @Test
    void testGetAllPublishers() {
        Publisher publisher = new Publisher();
        PublisherDto publisherDto = new PublisherDto(publisherId, "Publisher Name", "Publisher Address"); // UUID kullanılıyor

        when(publisherRepository.findAll()).thenReturn(Collections.singletonList(publisher));
        when(publisherMapper.toDto(publisher)).thenReturn(publisherDto);

        List<PublisherDto> publishers = publisherService.getAllPublishers();

        assertNotNull(publishers);
        assertEquals(1, publishers.size());
        assertEquals(publisherDto, publishers.get(0));
        verify(publisherRepository, times(1)).findAll();
        verify(publisherMapper, times(1)).toDto(publisher);
    }

    @Test
    void testGetPublisherById() {
        Publisher publisher = new Publisher();
        PublisherDto publisherDto = new PublisherDto(publisherId, "Publisher Name", "Publisher Address"); // UUID kullanılıyor

        when(publisherRepository.findById(publisherId)).thenReturn(Optional.of(publisher)); // UUID kullanılıyor
        when(publisherMapper.toDto(publisher)).thenReturn(publisherDto);

        Optional<PublisherDto> foundPublisher = publisherService.getPublisherById(publisherId); // UUID kullanılıyor

        assertTrue(foundPublisher.isPresent());
        assertEquals(publisherDto, foundPublisher.get());
        verify(publisherRepository, times(1)).findById(publisherId); // UUID kullanılıyor
        verify(publisherMapper, times(1)).toDto(publisher);
    }

    @Test
    void testCreatePublisher() {
        Publisher publisher = new Publisher();
        PublisherDto publisherDto = new PublisherDto(publisherId, "Publisher Name", "Publisher Address"); // UUID kullanılıyor

        when(publisherMapper.toModel(publisherDto)).thenReturn(publisher);
        when(publisherRepository.save(publisher)).thenReturn(publisher);
        when(publisherMapper.toDto(publisher)).thenReturn(publisherDto);

        PublisherDto createdPublisher = publisherService.createPublisher(publisherDto);

        assertNotNull(createdPublisher);
        assertEquals(publisherDto, createdPublisher);
        verify(publisherMapper, times(1)).toModel(publisherDto);
        verify(publisherRepository, times(1)).save(publisher);
        verify(publisherMapper, times(1)).toDto(publisher);
    }

    @Test
    void testUpdatePublisher() {
        Publisher publisher = new Publisher();
        PublisherDto publisherDto = new PublisherDto(publisherId, "Updated Publisher", "Updated Address"); // UUID kullanılıyor

        when(publisherRepository.findById(publisherId)).thenReturn(Optional.of(publisher)); // UUID kullanılıyor
        when(publisherRepository.save(publisher)).thenReturn(publisher);
        when(publisherMapper.toDto(publisher)).thenReturn(publisherDto);

        PublisherDto updatedPublisher = publisherService.updatePublisher(publisherId, publisherDto); // UUID kullanılıyor

        assertNotNull(updatedPublisher);
        assertEquals(publisherDto, updatedPublisher);
        verify(publisherRepository, times(1)).findById(publisherId); // UUID kullanılıyor
        verify(publisherRepository, times(1)).save(publisher);
        verify(publisherMapper, times(1)).toDto(publisher);
    }

    @Test
    void testUpdatePublisher_NotFound() {
        PublisherDto publisherDto = new PublisherDto(publisherId, "Publisher Name", "Publisher Address"); // UUID kullanılıyor

        when(publisherRepository.findById(publisherId)).thenReturn(Optional.empty()); // UUID kullanılıyor

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            publisherService.updatePublisher(publisherId, publisherDto); // UUID kullanılıyor
        });

        assertEquals("Publisher not found", exception.getMessage());
        verify(publisherRepository, times(1)).findById(publisherId); // UUID kullanılıyor
        verify(publisherRepository, times(0)).save(any(Publisher.class));
    }

    @Test
    void testDeletePublisher() {
        publisherService.deletePublisher(publisherId); // UUID kullanılıyor
        verify(publisherRepository, times(1)).deleteById(publisherId); // UUID kullanılıyor
    }
}
