package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    public static Connection connect(String database) throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/"+ database +
                        "?useUnicode=true"+
                        "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"+
                        "&useSSL=false",
                "root",
                "coderslab");
    }
}
