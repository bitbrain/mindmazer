package de.bitbrain.mindmazer.assets;

public interface Assets {

   interface Textures {
      String PLAYER = "textures/player.png";
      String CHIME = "textures/chime.png";
      String BUTTON_9 = "textures/button.9.png";
      String LIFE = "textures/life.png";
   }

   interface Sounds {
      // TODO
   }

   interface Musics {
      String MAINMENU = "sound/4m_mainmenuformindmazer.ogg";
      String INGAME_01 = "sound/maingame_1.ogg";
      String INGAME_02 = "sound/maingame_2.ogg";
      String INGAME_03 = "sound/maingame_3.ogg";
   }

   interface ParticleEffects {
      String JUMP_LAND = "particles/jump-land.p";
   }

   interface Fonts {
      String ANGIES_NEW_HOUSE = "fonts/angies-new-house.ttf";
   }
}
