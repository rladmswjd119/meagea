package project;


import entity.Animal;
import entity.AnimalFile;
import entity.Promotion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import project.async.AsyncMethod;
import project.dto.PromotionForm;
import project.dto.PromotionModifyForm;
import project.repository.AnimalFileRepository;
import project.repository.AnimalRepository;
import project.repository.PromotionRepository;
import project.service.PromotionService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PromotionServiceTest {

    @Mock
    private PromotionRepository proRepo;
    @Mock
    private AnimalRepository animalRepo;
    @Mock
    private AnimalFileRepository fileRepo;
    @Mock
    private AsyncMethod asyncMethod;
    @InjectMocks
    private PromotionService service;

    @Test
    public void savePromotionSuccessTest() {
        ArgumentCaptor<Promotion> proCaptor = ArgumentCaptor.forClass(Promotion.class);

        Animal animal = new Animal("머핀", 5, "암컷", 3.5, true, "친칠라", "믹스",
                                    "동네", 2, 1, 2, 1);
        given(animalRepo.findById(anyInt())).willReturn(Optional.of(animal));
        List<MultipartFile> list = new ArrayList<>();
        PromotionForm form = new PromotionForm("제목", list, animal.getNo(), "내용", "내용2");
        Promotion pro = new Promotion("제목", 5, "내용", "내용2");
        given(proRepo.save(proCaptor.capture())).willReturn(pro);

        service.savePromotion(form);
        Promotion cap = proCaptor.getValue();

        verify(proRepo).save(proCaptor.capture());
        verify(animalRepo, times(1)).findById(anyInt());
        assertThat(form.getTitle()).isEqualTo(cap.getTitle());
    }

    @Test
    public void savePromotionFailTest() {
        List<MultipartFile> list = new ArrayList<>();
        PromotionForm form = new PromotionForm("제목", list, 5, "내용", "내용2");
        Throwable ex = Assertions.assertThrows(NullPointerException.class, () -> service.savePromotion(form));

        assertThat(ex.getMessage()).isEqualTo("조회 결과 없음");
    }

    @Test
    public void findByNoSuccessTest() {
        Promotion pro = new Promotion("제목", 5, "내용", "내용2");
        given(proRepo.findById(eq(10))).willReturn(Optional.of(pro));

        int result = service.findPromotionByNo(10).getAnimalNo();
        assertThat(result).isEqualTo(5);
    }

    @Test
    public void findByNoFailTest() throws NullPointerException {
        Throwable ex = Assertions.assertThrows(Exception.class, () -> service.findPromotionByNo(10));

        assertThat(ex.getMessage()).isEqualTo("조회 결과 없음");
    }

    @Test
    public void findAllPromotionSuccessTest() {
        List<Promotion> proList = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            proList.add(new Promotion("제목", i, "내용", "내용2"));
        }
        given(proRepo.findByRemove(0)).willReturn(proList);

        List<Promotion> result = service.findAllPromotion();

        for(Promotion pro : result) {
            assertThat(pro.getTitle()).isEqualTo("제목");
        }
    }

    @Test
    public void findAllPromotionFailTest() {
        List<Promotion> proList = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            proList.add(new Promotion("제목", i, "내용", "내용2"));
        }

        Throwable ex = Assertions.assertThrows(NullPointerException.class, () -> service.findAllPromotion());
        assertThat(ex.getMessage()).isEqualTo("조회 결과 없음");
    }

    @Test
    public void updatePromotionSuccessTest() throws IOException {
        List<MultipartFile> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            File file = new File("/Users/gim-eunjeong/IdeaProjects/meagea/meagea-api/src/main/java/project/image/"
                    + "file" + i + ".jpg");
            MockMultipartFile mul = new MockMultipartFile("file" + i, new FileInputStream(file));
            list.add(mul);
        }
        PromotionModifyForm dto = new PromotionModifyForm(1, "수정된 제목", list, "수정된 설명", "수정된 조건");
        Promotion pro = new Promotion("제목", 5, "내용", "내용2");
        given(proRepo.findById(dto.getNo())).willReturn(Optional.of(pro));

        Promotion result = service.updatePromotion(dto);

        verify(proRepo, times(1)).findById(1);
        assertThat(result.getTitle()).isEqualTo(dto.getTitle());
    }

    @Test
    public void updatePromotionFailTest() throws IOException {
        List<MultipartFile> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            File file = new File("/Users/gim-eunjeong/IdeaProjects/meagea/meagea-api/src/main/java/project/image/"
                    + "file" + i + ".jpg");
            MockMultipartFile mul = new MockMultipartFile("file" + i, new FileInputStream(file));
            list.add(mul);
        }
        PromotionModifyForm dto = new PromotionModifyForm(1, "수정된 제목", list, "수정된 설명", "수정된 조건");

        Throwable ex = Assertions.assertThrows(NullPointerException.class, () -> service.updatePromotion(dto));
        assertThat(ex.getMessage()).isEqualTo("수정 가능한 Promotion 데이터가 존재하지 않습니다.");
    }

    @Test
    public void saveImageFileTest() throws IOException, ExecutionException, InterruptedException {
        List<MultipartFile> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            FileInputStream input = new FileInputStream("/Users/gim-eunjeong/IdeaProjects/meagea/meagea-api/src/main/java/project/image/file" + i + ".jpg");
            MultipartFile m = new MockMultipartFile("file" + i, "file" + i + ".jpg", "jpg", input);
            list.add(m);
        }

        List<AnimalFile> fileList = service.saveAnimalFile(0, list);
        int num = 0;
        for(AnimalFile file : fileList){
            assertThat(file.getPromotionNo()).isEqualTo(num);
            num++;
        }
    }
}
