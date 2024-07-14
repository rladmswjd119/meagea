package project;

import entity.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.dto.AnimalForm;
import project.repository.AnimalRepository;
import project.service.AnimalService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {
    @Mock
    private AnimalRepository aniRepo;
    @InjectMocks
    private AnimalService animalService;

    private ArgumentCaptor<Animal> animalCap;
    private AnimalForm form;

    @BeforeEach
    public void setUp(){
        animalCap = ArgumentCaptor.forClass(Animal.class);
        Animal animal = mock(Animal.class);
        form = new AnimalForm("뽀또", 5, "수컷", 3.5, true, "고양이",
                "치즈", "공사장", 3, 4, 5, 5);
        given(aniRepo.save(animalCap.capture())).willReturn(animal);
        animalService.addAnimal(form);
    }

    @Test
    public void saveAnimalTest() {
        verify(aniRepo, times(1)).save(animalCap.getValue());
        Animal result = animalCap.getValue();
        assertThat(result.getName()).isEqualTo(form.getName());
    }

    @Test
    public void findAnimalByNoSuccessTest(){
        Animal animal = animalCap.getValue();
        given(aniRepo.findById(animal.getNo())).willReturn(Optional.of(animal));

        Animal result = animalService.findAnimalByNo(animal.getNo());

        verify(aniRepo, times(1)).findById(animal.getNo());
        assertThat(result.getName()).isEqualTo(animal.getName());
    }

    @Test
    public void findAnimalByNoFailTest(){
        Throwable ex = assertThrows(NullPointerException.class, () -> animalService.findAnimalByNo(231));

        assertThat(ex.getMessage()).isEqualTo("조회 결과 없음");
    }
}
