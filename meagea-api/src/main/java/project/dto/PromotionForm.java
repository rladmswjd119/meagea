package project.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Getter
public class PromotionForm implements Serializable {
    public PromotionForm(String title, String name, List<MultipartFile> imageList, int age, double weight,
                         boolean neuter, String kind, String detail, String place, int healthState, int activity,
                         int sociality, int friendly, boolean adoptionState, String introduction, String condition) {
        this.title = title;
        this.name = name;
        this.imageList = imageList;
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
        this.adoptionState = adoptionState;
        this.introduction = introduction;
        this.condition = condition;
    }

    private String title;
    private String name;
    private List<MultipartFile> imageList;
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
    private String introduction;
    private String condition;
}
