//WMS3 2015

package com.cryotrax.game;
import com.badlogic.gdx.Gdx;

enum CRBlockertType {
	RoundRobin,
	Rotative,
	Bouncer,
	RotativeBouncer,
	RotativeRobin,
	RobinBouncer
}	

enum PowerupType {
	Health,
	Bullets,
}

enum HealthType {
	Worst,
	Middle,
	Best,
}

enum BulletType {
	Worst,
	Middle,
	Best,
}

enum GameState {
	GAME_SCREEN,
	MENU_SCREEN,
	LEVELS_SCREEN,
	SPLASH_SCREEN,
	LEVEL_ENTRY,
	GAME_LOADING_SCREEN
}

public final class ex01Types {
	public static final String TEST_DEVICE_ID = "B2E2D3E72C2E1AD78354603FB45C68E8";
	public static final String BUY_ADDRESS = "market://details?id=com.cryotraxprem.game.android";
	public static final boolean RENDER_COLLISION = false;
	//spaceship lateral speed
	public static final float TSPACE_ADJUST1 = +0.05f;
	public static final float TSPACE_ADJUST2 = +0.25f;
	public static final float TSPACE_ADJUST3 = +0.55f;
	//ADDMOB CA-PUB	+ ADD SETTINGS
	public static final String TADMOB_CA_PUBB = "ca-app-pub-5436286811878318/4010345984";
	//public static final long DELTA_INTERSTITIAL = 1000 * 60 * 5; //5  min
	public static final long DELTA_INTERSTITIAL = 1000; // 1 sec
	public static final String degm = " deg";
	public static final String DEG = " DEG";
	public static final String OF = " of ";
	public static final String X = "X";
	public static final String PERFECT = "PERFECT ANGLE!";
	public static final float ZEROF = 0f;
	public static final String ZERO_ANGLE = "SUPER AWESOME!";
	public static final String AWESOME_ANGLE = "AWESOME! < ";
	public static final String VGOOD_ANGLE = "VERY GOOD! < ";
	public static final String GOOD_ANGLE = "NICE! < ";
	public static final String OK_ANGLE = "OK!";
	public static final String BAD_ANGLE = "KINDA BAD!";
	public static final long AD_LOAD_DELAY_PERMITED = 2500;
	public static final float BASE_ROTATION = 0f;
	public static final float BASE_ALPHA_ZERO = 0f;
	public static final boolean SIGNOUT_SUCCESS = true;
	public static final boolean SIGNIN_CANCELED_YES = true;
	public static final boolean SIGNIN_SUCCESS = true;
	public static final boolean B_CHANGE_LOGIN = true;
	public static final boolean B_DONT_CHANGE_LOGIN = false;
	public static final boolean B_NEXT_IS_SIGNOUT_FALSE = false;
	public static final boolean DONT_CHANGE_SOCIAL = false;
	public static final boolean CHANGE_SOCIAL = true;
	public static final boolean VIEWED_AD_NO = false;
	public static final boolean VIEWED_AD_YES = true;
	public static final boolean LOGIN_NEXT_YES = true;
	public static final boolean LOGIN_NEXT_NO = false;
	public static final boolean LOGOUT_NEXT_YES = true;
	public static final int VIRTUAL_SCREEN_WIDTH = 480;
	public static final int VIRTUAL_SCREEN_HEIGHT = 800;
	public static final String RANKING_SOCIAL = "Social: ";
	public static final String RANKING_SOCIAL_NA = "Social: N/A";
	public static final String RANKING_PUBLIC = "Public: ";
	public static final String RANKING_PUBLIC_NA = "Public: N/A";
	public static final String RANKING_REFRESH = "REFRESH!";
	public static final String SOUND_MN_DONE_B01big = "sounds/menu/done_button.ogg";
	public static final String SOUND_MN_GREEN_HAVE_B01big = "sounds/menu/green_already_have.ogg";
	public static final String SOUND_MN_ERROR_NO_B01big = "sounds/menu/error_sound_no.ogg";
	public static final String SOUND_MN_LIFE_B01big = "sounds/menu/lifeslots_heartpump.ogg";
	public static final String SOUND_MN_AMMO_B01big = "sounds/menu/ammo_gun_load.ogg";
	public static final String SOUND_MN_CASHR_B01big = "sounds/menu/cash_register_coins.ogg";
	public static final String SOUND_MN_ENTERP_B01big = "sounds/menu/enter_play_game.ogg";
	public static final String SOUND_MN_EXIT_B01big = "sounds/menu/exit_code.ogg";
	public static final String SOUND_MN_SELECTOR_B01big = "sounds/menu/menu_selector.ogg";
	public static final String SOUND_MN_LEVELO_B01big = "sounds/menu/level_opener.ogg";
	public static final String SOUND_MN_LEVEL_LR_B01big = "sounds/menu/level_left_right.ogg";
	public static final String SOUND_MN_BACK_B01big = "sounds/menu/back_button.ogg";
	public static final String SOUND_MN_BASIC_B01big = "sounds/menu/level_left_right.ogg";
	public static final String SOUND_MN_SUCCESS_B01big = "sounds/menu/login_audio.ogg";
	public static final String SOUND_MN_LOGIN_B01big = "sounds/menu/login.ogg";
	public static final String SOUND_MN_LOGOUT_B01big = "sounds/menu/logout.ogg";
	public static final String SHADER_VIGNETTE_VERT01big = "data/shaders/vignette.vert";
	public static final String SHADER_VIGNETTE_FRAG01big = "data/shaders/vignette.frag";
	public static final String SHADER_GRAYSCALE_VERT01big = "data/shaders/grayscale.vert";
	public static final String SHADER_GRAYSCALE_FRAG01big = "data/shaders/grayscale.frag";
	public static final String SHADER_SEPIA_VERT01big = "data/shaders/sepia.vert";
	public static final String SHADER_SEPIA_FRAG01big = "data/shaders/sepia.frag";
	public static final String SHADER_SEPIA2_VERT01big = "data/shaders/sepia2.vert";
	public static final String SHADER_SEPIA2_FRAG01big = "data/shaders/sepia2.frag";
	public static final String SHADER_GRAYSCALEL_VERT01big = "data/shaders/grayscale_lasers.vert";
	public static final String SHADER_GRAYSCALEL_FRAG01big = "data/shaders/grayscale_lasers.frag";
	public static final String SOUND_ACCELSPACE1_REV = "sounds/accelspace01rev.ogg";
	public static final String SOUND_POWER_DOWN = "sounds/power_down.ogg";
	public static final String SOUND_ACCELSPACE1 = "sounds/accelspace01.ogg";
	public static final String SOUND_ACCELSPACE2 = "sounds/accelspace02.ogg";
	public static final String SOUND_POWERUP_BULLETS = "sounds/powerup_bullets.ogg";
	public static final String SOUND_POWERUP_HEALTH = "sounds/powerup_health.ogg";
	public static final String SOUND_LASER_SHOOT = "sounds/laser_shoot.ogg";
	public static final String SOUND_SPACESHIP_EXPLOSION = "sounds/spaceship_explosion.ogg";
	public static final String SOUND_BLOCKER_HIT_EXPLOSION = "sounds/blocker_hit_explosion.ogg";
	public static final String SOUND_ELECTRO_SHOCK = "sounds/electro_shock.ogg";
	public static final String SOUND_ELECTRO_SHOCK_EXPLODE = "sounds/electro_shock_explode.ogg";
	public static final String MUSIC_MENU1 = "sounds/menumusic/blockersmenu.ogg";
	public static final String MUSIC_GAME1 = "sounds/gamemusic/musicfinalgame.ogg";
	public static final float MUSIC_MENU_LEVEL = 0.25f;
	public static final float MUSIC_MENU_LEVEL_NULL = 0.00f;
	public static final float MUSIC_GAME_LEVEL = 0.25f;
	public static final float BLOCKER_HIT = 0.0715f;
	public static final float POWER_DOWN_VOL = 0.225f;

	//##############################################################################################
	//##############################################################################################
	//##############################################################################################
	public static final String MAIN_ATLASABACK1_ATLAS01big =
			"graphics/size01big/mainatlasABack1 result/mainatlasABack1.atlas";
	public static final String MAIN_ATLASABACK2_ATLAS01big =
			"graphics/size01big/mainatlasABack2 result/mainatlasABack2.atlas";
	public static final String MAIN_ATLASABACK3_ATLAS01big =
			"graphics/size01big/mainatlasABack3 result/mainatlasABack3.atlas";
	public static final String MAIN_ATLASABACK4_ATLAS01big =
			"graphics/size01big/mainatlasABack4 result/mainatlasABack4.atlas";
	public static final String MAIN_ATLASBBACK1_ATLAS01big =
			"graphics/size01big/mainatlasBBack1 result/mainatlasBBack1.atlas";
	public static final String MAIN_ATLASBBACK2_ATLAS01big =
			"graphics/size01big/mainatlasBBack2 result/mainatlasBBack2.atlas";
	public static final String MAIN_ATLASBBACK3_ATLAS01big =
			"graphics/size01big/mainatlasBBack3 result/mainatlasBBack3.atlas";
	public static final String MAIN_ATLASBBACK4_ATLAS01big =
			"graphics/size01big/mainatlasBBack4 result/mainatlasBBack4.atlas";
	public static final String MAIN_ATLASCBACK1_ATLAS01big =
			"graphics/size01big/mainatlasCBack1 result/mainatlasCBack1.atlas";
	public static final String MAIN_ATLASCBACK2_ATLAS01big =
			"graphics/size01big/mainatlasCBack2 result/mainatlasCBack2.atlas";
	public static final String MAIN_ATLASCBACK3_ATLAS01big =
			"graphics/size01big/mainatlasCBack3 result/mainatlasCBack3.atlas";
	public static final String MAIN_ATLASCBACK4_ATLAS01big =
			"graphics/size01big/mainatlasCBack4 result/mainatlasCBack4.atlas";
	public static final String MAIN_ATLASABACK1_SKIN01big =
			"graphics/size01big/mainatlasABack1 result/mainatlasABack1.json";
	public static final String MAIN_ATLASABACK2_SKIN01big =
			"graphics/size01big/mainatlasABack2 result/mainatlasABack2.json";
	public static final String MAIN_ATLASABACK3_SKIN01big =
			"graphics/size01big/mainatlasABack3 result/mainatlasABack3.json";
	public static final String MAIN_ATLASABACK4_SKIN01big =
			"graphics/size01big/mainatlasABack4 result/mainatlasABack4.json";
	public static final String MAIN_ATLASBBACK1_SKIN01big =
			"graphics/size01big/mainatlasBBack1 result/mainatlasBBack1.json";
	public static final String MAIN_ATLASBBACK2_SKIN01big =
			"graphics/size01big/mainatlasBBack2 result/mainatlasBBack2.json";
	public static final String MAIN_ATLASBBACK3_SKIN01big =
			"graphics/size01big/mainatlasBBack3 result/mainatlasBBack3.json";
	public static final String MAIN_ATLASBBACK4_SKIN01big =
			"graphics/size01big/mainatlasBBack4 result/mainatlasBBack4.json";
	public static final String MAIN_ATLASCBACK1_SKIN01big =
			"graphics/size01big/mainatlasCBack1 result/mainatlasCBack1.json";
	public static final String MAIN_ATLASCBACK2_SKIN01big =
			"graphics/size01big/mainatlasCBack2 result/mainatlasCBack2.json";
	public static final String MAIN_ATLASCBACK3_SKIN01big =
			"graphics/size01big/mainatlasCBack3 result/mainatlasCBack3.json";
	public static final String MAIN_ATLASCBACK4_SKIN01big =
			"graphics/size01big/mainatlasCBack4 result/mainatlasCBack4.json";
	public static final String MAIN_ATLAS_SEC_ATLAS01big =
			"graphics/size01big/mainatlasSec result/mainatlasSec.atlas";
	public static final String hud_angler_font01big =
			"fonts/fonts01/font28Disp.fnt";
	public static final String hud_angler_fontp01big =
			"fonts/fonts01/font28Dispp.fnt";
	public static final String hud_angler_font_title01big =
			"fonts/fonts01/font24DispTitle.fnt";
	public static final String LOAD_SPRITE_MENU01big =
			"graphics/size01big/menu/sprite_menu.atlas";
	public static final String LOAD_SPRITE_ALOADER01big =
			"graphics/size01big/loader/atlasloader.atlas";
	public static final String LOAD_SPLASH01big =
			"graphics/size01big/splash.png";
	public static final String DATA_GRAPHICS_SPRITE_MENU_JSON01big =
			"graphics/size01big/menu/sprite_menu.json";
	public static final String DATA_GRAPHICS_HELP_MENU_ATLAS01big =
			"graphics/size01big/help/menu_help.atlas";
	public static final String REPLAY_JSON01big =
			"graphics/size01big/replay_explosion/hud_replay.json";
	public static final String EXPLOSION_HUD_REPLAY01big =
			"graphics/size01big/replay_explosion/hud_replay.atlas";
	public static final String EXPLOSION_ATLAS01big =
			"graphics/size01big/replay_explosion/spriteexplosion.atlas";
	//----------------------------------------------------------------------------------------------
	public static final String MAIN_ATLASABACK1_ATLAS02medium =
			"graphics/size01big/mainatlasABack1 result/mainatlasABack1.atlas";
	public static final String MAIN_ATLASABACK2_ATLAS02medium =
			"graphics/size01big/mainatlasABack2 result/mainatlasABack2.atlas";
	public static final String MAIN_ATLASABACK3_ATLAS02medium =
			"graphics/size01big/mainatlasABack3 result/mainatlasABack3.atlas";
	public static final String MAIN_ATLASABACK4_ATLAS02medium =
			"graphics/size01big/mainatlasABack4 result/mainatlasABack4.atlas";
	public static final String MAIN_ATLASBBACK1_ATLAS02medium =
			"graphics/size01big/mainatlasBBack1 result/mainatlasBBack1.atlas";
	public static final String MAIN_ATLASBBACK2_ATLAS02medium =
			"graphics/size01big/mainatlasBBack2 result/mainatlasBBack2.atlas";
	public static final String MAIN_ATLASBBACK3_ATLAS02medium =
			"graphics/size01big/mainatlasBBack3 result/mainatlasBBack3.atlas";
	public static final String MAIN_ATLASBBACK4_ATLAS02medium =
			"graphics/size01big/mainatlasBBack4 result/mainatlasBBack4.atlas";
	public static final String MAIN_ATLASCBACK1_ATLAS02medium =
			"graphics/size01big/mainatlasCBack1 result/mainatlasCBack1.atlas";
	public static final String MAIN_ATLASCBACK2_ATLAS02medium =
			"graphics/size01big/mainatlasCBack2 result/mainatlasCBack2.atlas";
	public static final String MAIN_ATLASCBACK3_ATLAS02medium =
			"graphics/size01big/mainatlasCBack3 result/mainatlasCBack3.atlas";
	public static final String MAIN_ATLASCBACK4_ATLAS02medium =
			"graphics/size01big/mainatlasCBack4 result/mainatlasCBack4.atlas";
	public static final String MAIN_ATLASABACK1_SKIN02medium =
			"graphics/size01big/mainatlasABack1 result/mainatlasABack1.json";
	public static final String MAIN_ATLASABACK2_SKIN02medium =
			"graphics/size01big/mainatlasABack2 result/mainatlasABack2.json";
	public static final String MAIN_ATLASABACK3_SKIN02medium =
			"graphics/size01big/mainatlasABack3 result/mainatlasABack3.json";
	public static final String MAIN_ATLASABACK4_SKIN02medium =
			"graphics/size01big/mainatlasABack4 result/mainatlasABack4.json";
	public static final String MAIN_ATLASBBACK1_SKIN02medium =
			"graphics/size01big/mainatlasBBack1 result/mainatlasBBack1.json";
	public static final String MAIN_ATLASBBACK2_SKIN02medium =
			"graphics/size01big/mainatlasBBack2 result/mainatlasBBack2.json";
	public static final String MAIN_ATLASBBACK3_SKIN02medium =
			"graphics/size01big/mainatlasBBack3 result/mainatlasBBack3.json";
	public static final String MAIN_ATLASBBACK4_SKIN02medium =
			"graphics/size01big/mainatlasBBack4 result/mainatlasBBack4.json";
	public static final String MAIN_ATLASCBACK1_SKIN02medium =
			"graphics/size01big/mainatlasCBack1 result/mainatlasCBack1.json";
	public static final String MAIN_ATLASCBACK2_SKIN02medium =
			"graphics/size01big/mainatlasCBack2 result/mainatlasCBack2.json";
	public static final String MAIN_ATLASCBACK3_SKIN02medium =
			"graphics/size01big/mainatlasCBack3 result/mainatlasCBack3.json";
	public static final String MAIN_ATLASCBACK4_SKIN02medium =
			"graphics/size01big/mainatlasCBack4 result/mainatlasCBack4.json";
	public static final String MAIN_ATLAS_SEC_ATLAS02medium =
			"graphics/size02medium/mainatlasSec result/mainatlasSec.atlas";
	public static final String hud_angler_font02medium =
			"fonts/fonts01/font28Disp.fnt";
	public static final String hud_angler_fontp02medium =
			"fonts/fonts01/font28Dispp.fnt";
	public static final String hud_angler_font_title02medium =
			"fonts/fonts01/font24DispTitle.fnt";
	public static final String LOAD_SPRITE_MENU02medium =
			"graphics/size02medium/menu/sprite_menu.atlas";
	public static final String LOAD_SPRITE_ALOADER02medium =
			"graphics/size02medium/loader/atlasloader.atlas";
	public static final String LOAD_SPLASH02medium =
			"graphics/size01big/splash.png";
	public static final String DATA_GRAPHICS_SPRITE_MENU_JSON02medium =
			"graphics/size02medium/menu/sprite_menu.json";
	public static final String DATA_GRAPHICS_HELP_MENU_ATLAS02medium =
			"graphics/size02medium/help/menu_help.atlas";
	public static final String REPLAY_JSON02medium =
			"graphics/size02medium/replay_explosion/hud_replay.json";
	public static final String EXPLOSION_HUD_REPLAY02medium =
			"graphics/size02medium/replay_explosion/hud_replay.atlas";
	public static final String EXPLOSION_ATLAS02medium =
			"graphics/size02medium/replay_explosion/spriteexplosion.atlas";
	//----------------------------------------------------------------------------------------------
	public static final String MAIN_ATLASABACK1_ATLAS03small =
			"graphics/size01big/mainatlasABack1 result/mainatlasABack1.atlas";
	public static final String MAIN_ATLASABACK2_ATLAS03small =
			"graphics/size01big/mainatlasABack2 result/mainatlasABack2.atlas";
	public static final String MAIN_ATLASABACK3_ATLAS03small =
			"graphics/size01big/mainatlasABack3 result/mainatlasABack3.atlas";
	public static final String MAIN_ATLASABACK4_ATLAS03small =
			"graphics/size01big/mainatlasABack4 result/mainatlasABack4.atlas";
	public static final String MAIN_ATLASBBACK1_ATLAS03small =
			"graphics/size01big/mainatlasBBack1 result/mainatlasBBack1.atlas";
	public static final String MAIN_ATLASBBACK2_ATLAS03small =
			"graphics/size01big/mainatlasBBack2 result/mainatlasBBack2.atlas";
	public static final String MAIN_ATLASBBACK3_ATLAS03small =
			"graphics/size01big/mainatlasBBack3 result/mainatlasBBack3.atlas";
	public static final String MAIN_ATLASBBACK4_ATLAS03small =
			"graphics/size01big/mainatlasBBack4 result/mainatlasBBack4.atlas";
	public static final String MAIN_ATLASCBACK1_ATLAS03small =
			"graphics/size01big/mainatlasCBack1 result/mainatlasCBack1.atlas";
	public static final String MAIN_ATLASCBACK2_ATLAS03small =
			"graphics/size01big/mainatlasCBack2 result/mainatlasCBack2.atlas";
	public static final String MAIN_ATLASCBACK3_ATLAS03small =
			"graphics/size01big/mainatlasCBack3 result/mainatlasCBack3.atlas";
	public static final String MAIN_ATLASCBACK4_ATLAS03small =
			"graphics/size01big/mainatlasCBack4 result/mainatlasCBack4.atlas";
	public static final String MAIN_ATLASABACK1_SKIN03small =
			"graphics/size01big/mainatlasABack1 result/mainatlasABack1.json";
	public static final String MAIN_ATLASABACK2_SKIN03small =
			"graphics/size01big/mainatlasABack2 result/mainatlasABack2.json";
	public static final String MAIN_ATLASABACK3_SKIN03small =
			"graphics/size01big/mainatlasABack3 result/mainatlasABack3.json";
	public static final String MAIN_ATLASABACK4_SKIN03small =
			"graphics/size01big/mainatlasABack4 result/mainatlasABack4.json";
	public static final String MAIN_ATLASBBACK1_SKIN03small =
			"graphics/size01big/mainatlasBBack1 result/mainatlasBBack1.json";
	public static final String MAIN_ATLASBBACK2_SKIN03small =
			"graphics/size01big/mainatlasBBack2 result/mainatlasBBack2.json";
	public static final String MAIN_ATLASBBACK3_SKIN03small =
			"graphics/size01big/mainatlasBBack3 result/mainatlasBBack3.json";
	public static final String MAIN_ATLASBBACK4_SKIN03small =
			"graphics/size01big/mainatlasBBack4 result/mainatlasBBack4.json";
	public static final String MAIN_ATLASCBACK1_SKIN03small =
			"graphics/size01big/mainatlasCBack1 result/mainatlasCBack1.json";
	public static final String MAIN_ATLASCBACK2_SKIN03small =
			"graphics/size01big/mainatlasCBack2 result/mainatlasCBack2.json";
	public static final String MAIN_ATLASCBACK3_SKIN03small =
			"graphics/size01big/mainatlasCBack3 result/mainatlasCBack3.json";
	public static final String MAIN_ATLASCBACK4_SKIN03small =
			"graphics/size01big/mainatlasCBack4 result/mainatlasCBack4.json";
	public static final String MAIN_ATLAS_SEC_ATLAS03small =
			"graphics/size02medium/mainatlasSec result/mainatlasSec.atlas";
	public static final String hud_angler_font03small =
			"fonts/fonts01/font28Disp.fnt";
	public static final String hud_angler_fontp03small =
			"fonts/fonts01/font28Dispp.fnt";
	public static final String hud_angler_font_title03small =
			"fonts/fonts01/font24DispTitle.fnt";
	public static final String LOAD_SPRITE_MENU03small =
			"graphics/size03small/menu/sprite_menu.atlas";
	public static final String LOAD_SPRITE_ALOADER03small =
			"graphics/size03small/loader/atlasloader.atlas";
	public static final String LOAD_SPLASH03small =
			"graphics/size03small/splash.png";
	public static final String DATA_GRAPHICS_SPRITE_MENU_JSON03small =
			"graphics/size03small/menu/sprite_menu.json";
	public static final String DATA_GRAPHICS_HELP_MENU_ATLAS03small =
			"graphics/size03small/help/menu_help.atlas";
	public static final String REPLAY_JSON03small =
			"graphics/size03small/replay_explosion/hud_replay.json";
	public static final String EXPLOSION_HUD_REPLAY03small =
			"graphics/size03small/replay_explosion/hud_replay.atlas";
	public static final String EXPLOSION_ATLAS03small =
			"graphics/size02medium/replay_explosion/spriteexplosion.atlas";
	//##############################################################################################
	//##############################################################################################
	//##############################################################################################
	public static final String LOGIN_SUCCESSFUL = "Login Successful";
	public static final String LOGIN_ERROR = "LOGIN ERROR!";
	public static final String LOGIN_CANCELLED = "LOGIN CANCELLED!";
	public static final String NO_INTERNET_ERROR = "TURN ON INTERNET!";
	//##############################################################################################
	public static final String PREMIUM_ERROR = "GET PREMIUM!";
	public static final String PREMIUM_ERROR_FIRST = "GET PREMIUM FIRST!";
	//##############################################################################################
	public static final String NOT_LOGGED_IN_ERROR = "LOG IN FIRST!";
	public static final String LOGOUT_SUCCESSFUL = "Logout Successful";
	public static final String LOGOUT_ERROR = "LOGOUT ERROR";
	public static final String UPDATED_PREMIUM = "Updated To Premium!";
	public static final String RESET_PREMIUM = "Reset To Premium!";
	public static final String UPDATED_PREMIUM_FAIL = "Failed to update!";
	public static final String UPDATED_COINS_FAIL = "Failed to buy!";
	public static final String UPDATED_UNLOCKED_LEVELS = "Unlocked all levels!";
	public static final String UPDATED_UNLOCKED_LEVELS_FAIL = "Failed to unlock!";
	public static final String PURCHASED_AMMO9000 = "Purchased 9000 AMMO!";
	public static final String PURCHASED_LIFE9000 = "Purchased 9000 LIFE!";
	public static final String RESET_PREMIUM_UNLOCKED = "Premium + Unlocked!";
	public static final String NOT_AVAILABLE = "N/A";
	public static final String IAB_FAILED_PURCHASE = "Failed Purchase";
	public static final String IAB_FAILED_ACCOUNT_IAB = "IAB helper is not set up";
	public static final String IAB_FAILED_ACCOUNT_SETUP_PROMPT = "Login first!";
	public static final String IAB_UNKNOWN_ERROR = "Unknown Error";
	public static final int IAB_FAILED_ACCOUNT_SETUP_NO = 1001;
	public static final int IAB_UNKNOWN_ERROR_NO = 1002;
	public static final int IAB_FAILED_PURCHASE_NO = 1003;
	//ATLAS REGION NAMES
	public static final String VALUE_EXIT_DIALOG_BACK = "hud-level55";
	public static final String VALUE_TEXT = "connie";
	public static final String VALUE_OK = "ok_button";
	public static final String VALUE_NO = "no_button";
	public static final int LEVEL_MAX = 64;
	public static final int RATE_LATER = 8001;
	public static final int RATE_NEVER = 8002;
	public static final boolean RATE_FAIL = false;
	public static final boolean RATE_SUCCESS = true;
	public static final float DELAY_PROC_APPEAR = 4f;
	public static final float HUD_AN_DEL2F = 2f;
	public static final float HUD_AN_DEL4F = 4f;
	public static final float HUD_ALPHA0 = 0f;
	public static final float HUD_ALPHA085 = 0.875f;
	public static final int screen_sizew = Gdx.graphics.getWidth();
	public static final int screen_sizeh = Gdx.graphics.getHeight();
	public static final float RESIZER =
			((float)VIRTUAL_SCREEN_WIDTH/(float)VIRTUAL_SCREEN_HEIGHT) /
					((float)screen_sizew/(float)screen_sizeh);
	public static final boolean BLOCK_VIEW_REWARD = true; //set it to false to view 'vertism'
}