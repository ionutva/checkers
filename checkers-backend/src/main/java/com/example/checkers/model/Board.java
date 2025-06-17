package com.example.checkers.model;

public class Board {

    private Piece[][] cells = new Piece[8][8];

    public Board() {
        initializePieces();
    }

    private void initializePieces() {
        // Player 1 pieces at rows 0-2 on black squares (odd sums)
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 8; c++) {
                if ((r + c) % 2 == 1) {
                    cells[r][c] = new Piece(Player.PLAYER1, PieceType.MAN);
                }
            }
        }
        // Player 2 pieces at rows 5-7 on black squares (odd sums)
        for (int r = 5; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if ((r + c) % 2 == 1) {
                    cells[r][c] = new Piece(Player.PLAYER2, PieceType.MAN);
                }
            }
        }
    }

    public Piece getPiece(int row, int col) {
        return cells[row][col];
    }

    public void setPiece(int row, int col, Piece piece) {
        cells[row][col] = piece;
    }
}