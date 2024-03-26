package project.async;

import entity.AnimalFile;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import project.repository.AnimalFileRepository;
import project.unit.AnimalFileManager;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class AsyncMethod {

    private final AnimalFileRepository fileRepo;

    @Async("fileThread")
    public CompletableFuture<AnimalFile> saveAnimalFileAsync(List<MultipartFile> imageList, int proNo, AnimalFileManager fileMan, int i) {
        AnimalFile animalFile;
        try {
            MultipartFile m = imageList.get(i);
            System.out.println(m.getOriginalFilename());
            animalFile = new AnimalFile(proNo, m.getOriginalFilename(), fileMan.serverFile(m), "promotion");
            fileRepo.save(animalFile);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return CompletableFuture.completedFuture(animalFile);
    }
}
