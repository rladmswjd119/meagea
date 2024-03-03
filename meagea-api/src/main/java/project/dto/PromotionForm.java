package project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
public class PromotionForm implements Serializable {
    private String title;
    private List<MultipartFile> imageList;
    private int animalNo;
    private String introduction;
    private String condition;
}
