/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxnrb.logic.cards;

import com.dxnrb.logic.players.Player;
import jakarta.persistence.*;

/**
 *
 * @author danie
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "card_type") // identifies subclass in one table
public class Card {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CardID;
    
    @ManyToOne
    private Player owner;
    
    @ManyToOne
    private BuildingPile buildingPile;
    
    @ManyToOne
    private DiscardPile discardPile;
    
    @ManyToOne
    private StockPile stockPile;
    
    
    
    private int cardNumber;
    
    public Card(int value) {
        this.cardNumber = value;
    }

    public int getCardNumber() {
        return cardNumber;
    }
    
    // Method used for debugging
    public void printInfo() {
        if (cardNumber == 0)
        {
            System.out.println("Card: Wildcard");
        }
        else
        {
            System.out.println("Card: " + cardNumber);
        }
    }
}
