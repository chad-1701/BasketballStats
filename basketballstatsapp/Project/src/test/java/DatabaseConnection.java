import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DatabaseConnection {
    private final String DbUrl = "jdbc:sqlite:chasw.sqlite";
    @Test
    void ValidConnection() throws SQLException {
        String FilePath = "chasw.sqlite";
        File file = new File(FilePath);
        Connection connection = DriverManager.getConnection(DbUrl);
        Assert.assertTrue(connection.isValid(100));
        Assert.assertTrue(file.exists());
        connection.close();

    }

    @Test
    void InvalidConnection() throws SQLException {
        String FilePath = "InvalidDB.sqlite";
        File file = new File(FilePath);
        Connection connection = DriverManager.getConnection(DbUrl);
        Assert.assertTrue(connection.isValid(100));
        Assert.assertFalse(file.exists());
        connection.close();

    }
}
