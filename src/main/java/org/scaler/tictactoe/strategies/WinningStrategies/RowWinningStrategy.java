package org.scaler.tictactoe.strategies.WinningStrategies;

import org.scaler.tictactoe.models.Board;
import org.scaler.tictactoe.models.Cell;
import org.scaler.tictactoe.models.Game;
import org.scaler.tictactoe.models.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RowWinningStrategy implements WinningStrategy{
    @Override
    public boolean checkWinner(Game game, Move move) {
        Character currentPlayerSymbol = move.getPlayer().getSymbol().getaChar();

        List<HashMap<Character, Integer>> rowMaps = game.getRowMaps();
        for(HashMap<Character, Integer> row: rowMaps){
            if(row.get(currentPlayerSymbol) == game.getBoard().getSize()){
                return true;
            }
        }
        return false;
    }
}
