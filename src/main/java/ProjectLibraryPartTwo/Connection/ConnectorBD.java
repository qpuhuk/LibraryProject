package ProjectLibraryPartTwo.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectorBD {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Library";
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "Boybot007!");
        return DriverManager.getConnection(url, properties);
    }
}
