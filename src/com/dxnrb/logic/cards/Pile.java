/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxnrb.logic.cards;

import java.util.*;

/**
 *
 * @author danie
 */

public abstract class Pile {
    
    protected ArrayList<Card> shoe = new ArrayList<>();
    
    public int getSize() {
        return shoe.size();
    }
    
    public ArrayList<Card> getCards() {
        ArrayList<Card> countShoe = new ArrayList<>();
        for (Card card : shoe)
        {
            countShoe.add(card);
        }
        return countShoe;
    }
    
    public int getCardIndex(Card card) {
        return this.shoe.indexOf(card);
    }
    
    public boolean isEmpty() {
        if (shoe.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }    
}
