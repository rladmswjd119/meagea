package project.controller;

import entity.Animal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.dto.AnimalDto;
import project.dto.AnimalForm;
import project.service.AnimalService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meagea")
public class
AnimalController {
    private final AnimalService animalService;

    @PostMapping("/animal")
    public AnimalDto addAnimal(@ModelAttribute AnimalForm form){
        Animal animal = animalService.addAnimal(form);
        return new AnimalDto(animal.getName(), animal.getAge(), animal.getGender(), animal.getWeight(),
                animal.isNeuter(), animal.getKind(), animal.getDetail(), animal.getPlace(), animal.getHealthState(),
                animal.getActivity(), animal.getSociality(), animal.getFriendly(), animal.isAdoptionState());
    }

    @GetMapping("/animal/{no}")
    public AnimalDto getAnimal(@PathVariable int no) {
        Animal animal = animalService.findAnimalByNo(no);
        return new AnimalDto(animal.getName(), animal.getAge(), animal.getGender(), animal.getWeight(),
                animal.isNeuter(), animal.getKind(), animal.getDetail(), animal.getPlace(), animal.getHealthState(),
                animal.getActivity(), animal.getSociality(), animal.getFriendly(), animal.isAdoptionState());
    }
}
