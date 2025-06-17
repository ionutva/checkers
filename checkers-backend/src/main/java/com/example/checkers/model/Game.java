package com.example.checkers.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Board board;
    private Player currentPlayer;
    private boolean isGameOver;
    private Player winner;

    public Game() {
        board = new Board();
        currentPlayer = Player.PLAYER1;
        isGameOver = false;
        winner = null;
    }

    public synchronized String makeMove(Move move) {
        if (isGameOver) {
            return "Game over! No more moves allowed.";
        }

        Position from = move.getFrom();
        Position to = move.getTo();

        if (!isWithinBounds(from) || !isWithinBounds(to)) {
            return "Move out of board bounds.";
        }

        Piece piece = board.getPiece(from.getRow(), from.getCol());
        if (piece == null) {
            return "No piece at the source position.";
        }

        if (!piece.getOwner().equals(currentPlayer)) {
            return "It's not your turn.";
        }

        if (!isValidMove(piece, from, to)) {
            return "Invalid move.";
        }

        executeMove(piece, from, to);

        if (checkWinCondition()) {
            isGameOver = true;
            winner = currentPlayer;
            return "Move successful! " + currentPlayer + " wins!";
        } else {
            currentPlayer = (currentPlayer == Player.PLAYER1) ? Player.PLAYER2 : Player.PLAYER1;
            return "Move successful! Next turn: " + currentPlayer;
        }
    }

    private boolean isWithinBounds(Position pos) {
        return pos.getRow() >= 0 && pos.getRow() < 8 && pos.getCol() >= 0 && pos.getCol() < 8;
    }

    private boolean isValidMove(Piece piece, Position from, Position to) {
        if (board.getPiece(to.getRow(), to.getCol()) != null) {
            return false;
        }

        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();

        if (Math.abs(rowDiff) == 1 && Math.abs(colDiff) == 1) {

            if (piece.getType() == PieceType.MAN) {
                if ((piece.getOwner() == Player.PLAYER1 && rowDiff != 1) ||
                    (piece.getOwner() == Player.PLAYER2 && rowDiff != -1)) {
                    return false;
                }
            }
            if (hasAnyJumpAvailableForPlayer(currentPlayer)) {
                return false;
            }
            return true;
        } else if (Math.abs(rowDiff) == 2 && Math.abs(colDiff) == 2) {

            int midRow = (from.getRow() + to.getRow()) / 2;
            int midCol = (from.getCol() + to.getCol()) / 2;

            Piece midPiece = board.getPiece(midRow, midCol);
            if (midPiece == null || midPiece.getOwner() == piece.getOwner()) {
                return false;
            }
            if (piece.getType() == PieceType.MAN) {
                if ((piece.getOwner() == Player.PLAYER1 && rowDiff != 2) ||
                    (piece.getOwner() == Player.PLAYER2 && rowDiff != -2)) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    private void executeMove(Piece piece, Position from, Position to) {
        int rowDiff = to.getRow() - from.getRow();

        if (Math.abs(rowDiff) == 2) { // jump
            int midRow = (from.getRow() + to.getRow()) / 2;
            int midCol = (from.getCol() + to.getCol()) / 2;

            board.setPiece(midRow, midCol, null);
        }

        board.setPiece(to.getRow(), to.getCol(), piece);
        board.setPiece(from.getRow(), from.getCol(), null);

        if (piece.getType() == PieceType.MAN) {
            if (piece.getOwner() == Player.PLAYER1 && to.getRow() == 7) {
                piece.setType(PieceType.KING);
            }
            else if (piece.getOwner() == Player.PLAYER2 && to.getRow() == 0) {
                piece.setType(PieceType.KING);
            }
        }
    }

    private boolean hasAnyJumpAvailableForPlayer(Player player) {
        for (int r=0; r<8; r++) {
            for (int c=0; c<8; c++) {
                Piece piece = board.getPiece(r, c);
                if (piece != null && piece.getOwner() == player) {
                    Position from = new Position(r, c);
                    int[] dr = {-2, -2, 2, 2};
                    int[] dc = {-2, 2, -2, 2};
                    for (int i=0; i<4; i++) {
                        int nr = r + dr[i];
                        int nc = c + dc[i];
                        if (nr>=0 && nr<8 && nc>=0 && nc<8) {
                            Position to = new Position(nr, nc);
                            if (isValidMove(piece, from, to)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkWinCondition() {
        Player opponent = (currentPlayer == Player.PLAYER1) ? Player.PLAYER2 : Player.PLAYER1;

        for (int r=0; r<8; r++) {
            for (int c=0; c<8; c++) {
                Piece piece = board.getPiece(r, c);
                if (piece != null && piece.getOwner() == opponent) {
                    Position from = new Position(r, c);
                    for (int dr = -2; dr <= 2; dr++) {
                        for (int dc = -2; dc <= 2; dc++) {
                            if (Math.abs(dr) == Math.abs(dc) && dr != 0) {
                                int nr = r + dr;
                                int nc = c + dc;
                                if (nr >= 0 && nr < 8 && nc >= 0 && nc < 8) {
                                    Position to = new Position(nr, nc);
                                    if (isValidMove(piece, from, to)) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Player getWinner() {
        return winner;
    }
}