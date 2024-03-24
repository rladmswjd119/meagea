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

@Component
@RequiredArgsConstructor
public class AsyncMethod {

    private final AnimalFileRepository fileRepo;

    @Async("fileThread")
    public void saveAnimalFileAsync(List<MultipartFile> imageList, int proNo, AnimalFileManager fileMan, int i) throws IOException {
        MultipartFile m = imageList.get(i);
        System.out.println(m.getOriginalFilename());
        AnimalFile animalFile = new AnimalFile(proNo, m.getOriginalFilename(), fileMan.serverFile(m), "promotion");
        fileRepo.save(animalFile);
    }
}
