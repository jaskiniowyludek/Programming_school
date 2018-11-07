package pl.coderslab;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Solution {
        private  int id;
        private java.util.Date created = new Date();
        private java.util.Date updated = new Date();
        private String description;
        private int exercise_id;
        private int user_id;

        public Solution(){
        }

    public Solution(int id, java.util.Date created, java.util.Date updated, String description, int exercise_id, int user_id) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exercise_id = exercise_id;
        this.user_id = user_id;
    }

    public int getId(){
            return id;
    }
    public java.util.Date getCreated() {
        return created;
    }

    public void setCreated(java.util.Date created) {
        this.created = created;
    }

    public java.util.Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExercise() {
        return exercise_id;
    }

    public void setExercise(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public int getUser() {
        return user_id;
    }

    public void setUser(int user_id) {
        this.user_id = user_id;
    }
    public void saveToDb(Connection conn)throws SQLException{
            if (this.id==0){
                String sql = "INSERT INTO Solution(created, updated, description, exercise_id, user_id) " +
                        "VALUES(?,?,?,?,?)";
                PreparedStatement preparedStatement;
                String GeneretedColumns[] = {"ID"};
                preparedStatement = conn.prepareStatement(sql, GeneretedColumns);
                preparedStatement.setObject(1,new Timestamp(this.created.getTime()));
              //  preparedStatement.setObject(2,new Timestamp(this.updated.getTime()));
                preparedStatement.setString(3, this.description);
                preparedStatement.setInt(4,this.exercise_id);
                preparedStatement.setInt(5, this.user_id);
                preparedStatement.executeUpdate();
                ResultSet rs	=	preparedStatement.getGeneratedKeys();
                if	(rs.next())	{
                    this.id	=	rs.getInt(1);
                }
            }else{
                String	sql	=	"UPDATE	Solution	SET	created=?, updated=?, description=?" +
                        ", exercise_id=?, user_id=?	where	id	=	?";
                PreparedStatement	preparedStatement;
                preparedStatement	=	conn.prepareStatement(sql);
              //  preparedStatement.setObject(1,new Timestamp(this.created.getTime()));
                preparedStatement.setObject(2,new Timestamp(this.updated.getTime()));
                preparedStatement.setString(3,	this.description);
                preparedStatement.setInt(4,this.exercise_id);
                preparedStatement.setInt(5, this.user_id);
                preparedStatement.setInt(6,	this.id);
                preparedStatement.executeUpdate();
            }
            }
    public static Solution[] loadAllSolutions(Connection conn) throws SQLException{
        String sql = "SELECT * from Solution";
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Solution loadedSOL = new Solution();
            loadedSOL.id= resultSet.getInt("id");
            loadedSOL.created = resultSet.getTimestamp("created");
            loadedSOL.updated = resultSet.getTimestamp("updated");
            loadedSOL.description = resultSet.getString("description");
            loadedSOL.user_id = resultSet.getInt("user_id");
            loadedSOL.exercise_id = resultSet.getInt("exercise_id");
            solutions.add(loadedSOL);
        }
        Solution[] solutions1 = new Solution[solutions.size()];
        solutions1 = solutions.toArray(solutions1);
        return solutions1;
    }

    public static Solution loadById(Connection conn, int id) throws SQLException{
        String sql = "SELECT * from Solution WHERE id=?";
        PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            Solution loadedSOL = new Solution();
            loadedSOL.id= resultSet.getInt("id");
            loadedSOL.created = resultSet.getTimestamp("created");
            loadedSOL.updated = resultSet.getTimestamp("updated");
            loadedSOL.description = resultSet.getString("description");
            loadedSOL.user_id = resultSet.getInt("user_id");
            loadedSOL.exercise_id = resultSet.getInt("exercise_id");
            return loadedSOL;
        }
        return null;
    }

    public void delete(Connection conn) throws SQLException{
        if(this.id!=0){
            String sql = "DELETE FROM Solution WHERE id=?";
            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }

    public static ArrayList<Solution> loadAllByUserId(Connection conn, int userID) throws SQLException{
            ArrayList<Solution> solutions = new ArrayList<Solution>();
            String sql = "SELECT * FROM Solution WHERE user_id=?";
            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Solution foundS = new Solution();
                foundS.id = rs.getInt("id");
                foundS.created = rs.getTimestamp("created");
                foundS.updated = rs.getTimestamp("updated");
                foundS.description = rs.getString("description");
                foundS.user_id = rs.getInt("user_id");
                foundS.exercise_id = rs.getInt("exercise_id");
                solutions.add(foundS);
            }
            return solutions;
    }
    public static ArrayList<Solution> loadAllByExerciseId(Connection conn, int exerciseID) throws SQLException{
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM Solution WHERE exercise_id=? SORTED BY created DESC";
        PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, exerciseID);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()){
            Solution foundS = new Solution();
            foundS.id = rs.getInt("id");
            foundS.created = rs.getTimestamp("created");
            foundS.updated = rs.getTimestamp("updated");
            foundS.description = rs.getString("description");
            foundS.user_id = rs.getInt("user_id");
            foundS.exercise_id = rs.getInt("exercise_id");
            solutions.add(foundS);
        }
        return solutions;
    }
}

//pobranie	wszystkich	rozwiązań	danego	zadania	posortowanych	od	najnowszego	do	najstarszego
//(dopisz	metodę	 loadAllByExerciseId 	do	klasy	 Solution )