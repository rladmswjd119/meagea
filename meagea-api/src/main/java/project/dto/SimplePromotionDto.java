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

    // 입양 홍보 게시물에 사진을 첨부하고 싶은데 타입을 뭐로 할지 모르겠다.
    // private image;
    private String name;
    private String gender;
    private int age;
    private String kind;
}
