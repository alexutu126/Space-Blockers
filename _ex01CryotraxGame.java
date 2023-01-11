//WMS3 2015

package com.cryotrax.game;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class _ex01CryotraxGame implements InputProcessor, Screen  {
	public final CryotraxGame cryo_game;
	public static final int screen_sizew =
			Gdx.graphics.getWidth();
	public static final int screen_sizeh =
			Gdx.graphics.getHeight();
	public static final int VIRTUAL_SCREEN_WIDTH = 480;
	public static final int VIRTUAL_SCREEN_HEIGHT = 800;
	public static final float STOPPER_CAMERA = 25.250f;
	public static final float STOPPER_CAMERA_B = 25.250f;
	public static final float Fscreen_sizew2 = screen_sizew/2;
	public static final float Fscreen_sizeh2 = screen_sizeh/2;
    public static final float screen_wW = 25f;
    public static final float screen_hW = 800f / 480f * 25f;
    public static final float screen_hW2 = 800f / 480f * 25f / 2f;
    public static final float CENTER_CAMERA_X = 15f;
    public static final float CENTER_CAMERA_Y = -10f;	
    public static final float BG_CENTER_CAMERA_X = 15f;
	public static final float BG_CENTER_CAMERA_Y = 15f;
	public static final float DISPLAY_FUD_CAMERA_X = 15f;
	public static final float DISPLAY_FUD_CAMERA_Y = 15f;
	public static final float BACK_POS_X = BG_CENTER_CAMERA_X - 12.5f;
	public static final float BACK_POS_Y = 15f - screen_hW2;
	public static final float resizer =
			((float)VIRTUAL_SCREEN_WIDTH/(float)VIRTUAL_SCREEN_HEIGHT) /
			((float)screen_sizew/(float)screen_sizeh);
	public static final float BACK_POS_W = 25f;
	public static final float BACK_POS_H = screen_hW;
	public static final float SPCY3 = 9.4f;
	public static final float SPCY2 = 7.4f;
	public static final float SPCY1 = 5.4f;
	public static final float SPCDEST3 = 22.4f;
	public static final float SPCDEST2 = 19.4f;
	public static final float SPCDEST1 = 16.4f;
	public static final float SPCA3 = 0f;
	public static final float SPCA2 = 0f;
	public static final float SPCA1 = 0f;
	// how much do we advance when we acceleration by touching acceleration arrow screen
	public static final float DELTA_ADVANCE_Y = 6.45f;
	public static final float LSSIZE_WH = 14f;
	public static final float LSSIZE_HE = 2.1328125f;
	public static final float LSPOS_X = 8f;
	public static final float LSPOS_Y = 15f-1.06640625f;
	public static final float BARRIER_LIMIT = 0.001f;
	public static final float BARRIER_DIVIDER = 75f;
	public static final float BARRIER_DIVIDER_B = 125f;
	public static final float SPACESHIP_WIDTH_X = 3.95f;
	public static final float SPACESHIP_WIDTH_Xp2 = SPACESHIP_WIDTH_X / 2f;
	public static final float INFLATER_DENOMINATOR = 0.475f/40f;
	public static final float INFLATER_DENOMINATOR2 = 0.475f/40f;
	public static final float INFLATER_ZOOM2 = 0.00125f ;
	public static final float DELTA_LR_YER_EXPANDER = 5f;
	public static final float EXPAND_SHOOT_RECT_DELTA = 2f;
	public static final float ETRAP_DRAWING_FILTER_DELTA = 5f;
	public static final float DELTA_ETRAP = 35f;
	public float speed_decelerate = 0f;
	public float zoom_current_spaceship = 1f;
	public float delta_xer = 1f;
	public float spaces_world_velocity;
	public float spaces_world_velocity_original;
	public float spaces_world_velocity_original_shoot;
	public float delta_64_srsz;
	public float velo;
	public float velo_original;
	public float velo_original_shoot;
	public float grayness = 0.0f;
	public float grayness_delta = 0.6f;
	public float delta_etrap;
	public float finish_line_y_coordinate = 220f;
	public float finish_camera_translator_inflater = 1f;
	public int min_etrap_i = 0;
	public int max_etrap_i = 0;
	public int counter = 0;
	public int etrap_size;
	public int angle11, angle22, angle33;
	public int type_spacecraft_last_loaded;
	public int last_level_int;
    public int no_background;
    public int type_spaceship;
	public int temp;
	public long startTime;
	public long start;
	public long end;
	public long last_accesed;
	public long accessed_now;
	public long counter_add = 0;
	public boolean touch_dragged_now = false;
	public boolean touch_downed_now = false;
	public boolean finger_is_on_spaceship = false;
	// used in render() for MainMenuButton click to return to main menu otherwise crash
	public boolean is_still_in_game = true;
	public boolean returner;
	public boolean grayness_increase_more = false;
	public boolean grayness_increase_more_reversed = false;
	public boolean grayness_increase_more_enough = false;
	public boolean added_admob_once = false;
	// used to add multiplexer only when we came from menu not in-game replay menu
	public boolean came_from_within_game = false;
	public boolean starter_screen_firster = true;
	public boolean StartTimePassed = false;
	public boolean StartTimePassed_FirstTime = true; // used to load the assets - just starting
	// set to TRUE in PauseGameScreen (not explode, not finish, not out of bullets, just pause
	public boolean reset_or_pause_level = false;
	// used in render( in else ; made true if << if(StartTimePassed && !paused_game_screen) >>
	public boolean just_rendered_reset = false;
	// used in render( in else ; made true if << if(StartTimePassed &&  paused_game_screen) >>
	public boolean just_rendered_pause = false;
	// used in PauseGameScreen(for access once) - and render(to update some elements)
	public boolean paused_game_screen = false;
	// hud_display.AddEventListeners for the resume/restart button only once
	public boolean already_added_replay = false;
	// is practically spaces.can_explode
	public boolean spaces_can_explode = false;
	// at finish (bullets, or finish level) we need to change texture so we zoom with no artifacts
	public boolean set_big_spaceship_already = false;
	// used when we finish the level or run out of bullets - in PauseGameScreen
	public boolean finished_the_job = false;
	// used in render > if(!spaces.can_explode){  for not repeat (CheckNoMoreBulletsFinishBarrier
	public boolean finished_the_job_norepeat = false;
	// JSON need to be written once only in  -  render(float delta)
	public boolean wrote_to_json = false;
	public BitmapFont hud_angler_font_bitmap = new BitmapFont();
	public BitmapFont hud_angler_fontp_bitmap = new BitmapFont();
	public BitmapFont hud_angler_font_title_bitmap = new BitmapFont();
	public ShapeRenderer shaper;
	public String data;
	public String pathto_screencap = "";
	public Skin skin;
    public Skin skin_main;
    public Skin skin_secondary;
	public Sprite backs;
	public Sprite loadingSprite;
	public SpriteBatch batch;
	public SpriteBatch batchHUD;
	public SpriteCache batchCache;
	public AssetManager assets;
	public InputMultiplexer inputMultiplexer = new InputMultiplexer();
	public Texture back;
	public TextureAtlas textureAtlas;
    public TextureAtlas atlas_main;
    public TextureAtlas atlas_secondary;
	public Viewport viewport;
	public Runnable r_write_json;
	public Runnable r_kill_spaceship_sound;
	public Thread t_write_json;
	public Thread t_kill_spaceship_sound;
	public MyElectrotrapReseter etrap_reseter;
	public ex01MenuLoadingScreen mloader;
	public ex01ElectrotrapRedList etrap_list;
	public ex01ElectrotrapRedList world_electrotrap_list;
	public ex01ReadWorldData world_data;
	public ex01CryoshipPrincipial spaces;
	public ex01JSONSettingsLoader settings_for_game;
	public ex01PowerupList world_powerup_list;
	public ex01CryoHUD hud;
	public ex01CryoAsteroidList asteroids;
	public ex01Stars stars;
	public ex01CryoHUDDisplay hud_display;
	public OrthographicCamera camera;
	public OrthographicCamera bg_camera;
	public OrthographicCamera bg_camera_stars;
	public OrthographicCamera display_hud_camera;
	public OrthographicCamera display_hud_camera_table;
	public OrthographicCamera display_fud_camera;
	public OrthographicCamera display_fud_camera_text;
	public ShaderProgram shader_finish;
	public ShaderProgram shader_finish_sepia_pause;
	public ShaderProgram shader_grayscale;
	public Timer ExplodeTimerTask = new Timer();
	public File tmpFile;
	public Sprite planet;
	public static final float PLANET_DEFLATOR = 0.65f;
	public static final float PLANET_POY = 25f;
	public static final float PLANET_SPEED_FPSDEP = 0.25f;
	public float planet_poY = PLANET_POY;
	public float planet_poX;
	public Random r = new Random();
	public static final float PLANET_REPOS_X_DELTA = 3f;
	public static final float PLANET_REPOS_X_DELTA2 = 12.5f + 15.0f - 0.5f
			- BACK_POS_W*PLANET_DEFLATOR;
	public Vector3 lastTouch = new Vector3();
	public Vector3 newTouch = new Vector3();
	public float xdiff;
	public static final float LIMIT_X_LEFT = 5f;
	public static final float LIMIT_X_RIGHT = 25f;
	public float percenter_from_center;

	public _ex01CryotraxGame(final ex01MenuLoadingScreen menu_load, 
							 ex01JSONSettingsLoader settings, 
							 final CryotraxGame game,
							 int level_int, 
							 int type_spacecraft, 
							 int angle1,
							 int angle2, 
							 int angle3) {
		cryo_game = game;
		cryo_game.game_screen = this;
		mloader = menu_load;
		settings_for_game = settings;
		angle11 = angle1;
		angle22 = angle2;
		angle33 = angle3;
		type_spaceship = type_spacecraft;
		type_spacecraft_last_loaded = type_spacecraft;
		last_level_int = level_int;
	}
	
	public void _ex01CryotraxGameSEC1() {
		batch = new SpriteBatch();
		batchHUD = new SpriteBatch();
		batchCache = new SpriteCache(1000,false);
		shaper = new ShapeRenderer();
		InitCameras();
		shader_finish = new ShaderProgram(
				Gdx.files.internal("data/shaders/sepia.vert"),
				Gdx.files.internal("data/shaders/sepia.frag"));
		shader_grayscale = new ShaderProgram(
				Gdx.files.internal("data/shaders/grayscale2.vert"),
				Gdx.files.internal("data/shaders/grayscale2.frag"));
		shader_finish_sepia_pause = new ShaderProgram(
				Gdx.files.internal("data/shaders/sepia2.vert"),
				Gdx.files.internal("data/shaders/sepia2.frag"));
		assets = new AssetManager();
	}
	
	public void _ex01CryotraxGameSEC2(final int level_int,
									  final int type_spacecraft) {
		if(cryo_game.screen_type==1) {
			LoadAllTexturesPrimary_01big(type_spacecraft, level_int);
		} else if(cryo_game.screen_type==2){
			LoadAllTexturesPrimary_01medium(type_spacecraft, level_int);
		} else {
			LoadAllTexturesPrimary_01small(type_spacecraft, level_int);
		}
	}
	
	public void _ex01CryotraxGameSEC3() {
		if(cryo_game.screen_type==1) {
			LoadAllTexturesSecondary_01big();
		} else if(cryo_game.screen_type==2){
			LoadAllTexturesSecondary_01medium();
		} else {
			LoadAllTexturesSecondary_01small();
		}
	}
	
	public void _ex01CryotraxGameSEC4(final String level_name,
									  final int type_spacecraft, 
									  final int angle1, 
									  final int angle2, 
									  final int angle3) {

		spaces = new ex01CryoshipPrincipial(
				cryo_game.game_screen,
				DELTA_ADVANCE_Y,
				type_spacecraft);
		spaces.textureAtlas = atlas_main;
		spaces.textureAtlasSec = atlas_secondary;
		spaces.init(
				camera.position.x,
				camera.viewportWidth);
		spaces.SpaceshipFirstInit();
		world_data = new ex01ReadWorldData("data\\wordfinal\\" + level_name);

		world_electrotrap_list = new ex01ElectrotrapRedList(
				spaces.textureAtlas,
				world_data.list_electrotrp,
				world_data.list_electrotrp_elements,
				world_data.list_electrotrp_elements_type,
				angle1,
				angle2,
				angle3);

		etrap_reseter = new
				MyElectrotrapReseter(world_electrotrap_list);
		etrap_size =
				world_electrotrap_list.electro_red_list.size();
		finish_line_y_coordinate = world_electrotrap_list.electro_red_list
				.get(world_electrotrap_list.electro_red_list.size() - 1).getY();
		world_powerup_list = new ex01PowerupList(atlas_secondary);
		inputMultiplexer.addProcessor(cryo_game.game_screen);
		/* each 16 level will have a different BKG so a different TextureAtlas will be needed */
		backs = new Sprite(atlas_main.findRegion("back"));
		planet = new Sprite(atlas_main.findRegion("planet778blur"));
		backs.setPosition(
				BACK_POS_X,
				BACK_POS_Y);
		backs.setSize(
				BACK_POS_W,
				BACK_POS_H);
		planet.setSize(
				BACK_POS_W*PLANET_DEFLATOR*resizer,
				BACK_POS_W*PLANET_DEFLATOR);
		planet_poX = randInt(
				(int) Math.floor((double)PLANET_REPOS_X_DELTA),
				(int) Math.ceil( (double)PLANET_REPOS_X_DELTA2), r);
		planet.setPosition(
				planet_poX,
				PLANET_POY);

		data = new String("null");			// string used for FPS displaying
		startTime = TimeUtils.nanoTime(); 	// timer used for FPS displaying
		StartTimePassed = false;			// delta < 0.02f (50FPS)
	}

	// generates random integer number between two integers
	public static int randInt(int min, int max, Random rand) {
		// NOTE: Usually this should be a field rather than a method
		// variable so that it is not re-seeded every call.

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public void _ex01CryotraxGameSEC5(final CryotraxGame game,
									  final int angle1, 
									  final int angle2, 
									  final int angle3) {
		viewport = new StretchViewport(VIRTUAL_SCREEN_WIDTH, 
									   VIRTUAL_SCREEN_HEIGHT, 
									   display_hud_camera_table);
		hud = new ex01CryoHUD(spaces, 
							  atlas_main, 
							  SPCY3, 
							  SPCY2, 
							  SPCY1, 
							  SPCDEST3, 
							  SPCDEST2, 
							  SPCDEST1, 
							  SPCA3, 
							  SPCA2, 
							  SPCA1,
							  camera);
		world_electrotrap_list.hud = this.hud;
		world_electrotrap_list.camera_hud = display_hud_camera;  
		world_electrotrap_list.game_screen = this;
		hud_display = new ex01CryoHUDDisplay(world_electrotrap_list.electro_red_list.size(), 
											 settings_for_game, 
											 atlas_main, 
											 cryo_game.game_screen, 
											 game, 
											 skin_main, 
											 viewport, 
											 camera, 
											 angle1, angle2, angle3);
		ArrayList<String> start_counter = new ArrayList<String>();// string array "3", "2", "1", "Go"
		start_counter.add("3");
		start_counter.add("2");
		start_counter.add("1");
		start_counter.add("Go");
		hud_display.InitDisplay(start_counter);

		stars = new ex01Stars(atlas_main);
		asteroids = new ex01CryoAsteroidList(0f, world_electrotrap_list.electro_red_list
		   .get(world_electrotrap_list.electro_red_list.size() - 1).getY(),
				atlas_main);

		loadingSprite = new Sprite(cryo_game.atlas_menu.findRegion("load"));
		loadingSprite.setSize(
				LSSIZE_WH,
				LSSIZE_HE);
		loadingSprite.setPosition(
				LSPOS_X,
				LSPOS_Y);
	}
	
	public void _ex01CryotraxGameSEC6(final ex01MenuLoadingScreen menu_load,
									  final int level_int) {

		hud.slider_bounding_rectangle = hud.slider.getBoundingRectangle();
		hud.rectangle_slider_width = hud.slider_bounding_rectangle.width;
		hud.slider_bounding_rectangle.x -= 1f;
		hud.slider_bounding_rectangle.y -= 2.5f;
		hud.slider_bounding_rectangle.width += 2f;
		hud.slider_bounding_rectangle.height += 3f;

		spaces_world_velocity =
				spaces.world_velocity;
		spaces_world_velocity_original =
				spaces.world_velocity_original;
		spaces_world_velocity_original_shoot =
				spaces.world_velocity_original_shoot;

		hud.spaceship_bounding_rectangle_shoot_hud =
				hud.HUDshootOverlaySmallSprite.getBoundingRectangle();
		hud.spaceship_bounding_rectangle_shoot_hud.x +=
				EXPAND_SHOOT_RECT_DELTA/20;
		hud.spaceship_bounding_rectangle_shoot_hud.y +=
				EXPAND_SHOOT_RECT_DELTA/20;
		hud.spaceship_bounding_rectangle_shoot_hud.width -=
				EXPAND_SHOOT_RECT_DELTA/10;
		hud.spaceship_bounding_rectangle_shoot_hud.height -=
				EXPAND_SHOOT_RECT_DELTA/10;

		hud.spaceship_bounding_rectangle_lefter =
				hud.HUD_ArrowLEFT.getBoundingRectangle();
		hud.spaceship_bounding_rectangle_lefter.y -=
				DELTA_LR_YER_EXPANDER;
		hud.spaceship_bounding_rectangle_lefter.height +=
				DELTA_LR_YER_EXPANDER;
		hud.spaceship_bounding_rectangle_righter =
				hud.HUD_ArrowRIGHT.getBoundingRectangle();
		hud.spaceship_bounding_rectangle_righter.y -=
				DELTA_LR_YER_EXPANDER;
		hud.spaceship_bounding_rectangle_righter.height +=
				DELTA_LR_YER_EXPANDER;
		hud.spaceship_bounding_rectangle_upper =
				hud.HUD_ArrowUP.getBoundingRectangle();

		world_electrotrap_list.LoadSpaceshipData(spaces);
		atlas_main.getTextures().first().bind();
		atlas_secondary.getTextures().first().bind();

		/* delta_etrap used for optimised code - works  better on levels with much more e-traps */
		if(world_electrotrap_list.electro_red_list.get(1).getY() ==
		   world_electrotrap_list.electro_red_list.get(0).getY())
		{
			delta_etrap = (world_electrotrap_list.electro_red_list.get(2).getY() -
						   world_electrotrap_list.electro_red_list.get(0).getY());
		} else {
			delta_etrap = (world_electrotrap_list.electro_red_list.get(1).getY() -
						   world_electrotrap_list.electro_red_list.get(0).getY()); }

		delta_etrap = DELTA_ETRAP;

		hud_display.level_no = level_int;
		hud_display.primary_level_no = level_int;
		hud_display.HudLevelInitIntString();
		hud_display.HudLevelReplayResume();
		// used in the show() method to start sounds for the first time
		starter_screen_firster = true;
		// used in the loader
		menu_load.can_change_screen = true;
	}
	
	public void _ex01CryotraxGameSEC7() {
		cryo_game.already_loaded_game_once = true;
		hud_display.LoadAmmoLifePostProcess();
	}
	
	public void _ex01CryotraxGameSEC8() {
		hud_display.InitCheckBoxesStars();
		added_admob_once = false;
	}

    public void _ex01CryotraxGameReload0() throws Throwable {

    }
    
    public void _ex01CryotraxGameReload1(final String level_name,
    									 final int level_int, 
    									 final int type_spacecraft) throws Throwable {

    	world_data.ex01ReadWorldDataReload("data\\wordfinal\\" + level_name);
		if(type_spacecraft_last_loaded==type_spacecraft)
		{
			if(last_level_int!=level_int)
			{
				if(!AreFromSameDepartment(last_level_int, level_int)){
					CompletelyReloadTexturesEtrapAsteroids(
							type_spacecraft,
							level_int); }
				else {
					SuperficiallyReloadTexturesEtrapAsteroids(
							type_spacecraft,
							level_int);
				}
			} else {
				SuperficiallyReloadTexturesEtrapAsteroids(
						type_spacecraft,
						level_int);
			}
		} else {
			CompletelyReloadTexturesEtrapAsteroids(
					type_spacecraft,
					level_int);
		}

		spaces.textureAtlas.getTextures().first().bind(); 
		spaces.textureAtlasSec.getTextures().first().bind();   	
    }
    
    public void _ex01CryotraxGameReload2(final int type_spacecraft,
    									 final int angle1, 
    									 final int angle2, 
    									 final int angle3) throws Throwable {
		// reset stars texture binds       *** RELOAD TEXTURES ***
		stars.ex01StarsReload(atlas_main);
		// reset background texture binds  *** RELOAD TEXTURES ***
    	backs.setRegion(atlas_main.findRegion("back"));
		// reset spaceship texture binds   *** RELOAD TEXTURES ***
    	spaces.ResetSpaceship();
    	spaces.type_spaceship = type_spacecraft;
    	spaces.initReload(camera.position.x, camera.viewportWidth);
		// reset pups textures and data
		world_powerup_list.ex01PowerupListReload(atlas_secondary);
		hud.ex01CryoHUDReload(
				spaces,
				atlas_main,
				SPCY3,
				SPCY2,
				SPCY1,
				SPCDEST3,
				SPCDEST2,
				SPCDEST1,
				SPCA3,
				SPCA2,
				SPCA1);

		hud_display.ex01CryoHUDDisplayReload(world_electrotrap_list.electro_red_list.size(),
				settings_for_game,
				atlas_main,
				cryo_game.game_screen,
				skin_main,
				viewport,
				angle1,
				angle2,
				angle3);
		// *** re-add touch control ***
		if(!came_from_within_game) {
			inputMultiplexer.addProcessor(cryo_game.game_screen);
		}
    }
    
    public void _ex01CryotraxGameReload3() throws Throwable {
		ResetProcedureAfterReset();   
		ResetProcedureAfterReplay();
		// *** CUT SHADERS ***
		MakeGreyShadersNoActive();

		hud_display.ex01CryoHUDDisplayReloadData(
				world_electrotrap_list.electro_red_list.size(),
				settings_for_game,
				cryo_game.game_screen,
				skin_main);

		finish_line_y_coordinate = world_electrotrap_list.electro_red_list
				.get(world_electrotrap_list.electro_red_list.size() - 1).getY();
    }
    
    public void _ex01CryotraxGameReload4(final int level_int,
    									 final int type_spacecraft) throws Throwable {
		if(type_spacecraft_last_loaded==type_spacecraft)
		{
			if(last_level_int!=level_int)
			{
				if(!AreFromSameDepartment(last_level_int, level_int))
				{
					CompletelyReloadTexturesEtrapAsteroidsB(); }
				else {
					SuperficiallyReloadTexturesEtrapAsteroidsB();
				}
			} else {
				SuperficiallyReloadTexturesEtrapAsteroidsB();
			}
		} else {
			CompletelyReloadTexturesEtrapAsteroidsB();
		}
    	type_spacecraft_last_loaded = cryo_game.game_screen.type_spaceship;   	
    }
    
    public void _ex01CryotraxGameReload5(final int level_int,
    									 final int type_spacecraft) throws Throwable {
		// timer used for FPS displaying
    	startTime = TimeUtils.nanoTime();
		// delta < 0.02f (50FPS)
    	StartTimePassed = false;
		// we need to know which spaceship we load when we zoom at finish
		cryo_game.game_screen.type_spaceship = type_spacecraft;
		cryo_game.game_screen.type_spacecraft_last_loaded = type_spacecraft;    	
		last_level_int = level_int;		
    }
    
    public void _ex01CryotraxGameReload6() throws Throwable {
    	/* delta_etrap used for optimised code - works  better on levels with much more e-traps */
		if(world_electrotrap_list.electro_red_list.get(1).getY() ==
		   world_electrotrap_list.electro_red_list.get(0).getY())
		{ 
			delta_etrap = (world_electrotrap_list.electro_red_list.get(2).getY() -
					 	   world_electrotrap_list.electro_red_list.get(0).getY());
		} else { 
			delta_etrap = (world_electrotrap_list.electro_red_list.get(1).getY() -
						   world_electrotrap_list.electro_red_list.get(0).getY());
		}
		delta_etrap = DELTA_ETRAP;
		etrap_size = world_electrotrap_list.electro_red_list.size();    	
    }

    public void _ex01CryotraxGameReload7(final int level_int) throws Throwable {
		hud_display.level_no = level_int;
		hud_display.primary_level_no = level_int;
		hud_display.HudLevelInitIntString();
		hud_display.HudLevelReplayResume();
    }
    
    public void _ex01CryotraxGameReload8() throws Throwable {
		came_from_within_game = false;
		starter_screen_firster = true;	      
		hud_display.InitCheckBoxesStars();
		added_admob_once = false;
    }

	public void ResetPlanet(){
		planet_poY = PLANET_POY;
		planet_poX = randInt(
				(int) Math.floor((double) PLANET_REPOS_X_DELTA),
				(int) Math.ceil( (double)PLANET_REPOS_X_DELTA2), r);
		planet.setPosition(
				planet_poX,
				PLANET_POY);
	}

	public void UpdatePlanet(float delta){
		planet_poY -= PLANET_SPEED_FPSDEP * delta;
		planet.setPosition(
				planet_poX,
				planet_poY);
	}
	
	public class MyElectrotrapReseter implements Runnable{
		public MyElectrotrapReseter(ex01ElectrotrapRedList list) {
			etrap_list = list;
		}
		public void run(){
			etrap_list.ResetElectrotrap(
					spaces.textureAtlas,
					world_data.list_electrotrp,
					world_data.list_electrotrp_elements,
					world_data.list_electrotrp_elements_type);
			reset_or_pause_level = false;
		}
	}
    
	public void InitCameras(){
		camera = new OrthographicCamera(
				screen_wW,
				screen_hW);
		camera.position.set(
				CENTER_CAMERA_X,
				CENTER_CAMERA_Y,
				0f);
		camera.zoom = 1f;	 		  
		camera.update(); 
		bg_camera = new OrthographicCamera(
				screen_wW,
				screen_hW);
		bg_camera.position.set(
				BG_CENTER_CAMERA_X,
				BG_CENTER_CAMERA_Y,
				0f);
		bg_camera.zoom = 1f;
		bg_camera.update();
		display_hud_camera = new OrthographicCamera(
				screen_wW,
				screen_hW);
		display_hud_camera.position.set(
				BG_CENTER_CAMERA_X,
				BG_CENTER_CAMERA_Y,
				0f);
		display_hud_camera.zoom = 1f;  
		display_hud_camera.update();
		display_hud_camera_table = new OrthographicCamera();
		bg_camera_stars = new OrthographicCamera(
				screen_wW,
				screen_hW);
		bg_camera_stars.position.set(
				BG_CENTER_CAMERA_X,
				BG_CENTER_CAMERA_Y,
				0f);
		bg_camera_stars.update();				 
		display_fud_camera_text = new OrthographicCamera(
				screen_sizew,
				screen_sizeh);
		display_fud_camera_text.position.set(
				Fscreen_sizew2,
				Fscreen_sizeh2,
				0f);
		display_fud_camera_text.update();	 
		display_fud_camera = new OrthographicCamera(
				screen_wW,
				screen_hW);
		display_fud_camera.position.set(
				DISPLAY_FUD_CAMERA_X,
				DISPLAY_FUD_CAMERA_Y,
				0f);
		display_fud_camera.update();
		batchHUD.setProjectionMatrix(bg_camera.combined);
	}
	
	public void ResetCameras(){
		camera.position.set(
				CENTER_CAMERA_X,
				CENTER_CAMERA_Y,
				0f);
		camera.zoom = 1f;    			
		camera.update();
		bg_camera.position.set(
				BG_CENTER_CAMERA_X,
				BG_CENTER_CAMERA_Y,
				0f);
		bg_camera.zoom = 1f; 			
		bg_camera.update();
		display_hud_camera.position.set(
				BG_CENTER_CAMERA_X,
				BG_CENTER_CAMERA_Y,
				0f);
		display_hud_camera.zoom = 1f;	
		display_hud_camera.update();		
		bg_camera_stars.position.set(
				BG_CENTER_CAMERA_X,
				BG_CENTER_CAMERA_Y,
				0f);
		bg_camera_stars.update();				
		display_fud_camera.position.set(
				DISPLAY_FUD_CAMERA_X,
				DISPLAY_FUD_CAMERA_Y,
				0f);
		display_fud_camera_text.position.set(
				Fscreen_sizew2,
				Fscreen_sizeh2,
				0f);
		display_fud_camera.update();
		display_fud_camera_text.update();
		batchHUD.setProjectionMatrix(bg_camera.combined);
	}    
    
	public void ResetProcedureAfterReplay(){
		// used in render to check for delta < 0.02(50FPS)
		StartTimePassed = false;
		// used to load the assets - we're just starting up
		StartTimePassed_FirstTime = true;
		// set to TRUE in PauseGameScreen (explode, finish, our of bullets, or simple pause)
		reset_or_pause_level = false;
		// used in render( in else ; made true if << if(StartTimePassed && !paused_game_screen) >>
		just_rendered_reset = false;
		// used in render( in else ; made true if << if(StartTimePassed &&  paused_game_screen) >>
		just_rendered_pause = false;
		// used in PauseGameScreen(for access once) - and render(to update some elements)
		paused_game_screen = false;
		// hud_display.AddEventListeners for the resume/restart button only once
		already_added_replay = false;
		// is practically spaces.can_explode
		spaces_can_explode = false;
		// at finish (bullets, or finish level) we change texture so we zoom and not see artifacts
		set_big_spaceship_already = false;
		// used when we finish the level or run out of bullets - in PauseGameScreen to check for
		// simple pause and in render<< if(!finished_the_job){ >> to make updates only when we're
		// not paused or finished(level or bullets)
		finished_the_job = false;
		// in render > if(!spaces.can_explode){ for not repeat (CheckNoMoreBulletsFinishBarrier
		finished_the_job_norepeat = false;
		// JSON need to be written once only in  -  render(float delta)
		wrote_to_json = false;
		// in hud_display - MainMenuButton click to return to main menu - we use this in render
		// otherwise it will crash MOFO when we Main Menu from game pause
		is_still_in_game = true;
		// Used for GRAY SHADER transition
		grayness = 0.0f;
		// Used for GRAY SHADER transition
		grayness_delta = 0.6f;
		// Used for GRAY SHADER transition - in order to modify grayness only when we're paused
		grayness_increase_more = false;
		grayness_increase_more_enough = false;
		spaces_world_velocity = spaces.world_velocity;
		spaces_world_velocity_original =
				spaces.world_velocity_original;
		spaces_world_velocity_original_shoot =
					spaces.world_velocity_original_shoot;
			hud.spaceship_bounding_rectangle_shoot_hud =
					hud.HUDshootOverlaySmallSprite.getBoundingRectangle();
			hud.spaceship_bounding_rectangle_shoot_hud.x +=
					EXPAND_SHOOT_RECT_DELTA/20;
			hud.spaceship_bounding_rectangle_shoot_hud.y +=
					EXPAND_SHOOT_RECT_DELTA/20;
			hud.spaceship_bounding_rectangle_shoot_hud.width -=
					EXPAND_SHOOT_RECT_DELTA/10;
			hud.spaceship_bounding_rectangle_shoot_hud.height -=
				EXPAND_SHOOT_RECT_DELTA/10;
		hud.slider_bounding_rectangle = hud.slider.getBoundingRectangle();
		hud.slider_bounding_rectangle.x -= 1f;
		hud.slider_bounding_rectangle.y -= 2.5f;
		hud.slider_bounding_rectangle.width += 2f;
		hud.slider_bounding_rectangle.height += 3f;
		cryo_game.already_loaded_game_once = true;	
	}
	
	public void ResetProcedureAfterReset(){
		added_admob_once = false;
		this.inputMultiplexer
				.removeProcessor(hud_display.stage_run_out_of_bullets);
		this.inputMultiplexer
				.removeProcessor(hud_display.stage);
		hud_display.added_nobullets = false;
		hud_display.added = false;	
		hud_display.restart_button.clearActions();
		hud_display.resume_button.clearActions();
		finished_the_job = false;
		finished_the_job_norepeat = false;
		just_rendered_reset = false;
		just_rendered_pause = false;
		zoom_current_spaceship = 1f;
		finish_camera_translator_inflater = 1f;
		set_big_spaceship_already = false;	
		wrote_to_json = false;
		StartTimePassed_FirstTime = false;
		MainGameResetVars();
		spaces.ResetSpacesForInGameReset();
		hud_display.ResetHudDisplayForInGameReset(); 
		hud.HUDReset();
		DisappearHUDOnFail();
		ResetCameras();
		world_electrotrap_list.Dispose(); 
		world_electrotrap_list = new ex01ElectrotrapRedList(
				spaces.textureAtlas,
				world_data.list_electrotrp,
				world_data.list_electrotrp_elements,
				world_data.list_electrotrp_elements_type,
				angle11,
				angle22,
				angle33);
		world_electrotrap_list.game_screen = this;
		world_electrotrap_list.hud = this.hud;
		world_electrotrap_list.camera_hud = display_hud_camera;
		world_electrotrap_list.LoadSpaceshipData(spaces);
		world_powerup_list.ex01PowerupListReload();
		asteroids.ex01CryoAsteroidListReloadPositions(0f,
							 world_electrotrap_list.electro_red_list
						.get(world_electrotrap_list.electro_red_list.size() - 1).getY());
		ResetPlanet();
	}	    
    
	public void MainGameResetVars(){ 	
		StartTimePassed = false;
		StartTimePassed_FirstTime = true;
		reset_or_pause_level = false;
		just_rendered_reset = false;
		just_rendered_pause = false;
		paused_game_screen = false;
		already_added_replay = false;
		spaces_can_explode = false;
		set_big_spaceship_already = false;
		finished_the_job = false;
		finished_the_job_norepeat = false;
		wrote_to_json = false;
		is_still_in_game = true;
		finish_line_y_coordinate = world_electrotrap_list.electro_red_list
				.get(world_electrotrap_list.electro_red_list.size()-1).getY();
		finish_camera_translator_inflater = 1f;
		zoom_current_spaceship = 1f;
		delta_xer = 1f;
		grayness_delta = 0.6f;	
	}	
	
    // used when we load different level department or different space-craft 
	public void CompletelyReloadTexturesEtrapAsteroids(int type_spacecraft, int level_int){
		if(cryo_game.screen_type==1){
			LoadAllTexturesPrimary_01big(type_spacecraft, level_int);
			LoadAllTexturesSecondary_01big();
		} else if(cryo_game.screen_type==2){
			LoadAllTexturesPrimary_01medium(type_spacecraft, level_int);
			LoadAllTexturesSecondary_01medium();
		} else {
			LoadAllTexturesPrimary_01small(type_spacecraft, level_int);
			LoadAllTexturesSecondary_01small();
		}

		spaces.textureAtlas = atlas_main; 
		spaces.textureAtlasSec = atlas_secondary;							
	}
	
	// used when we load same level or different level from same department, at the same time
	// as having the same space-craft
	public void SuperficiallyReloadTexturesEtrapAsteroids(int type_spacecraft, int level_int){
		if(cryo_game.screen_type==1){
			LoadAllTexturesSuperficial_01big(type_spacecraft, level_int);
		} else if(cryo_game.screen_type == 2) {
			LoadAllTexturesSuperficial_01medium(type_spacecraft, level_int);
		} else {
			LoadAllTexturesSuperficial_01small(type_spacecraft, level_int);
		}
		spaces.textureAtlas = atlas_main; 
		spaces.textureAtlasSec = atlas_secondary;		
	}
	
	public void CompletelyReloadTexturesEtrapAsteroidsB(){
		planet.setTexture(atlas_main.findRegion("planet778blur").getTexture());
		asteroids.Dispose();
		asteroids = new ex01CryoAsteroidList(0f, world_electrotrap_list.electro_red_list
				.get(world_electrotrap_list.electro_red_list.size()-1)
				.getY(), spaces.textureAtlas);
	}

	// used when we load same level or different level from same department, at the same
	// time as having the same space-craft
	public void SuperficiallyReloadTexturesEtrapAsteroidsB(){
		asteroids.ex01CryoAsteroidListReloadPositions(0f, world_electrotrap_list
				.electro_red_list.get(world_electrotrap_list.electro_red_list.size()-1).getY());
	}

	@Override
	public void render(float delta) {
		if(camera.position.y-15f>0f){
			temp = ex01FastMath.fastFloor((camera.position.y) / ETRAP_DRAWING_FILTER_DELTA);
			if(temp<world_electrotrap_list.etrap_drawing_filter.length){
				min_etrap_i = world_electrotrap_list.etrap_drawing_filter[temp][0];
				max_etrap_i = world_electrotrap_list.etrap_drawing_filter[temp][1] + 1;
			} else {
			} 
		} else {
			min_etrap_i = 0;
			max_etrap_i = world_electrotrap_list.biggest_etrap_no_so_far;
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(TimeUtils.nanoTime()-startTime>1000000000) {
			data="FPS: "+Gdx.graphics.getFramesPerSecond();
			startTime = TimeUtils.nanoTime(); }
		//used for FPS Start load /* 1,000,000,000ns == one second */
		if(delta < 0.02f){ StartTimePassed = true; }
		// ****************************************************************************************
		// *** if we're not just starting up game and we're not given command to reset level ******
		if(StartTimePassed && !reset_or_pause_level){
			// weird things will happen if we don't check for this minimum 50FPS
			if(delta < 0.025f){
				if(!paused_game_screen){
					UpdatePlanet(delta);
					// this means we did not hit something BAD or been electrocuted dead
					if(!spaces.can_explode){
						// ***  DIDN't finished the level ? and DIDN't RUN out of bullets*** play
						if(!finished_the_job){
							if(!hud_display.no_more_bullets_recorded_done
									&& !finished_the_job_norepeat) {
								CheckPassOverFinishBarrier();
							}
							if( hud_display.no_more_bullets_recorded_done
									&& !finished_the_job_norepeat) {
								CheckNoMoreBulletsFinishBarrier();
							}
							AdvanceWorld(delta);
							// update left/right movements of the spaceship
							if (hud_display.startTimerFinished)
								spaces.UpdateSpaceship(hud, delta);
							// update HUD arrows(3), acceleration(3), left/right(2)
							if (hud_display.startTimerFinished) {

								hud.UpdateHUD(delta);
							}
							// update electron-trap counter, max angle, bullets, and life
							hud_display.ProcessHudData();
							// update life/bullets sprite rotation + UPDATE PAUSE/FINISH Scene2D
							hud_display.UpdateFUDDisplayScene2DFree(delta);
							// update counter upon startup
							hud_display.UpdateStartupCounterFUD(
									delta);
							if (hud_display.startTimerFinished)
								spaces.UpdateSpaceshipAccel(
										camera,
										zoom_current_spaceship,
										delta);


					    // VVV################# FINISH LEVEL   o r   RUN OUT OF BULLETS part ######
					    // VVV################# FINISH LEVEL   o r   RUN OUT OF BULLETS part ######
						} else {
						// *** finished the job so we write to JSON *** (finished_the_job = final
						// level OR no more bullets)
						// *** finished_the_job is in CheckPassOverFinishBarrier and
						// CheckNoMoreBulletsFinishBarrier at zoom finish ***

							// advance the world only when we are premium otherwise admob will
							// slow down the game
							ProcessStopBecauseOfFinish(delta, 0.00075f);
							if(!wrote_to_json) {
								TurnToSepiaForContrast();
								ExplodeTimerTask.purge();
								ExplodeTimerTask.schedule(new TimerTask() {
									@Override
									public void run(){
									// if we don't put this barrier we pass 6 times through here
									// don't know why
									if(!wrote_to_json){
										hud_display.AddClickRestartListener();
										hud_display.AddClickResumeListener();
										// we make it exploded already no need to pas through
										// can_explode
										spaces.exploded = true;
										// we turn of shoot sprites and electrotrap counter, life
										// and bullets, when we have no bullets
										AppearHUDOnFail();
										// we just finished zoom in after level finish OR no
										// more bullets
										spaces.exploded_finished = true;
										// used when we finish the level or run out of bullets to
										// relax the listening buffers
										hud.HudSpecialReset();
										// prepare settings class and then write it to file
										if (!wrote_to_json)
											hud_display.PropagateAmmoLifeFromToSettings();
										// we need to write only once
										wrote_to_json = true;
										PauseGameScreen();
									}
									}
								}, 0);
							}
						}
						// ^^^################# FINISH LEVEL   o r   RUN OUT OF BULLETS part ######
						// ^^^################# FINISH LEVEL   o r   RUN OUT OF BULLETS part ######


						if(!added_admob_once) {
							if (hud_display.startTimerFinished) {
								if (!spaces.no_more_bullets) spaces.UpdateWorldAccel(delta);
							}
							if (!hud_display.no_more_bullets_recorded_done) {
								spaces.can_explode = world_electrotrap_list.check_collision(
										delta,
										spaces.spaceship_rectangle_collision,
										camera.position.y,
										spaces.laser_shoots,
										spaces.explosion_laser_blocker,
										spaces.explosionMicroAnimation,
										spaces.blocker_hit_explosion,
										min_etrap_i,
										max_etrap_i);
							}
							spaces_can_explode = spaces.can_explode;
							if (spaces_can_explode) {
								hud_display.ProcessDeathByCollision();
							}
							asteroids.update(delta, camera.position.y);
							if (!hud_display.no_more_bullets_recorded_done) {
								world_electrotrap_list.update(
										hud_display,
										delta,
										camera.position.y,
										spaces.spaceship_rectangle_collision,
										spaces,
										world_powerup_list,
										hud_display,
										min_etrap_i,
										max_etrap_i);
							}
							// check to see if spaceship hits a power-up
							world_powerup_list.check_collision(
									spaces.spaceship_rectangle_collision,
									camera.position.y,
									hud_display,
									spaces,
									world_powerup_list.min_powerup_collision_h,
									world_powerup_list.max_powerup_collision_h,
									world_powerup_list.min_powerup_collision_b,
									world_powerup_list.max_powerup_collision_b);
							//update rotations and if hit by spaceship update the transitions
							// then take out out of screen power-ups
							world_powerup_list.Update(
									delta,
									camera.position.y);
							spaces.UpdateElectroShocks(delta);
							if (spaces.can_explode) {
								spaces.spaceship_explosion.play(0.2f);
								spaces.StopAccelerationSounds();
							}
						}


					// VVV################## EXPLOSION PART + ELECTROCUTION EXPLOSION ##############
					// VVV################## EXPLOSION PART + ELECTROCUTION EXPLOSION ##############
					// this else is if(!spaces.can_explode){ *** it means we DID HIT or
					// ELECTROSHOCK the space-craft ***
					} else {
					// *** if(!spaces.can_explode){ *** this means WE HIT something BAD or been
					// electrocuted NOT RUN OUT OF BULLETS ( we have the bullets part
					// in << if(!spaces.can_explode){ >> ) ***
						spaces.UpdateElectroShocks(delta);
						if(!spaces.exploded && !hud_display.no_more_bullets_recorded_done) {
							spaces.UpdateExplosion(hud_display, delta);	
						} else {
						/*** we have already passed explosion (either through explosion BUT NOT
						     through bullet depletion ***/
						}
						if (hud_display.startTimerFinished)
							hud.UpdateHUD(delta);
						// advance the world only when we are premium otherwise admob
						// will slow down the game
						ProcessStopBecauseOfFinish(delta, 0.00075f);
					}	
					// ^^^################## EXPLOSION PART + ELECTROCUTION EXPLOSION ##############
					// ^^^################## EXPLOSION PART + ELECTROCUTION EXPLOSION ##############


				} else {
				// if(!paused_game_screen){ the means we paused by pressing "P(BACK)" or "HOME"
					hud_display.UpdateFUDDisplayScene2DFree(delta);
					if(!finished_the_job){ // finished the level or exploded
						if (hud_display.startTimerFinished) spaces.UpdateSpaceship(hud, delta);
					}						
				}
			}
			if(!finished_the_job_norepeat) {
				accessed_now = TimeUtils.nanoTime();
				Render(delta);
				last_accesed = TimeUtils.nanoTime();
				counter_add+=last_accesed-accessed_now;
				counter++;
				if(counter==2500){
					data = Long.toString(counter_add/2500);
					counter=0;
					counter_add=0; }
			} else {
				RenderNoJob(delta);
			}
		// *****************************************************************************************
		} else { // *** if we're just starting up game OR we're given command to reset
				 // level ***   -   up is : if(StartTimePassed && !reset_or_pause_level){
			if(!reset_or_pause_level) RenderReLoadingScreen(batch);
			//this checks for >50fps and then checks to see if we DON'T HAVE a
			//pause screen on the screen command
			if(StartTimePassed && !paused_game_screen){
				// reset_or_pause_level set to original so << if(StartTimePassed &&
				// !reset_or_pause_level){ >> in render can update and render our sprites
				reset_or_pause_level = false;
				// used in the next if StartTimePassed has delta < 0.02(50FPS)
				just_rendered_reset = true;
			//this checks for >50fps and then checks to see if we HAVE A pause screen on the
			//screen command
			} else if(StartTimePassed && paused_game_screen){
				// reset_or_pause_level set to original so << if(StartTimePassed &&
				// !reset_or_pause_level){ >> in render can update and render our sprites
				reset_or_pause_level = false;
				// add event listeners and stop sounds
				just_rendered_pause = true;
				// HUD for resume/restart Sceen2D
				hud_display.stage.addAction(Actions.fadeIn(2f));
				hud_display.stage.addAction(sequence(
						Actions.fadeIn(2f),
						Actions.run(new Runnable(){
							public void run(){
							}
				})));				
				hud_display.showed = true;
				hud_display.table_main_menu.addAction(Actions.fadeIn(2f));
			}
		}
		// *****************************************************************************************
		// the command is to reset all variables not prepare the pause screen Scene2D menu
		if(just_rendered_reset && StartTimePassed){ ResetProcedureAfterReset(); }
		// *****************************************************************************************
		// the command is to add click listeners to pause and resume
		if(just_rendered_pause && StartTimePassed){ PauseProcedureAfterPause(delta); }
		// Screen Capture
		if(hud_display.clicked_google_screencap) {
			try {
				/*
				cryo_game.google_facebook_services.saveScreenshotFromGame();
				cryo_game.google_facebook_services.shareGoogleImage(
						hud_display.level_no,
						pathto_screencap);
				*/
			} catch(Exception ex){

			}
			hud_display.clicked_google_screencap = false;

		} else if (hud_display.clicked_facebook_screencap) {
			try {
				/*
				cryo_game.google_facebook_services.saveScreenshotFromGame();
				cryo_game.google_facebook_services.shareFacebookImage(hud_display.level_no);
				*/
			} catch(Exception ex){

			}
			hud_display.clicked_facebook_screencap = false;
		}

		//	hud_display.stage_fps.act(delta);
		//	hud_display.stage_fps.draw();
	}

	public void RenderNoJob(float delta) {
		if (is_still_in_game){
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(
					GL20.GL_SRC_ALPHA,
					GL20.GL_ONE_MINUS_SRC_ALPHA);
			batch.begin();
			if (grayness_increase_more) {
				if(!grayness_increase_more_enough){
					shader_finish.setUniformf("u_grayness", grayness);
					shader_finish_sepia_pause.setUniformf("u_grayness", grayness);
				}
			}
			batch.setProjectionMatrix(bg_camera.combined);
			backs.draw(batch);
			stars.Draw(batch);
			batch.setProjectionMatrix(bg_camera.combined);
			planet.draw(batch);
			batch.setProjectionMatrix(camera.combined);
			asteroids.Render(batch, camera.position.y);
			spaces.RenderSpaceship(batch, camera);
			batch.end();

			world_electrotrap_list.renderBlockerLaser(
					batch,
					camera.position.y,
					camera,
					spaces,
					min_etrap_i,
					max_etrap_i);
			spaces.renderLasers(camera);
			batch.begin();

			world_electrotrap_list.renderLightningVariantArt(
					batch,
					camera.position.y,
					min_etrap_i,
					max_etrap_i);
			world_electrotrap_list.renderBody(
					batch,
					camera.position.y,
					camera,
					min_etrap_i,
					max_etrap_i);

			world_powerup_list.Render(
					batch,
					camera.position.y);
			batch.end();

			batchHUD.begin(); 
			hud.RenderHUDAccelAndShot(batchHUD);
			hud_display.Render(batchHUD, delta); 
			hud.RenderHUDLateralAccelAndShot(batchHUD);
			batchHUD.end();

			hud_display.renderDisplayFUDs(
					spaces.exploded,
					spaces.exploded_finished,
					delta,
					hud);

			if(grayness_increase_more){
				if(!grayness_increase_more_reversed){
					grayness+=grayness_delta*delta;
					if(grayness>1f) { grayness=1f; }
				} else {
					grayness-=grayness_delta*delta;
					if(grayness<0f) {
						grayness=0f;
						grayness_increase_more_reversed = false;
						grayness_increase_more = false;
					}
				}
			}
		}			
	}

	public void Render(float delta) {
		if(is_still_in_game) {
			Gdx.gl20.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(
					GL20.GL_SRC_ALPHA,
					GL20.GL_ONE_MINUS_SRC_ALPHA);
			batch.begin();
			if (grayness_increase_more) {
				if(!grayness_increase_more_enough){
					shader_finish.setUniformf("u_grayness", grayness);
					shader_finish_sepia_pause.setUniformf("u_grayness", grayness);
				}
			}
			batch.setProjectionMatrix(bg_camera.combined);
			backs.draw(batch);
			stars.Draw(batch);
			batch.setProjectionMatrix(bg_camera.combined);
			planet.draw(batch);
			batch.setProjectionMatrix(camera.combined);
			asteroids.Render(batch, camera.position.y);
			spaces.RenderSpaceship(batch, camera);
			batch.end();

			world_electrotrap_list.renderBlockerLaser(
					batch,
					camera.position.y,
					camera,
					spaces,
					min_etrap_i,
					max_etrap_i);

			spaces.renderLasers(camera);
			batch.begin();

			world_electrotrap_list.renderLightningVariantArt(
					batch,
					camera.position.y,
					min_etrap_i,
					max_etrap_i);
			world_electrotrap_list.renderBody(
					batch,
					camera.position.y,
					camera,
					min_etrap_i,
					max_etrap_i);
			world_powerup_list.Render(
					batch,
					camera.position.y);
			batch.end();

			hud_display.renderDisplayFUDsTables(
					paused_game_screen,
					delta);
			hud_display.HudFPSStageRender(data);

			batchHUD.begin();
			hud.RenderHUDAccelAndShot(batchHUD);
			hud_display.Render(batchHUD, delta);
			hud.RenderHUDLateralAccelAndShot(batchHUD);
			batchHUD.end();

			hud_display.renderDisplayFUDs(
					spaces.exploded,
					spaces.exploded_finished,
					delta,
					hud);

			if (spaces.can_explode){ 
				batch.setProjectionMatrix(camera.combined); 
				batch.begin(); 
				spaces.sprite_explosion.draw(batch); 
				batch.end(); 
			}

			if(grayness_increase_more){
				if(!grayness_increase_more_reversed){
					grayness+=grayness_delta*delta;
					if(grayness>1f) {
						grayness=1f;
						grayness_increase_more_enough = true;
					}
				} else {
					grayness-=grayness_delta*delta;
					if(grayness<0f) {
						grayness=0f;
						grayness_increase_more_reversed = false;
						grayness_increase_more = false;
					}
				}
			}
			if(ex01Types.RENDER_COLLISION) {
				world_electrotrap_list.renderLightningRectCirclesOld(
						shaper,
						camera,
						camera.position.y,
						spaces.spaceship_rectangle_collision,
						spaces.laser_shoots,
						world_powerup_list);
			}
		}			
	}
	
	public void RenderReLoadingScreen(SpriteBatch batch){
        Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		batch.begin();    
		batch.setProjectionMatrix(bg_camera.combined); bg_camera.update();
		if(!reset_or_pause_level) loadingSprite.draw(batch);
		batch.end();
	}

	public void ProcessStopBecauseOfFinish(float delta, float speed){
		if(spaces.world_velocity>0.0100f){
			speed_decelerate = speed;
			spaces.world_velocity -= speed;
			AdvanceWorld(delta);
		} else if(spaces.world_velocity>0.0000f){
			speed_decelerate/=2;
			spaces.world_velocity -= speed_decelerate;
			AdvanceWorld(delta);
		}
		if(spaces.world_velocity<0f)spaces.world_velocity=0f;
	}

	// PAUSE: 1>exploded,  #  2>no-ammo,  #  3>pause("P","HOME"),  #  4>finish(passed-all-etraps)
	// called  CryotraxGame            : for "BACK or "P" key
	// called _ex01CryotraxGame        : in render for when we finished_the_job (level or no ammo)
	// called  ex01CryoShipPrincipial  : finish animation explosion in spaces.UpdateExplosion
	public void PauseGameScreen(){
		if(!paused_game_screen){
			// "P", "BACK", "HOME" simple pause
			if(!spaces.exploded && !finished_the_job){
				TurnToSepiaForContrastPause();
				spaces.StopSoundsForPauseResume();
				paused_game_screen = true; // used only for "P", "BACK", "HOME" simple pause
				reset_or_pause_level = true;				
				hud.HideShotForSimplyPaused();
				hud_display.ShowInfoForSimplyPaused();
				hud_display.ProcessPartialAngleData(angle11, angle22, angle33);
			// "LEVEL FINISH", "EXPLODED", "NO MORE BULLETS"
			} else {
				just_rendered_pause = true; //used in render to PauseProcedureAfterPause
				// never gets called if we explode or run out of bullets or die of electrocution
				if(!hud_display.appeared_from_exploded_or_finished_pass){ // only once per PAUSE
					if(!wrote_to_json)
						hud_display.PropagateAmmoLifeFromToSettings();
					spaces.StopSoundsForPauseResume();
					if(finished_the_job)
						spaces.PlayDownSoundsForFinish();
					hud_display.appeared_from_exploded_or_finished_pass = true;
					// *** exploded OR finish level ***   	
					if(!hud_display.no_more_bullets_recorded_done){
						hud_display.MakeResumeGrey();
						// died by collision or electrocution
						if(!finished_the_job){ // *** exploded ***
							hud_display.UpdateTries();
							cryo_game.menu_screen.settings.WriteJson();
							if(settings_for_game.settings.were_currently_limited_on_life){
								hud_display.LifeWarningPlusBuyDisplay();
							} else {
								hud_display.ProcessFinishAngleOnExploded();								
							}
						} else { // *** finished the level ***
							hud_display.UpdateTries();
							hud_display.blocked_deblocked_counter =
									world_electrotrap_list.blocked_deblocked_counter;
							hud_display.ProcessFinishAngleData(
									angle11,
									angle22,
									angle33);
						}
					// *** run out of bullets ***
					} else {
						hud_display.UpdateTries();
						cryo_game.menu_screen.settings.WriteJson();
						hud_display.AmmoWarningPlusBuyDisplay();
					}
				}
			}
			hud_display.old_level_string_error_info = (String) hud_display
					.level_base_spacecraft_error.getText().toString();
		} 
	}
	
	// simple gray shade make no active
	public void MakeGreyShadersNoActive(){
		batch.setShader(null);
		batchHUD.setShader(null);
		hud_display.stage_angles.getBatch().setShader(null);
		hud_display.stage_life_bullets_text.getBatch().setShader(null);		
		hud_display.stage_rotative_counter.getBatch().setShader(null);
		world_electrotrap_list.shader = world_electrotrap_list.shader_base;
		spaces.shader = spaces.shader_base;
	}
	
	// this shader is a little blacker than TurnToSepiaForContrast()
	public void TurnToSepiaForContrastPause(){
		grayness_increase_more = true;
		grayness_increase_more_reversed = false;
		batch.setShader(shader_finish_sepia_pause);
		batchHUD.setShader(shader_finish_sepia_pause);
		hud_display.stage_angles.getBatch().setShader(shader_finish_sepia_pause);
		hud_display.stage_life_bullets_text.getBatch().setShader(shader_finish_sepia_pause);
		hud_display.stage_rotative_counter.getBatch().setShader(shader_finish_sepia_pause);
		world_electrotrap_list.shader = world_electrotrap_list.shaderGrey;
		spaces.shader = spaces.shaderGrey;
	}
	
	// this shader is a little whiter than TurnToSepiaForContrastPause()
	public void TurnToSepiaForContrast(){
		grayness_increase_more = true;
		grayness_increase_more_reversed = false;
		this.batch.setShader(shader_finish);
		this.batchHUD.setShader(shader_finish);
		this.hud_display.stage_angles.getBatch().setShader(shader_finish);
		this.hud_display.stage_life_bullets_text.getBatch().setShader(shader_finish);
		this.hud_display.stage_rotative_counter.getBatch().setShader(shader_finish);
		this.world_electrotrap_list.shader = world_electrotrap_list.shaderGrey; 
		this.spaces.shader = spaces.shaderGrey;
	}		

	public void AdvanceWorld(float delta){		
		delta_64_srsz = delta * 64f;
		velo = spaces.world_velocity * delta_64_srsz;
		velo_original = spaces.world_velocity_original * delta_64_srsz;
		velo_original_shoot = spaces.world_velocity_original_shoot * delta_64_srsz;
		spaces.spaceshipAdvance(velo);
		spaces.UpdateShootings(
				spaces.spaceship_rectangle_collision,
				hud, hud_display,
				delta,
				velo_original_shoot * 8f,
				camera.position.y);
		camera.position.set(
				camera.position.x,
				camera.position.y + velo,
				0f);
		camera.update(); // deleted from render
		stars.AdvanceWorldUpdateStars(velo, velo_original);
	}	
	
	//check to see if we have reached the finish line - CALLED IN RENDER 
	//if(!spaces.can_explode){ > > > CheckPassOverFinishBarrier();
	public void CheckPassOverFinishBarrier(){
		if(spaces.sprite.getY() > finish_line_y_coordinate){
			hud.PressedReset();
			if(!set_big_spaceship_already){
				CheckPassOverFinishBarrier_HudSpacesReset();
				if(type_spaceship==0) { 
					spaces.LoadBigSpaceship1(); 
				} else if(type_spaceship==1) { 
					spaces.LoadBigSpaceship2(); 
				} else if(type_spaceship==2) { 
					spaces.LoadBigSpaceship3(); 
				}
			} //passed the finish line

			delta_xer = 1f;

			// solve the centering on the x-axis
			if(spaces.sprite.getX() + SPACESHIP_WIDTH_Xp2 > camera.position.x) { 
				delta_xer = camera.position.x - spaces.sprite.getX() - SPACESHIP_WIDTH_Xp2; 
			} else { 
				delta_xer = camera.position.x - spaces.sprite.getX() - SPACESHIP_WIDTH_Xp2;
			}

			if(Math.abs(camera.position.x - spaces.sprite.getX() - SPACESHIP_WIDTH_Xp2) >
					BARRIER_LIMIT ) {
				camera.position.set(
					camera.position.x -
							delta_xer/BARRIER_DIVIDER * finish_camera_translator_inflater,
					camera.position.y,
					0f);
			}

			// move the camera down so the space-craft seems to go upwards
			if(camera.position.y + STOPPER_CAMERA > spaces.sprite.getY()) { 
				camera.position.set(
					camera.position.x,
					camera.position.y - INFLATER_DENOMINATOR2 * finish_camera_translator_inflater,
					0f);
			}

			// stop the moving of the camera and block further access to this function
			if(camera.position.y + STOPPER_CAMERA < spaces.sprite.getY()){ 
				finished_the_job = true; 
				finished_the_job_norepeat = true; 
			} 	
			if(camera.zoom > 0.4f) { 
				camera.zoom -= 0.00525f; 
				zoom_current_spaceship = camera.zoom; 
			}		
			finish_camera_translator_inflater+=0.5f; // zoom in a little
		}
	}
	
	//check to see if we have reached the finish line - CALLED IN RENDER
	//if(!spaces.can_explode){ > > > CheckPassOverFinishBarrier();
	public void CheckNoMoreBulletsFinishBarrier(){
		hud.PressedReset();		
		if(!set_big_spaceship_already){
			CheckNoMoreBulletsFinishBarrier_HudSpacesReset();
			if(type_spaceship==0){ 
				spaces.LoadBigSpaceship1(); 
			} else if(type_spaceship==1){ 
				spaces.LoadBigSpaceship2(); 
			} else if(type_spaceship==2){ 
				spaces.LoadBigSpaceship3(); 
			}
		} // passed the finish line

		delta_xer = 1f;

		// solve the centering on the x-axis
		if(spaces.sprite.getX() + SPACESHIP_WIDTH_Xp2 > camera.position.x) { 
			delta_xer = camera.position.x - spaces.sprite.getX() - SPACESHIP_WIDTH_Xp2; 
		} else { 
			delta_xer = camera.position.x - spaces.sprite.getX() - SPACESHIP_WIDTH_Xp2; 
		}

		if(Math.abs(camera.position.x - spaces.sprite.getX()) > BARRIER_LIMIT ) {
			camera.position.set(
				camera.position.x - delta_xer/BARRIER_DIVIDER_B * finish_camera_translator_inflater,
				camera.position.y,
				0f);
		}

		// move the camera a bit down so the space-craft is seen up a little
		if(camera.position.y + STOPPER_CAMERA_B > spaces.sprite.getY()) {
			camera.position.set(
				camera.position.x,
				camera.position.y - INFLATER_DENOMINATOR * finish_camera_translator_inflater,
				0f);
		}

		// stop the space-craft once the camera is moved properly and block further
		// access to this function - finished_the_job_norepeat
		if(camera.position.y + STOPPER_CAMERA_B < spaces.sprite.getY()) {
			finished_the_job = true; 
			finished_the_job_norepeat = true; 
		} 		
		if(camera.zoom > 0.875f) { 
			camera.zoom -= INFLATER_ZOOM2;
			zoom_current_spaceship = camera.zoom; 
		}
		finish_camera_translator_inflater+=0.225f; // zoom in a little
	}	

	public void ResetHudSpaceshipAndVariables(){
		reset_or_pause_level = true;
	}	
	
	// this is called when we enter pause phase (when it starts fadeIn procedure)
	public void PauseProcedureAfterPause(float delta){
		if(!already_added_replay){
			already_added_replay = true;
			hud_display.AddClickRestartListener();
			hud_display.AddClickResumeListener();	
		}		
		hud_display.stage.act(delta);
		hud_display.stage.draw();

		if(hud_display.added_render_VALUE){
			hud_display.get_money_table_bought.act(delta);
			hud_display.level_base_coins_you_own_bought.act(delta);
			hud_display.level_base_coins_bought.act(delta);
			hud_display.get_money_table_bought.act(delta);
			hud_display.VALUE_screen.Render(delta);
		}		
	}

	public void CheckPassOverFinishBarrier_HudSpacesReset(){
		hud.finished_started = true;
		hud.pressed_accel = false;
		hud.pressed_middle = false;
		hud.pressed_shoot = false;
		spaces.touch_upped = true;
		spaces.touched = false;
		spaces.touchUP_();
		spaces.pressed_accel = false;
		AppearHUDOnFail();
		set_big_spaceship_already = true;		
	}
	
	public void CheckNoMoreBulletsFinishBarrier_HudSpacesReset(){
		hud.finished_started = true;
		hud.pressed_accel = false;
		hud.pressed_middle = false;
		hud.pressed_shoot = false;
		spaces.touch_upped = true;
		spaces.touched = false;
		spaces.touchUP_();
		spaces.pressed_accel = false;
		AppearHUDOnFail();
		set_big_spaceship_already = true;		
	}	
	
	public void AppearHUDOnFail(){
		hud_display.table_life_bullets_text.addAction(Actions.fadeOut(2f));
		hud_display.table_counter_angles_text.addAction(Actions.fadeOut(2f));
		hud.finished_started = true;
		hud.HUDshootOverlaySmallSprite.setAlpha(0.0f);
		hud.HUDshootSprite.setAlpha(0.0f);
		hud.slider.setAlpha(0.0f);
	}	
	
	public void DisappearHUDOnFail(){
		hud_display.DisappearHUDOnFail();
		hud.DisappearHUDOnFail();
	}

	public boolean AreFromSameDepartment(int last_level_int, int level_int){
		returner = true;	
		if(level_int<17){
			no_background = 1;
			if(!(last_level_int<17)) returner = false;
		} else if(level_int>16 && level_int<33){
			no_background = 2;
			if(!(last_level_int>16 && last_level_int<33)) returner = false;
		} else if(level_int>32 && level_int<49){
			no_background = 3;
			if(!(last_level_int>32 && last_level_int<49))  returner = false;
		} else if(level_int>48 && level_int<65){
			no_background = 4;
			if(!(last_level_int>48 && last_level_int<65))  returner = false;
		}
		return returner;
	}
	
	@Override
    public void show() {
		cryo_game.game_state = GameState.GAME_SCREEN;   
		world_electrotrap_list.startTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
		if(starter_screen_firster){
			starter_screen_firster = false;
			spaces.PrepareSpaceshipSounds();
			Gdx.input.setInputProcessor(inputMultiplexer);
			Gdx.input.setCatchBackKey(true);
		} else {
		}
    }

    @Override
    public void hide() {
		if(spaces!=null) {
			if (spaces.spaceship_acceleration != null) {
				spaces.spaceship_acceleration.stop(spaces.id_spaceship_acceleration);
			}
		}
    }
	
	@Override
	public void resize(int width, int height){		
		
	}
	
	@Override 
	public void pause(){		
	}
	
	@Override 
	public void resume(){
		StartTimePassed = false;
	}

	public void ProcessLeftRight(int x, int y, int pointer){
		if(hud.is_on_slider) {
			if ((!spaces_can_explode && !paused_game_screen)
					&& !spaces.exploded_finished && hud_display.startTimerFinished) {
				hud.HUD_LeftRightProcess(x, y, pointer, spaces, display_fud_camera);
			}
		}
	}

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
		touchDown_(x,y,pointer,button);
		return false;
    }

	public void touchDown_(int x, int y, int pointer, int button){
		touch_downed_now = true;
		display_fud_camera.unproject(newTouch.set(x, y, 0));

		/*
		hud.pressed_left = newTouch.x < (spaces.sprite.getX() +
				spaces.sprite.getWidth()/2 - spaces.sprite.getWidth()/8);
		hud.pressed_right = newTouch.x > (spaces.sprite.getX() +
				spaces.sprite.getWidth()/2 + spaces.sprite.getWidth()/8);
		*/

		if ((!spaces_can_explode && !paused_game_screen)
				&& !spaces.exploded_finished && hud_display.startTimerFinished) {
			hud.touchDownProccess(x, y, pointer, spaces, display_fud_camera);
		}
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		hud.is_on_slider = false;
    	hud.touchUP(spaces);
	    spaces.touchUP(x, y, pointer, button);
		ResetSlider();
        return false;
    }

	public void ResetSlider(){
		hud.slider_bounding_rectangle = hud.slider.getBoundingRectangle();
		hud.slider.setPosition(
				hud.HUD_SLIDER_X,
				hud.HUD_SLIDER_Y);
		hud.slider.setSize(
				hud.HUD_SLIDER_W,
				hud.HUD_SLIDER_H);
		hud.slider_bounding_rectangle = hud.slider.getBoundingRectangle();
		hud.slider_bounding_rectangle.x -= 1f;
		hud.slider_bounding_rectangle.y -= 2.5f;
		hud.slider_bounding_rectangle.width += 2f;
		hud.slider_bounding_rectangle.height += 3f;
	}

	public boolean touchDownCamerFromDragged = false;

	@Override
    public boolean touchDragged (int x, int y, int pointer) {
		touchDownCamerFromDragged = true;
		touchDown_(x, y, pointer, pointer);
		touchDownCamerFromDragged = false;
		touch_dragged_now = true;
		display_fud_camera.unproject(newTouch.set(x, y, 0));

		hud.pressed_left = newTouch.x < (spaces.sprite.getX() +
				spaces.sprite.getWidth()/2 - spaces.sprite.getWidth()/8);
		hud.pressed_right = newTouch.x > (spaces.sprite.getX() +
				spaces.sprite.getWidth()/2 + spaces.sprite.getWidth()/8);
		finger_is_on_spaceship = (newTouch.x >=
				(spaces.sprite.getX() + spaces.sprite.getWidth()/2 - spaces.sprite.getWidth()/8))
				&& (newTouch.x <= (spaces.sprite.getX() +
				spaces.sprite.getWidth()/2 +
				spaces.sprite.getWidth()/8));

		if (!hud.pressed_middle_shoot) {
			display_fud_camera.unproject(newTouch.set(x, y, 0));
			xdiff = newTouch.x - lastTouch.x;
			percenter_from_center = Math.abs(newTouch.x - (spaces.sprite.getX() +
					spaces.sprite.getWidth() / 2));
			if (percenter_from_center < 5f) {
				percenter_from_center = percenter_from_center / 5f;
			} else {
				percenter_from_center = 1f;
			}
			hud.pressed_left = newTouch.x < (spaces.sprite.getX() +
					spaces.sprite.getWidth() / 2 - spaces.sprite.getWidth() / 8);
			hud.pressed_right = newTouch.x >= (spaces.sprite.getX() +
					spaces.sprite.getWidth() / 2 + spaces.sprite.getWidth() / 8);
			if (newTouch.x > LIMIT_X_LEFT && newTouch.x < LIMIT_X_RIGHT) {
				hud.slider.setPosition(newTouch.x -
						hud.rectangle_slider_width / 2, hud.slider.getY());
				hud.slider_bounding_rectangle.x = newTouch.x -
						hud.slider_bounding_rectangle.getWidth() / 2;
			}
			lastTouch = newTouch;
			ProcessLeftRight(x, y, pointer);
		}

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

	@Override
	public boolean keyUp (int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped (char character) {
		return false;
	}

	@Override
	public boolean keyDown (int keycode) {
		if(keycode == Keys.BACK){
			if(!spaces.exploded && !finished_the_job ){
				PauseGameScreen();
			}
		}
		if(keycode == Keys.P){
			if(!spaces.exploded && !finished_the_job){
				PauseGameScreen();
			}
		}
		return false;
	}

	@Override
	public void dispose(){
		if(hud_angler_font_bitmap!=null) {
			hud_angler_font_bitmap.dispose();
			hud_angler_font_bitmap = null;
		}
		if(hud_angler_fontp_bitmap!=null) {
			hud_angler_fontp_bitmap.dispose();
			hud_angler_fontp_bitmap = null;
		}
		if(hud_angler_font_title_bitmap!=null) {
			hud_angler_font_title_bitmap.dispose();
			hud_angler_font_title_bitmap = null;
		}
		if(shaper!=null) {
			shaper.dispose();
			shaper = null;
		}
		data = null;
		if(skin!=null) {
			skin.dispose();
			skin = null;
		}
		if(skin_main!=null) {
			skin_main.dispose();
			skin_main = null;
		}
		if(skin_secondary!=null) {
			skin_secondary.dispose();
			skin_secondary = null;
		}
		backs = null;
		loadingSprite = null;
		if(batch!=null) {
			batch.flush();
			batch.dispose();
			batch = null;
		}
		if(batchHUD!=null) {
			batchHUD.flush();
			batchHUD.dispose();
			batchHUD = null;
		}
		if(batchCache!=null) {
			batchCache.dispose();
			batchCache = null;
		}
		if(inputMultiplexer!=null) {
			if(hud_display!=null)
				this.inputMultiplexer.removeProcessor(hud_display.stage_run_out_of_bullets);
			if(hud_display!=null)
				this.inputMultiplexer.removeProcessor(hud_display.stage);
			this.inputMultiplexer.removeProcessor(cryo_game.game_screen);
			this.inputMultiplexer.removeProcessor(this);
			this.inputMultiplexer.clear();
			this.inputMultiplexer = null;
		}
		if(back!=null) {
			back.dispose();
			back = null;
		}
		if(textureAtlas!=null) {
			textureAtlas.dispose();
			textureAtlas = null;
		}
		if(atlas_main!=null) {
			atlas_main.dispose();
			atlas_main = null;
		}
		if(atlas_secondary!=null) {
			atlas_secondary.dispose();
			atlas_secondary = null;
		}
		camera = null;
		bg_camera = null;
		bg_camera_stars = null;
		display_hud_camera = null;
		display_hud_camera_table = null;
		display_fud_camera = null;
		display_fud_camera_text = null;
		viewport = null;
		r_write_json = null;
		r_kill_spaceship_sound = null;
		t_write_json = null;
		t_kill_spaceship_sound = null;
		if(shader_finish!=null) {
			shader_finish.end();
			shader_finish.dispose();
		}
		if(shader_finish_sepia_pause!=null) {
			shader_finish_sepia_pause.end();
			shader_finish_sepia_pause.dispose();
		}
		if(shader_grayscale!=null) {
			shader_grayscale.end();
			shader_grayscale.dispose();
		}
		etrap_reseter = null;
		if(world_electrotrap_list!=null) {
			if(world_electrotrap_list.texture_atlas_here!=null){
				world_electrotrap_list.texture_atlas_here.dispose();
				world_electrotrap_list.texture_atlas_here = null;
			}
			world_electrotrap_list.Dispose();
			world_electrotrap_list = null;
		}
		etrap_list = null;
		if(world_data!=null){
			world_data.Dispose();
			world_data = null;
		}
		if(spaces!=null){
			spaces.Dispose();
			spaces = null;
		}
		if(settings_for_game!=null){
			settings_for_game.Dispose();
			settings_for_game = null;
		}
		if(world_powerup_list!=null) {
			world_powerup_list.Dispose();
			world_powerup_list = null;
		}
		if(hud_display!=null) {
			hud_display.DisposeHUDDisplay_All();
			hud_display = null;
		}
		if(stars!=null) {
			stars.Dispose();
			stars = null;
		}
		if(hud!=null) {
			hud.Dispose();
			hud = null;
		}
		if(asteroids!=null)	{
			asteroids.Dispose();
			asteroids = null;
		}
		if(ExplodeTimerTask!=null) {
			ExplodeTimerTask.cancel();
			ExplodeTimerTask = null;
		}
		mloader = null;
		if(assets!=null) {
			assets.clear();
			assets.dispose();
			assets = null;
		}
		pathto_screencap = null;
		tmpFile = null;
		planet = null;
		r = null;
	}


	//----------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------

	public void LoadAllTexturesPrimary_01big(int type_spacecraft, int level_int){
		if(assets!=null){
			assets.clear();
			assets.finishLoading();
			assets.dispose();
		}
		assets = new AssetManager();
		switch(type_spacecraft){
			case 0:
			{
				if(level_int<17){
					no_background = 1;
					assets.load(ex01Types.MAIN_ATLASABACK1_ATLAS01big, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASABACK1_SKIN01big, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK1_ATLAS01big));
					atlas_main = assets.get(ex01Types.MAIN_ATLASABACK1_ATLAS01big);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK1_SKIN01big));
					skin_main = assets.get(ex01Types.MAIN_ATLASABACK1_SKIN01big);

				} else if(level_int>16 && level_int<33){
					no_background = 2;
					assets.load(ex01Types.MAIN_ATLASABACK2_ATLAS01big, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASABACK2_SKIN01big, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK2_ATLAS01big));
					atlas_main = assets.get(ex01Types.MAIN_ATLASABACK2_ATLAS01big);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK2_SKIN01big));
					skin_main = assets.get(ex01Types.MAIN_ATLASABACK2_SKIN01big);

				} else if(level_int>32 && level_int<49){
					no_background = 3;
					assets.load(ex01Types.MAIN_ATLASABACK3_ATLAS01big, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASABACK3_SKIN01big, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK3_ATLAS01big));
					atlas_main = assets.get(ex01Types.MAIN_ATLASABACK3_ATLAS01big);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK3_SKIN01big));
					skin_main = assets.get(ex01Types.MAIN_ATLASABACK3_SKIN01big);

				} else if(level_int>48 && level_int<65){
					no_background = 4;
					assets.load(ex01Types.MAIN_ATLASABACK4_ATLAS01big, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASABACK4_SKIN01big, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK4_ATLAS01big));
					atlas_main = assets.get(ex01Types.MAIN_ATLASABACK4_ATLAS01big);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK4_SKIN01big));
					skin_main = assets.get(ex01Types.MAIN_ATLASABACK4_SKIN01big);
				}
			}break;
			case 1:
			{
				if(level_int<17){
					no_background = 1;
					assets.load(ex01Types.MAIN_ATLASBBACK1_ATLAS01big, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASBBACK1_SKIN01big, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK1_ATLAS01big));
					atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK1_ATLAS01big);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK1_SKIN01big));
					skin_main = assets.get(ex01Types.MAIN_ATLASBBACK1_SKIN01big);

				} else if(level_int>16 && level_int<33){
					no_background = 2;
					assets.load(ex01Types.MAIN_ATLASBBACK2_ATLAS01big, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASBBACK2_SKIN01big, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK2_ATLAS01big));
					atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK2_ATLAS01big);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK2_SKIN01big));
					skin_main = assets.get(ex01Types.MAIN_ATLASBBACK2_SKIN01big);

				} else if(level_int>32 && level_int<49){
					no_background = 3;
					assets.load(ex01Types.MAIN_ATLASBBACK3_ATLAS01big, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASBBACK3_SKIN01big, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK3_ATLAS01big));
					atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK3_ATLAS01big);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK3_SKIN01big));
					skin_main = assets.get(ex01Types.MAIN_ATLASBBACK3_SKIN01big);

				} else if(level_int>48 && level_int<65){
					no_background = 4;
					assets.load(ex01Types.MAIN_ATLASBBACK4_ATLAS01big, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASBBACK4_SKIN01big, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK4_ATLAS01big));
					atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK4_ATLAS01big);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK4_SKIN01big));
					skin_main = assets.get(ex01Types.MAIN_ATLASBBACK4_SKIN01big);
				}
			}break;
			case 2:
			{
				if(level_int<17){
					no_background = 1;
					assets.load(ex01Types.MAIN_ATLASCBACK1_ATLAS01big, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASCBACK1_SKIN01big, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK1_ATLAS01big));
					atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK1_ATLAS01big);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK1_SKIN01big));
					skin_main = assets.get(ex01Types.MAIN_ATLASCBACK1_SKIN01big);

				} else if(level_int>16 && level_int<33){
					no_background = 2;
					assets.load(ex01Types.MAIN_ATLASCBACK2_ATLAS01big, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASCBACK2_SKIN01big, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK2_ATLAS01big));
					atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK2_ATLAS01big);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK2_SKIN01big));
					skin_main = assets.get(ex01Types.MAIN_ATLASCBACK2_SKIN01big);

				} else if(level_int>32 && level_int<49){
					no_background = 3;
					assets.load(ex01Types.MAIN_ATLASCBACK3_ATLAS01big, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASCBACK3_SKIN01big, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK3_ATLAS01big));
					atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK3_ATLAS01big);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK3_SKIN01big));
					skin_main = assets.get(ex01Types.MAIN_ATLASCBACK3_SKIN01big);

				} else if(level_int>48 && level_int<65){
					no_background = 4;
					assets.load(ex01Types.MAIN_ATLASCBACK4_ATLAS01big, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASCBACK4_SKIN01big, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK4_ATLAS01big));
					atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK4_ATLAS01big);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK4_SKIN01big));
					skin_main = assets.get(ex01Types.MAIN_ATLASCBACK4_SKIN01big);
				}
			}
			break;default:break;
		}
	}

	public void LoadAllTexturesPrimary_01medium(int type_spacecraft, int level_int){
		if(assets!=null){
			assets.clear();
			assets.finishLoading();
			assets.dispose();
		}
		assets = new AssetManager();
		switch(type_spacecraft){
			case 0:
			{
				if(level_int<17){
					no_background = 1;
					assets.load(ex01Types.MAIN_ATLASABACK1_ATLAS02medium, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASABACK1_SKIN02medium, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK1_ATLAS02medium));
					atlas_main = assets.get(ex01Types.MAIN_ATLASABACK1_ATLAS02medium);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK1_SKIN02medium));
					skin_main = assets.get(ex01Types.MAIN_ATLASABACK1_SKIN02medium);

				} else if(level_int>16 && level_int<33){
					no_background = 2;
					assets.load(ex01Types.MAIN_ATLASABACK2_ATLAS02medium, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASABACK2_SKIN02medium, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK2_ATLAS02medium));
					atlas_main = assets.get(ex01Types.MAIN_ATLASABACK2_ATLAS02medium);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK2_SKIN02medium));
					skin_main = assets.get(ex01Types.MAIN_ATLASABACK2_SKIN02medium);

				} else if(level_int>32 && level_int<49){
					no_background = 3;
					assets.load(ex01Types.MAIN_ATLASABACK3_ATLAS02medium, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASABACK3_SKIN02medium, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK3_ATLAS02medium));
					atlas_main = assets.get(ex01Types.MAIN_ATLASABACK3_ATLAS02medium);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK3_SKIN02medium));
					skin_main = assets.get(ex01Types.MAIN_ATLASABACK3_SKIN02medium);

				} else if(level_int>48 && level_int<65){
					no_background = 4;
					assets.load(ex01Types.MAIN_ATLASABACK4_ATLAS02medium, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASABACK4_SKIN02medium, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK4_ATLAS02medium));
					atlas_main = assets.get(ex01Types.MAIN_ATLASABACK4_ATLAS02medium);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK4_SKIN02medium));
					skin_main = assets.get(ex01Types.MAIN_ATLASABACK4_SKIN02medium);
				}
			}break;
			case 1:
			{
				if(level_int<17){
					no_background = 1;
					assets.load(ex01Types.MAIN_ATLASBBACK1_ATLAS02medium, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASBBACK1_SKIN02medium, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK1_ATLAS02medium));
					atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK1_ATLAS02medium);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK1_SKIN02medium));
					skin_main = assets.get(ex01Types.MAIN_ATLASBBACK1_SKIN02medium);

				} else if(level_int>16 && level_int<33){
					no_background = 2;
					assets.load(ex01Types.MAIN_ATLASBBACK2_ATLAS02medium, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASBBACK2_SKIN02medium, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK2_ATLAS02medium));
					atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK2_ATLAS02medium);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK2_SKIN02medium));
					skin_main = assets.get(ex01Types.MAIN_ATLASBBACK2_SKIN02medium);

				} else if(level_int>32 && level_int<49){
					no_background = 3;
					assets.load(ex01Types.MAIN_ATLASBBACK3_ATLAS02medium, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASBBACK3_SKIN02medium, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK3_ATLAS02medium));
					atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK3_ATLAS02medium);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK3_SKIN02medium));
					skin_main = assets.get(ex01Types.MAIN_ATLASBBACK3_SKIN02medium);

				} else if(level_int>48 && level_int<65){
					no_background = 4;
					assets.load(ex01Types.MAIN_ATLASBBACK4_ATLAS02medium, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASBBACK4_SKIN02medium, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK4_ATLAS02medium));
					atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK4_ATLAS02medium);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK4_SKIN02medium));
					skin_main = assets.get(ex01Types.MAIN_ATLASBBACK4_SKIN02medium);
				}
			}break;
			case 2:
			{
				if(level_int<17){
					no_background = 1;
					assets.load(ex01Types.MAIN_ATLASCBACK1_ATLAS02medium, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASCBACK1_SKIN02medium, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK1_ATLAS02medium));
					atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK1_ATLAS02medium);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK1_SKIN02medium));
					skin_main = assets.get(ex01Types.MAIN_ATLASCBACK1_SKIN02medium);

				} else if(level_int>16 && level_int<33){
					no_background = 2;
					assets.load(ex01Types.MAIN_ATLASCBACK2_ATLAS02medium, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASCBACK2_SKIN02medium, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK2_ATLAS02medium));
					atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK2_ATLAS02medium);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK2_SKIN02medium));
					skin_main = assets.get(ex01Types.MAIN_ATLASCBACK2_SKIN02medium);

				} else if(level_int>32 && level_int<49){
					no_background = 3;
					assets.load(ex01Types.MAIN_ATLASCBACK3_ATLAS02medium, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASCBACK3_SKIN02medium, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK3_ATLAS02medium));
					atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK3_ATLAS02medium);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK3_SKIN02medium));
					skin_main = assets.get(ex01Types.MAIN_ATLASCBACK3_SKIN02medium);

				} else if(level_int>48 && level_int<65){
					no_background = 4;
					assets.load(ex01Types.MAIN_ATLASCBACK4_ATLAS02medium, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASCBACK4_SKIN02medium, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK4_ATLAS02medium));
					atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK4_ATLAS02medium);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK4_SKIN02medium));
					skin_main = assets.get(ex01Types.MAIN_ATLASCBACK4_SKIN02medium);
				}
			}
			break;default:break;
		}
	}

	public void LoadAllTexturesPrimary_01small(int type_spacecraft, int level_int){
		if(assets!=null){
			assets.clear();
			assets.finishLoading();
			assets.dispose();
		}
		assets = new AssetManager();
		switch(type_spacecraft){
			case 0:
			{
				if(level_int<17){
					no_background = 1;
					assets.load(ex01Types.MAIN_ATLASABACK1_ATLAS03small, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASABACK1_SKIN03small, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK1_ATLAS03small));
					atlas_main = assets.get(ex01Types.MAIN_ATLASABACK1_ATLAS03small);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK1_SKIN03small));
					skin_main = assets.get(ex01Types.MAIN_ATLASABACK1_SKIN03small);

				} else if(level_int>16 && level_int<33){
					no_background = 2;
					assets.load(ex01Types.MAIN_ATLASABACK2_ATLAS03small, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASABACK2_SKIN03small, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK2_ATLAS03small));
					atlas_main = assets.get(ex01Types.MAIN_ATLASABACK2_ATLAS03small);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK2_SKIN03small));
					skin_main = assets.get(ex01Types.MAIN_ATLASABACK2_SKIN03small);

				} else if(level_int>32 && level_int<49){
					no_background = 3;
					assets.load(ex01Types.MAIN_ATLASABACK3_ATLAS03small, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASABACK3_SKIN03small, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK3_ATLAS03small));
					atlas_main = assets.get(ex01Types.MAIN_ATLASABACK3_ATLAS03small);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK3_SKIN03small));
					skin_main = assets.get(ex01Types.MAIN_ATLASABACK3_SKIN03small);

				} else if(level_int>48 && level_int<65){
					no_background = 4;
					assets.load(ex01Types.MAIN_ATLASABACK4_ATLAS03small, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASABACK4_SKIN03small, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK4_ATLAS03small));
					atlas_main = assets.get(ex01Types.MAIN_ATLASABACK4_ATLAS03small);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASABACK4_SKIN03small));
					skin_main = assets.get(ex01Types.MAIN_ATLASABACK4_SKIN03small);
				}
			}break;
			case 1:
			{
				if(level_int<17){
					no_background = 1;
					assets.load(ex01Types.MAIN_ATLASBBACK1_ATLAS03small, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASBBACK1_SKIN03small, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK1_ATLAS03small));
					atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK1_ATLAS03small);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK1_SKIN03small));
					skin_main = assets.get(ex01Types.MAIN_ATLASBBACK1_SKIN03small);

				} else if(level_int>16 && level_int<33){
					no_background = 2;
					assets.load(ex01Types.MAIN_ATLASBBACK2_ATLAS03small, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASBBACK2_SKIN03small, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK2_ATLAS03small));
					atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK2_ATLAS03small);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK2_SKIN03small));
					skin_main = assets.get(ex01Types.MAIN_ATLASBBACK2_SKIN03small);

				} else if(level_int>32 && level_int<49){
					no_background = 3;
					assets.load(ex01Types.MAIN_ATLASBBACK3_ATLAS03small, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASBBACK3_SKIN03small, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK3_ATLAS03small));
					atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK3_ATLAS03small);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK3_SKIN03small));
					skin_main = assets.get(ex01Types.MAIN_ATLASBBACK3_SKIN03small);

				} else if(level_int>48 && level_int<65){
					no_background = 4;
					assets.load(ex01Types.MAIN_ATLASBBACK4_ATLAS03small, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASBBACK4_SKIN03small, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK4_ATLAS03small));
					atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK4_ATLAS03small);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK4_SKIN03small));
					skin_main = assets.get(ex01Types.MAIN_ATLASBBACK4_SKIN03small);
				}
			}break;
			case 2:
			{
				if(level_int<17){
					no_background = 1;
					assets.load(ex01Types.MAIN_ATLASCBACK1_ATLAS03small, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASCBACK1_SKIN03small, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK1_ATLAS03small));
					atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK1_ATLAS03small);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK1_SKIN03small));
					skin_main = assets.get(ex01Types.MAIN_ATLASCBACK1_SKIN03small);

				} else if(level_int>16 && level_int<33){
					no_background = 2;
					assets.load(ex01Types.MAIN_ATLASCBACK2_ATLAS03small, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASCBACK2_SKIN03small, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK2_ATLAS03small));
					atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK2_ATLAS03small);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK2_SKIN03small));
					skin_main = assets.get(ex01Types.MAIN_ATLASCBACK2_SKIN03small);

				} else if(level_int>32 && level_int<49){
					no_background = 3;
					assets.load(ex01Types.MAIN_ATLASCBACK3_ATLAS03small, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASCBACK3_SKIN03small, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK3_ATLAS03small));
					atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK3_ATLAS03small);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK3_SKIN03small));
					skin_main = assets.get(ex01Types.MAIN_ATLASCBACK3_SKIN03small);

				} else if(level_int>48 && level_int<65){
					no_background = 4;
					assets.load(ex01Types.MAIN_ATLASCBACK4_ATLAS03small, TextureAtlas.class);
					assets.load(ex01Types.MAIN_ATLASCBACK4_SKIN03small, Skin.class);
					assets.finishLoading();

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK4_ATLAS03small));
					atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK4_ATLAS03small);

					do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
					while(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK4_SKIN03small));
					skin_main = assets.get(ex01Types.MAIN_ATLASCBACK4_SKIN03small);
				}
			}
			break;default:break;
		}
	}

	public void LoadAllTexturesSuperficial_01big(int type_spacecraft, int level_int){
		switch(type_spacecraft){
			case 0:
			{
				if(level_int<17){
					no_background = 1;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK1_ATLAS01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASABACK1_ATLAS01big); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK1_SKIN01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASABACK1_SKIN01big); }

				} else if(level_int>16 && level_int<33){
					no_background = 2;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK2_ATLAS01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASABACK2_ATLAS01big); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK2_SKIN01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASABACK2_SKIN01big); }

				} else if(level_int>32 && level_int<49){
					no_background = 3;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK3_ATLAS01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASABACK3_ATLAS01big); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK3_SKIN01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASABACK3_SKIN01big); }

				} else if(level_int>48 && level_int<65){
					no_background = 4;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK4_ATLAS01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASABACK4_ATLAS01big); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK4_SKIN01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASABACK4_SKIN01big); }
				}
			}break;
			case 1:
			{
				if(level_int<17){
					no_background = 1;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK1_ATLAS01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK1_ATLAS01big); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK1_SKIN01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASBBACK1_SKIN01big); }

				} else if(level_int>16 && level_int<33){
					no_background = 2;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK2_ATLAS01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK2_ATLAS01big); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK2_SKIN01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASBBACK2_SKIN01big); }

				} else if(level_int>32 && level_int<49){
					no_background = 3;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK3_ATLAS01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK3_ATLAS01big); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK3_SKIN01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASBBACK3_SKIN01big); }

				} else if(level_int>48 && level_int<65){
					no_background = 4;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK4_ATLAS01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK4_ATLAS01big); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK4_SKIN01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASBBACK4_SKIN01big); }
				}
			}break;
			case 2:
			{
				if(level_int<17){
					no_background = 1;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK1_ATLAS01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK1_ATLAS01big); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK1_SKIN01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASCBACK1_SKIN01big); }

				} else if(level_int>16 && level_int<33){
					no_background = 2;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK2_ATLAS01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK2_ATLAS01big); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK2_SKIN01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASCBACK2_SKIN01big); }

				} else if(level_int>32 && level_int<49){
					no_background = 3;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK3_ATLAS01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK3_ATLAS01big); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK3_SKIN01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASCBACK3_SKIN01big); }

				} else if(level_int>48 && level_int<65){
					no_background = 4;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK4_ATLAS01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK4_ATLAS01big); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK4_SKIN01big)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASCBACK4_SKIN01big); }
				}
			}
			break;default:break;
		}
		if(!assets.isLoaded(ex01Types.MAIN_ATLAS_SEC_ATLAS01big)){ try { Thread.sleep(250); }

		catch (Exception e) { e.printStackTrace(); }
		} else { atlas_secondary = assets.get(ex01Types.MAIN_ATLAS_SEC_ATLAS01big); }
	}

	public void LoadAllTexturesSuperficial_01medium(int type_spacecraft, int level_int){
		switch(type_spacecraft){
			case 0:
			{
				if(level_int<17){
					no_background = 1;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK1_ATLAS02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASABACK1_ATLAS02medium); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK1_SKIN02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASABACK1_SKIN02medium); }

				} else if(level_int>16 && level_int<33){
					no_background = 2;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK2_ATLAS02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASABACK2_ATLAS02medium); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK2_SKIN02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASABACK2_SKIN02medium); }

				} else if(level_int>32 && level_int<49){
					no_background = 3;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK3_ATLAS02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASABACK3_ATLAS02medium); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK3_SKIN02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASABACK3_SKIN02medium); }

				} else if(level_int>48 && level_int<65){
					no_background = 4;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK4_ATLAS02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASABACK4_ATLAS02medium); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK4_SKIN02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASABACK4_SKIN02medium); }
				}
			}break;
			case 1:
			{
				if(level_int<17){
					no_background = 1;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK1_ATLAS02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK1_ATLAS02medium); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK1_SKIN02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASBBACK1_SKIN02medium); }

				} else if(level_int>16 && level_int<33){
					no_background = 2;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK2_ATLAS02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK2_ATLAS02medium); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK2_SKIN02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASBBACK2_SKIN02medium); }

				} else if(level_int>32 && level_int<49){
					no_background = 3;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK3_ATLAS02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK3_ATLAS02medium); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK3_SKIN02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASBBACK3_SKIN02medium); }

				} else if(level_int>48 && level_int<65){
					no_background = 4;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK4_ATLAS02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK4_ATLAS02medium); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK4_SKIN02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASBBACK4_SKIN02medium); }
				}
			}break;
			case 2:
			{
				if(level_int<17){
					no_background = 1;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK1_ATLAS02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK1_ATLAS02medium); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK1_SKIN02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASCBACK1_SKIN02medium); }

				} else if(level_int>16 && level_int<33){
					no_background = 2;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK2_ATLAS02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK2_ATLAS02medium); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK2_SKIN02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASCBACK2_SKIN02medium); }

				} else if(level_int>32 && level_int<49){
					no_background = 3;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK3_ATLAS02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK3_ATLAS02medium); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK3_SKIN02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASCBACK3_SKIN02medium); }

				} else if(level_int>48 && level_int<65){
					no_background = 4;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK4_ATLAS02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK4_ATLAS02medium); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK4_SKIN02medium)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASCBACK4_SKIN02medium); }
				}
			}
			break;default:break;
		}
		if(!assets.isLoaded(ex01Types.MAIN_ATLAS_SEC_ATLAS02medium)){ try { Thread.sleep(250); }

		catch (Exception e) { e.printStackTrace(); }
		} else { atlas_secondary = assets.get(ex01Types.MAIN_ATLAS_SEC_ATLAS02medium); }
	}

	public void LoadAllTexturesSuperficial_01small(int type_spacecraft, int level_int){
		switch(type_spacecraft){
			case 0:
			{
				if(level_int<17){
					no_background = 1;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK1_ATLAS03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASABACK1_ATLAS03small); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK1_SKIN03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASABACK1_SKIN03small); }

				} else if(level_int>16 && level_int<33){
					no_background = 2;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK2_ATLAS03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASABACK2_ATLAS03small); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK2_SKIN03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASABACK2_SKIN03small); }

				} else if(level_int>32 && level_int<49){
					no_background = 3;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK3_ATLAS03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASABACK3_ATLAS03small); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK3_SKIN03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASABACK3_SKIN03small); }

				} else if(level_int>48 && level_int<65){
					no_background = 4;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK4_ATLAS03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASABACK4_ATLAS03small); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASABACK4_SKIN03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASABACK4_SKIN03small); }
				}
			}break;
			case 1:
			{
				if(level_int<17){
					no_background = 1;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK1_ATLAS03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK1_ATLAS03small); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK1_SKIN03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASBBACK1_SKIN03small); }

				} else if(level_int>16 && level_int<33){
					no_background = 2;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK2_ATLAS03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK2_ATLAS03small); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK2_SKIN03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASBBACK2_SKIN03small); }

				} else if(level_int>32 && level_int<49){
					no_background = 3;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK3_ATLAS03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK3_ATLAS03small); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK3_SKIN03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASBBACK3_SKIN03small); }

				} else if(level_int>48 && level_int<65){
					no_background = 4;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK4_ATLAS03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASBBACK4_ATLAS03small); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASBBACK4_SKIN03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASBBACK4_SKIN03small); }
				}
			}break;
			case 2:
			{
				if(level_int<17){
					no_background = 1;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK1_ATLAS03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK1_ATLAS03small); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK1_SKIN03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASCBACK1_SKIN03small); }

				} else if(level_int>16 && level_int<33){
					no_background = 2;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK2_ATLAS03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK2_ATLAS03small); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK2_SKIN03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASCBACK2_SKIN03small); }

				} else if(level_int>32 && level_int<49){
					no_background = 3;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK3_ATLAS03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK3_ATLAS03small); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK3_SKIN03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASCBACK3_SKIN03small); }

				} else if(level_int>48 && level_int<65){
					no_background = 4;

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK4_ATLAS03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { atlas_main = assets.get(ex01Types.MAIN_ATLASCBACK4_ATLAS03small); }

					if(!assets.isLoaded(ex01Types.MAIN_ATLASCBACK4_SKIN03small)){ try
					{ Thread.sleep(250); }
					catch (Exception e) { e.printStackTrace(); }
					} else { skin_main = assets.get(ex01Types.MAIN_ATLASCBACK4_SKIN03small); }
				}
			}
			break;default:break;
		}
		if(!assets.isLoaded(ex01Types.MAIN_ATLAS_SEC_ATLAS03small)){ try { Thread.sleep(250); }

		catch (Exception e) { e.printStackTrace(); }
		} else { atlas_secondary = assets.get(ex01Types.MAIN_ATLAS_SEC_ATLAS03small); }
	}

	public void LoadAllTexturesSecondary_01big(){
		assets.load(
				ex01Types.MAIN_ATLAS_SEC_ATLAS01big,
				TextureAtlas.class);
		assets.load(
				ex01Types.hud_angler_font01big,
				BitmapFont.class);
		assets.load(
				ex01Types.hud_angler_fontp01big,
				BitmapFont.class);
		assets.load(
				ex01Types.hud_angler_font_title01big,
				BitmapFont.class);
		assets.finishLoading();

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!assets.isLoaded(ex01Types.MAIN_ATLAS_SEC_ATLAS01big));
		atlas_secondary = assets.get(ex01Types.MAIN_ATLAS_SEC_ATLAS01big);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!assets.isLoaded(ex01Types.hud_angler_font01big));
		hud_angler_font_bitmap = assets.get(ex01Types.hud_angler_font01big);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!assets.isLoaded(ex01Types.hud_angler_fontp01big));
		hud_angler_fontp_bitmap = assets.get(ex01Types.hud_angler_fontp01big);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!assets.isLoaded(ex01Types.hud_angler_font_title01big));
		hud_angler_font_title_bitmap = assets.get(ex01Types.hud_angler_font_title01big);

		atlas_main.getTextures().first().bind();
		atlas_secondary.getTextures().first().bind();
	}

	public void LoadAllTexturesSecondary_01medium(){
		assets.load(
				ex01Types.MAIN_ATLAS_SEC_ATLAS02medium,
				TextureAtlas.class);
		assets.load(
				ex01Types.hud_angler_font02medium,
				BitmapFont.class);
		assets.load(
				ex01Types.hud_angler_fontp02medium,
				BitmapFont.class);
		assets.load(
				ex01Types.hud_angler_font_title02medium,
				BitmapFont.class);
		assets.finishLoading();

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!assets.isLoaded(ex01Types.MAIN_ATLAS_SEC_ATLAS02medium));
		atlas_secondary = assets.get(ex01Types.MAIN_ATLAS_SEC_ATLAS02medium);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!assets.isLoaded(ex01Types.hud_angler_font02medium));
		hud_angler_font_bitmap = assets.get(ex01Types.hud_angler_font02medium);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!assets.isLoaded(ex01Types.hud_angler_fontp02medium));
		hud_angler_fontp_bitmap = assets.get(ex01Types.hud_angler_fontp02medium);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!assets.isLoaded(ex01Types.hud_angler_font_title02medium));
		hud_angler_font_title_bitmap = assets.get(ex01Types.hud_angler_font_title02medium);

		atlas_main.getTextures().first().bind();
		atlas_secondary.getTextures().first().bind();
	}

	public void LoadAllTexturesSecondary_01small(){
		assets.load(
				ex01Types.MAIN_ATLAS_SEC_ATLAS03small,
				TextureAtlas.class);
		assets.load(
				ex01Types.hud_angler_font03small,
				BitmapFont.class);
		assets.load(
				ex01Types.hud_angler_fontp03small,
				BitmapFont.class);
		assets.load(
				ex01Types.hud_angler_font_title03small,
				BitmapFont.class);
		assets.finishLoading();

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!assets.isLoaded(ex01Types.MAIN_ATLAS_SEC_ATLAS03small));
		atlas_secondary = assets.get(ex01Types.MAIN_ATLAS_SEC_ATLAS03small);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!assets.isLoaded(ex01Types.hud_angler_font03small));
		hud_angler_font_bitmap = assets.get(ex01Types.hud_angler_font03small);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!assets.isLoaded(ex01Types.hud_angler_fontp03small));
		hud_angler_fontp_bitmap = assets.get(ex01Types.hud_angler_fontp03small);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!assets.isLoaded(ex01Types.hud_angler_font_title03small));
		hud_angler_font_title_bitmap = assets.get(ex01Types.hud_angler_font_title03small);

		atlas_main.getTextures().first().bind();
		atlas_secondary.getTextures().first().bind();
	}
}

