package de.bitbrain.mindmazer.ui;

import de.bitbrain.mindmazer.core.GameStats;

public class LifeLabel extends HeightedLabel {

   private final GameStats stats;

   public LifeLabel(GameStats stats, HeightedLabelStyle style) {
   	super("0 Life", style);
      this.stats = stats;
   }
   
   @Override
   public void act(float delta) {   	
   	super.act(delta);
   	setText(stats.getLife() + " life");
   }
}
