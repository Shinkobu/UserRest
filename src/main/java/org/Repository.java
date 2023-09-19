package org;

import org.Exceptions.EmailExistsException;
import org.Exceptions.IdNotFoundException;

import javax.persistence.*;
import java.util.List;

/**
 * Репозиторий для работы с базой данных
 */
public class Repository {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("UserRest");

    /**
     * Создание нового пользователя
     * @param name - имя пользователя
     * @param email - почта пользователя
     * @param password - пароль пользователя
     */
    public void addUser(String name, String email, String password) {
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

    /**
     * Получение данных о пользователе по id
     * @param id - id пользователя
     * @throws NoResultException - исключение, если id нет в базе данных
     */
    public User getUser(int id) throws IdNotFoundException {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // the lowercase c refers to the object
        // :userId is a parameterized query thats value is set below
        String query = "SELECT c FROM User c WHERE c.userId = :userId";

        // Issue the query and get a matching User
        TypedQuery<User> tq = em.createQuery(query, User.class);
        tq.setParameter("userId", id);

        User user = null;
        try {
            // Get matching user object and output
            user = tq.getSingleResult();
        } catch (NoResultException ex){
            throw new IdNotFoundException ("IdNotFoundException: Пользователь с id " + id + " не существует");
        }
        finally {
            em.close();
        }
        return user;
    }

    /**
     * Проверка есть ли в базе данных указанный email
     * @param email - email для проверки
     * @throws EmailExistsException - исключение, если email найден
     */
    public boolean isEmailPresent(String email) throws EmailExistsException {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        boolean isPresent = false;
        // the lowercase c refers to the object
        // :userId is a parameterized query thats value is set below
        String query = "SELECT c FROM User c WHERE c.email = :email";

        // Issue the query and get a matching User
        TypedQuery<User> tq = em.createQuery(query, User.class);
        tq.setParameter("email", email);

        User user = null;
        try {
            // Get matching user object and output
            user = tq.getSingleResult();
            isPresent = true;
            throw new EmailExistsException("EmailExistsException: Пользователь с email " + email + " уже существует!");
        } catch (NoResultException ex) {
            return isPresent;
        } finally {
            em.close();
        }
    }

    /**
     * Получение данных всех пользователей
     */
    public List<User> getAllUsers() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        // the lowercase c refers to the object
        String strQuery = "SELECT c FROM User c WHERE c.id IS NOT NULL";

        // Issue the query and get a matching User
        TypedQuery<User> tq = em.createQuery(strQuery, User.class);
        List<User> users = null;
        try {
            // Get matching user object and output
            users = tq.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return users;
    }

    /**
     * Изменение пользователя по id
     * @param id - id пользователя, которого нужно изменить
     * @param name - новое имя
     * @param email - новый email
     * @param password - новый пароль
     */
    public void changeUser(int id, String name, String email, String password) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;

        User user = null;
        try {
            // Get transaction and start
            et = em.getTransaction();
            et.begin();

            // Find user and make changes
            user = em.find(User.class, id);
            user.setName(name);
            user.setPassword(password);
            user.setEmail(email);

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

    /**
     * Удаление пользователя по id
     * @param id
     */
    public void deleteUser(int id) {
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









