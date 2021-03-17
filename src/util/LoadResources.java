package util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LoadResources {
    
    private static LoadResources instance;
    
    //Resources
    private BufferedImage wall_png;
    private BufferedImage ground_png;
    private BufferedImage enemy_png;
    private BufferedImage self_png;
    private BufferedImage neutral_png;
    private BufferedImage bomb_png;
    private BufferedImage mine_png;
    private BufferedImage pickup_png;
    
    public static LoadResources getInstance() {
        if (instance == null) {
            instance = new LoadResources();
        }

        return instance;
    }
    
    public LoadResources(){
       try {
            wall_png = ImageIO.read(this.getClass().getResource("/view/images/wall.png"));
            ground_png = ImageIO.read(this.getClass().getResource("/view/images/ground.png"));

            bomb_png = ImageIO.read(this.getClass().getResource("/view/images/bomb.png"));
            enemy_png = ImageIO.read(this.getClass().getResource("/view/images/enemy.png"));
            mine_png = ImageIO.read(this.getClass().getResource("/view/images/mine.png"));
            neutral_png = ImageIO.read(this.getClass().getResource("/view/images/neutral.png"));
            self_png = ImageIO.read(this.getClass().getResource("/view/images/self.png"));
            pickup_png = ImageIO.read(this.getClass().getResource("/view/images/pickup.png"));
        } catch (IOException e) {
            System.out.println("erreur lors du chargement des images");
        } 
    }

    public BufferedImage getWall_png() {
        return wall_png;
    }

    public BufferedImage getGround_png() {
        return ground_png;
    }

    public BufferedImage getEnemy_png() {
        return enemy_png;
    }

    public BufferedImage getSelf_png() {
        return self_png;
    }

    public BufferedImage getNeutral_png() {
        return neutral_png;
    }

    public BufferedImage getBomb_png() {
        return bomb_png;
    }

    public BufferedImage getMine_png() {
        return mine_png;
    }

    public BufferedImage getPickup_png() {
        return pickup_png;
    }
    
    
    
    
    
    
}
