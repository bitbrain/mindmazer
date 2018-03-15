package de.bitbrain.mindmazer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import de.bitbrain.braingdx.BrainGdxGame;
import de.bitbrain.braingdx.GameContext;
import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.braingdx.audio.AudioManager;
import de.bitbrain.braingdx.graphics.pipeline.layers.RenderPipeIds;
import de.bitbrain.braingdx.graphics.renderer.SpriteRenderer;
import de.bitbrain.braingdx.postprocessing.effects.Vignette;
import de.bitbrain.braingdx.screens.AbstractScreen;
import de.bitbrain.braingdx.tweens.GameObjectTween;
import de.bitbrain.braingdx.tweens.SharedTweenManager;
import de.bitbrain.braingdx.world.GameObject;
import de.bitbrain.mindmazer.Colors;
import de.bitbrain.mindmazer.MindmazerGame;
import de.bitbrain.mindmazer.assets.Assets;

public class IntroScreen extends AbstractScreen<MindmazerGame> {

    private Sprite logo;

    private GameContext context;

    private boolean exiting;

    public IntroScreen(MindmazerGame game) {
        super(game);
    }

    @Override
    protected void onCreate(final GameContext context) {
        this.context = context;
        context.getScreenTransitions().in(0.5f);
        setBackgroundColor(Colors.BACKGROUND);
        context.getRenderManager().register("logo", new SpriteRenderer(Assets.Textures.BITBRAIN_LOGO));
        GameObject logo = context.getGameWorld().addObject();
        logo.setType("logo");
        logo.setDimensions(64f, 64f);
        context.getGameCamera().setTarget(logo);

        Tween.to(logo, GameObjectTween.SCALE, 2.5f)
                .target(5f)
                .ease(TweenEquations.easeOutElastic)
                .setCallbackTriggers(TweenCallback.COMPLETE)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int i, BaseTween<?> baseTween) {
                        if (!exiting) {
                            context.getScreenTransitions().out(new MenuScreen(getGame()), 1f);
                        }
                    }
                })
                .start(SharedTweenManager.getInstance());
        SharedAssetManager.getInstance().get(Assets.Sounds.BITBRAIN, Sound.class).play(0.5f);
    }

    @Override
    protected void onUpdate(float delta) {
        super.onUpdate(delta);
        if (!exiting && (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY))) {
            exiting = true;
            context.getScreenTransitions().out(new MenuScreen(getGame()), 1f);
        }
    }
}
