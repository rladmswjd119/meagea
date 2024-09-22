package project.controller;

import entity.Animal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import project.dto.AnimalDto;
import project.dto.AnimalForm;
import project.service.AnimalService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/meagea/animals")
public class
AnimalController {
    private final AnimalService animalService;

    @PostMapping("/")
    public AnimalDto addAnimal(@RequestBody AnimalForm form){
        log.info("form : {}", form.getName());

        Animal animal = animalService.addAnimal(form);
        return new AnimalDto(animal.getNo(), animal.getName(), animal.getAge(), animal.getGender(), animal.getWeight(),
                animal.isNeuter(), animal.getKind(), animal.getPlace(), animal.getHealthState(),
                animal.getActivity(), animal.getSociality(), animal.getFriendly(), animal.isAdoptionState());
    }

    @GetMapping("/{no}")
    public AnimalDto getAnimal(@PathVariable int no) {
        Animal animal = animalService.findAnimalByNo(no);
        return new AnimalDto(animal.getNo(), animal.getName(), animal.getAge(), animal.getGender(), animal.getWeight(),
                animal.isNeuter(), animal.getKind(), animal.getPlace(), animal.getHealthState(),
                animal.getActivity(), animal.getSociality(), animal.getFriendly(), animal.isAdoptionState());
    }

    @DeleteMapping("/")
    public void deleteAllAnimal() {
        animalService.deleteAll();
    }

    @GetMapping("/")
    public List<AnimalDto> getAllAnimalDto() throws ExecutionException, InterruptedException {
        List<Animal> animalList = animalService.getAllAnimal();
        CompletableFuture<List<AnimalDto>> listCompletableFuture = animalService.changeCompletListAnimalDto(animalService.changeListCompletAnimalDtoList(animalList));

        return listCompletableFuture.get();
    }
}
