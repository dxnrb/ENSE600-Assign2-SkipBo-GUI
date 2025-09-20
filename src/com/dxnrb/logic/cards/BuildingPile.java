/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxnrb.logic.cards;

import com.dxnrb.logic.interfaces.CardAddable;
import com.dxnrb.logic.interfaces.CardPeekable;
import com.dxnrb.logic.interfaces.CardRemovable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 *
 * @author danie
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildingPile extends Pile implements CardAddable, CardRemovable, CardPeekable {
    
    @Override
    public boolean addCard(Card card) {
        // If shoe is empty, a player can only add 1 or a wildcard to start the sequence
        int cardNumber = card.getCardNumber();
        int shoeSize = this.shoe.size();
        
        if (this.shoe.isEmpty()) {
            if (cardNumber == 0 || cardNumber == 1) {
                this.shoe.add(card);
                return true;
            }
            else
            {
                return false;
            }
        }
        
        // If the card being added is the next in the sequence:
        if (cardNumber == shoeSize + 1) {
            this.shoe.add(card);
            return true;
        }
        
        // If the card being added is a wildcard then it is valid anywhere in the sequence
        if ((cardNumber == 0) && (shoeSize < 12)) {
            this.shoe.add(card);
            return true;
        }
        
        // If a wildcard is currently at the top of the pile, check the card being played is sequential
        if ((this.shoe.getLast().getCardNumber() == 0) && (cardNumber == this.shoe.get(shoeSize-1).getCardNumber()+2))
        {
            this.shoe.add(card);
            return true;
        }
        
        return false;
    }
    
    @Override
    public Card drawCard() {
        return shoe.remove(0);
    }
    
    public boolean isFull() {
        if (this.shoe.size() == 12) {
            return true;
        } else {
            return false;
        }
    }
    
    public void clearPile() {
        this.shoe.clear();
    }
    
    @Override
    public Card peek() {
        if (this.shoe.isEmpty())
        {
            return null;
        }
        return this.shoe.getLast();
    }
}
