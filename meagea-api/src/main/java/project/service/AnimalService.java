package project.service;

import entity.Animal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.dto.AnimalDto;
import project.dto.AnimalForm;
import project.repository.AnimalRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepo;

    public Animal addAnimal(AnimalForm form){
        Animal animal = new Animal(form.getName(), form.getAge(), form.getGender(), form.getWeight(),
                                    form.isNeuter(), form.getKind(), form.getDetail(),form.getPlace(), form.getHealthState(),
                                    form.getActivity(), form.getSociality(), form.getFriendly());

        return animalRepo.save(animal);
    }

    public Animal findAnimalByNo(int no){
        Optional<Animal> animal = animalRepo.findById(no);
        if(animal.isEmpty()){
            throw new NullPointerException("조회 결과 없음");
        }

        return animal.get();
    }

    public void deleteAll() {
        animalRepo.deleteAllInBatch();
    }

    public List<Animal> getAllAnimal() {
        return animalRepo.findAll();
    }

    public List<CompletableFuture<AnimalDto>> changeListCompletAnimalDtoList(List<Animal> animalList) {
        List<CompletableFuture<AnimalDto>> animalCompletDtoList = new ArrayList<>();
        for(Animal animal : animalList){
            animalCompletDtoList.add(CompletableFuture.supplyAsync(() -> asyncChangeAnimalDto(animal)));
        }

        return animalCompletDtoList;
    }

    private AnimalDto asyncChangeAnimalDto(Animal animal) {
        return  new AnimalDto(animal.getNo(), animal.getName(), animal.getAge(), animal.getGender(), animal.getWeight()
                                            , animal.isNeuter(), animal.getKind(), animal.getDetail(), animal.getPlace(), animal.getHealthState()
                                            , animal.getActivity(), animal.getSociality(), animal.getFriendly(), animal.isAdoptionState());
    }

    public CompletableFuture<List<AnimalDto>> changeCompletListAnimalDto(List<CompletableFuture<AnimalDto>> listCompletAnimalDto) {
        CompletableFuture<?>[] completArray = listCompletAnimalDto.toArray(new CompletableFuture<?>[0]);
        return CompletableFuture.allOf(completArray).thenApply(i -> listCompletAnimalDto.stream().map(CompletableFuture::join).collect(Collectors.toList()));
    }
}
