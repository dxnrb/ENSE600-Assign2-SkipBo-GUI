/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.dxnrb.database;

import com.dxnrb.logic.*;
import com.dxnrb.logic.cards.BuildingPile;
import com.dxnrb.logic.cards.Card;
import com.dxnrb.logic.cards.DiscardPile;
import com.dxnrb.logic.cards.DrawPile;
import com.dxnrb.logic.cards.StockPile;
import com.dxnrb.logic.players.Player;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;
import java.io.File;
import java.lang.module.Configuration;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.*;

/**
 *
 * @author danie
 */
public class Derby {  
    public static void main(String[] args) {
        startDatabase();
    }
    
    // When you get back
    // Look into Jackson (JSON library)
    // Think about tables
    // I reckon it will be easier to have a player table with foreign keys to a game table
    // player table looks after cards in player hand, discard, and stock piles, plus their win rate
    // game table looks after build piles, players, checks winners etc, game status etc
    
    //--- DATABASE CALLS ---//
    
    public static void startDatabase1() {
        String url = "jdbc:derby:javaDB;create=true"; // "javaDB" will be created if missing
        String username = "root";
        String password = "010101";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to Derby database.");
            // Initialize schema (create tables if not already created)
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE game_state (" + 
                        "game_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " + 
                        "player_count INT NOT NULL CHECK (player_count BETWEEN 2 AND 6), " +
                        "current_turn INT NOT NULL, " +
                        "game_completed BOOLEAN DEFAULT FALSE, " +
                        "winning_player_id INT DEFAULT NULL, " +
                        "building_piles CLOB, " +   // JSON array of building piles
                        "draw_pile CLOB, " +         // JSON array of cards
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        "updated_at TIMESTAMP" +
                        ")");
                System.out.println("Table game_state created.");
            } catch (SQLException e) {
                if (e.getSQLState().equals("X0Y32")) {
                    // Table already exists
                    System.out.println("Table game_state already exists");
                }
            }
            
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE players (" + 
                        "player_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " + 
                        "game_id INT NOT NULL, " +
                        "player_name VARCHAR(100) NOT NULL, " +
                        "discard_piles CLOB, " +    // JSON array of discard piles
                        "stock_pile CLOB, " +       // JSON object (cards in stock pile)
                        "hand CLOB, " +             // JSON array of cards
                        "CONSTRAINT fk_game FOREIGN KEY (game_id) REFERENCES game_state(game_id)" +
                        ")");
                System.out.println("Table players created.");
            } catch (SQLException e) {
                if (e.getSQLState().equals("X0Y32")) {
                    // Table already exists
                    System.out.println("Table players already exists");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static SessionFactory factory;

    /**
     * Initialize Hibernate and connect to Derby database.
     */
    public static void startDatabase() {
        // Build registry
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure("hibernate.cfg.xml")
            .build();
        try {
            factory = new MetadataSources(registry)
                    .addAnnotatedClass(Player.class)
                    .addAnnotatedClass(Card.class)
                    .addAnnotatedClass(BuildingPile.class)
                    .addAnnotatedClass(StockPile.class)
                    .addAnnotatedClass(DiscardPile.class)
                    .addAnnotatedClass(DrawPile.class)
                    .buildMetadata()
                    .buildSessionFactory();

            System.out.println("Hibernate connected. Schema created/updated automatically.");
        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    /**
     * Save a GameManager state (players, piles, draw pile) to the database.
     */
    public void saveGame(GameManager game) {
        // Open session
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            // Save or update GameManager (game state)
            // If you annotated GameManager with @Entity and cascade = CascadeType.ALL on players/piles
            session.merge(game);

            // Commit transaction
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load players for a given gameID.
     */
    public static ArrayList<Player> loadPlayers(int gameID) {
        List<Player> list = null;
        try (Session session = factory.openSession()) {
            list = session.createQuery("FROM Player WHERE gameManager.gameID = :gid", Player.class)
                          .setParameter("gid", gameID)
                          .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(list); // convert List to ArrayList
    }

    /**
     * Load a draw pile by ID.
     */
    public static DrawPile loadDrawPile(int drawPileID) {
        Session session = factory.openSession();
        DrawPile drawPile = null;
        try {
            drawPile = session.get(DrawPile.class, drawPileID);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return drawPile;
    }

    /**
     * Close the Hibernate session factory.
     */
    public static void close() {
        if (factory != null) {
            factory.close();
        }
    }
    
    //--- READ METHODS ---//
    
    
    
    //--- WRITE METHODS ---//
    
    
}
