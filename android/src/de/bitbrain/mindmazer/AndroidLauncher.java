package de.bitbrain.mindmazer;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

import android.os.Bundle;

public class AndroidLauncher extends AndroidApplication implements GameHelperListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.numSamples = 2;
		config.useCompass = false;
		config.useWakelock = true;
		initialize(new MindmazerGame(), config);
	}

	@Override
	public void onSignInFailed() {
		// TODO
	}

	@Override
	public void onSignInSucceeded() {
		// TODO
	}
}
