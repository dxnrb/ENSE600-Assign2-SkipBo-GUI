/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxnrb.logic.cards;

import java.util.Collections;
import com.dxnrb.logic.interfaces.CardRemovable;
import com.dxnrb.logic.interfaces.CardShufflable;
import com.dxnrb.logic.interfaces.CardAddable;

/**
 *
 * @author danie
 */
public class DrawPile extends Pile implements CardRemovable, CardShufflable, CardAddable {
    
    public DrawPile() {
        // Deck contains 144 cards numbered 1 through 12
        for (int i = 1; i < 13; i++)
        {
            for (int j = 1; j < 13; j++)
            {
                Card card = new Card(j);
                this.shoe.add(card);
            }
        }
        // Deck contains 18 wildcards. 0 parsed to constructor creates wildcard.
        for (int i = 0; i < 18; i++ )
        {
            Card card = new Card(0);
            this.shoe.add(card);
        }
    }
    
    @Override
    public void shuffle() {
        Collections.shuffle(shoe);
    }
    
    @Override
    public boolean addCard(Card card) {
        this.shoe.add(card);
        return true;
    }
    
    public void restockCard(Card card) {
        this.shoe.addLast(card);
    }
    
    @Override
    public Card drawCard() {
        return shoe.removeFirst();
    }
}
