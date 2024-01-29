package org.scaler.tictactoe.strategies.WinningStrategies;

import org.scaler.tictactoe.models.Board;
import org.scaler.tictactoe.models.Game;
import org.scaler.tictactoe.models.Move;

import java.util.HashMap;
import java.util.List;

public class ColWinningStrategy implements WinningStrategy{
    @Override
    public boolean checkWinner(Game game, Move move) {

        Character currentPlayerSymbol = move.getPlayer().getSymbol().getaChar();

        List<HashMap<Character, Integer>> colMaps = game.getColMaps();
        for(HashMap<Character, Integer> col: colMaps){
            if(col.get(currentPlayerSymbol) == game.getBoard().getSize()){
                return true;
            }
        }
        return false;
    }
}
