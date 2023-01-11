//WMS3 2015

package com.cryotrax.game;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputAdapter;

public abstract class GdxSceneBase extends InputAdapter implements ApplicationListener {
	protected static GdxPlatformResolver m_platformResolver = null;

	public static GdxPlatformResolver getPlatformResolver() {
		return m_platformResolver;
	}

	public static void setPlatformResolver(GdxPlatformResolver platformResolver) {
		m_platformResolver = platformResolver;
	}

	public void create () {
	}

	public void resume () {
	}

	public void render () {
	}

	public void resize (int width, int height) {
	}

	public void pause () {
	}

	public void dispose () {
	}
}
