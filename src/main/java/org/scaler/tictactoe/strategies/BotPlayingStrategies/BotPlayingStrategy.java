package org.scaler.tictactoe.strategies.BotPlayingStrategies;

import org.scaler.tictactoe.models.Board;
import org.scaler.tictactoe.models.Cell;

public interface BotPlayingStrategy {
    public Cell makeMove(Board board);
//this will return which cell to make move , we will only need current board state
}
