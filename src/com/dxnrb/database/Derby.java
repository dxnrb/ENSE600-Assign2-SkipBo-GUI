/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.dxnrb.database;

import com.dxnrb.logic.players.Player;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;
import java.sql.*;

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
        String username = "root";
        String password = "010101";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to Derby database.");
            // Initialize schema (create tables if not already created)
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE game_state (" + 
                        "game_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " + 
                        "player_count INT NOT NULL CHECK (player_count BETWEEN 2 AND 6), " +
                        "current_turn INT NOT NULL, " +
                        "game_completed BOOLEAN DEFAULT FALSE, " +
                        "winning_player_id INT DEFAULT NULL, " +
                        "building_piles CLOB, " +   // JSON array of building piles
                        "draw_pile CLOB, " +        // JSON array of cards
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        "updated_at TIMESTAMP" +
                        ")");
                System.out.println("Table game_state created.");
            } catch (SQLException e) {
                if (e.getSQLState().equals("X0Y32")) {
                    // Table already exists
                    System.out.println("Table game_state already exists");
                }
            }
            
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE players (" + 
                        "player_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " + 
                        "game_id INT NOT NULL, " +
                        "player_name VARCHAR(100) NOT NULL, " +
                        "discard_piles CLOB, " +    // JSON array of discard piles
                        "stock_pile CLOB, " +       // JSON object (cards in stock pile)
                        "hand CLOB, " +             // JSON array of cards
                        "CONSTRAINT fk_game FOREIGN KEY (game_id) REFERENCES game_state(game_id)" +
                        ")");
                System.out.println("Table players created.");
            } catch (SQLException e) {
                if (e.getSQLState().equals("X0Y32")) {
                    // Table already exists
                    System.out.println("Table players already exists");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void savePlayer(Player player, int gameID) throws SQLException {
        try {
            Connection conn = getConnection();
            ObjectMapper mapper = new ObjectMapper();

            String handJson = mapper.writeValueAsString(player.getPlayerHand());
            String stockJson = mapper.writeValueAsString(player.getStockPile());
            String discardJson = mapper.writeValueAsString(player.getDiscardPileList()); // your discard piles class should have getCards() for each pile

            String gs = "INSERT INTO game_state (player_count, current_turn) VALUES (?, ?)";
            PreparedStatement pstm = conn.prepareStatement(gs, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, 2); // e.g., number of players
            pstm.setInt(2, 1);  // e.g., 1
            pstm.executeUpdate();

            // Get the generated game_id
            ResultSet rs = pstm.getGeneratedKeys();
            int gameIDs = 0;
            if (rs.next()) {
                gameIDs = rs.getInt(1);
            }
            rs.close();
            pstm.close();
            
            String sql = "INSERT INTO players (game_id, player_name, hand, stock_pile, discard_piles) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, gameID);
                pstmt.setString(2, player.getPlayerName());
                pstmt.setString(3, handJson);
                pstmt.setString(4, stockJson);
                pstmt.setString(5, discardJson);
                pstmt.executeUpdate();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:derby:javaDB;create=true";
        String username = "root";
        String password = "010101";
        return DriverManager.getConnection(url, username, password);
    }

    
    //--- READ METHODS ---//
    
    
    
    //--- WRITE METHODS ---//
    
    
}
