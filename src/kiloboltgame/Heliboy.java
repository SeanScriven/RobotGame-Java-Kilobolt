package kiloboltgame;

import java.util.Calendar;

public class Heliboy extends Enemy {

	Calendar cal;

	public Heliboy(int centerX, int centerY) {

		setCenterX(centerX * 40);
		setCenterY(centerY * 40);
		//duration = shootingTime;
		alive = true;

		cal = Calendar.getInstance();
		lastAttack = cal.getTimeInMillis();
	}

}
