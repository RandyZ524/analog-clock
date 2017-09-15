import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.StrokeLineCap;

import java.util.*;
import java.util.Date;
import java.text.*;
import java.text.SimpleDateFormat;
//Importing my stuff

public class zhanganalogclock extends Application {
 public static void main(String[] args) {
	Application.launch(args); //Launches application
 }

	double hour, minute, second, newsecond, inbetweensecond = 0;
	SimpleDateFormat format = new SimpleDateFormat("HHmmss");
	String currenttime = new String();
	String apm = new String();
	
	public void start(Stage primaryStage) throws Exception {
		int i = 0;
		double radian = 0;
		Line tickmark[] = new Line[60];
		Text clocknumbers[] = new Text[12];
		
		primaryStage.setTitle("My first analog clock");
		
		Circle frame = new Circle(350, 310, 300);
		frame.setStroke(Color.BLACK);
		frame.setFill(Color.WHITE);
		frame.setStrokeWidth(6);
		
		Line hourhand = new Line(350, 310, 350, 140);
		hourhand.setStrokeWidth(4);
		hourhand.setStrokeLineCap(StrokeLineCap.ROUND);
		Line minutehand = new Line(350, 310, 350, 80);
		minutehand.setStrokeWidth(4);
		minutehand.setStrokeLineCap(StrokeLineCap.ROUND);
		Line secondhand = new Line(350, 310, 350, 180);
		secondhand.setStrokeWidth(2);
		secondhand.setStrokeLineCap(StrokeLineCap.ROUND);
		secondhand.setStroke(Color.RED);
		
		Circle center = new Circle(350, 310, 8);
		Text timedisplay = new Text(180, 670, "00:00:00");
		timedisplay.setFont(Font.font("Verdana", 50));
		
		Group root = new Group(frame, center, hourhand, minutehand, secondhand, timedisplay);
		Scene scene = new Scene(root, 700, 700);
		
		for (i = 0; i < 60; i++) {
			radian = Math.toRadians(i * 6);
			
			if (i % 5 == 0) {
				tickmark[i] = new Line(350 + 220 * Math.sin(radian), 310 + 220 * Math.cos(radian), 350 + 180 * Math.sin(radian), 310 + 180 * Math.cos(radian));
				tickmark[i].setStrokeWidth(3);
			} else {
				tickmark[i] = new Line(350 + 220 * Math.sin(radian), 310 + 220 * Math.cos(radian), 350 + 200 * Math.sin(radian), 310 + 200 * Math.cos(radian));
			}
			
			root.getChildren().add(tickmark[i]);
		}
		
		for (i = 12; i > 0; i--) {
			radian = Math.toRadians(i * 30);
			
			clocknumbers[i - 1] = new Text(350 + 260 * Math.sin(radian) - 25, 310 - 260 * Math.cos(radian) + 20, Integer.toString(i));
			clocknumbers[i - 1].setFont(Font.font("Verdana", FontWeight.BOLD, 50));
			root.getChildren().add(clocknumbers[i - 1]);
		}
		
		clocknumbers[9].setLayoutX(clocknumbers[9].getLayoutX() - 10);
		
		AnimationTimer timer = new AnimationTimer() {
            @Override
			
            public void handle(long now) {
				
				currenttime = format.format(Calendar.getInstance().getTime());
				hour = Integer.parseInt(currenttime.substring(0, 2));
				
				if (hour >= 12) {
					apm = " PM";
					
					if (hour > 12) {
						hour = hour - 12;
					}
					
				} else {
					apm = " AM";
				}
				
				minute = Integer.parseInt(currenttime.substring(2, 4));
				newsecond = Integer.parseInt(currenttime.substring(4, 6));
				
				if (newsecond != second) {
					inbetweensecond = 0;
					second = newsecond;
					
					timedisplay.setText((int) hour + ":" + currenttime.substring(2, 4) + ":" + currenttime.substring(4, 6) + apm);
				} else {
					inbetweensecond++;
				}
				
				updateClockHands(hourhand, minutehand, secondhand, hour, minute, second, inbetweensecond);
				center.toFront();
				
			}
			
		};
		timer.start();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	
	private static void updateClockHands(Line hourhand1, Line minutehand1, Line secondhand1, double hour1, double minute1, double second1, double inbetweensecond1) {
		hourhand1.setEndX(350 + 170 * Math.sin(Math.toRadians((hour1 + ((minute1 + (second1 / 60)) / 60)) * 30)));
		hourhand1.setEndY(310 - 170 * Math.cos(Math.toRadians((hour1 + ((minute1 + (second1 / 60)) / 60)) * 30)));
		minutehand1.setEndX(350 + 230 * Math.sin(Math.toRadians((minute1 + ((second1 + (inbetweensecond1 / 60)) / 60)) * 6)));
		minutehand1.setEndY(310 - 230 * Math.cos(Math.toRadians((minute1 + ((second1 + (inbetweensecond1 / 60)) / 60)) * 6)));
		secondhand1.setEndX(350 + 230 * Math.sin(Math.toRadians((second1 + (inbetweensecond1 / 60)) * 6)));
		secondhand1.setEndY(310 - 230 * Math.cos(Math.toRadians((second1 + (inbetweensecond1 / 60)) * 6)));
		
		minutehand1.toFront();
		secondhand1.toFront();
	}
 
}