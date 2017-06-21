package kiloboltgame;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Projectile {

	private int x, y, speedX;
	private boolean visible;
	private Rectangle r;
	private boolean isRobot;
	private static ArrayList<Heliboy> heliboyarray;
	
	public Projectile(int startX, int startY) {
		x = startX;
		y = startY;
		//isRobot = robot;
		speedX = 7;
		visible = true;
		r = new Rectangle(0, 0, 0, 0);
	}
	
	public void update(){
		/*if (isRobot){
			x += speedX;
		}
		else{
			x-= speedX;
		}*/
		x += speedX;
		r.setBounds(x, y, 10, 5);
		if (x > 800) {
		   visible = false;
		   r = null;
		}
		if(x < 800){
			checkCollision();
		}
	}

	/*
	 * If enenmy's health is greater than 0 when hit take 1
	 * If enenmy's = 0 then remove from screen
	 */
	private void checkCollision() {
		heliboyarray = StartingClass.getHeliboyarray();
		for (int i = 0; i < heliboyarray.size(); i++){
			Heliboy h = (Heliboy) heliboyarray.get(i);
			if(r.intersects(h.r)){
				visible = false;
				if(h.health > 0){
					h.health -= 1;
				}
				else if (h.health == 0){
					h.setCenterX(-100);
					h.setAlive(false);
					StartingClass.score += 5;
				}
			}
		}
		/*if(r.intersects(StartingClass.hb.r)){
			visible = false;
			if (StartingClass.hb.health > 0) {
				StartingClass.hb.health -= 1;
			}
			if (StartingClass.hb.health == 0) {
				StartingClass.hb.setCenterX(-100);
				StartingClass.hb.setAlive(false);
				StartingClass.score += 5;
			}
		}
		if (r.intersects(StartingClass.hb2.r)){
			visible = false;
			if (StartingClass.hb2.health > 0) {
				StartingClass.hb2.health -= 1;
			}
			if (StartingClass.hb2.health == 0) {
				StartingClass.hb2.setCenterX(-100);
				StartingClass.hb2.setAlive(false);
				StartingClass.score += 5;
			}
		}*/
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSpeedX() {
		return speedX;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	
}
