package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Promotion {
    // 입양 홍보 게시글 정보
    public Promotion(String title, int animalNo, String introduction, String condition, List<Integer> fileNoList) {
        this.title = title;
        this.animalNo = animalNo;
        this.introduction = introduction;
        this.condition = condition;
        this.fileNoList = fileNoList;
        this.createDate = LocalDate.now();
        this.modifyDate = LocalDate.now();
        this.adoptionFormNoList = new ArrayList<>();
        this.diaryNoList = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;
    private int animalNo;
    private String title;
    private String introduction;
    private String condition;
    private List<Integer> adoptionFormNoList;
    private List<Integer> diaryNoList;
    private List<Integer> fileNoList;
    private LocalDate createDate;
    private LocalDate modifyDate;
}
