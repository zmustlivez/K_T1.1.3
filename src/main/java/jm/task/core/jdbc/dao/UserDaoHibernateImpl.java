package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
/*        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users ("
                    + "id BIGINT NOT NULL AUTO_INCREMENT,"
                    + "name VARCHAR(255) NOT NULL,"
                    + "lastname VARCHAR(255) NOT NULL,"
                    + "age TINYINT(3) NOT NULL,"
                    + "PRIMARY KEY (id)"
                    + ")").executeUpdate();
            transaction.commit();
            System.out.println("Table 'Users' created successfully by using Hibernate method");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }*/
    }


    @Override
    public void dropUsersTable() {
/*        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
            System.out.println("table delete with hibernate");
        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }*/
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("User saved successfully");
        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.load(User.class, id));
//            session.createSQLQuery("DELETE FROM users WHERE id = " + id).executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            users = session.createQuery("from User", User.class).list();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
//            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            transaction.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }
}
