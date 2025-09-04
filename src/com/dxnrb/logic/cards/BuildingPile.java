/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxnrb.logic.cards;

import com.dxnrb.logic.interfaces.CardAddable;
import com.dxnrb.logic.interfaces.CardPeekable;
import com.dxnrb.logic.interfaces.CardRemovable;
import java.util.ArrayList;


/**
 *
 * @author danie
 */
public class BuildingPile extends Pile implements CardAddable, CardRemovable, CardPeekable {
    
    @Override
    public boolean addCard(Card card) {
        // If shoe is empty, a player can only add 1 or a wildcard to start the sequence
        if (this.shoe.isEmpty()) {
            if (card.getCardNumber() == 0 || card.getCardNumber() == 1) {
                this.shoe.add(card);
                return true;
            }
            else
            {
                return false;
            }
        }
        else {
            // If the card being added is the next in the sequence:
            if (card.getCardNumber() == this.shoe.size() + 1) {
                this.shoe.add(card);
                return true;
            }
            // If the card being added is a wildcard then it is valid anywhere in the sequence
            else if ((card.getCardNumber() == 0) && (this.shoe.size() < 12)) {
                this.shoe.add(card);
                return true;
            }
            // If a wildcard is currently at the top of the pile, check the card being played is sequential
            else if ((this.shoe.getLast().getCardNumber() == 0) && (card.getCardNumber() == this.shoe.get(this.shoe.size()-1).getCardNumber()+2))
            {
                this.shoe.add(card);
                return true;
            }
        }
        return false;
}
    
    @Override
    public Card drawCard() {
        return shoe.remove(0);
    }
    
    public ArrayList<Card> clearPile() {
        ArrayList<Card> pileToClear = this.shoe;
        this.shoe.removeAll(shoe);
        return pileToClear;
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
