/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxnrb.logic.players;


import com.dxnrb.logic.GameManager;
import java.util.*;

import com.dxnrb.logic.cards.Card;
import com.dxnrb.logic.cards.DiscardPile;
import com.dxnrb.logic.cards.DrawPile;
import com.dxnrb.logic.cards.EmptyCard;
import com.dxnrb.logic.cards.StockPile;
import jakarta.persistence.*;

/**
 *
 * @author danie
 */
@Entity
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int playerID;
    
    @OneToOne(cascade = CascadeType.ALL)
    private StockPile stockPile = new StockPile();  
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id") // Foreign key in discard pile table
    private ArrayList<DiscardPile> playerDiscardPile = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id") // Foreign key in player hand table
    private ArrayList<Card> playerHand = new ArrayList<>();
    
    @ManyToOne // Foreign key in game_state table
    private GameManager gameManager;
    
    private String name;
    
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
