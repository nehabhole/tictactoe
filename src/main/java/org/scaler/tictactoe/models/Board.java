package org.scaler.tictactoe.models;

import java.util.ArrayList;
import java.util.List;

public class Board {
    int size;

    List<List<Cell>> board;

    public Board(int size){
        this.size = size;
        this.board = new ArrayList<>();

        for(int i = 0; i<size; i++){
            this.board.add(new ArrayList<>());
            for(int j =0 ; j<size; j++){
                this.board.get(i).add(new Cell(i,j));
            }
        }
    }

    public void displayBoard(){
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                Cell cell = board.get(i).get(j);
                if(cell.getStatus().equals(CellState.EMPTY)){
                    System.out.print("-"+" ");
                }else {
                    System.out.print(cell.getPlayer().getSymbol().getaChar() + " ");
                }
            }
            System.out.println();
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<List<Cell>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Cell>> board) {
        this.board = board;
    }
}
