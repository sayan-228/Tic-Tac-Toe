package org.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    // GUI variables
    private GridPane gridPane=new GridPane();
    private BorderPane borderPane=new BorderPane();
    private Label title=new Label("Tic Tac Toe");

    private Button newButton=new Button("New Game");
    Font font=Font.font("Segoe UI", FontWeight.BOLD,30);

    // game logic variables
    boolean gameOver=false;
    char activePlayer='O';
    char gameState[]={'B','B','B','B','B','B','B','B','B'}; //3 means no update is done
    int winning_positions[][]={
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}
    };
    int count=0;



// Creating the boxes in grid
    private Button[] boxes=new Button[9];

    //Stage and Scene Handler
    @Override
    public void start(Stage stage) throws IOException {
//        Button b=new Button("This is my button");
//
//        b.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                System.out.println("Button clicked");
//            }
//        });

        this.createGUI();
        this.handleEvent();
        scene = new Scene(borderPane, 480, 480);
        stage.setScene(scene);
        stage.show();
    }



    //Function for creating TicTacToe GUI
    private void createGUI() {

        title.setFont(font);
        newButton.setFont(font);
        newButton.setDisable(true);
        //setting title and newButton to borderPane
        borderPane.setTop(title);
        borderPane.setBottom(newButton);
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(newButton, Pos.CENTER);
        borderPane.setPadding(new Insets(20,20,20,20));

        //9 game Button
        int label=0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                Button button=new Button();
                button.setId(label+"");
                button.setFont(font);
                button.setPrefHeight(120);
                button.setPrefWidth(120);
                gridPane.add(button,j,i);
                gridPane.setAlignment(Pos.CENTER);
                boxes[label]=button;
                label++;
            }
        }

        borderPane.setCenter(gridPane);
        gridPane.setPadding(new Insets(20,20,20,20));

    }


    //Event Handling methods
    private void handleEvent() {

        //Handling button of new game
        newButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for(int i=0;i<9;i++){
                    gameState[i]='B';
                    boxes[i].setText("");
                    gameOver=false;
                    newButton.setDisable(true);
                }
                System.out.println("Playing another game");
            }
        });

        //Handling operations in grid
        for(Button btn:boxes){
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Button currentBtn=(Button)actionEvent.getSource();
                    String idS=currentBtn.getId();
                    int idI=Integer.parseInt(idS);  //Type casting into int

                    //Checking game over condition

                    if(gameOver==true){
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error message");
                        alert.setContentText("Game over! Play again");
                        alert.show();
                    }
                    else{
                        if (gameState[idI]=='B'){
                            if (activePlayer=='X'){
                                //chance of 1
                                currentBtn.setText(activePlayer+"");
                                gameState[idI]=activePlayer;
                                checkForWinner();
                                activePlayer='O';
                            }
                            else{
                                //chance of 0
                                currentBtn.setText(activePlayer+"");
                                gameState[idI]=activePlayer;
                                checkForWinner();
                                activePlayer='X';
                            }
                        }
                        else{
                            Alert alert=new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error message");
                            alert.setContentText("Box occupied");
                            alert.show();
                        }

                    }

                }
            });

        }
    }

    //Method for Checking Winner
    private void checkForWinner(){
        if (gameOver==false) {
            for(int wp[]:winning_positions) {
                if (gameState[wp[0]] == gameState[wp[1]] && gameState[wp[1]] == gameState[wp[2]] && gameState[wp[1]] != 'B') {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Success message");
                    alert.setContentText("Player " + activePlayer + " has won");
                    alert.show();
                    System.out.println("Player " + activePlayer + " won the game");
                    gameOver = true;
                    newButton.setDisable(false);
                    break;
                }
            }
            for(int i=0;i<9;i++){
                if (gameState[i]!='B'){
                    count+=1;
                }
            }
            if(count==9){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Success message");
                alert.setContentText("Match Tied");
                alert.show();
                gameOver = true;
                newButton.setDisable(false);
            }
            else{
                count=0;
            }

        }
    }

    //Main function for launching the application
    public static void main(String[] args) {
        launch();
    }

}