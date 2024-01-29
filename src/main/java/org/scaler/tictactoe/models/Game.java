package org.scaler.tictactoe.models;

import org.scaler.tictactoe.exceptions.BotCountExceedException;
import org.scaler.tictactoe.exceptions.CellOverrideException;
import org.scaler.tictactoe.exceptions.PlayersCountMismatchException;
import org.scaler.tictactoe.exceptions.SymbolDuplicateException;
import org.scaler.tictactoe.strategies.WinningStrategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Game {
    private List<Player> players;
    private Board board;
    private List<Move> moves;
    private GameStatus gameStatus;
    private Player winner;
    private int currentMovePlayerIndex;
    private List<WinningStrategy> strategies;

    private List<HashMap<Character,Integer>> rowMaps;
    private List<HashMap<Character,Integer>> colMaps;
    private List<HashMap<Character,Integer>> diagMaps;

    public void makeMove() throws CellOverrideException{
        if(this.getMoves().size() == this.getBoard().getSize() * this.getBoard().getSize()){
            this.setGameStatus(GameStatus.DRAW);
            return ;
        }
        Player currentPlayer = this.getPlayers().get(this.getCurrentMovePlayerIndex());
        Move move = currentPlayer.makeMove(this.getBoard());

        int index = this.getCurrentMovePlayerIndex()+1;
        this.setCurrentMovePlayerIndex(index%(this.getBoard().getSize()-1));

        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

//        System.out.println(" row = "+row+"col = "+col
//        +" status ="+ board.getBoard().get(row).get(col).getStatus());

        //validate if cell empty and update on board
        if(!board.getBoard().get(row).get(col).getStatus().equals(CellState.EMPTY)){
            throw new CellOverrideException();
        }

        moves.add(move);
        board.getBoard().get(move.getCell().getRow()).set(move.getCell().getCol(),move.getCell());

        char symbol = move.getPlayer().getSymbol().getaChar();
        updateMaps(move.getCell().getRow(), move.getCell().getCol(), symbol, false);

    }
    public GameStatus checkState(){
        List<WinningStrategy> strategies = this.getStrategies();
        //check for all strategies
        if(!this.getMoves().isEmpty()) {
            for(WinningStrategy strategy: strategies){
                if (strategy.checkWinner(this, this.getMoves().getLast())) {
                    this.setGameStatus(GameStatus.ENDED);
                    this.setWinner(this.getMoves().getLast().getPlayer());
                    return GameStatus.ENDED;
                }
            }
        }
        return GameStatus.INPROGRESS;
    }

    public void undo(){
        Move lastMove = this.getMoves().getLast();
        int row = lastMove.getCell().getRow();
        int col = lastMove.getCell().getCol();

        //remove the last move from list
        this.getMoves().removeLast();

        //remove the cell entry from board
        Cell emptyCell = new Cell(row, col );
        this.getBoard().getBoard().get(row).set(col, emptyCell);

        //update maps in reverse
        updateMaps(row, col, lastMove.getPlayer().getSymbol().getaChar(),true);
    }

    public void updateMaps(int row, int col, char symbol, boolean undo){
        //update maps
        int rowCount = this.rowMaps.get(row).get(symbol);
        if(undo){ rowCount--; }else{ rowCount++; }
        this.rowMaps.get(row).put(symbol,rowCount);

        int colCount = this.colMaps.get(col).get(symbol);
        if(undo){ colCount--; }else{ colCount++; }
        this.colMaps.get(col).put(symbol,colCount);

        if(row == col){
            int diagCount = this.diagMaps.getFirst().get(symbol);
            if(undo){ diagCount--; }else{ diagCount++; }
            this.diagMaps.getFirst().put(symbol,diagCount);
        }

        if( (row+col) == (this.board.getSize()-1)){
            int adiagCount = this.diagMaps.getLast().get(symbol);
            if(undo){ adiagCount--; }else{ adiagCount++; }
            this.diagMaps.getLast().put(symbol,adiagCount);
        }
    }

    public Game(int gamesize, List<Player> players, List<WinningStrategy> strategies){
        this.board = new Board(gamesize);
        this.players = players;
        this.moves = new ArrayList<Move>();
        this.gameStatus = GameStatus.INPROGRESS;
        this.currentMovePlayerIndex = 0;
        this.strategies = strategies;
        initializeMaps();
    }

    private void initializeMaps(){
        this.rowMaps = new ArrayList<>();

        for(int i = 0; i<3; i++) {
            HashMap<Character, Integer> map = new HashMap<>();
            map.put('X', 0);
            map.put('O', 0);
            this.rowMaps.add(map);
        }

        this.colMaps = new ArrayList<>();

        for(int i = 0; i<3; i++) {
            HashMap<Character, Integer> map = new HashMap<>();
            map.put('X', 0);
            map.put('O', 0);
            this.colMaps.add(map);
        }

        this.diagMaps = new ArrayList<>();

        for(int i = 0; i<2; i++) {
            HashMap<Character, Integer> map = new HashMap<>();
            map.put('X', 0);
            map.put('O', 0);
            this.diagMaps.add(map);
        }
    }

    public static Builder getBuilder(){
        return new Builder();
    }

    public static class Builder{
       private List<Player> players;
       private List<WinningStrategy> strategies;
       private int dimension;

       public Builder setPlayers(List<Player> players) {
           this.players = players;
           return this;
       }

       public Builder setStrategies(List<WinningStrategy> strategies) {
           this.strategies = strategies;
           return this;
       }

       public Builder setDimension(int dimension) {
           this.dimension = dimension;
           return this;
       }

       public void validateNoOfBots() throws BotCountExceedException{
           int count = 0;
           for(Player player: this.players){
               if(player.getType().equals(PlayerType.BOT)){
                   count++;
               }
           }
           if(count>1){
               throw new BotCountExceedException();
           }
       }

       public void validateNoOfPlayers() throws PlayersCountMismatchException{
           if(this.dimension-1 != this.players.size()){
               throw new PlayersCountMismatchException();
           }
       }

       public void validateUniqueSymbol() throws SymbolDuplicateException{
           HashSet<Character> symbols = new HashSet<>();
           for(Player player : this.players){
               symbols.add(player.getSymbol().getaChar());
           }
           if(symbols.size() != players.size()){
               throw new SymbolDuplicateException();
           }
       }

       public void validate() throws BotCountExceedException, PlayersCountMismatchException, SymbolDuplicateException{
           //validate no of bots
           validateNoOfBots();
           //validate no of players = size -1
           validateNoOfPlayers();
           //validate unique symbols
           validateUniqueSymbol();
       }

       public Game build() throws BotCountExceedException, PlayersCountMismatchException, SymbolDuplicateException{
           validate();
           //we can add validation here before game object creation
           return new Game(dimension, players, strategies);
       }


    }
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public int getCurrentMovePlayerIndex() {
        return currentMovePlayerIndex;
    }

    public void setCurrentMovePlayerIndex(int currentMovePlayerIndex) {
        this.currentMovePlayerIndex = currentMovePlayerIndex;
    }

    public List<WinningStrategy> getStrategies() {
        return strategies;
    }

    public void setStrategies(List<WinningStrategy> strategies) {
        this.strategies = strategies;
    }


    public List<HashMap<Character, Integer>> getRowMaps() {
        return rowMaps;
    }

    public void setRowMaps(List<HashMap<Character, Integer>> rowMaps) {
        this.rowMaps = rowMaps;
    }

    public List<HashMap<Character, Integer>> getColMaps() {
        return colMaps;
    }

    public void setColMaps(List<HashMap<Character, Integer>> colMaps) {
        this.colMaps = colMaps;
    }

    public List<HashMap<Character, Integer>> getDiagMaps() {
        return diagMaps;
    }

    public void setDiagMaps(List<HashMap<Character, Integer>> diagMaps) {
        this.diagMaps = diagMaps;
    }
}
