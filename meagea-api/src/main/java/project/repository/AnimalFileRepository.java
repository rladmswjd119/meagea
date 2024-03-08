package project.repository;

import entity.AnimalFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalFileRepository extends JpaRepository<AnimalFile, Integer> {
    List<AnimalFile> findAllByPromotionNo(int promotionNo);
    List<AnimalFile> findAllByLogNo(int logNo);
}
