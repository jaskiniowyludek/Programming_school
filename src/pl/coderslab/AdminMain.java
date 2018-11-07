package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminMain {

    public static void main(String[] args) throws SQLException {
        try (Connection conn = SQLHelper.connect("programming_school_database")){
            User[] users = User.loadAllUsers(conn);
            System.out.println("List of all users: ");
            for (User u: users){
                System.out.println("Id: "+u.getId()+", username: "+u.getUsername()+", email: "+u.getEmail()+
                "user group: "+u.getUser_group());
            }
            System.out.println("Type option's code to choose one of the options: add - to add user," +
                    " edit - to edit user, delete - to delete user. Type \"quit\" to exit");
            Scanner scan = new Scanner(System.in);
            String command = "";
            do {
                command=scan.nextLine();
                if (command.equals("add")){
                    User user = new User();
                    System.out.println("Type username: ");
                    user.setUsername(scan.nextLine());
                    System.out.println("Type email: ");
                    user.setEmail(scan.nextLine());
                    System.out.println("Type password: ");
                    user.setPassword(scan.nextLine());
                    System.out.println("Type group id: ");
                    user.setUser_group(scan.nextInt());
                    user.saveToDB(conn);
                    System.out.println("User added to database!");
                }else if (command.equals("edit")){
                    System.out.println("Type id: ");
                    int id = scan.nextInt();
                    User user = User.loadUserById(conn,id);
                    System.out.println("Type username: ");
                    user.setUsername(scan.nextLine());
                    System.out.println("Type email: ");
                    user.setEmail(scan.nextLine());
                    System.out.println("Type password: ");
                    user.setPassword(scan.nextLine());
                    System.out.println("Type group id: ");
                    user.setUser_group(scan.nextInt());
                    user.saveToDB(conn);
                    System.out.println("User modified successfully!");
                }else if (command.equals("delete")){
                    System.out.println("Type id: ");
                    int id = scan.nextInt();
                    User user = User.loadUserById(conn,id);
                    user.delete(conn);
                    System.out.println("User deleted!!");
                }else if(command.equals("quit")){

                }
                else System.out.println("Type: add, edit, delete or quit!");
            }while (!command.equals("quit"));
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
    }
}
//DODAĆ OBSŁUGE BŁĘDÓW!!
// TODO: what happens if user type Stirng instead of int, if email already exists in database etc...
