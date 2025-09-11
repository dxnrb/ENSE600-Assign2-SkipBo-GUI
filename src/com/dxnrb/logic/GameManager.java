/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxnrb.logic;

import com.dxnrb.logic.cards.BuildingPile;
import com.dxnrb.logic.cards.Card;
import com.dxnrb.logic.cards.DrawPile;
import com.dxnrb.logic.players.Player;
import java.util.ArrayList;
import javax.swing.JButton;

/**
 *
 * @author danie
 */
public class GameManager {
    private int gameID;
    private int playerCount = 2;
    
    private ArrayList<Player> players = new ArrayList<>();
    private int currentPlayerIndex = -1; // Initialized. nextTurn() method increments to 0 (starting index of player ArrayList)
    
    // List of buildingPile objects where each list index is a building pile slot on the game's table
    private ArrayList<BuildingPile> buildingPile = new ArrayList<>();
    
    // Constructor of draw pile initializes all cards in the game's deck.
    private DrawPile drawPile = new DrawPile();
    
    public GameManager(ArrayList<String> players, int gameID) {
        this.playerCount = players.size();
        this.gameID = gameID;
        
        // Shuffle draw pile
        this.drawPile.shuffle();
        
        // Initialize players
        for (String name : players) {
            Player p = new Player(name);
            this.players.add(p);
        }
        
        // Distribute cards to players stock piles
        if (this.playerCount <= 4) // 2 to 4 players get 30 cards in Stock Pile
        {
            for (int i = 0; i < 30; i++)
            {
                for (Player player : this.players)
                {
                    player.addToStockPile(this.drawPile.drawCard());
                }
            }
        }
        if (this.playerCount >= 5) // 5 to 6 players get 20 cards in Stock Pile
        {
            for (int i = 0; i < 20; i++)
            {
                for (Player player : this.players)
                {
                    player.addToStockPile(this.drawPile.drawCard());
                }
            }
        }
        
        // Distribute cards to players hands
        for (Player player : this.players)
        {
            player.fillHand(drawPile);
        }
        
        // Create building pile for game instance
        for (int i = 0; i < 4; i++)
        {
            BuildingPile pile = new BuildingPile();
            this.buildingPile.add(pile);
        }
        
        nextTurn(); // Initialize player 1's turn
    }
    
    public Card getDrawPile() { // Testing
        return drawPile.drawCard();
    }
    
    public Player getCurrentPlayer() {
        return this.players.get(currentPlayerIndex);
    }
    
    public ArrayList<Card> getCurrentPlayerHand() {
        return this.players.get(currentPlayerIndex).getPlayerHand();
    }
    
    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % this.players.size();
    }
    
    public ArrayList<BuildingPile> getBuildPile() {
        return this.buildingPile;
    }
    
    
    // Check card can be played
    private boolean playCard(JButton card, JButton pile) {
        // When you get back, need to find the card object related to the JButton
        // Use BuildingPile class logic and old TurnManager logic to check card can be played and remove from player hand etc
        return false;
    }
}
