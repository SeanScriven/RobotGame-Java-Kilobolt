package kiloboltgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;

import kiloboltgame.framework.Animation;

public class StartingClass extends Applet implements Runnable, KeyListener {
	
	enum GameState {
		Running, Dead
	}
	
	GameState state = GameState.Running;

	private static Robot robot;
	public static int score = 0;
    private Font font = new Font(null, Font.BOLD, 30);
	private Image image, currentSprite, character, character2, character3, 
				characterDown, characterJumped, background, heliboy, 
				heliboy2, heliboy3, heliboy4, heliboy5;
	public static Image tilegrassTop, tilegrassBot, tilegrassLeft, 
				tilegrassRight, tiledirt, coin1, coin2, coin3,
				coin4, coin5, coin6;
	private URL base;
	private Graphics second;
	private static Background bg1, bg2;
	private Animation anim, hanim, canim;
	private ArrayList<Tile> tilearray = new ArrayList<Tile>();
	private ArrayList<Coin> coinarray = new ArrayList<Coin>();
	private static ArrayList<Heliboy> heliboyarray = new ArrayList<Heliboy>();
	
	@Override
	public void init() {//runs this method when app runs for first time
		
		// size of most common android resolution
		setSize(800, 480);
		//set background color
		setBackground(Color.BLACK);
		//when game starts, app takes focus and input goes directly in
		setFocusable(true);
		//add the key listener to the app
		addKeyListener(this);
		
		Frame frame = (Frame) this.getParent().getParent();//assigns app window to frame variable
	    frame.setTitle("Q-Bot Alpha");//sets title
	    
	    try {
	    	//define URL base
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Image Setups
		character = getImage(base, "data/character.png");
		character2 = getImage(base, "data/character2.png");
		character3 = getImage(base, "data/character3.png");
		
		characterDown = getImage(base, "data/down.png");
		characterJumped = getImage(base, "data/jumped.png");
		
		heliboy = getImage(base, "data/heliboy.png");
		heliboy2 = getImage(base, "data/heliboy2.png");
		heliboy3 = getImage(base, "data/heliboy3.png");
		heliboy4 = getImage(base, "data/heliboy4.png");
		heliboy5 = getImage(base, "data/heliboy5.png");
		
		coin1 = getImage(base, "data/coin1.png");
		coin2 = getImage(base, "data/coin2.png");
		coin3 = getImage(base, "data/coin3.png");
		coin4 = getImage(base, "data/coin4.png");
		coin5 = getImage(base, "data/coin5.png");
		coin6 = getImage(base, "data/coin6.png");
		
		background = getImage(base, "data/background.png");
		
		tiledirt = getImage(base, "data/tiledirt.png");
		tilegrassTop = getImage(base, "data/tilegrasstop.png");
        tilegrassBot = getImage(base, "data/tilegrassbot.png");
        tilegrassLeft = getImage(base, "data/tilegrassleft.png");
        tilegrassRight = getImage(base, "data/tilegrassright.png");
        
		//create new frames for the anim objects
		anim = new Animation();
		anim.addFrame(character, 1250);
		anim.addFrame(character2, 50);
		anim.addFrame(character3, 50);
		anim.addFrame(character2, 50);
		
		hanim = new Animation();
		hanim.addFrame(heliboy, 100);
		hanim.addFrame(heliboy2, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy5, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy2, 100);
		
		canim = new Animation();
		canim.addFrame(coin1, 100);
		canim.addFrame(coin2, 100);
		canim.addFrame(coin3, 100);
		canim.addFrame(coin4, 100);
		canim.addFrame(coin5, 100);
		canim.addFrame(coin6, 100);
		
		currentSprite = anim.getImage();
	}

	@Override
	public void start() {
		//initialize the background objects
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		//initialize robot object
		robot = new Robot();
		//initialize tile objects
		try {
            loadMap("data/map1.txt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		/*time = new Timer(); // Instantiate Timer Object
		st = new ScheduledTask(); // Instantiate SheduledTask class
		time.schedule(st, 0, 5000);	*/
		
		//running process/thread
		Thread thread = new Thread(this);
	    thread.start();
	}

	private void loadMap(String filename) throws IOException {
		//an ArrayList to be filled with the lines from the .txt file
		 ArrayList<String> lines = new ArrayList<>();
		 //number of lines
	     int width = 0;
	     //number of characters per line
	     int height = 0;
	     
	     BufferedReader reader = new BufferedReader(new FileReader(filename));
	     while (true) {
	    	 String line = reader.readLine();
	         // no more lines to read
	         if (line == null) {
	        	 reader.close();
	             break;
	         }
	         //if comments ignore them
	         if (!line.startsWith("!")) {
	        	 lines.add(line);
	        	 width = Math.max(width, line.length());
	         }
	     }
	     height = lines.size();
	     //create tiles, coins and enemies in map where appropriate
	     for (int j = 0; j < 12; j++) {
	    	 String line = (String) lines.get(j);
	    	 for (int i = 0; i < width; i++) {
	    		 System.out.println(i + "is i ");
	    		 if (i < line.length()) {
	    			 char ch = line.charAt(i);
	    			 int num = Character.getNumericValue(ch);
	    			 if (num == 7){
	    				 Coin c = new Coin(i, j);
	    				 coinarray.add(c);
	    			 }
	    			 else if (num == 8){
	    				 Heliboy h = new Heliboy(i, j);
	    				 heliboyarray.add(h);
	    			 }
	    			 else{
	    				 Tile t = new Tile(i, j, num);
		    			 tilearray.add(t);
	    			 }
	    		 }
	    	 }
	     }
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void run() {
		if (state == GameState.Running) {
			//game loop that checks for changes and makes updates
			while(true){
				//update the robot
				robot.update();
				if (robot.isJumped()){
					currentSprite = characterJumped;
				}else if (robot.isJumped() == false && robot.isDucked() == false){
					//get the image associated with current frame
					currentSprite = anim.getImage();
				}
				/*if (hb.isAlive()){
					   hb.attack();
				   }
				if (hb2.isAlive()){
					   hb2.attack();
				   }*/
				//updates the Projectile objects
				ArrayList<Projectile> projectiles = robot.getProjectiles();
				for (int i = 0; i < projectiles.size(); i++) {
					Projectile p = (Projectile) projectiles.get(i);
					//if projectile p at index i is on screen update it
					if (p.isVisible() == true) {
						p.update();
					} else {
						projectiles.remove(i);
					}
				}
				//update the tiles
				updateTiles();
				//update the Heliboy objects
				updateEnemies();
				//update Coins
				updateCoins();
				//update the background objects
				bg1.update();
				bg2.update();
				animate();
				//repaint the scene
				repaint();
				try {
		            Thread.sleep(17);
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
				//if robot goes below ground
				if (robot.getCenterY() > 500 || robot.getCenterX() < 0) {
					state = GameState.Dead;
				}
			}
		}
	}
	
	/*
	 * update() updates the image smoothly
	 * retains the previous image for short time so moving from one to the other is smooth
	 */
	@Override
	public void update(Graphics g) {
		
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}


		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);


		g.drawImage(image, 0, 0, this);

	}
	
	/*
	 * paint() draws the graphics to the screen
	 */
	@Override
	public void paint(Graphics g) {
		if (state == GameState.Running) {
			g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
			g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
			paintTiles(g);
			
			ArrayList<Projectile> projectiles = robot.getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = (Projectile) projectiles.get(i);
				g.setColor(Color.YELLOW);
				g.fillRect(p.getX(), p.getY(), 10, 5);
			}
			
			g.drawImage(currentSprite, robot.getCenterX() - 61, robot.getCenterY() - 63, this);
			paintEnemies(g);
			paintCoins(g);
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(score), 740, 30);
		}else if (state == GameState.Dead){
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.RED);
			g.drawString("WASTED", 360, 240);
		}
	}
	
	/*
	 * Update each of the tiles
	 */
	private void updateTiles(){
		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			t.update();
		}
	}
	
	/*
	 * Update each of the coins
	 */
	private void updateCoins(){
		for (int i = 0; i < coinarray.size(); i++){
			Coin c = (Coin) coinarray.get(i);
			c.update();
		}
	}
	
	/*
	 * Update each of the enemies
	 */
	private void updateEnemies(){
		for (int i = 0; i < heliboyarray.size(); i++){
			Heliboy h = (Heliboy) heliboyarray.get(i);
			h.update();
		}
	}
	
	/*
	 * paint each of the tiles
	 */
	private void paintTiles(Graphics g) {
		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
		}
	}
	
	/*
	 * paint each of the coins
	 */
	private void paintCoins(Graphics g){
		for (int i = 0; i < coinarray.size(); i++){
			Coin c = (Coin) coinarray.get(i);
			g.drawImage(canim.getImage(), c.getCenterX(), c.getCenterY(), this);
		}
	}
	
	/*
	 * paint each of the enemies
	 */
	private void paintEnemies(Graphics g){
		for (int i = 0; i < heliboyarray.size(); i++){
			Heliboy h = (Heliboy) heliboyarray.get(i);
			g.drawImage(hanim.getImage(), h.getCenterX(), h.getCenterY(), this);
		}
	}

	@Override
	public void keyPressed(KeyEvent event) {
		//carry out an appropriate response depending on which button is pressed
		switch (event.getKeyCode()) {
		   case KeyEvent.VK_UP:
			   System.out.println("Move up");
			   break;

		   case KeyEvent.VK_DOWN:
			   currentSprite = characterDown;
	           if (robot.isJumped() == false){
	                robot.setDucked(true);
	                robot.setSpeedX(0);
	           }
	           break;
	            
		   case KeyEvent.VK_LEFT:
			   robot.moveLeft();
	           robot.setMovingLeft(true);
	           break;

		   case KeyEvent.VK_RIGHT:
			   robot.moveRight();
	           robot.setMovingRight(true);
			   break;

		   case KeyEvent.VK_SPACE:
			   robot.jump();
			   break;
			
		   case KeyEvent.VK_CONTROL:
			   if (robot.isDucked() == false && robot.isJumped() == false) {
					robot.shoot();
					robot.setReadyToFire(false);
				}
				break;
		   }
	}

	@Override
	public void keyReleased(KeyEvent event) {
		//carry out an appropriate response depending on which button is released
		 switch (event.getKeyCode()) {
		   case KeyEvent.VK_UP:
			   System.out.println("Stop moving up");
		       break;

		   case KeyEvent.VK_DOWN:
			   currentSprite = anim.getImage();
	           robot.setDucked(false);
		       break;

		   case KeyEvent.VK_LEFT:
			   robot.stopLeft();
		       break;

		   case KeyEvent.VK_RIGHT:
			   robot.stopRight();
		       break;
		   case KeyEvent.VK_SPACE:
		       break;
		   case KeyEvent.VK_CONTROL:
			   robot.setReadyToFire(true);
			   break;

		   }
		
	}

	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public void animate() {
		   anim.update(10);
		   hanim.update(50);
		   canim.update(30);
		}
	
	public static Background getBg1() {
        return bg1;
    }

    public static Background getBg2() {
        return bg2;
    }
    
    public static Robot getRobot(){
    	return robot;
    }

	public static ArrayList<Heliboy> getHeliboyarray() {
		return heliboyarray;
	}
    
}
