package project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class LogForm {
    private int promotionNo;
    private String body;
    private List<MultipartFile> imageList;
}
