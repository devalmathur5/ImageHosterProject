package ImageHoster.repository;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.xml.transform.Result;

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

    public Integer getMaxId(){
        EntityManager em = emf.createEntityManager();
        TypedQuery query = em.createQuery("select cc from Comment cc where cc.id= " +
                "(select max(c.id) from Comment c)", Comment.class);
        Integer id = -1;
        boolean flag = false;
        try {
            id = (Integer) query.getSingleResult();
            System.out.println("--------------" + id +"-----------");
        }catch (NoResultException e){
            flag = true;
        }
        finally {
            if(id == -1 && flag){
                return 1;
            }else {
                return id + 1;
            }
        }
    }
}
