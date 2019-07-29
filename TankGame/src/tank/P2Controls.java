package tank;

import java.util.BitSet;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
/**
 * 
 * @author Joshua
 *
 */
public class P2Controls {
    private KeyCode p2LeftKey;
    private KeyCode p2RightKey;
    private KeyCode p1UpKey;
    private KeyCode p1DownKey;
    private KeyCode fire;
    private KeyCode p2RotateUp;
    private KeyCode p2RotateDown;
    
    private Scene scene;
    private BitSet keyboard; 
    
    public P2Controls(Scene scene) {
    	this.p2LeftKey = KeyCode.J;
        this.p2RightKey = KeyCode.L;
        this.p1UpKey = KeyCode.I;
        this.p1DownKey = KeyCode.K;
        this.fire = KeyCode.H;
        this.p2RotateUp = KeyCode.U;
        this.p2RotateDown = KeyCode.N;
        this.scene = scene;
        this.keyboard = new BitSet();     
    }

    public void addEventListeners() {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
    }

    private EventHandler<KeyEvent> keyPressedEventHandler = new EventHandler<KeyEvent>() {
    	@Override
    	public void handle(KeyEvent e) {
    		keyboard.set(e.getCode().ordinal(), true);
    	}
    };
    	
    private EventHandler<KeyEvent> keyReleasedEventHandler = new EventHandler<KeyEvent>() {
    	@Override
    	public void handle(KeyEvent e) {
    		keyboard.set(e.getCode().ordinal(), false);
    	}
    };

    public boolean isMoveLeft() {
        return keyboard.get(p2LeftKey.ordinal()) && !keyboard.get(p2RightKey.ordinal());  
    }

    public boolean isMoveRight() {
        return keyboard.get(p2RightKey.ordinal()) && !keyboard.get(p2LeftKey.ordinal());
    }
    
    public boolean isFire() {
        return keyboard.get(fire.ordinal());
    }
    
    public boolean isMoveUp() {
        return keyboard.get(p1UpKey.ordinal()) && !keyboard.get(p1DownKey.ordinal());
    }
    
    public boolean isMoveDown() {
        return keyboard.get(p1DownKey.ordinal()) && !keyboard.get(p1UpKey.ordinal());
    }
    
    public boolean isRotateUp() {
        return keyboard.get(p2RotateUp.ordinal()) && !keyboard.get(p2RotateDown.ordinal());
    }
    
    public boolean isRotateDown() {
        return keyboard.get(p2RotateDown.ordinal()) && !keyboard.get(p2RotateUp.ordinal());
    }
         
}    