/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.dxnrb.ui;

import com.dxnrb.logic.GameManager;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author danie
 */
public class Game extends javax.swing.JFrame {

    GameManager gameManager;
    private JButton[] handPiles;
    private JButton[] discardPiles;
    private JButton[] buildPiles;
    
    // Enum to control GUI card flow states
    private enum PlayerAction {
        NONE,
        SELECT_HAND,
        SELECT_STOCK,
        SELECT_DISCARD
    }
    private PlayerAction currentAction = PlayerAction.NONE;
    private JButton selectedButton = null;
    
    
    
    /**
     * Creates new form Game
     */
    public Game(ArrayList<String> names) {
        initComponents();
        
        // Array of buttons makes it easier to iterate over to update GUI
        handPiles = new JButton[] {handPile1, handPile2, handPile3, handPile4, handPile5};
        discardPiles = new JButton[] {discardPile1, discardPile2, discardPile3, discardPile4};
        buildPiles = new JButton[] {buildPile1, buildPile2, buildPile3, buildPile4};
        
        gameManager = new GameManager(names, 1); // Change ID parameter when doing database
        
        renderNextPlayer();
        
        // The Game JFrame should only focus on making calls to the game manager to update moves for a players turn
        // This JFrame class (GUI) needs to be kept separate from the game logic
        // Create methods in the game manager that the GUI event methods call on and react to whatever the game manager returns
    }

    private void renderNextPlayer() {
        // Update turn indicator
        playerNameIndicator.setText(gameManager.getCurrentPlayer().getPlayerName() + "'s Turn");
        
        // Initialize GUI for players hand
        int i = 0;
        for (JButton button : handPiles) {
            if (gameManager.getCurrentPlayerHand(i).equals(null))
            {
                button.setText("Empty");
            }
            button.setText(Integer.toString(gameManager.getCurrentPlayerHand(i++).getCardNumber()));
        }
        
        // Initialize GUI for players stock pile
        stockPile.setText(Integer.toString(gameManager.getCurrentPlayerStockPileCard().getCardNumber()));
        stockPileRemaining.setText("(" + gameManager.getCurrentPlayerStockPile().getSize() + ")");
        
        // Initialize GUI for players discard pile
        i = 0;
        for (JButton button : discardPiles) {
            if (gameManager.getCurrentPlayerDiscardPile(i).isEmpty()) {
                button.setText("Empty");
            } else {
                button.setText(Integer.toString(gameManager.getCurrentPlayerDiscardPile(i++).peek().getCardNumber()));
            }
        }
        
        i = 0;
        for (JButton button : buildPiles) {
            if (gameManager.getBuildPile(i).isEmpty()) {
                button.setText("Empty");
            } else {
                button.setText(Integer.toString(gameManager.getBuildPile(i++).peek().getCardNumber()));
            }
        }
    }
    
    
    // Methods to control GUI card flow
    private void resetActionState() {
        for (JButton button : handPiles) {
            button.setEnabled(true);
        }
        for (JButton button : discardPiles) {
            button.setEnabled(true);
        }
        for (JButton button : buildPiles) {
            button.setEnabled(true);
        }
        stockPile.setEnabled(true);
    }
    private void selectHAND() {
        currentAction = PlayerAction.SELECT_HAND;
        for (JButton button : handPiles) {
            button.setEnabled(false);
        }
        for (JButton button : discardPiles) {
            button.setEnabled(true);
        }
        for (JButton button : buildPiles) {
            button.setEnabled(true);
        }
        stockPile.setEnabled(false);
    }
    private void selectSTOCK() {
        currentAction = PlayerAction.SELECT_HAND;
        for (JButton button : handPiles) {
            button.setEnabled(false);
        }
        for (JButton button : discardPiles) {
            button.setEnabled(false);
        }
        for (JButton button : buildPiles) {
            button.setEnabled(true);
        }
        stockPile.setEnabled(false);
    }
    private void selectDISCARD() {
        currentAction = PlayerAction.SELECT_HAND;
        for (JButton button : handPiles) {
            button.setEnabled(false);
        }
        for (JButton button : discardPiles) {
            button.setEnabled(false);
        }
        for (JButton button : buildPiles) {
            button.setEnabled(true);
        }
        stockPile.setEnabled(false);
    }
    
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        exitButton = new javax.swing.JButton();
        TableTop = new javax.swing.JPanel();
        BuildPiles = new javax.swing.JPanel();
        buildSlotBurner = new javax.swing.JPanel();
        buildSlot1 = new javax.swing.JPanel();
        buildPile1 = new javax.swing.JButton();
        buildSlot2 = new javax.swing.JPanel();
        buildPile2 = new javax.swing.JButton();
        buildSlot3 = new javax.swing.JPanel();
        buildPile3 = new javax.swing.JButton();
        buildSlot4 = new javax.swing.JPanel();
        buildPile4 = new javax.swing.JButton();
        DiscardPiles = new javax.swing.JPanel();
        discardSlotBurner = new javax.swing.JPanel();
        discardSlot1 = new javax.swing.JPanel();
        discardPile1 = new javax.swing.JButton();
        discardSlot2 = new javax.swing.JPanel();
        discardPile2 = new javax.swing.JButton();
        discardSlot3 = new javax.swing.JPanel();
        discardPile3 = new javax.swing.JButton();
        discardSlot4 = new javax.swing.JPanel();
        discardPile4 = new javax.swing.JButton();
        StockPiles = new javax.swing.JPanel();
        stockPile = new javax.swing.JButton();
        stockPileRemaining = new javax.swing.JLabel();
        HandPiles = new javax.swing.JPanel();
        handSlot1 = new javax.swing.JPanel();
        handPile1 = new javax.swing.JButton();
        handSlot2 = new javax.swing.JPanel();
        handPile2 = new javax.swing.JButton();
        handSlot3 = new javax.swing.JPanel();
        handPile3 = new javax.swing.JButton();
        handSlot4 = new javax.swing.JPanel();
        handPile4 = new javax.swing.JButton();
        handSlot5 = new javax.swing.JPanel();
        handPile5 = new javax.swing.JButton();
        playerNameIndicator = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(854, 480));

        exitButton.setBackground(new java.awt.Color(255, 51, 51));
        exitButton.setLabel("X");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        TableTop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableTopMouseClicked(evt);
            }
        });
        TableTop.setLayout(new java.awt.GridBagLayout());

        BuildPiles.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout buildSlotBurnerLayout = new javax.swing.GroupLayout(buildSlotBurner);
        buildSlotBurner.setLayout(buildSlotBurnerLayout);
        buildSlotBurnerLayout.setHorizontalGroup(
            buildSlotBurnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
        );
        buildSlotBurnerLayout.setVerticalGroup(
            buildSlotBurnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        BuildPiles.add(buildSlotBurner);

        buildSlot1.setPreferredSize(new java.awt.Dimension(100, 100));

        buildPile1.setText("Build Slot 1");
        buildPile1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buildPile1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buildSlot1Layout = new javax.swing.GroupLayout(buildSlot1);
        buildSlot1.setLayout(buildSlot1Layout);
        buildSlot1Layout.setHorizontalGroup(
            buildSlot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
            .addGroup(buildSlot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(buildSlot1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(buildPile1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        buildSlot1Layout.setVerticalGroup(
            buildSlot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(buildSlot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(buildSlot1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(buildPile1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        BuildPiles.add(buildSlot1);

        buildSlot2.setPreferredSize(new java.awt.Dimension(100, 100));

        buildPile2.setText("Build Slot 2");
        buildPile2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buildPile2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buildSlot2Layout = new javax.swing.GroupLayout(buildSlot2);
        buildSlot2.setLayout(buildSlot2Layout);
        buildSlot2Layout.setHorizontalGroup(
            buildSlot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
            .addGroup(buildSlot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(buildSlot2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(buildPile2)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        buildSlot2Layout.setVerticalGroup(
            buildSlot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(buildSlot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(buildSlot2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(buildPile2)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        BuildPiles.add(buildSlot2);

        buildSlot3.setPreferredSize(new java.awt.Dimension(100, 100));

        buildPile3.setText("Build Slot 3");
        buildPile3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buildPile3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buildSlot3Layout = new javax.swing.GroupLayout(buildSlot3);
        buildSlot3.setLayout(buildSlot3Layout);
        buildSlot3Layout.setHorizontalGroup(
            buildSlot3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
            .addGroup(buildSlot3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(buildSlot3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(buildPile3)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        buildSlot3Layout.setVerticalGroup(
            buildSlot3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(buildSlot3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(buildSlot3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(buildPile3)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        BuildPiles.add(buildSlot3);

        buildSlot4.setPreferredSize(new java.awt.Dimension(100, 100));

        buildPile4.setText("Build Slot 4");
        buildPile4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buildPile4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buildSlot4Layout = new javax.swing.GroupLayout(buildSlot4);
        buildSlot4.setLayout(buildSlot4Layout);
        buildSlot4Layout.setHorizontalGroup(
            buildSlot4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
            .addGroup(buildSlot4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(buildSlot4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(buildPile4)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        buildSlot4Layout.setVerticalGroup(
            buildSlot4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(buildSlot4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(buildSlot4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(buildPile4)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        BuildPiles.add(buildSlot4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        TableTop.add(BuildPiles, gridBagConstraints);

        DiscardPiles.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout discardSlotBurnerLayout = new javax.swing.GroupLayout(discardSlotBurner);
        discardSlotBurner.setLayout(discardSlotBurnerLayout);
        discardSlotBurnerLayout.setHorizontalGroup(
            discardSlotBurnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
        );
        discardSlotBurnerLayout.setVerticalGroup(
            discardSlotBurnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        DiscardPiles.add(discardSlotBurner);

        discardSlot1.setPreferredSize(new java.awt.Dimension(100, 100));

        discardPile1.setText("Discard Slot 1");
        discardPile1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discardPile1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout discardSlot1Layout = new javax.swing.GroupLayout(discardSlot1);
        discardSlot1.setLayout(discardSlot1Layout);
        discardSlot1Layout.setHorizontalGroup(
            discardSlot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
            .addGroup(discardSlot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(discardSlot1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(discardPile1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        discardSlot1Layout.setVerticalGroup(
            discardSlot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(discardSlot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(discardSlot1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(discardPile1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        DiscardPiles.add(discardSlot1);

        discardPile2.setText("Discard Slot 2");
        discardPile2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discardPile2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout discardSlot2Layout = new javax.swing.GroupLayout(discardSlot2);
        discardSlot2.setLayout(discardSlot2Layout);
        discardSlot2Layout.setHorizontalGroup(
            discardSlot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
            .addGroup(discardSlot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(discardSlot2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(discardPile2)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        discardSlot2Layout.setVerticalGroup(
            discardSlot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(discardSlot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(discardSlot2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(discardPile2)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        DiscardPiles.add(discardSlot2);

        discardPile3.setText("Discard Slot 3");
        discardPile3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discardPile3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout discardSlot3Layout = new javax.swing.GroupLayout(discardSlot3);
        discardSlot3.setLayout(discardSlot3Layout);
        discardSlot3Layout.setHorizontalGroup(
            discardSlot3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
            .addGroup(discardSlot3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(discardSlot3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(discardPile3)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        discardSlot3Layout.setVerticalGroup(
            discardSlot3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(discardSlot3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(discardSlot3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(discardPile3)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        DiscardPiles.add(discardSlot3);

        discardPile4.setText("Discard Slot 4");
        discardPile4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discardPile4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout discardSlot4Layout = new javax.swing.GroupLayout(discardSlot4);
        discardSlot4.setLayout(discardSlot4Layout);
        discardSlot4Layout.setHorizontalGroup(
            discardSlot4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
            .addGroup(discardSlot4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(discardSlot4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(discardPile4)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        discardSlot4Layout.setVerticalGroup(
            discardSlot4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(discardSlot4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(discardSlot4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(discardPile4)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        DiscardPiles.add(discardSlot4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        TableTop.add(DiscardPiles, gridBagConstraints);

        StockPiles.setPreferredSize(new java.awt.Dimension(100, 100));

        stockPile.setText("Stock Pile");
        stockPile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockPileActionPerformed(evt);
            }
        });

        stockPileRemaining.setText("Remaining");

        javax.swing.GroupLayout StockPilesLayout = new javax.swing.GroupLayout(StockPiles);
        StockPiles.setLayout(StockPilesLayout);
        StockPilesLayout.setHorizontalGroup(
            StockPilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StockPilesLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(stockPileRemaining)
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(StockPilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(StockPilesLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(stockPile)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        StockPilesLayout.setVerticalGroup(
            StockPilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, StockPilesLayout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addComponent(stockPileRemaining)
                .addGap(18, 18, 18))
            .addGroup(StockPilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(StockPilesLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(stockPile)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        TableTop.add(StockPiles, gridBagConstraints);

        HandPiles.setLayout(new java.awt.GridLayout(1, 0));

        handSlot1.setPreferredSize(new java.awt.Dimension(100, 100));

        handPile1.setText("Hand Slot 1");
        handPile1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handPile1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout handSlot1Layout = new javax.swing.GroupLayout(handSlot1);
        handSlot1.setLayout(handSlot1Layout);
        handSlot1Layout.setHorizontalGroup(
            handSlot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
            .addGroup(handSlot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(handSlot1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(handPile1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        handSlot1Layout.setVerticalGroup(
            handSlot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(handSlot1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(handSlot1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(handPile1)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        HandPiles.add(handSlot1);

        handPile2.setText("Hand Slot 2");
        handPile2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handPile2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout handSlot2Layout = new javax.swing.GroupLayout(handSlot2);
        handSlot2.setLayout(handSlot2Layout);
        handSlot2Layout.setHorizontalGroup(
            handSlot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
            .addGroup(handSlot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(handSlot2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(handPile2)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        handSlot2Layout.setVerticalGroup(
            handSlot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(handSlot2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(handSlot2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(handPile2)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        HandPiles.add(handSlot2);

        handPile3.setText("Hand Slot 3");
        handPile3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handPile3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout handSlot3Layout = new javax.swing.GroupLayout(handSlot3);
        handSlot3.setLayout(handSlot3Layout);
        handSlot3Layout.setHorizontalGroup(
            handSlot3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
            .addGroup(handSlot3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(handSlot3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(handPile3)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        handSlot3Layout.setVerticalGroup(
            handSlot3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(handSlot3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(handSlot3Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(handPile3)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        HandPiles.add(handSlot3);

        handPile4.setText("Hand Slot 4");
        handPile4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handPile4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout handSlot4Layout = new javax.swing.GroupLayout(handSlot4);
        handSlot4.setLayout(handSlot4Layout);
        handSlot4Layout.setHorizontalGroup(
            handSlot4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
            .addGroup(handSlot4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(handSlot4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(handPile4)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        handSlot4Layout.setVerticalGroup(
            handSlot4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(handSlot4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(handSlot4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(handPile4)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        HandPiles.add(handSlot4);

        handPile5.setText("Hand Slot 5");
        handPile5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handPile5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout handSlot5Layout = new javax.swing.GroupLayout(handSlot5);
        handSlot5.setLayout(handSlot5Layout);
        handSlot5Layout.setHorizontalGroup(
            handSlot5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
            .addGroup(handSlot5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(handSlot5Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(handPile5)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        handSlot5Layout.setVerticalGroup(
            handSlot5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(handSlot5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(handSlot5Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(handPile5)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        HandPiles.add(handSlot5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        TableTop.add(HandPiles, gridBagConstraints);

        playerNameIndicator.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        playerNameIndicator.setText("Player 1's Turn");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TableTop, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                    .addComponent(playerNameIndicator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(exitButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerNameIndicator, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exitButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TableTop, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to end the game?", "End Game", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            Point location = this.getLocation();
            MainMenu menu = new MainMenu();
            menu.setLocation(location);
            menu.setVisible(true);
            this.dispose();
        }
        else if (choice == JOptionPane.NO_OPTION) {
            return;
        }
        
    }//GEN-LAST:event_exitButtonActionPerformed

    private void handPile1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handPile1ActionPerformed
        // TODO add your handling code here:
        selectedButton = handPile1;
        selectHAND();
    }//GEN-LAST:event_handPile1ActionPerformed

    private void handPile2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handPile2ActionPerformed
        // TODO add your handling code here:
        selectedButton = handPile2;
        selectHAND();
    }//GEN-LAST:event_handPile2ActionPerformed

    private void handPile3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handPile3ActionPerformed
        // TODO add your handling code here:
        selectedButton = handPile3;
        selectHAND();
    }//GEN-LAST:event_handPile3ActionPerformed

    private void handPile4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handPile4ActionPerformed
        // TODO add your handling code here:
        selectedButton = handPile4;
        selectHAND();
    }//GEN-LAST:event_handPile4ActionPerformed

    private void handPile5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handPile5ActionPerformed
        // TODO add your handling code here:
        selectedButton = handPile5;
        selectHAND();
    }//GEN-LAST:event_handPile5ActionPerformed

    private void stockPileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockPileActionPerformed
        // TODO add your handling code here:
        selectedButton = stockPile;
        selectSTOCK();
    }//GEN-LAST:event_stockPileActionPerformed

    private void discardPile1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discardPile1ActionPerformed
        // TODO add your handling code here:
        selectedButton = discardPile1;
        selectDISCARD();
    }//GEN-LAST:event_discardPile1ActionPerformed

    private void discardPile2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discardPile2ActionPerformed
        // TODO add your handling code here:
        selectedButton = discardPile2;
        selectDISCARD();
    }//GEN-LAST:event_discardPile2ActionPerformed

    private void discardPile3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discardPile3ActionPerformed
        // TODO add your handling code here:+
        selectedButton = discardPile3;
        selectDISCARD();
    }//GEN-LAST:event_discardPile3ActionPerformed

    private void discardPile4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discardPile4ActionPerformed
        // TODO add your handling code here:
        selectedButton = discardPile4;
        selectDISCARD();
    }//GEN-LAST:event_discardPile4ActionPerformed

    private void buildPile1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buildPile1ActionPerformed
        // TODO add your handling code here:
        if (currentAction == PlayerAction.SELECT_HAND) {
            // Call new gamemanager method, see comments within that method to continue coding
        }
    }//GEN-LAST:event_buildPile1ActionPerformed

    private void buildPile2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buildPile2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buildPile2ActionPerformed

    private void buildPile3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buildPile3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buildPile3ActionPerformed

    private void buildPile4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buildPile4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buildPile4ActionPerformed

    private void TableTopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableTopMouseClicked
        // TODO add your handling code here:
        resetActionState();
    }//GEN-LAST:event_TableTopMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new Game().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BuildPiles;
    private javax.swing.JPanel DiscardPiles;
    private javax.swing.JPanel HandPiles;
    private javax.swing.JPanel StockPiles;
    private javax.swing.JPanel TableTop;
    private javax.swing.JButton buildPile1;
    private javax.swing.JButton buildPile2;
    private javax.swing.JButton buildPile3;
    private javax.swing.JButton buildPile4;
    private javax.swing.JPanel buildSlot1;
    private javax.swing.JPanel buildSlot2;
    private javax.swing.JPanel buildSlot3;
    private javax.swing.JPanel buildSlot4;
    private javax.swing.JPanel buildSlotBurner;
    private javax.swing.JButton discardPile1;
    private javax.swing.JButton discardPile2;
    private javax.swing.JButton discardPile3;
    private javax.swing.JButton discardPile4;
    private javax.swing.JPanel discardSlot1;
    private javax.swing.JPanel discardSlot2;
    private javax.swing.JPanel discardSlot3;
    private javax.swing.JPanel discardSlot4;
    private javax.swing.JPanel discardSlotBurner;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton handPile1;
    private javax.swing.JButton handPile2;
    private javax.swing.JButton handPile3;
    private javax.swing.JButton handPile4;
    private javax.swing.JButton handPile5;
    private javax.swing.JPanel handSlot1;
    private javax.swing.JPanel handSlot2;
    private javax.swing.JPanel handSlot3;
    private javax.swing.JPanel handSlot4;
    private javax.swing.JPanel handSlot5;
    private javax.swing.JLabel playerNameIndicator;
    private javax.swing.JButton stockPile;
    private javax.swing.JLabel stockPileRemaining;
    // End of variables declaration//GEN-END:variables
}
