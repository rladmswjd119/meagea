package project.service;

import entity.AnimalFile;
import entity.Log;
import entity.Promotion;
import lombok.RequiredArgsConstructor;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository proRepo;
    private final AnimalRepository animalRepo;
    private final AnimalFileRepository fileRepo;
    private final LogRepository logRepo;

    public Promotion savePromotion(PromotionForm form) {
        if(animalRepo.findById(form.getAnimalNo()).isEmpty()) {
            throw new NullPointerException("조회 결과 없음");
        }
        return proRepo.save(new Promotion(form.getTitle(), form.getAnimalNo(), form.getIntroduction(), form.getCondition()));
    }

    public List<AnimalFile> saveAnimalFile(int proNo, List<MultipartFile> imageList) throws IOException {
        AnimalFileManager fileMan = new AnimalFileManager();
        List<AnimalFile> animalFileList = new ArrayList<>();
        try {
            for(MultipartFile m : imageList) {
                AnimalFile animalFile = new AnimalFile(proNo, m.getOriginalFilename(), fileMan.serverFile(m), "promotion");
                fileRepo.save(animalFile);
                animalFileList.add(animalFile);
            }
        } catch (IOException ex){
            proRepo.deleteById(proNo);
            throw new IOException("홍보글 생성이 취소되었습니다.");
        }
        return animalFileList;
    }

    public Promotion findPromotionByNo(int no) {
        if(proRepo.findById(no).isEmpty()){
            throw new NullPointerException("조회 결과 없음");
        }
        return proRepo.findById(no).get();
    }

    public List<Promotion> findAllPromotion() {
        List<Promotion> proList = proRepo.findAll();
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

    public Promotion deletePromotion(int no, List<Log> deleteLogList, List<AnimalFile> deleteAnimalFileList) {
        Promotion pro = proRepo.findById(no).get();
        try {
            proRepo.deleteById(no);
        } catch (Exception ex){
            logRepo.saveAll(deleteLogList);
            fileRepo.saveAll(deleteAnimalFileList);
            throw new RuntimeException("홍보글 삭제가 취소되었습니다.");
        }

        return pro;
    }

    public List<AnimalFile> findAllAnimalFIleByPromotionNo(int no) {
        return fileRepo.findAllByPromotionNo(no);
    }

    public List<AnimalFile> deleteAnimalFIleListByPromotionNo(int no) {
        List<AnimalFile> deleteAnimalFileList = fileRepo.findAllByPromotionNo(no);
        fileRepo.deleteAll(deleteAnimalFileList);

        return deleteAnimalFileList;
    }
}
