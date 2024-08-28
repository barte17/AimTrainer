package Games;
import Main.Profil;
import java.awt.*;

public class GameReflex extends GameWithTargets {
    private int SPAWN_INTERVAL = 3000;
    private int BALL_DURATION = 800;


    public GameReflex(Profil profil, String difficulty, Color color,Image background) {
        super(profil,difficulty,color,background);

        if(difficulty.equals("TRUDNY")) {
            SPAWN_INTERVAL = 2800;
            BALL_DURATION = 600;
            BALL_SIZE = icon.getIconWidth();
        }

        startSpawnTimer(SPAWN_INTERVAL,BALL_DURATION);
    }

}