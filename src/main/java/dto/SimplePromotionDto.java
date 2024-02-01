package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimplePromotionDto {
    public SimplePromotionDto(int i) {
        setNo(no);
    }
    public SimplePromotionDto(){}

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
