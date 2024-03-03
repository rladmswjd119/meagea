package project.repository;

import entity.AnimalFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalFileRepository extends JpaRepository<AnimalFile, Integer> {
}
