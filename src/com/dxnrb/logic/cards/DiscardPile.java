/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxnrb.logic.cards;

import com.dxnrb.logic.interfaces.CardAddable;
import com.dxnrb.logic.interfaces.CardRemovable;
import com.dxnrb.logic.interfaces.CardPeekable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author danie
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscardPile extends Pile implements CardAddable, CardRemovable, CardPeekable {
    
    @Override
    public boolean addCard(Card card) {
        this.shoe.add(card);
        return true;
    }
    
    @Override
    public Card drawCard() {
        return shoe.removeLast();
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
