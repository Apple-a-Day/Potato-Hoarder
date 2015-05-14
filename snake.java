/*
Snake.java
Amy Li
Potato Hoarder
ICS Summative Project
ICS3U1
This program will output a snake game style game called Potato Hoarder where the player is a farmer collecting potatoes.
Started on: May 9, 2012
*/

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

class snake{
	
	public static void main(String []args){
		
		snakeframe ss = new snakeframe();
		ss.setTitle("Potato Hoarder");
		ss.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ss.setSize(605, 500);
		ss.setVisible(true);
		ss.setResizable(false);
		
	}
}

