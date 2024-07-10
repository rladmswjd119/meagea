package project.controller;

import entity.Animal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.dto.AnimalDto;
import project.dto.AnimalForm;
import project.service.AnimalService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meagea")
public class
AnimalController {
    private final AnimalService animalService;

    @PostMapping("/animal")
    public AnimalDto addAnimal(@ModelAttribute AnimalForm form){
        Animal animal = animalService.addAnimal(form);
        return new AnimalDto(animal.getNo(), animal.getName(), animal.getAge(), animal.getGender(), animal.getWeight(),
                animal.isNeuter(), animal.getKind(), animal.getDetail(), animal.getPlace(), animal.getHealthState(),
                animal.getActivity(), animal.getSociality(), animal.getFriendly(), animal.isAdoptionState());
    }

    @GetMapping("/animal/{no}")
    public AnimalDto getAnimal(@PathVariable int no) {
        Animal animal = animalService.findAnimalByNo(no);
        return new AnimalDto(animal.getNo(), animal.getName(), animal.getAge(), animal.getGender(), animal.getWeight(),
                animal.isNeuter(), animal.getKind(), animal.getDetail(), animal.getPlace(), animal.getHealthState(),
                animal.getActivity(), animal.getSociality(), animal.getFriendly(), animal.isAdoptionState());
    }

    @DeleteMapping("/animal")
    public void deleteAllAnimal() {
        animalService.deleteAll();
    }

    @GetMapping("/all-animal")
    public List<AnimalDto> getAllAnimalDto() throws ExecutionException, InterruptedException {
        List<Animal> animalList = animalService.getAllAnimal();
        CompletableFuture<List<AnimalDto>> listCompletableFuture = animalService.changeCompletListAnimalDto(animalService.changeListCompletAnimalDtoList(animalList));

        return listCompletableFuture.get();
    }
}
