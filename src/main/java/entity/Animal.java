package entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Animal {
    private int no;

    // 입양 홍보 게시물에 사진을 첨부하고 싶은데 타입을 뭐로 할지 모르겠다.
    // private image;
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
