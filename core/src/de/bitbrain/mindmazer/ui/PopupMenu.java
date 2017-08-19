package de.bitbrain.mindmazer.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

import de.bitbrain.braingdx.assets.SharedAssetManager;
import de.bitbrain.mindmazer.Colors;

public class PopupMenu extends Group {
	
	private static final float PADDING = 20f;
	
	private List<Table> entries = new ArrayList<Table>();
	
	private ImageButton menuButton;
	
	public PopupMenu() {
		menuButton = new ImageButton(Styles.IMAGEBUTTON_POPUPMENU);
		menuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				for (Table group : entries) {
					group.setVisible(!group.isVisible());
				}
			}
		});
		addActor(menuButton);
	}

	public void add(String icon, String text, ClickListener listener) {
		Table entry = new Table();
		entry.align(Align.left);
		Label description = new Label(text, Styles.LABEL_POPUP_DESCRIPTION);
		description.setAlignment(Align.right);
		entry.add(description);
		ImageButtonStyle iconStyle = new ImageButtonStyle(Styles.IMAGEBUTTON_POPUPMENU);
		iconStyle.checked = iconStyle.up;
		iconStyle.over = iconStyle.down;
		Sprite menuIconUp = new Sprite(SharedAssetManager.getInstance().get(icon, Texture.class));
		menuIconUp.setColor(Colors.CELL_A);
		iconStyle.imageUp = new SpriteDrawable(menuIconUp);
	   Sprite menuIconDown = new Sprite(SharedAssetManager.getInstance().get(icon, Texture.class));
	   menuIconDown.setColor(Colors.CELL_A);
	   iconStyle.imageDown = new SpriteDrawable(menuIconDown);
		ImageButton iconButton = new ImageButton(iconStyle);
		if (listener != null) {
			iconButton.addListener(listener);
		}
		entries.add(entry);
		entry.add(iconButton).padLeft(PADDING);
		addActor(entry);
		entry.setVisible(false);
		entry.setPosition(-description.getPrefWidth() - PADDING, PADDING * 1.5f + entries.size() * (iconButton.getPrefHeight() + PADDING));
	}
}
