
import com.dxnrb.database.Derby;
import com.dxnrb.logic.cards.BuildingPile;
import com.dxnrb.logic.cards.Card;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import org.junit.Test;

import java.util.ArrayList;
import java.sql.*;

import static org.junit.Assert.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author danie
 */
public class DatabaseTest {
    
    private final ObjectMapper mapper = new ObjectMapper();    
    
    @Test
    public void testDatabaseReadAndWrite() throws SQLException, JsonProcessingException {
        // This test was my initial attempt to create a local database
        // I wanted to be sure I could open a database,
        // create a table, write to the table, then read the values inserted into the table
        // I used simple assert statements to ensure that the data I got back
        // did not match the initialised test variables.
        
        // Open a database in memory (Wont create in project folder)
        Connection conn = DriverManager.getConnection("jdbc:derby:memory:TestDB;create=true");
        
        // Create table
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE test_game (" +
                "game_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                "player_name VARCHAR(20))");
        
        // Write entry
        String insertSql = "INSERT INTO test_game " +
                        "(player_name) " +
                        "VALUES (?)";
        
        PreparedStatement insertStmt = conn.prepareStatement(insertSql);
        insertStmt.setString(1, "Test Player");
        insertStmt.executeUpdate();
        
        // Read entry
        String selectSql = "SELECT * FROM test_game FETCH FIRST ROW ONLY";
        PreparedStatement selectStmt = conn.prepareStatement(selectSql);
        ResultSet rs = selectStmt.executeQuery();
        
        // Initialised variables to compare with database read output
        int gameID = 0;
        String playerName = null;
        
        if(rs.next()) {
            gameID = rs.getInt("game_id");
            playerName = rs.getString("player_name");
        }
        
        assertNotEquals("game_id should not be what it was initialised with", 0, gameID);
        assertEquals("player_name should be 'Test Player'", playerName, "Test Player");
        
        // Safely close all database objects
        rs.close();
        selectStmt.close();
        insertStmt.close();
        stmt.close();
        conn.close();
        
    }
    
    
    @Test
    public void testBuildingPileSerialization() throws Exception {
        // This test makes sure the Jackson serialization I will use in
        // my Derby class operates how I need it.
        // I also used System.out to visualise how the ObjectMapper works so
        // I knew how to structure my logic in my program.
        
        
        // Create original BuildingPile
        // Remember that adding to building pile has to be done in numerical order
        BuildingPile pile1 = new BuildingPile();
        pile1.addCard(new Card(1));
        pile1.addCard(new Card(2));
        pile1.addCard(new Card(3));

        BuildingPile pile2 = new BuildingPile();
        pile2.addCard(new Card(0));
        pile2.addCard(new Card(2));
        pile2.addCard(new Card(3));

        BuildingPile pile3 = new BuildingPile();
        pile3.addCard(new Card(1));
        pile3.addCard(new Card(0));
        pile3.addCard(new Card(3));

        BuildingPile pile4 = new BuildingPile(); // empty pile

        // Mimic GameManager building pile
        ArrayList<BuildingPile> originalPiles = new ArrayList<>();
        originalPiles.add(pile1);
        originalPiles.add(pile2);
        originalPiles.add(pile3);
        originalPiles.add(pile4);

        // Print original piles
        System.out.println("Original Building Piles:");
        for (int i = 0; i < originalPiles.size(); i++) {
            System.out.print("Pile " + i + ": [ ");
            for (Card card : originalPiles.get(i).getCards()) {
                System.out.print(card.getCardNumber() + " ");
            }
            System.out.print("]\n");
        }

        // Serialize to JSON
        String json = mapper.writeValueAsString(originalPiles);
        System.out.println("Serialized JSON: " + json);

        // Deserialize from JSON
        ArrayList<BuildingPile> deserializedPiles = mapper.readValue(json, new TypeReference<ArrayList<BuildingPile>>() {});
        
        // Confirm deserialized piles match original piles
        for (int i = 0; i < deserializedPiles.size(); i++) {
            int j = 0;
            for (Card card : deserializedPiles.get(i).getCards()) {
                assertEquals("Pile " + i + " card count should match", card.getCardNumber(), originalPiles.get(i).getCards().get(j++).getCardNumber());
            }
        }
        
        // Print deserialized piles
        System.out.println("Deserialized Building Piles:");
        for (int i = 0; i < deserializedPiles.size(); i++) {
            System.out.print("Pile " + i + ": [ ");
            for (Card card : deserializedPiles.get(i).getCards()) {
                System.out.print(card.getCardNumber() + " ");
            }
            System.out.print("]\n");
        }
    }
}
