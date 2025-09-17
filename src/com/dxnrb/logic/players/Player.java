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
            DiscardPile d = new DiscardPile(i);
            this.playerDiscardPile.add(d);
        }
    }
    
    
    // Player specific
    
    public String getPlayerName() {
        return this.name;
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
    
    
    // Stock
    public void addToStockPile(Card card) {
        this.stockPile.addCard(card);
    }
    
    public StockPile getStockPile() {
        return this.stockPile;
    }
    
    public void removeFromStockPile() {
        this.stockPile.drawCard();
    }
    
    
    // Discard    
    public ArrayList<DiscardPile> getDiscardPileList() {
        return this.playerDiscardPile;
    }
}
