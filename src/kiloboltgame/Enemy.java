package kiloboltgame;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Calendar;

public class Enemy {

	private int power, speedX, centerX, centerY;
	//create reference to bg1 object in StartingClass so when that moves the enemy moves with it
	private Background bg = StartingClass.getBg1();
	public Rectangle r = new Rectangle(0,0,0,0);
	private Robot robot = StartingClass.getRobot();
	public int health = 5;
	private int movementSpeed;
	private long currentTime;
	protected long lastAttack;
	protected long duration;
	protected boolean alive = false;
	private Calendar cal;
	//arrayList of bullets
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	
	public void update() {
		follow();
		centerX += speedX;
		//speed is five times that of background
		speedX = bg.getSpeedX() * 5 + movementSpeed;
		//set the bounds of the rectangle for collisions
		r.setBounds(centerX + 20, centerY + 20, 50, 60);
		if (r.intersects(Robot.yellowRed)){
			checkCollision();
		}
	}

	/*
	 * Checks to see if the enemy character collides with robot character
	 */
	private void checkCollision() {
		if (r.intersects(Robot.rect) || r.intersects(Robot.rect2) || r.intersects(Robot.rect3) || r.intersects(Robot.rect4)){
			if (robot.health > 0){
				robot.health -= 1;
			}
			else if (robot.health == 0){
				robot.setCenterX(-100);
			}
		}
	}
	/*
	 * Move the enemy according to position of robot
	 */
	public void follow() {
		if (centerX < -95 || centerX > 810){
			movementSpeed = 0;
		}
		else if (Math.abs(robot.getCenterX() - centerX) < 5) {
			movementSpeed = 0;
		}
		else {
			if (robot.getCenterX() >= centerX) {
				movementSpeed = 1;
			} else {
				movementSpeed = -1;
			}
		}
	}
	
	public void die() {
		
	}
	
	public void attack() {
		/*
		cal = Calendar.getInstance();
		currentTime = cal.getTimeInMillis();
		
		if( currentTime - lastAttack >= duration){
			lastAttack = currentTime;
			Projectile p = new Projectile(centerX - 50, centerY);
			projectiles.add(p);
			System.out.println("attack");
		}
		*/
	}

	public int getPower() {
		return power;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public Background getBg() {
		return bg;
	}

	public ArrayList<Projectile> getProjectiles(){
		return projectiles;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setPower(int power) {
		this.power = power;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setBg(Background bg) {
		this.bg = bg;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

		
}
