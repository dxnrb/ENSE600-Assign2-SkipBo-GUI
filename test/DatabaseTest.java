
import com.dxnrb.logic.cards.BuildingPile;
import com.dxnrb.logic.cards.Card;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.ArrayList;

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

//    @Test
//    public void testSerializeDeserializeBuildingPile() throws Exception {
//        // --- Setup a BuildingPile with some cards ---
//        BuildingPile pile = new BuildingPile();
//        ArrayList<Card> cards = new ArrayList<>();
//        cards.add(new Card(0));
//        cards.add(new Card(2));
//        pile.setCards(cards); // Ensure this method exists in Pile
//
//        // --- Serialize to JSON ---
//        String json = mapper.writeValueAsString(pile);
//        System.out.println("Serialized JSON: " + json);
//
//        // --- Deserialize back to a BuildingPile ---
//        BuildingPile deserializedPile = mapper.readValue(json, BuildingPile.class);
//
//        // --- Assertions ---
//        assertNotNull("Deserialized pile should not be null", deserializedPile);
//        assertEquals("Deserialized pile should have 2 cards", 2, deserializedPile.getCards().size());
//        assertEquals("First card should match", 0, deserializedPile.getCards().get(0).getCardNumber());
//        assertEquals("Second card should match", 2, deserializedPile.getCards().get(1).getCardNumber());
//
//        // Optionally, check full/empty flags
//        assertFalse("Pile should not be empty after deserialization", deserializedPile.isEmpty());
//    }
//
//    @Test
//    public void testSerializeDeserializeBuildingPileList() throws Exception {
//        // --- Create multiple BuildingPiles ---
//        ArrayList<BuildingPile> pileList = new ArrayList<>();
//
//        BuildingPile pile1 = new BuildingPile();
//        pile1.setCards(new ArrayList<Card>() {{
//            add(new Card(0));
//            add(new Card(2));
//        }});
//
//        BuildingPile pile2 = new BuildingPile();
//        pile2.setCards(new ArrayList<Card>() {{
//            add(new Card(1));
//        }});
//
//        pileList.add(pile1);
//        pileList.add(pile2);
//
//        // --- Serialize list to JSON ---
//        String jsonList = mapper.writeValueAsString(pileList);
//        System.out.println("Serialized BuildingPile List: " + jsonList);
//
//        // --- Deserialize list ---
//        ArrayList<BuildingPile> deserializedList = mapper.readValue(
//                jsonList, new TypeReference<ArrayList<BuildingPile>>() {}
//        );
//
//        // --- Assertions ---
//        assertNotNull("Deserialized list should not be null", deserializedList);
//        assertEquals("List should have 2 piles", 2, deserializedList.size());
//        assertEquals(2, deserializedList.get(0).getCards().size());
//        assertEquals(1, deserializedList.get(1).getCards().size());
//    }
    
    @Test
    public void testBuildingPileSerialization() throws Exception {
        // --- Create original BuildingPile ---
        BuildingPile pile1 = new BuildingPile();
        pile1.addCard(new Card(0));
        pile1.addCard(new Card(2));

        BuildingPile pile2 = new BuildingPile();
        pile2.addCard(new Card(0));

        BuildingPile pile3 = new BuildingPile();
        pile3.addCard(new Card(1));

        BuildingPile pile4 = new BuildingPile(); // empty pile

        ArrayList<BuildingPile> originalPiles = new ArrayList<>();
        originalPiles.add(pile1);
        originalPiles.add(pile2);
        originalPiles.add(pile3);
        originalPiles.add(pile4);

        // --- Print original piles ---
        System.out.println("Original Building Piles:");
        for (int i = 0; i < originalPiles.size(); i++) {
            System.out.println("Pile " + i + ": " + originalPiles.get(i).getCards());
        }

        // --- Serialize to JSON ---
        String json = mapper.writeValueAsString(originalPiles);
        System.out.println("Serialized JSON: " + json);

        // --- Deserialize from JSON ---
        ArrayList<BuildingPile> deserializedPiles = mapper.readValue(json, new TypeReference<ArrayList<BuildingPile>>() {});
        
        // --- Print deserialized piles ---
        System.out.println("Deserialized Building Piles:");
        for (int i = 0; i < deserializedPiles.size(); i++) {
            System.out.println("Pile " + i + ": " + deserializedPiles.get(i).getCards());
        }

        // --- Assertions ---
        assertEquals("Number of piles should match", originalPiles.size(), deserializedPiles.size());
        for (int i = 0; i < originalPiles.size(); i++) {
            ArrayList<Card> originalCards = originalPiles.get(i).getCards();
            ArrayList<Card> deserializedCards = deserializedPiles.get(i).getCards();
            assertEquals("Pile " + i + " card count should match", originalCards.size(), deserializedCards.size());
            for (int j = 0; j < originalCards.size(); j++) {
                assertEquals("Pile " + i + " card " + j + " number should match",
                        originalCards.get(j).getCardNumber(), deserializedCards.get(j).getCardNumber());
            }
        }
    }
}
