package pl.coderslab;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws SQLException {

        try(Connection conn = SQLHelper.connect("programming_school_database")){

            //ADDING SOLUTION WORKS!!
            Solution solution1 = new Solution();
            solution1.setDescription("to jest rozwiązanie jakieśtam2");
            solution1.setUser(1);
            solution1.setExercise(2);
            solution1.saveToDb(conn);
            //LOAD ALL SOLUTIONS
            Solution[] solutions = Solution.loadAllSolutions(conn);
            for (Solution s: solutions){
                System.out.println("Created: "+s.getCreated()+" id: "+s.getId());
            }
            //adding group to DB works!
        Group group1 = new Group();
        group1.setName("grupa nowa");
        group1.saveToDB(conn);
        //finding group in DB works!
        Group foundG = Group.loadGroupById(conn,1);
        System.out.println("Nazwa grupy: "+foundG.getName());
        //adding exercise to DB works!
        Exercise exercise1 = new Exercise();
        exercise1.setTitle("Odejmowanie");
        exercise1.setDescription("Odejmowanie 2 i 2");
        exercise1.saveToDB(conn);
        //finding exercise in DB works!
        Exercise foundEx = Exercise.loadById(conn,1);
        System.out.println("Title: "+foundEx.getTitle()+" description: "+foundEx.getDescription());
        //adding user ok!
//            User user1 = new User();
//            user1.setUsername("user2_name");
//            user1.setPassword("user2password");
//            user1.setEmail("user2@user2.com");
//            user1.setUser_group(2);
//            user1.saveToDB(conn);
        //deleting user ok!
//        User usertoDelete = User.loadUserById(conn,3);
//        usertoDelete.delete(conn);
//            System.out.println(user1.getId());
//            User user2 = new User();
//            user2.getId();
//            user2.setUsername("user2_name");
//            user2.setPassword("user2password");
//            user2.setEmail("user2@user2.com");
//            user2.saveToDB(conn);
//            User user3 = new User();
//            user3.getId();
//            user3.setUsername("user3_name");
//            user3.setPassword("user3password");
//            user3.setEmail("user3@user3.com");
//            user3.saveToDB(conn);
//            System.out.println("tutaj jestem!");
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
            User[] usersInGroup2 = User.loadAllByGroupId(conn,2);
            for (User u: usersInGroup2){
                System.out.println(u.getUsername()+", grupa: "+u.getUser_group());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
