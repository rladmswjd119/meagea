package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "animal_file")
@NoArgsConstructor
public class AnimalFile {

    public AnimalFile(int promotionNo, String uploadFileName, String serverFileName, String property) {
        this.no = (int)(Math.random()*10000);
        this.promotionNo = promotionNo;
        this.uploadFileName = uploadFileName;
        this.serverFileName = serverFileName;
        this.property = property;
    }

    @Id
    private int no;
    private int promotionNo;
    private Integer diaryLogNo;
    private String uploadFileName;
    private String serverFileName;
    private String property;
}
