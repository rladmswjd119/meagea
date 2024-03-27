package project.repository;

import entity.AnimalFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalFileRepository extends JpaRepository<AnimalFile, Integer> {
    List<AnimalFile> findAllByPromotionNoAndProperty(int promotionNo, String property);
    List<AnimalFile> findAllByLogNo(int logNo);
    List<AnimalFile> findAllByPromotionNo(int promotionNo);

    void deleteByPromotionNo(int promotionNo);

}
