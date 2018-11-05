package pl.coderslab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {
    private int id;
    private String title;
    private String description;

    public Exercise(){}
    public Exercise(int id, String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId(){
        return  id;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void saveToDB(Connection conn) throws SQLException{
        if(this.id==0){
            String sql = "INSERT INTO Exercise(title, description) VALUES (?,?)";
            String generatedColumns[] = {"ID"};
            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1,this.title);
            preparedStatement.setString(2,this.description);
            preparedStatement.executeUpdate();
            ResultSet rs	=	preparedStatement.getGeneratedKeys();
            if	(rs.next())	{
                this.id	=	rs.getInt(1);
            }
        }else{
            String	sql	=	"UPDATE	Exercise	SET	title=?, description=?	where	id	=	?";
            PreparedStatement	preparedStatement;
            preparedStatement	=	conn.prepareStatement(sql);
            preparedStatement.setString(1,	this.title);
            preparedStatement.setString(2,	this.description);
            preparedStatement.setInt(3,	this.id);
            preparedStatement.executeUpdate();
        }
        }
        public static Exercise[] loadAllExercise(Connection conn) throws SQLException{
            String sql = "SELECT * from Exercise";
            ArrayList<Exercise> exercises = new ArrayList<Exercise>();
            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Exercise loadedExc = new Exercise();
                loadedExc.id= resultSet.getInt("id");
                loadedExc.title = resultSet.getString("title");
                loadedExc.description = resultSet.getString("description");
                exercises.add(loadedExc);
            }
            Exercise[] exercises1 = new Exercise[exercises.size()];
            exercises1 = exercises.toArray(exercises1);
            return exercises1;
        }

        public static Exercise loadById(Connection conn, int id) throws SQLException{
            String sql = "SELECT * from Exercise WHERE id=?";
            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                Exercise loadedEx = new Exercise();
                loadedEx.title = rs.getString("title");
                loadedEx.description = rs.getString("description");
                loadedEx.id = rs.getInt("id");
                return loadedEx;
            }
            return null;
        }

        public void delete(Connection conn) throws SQLException{
            if(this.id!=0){
                String sql = "DELETE FROM Exercise WHERE id=?";
                PreparedStatement preparedStatement;
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1,this.id);
                preparedStatement.executeUpdate();
                this.id = 0;
            }
        }
}
