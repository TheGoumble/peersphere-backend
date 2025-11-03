package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final String SECRETS_FILE = "secrets";
    private static String url;
    private static String user;
    private static String password;

    static {
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream(SECRETS_FILE);
            props.load(fis);

            url = props.getProperty("DB_URL");
            user = props.getProperty("DB_USER");
            password = props.getProperty("DB_PASSWORD");

            System.out.println("Loaded secrets successfully.");

        } catch (IOException e) {
            System.err.println("Could not load secrets file. Make sure it’s in the same folder as pom.xml.");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}