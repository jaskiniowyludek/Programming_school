package pl.coderslab;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {

    private int id = 0;
    private String username;
    private String password;
    private String email;
    private int user_group;

    public int getUser_group() {
        return user_group;
    }

    public void setUser_group(int user_group) {
        this.user_group = user_group;
    }

    public	User(String	username, String	email, String	password, int user_group)	{
        this.username	=	username;
        this.email	=	email;
        this.setPassword(password);
        this.user_group = user_group;

    }
    public User(){}
    public int getId(){
        return id;
    }

    public void setUsername(String username){
        this.username=username;
    }
    public String getUsername(){
        return username;
    }
    public	void	setPassword(String	password)	{
        this.password	=	BCrypt.hashpw(password,	BCrypt.gensalt());
    }
    public String getPassword(){
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public	void	saveToDB(Connection conn)	throws SQLException {
        if	(this.id	==	0)	{
            String	sql	=	"INSERT	INTO User(username,	email,	password, user_group_id)	VALUES	(?,	?,	?, ?)";
            String	generatedColumns[]	=	{	"ID"	};
            PreparedStatement preparedStatement;
            preparedStatement	=	conn.prepareStatement(sql,	generatedColumns);
            preparedStatement.setString(1,	this.username);
            preparedStatement.setString(2,	this.email);
            preparedStatement.setString(3,	this.password);
            preparedStatement.setInt(4, this.user_group);
            preparedStatement.executeUpdate();
            ResultSet rs	=	preparedStatement.getGeneratedKeys();
            if	(rs.next())	{
                this.id	=	rs.getInt(1);
            }
        }else	{
            String	sql	=	"UPDATE	User	SET	username=?,	email=?,	password=?, user_group_id=?	where	id	=	?";
            PreparedStatement	preparedStatement;
            preparedStatement	=	conn.prepareStatement(sql);
            preparedStatement.setString(1,	this.username);
            preparedStatement.setString(2,	this.email);
            preparedStatement.setString(3,	this.password);
            preparedStatement.setInt(4, this.user_group);
            preparedStatement.setInt(5,	this.id);
            preparedStatement.executeUpdate();
        }
    }
    static	public	User	loadUserById(Connection	conn,	int	id)	throws	SQLException	{
        String	sql	=	"SELECT	*	FROM	User	where	id=?";
        PreparedStatement	preparedStatement;
        preparedStatement	=	conn.prepareStatement(sql);
        preparedStatement.setInt(1,	id);
        ResultSet	resultSet	=	preparedStatement.executeQuery();
        if	(resultSet.next())	{
            User	loadedUser	=	new	User();
            loadedUser.id	=	resultSet.getInt("id");
            loadedUser.username	=	resultSet.getString("username");
            loadedUser.password	=	resultSet.getString("password");
            loadedUser.email	=	resultSet.getString("email");
            loadedUser.user_group = resultSet.getInt("user_group_id");
            return	loadedUser;}
        return	null;
    }
    static	public	User[]	loadAllUsers(Connection	conn)	throws	SQLException	{
        ArrayList<User> users	=	new	ArrayList<User>();
        String	sql	=	"SELECT	*	FROM	User";
        PreparedStatement	preparedStatement;
        preparedStatement	=	conn.prepareStatement(sql);
        ResultSet	resultSet	=	preparedStatement.executeQuery();
        while	(resultSet.next())	{
            User	loadedUser	=	new	User();
            loadedUser.id	=	resultSet.getInt("id");
            loadedUser.username	=	resultSet.getString("username");
            loadedUser.password	=	resultSet.getString("password");
            loadedUser.email	=	resultSet.getString("email");
            loadedUser.user_group = resultSet.getInt("user_group_id");
            users.add(loadedUser);}
        User[]	uArray	=	new	User[users.size()];
        uArray	=	users.toArray(uArray);
        return	uArray;
    }
    public	void	delete(Connection	conn)	throws	SQLException	{
        if	(this.id	!=	0)	{
            String	sql	=	"DELETE	FROM	User	WHERE	id=	?";
            PreparedStatement	preparedStatement;
            preparedStatement	=	conn.prepareStatement(sql);
            preparedStatement.setInt(1,	this.id);
            preparedStatement.executeUpdate();
            this.id=0;
        }
    }
}
