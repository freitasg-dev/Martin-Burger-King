package robo;

import robocode.*;
import java.awt.*;

//Robo feito por Lucas, Eduardo e Guilherme Simas

public class Robo extends AdvancedRobot { //usa robo avançado para ter funçoes mais avançadas

    int ladoCurva = 1;
    int contadorTurnos = 0;

    public void run() {
        setBodyColor(Color.blue);
        setGunColor(Color.black);
        setRadarColor(Color.black);
        setBulletColor(Color.red);
        setScanColor(Color.white);

        setAdjustGunForRobotTurn(true); // desbloquea o canhao (nao fica travado junto com o chassis)
        setAdjustRadarForGunTurn(true); // desbloquea o radar

        while (true) {
            // mantem a aceleração maxima constante para a frente
            setAhead(4000);

            // anda de lado, enquanto lado curva for 1, ele anda pra direita, se for -1, anda pra esquerda
            setTurnRight(5 * ladoCurva);

            // radar gira infinitamente para direita (ate encontrar um inimigo)
            setTurnRadarRight(360);

            // conta 36 turnos, quando chega no 36 ou passa de 36, inverte o lado (multiplica por -1, entao o ladoCurva vira -1)
            contadorTurnos++;
            if (contadorTurnos >= 36) {
                ladoCurva *= -1;
                contadorTurnos = 0; // reseta o contador
            }

            execute();

        }

    }

    public void onScannedRobot(ScannedRobotEvent e) {
        // alinha o canhao com o inimigo
        double anguloCanhao = robocode.util.Utils.normalRelativeAngleDegrees(getHeading() + e.getBearing() - getGunHeading());
        setTurnGunRight(anguloCanhao);

        // prende o radar no inimigo
        double giroRadar = robocode.util.Utils.normalRelativeAngleDegrees(
                getHeading() + e.getBearing() - getRadarHeading()
        );
        setTurnRadarRight(giroRadar * 1.5);

        // so dispara quando o canhao n tiver super-aquecido e quando tiver a distancia suficiente, usando a formula do robo tracker-fire junto.
        if (getGunHeat() == 0 && Math.abs(anguloCanhao) < 10) {
            if (e.getDistance() < 200) {
                setFire(3.0);
            } else if (e.getDistance() < 500) {
                setFire(2.0);
            } else {
                setFire(1.0);
            }
        }
        execute();
    }

    public void onHitByBullet(HitByBulletEvent e) {
        // inverte o lado quando toma dano e ja reseta o contador junto
        ladoCurva *= -1;
        contadorTurnos = 0;
        setAhead(4000);
        execute();
    }

    public void onHitWall(HitWallEvent e) {
        // quando bate na parede, anda pra tras, e inverte o lado novamente e reseta o contador
        setBack(150);
        ladoCurva *= -1;
        contadorTurnos = 0;
        execute();
    }

    public void onHitRobot(HitRobotEvent e) {
        // mesma coisa com o da parede, mas quando bate com um inimigo
        setBack(150);
        ladoCurva *= -1;
        contadorTurnos = 0;
        execute();
    }
}