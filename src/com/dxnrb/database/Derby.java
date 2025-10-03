/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.dxnrb.database;

import com.dxnrb.logic.cards.BuildingPile;
import com.dxnrb.logic.cards.Card;
import com.dxnrb.logic.cards.DiscardPile;
import com.dxnrb.logic.cards.DrawPile;
import com.dxnrb.logic.cards.StockPile;
import com.dxnrb.logic.players.Player;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.type.TypeReference;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author danie
 */
public class Derby {  
    public static void main(String[] args) {
        startDatabase();
    }
    
    private static ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    
    //--- DATABASE CALLS ---//
    
    public static void startDatabase() {
        try (Connection conn = getConnection()) {
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
                        "stock_pile CLOB, " +       // JSON array of cards in stock pile
                        "hand CLOB, " +             // JSON array of cards
                        "CONSTRAINT fk_game FOREIGN KEY (game_id) REFERENCES game_state(game_id) ON DELETE CASCADE" +
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
    
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:derby:javaDB;create=true"; // "javaDB" will be created if missing
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
        Connection conn = getConnection();
    if (conn != null) {
        try {
            conn.close();
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
    
    //--- READ METHODS ---//
    
    public static ArrayList<HashMap<String, Object>> readGameStates() throws SQLException {
        ArrayList<HashMap<String, Object>> results = new ArrayList<>();

        String sql = "SELECT game_id, player_count, current_turn, " +
                     "game_completed, winning_player_id, updated_at " +
                     "FROM game_state";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                HashMap<String, Object> row = new HashMap<>();
                row.put("game_id", rs.getInt("game_id"));
                row.put("player_count", rs.getInt("player_count"));
                
                ArrayList<String> players = readPlayersForGame(rs.getInt("game_id"));
                
                int turnIndex = rs.getInt("current_turn");
                if (turnIndex >= 0 && turnIndex < players.size()) {
                    row.put("current_turn", players.get(turnIndex));
                } else {
                    row.put("current_turn", null);
                }

                row.put("game_completed", rs.getBoolean("game_completed"));

                int winnerID = rs.getInt("winning_player_id");
                
                if (rs.wasNull()) {
                    row.put("winning_player", null);
                } else {
                    row.put("winning_player", getPlayerNameByID(winnerID));
                }

                row.put("updated_at", rs.getTimestamp("updated_at"));
                results.add(row);
            }
        }
        return results;
    }

    public static HashMap<String, Object> readGameStateID(int gameID) throws SQLException, JsonProcessingException {
        String sql = "SELECT * FROM game_state WHERE game_id = ?";
        HashMap<String, Object> row = new HashMap<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, gameID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    row.put("game_id", rs.getInt("game_id"));
                    row.put("player_count", rs.getInt("player_count"));
                    row.put("current_turn", rs.getInt("current_turn"));
                    row.put("game_completed", rs.getBoolean("game_completed"));

                    int winnerId = rs.getInt("winning_player_id");
                    row.put("winning_player_id", rs.wasNull() ? null : winnerId);

                    // Deserialize building piles
                    ArrayList<BuildingPile> buildingPiles = mapper.readValue(rs.getString("building_piles"), new TypeReference<ArrayList<BuildingPile>>(){});
                    row.put("building_piles", buildingPiles);

                    // Deserialize draw pile
                    ArrayList<Card> drawPile = mapper.readValue(rs.getString("draw_pile"), new TypeReference<ArrayList<Card>>() {});
                    row.put("draw_pile", drawPile);

                    row.put("updated_at", rs.getTimestamp("updated_at"));
                }
            }
        }

        return row;
    }

    // Read players for a specific game_id
    public static ArrayList<String> readPlayersForGame(int gameID) throws SQLException {
        ArrayList<String> players = new ArrayList<>();

        String sql = "SELECT player_name FROM players WHERE game_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gameID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    players.add(rs.getString("player_name"));
                }
            }
        }
        return players;
    }
    
    public static ArrayList<HashMap<String, Object>> readPlayersPilesForGame(int gameID) throws SQLException, JsonProcessingException {
        String sql = "SELECT * FROM players WHERE game_id = ?";
        ArrayList<HashMap<String, Object>> playerRows = new ArrayList<>();

        try (Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gameID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HashMap<String, Object> row = new HashMap<>();

                    row.put("player_id", rs.getInt("player_id"));
                    row.put("player_name", rs.getString("player_name"));

                    // Hand
                    ArrayList<Card> playerHand = mapper.readValue(rs.getString("hand"), new TypeReference<ArrayList<Card>>(){});
                    row.put("player_hand", playerHand);

                    // Stock pile
                    ArrayList<Card> stockCards = mapper.readValue(rs.getString("stock_pile"), new TypeReference<ArrayList<Card>>(){});
                    StockPile stockPile = new StockPile();
                    for (Card card : stockCards) {
                        stockPile.addCard(card);
                    }
                    row.put("stock_pile", stockPile);

                    // Discard piles
                    ArrayList<DiscardPile> discardPile = mapper.readValue(rs.getString("discard_piles"), new TypeReference<ArrayList<DiscardPile>>(){});
                    row.put("discard_piles", discardPile);

                    playerRows.add(row);
                }
            }
        }

        return playerRows;
    }

    public static String getPlayerNameByID(int playerId) throws SQLException {
        String sql = "SELECT player_name FROM players WHERE player_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("player_name");
                } else {
                    return null; // no player with that ID
                }
            }
        }
    }

    
    //--- WRITE METHODS ---//
    
    public static void saveGame(int gameID, int playerCount, int currentTurn, boolean gameCompleted, Integer winningPlayerId, String buildingPilesJson, String drawPileJson) throws SQLException {
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

    public static void deleteGameAndPlayers(int gameID) throws SQLException {
        String deleteGameSql = "DELETE FROM game_state WHERE game_id = ?";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtGame = conn.prepareStatement(deleteGameSql)) {
                stmtGame.setInt(1, gameID);
                stmtGame.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
    
    public static void savePlayers(ArrayList<Player> players) throws SQLException {
        int gameId = getGameID(); // active game_id

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false); // ensure all players update

            for (Player player : players) {
                String handJson = mapper.writeValueAsString(player.getPlayerHand());
                String stockJson = mapper.writeValueAsString(player.getStockPile().getCards());
                String discardJson = mapper.writeValueAsString(player.getDiscardPileList());

                // If the Player already has an ID, try updating
                if (player.getPlayerID() != null) {
                    String updateSql = "UPDATE players " +
                            "SET player_name = ?, hand = ?, stock_pile = ?, discard_piles = ? " +
                            "WHERE player_id = ? AND game_id = ?";

                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, player.getPlayerName());
                        updateStmt.setString(2, handJson);
                        updateStmt.setString(3, stockJson);
                        updateStmt.setString(4, discardJson);
                        updateStmt.setInt(5, player.getPlayerID());
                        updateStmt.setInt(6, gameId);

                        int rowsAffected = updateStmt.executeUpdate();

                        // If nothing was updated, insert instead
                        if (rowsAffected == 0) {
                            insertNewPlayer(conn, gameId, player, handJson, stockJson, discardJson);
                        }
                    }
                } else {
                    // No player_id yet -> must insert
                    insertNewPlayer(conn, gameId, player, handJson, stockJson, discardJson);
                }
            }

            conn.commit(); // all or nothing
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // Helper to insert player and assign new ID
    private static void insertNewPlayer(Connection conn, int gameId, Player player,
                                        String handJson, String stockJson, String discardJson) throws SQLException {
        String insertSql = "INSERT INTO players (game_id, player_name, hand, stock_pile, discard_piles) " +
                           "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            insertStmt.setInt(1, gameId);
            insertStmt.setString(2, player.getPlayerName());
            insertStmt.setString(3, handJson);
            insertStmt.setString(4, stockJson);
            insertStmt.setString(5, discardJson);
            insertStmt.executeUpdate();

            // Get generated player_id back
            try (ResultSet keys = insertStmt.getGeneratedKeys()) {
                if (keys.next()) {
                    player.setPlayerID(keys.getInt(1)); // store it in Player
                }
            }
        }
    }
}
