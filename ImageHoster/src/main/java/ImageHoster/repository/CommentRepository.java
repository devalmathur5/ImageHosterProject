package ImageHoster.repository;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

@Repository
public class CommentRepository {
    @PersistenceUnit(unitName = "ImageHoster")
    private EntityManagerFactory emf;

    public Comment createComment(Comment newComment) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        System.out.println("------PK is null--------------- is trasaction Beginning? ");
        try {
            transaction.begin();
            em.persist(newComment);
            transaction.commit();

            System.out.println("------------ is it committing ????????");
        } catch (Exception e) {
            transaction.rollback();
        }
        return newComment;
    }
}
