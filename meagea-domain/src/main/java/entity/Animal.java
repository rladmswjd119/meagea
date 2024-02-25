package entity;

import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class Animal {
    public Animal(String name, int age, double weight, boolean neuter, String kind, String detail,
                  String place, int healthState, int activity, int sociality, int friendly) {
        this.no = 10;
        this.name = name;
        this.age = age;
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

    private int no;
    private String name;
    private int age;
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
