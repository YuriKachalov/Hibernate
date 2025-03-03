package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService users = new UserServiceImpl();
        users.createUsersTable();
        users.saveUser("Oleg", "Petrov", (byte) 24);
        users.saveUser("Sergei", "Ivanov", (byte) 45);
        users.saveUser("Andrei", "Matveef", (byte) 32);
        users.saveUser("Irina", "Petrova", (byte) 34);
        users.getAllUsers();
        users.cleanUsersTable();
        users.dropUsersTable();
    }
}
