//WMS3 2015

package com.cryotrax.game;
import java.util.Random;
import java.util.Timer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class ex01MenuLoadingScreen implements Screen {
	public static final float screen_hW = 800f / 480f * 25f;
	public float loadingCount = 0;
	public float loadingLoaderCount = 0.000f;
	public int level_int;
	public int type_scraft;
	public int ang1, ang2, ang3;
    public CryotraxGame cryo_game;
    public OrthographicCamera camera;
	public Sprite backs;
	public Sprite loadingSprite;
	public Sprite loadingTimer;
	public Animation loadingAnimation;	
	public SpriteBatch batch;
	public Random random = new Random();
	public String level_name;
	public ex01JSONSettingsLoader settings_game;
	public TextureRegion[] loader_regions = new TextureRegion[9];
	public Animation loader_animation;
	public Timer Creater = new Timer();
	public boolean loading_now = true;
	public boolean can_change_screen = false;
	public boolean started_procedure = false;
	public ex01MenuLoadingScreen this_menu_loader;
	public _ex01CryotraxGame new_game_screen;
	public Runnable r_write_json;
	public Thread t_write_json;
	public Runnable r_write_json2;
	public Thread t_write_json2;
	public static final float CAMERA_X = 15f;
	public static final float TIMER_DELTA_WX = 0.5f;
	public static final float LOADING_TEXT_WIDTH = 14f;
	public static final float LOADING_TEXT_HEIGHT =
			LOADING_TEXT_WIDTH * 39f/256f;
	public static final float LOADING_TIMER_W = LOADING_TEXT_HEIGHT; 
	public static final float LOADING_TIMER_H = LOADING_TEXT_HEIGHT; 
	public static final float LOADING_TEXT_X =
			CAMERA_X - LOADING_TEXT_WIDTH/2 +
			(LOADING_TIMER_W + TIMER_DELTA_WX)/2;
	public static final float LOADING_TEXT_Y =
			(0f - LOADING_TEXT_HEIGHT)/2f;
	public static final float LOADING_TIMER_X =
			LOADING_TEXT_X - LOADING_TIMER_W - TIMER_DELTA_WX;
	public static final float LOADING_TIMER_Y = LOADING_TEXT_Y;	
	
    public ex01MenuLoadingScreen(ex01JSONSettingsLoader settings,
								 final CryotraxGame game,
								 int level,
								 int type_spacecraft,
								 int angle1,
								 int angle2,
								 int angle3) {
    	this_menu_loader = this;
    	this.cryo_game = game;
    	settings_game = settings;
    	ang1 = angle1;
    	ang2 = angle2;
    	ang3 = angle3;
    	loadingSprite = new Sprite(cryo_game.atlas_menu.findRegion("load"));
    	loadingSprite.setSize(
				LOADING_TEXT_WIDTH,
				LOADING_TEXT_HEIGHT);
    	loadingSprite.setPosition(
				LOADING_TEXT_X,
				LOADING_TEXT_Y);
    	loadingTimer = new Sprite(cryo_game.atlas_loader.findRegion("loader01"));
    	loadingTimer.setSize(
				LOADING_TIMER_W,
				LOADING_TIMER_H);
    	loadingTimer.setPosition(
				LOADING_TIMER_X,
				LOADING_TIMER_Y);
    	type_scraft = type_spacecraft;
		batch = new SpriteBatch();
		camera = new OrthographicCamera(25f, screen_hW);	
		camera.position.set(15f, 0f, 0f);
		camera.update();
		if(level < 10) {
			level_name = "world_data0" + Integer.toString(level);
		} else {
			level_name = "world_data" + Integer.toString(level);
		}
		cryo_game.already_loaded_loader_once = true;
		cryo_game.loader = this;
		level_int = level;
		//animation separation stuff
		loader_regions[0] = cryo_game.atlas_loader.findRegion("loader01"); //0.000;
	    loader_regions[1] = cryo_game.atlas_loader.findRegion("loader02"); //0.025;
	    loader_regions[2] = cryo_game.atlas_loader.findRegion("loader03"); //0.050;
	    loader_regions[3] = cryo_game.atlas_loader.findRegion("loader04"); //0.075;
	    loader_regions[4] = cryo_game.atlas_loader.findRegion("loader05"); //0.100;
	    loader_regions[5] = cryo_game.atlas_loader.findRegion("loader06"); //0.125;
	    loader_regions[6] = cryo_game.atlas_loader.findRegion("loader07"); //0.150;
	    loader_regions[7] = cryo_game.atlas_loader.findRegion("loader08"); //0.175;
	    loader_regions[8] = cryo_game.atlas_loader.findRegion("loader09"); //0.200;
	    loader_animation = new Animation(0.025f, loader_regions);
	    loadingTimer.setRegion(loader_animation.getKeyFrame(loadingLoaderCount, false));
    }
    
    public void ex01MenuLoadingScreenReload(ex01JSONSettingsLoader settings,
											final CryotraxGame game,
											int level,
											int type_spacecraft,
											int angle1,
											int angle2,
											int angle3) {
    	loadingCount = 0;
    	this.cryo_game = game;
    	settings_game = settings;
    	ang1 = angle1;
    	ang2 = angle2;
    	ang3 = angle3;
    	type_scraft = type_spacecraft;
		camera.update();
		if(level < 10) {
			level_name = "world_data0" + Integer.toString(level);
		} else {
			level_name = "world_data" + Integer.toString(level);
		}
		cryo_game.already_loaded_loader_once = true;
		level_int = level;
    }   
    
    public boolean done = false;
    public int loadingStep = 0;
    public static final float ANIM_STEP = 0.025f;
    public static final float ANIM_BASE = 0.000f;
	
    @Override
    public void render(float delta) {
		UpdateLoadingScreen(delta);			
		RenderLoadingScreen(batch);
		if(done){
			cryo_game.levels_screen.still_working_play_press = false;
			try { Thread.sleep(250); } catch (InterruptedException e) { e.printStackTrace(); }
			loadingCount = 0; 
			loadingStep = 0;
			loadingLoaderCount = ANIM_BASE;
			if(cryo_game.menu_screen.settings.settings.sounds_on)
				cryo_game.menu_screen.enter_play_game.stop();
			cryo_game.setScreen(new_game_screen);
			done = false;
			cryo_game.game_state = GameState.GAME_SCREEN;
    	}	
	}

	public void UpdateLoadingScreen(float delta){
		loadingCount += delta;
		loadingTimer.setRegion(loader_animation.getKeyFrame(loadingLoaderCount, false));
		if(loadingCount>randFloat(0.2f, 0.5f, random) & delta < 0.02f){
			if (!cryo_game.already_loaded_game_once) {
		    	if(!done){
		    		switch(loadingStep){

		    			case 0: { new_game_screen = new _ex01CryotraxGame(
								this_menu_loader,
								settings_game,
								cryo_game,
								level_int,
								type_scraft,
								ang1,
								ang2,
								ang3);
						}
						break;

		    			case 1: { new_game_screen._ex01CryotraxGameSEC1();
						}
						break;

		    			case 2: { new_game_screen._ex01CryotraxGameSEC2(level_int, type_scraft);
						}
						break;

		    			case 3: { new_game_screen._ex01CryotraxGameSEC3();
						}
						break;

		    			case 4: { new_game_screen._ex01CryotraxGameSEC4(
								level_name,
								type_scraft,
								ang1,
								ang2,
								ang3);
						}
						break;

		    			case 5: { new_game_screen._ex01CryotraxGameSEC5(
								cryo_game,
								ang1,
								ang2,
								ang3);
						}
						break;

		    			case 6: { new_game_screen._ex01CryotraxGameSEC6(
								this_menu_loader,
								level_int);
						}
						break;

		    			case 7: { new_game_screen._ex01CryotraxGameSEC7();
						}
						break;

		    			case 8: { new_game_screen._ex01CryotraxGameSEC8(); done = true;
						}
						break;
		    		}    
		    		loadingStep++; loadingLoaderCount = loadingStep * ANIM_STEP;
		    	} 
			} else {
		    	if(!done){
		    		switch(loadingStep){

		    			case 0: { try { cryo_game.game_screen._ex01CryotraxGameReload0();
						} catch (Throwable e) { e.printStackTrace(); } }
						break;

		    			case 1: { try { cryo_game.game_screen._ex01CryotraxGameReload1(
								level_name,
								level_int,
								type_scraft); } catch (Throwable e) { e.printStackTrace(); }
						}
						break;

		    			case 2: { try { cryo_game.game_screen._ex01CryotraxGameReload2(
								type_scraft,
								ang1,
								ang2,
								ang3); } catch (Throwable e) { e.printStackTrace(); }
						}
						break;

		    			case 3: { try { cryo_game.game_screen._ex01CryotraxGameReload3();
						} catch (Throwable e) { e.printStackTrace(); }
						}
						break;

		    			case 4: { try { cryo_game.game_screen._ex01CryotraxGameReload4(
								level_int,
								type_scraft); } catch (Throwable e) { e.printStackTrace(); }
						}
						break;

		    			case 5: { try { cryo_game.game_screen._ex01CryotraxGameReload5(
								level_int,
								type_scraft); } catch (Throwable e) { e.printStackTrace(); }
						}
						break;

		    			case 6: { try { cryo_game.game_screen._ex01CryotraxGameReload6();
							} catch (Throwable e) { e.printStackTrace(); }
						}
						break;

		    			case 7: { try { cryo_game.game_screen._ex01CryotraxGameReload7(level_int);
							} catch (Throwable e) { e.printStackTrace(); }
						}
						break;

		    			case 8: { try { cryo_game.game_screen._ex01CryotraxGameReload8();
							done = true; } catch (Throwable e) { e.printStackTrace(); }
						}
						break;
		    		}    
		    		loadingStep++; loadingLoaderCount = loadingStep * ANIM_STEP;
		    	}				
			}
		}
	}
	
	// generates random integer number between two integers 
	public static float randFloat(float min, float max, Random rand) {
	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
		float randomNum = rand.nextFloat() * (max - min) + 1f + min;
		//float finalX = rand.nextFloat() * (maxX - minX) + minX;
	    return randomNum;
	}		
	
	public void RenderLoadingScreen(SpriteBatch batch){
		Gdx.gl.glClearColor(0, 0, 0, 1);	      //make the background clear COLOR black	
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the COLOR with black		
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		batch.begin();    
		batch.setProjectionMatrix(camera.combined);
		camera.update();
		loadingSprite.draw(batch);
		loadingTimer.draw(batch);
		batch.end();
	}
    
	@Override
	public void pause(){
	}
	
	@Override
	public void resize(int width, int height) {

	}
	
	@Override
	public void hide(){
	}
	
	@Override
	public void resume(){
	}
	
	@Override
	public void dispose(){
		Dispose();
	}
	
	@Override
	public void show(){
	}

	public void Dispose(){
		cryo_game = null;
		camera = null;
		backs = null;
		loadingSprite = null;
		loadingTimer = null;
		loadingAnimation = null;
		if(batch!=null){
			batch.flush();
			batch.dispose();
			batch = null;
		}
		random = null;
		level_name = null;
		settings_game = null;
		for(int i=0; i<loader_regions.length; i++){
			loader_regions[i] = null;
		}
		loader_animation = null;
		if(Creater!=null){
			Creater.cancel();
			Creater = null;
		}
		new_game_screen = null;
		this_menu_loader = this;
		r_write_json = null;
		t_write_json = null;
		r_write_json2 = null;
		t_write_json2 = null;
	}
}