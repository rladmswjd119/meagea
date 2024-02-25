package entity;

import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class AnimalFile {

    public AnimalFile(String uploadFileName, String serverFileName) {
        this.no = 1;
        this.uploadFileName = uploadFileName;
        this.serverFileName = serverFileName;
    }

    private int no;
    private String uploadFileName;
    private String serverFileName;
}
