/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxnrb.logic.players;


import java.util.*;

import com.dxnrb.logic.cards.Card;
import com.dxnrb.logic.cards.DiscardPile;
import com.dxnrb.logic.cards.DrawPile;
import com.dxnrb.logic.cards.EmptyCard;
import com.dxnrb.logic.cards.StockPile;

/**
 *
 * @author danie
 */
public class Player {
    private String name;
    private Integer playerID;
    
    private StockPile stockPile = new StockPile();    
    private ArrayList<DiscardPile> playerDiscardPile = new ArrayList<>();
    private ArrayList<Card> playerHand = new ArrayList<>();
    
    public Player(String name) {
        this.name = name;

        // Initialize players hand
        for (int i = 0; i < 5; i++) {
            this.playerHand.add(new EmptyCard());
        }
        
        // Initialize players discard pile 
        for (int i = 0; i < 4; i++)
        {
            DiscardPile d = new DiscardPile();
            this.playerDiscardPile.add(d);
        }
    }
    
    public Player(String name, int ID) {
        this.name = name;
        this.playerID = ID;
    }
    
    
    // Player specific
    
    public String getPlayerName() {
        return this.name;
    }
    
    public void setPlayerID(int id) {
        this.playerID = id;
    }
    
    public Integer getPlayerID() {
        return this.playerID;
    }
    
    
    // Hand
    
    public void fillHand(DrawPile drawPile) {
        for (int i = 0; i < playerHand.size(); i++) {
            if (playerHand.get(i) instanceof EmptyCard) {
                this.playerHand.set(i, drawPile.drawCard());
            }
        }
    }
    
    public void removeFromPlayerHand(Card card) {
        int index = this.playerHand.indexOf(card);
        this.playerHand.set(index, new EmptyCard());
    }
    
    public ArrayList<Card> getPlayerHand() {
        return this.playerHand;
    }
    
    public void setPlayerHand(ArrayList<Card> hand) { // Setters now needed for resuming game feature
        this.playerHand = hand;
    }
    
    
    // Stock
    public void addToStockPile(Card card) {
        this.stockPile.addCard(card);
    }
    
    public StockPile getStockPile() {
        return this.stockPile;
    }
    
    public void setStockPile(StockPile stockPile) {
        this.stockPile = stockPile;
    }
    
    public void removeFromStockPile() {
        this.stockPile.drawCard();
    }
    
    
    // Discard    
    public ArrayList<DiscardPile> getDiscardPileList() {
        return this.playerDiscardPile;
    }
    
    public void setDiscardPileList(ArrayList<DiscardPile> discardPile) {
        this.playerDiscardPile = discardPile;
    }
}
