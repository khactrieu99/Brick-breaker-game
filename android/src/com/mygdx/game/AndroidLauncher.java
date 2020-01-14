package com.mygdx.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.Screens.BrickBreaker;
import com.mygdx.game.Screens.MenuScreen;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new BrickBreaker(), config);
		initialize(new MyGame(), config);
		//initialize(new MenuScreen(), config);
	}
}
