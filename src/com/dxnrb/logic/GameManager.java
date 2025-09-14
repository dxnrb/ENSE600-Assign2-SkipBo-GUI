/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxnrb.logic;

import com.dxnrb.logic.cards.BuildingPile;
import com.dxnrb.logic.cards.Card;
import com.dxnrb.logic.cards.DiscardPile;
import com.dxnrb.logic.cards.DrawPile;
import com.dxnrb.logic.cards.EmptyCard;
import com.dxnrb.logic.cards.Pile;
import com.dxnrb.logic.cards.StockPile;
import com.dxnrb.logic.players.Player;
import java.util.ArrayList;


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
        getCurrentPlayer().fillHand(drawPile);
    }
    
    public void restockDrawPile() {
        for (BuildingPile pile : this.buildingPile) {
            if (pile.isFull()) {
                for (Card card : pile.getCards()) {
                    this.drawPile.restockCard(card);
                }
                pile.clearPile();
            }
        }
    }
    
    // Check card can be played
    public boolean playCard(Card card, Pile toPile) {
        if (toPile instanceof BuildingPile buildingPile) { // If card is being played to build pile
            if (buildingPile.addCard(card)) {
                getCurrentPlayer().removeFromPlayerHand(card);
                restockDrawPile();
                return true;
            }
            else {
                return false;
            }
        }
        if (toPile instanceof DiscardPile discardPile) { // If card is being played to discard pile
            discardPile.addCard(card);
            getCurrentPlayer().removeFromPlayerHand(card);
            restockDrawPile();
            return true;
        }
        return false;
    }
    public boolean playCard(Pile fromPile, BuildingPile toPile) { // Overload for playing from stock or discard pile
        if (fromPile instanceof StockPile stockPile) { // Card being played from stock pile
            if (toPile.addCard(stockPile.peek())) {
                getCurrentPlayer().removeFromStockPile();
                restockDrawPile();
                return true;
            }
            else {
                return false;
            }
        }  
        if (fromPile instanceof DiscardPile discardPile) { // Card being played from discard pile
            if (toPile.addCard(discardPile.peek())) {
                discardPile.drawCard();
                restockDrawPile();
                return true;
            }
            else {
                return false;
            }
        }
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
    
    public void isPlayerHandEmpty() { // Refills hand if ArrayList is empty
        int i = 0;
        for (Card card : getCurrentPlayer().getPlayerHand()) {
            if (card instanceof EmptyCard) {
                i++;
            }
        }
        if (i == 5) {
            getCurrentPlayer().fillHand(drawPile);
        }
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
