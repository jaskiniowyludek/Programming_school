package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        Connection conn = SQLHelper.connect("programming_school_database");

            User user1 = new User();
            System.out.println("tutaj zaczynam!");
            System.out.println(user1.getId());
            user1.setUsername("user1_name");
            user1.setPassword("user1password");
            user1.setEmail("user1@user11.com");
            user1.saveToDB(conn);
            System.out.println(user1.getId());
            User user2 = new User();
            user2.getId();
            user2.setUsername("user2_name");
            user2.setPassword("user2password");
            user2.setEmail("user2@user2.com");
            user2.saveToDB(conn);
            User user3 = new User();
            user3.getId();
            user3.setUsername("user3_name");
            user3.setPassword("user3password");
            user3.setEmail("user3@user3.com");
            user3.saveToDB(conn);
            System.out.println("tutaj jestem!");
 //           User userD = User.loadUserById(conn,4);
//            System.out.println("Id: "+userD.getId()+" name: "+userD.getUsername()+"mail: "+userD.getEmail());
////            userD.setUsername("user1_name_modified");
////            userD.saveToDB(conn);
//            userD.delete(conn);
//            System.out.println("id userD: "+userD.getId());
////            User userNE = User.loadUserById(conn, 10);
////            System.out.println(userNE.getUsername());
            User[] users = User.loadAllUsers(conn);
            int usersLength = users.length;
            System.out.println("długość tablicy to: "+usersLength);
            for (User u: users){
                System.out.println(u.getUsername()+", "+u.getEmail());
            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }

    }
}
