package com.example.tictactoe;

/***
 * the game board control the game logic
 * written by shulamit cohen id:316302389 and hila grosbard id: 208502054
 */

public class GameBoard {
    private static final int N = 3;
    private boolean draw = false;//flags
    private boolean continueGame = true ;
    private int board[][];//contain the value on the board
    private int currentPlayer;
    private boolean [][] winingSign;//contain true in the wining places
    private int XScoreCount;
    private int YScoreCount;
    private int drawCount;

    public int getBoardLength() {
        return board.length;
    }

    public GameBoard() {
//init the game board
        this.board = new int[N][N];
        this.currentPlayer = 1;//x player =1 o player =2 0=empty place
        winingSign = new boolean[N][N];
    }

    public void checkForWin(int x, int y) {
//check if the current player has won
        this.board[x][y] = currentPlayer;
        if (x == 1 && y == 1) {//check for winnings at the diagonals
            if (searchLeftDiagonal()) {//if won
                winingSign[0][2] = winingSign[1][1] = winingSign[2][0] = true;//update this buttons at the matrix as a winning buttons
                continueGame=false;//game should not be continued
                addScore();//update the score
                return;
            }
            else if (searchRightDiagonal()) {//if won at the other diagonal
                winingSign[0][0] = winingSign[1][1] = winingSign[2][2] = true;//update this buttons at the matrix as a winning buttons
                continueGame=false;//game should not be continued
                addScore();//update the score
                return;
            }
        }
        if (x == 0 && y == 0 || x == N - 1 && y == N - 1) {//check for winnings at the diagonals
            if (searchRightDiagonal()) {//if won
                winingSign[0][0] = winingSign[1][1] = winingSign[2][2] = true;//update this buttons at the matrix as a winning buttons
                continueGame=false;//game should not be continued
                addScore();//update the score
                return;
            }
        }
        if (x == 0 && y == N - 1 || x == N-1 && y == 0) {//check for winnings at the diagonals
            if (searchLeftDiagonal()) {//if won
                winingSign[0][2] = winingSign[1][1] = winingSign[2][0] = true;//update this buttons at the matrix as a winning buttons
                continueGame=false;
                addScore();
                return;
            }
        }
        if (searchColumn(y)) {//if won at the column
            for (int i=0; i<winingSign.length; i++)
                winingSign[i][y]=true;//update in the winning matrix
            continueGame=false;
            addScore();
            return;
        }
        if (searchROW(x)) {//if won at row
            for (int i=0; i<winingSign.length; i++)
                winingSign[x][i]=true;
            continueGame=false;
            addScore();
            return;
        }
        if (checkDraw())//if there is a draw
        {
            draw=true;
            continueGame=false;
            drawCount++;
        }
        setCurrentPlayer();//change current players turn
    }

    private void addScore() {
        //add 1 point to the winning player
        if (currentPlayer==1)
            XScoreCount++;
        else
            YScoreCount++;
    }

    /***
     * check if there is a draw
     * @return true if there is a draw and flase other wise
     */
    private boolean checkDraw() {
        for (int i=0;i<board.length;i++)
            for (int j=0;j<board.length;j++)
            {
                if(board[i][j]==0)
                    return false;
            }
        return true;
    }

    /***
     * calculate if there is a winning at the row
     * @param x - the row number
     * @return true if there is a row wining
     */
    private boolean searchROW(int x){
        for(int i=0;i<this.board.length; i++) {
            if (board[x][i] != this.currentPlayer)
                return false;
        }
        return true;
    }
    /***
     * calculate if there is a winning at the column
     * @param y - the column number
     * @return true if there is a column wining
     */
    private boolean searchColumn(int y){
        for(int i=0;i<this.board.length; i++) {
            if (board[i][y] != this.currentPlayer)
                return false;
        }
        return true;
    }
    /***
     * calculate if there is a winning at the right diagonal
     * @return true if there is a right diagonal wining
     */
    private boolean searchRightDiagonal(){
        for(int i=0;i<this.board.length; i++) {
            if (board[i][i] != this.currentPlayer)
                return false;
        }
        return true;
    }
    /***
     * calculate if there is a winning at the left diagonal
     * @return true if there is a left diagonal wining
     */
    private boolean searchLeftDiagonal(){
        for(int i=0;i<this.board.length; i++) {
            if (board[i][this.board.length-i-1] != this.currentPlayer)
                return false;
        }
        return true;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    private void setCurrentPlayer() {
        //change the players turn
        if(currentPlayer==1)
            this.currentPlayer = 2;
        else
            this.currentPlayer=1;
    }

    /***
     * start the game all over , initiate the board
     */
    public void startOver() {
        for (int i=0; i<board.length; i++)
            for (int j=0; j<board[0].length; j++)
            {
                board[i][j]=0;
                winingSign[i][j]=false;
            }
        currentPlayer=1;
        draw = false;
        continueGame = true ;
    }

    public boolean isDraw() {
        return draw;
    }

    public boolean isContinueGame() {
        return continueGame;
    }

    public boolean[][] getWiningSign() {
        return winingSign;
    }

    public void setXScoreCount(int XScoreCount) {
        this.XScoreCount = XScoreCount;
    }

    public void setYScoreCount(int YScoreCount) {
        this.YScoreCount = YScoreCount;
    }

    public void setDrawCount(int drawCount) {
        this.drawCount = drawCount;
    }

    public int getXScoreCount() {
        return XScoreCount;
    }

    public int getYScoreCount() {
        return YScoreCount;
    }

    public int getDrawCount() {
        return drawCount;
    }
}
