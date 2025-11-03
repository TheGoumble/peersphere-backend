package util;

import util.DatabaseConnection;
import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Connected successfully to peersphere_db");
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}