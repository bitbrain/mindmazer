package de.bitbrain.mindmazer.util;

import com.badlogic.gdx.graphics.Color;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import de.bitbrain.braingdx.tweens.SharedTweenManager;
import de.bitbrain.braingdx.tweens.ValueTween;
import de.bitbrain.braingdx.util.ValueProvider;

public class RainbowMachine {
	
	private static final float DURATION_TICK = 6.5f;
	
	static {
		Tween.registerAccessor(ValueProvider.class, new ValueTween());
	}
	
	private final TweenManager tweenManager = SharedTweenManager.getInstance();
	
	private ValueProvider rChannel;
	private ValueProvider gChannel;
	private ValueProvider bChannel;
	
	
	public RainbowMachine() {
		this.rChannel = new ValueProvider();
		this.gChannel = new ValueProvider();
		this.bChannel = new ValueProvider();
	}
	
	public void start() {
		rChannel.setValue(1f);
		gChannel.setValue(0f);
		bChannel.setValue(0f);
		// R channel tween
		Tween.to(rChannel, ValueTween.VALUE, DURATION_TICK)
		     .target(0f)
		     .delay(DURATION_TICK * 1f)
		     .repeatYoyo(Tween.INFINITY, DURATION_TICK * 2f)
		     .start(tweenManager);
		// G channel tween
		Tween.to(gChannel, ValueTween.VALUE, DURATION_TICK)
	     .target(1f)
	     .delay(DURATION_TICK * 2f)
	     .repeatYoyo(Tween.INFINITY, DURATION_TICK * 2f)
	     .start(tweenManager);
		// B channel tween
		Tween.to(bChannel, ValueTween.VALUE, DURATION_TICK)
	     .target(1f)
	     .repeatYoyo(Tween.INFINITY, DURATION_TICK * 2f)
	     .start(tweenManager);
		
	}
	
	public void stop() {
		tweenManager.killTarget(rChannel);
		tweenManager.killTarget(gChannel);
		tweenManager.killTarget(bChannel);
	}
	
	public Color getColor() {
		return new Color(
				rChannel.getValue(),
				gChannel.getValue(),
				bChannel.getValue(),
				1f);
	}
}
