package project;


import entity.Animal;
import entity.Promotion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.*;
import org.springframework.web.multipart.MultipartFile;
import project.dto.PromotionForm;
import project.dto.PromotionModifyForm;
import project.repository.AnimalFileRepository;
import project.repository.AnimalRepository;
import project.repository.PromotionRepository;
import project.service.PromotionService;

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
    @Mock
    private ResourceLoader resourceLoader;
    @InjectMocks
    private PromotionService service;

    private ArgumentCaptor<Promotion> proCaptor;
    private PromotionForm form;

    @BeforeEach
    public void setUp(){
        proCaptor = ArgumentCaptor.forClass(Promotion.class);
        Animal animal = mock(Animal.class);
        List<MultipartFile> list = mock(String.valueOf(new ParameterizedTypeReference<List<MultipartFile>>() {}));
        given(animalRepo.findById(anyInt())).willReturn(Optional.of(animal));

        form = new PromotionForm("제목", list, animal.getNo(), "내용", "내용2");
        service.savePromotion(form);
        verify(proRepo).save(proCaptor.capture());
    }

    @Test
    public void savePromotionSuccessTest()  {
        Promotion pro = proCaptor.getValue();

        verify(animalRepo, times(1)).findById(pro.getAnimalNo());
        verify(proRepo, times(1)).save(pro);
        assertThat(form.getAnimalNo()).isEqualTo(pro.getAnimalNo());
    }

    @Test
    public void findByNoSuccessTest() {
        Promotion pro = proCaptor.getValue();
        given(proRepo.findById(pro.getNo())).willReturn(Optional.of(pro));

        Promotion result = service.findPromotionByNo(pro.getNo());

        verify(proRepo, times(1)).findById(pro.getNo());
        assertThat(form.getTitle()).isEqualTo(result.getTitle());
    }

    @Test
    public void findByNoFailTest() throws NullPointerException {
        Throwable ex = Assertions.assertThrows(Exception.class, () -> service.findPromotionByNo(10));

        assertThat(ex.getMessage()).isEqualTo("조회 결과 없음");
    }

    @Test
    public void findAllPromotionSuccessTest() {
        List<Promotion> capList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Promotion captor = proCaptor.getValue();
            capList.add(captor);
        }
        given(proRepo.findByRemove(0)).willReturn(capList);

        List<Promotion> result = service.findAllPromotion();

        for(int i = 0; i < 4; i++) {
            assertThat(capList.get(i).getTitle()).isEqualTo(result.get(i).getTitle());
        }
    }

    @Test
    public void findAllPromotionFailTest() {
        Throwable ex = Assertions.assertThrows(NullPointerException.class, () -> service.findAllPromotion());

        assertThat(ex.getMessage()).isEqualTo("조회 결과 없음");
    }

    @Test
    public void updatePromotionSuccessTest() throws IOException {
        List<MultipartFile> list = mock(String.valueOf(new ParameterizedTypeReference<List<MultipartFile>>() {}));
        PromotionModifyForm dto = new PromotionModifyForm(1, "수정된 제목", list, "수정된 설명", "수정된 조건");
        Promotion pro = proCaptor.getValue();
        given(proRepo.findById(dto.getNo())).willReturn(Optional.of(pro));

        Promotion result = service.updatePromotion(dto);

        verify(proRepo, times(1)).findById(dto.getNo());
        // setUp 작업 때문에 2번 실행된다.
        verify(proRepo, times(2)).save(pro);

        assertThat(dto.getTitle()).isEqualTo(result.getTitle());
    }

    @Test
    public void updatePromotionFailTest() throws IOException {
        PromotionModifyForm dto = mock(PromotionModifyForm.class);

        Throwable ex = Assertions.assertThrows(NullPointerException.class, () -> service.updatePromotion(dto));
        assertThat(ex.getMessage()).isEqualTo("수정 가능한 Promotion 데이터가 존재하지 않습니다.");
    }
}
