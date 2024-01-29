package org.scaler.tictactoe.strategies.BotPlayingStrategies;

import org.scaler.tictactoe.models.Board;
import org.scaler.tictactoe.models.Cell;
import org.scaler.tictactoe.models.CellState;

public class EasyBotPlayingStrategy implements BotPlayingStrategy{
    @Override
    public Cell makeMove(Board board) {
        int size = board.getSize();
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                Cell cell = board.getBoard().get(i).get(j);
                if(cell.getStatus().equals(CellState.EMPTY)){
                    return cell;
                }
            }
        }
        return null;
    }
}
