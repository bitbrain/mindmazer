package de.bitbrain.mindmazer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;

import de.bitbrain.mindmazer.social.SocialManager;

public class AndroidLauncher extends AndroidApplication implements GameHelperListener, SocialManager {

   public static final String TAG = "MindmazerActivity";

	private GameHelper gameHelper;

	@Override
	public void onSignInFailed() {
		Log.d(TAG, "Sign in failed!");
	}

	@Override
	public void onSignInSucceeded() {
		Log.d(TAG, "Sign in Succeeded!");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.numSamples = 2;
		config.useCompass = false;
		config.useWakelock = true;
		gameHelper = new GameHelper(this, GameHelper.CLIENT_ALL);
		gameHelper.setup(this);
		initialize(new MindmazerGame(this), config);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart(): Connecting to Google APIs");
		gameHelper.onStart(this);
	}

	 @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop(): Disconnecting from Google APIs");
		 gameHelper.onStop();
    }

	@Override
	protected void onPause() {
		super.onPause();
		gameHelper.onStop();
		Log.i("PLAYSERVICES", "onPause()");
	}

	@Override
	protected void onResume() {
		super.onResume();
		gameHelper.onStart(this);
		Log.i("PLAYSERVICES", "onResume()");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
		Log.i("PLAYSERVICES", "onActivityResult()");
	}

	@Override
	public void login() {
		try {
			runOnUiThread(new Runnable(){
				@Override
				public void run(){
					Log.d(TAG, "login(): begin user initiated sign in");
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (final Exception ex){
			Log.d(TAG, ex.getMessage());
		}
	}

	@Override
	public void logout() {
		try {
			runOnUiThread(new Runnable(){
				@Override
				public void run(){
					gameHelper.signOut();
				}
			});
		} catch (final Exception ex){
			Log.d(TAG, ex.getMessage());
		}
	}

	@Override
	public boolean isSignedIn() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void showLadder() {
		if (isSignedIn()) {
			startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(gameHelper.getApiClient()), 105);
		}
	}

	@Override
	public void showAchievements() {
		if (isSignedIn()) {
			startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 105);
		}
	}

	@Override
	public boolean isConnected() {
		return gameHelper.isSignedIn();
	}
}
