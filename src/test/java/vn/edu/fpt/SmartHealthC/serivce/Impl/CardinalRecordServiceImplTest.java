package vn.edu.fpt.SmartHealthC.serivce.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ActivityRecordCreateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.ActivityRecordUpdateDTO;
import vn.edu.fpt.SmartHealthC.domain.dto.request.CardinalRecordDTO;
import vn.edu.fpt.SmartHealthC.domain.entity.ActivityRecord;
import vn.edu.fpt.SmartHealthC.domain.entity.AppUser;
import vn.edu.fpt.SmartHealthC.domain.entity.CardinalRecord;
import vn.edu.fpt.SmartHealthC.exception.AppException;
import vn.edu.fpt.SmartHealthC.exception.ErrorCode;
import vn.edu.fpt.SmartHealthC.repository.ActivityRecordRepository;
import vn.edu.fpt.SmartHealthC.repository.AppUserRepository;
import vn.edu.fpt.SmartHealthC.repository.CardinalRecordRepository;
import vn.edu.fpt.SmartHealthC.serivce.AppUserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CardinalRecordServiceImplTest {
    @Mock
    private CardinalRecordRepository cardinalRecordRepository;
    @InjectMocks
    private CardinalRecordServiceImpl cardinalRecordService;
    @Mock
    private AppUserRepository appUserRepository;


    private AppUser testAppUser;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        testAppUser = new AppUser();
        testAppUser.setId(1);
        testAppUser.setName("Test User");
    }

    @Test
    void createCardinalRecord_appUserID_Notfound() {

        CardinalRecordDTO activityRecord = new CardinalRecordDTO();

        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("Test@gmail.com");

        // Mock empty user retrieval (Optional.empty())
        when(appUserRepository.findByAccountEmail("Test@gmail.com")).thenReturn(Optional.empty());

        // Assert the expected exception with specific error code
        AppException exception = assertThrows(AppException.class, () -> cardinalRecordService.createCardinalRecord(activityRecord));

        assertEquals(ErrorCode.APP_USER_NOT_FOUND, exception.getErrorCode());

    }


}
