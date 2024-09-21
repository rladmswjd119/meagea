package project.service;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import project.unit.AnimalFileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnimalFileManagerTest {

    @Test
    public void 유기동물_이미지_관리() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        AnimalFileManager fileMan = new AnimalFileManager();

        for (int i = 0; i < 4; i++) {
            File file = new ClassPathResource("file" + i + ".jpg").getFile();
            FileInputStream input = new FileInputStream(file);
            MockMultipartFile mock = new MockMultipartFile("file" + i, "file" + i + ".jpg", "jpg", input);
            fileMan.serverFile(mock);
        }
    }
}
