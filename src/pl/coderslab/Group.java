package pl.coderslab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Group {

    private int id;
    private String name;

    public Group(){}
    public Group(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void saveToDB(Connection conn) throws SQLException {
        if (this.id==0){
            String sql = "INSERT INTO User_group(name) VALUES (?)";
            String	generatedColumns[]	=	{	"ID"	};
            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.name);
            preparedStatement.executeUpdate();
            ResultSet rs	=	preparedStatement.getGeneratedKeys();
            if	(rs.next())	{
                this.id	=	rs.getInt(1);
            }
        }else{
            String	sql	=	"UPDATE	User_group	SET	name=?	where	id	=	?";
            PreparedStatement	preparedStatement;
            preparedStatement	=	conn.prepareStatement(sql);
            preparedStatement.setString(1,	this.name);
            preparedStatement.executeUpdate();
        }
    }

    static public Group loadGroupById(Connection conn, int id) throws SQLException{
        String sql = "SELECT * FROM User_group WHERE id=?";
        PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()){
            Group foundGrounp = new Group();
            foundGrounp.id = rs.getInt("id");
            foundGrounp.name = rs.getString("name");
            return foundGrounp;
        }
        return null;
    }
    static public Group[] loadAllGroups(Connection conn) throws SQLException{
        String sql = "SELECT * FROM User_group";
        PreparedStatement preparedStatemnet;
        preparedStatemnet = conn.prepareStatement(sql);
        ArrayList<Group> groups = new ArrayList<Group>();
        ResultSet rs = preparedStatemnet.executeQuery();
        while (rs.next()){
            Group foundGroup = new Group();
            foundGroup.name = rs.getString("name");
            foundGroup.id = rs.getInt("id");
            groups.add(foundGroup);
        }
        Group[]	gArray	=	new	Group[groups.size()];
        gArray	=	groups.toArray(gArray);
        return	gArray;
    }
    public	void	delete(Connection	conn)	throws	SQLException	{
        if	(this.id	!=	0)	{
            String	sql	=	"DELETE	FROM	User_group	WHERE	id=	?";
            PreparedStatement	preparedStatement;
            preparedStatement	=	conn.prepareStatement(sql);
            preparedStatement.setInt(1,	this.id);
            preparedStatement.executeUpdate();
            this.id=0;
        }
    }
}
