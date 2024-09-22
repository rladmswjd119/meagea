package project.dto;

import entity.AnimalFile;
import entity.Log;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDetailDto {

    public PromotionDetailDto(int proNo, String title, int animalNo, String introduction, String terms, LocalDateTime makeDate, LocalDateTime modifyDate,
                              String name, int age, String gender, double weight, boolean neuter, String kind, String place, int healthState, int activity,
                              int sociality, int friendly, boolean adoptionState, List<AnimalFile> proImageList) {
        this.proNo = proNo;
        this.title = title;
        this.animalNo = animalNo;
        this.introduction = introduction;
        this.terms = terms;
        this.makeDate = makeDate;
        this.modifyDate = modifyDate;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.neuter = neuter;
        this.kind = kind;
        this.place = place;
        this.healthState = healthState;
        this.activity = activity;
        this.sociality = sociality;
        this.friendly = friendly;
        this.adoptionState = adoptionState;
        this.proImageList = proImageList;
    }

    private int proNo;
    private String title;
    private int animalNo;
    private String introduction;
    private String terms;
    private LocalDateTime makeDate;
    private LocalDateTime modifyDate;

    private String name;
    private int age;
    private String gender;
    private double weight;
    private boolean neuter;
    private String kind;
    private String place;
    private int healthState;
    private int activity;
    private int sociality;
    private int friendly;
    private boolean adoptionState;

    private List<AnimalFile> proImageList;
    private List<LogTotalDto> logList;
}
