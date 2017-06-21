package kiloboltgame;

import java.awt.Rectangle;

public class Coin {

	private int centerX, centerY, speedX;
	private Background bg = StartingClass.getBg1();
	public Rectangle r = new Rectangle(0,0,0,0);
	private Robot robot = StartingClass.getRobot();
	
	public Coin(int centerX, int centerY) {
		this.centerX = centerX * 40;
		this.centerY = centerY * 40;
	}

	public void update() {
		speedX = bg.getSpeedX() * 5;
		centerX += speedX;
		r.setBounds(centerX+5, centerY+5, 30, 30);
		if (r.intersects(Robot.yellowRed)){
			checkCollision();
		}
	}
	
	/*
	 * Checks to see if the coin collides with robot character
	 */
	private void checkCollision() {
		if (r.intersects(Robot.rect) || r.intersects(Robot.rect2) || r.intersects(Robot.rect3) || r.intersects(Robot.rect4)){
			StartingClass.score += 5;
			setCenterX(-100);
		}
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public Background getBg() {
		return bg;
	}

	public void setBg(Background bg) {
		this.bg = bg;
	}

}
