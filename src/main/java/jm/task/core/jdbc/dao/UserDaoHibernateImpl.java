package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory factory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = factory.getCurrentSession()) {
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
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS user";
            session.createNativeQuery(sql).executeUpdate();
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = factory.getCurrentSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> user = null;
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            user = session.createQuery("from User")
                    .getResultList();
            for (User u : user) {
                System.out.println(u);
            }
            session.getTransaction().commit();
        }
        return user;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            String sql = "DELETE FROM user";
            session.createNativeQuery(sql).executeUpdate();
            session.close();
        }
    }
}
