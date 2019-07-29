package tank;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class GameOverText extends Text {
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
