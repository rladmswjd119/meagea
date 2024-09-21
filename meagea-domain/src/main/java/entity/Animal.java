package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "animal")
@NoArgsConstructor
public class Animal {
    public Animal(String name, int age, String gender, double weight, boolean neuter, String kind,
                  String place, int healthState, int activity, int sociality, int friendly) {
        this.no = (int)(Math.random()*10000);
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.neuter = neuter;
        this.kind = kind;
        this.place = place;
        this.healthState = healthState;
        this.activity = activity;
        this.sociality = sociality;
        this.friendly = friendly;
        this.adoptionState = false;
        this.gender = gender;
        this.remove = 0;
    }

    @Id
    private int no;
    private String name;
    private int age;
    private double weight;
    private boolean neuter;
    private String kind;
    private String place;
    private int healthState;
    private int activity;
    private int sociality;
    private int friendly;
    private boolean adoptionState;
    private String gender;
    private int remove;
}
