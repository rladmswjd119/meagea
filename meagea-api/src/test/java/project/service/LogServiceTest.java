package project.service;

import entity.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.dto.LogForm;
import project.repository.LogRepository;
import project.service.LogService;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class LogServiceTest {

    @Mock
    private LogRepository logRepo;
    @InjectMocks
    private LogService logService;

    private Log log;

    private LogForm form;

    @BeforeEach
    public void setUp() {
        form = new LogForm(1, "내용", mock());
        ArgumentCaptor<Log> captor = ArgumentCaptor.forClass(Log.class);

        logService.addLog(form);
        verify(logRepo).save(captor.capture());

        log = captor.getValue();
    }

    @Test
    public void 로그_저장_성공_테스트() {
        verify(logRepo, times(1)).save(log);
        assertThat(form.getBody()).isEqualTo(log.getBody());
    }

    @Test
    public void 로그_저장_실패_테스트() {
        LogForm form = mock(LogForm.class);

        Throwable ex = assertThrows(RuntimeException.class, () -> logService.addLog(form));

        assertThat(ex.getMessage()).isEqualTo("Log 내용이 비어있습니다.");
    }

    @Test
    public void 로그_조회_성공_테스트() {
        List<Log> logList = new ArrayList<>();
        given(logRepo.findAllByPromotionNoOrderByMakeDate(anyInt())).willReturn(logList);

        logService.getAllLogByPromotionNo(anyInt());

        verify(logRepo, times(1)).findAllByPromotionNoOrderByMakeDate(anyInt());
    }
}
