package de.bitbrain.mindmazer.i18n;

import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

public class Bundle {

   public static I18NBundle translations;

   private static FileHandle generalHandle;

   public static void load() {
      Gdx.app.log("LOAD", "Loading bundles...");
      generalHandle = Gdx.files.internal("i18n/locale");
      Locale locale = Locale.getDefault();
      setLocale(locale);
      Gdx.app.log("INFO", "Done loading bundles.");
   }

   public static void setLocale(Locale locale) {
      Gdx.app.log("INFO", "Set locale to '" + locale + "'");
      translations = I18NBundle.createBundle(generalHandle, locale);
   }

}
