package com.example;




import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String dbDirectory = "database_files/";
        String dbName = "users.db";
        String dbPath = "jdbc:sqlite:" + dbDirectory + dbName;

        // Pripojenie k databáze
        try (Connection connection = DriverManager.getConnection(dbPath)) {
            if (connection != null) {
                System.out.println("Pripojené k databáze");

                // Vytvorenie tabuľky user_info
                String createTableSQL = "CREATE TABLE IF NOT EXISTS user_info (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name VARCHAR(100), " +
                        "surname VARCHAR(100), " +
                        "date_of_birth VARCHAR(100), " +
                        "email VARCHAR(100), " +
                        "registration_date VARCHAR(100), " +
                        "role_id INTEGER, " +
                        "number_of_books_uploaded INTEGER)";

                try (Statement statement = connection.createStatement()) {
                    statement.execute(createTableSQL);
                    System.out.println("Tabuľka user_info bola vytvorená.");
                }

                // Vloženie reálnych dát pre 7 používateľov
                String[][] usersData = {
                        {"Anna", "Nováková", "1990-02-15", "anna.novakova@example.com", "2024-11-12", "0", "5"},
                        {"Ján", "Kováč", "1988-05-22", "jan.kovac@example.com", "2024-11-12", "1", "8"},
                        {"Mária", "Horváthová", "1992-07-10", "maria.horvathova@example.com", "2024-11-12", "0", "3"},
                        {"Peter", "Szabo", "1991-11-03", "peter.szabo@example.com", "2024-11-12", "0", "2"},
                        {"Zuzana", "Králová", "1985-03-09", "zuzana.kralova@example.com", "2024-11-12", "1", "12"},
                        {"Milan", "Varga", "1993-06-18", "milan.varga@example.com", "2024-11-12", "0", "6"},
                        {"Lucia", "Urbanová", "1987-08-24", "lucia.urbanova@example.com", "2024-11-12", "1", "10"}
                };

                String insertSQL = "INSERT INTO user_info (name, surname, date_of_birth, email, registration_date, role_id, number_of_books_uploaded) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                    for (String[] userData : usersData) {
                        pstmt.setString(1, userData[0]);
                        pstmt.setString(2, userData[1]);
                        pstmt.setString(3, userData[2]);
                        pstmt.setString(4, userData[3]);
                        pstmt.setString(5, userData[4]);
                        pstmt.setInt(6, Integer.parseInt(userData[5]));
                        pstmt.setInt(7, Integer.parseInt(userData[6]));
                        pstmt.executeUpdate();
                    }
                    System.out.println("Pridaných 7 používateľov s realistickými menami.");
                }

                // Vloženie údajov 8. používateľa z konzoly
                System.out.print("Zadajte meno používateľa: ");
                String name = sc.nextLine();
                System.out.print("Zadajte priezvisko používateľa: ");
                String surname = sc.nextLine();
                System.out.print("Zadajte dátum narodenia (YYYY-MM-DD): ");
                String dateOfBirth = sc.nextLine();
                System.out.print("Zadajte email: ");
                String email = sc.nextLine();
                System.out.print("Zadajte dátum registrácie (YYYY-MM-DD): ");
                String regDate = sc.nextLine();
                System.out.print("Zadajte role ID (0 pre používateľa, 1 pre admina): ");
                int roleId = Integer.parseInt(sc.nextLine());
                System.out.print("Zadajte počet nahratých kníh: ");
                int numBooks = Integer.parseInt(sc.nextLine());

                try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                    pstmt.setString(1, name);
                    pstmt.setString(2, surname);
                    pstmt.setString(3, dateOfBirth);
                    pstmt.setString(4, email);
                    pstmt.setString(5, regDate);
                    pstmt.setInt(6, roleId);
                    pstmt.setInt(7, numBooks);
                    pstmt.executeUpdate();
                    System.out.println("Pridaný 8. používateľ z konzoly.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Chyba: " + e.getMessage());
        }
    }
}
