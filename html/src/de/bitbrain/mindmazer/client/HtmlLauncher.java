package de.bitbrain.mindmazer.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import de.bitbrain.mindmazer.MindmazerGame;
import de.bitbrain.mindmazer.social.SocialManager;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(480, 320);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new MindmazerGame(new SocialManager() {
                        @Override
                        public void login() {

                        }

                        @Override
                        public void logout() {

                        }

                        @Override
                        public boolean isSignedIn() {
                                return false;
                        }

                        @Override
                        public void showLadder() {

                        }

                        @Override
                        public void showAchievements() {

                        }

                        @Override
                        public boolean isConnected() {
                                return false;
                        }
                });
        }
}