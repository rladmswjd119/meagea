package project.service;

import entity.Animal;
import entity.AnimalFile;
import entity.Log;
import entity.Promotion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.dto.PromotionDetailDto;
import project.dto.PromotionForm;
import project.dto.PromotionModifyDto;
import project.dto.SimplePromotionDto;
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

    public PromotionDetailDto savePromotion(PromotionForm form) throws IOException {
        if(animalRepo.findById(form.getAnimalNo()).isEmpty()) {
            throw new NullPointerException("조회 결과 없음");
        }
        Animal animal = animalRepo.findById(form.getAnimalNo()).get();
        Promotion pro = proRepo.save(new Promotion(form.getTitle(), animal.getNo(), form.getIntroduction(), form.getCondition()));
        AnimalFileManager fileMan = new AnimalFileManager();
        List<AnimalFile> imageList = new ArrayList<>();
        for(MultipartFile m : form.getImageList()) {
            AnimalFile animalFile = new AnimalFile(pro.getNo(), m.getOriginalFilename(), fileMan.serverFile(m), "promotion");
            fileRepo.save(animalFile);
            imageList.add(animalFile);
        }

        return new PromotionDetailDto(pro, animal, imageList);
    }

    public PromotionDetailDto findByNo(int no) {
        if(proRepo.findById(no).isEmpty()){
            throw new NullPointerException("조회 결과 없음");
        }
        Promotion pro = proRepo.findById(no).get();
        Optional<Animal> animal = animalRepo.findById(pro.getAnimalNo());
        List<AnimalFile> imageList = fileRepo.findAllByPromotionNo(pro.getNo());

        return new PromotionDetailDto(pro, animal.get(), imageList);
    }

    public List<SimplePromotionDto> findAllSimple() {
        List<Promotion> proList = proRepo.findAll();
        if(proList.isEmpty()) {
            throw new NullPointerException("조회 결과 없음");
        }
        
        List<SimplePromotionDto> dtoList = new ArrayList<>();
        for(Promotion pro : proList){
            Optional<Animal> optAnimal = animalRepo.findById(pro.getAnimalNo());
            Animal animal = optAnimal.get();
            SimplePromotionDto dto = new SimplePromotionDto(pro.getNo(), animal.getNo(), pro.getTitle(), animal.getName(),
                                                            animal.getGender(), animal.getAge(), animal.getKind());
            dtoList.add(dto);
        }

        return dtoList;
    }

    public PromotionDetailDto updatePromotion(PromotionModifyDto modifyDto) {
        Optional<Promotion> optPro = proRepo.findById(modifyDto.getNo());
        if(optPro.isEmpty()) {
            throw new NullPointerException("수정 가능한 Promotion 객체가 존재하지 않습니다.");
        }
        Promotion pro = optPro.get();
        pro.setTitle(modifyDto.getTitle());
        pro.setIntroduction(modifyDto.getIntroduction());
        pro.setTerms(modifyDto.getTerms());
        pro.setModifyDate(LocalDateTime.now());
        proRepo.save(pro);

        Animal animal = animalRepo.findById(pro.getAnimalNo()).get();
        List<AnimalFile> imageList = fileRepo.findAllByPromotionNo(pro.getNo());

        return new PromotionDetailDto(pro, animal, imageList);
    }

    public void deletePromotion(int no) {
        List<AnimalFile> imageList = fileRepo.findAllByPromotionNo(no);
        fileRepo.deleteAll(imageList);

        List<Log> logList = logRepo.findAllByPromotionNo(no);
        if(!logList.isEmpty()){
            logRepo.deleteAll(logList);
        }

        proRepo.deleteById(no);
    }
}
