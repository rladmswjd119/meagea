package entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class Promotion {
    public Promotion(Animal animal) {
        setAnimal(animal);
    }
    public Promotion(){};

    // 입양 홍보 게시글 정보
    private int no;
    private Animal animal;
    private String introduction;
    private String condition;
    private LocalDate createDate;
    private LocalDate modifyDate;
    private User adoptionUser;
    private List<AdoptionForm> adoptionFormList;
    private List<Diary> diaryList;
}
