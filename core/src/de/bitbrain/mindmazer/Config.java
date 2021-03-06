package de.bitbrain.mindmazer;

public interface Config {

   String GAME_NAME = "mindmazer";
   String GAME_VERSION = "v1.0";
   
   boolean DEBUG = false;
   int TILE_SIZE = 16;
   float BASE_ZOOM_DESKTOP = 0.2f;
   float BASE_ZOOM = 0.02f;
   int DEFAULT_LIFE = 3;
   float MUSIC_VOLUME = 0.7f;
   int SWIPE_TOLERANCE = 8;
   int SEED_STRING_LENGTH = 24;
   
   String PREFERENCE_ID = "mindmazer-preferences";
}
