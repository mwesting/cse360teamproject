import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TeamProjec extends Application {
	
	Label errorLabel = new Label();
	Label prevLabel = new Label("Preview:");
	
	
	Text previewText = new Text();
	Text errorText = new Text();
	
	ScrollPane previewScroll = new ScrollPane(previewText);
	ScrollPane errorScroll = new ScrollPane(errorText);
	
	File textFile;
	
	boolean isFile = false;
	
	boolean left = true;
	boolean right = false;
	boolean center = false;
	
	boolean title = false;
	
	boolean oneLine = true;
	
	boolean single = true;
	
	boolean indent = false;
	boolean block = false;
	
	boolean nextLine = false;
	
	public void start(Stage primaryStage) {
		
		prevLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));	
		prevLabel.setPadding(new Insets(0, 0, 10, 0));
		
		errorLabel.setTextFill(Color.DARKRED);
		errorLabel.setFont(new Font("Arial", 16));
		errorLabel.setMinSize(50, 50);
		
		
		Button openFile = new Button("Open File");
		Button saveFile = new Button("Save File");
		Button errorLog = new Button("Error Log");
		
		openFile.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				textFile = fileChooser.showOpenDialog(primaryStage);
				

				
				if (textFile == null) {
					setErrorText("No file selected.");
					isFile = false;
					previewText.setText("");
					return;
				}
				
				else if (!textFile.getName().contains(".txt")) {
					setErrorText("Incorrect file type.");
					isFile = false;
					previewText.setText("");
					return;
				}
				
				else if (isFile) {
					previewText.setText("");
				}
				
				isFile = true;
				Scanner sc;
						
				try {
					 sc = new Scanner(textFile);
				} catch (FileNotFoundException e) {
					setErrorText("Invalid file.");
					return;
				}
				
				
				
				//Read in code
				Class1 textProcessor= new Class1();
				
				//DEFAULTS
				left=true;
				single=true;
				oneLine=true;
				
				
				
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					System.out.println(line);
					//Flag setting
					if (line.charAt(0) == '-') {
						line = line.substring(1);
						
						switch (line) {
						case "l":
							left = true;
							right = false;
							center = false;
							break;
							
						case "r":
							left = false;
							right = true;
							center = false;
							break;
						
						case "c":
							left = false;
							right = false;
							center = true;
							break;
							
						case "t":
							title = true;
							break;
						
						case "1": //single column
							single = true;
							break;
						
						case "2": //two columns
							single = false;
							break;
							
						case "s":
							oneLine = true;
							break;
						
						case "d":
							oneLine = false;
							break;
						
						case "b":
							block = true;
							break;							
						
						case "n":
							block = false;
							break;
						
						case "e":
							nextLine = true;
							break;
							
						
						case "i":
							indent = true;
							break;
						
						}
					
						
					//Text processing
					} else {
						
						String[] words= new String[80];
						if(title) //if -t
						{
							words=textProcessor.title(line);
							title=false;
						}else if(nextLine) { //if -e
							words[0]=" ";
							nextLine=false;
						}else if(right) { //if -r
							words=textProcessor.right(line);
						}else if(center) { //if -c
							words=textProcessor.center(line);
						}else if(left) { //if -l 
							if(!single) { //if -2 two columns
								words=textProcessor.twoColumns(line, sc);
							}else { //if -1 single column
								if(indent) { //if -i
									words=textProcessor.indent(line);
									indent=false;
								}else if(block) {//if -b, false if -n
									words=textProcessor.block(line);
									 
							
								}else {
									words=textProcessor.left(line);
								}
							}//end of else !single
						}//end of left
						if(!oneLine) {//if -d, false if -s 
							//words[words.length-1]="\n\n"; //add another \n to the end of the line
						}else {
							//words[words.length-1]="\n";//add only one \n to the end of the line
						}
						
						
						//Length processing
						/*
						if (line.length() > 80) {
							//String[] words = line.split(" ", -1);
							
							int i = 0;
							int size = -1;
							while (i < words.length) {
								//size += words[i].length() + 1;
								size+=words[i].length();
								
								if (size > 80) {
									System.out.println(i + " " + size);
									break;
								}
								i++;
							}
							
							System.out.println(i + " " + words[i]);
							
							for (int j = 0; j < i; j++) {
								previewText.setText(previewText.getText() + words[j] + " ");
							}
							
							previewText.setText(previewText.getText() + "\n");
							
							for (int j = i; j < words.length; j++) {
								previewText.setText(previewText.getText() + words[j] + " ");
							}
							
							previewText.setText(previewText.getText() + "\n");
							
						} else {
							previewText.setText(previewText.getText() + line + "\n");
						}
						*/
						int i = 0;
						int size = 0;
						int b=0;
						int wordsLength=0;
						while (b < words.length) { //count the elements in words
							if(words[b]!=null)
							{
								wordsLength++;
							}
							b++;
						}
						while (i < wordsLength) {
							//size += words[i].length() + 1;
							if(words[i]!=null)
							{
								size+=words[i].length();
							}
							
							if (size > 80) {
								System.out.println(i + " " + size);
								break;
							}
							previewText.setText(previewText.getText() + words[i]);
							i++;
							
							
						}
						previewText.setText(previewText.getText() + "\n"); //add newline at the end of the line
						if(!oneLine) {//if -d, false if -s 
							previewText.setText(previewText.getText() + "\n");//add an extra newline for double space
					
						}
						
						System.out.println(i + " " + words[i]);
						
						
						
					}
					
					
					
					
					
					
					//previewText.setText(previewText.getText() + "\n" + line);
					
					
					
				}// END OF WHILE LOOP while (sc.hasNextLine()) 
				
				
				
				
				
				
				sc.close();
			}	
		});
		
		saveFile.setOnAction(new SaveFileHandler());
		

		
		openFile.setMinSize(80, 30);
		saveFile.setMinSize(80, 30);
		errorLog.setMinSize(80, 30);
		
		HBox hBox = new HBox();
		hBox.getChildren().addAll(openFile, saveFile, errorLog);
		hBox.setAlignment(Pos.CENTER);
		hBox.setSpacing(80);
		hBox.setPadding(new Insets(10, 10, 0, 0));
		
		previewScroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		previewScroll.setMinSize(300, 260);
		
		errorScroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		errorScroll.setMinSize(300, 260);
		
		VBox vBox = new VBox();
		vBox.getChildren().add(hBox);
		vBox.getChildren().add(errorLabel);
		vBox.getChildren().add(prevLabel);
		vBox.getChildren().add(previewScroll);
		
		errorLog.setOnAction(new EventHandler<ActionEvent>() {
			
			boolean error = false;
			
			public void handle(ActionEvent event) {
				if (error) {
					vBox.getChildren().remove(errorScroll);
					vBox.getChildren().add(previewScroll);
					prevLabel.setText("Preview:");
					errorLog.setText("Error Log");
					error = false;
				} else {
					vBox.getChildren().remove(previewScroll);
					vBox.getChildren().add(errorScroll);
					prevLabel.setText("Error Log:");
					errorLog.setText("Preview");
					error = true;
				}
			}
		});

		
		Scene scene = new Scene(vBox, 600, 400);
		primaryStage.setTitle("Text File Formatter");
		primaryStage.setScene(scene); 
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public void setErrorText(String error) {
		errorLabel.setTextFill(Color.DARKRED);
		errorLabel.setText("ERROR! Check log for details...");
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				errorLabel.setText("");		
			}
		}));
		
		timeline.setCycleCount(1);
		timeline.play();
		
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
		 LocalDateTime now = LocalDateTime.now();  
		 errorText.setText(errorText.getText() + "\n" + "[" + dtf.format(now) + " ERROR]:" + " " + error);
		
	}
	
	private class SaveFileHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if (!isFile)
				setErrorText("No file loaded. Unable to save!");
			else {
				errorLabel.setTextFill(Color.BLUE);
				errorLabel.setText("File saved!");
			}
		}
		
	}
		
}