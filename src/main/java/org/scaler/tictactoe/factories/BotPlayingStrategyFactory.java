package org.scaler.tictactoe.factories;

import org.scaler.tictactoe.models.BotDifficultyLevel;
import org.scaler.tictactoe.strategies.BotPlayingStrategies.BotPlayingStrategy;
import org.scaler.tictactoe.strategies.BotPlayingStrategies.EasyBotPlayingStrategy;
import org.scaler.tictactoe.strategies.BotPlayingStrategies.HardBotPlayingStrategy;
import org.scaler.tictactoe.strategies.BotPlayingStrategies.MediumBotPlayingStrategy;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy getBotPlayingStrategy(BotDifficultyLevel level){
        if(level == BotDifficultyLevel.EASY){
            return new EasyBotPlayingStrategy();
        }else if(level == BotDifficultyLevel.MEDIUM){
            return new MediumBotPlayingStrategy();
        }else{
            return new HardBotPlayingStrategy();
        }
    }
}
