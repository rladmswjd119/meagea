package project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnimalDto {
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
}
