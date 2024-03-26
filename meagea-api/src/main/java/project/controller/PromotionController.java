package project.controller;

import entity.Animal;
import entity.AnimalFile;
import entity.Log;
import entity.Promotion;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.dto.PromotionDetailDto;
import project.dto.PromotionModifyForm;
import project.service.AnimalService;
import project.service.LogService;
import project.service.PromotionService;
import project.dto.PromotionForm;
import project.dto.SimplePromotionDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@RestController
@RequiredArgsConstructor
@RequestMapping("/meagea")
public class PromotionController {

    private final PromotionService proService;
    private final AnimalService animalService;
    private final LogService logService;

    @PostMapping("/promotion")
    public PromotionDetailDto addPromotion(@ModelAttribute PromotionForm form) throws Exception {
        Promotion pro = proService.savePromotion(form);
        Animal animal = animalService.findAnimalByNo(form.getAnimalNo());
        List<AnimalFile> animalFileList = proService.saveAnimalFile(pro.getNo(), form.getImageList());

        return new PromotionDetailDto(pro.getNo(), pro.getTitle(), pro.getAnimalNo(), pro.getIntroduction(),
                pro.getTerms(), pro.getMakeDate(), pro.getModifyDate(),
                animal.getName(), animal.getAge(), animal.getGender(), animal.getWeight(), animal.isNeuter(),
                animal.getKind(), animal.getDetail(), animal.getPlace(), animal.getHealthState(),
                animal.getActivity(), animal.getSociality(), animal.getFriendly(), animal.isAdoptionState(),
                animalFileList);
    }

    @GetMapping("/promotion/{no}")
    public PromotionDetailDto getPromotion(@PathVariable int no) {
        Promotion pro = proService.findPromotionByNo(no);
        Animal animal = animalService.findAnimalByNo(pro.getAnimalNo());
        List<AnimalFile> animalFileList = proService.findAllAnimalFIleByPromotionNo(no);

        return new PromotionDetailDto(pro.getNo(), pro.getTitle(), pro.getAnimalNo(), pro.getIntroduction(),
                pro.getTerms(), pro.getMakeDate(), pro.getModifyDate(),
                animal.getName(), animal.getAge(), animal.getGender(), animal.getWeight(), animal.isNeuter(),
                animal.getKind(), animal.getDetail(), animal.getPlace(), animal.getHealthState(),
                animal.getActivity(), animal.getSociality(), animal.getFriendly(), animal.isAdoptionState(),
                animalFileList);
    }

    @GetMapping("/all-promotion-title")
    public List<SimplePromotionDto> getAllPromotionTitle() {
        List<Promotion> proList = proService.findAllPromotion();
        List<SimplePromotionDto> simpleList = new ArrayList<>();
        for(Promotion pro : proList){
            Animal animal = animalService.findAnimalByNo(pro.getAnimalNo());
            SimplePromotionDto dto = new SimplePromotionDto(pro.getNo(), animal.getNo(), pro.getTitle(), animal.getName(),
                                                            animal.getGender(), animal.getAge(), animal.getKind());
            simpleList.add(dto);
        }

        return simpleList;
    }

    @PatchMapping("/promotion")
    public PromotionDetailDto modifyPromotion(@ModelAttribute PromotionModifyForm modifyDto) {
        Promotion modifyPro = proService.updatePromotion(modifyDto);
        Animal animal = animalService.findAnimalByNo(modifyPro.getAnimalNo());
        List<AnimalFile> animalFileList = proService.findAllAnimalFIleByPromotionNo(modifyPro.getNo());

        return new PromotionDetailDto(modifyPro.getNo(), modifyPro.getTitle(), modifyPro.getAnimalNo(),
                modifyPro.getIntroduction(), modifyPro.getTerms(), modifyPro.getMakeDate(), modifyPro.getModifyDate(),
                animal.getName(), animal.getAge(), animal.getGender(), animal.getWeight(), animal.isNeuter(),
                animal.getKind(), animal.getDetail(), animal.getPlace(), animal.getHealthState(),
                animal.getActivity(), animal.getSociality(), animal.getFriendly(), animal.isAdoptionState(),
                animalFileList);
    }

    @DeleteMapping("/promotion/{no}")
    public PromotionDetailDto deletePromotion(@PathVariable int no) {
        List<AnimalFile> deleteAnimalFileList = proService.deleteAnimalFIleListByPromotionNo(no);
        logService.deletAllLogByPromotionNo(no);
        Promotion deletePro = proService.deletePromotion(no);
        Animal animal = animalService.findAnimalByNo(deletePro.getAnimalNo());

        return new PromotionDetailDto(deletePro.getNo(), deletePro.getTitle(), deletePro.getAnimalNo(),
                deletePro.getIntroduction(), deletePro.getTerms(), deletePro.getMakeDate(), deletePro.getModifyDate(),
                animal.getName(), animal.getAge(), animal.getGender(), animal.getWeight(), animal.isNeuter(),
                animal.getKind(), animal.getDetail(), animal.getPlace(), animal.getHealthState(),
                animal.getActivity(), animal.getSociality(), animal.getFriendly(), animal.isAdoptionState(),
                deleteAnimalFileList);

    }
}