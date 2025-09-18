/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxnrb.logic.cards;

import com.dxnrb.logic.interfaces.CardAddable;
import com.dxnrb.logic.interfaces.CardRemovable;
import com.dxnrb.logic.interfaces.CardPeekable;
import jakarta.persistence.*;
import java.util.ArrayList;

/**
 *
 * @author danie
 */
@Entity
public class DiscardPile extends Pile implements CardAddable, CardRemovable, CardPeekable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @OneToMany(cascade = CascadeType.ALL)
    private ArrayList<Card> shoe = new ArrayList<>();
    
    protected int index;
    
    public DiscardPile(int index) {
        this.index = index;
    }
    
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
