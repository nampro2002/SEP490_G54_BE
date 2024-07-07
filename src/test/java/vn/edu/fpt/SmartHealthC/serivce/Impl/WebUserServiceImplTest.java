package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vn.edu.fpt.SmartHealthC.domain.dto.request.WebUserRequestDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.WebUser;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.WebUserRepository;
import vn.edu.fpt.SmartHealthC.serivce.Impl.WebUserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WebUserServiceImplTest {

    @Mock
    private WebUserRepository webUserRepository;

    @InjectMocks
    private WebUserServiceImpl webUserService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWebUserById_ValidId() {
        // Arrange
        Integer id = 1;
        WebUser mockWebUser = new WebUser();
        mockWebUser.setId(id);
        when(webUserRepository.findById(id)).thenReturn(Optional.of(mockWebUser));

        // Act
        WebUser retrievedWebUser = webUserService.getWebUserById(id);

        // Assert
        assertNotNull(retrievedWebUser);
        assertEquals(id, retrievedWebUser.getId());
    }

    @Test
    void testGetWebUserById_InvalidId() {
        // Arrange
        Integer id = 1;
        when(webUserRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppException.class, () -> webUserService.getWebUserById(id));
    }

    @Test
    void testGetAllWebUsers_EmptyList() {
        // Arrange
        when(webUserRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<WebUser> retrievedWebUsers = webUserService.getAllWebUsers();

        // Assert
        assertEquals(0, retrievedWebUsers.size());
    }

    @Test
    void testGetAllWebUsers_NonEmptyList() {
        // Arrange
        List<WebUser> mockWebUsers = new ArrayList<>();
        mockWebUsers.add(new WebUser());
        mockWebUsers.add(new WebUser());
        when(webUserRepository.findAll()).thenReturn(mockWebUsers);

        // Act
        List<WebUser> retrievedWebUsers = webUserService.getAllWebUsers();

        // Assert
        assertEquals(2, retrievedWebUsers.size());
    }

    @Test
    void testGetAllUnDeletedMS_EmptyList() {
        // Arrange
        when(webUserRepository.findAllUnDeletedMS()).thenReturn(new ArrayList<>());

        // Act
        List<WebUser> retrievedWebUsers = webUserService.getAllUnDeletedMS();

        // Assert
        assertEquals(0, retrievedWebUsers.size());
    }

    @Test
    void testGetAllUnDeletedMS_NonEmptyList() {
        // Arrange
        List<WebUser> mockWebUsers = new ArrayList<>();
        mockWebUsers.add(new WebUser());
        mockWebUsers.add(new WebUser());
        when(webUserRepository.findAllUnDeletedMS()).thenReturn(mockWebUsers);

        // Act
        List<WebUser> retrievedWebUsers = webUserService.getAllUnDeletedMS();

        // Assert
        assertEquals(2, retrievedWebUsers.size());
    }

    @Test
    void testGetWebUserByEmail_ValidEmail() {
        // Arrange
        String email = "test@example.com";
        WebUser mockWebUser = new WebUser();
        when(webUserRepository.findByEmail(email)).thenReturn(Optional.of(mockWebUser));

        // Act
        WebUser retrievedWebUser = webUserService.getWebUserByEmail(email);

        // Assert
        assertNotNull(retrievedWebUser);
    }

    @Test
    void testGetWebUserByEmail_InvalidEmail() {
        // Arrange
        String email = "nonexistent@example.com";
        when(webUserRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AppException.class, () -> webUserService.getWebUserByEmail(email));
    }
}