//WMS3 2015

package com.cryotrax.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import java.util.Timer;


public class CryotraxGame extends Game{
	public static final int SIZE_S3 = 1280;
	public static final int SIZE_i6102D = 640;
	public static final int VIRTUAL_SCREEN_WIDTH = 480;
	public static final int VIRTUAL_SCREEN_HEIGHT = 800;
	public static final float width =
			VIRTUAL_SCREEN_WIDTH/1.125f;
	public static final float height =
			VIRTUAL_SCREEN_WIDTH/1.125f * 278f/430f;
	public static final float screen_wW = 25f;
	public static final float screen_hW = 800f / 480f * 25f;
	public static final float BG_CENTER_CAMERA_X = 15f;
	public static final float BG_CENTER_CAMERA_Y = 15f;
	public int screen_sizew;
	public int screen_sizeh;
	public boolean already_loaded_game_once = false;
	public boolean already_loaded_loader_once = false;
	public TextureAtlas atlas_menu;
	public TextureAtlas atlas_loader;
	public AssetManager assets;
	public GameState game_state;
	public ex01MenuScreen menu_screen;
	public ex01MenuLevelSelector levels_screen;
	public ex01MenuLoadingScreen loader;
	public ex01LevelCredentials level_credentials;
	public _ex01CryotraxGame game_screen;
	public ExitError exit_error;
	public AGoogleFacebookServices google_facebook_services;
	public IabInterface inappInterface;
	public IActionResolver inappInterfaceResolver;
	public AGetPremiumWindow premium_interface;
	public SpriteBatch batch;
	public SpriteCache batchCache;
	// camera used for background image (the nebula) - the ACCEL and LATERAL HUD and the
	// shot and health HUD
	public OrthographicCamera bg_camera;
	public int screen_type = 1; //big

	public CryotraxGame(ExitError errors,
						IabInterface inapp,
						com.cryotrax.game.IActionResolver inapp_resolver,
						AGoogleFacebookServices services,
						AGetPremiumWindow premium){
		if(errors !=null){
			this.exit_error = errors;
		} else {
			this.exit_error = new DummyExitError();
		}

		if(inapp != null){
			this.inappInterface = inapp;
		} else {
			this.inappInterface = new DummyInappController();
		}

		if(inapp_resolver != null){
			this.inappInterfaceResolver = inapp_resolver;
		} else {
			this.inappInterfaceResolver = new DummyInappResolverController();
		}

		if(services != null){
			this.google_facebook_services = services;
		} else {
			this.google_facebook_services = new DummyGoogleFacebookServices();
		}

		if(premium!=null){
			this.premium_interface = premium;
		} else {
			this.premium_interface = new DummyPremium();
		}
	}

	public void InitRateAppAppear(){
		// Increment launch counter
		menu_screen.settings.settings.launch_count +=1;
		// Get date of first launch
		if (menu_screen.settings.settings.date_firstLaunch == 0) {
			menu_screen.settings.settings.date_firstLaunch = System.currentTimeMillis();
		}
		boolean exceedsSpecifiedLaunches = menu_screen.settings.settings.launch_count >=
				menu_screen.settings.settings.LAUNCHES_UNTIL_PROMPT;
		boolean exceedsDaysSinceFirstLaunch = System.currentTimeMillis() >=
				menu_screen.settings.settings.date_firstLaunch +
						(menu_screen.settings.settings.DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000);

		// Wait until specified number of launches or until specified number of days have passed
		if ((exceedsSpecifiedLaunches || exceedsDaysSinceFirstLaunch)
				&& !menu_screen.settings.settings.DAYS_AND_LAUNCHES) {
			google_facebook_services.showRateDialog(menu_screen);
		}
		else if (exceedsSpecifiedLaunches && exceedsDaysSinceFirstLaunch
				&& menu_screen.settings.settings.DAYS_AND_LAUNCHES) {
			google_facebook_services.showRateDialog(menu_screen);
		}
	}

	public void SAVE_GAME_TO_CLOUD(){
	}

	public ex01SplashScreen getSplashScreen(){
		return new ex01SplashScreen(this, screen_sizew, screen_sizeh);
	}

	@Override
	public void create(){
		game_state = GameState.SPLASH_SCREEN;
		screen_sizew = Gdx.graphics.getWidth();
		screen_sizeh = Gdx.graphics.getHeight();
		screen_type = CheckScreenSize();
		assets = new AssetManager();
		atlas_menu = new TextureAtlas();
		atlas_loader = new TextureAtlas();

		if(screen_type==1) {
			assets.load(ex01Types.LOAD_SPRITE_MENU01big, TextureAtlas.class);
			assets.load(ex01Types.LOAD_SPRITE_ALOADER01big, TextureAtlas.class);
			assets.load(ex01Types.LOAD_SPLASH01big, Texture.class);
			assets.finishLoading();
			atlas_menu = assets.get(ex01Types.LOAD_SPRITE_MENU01big);
			atlas_loader = assets.get(ex01Types.LOAD_SPRITE_ALOADER01big);
		} else if(screen_type==2) {
			assets.load(ex01Types.LOAD_SPRITE_MENU02medium, TextureAtlas.class);
			assets.load(ex01Types.LOAD_SPRITE_ALOADER02medium, TextureAtlas.class);
			assets.load(ex01Types.LOAD_SPLASH02medium, Texture.class);
			assets.finishLoading();
			atlas_menu = assets.get(ex01Types.LOAD_SPRITE_MENU02medium);
			atlas_loader = assets.get(ex01Types.LOAD_SPRITE_ALOADER02medium);
		} else if(screen_type==3){
			assets.load(ex01Types.LOAD_SPRITE_MENU03small, TextureAtlas.class);
			assets.load(ex01Types.LOAD_SPRITE_ALOADER03small, TextureAtlas.class);
			assets.load(ex01Types.LOAD_SPLASH03small, Texture.class);
			assets.finishLoading();
			atlas_menu = assets.get(ex01Types.LOAD_SPRITE_MENU03small);
			atlas_loader = assets.get(ex01Types.LOAD_SPRITE_ALOADER03small);
		}

		bg_camera = new OrthographicCamera(
				screen_wW,
				screen_hW);
		bg_camera.position.set(
				BG_CENTER_CAMERA_X,
				BG_CENTER_CAMERA_Y,
				0f);
		bg_camera.zoom = 1f;
		bg_camera.update();
		setScreen (getSplashScreen());
		level_credentials = new ex01LevelCredentials();
	}

	public int CheckScreenSize(){
		if(screen_sizeh>SIZE_S3)
		{   return 1;
		} else if (screen_sizeh>SIZE_i6102D && screen_sizeh<=SIZE_S3){
			return 2;
		} else {
			return 3;
		}
	}

	@Override
	public void render(){
		super.render();
	}

	@Override
	public void pause(){
		switch(game_state){
			case SPLASH_SCREEN:
			{
			}
			break;
			case MENU_SCREEN:
			{
			}
			break;
			case LEVELS_SCREEN:
			{
			}
			break;
			case GAME_SCREEN:
			{
				game_screen.PauseGameScreen();
			}
			break;
			case GAME_LOADING_SCREEN:
			{
			}
			break;
			default:
				break;
		}
	}

	@Override
	public void resume(){
		switch(game_state){
			case SPLASH_SCREEN:
			{
			}
			break;
			case MENU_SCREEN:
			{
			}
			break;
			case LEVELS_SCREEN:
			{
			}
			break;
			case GAME_SCREEN:
			{
			}
			break;
			default:
				break;
		}
	}

	@Override
	public void dispose(){
		if(game_screen!=null) {
			game_screen.dispose();
			game_screen = null;
		}
		if(menu_screen!=null) {
			menu_screen.Dispose();
			menu_screen = null;
		}
		if(levels_screen!=null){
			levels_screen.Dispose();
			levels_screen = null;
		}
		if(loader!=null){
			loader.Dispose();
			loader = null;
		}
		level_credentials = null;
		if(atlas_menu!=null){
			atlas_menu.dispose();
			atlas_menu = null;
		}
		if(atlas_loader!=null){
			atlas_loader.dispose();
			atlas_loader = null;
		}
		if(batch!=null){
			batch.flush();
			batch.dispose();
			batch = null;
		}
		if(batchCache!=null){
			batchCache.dispose();
			batchCache = null;
		}

		game_state = null;
		exit_error = null;
		google_facebook_services = null;
		inappInterface = null;
		bg_camera = null;

		if(assets!=null){
			assets.clear();
			assets.dispose();
			assets = null;
		}
		super.dispose();
	}
}