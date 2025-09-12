/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxnrb.logic;

import com.dxnrb.logic.cards.BuildingPile;
import com.dxnrb.logic.cards.Card;
import com.dxnrb.logic.cards.DiscardPile;
import com.dxnrb.logic.cards.DrawPile;
import com.dxnrb.logic.cards.StockPile;
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
    
    
    // Game specific methods //
    
    public Card getCardFromDrawPile() { 
        return drawPile.drawCard();
    }
    
    public void restockDrawPile(Card card) {
        drawPile.restockCard(card);
    }
    
    public ArrayList<BuildingPile> getBuildPile() {
        return this.buildingPile;
    }
    
    public BuildingPile getBuildPile(int i) { // Overloaded to get BuildingPile within the ArrayList
        return this.buildingPile.get(i);
    }
    
    // ChatGPT showed me some refined logic for letting the game manager be in control of whose turn it is
    // I was asking it to understand how I need to structure my managers in conjunction with the Game class that controls the GUI
    // My first assignment I had a turn manager, but GPT mentioned it is obsolete for the GUI as the "turns" occur with GUI interaction
    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % this.players.size();
    }
    
    // Check card can be played
    private boolean playCard(JButton card, JButton pile) {
        // When you get back, need to find the card object related to the JButton
        // Use BuildingPile class logic and old TurnManager logic to check card can be played and remove from player hand etc
        
        // Because I focused on other stuff today, when you get back:
        // Consider comment above,
        // Design your card flow and handling by having the GUI send enums to this method
        // The Game GUI class can send an enum to infer pile, and an int to infer the pile index
        // This method can then perform the logic to move cards
        // Look into whether the game GUI responds to this methods return statement to display a JOptionPane error
        // Else get this method to do it - only reason for considering this is incase there are different error messages,
        // this method would be the only thing that knows why a card cant be played
        return false;
    }    
    
    
    // Current player specific methods //
    public Player getCurrentPlayer() {
        return this.players.get(currentPlayerIndex);
    }
    
    // Hand:
    public ArrayList<Card> getCurrentPlayerHand() {
        return this.players.get(currentPlayerIndex).getPlayerHand();
    }
    
    public Card getCurrentPlayerHand(int i) { // Overloaded to get card in hand
        return this.players.get(currentPlayerIndex).getPlayerHand().get(i);
    }
    
    // Stock:
    public StockPile getCurrentPlayerStockPile() {
        return this.players.get(currentPlayerIndex).getStockPile();
    }
    
    public Card getCurrentPlayerStockPileCard() {
        return this.players.get(currentPlayerIndex).getStockPile().peek();
    }
    
    // Discard
    public ArrayList<DiscardPile> getCurrentPlayerDiscardPile() {
        return this.players.get(currentPlayerIndex).getDiscardPileList();
    }
    
    public DiscardPile getCurrentPlayerDiscardPile(int i) { // Overloaded to get DiscardPile within the ArrayList
        return this.players.get(currentPlayerIndex).getDiscardPileList().get(i);
    }
}
