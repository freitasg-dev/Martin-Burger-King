package robo;
import robocode.*;
import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * MeuPrimeiroRobo - a robot by Lucas, Eduardo and Guilherme
 */
public class MeuPrimeiroRobo extends Robot
{

	int direcao = 1; 

	/**
	 * run: MeuPrimeiroRobo's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar

		setBodyColor(Color.blue);
		setGunColor(Color.black);
		setRadarColor(Color.black);
		setBulletColor(Color.red);
		setScanColor(Color.white);

		// Robot main loop

		while(true) {

    		ahead(150);
    		turnRight(90);

    		ahead(150);
   		 	turnRight(90);

    		ahead(150);
    		turnRight(90);

    		ahead(150);
    		turnRight(90);	
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		fire(2);
		back(100);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		
		direcao = direcao * -1;
		turnRight(90);
		ahead(100 * direcao);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		direcao = direcao * -1;
		back(80);
		turnRight(90);
	}	
}
