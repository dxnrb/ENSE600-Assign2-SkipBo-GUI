/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.dxnrb.ui;

import com.dxnrb.database.Derby;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Point;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author danie
 */
public class MainMenu extends javax.swing.JFrame {

    /**
     * Creates new form MainMenu
     */
    // Game initialisation screen variables
    private JTextField[] playerFields;
    private JLabel[] textFieldLabels;
    
    public MainMenu() {
        initComponents();
        
        // Initialise card layout
        CardLayout menuCard = (CardLayout)(Parent.getLayout());
        
        // Add JPanels to cards
        Parent.add(menu, "menu");
        
        
        Parent.add(instructions, "instructions");
        
        Parent.add(scoreBoard, "scoreBoard");
        
        Parent.add(gameLog, "gameLog");
        gameLogTable.getSelectionModel().addListSelectionListener(gameLogTable);
        setupGameLogTableListener();
        updateGameLogTable();
        
        
        Parent.add(gameInit, "gameInit");
        // Create player count spinner
        playerSpinner.setModel(new SpinnerNumberModel(2, 2, 6, 1));
        // Disable typing in spinner (ChatGPT showed me this)
        ((JSpinner.DefaultEditor) playerSpinner.getEditor()).getTextField().setEditable(false);
        // Store object reference for dynamic updating on spinner change event
        playerFields = new JTextField[] {playerTextField1, playerTextField2, playerTextField3, playerTextField4, playerTextField5, playerTextField6};;
        textFieldLabels = new JLabel[] {playerLabel1, playerLabel2, playerLabel3, playerLabel4, playerLabel5, playerLabel6};
        // Initialise visibility on card
        playerSpinnerStateChanged(null);
        
        
        // Show menu panel at start up
        menuCard.show(Parent, "menu");
    }
    
    
    // ChatGPT wrote this because I couldn't find an event listener in GUI builder for JTable
    // There was mouse clicked but it didnt update if the keyboard was used and I didnt want two listeners
    private void setupGameLogTableListener() {
        gameLogTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        gameLogTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = gameLogTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Object completedValue = gameLogTable.getValueAt(selectedRow, 3); // 'Completed' column

                    boolean gameCompleted = false; // default if null or invalid
                    if (completedValue != null) {
                        if (completedValue instanceof Boolean) {
                            gameCompleted = (Boolean) completedValue;
                        } else {
                            // Try parsing as string in case it's "true"/"false"
                            gameCompleted = Boolean.parseBoolean(completedValue.toString());
                        }
                    }
                    // Enable or disable the button
                    gameLogContinueButton.setEnabled(!gameCompleted);
                } else {
                    gameLogContinueButton.setEnabled(false);
                }
            }
        });
    }
    
    public void updateGameLogTable() {
        String[] columnNames = {"Game ID", "Players", "Current Turn", "Completed", "Winner", "Last Updated"};
        DefaultTableModel model = (DefaultTableModel) gameLogTable.getModel();
        model.setColumnIdentifiers(columnNames);
        model.setRowCount(0);
        gameLogTable.setDefaultEditor(Object.class, null);
        
        try {
            // Read all game states from the database
            ArrayList<HashMap<String, Object>> gameStates = Derby.readGameStates();

            for (HashMap<String, Object> row : gameStates) {
                // Get players as a comma-separated string
                ArrayList<String> playersList = Derby.readPlayersForGame((int) row.get("game_id"));
                String players = "<html>" + String.join("<br>", playersList) + "</html>"; // ChatGPT wrote this line, I didn't know html had to be used to line break in a JTable

                // Extract the rest of the columns
                Object currentTurn = row.get("current_turn");
                Object gameCompleted = row.get("game_completed");
                Object winner = row.get("winner");
                Object updatedAt = row.get("updated_at");

                // Add a new row to the model
                model.addRow(new Object[]{
                    row.get("game_id"),
                    players,
                    currentTurn,
                    gameCompleted,
                    winner,
                    updatedAt
                });
            }
            
            // ChatGPT wrote this --->
            // Resize row heights to fit multi-line cells
            // First, adjust column widths to fit content
            for (int column = 0; column < gameLogTable.getColumnCount(); column++) {
                int maxWidth = 50; // minimum starting width
                for (int row = 0; row < gameLogTable.getRowCount(); row++) {
                    TableCellRenderer renderer = gameLogTable.getCellRenderer(row, column);
                    Component comp = gameLogTable.prepareRenderer(renderer, row, column);
                    maxWidth = Math.max(maxWidth, comp.getPreferredSize().width + 10); // add padding
                }
                gameLogTable.getColumnModel().getColumn(column).setPreferredWidth(maxWidth);
            }

            // Then, adjust row heights to fit multi-line cells
            for (int row = 0; row < gameLogTable.getRowCount(); row++) {
                int maxHeight = gameLogTable.getRowHeight(); // default height
                for (int column = 0; column < gameLogTable.getColumnCount(); column++) {
                    TableCellRenderer renderer = gameLogTable.getCellRenderer(row, column);
                    Component comp = gameLogTable.prepareRenderer(renderer, row, column);
                    maxHeight = Math.max(maxHeight, comp.getPreferredSize().height);
                }
                gameLogTable.setRowHeight(row, maxHeight);
            }
            // <--- ChatGPT wrote this
            
            gameLogTable.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Failed to load game logs: " + e.getMessage(),
                    "Database Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Parent = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        navigation = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        instructionsButton = new javax.swing.JButton();
        scoreButton = new javax.swing.JButton();
        logButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        instructions = new javax.swing.JPanel();
        instructionsHeader = new javax.swing.JLabel();
        instructionsText = new javax.swing.JTextArea();
        exitButton_Instructions = new javax.swing.JButton();
        gameInit = new javax.swing.JPanel();
        playerSpinner = new javax.swing.JSpinner();
        spinnerLabel = new javax.swing.JLabel();
        exitButton_gameInit = new javax.swing.JButton();
        dynamicPlayerFields = new javax.swing.JPanel();
        playerTextField1 = new javax.swing.JTextField();
        playerLabel4 = new javax.swing.JLabel();
        playerLabel5 = new javax.swing.JLabel();
        playerLabel6 = new javax.swing.JLabel();
        playerTextField2 = new javax.swing.JTextField();
        namesLabel = new javax.swing.JLabel();
        playerTextField3 = new javax.swing.JTextField();
        playerTextField4 = new javax.swing.JTextField();
        playerTextField5 = new javax.swing.JTextField();
        playerTextField6 = new javax.swing.JTextField();
        playerLabel1 = new javax.swing.JLabel();
        playerLabel2 = new javax.swing.JLabel();
        playerLabel3 = new javax.swing.JLabel();
        startGameButton = new javax.swing.JButton();
        scoreBoard = new javax.swing.JPanel();
        exitButton_ScoreBoard = new javax.swing.JButton();
        gameLog = new javax.swing.JPanel();
        gameLogScrollPane = new javax.swing.JScrollPane();
        gameLogTable = new javax.swing.JTable();
        exitButton_GameLog = new javax.swing.JButton();
        gameLogHeader = new javax.swing.JLabel();
        gameLogContinueButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(854, 480));

        Parent.setLayout(new java.awt.CardLayout());

        menu.setPreferredSize(new java.awt.Dimension(854, 480));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/dxnrb/img/logo.png"))); // NOI18N

        startButton.setLabel("Start Game");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        instructionsButton.setLabel("Instructions");
        instructionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                instructionsButtonActionPerformed(evt);
            }
        });

        scoreButton.setLabel("Score Board");
        scoreButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scoreButtonActionPerformed(evt);
            }
        });

        logButton.setLabel("Game Logs");
        logButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout navigationLayout = new javax.swing.GroupLayout(navigation);
        navigation.setLayout(navigationLayout);
        navigationLayout.setHorizontalGroup(
            navigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navigationLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(navigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(instructionsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scoreButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(startButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        navigationLayout.setVerticalGroup(
            navigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navigationLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(startButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(instructionsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scoreButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logButton))
        );

        exitButton.setBackground(new java.awt.Color(255, 51, 51));
        exitButton.setLabel("X");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuLayout = new javax.swing.GroupLayout(menu);
        menu.setLayout(menuLayout);
        menuLayout.setHorizontalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayout.createSequentialGroup()
                .addContainerGap(295, Short.MAX_VALUE)
                .addGroup(menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuLayout.createSequentialGroup()
                        .addComponent(exitButton)
                        .addContainerGap())
                    .addGroup(menuLayout.createSequentialGroup()
                        .addComponent(logo)
                        .addContainerGap(300, Short.MAX_VALUE))))
            .addGroup(menuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(navigation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        menuLayout.setVerticalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exitButton)
                .addGap(12, 12, 12)
                .addComponent(logo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(navigation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(117, Short.MAX_VALUE))
        );

        Parent.add(menu, "card2");

        instructions.setPreferredSize(new java.awt.Dimension(854, 480));

        instructionsHeader.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        instructionsHeader.setText("Instructions");

        instructionsText.setEditable(false);
        instructionsText.setColumns(20);
        instructionsText.setLineWrap(true);
        instructionsText.setRows(5);
        instructionsText.setText("Objective:\nBe the first player to play all the cards in your STOCK pile.\n\nHow To Play:\nUsing your HAND, DISCARD, and STOCK pile, play cards to the BUILD pile.\n\nWhatever card played to a BUILD pile must be the next sequential number.\n\nYour DISCARD piles can be built in any order.\n\nYou can play from your DISCARD pile as many times as you like during your turn.\n\nPlaying your HAND to your DISCARD pile ends your turn.\n\nIf you use up all cards in your HAND during your turn, you replenish your HAND and keep playing.\n\nWildcards (0) can be played as any number 1 through 12.");
        instructionsText.setWrapStyleWord(true);
        instructionsText.setFocusable(false);

        exitButton_Instructions.setBackground(new java.awt.Color(255, 51, 51));
        exitButton_Instructions.setLabel("X");
        exitButton_Instructions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButton_InstructionsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout instructionsLayout = new javax.swing.GroupLayout(instructions);
        instructions.setLayout(instructionsLayout);
        instructionsLayout.setHorizontalGroup(
            instructionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(instructionsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exitButton_Instructions)
                .addContainerGap())
            .addGroup(instructionsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(instructionsText, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(instructionsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(instructionsHeader)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        instructionsLayout.setVerticalGroup(
            instructionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(instructionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exitButton_Instructions)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(instructionsHeader)
                .addGap(18, 18, 18)
                .addComponent(instructionsText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Parent.add(instructions, "card3");

        gameInit.setPreferredSize(new java.awt.Dimension(854, 480));

        playerSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                playerSpinnerStateChanged(evt);
            }
        });

        spinnerLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        spinnerLabel.setText("Players");

        exitButton_gameInit.setBackground(new java.awt.Color(255, 51, 51));
        exitButton_gameInit.setLabel("X");
        exitButton_gameInit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButton_gameInitActionPerformed(evt);
            }
        });

        playerTextField1.setText("Player 1");
        playerTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                playerTextField1FocusGained(evt);
            }
        });

        playerLabel4.setText("4");

        playerLabel5.setText("5");

        playerLabel6.setText("6");

        playerTextField2.setText("Player 2");
        playerTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                playerTextField2FocusGained(evt);
            }
        });

        namesLabel.setText("Player names:");

        playerTextField3.setText("Player 3");
        playerTextField3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                playerTextField3FocusGained(evt);
            }
        });

        playerTextField4.setText("Player 4");
        playerTextField4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                playerTextField4FocusGained(evt);
            }
        });

        playerTextField5.setText("Player 5");
        playerTextField5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                playerTextField5FocusGained(evt);
            }
        });

        playerTextField6.setText("Player 6");
        playerTextField6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                playerTextField6FocusGained(evt);
            }
        });

        playerLabel1.setText("1");

        playerLabel2.setText("2");

        playerLabel3.setText("3");

        startGameButton.setBackground(new java.awt.Color(0, 204, 51));
        startGameButton.setText("Play!");
        startGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startGameButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dynamicPlayerFieldsLayout = new javax.swing.GroupLayout(dynamicPlayerFields);
        dynamicPlayerFields.setLayout(dynamicPlayerFieldsLayout);
        dynamicPlayerFieldsLayout.setHorizontalGroup(
            dynamicPlayerFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dynamicPlayerFieldsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dynamicPlayerFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(playerLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(playerLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(playerLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(playerLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(playerLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(playerLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dynamicPlayerFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(namesLabel)
                    .addGroup(dynamicPlayerFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(playerTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                        .addComponent(playerTextField2)
                        .addComponent(playerTextField3)
                        .addComponent(playerTextField4)
                        .addComponent(playerTextField5)
                        .addComponent(playerTextField6))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dynamicPlayerFieldsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(startGameButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dynamicPlayerFieldsLayout.setVerticalGroup(
            dynamicPlayerFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dynamicPlayerFieldsLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(namesLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dynamicPlayerFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(playerLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dynamicPlayerFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(playerLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dynamicPlayerFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(playerLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dynamicPlayerFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(playerLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dynamicPlayerFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(playerLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dynamicPlayerFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(playerLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(startGameButton))
        );

        javax.swing.GroupLayout gameInitLayout = new javax.swing.GroupLayout(gameInit);
        gameInit.setLayout(gameInitLayout);
        gameInitLayout.setHorizontalGroup(
            gameInitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameInitLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exitButton_gameInit)
                .addContainerGap())
            .addGroup(gameInitLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dynamicPlayerFields, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gameInitLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(spinnerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playerSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(354, 354, 354))
        );
        gameInitLayout.setVerticalGroup(
            gameInitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameInitLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exitButton_gameInit)
                .addGap(29, 29, 29)
                .addGroup(gameInitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinnerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(playerSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(dynamicPlayerFields, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Parent.add(gameInit, "card4");

        exitButton_ScoreBoard.setBackground(new java.awt.Color(255, 51, 51));
        exitButton_ScoreBoard.setLabel("X");
        exitButton_ScoreBoard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButton_ScoreBoardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout scoreBoardLayout = new javax.swing.GroupLayout(scoreBoard);
        scoreBoard.setLayout(scoreBoardLayout);
        scoreBoardLayout.setHorizontalGroup(
            scoreBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, scoreBoardLayout.createSequentialGroup()
                .addContainerGap(821, Short.MAX_VALUE)
                .addComponent(exitButton_ScoreBoard)
                .addContainerGap())
        );
        scoreBoardLayout.setVerticalGroup(
            scoreBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scoreBoardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exitButton_ScoreBoard)
                .addContainerGap(447, Short.MAX_VALUE))
        );

        Parent.add(scoreBoard, "card5");

        gameLogScrollPane.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                gameLogScrollPaneKeyPressed(evt);
            }
        });

        gameLogTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        gameLogTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gameLogTableMouseClicked(evt);
            }
        });
        gameLogScrollPane.setViewportView(gameLogTable);

        exitButton_GameLog.setBackground(new java.awt.Color(255, 51, 51));
        exitButton_GameLog.setLabel("X");
        exitButton_GameLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButton_GameLogActionPerformed(evt);
            }
        });

        gameLogHeader.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        gameLogHeader.setText("Game Logs");

        gameLogContinueButton.setText("Continue Game");
        gameLogContinueButton.setEnabled(false);
        gameLogContinueButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameLogContinueButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gameLogLayout = new javax.swing.GroupLayout(gameLog);
        gameLog.setLayout(gameLogLayout);
        gameLogLayout.setHorizontalGroup(
            gameLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameLogLayout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(gameLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gameLogLayout.createSequentialGroup()
                        .addComponent(gameLogHeader)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitButton_GameLog))
                    .addGroup(gameLogLayout.createSequentialGroup()
                        .addGroup(gameLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(gameLogScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 772, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gameLogContinueButton))
                        .addGap(0, 33, Short.MAX_VALUE)))
                .addContainerGap())
        );
        gameLogLayout.setVerticalGroup(
            gameLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameLogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gameLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(exitButton_GameLog)
                    .addComponent(gameLogHeader))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gameLogScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gameLogContinueButton)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        Parent.add(gameLog, "card6");

        getContentPane().add(Parent, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit the game?", "Quit Game", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == JOptionPane.YES_OPTION)
        {
            try {
                Derby.closeDatabase();
            } catch (SQLException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        }
        else if (choice == JOptionPane.NO_OPTION)
        {
            return;
        }
        
    }//GEN-LAST:event_exitButtonActionPerformed

    private void instructionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_instructionsButtonActionPerformed
        // TODO add your handling code here:
        CardLayout menuCard = (CardLayout)(Parent.getLayout());
        menuCard.show(Parent, "instructions");
    }//GEN-LAST:event_instructionsButtonActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        // TODO add your handling code here:
        CardLayout menuCard = (CardLayout)(Parent.getLayout());
        menuCard.show(Parent, "gameInit");
    }//GEN-LAST:event_startButtonActionPerformed

    private void exitButton_InstructionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButton_InstructionsActionPerformed
        // TODO add your handling code here:
        CardLayout menuCard = (CardLayout)(Parent.getLayout());
        menuCard.show(Parent, "menu");
    }//GEN-LAST:event_exitButton_InstructionsActionPerformed

    private void exitButton_gameInitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButton_gameInitActionPerformed
        // TODO add your handling code here:
        CardLayout menuCard = (CardLayout)(Parent.getLayout());
        menuCard.show(Parent, "menu");
    }//GEN-LAST:event_exitButton_gameInitActionPerformed

    private void playerSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_playerSpinnerStateChanged
        // TODO add your handling code here:
        int numPlayers = (int) playerSpinner.getValue();
        
        // Dynamically enable entry fields
        for (int i = 0; i < numPlayers; i++) {
            playerFields[i].setVisible(true);
            textFieldLabels[i].setVisible(true);
        }
        // Dynamically disable entry fields
        for (int i = numPlayers; i < 6; i++) {
            playerFields[i].setVisible(false);
            textFieldLabels[i].setVisible(false);
        }
        
        // Update card
        gameInit.revalidate();
        gameInit.repaint();
    }//GEN-LAST:event_playerSpinnerStateChanged

    private void startGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startGameButtonActionPerformed
        // TODO add your handling code here:
        
        // Read the player names
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < (int) playerSpinner.getValue(); i++) {
            names.add(playerFields[i].getText());
        }
        // For the new instance of the Game JFrame, pass the list of names so it can create a game manager
        Game gameWindow;
        try {
            gameWindow = new Game(names);
            
            // Start database
            Derby.startDatabase();
            
            // Get this JFrames location and set gameWindows position to match for window continuity
            Point location = this.getLocation();
            gameWindow.setLocation(location);
            gameWindow.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_startGameButtonActionPerformed

    private void playerTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_playerTextField1FocusGained
        // TODO add your handling code here:
        playerTextField1.selectAll();
    }//GEN-LAST:event_playerTextField1FocusGained

    private void playerTextField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_playerTextField2FocusGained
        // TODO add your handling code here:
        playerTextField2.selectAll();
    }//GEN-LAST:event_playerTextField2FocusGained

    private void playerTextField3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_playerTextField3FocusGained
        // TODO add your handling code here:
        playerTextField3.selectAll();
    }//GEN-LAST:event_playerTextField3FocusGained

    private void playerTextField4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_playerTextField4FocusGained
        // TODO add your handling code here:
        playerTextField4.selectAll();
    }//GEN-LAST:event_playerTextField4FocusGained

    private void playerTextField5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_playerTextField5FocusGained
        // TODO add your handling code here:
        playerTextField5.selectAll();
    }//GEN-LAST:event_playerTextField5FocusGained

    private void playerTextField6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_playerTextField6FocusGained
        // TODO add your handling code here:
        playerTextField6.selectAll();
    }//GEN-LAST:event_playerTextField6FocusGained

    private void logButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logButtonActionPerformed
        // TODO add your handling code here:
        updateGameLogTable();
        CardLayout menuCard = (CardLayout)(Parent.getLayout());
        menuCard.show(Parent, "gameLog");
    }//GEN-LAST:event_logButtonActionPerformed

    private void scoreButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scoreButtonActionPerformed
        // TODO add your handling code here:
        CardLayout menuCard = (CardLayout)(Parent.getLayout());
        menuCard.show(Parent, "scoreBoard");
    }//GEN-LAST:event_scoreButtonActionPerformed

    private void exitButton_GameLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButton_GameLogActionPerformed
        // TODO add your handling code here:
        CardLayout menuCard = (CardLayout)(Parent.getLayout());
        menuCard.show(Parent, "menu");
    }//GEN-LAST:event_exitButton_GameLogActionPerformed

    private void exitButton_ScoreBoardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButton_ScoreBoardActionPerformed
        // TODO add your handling code here:
        CardLayout menuCard = (CardLayout)(Parent.getLayout());
        menuCard.show(Parent, "menu");
    }//GEN-LAST:event_exitButton_ScoreBoardActionPerformed

    private void gameLogContinueButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gameLogContinueButtonActionPerformed
        // TODO add your handling code here:
        int selectedRow = gameLogTable.getSelectedRow();
        if (selectedRow >= 0) {
            Object gameID = gameLogTable.getValueAt(selectedRow, 0);
            try {
                HashMap<String, Object> gameState = Derby.readGameStateID((int)gameID);
                Game gameWindow;
                try {
                    gameWindow = new Game(gameState);

                    // Start database
                    Derby.startDatabase();

                    // Get this JFrames location and set gameWindows position to match for window continuity
                    Point location = this.getLocation();
                    gameWindow.setLocation(location);
                    gameWindow.setVisible(true);
                    this.dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_gameLogContinueButtonActionPerformed

    private void gameLogTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gameLogTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_gameLogTableMouseClicked

    private void gameLogScrollPaneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gameLogScrollPaneKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_gameLogScrollPaneKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main (String args[]) {
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
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Parent;
    private javax.swing.JPanel dynamicPlayerFields;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton exitButton_GameLog;
    private javax.swing.JButton exitButton_Instructions;
    private javax.swing.JButton exitButton_ScoreBoard;
    private javax.swing.JButton exitButton_gameInit;
    private javax.swing.JPanel gameInit;
    private javax.swing.JPanel gameLog;
    private javax.swing.JButton gameLogContinueButton;
    private javax.swing.JLabel gameLogHeader;
    private javax.swing.JScrollPane gameLogScrollPane;
    private javax.swing.JTable gameLogTable;
    private javax.swing.JPanel instructions;
    private javax.swing.JButton instructionsButton;
    private javax.swing.JLabel instructionsHeader;
    private javax.swing.JTextArea instructionsText;
    private javax.swing.JButton logButton;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel menu;
    private javax.swing.JLabel namesLabel;
    private javax.swing.JPanel navigation;
    private javax.swing.JLabel playerLabel1;
    private javax.swing.JLabel playerLabel2;
    private javax.swing.JLabel playerLabel3;
    private javax.swing.JLabel playerLabel4;
    private javax.swing.JLabel playerLabel5;
    private javax.swing.JLabel playerLabel6;
    private javax.swing.JSpinner playerSpinner;
    private javax.swing.JTextField playerTextField1;
    private javax.swing.JTextField playerTextField2;
    private javax.swing.JTextField playerTextField3;
    private javax.swing.JTextField playerTextField4;
    private javax.swing.JTextField playerTextField5;
    private javax.swing.JTextField playerTextField6;
    private javax.swing.JPanel scoreBoard;
    private javax.swing.JButton scoreButton;
    private javax.swing.JLabel spinnerLabel;
    private javax.swing.JButton startButton;
    private javax.swing.JButton startGameButton;
    // End of variables declaration//GEN-END:variables
}
