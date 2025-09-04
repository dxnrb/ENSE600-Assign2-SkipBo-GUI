/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxnrb.logic.cards;

/**
 *
 * @author danie
 */
public class Card {
    
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
