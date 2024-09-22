package project.controller;

import entity.Animal;
import entity.AnimalFile;
import entity.Log;
import entity.Promotion;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.*;
import project.eunm.FileType;
import project.service.AnimalService;
import project.service.LogService;
import project.service.PromotionService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequiredArgsConstructor
@RequestMapping("/meagea/promotions")
public class PromotionController {

    private final PromotionService proService;
    private final AnimalService animalService;
    private final LogService logService;

    @PostMapping("/")
    public ResponseEntity<PromotionDetailDto> addPromotion(@ModelAttribute PromotionForm form) throws Exception {
        Promotion pro = proService.savePromotion(form);
        Animal animal = animalService.findAnimalByNo(form.getAnimalNo());

        List<CompletableFuture<AnimalFile>> futureAnimalFileList = proService.saveAnimalFile(pro.getNo(), form.getImageList(), -1, FileType.PRO);
        CompletableFuture<List<AnimalFile>> animalListFuture = proService.turnAnimalList(futureAnimalFileList);

        PromotionDetailDto promotionDetailDto = new PromotionDetailDto(pro.getNo(), pro.getTitle(), pro.getAnimalNo(), pro.getIntroduction(), pro.getTerms(),
                                                                        pro.getMakeDate(), pro.getModifyDate(), animal.getName(), animal.getAge(), animal.getGender(),
                                                                        animal.getWeight(), animal.isNeuter(), animal.getKind(), animal.getPlace(),
                                                                        animal.getHealthState(), animal.getActivity(), animal.getSociality(), animal.getFriendly(),
                                                                        animal.isAdoptionState(), animalListFuture.get());

        return ResponseEntity.ok().cacheControl(CacheControl.noCache()).body(promotionDetailDto);
    }

    @GetMapping("/{no}")
    public PromotionDetailDto getPromotion(@PathVariable int no) {
        Promotion pro = proService.findPromotionByNo(no);
        Animal animal = animalService.findAnimalByNo(pro.getAnimalNo());
        List<AnimalFile> animalFileList = proService.findAllAnimalFileByPromotionNo(no);

        List<Log> logList = logService.getAllLogByPromotionNo(pro.getNo());
        List<LogTotalDto> logTotalDtoList = logService.changeComListLogDto(logService.changeListComLogDto(logList)).join();

        return new PromotionDetailDto(pro.getNo(), pro.getTitle(), pro.getAnimalNo(), pro.getIntroduction(),
                pro.getTerms(), pro.getMakeDate(), pro.getModifyDate(),
                animal.getName(), animal.getAge(), animal.getGender(), animal.getWeight(), animal.isNeuter(),
                animal.getKind(), animal.getPlace(), animal.getHealthState(),
                animal.getActivity(), animal.getSociality(), animal.getFriendly(), animal.isAdoptionState(),
                animalFileList, logTotalDtoList);
    }

    @GetMapping("/")
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

    @PatchMapping("/")
    public PromotionDetailDto modifyPromotion(@ModelAttribute PromotionModifyForm modifyDto) {
        Promotion modifyPro = proService.updatePromotion(modifyDto);
        Animal animal = animalService.findAnimalByNo(modifyPro.getAnimalNo());
        List<AnimalFile> animalFileList = proService.findAllAnimalFileByPromotionNo(modifyPro.getNo());

        return new PromotionDetailDto(modifyPro.getNo(), modifyPro.getTitle(), modifyPro.getAnimalNo(),
                modifyPro.getIntroduction(), modifyPro.getTerms(), modifyPro.getMakeDate(), modifyPro.getModifyDate(),
                animal.getName(), animal.getAge(), animal.getGender(), animal.getWeight(), animal.isNeuter(),
                animal.getKind(), animal.getPlace(), animal.getHealthState(),
                animal.getActivity(), animal.getSociality(), animal.getFriendly(), animal.isAdoptionState(),
                animalFileList);
    }

    @DeleteMapping("/")
    public void deleteAll(){
        proService.deleteAll();
    }
}
