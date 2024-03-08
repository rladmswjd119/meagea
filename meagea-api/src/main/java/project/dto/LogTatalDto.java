package project.dto;

import entity.AnimalFile;
import entity.Log;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class LogTatalDto {
    private int promotionNo;
    private String body;
    private LocalDateTime makeDate;
    private List<AnimalFile> imageList;
}
