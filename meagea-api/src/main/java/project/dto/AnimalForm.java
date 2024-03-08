package project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimalForm {
    public AnimalForm(String name, int age, String gender, double weight, boolean neuter, String kind, String detail, String place,
                      int healthState, int activity, int sociality, int friendly) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.neuter = neuter;
        this.kind = kind;
        this.detail = detail;
        this.place = place;
        this.healthState = healthState;
        this.activity = activity;
        this.sociality = sociality;
        this.friendly = friendly;
        this.adoptionState = false;
    }

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
