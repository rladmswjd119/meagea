package project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class SimplePromotionDto {
    public SimplePromotionDto(int no, int animalNo, String title, String name, String gender, int age, String kind) {
        this.no = no;
        this.animalNo = animalNo;
        this.title = title;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.kind = kind;
    }

    private int no;
    private int animalNo;
    private String title;
    private String name;
    private String gender;
    private int age;
    private String kind;
}
