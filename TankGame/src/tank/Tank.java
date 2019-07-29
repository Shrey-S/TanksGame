package tank;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
    //Pane healthPane;

    Image playerOneImage;
    Image playerTwoImage;
    Image bulletImage;
    Image background;
    
    boolean isSinglePlayer = true;       // Single Player 
    		
    TankModel playerOne;
    TankModel playerTwo;
    /*
    if (isSinglePlayer == 1) {
    	TankModel playerTwo;         // Tank AI Bot to play against
    } else {
    	TankModel playerTwo;
    	isSinglePlayer = 0;
    }
    */
    TankAI AI;
    Environment sceneEnv;

    List<Bullets> p1Bullets = new ArrayList<>();
    List<Bullets> p2Bullets = new ArrayList<>();
    
    Group group;
    Scene scene;
    Button resume;
    Button pause;
    Button mainMenu;
    
    Rectangle playerOneHealth;
    Rectangle playerTwoHealth;
    
    Text gameOverText = new Text();

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
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

        primaryStage.setScene(scene);
        primaryStage.show();

        loadSprites();
        createTankModels();
        createEnvironment();        
        
        pause = new Button();
        pause.setLayoutX((Settings.SCENE_WIDTH / 2) - 15);
        pause.setLayoutY(Settings.SCENE_HEIGHT - 50);
        pause.setText("Pause");
        pause.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		pauseGame(game);
        	}
        });
        
        resume = new Button();
        resume.setLayoutX((Settings.SCENE_WIDTH / 2) + 45);
        resume.setLayoutY(Settings.SCENE_HEIGHT - 50);
        resume.setText("Resume");
        resume.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		resumeGame(game);
        	}
        });
             
        group.getChildren().add(resume);
        group.getChildren().add(pause);  
        
        //createHealthBars();
        
        //group.getChildren().add(playerOneHealth);
        //group.getChildren().add(playerTwoHealth);
        
        //group.getChildren().add(playerOneHealth);
        //group.getChildren().add(playerTwoHealth);
        
        startGame(game);                          
    }
    
    private void startGame(AnimationTimer g) {
    	g.start();
    }
    
    //used go transition to game over sequence
    private void pauseGame(AnimationTimer g) {
    	g.stop();
    }
    
    private void resumeGame(AnimationTimer g) {
    	g.start();
    }
    
    private void loadSprites() throws FileNotFoundException {
        playerOneImage = new Image(new FileInputStream("/Users/shrey/Desktop/Tank_Game/TankGame/images/tank_right.png"));
        playerTwoImage = new Image(new FileInputStream("/Users/shrey/Desktop/Tank_Game/TankGame/images/tank_left.png"));
        bulletImage = new Image(new FileInputStream("/Users/shrey/Desktop/Tank_Game/TankGame/images/transparent-laser.png"));
        background = new Image(new FileInputStream("/Users/shrey/Desktop/Tank_Game/TankGame/images/background.png"));
        
    } 

    private void createTankModels() {
        // TankModel input
        P1Controls c1 = new P1Controls(scene);
        P2Controls c2 = new P2Controls(scene);
        AI = new TankAI(); 
        
        // create input listeners
        c1.addEventListeners(); 
        c2.addEventListeners();
        
        double startingY = Settings.SCENE_HEIGHT * .85; 
        double startingP1X = Settings.SCENE_WIDTH * .10;
        double startingP2X = Settings.SCENE_WIDTH * .80;

        // create TankModel
        playerOne = new TankModel(playerLayer, playerOneImage, startingP1X, startingY, 0, 0, Settings.TANK_HEALTH, Settings.TANK_SPEED, c1, c2, "player1", 0, 0);
        if (isSinglePlayer) {
        	playerTwo = new TankModel(playerLayer, playerTwoImage, startingP2X, startingY, 0, 0, Settings.TANK_HEALTH, Settings.TANK_SPEED, c1, c2, "player2", 0, 0, AI);                          
        } else {
        	playerTwo = new TankModel(playerLayer, playerTwoImage, startingP2X, startingY, 0, 0, Settings.TANK_HEALTH, Settings.TANK_SPEED, c1, c2, "player2", 0, 0);                          
        }
     
    }
    
    private void createEnvironment() {
    	//double startingY = Settings.SCENE_HEIGHT * .75;
    	sceneEnv = new Environment(envLayer, background, 0, 0, 0, 0, 0, 0);
    }
    
    private AnimationTimer game = new AnimationTimer() {
        @Override
        public void handle(long now) {
           
        	AI.giveInfo(playerOne.getX(), playerTwo.getX(), playerOne.getY(), playerTwo.getY(), playerOne.getR(), playerTwo.getR(), p1Bullets, p2Bullets);
        	
        	playerOne.processInput(); 
        	playerTwo.processInput(); 
            
        	playerOne.move();
        	playerTwo.move();
            
        	playerOne.updateUI();
        	playerTwo.updateUI();
        	
        	if (playerOne.getP1Controls().isFire()) {
        		double xPosP1 = playerOne.getView().getLayoutX() + playerOne.getImage().getWidth() - 20; 
            	double yPosP1 = playerOne.getView().getLayoutY() + 5;
            	
            	Bullets bullet = new Bullets(bulletLayer, bulletImage, xPosP1, yPosP1, 0.00, 0.00, Settings.BULLET_SPEED, Settings.BULLET_DAMAGE, playerOne.getR(), 0, "player1");
            	p1Bullets.add(bullet);
        	}
        	
        	if (isSinglePlayer) {
        		if (AI.isFire()) {
        			
        		
	        		double xPosP2 = playerTwo.getView().getLayoutX() - (playerTwo.getImage().getWidth() / 3); 
	            	double yPosP2 = playerTwo.getView().getLayoutY() + 5;
	            	
	            	Bullets bullet = new Bullets(bulletLayer, bulletImage, xPosP2, yPosP2, 0.00, 0.00, -Settings.BULLET_SPEED, Settings.BULLET_DAMAGE, playerTwo.getR(), 0, "player2");
	            	p2Bullets.add(bullet);
        		}
        	} else if (playerTwo.getP2Controls().isFire()) {
        		double xPosP2 = playerTwo.getView().getLayoutX() - (playerTwo.getImage().getWidth() / 3); 
            	double yPosP2 = playerTwo.getView().getLayoutY() + 5;
            	
            	Bullets bullet = new Bullets(bulletLayer, bulletImage, xPosP2, yPosP2, 0.00, 0.00, -Settings.BULLET_SPEED, Settings.BULLET_DAMAGE, playerTwo.getR(), 0, "player2");
            	p2Bullets.add(bullet);
        	}
        	/*
        	if (playerOne.getY() > playerOne.getTankMinY() && playerOne.getY() < playerOne.getTankMaxY()) {        		
        	    	Parachute p = new Parachute(parachuteLayer, parachute, playerOne.getView().getLayoutX() + (playerOne.getWidth() / 4), playerOne.getView().getLayoutY() - (playerOne.getHeight() / 2), 0, 0, playerOne.getPlayer(), Settings.TANK_SPEED);
        	    	p.getView().setFitWidth(75.0);
        	        p.getView().setFitHeight(75.0);  
        	        p.removeFromLayer();
        	    }        	
        	*/
        	p1Bullets.forEach(bullet -> bullet.move());
        	p1Bullets.forEach(bullet -> bullet.updateUI());
        	p2Bullets.forEach(bullet -> bullet.move());
        	p2Bullets.forEach(bullet -> bullet.updateUI());
        	
        	for (Bullets bullet : p1Bullets) {
        		if (bullet.hasCollided(playerTwo)) {
        			AI.saveCollision(bullet.getX());
        			playerTwo.setHealth(playerTwo.getHealth() - bullet.getDamage());
        			bullet.removeFromLayer();
        			p1Bullets.remove(bullet);
        			System.out.println(playerTwo.getHealth());
        		}
        		/*if (bullet.outOfBounds()) {
        			AI.addDeadPBullet();
        		} */
        	}
        	
        	for (Bullets bullet : p2Bullets) {
        		if (bullet.hasCollided(playerOne)) {
        			playerOne.setHealth(playerOne.getHealth() - bullet.getDamage());
        			bullet.removeFromLayer();
        			p2Bullets.remove(bullet);
        			System.out.println(playerOne.getHealth());
        		}       
        		/*if (bullet.outOfBounds()) {
        			AI.addDeadTBullet();
        		} */		
        	}
        	
        	if (playerOne.getHealth() <= 0) {
             	this.stop();  
             	new GameOverText(textPane, group, "Player 2");
             	mainMenuButton();
        	}
        	
        	if (playerTwo.getHealth() <= 0) {
             	this.stop();  
             	new GameOverText(textPane, group, "Player 1");
             	mainMenuButton();        	        	
        	}
        }
    }; 
    
    private void mainMenuButton() {
    	mainMenu = new Button();
        mainMenu.setLayoutX((Settings.SCENE_WIDTH / 2) - 20);
        mainMenu.setLayoutY(Settings.SCENE_HEIGHT * .60);
        mainMenu.setText("Main Menu");
        mainMewnu.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		resumeGame(game);
        	}
        });
             
        group.getChildren().add(mainMenu);
        group.getChildren().remove(resume);
        group.getChildren().remove(pause);
    }
/*
    private void createHealthBars() {
    	playerOneHealth = new Rectangle(200.0, 30.0, Color.YELLOW);
        playerTwoHealth = new Rectangle(200.0, 30.0, Color.YELLOW);
        
        DoubleProperty healthPercentage1 = new SimpleDoubleProperty(1.0);
        DoubleProperty healthPercentage2 = new SimpleDoubleProperty(1.0);

        DoubleBinding b1 = playerOneHealth.widthProperty().multiply(healthPercentage1);
        DoubleBinding b2 = playerTwoHealth.widthProperty().multiply(healthPercentage2);
        playerOneHealth.widthProperty().bind(b1);
        playerTwoHealth.widthProperty().bind(b2);
    }
   
*/    
    public static void main(String[] args) {
        launch(args);
    }

}