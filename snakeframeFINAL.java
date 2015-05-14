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
import java.io.*;


class snakeframe extends JFrame implements ActionListener,KeyListener{
    Timer time;
	JButton newgame, start, highscore, instruct, quit, submit;
	JPanel bar, screen, game, entername;
	JLabel yourname;
	JTextField namespace;
	Container c;
	ImageIcon opening, farmer, tail, background, overscreen, badpotato;
	ArrayList<body>sn=new ArrayList<body>();
	ArrayList<badpot>kill=new ArrayList<badpot>();
	String direction="up";
	String olddirect="";
	String filehigh="";
	String name="";
	String scorestring="";
	String user[]=new String[5];
	int highsc[]=new int[5];
	int potX, potY, badX, badY, score, headX, headY, counter, numkey;
	boolean restart=false;
	boolean contgame=false;
	boolean ispotato=false;
	boolean over=false;
	boolean submitscore=false;
	
	//constructor
	public snakeframe(){
	    
		time=new Timer(100,this);
		
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
				for (badpot b:kill){
					g.drawImage(badpotato.getImage(),b.x,b.y,20,20,null);
				}
				
				//display player (farmer)
				g.drawImage(farmer.getImage(),headX,headY,20,20,null);
				//display game over
				if (over){
					g.drawImage(overscreen.getImage(),0,0,600,445,null);
				}
				
				g.drawString(scorestring,520,20);
			}
		};
		game.addKeyListener(this);
		
	//Enter scores panel
		entername=new JPanel();
		entername.setVisible(false);
		
		submit=new JButton("Submit");
		submit.addActionListener(this);
		
		yourname=new JLabel("Enter your name: ");
		
		namespace=new JTextField(15);
		
		entername.add(yourname);
		entername.add(namespace);
		entername.add(submit);
		
	//open images
		opening=new ImageIcon("potato.jpg");
		farmer=new ImageIcon("farmer.png");
		tail=new ImageIcon("potatotail.png");
		background=new ImageIcon("gamebackground.jpg");
		overscreen=new ImageIcon("gameover.png");
		badpotato=new ImageIcon("badpotato.png");
		
	//bottom buttons bar
		bar=new JPanel();
		
		newgame = new JButton("New Game");
		newgame.addActionListener(this);
		newgame.addKeyListener(this);
		
		start = new JButton("Start");
		start.addActionListener(this);
		start.addKeyListener(this);
		
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
		bar.add(start);
		bar.add(highscore);
		bar.add(instruct);
		bar.add(quit);
		
	//layout
		c= getContentPane();
		c.setLayout(new BorderLayout());
		c.add(bar,BorderLayout.SOUTH);
		bar.setLayout(new GridLayout(1,3,0,0));
		
		c.add(entername,BorderLayout.NORTH);
		
		c.add(screen,BorderLayout.CENTER);
	}
	
	//spawn good and bad potato
	public void growpotato(){
		if (!ispotato){
			do {
				//generate good potato
				potX=(int)(Math.random()*30)*20;
				potY=(int)(Math.random()*21)*20;
				//generate rotten potato
				if (score%30==0){
					badX=(int)(Math.random()*28+1)*20;
					badY=(int)(Math.random()*19+1)*20;
				}
			}while (badspot()); //make sure is not in same spot as potato 
			kill.add(new badpot(badX,badY));
		}
		ispotato=true;
	}
	
	public boolean badspot(){ 
		boolean j=false;
		for (badpot b:kill){ //check if new good potatoes are in same place as other bad potatoes
			if (potX==b.x && potY==b.y){
				j=true;
			}
		}
		
		if ((potX==badX) && (potY==badY)){ //check if newly spawn good potato is in same spot as newly spawn bad potato
			j=true;
		}
		
		for (body p:sn){ //check if newly spawed potatoes are in same place as body
			if (potX==p.x && potY==p.y){//good potato
				j=true;
			}
			else if (badX==p.x && badY==p.y){//bad potato
				j=true;
			}
		}
		if (score%30==0){
			if (!(Math.abs(badX-headX)==40 && Math.abs(badY-headY)==40)){ //make sure newly spawn bad potato is not right infront of head
				j=true;
			}
		}
		
		return j;
	}
	
	public void testcollision(){
		//if crash into walls
		if (headX>=600 || headY>=425 || headX<0 || headY<0){
			contgame=false;
			over=true;
			System.out.println("Game Over");
			sn.remove(0);
			entername.setVisible(true);
		}
		//if walk into potato
		else if (headX==potX && headY==potY){
			ispotato=false;
			score+=10;
			scorestring="Score: " + score;
			System.out.println(score);
		}
		//movement (remove end block)
		else{
			if (sn.size()>0){
				sn.remove(0);
			}
		}	
		
		//if crash into tail
		for (int x = sn.size()-2; x >=0 ; x--){
			if (headX==sn.get(x).x && headY==sn.get(x).y){
				contgame=false;
				over=true;
				System.out.println("Game Over");
				entername.setVisible(true);
				break;
			}
		}
		
		//if walk into bad potato
		for (badpot b:kill){
			if (headX==b.x && headY==b.y){
				contgame=false;
				over=true;
				System.out.println("Game Over");
				entername.setVisible(true);
			}
		}
	}
	
	//reset everything for new game
	public void resetall(){
		if (restart){
			headY=220;
			headX=280;
			potY=-20;
			potX=-20;
			score=0;
			scorestring="Score: " + score;
			kill.clear();
			sn.clear();
			direction="up";
			over=false;
			submitscore=false;
			counter=3;
			game.repaint();
			numkey=1;
		}
	}
	
	//movement 
	public void move(){
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

	//clicked buttons
	public void actionPerformed(ActionEvent a) {
		//timer actions
	    if (a.getSource()==time){
			if (contgame){
				move();
				testcollision();
				growpotato();
				game.repaint();
				numkey++;
			}
		}
		else{
		//buttons pressed
			JButton b = (JButton)a.getSource();
		
			if (b.getText().equals("New Game")){//new game (refresh game)
				c.remove(screen);//remove title screen
				c.add(game,BorderLayout.CENTER);
				c.validate();
				restart=true;
				resetall();
			}
			else if (b.getText().equals("Start")){//start moving
				time.start();
				if (restart){
					sn.add(new body(280,220));
					ispotato=false;
					contgame=true;
					restart=false;
				}
			}
			else if (b.getText().equals("Highscores")){
				try{
					JOptionPane.showMessageDialog(null, readhigh(),"Highscores", JOptionPane.PLAIN_MESSAGE);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			else if (b.getText().equals("Instructions")){//bring up instructions box
				JOptionPane.showMessageDialog(null, "Hello! Welcome to Potato Hoarder! \nCollect the potatoes to grow your tail, \nthe more you collect, the higher your score. \nHowever, make sure you don't: \n1.Crash into the walls \n2.Crash into your tail\n3.Collect a bad potato(They're purple!) \nHave fun! :D","Instructions", JOptionPane.INFORMATION_MESSAGE);
			}
			else if (b.getText().equals("Quit")){//exit game
				System.exit(0);
			}
			else if (b.getText().equals("Submit")){
				name=namespace.getText();
				if (name.length()>15){
					name=name.substring(0,15);
				}
				entername.setVisible(false);
				try{
					writehigh();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	//pressed keys
	public void keyPressed(KeyEvent e) {
		//to make sure only one key is registered/taken
		while (numkey>0){
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
			}
		}
		//right
		else if(in==39){
			if (!(olddirect.equals("left")||olddirect.equals("right"))){
				direction="right";
			}
		}
		//up
		else if(in==38){
			if (!(olddirect.equals("down")||olddirect.equals("up"))){
				direction="up";
			}
		}
		//down
		else if(in==40){
			if (!(olddirect.equals("up")||olddirect.equals("down"))){
				direction="down";
			}
		}	
	}
	
	public String readhigh() throws Exception{
		BufferedReader reader = new BufferedReader(new FileReader("highscore.txt"));
		
		//read high scores text file
		filehigh="";
		for (int i = 0; i < 5; i++){
			
			filehigh += reader.readLine() + "\n";
		}
		
		reader.close();
		return filehigh;
	}
	
	public void writehigh() throws Exception{
		filehigh="";
		
		//set temporary variables for sorting purposes
		int temp=0;
		String tempn="";
		
		BufferedReader reader = new BufferedReader(new FileReader("highscore.txt"));
		
		//get values for text file of highscores
		for (int i=0;i<=4;i++){
			filehigh=reader.readLine();	
			if (filehigh!=null){
				user[i]=filehigh.substring(0,filehigh.indexOf("-"));
				tempn=filehigh.substring(filehigh.indexOf("-")+1,filehigh.indexOf("0")+1);
				highsc[i]=Integer.parseInt(tempn);	
			}else{
				user[i]="computer";
				highsc[i]=0;
			}
		}
		
		//clear variables
		tempn="";
		filehigh="";
		
		//check to see if new score is a high score
		for (int i=4;i>=0;i--){
			if (score>highsc[i]){
				temp=highsc[i];
				tempn=user[i];
				highsc[i]=score;
				user[i]=name;
				if (!(i==4)){
					highsc[i+1]=temp;
					user[i+1]=tempn;
				}
			}
		}
		
		for (int i=0;i<=4;i++){
			filehigh+=user[i]+"-"+highsc[i]+"\n";
		}
		
		//write new high scores to text file
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("highscore.txt")));
		writer.println(filehigh);
		writer.close();
		reader.close();
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

class badpot{
	int x;
	int y;
	public badpot(int x1,int y1){
		x=x1;
		y=y1;
	}
}
	
	


