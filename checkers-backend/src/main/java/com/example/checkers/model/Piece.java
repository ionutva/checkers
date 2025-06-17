package com.example.checkers.model;

public class Piece {

    private Player owner;
    private PieceType type;

    public Piece(Player owner, PieceType type) {
        this.owner = owner;
        this.type = type;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public PieceType getType() {
        return type;
    }

    public void setType(PieceType type) {
        this.type = type;
    }
}