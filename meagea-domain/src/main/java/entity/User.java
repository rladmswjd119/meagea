package entity;

import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class User {
    private String id;
    private String password;
    private String name;
    private List<Integer> adoptionAnimalNoList;
    private List<Integer> attentionAnimalNo;

}
