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
    
    public Card chooseFromHand(Scanner selector, DrawPile drawPile) {
        // Input validation
        while (true)
        {
            // Output available cards currently in hand
            for (int i = 0; i < playerHand.size(); i++)
            {
                System.out.println("(" + (i+1) + ") " + playerHand.get(i).getCardNumber());
            }
            System.out.println("(x) Go back");
            
            try
            {
                // Go back case
                String input = selector.next();
                if (input.equalsIgnoreCase("x"))
                {
                    // Returning null cancels selection
                    return null;
                }
                
                // Integer case
                int index = Integer.parseInt(input)-1;
                if (index > -1 && index < playerHand.size())
                {
                    // Return card to turn manager
                    return playerHand.get(index);
                }
                else
                {
                    System.out.println("\nPlease enter a number between 1 and " + playerHand.size());
                }
            }
            catch (InputMismatchException | NumberFormatException | IndexOutOfBoundsException e)
            {
                System.out.println("\nPlease enter a valid number or 'x' to go back.");
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
    
    public Card playFromStock() {
        return this.stockPile.peek();
    }
    
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
    
    public Card chooseFromDiscard(Scanner selector, DrawPile drawPile) {
        while (true)
        {
            // Output available decks currently in discard pile
            for (int i = 0; i < this.playerDiscardPile.size(); i++)
            {
                if (!this.playerDiscardPile.get(i).isEmpty())
                {
                    System.out.println("(" + (i+1) + ") " + this.playerDiscardPile.get(i).peek().getCardNumber());
                }
                else
                {
                    System.out.println("(" + (i+1) + ") Empty");
                }
                
            }
            System.out.println("(x) Go back");
            
            try
            {
                // Go back case
                String input = selector.next();
                if (input.equalsIgnoreCase("x"))
                {
                    // Returning null cancels selection
                    return null;
                }
                
                // Integer case
                int index = Integer.parseInt(input)-1;
                if (index > -1 && index < this.playerDiscardPile.size())
                {
                    if (this.playerDiscardPile.get(index).isEmpty())
                    {
                        System.out.println("There is no card to play from this pile.");
                        continue;
                    }
                    
                    // Return card to turn manager
                    return playerDiscardPile.get(index).peek();
                }
                else
                {
                    System.out.println("\nPlease enter a number between 1 and " + this.playerDiscardPile.size());
                }
            }
            catch (InputMismatchException | NumberFormatException | IndexOutOfBoundsException e)
            {
                System.out.println("\nPlease enter a valid number or 'x' to go back.");
            }
        }
    }
    
    public void addToDiscardPile(Card card, int index) {
        this.playerDiscardPile.get(index).addCard(card);
    }
    
    public void removeFromDiscardPile(int index) {
        this.playerDiscardPile.get(index).drawCard();
    }
    
    public ArrayList<DiscardPile> getDiscardPileList() {
        return this.playerDiscardPile;
    }
}
