package org.scaler.tictactoe.controllers;

import org.scaler.tictactoe.exceptions.BotCountExceedException;
import org.scaler.tictactoe.exceptions.CellOverrideException;
import org.scaler.tictactoe.exceptions.PlayersCountMismatchException;
import org.scaler.tictactoe.exceptions.SymbolDuplicateException;
import org.scaler.tictactoe.factories.BotPlayingStrategyFactory;
import org.scaler.tictactoe.models.*;
import org.scaler.tictactoe.strategies.BotPlayingStrategies.BotPlayingStrategy;
import org.scaler.tictactoe.strategies.WinningStrategies.WinningStrategy;

import java.util.List;

public class GameController {
    public Game startGame(int dimension, List<Player> players, List<WinningStrategy> winningStrategies)
            throws BotCountExceedException, PlayersCountMismatchException, SymbolDuplicateException {
        return Game.getBuilder()
                .setDimension(dimension)
                .setPlayers(players)
                .setStrategies(winningStrategies)
                .build();
    }

    public GameStatus checkState(Game game){
        return game.checkState();
    }

    public Player getWinner(Game game){
        if(game.getGameStatus().equals(GameStatus.ENDED)){
            return game.getWinner();
        }else{
            return null;
        }
    }

    public void displayBoard(Game game){
        game.getBoard().displayBoard();
    }


    public void makeMove(Game game) throws CellOverrideException {
        game.makeMove();
    }


    public void undoMove(Game game){
        game.undo();
    }
}
