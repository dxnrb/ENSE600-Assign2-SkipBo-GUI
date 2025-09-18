/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.dxnrb.logic.GameManager;
import com.dxnrb.logic.cards.BuildingPile;
import com.dxnrb.logic.cards.Card;
import com.dxnrb.logic.cards.EmptyCard;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author danie
 */
public class GameManagerTest {
    
    @Test
    public void testNextTurnCyclesPlayers() throws SQLException { // Use of asserstEqual
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
    public void testRestocktoDrawPile() throws SQLException { // Use of assertsTrue
        // This test ensures that when a build pile is full it is cleared.
        // The cleared cards are shuffled and returned to the draw pile.
        // This is necessary so that the draw pile is never empty, and gives the illusion
        // of an endless draw pile, because the game only has 144 cards at all times.
        
        // Initialize game manager class
        ArrayList<String> names = new ArrayList<>();
        names.add("Player 1");
        names.add("Player 2");
        GameManager gameManager = new GameManager(names, 1);
        
        BuildingPile pile = gameManager.getBuildPile(0); // Fill one of the games' building piles
        ArrayList<Card> restockingPile = pile.getCards(); // Reference these card objects to compare against draw pile after restock
        for (int i = 1; i <=12; i++) {
            pile.addCard(new Card(i)); // Add to the building pile in sequential order
        }
        
        // Confirm the pile is ready to restock
        assertTrue("Pile should be full", gameManager.getBuildPile(0).isFull());
        
        // Restock the cards
        gameManager.restockDrawPile();
        
        // Confirm the cards from the building pile are back in the draw pile
        ArrayList<Card> drawPile = gameManager.getDrawPile().getCards();
        for (Card card : restockingPile) {
            assertTrue("Draw pile should contain card: " + card, drawPile.contains(card));
        }
    }
    
    @Test
    public void testCardCanBePlayed() throws SQLException {
        // Initialize game manager class
        ArrayList<String> names = new ArrayList<>();
        names.add("Player 1");
        names.add("Player 2");
        GameManager gameManager = new GameManager(names, 1);
        
        // Test playCard() method works (Card, Pile)
        
        // Game manager will call fillHand() when setting up players
        // Check if this random distribution can be played
        // Test may fail if hand contains a card next in sequence, if so, retest.
        // Uncomment for loop if you are happy retesting a few times to see the results change
//        for (int i = 0; i < 5; i++) {
//            assertFalse("One of the cards in the random hand test could be played; retest.", gameManager.playCard(gameManager.getCurrentPlayerHand(i), gameManager.getBuildPile(0)));
//        }
        
        // Rig players hand to be sequential
        gameManager.getCurrentPlayerHand().clear();
        for (int i = 1; i <= 5; i++) {
            gameManager.getCurrentPlayerHand().add(new Card(i));
            assertTrue("Next card in sequence played should be accepted", gameManager.playCard(gameManager.getCurrentPlayerHand(i-1), gameManager.getBuildPile(1)));
        }
        
        // Test for playing a card to discard pile is redundant becase it will accept any card,
        // However, this is a quick test to see how it handles EmptyCard objects and that playCard() method functions as intended
        gameManager.getCurrentPlayerHand().clear();
        gameManager.getCurrentPlayerHand().add(new EmptyCard());
        assertTrue("Discard piles can accept any card, something went wrong", gameManager.playCard(gameManager.getCurrentPlayerHand(0), gameManager.getCurrentPlayerDiscardPile(3)));
        
        
        
        // Test that overloaded playCard() method works (Pile, Pile)
        
        // Test playing from stock pile
        gameManager.getCurrentPlayerStockPile().addCard(new Card(1));
        assertTrue("Could not play from stock pile. Is the top card of stock pile the next in sequence?", gameManager.playCard(gameManager.getCurrentPlayerStockPile(), gameManager.getBuildPile(2)));
        
        // Test playing from discard pile
        gameManager.getCurrentPlayerDiscardPile(0).addCard(new Card(1));
        assertTrue("Could not play from discard pile. Is the top card of the discard pile the next in sequence?", gameManager.playCard(gameManager.getCurrentPlayerDiscardPile(0), gameManager.getBuildPile(3)));
    }
}
