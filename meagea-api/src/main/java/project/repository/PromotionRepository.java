package project.repository;

import entity.Promotion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class PromotionRepository {

    @PersistenceContext
    private EntityManager em;

    public void addPromotion(Promotion promotion) {
        em.persist(promotion);
    }

}
