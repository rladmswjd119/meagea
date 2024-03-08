package project.dto;

import entity.AnimalFile;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PromotionDetailDto {
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
    private String detail;
    private String place;
    private int healthState;
    private int activity;
    private int sociality;
    private int friendly;
    private boolean adoptionState;

    private List<AnimalFile> imageLIst;
}
