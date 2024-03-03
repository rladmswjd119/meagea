package project.controller;

import entity.Animal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.dto.AnimalForm;
import project.service.AnimalService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meagea")
public class AnimalController {
    private final AnimalService animalService;

    @PostMapping("/animal")
    public Animal addAnimal(@ModelAttribute AnimalForm form){
        return animalService.addAnimal(form);
    }

    @GetMapping("/animal/{no}")
    public Animal getAnimal(@PathVariable int no) {
        return animalService.findAnimalByNo(no);
    }
}
