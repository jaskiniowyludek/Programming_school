package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        Connection conn = SQLHelper.connect("programming_school_database");
            User user1 = new User();
            user1.getId();
            user1.setUsername("user1_name");
            user1.setPassword("user1password");
            user1.setEmail("user1@user11.com");
            user1.saveToDB(conn);
            User user2 = new User();
            user2.getId();
            user2.setUsername("user2_name");
            user2.setPassword("user2password");
            user2.setEmail("user2@user2.com");
            user2.saveToDB(conn);

    }
}
