package pl.coderslab;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class AdminMain {

    public static void main(String[] args) throws SQLException {
        try (Connection conn = SQLHelper.connect("programming_school_database")){
            Scanner scan = new Scanner(System.in);
            String command = "";
            do {
                System.out.println("Type option's code to choose one of the options: user - to go to user panel," +
                        " exercise - to go to exericise panel, group - to go to group panel," +
                        "solution - to go to solutions panel. Type \"quit\"" +
                        "if you want to exit");
                command=scan.nextLine();
                if (command.equals("user")){
                    userFuncionalities();
                }else if (command.equals("exercise")){
                    exerciseFuncionalities();
                }else if(command.equals("group")){
                    groupFuncionallities();
                }else if(command.equals("solution")){
                    solutionsFuncionalities();
                }
                else if(command.equals("quit")){
                    System.out.println("Bye!!");
                }
                else System.out.println("Type: \"user\", \"exercise\", \"group\" or \"quit\"!");
            }
            while (!command.equals("quit"));
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
    }

    public static void solutionsFuncionalities()throws SQLException{
        try(Connection conn = SQLHelper.connect("programming_school_database")) {
            System.out.println("Type one of the options: \"add\" if you want to add an exercise to a user," +
                    "\"view\" if you want to see all the solutions of a user, \"quit\" if you want to go back" +
                    "to the homepage MENU");
            Scanner scan = new Scanner(System.in);
            String command="";
            do{
                command = scan.nextLine();
                if (command.equals("add")){
                    User[] users = User.loadAllUsers(conn);
                    System.out.println("List of all the users: ");
                    for (User u: users){
                        System.out.println("Id: "+u.getId()+", username: "+u.getUsername()+", email: "+u.getEmail()+
                                ", user group: "+u.getUser_group());
                    }
                    System.out.println("Type user's id:");
                    int id = scan.nextInt();
                    User user = User.loadUserById(conn, id);
                    System.out.println("List of all available exercises: ");
                    Exercise[] exercises = Exercise.loadAllExercise(conn);
                    for (Exercise e: exercises){
                        System.out.println("Id: "+e.getId()+", title: "+e.getTitle()+", description: "+e.getDescription());
                    }
                    System.out.println("Type exercise's id you want to choose: ");
                    int idE = scan.nextInt();
                    Exercise exercise = Exercise.loadById(conn, id);
                    Solution solution = new Solution();
                    solution.setUser(user.getId());
                    solution.setExercise(exercise.getId());
                    solution.saveToDb(conn);
                    System.out.println("Exercise added to user's exercises!");
                }else if (command.equals("view")){
                    User[] users = User.loadAllUsers(conn);
                    System.out.println("List of all the users: ");
                    for (User u: users){
                        System.out.println("Id: "+u.getId()+", username: "+u.getUsername()+", email: "+u.getEmail()+
                                ", user group: "+u.getUser_group());
                    }
                    System.out.println("Type user's id to see solutions:");
                    int id = scan.nextInt();
                    ArrayList<Solution> solutions = Solution.loadAllByUserId(conn,id);
                    if (solutions.size()==0){
                        System.out.println("No solutions added");
                    }
                    for (Solution s: solutions){
                        System.out.println("Created: "+s.getCreated()+", updated: "+s.getUpdated()+
                        ", description: "+s.getDescription());
                    }
                }else if (command.equals("quit")){
                }else System.out.println("Type \"add\", \"view\" or \"quit\"");

            }while (!command.equals("quit"));
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
    }

    public static void groupFuncionallities()throws SQLException{
        try (Connection conn = SQLHelper.connect("programming_school_database")){
            Group[] groups = Group.loadAllGroups(conn);
            System.out.println("List of all the groups:");
            for (Group g: groups){
                System.out.println("Id: "+g.getId()+", name: "+g.getName());
            }
            System.out.println("Type option's code to choose one of the options: add - to add user," +
                    " edit - to edit user, delete - to delete user. Type \"quit\" to go back to the homepage MENU");
            Scanner scan = new Scanner(System.in);
            String command = "";
            do {
                command=scan.nextLine();
                if (command.equals("add")){
                    Group group = new Group();
                    System.out.println("Type group's name: ");
                    group.setName(scan.nextLine());
                    group.saveToDB(conn);
                    System.out.println("New group added to database!");
                }else if (command.equals("edit")){
                    System.out.println("Type id: ");
                    int id = scan.nextInt();
                    Group group = Group.loadGroupById(conn, id);
                    System.out.println("Type group's name: ");
                    group.setName(scan.nextLine());
                    group.saveToDB(conn);
                    System.out.println("Group modified successfully!");
                }else if (command.equals("delete")){
                    System.out.println("Type id: ");
                    int id = scan.nextInt();
                    Group group = Group.loadGroupById(conn, id);
                    group.delete(conn);
                    System.out.println("Group deleted!!");
                }else if(command.equals("quit")){

                }
                else System.out.println("Type: add, edit, delete or quit!");
            }while (!command.equals("quit"));
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
    }

    public static void exerciseFuncionalities() throws SQLException{
        try (Connection conn = SQLHelper.connect("programming_school_database")){
            Exercise[] exercises = Exercise.loadAllExercise(conn);
            System.out.println("List of all exercises: ");
            for (Exercise e: exercises){
                System.out.println("Id: "+e.getId()+", title: "+e.getTitle()+", description: "+e.getDescription());
            }
            System.out.println("Type option's code to choose one of the options: add - to add user," +
                    " edit - to edit user, delete - to delete user. Type \"quit\" to go back to homepage MENU");
            Scanner scan = new Scanner(System.in);
            String command = "";
            do {
                command=scan.nextLine();
                if (command.equals("add")){
                    Exercise exercise = new Exercise();
                    System.out.println("Type title: ");
                    exercise.setTitle(scan.nextLine());
                    System.out.println("Type description: ");
                    exercise.setDescription(scan.nextLine());
                    exercise.saveToDB(conn);
                    System.out.println("Exercise added to database!");
                }else if (command.equals("edit")){
                    System.out.println("Type id: ");
                    int id = scan.nextInt();
                    Exercise exercise = Exercise.loadById(conn, id);
                    System.out.println("Type title: ");
                    exercise.setTitle(scan.nextLine());
                    System.out.println("Type description: ");
                    exercise.setDescription(scan.nextLine());
                    exercise.saveToDB(conn);
                    System.out.println("Exercise modified successfully!");
                }else if (command.equals("delete")){
                    System.out.println("Type id: ");
                    int id = scan.nextInt();
                    Exercise exercise = Exercise.loadById(conn, id);
                    exercise.delete(conn);
                    System.out.println("Exercise deleted!!");
                }else if(command.equals("quit")){

                }
                else System.out.println("Type: add, edit, delete or quit!");
            }while (!command.equals("quit"));
        }catch (SQLException e){
            System.out.println(e.getErrorCode());
        }
    }

    public static void userFuncionalities()throws SQLException{
        try (Connection conn = SQLHelper.connect("programming_school_database")){
            User[] users = User.loadAllUsers(conn);
            System.out.println("List of all users: ");
            for (User u: users){
                System.out.println("Id: "+u.getId()+", username: "+u.getUsername()+", email: "+u.getEmail()+
                        ", user group: "+u.getUser_group());
            }
            System.out.println("Type option's code to choose one of the options: add - to add user," +
                    " edit - to edit user, delete - to delete user. Type \"quit\" to go back to homepage MENU");
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
