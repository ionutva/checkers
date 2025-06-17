package com.example.checkers.model;

import java.util.ArrayList;
import java.util.List;

public class GameStateDto {
    private String currentPlayer;
    private boolean isGameOver;
    private String winner;
    private List<CellDto> board;

    public GameStateDto(Game game) {
        this.currentPlayer = game.getCurrentPlayer().name();
        this.isGameOver = game.isGameOver();
        this.winner = game.getWinner() == null ? null : game.getWinner().name();

        board = new ArrayList<>();
        Board b = game.getBoard();
        for (int r =0; r < 8; r++) {
            for (int c=0; c<8; c++) {
                Piece p = b.getPiece(r,c);
                board.add(new CellDto(r,c,p));
            }
        }
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public String getWinner() {
        return winner;
    }

    public List<CellDto> getBoard() {
        return board;
    }

    public static class CellDto {
        private int row;
        private int col;
        private String owner;
        private String type;

        public CellDto(int row, int col, Piece piece) {
            this.row = row;
            this.col = col;
            if (piece != null) {
                this.owner = piece.getOwner().name();
                this.type = piece.getType().name();
            }
        }

        public int getRow() {
            return row;
        }
        public int getCol() {
            return col;
        }
        public String getOwner() {
            return owner;
        }
        public String getType() {
            return type;
        }
    }
}