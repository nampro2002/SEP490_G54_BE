package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vn.edu.fpt.SmartHealthC.domain.dto.request.LessonRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.LessonResponseDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.response.ResponsePaging;
import vn.edu.fpt.SmartHealthC.exception.AppException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LessonServiceImplTest {

    @Mock
    private LessonRepository lessonRepository;

    @InjectMocks
    private LessonServiceImpl lessonService;

    private Lesson testLesson;

    @BeforeEach
    void setUp() {
        testLesson = Lesson.builder()
                .id(1)
                .title("Test Lesson")
                .content("Test Content")
                .video("test-video.mp4")
                .lessonNumber(1)
                .build();
    }

    @Test
    void testCreateLesson() {
        // Arrange
        LessonRequestDTO requestDTO = LessonRequestDTO.builder()
                .title("New Lesson")
                .content("New Content")
                .video("new-video.mp4")
                .lessonNumber(2)
                .build();

        when(lessonRepository.save(any(Lesson.class))).thenReturn(testLesson);

        // Act
        LessonResponseDTO responseDTO = lessonService.createLesson(requestDTO);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(testLesson.getTitle(), responseDTO.getTitle());
        assertEquals(testLesson.getContent(), responseDTO.getContent());
        assertEquals(testLesson.getVideo(), responseDTO.getVideo());
        assertEquals(testLesson.getLessonNumber(), responseDTO.getLessonNumber());
    }

    @Test
    void testCreateLesson_ThrowsAppException_WhenSaveFails() {
        // Arrange
        LessonRequestDTO requestDTO = LessonRequestDTO.builder()
                .title("New Lesson")
                .content("New Content")
                .video("new-video.mp4")
                .lessonNumber(2)
                .build();

        when(lessonRepository.save(any(Lesson.class))).thenThrow(RuntimeException.class);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> lessonService.createLesson(requestDTO));
    }

    @Test
    void testGetLessonById() {
        // Arrange
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(testLesson));

        // Act
        LessonResponseDTO responseDTO = lessonService.getLessonById(1);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(testLesson.getTitle(), responseDTO.getTitle());
        assertEquals(testLesson.getContent(), responseDTO.getContent());
        assertEquals(testLesson.getVideo(), responseDTO.getVideo());
        assertEquals(testLesson.getLessonNumber(), responseDTO.getLessonNumber());
    }

    @Test
    void testGetLessonById_ThrowsAppException_WhenLessonNotFound() {
        // Arrange
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(AppException.class, () -> lessonService.getLessonById(1));
    }

    @Test
    void testGetLessonEntityById() {
        // Arrange
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(testLesson));

        // Act
        Lesson result = lessonService.getLessonEntityById(1);

        // Assert
        assertNotNull(result);
        assertEquals(testLesson.getId(), result.getId());
        assertEquals(testLesson.getTitle(), result.getTitle());
        assertEquals(testLesson.getContent(), result.getContent());
        assertEquals(testLesson.getVideo(), result.getVideo());
        assertEquals(testLesson.getLessonNumber(), result.getLessonNumber());
    }

    @Test
    void testGetLessonEntityById_ThrowsAppException_WhenLessonNotFound() {
        // Arrange
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(AppException.class, () -> lessonService.getLessonEntityById(1));
    }

    @Test
    void testGetAllLessons() {
        // Arrange
        List<Lesson> lessonList = new ArrayList<>();
        lessonList.add(testLesson);

        Page<Lesson> page = new PageImpl<>(lessonList); // Create a PageImpl with your lessonList

        when(lessonRepository.findAll(any(Pageable.class), anyString())).thenReturn(page);

        // Act
        ResponsePaging<List<LessonResponseDTO>> response = lessonService.getAllLessons(0, "");

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getDataResponse().size()); // Check size should be 1

        LessonResponseDTO responseDTO = response.getDataResponse().get(0);
        assertEquals(testLesson.getTitle(), responseDTO.getTitle());
        assertEquals(testLesson.getContent(), responseDTO.getContent());
        assertEquals(testLesson.getVideo(), responseDTO.getVideo());
        assertEquals(testLesson.getLessonNumber(), responseDTO.getLessonNumber());
        assertEquals(1, response.getTotalPages());
        assertEquals(1, response.getCurrentPage()); // Assuming pageNo is 0 in this test case
    }

    @Test
    void testUpdateLesson() {
        // Arrange
        LessonRequestDTO requestDTO = LessonRequestDTO.builder()
                .title("Updated Lesson")
                .content("Updated Content")
                .video("updated-video.mp4")
                .lessonNumber(2)
                .build();

        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(testLesson));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(testLesson);

        // Act
        LessonResponseDTO responseDTO = lessonService.updateLesson(1, requestDTO);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(testLesson.getTitle(), responseDTO.getTitle());
        assertEquals(testLesson.getContent(), responseDTO.getContent());
        assertEquals(testLesson.getVideo(), responseDTO.getVideo());
        assertEquals(testLesson.getLessonNumber(), responseDTO.getLessonNumber());
    }

    @Test
    void testUpdateLesson_ThrowsAppException_WhenLessonNotFound() {
        // Arrange
        LessonRequestDTO requestDTO = LessonRequestDTO.builder()
                .title("Updated Lesson")
                .content("Updated Content")
                .video("updated-video.mp4")
                .lessonNumber(2)
                .build();

        when(lessonRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(AppException.class, () -> lessonService.updateLesson(1, requestDTO));
    }

    @Test
    void testDeleteLesson() {
        // Arrange
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(testLesson));

        // Act
        LessonResponseDTO responseDTO = lessonService.deleteLesson(1);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(testLesson.getTitle(), responseDTO.getTitle());
        assertEquals(testLesson.getContent(), responseDTO.getContent());
        assertEquals(testLesson.getVideo(), responseDTO.getVideo());
        assertEquals(testLesson.getLessonNumber(), responseDTO.getLessonNumber());
    }

    @Test
    void testDeleteLesson_ThrowsAppException_WhenLessonNotFound() {
        // Arrange
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(AppException.class, () -> lessonService.deleteLesson(1));
    }
}
