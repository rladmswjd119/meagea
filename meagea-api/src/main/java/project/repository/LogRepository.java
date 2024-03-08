package project.repository;

import entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {
    List<Log> findAllByPromotionNo(int promotionNo);
}
