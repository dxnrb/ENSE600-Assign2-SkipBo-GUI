/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.dxnrb.logic.GameManager;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author danie
 */
public class GameManagerTest {
    
    @Test
    public void testNextTurnCyclesPlayers() {
        // This function tests some new logic that ChatGPT showed me
        // It uses modulo to reset the turn count to 0 once the final player is reached
        // I made this test to understand if I need to initialize the int variable that tracks the index of the current player
        // I wanted the first name in the "Start Game" initializer of the GUI to be the first to play
        // I was unsure if making the int variable -1 would cause problems so this test confirmed to me if it would work or not
        
        // The Game class must be initialized with a ArrayList<String> so it can create a GameManager class
        // The code below mimics this setup
        ArrayList<String> names = new ArrayList<>();
        names.add("Player 1");
        names.add("Player 2");
        GameManager gameManager = new GameManager(names, 1);
        
        // This test returns true if Player 1 is the first turn
        assertEquals("Player 1", gameManager.getCurrentPlayer().getPlayerName());
        
        // Calling nextTurn() increments the int variable within the GameManager class
        gameManager.nextTurn();
        // Test returns true if the next name is now the current player
        assertEquals("Player 2", gameManager.getCurrentPlayer().getPlayerName());
        
        // Calling nextTurn() should reset to index 0 of the ArrayList<String> of names
        gameManager.nextTurn();
        // Test returns true if the next turn is the first player within the ArrayList
        assertEquals("Player 1", gameManager.getCurrentPlayer().getPlayerName());
    }
    
    @Test
    public void testCardCanBePlayed() {
        
    }
}
