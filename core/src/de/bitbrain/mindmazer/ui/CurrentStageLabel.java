package de.bitbrain.mindmazer.ui;

import de.bitbrain.mindmazer.core.GameStats;

public class CurrentStageLabel extends HeightedLabel {
	
	private final GameStats stats;

	public CurrentStageLabel(GameStats stats, HeightedLabelStyle style) {
		super("stage 1", style);
		this.stats = stats;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		setText("stage " + stats.getStage());
	}

}
