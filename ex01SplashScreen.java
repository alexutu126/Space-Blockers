//WMS3 2015

package com.cryotrax.game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.InputProcessor;

//----------------------------------------------------------------

public class ex01SplashScreen implements InputProcessor, Screen {
	private Texture splashTexture;
	private TextureRegion splashTextureRegion;
	private long startTime;
	private CryotraxGame cryo_game;
	private OrthographicCamera camera;
	private int screen_sizew;
	private int screen_sizeh;
	
	public ex01SplashScreen(
			CryotraxGame game,
			int screen_sizew,
			int screen_sizeh){
		//super(game);
		cryo_game = game;
		this.batch = new SpriteBatch();
		cryo_game.game_state = GameState.SPLASH_SCREEN;
		startTime = TimeUtils.millis();
        camera = new OrthographicCamera(25f, 800f / 480f * 25f);
		camera.position.set(15f, 15f, 0f);
		camera.update();
		this.screen_sizew = screen_sizew;
		this.screen_sizeh = screen_sizeh;
		if(cryo_game.screen_type==1) {
			splashTexture = cryo_game.assets.get(ex01Types.LOAD_SPLASH01big);
			//set the filtering
			splashTexture.setFilter(TextureFilter.Linear,
					TextureFilter.Linear);
			//get the region
			splashTextureRegion = new TextureRegion(splashTexture, 0, 0, 480, 800);
		} else if (cryo_game.screen_type==2) {
			splashTexture = cryo_game.assets.get(ex01Types.LOAD_SPLASH02medium);
			//set the filtering
			splashTexture.setFilter(TextureFilter.Linear,
					TextureFilter.Linear);
			//get the region
			splashTextureRegion = new TextureRegion(splashTexture, 0, 0, 480, 800);
		} else {
			splashTexture = cryo_game.assets.get(ex01Types.LOAD_SPLASH03small);
			//set the filtering
			splashTexture.setFilter(TextureFilter.Linear,
					TextureFilter.Linear);
			//get the region
			splashTextureRegion = new TextureRegion(splashTexture, 0, 0, 300, 500);
		}
	}
	
	@Override
	public void hide(){			
	}
	
	@Override
	public void show(){
	}
	
	@Override
	public void pause(){	
	}
	
	@Override
	public void resize(int width, int height){
	}

	@Override
	public void resume(){	
	}
	
	protected  SpriteBatch batch;
	
	@Override
	public void render(float delta){
	//	super.render(delta);
		batch.begin();
		batch.draw( splashTextureRegion, 0, 0, screen_sizew, screen_sizeh);
		batch.end();
		//used for FPS counting
		if (TimeUtils.millis() - startTime > 1000) /* 1,000,000,000ns == one second */{
			if(cryo_game.menu_screen==null){
				cryo_game.game_state = GameState.MENU_SCREEN;
				cryo_game.setScreen(new ex01MenuScreen(cryo_game.atlas_menu, cryo_game));
			} else {
				cryo_game.game_state = GameState.MENU_SCREEN;
				cryo_game.menu_screen.InitShaderTimers();
				cryo_game.setScreen(cryo_game.menu_screen);					
			}
		}
	}
	
	@Override
	public void dispose(){
	//	super.dispose();
		splashTexture.dispose();
	}

    @Override
    public boolean keyDown (int keycode) {
       return false;
    }

    @Override
    public boolean keyUp (int keycode) {
       return false;
    }

    @Override
    public boolean keyTyped (char character) {
       return false;
    }

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
       return false;
    }

    @Override
    public boolean mouseMoved (int x, int y) {
       return false;
    }

    @Override
    public boolean scrolled (int amount) {
       return false;
    }		
}
