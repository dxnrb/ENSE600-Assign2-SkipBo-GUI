/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.dxnrb.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author danie
 */
public class Derby {  
    public static void main(String[] args) {
        startDatabase();
    }
    
    // When you get back
    // Look into Jackson (JSON library)
    // Think about tables
    // I reckon it will be easier to have a player table with foreign keys to a game table
    // player table looks after cards in player hand, discard, and stock piles, plus their win rate
    // game table looks after build piles, players, checks winners etc, game status etc
    
    //--- DATABASE CALLS ---//
    
    public static void startDatabase() {
        String url = "jdbc:derby:javaDB;create=true"; // "javaDB" will be created if missing

        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("Connected to Derby database.");

            // Initialize schema (create tables if not already created)
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE game_state (" + 
                        "game_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," + 
                        "player1_name VARCHAR(50)," +
                        "player2_name VARCHAR(50)," +
                        "player3_name VARCHAR(50)," +
                        "player4_name VARCHAR(50)," +
                        "player5_name VARCHAR(50)," +
                        "player6_name VARCHAR(50)," +
                        "player1_hand CLOB," +
                        "player2_hand CLOB," +
                        "player3_hand CLOB," +
                        "player4_hand CLOB," +
                        "player5_hand CLOB," +
                        "player6_hand CLOB," +
                        "current_turn INT," +
                        "complete BOOLEAN DEFAULT FALSE," +
                        "winner VARCHAR(50)," +
                        "");
                System.out.println("Table created.");
            } catch (SQLException e) {
                if (e.getSQLState().equals("X0Y32")) {
                    // Table already exists
                    System.out.println("Table already exists.");
                } else {
                    throw e; // some other error
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    //--- READ METHODS ---//
    
    
    
    //--- WRITE METHODS ---//
    
    
}
