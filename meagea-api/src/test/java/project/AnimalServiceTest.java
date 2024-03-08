package project;

import entity.Animal;
import org.assertj.core.api.Assertions;
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

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {
    @Mock
    private AnimalRepository aniRepo;
    @InjectMocks
    private AnimalService animalService;

    @Test
    public void saveAnimalTest() {
        ArgumentCaptor<Animal> animalCap = ArgumentCaptor.forClass(Animal.class);

        AnimalForm form = new AnimalForm("뽀또", 5, "수컷", 3.5, true, "고양이",
                "치즈", "공사장", 3, 4, 5, 5);
        Animal animal = new Animal("머핀", 5, "수컷", 3.5, true, "고양이",
                "치즈", "공사장", 3, 4, 5, 5);
        given(aniRepo.save(animalCap.capture())).willReturn(animal);

        animalService.addAnimal(form);
        Animal result = animalCap.getValue();

        verify(aniRepo, times(1)).save(any());
        assertThat(result.getName()).isEqualTo(form.getName());
    }

    @Test
    public void findAnimalByNoSuccessTest(){
        Animal animal = new Animal("머핀", 5, "수컷", 3.5, true, "고양이",
                "치즈", "공사장", 3, 4, 5, 5);
        given(aniRepo.findById(eq(10))).willReturn(Optional.of(animal));

        Animal result = aniRepo.findById(10).get();

        verify(aniRepo, times(1)).findById(10);
        assertThat(result.getName()).isEqualTo("머핀");
    }

    @Test
    public void findAnimalByNoFailTest(){
        Throwable ex = assertThrows(NullPointerException.class, () -> animalService.findAnimalByNo(10));

        assertThat(ex.getMessage()).isEqualTo("조회 결과 없음");
    }
}
