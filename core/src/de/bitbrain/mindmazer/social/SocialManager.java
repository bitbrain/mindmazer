package de.bitbrain.mindmazer.social;

public interface SocialManager {

    void login();
    void logout();
    boolean isSignedIn();
    void showLadder();
    void showAchievements();
    boolean isConnected();
}
