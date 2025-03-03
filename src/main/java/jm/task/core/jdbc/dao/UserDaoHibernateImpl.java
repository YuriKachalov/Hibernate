package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory FACTORY = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = FACTORY.getCurrentSession()) {
            session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS user (id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(150) NOT NULL," +
                    "lastName VARCHAR(150) NOT NULL," +
                    "age INT NOT NULL)";
            session.createNativeQuery(sql).executeUpdate();
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = FACTORY.getCurrentSession()) {
            session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS user";
            session.createNativeQuery(sql).executeUpdate();
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = FACTORY.getCurrentSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = FACTORY.getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        EntityManager entityManager = FACTORY.createEntityManager();
        try {
            String jpql = "SELECT u FROM User u";
            TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
            List<User> user = query.getResultList();
            for (User u : user) {
                System.out.println(u);
            }
            return user;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = FACTORY.getCurrentSession()) {
            session.beginTransaction();
            String sql = "DELETE FROM user";
            session.createNativeQuery(sql).executeUpdate();
            session.close();
        }
    }
}
