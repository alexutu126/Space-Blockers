//WMS3 2015

package com.cryotrax.game;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.audio.Sound;

//----------------------------------------------------------------

public class ex01CryoshipPrincipial extends Sprite {
	public static final float CENTER_CAMERA_X = 15f;
	public static final float CENTER_CAMERA_Y = -10f;
	public static final float LASER_DELAY_INBETWEEN = 0.10f;
	public static final float hscreenadd = 800f / 480f * 25f / 2f;
	public static final float SPACESHIP_WIDTH_X = 3.95f;
	public static final float SPACESHIP_WIDTH_Y = 3.95f;
	public static final float SPACESHIP_POS_X = CENTER_CAMERA_X - SPACESHIP_WIDTH_X / 2;
	public static final float SPACESHIP_POS_Y = -22f;
	public static final float SRECT_CIRCLE_RADIUS = 1.1f;
	public static final float SRECT_COL_X = CENTER_CAMERA_X;
	public static final float SRECT_COL_Y = CENTER_CAMERA_Y - 10.20f;
	public static final float ESHOCK1_WIDTH = 5f;
	public static final float ESHOCK1_HEIGHT = 5f;
	public static final float ESHOCK2_WIDTH = 3.75f;
	public static final float ESHOCK2_HEIGHT = 3.75f;
	public static final float ESHOCK3_WIDTH = 4.50f;
	public static final float ESHOCK3_HEIGHT = 4.50f;
	public static final float SPRITE_EXPLOSION_WIDTH = 17.5f;
	public static final float SPRITE_EXPLOSION_HEIGHT = 17.5f;
	public static final float THRUSTERS_WIDTH = 3.0f;
	public static final float THRUSTERS_HEIGHT = 3.0f;
	public static final float THRUSTERS_POS_X = CENTER_CAMERA_X - THRUSTERS_WIDTH/2f;
	public static final float THRUSTERS_POS_Y = CENTER_CAMERA_Y - 13.75f;
	public static final float THRUSTERS_ALPHA = 0.90f;
	public static final float THRUSTERS_ORIGIN_X = THRUSTERS_WIDTH / 2;
	public static final float THRUSTERS_ORIGIN_Y = THRUSTERS_HEIGHT - 1f;
	public static final float ACCELERATION_X = 0.025f;
	public static final float ACCELERATION_Y = 0.000f;
	public static final float VELOCITY_X = 0.5f;
	public static final float VELOCITY_Y = 0.2f;
	public static final float ORIG_THRUSTER_W = THRUSTERS_WIDTH;
	public static final float ORIG_THRUSTER_H = THRUSTERS_HEIGHT;
	public static final float ORIG_THRUSTER_X = THRUSTERS_POS_X;
	public static final float ORIG_THRUSTER_Y = -2.05f;
	//  spaceship cheapest world speed acceleration is not that good
	public static final float WORLD_VELOCITY1 = 0.0065f;
	//  spaceship cheapest world speed acceleration is not that medium
	public static final float WORLD_VELOCITY2 = 0.0065f;
	//  spaceship cheapest world speed acceleration is not that fast
	public static final float WORLD_VELOCITY3 = 0.0065f;
	public static final float WORLD_BASER_ALL = 0.0400f;// 85.0% din ala de sus
	public static final float WORLD_VELOCITY_SHOOT1 = 0.065f;
	public static final float WORLD_VELOCITY_SHOOT2 = 0.075f;
	public static final float WORLD_VELOCITY_SHOOT3 = 0.085f;
	public static final float SCALE_X = 1f;
	public static final float SCALE_Y = 1f;
	public static final float INIT_THRUSTER_DECEL_BIG_Y = 3f;
	public static final float SPACESHIP_ACCEL_VOLUME_BASE = 0.12f;
	public static final float SPACESHIP_ACCEL_VOLUME_OVERLAP_BASE = 0.12f;
	public static final float SPACESHIP_ACCEL_VOLUME = 0.12f;
	public static final float SPACESHIP_ACCEL_VOLUME_OVERLAP = 0.12f;
	public static final float SPACESHIP_ACCEL_VOLUME_S = 0.12f;
	public static final float SPACESHIP_ACCEL_VOLUME_OVERLAP_S = 0.12f;
	public static final float SPACESHIP_PITCH_VOLUME = 1.001f;
	public static final float SPACESHIP_PITCH_VOLUME_OVERLAP = 1.00f;
	public static final float SPACESHIP_PITCH_VOLUME_REAL = 1.001f;
	public static final float SPACESHIP_ACCEL_VOLUME_REAL = 0.11f;
	public static final float ELAPSED_TIME = 0f;
	public static final float ACCEL3 =  0.1000f;
	public static final float ACCEL2 =  0.1000f;
	public static final float ACCEL1 =  0.1000f;
	//  initial speed of 3rd spaceship
	public static final float VELOCITY3 = 0.04f;
	//  initial speed of 2nd spaceship
	public static final float VELOCITY2 = 0.03f;
	//  initial speed of 1st spaceship
	public static final float VELOCITY1 = 0.02f;
	public static final float INIT_VELOCITY_V = 0f;
	public static final float XACCEL3 = 0.2250f;
	public static final float XACCEL2 = 0.2000f;
	public static final float XACCEL1 = 0.1500f;
	public static final float COUNTER_PASSED_TOUCH_UP12 = 0.025f*12;
	public static final float ELAPSE_FORERUN = 11 * 0.025f;
	public static final float NO_SHOOTINGS = 16;
	public static final float SPRITE_EXPL_DELTA =
			(SPRITE_EXPLOSION_WIDTH - SPACESHIP_WIDTH_X) / 2f;
	public static final int CNT_ESHOCK1 = 0;
	public static final int CNT_ESHOCK2 = 0;
	public static final int CNT_ESHOCK3 = 0;
	public static final int CNT_PASSED_TCH_UP = 0;
	public static final int CNT_EXPLOSION = 0;
	public static final int HOW_MUCH_ADV_WORLD_UNITS_H = 0;
	public static final int PASSED_SINCE_LAST_LASER_SHOOT = 0;
	public static final int PASSED_SINCE_LAST_SESSION = 0;
	public static final int DELTA_SINCE_ACCEL_PLAYING = 0;
	public static final int DELTA_SINCE_ACCEL_PLAYING_OVERLAY = 0;
	public static final int COUNTER_SHOOTS_FIRED_SESSION = 0;
	public static final int FUD_ACCEL_COUNT = 0;
	public static final int WORLD_ACCEL_COUNT = 0;
	public static final int IS_STILL_ROLLING = 0;
	public static final int CURRENT_FRAME = 1;
	public static final int MAX_FUD_ACCEL = 70;
	public static final String DATA_SPACESHIP_VERT = "data/shaders/spaceship.vert";
	public static final String DATA_SPACESHIP_FRAG = "data/shaders/spaceship.frag";
	public static final String DATA_GRAYSCALE_LASERS_VERT = "data/shaders/grayscale_lasers.vert";
	public static final String DATA_GRAYSCALE_LASERS_FRAG = "data/shaders/grayscale_lasers.frag";
	public static final int SHOOTSNO_PER_SHOOT = 1;
	public float counterElectroShock1 = CNT_ESHOCK1;
	public float counterElectroShock2 = CNT_ESHOCK2;
	public float counterElectroShock3 = CNT_ESHOCK3;
	public float counter_passed_touch_up = CNT_PASSED_TCH_UP;
	public float counter_explosion = CNT_EXPLOSION;
	public float elapsed_time_saved;
	public float finalDest3;
	public float originalY3;
	public float how_much_advanced_world_units_h = HOW_MUCH_ADV_WORLD_UNITS_H;
	// maximum coord to the left that the ship can take
	public float maxLeft;
	// maximum coord to the right that the ship can take
	public float maxRight;
	public float world_velocity;
	public float world_velocity_shoot;
	public float world_velocity_original;
	public float world_velocity_original_shoot;
	public float originalThrusterW;
	public float originalThrusterH;
	public float originalThrusterY;
	public float originalThrusterX;
	public float originalThrusterDY;
	public float originalThrusterDX;
	public float scaleX = SCALE_X;
	public float scaleY = SCALE_Y;
	public float initialThrusterDecelBigY = INIT_THRUSTER_DECEL_BIG_Y;
	// used when we reposition the lateral position of the spaceship and thruster
	public float lateral_distance_reseter;
	public float lateral_distance_reseter_collision;
	public float passed_since_last_laser_shoot =
			PASSED_SINCE_LAST_LASER_SHOOT;
	public float passed_since_last_session =
			PASSED_SINCE_LAST_SESSION;
	public float spaceship_acceleration_volumeBASE =
			SPACESHIP_ACCEL_VOLUME_BASE;
	public float spaceship_acceleration_volume_overlapBASE =
			SPACESHIP_ACCEL_VOLUME_OVERLAP_BASE;
	public float spaceship_acceleration_volume =
			SPACESHIP_ACCEL_VOLUME;
	public float spaceship_acceleration_volume_real =
			SPACESHIP_ACCEL_VOLUME_REAL;
	public float spaceship_acceleration_volume_overlap =
			SPACESHIP_ACCEL_VOLUME_OVERLAP;
	public float spaceship_acceleration_volumeS =
			SPACESHIP_ACCEL_VOLUME_S;
	public float spaceship_acceleration_volume_overlapS =
			SPACESHIP_ACCEL_VOLUME_OVERLAP_S;
	public float spaceship_pitch_volume =
			SPACESHIP_PITCH_VOLUME;
	public float spaceship_pitch_volume_real =
			SPACESHIP_PITCH_VOLUME_REAL;
	public float spaceship_pitch_volume_overlap =
			SPACESHIP_PITCH_VOLUME_OVERLAP;
	public float delta_since_accel_playing =
			DELTA_SINCE_ACCEL_PLAYING;
	public float delta_since_accel_playing_overlay =
			DELTA_SINCE_ACCEL_PLAYING_OVERLAY;
	public float elapsedTime = ELAPSED_TIME;
	public float accel3 = ACCEL3;
	public float accel2 = ACCEL2;
	public float accel1 = ACCEL1;
	public float accel_baser;
	public float accel;
	public float velocity_baser;
	public float velocity3 = VELOCITY3;
	public float velocity2 = VELOCITY2;
	public float velocity1 = VELOCITY1;
	public float Xaccel3 = XACCEL3;
	public float Xaccel2 = XACCEL2;
	public float Xaccel1 = XACCEL1;
	public float Xaccel_baser;
	public float initialVelocityV = INIT_VELOCITY_V;
	public float spaceship_rect_widthp2;
	public float spaceship_rect_heightp208;
	public float MaxLeftPlus015;
	public float MaxLeftPlusLDRC;
	public float MaxLeftPlusLDRCm02;
	public float MaxLeftPlusLDRCp191;
	public float MaxRightMinus015;
	public float MaxRightPlusLDRC;
	public float MaxRightPlusLDRCm02;
	public float MaxRightPlusLDRCp191;
	public float MaxLeftp030;
	public float MaxRightm030;
	public float delta_64_srsz;
	public float advance;
	public float SPACE_ADJUST1 = ex01Types.TSPACE_ADJUST1;
	public float SPACE_ADJUST2 = ex01Types.TSPACE_ADJUST2;
	public float SPACE_ADJUST3 = ex01Types.TSPACE_ADJUST3;
	public float space_adjust;
	public boolean no_more_bullets = false;
	public boolean shock_active1 = false;
	public boolean shock_active2 = false;
	public boolean shock_active3 = false;
	public boolean give_electro_shock_activated = false;
	// for the else in NeedToChangeStart
	public boolean give_electro_shock_activated_denier = false;
	public boolean touched_left = false;
	public boolean touched_right = false;
	public boolean touched = false;
	public boolean finished_lateral_rotation = true;
	public boolean stopped_rotation_to_left;
	public boolean stopped_rotation_to_right;
	public boolean touch_upped = true;
	public boolean reversed_rotation = false;
	public boolean acceleration_overlap_playing = false;
	public boolean acceleration_overlap_playing_real = false;
	public boolean can_laser_shoot = false;
	public boolean was_going_left_last_time = false;
	public boolean was_going_right_last_time = false;
	public boolean pressed_accel = false;
	public boolean pressed_shoot = false;
	public boolean started_overlay = false;
	public boolean started_base = false;
	public boolean can_be_electrocuted = false;
	public boolean can_explode = false;
	// used for HUD replay display
	public boolean exploded = false;
	// used for HUD replay display
	public boolean exploded_finished = false;
	public boolean is_bullet_busy[] = new boolean[16];
	public boolean can_delta_overlay = false;
	public boolean accel_real_started = false;
	public int counter_shots_fired_session = COUNTER_SHOOTS_FIRED_SESSION;
	public int FUD_accel_count = FUD_ACCEL_COUNT;
	public int WORLD_accel_count = WORLD_ACCEL_COUNT;
	public int is_still_rolling = IS_STILL_ROLLING;
	public int currentFrame = CURRENT_FRAME;
	public int which_to_change, i, j, lasersj;
	public int type_spaceship;
	public int counter_shoot = 0;
	public long id_spaceship_acceleration;
	public long id_spaceship_acceleration_real;
	public long id_spaceship_acceleration_overlap;
	public Sprite thrusters;
	public Sprite sprite_explosion;
	public Sprite eShock1;
	public Sprite eShock2;
	public Sprite eShock3;
	public Sprite sprite;
	public Sprite sprite_base;
	public Sprite space1_bigS; // bigger texture for when we finish and we have to zoom
	public Sprite space2_bigS;
	public Sprite space3_bigS;
	public Animation animationSpaceship;
	public Animation animationSpaceship2;
	public Animation animationSpaceshipReverse;
	public Animation animationSpaceship2Reverse;
	public Animation explosionAnimation;
	public Animation explosionAnimationOver;
	public Animation explosionMicroAnimation;
	public Animation electroShockAnimation;
	public AtlasRegion middle;
	public AtlasRegion space1_bigAR;
	public AtlasRegion space2_bigAR;
	public AtlasRegion space3_bigAR;
	public TextureAtlas atlas_space_big;
	public TextureAtlas textureAtlas;
	public TextureAtlas textureAtlasSec;
	public TextureAtlas textureAtlasExplosion;
	public TextureAtlas textureAtlasExplosionOver;
	public TextureRegion[] MicroExplosion;
	public TextureRegion textureAtlas_laser;    	// texture used for laser base mesh 
	public TextureRegion textureAtlas_laserOver;	// texture used for laser mesh
	public TextureRegion thruster;
	public Texture texture_space_base;
	public Sound sound_power_down;
	public Sound sound_accelspace1_rev;
	public Sound sShock1;
	public Sound sShock2;
	public Sound sShock3;
	public Sound sShock1Explode;
	public Sound sShock2Explode;
	public Sound sShock3Explode;
	public Sound spaceship_accel_big;
	public Sound laser_shoot;
	public Sound blocker_hit_explosion;
	public Sound powerup_health_hit;
	public Sound powerup_bullets_hit;
	public Sound spaceship_explosion;
	public Sound spaceship_acceleration;
	public Sound spaceship_acceleration_real;
	public Sound spaceship_acceleration_overlap;
	public Music game_music;
	//collision detection rectangle and the two circles on the wings
	public Circle spaceship_rectangle_collision;
	// base mesh
	public Mesh mesh;
	// laser mesh
	public Mesh meshO;
	public Vector2 start;
	public Vector2 end;
	public Vector2 acceleration;
	public Vector2 velocity;
	// shader program
	public ShaderProgram shader;
	public ShaderProgram shader_base;
	// shader grey program;
	public ShaderProgram shaderGrey;
	public ArrayList<ex01CryoshipLaserShoot> laser_shoots;
	public ArrayList<Sprite> explosion_laser_blocker;
	public AssetManager load_spaceship;
	public TextureRegion[] SpaceshipRotateLeft = new TextureRegion[11];
	public TextureRegion[] SpaceshipRotateRight = new TextureRegion[11];
	public TextureRegion[] SpaceshipRotateLeftReverse = new TextureRegion[11];
	public TextureRegion[] SpaceshipRotateRightReverse = new TextureRegion[11];
	public TextureRegion[] SpriteExplosion = new TextureRegion[72];
	public TextureRegion[] SpriteExplosionOver = new TextureRegion[48];
	public TextureRegion[] SpriteElectroShock = new TextureRegion[16];
	public Runnable r_write_json;
	public Runnable r_write_json2;
	public Thread t_write_json;
	public Thread t_write_json2;
	public Timer ExplodeTimerTask = new Timer();
	public Timer ExplodeTimerTaskLoad = new Timer();
	public static TimerTask TimerTaskExploded;
	public ex01CryoHUDDisplay hudder;
	public ex01CryoshipLaserShoot lshoot;
	public _ex01CryotraxGame game_upper;

	public ex01CryoshipPrincipial(_ex01CryotraxGame game,
								  float delta_advance_h,
								  int type_spacecraft){

		laser_shoots = new ArrayList<ex01CryoshipLaserShoot>();
		game_upper = game;
		type_spaceship = type_spacecraft;
		finalDest3 = delta_advance_h;
		if(game_upper.cryo_game.screen_type==1) {
			Load_01big();
		} else if(game_upper.cryo_game.screen_type==2){
			Load_01medium();
		} else {
			Load_01small();
		}
	}
	
	public void SpaceshipFirstInit(){
		CreateFirstStartEnd();
		Create16Shootings();
		ExploderThreadInit();
	}

	public void Load_01big(){
		//load the assets
		load_spaceship = new AssetManager();

		load_spaceship.load(ex01Types.EXPLOSION_HUD_REPLAY01big, TextureAtlas.class);
		load_spaceship.load(ex01Types.EXPLOSION_ATLAS01big, TextureAtlas.class);
		load_spaceship.load(ex01Types.SOUND_ACCELSPACE1, Sound.class);
		load_spaceship.load(ex01Types.SOUND_ACCELSPACE2, Sound.class);
		load_spaceship.load(ex01Types.SOUND_POWERUP_BULLETS, Sound.class);
		load_spaceship.load(ex01Types.SOUND_POWERUP_HEALTH, Sound.class);
		load_spaceship.load(ex01Types.SOUND_LASER_SHOOT, Sound.class);
		load_spaceship.load(ex01Types.SOUND_SPACESHIP_EXPLOSION, Sound.class);
		load_spaceship.load(ex01Types.SOUND_BLOCKER_HIT_EXPLOSION, Sound.class);
		load_spaceship.load(ex01Types.SOUND_ELECTRO_SHOCK, Sound.class);
		load_spaceship.load(ex01Types.SOUND_ELECTRO_SHOCK_EXPLODE, Sound.class);
		load_spaceship.load(ex01Types.SOUND_POWER_DOWN, Sound.class);
		load_spaceship.load(ex01Types.SOUND_ACCELSPACE1_REV, Sound.class);
		load_spaceship.load(ex01Types.MUSIC_GAME1, Music.class);
		load_spaceship.finishLoading();

		//get the assets - sounds
		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_ACCELSPACE1));
		spaceship_acceleration = load_spaceship.get(ex01Types.SOUND_ACCELSPACE1);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_POWER_DOWN));
		sound_power_down = load_spaceship.get(ex01Types.SOUND_POWER_DOWN);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_ACCELSPACE1_REV));
		sound_accelspace1_rev = load_spaceship.get(ex01Types.SOUND_ACCELSPACE1_REV);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_ACCELSPACE2));
		spaceship_acceleration_real = load_spaceship.get(ex01Types.SOUND_ACCELSPACE2);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_POWERUP_BULLETS));
		powerup_bullets_hit = load_spaceship.get(ex01Types.SOUND_POWERUP_BULLETS);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_POWERUP_HEALTH));
		powerup_health_hit = load_spaceship.get(ex01Types.SOUND_POWERUP_HEALTH);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_LASER_SHOOT));
		laser_shoot = load_spaceship.get(ex01Types.SOUND_LASER_SHOOT);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_SPACESHIP_EXPLOSION));
		spaceship_explosion = load_spaceship.get(ex01Types.SOUND_SPACESHIP_EXPLOSION);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_BLOCKER_HIT_EXPLOSION));
		blocker_hit_explosion = load_spaceship.get(ex01Types.SOUND_BLOCKER_HIT_EXPLOSION);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.MUSIC_GAME1));
		game_music = load_spaceship.get(ex01Types.MUSIC_GAME1);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_ELECTRO_SHOCK));
		sShock1 = load_spaceship.get(ex01Types.SOUND_ELECTRO_SHOCK);
		sShock2 = load_spaceship.get(ex01Types.SOUND_ELECTRO_SHOCK);
		sShock3 = load_spaceship.get(ex01Types.SOUND_ELECTRO_SHOCK);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_ELECTRO_SHOCK_EXPLODE));
		sShock1Explode = load_spaceship.get(ex01Types.SOUND_ELECTRO_SHOCK_EXPLODE);
		sShock2Explode = load_spaceship.get(ex01Types.SOUND_ELECTRO_SHOCK_EXPLODE);
		sShock3Explode = load_spaceship.get(ex01Types.SOUND_ELECTRO_SHOCK_EXPLODE);
		//get the assets - load hud_replay atlas and explosion atlas
		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.EXPLOSION_HUD_REPLAY01big));

		atlas_space_big = load_spaceship.get(ex01Types.EXPLOSION_HUD_REPLAY01big);
		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.EXPLOSION_ATLAS01big));

		textureAtlasExplosion = load_spaceship.get(ex01Types.EXPLOSION_ATLAS01big);

		// load spaceship Sprite for when we finish the level
		LoadBigSpaceshipFinishers();
		// write to JSON loader after we exploded - we need a separate thread
		// so we don't block the render thread
		LoadExplosionThreadWriteJson();
	}

	public void Load_01medium(){
		//load the assets
		load_spaceship = new AssetManager();

		load_spaceship.load(ex01Types.EXPLOSION_HUD_REPLAY02medium, TextureAtlas.class);
		load_spaceship.load(ex01Types.EXPLOSION_ATLAS02medium, TextureAtlas.class);
		load_spaceship.load(ex01Types.SOUND_ACCELSPACE1, Sound.class);
		load_spaceship.load(ex01Types.SOUND_ACCELSPACE2, Sound.class);
		load_spaceship.load(ex01Types.SOUND_POWERUP_BULLETS, Sound.class);
		load_spaceship.load(ex01Types.SOUND_POWERUP_HEALTH, Sound.class);
		load_spaceship.load(ex01Types.SOUND_LASER_SHOOT, Sound.class);
		load_spaceship.load(ex01Types.SOUND_SPACESHIP_EXPLOSION, Sound.class);
		load_spaceship.load(ex01Types.SOUND_BLOCKER_HIT_EXPLOSION, Sound.class);
		load_spaceship.load(ex01Types.SOUND_ELECTRO_SHOCK, Sound.class);
		load_spaceship.load(ex01Types.SOUND_ELECTRO_SHOCK_EXPLODE, Sound.class);
		load_spaceship.load(ex01Types.SOUND_POWER_DOWN, Sound.class);
		load_spaceship.load(ex01Types.SOUND_ACCELSPACE1_REV, Sound.class);
		load_spaceship.load(ex01Types.MUSIC_GAME1, Music.class);
		load_spaceship.finishLoading();

		//get the assets - sounds
		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_ACCELSPACE1));
		spaceship_acceleration = load_spaceship.get(ex01Types.SOUND_ACCELSPACE1);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_POWER_DOWN));
		sound_power_down = load_spaceship.get(ex01Types.SOUND_POWER_DOWN);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_ACCELSPACE1_REV));
		sound_accelspace1_rev = load_spaceship.get(ex01Types.SOUND_ACCELSPACE1_REV);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_ACCELSPACE2));
		spaceship_acceleration_real = load_spaceship.get(ex01Types.SOUND_ACCELSPACE2);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_POWERUP_BULLETS));
		powerup_bullets_hit = load_spaceship.get(ex01Types.SOUND_POWERUP_BULLETS);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_POWERUP_HEALTH));
		powerup_health_hit = load_spaceship.get(ex01Types.SOUND_POWERUP_HEALTH);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_LASER_SHOOT));
		laser_shoot = load_spaceship.get(ex01Types.SOUND_LASER_SHOOT);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_SPACESHIP_EXPLOSION));
		spaceship_explosion = load_spaceship.get(ex01Types.SOUND_SPACESHIP_EXPLOSION);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_BLOCKER_HIT_EXPLOSION));
		blocker_hit_explosion = load_spaceship.get(ex01Types.SOUND_BLOCKER_HIT_EXPLOSION);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.MUSIC_GAME1));
		game_music = load_spaceship.get(ex01Types.MUSIC_GAME1);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_ELECTRO_SHOCK));
		sShock1 = load_spaceship.get(ex01Types.SOUND_ELECTRO_SHOCK);
		sShock2 = load_spaceship.get(ex01Types.SOUND_ELECTRO_SHOCK);
		sShock3 = load_spaceship.get(ex01Types.SOUND_ELECTRO_SHOCK);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_ELECTRO_SHOCK_EXPLODE));
		sShock1Explode = load_spaceship.get(ex01Types.SOUND_ELECTRO_SHOCK_EXPLODE);
		sShock2Explode = load_spaceship.get(ex01Types.SOUND_ELECTRO_SHOCK_EXPLODE);
		sShock3Explode = load_spaceship.get(ex01Types.SOUND_ELECTRO_SHOCK_EXPLODE);
		//get the assets - load hud_replay atlas and explosion atlas
		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.EXPLOSION_HUD_REPLAY02medium));

		atlas_space_big = load_spaceship.get(ex01Types.EXPLOSION_HUD_REPLAY02medium);
		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.EXPLOSION_ATLAS02medium));

		textureAtlasExplosion = load_spaceship.get(ex01Types.EXPLOSION_ATLAS02medium);

		// load spaceship Sprite for when we finish the level
		LoadBigSpaceshipFinishers();
		// write to JSON loader after we exploded - we need a separate thread
		// so we don't block the render thread
		LoadExplosionThreadWriteJson();
	}

	public void Load_01small(){
		//load the assets
		load_spaceship = new AssetManager();

		load_spaceship.load(ex01Types.EXPLOSION_HUD_REPLAY03small, TextureAtlas.class);
		load_spaceship.load(ex01Types.EXPLOSION_ATLAS03small, TextureAtlas.class);
		load_spaceship.load(ex01Types.SOUND_ACCELSPACE1, Sound.class);
		load_spaceship.load(ex01Types.SOUND_ACCELSPACE2, Sound.class);
		load_spaceship.load(ex01Types.SOUND_POWERUP_BULLETS, Sound.class);
		load_spaceship.load(ex01Types.SOUND_POWERUP_HEALTH, Sound.class);
		load_spaceship.load(ex01Types.SOUND_LASER_SHOOT, Sound.class);
		load_spaceship.load(ex01Types.SOUND_SPACESHIP_EXPLOSION, Sound.class);
		load_spaceship.load(ex01Types.SOUND_BLOCKER_HIT_EXPLOSION, Sound.class);
		load_spaceship.load(ex01Types.SOUND_ELECTRO_SHOCK, Sound.class);
		load_spaceship.load(ex01Types.SOUND_ELECTRO_SHOCK_EXPLODE, Sound.class);
		load_spaceship.load(ex01Types.SOUND_POWER_DOWN, Sound.class);
		load_spaceship.load(ex01Types.SOUND_ACCELSPACE1_REV, Sound.class);
		load_spaceship.load(ex01Types.MUSIC_GAME1, Music.class);
		load_spaceship.finishLoading();

		//get the assets - sounds
		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_ACCELSPACE1));
		spaceship_acceleration = load_spaceship.get(ex01Types.SOUND_ACCELSPACE1);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_POWER_DOWN));
		sound_power_down = load_spaceship.get(ex01Types.SOUND_POWER_DOWN);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_ACCELSPACE1_REV));
		sound_accelspace1_rev = load_spaceship.get(ex01Types.SOUND_ACCELSPACE1_REV);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_ACCELSPACE2));
		spaceship_acceleration_real = load_spaceship.get(ex01Types.SOUND_ACCELSPACE2);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_POWERUP_BULLETS));
		powerup_bullets_hit = load_spaceship.get(ex01Types.SOUND_POWERUP_BULLETS);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_POWERUP_HEALTH));
		powerup_health_hit = load_spaceship.get(ex01Types.SOUND_POWERUP_HEALTH);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_LASER_SHOOT));
		laser_shoot = load_spaceship.get(ex01Types.SOUND_LASER_SHOOT);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_SPACESHIP_EXPLOSION));
		spaceship_explosion = load_spaceship.get(ex01Types.SOUND_SPACESHIP_EXPLOSION);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_BLOCKER_HIT_EXPLOSION));
		blocker_hit_explosion = load_spaceship.get(ex01Types.SOUND_BLOCKER_HIT_EXPLOSION);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.MUSIC_GAME1));
		game_music = load_spaceship.get(ex01Types.MUSIC_GAME1);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_ELECTRO_SHOCK));
		sShock1 = load_spaceship.get(ex01Types.SOUND_ELECTRO_SHOCK);
		sShock2 = load_spaceship.get(ex01Types.SOUND_ELECTRO_SHOCK);
		sShock3 = load_spaceship.get(ex01Types.SOUND_ELECTRO_SHOCK);

		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.SOUND_ELECTRO_SHOCK_EXPLODE));

		sShock1Explode = load_spaceship.get(ex01Types.SOUND_ELECTRO_SHOCK_EXPLODE);
		sShock2Explode = load_spaceship.get(ex01Types.SOUND_ELECTRO_SHOCK_EXPLODE);
		sShock3Explode = load_spaceship.get(ex01Types.SOUND_ELECTRO_SHOCK_EXPLODE);
		//get the assets - load hud_replay atlas and explosion atlas
		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.EXPLOSION_HUD_REPLAY03small));

		atlas_space_big = load_spaceship.get(ex01Types.EXPLOSION_HUD_REPLAY03small);
		do { try { Thread.sleep(50); } catch (Exception e) { e.printStackTrace(); } }
		while(!load_spaceship.isLoaded(ex01Types.EXPLOSION_ATLAS03small));

		textureAtlasExplosion = load_spaceship.get(ex01Types.EXPLOSION_ATLAS03small);

		// load spaceship Sprite for when we finish the level
		LoadBigSpaceshipFinishers();
		// write to JSON loader after we exploded - we need a separate thread
		// so we don't block the render thread
		LoadExplosionThreadWriteJson();
	}
	
	public void ExploderThreadInit(){
    	r_write_json2 = new Runnable(){
    		public void run(){
    			ExplodeTimerTask.purge();
    			ExplodeTimerTask.schedule(TimerTaskExploded, 0);
    		}
    	};
    	t_write_json2 = new Thread(r_write_json2);				
	}
	
	public void LoadBigSpaceshipFinishers(){		
		space1_bigAR = atlas_space_big.findRegion("0010 1");
		space2_bigAR = atlas_space_big.findRegion("0010 2");
		space3_bigAR = atlas_space_big.findRegion("0010 3");
		space1_bigS = new Sprite(space1_bigAR);
		space2_bigS = new Sprite(space2_bigAR);
		space3_bigS = new Sprite(space3_bigAR);
		space1_bigS.setPosition(SPACESHIP_POS_X, SPACESHIP_POS_Y);
		space2_bigS.setPosition(SPACESHIP_POS_X, SPACESHIP_POS_Y);
		space3_bigS.setPosition(SPACESHIP_POS_X, SPACESHIP_POS_Y);
		space1_bigS.setSize(SPACESHIP_WIDTH_X, SPACESHIP_WIDTH_Y);
		space2_bigS.setSize(SPACESHIP_WIDTH_X, SPACESHIP_WIDTH_Y);
		space3_bigS.setSize(SPACESHIP_WIDTH_X, SPACESHIP_WIDTH_Y);
	}
	
	public void LoadExplosionThreadWriteJson(){
    	r_write_json = new Runnable(){
    		public void run(){
    			sprite_explosion.setRegion(textureAtlasExplosion.findRegion("spacesepxl0047"));
    			exploded = true;
    			game_upper.TurnToSepiaForContrast();
    			game_upper.AppearHUDOnFail();
    			game_upper.PauseGameScreen();
    			hudder.AddClickRestartListener();
    			hudder.AddClickResumeListener();
    	}};
    	t_write_json = new Thread(r_write_json);		
	}

	public void SpaceshipAnimationReload(){
	    SpriteElectroShock[0] = textureAtlas.findRegion("spaceelectro0000");
	    SpriteElectroShock[1] = textureAtlas.findRegion("spaceelectro0002");
	    SpriteElectroShock[2] = textureAtlas.findRegion("spaceelectro0004");
	    SpriteElectroShock[3] = textureAtlas.findRegion("spaceelectro0006");
	    SpriteElectroShock[4] = textureAtlas.findRegion("spaceelectro0008");
	    SpriteElectroShock[5] = textureAtlas.findRegion("spaceelectro0010");
	    SpriteElectroShock[6] = textureAtlas.findRegion("spaceelectro0012");
	    SpriteElectroShock[7] = textureAtlas.findRegion("spaceelectro0014");
	    SpriteElectroShock[8] = textureAtlas.findRegion("spaceelectro0016");
	    SpriteElectroShock[9] = textureAtlas.findRegion("spaceelectro0018");
	    SpriteElectroShock[10] = textureAtlas.findRegion("spaceelectro0020");
	    SpriteElectroShock[11] = textureAtlas.findRegion("spaceelectro0022");
	    SpriteElectroShock[12] = textureAtlas.findRegion("spaceelectro0024");
	    SpriteElectroShock[13] = textureAtlas.findRegion("spaceelectro0026");
	    SpriteElectroShock[14] = textureAtlas.findRegion("spaceelectro0028");
	    SpriteElectroShock[15] = textureAtlas.findRegion("spaceelectro0030");
	    SpriteExplosion[0] = textureAtlasExplosion.findRegion("spacesepxl0000");
	    SpriteExplosion[1] = textureAtlasExplosion.findRegion("spacesepxl0001");
	    SpriteExplosion[2] = textureAtlasExplosion.findRegion("spacesepxl0002");
	    SpriteExplosion[3] = textureAtlasExplosion.findRegion("spacesepxl0003");
	    SpriteExplosion[4] = textureAtlasExplosion.findRegion("spacesepxl0004");
	    SpriteExplosion[5] = textureAtlasExplosion.findRegion("spacesepxl0005");
	    SpriteExplosion[6] = textureAtlasExplosion.findRegion("spacesepxl0006");
	    SpriteExplosion[7] = textureAtlasExplosion.findRegion("spacesepxl0007");
	    SpriteExplosion[8] = textureAtlasExplosion.findRegion("spacesepxl0008");
	    SpriteExplosion[9] = textureAtlasExplosion.findRegion("spacesepxl0009");
	    SpriteExplosion[10] = textureAtlasExplosion.findRegion("spacesepxl0010");
	    SpriteExplosion[11] = textureAtlasExplosion.findRegion("spacesepxl0011");
	    SpriteExplosion[12] = textureAtlasExplosion.findRegion("spacesepxl0012");
	    SpriteExplosion[13] = textureAtlasExplosion.findRegion("spacesepxl0013");
	    SpriteExplosion[14] = textureAtlasExplosion.findRegion("spacesepxl0014");
	    SpriteExplosion[15] = textureAtlasExplosion.findRegion("spacesepxl0015");
	    SpriteExplosion[16] = textureAtlasExplosion.findRegion("spacesepxl0016");
	    SpriteExplosion[17] = textureAtlasExplosion.findRegion("spacesepxl0017");
	    SpriteExplosion[18] = textureAtlasExplosion.findRegion("spacesepxl0018");
	    SpriteExplosion[19] = textureAtlasExplosion.findRegion("spacesepxl0019");
	    SpriteExplosion[20] = textureAtlasExplosion.findRegion("spacesepxl0020");
	    SpriteExplosion[21] = textureAtlasExplosion.findRegion("spacesepxl0021");
	    SpriteExplosion[22] = textureAtlasExplosion.findRegion("spacesepxl0022");
	    SpriteExplosion[23] = textureAtlasExplosion.findRegion("spacesepxl0023");
	    SpriteExplosion[24] = textureAtlasExplosion.findRegion("spacesepxl0024");
	    SpriteExplosion[25] = textureAtlasExplosion.findRegion("spacesepxl0025");
	    SpriteExplosion[26] = textureAtlasExplosion.findRegion("spacesepxl0026");
	    SpriteExplosion[27] = textureAtlasExplosion.findRegion("spacesepxl0027");
	    SpriteExplosion[28] = textureAtlasExplosion.findRegion("spacesepxl0028");
	    SpriteExplosion[29] = textureAtlasExplosion.findRegion("spacesepxl0029");
	    SpriteExplosion[30] = textureAtlasExplosion.findRegion("spacesepxl0030");
	    SpriteExplosion[31] = textureAtlasExplosion.findRegion("spacesepxl0031");
	    SpriteExplosion[32] = textureAtlasExplosion.findRegion("spacesepxl0032");
	    SpriteExplosion[33] = textureAtlasExplosion.findRegion("spacesepxl0033");
	    SpriteExplosion[34] = textureAtlasExplosion.findRegion("spacesepxl0034");
	    SpriteExplosion[35] = textureAtlasExplosion.findRegion("spacesepxl0035");
	    SpriteExplosion[36] = textureAtlasExplosion.findRegion("spacesepxl0036");
	    SpriteExplosion[37] = textureAtlasExplosion.findRegion("spacesepxl0037");
	    SpriteExplosion[38] = textureAtlasExplosion.findRegion("spacesepxl0038");
	    SpriteExplosion[39] = textureAtlasExplosion.findRegion("spacesepxl0039");
	    SpriteExplosion[40] = textureAtlasExplosion.findRegion("spacesepxl0040");
	    SpriteExplosion[41] = textureAtlasExplosion.findRegion("spacesepxl0041");
	    SpriteExplosion[42] = textureAtlasExplosion.findRegion("spacesepxl0042");
	    SpriteExplosion[43] = textureAtlasExplosion.findRegion("spacesepxl0043");
	    SpriteExplosion[44] = textureAtlasExplosion.findRegion("spacesepxl0044");
	    SpriteExplosion[45] = textureAtlasExplosion.findRegion("spacesepxl0045");
	    SpriteExplosion[46] = textureAtlasExplosion.findRegion("spacesepxl0046");
	    SpriteExplosion[47] = textureAtlasExplosion.findRegion("spacesepxl0047");	    
	    SpriteExplosion[48] = textureAtlasExplosion.findRegion("spacesepxl0048");
	    SpriteExplosion[49] = textureAtlasExplosion.findRegion("spacesepxl0049");
	    SpriteExplosion[50] = textureAtlasExplosion.findRegion("spacesepxl0050");
	    SpriteExplosion[51] = textureAtlasExplosion.findRegion("spacesepxl0051");
	    SpriteExplosion[52] = textureAtlasExplosion.findRegion("spacesepxl0052");
	    SpriteExplosion[53] = textureAtlasExplosion.findRegion("spacesepxl0053");
	    SpriteExplosion[54] = textureAtlasExplosion.findRegion("spacesepxl0054");
	    SpriteExplosion[55] = textureAtlasExplosion.findRegion("spacesepxl0055");
	    SpriteExplosion[56] = textureAtlasExplosion.findRegion("spacesepxl0056");
	    SpriteExplosion[57] = textureAtlasExplosion.findRegion("spacesepxl0057");
	    SpriteExplosion[58] = textureAtlasExplosion.findRegion("spacesepxl0058");	    
	    SpriteExplosion[59] = textureAtlasExplosion.findRegion("spacesepxl0059");
	    SpriteExplosion[60] = textureAtlasExplosion.findRegion("spacesepxl0060");
	    SpriteExplosion[61] = textureAtlasExplosion.findRegion("spacesepxl0061");
	    SpriteExplosion[62] = textureAtlasExplosion.findRegion("spacesepxl0062");
	    SpriteExplosion[63] = textureAtlasExplosion.findRegion("spacesepxl0063");
	    SpriteExplosion[64] = textureAtlasExplosion.findRegion("spacesepxl0064");	    
	    SpriteExplosion[65] = textureAtlasExplosion.findRegion("spacesepxl0065");
	    SpriteExplosion[66] = textureAtlasExplosion.findRegion("spacesepxl0066");
	    SpriteExplosion[67] = textureAtlasExplosion.findRegion("spacesepxl0067");
	    SpriteExplosion[68] = textureAtlasExplosion.findRegion("spacesepxl0068");
	    SpriteExplosion[69] = textureAtlasExplosion.findRegion("spacesepxl0069");
	    SpriteExplosion[70] = textureAtlasExplosion.findRegion("spacesepxl0070");
	    SpriteExplosion[71] = textureAtlasExplosion.findRegion("spacesepxl0071");
	    MicroExplosion[0] = textureAtlasSec.findRegion("micro0000");
	    MicroExplosion[1] = textureAtlasSec.findRegion("micro0002");
	    MicroExplosion[2] = textureAtlasSec.findRegion("micro0004");
	    MicroExplosion[3] = textureAtlasSec.findRegion("micro0006");
	    MicroExplosion[4] = textureAtlasSec.findRegion("micro0008");
	    MicroExplosion[5] = textureAtlasSec.findRegion("micro0010");
	    MicroExplosion[6] = textureAtlasSec.findRegion("micro0012");
	    MicroExplosion[7] = textureAtlasSec.findRegion("micro0014");
	    MicroExplosion[8] = textureAtlasSec.findRegion("micro0016");
	    MicroExplosion[9] = textureAtlasSec.findRegion("micro0018");
	    MicroExplosion[10] = textureAtlasSec.findRegion("micro0020");
	    MicroExplosion[11] = textureAtlasSec.findRegion("micro0022");
	    MicroExplosion[12] = textureAtlasSec.findRegion("micro0024");
	    MicroExplosion[13] = textureAtlasSec.findRegion("micro0026");
	    MicroExplosion[14] = textureAtlasSec.findRegion("micro0028");
	    MicroExplosion[15] = textureAtlasSec.findRegion("micro0030");
	    SpaceshipRotateLeft[10] = (textureAtlas.findRegion("0000"));
	    SpaceshipRotateLeft[9] = (textureAtlas.findRegion("0001"));
	    SpaceshipRotateLeft[8] = (textureAtlas.findRegion("0002"));
	    SpaceshipRotateLeft[7] = (textureAtlas.findRegion("0003"));
	    SpaceshipRotateLeft[6] = (textureAtlas.findRegion("0004"));
	    SpaceshipRotateLeft[5] = (textureAtlas.findRegion("0005"));
	    SpaceshipRotateLeft[4] = (textureAtlas.findRegion("0006"));
	    SpaceshipRotateLeft[3] = (textureAtlas.findRegion("0007"));
	    SpaceshipRotateLeft[2] = (textureAtlas.findRegion("0008"));
	    SpaceshipRotateLeft[1] = (textureAtlas.findRegion("0009"));
	    SpaceshipRotateLeft[0] = (textureAtlas.findRegion("0010"));
	    SpaceshipRotateLeftReverse[0] = (textureAtlas.findRegion("0000"));
	    SpaceshipRotateLeftReverse[1] = (textureAtlas.findRegion("0001"));
	    SpaceshipRotateLeftReverse[2] = (textureAtlas.findRegion("0002"));
	    SpaceshipRotateLeftReverse[3] = (textureAtlas.findRegion("0003"));
	    SpaceshipRotateLeftReverse[4] = (textureAtlas.findRegion("0004"));
	    SpaceshipRotateLeftReverse[5] = (textureAtlas.findRegion("0005"));
	    SpaceshipRotateLeftReverse[6] = (textureAtlas.findRegion("0006"));
	    SpaceshipRotateLeftReverse[7] = (textureAtlas.findRegion("0007"));
	    SpaceshipRotateLeftReverse[8] = (textureAtlas.findRegion("0008"));
	    SpaceshipRotateLeftReverse[9] = (textureAtlas.findRegion("0009"));	    
	    SpaceshipRotateLeftReverse[10] = (textureAtlas.findRegion("0010"));
	    SpaceshipRotateRight[0] = (textureAtlas.findRegion("0010"));
	    SpaceshipRotateRight[1] = (textureAtlas.findRegion("0011"));
	    SpaceshipRotateRight[2] = (textureAtlas.findRegion("0012"));
	    SpaceshipRotateRight[3] = (textureAtlas.findRegion("0013"));
	    SpaceshipRotateRight[4] = (textureAtlas.findRegion("0014"));
	    SpaceshipRotateRight[5] = (textureAtlas.findRegion("0015"));
	    SpaceshipRotateRight[6] = (textureAtlas.findRegion("0016"));
	    SpaceshipRotateRight[7] = (textureAtlas.findRegion("0017"));
	    SpaceshipRotateRight[8] = (textureAtlas.findRegion("0018"));
	    SpaceshipRotateRight[9] = (textureAtlas.findRegion("0019"));
	    SpaceshipRotateRight[10] = (textureAtlas.findRegion("0020"));	   
	    SpaceshipRotateRightReverse[10] = (textureAtlas.findRegion("0010"));
	    SpaceshipRotateRightReverse[9] = (textureAtlas.findRegion("0011"));
	    SpaceshipRotateRightReverse[8] = (textureAtlas.findRegion("0012"));
	    SpaceshipRotateRightReverse[7] = (textureAtlas.findRegion("0013"));
	    SpaceshipRotateRightReverse[6] = (textureAtlas.findRegion("0014"));
	    SpaceshipRotateRightReverse[5] = (textureAtlas.findRegion("0015"));
	    SpaceshipRotateRightReverse[4] = (textureAtlas.findRegion("0016"));
	    SpaceshipRotateRightReverse[3] = (textureAtlas.findRegion("0017"));
	    SpaceshipRotateRightReverse[2] = (textureAtlas.findRegion("0018"));
	    SpaceshipRotateRightReverse[1] = (textureAtlas.findRegion("0019"));
	    SpaceshipRotateRightReverse[0] = (textureAtlas.findRegion("0020"));	   
	    electroShockAnimation = new Animation(0.025f, SpriteElectroShock);
	    explosionAnimation = new Animation(0.020f,SpriteExplosion);
	    explosionAnimationOver = new Animation(0.025f,SpriteExplosionOver);
	    explosionMicroAnimation = new Animation(0.025f,MicroExplosion);
	    animationSpaceship = new Animation(0.025f,SpaceshipRotateLeft);		
	    animationSpaceship2 = new Animation(0.025f,SpaceshipRotateRight);
	    animationSpaceshipReverse = new Animation(0.025f,SpaceshipRotateLeftReverse);		
	    animationSpaceship2Reverse = new Animation(0.025f,SpaceshipRotateRightReverse);	    			
	}	

	public void ResetSpacesForInGameReset(){
		LoadSmallSpaceship();
		ResetSpaceship();	
		ResetShipBooleans();
		CreateFirstStartEnd();
    	Create16ShootingsReload();
	}
	
	public void ResetSpaceship(){
    	SpaceshipReloadSprites();
		SpaceshipBodyExplosionThrusterReset();
		SpaceshipVariablesReset();
		SpaceshipBooleansReset();
		SpaceshipOtherVariablesReset();
    	Create16ShootingsReload();
	}
	
	public void ResetShipBooleans(){
		can_explode = false;     	//used to start rendering explosion of spaceship
		exploded = false;        	//is true after explosion gets done so we display replay fud
		exploded_finished = false;			
	}
	
	public void SpaceshipReloadSprites(){
	    middle = textureAtlas.findRegion("0010");
	    sprite.setRegion(middle);
		sprite.setSize(SPACESHIP_WIDTH_X, SPACESHIP_WIDTH_Y);
	    sprite_base.setRegion(middle);
		thruster = textureAtlas.findRegion("thruster");
		thrusters.setRegion(thruster);
		SpaceshipAnimationReload();
	}
	
	public void SpaceshipVariablesReset(){
		counter_shoot = 0;
		acceleration.x = ACCELERATION_X;
		acceleration.y = ACCELERATION_Y;
		velocity.x = VELOCITY_X;
		velocity.y = VELOCITY_Y;		
		originalThrusterW = ORIG_THRUSTER_W;
		originalThrusterH = ORIG_THRUSTER_H;
		originalThrusterX = ORIG_THRUSTER_X;
		originalThrusterY = ORIG_THRUSTER_Y;
		originalThrusterDY = originalThrusterY - sprite.getY();
		originalThrusterDX = originalThrusterX - sprite.getX();		
		lateral_distance_reseter = (sprite.getWidth() - thrusters.getWidth()) / 2;
		lateral_distance_reseter_collision =
				(sprite.getWidth() - spaceship_rectangle_collision.radius * 2) / 2;
		if(type_spaceship==0) {
			world_velocity_shoot = WORLD_VELOCITY_SHOOT1;
			space_adjust = SPACE_ADJUST1;
			world_velocity = WORLD_VELOCITY1;
		}
		if(type_spaceship==1) {
			world_velocity_shoot = WORLD_VELOCITY_SHOOT2;
			space_adjust = SPACE_ADJUST2;
			world_velocity = WORLD_VELOCITY2;
		}
		if(type_spaceship==2) {
			world_velocity_shoot = WORLD_VELOCITY_SHOOT3;
			space_adjust = SPACE_ADJUST3;
			world_velocity = WORLD_VELOCITY3;
		}
		world_velocity_original = world_velocity;	
		scaleX = SCALE_X;
		scaleY = SCALE_Y;
		thrusters.setScale(scaleX, scaleY);
	}
	
	public void SpaceshipBooleansReset(){
		shock_active1 = false;
		shock_active2 = false;
		shock_active3 = false;
		give_electro_shock_activated = false;
		give_electro_shock_activated_denier = false; // for the else in NeedToChangeStart
		touched_left = false;
		touched_right = false;
		touched = false;
		finished_lateral_rotation = true;
		stopped_rotation_to_left = false;
		stopped_rotation_to_right = false;
		touch_upped = true;
		reversed_rotation = false;
		acceleration_overlap_playing = false;
		can_laser_shoot = false;
		was_going_left_last_time = false;
		was_going_right_last_time = false;
		pressed_accel = false;
		pressed_shoot = false;	
		started_overlay = false;
		started_base = false;	
		can_be_electrocuted = false;	
		can_explode = false;		
		exploded = false;		    				 // used for HUD replay display
		exploded_finished = false;					 // used for HUD replay display
		no_more_bullets = false;
		for(i = 0; i < is_bullet_busy.length; i++){
			is_bullet_busy[i] = false;
		}
	}
	
	public void SpaceshipOtherVariablesReset(){
		give_electro_shock_activated = false;
		counterElectroShock1 = CNT_ESHOCK1;
		counterElectroShock2 = CNT_ESHOCK2;
		counterElectroShock3 = CNT_ESHOCK3;
		counter_passed_touch_up = CNT_PASSED_TCH_UP;
		counter_explosion = CNT_EXPLOSION;
		how_much_advanced_world_units_h = HOW_MUCH_ADV_WORLD_UNITS_H;
		scaleX = SCALE_X;
		scaleY = SCALE_Y;
		initialThrusterDecelBigY = INIT_THRUSTER_DECEL_BIG_Y;
		passed_since_last_laser_shoot = PASSED_SINCE_LAST_LASER_SHOOT;
		passed_since_last_session = PASSED_SINCE_LAST_SESSION;
		spaceship_acceleration_volumeBASE = SPACESHIP_ACCEL_VOLUME_BASE;
		spaceship_acceleration_volume_overlapBASE = SPACESHIP_ACCEL_VOLUME_OVERLAP_BASE;	
		spaceship_acceleration_volume = SPACESHIP_ACCEL_VOLUME;
		spaceship_acceleration_volume_overlap = SPACESHIP_ACCEL_VOLUME_OVERLAP;
		spaceship_acceleration_volumeS = SPACESHIP_ACCEL_VOLUME_S;
		spaceship_acceleration_volume_overlapS = SPACESHIP_ACCEL_VOLUME_OVERLAP_S;	
		spaceship_pitch_volume = SPACESHIP_PITCH_VOLUME;
		spaceship_pitch_volume_overlap = SPACESHIP_PITCH_VOLUME_OVERLAP;
		delta_since_accel_playing = DELTA_SINCE_ACCEL_PLAYING;
		delta_since_accel_playing_overlay = DELTA_SINCE_ACCEL_PLAYING_OVERLAY;
		elapsedTime = ELAPSED_TIME;	
		counter_shots_fired_session = COUNTER_SHOOTS_FIRED_SESSION;
		FUD_accel_count = FUD_ACCEL_COUNT;
		WORLD_accel_count = WORLD_ACCEL_COUNT;		
		is_still_rolling = IS_STILL_ROLLING;
		currentFrame = CURRENT_FRAME;	
		if(type_spaceship==0) {
			world_velocity_shoot = WORLD_VELOCITY_SHOOT1;
			space_adjust = SPACE_ADJUST1;
			velocity_baser = VELOCITY1;
			Xaccel_baser = XACCEL1;
			accel_baser = accel1;
			world_velocity = WORLD_VELOCITY1;
		}
		if(type_spaceship==1) {
			world_velocity_shoot = WORLD_VELOCITY_SHOOT2;
			space_adjust = SPACE_ADJUST2;
			velocity_baser = VELOCITY2;
			Xaccel_baser = XACCEL2;
			accel_baser = accel2;
			world_velocity = WORLD_VELOCITY2;
		}
		if(type_spaceship==2) {
			world_velocity_shoot = WORLD_VELOCITY_SHOOT3;
			space_adjust = SPACE_ADJUST3;
			velocity_baser = VELOCITY3;
			Xaccel_baser = XACCEL3;
			accel_baser = accel3;
			world_velocity = WORLD_VELOCITY3;
		}
		Xaccel1 = Xaccel_baser;
		world_velocity_original = world_velocity;	
		world_velocity_original_shoot = world_velocity_shoot;
		velocity1 = velocity_baser;
		accel = accel_baser;		
	}		

	public void LoadBigSpaceship1(){
		space1_bigS.setPosition(sprite.getX(), sprite.getY());
	}
	
	public void LoadBigSpaceship2(){
		space2_bigS.setPosition(sprite.getX(), sprite.getY());
	}
	
	public void LoadBigSpaceship3(){
		space3_bigS.setPosition(sprite.getX(), sprite.getY());;
	}
	
	public void LoadSmallSpaceship(){
		sprite.setSize(SPACESHIP_WIDTH_X, SPACESHIP_WIDTH_Y);
		sprite.setTexture(texture_space_base);
	}

	public void init(float CAM_P, float CAM_W){
		SpaceshipAnimationInit();
		SpaceshipBodyExplosionThrusterInit();
		SpaceshipVariablesInit(CAM_P, CAM_W);
		SpacheshipLaserShootInit();
	}
	
	public void initReload(float CAM_P, float CAM_W){
		SpaceshipVariablesInitReload(CAM_P, CAM_W);
	}
	
	public void SpaceshipAnimationInit(){
	    MicroExplosion = new TextureRegion[16];
	    SpriteElectroShock[0] = textureAtlas.findRegion("spaceelectro0000");
	    SpriteElectroShock[1] = textureAtlas.findRegion("spaceelectro0002");
	    SpriteElectroShock[2] = textureAtlas.findRegion("spaceelectro0004");
	    SpriteElectroShock[3] = textureAtlas.findRegion("spaceelectro0006");
	    SpriteElectroShock[4] = textureAtlas.findRegion("spaceelectro0008");
	    SpriteElectroShock[5] = textureAtlas.findRegion("spaceelectro0010");
	    SpriteElectroShock[6] = textureAtlas.findRegion("spaceelectro0012");
	    SpriteElectroShock[7] = textureAtlas.findRegion("spaceelectro0014");
	    SpriteElectroShock[8] = textureAtlas.findRegion("spaceelectro0016");
	    SpriteElectroShock[9] = textureAtlas.findRegion("spaceelectro0018");
	    SpriteElectroShock[10] = textureAtlas.findRegion("spaceelectro0020");
	    SpriteElectroShock[11] = textureAtlas.findRegion("spaceelectro0022");
	    SpriteElectroShock[12] = textureAtlas.findRegion("spaceelectro0024");
	    SpriteElectroShock[13] = textureAtlas.findRegion("spaceelectro0026");
	    SpriteElectroShock[14] = textureAtlas.findRegion("spaceelectro0028");
	    SpriteElectroShock[15] = textureAtlas.findRegion("spaceelectro0030");
	    SpriteExplosion[0] = textureAtlasExplosion.findRegion("spacesepxl0000");
	    SpriteExplosion[1] = textureAtlasExplosion.findRegion("spacesepxl0001");
	    SpriteExplosion[2] = textureAtlasExplosion.findRegion("spacesepxl0002");
	    SpriteExplosion[3] = textureAtlasExplosion.findRegion("spacesepxl0003");
	    SpriteExplosion[4] = textureAtlasExplosion.findRegion("spacesepxl0004");
	    SpriteExplosion[5] = textureAtlasExplosion.findRegion("spacesepxl0005");
	    SpriteExplosion[6] = textureAtlasExplosion.findRegion("spacesepxl0006");
	    SpriteExplosion[7] = textureAtlasExplosion.findRegion("spacesepxl0007");
	    SpriteExplosion[8] = textureAtlasExplosion.findRegion("spacesepxl0008");
	    SpriteExplosion[9] = textureAtlasExplosion.findRegion("spacesepxl0009");
	    SpriteExplosion[10] = textureAtlasExplosion.findRegion("spacesepxl0010");
	    SpriteExplosion[11] = textureAtlasExplosion.findRegion("spacesepxl0011");
	    SpriteExplosion[12] = textureAtlasExplosion.findRegion("spacesepxl0012");
	    SpriteExplosion[13] = textureAtlasExplosion.findRegion("spacesepxl0013");
	    SpriteExplosion[14] = textureAtlasExplosion.findRegion("spacesepxl0014");
	    SpriteExplosion[15] = textureAtlasExplosion.findRegion("spacesepxl0015");
	    SpriteExplosion[16] = textureAtlasExplosion.findRegion("spacesepxl0016");
	    SpriteExplosion[17] = textureAtlasExplosion.findRegion("spacesepxl0017");
	    SpriteExplosion[18] = textureAtlasExplosion.findRegion("spacesepxl0018");
	    SpriteExplosion[19] = textureAtlasExplosion.findRegion("spacesepxl0019");
	    SpriteExplosion[20] = textureAtlasExplosion.findRegion("spacesepxl0020");
	    SpriteExplosion[21] = textureAtlasExplosion.findRegion("spacesepxl0021");
	    SpriteExplosion[22] = textureAtlasExplosion.findRegion("spacesepxl0022");
	    SpriteExplosion[23] = textureAtlasExplosion.findRegion("spacesepxl0023");
	    SpriteExplosion[24] = textureAtlasExplosion.findRegion("spacesepxl0024");
	    SpriteExplosion[25] = textureAtlasExplosion.findRegion("spacesepxl0025");
	    SpriteExplosion[26] = textureAtlasExplosion.findRegion("spacesepxl0026");
	    SpriteExplosion[27] = textureAtlasExplosion.findRegion("spacesepxl0027");
	    SpriteExplosion[28] = textureAtlasExplosion.findRegion("spacesepxl0028");
	    SpriteExplosion[29] = textureAtlasExplosion.findRegion("spacesepxl0029");
	    SpriteExplosion[30] = textureAtlasExplosion.findRegion("spacesepxl0030");
	    SpriteExplosion[31] = textureAtlasExplosion.findRegion("spacesepxl0031");
	    SpriteExplosion[32] = textureAtlasExplosion.findRegion("spacesepxl0032");
	    SpriteExplosion[33] = textureAtlasExplosion.findRegion("spacesepxl0033");
	    SpriteExplosion[34] = textureAtlasExplosion.findRegion("spacesepxl0034");
	    SpriteExplosion[35] = textureAtlasExplosion.findRegion("spacesepxl0035");
	    SpriteExplosion[36] = textureAtlasExplosion.findRegion("spacesepxl0036");
	    SpriteExplosion[37] = textureAtlasExplosion.findRegion("spacesepxl0037");
	    SpriteExplosion[38] = textureAtlasExplosion.findRegion("spacesepxl0038");
	    SpriteExplosion[39] = textureAtlasExplosion.findRegion("spacesepxl0039");
	    SpriteExplosion[40] = textureAtlasExplosion.findRegion("spacesepxl0040");
	    SpriteExplosion[41] = textureAtlasExplosion.findRegion("spacesepxl0041");
	    SpriteExplosion[42] = textureAtlasExplosion.findRegion("spacesepxl0042");
	    SpriteExplosion[43] = textureAtlasExplosion.findRegion("spacesepxl0043");
	    SpriteExplosion[44] = textureAtlasExplosion.findRegion("spacesepxl0044");
	    SpriteExplosion[45] = textureAtlasExplosion.findRegion("spacesepxl0045");
	    SpriteExplosion[46] = textureAtlasExplosion.findRegion("spacesepxl0046");
	    SpriteExplosion[47] = textureAtlasExplosion.findRegion("spacesepxl0047");	    
	    SpriteExplosion[48] = textureAtlasExplosion.findRegion("spacesepxl0048");
	    SpriteExplosion[49] = textureAtlasExplosion.findRegion("spacesepxl0049");
	    SpriteExplosion[50] = textureAtlasExplosion.findRegion("spacesepxl0050");
	    SpriteExplosion[51] = textureAtlasExplosion.findRegion("spacesepxl0051");
	    SpriteExplosion[52] = textureAtlasExplosion.findRegion("spacesepxl0052");
	    SpriteExplosion[53] = textureAtlasExplosion.findRegion("spacesepxl0053");
	    SpriteExplosion[54] = textureAtlasExplosion.findRegion("spacesepxl0054");
	    SpriteExplosion[55] = textureAtlasExplosion.findRegion("spacesepxl0055");
	    SpriteExplosion[56] = textureAtlasExplosion.findRegion("spacesepxl0056");
	    SpriteExplosion[57] = textureAtlasExplosion.findRegion("spacesepxl0057");
	    SpriteExplosion[58] = textureAtlasExplosion.findRegion("spacesepxl0058");	    
	    SpriteExplosion[59] = textureAtlasExplosion.findRegion("spacesepxl0059");
	    SpriteExplosion[60] = textureAtlasExplosion.findRegion("spacesepxl0060");
	    SpriteExplosion[61] = textureAtlasExplosion.findRegion("spacesepxl0061");
	    SpriteExplosion[62] = textureAtlasExplosion.findRegion("spacesepxl0062");
	    SpriteExplosion[63] = textureAtlasExplosion.findRegion("spacesepxl0063");
	    SpriteExplosion[64] = textureAtlasExplosion.findRegion("spacesepxl0064");	    
	    SpriteExplosion[65] = textureAtlasExplosion.findRegion("spacesepxl0065");
	    SpriteExplosion[66] = textureAtlasExplosion.findRegion("spacesepxl0066");
	    SpriteExplosion[67] = textureAtlasExplosion.findRegion("spacesepxl0067");
	    SpriteExplosion[68] = textureAtlasExplosion.findRegion("spacesepxl0068");
	    SpriteExplosion[69] = textureAtlasExplosion.findRegion("spacesepxl0069");
	    SpriteExplosion[70] = textureAtlasExplosion.findRegion("spacesepxl0070");
	    SpriteExplosion[71] = textureAtlasExplosion.findRegion("spacesepxl0071");	    
	    MicroExplosion[0] = textureAtlasSec.findRegion("micro0000");
	    MicroExplosion[1] = textureAtlasSec.findRegion("micro0002");
	    MicroExplosion[2] = textureAtlasSec.findRegion("micro0004");
	    MicroExplosion[3] = textureAtlasSec.findRegion("micro0006");
	    MicroExplosion[4] = textureAtlasSec.findRegion("micro0008");
	    MicroExplosion[5] = textureAtlasSec.findRegion("micro0010");
	    MicroExplosion[6] = textureAtlasSec.findRegion("micro0012");
	    MicroExplosion[7] = textureAtlasSec.findRegion("micro0014");
	    MicroExplosion[8] = textureAtlasSec.findRegion("micro0016");
	    MicroExplosion[9] = textureAtlasSec.findRegion("micro0018");
	    MicroExplosion[10] = textureAtlasSec.findRegion("micro0020");
	    MicroExplosion[11] = textureAtlasSec.findRegion("micro0022");
	    MicroExplosion[12] = textureAtlasSec.findRegion("micro0024");
	    MicroExplosion[13] = textureAtlasSec.findRegion("micro0026");
	    MicroExplosion[14] = textureAtlasSec.findRegion("micro0028");
	    MicroExplosion[15] = textureAtlasSec.findRegion("micro0030");
	    SpaceshipRotateLeft[10] = (textureAtlas.findRegion("0000"));
	    SpaceshipRotateLeft[9] = (textureAtlas.findRegion("0001"));
	    SpaceshipRotateLeft[8] = (textureAtlas.findRegion("0002"));
	    SpaceshipRotateLeft[7] = (textureAtlas.findRegion("0003"));
	    SpaceshipRotateLeft[6] = (textureAtlas.findRegion("0004"));
	    SpaceshipRotateLeft[5] = (textureAtlas.findRegion("0005"));
	    SpaceshipRotateLeft[4] = (textureAtlas.findRegion("0006"));
	    SpaceshipRotateLeft[3] = (textureAtlas.findRegion("0007"));
	    SpaceshipRotateLeft[2] = (textureAtlas.findRegion("0008"));
	    SpaceshipRotateLeft[1] = (textureAtlas.findRegion("0009"));
	    SpaceshipRotateLeft[0] = (textureAtlas.findRegion("0010"));
	    SpaceshipRotateLeftReverse[0] = (textureAtlas.findRegion("0000"));
	    SpaceshipRotateLeftReverse[1] = (textureAtlas.findRegion("0001"));
	    SpaceshipRotateLeftReverse[2] = (textureAtlas.findRegion("0002"));
	    SpaceshipRotateLeftReverse[3] = (textureAtlas.findRegion("0003"));
	    SpaceshipRotateLeftReverse[4] = (textureAtlas.findRegion("0004"));
	    SpaceshipRotateLeftReverse[5] = (textureAtlas.findRegion("0005"));
	    SpaceshipRotateLeftReverse[6] = (textureAtlas.findRegion("0006"));
	    SpaceshipRotateLeftReverse[7] = (textureAtlas.findRegion("0007"));
	    SpaceshipRotateLeftReverse[8] = (textureAtlas.findRegion("0008"));
	    SpaceshipRotateLeftReverse[9] = (textureAtlas.findRegion("0009"));	    
	    SpaceshipRotateLeftReverse[10] = (textureAtlas.findRegion("0010"));
	    SpaceshipRotateRight[0] = (textureAtlas.findRegion("0010"));
	    SpaceshipRotateRight[1] = (textureAtlas.findRegion("0011"));
	    SpaceshipRotateRight[2] = (textureAtlas.findRegion("0012"));
	    SpaceshipRotateRight[3] = (textureAtlas.findRegion("0013"));
	    SpaceshipRotateRight[4] = (textureAtlas.findRegion("0014"));
	    SpaceshipRotateRight[5] = (textureAtlas.findRegion("0015"));
	    SpaceshipRotateRight[6] = (textureAtlas.findRegion("0016"));
	    SpaceshipRotateRight[7] = (textureAtlas.findRegion("0017"));
	    SpaceshipRotateRight[8] = (textureAtlas.findRegion("0018"));
	    SpaceshipRotateRight[9] = (textureAtlas.findRegion("0019"));
	    SpaceshipRotateRight[10] = (textureAtlas.findRegion("0020"));	   
	    SpaceshipRotateRightReverse[10] = (textureAtlas.findRegion("0010"));
	    SpaceshipRotateRightReverse[9] = (textureAtlas.findRegion("0011"));
	    SpaceshipRotateRightReverse[8] = (textureAtlas.findRegion("0012"));
	    SpaceshipRotateRightReverse[7] = (textureAtlas.findRegion("0013"));
	    SpaceshipRotateRightReverse[6] = (textureAtlas.findRegion("0014"));
	    SpaceshipRotateRightReverse[5] = (textureAtlas.findRegion("0015"));
	    SpaceshipRotateRightReverse[4] = (textureAtlas.findRegion("0016"));
	    SpaceshipRotateRightReverse[3] = (textureAtlas.findRegion("0017"));
	    SpaceshipRotateRightReverse[2] = (textureAtlas.findRegion("0018"));
	    SpaceshipRotateRightReverse[1] = (textureAtlas.findRegion("0019"));
	    SpaceshipRotateRightReverse[0] = (textureAtlas.findRegion("0020"));	   
	    electroShockAnimation = new Animation(0.025f, SpriteElectroShock);
	    explosionAnimation = new Animation(0.025f,SpriteExplosion);
	    explosionAnimationOver = new Animation(0.025f,SpriteExplosionOver);
	    explosionMicroAnimation = new Animation(0.025f,MicroExplosion);
	    animationSpaceship = new Animation(0.025f,SpaceshipRotateLeft);		
	    animationSpaceship2 = new Animation(0.025f,SpaceshipRotateRight);
	    animationSpaceshipReverse = new Animation(0.025f,SpaceshipRotateLeftReverse);		
	    animationSpaceship2Reverse = new Animation(0.025f,SpaceshipRotateRightReverse);	    		
	}	
	
	public void SpaceshipBodyExplosionThrusterInit(){
	    middle = textureAtlas.findRegion("0010");
		sprite = new Sprite(middle);
		sprite_base = new Sprite(middle);
		sprite.setPosition(SPACESHIP_POS_X, SPACESHIP_POS_Y);
		sprite.setSize(SPACESHIP_WIDTH_X, SPACESHIP_WIDTH_Y);
		texture_space_base = sprite.getTexture();
		spaceship_rectangle_collision = new Circle(SRECT_COL_X, SRECT_COL_Y, SRECT_CIRCLE_RADIUS);
		spaceship_rect_widthp2 = spaceship_rectangle_collision.radius;
		spaceship_rect_heightp208 = spaceship_rectangle_collision.radius;
		eShock1 = new Sprite(SpriteElectroShock[0]);
		eShock2 = new Sprite(SpriteElectroShock[0]);
		eShock3 = new Sprite(SpriteElectroShock[0]);
		eShock1.setSize(ESHOCK1_WIDTH, ESHOCK1_HEIGHT);
		eShock2.setSize(ESHOCK2_WIDTH, ESHOCK2_HEIGHT);
		eShock3.setSize(ESHOCK3_WIDTH, ESHOCK3_HEIGHT);
		sprite_explosion = new Sprite(SpriteExplosion[0]);
		sprite_explosion.setSize(SPRITE_EXPLOSION_WIDTH, SPRITE_EXPLOSION_HEIGHT);
		thruster = textureAtlas.findRegion("thruster");
		thrusters = new Sprite(thruster);	
		thrusters.setSize(THRUSTERS_WIDTH, THRUSTERS_HEIGHT);	
		thrusters.setPosition(THRUSTERS_POS_X, THRUSTERS_POS_Y);		
		thrusters.setAlpha(THRUSTERS_ALPHA);
		thrusters.setOrigin(THRUSTERS_ORIGIN_X, THRUSTERS_ORIGIN_Y);
	}	
	
	public void SpaceshipBodyExplosionThrusterReset(){	
		sprite.setPosition(SPACESHIP_POS_X, SPACESHIP_POS_Y);
		sprite.setSize(SPACESHIP_WIDTH_X, SPACESHIP_WIDTH_Y);
		spaceship_rectangle_collision.x = SRECT_COL_X;
		spaceship_rectangle_collision.y = SRECT_COL_Y;
		thrusters.setPosition(THRUSTERS_POS_X, THRUSTERS_POS_Y);
		thrusters.setAlpha(THRUSTERS_ALPHA);
		thrusters.setOrigin(THRUSTERS_ORIGIN_X, THRUSTERS_ORIGIN_Y);
		spaceship_rect_widthp2 = spaceship_rectangle_collision.radius;
		spaceship_rect_heightp208 = spaceship_rectangle_collision.radius;
	}
	
	public void SpaceshipVariablesInit(float CAM_P, float CAM_W){	
		acceleration = new Vector2(ACCELERATION_X, ACCELERATION_Y);
		velocity = new Vector2(VELOCITY_X, VELOCITY_Y);
		originalThrusterW = ORIG_THRUSTER_W;
		originalThrusterH = ORIG_THRUSTER_H;
		originalThrusterX = ORIG_THRUSTER_X;
		originalThrusterY = ORIG_THRUSTER_Y;
		originalThrusterDY = originalThrusterY - sprite.getY();
		originalThrusterDX = originalThrusterX - sprite.getX();
		// maximum coord to the left that the ship can take
		maxLeft = CAM_P - CAM_W / 2;
		// maximum coord to the right that the ship can take
		maxRight = CAM_P + CAM_W / 2 - sprite.getWidth();
		lateral_distance_reseter = (sprite.getWidth() - thrusters.getWidth()) / 2;
		lateral_distance_reseter_collision = (sprite.getWidth()) / 2;
		if(type_spaceship==0) {
			world_velocity_shoot = WORLD_VELOCITY_SHOOT1;
			space_adjust = SPACE_ADJUST1;
			velocity_baser = VELOCITY1;
			Xaccel_baser = XACCEL1;
			accel_baser = accel1;
			world_velocity = WORLD_VELOCITY1;
		}
		if(type_spaceship==1) {
			world_velocity_shoot = WORLD_VELOCITY_SHOOT2;
			space_adjust = SPACE_ADJUST2;
			velocity_baser = VELOCITY2;
			Xaccel_baser = XACCEL2;
			accel_baser = accel2;
			world_velocity = WORLD_VELOCITY2;
		}
		if(type_spaceship==2) {
			world_velocity_shoot = WORLD_VELOCITY_SHOOT3;
			space_adjust = SPACE_ADJUST3;
			velocity_baser = VELOCITY3;
			Xaccel_baser = XACCEL3;
			accel_baser = accel3;
			world_velocity = WORLD_VELOCITY3;
		}
		Xaccel1 = Xaccel_baser;
		world_velocity_original = world_velocity;	
		world_velocity_original_shoot = world_velocity_shoot;
		velocity1 = velocity_baser;
		accel = accel_baser;
		start = new Vector2();
		end = new Vector2();
		MaxLeftPlus015 = maxLeft + 0.15f;
		MaxLeftPlusLDRC = maxLeft + lateral_distance_reseter_collision;
		MaxLeftPlusLDRCm02 = maxLeft + lateral_distance_reseter_collision - 0.2f;
		MaxLeftPlusLDRCp191 = maxLeft + lateral_distance_reseter_collision + 1.91f;
		MaxRightMinus015 = maxRight - 0.15f;
		MaxRightPlusLDRC = maxRight + lateral_distance_reseter_collision;
		MaxRightPlusLDRCm02 = maxRight + lateral_distance_reseter_collision - 0.2f;
		MaxRightPlusLDRCp191 = maxRight + lateral_distance_reseter_collision + 1.91f;	
		MaxLeftp030 = maxLeft + 1.30f;
		MaxRightm030 = maxRight - 1.30f;
	}
	
	public void SpaceshipVariablesInitReload(float CAM_P, float CAM_W){	
		if(type_spaceship==0) {
			world_velocity_shoot = WORLD_VELOCITY_SHOOT1;
			space_adjust = SPACE_ADJUST1;
			velocity_baser = VELOCITY1;
			Xaccel_baser = XACCEL1;
			accel_baser = accel1;
			world_velocity = WORLD_VELOCITY1;
		}
		if(type_spaceship==1) {
			world_velocity_shoot = WORLD_VELOCITY_SHOOT2;
			space_adjust = SPACE_ADJUST2;
			velocity_baser = VELOCITY2;
			Xaccel_baser = XACCEL2;
			accel_baser = accel2;
			world_velocity = WORLD_VELOCITY2;
		}
		if(type_spaceship==2) {
			world_velocity_shoot = WORLD_VELOCITY_SHOOT3;
			space_adjust = SPACE_ADJUST3;
			velocity_baser = VELOCITY3;
			Xaccel_baser = XACCEL3;
			accel_baser = accel3;
			world_velocity = WORLD_VELOCITY3;
		}
	}	
	
	public void SpacheshipLaserShootInit(){	//shader for shootings laser 	
		VertexAttribute pos_base = new
				VertexAttribute(Usage.Position, 2, "a_position");
		VertexAttribute col_base = new
				VertexAttribute(Usage.ColorPacked, 4, "a_color");
		VertexAttribute tex_base = new
				VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords");
		mesh = new Mesh(true, 4, 6, pos_base, col_base, tex_base);			
		VertexAttribute pos_baseO = new
				VertexAttribute(Usage.Position, 2, "a_position");
		VertexAttribute col_baseO = new
				VertexAttribute(Usage.ColorPacked, 4, "a_color");
		VertexAttribute tex_baseO = new
				VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords");
		meshO = new Mesh(true, 4, 6, pos_baseO, col_baseO, tex_baseO);	 	 			
		textureAtlas_laser = textureAtlas.findRegion("bullet_base");
		textureAtlas_laserOver = textureAtlas.findRegion("laser_overlay");
		createShader();		
	}

	//create and link the shader programs computers view matrix and textures
	public void createShader()
    {
        shader = new ShaderProgram(
				Gdx.files.internal(DATA_SPACESHIP_VERT),
				Gdx.files.internal(DATA_SPACESHIP_FRAG));
        shaderGrey = new ShaderProgram(
				Gdx.files.internal(DATA_GRAYSCALE_LASERS_VERT),
				Gdx.files.internal(DATA_GRAYSCALE_LASERS_FRAG));
        shader_base = shader; 
    }	
	
	public void RenderSpaceship(SpriteBatch batch, OrthographicCamera camera){
		if (!can_explode) {
			if(game_upper.set_big_spaceship_already){
				if(type_spaceship==0){
					space1_bigS.setPosition(sprite.getX(), sprite.getY());
					space1_bigS.draw(batch);
				} else if(type_spaceship==1){
					space2_bigS.setPosition(sprite.getX(), sprite.getY());
					space2_bigS.draw(batch);
				} else if(type_spaceship==2){
					space3_bigS.setPosition(sprite.getX(), sprite.getY());
					space3_bigS.draw(batch);
				}
			} else {
				sprite.draw(batch); // draw spaceship body
			}
		}
		if (!can_explode) thrusters.draw(batch);  	  // draw spaceship thrusters
		if (!can_explode) renderElectroShocks(batch); // draw shocks when electrocuted
		renderLasersExplosion(batch);
	}
	
	//called in the game screen >>> spaces.spaceshipAdvance(velo);
	//   velo = spaces.world_velocity * delta_64_srsz;
	public void spaceshipAdvance(float velocity){
		sprite.setPosition(sprite.getX(), sprite.getY() + velocity);
		spaceship_rectangle_collision.setPosition(
				spaceship_rectangle_collision.x,
				spaceship_rectangle_collision.y + velocity);
		eShock1.setPosition(eShock1.getX(), eShock1.getY() + velocity);
		eShock2.setPosition(eShock2.getX(), eShock2.getY() + velocity);
		eShock3.setPosition(eShock3.getX(), eShock3.getY() + velocity);			
		thrusters.setPosition(thrusters.getX(), thrusters.getY() + velocity);	
	}	
	
	public void touchDOWN(boolean pressed_left) {
		if(!reversed_rotation && !game_upper.hud.pressed_middle_shoot){
			if(!finished_lateral_rotation){
				if ((pressed_left) != touched_left ){
					touched_left = pressed_left;
					touched_right = !touched_left;
					reversed_rotation = true;
				}
			}
			if(!touched){
				touch_upped = false;
				if(!finished_lateral_rotation){
					elapsedTime = ELAPSE_FORERUN - counter_passed_touch_up ;
				} else {
					elapsedTime = ELAPSED_TIME;
				}
				touched = true;
				stopped_rotation_to_left = false;
				stopped_rotation_to_right = false;
				touched_left = pressed_left;
				touched_right = !touched_left;
				finished_lateral_rotation = false;
			}
		}
    }	
	
	public void touchUP_(){
		velocity.x = VELOCITY_X;
		velocity.y = VELOCITY_Y;
	    if(!reversed_rotation){
		    stopped_rotation_to_left = (touched_left) ? true : false;
		    stopped_rotation_to_right = !stopped_rotation_to_left;
		    touched = false;
		    if(elapsedTime < ELAPSE_FORERUN){
			    counter_passed_touch_up = ELAPSE_FORERUN - elapsedTime;		   
		    } else {
			    counter_passed_touch_up = 0; 
		    }
		    elapsed_time_saved = elapsedTime;
		    elapsedTime = 0;
		    touch_upped = true;
	    } 
	}
	
	public void touchUP (int x, int y, int pointer, int button) {
		touchUP_();
	}
	
	public void ResetPauseNotFinishedRotation(){

	}
	
	//###############################################################################
	//update left, right, rotations, lateral stoppers, etc, THIS IS WHERE THE ACTION GOES
	public void update(boolean touched_middle, float delta){
		elapsedTime += delta;
		if (touch_upped || reversed_rotation) counter_passed_touch_up += delta;
		if(touched){ // pressed touch screen (finger still on the screen)
			if(touched_left){
				was_going_left_last_time = true;
				was_going_right_last_time = false;
				if(reversed_rotation){
					counter_passed_touch_up = COUNTER_PASSED_TOUCH_UP12;
					if(animationSpaceship2Reverse.isAnimationFinished(counter_passed_touch_up)){
						elapsed_time_saved = 0;
						counter_passed_touch_up = 0;
						touched = true;
						touched_left = true;
						touched_right = false;
						reversed_rotation = false;
						elapsedTime = 0;
						stopped_rotation_to_left = false;
						stopped_rotation_to_right = false;
						touched_right = !touched_left;
						finished_lateral_rotation = false;						
					} else {
						sprite.setRegion(animationSpaceship2Reverse
								.getKeyFrame(counter_passed_touch_up, true));
					}		
					if(MaxLeftp030 > sprite.getX() ){
						 velocity.x += (acceleration.x - 5.1f * velocity.x - 1.041f) * delta;
					} else {
						 velocity.x += (acceleration.x + 1.041f) * delta;
					}
					sprite.setPosition(
							sprite.getX() -
									velocity.x * elapsedTime * game_upper.percenter_from_center,
							sprite.getY());
					thrusters.setPosition(
							thrusters.getX() -
									velocity.x * elapsedTime * game_upper.percenter_from_center,
							thrusters.getY());
		    		spaceship_rectangle_collision.x =
							spaceship_rectangle_collision.x -
									velocity.x * elapsedTime * game_upper.percenter_from_center;
				} else {				
					if(animationSpaceship.isAnimationFinished(elapsedTime))
					{	
					} else {
						sprite.setRegion(animationSpaceship.getKeyFrame(elapsedTime, true));
					}	
					if(MaxLeftp030 > sprite.getX() ){
						 velocity.x += (acceleration.x - 5.1f * velocity.x) * delta;
					} else {
						 velocity.x += (acceleration.x - 0.01f + space_adjust) * delta;
					}
					sprite.setPosition(
							sprite.getX() -
									velocity.x * elapsedTime * game_upper.percenter_from_center,
							sprite.getY());
					thrusters.setPosition(
							thrusters.getX() -
									velocity.x * elapsedTime * game_upper.percenter_from_center,
							thrusters.getY());
		    		spaceship_rectangle_collision.x =
							spaceship_rectangle_collision.x -
									velocity.x * elapsedTime * game_upper.percenter_from_center;
				}
			} else if (touched_right) {
				was_going_left_last_time = false;
				was_going_right_last_time = true;				
				if(reversed_rotation){
					counter_passed_touch_up = COUNTER_PASSED_TOUCH_UP12;					
					if(animationSpaceshipReverse.isAnimationFinished(counter_passed_touch_up)){
						elapsed_time_saved = 0;
						counter_passed_touch_up = 0;
						touched = true;
						touched_right = true;
						touched_left = false;
						reversed_rotation = false;
						elapsedTime = 0;
						stopped_rotation_to_left = false;
						stopped_rotation_to_right = false;
						touched_right = !touched_left;
						finished_lateral_rotation = false;						
					} else {
						sprite.setRegion(animationSpaceshipReverse
								.getKeyFrame(counter_passed_touch_up, true));
					}	
					if(MaxRightm030 < sprite.getX() ){
						 velocity.x += (acceleration.x - 5.1f * velocity.x - 1.041f) * delta;
					} else {
						 velocity.x += (acceleration.x + 1.041f) * delta;
					} 
					sprite.setPosition(
							sprite.getX() +
									velocity.x * elapsedTime * game_upper.percenter_from_center,
							sprite.getY());
					thrusters.setPosition(
							thrusters.getX() +
									velocity.x * elapsedTime * game_upper.percenter_from_center,
							thrusters.getY());
		    		spaceship_rectangle_collision.x =
							spaceship_rectangle_collision.x +
									velocity.x * elapsedTime * game_upper.percenter_from_center;
				} else {				
					if(animationSpaceship2.isAnimationFinished(elapsedTime))
					{	
					} else {				
						sprite.setRegion(animationSpaceship2.getKeyFrame(elapsedTime, true));
					}
					if(MaxRightm030 < sprite.getX() ){
						 velocity.x += (acceleration.x - 5.1f * velocity.x) * delta;
					} else {
						 velocity.x += (acceleration.x - 0.01f + space_adjust) * delta;
					} 
					sprite.setPosition(
							sprite.getX() +
									velocity.x * elapsedTime * game_upper.percenter_from_center,
							sprite.getY());
					thrusters.setPosition(
							thrusters.getX() +
									velocity.x * elapsedTime * game_upper.percenter_from_center,
							thrusters.getY());
		    		spaceship_rectangle_collision.x =
							spaceship_rectangle_collision.x +
									velocity.x * elapsedTime * game_upper.percenter_from_center;
				}
			} else if (touched_middle){
			}
		} else { //	finger was touch upped (not on the screen anymore so touched = false 
			if(!finished_lateral_rotation){
				if(stopped_rotation_to_left){
					if(animationSpaceshipReverse.isAnimationFinished(counter_passed_touch_up)){
						finished_lateral_rotation = true;
						sprite.setRegion(middle);
						counter_passed_touch_up = 0;
						is_still_rolling = 3;
					} else {
						sprite.setRegion(animationSpaceshipReverse
								.getKeyFrame(counter_passed_touch_up, true));
					}
					sprite.setPosition(
							sprite.getX() - velocity.x * 10.2f * delta ,
							sprite.getY());
					thrusters.setPosition(
							thrusters.getX() - velocity.x * 10.2f * delta ,
							thrusters.getY());
		    		spaceship_rectangle_collision.x =
							spaceship_rectangle_collision.x  - velocity.x * 10.2f * delta;
				} else {
					if(animationSpaceship2Reverse.isAnimationFinished(counter_passed_touch_up)){
						finished_lateral_rotation = true;
						sprite.setRegion(middle);
						counter_passed_touch_up = 0;
						is_still_rolling = 3;
					} else {
						sprite.setRegion(animationSpaceship2Reverse
								.getKeyFrame(counter_passed_touch_up, true));
					}
					sprite.setPosition(
							sprite.getX() + velocity.x * 10.2f * delta ,
							sprite.getY());
					thrusters.setPosition(
							thrusters.getX() + velocity.x * 10.2f * delta ,
							thrusters.getY());
		    		spaceship_rectangle_collision.x =
							spaceship_rectangle_collision.x  + velocity.x * 10.2f * delta;
				}
			} else {
				sprite.setRegion(middle);
				game_upper.hud.finished_in_middle_ready = true;
			}
		}		
	}
	
	//##############################################################################################
	//explosion of the spaceship
	public void UpdateExplosion(ex01CryoHUDDisplay hud, float delta)
	{
		counter_explosion += delta;		
		if(!explosionAnimation.isAnimationFinished(counter_explosion)){
			sprite_explosion.setRegion(explosionAnimation.getKeyFrame(counter_explosion, true));

		} else {
			hudder = hud; exploded = true;
			game_upper.TurnToSepiaForContrast();
			ExplodeTimerTask.purge();	
			ExplodeTimerTask.schedule(new TimerTask() {
				@Override
				public void run(){					
					sprite_explosion.setRegion(textureAtlasExplosion.findRegion("spacesepxl0071"));
	    			game_upper.AppearHUDOnFail();
	    			game_upper.PauseGameScreen();
	    			hudder.AddClickRestartListener();
	    			hudder.AddClickResumeListener();		
				}
			}, 0);
		}					
		sprite_explosion.setPosition(
				sprite.getX() - SPRITE_EXPL_DELTA,
				sprite.getY() - SPRITE_EXPL_DELTA);
	}	
	
	public void Explode(ex01CryoHUDDisplay hud){
		hudder = hud;
		r_write_json.run();
	}

	//##############################################################################################
	//>>>we need to block the spaceship when it goes too far to the left
	//>>>update the spaceship : rotation, thrusters, shootings , and FAR too right or  LEFT behav.
	public void UpdateSpaceship(ex01CryoHUD hud, float delta){
		game_upper.finger_is_on_spaceship =
				(game_upper.newTouch.x >= (game_upper.spaces.sprite.getX() +
						game_upper.spaces.sprite.getWidth()/2 -
						game_upper.spaces.sprite.getWidth()/8)) &&
						(game_upper.newTouch.x <= (game_upper.spaces.sprite.getX() +
								game_upper.spaces.sprite.getWidth()/2 +
								game_upper.spaces.sprite.getWidth()/8));
		if(!game_upper.touch_downed_now && !game_upper.touch_dragged_now &&
				game_upper.finger_is_on_spaceship && !game_upper.hud.pressed_middle)
		{

			game_upper.hud.touchUP(game_upper.spaces);
			game_upper.spaces.touchUP_();
			reversed_rotation = false;
			elapsedTime = 0;
			counter_passed_touch_up = 0;
			touchUP_();
			update(hud.pressed_middle, delta);
		}
		if(hud.pressed_left){
	    	if(sprite.getX() > MaxLeftPlus015){	//too much to the left 
	    		update(hud.pressed_middle, delta);
	    	} else {
	    		reversed_rotation = false;
	    		elapsedTime = 0;
	    		counter_passed_touch_up = 0;
	    		touchUP_();
	    		update(hud.pressed_middle, delta);
	    		sprite.setPosition(maxLeft, sprite.getY());
	    		spaceship_rectangle_collision.x = MaxLeftPlusLDRC;
	    		thrusters.setPosition(maxLeft + lateral_distance_reseter, thrusters.getY());
	    	}
		//we need to block the spaceship when it goes too far to the right
		} else if (hud.pressed_right){
	    	if(sprite.getX() < MaxRightMinus015){ //too much to the right	 
	    		update(hud.pressed_middle, delta);
	    	} else {
	    		reversed_rotation = false;	
	    		elapsedTime = 0;
	    		counter_passed_touch_up = 0;	    		
	    		touchUP_();
	    		update(hud.pressed_middle, delta);
	    		sprite.setPosition(maxRight, sprite.getY());	
	    		spaceship_rectangle_collision.x = MaxRightPlusLDRC;	    		
	    		thrusters.setPosition(maxRight + lateral_distance_reseter, thrusters.getY());	    		
	    	}			
		} else if (hud.pressed_middle){
	    	if(sprite.getX() > MaxLeftPlus015){ //too much to the left	
	    		update(hud.pressed_middle, delta);
	    	} else {
	    		reversed_rotation = false;
	    		elapsedTime = 0;
	    		counter_passed_touch_up = 0;
	    		touchUP_();
	    		update(hud.pressed_middle, delta);
	    		sprite.setPosition(maxLeft , sprite.getY());
	    		spaceship_rectangle_collision.x = MaxLeftPlusLDRC;    		
	    		thrusters.setPosition(maxLeft + lateral_distance_reseter, thrusters.getY());
	    	}	
	    	if(sprite.getX() < MaxRightMinus015){ //too much to the right	 	
	    		update(hud.pressed_middle, delta);
	    	} else {
	    		reversed_rotation = false;	
	    		elapsedTime = 0;
	    		counter_passed_touch_up = 0;	    		
	    		touchUP_();
	    		update(hud.pressed_middle, delta);
	    		sprite.setPosition(maxRight, sprite.getY());
	    		spaceship_rectangle_collision.x = MaxRightPlusLDRC;	   	    		
	    		thrusters.setPosition(maxRight + lateral_distance_reseter, thrusters.getY());
	    	}		    	
		}
		game_upper.touch_downed_now = false;
		game_upper.touch_dragged_now = false;
	}
	
	public void StartAccelReal(){
		spaceship_acceleration_volume_real = SPACESHIP_ACCEL_VOLUME_REAL;
		spaceship_pitch_volume_real = SPACESHIP_PITCH_VOLUME_REAL;	
		spaceship_acceleration_real.stop();
		id_spaceship_acceleration_real = spaceship_acceleration_real.play(0.00f);
		spaceship_acceleration_real.setVolume(
				id_spaceship_acceleration_real,
				spaceship_acceleration_volume_real);
		spaceship_acceleration_real.setPitch(
				id_spaceship_acceleration_real,
				spaceship_pitch_volume_real);
		acceleration_overlap_playing_real = false;		
	}
	
	//##############################################################################################
	//>>>update spaceship acceleration as with the FUD above
	public void UpdateSpaceshipAccel(OrthographicCamera camera,
									 float zoom_current,
									 float delta){

		//float screen_resizer = screen_sizeh / 800f;
		//float delta_64_srsz = delta * 64f * screen_resizer; CU ASTA ACCELEREAZA CA POLA on S4
		delta_64_srsz = delta * 64f;
		//if(pressed_accel && FUD_accel_count < MAX_FUD_ACCEL){ we no longer use this because it is
		// frame rate dependent ...
		//.. and it will stop not at the same position every time (longer frames mean linger
		// distances)
		if(pressed_accel && how_much_advanced_world_units_h < finalDest3){
			camera.zoom += 0.0020f;
			if(spaceship_pitch_volume + 0.00045f >= 1.8f){
				spaceship_pitch_volume = 1.8f;
			} else {
				spaceship_pitch_volume += 0.00045f;
				if(!accel_real_started){
					StartAccelReal();
					accel_real_started = true;
				}
				spaceship_pitch_volume_real += 0.00045f;
				spaceship_acceleration_volume_real += 0.00005f;
			}
			if(spaceship_pitch_volume_overlap + 0.00045f >= 1.8f){
				spaceship_pitch_volume_overlap = 1.8f;
			} else {
				spaceship_pitch_volume_overlap += 0.00045f;
			}		
			spaceship_acceleration_real.setPitch(
					id_spaceship_acceleration_real,
					spaceship_pitch_volume_real);
			spaceship_acceleration_real.setVolume(
					id_spaceship_acceleration_real,
					spaceship_acceleration_volume_real);
			spaceship_acceleration.setPitch(
					id_spaceship_acceleration,
					spaceship_pitch_volume);
			//velocity1 += accel * delta;
			//advance = velocity1 * delta_64_srsz;
			advance = 0;
			how_much_advanced_world_units_h += advance;
			sprite.setPosition(sprite.getX(), sprite.getY() + advance);
			spaceship_rectangle_collision.setPosition(
					spaceship_rectangle_collision.x,
					spaceship_rectangle_collision.y + advance);
			thrusters.setScale(scaleX, scaleY);
			thrusters.setPosition(thrusters.getX(), thrusters.getY() + advance);			
			eShock1.setPosition(eShock1.getX(), eShock1.getY() + advance);
			eShock2.setPosition(eShock2.getX(), eShock2.getY() + advance);
			eShock3.setPosition(eShock3.getX(), eShock3.getY() + advance);			
			scaleX += 0.01f;
			scaleY += 0.01f;
			FUD_accel_count++;			
		} else if (!pressed_accel){
			if (FUD_accel_count > 0) {
				camera.zoom -= 0.0020f;
				accel_real_started = false;				
				spaceship_pitch_volume_real -= 0.00245f;
				spaceship_acceleration_volume_real -= 0.01125f;
				if(spaceship_pitch_volume_real <= 1f)
					spaceship_pitch_volume_real = 1f;
				if(spaceship_acceleration_volume_real <= 0f)
					spaceship_acceleration_volume_real = 0f;
				spaceship_acceleration_real.setPitch(
						id_spaceship_acceleration_real,
						spaceship_pitch_volume_real);
				spaceship_acceleration_real.setVolume(
						id_spaceship_acceleration_real,
						spaceship_acceleration_volume_real);
				spaceship_pitch_volume_overlap -= 0.00045f;				
				spaceship_pitch_volume -= 0.00045f;
				spaceship_acceleration.setPitch(
						id_spaceship_acceleration,
						spaceship_pitch_volume);
				//advance = velocity1 * delta_64_srsz;
				advance = 0;
				how_much_advanced_world_units_h -= advance;				
				sprite.setPosition(
						sprite.getX(),
						sprite.getY() - advance);
				spaceship_rectangle_collision.setPosition(
						spaceship_rectangle_collision.x,
						spaceship_rectangle_collision.y - advance);
				thrusters.setPosition(
						thrusters.getX(),
						thrusters.getY() - advance);
				thrusters.setScale(scaleX, scaleY);
				eShock1.setPosition(eShock1.getX(), eShock1.getY() - advance);
				eShock2.setPosition(eShock2.getX(), eShock2.getY() - advance);
				eShock3.setPosition(eShock3.getX(), eShock3.getY() - advance);				
				//velocity1 -= accel * delta;
				FUD_accel_count--;
				scaleX -= 0.01f;
				scaleY -= 0.01f;
			} else {
				spaceship_acceleration_volume_real = SPACESHIP_ACCEL_VOLUME_REAL;
				spaceship_pitch_volume_real = SPACESHIP_PITCH_VOLUME_REAL;
				spaceship_acceleration_real.setPitch(
						id_spaceship_acceleration_real,
						spaceship_pitch_volume_real);
				spaceship_acceleration_real.stop();
				accel_real_started = false;
				spaceship_pitch_volume = SPACESHIP_PITCH_VOLUME;
				spaceship_acceleration.setPitch(
						id_spaceship_acceleration,
						spaceship_pitch_volume);
				sprite.setPosition(
						sprite.getX(),
						sprite.getY() - how_much_advanced_world_units_h);
				spaceship_rectangle_collision.setPosition(
						spaceship_rectangle_collision.x,
						spaceship_rectangle_collision.y - how_much_advanced_world_units_h);
				thrusters.setPosition(
						thrusters.getX(),
						thrusters.getY() - how_much_advanced_world_units_h);
				eShock1.setPosition(
						eShock1.getX(),
						eShock1.getY() - how_much_advanced_world_units_h);
				eShock2.setPosition(
						eShock2.getX(),
						eShock2.getY() - how_much_advanced_world_units_h);
				eShock3.setPosition(
						eShock3.getX(),
						eShock3.getY() - how_much_advanced_world_units_h);
				how_much_advanced_world_units_h = 0;
				velocity1 = velocity_baser;
				camera.zoom = zoom_current;
			}
		}
	}	

	//##############################################################################################
	//world_velocity ( amount we carry camera, hud, etc,...) gets increased then decreased upon
	//	touch up based on acceleration
	//update world acceleration : NOTE : IMPORTANT : in the beginning there is only the camera
	//  and the objects that stand still; the UpdateHUD and UpdateSpaceshipAccel above will accel.
	//  the FUD and spaceship by advancing on the screen on a still camera . actually the FUD
	//  and spaceship have no speed they just dance up and down on the screen. NOW,
	//  UpdateWorldAccel will advance the back, the camera and spaceship, the FUD and HUD,
	//  by giving them speed in the world. they use the same acceleration as the static
	//  acceleration used for the FUD and spaceship above
	public void UpdateWorldAccel(float delta){
		if(pressed_accel && WORLD_accel_count < MAX_FUD_ACCEL){
			world_velocity += Xaccel1 * delta; WORLD_accel_count++;
		} else if (!pressed_accel){
			if (WORLD_accel_count > 0) { world_velocity -= Xaccel1 * delta; WORLD_accel_count--;
			} else { world_velocity = WORLD_BASER_ALL; world_velocity_original = world_velocity; }
		}
	}	
	
	//updates the explosions from lightning electroshocks animation 
	public void UpdateElectroShocks(float delta){
		counterElectroShock1 += delta;
		counterElectroShock2 += delta;
		counterElectroShock3 += delta;
		if(shock_active1){
			if(electroShockAnimation.isAnimationFinished(counterElectroShock1)){
				shock_active1 = false;
				counterElectroShock1 = 0;
				give_electro_shock_activated = false;
				give_electro_shock_activated_denier = false;
			} else {
				eShock1.setRegion(electroShockAnimation.getKeyFrame(counterElectroShock1, true)); }
		}
		if(shock_active2){		
			if(electroShockAnimation.isAnimationFinished(counterElectroShock2)){
				shock_active2 = false;
				counterElectroShock2 = 0;
				give_electro_shock_activated = false;
				give_electro_shock_activated_denier = false;
			} else {
				eShock2.setRegion(electroShockAnimation.getKeyFrame(counterElectroShock2, true)); }
		}
		if(shock_active3){		
			if(electroShockAnimation.isAnimationFinished(counterElectroShock3)){
				shock_active3 = false;
				counterElectroShock3 = 0;
				give_electro_shock_activated = false;
				give_electro_shock_activated_denier = false;
			} else {
				eShock3.setRegion(electroShockAnimation.getKeyFrame(counterElectroShock3, true)); }
		}
	}
	
	//renders the explosions from lightning electroshocks
	public void renderElectroShocks(SpriteBatch batch){
		if (shock_active1) eShock1.draw(batch);
		if (shock_active2) eShock2.draw(batch);
		if (shock_active3) eShock3.draw(batch);		
	}	
	
	// this selects the bullets power-up that is available and repositions it 
	public void ex01CryoshipLaserShootGenerate(Vector2 startv,
											   Vector2 endv){
		if(!is_bullet_busy[0]){
			laser_shoots.get(0).PositionLaserShootAt(startv, endv);
			is_bullet_busy[0] = true;
			which_to_change = 0;
		} else if(!is_bullet_busy[1]){
			laser_shoots.get(1).PositionLaserShootAt(startv, endv);
			is_bullet_busy[1] = true;
			which_to_change = 1;
		} else if(!is_bullet_busy[2]){
			laser_shoots.get(2).PositionLaserShootAt(startv, endv);
			is_bullet_busy[2] = true;
			which_to_change = 2;
		} else if(!is_bullet_busy[3]){
			laser_shoots.get(3).PositionLaserShootAt(startv, endv);
			is_bullet_busy[3] = true;
			which_to_change = 3;
		} else if(!is_bullet_busy[4]){
			laser_shoots.get(4).PositionLaserShootAt(startv, endv);
			is_bullet_busy[4] = true;
			which_to_change = 4;
		} else if(!is_bullet_busy[5]){
			laser_shoots.get(5).PositionLaserShootAt(startv, endv);
			is_bullet_busy[5] = true;
			which_to_change = 5;
		} else if(!is_bullet_busy[6]){
			laser_shoots.get(6).PositionLaserShootAt(startv, endv);
			is_bullet_busy[6] = true;
			which_to_change = 6;
		} else if(!is_bullet_busy[7]){
			laser_shoots.get(7).PositionLaserShootAt(startv, endv);
			is_bullet_busy[7] = true;
			which_to_change = 7;
		} else if(!is_bullet_busy[8]){
			laser_shoots.get(8).PositionLaserShootAt(startv, endv);
			is_bullet_busy[8] = true;
			which_to_change = 8;
		} else if(!is_bullet_busy[9]){
			laser_shoots.get(9).PositionLaserShootAt(startv, endv);
			is_bullet_busy[9] = true;
			which_to_change = 9;
		} else if(!is_bullet_busy[10]){
			laser_shoots.get(10).PositionLaserShootAt(startv, endv);
			is_bullet_busy[10] = true;
			which_to_change = 10;
		} else if(!is_bullet_busy[11]){
			laser_shoots.get(11).PositionLaserShootAt(startv, endv);
			is_bullet_busy[11] = true;
			which_to_change = 11;
		} else if(!is_bullet_busy[12]){
			laser_shoots.get(12).PositionLaserShootAt(startv, endv);
			is_bullet_busy[12] = true;
			which_to_change = 12;
		} else if(!is_bullet_busy[13]){
			laser_shoots.get(13).PositionLaserShootAt(startv, endv);
			is_bullet_busy[13] = true;
			which_to_change = 13;
		} else if(!is_bullet_busy[14]){
			laser_shoots.get(14).PositionLaserShootAt(startv, endv);
			is_bullet_busy[14] = true;
			which_to_change = 14;
		} else if(!is_bullet_busy[15]){
			laser_shoots.get(15).PositionLaserShootAt(startv, endv);
			is_bullet_busy[15] = true;
			which_to_change = 15;
		}		
		laser_shoots.get(which_to_change).blocked_after_first_explosion_not_repositioned = false;
	}			
	
	public void CreateFirstStartEnd(){
		start.x = spaceship_rectangle_collision.x ;
		start.y = spaceship_rectangle_collision.y;
		end.x = spaceship_rectangle_collision.x;
		end.y = spaceship_rectangle_collision.y + spaceship_rect_heightp208;		
	}
	
	public void Create16Shootings(){
		for(i=0; i<NO_SHOOTINGS; i++){
			ex01CryoshipLaserShoot new_laser_shoot = new ex01CryoshipLaserShoot(
					i,
					this,
					start,
					end,
					MicroExplosion[0],
					textureAtlas_laser,
					textureAtlas_laserOver);
			laser_shoots.add(new_laser_shoot);
		}
	}
	
	public void Create16ShootingsReload(){
		textureAtlas_laser = textureAtlas.findRegion("bullet_base");
		textureAtlas_laserOver = textureAtlas.findRegion("laser_overlay");
		for(i=0; i<NO_SHOOTINGS; i++){
			laser_shoots.get(i).ex01CryoshipLaserShootReload(
					start,
					end,
					MicroExplosion[0],
					textureAtlas_laser,
					textureAtlas_laserOver);
		}
	}
	
	//this creates laser shoots, advances them and disposes the ones that are too far 
	public void UpdateShootings(Circle spaceship_rect,
								ex01CryoHUD hud,
								ex01CryoHUDDisplay hud_display,
								float delta,
								float velocity,
								float camera_y){
		passed_since_last_laser_shoot += delta;
		//now  new laser if we pressed shoot
		if(hud.pressed_shoot_once){
			if(can_laser_shoot){				
				start.x = spaceship_rect.x;
				start.y = spaceship_rect.y;
				end.x = spaceship_rect.x;
				end.y = spaceship_rect.y + spaceship_rect_heightp208;
				ex01CryoshipLaserShootGenerate(
						start,
						end);
				can_laser_shoot = false;
				laser_shoot.play(0.05f); 
				counter_shoot++;
				if(counter_shoot == SHOOTSNO_PER_SHOOT){
					counter_shoot = 0;
					hud_display.RecedeBulletsCounter();
				}
				hud.pressed_shoot_once = false;
			}
			if(no_more_bullets){
				hud_display.no_more_bullets_recorded = true;
				hud_display.NoMoreBullets();
			}
		}		
		
		if(passed_since_last_laser_shoot > LASER_DELAY_INBETWEEN && !no_more_bullets){
			passed_since_last_laser_shoot = 0; can_laser_shoot = true;
		} else { can_laser_shoot = false; }

		//advance the existing lasers AND delete laser shootings out of screen
		for(lasersj = laser_shoots.size() - 1; lasersj >= 0; lasersj--){
			if(is_bullet_busy[lasersj]){
				//advance the existing lasers	
				lshoot = laser_shoots.get(lasersj);
				lshoot.AdvanceLaserShoot(velocity);
				lshoot.SetPosition();
				//delete laser shootings out of screen			
				if(lshoot.collision_rect.y>camera_y+hscreenadd){
					is_bullet_busy[lasersj] = false;
					laser_shoots.get(lasersj).can_explode_now = false;
					laser_shoots.get(lasersj).blocked_after_first_explosion_not_repositioned =
							false;
					laser_shoots.get(lasersj).finished_exploding = false;
					laser_shoots.get(lasersj).counter_explosion = 0f;
				}
			}
		}			
	}

	//render the laser shoot explosion
	public void renderLasersExplosion(SpriteBatch batch){
		for(int i = 0; i < laser_shoots.size(); i++){
			if(laser_shoots.get(i).can_explode_now && !laser_shoots.get(i).finished_exploding){
				laser_shoots.get(i).explosion.draw(batch);
			}
		}
	}	
	
	public void renderLasers(OrthographicCamera camera){ //render the laser shoot lasers 
	    Gdx.gl20.glEnable(GL20.GL_BLEND);
	    Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);  
	    shader.begin();	 
	    shader.setUniformMatrix("u_worldView", camera.combined);
	    shader.setUniformi("u_texture", 0);	 	    
	    textureAtlas_laser.getTexture().bind(0);	
		for(int i = 0; i < laser_shoots.size(); i++){
			if(!laser_shoots.get(i).can_explode_now && is_bullet_busy[i]){
				mesh.setVertices(laser_shoots.get(i).vert);
				mesh.setIndices(laser_shoots.get(i).indi);		    	    
				meshO.setVertices(laser_shoots.get(i).vertO);
				meshO.setIndices(laser_shoots.get(i).indiO);
			    mesh.render(shader, GL20.GL_TRIANGLES);				
			    meshO.render(shader, GL20.GL_TRIANGLES);			    
			}
		}	
	    shader.end();			
	}
	
	public void DisposeSounds(){
		if(sound_power_down!=null)
			sound_power_down.dispose(); sound_power_down = null;
		if(sound_accelspace1_rev!=null)
			sound_accelspace1_rev.dispose(); sound_accelspace1_rev = null;
		if(sShock1!=null)
			sShock1.dispose(); sShock1 = null;
		if(sShock2!=null)
			sShock2.dispose(); sShock2 = null;
		if(sShock3!=null)
			sShock3.dispose(); sShock3 = null;
		if(sShock1Explode!=null)
			sShock1Explode.dispose(); sShock1Explode = null;
		if(sShock2Explode!=null)
			sShock2Explode.dispose(); sShock2Explode = null;
		if(sShock3Explode!=null)
			sShock3Explode.dispose(); sShock3Explode = null;
		if(spaceship_accel_big!=null)
			spaceship_accel_big.dispose(); spaceship_accel_big = null;
		if(laser_shoot!=null)
			laser_shoot.dispose(); laser_shoot = null;
		if(blocker_hit_explosion!=null)
			blocker_hit_explosion.dispose(); blocker_hit_explosion = null;
		if(powerup_health_hit!=null)
			powerup_health_hit.dispose(); powerup_health_hit = null;
		if(powerup_bullets_hit!=null)
			powerup_bullets_hit.dispose(); powerup_bullets_hit = null;
		if(spaceship_explosion!=null)
			spaceship_explosion.dispose(); spaceship_explosion = null;
		if(spaceship_acceleration!=null)
			spaceship_acceleration.dispose(); spaceship_acceleration = null;
		if(spaceship_acceleration_real!=null)
			spaceship_acceleration_real.dispose(); spaceship_acceleration_real = null;
		if(spaceship_acceleration_overlap!=null)
			spaceship_acceleration_overlap.dispose(); spaceship_acceleration_overlap = null;
		if(game_music!=null)
			game_music.dispose(); game_music = null;
	}
	
	public void Dispose(){
		DisposeSounds();
		thrusters = null;
		sprite_explosion = null;
		eShock1 = null;
		eShock2 = null;
		eShock3 = null;
		sprite = null;
		sprite_base = null;
		space1_bigS = null; 
		space2_bigS = null;
		space3_bigS = null;
		animationSpaceship = null;
		animationSpaceship2 = null;
		animationSpaceshipReverse = null;
		animationSpaceship2Reverse = null;
		explosionAnimation = null;
		explosionAnimationOver = null;
		explosionMicroAnimation = null;
		electroShockAnimation = null;
		middle = null;
		space1_bigAR = null;
		space2_bigAR = null;
		space3_bigAR = null;
		if(atlas_space_big!=null){
			atlas_space_big.dispose();
			atlas_space_big = null;
		}
		if(textureAtlas!=null){
			textureAtlas.dispose();
			textureAtlas = null;
		}
		if(textureAtlasSec!=null){
			textureAtlasSec.dispose();
			textureAtlasSec = null;
		}
		if(textureAtlasExplosion!=null){
			textureAtlasExplosion.dispose();
			textureAtlasExplosion = null;
		}
		if(textureAtlasExplosionOver!=null){
			textureAtlasExplosionOver.dispose();
			textureAtlasExplosionOver = null;
		}
		for(int i=0; i<MicroExplosion.length; i++){
			MicroExplosion[i] = null;
		}
		textureAtlas_laser = null;
		textureAtlas_laserOver = null;
		thruster = null;
		if(texture_space_base!=null){
			texture_space_base.dispose();
			texture_space_base = null;
		}
		spaceship_rectangle_collision = null;
		if(mesh!=null){
			mesh.dispose();
			mesh = null;
		}
		if(meshO!=null){
			meshO.dispose();
			meshO = null;
		}
		start = null;
		end = null;
		acceleration = null;
		velocity = null;
		if(shader!=null) {
			shader.end();
			shader.dispose();
		}
		if(shader_base!=null) {
			shader_base.end();
			shader_base.dispose();
		}
		if(shaderGrey!=null) {
			shaderGrey.end();
			shaderGrey.dispose();
		}
		if(laser_shoots!=null){
			for(ex01CryoshipLaserShoot lshootone : laser_shoots){
				lshootone.Dispose();
				lshootone = null;
			}
			laser_shoots.clear(); laser_shoots = null;
		}
		if(explosion_laser_blocker!=null){
			for(Sprite explosion_blocker : explosion_laser_blocker){
				explosion_blocker = null;
			}
			explosion_laser_blocker.clear(); explosion_laser_blocker = null;
		}
		r_write_json = null;
		r_write_json2 = null;
		t_write_json = null;
		t_write_json2 = null;
		hudder = null;
		lshoot = null;
		game_upper = null;
		if(TimerTaskExploded!=null) {
			TimerTaskExploded.cancel();
			TimerTaskExploded = null;
		}
		if(ExplodeTimerTask!=null) {
			ExplodeTimerTask.cancel();
			ExplodeTimerTask = null;
		}
		if(ExplodeTimerTaskLoad!=null) {
			ExplodeTimerTaskLoad.cancel();
			ExplodeTimerTaskLoad = null;
		}
		if(load_spaceship!=null){
			load_spaceship.clear();
			load_spaceship.dispose();
			load_spaceship = null;
		}
		for(int i=0; i<SpaceshipRotateLeft.length; i++){
			SpaceshipRotateLeft[i] = null;
		}
		for(int i=0; i<SpaceshipRotateRight.length; i++){
			SpaceshipRotateRight[i] = null;
		}
		for(int i=0; i<SpaceshipRotateLeftReverse.length; i++){
			SpaceshipRotateLeftReverse[i] = null;
		}
		for(int i=0; i<SpaceshipRotateRightReverse.length; i++){
			SpaceshipRotateRightReverse[i] = null;
		}
		for(int i=0; i<SpriteExplosion.length; i++){
			SpriteExplosion[i] = null;
		}
		for(int i=0; i<SpriteExplosionOver.length; i++){
			SpriteExplosionOver[i] = null;
		}
		for(int i=0; i<SpriteElectroShock.length; i++){
			SpriteElectroShock[i] = null;
		}
	}
	
	public void StopAccelerationSounds(){
		spaceship_acceleration.stop(id_spaceship_acceleration);
		spaceship_acceleration_real.stop(id_spaceship_acceleration_real);
		sound_power_down.play(ex01Types.POWER_DOWN_VOL);
	}
	
	public void PlayDownSoundsForFinish(){
		sound_accelspace1_rev.play(spaceship_acceleration_volume);		
	}
	
	public void StopSoundsForPauseResume(){
		sShock1.stop();
		sShock2.stop();
		sShock3.stop();
		sShock1Explode.stop();
		sShock2Explode.stop();
		sShock3Explode.stop();	
		laser_shoot.stop();
		blocker_hit_explosion.stop();
		powerup_health_hit.stop();
		powerup_bullets_hit.stop();
		spaceship_explosion.stop();
		spaceship_acceleration.stop(id_spaceship_acceleration);
		spaceship_acceleration_real.stop(id_spaceship_acceleration_real);
	}
	
	public void PrepareSpaceshipSounds(){
		id_spaceship_acceleration = spaceship_acceleration.loop(spaceship_acceleration_volume);
		spaceship_acceleration.setVolume(id_spaceship_acceleration, spaceship_acceleration_volume);
		spaceship_acceleration.setPitch(id_spaceship_acceleration, spaceship_pitch_volume);
		AlsoStartMusic();
	}

	public void AlsoStartMusic(){
		game_music.setVolume(ex01Types.MUSIC_GAME_LEVEL);
		game_music.setLooping(true);
		game_music.play();
	}

	public void AlsoKillMusic(){
		if(game_music!=null && game_music.isPlaying()){
			game_music.pause();
		}
	}
	
	public void ResetSoundsFromPaused(){
		id_spaceship_acceleration = spaceship_acceleration.loop(spaceship_acceleration_volume);
		spaceship_acceleration.setPriority(id_spaceship_acceleration, 0);
		spaceship_acceleration.setVolume(id_spaceship_acceleration, spaceship_acceleration_volume);
		spaceship_acceleration.setPitch(id_spaceship_acceleration, spaceship_pitch_volume);
	}

	public void ResumeSoundsFromPaused(){
		id_spaceship_acceleration = spaceship_acceleration.loop(spaceship_acceleration_volume);
		spaceship_acceleration.setPriority(id_spaceship_acceleration, 0);
		spaceship_acceleration.setVolume(id_spaceship_acceleration, spaceship_acceleration_volume);
		spaceship_acceleration.setPitch(id_spaceship_acceleration, spaceship_pitch_volume);	
	}	
}
