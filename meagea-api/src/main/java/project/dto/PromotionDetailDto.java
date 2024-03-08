package project.dto;

import entity.Animal;
import entity.AnimalFile;
import entity.Promotion;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PromotionDetailDto {
    private Promotion promotion;
    private Animal animal;
    private List<AnimalFile> imageLIst;
}
