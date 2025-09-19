/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.dxnrb.database;

import com.dxnrb.logic.cards.DrawPile;
import com.dxnrb.logic.players.Player;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author danie
 */
public class Derby {  
    public static void main(String[] args) {
        startDatabase();
    }
    
    private static ObjectMapper mapper = new ObjectMapper();
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
    
    public static void saveGame(int gameID, int playerCount, int currentTurn, boolean gameCompleted,
                            Integer winningPlayerId, String buildingPilesJson, String drawPileJson) throws SQLException {
        String updateSql = "UPDATE game_state SET " +
                "player_count = ?, " +
                "current_turn = ?, " +
                "game_completed = ?, " +
                "winning_player_id = ?, " +
                "building_piles = ?, " +
                "draw_pile = ?, " +
                "updated_at = CURRENT_TIMESTAMP " +
                "WHERE game_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            updateStmt.setInt(1, playerCount);
            updateStmt.setInt(2, currentTurn);
            updateStmt.setBoolean(3, gameCompleted);

            if (winningPlayerId != null) {
                updateStmt.setInt(4, winningPlayerId);
            } else {
                updateStmt.setNull(4, java.sql.Types.INTEGER);
            }

            updateStmt.setString(5, buildingPilesJson);
            updateStmt.setString(6, drawPileJson);
            updateStmt.setInt(7, gameID);

            int rowsUpdated = updateStmt.executeUpdate();

            // If no game with that ID, insert a new one
            if (rowsUpdated == 0) { 
                String insertSql = "INSERT INTO game_state " +
                        "(player_count, current_turn, game_completed, winning_player_id, " +
                        "building_piles, draw_pile, created_at, updated_at) " +
                        "VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, playerCount);
                    insertStmt.setInt(2, currentTurn);
                    insertStmt.setBoolean(3, gameCompleted);

                    if (winningPlayerId != null) {
                        insertStmt.setInt(4, winningPlayerId);
                    } else {
                        insertStmt.setNull(4, java.sql.Types.INTEGER);
                    }

                    insertStmt.setString(5, buildingPilesJson);
                    insertStmt.setString(6, drawPileJson);

                    insertStmt.executeUpdate();
                }
            }
        }
    }

    
    // Insert one player tied to an existing game_id
    public static void savePlayer(Player player) throws SQLException {
        int gameId = getGameID(); // use the current active game_id

        try (Connection conn = getConnection()) {
            String handJson = mapper.writeValueAsString(player.getPlayerHand());
            String stockJson = mapper.writeValueAsString(player.getStockPile());
            String discardJson = mapper.writeValueAsString(player.getDiscardPileList());

            // Try updating existing player entry
            String updateSql = "UPDATE players " +
                    "SET hand = ?, stock_pile = ?, discard_piles = ? " +
                    "WHERE game_id = ? AND player_name = ?";

            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setString(1, handJson);
                updateStmt.setString(2, stockJson);
                updateStmt.setString(3, discardJson);
                updateStmt.setInt(4, gameId);
                updateStmt.setString(5, player.getPlayerName());

                int rowsAffected = updateStmt.executeUpdate();

                // If no row was updated, insert a new one
                if (rowsAffected == 0) {
                    String insertSql = "INSERT INTO players " +
                            "(game_id, player_name, hand, stock_pile, discard_piles) " +
                            "VALUES (?, ?, ?, ?, ?)";

                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, gameId);
                        insertStmt.setString(2, player.getPlayerName());
                        insertStmt.setString(3, handJson);
                        insertStmt.setString(4, stockJson);
                        insertStmt.setString(5, discardJson);
                        insertStmt.executeUpdate();
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // consider better error handling
        }
    }
    
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:derby:javaDB;create=true";
        String username = "root";
        String password = "010101";
        return DriverManager.getConnection(url, username, password);
    }
    
    public static int getGameID() throws SQLException {
        int gameID = -1;

        String selectSql = "SELECT game_id FROM game_state ORDER BY created_at DESC FETCH FIRST ROW ONLY";

        try (Connection conn = getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql);
             ResultSet rs = selectStmt.executeQuery()) {

            if (rs.next()) {
                // Found an existing game, return its ID
                gameID = rs.getInt("game_id");
            } else {
                // No game found, insert a new row
                String insertSql = "INSERT INTO game_state " +
                        "(player_count, current_turn, game_completed, created_at, updated_at) " +
                        "VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    insertStmt.setInt(1, 2); // default player count
                    insertStmt.setInt(2, -1); // default current turn
                    insertStmt.setBoolean(3, false); // default not completed
                    insertStmt.executeUpdate();

                    try (ResultSet keys = insertStmt.getGeneratedKeys()) {
                        if (keys.next()) {
                            gameID = keys.getInt(1);
                        }
                    }
                }
            }
        }

        return gameID;
    }

    public static String convertPileToJson(Object pile) {
        if (pile == null) return "[]";

        try {
            if (pile instanceof ArrayList<?>) {
                // Treat as building piles
                return mapper.writeValueAsString(pile);
            } else if (pile instanceof DrawPile) {
                // Treat as draw pile
                return mapper.writeValueAsString(((DrawPile) pile).getCards());
            } else {
                throw new IllegalArgumentException("Unsupported pile type: " + pile.getClass());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]"; // fallback
        }
    }
    
    public static void closeDatabase() throws SQLException {
        Connection connection = getConnection();
    if (connection != null) {
        try {
            connection.close();
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
    
    //--- READ METHODS ---//
    
    
    
    //--- WRITE METHODS ---//
    
    
}
