/*
Snake.java
Amy Li
Potato Hoarder
ICS Summative Project
ICS3U1
This program will output a snake game style game called Potato Hoarder where the player is a farmer collecting potatoes.
Started on: May 9, 2012
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


class snakeframe extends JFrame implements ActionListener,KeyListener{
    Timer time;
	JButton play, instruct, quit;
	JPanel bar, screen, game;
	JLabel potato;
	Container c;
	ImageIcon opening, farmer, tail, background;
	int potX, potY;
	boolean contgame=false;
	
	public snakeframe(){
	    
		time=new Timer(250,this);
		time.start();
		screen=new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(opening.getImage(),0,0,600,480,null);
			}
		};
		game=new JPanel(){
			public void paintComponent(Graphics g){
			    super.paintComponent(g);
				g.drawImage(background.getImage(),0,0,600,480,null);
				g.setColor(new Color(150,100,50));
				g.fillRect(potX,40,10,10);
				g.setColor(Color.black);
				g.drawRect(potX,40,10,10);
				
			}
		};
		game.addKeyListener(this);
	//opening image
		opening=new ImageIcon("potato.jpg");
		farmer=new ImageIcon("ingamefarmer.gif");
		tail=new ImageIcon("potatotail.gif");
		background=new ImageIcon("gamebackground.jpg");
		
	//bottom buttons bar
		bar=new JPanel();
		
		play = new JButton("New Game");
		play.addActionListener(this);
		play.addKeyListener(this);
		
		instruct = new JButton("Instructions");
		instruct.addActionListener(this);
		instruct.addKeyListener(this);
		
		quit = new JButton("Quit");
		quit.addActionListener(this);
		quit.addKeyListener(this);
		
		bar.add(play);
		bar.add(instruct);
		bar.add(quit);
		
	//layout
		c= getContentPane();
		c.setLayout(new BorderLayout());
		c.add(bar,BorderLayout.SOUTH);
		bar.setLayout(new GridLayout(1,3,0,0));
		
		c.add(screen,BorderLayout.CENTER);
	}
	
	//public void growpotato(Graphics g){
	//spawn potatoes
		//if (contgame=true){
		//	potX=(int)(Math.random()*50);
		//	potY=(int)(Math.random()*50);
		//	g.drawImage(tail.getImage(),potX,potY,20,20,null);
			
		//}
	//}
	
	//clicked buttons
	public void actionPerformed(ActionEvent a) {
	    if (a.getSource()==time){
		    potX++;
			game.repaint();
		
		}
		else{
			JButton b = (JButton)a.getSource();
		
			if (b.getText().equals("New Game")){
				System.out.println("New Game button has been pressed lalal");
				c.remove(screen);
				c.add(game,BorderLayout.CENTER);
				c.validate();
				contgame=true;
				
			}
			else if (b.getText().equals("Instructions")){
				JOptionPane.showMessageDialog(null, "Collect the potatoes to grow your tail, \nbut don't crash into walls or your tail! \nOtherwise, it's game over. D:\n(Don't eat your potatoes!)","Instructions", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("Instructions button has been pressed");
			}
			else if (b.getText().equals("Quit")){
				System.exit(0);
				System.out.println("Quit button has been pressed");
			}
		}
	}
	
	//pressed keys
	public void keyPressed(KeyEvent e) {
		move(e.getKeyCode());
		System.out.println("abc");
	}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	//gameplay
	public void move(int in){
		//left
		if(in==37){
			
		}
		//right
		else if(in==39){
			
		}
		//up
		else if(in==38){
			
		}
		//down
		else if(in==40){
			
		}
	}
}


	
	


