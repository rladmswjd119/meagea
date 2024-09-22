package project.repository;

import entity.AnimalFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalFileRepository extends JpaRepository<AnimalFile, Integer> {
    List<AnimalFile> findAllByPromotionNoAndProperty(int no, String property);
    List<AnimalFile> findAllByLogNo(int logNo);
    List<AnimalFile> findAllByPromotionNo(int promotionNo);

    void deleteAllByPromotionNo(int promotionNo);
    void deleteAllByLogNo(int logNo);

    void deleteByPromotionNo(int proNo);
}
