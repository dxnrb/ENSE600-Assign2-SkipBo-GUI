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

/**
 *
 * @author danie
 */
public class GameManager {
    private int gameID;
    private int playerCount = 2;
    
    private ArrayList<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;
    
    private ArrayList<BuildingPile> buildingPile = new ArrayList<>();
    
    private DrawPile drawPile = new DrawPile();
    
    public GameManager(ArrayList<String> players, int gameID) {
        this.playerCount = players.size();
        this.gameID = gameID;
        
        for (String name : players) {
            Player p = new Player(name);
            this.players.add(p);
        }
    }
    
    public void initializeGame() {
        this.drawPile.shuffle();
        
        // Create building pile for game instance
        for (int i = 0; i < 4; i++)
        {
            BuildingPile pile = new BuildingPile();
            this.buildingPile.add(pile);
        }
    }
    
    public Card getDrawPile() { // TEsting
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
}
