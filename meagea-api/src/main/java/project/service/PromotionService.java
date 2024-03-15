package project.service;

import entity.Animal;
import entity.AnimalFile;
import entity.Log;
import entity.Promotion;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.dto.PromotionForm;
import project.dto.PromotionModifyForm;
import project.repository.AnimalFileRepository;
import project.repository.AnimalRepository;
import project.repository.LogRepository;
import project.repository.PromotionRepository;
import project.unit.AnimalFileManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository proRepo;
    private final AnimalRepository animalRepo;
    private final AnimalFileRepository fileRepo;
    private final LogRepository logRepo;

    public Promotion savePromotion(PromotionForm form) {
        Optional<Animal> animal = animalRepo.findById(form.getAnimalNo());
        if(animal.isEmpty()) {
            throw new NullPointerException("조회 결과 없음");
        }
        return proRepo.save(new Promotion(form.getTitle(), animal.get().getNo(), form.getIntroduction(), form.getCondition()));
    }

    @Async("fileThread")
    public Future<List<AnimalFile>> saveAnimalFile(int proNo, List<MultipartFile> imageList) throws IOException {
        AnimalFileManager fileMan = new AnimalFileManager();
        List<AnimalFile> animalFileList = new ArrayList<>();
        try {
            if(imageList.size() > 10) {
                throw new IOException("이미지 파일은 최대 4개까지 첨부가 가능합니다.");
            }

            for(MultipartFile m : imageList) {
                AnimalFile animalFile = new AnimalFile(proNo, m.getOriginalFilename(), fileMan.serverFile(m), "promotion");
                fileRepo.save(animalFile);
                animalFileList.add(animalFile);
            }
        } catch (IOException ex){
            proRepo.deleteById(proNo);
            throw new IOException("홍보글 생성이 취소되었습니다.");
        }
        return new AsyncResult<>(animalFileList);
    }

    public Promotion findPromotionByNo(int no) {
        Optional<Promotion> pro = proRepo.findById(no);
        if(pro.isEmpty()){
            throw new NullPointerException("조회 결과 없음");
        }
        return pro.get();
    }

    public List<Promotion> findAllPromotion() {
        List<Promotion> proList = proRepo.findByRemove(0);
        if(proList.isEmpty()) {
            throw new NullPointerException("조회 결과 없음");
        }

        return proList;
    }

    public Promotion updatePromotion(PromotionModifyForm modifyDto) {
        Optional<Promotion> optPro = proRepo.findById(modifyDto.getNo());
        if(optPro.isEmpty()) {
            throw new NullPointerException("수정 가능한 Promotion 데이터가 존재하지 않습니다.");
        }
        Promotion pro = optPro.get();
        pro.setTitle(modifyDto.getTitle());
        pro.setIntroduction(modifyDto.getIntroduction());
        pro.setTerms(modifyDto.getTerms());
        pro.setModifyDate(LocalDateTime.now());
        proRepo.save(pro);

        return pro;
    }

    public Promotion deletePromotion(int promotionNo) {
        Promotion pro = proRepo.findById(promotionNo).get();
        try {
            pro.setRemove(1);
            proRepo.save(pro);
        } catch (Exception ex){
            List<Log> deleteLogList = logRepo.findAllByPromotionNoAndRemove(promotionNo, 1);
            deleteLogList.forEach(log -> log.setRemove(0));
            logRepo.saveAll(deleteLogList);

            List<AnimalFile> deleteAnimalFileList = fileRepo.findAllByPromotionNo(promotionNo);
            deleteAnimalFileList.forEach(file -> file.setRemove(0));
            fileRepo.saveAll(deleteAnimalFileList);

            throw new RuntimeException("홍보글 삭제가 취소되었습니다.");
        }

        return pro;
    }

    public List<AnimalFile> findAllAnimalFIleByPromotionNo(int no) {
        return fileRepo.findAllByPromotionNoAndProperty(no, "promotion");
    }

    public List<AnimalFile> deleteAnimalFIleListByPromotionNo(int promotionNo) {
        List<AnimalFile> deleteAnimalFileList = fileRepo.findAllByPromotionNo(promotionNo);
        deleteAnimalFileList.forEach(file -> file.setRemove(1));
        fileRepo.saveAll(deleteAnimalFileList);

        return deleteAnimalFileList;
    }
}
