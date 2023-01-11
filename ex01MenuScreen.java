//WMS3 2015

package com.cryotrax.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import java.util.Timer;
import java.util.TimerTask;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class ex01MenuScreen extends GdxSceneBase implements Screen,
															InputProcessor,
															PurchaseCallback,
															PSettingsCallback,
															ProcessGetScoreRank,
															PSignInCallback,
															PRateCallback {
    final CryotraxGame cryo_game;
    public static final float width =
			ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f;
    public static final float height =
			ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f * 278f/430f;
	public static final float TRANSITION_IN_TIME = 1.15f;
    public Viewport viewport;
	public ex01MenuScreenMenu menus;
	public ex01JavaInterface VALUE_screen;
	public ex01MenuScreenExitDialog exit_dialog_screen;
	public ex01MenuScreenSettingsDialog settings_dialog_screen;	
	public ex01MenuScreenGetSpacecraftDialog get_spacecraft_dialog_screen;
	public ex01MenuScreenGetCoinsDialog get_coins_dialog_screen;
	public ex01MenuScreenGetLifeslots get_lifeslots_screen;
	public ex01MenuScreenGetBullets get_bullets_screen;
	public ex01MenuScreenScoresDialog scores_dialog_screen;	
	public ex01MenuScreenHelp help_screen;
	public ex01MenuScreenListerPos scores_lister;
	public InputMultiplexer multiplexer;
	public enum State { TransitionIn, Picture }
	public float time;
	public float resolution[];
	public float radius;
	public boolean finished_transition = false;
	public boolean grayscale_shader_active_exit = false;
	public boolean grayscale_shader_active_VALUE = false;
	public boolean grayscale_shader_active_settings = false;
	public boolean grayscale_shader_active_get_spacecraft = false;
	public boolean grayscale_shader_active_get_coins = false;
	public boolean grayscale_shader_active_get_lifeslots = false;
	public boolean grayscale_shader_active_get_bullets = false;
	public boolean grayscale_shader_active_scores = false;
	public boolean grayscale_shader_active_scores_lister = false;
	public boolean grayscale_shader_active_help = false;
	public Skin skin;
	public String text;
	public String text1;
	public ex01JSONSettingsLoader settings;
	public OrthographicCamera camera;
	public ShaderProgram shader;
	public ShaderProgram shader_grayscale;
	public State state;
	public InputProcessor backProcessor;
	public Sound back_button;
	public Sound done_button;
	public Sound level_left_right_button;
	public Sound level_opener_selector_button;
	public Sound menu_selector_button;
	public Sound exit_code;
	public Sound enter_play_game;
	public Sound cash_register_coins;
	public Sound ammo_gun_load;
	public Sound lifeslots_heartpump;
	public Sound error_sound_no;
	public Sound green_already_have;
	public Sound basic_button;
	public Sound success_button;
	public Sound login_button;
	public Sound logout_button;
	public Music menu_music;
	public boolean checked_for_achievements_start = false;

	public short render_no = 0;
	public boolean bNextIsSignOut
			= true;  // used in TransitError to see if texture is LOGOUT
	public boolean already_loggedin_at_startup
			= false; // used to start login procedure at startup if we need to
	public boolean already_loggedin_at_startup_for_transit
			= false; // are we at game startup? this is first TransitError
	public boolean checked_inapp_data
			= false; // used in CheckInAppData but not any more - for the mom.
	public boolean this_came_from_reset_purchase
			= false; // set in CheckInAppData so SetPurchaseResultPremiumSuccess is run correct
	public boolean also_unlock_levels
			= false; // set also in CheckInAppData so we also reset levels if we need to
	public boolean is_still_rolling_reset_premium_unlocked
			= true;  // set UnlockAllLevelsFailed & InitUnlockAllLevels for SAVE and UNLOCK fade
	public boolean is_still_rolling_reset_premium_unlocked_duplex
			= false; // used for displaying both UNLOCK and SAVE buttons set false when not
	public boolean bCameFromHUD
			= false; // so we know that login or purchase command came from HUD or not
	public boolean StartTimePassed
			= false;
	public boolean rank_command_came_from_selector
			= false;
	public boolean isSomethingWrong
			= false; // this is set when we need to reset premium + unlocked / premium

	public ex01MenuScreen(TextureAtlas atlas, final CryotraxGame game) {
		settings = new ex01JSONSettingsLoader();
		cryo_game = game;
		cryo_game.menu_screen = this;
		if(cryo_game.screen_type==1){
			cryo_game.assets.load(ex01Types.
					DATA_GRAPHICS_HELP_MENU_ATLAS01big, TextureAtlas.class);
			cryo_game.assets.load(ex01Types.
					DATA_GRAPHICS_SPRITE_MENU_JSON01big, Skin.class);
		} else if(cryo_game.screen_type==2){
			cryo_game.assets.load(ex01Types.
					DATA_GRAPHICS_HELP_MENU_ATLAS02medium, TextureAtlas.class);
			cryo_game.assets.load(ex01Types.
					DATA_GRAPHICS_SPRITE_MENU_JSON02medium, Skin.class);
		} else {
			cryo_game.assets.load(ex01Types.
					DATA_GRAPHICS_HELP_MENU_ATLAS03small, TextureAtlas.class);
			cryo_game.assets.load(ex01Types.
					DATA_GRAPHICS_SPRITE_MENU_JSON03small, Skin.class);
		}
		cryo_game.assets.load(ex01Types.MUSIC_MENU1, Music.class);
		cryo_game.assets.load(ex01Types.SOUND_MN_BACK_B01big, Sound.class);
		cryo_game.assets.load(ex01Types.SOUND_MN_DONE_B01big, Sound.class);
		cryo_game.assets.load(ex01Types.SOUND_MN_LEVEL_LR_B01big, Sound.class);
		cryo_game.assets.load(ex01Types.SOUND_MN_LEVELO_B01big, Sound.class);
		cryo_game.assets.load(ex01Types.SOUND_MN_SELECTOR_B01big, Sound.class);
		cryo_game.assets.load(ex01Types.SOUND_MN_EXIT_B01big, Sound.class);
		cryo_game.assets.load(ex01Types.SOUND_MN_ENTERP_B01big, Sound.class);
		cryo_game.assets.load(ex01Types.SOUND_MN_CASHR_B01big, Sound.class);
		cryo_game.assets.load(ex01Types.SOUND_MN_AMMO_B01big, Sound.class);
		cryo_game.assets.load(ex01Types.SOUND_MN_LIFE_B01big, Sound.class);
		cryo_game.assets.load(ex01Types.SOUND_MN_ERROR_NO_B01big, Sound.class);
		cryo_game.assets.load(ex01Types.SOUND_MN_GREEN_HAVE_B01big, Sound.class);
		cryo_game.assets.load(ex01Types.SOUND_MN_BASIC_B01big, Sound.class);
		cryo_game.assets.load(ex01Types.SOUND_MN_SUCCESS_B01big, Sound.class);
		cryo_game.assets.load(ex01Types.SOUND_MN_LOGIN_B01big, Sound.class);
		cryo_game.assets.load(ex01Types.SOUND_MN_LOGOUT_B01big, Sound.class);
		cryo_game.assets.finishLoading();

		if(cryo_game.screen_type==1){
			skin = cryo_game.assets.get(ex01Types.DATA_GRAPHICS_SPRITE_MENU_JSON01big);
		} else if(cryo_game.screen_type==2){
			skin = cryo_game.assets.get(ex01Types.DATA_GRAPHICS_SPRITE_MENU_JSON02medium);
		} else {
			skin = cryo_game.assets.get(ex01Types.DATA_GRAPHICS_SPRITE_MENU_JSON03small);
		}

		camera = new OrthographicCamera();
		viewport = new StretchViewport(ex01Types.VIRTUAL_SCREEN_WIDTH,
				ex01Types.VIRTUAL_SCREEN_HEIGHT,
				camera);

		exit_dialog_screen = new
				ex01MenuScreenExitDialog(skin,
				this, viewport);
		VALUE_screen = new
				ex01JavaInterface(cryo_game, skin,
				this, viewport);
		settings_dialog_screen = new
				ex01MenuScreenSettingsDialog(skin,
				this, viewport);
		get_spacecraft_dialog_screen = new
				ex01MenuScreenGetSpacecraftDialog(settings, atlas, skin,
				this, viewport);
		get_coins_dialog_screen = new
				ex01MenuScreenGetCoinsDialog(settings, atlas, skin,
				this, viewport);
		get_lifeslots_screen = new
				ex01MenuScreenGetLifeslots(settings, atlas, skin,
				this, viewport);
		get_bullets_screen = new
				ex01MenuScreenGetBullets(settings, atlas, skin,
				this, viewport);
		scores_dialog_screen = new
				ex01MenuScreenScoresDialog(skin,
				this, viewport, camera);


		if(cryo_game.screen_type==1){
			help_screen = new
					ex01MenuScreenHelp(skin,
					this, viewport, cryo_game.assets
					.<TextureAtlas>get(ex01Types.DATA_GRAPHICS_HELP_MENU_ATLAS01big));
		} else if(cryo_game.screen_type==2){
			help_screen = new
					ex01MenuScreenHelp(skin,
					this, viewport, cryo_game.assets
					.<TextureAtlas>get(ex01Types.DATA_GRAPHICS_HELP_MENU_ATLAS02medium));
		} else {
			help_screen = new
					ex01MenuScreenHelp(skin,
					this, viewport, cryo_game.assets
					.<TextureAtlas>get(ex01Types.DATA_GRAPHICS_HELP_MENU_ATLAS03small));
		}

		scores_lister = new
				ex01MenuScreenListerPos(skin,
				this, viewport);
		menus = new
				ex01MenuScreenMenu(atlas, skin,
				this, viewport);

		menu_music = cryo_game.assets
				.get(ex01Types.MUSIC_MENU1);
		back_button = cryo_game.assets
				.get(ex01Types.SOUND_MN_BACK_B01big);
		done_button = cryo_game.assets
				.get(ex01Types.SOUND_MN_DONE_B01big);
		level_left_right_button = cryo_game.assets
				.get(ex01Types.SOUND_MN_LEVEL_LR_B01big);
		level_opener_selector_button = cryo_game.assets
				.get(ex01Types.SOUND_MN_LEVELO_B01big);
		menu_selector_button = cryo_game.assets
				.get(ex01Types.SOUND_MN_SELECTOR_B01big);
		exit_code = cryo_game.assets
				.get(ex01Types.SOUND_MN_EXIT_B01big);
		enter_play_game = cryo_game.assets
				.get(ex01Types.SOUND_MN_ENTERP_B01big);
		cash_register_coins = cryo_game.assets
				.get(ex01Types.SOUND_MN_CASHR_B01big);
		ammo_gun_load = cryo_game.assets
				.get(ex01Types.SOUND_MN_AMMO_B01big);
		lifeslots_heartpump = cryo_game.assets
				.get(ex01Types.SOUND_MN_LIFE_B01big);
		error_sound_no = cryo_game.assets
				.get(ex01Types.SOUND_MN_ERROR_NO_B01big);
		green_already_have = cryo_game.assets
				.get(ex01Types.SOUND_MN_GREEN_HAVE_B01big);
		basic_button = cryo_game.assets
				.get(ex01Types.SOUND_MN_BASIC_B01big);
		success_button = cryo_game.assets
				.get(ex01Types.SOUND_MN_SUCCESS_B01big);
		login_button = cryo_game.assets
				.get(ex01Types.SOUND_MN_LOGIN_B01big);
		logout_button = cryo_game.assets
				.get(ex01Types.SOUND_MN_LOGOUT_B01big);
		VALUE_screen.done_button = cryo_game.assets
				.get(ex01Types.SOUND_MN_DONE_B01big);
		InitShaderTransition();
		InitShaderTimers();
		menus.menu_init.RenderLevelDialogInit();
		cryo_game.game_state = GameState.MENU_SCREEN;
	}

	public void setAchievementResult(){

	}

	public void CheckForAchievements(){
		if(!checked_for_achievements_start) {
			checked_for_achievements_start = true;
			CheckForAchievementsBase();
		}
	}

	public void CheckForAchievementsBase(){
		/*
		ScanSettingsForAchievements();
		boolean write_json = false;

		if(settings.settings.is_achievement_16) {
			Gdx.app.log("GGG", "is_achievement_junior_done");
		} else {
			if(is_start16_achieved){
				if(cryo_game.exit_error.isWiFiConnected() && menus.bIsLoggedIn){
					cryo_game.google_facebook_services.UnlockStar16();
					settings.settings.is_achievement_16 = true;
					write_json = true;
				}
			}
		}

		if(settings.settings.is_achievement_32) {
			Gdx.app.log("GGG", "is_achievement_hard_worker_done");
		} else {
			if(is_start32_achieved){
				if(cryo_game.exit_error.isWiFiConnected() && menus.bIsLoggedIn){
					cryo_game.google_facebook_services.UnlockStar32();
					settings.settings.is_achievement_32 = true;
					write_json = true;
				}
			}
		}

		if(settings.settings.is_achievement_48) {
			Gdx.app.log("GGG", "is_achievement_industrious_done");
		} else {
			if (is_start48_achieved) {
				if (cryo_game.exit_error.isWiFiConnected() && menus.bIsLoggedIn) {
					cryo_game.google_facebook_services.UnlockStar48();
					settings.settings.is_achievement_48 = true;
					write_json = true;
				}
			}
		}

		if(settings.settings.is_achievement_64) {
			Gdx.app.log("GGG", "is_achievement_military_might_done");
		} else {
			if(is_start64_achieved){
				if(cryo_game.exit_error.isWiFiConnected() && menus.bIsLoggedIn){
					cryo_game.google_facebook_services.UnlockStar64();
					settings.settings.is_achievement_64 = true;
					write_json = true;
				}
			}
		}

		if(settings.settings.is_achievement_1) {
			Gdx.app.log("GGG", "is_achievement_global_dominator_done");
		} else {
			if(is_star1_achieved){
				if(cryo_game.exit_error.isWiFiConnected() && menus.bIsLoggedIn){
					cryo_game.google_facebook_services.UnlockStar1();
					settings.settings.is_achievement_1 = true;
					write_json = true;
				}
			}
		}

		if(settings.settings.is_achievement_2) {
			Gdx.app.log("GGG", "is_achievement_intergalactic_emperor_done");
		} else {
			if(is_star2_achieved){
				if(cryo_game.exit_error.isWiFiConnected() && menus.bIsLoggedIn){
					cryo_game.google_facebook_services.UnlockStar2();
					settings.settings.is_achievement_2 = true;
					write_json = true;
				}
			}
		}

		if(settings.settings.is_achievement_3){
			Gdx.app.log("GGG", "is_achievement_architect_the_universe_done");
		} else {
			if(is_star3_achieved){
				if(cryo_game.exit_error.isWiFiConnected() && menus.bIsLoggedIn){
					cryo_game.google_facebook_services.UnlockStar3();
					settings.settings.is_achievement_3 = true;
					menus.base_menu.settings.WriteJson();
				}
			}
		}

		if(write_json){
			menus.base_menu.settings.WriteJson();
		}
		*/
	}

	public boolean is_start16_achieved = true;
	public boolean is_start32_achieved = true;
	public boolean is_start48_achieved = true;
	public boolean is_start64_achieved = true;
	public boolean is_star1_achieved = true;
	public boolean is_star2_achieved = true;
	public boolean is_star3_achieved = true;

	public void ScanSettingsForAchievements(){
		int count = 0;

		for(int i=0; i<16; i++){
			if(!settings.settings.levels.get(i).is_unlocked){
				is_start16_achieved = false;
			}
		}
		for(int i=16; i<32; i++){
			if(!settings.settings.levels.get(i).is_unlocked){
				is_start32_achieved = false;
			}
		}
		for(int i=32; i<48; i++){
			if(!settings.settings.levels.get(i).is_unlocked){
				is_start48_achieved = false;
			}
		}
		for(int i=48; i<64; i++){
			if(!settings.settings.levels.get(i).is_unlocked){
				is_start64_achieved = false;
			}
		}

		for(ex01JSONSettingsLoader.level_data data :  settings.settings.levels){
			if(!data.is_mission_1_achieved){
				is_star1_achieved = false;
				is_star2_achieved = false;
				is_star3_achieved = false;
			}
			if(!data.is_mission_2_achieved){
				is_star2_achieved = false;
				is_star3_achieved = false;
			}
			if(!data.is_mission_3_achieved){
				is_star3_achieved = false;
			}
		}
	}


	@Override
	public void setSignInResult(boolean result, boolean canceled) {
		if(!menus.bJustWantedToLogout){
			if(result==ex01Types.SIGNIN_SUCCESS){
				SetSignInResultSuccess();
			} else {
				SetSignInResultFail(canceled);
			}
		} else {
			if(result==ex01Types.SIGNOUT_SUCCESS){
				SetSignOutResultSuccess();
			} else {
				SetSignOutResultFail();
			}
		}
	}

	@Override
	public int setPurchaseResult(int result, int product, int result_code){
		if(result == IActionResolver.SUCCESS_PURCHASE){
			switch(product){
				case IActionResolver.PRODUCT_PREMIUM:
				{
					SetPurchaseResultPremiumSuccess();
				}break;
				case IActionResolver.PRODUCT_UNLOCK_LEVELS:
				{
					SetPurchaseResultUnlockSuccess();
				}break;
				case IActionResolver.PRODUCT_AMMO9000:
				{
					cryo_game.game_screen.hud_display.PurchaseAmmo9000AllLevelsHUD();
				}break;
				case IActionResolver.PRODUCT_LIFE9000:
				{
					cryo_game.game_screen.hud_display.PurchaseLife9000AllLevelsHUD();
				}break;
				case IActionResolver.PRODUCT_COINS100:
				{
					ProcessBuy100Coins();
				}break;
				case IActionResolver.PRODUCT_COINS250:
				{
					ProcessBuy250Coins();
				}break;
				case IActionResolver.PRODUCT_COINS500:
				{
					ProcessBuy500Coins();
				}break;
				case IActionResolver.PRODUCT_COINS750:
				{
					ProcessBuy750Coins();
				}break;
				case IActionResolver.PRODUCT_COINS1000:
				{
					ProcessBuy1000Coins();
				}break;
				case IActionResolver.PRODUCT_COINS1500:
				{
					ProcessBuy1500Coins();
				}break;
			}
		} else if (result == IActionResolver.FAILED_PURCHASE){
			switch(product){
				case IActionResolver.PRODUCT_PREMIUM:
				{
					SetPurchaseResultPremiumFail(result_code);
				}break;
				case IActionResolver.PRODUCT_UNLOCK_LEVELS:
				{
					SetPurchaseResultUnlockFail(result_code);
				}break;
				case IActionResolver.PRODUCT_AMMO9000:{
					SetPurchaseResultAmmo9000Fail(result_code);
				}
				case IActionResolver.PRODUCT_LIFE9000:{
					SetPurchaseResultLife9000Fail(result_code);
				}
				case IActionResolver.PRODUCT_COINS100:
				{
					SetPurchaseResultCoins100Fail(result_code);
				}break;
				case IActionResolver.PRODUCT_COINS250:
				{
					SetPurchaseResultCoins250Fail(result_code);
				}break;
				case IActionResolver.PRODUCT_COINS500:
				{
					SetPurchaseResultCoins500Fail(result_code);
				}break;
				case IActionResolver.PRODUCT_COINS750:
				{
					SetPurchaseResultCoins750Fail(result_code);
				}break;
				case IActionResolver.PRODUCT_COINS1000:
				{
					SetPurchaseResultCoins1000Fail(result_code);
				}break;
				case IActionResolver.PRODUCT_COINS1500:
				{
					SetPurchaseResultCoins1500Fail(result_code);
				}break;
			}
		}
		return 0;
	}

	@Override
	public void setSettingsResult(ex01JSONSettingsLoader.json_settings settings) {
		SetSettingsResetProcessing(settings);
		SetSettingsResetMenuUI(settings);
		SetSettingsResetHudUI(settings);
	}

	@Override
	public void setRateGameResult(boolean result, int result_code) {
		if(result == ex01Types.RATE_SUCCESS){
			settings.settings.rate_never = true;
		} else {
			if(result_code == ex01Types.RATE_LATER){
			} else if (result_code == ex01Types.RATE_NEVER){
				settings.settings.rate_never = true;
			}
		}
		settings.WriteJson();
	}

	public boolean can_exit_game_or_do_other_write = true; // no exit until we are finished

	public void SetSettingsResetProcessing(ex01JSONSettingsLoader.json_settings settings){
		can_exit_game_or_do_other_write = false;
		//check to see if we updated to premium and bought unlock
		boolean has_just_bought_premium_before_game_load = cryo_game.menu_screen
				.settings.settings.is_this_premium_updated;
		boolean has_just_bought_unlock_levels_before_game_load = cryo_game.menu_screen
				.settings.settings.is_this_unlocked_all_levels;
		boolean selected_space_10 =
				cryo_game.menu_screen.settings.settings.selected_cryozl10;
		boolean selected_space_12 =
				cryo_game.menu_screen.settings.settings.selected_cryozl12;
		boolean selected_space_14 =
				cryo_game.menu_screen.settings.settings.selected_cryozl14;
		int index_space_sele =
				cryo_game.menu_screen.settings.settings.index_spaceship_selected;

		//did we also buy coins?
		int no_coins_before_game_load = cryo_game.menu_screen
				.settings.settings.number_coins;
		int no_ammo_before_game_load = cryo_game.menu_screen
				.settings.settings.number_ammo;
		int no_life_before_game_load = cryo_game.menu_screen
				.settings.settings.number_lifeslots;

		//did we bought any spacecraft?
		if(cryo_game.menu_screen.settings.settings.bought_cryozl10 == true){
			settings.bought_cryozl10 = true;
			cryo_game.menu_screen
					.get_spacecraft_dialog_screen.spaceship_buys.get(0).bought = true;
		}
		if(cryo_game.menu_screen.settings.settings.bought_cryozl12 == true){
			settings.bought_cryozl12 = true;
			cryo_game.menu_screen
					.get_spacecraft_dialog_screen.spaceship_buys.get(1).bought = true;
		}
		if(cryo_game.menu_screen.settings.settings.bought_cryozl14 == true){
			settings.bought_cryozl14 = true;
			cryo_game.menu_screen
					.get_spacecraft_dialog_screen.spaceship_buys.get(2).bought = true;
		}

		//which spacecraft was selected and which is now
		if(cryo_game.menu_screen.settings.settings.selected_cryozl12){
			if(!settings.selected_cryozl14){
				CollateSpacecraftFromNewBuys(settings);
			}
		}
		else if(cryo_game.menu_screen.settings.settings.selected_cryozl14){
			CollateSpacecraftFromNewBuys(settings);
		}

		//reset
		cryo_game.menu_screen.settings.settings = settings;
		ResetSpaceshipLifeAmmoCoins();

		//now reload with what we purchased upwards
		if(has_just_bought_premium_before_game_load)
			cryo_game.menu_screen.settings.settings.is_this_premium_updated = true;
		if(has_just_bought_unlock_levels_before_game_load) {
			cryo_game.menu_screen.settings.settings.is_this_unlocked_all_levels = true;
			cryo_game.menu_screen.settings.settings.no_level_unlocked = 64;
		}
		if(no_coins_before_game_load!=0)
			cryo_game.menu_screen.settings.settings.number_coins+=no_coins_before_game_load;
		if(no_ammo_before_game_load!=0)
			cryo_game.menu_screen.settings.settings.number_ammo+=no_ammo_before_game_load;
		if(no_life_before_game_load!=0)
			cryo_game.menu_screen.settings.settings.number_lifeslots+=no_life_before_game_load;

		//redraw
		cryo_game.levels_screen.level_selector
				.ResetLevelsDrawkingsOnChangeAlsoLevels(cryo_game.menu_screen.settings);

		//this redraws stars in page levels
		cryo_game.levels_screen.level_selector
				.ResetLevelsDrawkingsOnChange();

		//write file then send to cloud
		cryo_game.menu_screen.settings.settings.is_this_in_need_for_load_saved = false;
		//we sleep so we don't get error error001 - maybe parts of JSON are still on pipe
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cryo_game.menu_screen.settings.WriteJson();
		cryo_game.SAVE_GAME_TO_CLOUD();
//		can_exit_game_or_do_other_write = true; now in cryo SAVE_TO_CLOUD
	}

	public void CollateSpacecraftFromNewBuys(ex01JSONSettingsLoader.json_settings settings){
		settings.selected_cryozl10 =
				cryo_game.menu_screen.settings.settings.selected_cryozl10;
		settings.selected_cryozl12 =
				cryo_game.menu_screen.settings.settings.selected_cryozl12;
		settings.selected_cryozl14 =
				cryo_game.menu_screen.settings.settings.selected_cryozl14;

		if(settings.selected_cryozl10) {
			settings.index_spaceship_selected = 0;
			cryo_game.menu_screen.get_spacecraft_dialog_screen.selected_spaceship =
					ex01MenuScreenGetSpacecraftDialog.SelectedSpaceship.one;
		}
		else if(settings.selected_cryozl12) {
			settings.index_spaceship_selected = 1;
			cryo_game.menu_screen.get_spacecraft_dialog_screen.selected_spaceship =
					ex01MenuScreenGetSpacecraftDialog.SelectedSpaceship.two;
		}
		else if(settings.selected_cryozl14) {
			settings.index_spaceship_selected = 2;
			cryo_game.menu_screen.get_spacecraft_dialog_screen.selected_spaceship =
					ex01MenuScreenGetSpacecraftDialog.SelectedSpaceship.three;
		}
	}

	public void ResetSpaceshipLifeAmmoCoins(){
		cryo_game.menu_screen.get_spacecraft_dialog_screen.spaceship_buys.get(0).bought =
				cryo_game.menu_screen.settings.settings.bought_cryozl10;
		cryo_game.menu_screen.get_spacecraft_dialog_screen.spaceship_buys.get(1).bought =
				cryo_game.menu_screen.settings.settings.bought_cryozl12;
		cryo_game.menu_screen.get_spacecraft_dialog_screen.spaceship_buys.get(2).bought =
				cryo_game.menu_screen.settings.settings.bought_cryozl14;

		cryo_game.menu_screen.get_spacecraft_dialog_screen.spaceship_buys.get(0).selected =
				cryo_game.menu_screen.settings.settings.selected_cryozl10;
		cryo_game.menu_screen.get_spacecraft_dialog_screen.spaceship_buys.get(1).selected =
				cryo_game.menu_screen.settings.settings.selected_cryozl12;
		cryo_game.menu_screen.get_spacecraft_dialog_screen.spaceship_buys.get(2).selected =
				cryo_game.menu_screen.settings.settings.selected_cryozl14;

		cryo_game.menu_screen.get_spacecraft_dialog_screen
				.ProcessBoughtUseButtons();
		cryo_game.menu_screen.get_lifeslots_screen
				.UpdateLifeSlotsHave();
		cryo_game.menu_screen.get_bullets_screen
				.UpdateAmmoSlotsHave();
	}

	public void SetSettingsResetMenuUI(ex01JSONSettingsLoader.json_settings settings){
		//process menu
		menus.savegame_free_version_table.addAction(fadeOut(1f));
		menus.can_render_loadsave = false;
		if(!settings.is_this_unlocked_all_levels)
			menus.can_render_unlock = true;

		is_still_rolling_reset_premium_unlocked = false;
		menus.can_render_unlock_buy_only_load = false;
		menus.savegame_free_version_table.toBack();
		menus.savegame_free_version_table.remove();
		if(menus.unlock_free_version_table==null && !settings.is_this_unlocked_all_levels){
			menus.InitUnlockAllLevels();
			menus.unlock_free_version_table.addAction(fadeOut(0f));
			menus.unlock_free_version_table.act(Gdx.graphics.getDeltaTime());
		}
	}

	public void SetSettingsResetHudUI(ex01JSONSettingsLoader.json_settings settings){
		//same for HUD
		if(cryo_game.game_screen!=null) {
			if(cryo_game.game_screen.hud_display!=null) {
				cryo_game.game_screen.hud_display.savegame_free_version_table
						.addAction(fadeOut(1f));
				cryo_game.game_screen.hud_display.can_render_loadsave = false;
				if(!settings.is_this_unlocked_all_levels)
					cryo_game.game_screen.hud_display.can_render_unlock = true;
				cryo_game.game_screen.hud_display.is_still_rolling_reset_premium_unlocked = false;
				cryo_game.game_screen.hud_display.can_render_unlock_buy_only_load = false;
				cryo_game.game_screen.hud_display.savegame_free_version_table.toBack();
				cryo_game.game_screen.hud_display.savegame_free_version_table.remove();

				if (cryo_game.game_screen.hud_display.unlock_free_version_table == null &&
						!settings.is_this_unlocked_all_levels ) {
					cryo_game.game_screen.hud_display.InitUnlockAllLevels();
					cryo_game.game_screen.hud_display.unlock_free_version_table
							.addAction(fadeOut(0f));
					cryo_game.game_screen.hud_display.unlock_free_version_table
							.act(Gdx.graphics.getDeltaTime());
				}
			}
		}
	}

	public void SetSignInResultSuccess(){
		if(!bCameFromHUD) {
			cryo_game.menu_screen.settings.settings.user_doesnt_want_signed_in = false;
			cryo_game.menu_screen.settings.WriteJson();
			menus.bCurrentlyFadedOutDontDraw = true;
			bNextIsSignOut = true;
			menus.bIsLoggedIn = true;
			menus.CheckPossibleInNeedToFadeOut();
			menus.FadeOutAllBeforeErrorFinished();
			menus.level_base_premium_error.setStyle(menus.textb2ok);
			if(cryo_game.menu_screen.settings.settings.sounds_on)
				cryo_game.menu_screen.menus.base_menu.login_button.play(SND_VOL.SUCCESS_VOL);
			menus.TransitError(ex01Types.LOGIN_SUCCESSFUL,
					true,
					ex01Types.B_CHANGE_LOGIN,
					bNextIsSignOut);
			if(!settings.settings.rate_never) {
				cryo_game.InitRateAppAppear();
			}
		} else {
			cryo_game.game_screen.hud_display.SignInSuccessInHUD();
			bCameFromHUD = false;
		}
	}

	public void SetSignInResultFail(boolean canceled){
		if(!bCameFromHUD) {
			menus.bCurrentlyFadedOutDontDraw = true;
			menus.level_base_premium_error.setStyle(menus.textb1);
			if (cryo_game.menu_screen.settings.settings.sounds_on)
				cryo_game.menu_screen.error_sound_no.play(SND_VOL.ERROR_VOL_M);
			menus.FadeOutAllBeforeErrorFinished();
			if (cryo_game.exit_error.isWiFiConnected()) {
				if (canceled != ex01Types.SIGNIN_CANCELED_YES) {
					menus.TransitError(ex01Types.LOGIN_ERROR,
							true,
							ex01Types.B_DONT_CHANGE_LOGIN,
							ex01Types.B_DONT_CHANGE_LOGIN);
				} else {
					cryo_game.menu_screen.settings.settings.user_doesnt_want_signed_in = true;
					cryo_game.menu_screen.settings.WriteJson();
					menus.TransitError(ex01Types.LOGIN_CANCELLED,
							true,
							ex01Types.B_DONT_CHANGE_LOGIN,
							ex01Types.B_DONT_CHANGE_LOGIN);
				}
			} else {
				if (canceled == ex01Types.SIGNIN_CANCELED_YES) {
					cryo_game.menu_screen.settings.settings.user_doesnt_want_signed_in = true;
					cryo_game.menu_screen.settings.WriteJson();
					menus.TransitError(ex01Types.LOGIN_CANCELLED,
							true,
							ex01Types.B_DONT_CHANGE_LOGIN,
							ex01Types.B_DONT_CHANGE_LOGIN);
				} else {
					menus.TransitError(ex01Types.NO_INTERNET_ERROR,
							true,
							ex01Types.B_DONT_CHANGE_LOGIN,
							ex01Types.B_DONT_CHANGE_LOGIN);
				}
			}
		} else {
			cryo_game.game_screen.hud_display.SignInResultFailedHUD(canceled);
			bCameFromHUD = false;
		}
	}

	public void SetSignOutResultSuccess(){
		cryo_game.menu_screen.settings.settings.user_doesnt_want_signed_in = true;
		cryo_game.menu_screen.settings.WriteJson();
		bNextIsSignOut = false;
		menus.bCurrentlyFadedOutDontDraw = true;
		menus.bIsLoggedIn = false;
		menus.level_base_premium_error.setStyle(menus.textb2ok);
		menus.CheckPossibleInNeedToFadeOut();
		menus.FadeOutAllBeforeErrorFinished();
		if(cryo_game.menu_screen.settings.settings.sounds_on)
			cryo_game.menu_screen.menus.base_menu.logout_button.play(SND_VOL.SUCCESS_VOL);
		menus.TransitError(
				ex01Types.LOGOUT_SUCCESSFUL,
				true,
				ex01Types.B_CHANGE_LOGIN,
				ex01Types.B_NEXT_IS_SIGNOUT_FALSE);
	}

	public void SetSignOutResultFail(){
		menus.bCurrentlyFadedOutDontDraw = true;
		menus.level_base_premium_error.setStyle(menus.textb1);
		if(cryo_game.menu_screen.settings.settings.sounds_on)
			cryo_game.menu_screen.error_sound_no.play(SND_VOL.ERROR_VOL_M);
		menus.FadeOutAllBeforeErrorFinished();
		menus.TransitError(ex01Types.LOGOUT_ERROR,
				true,
				ex01Types.B_DONT_CHANGE_LOGIN,
				ex01Types.B_DONT_CHANGE_LOGIN);
	}

	public boolean delay_achievements_for_next_restart = false;

	public void SetPurchaseResultPremiumSuccess(){
		if(this_came_from_reset_purchase) {
			delay_achievements_for_next_restart = true;
			TransformIntoPremiumSettings();
			TransformIntoPremiumSettingsUI();
			if(!bCameFromHUD) {
				menus.stillWorkingOnReset = true;
				menus.bCurrentlyFadedOutDontDraw = true;
				if(also_unlock_levels) {
					ResetIntoPremiumAlsoUnlock();
				} else {
					ResetIntoPremium();
				}
			} else {
				cryo_game.game_screen.hud_display.stillWorkingOnReset = true;
				cryo_game.game_screen.hud_display.bCurrentlyFadedOutDontDraw = true;
				if(also_unlock_levels) {
					cryo_game.game_screen.hud_display.ResetIntoPremiumAlsoUnlock();
				} else {
					cryo_game.game_screen.hud_display.ResetIntoPremium();
				}
				bCameFromHUD = false;
			}
		} else {
			TransformIntoPremiumSettings();
			TransformIntoPremiumSettingsUI();
			if(!bCameFromHUD) {
				menus.bCurrentlyFadedOutDontDraw = true;
				menus.FadeOutLoginBeforeErrorFinished();
				TransformIntoPremium();
			} else {
				ProcessSettingsAndHUDPurchased9000Life();
				cryo_game.game_screen.hud_display.TransformIntoPremium();
				bCameFromHUD = false;
			}
		}
	}

	public void SetPurchaseResultPremiumFail(int result_code){
		switch(result_code) {
			case ex01Types.IAB_FAILED_ACCOUNT_SETUP_NO:{
				ProcessFailedPurchasePremium(ex01Types.IAB_FAILED_ACCOUNT_SETUP_PROMPT);
			} break;
			case ex01Types.IAB_FAILED_PURCHASE_NO:{
				ProcessFailedPurchasePremium(ex01Types.UPDATED_PREMIUM_FAIL);
			} break;
			default:{
				ProcessFailedPurchasePremium(ex01Types.IAB_UNKNOWN_ERROR);
			} break;
		}
	}

	public void MaintainLoadButton(){
		if (settings.settings.is_this_in_need_for_load_saved) {
			menus.LoginLoadButton();
			menus.currently_rendering_load_reset = false;
			menus.can_render_unlock_buy_only_load = true;
			if(cryo_game.game_screen!=null) {
				cryo_game.game_screen.hud_display.LoginLoadButton();
				cryo_game.game_screen.hud_display.currently_rendering_load_reset = false;
			}
		}
	}

	public void SetPurchaseResultUnlockSuccess(){
		cryo_game.menu_screen.settings.settings.no_level_unlocked = 64;
		if(!bCameFromHUD) {
			menus.bCurrentlyFadedOutDontDraw = true;
			menus.FadeOutLoginBeforeErrorFinished();
			UnlockAllLevels();
			MaintainLoadButton();
		} else {
			cryo_game.game_screen.hud_display.UnlockAllLevelsHUD();
			bCameFromHUD = false;
		}
	}

	public void SetPurchaseResultUnlockFail(int result_code){
		switch(result_code) {
			case ex01Types.IAB_FAILED_ACCOUNT_SETUP_NO:{
				ProcessFailedPurchaseLevels(ex01Types.IAB_FAILED_ACCOUNT_SETUP_PROMPT);
			} break;
			case ex01Types.IAB_FAILED_PURCHASE_NO:{
				ProcessFailedPurchaseLevels(ex01Types.UPDATED_UNLOCKED_LEVELS_FAIL);
			} break;
			default:{
				ProcessFailedPurchaseLevels(ex01Types.IAB_UNKNOWN_ERROR);
			} break;
		}
	}

	public void SetPurchaseResultAmmo9000Fail(int result_code){
		switch(result_code) {
			case ex01Types.IAB_FAILED_ACCOUNT_SETUP_NO:{
				cryo_game.game_screen.hud_display.
						PurchaseAmmo9000FailedHUD(ex01Types.IAB_FAILED_ACCOUNT_SETUP_PROMPT);
			} break;
			case ex01Types.IAB_FAILED_PURCHASE_NO:{
				cryo_game.game_screen.hud_display.
						PurchaseAmmo9000FailedHUD(ex01Types.IAB_FAILED_PURCHASE);
			} break;
			default:{
				cryo_game.game_screen.hud_display.
						PurchaseAmmo9000FailedHUD(ex01Types.IAB_UNKNOWN_ERROR);
			} break;
		}
	}

	public void SetPurchaseResultLife9000Fail(int result_code){
		switch(result_code) {
			case ex01Types.IAB_FAILED_ACCOUNT_SETUP_NO:{
				cryo_game.game_screen.hud_display.
						PurchaseLife9000FailedHUD(ex01Types.IAB_FAILED_ACCOUNT_SETUP_PROMPT);
			} break;
			case ex01Types.IAB_FAILED_PURCHASE_NO:{
				cryo_game.game_screen.hud_display.
						PurchaseLife9000FailedHUD(ex01Types.IAB_FAILED_PURCHASE);
			} break;
			default:{
				cryo_game.game_screen.hud_display.
						PurchaseLife9000FailedHUD(ex01Types.IAB_UNKNOWN_ERROR);
			} break;
		}
	}

	public void SetPurchaseResultCoins100Fail(int result_code){
		if(!cryo_game.menu_screen.settings.settings.is_this_for_free) {
			switch (result_code) {
				case ex01Types.IAB_FAILED_ACCOUNT_SETUP_NO: {
					ProcessBuy100CoinsFailed(ex01Types.IAB_FAILED_ACCOUNT_SETUP_PROMPT);
				}
				break;
				case ex01Types.IAB_FAILED_PURCHASE_NO: {
					ProcessBuy100CoinsFailed(ex01Types.UPDATED_COINS_FAIL);
				}
				break;
				default: {
					ProcessBuy100CoinsFailed(ex01Types.IAB_UNKNOWN_ERROR);
				}
				break;
			}
		} else {
			ProcessBuy100CoinsFailed(ex01Types.PREMIUM_ERROR_FIRST);
		}
	}

	public void SetPurchaseResultCoins250Fail(int result_code){
		if(!cryo_game.menu_screen.settings.settings.is_this_for_free) {
			switch (result_code) {
				case ex01Types.IAB_FAILED_ACCOUNT_SETUP_NO: {
					ProcessBuy250CoinsFailed(ex01Types.IAB_FAILED_ACCOUNT_SETUP_PROMPT);
				}
				break;
				case ex01Types.IAB_FAILED_PURCHASE_NO: {
					ProcessBuy250CoinsFailed(ex01Types.UPDATED_COINS_FAIL);
				}
				break;
				default: {
					ProcessBuy250CoinsFailed(ex01Types.IAB_UNKNOWN_ERROR);
				}
				break;
			}
		} else {
			ProcessBuy250CoinsFailed(ex01Types.PREMIUM_ERROR_FIRST);
		}
	}

	public void SetPurchaseResultCoins500Fail(int result_code){
		if(!cryo_game.menu_screen.settings.settings.is_this_for_free) {
			switch (result_code) {
				case ex01Types.IAB_FAILED_ACCOUNT_SETUP_NO: {
					ProcessBuy500CoinsFailed(ex01Types.IAB_FAILED_ACCOUNT_SETUP_PROMPT);
				}
				break;
				case ex01Types.IAB_FAILED_PURCHASE_NO: {
					ProcessBuy500CoinsFailed(ex01Types.UPDATED_COINS_FAIL);
				}
				break;
				default: {
					ProcessBuy500CoinsFailed(ex01Types.IAB_UNKNOWN_ERROR);
				}
				break;
			}
		} else {
			ProcessBuy500CoinsFailed(ex01Types.PREMIUM_ERROR_FIRST);
		}
	}

	public void SetPurchaseResultCoins750Fail(int result_code){
		if(!cryo_game.menu_screen.settings.settings.is_this_for_free) {
			switch (result_code) {
				case ex01Types.IAB_FAILED_ACCOUNT_SETUP_NO: {
					ProcessBuy750CoinsFailed(ex01Types.IAB_FAILED_ACCOUNT_SETUP_PROMPT);
				}
				break;
				case ex01Types.IAB_FAILED_PURCHASE_NO: {
					ProcessBuy750CoinsFailed(ex01Types.UPDATED_COINS_FAIL);
				}
				break;
				default: {
					ProcessBuy750CoinsFailed(ex01Types.IAB_UNKNOWN_ERROR);
				}
				break;
			}
		} else {
			ProcessBuy750CoinsFailed(ex01Types.PREMIUM_ERROR_FIRST);
		}
	}

	public void SetPurchaseResultCoins1000Fail(int result_code){
		if(!cryo_game.menu_screen.settings.settings.is_this_for_free) {
			switch (result_code) {
				case ex01Types.IAB_FAILED_ACCOUNT_SETUP_NO: {
					ProcessBuy1000CoinsFailed(ex01Types.IAB_FAILED_ACCOUNT_SETUP_PROMPT);
				}
				break;
				case ex01Types.IAB_FAILED_PURCHASE_NO: {
					ProcessBuy1000CoinsFailed(ex01Types.UPDATED_COINS_FAIL);
				}
				break;
				default: {
					ProcessBuy1000CoinsFailed(ex01Types.IAB_UNKNOWN_ERROR);
				}
				break;
			}
		} else {
			ProcessBuy1000CoinsFailed(ex01Types.PREMIUM_ERROR_FIRST);
		}
	}

	public void SetPurchaseResultCoins1500Fail(int result_code){
		if(!cryo_game.menu_screen.settings.settings.is_this_for_free) {
			switch (result_code) {
				case ex01Types.IAB_FAILED_ACCOUNT_SETUP_NO: {
					ProcessBuy1500CoinsFailed(ex01Types.IAB_FAILED_ACCOUNT_SETUP_PROMPT);
				}
				break;
				case ex01Types.IAB_FAILED_PURCHASE_NO: {
					ProcessBuy1500CoinsFailed(ex01Types.UPDATED_COINS_FAIL);
				}
				break;
				default: {
					ProcessBuy1500CoinsFailed(ex01Types.IAB_UNKNOWN_ERROR);
				}
				break;
			}
		} else {
			ProcessBuy1500CoinsFailed(ex01Types.PREMIUM_ERROR_FIRST);
		}
	}

	public void ProcessFailedPurchasePremium(String text){
		if(!bCameFromHUD) {
			menus.bCurrentlyFadedOutDontDraw = true;
			menus.FadeOutLoginBeforeErrorFinished();
			if(!menus.bAlreadyPressedNewGame)
				TransformIntoPremiumFailed(text);
		} else {
			cryo_game.game_screen.hud_display.ProcedureFailedAdFree(text);
			//bCameFromHUD = false;
		}
	}

	public void ProcessFailedPurchaseLevels(String text){
		if(!bCameFromHUD) {
			menus.bCurrentlyFadedOutDontDraw = true;
			menus.FadeOutLoginBeforeErrorFinished();
			UnlockAllLevelsFailed(text);
		} else {
			cryo_game.game_screen.hud_display.UnlockAllLevelsFailedHUD(text);
			bCameFromHUD = false;
		}
	}

	public void StartLoginProcedures(){
		menus.bCurrentlyFadedOutDontDraw = true;
		PerfromGoogleCloudSignIN();
	}

	public void StartLoginProceduresFromHUD(){
		PerfromGoogleCloudSignIN();
	}

	public void RetractTablesUnlockFromHUDDisplay(){
		if(cryo_game.game_screen!=null && cryo_game.game_screen.hud_display!=null){
			cryo_game.game_screen.hud_display.unlock_free_version_table.remove();
		}
	}

	public void RetractTablesPremiumFromHUDDisplay(){
		if(cryo_game.game_screen!=null && cryo_game.game_screen.hud_display!=null){
			cryo_game.game_screen.hud_display.add_free_version_table.remove();
		}
	}

	public void TransformIntoPremium(){
		menus.add_free_version_table.remove();
		menus.level_base_premium_error.setStyle(menus.textb2ok);
		if(cryo_game.menu_screen.settings.settings.sounds_on)
			cryo_game.menu_screen.menus.base_menu.success_button.play(SND_VOL.SUCCESS_VOL);
		RetractTablesPremiumFromHUDDisplay();
		menus.TransitError(ex01Types.UPDATED_PREMIUM,
				true,
				ex01Types.B_DONT_CHANGE_LOGIN,
				ex01Types.B_DONT_CHANGE_LOGIN);
		menus.InitUnlock();
		if(cryo_game.game_screen!=null)
			cryo_game.game_screen.hud_display.InitUnlock();
	}

	public void TransformIntoPremiumSettingsUI(){
		cryo_game.menu_screen.get_lifeslots_screen.UpdateLifeSlotsHave();
		cryo_game.menu_screen.get_bullets_screen.UpdateAmmoSlotsHave();
		cryo_game.menu_screen.get_spacecraft_dialog_screen.UpdateSpacecraftOptions();
	}

	public void TransformIntoPremiumSettings(){
		menus.base_menu.settings.settings.is_this_premium_updated = true;
		menus.base_menu.settings.settings.can_buy_cryozl10 = true;
		menus.base_menu.settings.settings.can_buy_cryozl12 = true;
		menus.base_menu.settings.settings.can_buy_cryozl14 = true;
		menus.base_menu.settings.settings.bought_cryozl10 = true;
		menus.base_menu.settings.settings.bought_cryozl12 = true;
		menus.base_menu.settings.settings.bought_cryozl14 = true;
		if(!this_came_from_reset_purchase) {
			menus.base_menu.settings.settings.counter_ammo_base += 5000;
			menus.base_menu.settings.settings.counter_life_base += 5000;
		}
		menus.base_menu.settings.settings.were_currently_limited_on_life = false;
		menus.base_menu.settings.settings.were_currently_limited_on_ammo = false;
		menus.base_menu.settings.WriteJson();
		if(!this_came_from_reset_purchase)
			cryo_game.SAVE_GAME_TO_CLOUD();
	}

	public void ProcessSettingsAndHUDPurchased9000Life(){
		cryo_game.game_screen.hud_display.currentHealthCounter = 10;
		cryo_game.game_screen.hud_display.counter_life_fiver = 10;
		cryo_game.game_screen.hud_display.counter_life_base += 5000;
		cryo_game.game_screen.hud_display.counter_ammo_fiver += 5;
		cryo_game.game_screen.hud_display.counter_ammo_base += 5000;

		cryo_game.game_screen.hud_display.need_to_change_life = true;
		cryo_game.game_screen.hud_display.need_to_change_ammo = true;
		cryo_game.game_screen.hud_display.need_to_change_hud_data = true;
		cryo_game.game_screen.hud_display
				.SetHealthCounterTo(cryo_game.game_screen.hud_display.currentHealthCounter);
	}

	public void ResetIntoPremium(){
		if(menus.add_free_version_table!=null)
			menus.add_free_version_table.remove();
		menus.base_menu.settings.settings.is_this_premium_updated = true;
		menus.level_base_premium_error.setStyle(menus.textb2ok);
		if(cryo_game.menu_screen.settings.settings.sounds_on)
			cryo_game.menu_screen.menus.base_menu.success_button.play(SND_VOL.SUCCESS_VOL);
		menus.can_render_unlock = true;
		menus.can_render_unlock_buy_only_load = false;
		// this redraws stars in page levels
		cryo_game.levels_screen.level_selector
				.ResetLevelsDrawkingsOnChange();
		menus.TransitError(ex01Types.RESET_PREMIUM,
				true,
				ex01Types.B_DONT_CHANGE_LOGIN,
				ex01Types.B_DONT_CHANGE_LOGIN);

		//initiate load save button procedure
		if(!menus.base_menu.settings.settings.is_this_in_need_for_load_saved){
			menus.InitLoadGameFromCloud();
			menus.savegame_free_version_table.addAction(sequence(Actions.delay(6f),
					Actions.fadeIn(1f)));
			menus.base_menu.settings.settings.is_this_in_need_for_load_saved = true;
			menus.can_render_loadsave = true;
			menus.can_render_unlock = true;

			//HUD ALSO
			if(cryo_game.game_screen!=null) {
				cryo_game.game_screen.hud_display.InitLoadGameFromCloud();
				cryo_game.game_screen.hud_display.InitUnlock();
				cryo_game.game_screen.hud_display.savegame_free_version_table
						.addAction(sequence(
								Actions.delay(6f),
								Actions.fadeIn(1f)));
				cryo_game.game_screen.hud_display.can_render_loadsave = true;
				cryo_game.game_screen.hud_display.can_render_unlock = true;
				cryo_game.game_screen.hud_display.can_render_unlock_buy_only_load = false;
				cryo_game.game_screen.hud_display
						.is_still_rolling_reset_premium_unlocked_duplex = false;
				cryo_game.game_screen.hud_display
						.is_still_rolling_reset_premium_unlocked = false;
			}
		}

		// ##### ##### ##### HUD display part
		if(cryo_game.game_screen!=null){
			if(cryo_game.game_screen.hud_display.add_free_version_table!=null){
				cryo_game.game_screen.hud_display.add_free_version_table.remove();
			}
			cryo_game.game_screen.hud_display.level_base_premium_error
					.setStyle(cryo_game.game_screen.hud_display.textb2ok);
		}

		cryo_game.menu_screen.settings.WriteJson();
		cryo_game.SAVE_GAME_TO_CLOUD();
	}

	public void ResetIntoPremiumAlsoUnlock(){
		cryo_game.menu_screen.settings.settings.no_level_unlocked = 64;
		if(menus.add_free_version_table!=null)
			menus.add_free_version_table.remove();
		menus.base_menu.settings.settings.is_this_premium_updated = true;
		if(menus.unlock_free_version_table!=null)
			menus.unlock_free_version_table.remove();
		menus.base_menu.settings.settings.is_this_unlocked_all_levels = true;

		for(ex01MenuLevelSelectorBase.Level level: this.menus.menu_init.level_selector.levels)
		{
			level.is_closed = false;
			level.is_unlocked = true;
		}
		for(ex01JSONSettingsLoader.level_data leveld:  settings.settings.levels)
		{
			leveld.is_closed = false;
			leveld.is_unlocked = true;
		}
		menus.can_render_unlock_buy_only_load = true;
		// this redraws stars in page levels
		cryo_game.levels_screen.level_selector
				.ResetLevelsDrawkingsOnChange();
		menus.level_base_premium_error.setStyle(menus.textb2ok);
		if(cryo_game.menu_screen.settings.settings.sounds_on)
			cryo_game.menu_screen.menus.base_menu.success_button.play(SND_VOL.SUCCESS_VOL);
		menus.TransitError(ex01Types.RESET_PREMIUM_UNLOCKED,
				true,
				ex01Types.B_DONT_CHANGE_LOGIN,
				ex01Types.B_DONT_CHANGE_LOGIN);

		//initiate load save button procedure
		if(!menus.base_menu.settings.settings.is_this_in_need_for_load_saved){
			menus.InitLoadGameFromCloud();
			menus.savegame_free_version_table.addAction(sequence(Actions.delay(6f),
																 Actions.fadeIn(2f)));
			menus.base_menu.settings.settings.is_this_in_need_for_load_saved = true;
			menus.can_render_loadsave = true;

			//HUD ALSO
			if(cryo_game.game_screen!=null) {
				cryo_game.game_screen.hud_display.InitLoadGameFromCloud();
				cryo_game.game_screen.hud_display.savegame_free_version_table
						.addAction(sequence(
								Actions.delay(6f),
								Actions.fadeIn(1f)));
				cryo_game.game_screen.hud_display.can_render_loadsave = true;
			}
		}

		// ##### ##### ##### HUD display part
		if(cryo_game.game_screen!=null){
			if(cryo_game.game_screen.hud_display.add_free_version_table!=null){
				cryo_game.game_screen.hud_display.add_free_version_table.remove();
			}
			if(cryo_game.game_screen.hud_display.unlock_free_version_table!=null){
				cryo_game.game_screen.hud_display.unlock_free_version_table.remove();
			}
			cryo_game.game_screen.hud_display.level_base_premium_error
					.setStyle(cryo_game.game_screen.hud_display.textb2ok);
			cryo_game.game_screen.hud_display.can_render_unlock_buy_only_load = true;
		}

		cryo_game.menu_screen.settings.WriteJson();
		cryo_game.SAVE_GAME_TO_CLOUD();
	}

	public void UnlockAllLevelsALL(){
		for(ex01MenuLevelSelectorBase.Level level: this.menus.menu_init.level_selector.levels)
		{
			level.is_closed = false;
			level.is_unlocked = true;
		}
		for(ex01JSONSettingsLoader.level_data leveld:  settings.settings.levels)
		{
			leveld.is_closed = false;
			leveld.is_unlocked = true;
		}
		cryo_game.menu_screen.settings.WriteJson();
		cryo_game.SAVE_GAME_TO_CLOUD();
		// this redraws stars in page levels
		cryo_game.levels_screen.level_selector
				.ResetLevelsDrawkingsOnChange();
	}

	public void UnlockAllLevels(){
		UnlockAllLevelsALL();
		menus.unlock_free_version_table.remove();
		menus.base_menu.settings.settings.is_this_unlocked_all_levels = true;
		menus.level_base_premium_error.setStyle(menus.textb2ok);
		if(cryo_game.menu_screen.settings.settings.sounds_on)
			cryo_game.menu_screen.menus.base_menu.success_button.play(SND_VOL.SUCCESS_VOL);
		menus.TransitError(ex01Types.UPDATED_UNLOCKED_LEVELS,
				true,
				ex01Types.B_DONT_CHANGE_LOGIN,
				ex01Types.B_DONT_CHANGE_LOGIN);
		RetractTablesUnlockFromHUDDisplay();
	}

	public void ProcessBuy100Coins(){
		get_coins_dialog_screen.ProcessGotCoinsResult();
	}
	public void ProcessBuy250Coins(){
		get_coins_dialog_screen.ProcessGotCoinsResult();
	}
	public void ProcessBuy500Coins(){
		get_coins_dialog_screen.ProcessGotCoinsResult();
	}
	public void ProcessBuy750Coins(){
		get_coins_dialog_screen.ProcessGotCoinsResult();
	}
	public void ProcessBuy1000Coins(){
		get_coins_dialog_screen.ProcessGotCoinsResult();
	}
	public void ProcessBuy1500Coins(){
		get_coins_dialog_screen.ProcessGotCoinsResult();
	}

	public void TransformIntoPremiumFailed(String string){
		menus.add_free_version_table.clearActions();
		text = string;
		menus.add_free_version_table.addAction(sequence(Actions.fadeOut(1.0f),
				Actions.run(new Runnable() {
					public void run() {
						menus.level_base_premium_error.setStyle(menus.textb1);
						if (cryo_game.menu_screen.settings.settings.sounds_on)
							cryo_game.menu_screen.menus.base_menu
									.error_sound_no.play(SND_VOL.ERROR_VOL_M);
						menus.TransitError(text,
								true,
								ex01Types.B_DONT_CHANGE_LOGIN,
								ex01Types.B_DONT_CHANGE_LOGIN);
					}
				})));
	}

	public void UnlockAllLevelsFailed(String text){
		text1 = text;
		menus.unlock_free_version_table.clearActions();
		is_still_rolling_reset_premium_unlocked_duplex = true;
		is_still_rolling_reset_premium_unlocked = true;
		menus.unlock_free_version_table.addAction(sequence(Actions.fadeOut(1.0f),
				Actions.run(new Runnable() {
					public void run() {
						menus.level_base_premium_error.setStyle(menus.textb1);
						if (cryo_game.menu_screen.settings.settings.sounds_on)
							cryo_game.menu_screen.menus.base_menu.error_sound_no
									.play(SND_VOL.ERROR_VOL_M);
						menus.TransitError(text1,
								true,
								ex01Types.B_DONT_CHANGE_LOGIN,
								ex01Types.B_DONT_CHANGE_LOGIN);
					}
				})));
	}

	public void ProcessBuy100CoinsFailed(String errors){
		get_coins_dialog_screen.ProcessErrorOnBought(errors);
	}
	public void ProcessBuy250CoinsFailed(String errors){
		get_coins_dialog_screen.ProcessErrorOnBought(errors);
	}
	public void ProcessBuy500CoinsFailed(String errors){
		get_coins_dialog_screen.ProcessErrorOnBought(errors);
	}
	public void ProcessBuy750CoinsFailed(String errors){
		get_coins_dialog_screen.ProcessErrorOnBought(errors);
	}
	public void ProcessBuy1000CoinsFailed(String errors){
		get_coins_dialog_screen.ProcessErrorOnBought(errors);
	}
	public void ProcessBuy1500CoinsFailed(String errors){
		get_coins_dialog_screen.ProcessErrorOnBought(errors);
	}

	@Override
	public void setGetScoreResult(long score, boolean success) {
		if(menus!=null && menus.menu_init!=null) {
			if (success) {
				if (!rank_command_came_from_selector) {
					if (scores_dialog_screen.get_social_score) {
						if (score != -1) {
							settings.settings
									.level_rank[scores_dialog_screen.selected_lev] = (int) score;
							scores_dialog_screen.ranking_top.setText(ex01Types.RANKING_SOCIAL +
									Integer.toString(settings.settings.level_rank
											[scores_dialog_screen.selected_lev]));
						} else {
							scores_dialog_screen.ranking_top.setText(ex01Types.RANKING_SOCIAL_NA);
						}
					} else {
						if (score != -1) {
							settings.settings
									.level_rank[scores_dialog_screen.selected_lev] = (int) score;
							scores_dialog_screen.ranking_top.setText(ex01Types.RANKING_PUBLIC +
									Integer.toString(settings.settings.level_rank
											[scores_dialog_screen.selected_lev]));
						} else {
							scores_dialog_screen.ranking_top.setText(ex01Types.RANKING_PUBLIC_NA);
						}
					}
				} else {
					if (menus.menu_init.get_social_score) {
						if (score != -1) {
							settings.settings.level_rank[menus.menu_init
									.level_selector.received_open_from_level_no] = (int) score;
							menus.menu_init.ranking_top.setText(ex01Types.RANKING_SOCIAL +
									Integer.toString(settings
											.settings.level_rank[menus.menu_init
											.level_selector.received_open_from_level_no + 1]));
						} else {
							menus.menu_init.ranking_top.setText(ex01Types.RANKING_SOCIAL_NA);
						}
						rank_command_came_from_selector = false;
					} else {
						if (score != -1) {
							settings.settings.level_rank[menus.menu_init
									.level_selector.received_open_from_level_no] = (int) score;
							menus.menu_init.ranking_top.setText(ex01Types.RANKING_PUBLIC +
									Integer.toString(settings.settings.
											level_rank[menus.menu_init
											.level_selector.received_open_from_level_no + 1]));
						} else {
							menus.menu_init.ranking_top.setText(ex01Types.RANKING_PUBLIC_NA);
						}
						rank_command_came_from_selector = false;
					}
				}
			} else {
				if (!rank_command_came_from_selector) {
					scores_dialog_screen.ranking_top.setText(ex01Types.NOT_AVAILABLE);
				} else {
					menus.menu_init.ranking_top.setText(ex01Types.NOT_AVAILABLE);
				}
			}
		}
	}

    public void FadeOutSettingsDialog(float fade){
    	settings_dialog_screen.settings_dialog.addAction(Actions.fadeOut(fade));
    }    
    
    public void FadeInSettingsDialog(float fade){
    	settings_dialog_screen.settings_dialog.addAction(Actions.fadeIn(fade));
    }     
    
    public void InitShaderTransition(){
		shader = new ShaderProgram(
				Gdx.files.internal(ex01Types.SHADER_VIGNETTE_VERT01big),
				Gdx.files.internal(ex01Types.SHADER_VIGNETTE_FRAG01big));
		shader_grayscale = new ShaderProgram(
				Gdx.files.internal(ex01Types.SHADER_GRAYSCALE_VERT01big),
				Gdx.files.internal(ex01Types.SHADER_GRAYSCALE_FRAG01big));
		resolution = new float[2];
		camera.position.set(ex01Types.VIRTUAL_SCREEN_WIDTH * 0.5f,
							ex01Types.VIRTUAL_SCREEN_HEIGHT * 0.5f,
							0.0f);
		menus.stage.getBatch().setShader(shader);
		if (!shader.isCompiled()) {
			Gdx.app.error("Shader", shader.getLog());
		} 	
    }
    
    public void InitShaderTimers(){
		finished_transition = false;
		this.menus.stage.getBatch().setShader(shader);
		state = State.TransitionIn;
		time = 0.0f;       	
    }
    
    @Override
    public void render(float delta) {
    //used for FPS Start load /* 1,000,000,000ns == one second */
    if(delta < 0.02f){ StartTimePassed = true; }
	// *********************************************************************************************
		if(StartTimePassed){    	
	    	if(render_no == 0){
	    		render_no++;
	    		InitShaderTimers();
				cryo_game.menu_screen.menu_music.setLooping(true);
				if(settings.settings.sounds_on) {
					cryo_game.menu_screen.menu_music.setVolume(ex01Types.MUSIC_MENU_LEVEL);
				} else {
					cryo_game.menu_screen.menu_music.setVolume(ex01Types.MUSIC_MENU_LEVEL_NULL);
				}
				cryo_game.menu_screen.menu_music.play();
			}
	        Gdx.gl.glClearColor(0, 0, 0, 1);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        if(!finished_transition) {
	        	renderTransition(delta);   
	        } else {
				if(!already_loggedin_at_startup) {
					if(!cryo_game.menu_screen.settings.settings.is_this_for_free) { // PREMIUM
						if (!cryo_game.menu_screen.settings.settings.user_doesnt_want_signed_in) {
							StartLoginProcedures();
						} else {
							menus.bCurrentlyFadedOutDontDraw = true;
							menus.level_base_premium_error.setStyle(menus.textb1);
							menus.FadeOutAllBeforeErrorFinished();
							menus.TransitErrorFinish();
							menus.LogoutUnlockButton();
							menus.LogoutLoadButton();
						}
					}
					already_loggedin_at_startup = true;
				}
			}
	        menus.Render(delta);
			if(grayscale_shader_active_VALUE){
				VALUE_screen.Render(delta);
			}
			if(grayscale_shader_active_exit){
				exit_dialog_screen.Render(delta);	
			}
			if(grayscale_shader_active_settings){
				settings_dialog_screen.Render(delta);	
			}
			if(grayscale_shader_active_get_spacecraft){
				get_spacecraft_dialog_screen.Render(delta);
			}   		
			if(grayscale_shader_active_get_coins){
				get_coins_dialog_screen.Render(delta);
			}
			if(grayscale_shader_active_get_lifeslots){
				get_lifeslots_screen.Render(delta);
			}
			if(grayscale_shader_active_get_bullets){
				get_bullets_screen.Render(delta);
			}   		
			if(grayscale_shader_active_scores){
				scores_dialog_screen.Render(delta);   	
			}
			if(grayscale_shader_active_scores_lister){
				scores_lister.Render(delta);   	
			}		
			if(grayscale_shader_active_help){
				help_screen.Render(delta);   	
			}
		}
    }
    
    public void renderTransition(float delta){
		// if(delta < 0.02f){ StartTimePassed = true; }
		// used for FPS Start load /* 1,000,000,000ns == one second *  /
		// *****************************************************************************************
		radius = time / TRANSITION_IN_TIME;
		if (time > TRANSITION_IN_TIME) {
			finished_transition = true;
			time = 0.0f;
			state = State.Picture;
		}
		time += delta;
		radius = MathUtils.clamp(radius, 0.0f, 1.0f);
		menus.stage.getBatch().setProjectionMatrix(camera.combined);
		menus.stage.getBatch().begin();
		shader.setUniform2fv("resolution", resolution , 0, 2);
		shader.setUniformf("radius", radius);
		menus.stage.getBatch().end();
    }
    
	@Override
	public void pause(){
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		resolution[0] = width;
		resolution[1] = height;		
	}
	
	@Override
	public void hide(){
	}
	
	@Override
	public void resume(){

		cryo_game.menu_screen.InitShaderTimers();
	}
	
	@Override
	public void dispose(){
		Dispose();
	}
	
	@Override
	public void show(){
		InitShaderTimers();
		if(cryo_game.game_state == GameState.MENU_SCREEN){
			if(grayscale_shader_active_exit){
				cryo_game.menu_screen.grayscale_shader_active_exit = true;
				cryo_game.menu_screen.menus.stage.getBatch()
						.setShader(cryo_game.menu_screen.shader_grayscale);
				Gdx.input.setInputProcessor(cryo_game.menu_screen.exit_dialog_screen
						.stage_exit_dialog);

			} else if(grayscale_shader_active_VALUE){
				cryo_game.menu_screen.grayscale_shader_active_VALUE = true;
				cryo_game.menu_screen.menus.stage.getBatch()
						.setShader(cryo_game.menu_screen.shader_grayscale);
				Gdx.input.setInputProcessor(cryo_game.menu_screen.VALUE_screen
						.stage_exit_dialog);

			} else if(grayscale_shader_active_settings){
				cryo_game.menu_screen.grayscale_shader_active_settings = true;
				cryo_game.menu_screen.menus.stage.getBatch()
						.setShader(cryo_game.menu_screen.shader_grayscale);
				Gdx.input.setInputProcessor(cryo_game.menu_screen.settings_dialog_screen
						.stage_settings_dialog);

			} else if(grayscale_shader_active_get_spacecraft){
				cryo_game.menu_screen.grayscale_shader_active_settings = false;
				cryo_game.menu_screen.grayscale_shader_active_get_spacecraft = true;
				cryo_game.menu_screen.menus.stage.getBatch()
						.setShader(cryo_game.menu_screen.shader_grayscale);
				Gdx.input.setInputProcessor(cryo_game.menu_screen.get_spacecraft_dialog_screen
						.stage_get_spacecraft_dialog);

			} else if(grayscale_shader_active_get_coins){
				cryo_game.menu_screen.grayscale_shader_active_settings = false;
				cryo_game.menu_screen.grayscale_shader_active_get_coins = true;
				cryo_game.menu_screen.menus.stage.getBatch()
						.setShader(cryo_game.menu_screen.shader_grayscale);
				Gdx.input.setInputProcessor(cryo_game.menu_screen.get_coins_dialog_screen
						.stage_get_coins_dialog);

			} else if(grayscale_shader_active_get_lifeslots){
				cryo_game.menu_screen.grayscale_shader_active_settings = false;
				cryo_game.menu_screen.grayscale_shader_active_get_lifeslots = true;
				cryo_game.menu_screen.menus.stage.getBatch()
						.setShader(cryo_game.menu_screen.shader_grayscale);
				Gdx.input.setInputProcessor(cryo_game.menu_screen.get_lifeslots_screen
						.stage_get_lifeslots_dialog);

			} else if(grayscale_shader_active_get_bullets){
				cryo_game.menu_screen.grayscale_shader_active_settings = false;
				cryo_game.menu_screen.grayscale_shader_active_get_bullets = true;
				cryo_game.menu_screen.menus.stage.getBatch()
						.setShader(cryo_game.menu_screen.shader_grayscale);
				Gdx.input.setInputProcessor(cryo_game.menu_screen.get_bullets_screen
						.stage_get_bullets_dialog);

			} else if(grayscale_shader_active_scores){
				cryo_game.menu_screen.grayscale_shader_active_scores = true;
				cryo_game.menu_screen.menus.stage.getBatch()
						.setShader(cryo_game.menu_screen.shader_grayscale);
				Gdx.input.setInputProcessor(cryo_game.menu_screen.scores_dialog_screen
						.stage_scores_dialog);

			} else if(grayscale_shader_active_scores_lister){
				cryo_game.menu_screen.grayscale_shader_active_scores = false;
				cryo_game.menu_screen.grayscale_shader_active_scores_lister = true;
				cryo_game.menu_screen.menus.stage.getBatch()
						.setShader(cryo_game.menu_screen.shader_grayscale);
				Gdx.input.setInputProcessor(cryo_game.menu_screen.scores_lister
						.list_stage);

			} else if(grayscale_shader_active_help){
				cryo_game.menu_screen.grayscale_shader_active_scores = false;
				cryo_game.menu_screen.grayscale_shader_active_help = true;
				cryo_game.menu_screen.menus.stage.getBatch()
						.setShader(cryo_game.menu_screen.shader_grayscale);
				Gdx.input.setInputProcessor(cryo_game.menu_screen.help_screen
						.stage_help);

			} else {
				Gdx.input.setInputProcessor(cryo_game.menu_screen.menus.stage);								
			}
		}
	}

	public void CheckInAppData(){
		if(!checked_inapp_data){
			if(cryo_game.inappInterfaceResolver.isThisPremiumUpdated()
					&& !settings.settings.is_this_premium_updated){
				if(cryo_game.inappInterfaceResolver.isThisLevelsUnlocked()
						&& !settings.settings.is_this_unlocked_all_levels) {
					also_unlock_levels = true;
				}
				this_came_from_reset_purchase = true;
				isSomethingWrong = true;
				cryo_game.inappInterfaceResolver.ActAsIfWeJustPurchasedPremium(this);
			} else {
				menus.bCurrentlyFadedOutDontDraw = false;
			}
			//checked_inapp_data = true;
		}
	}

	public void PerfromGoogleCloudSignIN(){
//		cryo_game.google_facebook_services.SignInClick(ex01Types.SING_IN, this);
	}

	public void PerfromGoogleCloudSignOUT(){
//		cryo_game.google_facebook_services.SignInClick(ex01Types.SIGN_OUT, this);
	}

	public void ChangeLoginLogout(boolean next_is_logout){
		if(next_is_logout){
			menus.loginlogout_free_version.setStyle(menus.styleoffz);
			if(cryo_game.menu_screen.settings.settings.is_this_in_need_for_load_saved)
				menus.LoginLoadButton();
			if(!cryo_game.menu_screen.settings.settings.is_this_unlocked_all_levels)
				menus.LoginUnlockButton();
		} else {
			menus.loginlogout_free_version.setStyle(menus.styleonz);
			FadeOutLoadInHUSD();
			FadeOutUnlockInHUSD();
			menus.LogoutUnlockButton();
			menus.LogoutLoadButton();
		}
	}

	public void FadeOutLoadInHUSD(){
		if(cryo_game.game_screen!=null){
			if(cryo_game.game_screen.hud_display.savegame_free_version_table!=null){
				cryo_game.game_screen.hud_display
						.savegame_free_version_table.clearActions();
				cryo_game.game_screen.hud_display
						.savegame_free_version_table.addAction(fadeOut(0f));
				cryo_game.game_screen.hud_display
						.savegame_free_version_table.act(Gdx.graphics.getDeltaTime());
			}
		}
	}

	public void FadeOutUnlockInHUSD(){
		if(cryo_game.game_screen!=null){
			if(cryo_game.game_screen.hud_display.unlock_free_version_table!=null){
				cryo_game.game_screen.hud_display
						.unlock_free_version_table.clearActions();
				cryo_game.game_screen.hud_display
						.unlock_free_version_table.addAction(fadeOut(0f));
				cryo_game.game_screen.hud_display
						.unlock_free_version_table.act(Gdx.graphics.getDeltaTime());
			}
		}
	}

	public void InactivateShaderBooleans(){
		cryo_game.menu_screen.grayscale_shader_active_VALUE = false;
		cryo_game.menu_screen.grayscale_shader_active_exit = false;
		cryo_game.menu_screen.grayscale_shader_active_settings = false;
		cryo_game.menu_screen.grayscale_shader_active_get_spacecraft = false;
		cryo_game.menu_screen.grayscale_shader_active_settings = false;
		cryo_game.menu_screen.grayscale_shader_active_get_coins = false;
		cryo_game.menu_screen.grayscale_shader_active_settings = false;
		cryo_game.menu_screen.grayscale_shader_active_get_lifeslots = false;
		cryo_game.menu_screen.grayscale_shader_active_settings = false;
		cryo_game.menu_screen.grayscale_shader_active_get_bullets = false;
		cryo_game.menu_screen.grayscale_shader_active_scores = false;
		cryo_game.menu_screen.grayscale_shader_active_scores = false;
		cryo_game.menu_screen.grayscale_shader_active_scores_lister = false; 
		cryo_game.menu_screen.grayscale_shader_active_scores = false;
		cryo_game.menu_screen.grayscale_shader_active_help = false;
	}
 
	@Override
	public boolean keyDown(int keyCode){
		return true;
	}

	public void Dispose(){
		viewport = null;
		if(menus!=null){
			menus.Dispose();
			menus = null;
		}
		if(VALUE_screen!=null){
			VALUE_screen.Dispose();
			VALUE_screen = null;
		}
		if(exit_dialog_screen!=null){
			exit_dialog_screen.Dispose();
			exit_dialog_screen = null;
		}
		if(settings_dialog_screen!=null){
			settings_dialog_screen.Dispose();
			settings_dialog_screen = null;
		}
		if(get_spacecraft_dialog_screen!=null){
			get_spacecraft_dialog_screen.Dispose();
			get_spacecraft_dialog_screen = null;
		}
		if(get_coins_dialog_screen!=null){
			get_coins_dialog_screen.Dispose();
			get_coins_dialog_screen = null;
		}
		if(get_lifeslots_screen!=null){
			get_lifeslots_screen.Dispose();
			get_lifeslots_screen = null;
		}
		if(get_bullets_screen!=null){
			get_bullets_screen.Dispose();
			get_bullets_screen = null;
		}
		if(scores_dialog_screen!=null){
			scores_dialog_screen.Dispose();
			scores_dialog_screen = null;
		}
		if(help_screen!=null){
			help_screen.Dispose();
			help_screen = null;
		}
		if(scores_lister!=null){
			scores_lister.Dispose();
			scores_lister = null;
		}
		if(multiplexer!=null){
			multiplexer.clear();
			multiplexer = null;
		}
		if(skin!=null){
			skin.dispose();
			skin = null;
		}

		settings = null;
		camera = null;
		state = null;
		backProcessor = null;

		if(shader!=null) {
			shader.end();
			shader.dispose();
		}
		if(shader_grayscale!=null) {
			shader_grayscale.end();
			shader_grayscale.dispose();
		}
		if(back_button!= null) {
			back_button.dispose();
			back_button = null;
		}
		if(done_button!=null) {
			done_button.dispose();
			done_button = null;
		}
		if(level_left_right_button!=null) {
			level_left_right_button.dispose();
			level_left_right_button = null;
		}
		if(level_opener_selector_button!=null) {
			level_opener_selector_button.dispose();
			level_opener_selector_button = null;
		}
		if(menu_selector_button!=null) {
			menu_selector_button.dispose();
			menu_selector_button = null;
		}
		if(exit_code!=null) {
			exit_code.dispose();
			exit_code = null;
		}
		if(enter_play_game!=null) {
			enter_play_game.dispose();
			enter_play_game = null;
		}
		if(cash_register_coins!=null) {
			cash_register_coins.dispose();
			cash_register_coins = null;
		}
		if(ammo_gun_load!=null) {
			ammo_gun_load.dispose();
			ammo_gun_load = null;
		}
		if(lifeslots_heartpump!=null) {
			lifeslots_heartpump.dispose();
			lifeslots_heartpump = null;
		}
		if(error_sound_no!=null) {
			error_sound_no.dispose();
			error_sound_no = null;
		}
		if(green_already_have!=null) {
			green_already_have.dispose();
			green_already_have = null;
		}
		if(basic_button!=null) {
			basic_button.dispose();
			basic_button = null;
		}
		if(success_button!=null) {
			success_button.dispose();
			success_button = null;
		}
		if(login_button!=null) {
			login_button.dispose();
			login_button = null;
		}
		if(logout_button!=null) {
			logout_button.dispose();
			logout_button = null;
		}

		if(menu_music!=null){
			if(menu_music.isPlaying()){
				menu_music.stop();
			}
			menu_music.dispose();
			menu_music = null;
		}
	}
}