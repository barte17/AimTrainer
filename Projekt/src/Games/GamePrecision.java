package Games;
import Main.Profil;
import java.awt.*;

public class GamePrecision extends GameWithTargets {
    private int SPAWN_INTERVAL = 750;
    private int BALL_DURATION = 2000;


    public GamePrecision(Profil profil, String difficulty, Color color, Image background) {
        super(profil,difficulty,color,background);

        if(difficulty.equals("TRUDNY")) {
            SPAWN_INTERVAL = 600;
            BALL_DURATION = 1500;
            BALL_SIZE = icon.getIconWidth();
        }

        startSpawnTimer(SPAWN_INTERVAL,BALL_DURATION);
    }

}