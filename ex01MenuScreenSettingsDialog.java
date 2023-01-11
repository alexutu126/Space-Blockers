//WMS3 2015

package com.cryotrax.game;
import static com.badlogic.gdx.math.Interpolation.swingIn;
import static com.badlogic.gdx.math.Interpolation.swingOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ex01MenuScreenSettingsDialog {
	public static final float SPEED_SETTINGS = 3f;
    public static final float width =
			ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f;
    public static final float height =
			ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f * 278f/430f;
    public Table settings_dialog;
	public Table settings_table_done;
	public Table settings_table_back;
	public Table settings_table_settings;
	public Table settings_chk_table;
    public CheckBox settings_chk1_sound;  
    public ImageButton settings_get_spacecraft;
    public ImageButton settings_get_coins;
    public ImageButton settings_get_life;
    public ImageButton settings_get_bullets;
    public ImageButton settings_done;	
    public Image settings_image_back;	
    public Image settings_image_settings;
	public Stage stage_settings_dialog;
    public Stack settings_dialog_stack;	
	public ex01MenuScreen base_menu;
    public boolean still_working_on_done_press = false;
    
    //settings dialog - pretty also
    public ex01MenuScreenSettingsDialog(Skin skin,
										ex01MenuScreen bmenu,
										Viewport viewport) {
		stage_settings_dialog = new Stage(viewport);
    	base_menu = bmenu;
        settings_dialog_stack = new Stack();
        settings_dialog = new Table();
        settings_table_back = new Table();
        settings_table_settings = new Table();
        settings_dialog.setTransform(true);
        settings_dialog.addAction(Actions.fadeOut(0f));
        settings_dialog.add(settings_dialog_stack);
        settings_chk1_sound = new CheckBox("", skin);
        settings_get_spacecraft = new ImageButton(skin, "btn_settings_buyspace");
        settings_get_coins = new ImageButton(skin, "btn_settings_buycoins");
        settings_get_life = new ImageButton(skin, "btn_settings_buylife");
        settings_get_bullets = new ImageButton(skin, "btn_settings_buybullets");
        CheckBox.CheckBoxStyle chk_style1 = skin.get("chk_settings_sound",
				CheckBox.CheckBoxStyle.class);
        settings_chk1_sound.setStyle(chk_style1);      
        settings_chk_table = new Table();
        settings_chk_table.setTransform(true);
        settings_chk_table.add(settings_get_coins)
				.colspan(1)
				.pad(-ex01Types.VIRTUAL_SCREEN_WIDTH / 220f)
				.space(0f)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.3f * 119f / 256f);
        settings_chk_table.row();
        settings_chk_table.add(settings_get_spacecraft)
				.colspan(1)
				.pad(-ex01Types.VIRTUAL_SCREEN_WIDTH / 220f)
				.space(0f)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.3f * 119f / 256f);
        settings_chk_table.row();
        settings_chk_table.add(settings_get_life)
				.colspan(1)
				.pad(-ex01Types.VIRTUAL_SCREEN_WIDTH / 220f)
				.space(0f)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.3f * 119f / 256f);
        settings_chk_table.row();
        settings_chk_table.add(settings_get_bullets)
				.colspan(1)
				.pad(-ex01Types.VIRTUAL_SCREEN_WIDTH / 220f)
				.space(0f)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.3f * 119f / 256f);
        settings_chk_table.row();
        settings_chk_table.add(settings_chk1_sound)
				.colspan(1)
				.pad(0f)
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.15f);
        settings_chk1_sound
				.getImageCell()
				.pad(-ex01Types.VIRTUAL_SCREEN_WIDTH / 220f)
				.space(0f)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.3f * 119f / 256f);
        settings_image_back = new Image(skin.getDrawable("replay"));
        settings_image_settings = new Image(skin.getDrawable("gamesetup2"));     
        settings_table_back.add(settings_image_back)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.05f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.05f * 896f / 512f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH/150f);
        settings_table_settings.add(settings_image_settings)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.75f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.75f * 59f / 256f).top();
        settings_table_settings.top().padTop(ex01Types.VIRTUAL_SCREEN_WIDTH/11f);
        settings_table_settings.addAction(Actions.alpha(0.80f));
        settings_dialog_stack.add(settings_table_back);
        settings_done = new ImageButton(skin, "done_button");
        settings_done.pad(0f);        
        settings_table_done = new Table();
        settings_table_done.add(settings_chk_table);  
        settings_table_done.row();
        settings_table_done.add(settings_done)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.1f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.1f * 106f / 256f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 456f)
				.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 16.75f);
        settings_table_done.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH/3.15f);
        settings_done.padTop(-ex01Types.VIRTUAL_SCREEN_WIDTH/2.5f);
		settings_dialog_stack.add(settings_table_done);        
		settings_dialog_stack.add(settings_table_settings);
        settings_dialog.addAction(Actions.moveBy(0f,-50f,0f));
        settings_dialog.setTransform(true);
        settings_dialog.addAction(Actions.sizeTo(width, height));
        settings_dialog.setFillParent(true);
        stage_settings_dialog.addActor(settings_dialog);
        
        settings_get_spacecraft.addListener( new ClickListener() {
 			@Override
 			public void clicked(InputEvent event, float x, float y){
 				if(base_menu.finished_transition){
 					if(base_menu.settings.settings.sounds_on)
						base_menu.menu_selector_button.play(SND_VOL.SOUND_MENU_SELECTOR);
 					base_menu.FadeOutSettingsDialog(1.25f);
 					base_menu.get_spacecraft_dialog_screen.UpdateCoinsHave();
 					settings_get_spacecraft.addAction(sequence(
							moveBy(0f, 12f, 0.2f),
							moveBy(0f, -12f, 0.3f),
							Actions.run(new Runnable(){
 						public void run(){
 							base_menu.get_spacecraft_dialog_screen.get_spacecraft_dialog
									.addAction(parallel(
											fadeIn(1.25f),
											moveBy(0f,50f,1.25f,swingOut)));
 							base_menu.get_spacecraft_dialog_screen.get_spacecraft_money_t
									.addAction(sequence(
											parallel(fadeIn(1.25f),
													moveBy(0f,25f,1.25f,swingOut)) ,
											Actions.run(new Runnable(){
												public void run(){
												}
 							})));
 							base_menu.grayscale_shader_active_settings = false;
 							base_menu.grayscale_shader_active_get_spacecraft = true;
 						}
 					})));					
 					Gdx.input.setInputProcessor(base_menu
							.get_spacecraft_dialog_screen.stage_get_spacecraft_dialog);
 				}
 			}			
 		});        
        
        settings_get_coins.addListener( new ClickListener() {
 			@Override
 			public void clicked(InputEvent event, float x, float y){
 				if(base_menu.finished_transition){
 					if(base_menu.settings.settings.sounds_on)
						base_menu.menu_selector_button.play(SND_VOL.SOUND_MENU_SELECTOR);
 					base_menu.FadeOutSettingsDialog(1.25f);		
 					base_menu.get_coins_dialog_screen.UpdateCoinsHave();
 					settings_get_coins.addAction(sequence(
							moveBy(0f, 12f, 0.2f),
							moveBy(0f, -12f, 0.3f),
							Actions.run(new Runnable(){
 						public void run(){
 							base_menu.get_coins_dialog_screen.get_money_dialog
									.addAction(parallel(
											fadeIn(1.25f),
											moveBy(0f,50f,1.25f,swingOut)));
 							base_menu.get_coins_dialog_screen.get_money_table_owned
									.addAction(sequence(
											parallel(fadeIn(1.25f),
													moveBy(0f,25f,1.25f,swingOut)) ,
											Actions.run(new Runnable(){
												public void run(){
												}
 							})));
 							base_menu.grayscale_shader_active_settings = false;
 							base_menu.grayscale_shader_active_get_coins = true;
 						}
 					})));					
 					Gdx.input.setInputProcessor(base_menu
							.get_coins_dialog_screen.stage_get_coins_dialog);
 				}
 			}			
 		});      
        
        settings_get_life.addListener( new ClickListener() {
 			@Override
 			public void clicked(InputEvent event, float x, float y){
 				if(base_menu.finished_transition){
 					if(base_menu.settings.settings.sounds_on)
						base_menu.menu_selector_button.play(SND_VOL.SOUND_MENU_SELECTOR);
 					base_menu.FadeOutSettingsDialog(1.25f);		
 					base_menu.get_lifeslots_screen.UpdateCoinsHave();
 					settings_get_life.addAction(sequence(
							moveBy(0f, 12f, 0.2f),
							moveBy(0f, -12f, 0.3f),
							Actions.run(new Runnable(){
								public void run(){
									base_menu.get_lifeslots_screen.get_lifeslots_dialog.
											addAction(parallel(fadeIn(1.25f),
													moveBy(0f,50f,1.25f,swingOut)));
									base_menu.get_lifeslots_screen.get_lifeslots_lifeslots_t
											.addAction(sequence(
													parallel(fadeIn(1.25f),
															moveBy(0f,25f,1.25f,swingOut))));
									base_menu.get_lifeslots_screen.get_lifeslots_money_t
											.addAction(sequence(parallel(
													fadeIn(1.25f),
													moveBy(0f,25f,1.25f,swingOut)) ,
													Actions.run(new Runnable(){
														public void run(){
														}
									})));
									base_menu.grayscale_shader_active_settings = false;
									base_menu.grayscale_shader_active_get_lifeslots = true;
								}
 					})));					
 					Gdx.input.setInputProcessor(base_menu
							.get_lifeslots_screen.stage_get_lifeslots_dialog);
 				}
 			}			
 		});    
        
        settings_get_bullets.addListener( new ClickListener() {
  			@Override
  			public void clicked(InputEvent event, float x, float y){
  				if(base_menu.finished_transition){
  					if(base_menu.settings.settings.sounds_on)
						base_menu.menu_selector_button.play(SND_VOL.SOUND_MENU_SELECTOR);
  					base_menu.FadeOutSettingsDialog(1.25f);	
  					base_menu.get_bullets_screen.UpdateCoinsHave();
  					settings_get_bullets.addAction(sequence(
						moveBy(0f, 12f, 0.2f),
						moveBy(0f, -12f, 0.3f),
						Actions.run(new Runnable(){
							public void run(){
								base_menu.get_bullets_screen.get_bullets_dialog.addAction(
										parallel(
										fadeIn(1.25f),
										moveBy(0f,50f,1.25f,swingOut)));
								base_menu.get_bullets_screen.get_ammo_ammo_t.addAction(
										sequence(
										parallel(fadeIn(1.25f),
												moveBy(0f,25f,1.25f,swingOut))));
								base_menu.get_bullets_screen.get_bullets_money_t.addAction(
										sequence(
										parallel(fadeIn(1.25f),
												moveBy(0f,25f,1.25f,swingOut)) ,
										Actions.run(new Runnable(){
											public void run(){
											}
								})));
								base_menu.grayscale_shader_active_settings = false;
								base_menu.grayscale_shader_active_get_bullets = true;
							}
  					})));					
  					Gdx.input.setInputProcessor(base_menu
							.get_bullets_screen.stage_get_bullets_dialog);
  				}
  			}			
  		});                   
        
        settings_done.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!still_working_on_done_press){
					still_working_on_done_press = true;
					if(base_menu.settings.settings.sounds_on)
						base_menu.done_button.play(SND_VOL.DONE_BUTTON_VOLUME);
					base_menu.menus.settings_text.addAction(parallel(
							Actions.alpha(0.7f,3f),
							scaleTo(1.0f, 1.0f, 0.8f),
							sequence(moveBy(0f, 15f, 0.15f),
									moveBy(0f, -15f, 1f, swingOut))));
					settings_done.addAction(sequence(
							moveBy(0f, 12f, 0.1f),
							moveBy(0f, -12f, 0.2f)));
					settings_dialog.addAction(sequence(parallel(
							fadeOut(1.25f),
							moveBy(0f,-50f,1.25f,swingIn)),
							run(new Runnable(){
								public void run(){
									base_menu.menus.FadeIn_ExtraButtons();
									base_menu.menus.FadeInMenuButtons(1.25f);
									base_menu.grayscale_shader_active_settings = false;
									base_menu.menus.stage.getBatch().setShader(null);
									Gdx.input.setInputProcessor(base_menu.menus.stage);
									still_working_on_done_press = false;
								}
					})));
				}
			}	
		});	
        
        settings_chk1_sound.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				base_menu.done_button.play(SND_VOL.DONE_BUTTON_VOLUME);
				base_menu.settings.settings.sounds_on = !base_menu.settings.settings.sounds_on;
				if(base_menu.settings.settings.sounds_on){
					if(base_menu.menu_music.getVolume()!=ex01Types.MUSIC_MENU_LEVEL){
						base_menu.menu_music.setVolume(ex01Types.MUSIC_MENU_LEVEL);
					}
				} else {
					if(base_menu.menu_music.getVolume()!=ex01Types.MUSIC_MENU_LEVEL_NULL){
						base_menu.menu_music.setVolume(ex01Types.MUSIC_MENU_LEVEL_NULL);
					}
				}
				base_menu.settings.WriteJson();
				base_menu.can_exit_game_or_do_other_write = true;
				if(!base_menu.settings.settings.is_this_in_need_for_load_saved) {
					base_menu.cryo_game.SAVE_GAME_TO_CLOUD();
				}
			}	
		});	
        if(!base_menu.cryo_game.menu_screen
				.settings.settings.sounds_on){
			settings_chk1_sound.setChecked(true);
		}
    }

    public void Render(float delta){
		stage_settings_dialog.act(delta*SPEED_SETTINGS);
		stage_settings_dialog.draw();        
    }

	public void Dispose(){
		if(settings_dialog!=null)
			settings_dialog.clear();
		if(settings_table_done!=null)
			settings_table_done.clear();
		if(settings_table_back!=null)
			settings_table_back.clear();
		if(settings_table_settings!=null)
			settings_table_settings.clear();
		if(settings_chk_table!=null)
			settings_chk_table.clear();
		if(settings_chk1_sound!=null)
			settings_chk1_sound.clear();
		if(settings_get_spacecraft!=null)
			settings_get_spacecraft.clear();
		if(settings_get_coins!=null)
			settings_get_coins.clear();
		if(settings_get_life!=null)
			settings_get_life.clear();
		if(settings_get_bullets!=null)
			settings_get_bullets.clear();
		if(settings_done!=null)
			settings_done.clear();
		if(settings_image_back!=null)
			settings_image_back.clear();
		if(settings_image_settings!=null)
			settings_image_settings.clear();
		if(settings_dialog_stack!=null)
			settings_dialog_stack.clear();
		settings_dialog = null;
		settings_table_done = null;
		settings_table_back = null;
		settings_table_settings = null;
		settings_chk_table = null;
		settings_chk1_sound = null;
		settings_get_spacecraft = null;
		settings_get_coins = null;
		settings_get_life = null;
		settings_get_bullets = null;
		settings_done = null;
		settings_image_back = null;
		settings_image_settings = null;
		settings_dialog_stack = null;

		if(stage_settings_dialog!=null){
			stage_settings_dialog.clear();
			stage_settings_dialog = null;
		}
		base_menu = null;
	}
}
