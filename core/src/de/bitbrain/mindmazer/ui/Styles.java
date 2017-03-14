package de.bitbrain.mindmazer.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import de.bitbrain.mindmazer.Colors;
import de.bitbrain.mindmazer.assets.Assets;
import de.bitbrain.mindmazer.graphics.BitmapFontBaker;

public class Styles {

   public static LabelStyle LABEL_TEXT_INFO = new LabelStyle();

   public static void init() {
      LABEL_TEXT_INFO.font = BitmapFontBaker.bake(Assets.Fonts.RESOURCE, (int) (Gdx.graphics.getWidth() / 6.5f));
      LABEL_TEXT_INFO.fontColor = Colors.CELL_A;
   }

}
