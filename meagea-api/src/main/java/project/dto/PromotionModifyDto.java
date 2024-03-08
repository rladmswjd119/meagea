package project.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PromotionModifyDto {
    private int no;
    private String title;
    private List<MultipartFile> imageList;
    private String introduction;
    private String terms;
}
