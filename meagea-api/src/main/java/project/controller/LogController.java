package project.controller;

import entity.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.dto.LogForm;
import project.dto.LogTotalDto;
import project.eunm.FileType;
import project.service.LogService;
import project.service.PromotionService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meagea/logs")
public class LogController {

    final private LogService logService;
    final private PromotionService proService;

    @PostMapping("/")
    public List<LogTotalDto> addLog(@ModelAttribute LogForm form) throws IOException {
        Log log = logService.addLog(form);
        proService.saveAnimalFile(form.getPromotionNo(), form.getImageList(), log.getNo(), FileType.LOG);

        List<Log> logList = logService.getAllLogByPromotionNo(form.getPromotionNo());
        List<CompletableFuture<LogTotalDto>> listComLogDto = logService.changeListComLogDto(logList);
        CompletableFuture<List<LogTotalDto>> comListLogDto = logService.changeComListLogDto(listComLogDto);

        return comListLogDto.join();
    }

    @DeleteMapping("/")
    public void deleteAllLog(){
        logService.deletAllLog();
    }
}
