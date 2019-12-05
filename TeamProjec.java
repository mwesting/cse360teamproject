import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;

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
				
				ArrayList<String> fileList = new ArrayList<String>();
				ArrayList<String> wordsProcessingList;//=new ArrayList<String>();
				while(sc.hasNext()) {
					fileList.add(sc.next());
					
				}//end of while
				
				int fileListIndex=0;
				int placeHolder=0;
				while (fileListIndex<fileList.size()) {
					//System.out.println("fileListIndex: "+ fileListIndex);
						//String line = sc.nextLine();
						String line="";
						
					//System.out.println(line);
					//Flag setting
					if (fileList.get(fileListIndex).charAt(0) == '-') {
						line = fileList.get(fileListIndex).substring(1);
						
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
						wordsProcessingList=new ArrayList<String>();
						placeHolder=fileListIndex-1;
						//read all words from here till the next flag
				
						while (fileListIndex<fileList.size()&&fileList.get(fileListIndex).charAt(0)!='-') {
							if (fileList.get(fileListIndex).charAt(0) != '-') {
								wordsProcessingList.add(fileList.get(fileListIndex));
							}
							fileListIndex++;
						}//end of while
						
						String[] words= new String[80];
						
						
						///////////////////////////////////// Start Flag Logic Here
						if(title) //if -t
						{
							words=textProcessor.ListLengthProcessing(wordsProcessingList, 80);
							fileListIndex=placeHolder;
							for(int i=0;i<words.length-1;i++)
							{
								if(words[i]!=null&& words[i]!=" ")
								{
									fileListIndex++;
								}
							}
							words=textProcessor.titlew(words);
							title=false;
						}else if(nextLine) { //if -e
							
							
							fileListIndex=placeHolder;
							
							words[0]=" ";
							nextLine=false;
						}else if(right) { //if -r
							words=textProcessor.ListLengthProcessing(wordsProcessingList, 80);
							fileListIndex=placeHolder;
							for(int i=0;i<words.length-1;i++)
							{
								if(words[i]!=null&& words[i]!=" ")
								{
									fileListIndex++;
								}
							}
							words=textProcessor.rightw(words);
						}else if(center) { //if -c
							words=textProcessor.ListLengthProcessing(wordsProcessingList, 80);
							fileListIndex=placeHolder;
							for(int i=0;i<words.length-1;i++)
							{
								if(words[i]!=null&& words[i]!=" ")
								{
									fileListIndex++;
								}
							}
							words=textProcessor.centerw(words);
						}else if(left) { //if -l 
							if(!single) { //if -2 two columns
								//basically for two columns I have to run through the entire file with the same outer program but just modified for 35 characters
								
								
								fileListIndex=placeHolder;
								/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
								ArrayList<String[]> chunks=new ArrayList<String[]>(); //create arrayList of String arrays
								int placeHolder2;
								while (fileListIndex<fileList.size()&&!single) {
									
									//Flag setting
									if (fileList.get(fileListIndex).charAt(0) == '-') {
										line="";
										
										line = fileList.get(fileListIndex).substring(1);
										
										switch (line) {
										
										case "1": //single column
											
											single = true;
											break;
										case "e":
											nextLine = true;
											break;
										
										}
										
									//Text processing
									} else {
										wordsProcessingList=new ArrayList<String>();
										placeHolder2=fileListIndex-1;
										
										//read all words from here till the next flag
										while (fileListIndex<fileList.size()&&fileList.get(fileListIndex).charAt(0)!='-') {
											if (fileList.get(fileListIndex).charAt(0) != '-') {
												wordsProcessingList.add(fileList.get(fileListIndex));
											}
											fileListIndex++;
										}//end of while
										
										
										
										///////////// Start inner columns flag Logic Here
										
										if(nextLine) {

											
											fileListIndex=placeHolder2;
											
											words[0]="                                 ";//33 spaces ; because there is another space added after a "word" in my lengthProcessing
											nextLine=false;
										}else if(left) { //if -l 
											
												 //if -l
													words=textProcessor.ListLengthProcessing(wordsProcessingList, 35);
													
													fileListIndex=placeHolder2;
													
													for(int i=0;i<words.length-1;i++)
													{
														if(words[i]!=null&&words[i]!=" ")
														{
															fileListIndex++;
														}
													}
													words=textProcessor.leftw(words);
													
												
											//end of else !single
										}//end of left
										
										chunks.add(words);
									}//end of else
									
									fileListIndex++;
									
								}// END OF WHILE LOOP while (sc.hasNextLine()) 
								
								
								////////////////////////////////////////////////////////////////////////////////////////////////////////////////
								
									
								
								fileListIndex=placeHolder;
								for(int h=0;h<chunks.size();h++)
								{
									for(int i=0;i<chunks.get(h).length;i++)
									{
										if(chunks.get(h)[i]!=null&& chunks.get(h)[i]!=" ")
										{
											fileListIndex++;
										}
									}
								}
								
								
								words=textProcessor.twoColumnsw(chunks);
								single=true;
							}else { //if -1 single column
								if(indent) { //if -i
									words=textProcessor.ListLengthProcessing(wordsProcessingList, 75);
									fileListIndex=placeHolder;
									for(int i=0;i<words.length-1;i++)
									{
										if(words[i]!=null&& words[i]!=" ")
										{
											fileListIndex++;
										}
									}
									words=textProcessor.indentw(words);
									indent=false;
								}else if(block) {//if -b, false if -n
									words=textProcessor.ListLengthProcessing(wordsProcessingList, 70);
									fileListIndex=placeHolder;
									for(int i=0;i<words.length-1;i++)
									{
										if(words[i]!=null&& words[i]!=" ")
										{
											fileListIndex++;
										}
									}
									words=textProcessor.blockw(words);
									 
							
								}else { //if -l
									words=textProcessor.ListLengthProcessing(wordsProcessingList, 80);
									
									fileListIndex=placeHolder;
									
									for(int i=0;i<words.length-1;i++)
									{
										if(words[i]!=null&&words[i]!=" ")
										{
											fileListIndex++;
										}
									}
									words=textProcessor.leftw(words);
									
								}
							}//end of else !single
						}//end of left
						if(!oneLine) {//if -d, false if -s 
							//words[words.length-1]="\n\n"; //add another \n to the end of the line
						}else {
							//words[words.length-1]="\n";//add only one \n to the end of the line
						}
						
						
						
						
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
							
							//if (size > 80) {
							//	System.out.println(i + " 1 " + size);
								//break;
							//}
							previewText.setText(previewText.getText() + words[i]);
							i++;
							
							
						}
						previewText.setText(previewText.getText() + "\n"); //add newline at the end of the line
						if(!oneLine) {//if -d, false if -s 
							previewText.setText(previewText.getText() + "\n");//add an extra newline for double space
					
						}
						
					//	System.out.println(i + " 2 " + words[i-2]);
						
						
						
					}//end of else
					
					
					
					
					
					
					//previewText.setText(previewText.getText() + "\n" + line);
					
					
					fileListIndex++;
					
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