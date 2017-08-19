package de.bitbrain.mindmazer.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.braingdx.graphics.GraphicsFactory;
import de.bitbrain.mindmazer.Colors;
import de.bitbrain.mindmazer.assets.Assets;
import de.bitbrain.mindmazer.graphics.BitmapFontBaker;

public class Styles {

   public static final HeightedLabelStyle LABEL_TEXT_LOGO = new HeightedLabelStyle();
   public static final HeightedLabelStyle LABEL_TEXT_INFO = new HeightedLabelStyle();
	public static final LabelStyle LABEL_POPUP_DESCRIPTION = new LabelStyle();
   public static final LabelStyle LABEL_TEXT_CREDITS = new LabelStyle();
   public static final HeightedLabelStyle LABEL_CAPTION = new HeightedLabelStyle();
   public static final TextButtonStyle TEXTBUTTON_MENU = new TextButtonStyle();
	public static final TextButtonStyle TEXTBUTTON_TOAST = new TextButtonStyle();
	public static final ImageButtonStyle IMAGEBUTTON_POPUPMENU = new ImageButtonStyle();

   public static void init() {
      LABEL_TEXT_INFO.font = BitmapFontBaker.bake(Assets.Fonts.ANGIES_NEW_HOUSE, (int) (Gdx.graphics.getWidth() / 10.5f));
      LABEL_TEXT_INFO.fontColor = Colors.CELL_A;
      LABEL_TEXT_INFO.fontColorHeighted = Colors.CELL_B_DARK;  
      
      TEXTBUTTON_TOAST.font = BitmapFontBaker.bake(Assets.Fonts.ANGIES_NEW_HOUSE, (int) (Gdx.graphics.getWidth() / 8.5f));
      TEXTBUTTON_TOAST.fontColor = Color.WHITE;
      TEXTBUTTON_TOAST.up = new SpriteDrawable(new Sprite(GraphicsFactory.createTexture(2, 2, new Color(0f, 0f, 0f, 0.65f))));
      
      LABEL_TEXT_LOGO.font = BitmapFontBaker.bake(Assets.Fonts.ANGIES_NEW_HOUSE,
            (int) (Gdx.graphics.getWidth() / 5.5f));
      LABEL_TEXT_LOGO.fontColor = Colors.CELL_A;
      LABEL_TEXT_LOGO.fontColorHeighted = Colors.CELL_B_DARK;
      
      LABEL_TEXT_CREDITS.font = BitmapFontBaker.bake(Assets.Fonts.ANGIES_NEW_HOUSE,
            (int) (Gdx.graphics.getWidth() / 13.5f));
      Color creditColor = Colors.FOREGROUND.cpy();
      creditColor.a = 0.78f;
      LABEL_TEXT_CREDITS.fontColor = creditColor;
      
      LABEL_CAPTION.font = BitmapFontBaker.bake(Assets.Fonts.ANGIES_NEW_HOUSE, (int) (Gdx.graphics.getWidth() / 6.5f));
      LABEL_CAPTION.fontColor = Colors.CELL_A;
      LABEL_CAPTION.fontColorHeighted = Colors.CELL_B_DARK;
      
      LABEL_POPUP_DESCRIPTION.font = BitmapFontBaker.bake(Assets.Fonts.ANGIES_NEW_HOUSE, (int) (Gdx.graphics.getWidth() / 16.5f));
      LABEL_POPUP_DESCRIPTION.fontColor = Color.WHITE;
      
      TEXTBUTTON_MENU.font = BitmapFontBaker.bake(Assets.Fonts.ANGIES_NEW_HOUSE,
            (int) (Gdx.graphics.getWidth() / 11.5f));
      Texture buttonTexture = SharedAssetManager.getInstance().get(Assets.Textures.BUTTON_9, Texture.class);
      TEXTBUTTON_MENU.fontColor = Colors.CELL_A;
      TEXTBUTTON_MENU.up = new NinePatchDrawable(GraphicsFactory.createNinePatch(buttonTexture, 14, Colors.CELL_A));
      TEXTBUTTON_MENU.overFontColor = Colors.CELL_B;
      TEXTBUTTON_MENU.over = new NinePatchDrawable(GraphicsFactory.createNinePatch(buttonTexture, 14, Colors.CELL_B));
      TEXTBUTTON_MENU.checkedFontColor = Colors.CELL_B_DARK;
      TEXTBUTTON_MENU.checked = new NinePatchDrawable(GraphicsFactory.createNinePatch(buttonTexture, 14, Colors.CELL_B_DARK));
      
      IMAGEBUTTON_POPUPMENU.up = new NinePatchDrawable(GraphicsFactory.createNinePatch(buttonTexture, 14, Colors.CELL_A));
      IMAGEBUTTON_POPUPMENU.checked = new NinePatchDrawable(GraphicsFactory.createNinePatch(buttonTexture, 14, Colors.CELL_B));
      IMAGEBUTTON_POPUPMENU.down = new NinePatchDrawable(GraphicsFactory.createNinePatch(buttonTexture, 14, Colors.CELL_B));
      Sprite menuIconUp = new Sprite(SharedAssetManager.getInstance().get(Assets.Textures.MENU, Texture.class));
      menuIconUp.setColor(Colors.CELL_B);
      IMAGEBUTTON_POPUPMENU.imageUp = new SpriteDrawable(menuIconUp);
      Sprite menuIconDown = new Sprite(SharedAssetManager.getInstance().get(Assets.Textures.MENU, Texture.class));
      menuIconDown.setColor(Colors.CELL_A);
      IMAGEBUTTON_POPUPMENU.imageDown = new SpriteDrawable(menuIconDown);
      IMAGEBUTTON_POPUPMENU.imageChecked = new SpriteDrawable(menuIconDown);
   }

}
