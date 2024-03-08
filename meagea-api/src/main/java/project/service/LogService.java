package project.service;

import entity.AnimalFile;
import entity.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.dto.LogForm;
import project.dto.LogTatalDto;
import project.repository.AnimalFileRepository;
import project.repository.LogRepository;
import project.unit.AnimalFileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {
    final private LogRepository logRepo;
    final private AnimalFileRepository fileRepo;

    public void addLog(LogForm form) throws IOException {
        Log log = new Log(form.getPromotionNo(), form.getBody());
        logRepo.save(log);

        List<AnimalFile> imageList = new ArrayList<>();
        for(MultipartFile image : form.getImageList()){
            AnimalFileManager aniMan = new AnimalFileManager();
            String serverFileName = aniMan.serverFile(image);
            AnimalFile animalFile = new AnimalFile(form.getPromotionNo(), image.getOriginalFilename(), serverFileName, "log");
            imageList.add(fileRepo.save(animalFile));
        }
    }

    public List<LogTatalDto> getAllLogByPromotionNo(int promotionNo) {
        List<LogTatalDto> dtoList = new ArrayList<>();
        List<Log> logList = logRepo.findAllByPromotionNo(promotionNo);
        for(Log log : logList) {
            List<AnimalFile> animalFileList = fileRepo.findAllByLogNo(log.getNo());
            LogTatalDto dto = new LogTatalDto(log, animalFileList);
            dtoList.add(dto);
        }

        return dtoList;
    }
}
