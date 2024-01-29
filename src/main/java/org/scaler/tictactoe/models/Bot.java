package org.scaler.tictactoe.models;

import org.scaler.tictactoe.factories.BotPlayingStrategyFactory;
import org.scaler.tictactoe.strategies.BotPlayingStrategies.BotPlayingStrategy;

public class Bot extends Player {
    private BotDifficultyLevel level;

    public Bot(String name, Symbol symbol,  BotDifficultyLevel level) {
        super(name, symbol, PlayerType.BOT);
        this.level = level;
    }

    public BotDifficultyLevel getLevel() {
        return level;
    }

    public void setLevel(BotDifficultyLevel level) {
        this.level = level;
    }

    @Override
    public Move makeMove(Board board){
        System.out.println("Bot making move : ");
        BotPlayingStrategy strategy = BotPlayingStrategyFactory.getBotPlayingStrategy(this.getLevel());

        Cell cell = strategy.makeMove(board);

        Cell moveCell = new Cell(cell.getRow(), cell.getCol());
        moveCell.setStatus(CellState.FILLED);
        moveCell.setPlayer(this);

        return new Move(this, moveCell);
    }


}
