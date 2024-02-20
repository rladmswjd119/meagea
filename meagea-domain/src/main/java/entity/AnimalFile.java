package entity;

import lombok.Getter;

@Getter
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
