//WMS3 2015

package com.cryotrax.game;
import static com.badlogic.gdx.math.Interpolation.swingOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation.Swing;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cryotrax.game.ex01JSONSettingsLoader.json_settings;

public class ex01MenuScreenMenu {
	public static final int RESULT_OK = 1;
	public static final float SPEED_MENU = 3f;
    public static final float width =
			ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f;
    public static final float height =
			ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f * 278f/430f;
    public static final float FADE_IN_BASE = 4f;
	public String str;
	public int result;
	public ImageButton new_game_base;
	public ImageButton settings_base;
	public ImageButton scores_base;
	public ImageButton help_base;
	public ImageButton exit_base;
	public ImageButton new_game_overlayer;
	public ImageButton settings_overlayer;
	public ImageButton scores_overlayer;
	public ImageButton help_overlayer;	
	public ImageButton exit_overlayer;		
	public ImageButton new_game_text;
	public ImageButton settings_text;
	public ImageButton scores_text;
	public ImageButton help_text;
	public ImageButton add_free_version;
	public ImageButton.ImageButtonStyle styleon;
	public ImageButton.ImageButtonStyle styleoff;
	public TextureRegion backgroundTexR;
	public TextureRegion button_base_reg;
	public TextureRegion button_overlayer_reg;
	public TextureRegion new_game_text_reg;
	public TextureRegion settings_text_reg;
	public TextureRegion scores_text_reg;
	public TextureRegion help_text_reg;
	public TextureRegion exit_text_reg;
	public TextureRegion add_free_reg;
	public TextureRegion add_free_regOff;
	public TextureRegion cryo_image_tr;
	public TextureAtlas texture_atlas;
	public Table table;
	public Table table_menu;
	public Table table_menu_overlayer;
	public Table table_menu_overlayer_text;
	public Table add_free_version_table;
	public Table cryo_image_table;
    public Image backgroundImg;
	public Image cryo_image;
	public Stage stage;	
	public ex01MenuScreen base_menu;
	public ex01MenuLevelSelector menu_init;
	public InputMultiplexer multiplexer = new InputMultiplexer();
	public boolean still_working_on_menu_press = false;
	public boolean can_switchstyles = false;
	public boolean can_render_unlock = false;
	public boolean can_render_unlock_buy_only_load = false;
	public boolean can_render_loadsave = false;
	public Table unlock_free_version_table;
	public TextureRegion unlock_free_reg;
	public ImageButton unlock_free_version;
	public TimeUtils timerForAdd;
	public long timestamptime;
	public long timestamptime_render_load;
	public PurchaseCallback callback_function;
	//error and info stuff
	public Stack settings_get_premium_error;
	public Table get_premium_table_error;
	public Table level_base_premium_t_error;
	public TextButton level_base_premium_error;
	public TextButton.TextButtonStyle textb1;
	public TextButton.TextButtonStyle textb2ok;
	public Table level_base_premium_you_own_t_error;
	public Image level_base_premium_you_own_error;
	public Image level_base_premium_you_own_info;
	public boolean still_working_button_standard = false;
	public boolean still_working_on_done_press = false;
	//load saved game stuff
	public Table savegame_free_version_table;
	public TextureRegion savegame_free_reg;
	public ImageButton savegame_free_version;
	//login/logout stuff
	public Table loginlogout_free_version_table;
	public ImageButton loginlogout_free_version;
	public TextureRegion loginlogout_free_reg;
	public TextureRegion loginlogout_free_regOff;
	public ImageButton.ImageButtonStyle styleonz;
	public ImageButton.ImageButtonStyle styleoffz;
	//other
	public boolean bIsLoggedIn
			= false; // flag tells us when we are logged in and the login button is set accord.
	public boolean bCurrentlyFadedOutDontDraw
			= false; // used in RenderExtra to delay fadeIns and to stop button clicks until end
	public boolean bJustWantedToLogout
			= false; // used in setSignInResult to check if we have signIn or signOut
	public boolean stillWorkingOnReset
			= false; // used in TransitError to block change to login button texture until end
	public boolean about_to_finish_reset
			= false; // in TransitErrorFinish to finish Reset procedure and reset variables
	public boolean possible_fade_in_add_free
			= false; // used so we process buttons on login, reset, and click new game too early
	public boolean possible_fade_in_unlock_free
			= false; // used so we process buttons on login, reset, and click new game too early
	public boolean possible_fade_in_saveload_free
			= false; // used so we process buttons on login, reset, and click new game too early
	public boolean bNeedToChangeLoginButton
			= false; // change login button texture but when we need
	public boolean bWhatValueLogin
			= false; // is next login -> LOGIN or LOGOUT
	public boolean currently_rendering_load
			= false; // used to display both unlock and load saved game 2 sec apart
	public boolean currently_rendering_load_reset
			= false; // flag for the above so we know which we draw (loar or unlock)
	public boolean diffuse;

	public ex01MenuScreenMenu(TextureAtlas atlas,
							  Skin skin,
							  ex01MenuScreen bmenu,
							  Viewport viewport) {
		base_menu = bmenu;
		callback_function = bmenu;
		texture_atlas = atlas;
		stage = new Stage(viewport);
    	MenuScreenInitBack();
    	MenuScreenInitBase();
    	MenuScreenInitOverlayer();
    	MenuScreenInitOverlayerText();
		InitPremiumError(atlas, skin);
		table_menu_overlayer.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH/168.5f);
		table_menu_overlayer_text.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH/200f);
	}

	public void InitUnlock(){
		if(unlock_free_version_table==null)
			InitUnlockAllLevels();
		possible_fade_in_unlock_free = true;
		can_render_unlock = true;
		if(!bIsLoggedIn){
			can_render_unlock = false;
		}
	}

	public void CheckWithGoogleSoWeDrawCorrectly(){
		timestamptime_render_load = timerForAdd.millis();
		if(!base_menu.settings.settings.is_this_premium_updated){
			if(add_free_version_table==null) InitNotAddFreeVersion();
			possible_fade_in_add_free = true;
			timerForAdd = new TimeUtils();
			timestamptime = timerForAdd.millis();
			can_switchstyles = true;
		} else {
			if(!base_menu.settings.settings.is_this_unlocked_all_levels){
				InitUnlock();
				//only when we're premium do we need to save (bad luck!)
				if(base_menu.settings.settings.is_this_in_need_for_load_saved){
					if(savegame_free_version_table==null) InitLoadGameFromCloud();
					possible_fade_in_saveload_free = true;
					can_render_loadsave = true;
					base_menu.is_still_rolling_reset_premium_unlocked_duplex = false;
				} else {
					base_menu.is_still_rolling_reset_premium_unlocked = false;
				}
			} else {
				if(base_menu.settings.settings.is_this_in_need_for_load_saved){
					if(savegame_free_version_table==null) InitLoadGameFromCloud();
					possible_fade_in_saveload_free = true;
					can_render_loadsave = true;
					base_menu.is_still_rolling_reset_premium_unlocked_duplex = false;
				} else {
					base_menu.is_still_rolling_reset_premium_unlocked = false;
				}
			}
		}
		if(loginlogout_free_version_table==null)
			InitLoginLogut();
	}

	public void CheckPossibleInNeedToFadeOut(){
		if(!base_menu.settings.settings.is_this_premium_updated){
			possible_fade_in_add_free = true;
		} else {
			if(!base_menu.settings.settings.is_this_unlocked_all_levels){
				possible_fade_in_unlock_free = true;
			}
			if(base_menu.settings.settings.is_this_in_need_for_load_saved){
				possible_fade_in_saveload_free = true;
			}
		}
	}

	public void LogoutUnlockButton(){
		can_render_unlock = false;
		if(base_menu.cryo_game.game_screen!=null){
			base_menu.cryo_game.game_screen.hud_display.can_render_unlock = false;
		}
	}

	public void LoginUnlockButton(){
		can_render_unlock = true;
		if(base_menu.cryo_game.game_screen!=null){
			base_menu.cryo_game.game_screen.hud_display.can_render_unlock = true;
		}
	}

	public void LogoutLoadButton(){
		can_render_loadsave = false;
		if(base_menu.cryo_game.game_screen!=null){
			base_menu.cryo_game.game_screen.hud_display.can_render_loadsave = false;
		}
	}

	public void LoginLoadButton(){
		can_render_loadsave = true;
		if(base_menu.cryo_game.game_screen!=null){
			base_menu.cryo_game.game_screen.hud_display.can_render_loadsave = true;
		}
	}

	public void InitLoginLogut(){
		loginlogout_free_version_table = new Table();
		styleonz = new ImageButton.ImageButtonStyle();
		styleoffz = new ImageButton.ImageButtonStyle();
		loginlogout_free_reg = texture_atlas.findRegion("loginout5ine");
		loginlogout_free_regOff = texture_atlas.findRegion("loginout5oute");
		styleonz.up = new TextureRegionDrawable(new
				TextureRegionDrawable(loginlogout_free_reg));
		styleoffz.up = new TextureRegionDrawable(new
				TextureRegionDrawable(loginlogout_free_regOff));
		loginlogout_free_version = new ImageButton(styleonz);
		loginlogout_free_version_table
				.add(loginlogout_free_version)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.95f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.95f * 106f / 256f)
				.padLeft(-ex01Types.VIRTUAL_SCREEN_WIDTH / 300f);
		stage.addActor(loginlogout_free_version_table);
		loginlogout_free_version_table.setFillParent(true);
		loginlogout_free_version_table
				.bottom()
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 21.45f);
		loginlogout_free_version.pack();
		loginlogout_free_version_table.pack();
		loginlogout_free_version_table.setTransform(true);
		loginlogout_free_version_table.setOrigin(
				loginlogout_free_version_table.getWidth() / 2,
				loginlogout_free_version_table.getHeight() / 2);
		loginlogout_free_version.setOrigin(loginlogout_free_version.getWidth() / 2,
										   loginlogout_free_version.getHeight() / 2);
		loginlogout_free_version_table.addAction(Actions.fadeOut(0f));
		loginlogout_free_version_table.act(Gdx.graphics.getDeltaTime());

		loginlogout_free_version.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!still_working_on_menu_press) {
					still_working_on_menu_press = true;
					if(base_menu.settings.settings.sounds_on)
						base_menu.basic_button.play(SND_VOL.SOUND_MENU_BASIC);
					loginlogout_free_version.addAction(sequence(
						moveBy(0f, -12f, 0.2f),
						moveBy(0f, 12f, 0.3f),
						run(new Runnable() {
							public void run() {
								if (bIsLoggedIn) {
									bCurrentlyFadedOutDontDraw = true;
									bJustWantedToLogout = true;
									CheckPossibleInNeedToFadeOut();
									base_menu.PerfromGoogleCloudSignOUT();
								} else {
									bCurrentlyFadedOutDontDraw = true;
									bJustWantedToLogout = false;
									currently_rendering_load_reset = false;
									CheckPossibleInNeedToFadeOut();
									base_menu.settings.settings
											.user_doesnt_want_signed_in = false;
									base_menu.settings.WriteJson();
									base_menu.PerfromGoogleCloudSignIN();
								}
							}
					})));
				}
			}
		});
	}

	public void InitPremiumError(TextureAtlas atlas, Skin skin){
			//INIT the owned tables
			get_premium_table_error = new Table();
			textb1 = new TextButton.TextButtonStyle();
			textb2ok = new TextButton.TextButtonStyle();
			TextureRegion style1 = skin.getRegion("money");
			TextureRegion style2 = skin.getRegion("moneyok");
			textb1.font = skin.getFont("errors-font");
			textb1.up = new TextureRegionDrawable(style1);
			textb2ok.font = skin.getFont("errors-font");
			textb2ok.up = new TextureRegionDrawable(style2);
			//this stack contains the owned stuff
			settings_get_premium_error = new Stack();
			get_premium_table_error
					.add(settings_get_premium_error)
					.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f)
					.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f * 85f / 384f);
			get_premium_table_error.setFillParent(true);
			get_premium_table_error.setTransform(true);
			get_premium_table_error
					.bottom()
					.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/7.5f);
			get_premium_table_error.addAction(moveBy(0f,-25f,0f));
			level_base_premium_t_error = new Table();
			level_base_premium_t_error.setTransform(true);
			level_base_premium_error = new TextButton("tare", textb1);
			level_base_premium_t_error.setFillParent(true);
			level_base_premium_t_error
					.add(level_base_premium_error)
					.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.15f)
					.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.15f * 68f / 400f);
			level_base_premium_t_error.bottom();
			settings_get_premium_error.add(level_base_premium_t_error);
			level_base_premium_you_own_t_error = new Table();
			level_base_premium_you_own_t_error.setFillParent(true);
			level_base_premium_you_own_error = new Image(new
					TextureRegionDrawable(atlas.findRegion("error")));
			level_base_premium_you_own_t_error
					.add(level_base_premium_you_own_error)
					.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f)
					.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f * 80f / 256f)
					.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 50f);
			level_base_premium_you_own_t_error
					.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/6.55f);
			settings_get_premium_error.add(level_base_premium_you_own_t_error);
			level_base_premium_you_own_error.addAction(Actions.alpha(0f));
			level_base_premium_error.addAction(Actions.alpha(0f));
			stage.addActor(get_premium_table_error);
	}

	public void TransitError(String string,
							 boolean error,
							 boolean need_tochange_login_button,
							 boolean what_value_login){
		str = string;
		if(!stillWorkingOnReset) {
			bNeedToChangeLoginButton = need_tochange_login_button;
			bWhatValueLogin = what_value_login;
		}
		diffuse = error;
		get_premium_table_error.clearActions();
		level_base_premium_error.getLabel().setColor(Color.GREEN);
		level_base_premium_error.getLabel().setText(string);
		level_base_premium_error.setTransform(true);
		level_base_premium_error.setOrigin(Align.center);
		level_base_premium_you_own_error.setOrigin(Align.center);
		level_base_premium_error.clearActions();
		level_base_premium_you_own_error.clearActions();
		level_base_premium_error.addAction(parallel(
				Actions.fadeIn(1.0f),
				Actions.scaleTo(1.3f, 1.3f, 1.0f),
				Actions.sequence(Actions.moveBy(0f, 12f, 0.3f),
					Actions.rotateBy(360f, 2.4f, Swing.circleOut),
					Actions.moveBy(0f, -12f, 0.3f),
					Actions.run(new Runnable() {
						public void run() {
							level_base_premium_error.addAction(parallel(
								Actions.scaleTo(1.0f, 1.0f, 1.0f),
								Actions.sequence(Actions.fadeOut(6.0f),
									Actions.run(new Runnable() {
										public void run() {
											still_working_button_standard = false;
											if (diffuse) {
												if(str==ex01Types.RESET_PREMIUM ||
												   str==ex01Types.RESET_PREMIUM_UNLOCKED){
													stillWorkingOnReset = false;
													about_to_finish_reset = true;
												}
												TransitErrorFinish();
											}
										}
							}))));
						}
		}))));
		level_base_premium_you_own_error.addAction(parallel(
				Actions.fadeIn(1.0f),
				Actions.scaleTo(1.3f, 1.3f, 1.0f),
				Actions.sequence(Actions.moveBy(0f, 12f, 0.3f),
						Actions.rotateBy(360f, 2.4f, Swing.circleOut),
						Actions.moveBy(0f, -12f, 0.3f),
						Actions.run(new Runnable() {
							public void run() {
								level_base_premium_you_own_error.addAction(parallel(
										Actions.scaleTo(1.0f, 1.0f, 1.0f),
										Actions.fadeOut(6.0f)));
							}
		}))));
	}

	public void TransitErrorFinish(){
		still_working_on_menu_press = false;
		base_menu.is_still_rolling_reset_premium_unlocked = false;
		base_menu.is_still_rolling_reset_premium_unlocked_duplex = false;

		if (bCurrentlyFadedOutDontDraw) {
			if (!base_menu.already_loggedin_at_startup_for_transit) { // game start 1st pass
				CheckForResetAndActAccordingly();
				base_menu.already_loggedin_at_startup_for_transit = true;
			} else { // login click or second TransitError pass from game start
				CheckForResetAndActAccordingly();
				if(base_menu.isSomethingWrong){
					if(about_to_finish_reset){
						bCurrentlyFadedOutDontDraw = false;
						stillWorkingOnReset = false;
						about_to_finish_reset = false;
						base_menu.isSomethingWrong = false;
						base_menu.ChangeLoginLogout(ex01Types.LOGIN_NEXT_YES); // amanat mai sus
					}
				} else {
					bCurrentlyFadedOutDontDraw = false;
				}
				if(!bCurrentlyFadedOutDontDraw)
					FadeInAllThatWereFadedOut();
			}
			if(!bCurrentlyFadedOutDontDraw)
				FadeInAllThatWereFadedOut();

			if(add_free_version_table!=null)
				add_free_version_table.addAction(Actions.fadeIn(1f));
		}
	}

	public void CheckForResetAndActAccordingly(){
//FREE  base_menu.CheckInAppData(); // ActAsIfWeJustPurchasedPremium
//FREE  CheckWithGoogleSoWeDrawCorrectly();
//FREE  if (bNeedToChangeLoginButton) {
//FREE  	base_menu.ChangeLoginLogout(bWhatValueLogin);
//FREE  }
	}

	// makes all unlock/ad/load/buttons dissapear for error or success display
	public void FadeOutAllBeforeErrorFinished(){
		if(possible_fade_in_add_free){
			if(add_free_version_table!=null) {
				add_free_version_table.addAction(Actions.fadeOut(0f));
			}
		}
		if(possible_fade_in_unlock_free){
			if(unlock_free_version_table!=null) {
				unlock_free_version_table.addAction(Actions.fadeOut(0f));
			}
		}
		if(possible_fade_in_saveload_free){
			if(savegame_free_version_table!=null) {
				savegame_free_version_table.addAction(Actions.fadeOut(0f));
			}
		}
		if (loginlogout_free_version_table!=null )
			loginlogout_free_version_table.addAction(Actions.fadeOut(0f));
	}

	public void FadeOutLoginBeforeErrorFinished(){
		loginlogout_free_version_table.addAction(Actions.fadeOut(1f));
	}

	public void FadeInAllThatWereFadedOut(){
		if(loginlogout_free_version_table != null)
			loginlogout_free_version_table.addAction(Actions.fadeIn(1f));
	}
	
	public void InitLoadGameFromCloud(){
		savegame_free_version_table = new Table();
		savegame_free_reg = texture_atlas.findRegion("saved_game3");
		ImageButton.ImageButtonStyle ibs_savegame_free = new
				ImageButton.ImageButtonStyle();
		ibs_savegame_free.up = new TextureRegionDrawable(new
				TextureRegionDrawable(savegame_free_reg));
		savegame_free_version = new ImageButton(ibs_savegame_free);
		savegame_free_version_table.add(savegame_free_version)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.35f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.35f * 105f / 256f);
		stage.addActor(savegame_free_version_table);
		savegame_free_version_table.setFillParent(true);
		savegame_free_version_table
				.bottom()
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 6.45f);
		savegame_free_version_table.addAction(fadeOut(0f));
		savegame_free_version.pack();
		savegame_free_version_table.pack();
		savegame_free_version.setTransform(true);
		savegame_free_version_table.setOrigin(
				savegame_free_version_table.getWidth() / 2,
				savegame_free_version_table.getHeight() / 2);
		savegame_free_version.setOrigin(savegame_free_version.getWidth() / 2,
				savegame_free_version.getHeight() / 2);
		savegame_free_version_table.addAction(Actions.fadeOut(0f));
		savegame_free_version_table.act(Gdx.graphics.getDeltaTime());
		savegame_free_version.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!still_working_on_menu_press && bIsLoggedIn) {
					still_working_on_menu_press = true;
					if(base_menu.settings.settings.sounds_on)
						base_menu.basic_button.play(SND_VOL.SOUND_MENU_BASIC);
					savegame_free_version.addAction(sequence(
						moveBy(0f, 12f, 0.2f),
						moveBy(0f, -12f, 0.3f),
						run(new Runnable() {
							public void run() {
								still_working_on_menu_press = false;
								/*
								base_menu.cryo_game.inappInterfaceResolver
										.loadGameSaveCommandComingFromCore(base_menu);
								*/
							}
					})));
				}
			}
		});		
	}

	public void InitUnlockAllLevels(){
		unlock_free_version_table = new Table();
		unlock_free_reg = texture_atlas.findRegion("unlocklevels");
		ImageButton.ImageButtonStyle ibs_unlock_free =
				new ImageButton.ImageButtonStyle();
		ibs_unlock_free.up = new TextureRegionDrawable(
				new TextureRegionDrawable(unlock_free_reg));
		unlock_free_version = new ImageButton(ibs_unlock_free);
		unlock_free_version_table.add(unlock_free_version)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.35f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.35f * 105f / 256f);
		stage.addActor(unlock_free_version_table);
		unlock_free_version_table.setFillParent(true);
		unlock_free_version_table
				.bottom()
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 6.45f);
		unlock_free_version_table.addAction(fadeOut(0f));
		unlock_free_version.pack();
		unlock_free_version_table.pack();
		unlock_free_version.setTransform(true);
		unlock_free_version_table.setOrigin(
				unlock_free_version_table.getWidth() / 2,
				unlock_free_version_table.getHeight() / 2);
		unlock_free_version.setOrigin(
				unlock_free_version.getWidth() / 2,
				unlock_free_version.getHeight() / 2);
		unlock_free_version.toFront();
		unlock_free_version_table.addAction(Actions.fadeOut(0f));
		unlock_free_version_table.act(Gdx.graphics.getDeltaTime());
		unlock_free_version.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!still_working_on_menu_press && bIsLoggedIn) {
					still_working_on_menu_press = true;
					if(base_menu.settings.settings.sounds_on)
						base_menu.basic_button.play(SND_VOL.SOUND_MENU_BASIC);
					unlock_free_version.addAction(sequence(
						moveBy(0f, 12f, 0.2f),
						moveBy(0f, -12f, 0.3f),
						run(new Runnable() {
							public void run() {
								base_menu.is_still_rolling_reset_premium_unlocked = true;
								result = base_menu.cryo_game.inappInterfaceResolver
										.requestIabPurchase(
												IActionResolver.PRODUCT_UNLOCK_LEVELS,
												callback_function);
								if(result==RESULT_OK){
								} else { // something went wrong
								}
								bCurrentlyFadedOutDontDraw = true;
								FadeOutLoginBeforeErrorFinished();
							}
					})));
				}
			}
		});	
	}

	public void SwitchStyleForBuyPremium(){
		if(add_free_version!=null) {
			if (add_free_version.getStyle() == styleon) {
				add_free_version.setStyle(styleoff);
			} else {
				add_free_version.setStyle(styleon);
			}
		}
	}

	public void InitNotAddFreeVersion(){
		add_free_version_table = new Table();
		styleon = new ImageButton.ImageButtonStyle();
		styleoff = new ImageButton.ImageButtonStyle();
		add_free_reg = texture_atlas.findRegion("getpremiumingameon");
		add_free_regOff = texture_atlas.findRegion("getpremiumingameoff");
		styleon.up = new TextureRegionDrawable(new
				TextureRegionDrawable(add_free_reg));
		styleoff.up = new TextureRegionDrawable(new
				TextureRegionDrawable(add_free_regOff));
		add_free_version = new ImageButton(styleon);
		add_free_version_table.add(add_free_version)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.35f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.35f * 105f / 256f);
		stage.addActor(add_free_version_table);
		add_free_version_table.setFillParent(true);
		add_free_version_table
				.bottom()
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 6.45f);
		add_free_version.pack();
		add_free_version_table.pack();
		add_free_version_table.setTransform(true);
		add_free_version_table.setOrigin(
				add_free_version_table.getWidth() / 2,
				add_free_version_table.getHeight() / 2);
		add_free_version_table.addAction(Actions.fadeOut(0f));
		add_free_version_table.act(Gdx.graphics.getDeltaTime());
		add_free_version.setOrigin(add_free_version.getWidth() / 2,
				add_free_version.getHeight() / 2);

		add_free_version.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!still_working_on_menu_press) {
					still_working_on_menu_press = true;
					if (base_menu.settings.settings.sounds_on)
						base_menu.basic_button.play(SND_VOL.SOUND_MENU_BASIC);
					add_free_version.addAction(sequence(
							moveBy(0f, 12f, 0.2f),
							moveBy(0f, -12f, 0.3f),
							run(new Runnable() {
								public void run() {
									result = base_menu.cryo_game.inappInterfaceResolver
											.requestIabPurchase(
													IActionResolver.PRODUCT_PREMIUM,
													callback_function);
									if (result == RESULT_OK) {
									} else { // something went wrong
									}
									bCurrentlyFadedOutDontDraw = true;
									FadeOutLoginBeforeErrorFinished();
								}
							})));
				}
			}
		});
	}

    //background image
    public void MenuScreenInitBack() {
		backgroundTexR = texture_atlas.findRegion("back");
		backgroundImg = new Image(new
				TextureRegionDrawable(backgroundTexR));
		table = new Table();
		table.row();
		table.add(backgroundImg)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH)
				.height(ex01Types.VIRTUAL_SCREEN_HEIGHT);
		table.setFillParent(true);	
		stage.addActor(table);
		stage.addAction(fadeIn(2f));
		cryo_image_table = new Table();
		cryo_image_tr = texture_atlas.findRegion("cryotrax");
		cryo_image = new Image(new TextureRegionDrawable(cryo_image_tr));
		cryo_image.addAction(Actions.alpha(0.85f));
		cryo_image_table.add(cryo_image)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.65f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.65f * 280f / 420f)
				.top()
				.padTop(-ex01Types.VIRTUAL_SCREEN_WIDTH / 1.210f);
		stage.addActor(cryo_image_table);
		cryo_image_table.setFillParent(true);
    }    
    
    //menu base - grey under-cover buttons
    public void MenuScreenInitBase() {
		button_base_reg = texture_atlas.findRegion("button_baser");
		ImageButton.ImageButtonStyle ibs = new
				ImageButton.ImageButtonStyle();
		ibs.up = new TextureRegionDrawable(new
				TextureRegionDrawable(button_base_reg));
		new_game_base = new ImageButton(ibs);
		settings_base = new ImageButton(ibs);
		scores_base = new ImageButton(ibs);
		help_base = new ImageButton(ibs);
		exit_base = new ImageButton(ibs);
		table_menu = new Table();
		table_menu.row();
		Color color = new Color(1f, 1f, 1f, 0.1f);

		new_game_base.setColor(color);
		new_game_base.addAction(fadeIn(FADE_IN_BASE));
		table_menu.add(new_game_base)
				.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 70)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.90f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.90f * 86 / 250);
		table_menu.row();
		settings_base.addAction(fadeIn(FADE_IN_BASE));
		settings_base.setColor(color);
		table_menu.add(settings_base)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/1.90f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.90f * 86 / 250);
		table_menu.row();
		scores_base.addAction(fadeIn(FADE_IN_BASE));
		scores_base.setColor(color);
		table_menu.add(scores_base)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/1.90f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.90f * 86 / 250);
		table_menu.row();
		help_base.addAction(fadeIn(FADE_IN_BASE));
		help_base.setColor(color);
		table_menu.add(help_base)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/1.90f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.90f * 86 / 250);
		table_menu.row();
		exit_base.addAction(fadeIn(FADE_IN_BASE));		
		exit_base.setColor(color);
		table_menu.add(exit_base)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/1.90f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.90f * 86 / 250);
		table_menu.setFillParent(true);
		table_menu.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 50);
		table_menu.pack();
		stage.addActor(table_menu);
    }      
    
    //blue greenish over-layers
    public void MenuScreenInitOverlayer() {
    	button_overlayer_reg = texture_atlas.findRegion("button_overlayer");
		ImageButton.ImageButtonStyle ibs = new
				ImageButton.ImageButtonStyle();
		ibs.up = new TextureRegionDrawable(new
				TextureRegionDrawable(button_overlayer_reg));
    	new_game_overlayer = new ImageButton(ibs);
    	settings_overlayer = new ImageButton(ibs);
    	scores_overlayer = new ImageButton(ibs);
    	help_overlayer = new ImageButton(ibs);
    	exit_overlayer = new ImageButton(ibs);
		table_menu_overlayer = new Table();
		table_menu_overlayer.row();
		table_menu_overlayer.add(new_game_overlayer)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/1.90f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.90f * 86 / 250);
		table_menu_overlayer.row();
		table_menu_overlayer.add(settings_overlayer)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/1.90f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.90f * 86 / 250);
		table_menu_overlayer.row();
		table_menu_overlayer.add(scores_overlayer)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.90f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.90f * 86 / 250);
		table_menu_overlayer.row();
		table_menu_overlayer.add(help_overlayer)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/1.90f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.90f * 86 / 250);
		table_menu_overlayer.row();
		table_menu_overlayer.add(exit_overlayer)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/1.90f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.90f * 86 / 250);
		table_menu_overlayer.setFillParent(true);
		table_menu_overlayer.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 50);
		table_menu_overlayer.pack();
		stage.addActor(table_menu_overlayer);    		
    }  

	public boolean bAlreadyPressedNewGame = false;

    public void MenuScreenInitOverlayerText() {
    	new_game_text_reg = texture_atlas.findRegion("button_newgamer");
    	settings_text_reg = texture_atlas.findRegion("button_settings");
    	scores_text_reg = texture_atlas.findRegion("button_scores");
    	help_text_reg = texture_atlas.findRegion("button_help");
    	exit_text_reg = texture_atlas.findRegion("button_exit");
		ImageButton.ImageButtonStyle ibs_new_gamer = new
				ImageButton.ImageButtonStyle();
		ibs_new_gamer.up = new TextureRegionDrawable(new
				TextureRegionDrawable(new_game_text_reg));
		new_game_text = new ImageButton(ibs_new_gamer);
		ImageButton.ImageButtonStyle ibs_settings = new
				ImageButton.ImageButtonStyle();
		ibs_settings.up = new TextureRegionDrawable(new
				TextureRegionDrawable(settings_text_reg));
		settings_text = new ImageButton(ibs_settings);
		ImageButton.ImageButtonStyle ibs_scores = new
				ImageButton.ImageButtonStyle();
		ibs_scores.up = new TextureRegionDrawable(new
				TextureRegionDrawable(scores_text_reg));
		scores_text = new ImageButton(ibs_scores);
		ImageButton.ImageButtonStyle ibs_help = new
				ImageButton.ImageButtonStyle();
		ibs_help.up = new TextureRegionDrawable(new
				TextureRegionDrawable(help_text_reg));
		help_text = new ImageButton(ibs_help);
		ImageButton.ImageButtonStyle ibs_exit = new
				ImageButton.ImageButtonStyle();
		ibs_exit.up = new TextureRegionDrawable(new
				TextureRegionDrawable(exit_text_reg));
		base_menu.exit_dialog_screen.exit_text = new
				ImageButton(ibs_exit);
		table_menu_overlayer_text = new Table();
		table_menu_overlayer_text.row();
		new_game_text.setColor(new Color(1f, 1f, 1f, 0.70f));
		new_game_text.setTransform(true);
		table_menu_overlayer_text
				.add(new_game_text)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/ 1.90f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.90f * 86 / 250);
		table_menu_overlayer_text.row();
		settings_text.setColor(new Color(1f, 1f, 1f, 0.70f));
		settings_text.setTransform(true);
		table_menu_overlayer_text
				.add(settings_text)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/ 1.90f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.90f * 86 / 250);
		table_menu_overlayer_text.row();
		scores_text.setColor(new Color(1f, 1f, 1f, 0.70f));			
		scores_text.setTransform(true);
		table_menu_overlayer_text
				.add(scores_text)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/ 1.90f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.90f * 86 / 250);
		table_menu_overlayer_text.row();
		help_text.setColor(new Color(1f, 1f, 1f, 0.70f));
		help_text.setTransform(true);
		table_menu_overlayer_text
				.add(help_text)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/ 1.90f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.90f * 86 / 250);
		table_menu_overlayer_text.row();
		base_menu.exit_dialog_screen.exit_text.setColor(new Color(1f, 1f, 1f, 0.70f));
		base_menu.exit_dialog_screen.exit_text.setTransform(true);
		table_menu_overlayer_text
				.add(base_menu.exit_dialog_screen.exit_text)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/ 1.90f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.90f * 86 / 250);
		table_menu_overlayer_text.setFillParent(true);
		table_menu_overlayer_text.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 50);

		table_menu_overlayer_text.invalidate();
		table_menu_overlayer_text.pack();
		new_game_text.setOrigin(
				new_game_text.getWidth() / 2f,
				new_game_text.getHeight() / 2f);
		settings_text.setOrigin(
				settings_text.getWidth() / 2f,
				settings_text.getHeight() / 2f);
		scores_text.setOrigin(
				scores_text.getWidth() / 2f,
				scores_text.getHeight() / 2f);
		help_text.setOrigin(
				help_text.getWidth() / 2f,
				help_text.getHeight() / 2f);
		base_menu.exit_dialog_screen.exit_text.setOrigin(
				base_menu.exit_dialog_screen.exit_text.getWidth() / 2f,
				base_menu.exit_dialog_screen.exit_text.getHeight() / 2f);

		stage.addActor(table_menu_overlayer_text);
		menu_init = new ex01MenuLevelSelector(
				base_menu.settings,
				texture_atlas,
				base_menu.cryo_game);
		multiplexer.addProcessor(base_menu.cryo_game.levels_screen.stage_ranking);
		multiplexer.addProcessor(base_menu.cryo_game.levels_screen.stage);
		new_game_text.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!still_working_on_menu_press && base_menu.finished_transition &&
						(!bCurrentlyFadedOutDontDraw || Gdx.app.getType() ==
								Application.ApplicationType.Desktop)){

					base_menu.menus.still_working_on_menu_press = false;
					bAlreadyPressedNewGame = true;
					if(base_menu.settings.settings.is_this_premium_updated){
						StartProcedureOnNoADS();
					} else {
						if(base_menu.settings.settings.new_game_adview_delay_x_counter>=
						   base_menu.settings.settings.NEW_GAME_ADVIEW_DELAY_X_TIMES_STARTER) {
							if (!menu_init.settings_for_data.settings.is_this_for_free) {
								ROTATOR_RESET_ExitLevelSoResetErrorInfoHUD();
							}
							ProcessNeedToGoVALUE();
						} else {
							base_menu.settings.settings.new_game_adview_delay_x_counter++;
							base_menu.settings.WriteJson();
							StartProcedureOnNoADS();
						}
					}
				} else {
					new_game_base.addAction(sequence(
							moveBy(0f, -18f, 0.3f),
							moveBy(0f, 18f, 0.3f)));
				}
			}
		});	
		
		settings_text.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!still_working_on_menu_press && base_menu.finished_transition &&
						(!bCurrentlyFadedOutDontDraw || Gdx.app.getType() ==
								Application.ApplicationType.Desktop)){

					still_working_on_menu_press = true;
					FadeOUT_ExtraButtons();
					if(base_menu.settings.settings.sounds_on)
						base_menu.menu_selector_button.play(SND_VOL.SOUND_MENU_SELECTOR);
					FadeOutMenuButtons(1.5f);
					settings_base.addAction(sequence(
						moveBy(0f, -18f, 0.1f),
						moveBy(0f, 18f, 0.1f)));
					settings_overlayer.addAction(sequence(
						moveBy(0f, 12f, 0.2f),
						moveBy(0f, -12f, 0.3f),
						run(new Runnable() {
							public void run() {
								base_menu.settings_dialog_screen.settings_dialog
										.addAction(parallel(
												fadeIn(1.25f),
												moveBy(0f,50f,1.25f,swingOut)));
								base_menu.grayscale_shader_active_settings = true;
								stage.getBatch().setShader(base_menu.shader_grayscale);
								Gdx.input.setInputProcessor(base_menu
										.settings_dialog_screen.stage_settings_dialog);
								still_working_on_menu_press = false;
							}
					})));					
					settings_text.addAction(parallel(
						Actions.fadeIn(0.3f),
						Actions.scaleTo(1.2f, 1.2f, 0.3f),
						Actions.sequence(moveBy(0f, 15f, 0.3f),
								moveBy(0f, -15f, 0.15f, Swing.swing))));
				} else {
					settings_base.addAction(sequence(
							moveBy(0f, -18f, 0.3f),
							moveBy(0f, 18f, 0.3f)));
				}
			}
		});	
		
		scores_text.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!still_working_on_menu_press && base_menu.finished_transition &&
						(!bCurrentlyFadedOutDontDraw || Gdx.app.getType() ==
								Application.ApplicationType.Desktop)){

					if(!base_menu.settings.settings.is_this_for_free) {
						base_menu.scores_dialog_screen
								.GetScoreRankGeneral();
					} else {
						base_menu.scores_dialog_screen.ranking_top
								.setText(ex01Types.PREMIUM_ERROR);
					}
					still_working_on_menu_press = true;
					FadeOUT_ExtraButtons();
					if(base_menu.settings.settings.sounds_on)
						base_menu.menu_selector_button.play(SND_VOL.SOUND_MENU_SELECTOR);
					FadeOutMenuButtons(1.5f);
					scores_base.addAction(sequence(
						moveBy(0f, -18f, 0.1f),
						moveBy(0f, 18f, 0.1f)));
					scores_overlayer.addAction(sequence(
						moveBy(0f, 12f, 0.2f),
						moveBy(0f, -12f, 0.3f),
						run(new Runnable() {
							public void run() {
								base_menu.scores_dialog_screen.
										ProcessMaxAndTriesGetFromSettings();
								base_menu.scores_dialog_screen.scores_dialog
										.addAction(parallel(
												fadeIn(1.25f),
												moveBy(0f,50f,1.25f,swingOut)));
								base_menu.grayscale_shader_active_scores = true;
								stage.getBatch().setShader(base_menu.shader_grayscale);
								Gdx.input.setInputProcessor(base_menu
										.scores_dialog_screen.stage_scores_dialog);
								still_working_on_menu_press = false;
							}
					})));					
					scores_text.addAction(parallel(
						Actions.fadeIn(0.3f),
						Actions.scaleTo(1.2f, 1.2f, 0.3f),
						Actions.sequence(moveBy(0f, 15f, 0.3f),
								moveBy(0f, -15f, 0.15f, Swing.swing))));
				} else {
					scores_base.addAction(sequence(
							moveBy(0f, -18f, 0.3f),
							moveBy(0f, 18f, 0.3f)));
				}
			}
		});	
		
		help_text.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!still_working_on_menu_press && base_menu.finished_transition &&
						(!bCurrentlyFadedOutDontDraw || Gdx.app.getType() ==
								Application.ApplicationType.Desktop)){

					still_working_on_menu_press = true;
					FadeOUT_ExtraButtons();
					if(base_menu.settings.settings.sounds_on)
						base_menu.menu_selector_button.play(SND_VOL.SOUND_MENU_SELECTOR);
					FadeOutMenuButtons(1.5f);
					base_menu.help_screen.help_done.addAction(fadeIn(0.55f));
					help_base.addAction(sequence(
						moveBy(0f, -18f, 0.1f),
						moveBy(0f, 18f, 0.1f)));
					help_overlayer.addAction(sequence(
						moveBy(0f, 12f, 0.2f),
						moveBy(0f, -12f, 0.3f),
						run(new Runnable(){
							public void run(){
								base_menu.help_screen.table_left_right.addAction(parallel(
										Actions.fadeIn(1.25f),
										Actions.moveBy(0f,50f,1.25f,swingOut)));
								base_menu.help_screen.image1.addAction(parallel(
										Actions.fadeIn(1.25f),
										Actions.moveBy(0f,50f,1.25f,swingOut)));
								base_menu.help_screen.image2.addAction(parallel(
										Actions.fadeIn(1.25f),
										Actions.moveBy(0f, 50f, 1.25f, swingOut)));
								base_menu.help_screen.image3.addAction(parallel(
										Actions.fadeIn(1.25f),
										Actions.moveBy(0f,50f,1.25f,swingOut)));
								base_menu.grayscale_shader_active_help = true;
								stage.getBatch().setShader(base_menu.shader_grayscale);
								Gdx.input.setInputProcessor(base_menu.help_screen.stage_help);
								still_working_on_menu_press = false;
							}
					})));					
					help_text.addAction(parallel(
						Actions.fadeIn(0.3f),
						Actions.scaleTo(1.2f, 1.2f, 0.3f),
						Actions.sequence(moveBy(0f, 15f, 0.3f),
								moveBy(0f, -15f, 0.15f, Swing.swing))));
				} else {
					help_base.addAction(sequence(
							moveBy(0f, -18f, 0.3f),
							moveBy(0f, 18f, 0.3f)));
				}
			}
		});
		
		base_menu.exit_dialog_screen.exit_text.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!still_working_on_menu_press && base_menu.finished_transition &&
				 (!bCurrentlyFadedOutDontDraw || Gdx.app.getType() ==
						 Application.ApplicationType.Desktop)){

					still_working_on_menu_press = true;
					FadeOUT_ExtraButtons();
					if(base_menu.settings.settings.sounds_on)
						base_menu.menu_selector_button.play(SND_VOL.SOUND_MENU_SELECTOR);
					exit_base.addAction(sequence(
						moveBy(0f, -18f, 0.1f),
						moveBy(0f, 18f, 0.1f)));
					exit_overlayer.addAction(sequence(
						moveBy(0f, 12f, 0.2f),
						moveBy(0f, -12f, 0.3f),
						run(new Runnable(){
							public void run() {
								FadeOutMenuButtons(1.5f);
								base_menu.exit_dialog_screen.exit_dialog
										.addAction(parallel(
												Actions.fadeIn(1.25f),
												Actions.moveBy(0f,50f,1.25f,swingOut)));
								base_menu.grayscale_shader_active_exit = true;
								stage.getBatch().setShader(base_menu.shader_grayscale);
								Gdx.input.setInputProcessor(base_menu
										.exit_dialog_screen.stage_exit_dialog);
								still_working_on_menu_press = false;
							}
					})));					
					base_menu.exit_dialog_screen.exit_text.addAction(parallel(
						Actions.fadeIn(0.3f),
						Actions.scaleTo(1.2f, 1.2f, 0.3f),
						Actions.sequence(moveBy(0f, 15f, 0.3f),
								moveBy(0f, -15f, 0.15f, Swing.swing))));
				} else {
					exit_base.addAction(sequence(
							moveBy(0f, -18f, 0.3f),
							moveBy(0f, 18f, 0.3f)));
				}
			}
		});			
    }

	public void StartProcedureOnNoADS(){
		still_working_on_menu_press = true;
		if(base_menu.settings.settings.sounds_on)
			base_menu.menu_selector_button.play(SND_VOL.SOUND_MENU_SELECTOR);
		new_game_base.addAction(sequence(
				moveBy(0f, -18f, 0.1f),
				moveBy(0f, 18f, 0.1f)));
		new_game_overlayer.addAction(sequence(
				moveBy(0f, 12f, 0.2f),
				moveBy(0f, -12f, 0.3f),
				run(new Runnable(){
					public void run(){
						if (!menu_init.settings_for_data.settings.is_this_for_free) {
							ROTATOR_RESET_ExitLevelSoResetErrorInfoHUD();
						}
						if(base_menu.cryo_game.levels_screen==null){
							base_menu.cryo_game.setScreen(menu_init);
						} else {
							base_menu.cryo_game.setScreen(
									base_menu.cryo_game.levels_screen);
							base_menu.cryo_game.game_state =
									GameState.LEVELS_SCREEN;
							Gdx.input.setInputProcessor(multiplexer);
							still_working_on_menu_press = false;
						}
					}
				})));
		new_game_text.addAction(parallel(
				Actions.fadeIn(0.3f),
				Actions.scaleTo(1.2f, 1.2f, 0.3f),
				Actions.sequence(moveBy(0f, 15f, 0.3f),
						moveBy(0f, -15f, 0.2f, Swing.swing))));
	}

	public void FadeOUT_ExtraButtons(){
		if(loginlogout_free_version_table!=null)
			loginlogout_free_version_table.addAction(Actions.fadeOut(2f));

		if(!base_menu.settings.settings.is_this_premium_updated) {
			if (add_free_version != null) {
				add_free_version.addAction(Actions.fadeOut(2f));
			}
		}
		if(savegame_free_version!=null) {
			if (savegame_free_version != null) {
				savegame_free_version.addAction(Actions.fadeOut(2f));
			}
		}
		if(unlock_free_version!=null) {
			if (unlock_free_version != null) {
				unlock_free_version.addAction(Actions.fadeOut(2f));
			}
		}
	}

	public void ROTATOR_RESET_ExitLevelSoResetErrorInfoHUD(){
		level_base_premium_error
				.clearActions();
		level_base_premium_you_own_error
				.clearActions();
		level_base_premium_error
				.invalidateHierarchy();
		level_base_premium_you_own_error
				.invalidateHierarchy();
		level_base_premium_error
				.addAction(Actions.alpha(ex01Types.BASE_ALPHA_ZERO));
		level_base_premium_you_own_error
				.addAction(Actions.alpha(ex01Types.BASE_ALPHA_ZERO));
		level_base_premium_error
				.setRotation(ex01Types.BASE_ROTATION);
		level_base_premium_you_own_error.
				setRotation(ex01Types.BASE_ROTATION);

		ResetRollingAddUnlockVariables(); // ce era in TransitError la sfarsit
	}

	public void ResetRollingAddUnlockVariables(){
		base_menu.is_still_rolling_reset_premium_unlocked = false;
		base_menu.is_still_rolling_reset_premium_unlocked_duplex = false;
		bCurrentlyFadedOutDontDraw = false;

		if(!base_menu.already_loggedin_at_startup_for_transit) {
			base_menu.CheckInAppData();
			CheckWithGoogleSoWeDrawCorrectly();
			if(bNeedToChangeLoginButton){
				base_menu.ChangeLoginLogout(bWhatValueLogin);
			}
			base_menu.already_loggedin_at_startup_for_transit = true;
		} else {
			if(bNeedToChangeLoginButton){
				base_menu.ChangeLoginLogout(bWhatValueLogin);
			}
			FadeInAllThatWereFadedOut();
		}

		loginlogout_free_version_table.addAction(Actions.fadeIn(1f));
	}

	public void FadeOutAdLoginButtons(){
		if(!base_menu.settings.settings.is_this_premium_updated && add_free_version!=null
				&& add_free_version.getColor().a!=ex01Types.BASE_ALPHA_ZERO) {
			add_free_version.addAction(Actions.fadeOut(1f));
		}
		if(loginlogout_free_version!=null)
			loginlogout_free_version.addAction(Actions.fadeOut(1f));
	}

	public void FadeInAdLoginButtons(){
		if(!base_menu.settings.settings.is_this_premium_updated && add_free_version!=null
				&& add_free_version.getColor().a!=ex01Types.BASE_ALPHA_ZERO) {
			add_free_version.addAction(Actions.fadeIn(1f));
		}
		if(loginlogout_free_version!=null)
			loginlogout_free_version.addAction(Actions.fadeIn(1f));
	}

	public void ProcessNeedToGoVALUE(){
		if(base_menu.settings.settings.sounds_on)
			base_menu.menu_selector_button.play(SND_VOL.SOUND_MENU_SELECTOR);
		new_game_base.addAction(sequence(
			moveBy(0f, -18f, 0.1f),
			moveBy(0f, 18f, 0.1f)));
		new_game_overlayer.addAction(sequence(
			moveBy(0f, 12f, 0.2f),
			moveBy(0f, -12f, 0.3f),
			run(new Runnable() {
				public void run() {
					base_menu.settings.settings
							.new_game_adview_time_ask_counter++;
					if (base_menu.settings.settings.new_game_adview_time_ask_counter ==
							json_settings.NEW_GAME_ADVIEW_TIME_ASK
							&& !ex01Types.BLOCK_VIEW_REWARD) {
						base_menu.settings.settings
								.new_game_adview_time_ask_counter = 0;
						still_working_on_menu_press = true;
						if (!menu_init.settings_for_data.settings.is_this_for_free) {
							FadeOutAdLoginButtons();
						}
						FadeOutMenuButtons(1.5f);
						base_menu.VALUE_screen.exit_dialog.addAction(parallel(
								Actions.fadeIn(1.25f),
								Actions.moveBy(0f, 50f, 1.25f, swingOut)));
						base_menu.grayscale_shader_active_VALUE = true;
						stage.getBatch().setShader(base_menu.shader_grayscale);
						Gdx.input.setInputProcessor(base_menu.VALUE_screen.stage_exit_dialog);
						still_working_on_menu_press = false;
					} else {
						still_working_on_menu_press = true;
						if (!menu_init.settings_for_data.settings.is_this_for_free) {
							FadeOutAdLoginButtons();
						}
						base_menu.VALUE_screen.ShowShortVALUEMenu();
						still_working_on_menu_press = false;
					}
					base_menu.settings.WriteJson();
				}
			})));
		new_game_text.addAction(parallel(Actions.fadeIn(0.3f),
				Actions.scaleTo(1.2f, 1.2f, 0.3f),
				Actions.sequence(
					moveBy(0f, 15f, 0.3f),
					moveBy(0f, -15f, 0.2f, Swing.swing))));
    }
    
    public void StartNewGameInVALUE(){
		still_working_on_menu_press = true;
		if(base_menu.cryo_game.levels_screen==null){
			base_menu.cryo_game.setScreen(menu_init);
			Gdx.input.setInputProcessor(multiplexer);
		} else {
			base_menu.cryo_game.game_state = GameState.LEVELS_SCREEN;
			base_menu.cryo_game.setScreen(base_menu.cryo_game.levels_screen);
			Gdx.input.setInputProcessor(multiplexer);
			still_working_on_menu_press = false;
		}
    }

	public void RenderExtra(float delta){
		if(!bCurrentlyFadedOutDontDraw) {
			if (timerForAdd.millis() - timestamptime > 1000 && can_switchstyles) {
				timestamptime = TimeUtils.millis();
				SwitchStyleForBuyPremium();
				if(add_free_version_table.getColor().a == 0f)
					add_free_version_table.addAction(fadeIn(1f));
			}

			// this situation is when we have unlock levels button and load saved game button
			if (can_render_unlock && !can_render_unlock_buy_only_load) {
				// can_render_unlock_buy_only_load > we show UNLOCK and LOAD GAME
				if (can_render_loadsave) {
					if (!base_menu.is_still_rolling_reset_premium_unlocked_duplex) {
						if (timerForAdd.millis() - timestamptime_render_load > 2500) {
							timestamptime_render_load = timerForAdd.millis();
							currently_rendering_load_reset = false;
							currently_rendering_load = !currently_rendering_load;
						}
						if (currently_rendering_load && !currently_rendering_load_reset) {
							if(savegame_free_version_table!=null) {
								savegame_free_version_table.clearActions();
								savegame_free_version_table.addAction(Actions.fadeIn(0f));
								savegame_free_version.addAction(sequence(
										Actions.scaleTo(1.145f, 1.145f, 2f),
										Actions.scaleTo(1f, 1f, 2f)));
								savegame_free_version_table.toFront();
							}
							if (unlock_free_version_table != null) {
								unlock_free_version_table.clearActions();
								unlock_free_version_table.addAction(Actions.fadeOut(0f));
							}
							currently_rendering_load_reset = true;
						} else if (!currently_rendering_load && !currently_rendering_load_reset) {
							if(savegame_free_version_table!=null) {
								savegame_free_version_table.clearActions();
								savegame_free_version_table.addAction(Actions.fadeOut(0f));
							}
							if (unlock_free_version_table != null) {
								unlock_free_version_table.clearActions();
								unlock_free_version_table.addAction(Actions.fadeIn(0f));
								unlock_free_version_table.toFront();
								unlock_free_version.addAction(sequence(
										Actions.scaleTo(1.145f, 1.145f, 2f),
										Actions.scaleTo(1f, 1f, 2f)));
							}
							currently_rendering_load_reset = true;
						}
						if (unlock_free_version_table != null) {
						}
					}
				} else {
					if (unlock_free_version_table != null
							&& unlock_free_version_table.getColor().a == 0f) {
						if (!base_menu.is_still_rolling_reset_premium_unlocked) {
							unlock_free_version_table.addAction(fadeIn(1f));
							if (unlock_free_version_table != null) {
							}
						}
					}
				}
			}

			else if (can_render_unlock) {
				// can_render_unlock_buy_only_load > we only show LOAD GAME when we're first
				// entering the game after reset
				if (can_render_loadsave) {
					if (!base_menu.is_still_rolling_reset_premium_unlocked) {
						if (timerForAdd.millis() - timestamptime_render_load > 2500) {
							timestamptime_render_load = timerForAdd.millis();
							currently_rendering_load_reset = false;
							if(savegame_free_version_table!=null) {
								savegame_free_version_table.addAction(Actions.fadeIn(1f));
								savegame_free_version_table.toFront();
							}
						}
						savegame_free_version_table.act(delta);
						if (!currently_rendering_load_reset) {
							if(savegame_free_version_table!=null) {
								savegame_free_version_table.addAction(sequence(
										Actions.scaleTo(1.085f, 1.085f, 2f),
										Actions.scaleTo(1f, 1f, 2f)));
							}
							currently_rendering_load_reset = true;
						}
					}
				}
			}

			else {
				if (can_render_loadsave) {
					RenderLoadSaveOnly();
				}
			}
		}
	}

	public void RenderLoadSaveOnly(){
		if (!currently_rendering_load_reset) {
			if(bIsLoggedIn) {
				savegame_free_version_table.clearActions();
				savegame_free_version_table.addAction(fadeIn(1f));
				savegame_free_version_table.addAction(sequence(
						Actions.scaleTo(1.085f, 1.085f, 2f),
						Actions.scaleTo(1f, 1f, 2f)));

				if(base_menu.cryo_game.game_screen!=null){
					if(base_menu.cryo_game.game_screen.hud_display
							.savegame_free_version_table!=null){
						base_menu.cryo_game.game_screen.hud_display.savegame_free_version_table
								.clearActions();
						base_menu.cryo_game.game_screen.hud_display.savegame_free_version_table
								.addAction(fadeIn(1f));
						base_menu.cryo_game.game_screen.hud_display.savegame_free_version_table
								.addAction(sequence(
										Actions.scaleTo(1.085f, 1.085f, 2f),
										Actions.scaleTo(1f, 1f, 2f)));
						base_menu.cryo_game.game_screen.hud_display
								.savegame_free_version_table.act(Gdx.graphics.getDeltaTime());
					}
				}
				currently_rendering_load_reset = true;
			}
		}
	}

	public void FadeIn_ExtraButtons(){
		if(loginlogout_free_version_table!=null) {
			loginlogout_free_version_table.addAction(Actions.fadeIn(1f));
		}
		if(!base_menu.settings.settings.is_this_premium_updated) {
			if(add_free_version!=null){
				add_free_version.addAction(Actions.fadeIn(2f));
			}
		}
		if(savegame_free_version!=null) {
			savegame_free_version.addAction(Actions.fadeIn(2f));
		}
		if(unlock_free_version!=null) {
			unlock_free_version.addAction(Actions.fadeIn(2f));
		}
	}

	public void Render(float delta){
//FREE	RenderExtra(delta);
    	stage.act(delta*SPEED_MENU);
        stage.draw();		
	}
	
    public void FadeOutMenuButtons(float fade){
    	table_menu.addAction(Actions.fadeOut(fade));
    	table_menu_overlayer.addAction(Actions.fadeOut(fade));
    	table_menu_overlayer_text.addAction(Actions.fadeOut(fade));
    }
    
    public void FadeInMenuButtons(float fade){
    	table_menu.addAction(Actions.fadeIn(fade));
    	table_menu_overlayer.addAction(Actions.fadeIn(fade));
    	table_menu_overlayer_text.addAction(Actions.fadeIn(fade));
    }

	public void Dispose(){
		if(new_game_base!=null)
			new_game_base.clear();
		if(settings_base!=null)
			settings_base.clear();
		if(scores_base!=null)
			scores_base.clear();
		if(help_base!=null)
			help_base.clear();
		if(exit_base!=null)
			exit_base.clear();
		if(new_game_overlayer!=null)
			new_game_overlayer.clear();
		if(settings_overlayer!=null)
			settings_overlayer.clear();
		if(scores_overlayer!=null)
			scores_overlayer.clear();
		if(help_overlayer!=null)
			help_overlayer.clear();
		if(exit_overlayer!=null)
			exit_overlayer.clear();
		if(new_game_text!=null)
			new_game_text.clear();
		if(settings_text!=null)
			settings_text.clear();
		if(scores_text!=null)
			scores_text.clear();
		if(help_text!=null)
			help_text.clear();
		if(add_free_version!=null)
			add_free_version.clear();

		new_game_base = null;
		settings_base = null;
		scores_base = null;
		help_base = null;
		exit_base = null;
		new_game_overlayer = null;
		settings_overlayer = null;
		scores_overlayer = null;
		help_overlayer = null;
		exit_overlayer = null;
		new_game_text = null;
		settings_text = null;
		scores_text = null;
		help_text = null;
		add_free_version = null;

		backgroundTexR = null;
		button_base_reg = null;
		button_overlayer_reg = null;
		new_game_text_reg = null;
		settings_text_reg = null;
		scores_text_reg = null;
		help_text_reg = null;
		exit_text_reg = null;
		add_free_reg = null;
		cryo_image_tr = null;

		if(table!=null)
			table.clear();
		if(table_menu!=null)
			table_menu.clear();
		if(table_menu_overlayer!=null)
			table_menu_overlayer.clear();
		if(table_menu_overlayer_text!=null)
			table_menu_overlayer_text.clear();
		if(cryo_image_table!=null)
			cryo_image_table.clear();
		if(add_free_version_table!=null)
			add_free_version_table.clear();

		table = null;
		table_menu = null;
		table_menu_overlayer = null;
		table_menu_overlayer_text = null;
		cryo_image_table = null;
		add_free_version_table = null;

		if(texture_atlas!=null){
			texture_atlas.dispose();
			texture_atlas = null;
		}
		if(multiplexer!=null){
			multiplexer.clear();
			multiplexer = null;
		}
		if(stage!=null){
			stage.clear();
			stage.dispose();
			stage = null;
		}
		if(cryo_image!=null) cryo_image.clear();
		cryo_image = null;
		if(backgroundImg!=null) backgroundImg.clear();
		backgroundImg = null;
		base_menu = null;
		if(menu_init!=null){
			menu_init.dispose();
			menu_init = null; // did we really dispose this?
		}

		if(unlock_free_version_table!=null){
			unlock_free_version_table.clear();
			unlock_free_version_table = null;
		}
		if(unlock_free_reg!=null){
			unlock_free_reg = null;
		}
		if(unlock_free_version!=null){
			unlock_free_version.clear();
			unlock_free_version = null;
		}
		add_free_regOff = null;
		styleon = null;
		styleoff = null;
		timerForAdd = null;

		if(settings_get_premium_error!=null){
			settings_get_premium_error.clear();
			settings_get_premium_error = null;
		}
		if(get_premium_table_error!=null){
			get_premium_table_error.clear();
			get_premium_table_error = null;
		}
		if(level_base_premium_t_error!=null){
			level_base_premium_t_error.clear();
			level_base_premium_t_error = null;
		}
		if(level_base_premium_error!=null){
			level_base_premium_error.clear();
			level_base_premium_error = null;
		}
		if(level_base_premium_you_own_t_error!=null){
			level_base_premium_you_own_t_error.clear();
			level_base_premium_you_own_t_error = null;
		}
		if(level_base_premium_you_own_error!=null){
			level_base_premium_you_own_error.clear();
			level_base_premium_you_own_error = null;
		}
		if(level_base_premium_you_own_info!=null){
			level_base_premium_you_own_info.clear();
			level_base_premium_you_own_info = null;
		}
		textb1 = null;
		textb2ok = null;

		if(savegame_free_version_table!=null){
			savegame_free_version_table.clear();
			savegame_free_version_table = null;
		}
		if(savegame_free_version!=null){
			savegame_free_version.clear();
			savegame_free_version = null;
		}
		savegame_free_reg = null;

		if(loginlogout_free_version_table!=null){
			loginlogout_free_version_table.clear();
			loginlogout_free_version_table = null;
		}
		if(loginlogout_free_version!=null){
			loginlogout_free_version.clear();
			loginlogout_free_version = null;
		}
		loginlogout_free_reg = null;
		loginlogout_free_regOff = null;
		styleonz = null;
		styleoffz = null;
		str = null;
	}
}
