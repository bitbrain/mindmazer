package de.bitbrain.mindmazer.assets;

public interface Assets {

   interface Textures {
      String PLAYER = "textures/player.png";
      String LIFE = "textures/life.png";
      String CHIME = "textures/chime.png";
      String BUTTON_9 = "textures/button.9.png";
      String MENU = "textures/menu.png";
      String ACHIEVEMENTS = "textures/achievements.png";
      String MUTE = "textures/mute.png";
      String EXIT = "textures/exit.png";
   }

   interface Sounds {
      String OBSCURE_MAP = "sound/hidemap.ogg";
      String SHOW_MAP = "sound/showmap.ogg";
      String DEATH = "sound/death.ogg";
      String BUTTON_CLICK = "sound/button_click.ogg";
      String LIFE_UP = "sound/1up.ogg";
      String LEVEL_COMPLETE = "sound/level_complete.ogg";
      String GAME_OVER = "sound/game_over_fx_short.ogg";
      String JUMP_01 = "sound/step_2.ogg";
      String LAND_01 = "sound/step_1.ogg";
   }

   interface Musics {
      String MAINMENU = "music/4m_mainmenuformindmazer.ogg";
      String INGAME_01 = "music/maingame_1.ogg";
      String INGAME_02 = "music/maingame_2.ogg";
      String INGAME_03 = "music/maingame_3.ogg";
      String GAMEOVER = "music/game_over_screen.ogg";
   }

   interface ParticleEffects {
      String JUMP_LAND = "particles/jump-land.p";
   }

   interface Fonts {
      String ANGIES_NEW_HOUSE = "fonts/angies-new-house.ttf";
   }
}
