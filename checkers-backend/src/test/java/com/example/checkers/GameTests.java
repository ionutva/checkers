package com.example.checkers;

import com.example.checkers.model.Game;
import com.example.checkers.model.Move;
import com.example.checkers.model.Piece;
import com.example.checkers.model.PieceType;
import com.example.checkers.model.Player;
import com.example.checkers.model.Position;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameTests {

    private Game game;

    @Before
    public void setup() {
        game = new Game();
    }

    @Test
    public void testInitialSetup() {
        Piece p = game.getBoard().getPiece(0, 1);
        Assert.assertNotNull(p);
        Assert.assertEquals(Player.PLAYER1, p.getOwner());
        Assert.assertEquals(PieceType.MAN, p.getType());
    }

    @Test
    public void testValidSimpleMove() {
        Move move = new Move(new Position(2, 1), new Position(3, 2));
        String res = game.makeMove(move);
        Assert.assertTrue(res.startsWith("Move successful"));
        Assert.assertNull(game.getBoard().getPiece(2, 1));
        Assert.assertNotNull(game.getBoard().getPiece(3, 2));
    }

    @Test
    public void testInvalidMoveToOccupied() {
        Move move = new Move(new Position(2, 1), new Position(5, 4));
        String res = game.makeMove(move);
        Assert.assertEquals("Invalid move.", res);
    }

    @Test
    public void testMoveWrongTurn() {
        Move move1 = new Move(new Position(2, 1), new Position(3, 2));
        game.makeMove(move1);
        Move move2 = new Move(new Position(5, 0), new Position(4, 1)); // player2 moves
        String res2 = game.makeMove(move1); // player1 moves again out of turn
        Assert.assertEquals("It's not your turn.", res2);
    }
}