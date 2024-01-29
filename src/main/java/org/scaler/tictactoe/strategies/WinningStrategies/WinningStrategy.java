package org.scaler.tictactoe.strategies.WinningStrategies;

import org.scaler.tictactoe.models.Board;
import org.scaler.tictactoe.models.Game;
import org.scaler.tictactoe.models.Move;

public interface WinningStrategy {
    public boolean checkWinner(Game game, Move move);
    // we will need board as well as last player move to run the logic
}
