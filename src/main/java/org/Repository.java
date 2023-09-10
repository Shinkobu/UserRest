package org;

import javax.persistence.*;
import java.util.List;

public class Repository {
    // Create an EntityManagerFactory when you start the application
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("UserRest");

    public static void main(String[] args) {
        addUser("Paolo", "paolo@google.com", "jfjfjf",2);
        addUser("Paolo", "paolo@google.com", "jfjfjf",3);
//        addUser("Mario", "mario@google.com", "jfjfj111222f");
//        addUser("Luigi", "luigi@google.com", "jfjfjf");
        getAllUsers();
//        getUser(3);
//        changeUserName(4, "Mark");
//        deleteUser(1);
        ENTITY_MANAGER_FACTORY.close();
    }

    public static void addUser(String name, String email, String password, int id) {
        // The EntityManager class allows operations such as create, read, update, delete
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        // Used to issue transactions on the EntityManager
        EntityTransaction et = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Create and set values for new user
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setUserId(id);

            // Save the user object
            em.persist(user);
            et.commit();
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
        }
    }

    public static void getUser(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // the lowercase c refers to the object
        // :custID is a parameterized query thats value is set below
        String query = "SELECT c FROM users c WHERE c.id = :userId";

        // Issue the query and get a matching User
        TypedQuery<User> tq = em.createQuery(query, User.class);
        tq.setParameter("custID", id);

        User user = null;
        try {
            // Get matching user object and output
            user = tq.getSingleResult();
            System.out.println(user.getName() + " " + user.getUserId());
        }
        catch(NoResultException ex) {
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
    }

    public static void getAllUsers() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // the lowercase c refers to the object
        // :custID is a parameterized query thats value is set below
        String strQuery = "SELECT c FROM User c WHERE c.id IS NOT NULL";

        // Issue the query and get a matching User
        TypedQuery<User> tq = em.createQuery(strQuery, User.class);
        List<User> custs;
        try {
            // Get matching user object and output
            custs = tq.getResultList();
            custs.forEach(cust->System.out.println(cust.getName() + " " + cust.getUserId()));
        }
        catch(NoResultException ex) {
            ex.printStackTrace();
        }
        finally {
            em.close();
        }
    }

    public static void changeUserName(int id, String userName) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        User user = null;

        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Find user and make changes
            user = em.find(User.class, id);
            user.setName(userName);

            // Save the user object
            em.persist(user);
            et.commit();
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
        }
    }

    public static void deleteUser(int id) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        User user = null;

        try {
            et = em.getTransaction();
            et.begin();
            user = em.find(User.class, id);
            em.remove(user);
            et.commit();
        } catch (Exception ex) {
            // If there is an exception rollback changes
            if (et != null) {
                et.rollback();
            }
            ex.printStackTrace();
        } finally {
            // Close EntityManager
            em.close();
        }
    }
}









