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
import java.util.ArrayList;


class snakeframe extends JFrame implements ActionListener,KeyListener{
    Timer time;
	JButton newgame, highscore, instruct, quit;
	JPanel bar, screen, game;
	JLabel potato;
	Container c;
	ImageIcon opening, farmer, tail, background, overscreen, start, badpotato;
	ArrayList<body>sn=new ArrayList<body>();
	String direction="up";
	String olddirect="";
	int potX, potY, badX, badY, score, headX, headY, numkey;
	boolean restart=false;
	boolean contgame=false;
	boolean ispotato=false;
	boolean over=false;
	
	public snakeframe(){
	    
		time=new Timer(90,this);
		
		screen=new JPanel(){
			public void paintComponent(Graphics g){
				//display opening image
				g.drawImage(opening.getImage(),0,0,600,445,null);
			}
		};
		game=new JPanel(){
			public void paintComponent(Graphics g){
			    super.paintComponent(g);
				//display background during gameplay
				g.drawImage(background.getImage(),0,0,600,445,null);
				//for every component of array, draw image
				for (body p:sn){
					g.drawImage(tail.getImage(),p.x,p.y,20,20,null);
				}
				//display random spawn potatoes 
				g.drawImage(tail.getImage(),potX,potY,20,20,null);
				//display random spawn rotten potatoes
				g.drawImage(badpotato.getImage(),badX,badY,20,20,null);
				//display player (farmer)
				g.drawImage(farmer.getImage(),headX,headY,20,20,null);
				//display game over
				if (over){
					g.drawImage(overscreen.getImage(),0,0,600,445,null);
				}
				
				//need fix*******************************
				if (restart){
					g.drawImage(start.getImage(),0,0,600,445,null);
				}
			}
		};
		game.addKeyListener(this);
		game.setFocusable(true);
		
	//open images
		opening=new ImageIcon("potato.jpg");
		farmer=new ImageIcon("ingamefarmer.gif");
		tail=new ImageIcon("potatotail.gif");
		background=new ImageIcon("gamebackground.jpg");
		overscreen=new ImageIcon("gameover.png");
		start=new ImageIcon("startgame.png");
		badpotato=new ImageIcon("badpotato.gif");
		
	//bottom buttons bar
		bar=new JPanel();
		
		newgame = new JButton("New Game");
		newgame.addActionListener(this);
		newgame.addKeyListener(this);
		
		highscore = new JButton("Highscores");
		highscore.addActionListener(this);
		highscore.addKeyListener(this);
		
		instruct = new JButton("Instructions");
		instruct.addActionListener(this);
		instruct.addKeyListener(this);
		
		quit = new JButton("Quit");
		quit.addActionListener(this);
		quit.addKeyListener(this);
		
		bar.add(newgame);
		bar.add(highscore);
		bar.add(instruct);
		bar.add(quit);
		
	//layout
		c= getContentPane();
		c.setLayout(new BorderLayout());
		c.add(bar,BorderLayout.SOUTH);
		bar.setLayout(new GridLayout(1,3,0,0));
		
		c.add(screen,BorderLayout.CENTER);
	}
	
	//spawn potatoes to collect
	public void growpotato(){
		if (!ispotato){
			//generate good potato
			potX=(int)(Math.random()*30)*20;
			potY=(int)(Math.random()*21)*20;
			//generate rotten potato
			do {
				badX=(int)(Math.random()*30)*20;
				badY=(int)(Math.random()*21)*20;
			}while (badX==potX && badY==potY); //make sure is not in same spot as potato
			ispotato=true;
		}
	}
	
	public void testcollision(){
		//if crash into walls
		if (headX>=600 || headY>=425 || headX<0 || headY<0){
			contgame=false;
			over=true;
			System.out.println("Game Over");
			sn.remove(0);
		}
		//if walk into potato
		else if (headX==potX && headY==potY){
			ispotato=false;
			score+=10;
			System.out.println(score);
		}
		//if walk into bad potato
		else if (headX==badX && headY==badY){
			contgame=false;
			over=true;
			System.out.println("Game Over");
			sn.remove(0);
		}
		//movement (remove end block)
		else{
			sn.remove(0);
		}	
		
		//if crash into tail
		for (int x = sn.size()-2; x >=0 ; x--){
			if (headX==sn.get(x).x && headY==sn.get(x).y){
				contgame=false;
				over=true;
				System.out.println("Game Over");
				sn.remove(0);
				break;
			}
		}
	}
	
	//reset everything for new game
	public void resetall(){
		if (restart){
			headY=220;
			headX=280;
			sn.clear();
			sn.add(new body(280,220));
			direction="up";
			ispotato=false;
			over=false;
			restart=false;
			contgame=false;
			game.repaint();
		}
	}
	
	//movement
	public void move(){
		if (contgame){
			//up
			if (direction.equals("up")){
				sn.add(new body(headX,headY-20));
				headY-=20;
			}
			//down
			if (direction.equals("down")){
				sn.add(new body(headX,headY+20));
				headY+=20;
			}
			//right
			if (direction.equals("right")){
				sn.add(new body(headX+20,headY));
				headX+=20;
			}
			//left
			if (direction.equals("left")){
				sn.add(new body(headX-20, headY));
				headX-=20;
			}
		}
	}
	
	//clicked buttons
	public void actionPerformed(ActionEvent a) {
		//timer actions
	    if (a.getSource()==time){
			if (contgame){
				numkey++;
				move();
				testcollision();
				growpotato();
				game.repaint();
			}
		}
		else{
		//buttons pressed
			JButton b = (JButton)a.getSource();
		
			if (b.getText().equals("New Game")){//new game
				c.remove(screen);//remove title screen
				c.add(game,BorderLayout.CENTER);
				c.validate();
				restart=true;
				resetall();
				contgame=true;
				time.start();
			}
			else if (b.getText().equals("Highscores")){//start moving
				
			}
			else if (b.getText().equals("Instructions")){//bring up instructions box
				JOptionPane.showMessageDialog(null, "Collect the potatoes to grow your tail, \nbut don't crash into walls or your tail, \notherwise, it's game over. \nCareful not to collect the colourful potatoes, \nthey are poisonous!","Instructions", JOptionPane.INFORMATION_MESSAGE);
			}
			else if (b.getText().equals("Quit")){//exit game
				System.exit(0);
			}
		}
	}
	
	//pressed keys
	public void keyPressed(KeyEvent e) {
		while (contgame && numkey>0){
			keyuser(e.getKeyCode());
			numkey=0;
		}
	}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	//gameplay (change direction)
	public void keyuser(int in){
		
	
		//change direction
		olddirect=direction;
		//left
		if(in==37){
			//check if trying to move in direction of tail
			if (!(olddirect.equals("right")||olddirect.equals("left"))){
				direction="left";
			}else{
				numkey=1;
			}
		}
		//right
		else if(in==39){
			if (!(olddirect.equals("left")||olddirect.equals("right"))){
				direction="right";
			}else{
				numkey=1;
			}
		}
		//up
		else if(in==38){
			if (!(olddirect.equals("down")||olddirect.equals("up"))){
				direction="up";
			}else{
				numkey=1;
			}
		}
		//down
		else if(in==40){
			if (!(olddirect.equals("up")||olddirect.equals("down"))){
				direction="down";
			}else{
				numkey=1;
			}
		}
		
	}
}

class body{
	int x;
	int y;
	public body(int x1,int y1){
		x=x1;
		y=y1;
	}
}
	
	


