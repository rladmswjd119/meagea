package project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class PromotionModifyForm {
    private int no;
    private String title;
    private List<MultipartFile> imageList;
    private String introduction;
    private String terms;
}
