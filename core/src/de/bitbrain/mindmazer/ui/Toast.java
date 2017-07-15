package de.bitbrain.mindmazer.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import de.bitbrain.braingdx.tweens.ActorTween;
import de.bitbrain.braingdx.tweens.SharedTweenManager;
import de.bitbrain.mindmazer.Colors;

public class Toast {

	private static final float DEFAULT_DURATION_OUT = 0.7f;
	private static final float DEFAULT_DURATION = 2f;
	private static final Color DEFAULT_COLOR = Colors.CELL_A;
	private static final Toast instance = new Toast();
	
	private final TweenManager tweenManager = SharedTweenManager.getInstance();
	
	private Stage stage;
	private TextButton toast;
	
	public void setStage(Stage stage) {
		if (this.stage != null) {
			stage.getActors().removeValue(toast, true);
		}
		this.stage = stage;
		if (toast == null) {
			toast = new TextButton("", Styles.TEXTBUTTON_TOAST);
			toast.setTouchable(Touchable.disabled);
			toast.setFillParent(true);			
		}
		stage.addActor(toast);	
	}
	
	public static Toast getInstance() {
		return instance;
	}
	
	public void makeToast(String text, Color color, float duration, float outDuration) {
		if (stage != null) {
			toast.setText(text);
			toast.getLabel().setColor(color.cpy());
			toast.getColor().a = 1f;
			tweenManager.killTarget(toast);
			Tween.to(toast, ActorTween.ALPHA, outDuration)
			  .delay(duration)
		     .target(0f)
		     .start(tweenManager);
		}
	}
	
	public void makeToast(String text) {
		makeToast(text, DEFAULT_COLOR, DEFAULT_DURATION, DEFAULT_DURATION_OUT);
	}
}
