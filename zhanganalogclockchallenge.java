import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

import java.util.Random;
import java.util.TimeZone;
import java.util.Calendar;
//Importing my stuff

public class zhanganalogclockchallenge extends Application {
 public static void main(String[] args) {
	Application.launch(args); //Launches application
 }

	static int numofselectedtz, i, j, k = 0;
	static int numoftimezones;
	static double radian, hour, newhour, minute, second, newsecond, inbetweensecond = 0;;
	static boolean listenergo = true;
	static String timezoneinput, apm, minuterep, secondrep = new String();
	Random random = new Random();
	Clock[] gridclocks = new Clock[10];
	TimeZone tz;
	Calendar cal;
 
	public class Clock {
		int xpos, ypos;
		String timezone;
		Line hourhand, minutehand, secondhand;
		Line[] tickmark;
		Circle center, frame;
		Text timedisplay, timezonedisplay;
		Rectangle backgroundrect;
		
		Clock(int xpos, int ypos, String timezone, Line hourhand, Line minutehand, Line secondhand, Line[] tickmark, Circle center, Circle frame, Text timedisplay, Text timezonedisplay, Rectangle backgroundrect) {
			this.xpos = xpos;
			this.ypos = ypos;
			this.timezone = timezone;
			this.hourhand = hourhand;
			this.minutehand = minutehand;
			this.secondhand = secondhand;
			this.tickmark = tickmark;
			this.center = center;
			this.frame = frame;
			this.timedisplay = timedisplay;
			this.timezonedisplay = timezonedisplay;
			this.backgroundrect = backgroundrect;
		}
		
		public void create() {
			backgroundrect.setX(xpos - 70);
			backgroundrect.setY(ypos - 65);
			backgroundrect.setWidth(350);
			backgroundrect.setHeight(120);
			
			frame.setCenterX(xpos);
			frame.setCenterY(ypos);
			frame.setRadius(50);
			frame.setStroke(Color.BLACK);
			frame.setFill(Color.WHITE);
			frame.setStrokeWidth(2);
			
			hourhand.setStartX(xpos);
			hourhand.setStartY(ypos);
			hourhand.setEndX(xpos);
			hourhand.setEndY(ypos);
			
			minutehand.setStartX(xpos);
			minutehand.setStartY(ypos);
			minutehand.setEndX(xpos);
			minutehand.setEndY(ypos);
			
			secondhand.setStartX(xpos);
			secondhand.setStartY(ypos);
			secondhand.setEndX(xpos);
			secondhand.setEndY(ypos);
			secondhand.setStroke(Color.RED);
			
			for (k = 0; k < 60; k++) {
				radian = Math.toRadians(k * 6);
				
				if (k % 5 == 0) {
					//Big tick marks, their end points are located along a smaller circle
					tickmark[k] = new Line(xpos + 50 * Math.sin(radian), ypos + 50 * Math.cos(radian), xpos + 42 * Math.sin(radian), ypos + 42 * Math.cos(radian));
					tickmark[k].setStrokeWidth(2);
				} else {
					//Small tick marks
					tickmark[k] = new Line(xpos + 50 * Math.sin(radian), ypos + 50 * Math.cos(radian), xpos + 45 * Math.sin(radian), ypos + 45 * Math.cos(radian));
				}
				
			}
			
			center.setCenterX(xpos);
			center.setCenterY(ypos);
			center.setRadius(3);
			
			timedisplay.setText("00:00:00");
			timedisplay.setLayoutX(xpos + 60);
			timedisplay.setLayoutY(ypos + 20);
			timedisplay.setFont(Font.font("Verdana", 12));
			
			timezonedisplay.setText(timezone.replace("_", " "));
			timezonedisplay.setLayoutX(xpos + 60);
			timezonedisplay.setLayoutY(ypos - 10);
			timezonedisplay.setFont(Font.font("Verdana", 12));
		}
		
		public void update(double localhour, double localminute, double localsecond, double localinbetweensecond, double truehour) {
			
			if (truehour >= 12) {
				frame.setFill(Color.BLACK);
				hourhand.setStroke(Color.WHITE);
				minutehand.setStroke(Color.WHITE);
				center.setStroke(Color.WHITE);
				center.setFill(Color.WHITE);
				
				for (k = 0; k < 60; k++) {
					tickmark[k].setStroke(Color.WHITE);
				}
				
			} else {
				frame.setFill(Color.WHITE);
				hourhand.setStroke(Color.BLACK);
				minutehand.setStroke(Color.BLACK);
				center.setStroke(Color.BLACK);
				center.setFill(Color.BLACK);
				
				for (k = 0; k < 60; k++) {
					tickmark[k].setStroke(Color.BLACK);
				}
				
			}
			
			hourhand.setEndX(xpos + 30 * Math.sin(Math.toRadians((localhour + ((localminute + (localsecond / 60)) / 60)) * 30)));
			hourhand.setEndY(ypos - 30 * Math.cos(Math.toRadians((localhour + ((localminute + (localsecond / 60)) / 60)) * 30)));
			minutehand.setEndX(xpos + 45 * Math.sin(Math.toRadians((localminute + ((localsecond + (localinbetweensecond / 60)) / 60)) * 6)));
			minutehand.setEndY(ypos - 45 * Math.cos(Math.toRadians((localminute + ((localsecond + (localinbetweensecond / 60)) / 60)) * 6)));
			secondhand.setEndX(xpos + 45 * Math.sin(Math.toRadians((localsecond + (localinbetweensecond / 60)) * 6)));
			secondhand.setEndY(ypos - 45 * Math.cos(Math.toRadians((localsecond + (localinbetweensecond / 60)) * 6)));
			//Moves clock hands around, each one has "smooth" movement
			
			minutehand.toFront();
			secondhand.toFront();
			center.toFront();
		}
		
	}
	
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("My first analog clock: the sequel");
		
		Group rootNode = new Group();
		Scene scene = new Scene(rootNode, 700, 600);
		
		Button toInterface = new Button();
		toInterface.setLayoutX(10);
		toInterface.setLayoutY(535);
		toInterface.setText("Enter!");
		Button randomize = new Button();
		randomize.setLayoutX(10);
		randomize.setLayoutY(565);
		randomize.setText("Randomize all!");
		rootNode.getChildren().addAll(toInterface, randomize);
		
		String[] alltimezones = TimeZone.getAvailableIDs();
		numoftimezones = alltimezones.length;
		String[] possibletimezones = new String[numoftimezones];
		TextField[] tzinputboxes = new TextField[10];
		ComboBox[] timezoneselections = new ComboBox[10];
		
		for (i = 0; i < 10; i++) {
			TextField tzinputbox = new TextField();
			ComboBox timezoneselection = new ComboBox();
			tzinputbox.setLayoutX(350 * (i % 2) + 10);
			tzinputbox.setLayoutY(110 * (i / 2));
			timezoneselection.setLayoutX(350 * (i % 2) + 10);
			timezoneselection.setLayoutY(110 * (i / 2) + 30);
			//Sets the position of the text boxes and the dropdown boxes from right to left, then top to bottom 2 x 5
			rootNode.getChildren().addAll(tzinputbox, timezoneselection);
			final int index = i;
			
			tzinputbox.textProperty().addListener((obs, trash, timezoneinput) -> {
				//Listener that detects if the textfield is changed
				
				if (listenergo) {
					//These conditional and boolean manipulation statements (identical below) ensure that neither listener activates because of another one's actions
					listenergo = false;
					
					numofselectedtz = 0;
					timezoneselection.getItems().clear();
					//Clears dropdown menu, getting ready to add search results
					
					for (j = 0; j < numoftimezones; j++) {
						
						if (alltimezones[j].toLowerCase().replace("_", " ").contains(timezoneinput.toLowerCase())) {
							//Checks if the input in the input box matches elements of the array of time zones, then adds the valid ones to an array of "possible time zones"
							possibletimezones[numofselectedtz] = alltimezones[j];
							numofselectedtz++;
						}
						
					}
					
					for (j = 0; j < numofselectedtz; j++) {
						//Moves the possible time zones into the dropdown menu
						timezoneselection.getItems().add(possibletimezones[j]);
					}
					
					listenergo = true;
					//Releases the bind on the other listener, allowing it to activate
				}
				
			});
			
			tzinputboxes[i] = tzinputbox;
			timezoneselections[i] = timezoneselection;
			//Adds the individual text boxes and dropdown menus into their respective Node arrays
			
			timezoneselection.valueProperty().addListener(new ChangeListener<String>() {
				@Override
				
				public void changed(ObservableValue ov, String oldselection, String newselection) {
					
					if (listenergo) {
						listenergo = false;
						tzinputboxes[index].setText(newselection);
						//This updates the text box to the timezone that is selected in the dropdown menu
						listenergo = true;
					}
					
				}
				
			});
			
		}
		
		//"Randomize!" button press action
		randomize.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				
				for (i = 0; i < 10; i++) {
					timezoneselections[i].getItems().clear();
					//Clears every dropdown menu, getting ready to add something in
					tzinputboxes[i].setText(alltimezones[random.nextInt(numoftimezones)]);
					//The action of updating the input boxes also triggers the first listener, which puts the appropriate timezone into the dropdown menu as well
				}
				
			}
			
		});
		
		//"Enter!" button press action
		toInterface.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				rootNode.getChildren().clear();
				
				for (i = 0; i < 10; i++) {
					gridclocks[i] = new Clock(350 * (i % 2) + 70, 120 * (i / 2) + 65, tzinputboxes[i].getText(), new Line(), new Line(), new Line(), new Line[60], new Circle(), new Circle(), new Text(), new Text(), new Rectangle());
					//In the same manner as the input boxes and the dropdown menus, this arranges the clock objects L-R, T-B, 2 x 5
					gridclocks[i].create();
					rootNode.getChildren().addAll(gridclocks[i].backgroundrect, gridclocks[i].frame, gridclocks[i].hourhand, gridclocks[i].minutehand, gridclocks[i].secondhand, gridclocks[i].center, gridclocks[i].timedisplay, gridclocks[i].timezonedisplay);
					
					for (j = 0; j < 60; j++) {
						rootNode.getChildren().add(gridclocks[i].tickmark[j]);
					}
					
				}
				
				//This animation timer holds the key to updating the clocks, 60 times a second
				AnimationTimer clocktimer = new AnimationTimer() {
					@Override
					
					public void handle(long now) {
						
						for (i = 0; i < 10; i++) {
							tz = TimeZone.getTimeZone(gridclocks[i].timezone);
							cal = Calendar.getInstance(tz);
							hour = cal.get(Calendar.HOUR_OF_DAY);
							//Uses the object's timezone and nabs a Calendar object with all the time we'll ever need
							
							if (hour >= 12) {
								apm = " PM";
								
								if (hour > 12) {
									hour -= 12;
								}
								
							} else {
								apm = " AM";
								
								if (hour == 0) {
									hour += 12;
								}
								
							}
							//This logic essentially converts 24 hour into 12 hour. I used 24 hour because then you can also get "AM" and "PM"
							
							minute = cal.get(Calendar.MINUTE);
							newsecond = cal.get(Calendar.SECOND);
							
							if (newsecond != second) {
								inbetweensecond = 0;
								second = newsecond;
							} else if (i == 0) {
								System.out.println(inbetweensecond);
								inbetweensecond++;
							}
							//inbetweensecond always exists between 0 and 60. It's reset to 0 every time the second value changes. Once it's divided by 60, it represents a fraction of a second, which paves the way to smooth ticking!
							
							minuterep = minute < 10 ? "0" + String.valueOf((int) minute) : String.valueOf((int) minute);
							secondrep = second < 10 ? "0" + String.valueOf((int) second) : String.valueOf((int) second);
							//Sameer taught me how to use these question mark things
							
							gridclocks[i].update(hour, minute, second, inbetweensecond, cal.get(Calendar.HOUR_OF_DAY));
							gridclocks[i].timedisplay.setText((int) hour + ":" + minuterep + ":" + secondrep + apm);
						}
						
					}
					
				};
				clocktimer.start();
				
			}
			
		});
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}