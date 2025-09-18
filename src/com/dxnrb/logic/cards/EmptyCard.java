/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dxnrb.logic.cards;

import jakarta.persistence.*;

/**
 *
 * @author danie
 */
@Entity
@DiscriminatorValue("EMPTY") // marks this subclass in the Card table
public class EmptyCard extends Card {
    public EmptyCard() {
        super(-1);
    }
}
