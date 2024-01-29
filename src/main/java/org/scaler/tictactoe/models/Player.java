package org.scaler.tictactoe.models;

import java.util.Scanner;

public class Player {
    private String name;
    private Symbol symbol;
    private PlayerType type;

    public Player(String name, Symbol symbol,PlayerType type){
        this.name = name;
        this.symbol = symbol;
        this.type = type;
    }

    public Move makeMove(Board board){
        Scanner src = new Scanner(System.in);
        System.out.println("Enter : "+this.getName()+" row col : ");

        int row = src.nextInt();
        int col = src.nextInt();

        Cell cell = new Cell(row, col);
        cell.setStatus(CellState.FILLED);
        cell.setPlayer(this);

        return new Move(this, cell);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public PlayerType getType() {
        return type;
    }

    public void setType(PlayerType type) {
        this.type = type;
    }
}
