package tank;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
/**
 * 
 * @author Joshua
 *
 */
public class Tank extends Application {
    Pane playerLayer;
    Pane bulletLayer;
    Pane envLayer;
    Pane textPane;

    Image playerOneImage;
    Image playerTwoImage;
    Image bulletImage;
    Image background;

    TankModel playerOne;
    TankModel playerTwo;
    Environment sceneEnv;

    List<Bullets> p1Bullets = new ArrayList<>();
    List<Bullets> p2Bullets = new ArrayList<>();
    
    Group group;
    Scene scene;
    Button resume;
    Button pause;
    Button gameOver;
    
    Rectangle playerOneHealth;
    Rectangle playerTwoHealth;
    

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        group = new Group();

        // create layers
        playerLayer = new Pane();
        bulletLayer = new Pane();
        envLayer = new Pane();
        textPane = new Pane();

        group.getChildren().add(envLayer);
        group.getChildren().add(playerLayer);
        group.getChildren().add(bulletLayer);       

        scene = new Scene(group, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        stage.setScene(scene);
        stage.show();

        loadSprites();
        createTankModels();
        createEnvironment();        
        
        resumeButton();
        pauseButton();
        gameOverButton(stage);
        
        startGame(game);                          
    }
    
    private void startGame(AnimationTimer g) {
    	g.start();
    }
    
    private void pauseGame(AnimationTimer g) {
    	g.stop();
    }
    
    private void loadSprites() {
    	try {
	        playerOneImage = new Image(new FileInputStream("/Users/joshua/Documents/GitHub/Tank_Game/GameDemo/images/tank_right.png"));
	        playerTwoImage = new Image(new FileInputStream("/Users/joshua/Documents/GitHub/Tank_Game/GameDemo/images/tank_left.png"));
	        bulletImage = new Image(new FileInputStream("/Users/joshua/Documents/GitHub/Tank_Game/GameDemo/images/transparent-laser.png"));
	        background = new Image(new FileInputStream("/Users/joshua/Documents/GitHub/Tank_Game/GameDemo/images/background.png"));
    	}
    	catch (FileNotFoundException e) {
    		System.out.println(e.getCause());
    	}    
    } 

    private void createTankModels() {
        // TankModel input
        P1Controls c1 = new P1Controls(scene);
        P2Controls c2 = new P2Controls(scene);               
        
        // create input listeners
        c1.addEventListeners(); 
        c2.addEventListeners();
        
        double startingY = Settings.SCENE_HEIGHT * .85; 
        double startingP1X = Settings.SCENE_WIDTH * .10;
        double startingP2X = Settings.SCENE_WIDTH * .80;

        // create TankModel
        playerOne = new TankModel(playerLayer, playerOneImage, startingP1X, startingY, 0, 0, Settings.TANK_HEALTH, Settings.TANK_SPEED, c1, c2, "player1", 0, 0);
        playerTwo = new TankModel(playerLayer, playerTwoImage, startingP2X, startingY, 0, 0, Settings.TANK_HEALTH, Settings.TANK_SPEED, c1, c2, "player2", 0, 0);              
    }
    
    private void createEnvironment() {
    	sceneEnv = new Environment(envLayer, background, 0, 0, 0, 0, 0, 0);
    }
    
    private AnimationTimer game = new AnimationTimer() {
        @Override
        public void handle(long now) {
           
        	playerOne.processInput(); 
        	playerTwo.processInput(); 
            
        	playerOne.move();
        	playerTwo.move();
            
        	playerOne.updateUI();
        	playerTwo.updateUI();
        	
        	if (playerOne.getP1Controls().isFire()) {
        		double xPosP1 = playerOne.getView().getLayoutX() + playerOne.getImage().getWidth() - 20; 
            	double yPosP1 = playerOne.getView().getLayoutY() + 5;
            	
            	Bullets bullet = new Bullets(bulletLayer, bulletImage, xPosP1, yPosP1, 0.00, 0.00, Settings.BULLET_SPEED, Settings.BULLET_DAMAGE, playerOne.getR(), 0, "player1", false);
            	p1Bullets.add(bullet);
        	}
        	
        	if (playerTwo.getP2Controls().isFire()) {
        		double xPosP2 = playerTwo.getView().getLayoutX() - (playerTwo.getImage().getWidth() / 3); 
            	double yPosP2 = playerTwo.getView().getLayoutY() + 5;
            	
            	Bullets bullet = new Bullets(bulletLayer, bulletImage, xPosP2, yPosP2, 0.00, 0.00, -Settings.BULLET_SPEED, Settings.BULLET_DAMAGE, playerTwo.getR(), 0, "player2", false);
            	p2Bullets.add(bullet);
        	}
        
        	p1Bullets.forEach(bullet -> bullet.move());
        	p1Bullets.forEach(bullet -> bullet.updateUI());
        	p2Bullets.forEach(bullet -> bullet.move());
        	p2Bullets.forEach(bullet -> bullet.updateUI());
        	
        	hasCollidedWith(p1Bullets, playerTwo);
        	removeBullets(p1Bullets);
        	
        	hasCollidedWith(p2Bullets, playerOne);
        	removeBullets(p2Bullets);
        	
        	if (playerOne.getHealth() <= 0) {
             	this.stop();  
             	new GameOverText(textPane, group, "Player 2");
             	group.getChildren().add(gameOver);
                group.getChildren().remove(resume);
                group.getChildren().remove(pause);
        	}
        	
        	if (playerTwo.getHealth() <= 0) {
             	this.stop();  
             	new GameOverText(textPane, group, "Player 1");
             	group.getChildren().add(gameOver);
                group.getChildren().remove(resume);
                group.getChildren().remove(pause);      	        	
        	}
        }
    }; 
    
    private void hasCollidedWith(List<Bullets> bullets, TankModel player) {
    	for (Bullets bullet : bullets) {
    		if(bullet.hasCollided(player)) {
    			player.setHealth(player.getHealth() - bullet.getDamage());
    			bullet.setIsRemovable(true);
    		}
    	}
    }
    
    private void removeBullets(List<Bullets> bullets) {
    	Iterator<Bullets> iter = bullets.iterator();
    	while (iter.hasNext()) {
    		Bullets bullet = iter.next();
    		if (bullet.getIsRemovable() == true) {
    			bullet.removeFromLayer();
    			iter.remove();
    		}
    	}
    }
    
    private void gameOverButton(Stage stage) {
    	gameOver = new Button();
        gameOver.setLayoutX((Settings.SCENE_WIDTH / 2) - 20);
        gameOver.setLayoutY(Settings.SCENE_HEIGHT * .60);
        gameOver.setText("Exit Game");
        gameOver.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		stage.close();
        	}
        });            
    }  
    
    private void resumeButton() {
    	resume = new Button();
        resume.setLayoutX((Settings.SCENE_WIDTH / 2) + 45);
        resume.setLayoutY(Settings.SCENE_HEIGHT - 50);
        resume.setText("Resume");
        resume.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		startGame(game);
        	}
        });          
        group.getChildren().add(resume);
    }
    
    private void pauseButton() {
    	pause = new Button();
        pause.setLayoutX((Settings.SCENE_WIDTH / 2) - 15);
        pause.setLayoutY(Settings.SCENE_HEIGHT - 50);
        pause.setText("Pause");
        pause.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		pauseGame(game);
        	}
        });        
        group.getChildren().add(pause);
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}

class GameOverText extends Text {
	private double x;
	private double y;
	
	public GameOverText(Pane textPane, Group group, String player) {
		setFont(Font.font(null, FontWeight.BOLD, 64));
        setStroke(Color.BLACK);
        setFill(Color.WHITE);
        group.getChildren().add(this);
        setText("Game Over! " + player + " Wins!");
        x = (Settings.SCENE_WIDTH - getBoundsInLocal().getWidth()) / 2;
        y = (Settings.SCENE_HEIGHT - getBoundsInLocal().getHeight()) / 2;
        relocate(x, y);
        setBoundsType(TextBoundsType.VISUAL);
	}
}