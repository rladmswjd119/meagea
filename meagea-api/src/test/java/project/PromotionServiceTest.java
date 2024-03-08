package project;


import entity.Animal;
import entity.Promotion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import project.dto.PromotionForm;
import project.dto.PromotionModifyDto;
import project.dto.SimplePromotionDto;
import project.repository.AnimalFileRepository;
import project.repository.AnimalRepository;
import project.repository.PromotionRepository;
import project.service.PromotionService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @InjectMocks
    private PromotionService service;

    @Test
    public void savePromotionSuccessTest() throws IOException {
        ArgumentCaptor<Promotion> proCaptor = ArgumentCaptor.forClass(Promotion.class);

        Animal animal = new Animal("머핀", 5, "암컷", 3.5, true, "친칠라", "믹스",
                                    "동네", 2, 1, 2, 1);
        given(animalRepo.findById(anyInt())).willReturn(Optional.of(animal));
        List<MultipartFile> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            File file = new File("src\\main\\java\\project\\image\\" + "file" + i + ".jpg");
            MockMultipartFile mul = new MockMultipartFile("file" + i, new FileInputStream(file));
            list.add(mul);
        }
        PromotionForm form = new PromotionForm("제목", list, animal.getNo(), "내용", "내용2");
        Promotion pro = new Promotion("제목", 5, "내용", "내용2");
        given(proRepo.save(proCaptor.capture())).willReturn(pro);

        service.savePromotion(form);
        Promotion cap = proCaptor.getValue();

        verify(proRepo).save(proCaptor.capture());
        verify(animalRepo, times(2)).findById(anyInt());
        verify(fileRepo, times(4)).save(any());
        assertThat(form.getTitle()).isEqualTo(cap.getTitle());
    }

    @Test
    public void savePromotionFailTest() throws IOException {
        List<MultipartFile> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            File file = new File("src\\main\\java\\project\\image\\" + "file" + i + ".jpg");
            MockMultipartFile mul = new MockMultipartFile("file" + i, new FileInputStream(file));
            list.add(mul);
        }
        PromotionForm form = new PromotionForm("제목", list, 5, "내용", "내용2");
        Throwable ex = Assertions.assertThrows(Exception.class, () -> service.savePromotion(form));

        assertThat(ex.getMessage()).isEqualTo("조회 결과 없음");
    }

    @Test
    public void findByNoSuccessTest() {
        Promotion pro = new Promotion("제목", 5, "내용", "내용2");
        given(proRepo.findById(eq(10))).willReturn(Optional.of(pro));

        int result = service.findByNo(10).getAnimal().getNo();
        assertThat(result).isEqualTo(5);
    }

    @Test
    public void findByNoFailTest() throws NullPointerException {
        Throwable ex = Assertions.assertThrows(Exception.class, () -> service.findByNo(10));

        assertThat(ex.getMessage()).isEqualTo("조회 결과 없음");
    }

    @Test
    public void findAllSimpleSuccessTest() {
        List<Promotion> proList = new ArrayList<>();
        Animal animal = new Animal("머핀", 5, "암컷", 3.5, true, "친칠라", "잡종",
                "동네", 2, 1, 2, 1);
        for(int i = 0; i < 4; i++) {
            proList.add(new Promotion("제목", i, "내용", "내용2"));
            given(animalRepo.findById(eq(i))).willReturn(Optional.of(animal));
        }
        given(proRepo.findAll()).willReturn(proList);

        List<SimplePromotionDto> result = service.findAllSimple();

        for(SimplePromotionDto dto : result) {
            assertThat(dto.getTitle()).isEqualTo("제목");
        }
    }

    @Test
    public void findAllSimpleFailTest() {
        List<Promotion> proList = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            proList.add(new Promotion("제목", i, "내용", "내용2"));
        }

        Throwable ex = Assertions.assertThrows(NullPointerException.class, () -> service.findAllSimple());
        assertThat(ex.getMessage()).isEqualTo("조회 결과 없음");
    }

    @Test
    public void updatePromotionSuccessTest() throws IOException {
        List<MultipartFile> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            File file = new File("src\\main\\java\\project\\image\\" + "file" + i + ".jpg");
            MockMultipartFile mul = new MockMultipartFile("file" + i, new FileInputStream(file));
            list.add(mul);
        }
        PromotionModifyDto dto = new PromotionModifyDto(1, "수정된 제목", list, "수정된 설명", "수정된 조건");
        Promotion pro = new Promotion("제목", 5, "내용", "내용2");
        given(proRepo.findById(dto.getNo())).willReturn(Optional.of(pro));

        Promotion result = service.updatePromotion(dto).getPromotion();

        verify(proRepo, times(1)).findById(1);
        assertThat(result.getTitle()).isEqualTo(dto.getTitle());
    }

    @Test
    public void updatePromotionFailTest() throws IOException {
        List<MultipartFile> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            File file = new File("src\\main\\java\\project\\image\\" + "file" + i + ".jpg");
            MockMultipartFile mul = new MockMultipartFile("file" + i, new FileInputStream(file));
            list.add(mul);
        }
        PromotionModifyDto dto = new PromotionModifyDto(1, "수정된 제목", list, "수정된 설명", "수정된 조건");

        Throwable ex = Assertions.assertThrows(NullPointerException.class, () -> service.updatePromotion(dto));
        assertThat(ex.getMessage()).isEqualTo("수정 가능한 Promotion 객체가 존재하지 않습니다.");
    }
}
