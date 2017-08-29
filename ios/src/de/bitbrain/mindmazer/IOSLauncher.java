package de.bitbrain.mindmazer;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import de.bitbrain.mindmazer.social.SocialManager;

public class IOSLauncher extends IOSApplication.Delegate {
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        return new IOSApplication(new MindmazerGame(new SocialManager() {
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
        }), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }
}