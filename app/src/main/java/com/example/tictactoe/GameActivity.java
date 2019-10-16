package com.example.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnPositionZero;
    private Button btnPositionOne;
    private Button btnPositionTwo;
    private Button btnPositionThree;
    private Button btnPositionFour;
    private Button btnPositionFive;
    private Button btnPositionSix;
    private Button btnPositionSeven;
    private Button btnPositionEight;
    private Button btnPlayAgain;
    private GameBoard gameBoard;
    private TextView txvTurnID;
    private TextView TxvXScore;
    private TextView TxvYScore;
    private TextView TxvDraw;
    private SharedPreferences sp;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gameBoard = new GameBoard();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //connect all the buttons
        btnPositionZero = findViewById(R.id.btnPosition0);
        btnPositionOne = findViewById(R.id.btnPosition1);
        btnPositionTwo = findViewById(R.id.btnPosition2);
        btnPositionThree = findViewById(R.id.btnPosition3);
        btnPositionFour = findViewById(R.id.btnPosition4);
        btnPositionFive = findViewById(R.id.btnPosition5);
        btnPositionSix = findViewById(R.id.btnPosition6);
        btnPositionSeven = findViewById(R.id.btnPosition7);
        btnPositionEight = findViewById(R.id.btnPosition8);
        TxvXScore = findViewById(R.id.txvXscore);
        TxvYScore = findViewById(R.id.txvYscore);
        TxvDraw = findViewById(R.id.txvDraw);
        btnPlayAgain = findViewById(R.id.btnPlayAgainID);
        //set click listeners to all buttons
        btnPositionZero.setOnClickListener(this);
        btnPositionOne.setOnClickListener(this);
        btnPositionTwo.setOnClickListener(this);
        btnPositionThree.setOnClickListener(this);
        btnPositionFour.setOnClickListener(this);
        btnPositionFive.setOnClickListener(this);
        btnPositionSix.setOnClickListener(this);
        btnPositionSeven.setOnClickListener(this);
        btnPositionEight.setOnClickListener(this);
        btnPlayAgain.setOnClickListener(this);
        txvTurnID=findViewById(R.id.txvTurnID);
        btnPlayAgain.setEnabled(false);
        getResult();
    }

    @Override
    protected void onStop() {//when the screen is out of focus
        super.onStop();
        updateResults();//keep all the results
        if(mediaPlayer!=null && mediaPlayer.isPlaying())//stop the sound
            mediaPlayer.stop();
    }

    private void updateResults() {
        //set  the current scores to the local file
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("x_score", gameBoard.getXScoreCount());
        editor.putInt("y_score", gameBoard.getYScoreCount());
        editor.putInt("draw", gameBoard.getDrawCount());
        editor.commit();
    }

    private void getResult() {
        //get the scores from the memory
        sp = getSharedPreferences("TicTacToePreferences", Context.MODE_PRIVATE);
        int XScore = sp.getInt("x_score", 0);
        int YScore = sp.getInt("y_score", 0);
        int draw = sp.getInt("draw", 0);
        //set the results to the right location
        gameBoard.setXScoreCount(XScore);
        gameBoard.setYScoreCount(YScore);
        gameBoard.setDrawCount(draw);
        //update the text views
        TxvXScore.setText(gameBoard.getXScoreCount() + "");
        TxvYScore.setText(gameBoard.getYScoreCount() + "");
        TxvDraw.setText(gameBoard.getDrawCount() + "");

    }

    @Override
    public void onClick(View v) {

        //btnPlay again pressed
        if(v.getId()== R.id.btnPlayAgainID) {
            //play again
            startOver();
            return;
        }
        //go through all the buttons to find the correct button that was clicked
        int position ;
        int  boardLength = gameBoard.getBoardLength();
        for (int i=0;  i<boardLength; i++)
            for (int j=0;  j<boardLength; j++)
            {
                position =  i*3 + j;
                Button button= (Button) findViewById(getResources().getIdentifier("btnPosition" + position,"id", this.getPackageName()));
                if(button!=null && v.getId()==button.getId())//
                {
                    button.setText(getCurrentPlayer());//update the button text to x/o according to the current player
                    button.setEnabled(false);//set the clicked button unclickable
                    sendForCheck(i,j);//check if there is winning/ draw or if the game should continue
                    return;
                }
            }
    }

    /***
     * the function gets the place where the player marked, and check if he won the game there is a draw
     * @param x the board row
     * @param y the board column
     */
    private void sendForCheck(int x, int y){
        gameBoard.checkForWin(x,y);
        if(gameBoard.isContinueGame()) {//if the player didnt won
            txvTurnID.setText(getCurrentPlayer()+" turn");
            return;
        }
        if(gameBoard.isDraw())//if the game finished with draw
        {
            txvTurnID.setText("Draw - No Winner!");
            TxvDraw.setText(gameBoard.getDrawCount() + "");
            btnPlayAgain.setEnabled(true);
            Toast.makeText(this, "Game Over!" , Toast.LENGTH_LONG).show();
            return;
        }
        //color the text of the winning buttons:
        int position ;
        boolean [][] wining = gameBoard.getWiningSign();//matrix that holds the positions of the winning buttons
        for (int i=0;  i<wining.length; i++)
            for (int j=0;  j<wining.length; j++)
            {
                if(wining[i][j])//if the current button is winning button
                {
                    position =  i*3 + j;//calc the number of the butten id
                    //get the button by its id
                    Button button= (Button) findViewById(getResources().getIdentifier("btnPosition" + position,"id", this.getPackageName()));
                    button.setTextColor(getResources().getColor(R.color.colorAccent));//change the button text color to the winning color
                }
            }
        //update all the text views with the right results
        TxvXScore.setText(gameBoard.getXScoreCount() + "");
        TxvYScore.setText(gameBoard.getYScoreCount() + "");
        txvTurnID.setText(getCurrentPlayer()+ " Won!!");
        btnPlayAgain.setEnabled(true);
        Toast.makeText(this, "Game Over!" , Toast.LENGTH_LONG).show();
        makeSound();//make a media sound and activate it
        blockGame(true);//block all buttons
    }

    /**
     * make a media sound and activate it
     */
    private void makeSound() {
        mediaPlayer = MediaPlayer.create(this,R.raw.clapping_sound);
        mediaPlayer.start();
    }

    /**
     * starts the game over
     */
    private void startOver()
    {
        if (mediaPlayer!=null && mediaPlayer.isPlaying())
            mediaPlayer.stop();//stop the souns
        for(int i = 0; i< 9; i++){//change all the buttons colors back to black
            Button button= (Button) findViewById(getResources().getIdentifier("btnPosition" + i,"id", this.getPackageName()));
            button.setText("");
            button.setTextColor(getResources().getColor(R.color.colorBlack));

        }
        txvTurnID.setText("X turn");
        btnPlayAgain.setEnabled(false);
        gameBoard.startOver();
        blockGame(false);
    }

    /**
     * set all the buttons to clickable / unclickable
     * @param block says true if we want to block and false otherwise
     */
    private void blockGame(boolean block) {
        for(int i = 0; i< 9; i++){
            Button button= (Button) findViewById(getResources().getIdentifier("btnPosition" + i,"id", this.getPackageName()));
            button.setEnabled(!block);
        }
    }

    /**
    * @return the char of the current player (x for 1, o for 0)
    */
    public String getCurrentPlayer() {
        if (gameBoard.getCurrentPlayer() == 1)
            return "X";
        else return "O";
    }
}