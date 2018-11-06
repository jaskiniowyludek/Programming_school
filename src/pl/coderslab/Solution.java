package pl.coderslab;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Solution {
        private  int id;
        private Date created;
        private Date updated;
        private String description;
        private int exercise_id;
        private int user_id;

        public Solution(){
        }

    public Solution(int id, Date created, Date updated, String description, int exercise_id, int user_id) {
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
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
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
                preparedStatement.setDate(1,this.created);
                preparedStatement.setDate(2, this.updated);
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
                preparedStatement.setDate(1,this.created);
                preparedStatement.setDate(2, this.updated);
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
            loadedSOL.created = resultSet.getDate("created");
            loadedSOL.updated = resultSet.getDate("updated");
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
            loadedSOL.created = resultSet.getDate("created");
            loadedSOL.updated = resultSet.getDate("updated");
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
    }

