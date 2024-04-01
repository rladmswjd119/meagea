package project;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import project.unit.AnimalFileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AnimalFileManagerTest {

    @Test
    public void 유기동물_이미지_관리() throws IOException {
        AnimalFileManager fileMan = new AnimalFileManager();

        for (int i = 0; i < 4; i++) {
            File file = new File("src\\main\\java\\project\\image\\" + "file" + i + ".jpg");
            FileInputStream input = new FileInputStream(file);
            MockMultipartFile mock = new MockMultipartFile("file" + i, "file" + i + ".jpg", "jpg", input);
            fileMan.serverFile(mock);
        }
    }
}
