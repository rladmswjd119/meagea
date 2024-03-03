package project.service;

import entity.Animal;
import entity.AnimalFile;
import entity.Promotion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.dto.PromotionForm;
import project.dto.SimplePromotionDto;
import project.repository.AnimalFileRepository;
import project.repository.AnimalRepository;
import project.repository.PromotionRepository;
import project.unit.AnimalFileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository proRepo;
    private final AnimalRepository animalRepo;
    private final AnimalFileRepository fileRepo;

    public Promotion savePromotion(PromotionForm form) throws IOException {
        if(animalRepo.findById(form.getAnimalNo()).isEmpty()) {
            throw new NullPointerException("조회 결과 없음");
        }
        Animal animal = animalRepo.findById(form.getAnimalNo()).get();
        Promotion pro = proRepo.save(new Promotion(form.getTitle(), animal.getNo(), form.getIntroduction(), form.getCondition()));
        AnimalFileManager fileMan = new AnimalFileManager();
        for(MultipartFile m : form.getImageList()) {
            AnimalFile animalFile = new AnimalFile(pro.getNo(), m.getOriginalFilename(), fileMan.serverFile(m), "promotion");
            fileRepo.save(animalFile);
        }

        return pro;
    }

    public Promotion findByNo(int no){
        if(proRepo.findById(no).isEmpty()){
            throw new NullPointerException("조회 결과 없음");
        }

        return proRepo.findById(no).get();
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

}
