package project.dto;

import entity.AnimalFile;
import entity.Log;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LogTatalDto {
    private Log log;
    private List<AnimalFile> imageList;
}
