package de.bitbrain.mindmazer.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.braingdx.graphics.GraphicsFactory;
import de.bitbrain.mindmazer.Colors;
import de.bitbrain.mindmazer.assets.Assets;
import de.bitbrain.mindmazer.graphics.BitmapFontBaker;

public class Styles {

   public static final LabelStyle LABEL_TEXT_LOGO = new LabelStyle();
   public static final LabelStyle LABEL_TEXT_INFO = new LabelStyle();
   public static final LabelStyle LABEL_TEXT_CREDITS = new LabelStyle();
   public static final TextButtonStyle TEXTBUTTON_MENU = new TextButtonStyle();

   public static void init() {
      LABEL_TEXT_INFO.font = BitmapFontBaker.bake(Assets.Fonts.ANGIES_NEW_HOUSE, (int) (Gdx.graphics.getWidth() / 8.5f));
      LABEL_TEXT_INFO.fontColor = Color.WHITE;
      LABEL_TEXT_LOGO.font = BitmapFontBaker.bake(Assets.Fonts.ANGIES_NEW_HOUSE,
            (int) (Gdx.graphics.getWidth() / 5.5f));
      LABEL_TEXT_LOGO.fontColor = Color.WHITE;
      LABEL_TEXT_CREDITS.font = BitmapFontBaker.bake(Assets.Fonts.ANGIES_NEW_HOUSE,
            (int) (Gdx.graphics.getWidth() / 13.5f));
      Color creditColor = Colors.FOREGROUND.cpy();
      creditColor.a = 0.78f;
      LABEL_TEXT_CREDITS.fontColor = creditColor;
      TEXTBUTTON_MENU.font = BitmapFontBaker.bake(Assets.Fonts.ANGIES_NEW_HOUSE,
            (int) (Gdx.graphics.getWidth() / 11.5f));
      Texture buttonTexture = SharedAssetManager.getInstance().get(Assets.Textures.BUTTON_9, Texture.class);
      TEXTBUTTON_MENU.fontColor = Colors.CELL_A;
      TEXTBUTTON_MENU.up = new NinePatchDrawable(GraphicsFactory.createNinePatch(buttonTexture, 14, Colors.CELL_A));
      TEXTBUTTON_MENU.overFontColor = Colors.CELL_B;
      TEXTBUTTON_MENU.over = new NinePatchDrawable(GraphicsFactory.createNinePatch(buttonTexture, 14, Colors.CELL_B));
      TEXTBUTTON_MENU.checkedFontColor = Colors.CELL_B_DARK;
      TEXTBUTTON_MENU.checked = new NinePatchDrawable(GraphicsFactory.createNinePatch(buttonTexture, 14, Colors.CELL_B_DARK));
   }

}
