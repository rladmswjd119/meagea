package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
        this.remove = 0;
    }

    @Id
    private int no;
    private int promotionNo;
    private Integer logNo;
    private String uploadFileName;
    private String serverFileName;
    private String property;
    private int remove;
}
