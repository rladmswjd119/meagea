package project.service;

import entity.Animal;
import entity.AnimalFile;
import entity.Log;
import entity.Promotion;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.dto.PromotionForm;
import project.dto.PromotionModifyForm;
import project.eunm.FileType;
import project.repository.AnimalFileRepository;
import project.repository.AnimalRepository;
import project.repository.LogRepository;
import project.repository.PromotionRepository;
import project.unit.AnimalFileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository proRepo;
    private final AnimalRepository animalRepo;
    private final AnimalFileRepository fileRepo;
    private final LogRepository logRepo;
    private final ResourceLoader resourceLoader;
    private final LogService logService;

    public Promotion savePromotion(PromotionForm form) {
        Optional<Animal> animal = animalRepo.findById(form.getAnimalNo());
        if (animal.isEmpty()) {
            throw new NullPointerException("조회 결과 없음");
        }

        return proRepo.save(new Promotion(form.getTitle(), animal.get().getNo(), form.getIntroduction(), form.getCondition()));
    }

    public List<CompletableFuture<AnimalFile>> saveAnimalFile(int proNo, List<MultipartFile> imageList, int logNo, FileType fileType) throws IOException {
        AnimalFileManager fileMan = new AnimalFileManager();
        List<CompletableFuture<AnimalFile>> futureAnimalFileList = new ArrayList<>();
        try {
            if (imageList.size() > 10) {
                throw new IOException("이미지 파일은 최대 4개까지 첨부가 가능합니다.");
            }

            // 비동기
            for (int i = 0; i < imageList.size(); i++) {
                int num = i;
                futureAnimalFileList.add(CompletableFuture.supplyAsync(() -> {
                    try {
                        return saveAnimalFileAsync(imageList, proNo, fileMan, num, logNo, fileType);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }));
            }

        } catch (RuntimeException ex) {
            fileRepo.deleteByPromotionNo(proNo);
            proRepo.deleteById(proNo);
            throw new RuntimeException("홍보글 생성이 취소되었습니다.");
        }
        return futureAnimalFileList;
    }

    // 비동기 메서드
    public AnimalFile saveAnimalFileAsync(List<MultipartFile> imageList, int proNo, AnimalFileManager fileMan,
                                          int i, int logNo, FileType fileType) throws IOException {
        AnimalFile animalFile;
        try {
            MultipartFile m = imageList.get(i);
            animalFile = new AnimalFile(proNo, m.getOriginalFilename(), fileMan.serverFile(m), fileType.name(), logNo);
            fileRepo.save(animalFile);
        } catch (IOException e) {
            throw new IOException("비동기 메서드 예외");
        }

        return animalFile;
    }

    public CompletableFuture<List<AnimalFile>> turnAnimalList(List<CompletableFuture<AnimalFile>> futureList){
        CompletableFuture<?>[] futureArray = futureList.toArray(new CompletableFuture<?>[0]);

        return CompletableFuture.allOf(futureArray)
                .thenApply(i -> futureList.stream().map(CompletableFuture::join).collect(Collectors.toList()));
    }

    public Promotion findPromotionByNo(int no) {
        Optional<Promotion> pro = proRepo.findById(no);
        if (pro.isEmpty()) {
            throw new NullPointerException("조회 결과 없음");
        }
        return pro.get();
    }

    public List<Promotion> findAllPromotion() {
        List<Promotion> proList = proRepo.findByRemove(0);
        if (proList.isEmpty()) {
            throw new NullPointerException("조회 결과 없음");
        }

        return proList;
    }

    public Promotion updatePromotion(PromotionModifyForm modifyDto) {
        Optional<Promotion> optPro = proRepo.findById(modifyDto.getNo());
        if (optPro.isEmpty()) {
            throw new NullPointerException("수정 가능한 Promotion 데이터가 존재하지 않습니다.");
        }
        Promotion pro = optPro.get();
        pro.modifyPromotion(modifyDto.getTitle(), modifyDto.getIntroduction(), modifyDto.getTerms());
        proRepo.save(pro);

        return pro;
    }

    public List<AnimalFile> findAllAnimalFileByPromotionNo(int no) {
        return fileRepo.findAllByPromotionNoAndProperty(no, "PRO");
    }

    public void deleteAll() {
        fileRepo.deleteAllInBatch();
        logRepo.deleteAllInBatch();
        proRepo.deleteAllInBatch();
    }
}
