import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.*;
import java.util.*;

import javafx.scene.text.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
 
import javafx.scene.Group;
import javafx.scene.Scene; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage; 
import javafx.scene.*;

import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;

public class Main extends Application{
	static int turn = 0;
	static int[][] arr = {{-1, -1, -1}, {-1, -1, -1}, {-1, -1, -1}};
	static boolean gameOver = false;
	static String gameType = "";
	static String player1Choice = "";
	
	
	static FileInputStream circleImageFile;
	static FileInputStream crossImageFile; 
	static FileInputStream blankImageFile; 
	
	
	static Image circleImage;
	static Image crossImage;
	static Image blankImage;
	
	static Image player1Image;
	static Image player2Image;
	
	static String winner = "";
	
	public static void checkWinner() {
		int[] player2 = {1, 1, 1};
        int[] player1 = {0, 0, 0};

        int[] arrRow1 = {arr[0][0], arr[0][1], arr[0][2]};
        int[] arrRow2 = {arr[1][0], arr[1][1], arr[1][2]};
        int[] arrRow3 = {arr[2][0], arr[2][1], arr[2][2]};

        int[] arrCol1 = {arr[0][0], arr[1][0], arr[2][0]};
        int[] arrCol2 = {arr[0][1], arr[1][1], arr[2][1]};
        int[] arrCol3 = {arr[0][2], arr[1][2], arr[2][2]};

        int[] arrDiag1 = {arr[0][0], arr[1][1], arr[2][2]};
        int[] arrDiag2 = {arr[0][2], arr[1][1], arr[2][0]};

        int[][] check = {arrRow1, arrRow2, arrRow3, arrCol1, arrCol2, arrCol3, arrDiag1, arrDiag2};

        for (int i = 0; i < 8; ++i) {

            if (Arrays.equals(check[i], player2)) {
                if (gameType.equals("singlePlayer")) {
                	winner = "Computer won";
                }
                else {
                	winner = "Player 2 won";
                }
                gameOver = true;
                break;
            }
            if (Arrays.equals(check[i], player1)) {
                winner = "Player 1 won";
                gameOver = true;
                break;
            }
        }
        if (!gameOver && allFilled()) {
            winner = "Its a tie";
            gameOver = true;
        }
	}
	
	public static boolean allFilled() {
        int count = 0;
        for (int i = 0; i < 3; ++i) {
        	for (int j = 0; j < 3; ++j) {
        		if (arr[i][j] != -1) {
        			count++;
        		}
        	}
        }
        return count == 9;
    }
	
	
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tic - Tac - Toe");
        
        Label winnerLabel = new Label();
        winnerLabel.setFont(Font.font("Ariel", FontWeight.BOLD, 22));
       
	
		FileInputStream gridImageFile = new FileInputStream("grid.jpg");
		Image gridImage = new Image(gridImageFile);
		ImageView gridImageView = new ImageView(gridImage);
		
		
		Main.circleImageFile = new FileInputStream("circle.png");
		Main.crossImageFile = new FileInputStream("cross.png");
		Main.blankImageFile = new FileInputStream("blank.png");
		
		Main.circleImage = new Image(circleImageFile, 150, 150, true, false);
		Main.crossImage = new Image(crossImageFile, 150, 150, true, false);
		Main.blankImage = new Image(blankImageFile, 150, 150, true, false);
		
		Main.player1Image = circleImage;
		Main.player2Image = crossImage;
		
		gridImageView.setFitWidth(500);
		gridImageView.setPreserveRatio(true);
		
		GridPane grid = new GridPane();
		
		grid.setAlignment(Pos.CENTER);
		
		grid.setVgap(20);
		grid.setHgap(20);
		
		int[][] arr = {{0, 0}, {1, 0}, {2, 0}, {2, 1}, {1, 1}, {0, 1}, {2, 2}, {1, 2}, {0, 2}};
		for (int i = 0; i < 9; ++i) {
			grid.add(new ImageView(Main.blankImage), arr[i][0], arr[i][1]);
		}
		
		for (Node item : grid.getChildren()) {
			item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                	if (Main.gameOver == false) {
                	
		            	Image changedImage;
		            	
		            	if (Main.turn % 2 == 0) {
		            		changedImage = Main.player1Image;
		            		Main.arr[grid.getRowIndex(item)][grid.getColumnIndex(item)] = 0;
		            		grid.add(new ImageView(changedImage), grid.getColumnIndex(item), grid.getRowIndex(item));
		            		
		            		Main.checkWinner();
		            		Main.turn++;
		            		
		            		if (Main.gameType.equals("singlePlayer") && Main.gameOver == false) {
		            		
		            			// try {Thread.sleep(1000); } catch (Exception e) {}
		            			
		   						changedImage = Main.player2Image;
		   						int row, col;
		   						do {
									row = new Random().nextInt(3);
									col = new Random().nextInt(3);
								} while (Main.arr[row][col] != -1);
								
								Main.arr[row][col] = 1;
								
								grid.add(new ImageView(changedImage), col, row);
					
		            			Main.checkWinner();
		            			Main.turn++;
		            		}
		            	} else {
		            		changedImage = Main.player2Image;
		            		Main.arr[grid.getRowIndex(item)][grid.getColumnIndex(item)] = 1;
		            		grid.add(new ImageView(changedImage), grid.getColumnIndex(item), grid.getRowIndex(item));
		            		Main.checkWinner();
		            		Main.turn++;
		            		
		            	}
                    }
                    if (gameOver) {
                		winnerLabel.setText(Main.winner);
                    }
                }
        	});
		}
		
        StackPane root5 = new StackPane();
        root5.getChildren().addAll(gridImageView, grid);
        
        VBox root3 = new VBox(50);
        root3.getChildren().addAll(root5, winnerLabel);
        root3.setAlignment(Pos.CENTER);

		Label heading = new Label();
		heading.setFont(Font.font("Ariel", FontWeight.BOLD, 35));
		
		Label subheading = new Label();
		subheading.setFont(Font.font("Ariel", FontWeight.BOLD, 22));
		
		Button cross1 = new Button("Cross");
		Button circle1 = new Button("Circle");
		
		cross1.setFont(Font.font("Ariel", FontWeight.BOLD, 22));
		circle1.setFont(Font.font("Ariel", FontWeight.BOLD, 22));
		
		cross1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {

				Main.player1Image = Main.crossImage;
				Main.player2Image = Main.circleImage;
				
				primaryStage.setScene(new Scene(root3, 500, 800));
			}
		});
		circle1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
			
				Main.player1Image = Main.circleImage;
				Main.player2Image = Main.crossImage;
				
				primaryStage.setScene(new Scene(root3, 500, 800));
			}
		});
		
		VBox root1 = new VBox(50);
        root1.getChildren().addAll(heading, subheading, cross1, circle1);
        root1.setAlignment(Pos.CENTER);
		
		Label title = new Label("TICTACTOE");
		title.setFont(Font.font("Ariel", FontWeight.BOLD, 50));
		
		Button singlePlayerButton = new Button("Single Player");
		Button multiPlayerButton = new Button("Multi Player");
		
		singlePlayerButton.setFont(Font.font("Ariel", FontWeight.BOLD, 22));
		multiPlayerButton.setFont(Font.font("Ariel", FontWeight.BOLD, 22));
		
		singlePlayerButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				Main.gameType = "singlePlayer";
				heading.setText("Single Player Settings");
				subheading.setText("Choose circle or cross?");
				primaryStage.setScene(new Scene(root1, 500, 800));
			}
		});
		multiPlayerButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				Main.gameType = "multiPlayer";
				heading.setText("Multi Player Settings");
				subheading.setText("Player 1's choice: Circle or Cross?");
				primaryStage.setScene(new Scene(root1, 500, 800));
			}
		});
        
        VBox root0 = new VBox(50);
        root0.getChildren().addAll(title, singlePlayerButton, multiPlayerButton);
        root0.setAlignment(Pos.CENTER);
	
       
        
        primaryStage.setScene(new Scene(root0, 500, 800));
        primaryStage.show();
    }
}

