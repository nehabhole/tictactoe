package org.scaler.tictactoe.strategies.WinningStrategies;

import org.scaler.tictactoe.models.Board;
import org.scaler.tictactoe.models.Game;
import org.scaler.tictactoe.models.Move;

public class DiagWinningStrategy implements WinningStrategy{
    @Override
    public boolean checkWinner(Game game, Move move) {
        return false;
    }
}
