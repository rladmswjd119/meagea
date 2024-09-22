package project.service;

import entity.AnimalFile;
import entity.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.LogForm;
import project.dto.LogTotalDto;
import project.repository.AnimalFileRepository;
import project.repository.LogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogService {
    final private LogRepository logRepo;
    final private AnimalFileRepository fileRepo;

    public Log addLog(LogForm form) {

        Log log = new Log(form.getPromotionNo(), form.getBody());
        if(form.getBody() == null) {
            System.out.println("예외!");
            throw new RuntimeException("Log 내용이 비어있습니다.");
        }

        return logRepo.save(log);
    }

    public List<Log> getAllLogByPromotionNo(int promotionNo) {
        System.out.println("DB에서 조회");
        return logRepo.findAllByPromotionNoOrderByMakeDate(promotionNo);
    }

    @Transactional(rollbackFor={Exception.class})
    public List<Log> deletAllLogByPromotionNo(int promotionNo) {
        List<Log> logList = logRepo.findAllByPromotionNo(promotionNo);
        try {
            if(!logList.isEmpty()){
                logRepo.deleteAll(logList);
                fileRepo.deleteAllByPromotionNo(promotionNo);
            }
        } catch (Exception ex) {
            throw new RuntimeException("홍보글 삭제가 취소되었습니다.");
        }
        return logList;
    }

    public List<CompletableFuture<LogTotalDto>> changeListComLogDto(List<Log> logList) {
        List<CompletableFuture<LogTotalDto>> logTotalDtoList = new ArrayList<>();
        for(Log log : logList) {
            logTotalDtoList.add(CompletableFuture.supplyAsync(() -> asyncChangeLogDto(log)));
        }
        return logTotalDtoList;
    }

    private LogTotalDto asyncChangeLogDto(Log log) {
        List<AnimalFile> animalFileList = fileRepo.findAllByLogNo(log.getNo());

        return new LogTotalDto(log.getPromotionNo(), log.getBody(), log.getMakeDate(), animalFileList);
    }

    public CompletableFuture<List<LogTotalDto>> changeComListLogDto(List<CompletableFuture<LogTotalDto>> listComLogDto) {
        CompletableFuture<?>[] array = listComLogDto.toArray(new CompletableFuture<?>[0]);
        return CompletableFuture.allOf(array)
                                .thenApply(i -> listComLogDto.stream().map(CompletableFuture::join).collect(Collectors.toList()));
    }

    public void deletAllLog() {
        logRepo.deleteAll();
    }
}
