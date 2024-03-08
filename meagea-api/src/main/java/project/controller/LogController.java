package project.controller;

import entity.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.dto.LogForm;
import project.dto.LogTatalDto;
import project.service.LogService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meagea")
public class LogController {

    final private LogService logService;

    @PostMapping("/log")
    public List<LogTatalDto> addLog(@ModelAttribute LogForm form) throws IOException {
        logService.addLog(form);
        return logService.getAllLogByPromotionNo(form.getPromotionNo());
    }
}
