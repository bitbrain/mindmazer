package de.bitbrain.mindmazer.ui;

import de.bitbrain.mindmazer.core.GameStats;
import de.bitbrain.mindmazer.i18n.Bundle;
import de.bitbrain.mindmazer.i18n.Messages;

public class CurrentStageLabel extends HeightedLabel {
	
	private final GameStats stats;

	public CurrentStageLabel(GameStats stats, HeightedLabelStyle style) {
		super(Bundle.translations.get(Messages.INGAME_UI_STAGE) + " 1", style);
		this.stats = stats;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		setText(Bundle.translations.get(Messages.INGAME_UI_STAGE) + " " + stats.getStage());
	}

}
