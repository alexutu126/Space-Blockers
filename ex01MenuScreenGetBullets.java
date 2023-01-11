//WMS3 2015

package com.cryotrax.game;
import java.util.ArrayList;
import static com.badlogic.gdx.math.Interpolation.swingIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation.Swing;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ex01MenuScreenGetBullets {
	public static final float SPEED_BULLETS = 3f;
    public static final float width = ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f;
    public static final float height = ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f * 278f/430f;
	public Table get_bullets_dialog;
	public Table get_bullets_table_done;
	public Table get_bullets_table_left_right;
	public Table get_bullets_table_buy_use;
	public Table get_bullets_table_back;
	public Table get_bullets_buybullets1;
	public Table get_bullets_buybullets2;
	public Table get_bullets_buybullets3;
	public Table get_bullets_buybullets4;
	public Table get_bullets_money_t;
	public Table level_base_coins_t;
	public Table level_base_coins_you_own_t;
    public Image get_bullets_image_back;   
    public Image get_bullets_image_spacehip1;
    public Image get_bullets_image_spacehip2;
    public Image get_bullets_image_spacehip3;
    public Image get_bullets_image_spacehip4;
    public Image get_bullets_image_money;
	public Image level_base_coins_you_own;
    public ImageButton settings_get_bullets_done;
    public ImageButton settings_get_bullets_left;
    public ImageButton settings_get_bullets_right;
    public ImageButton settings_get_bullets_buy;
    public ImageButton settings_get_bullets_use;
    public ImageButton.ImageButtonStyle settings_get_bullets_buy_style;
    public ImageButton.ImageButtonStyle settings_get_bullets_bought_style;
    public ImageButton.ImageButtonStyle settings_get_bullets_use_style;
    public ImageButton.ImageButtonStyle settings_get_bullets_use_noactive_style;
    public ImageButton.ImageButtonStyle settings_get_bullets_use_selected_style;
    public TextButton level_base_coins;
	public Stage stage_get_bullets_dialog;   
	public Stack settings_get_bullets_money_s;
    public Stack settings_get_bullets_stack;
	public ex01MenuScreen base_menu;
	public ArrayList<Spaceship> spaceship_buys;
    public enum SelectedSpaceship {one, two, three, four};
    public SelectedSpaceship selected_spaceship = SelectedSpaceship.one;
    public ex01JSONSettingsLoader settings_for_data;
	//bought stuff
	public Stack settings_get_bullets_bought;
	public Table get_bullets_table_bought;
	public Table level_base_bullets_t_bought;
	public TextButton level_base_bullets_bought;
	public Table level_base_bullets_you_own_t_bought;
	public Image level_base_bullets_you_own_bought;
	public boolean still_working_buying = false;
	public boolean current_bought = false;
	//error stuff
	public Stack settings_get_bullets_error;
	public Table get_bullets_table_error;
	public Table level_base_bullets_t_error;
	public TextButton level_base_bullets_error;
	public Table level_base_bullets_you_own_t_error;
	public Image level_base_bullets_you_own_error;
	public Image level_base_bullets_you_own_info;   
	//owned life-slots
    public Image get_ammo_image_ammo;
    public Image level_base_coins_you_own_ammo;
    public Stack settings_get_ammo_ammo_s;
    public Stack settings_get_ammo_money_s_ammo;
    public Table get_ammo_ammo_t;
    public Table level_base_coins_t_ammo;
    public Table level_base_coins_you_own_t_ammo;
    public TextButton level_base_coins_ammo;
    public boolean still_working_on_done_press = false;
    
    public ex01MenuScreenGetBullets(ex01JSONSettingsLoader settings,
									TextureAtlas atlas,
									Skin skin,
									ex01MenuScreen bmenu,
									Viewport viewport) {
    	stage_get_bullets_dialog = new Stage(viewport);
    	base_menu = bmenu;
    	settings_for_data = settings;  	
    	InitBulletsBuys(atlas);
    	InitMainElements(skin);
    	InitCoinsOwned(atlas, skin);
    	InitBulletsBought(atlas, skin);
    	InitBulletsError(atlas, skin);
       	InitBulletsOwned(atlas, skin);
    }
    
	public void InitBulletsOwned(TextureAtlas atlas, Skin skin){	
		get_ammo_image_ammo = new Image(new TextureRegionDrawable(atlas.findRegion("ammohave")));
		settings_get_ammo_ammo_s = new Stack();      
		get_ammo_ammo_t = new Table();
		get_ammo_ammo_t.add(settings_get_ammo_ammo_s)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f * 85f / 384f);
        get_ammo_ammo_t.setFillParent(true);
        get_ammo_ammo_t.bottom().padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 7.30f);
		get_ammo_ammo_t.addAction(moveBy(0f,-25f,0f));
		level_base_coins_ammo = new
				TextButton(Integer.toString(settings_for_data.settings.counter_ammo_base)
				+ " ammo", skin, "ammo_have");
		level_base_coins_t_ammo = new Table();
		level_base_coins_t_ammo.setTransform(true);	
		level_base_coins_t_ammo.setFillParent(true);
		level_base_coins_t_ammo.add(level_base_coins_ammo)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f * 85f / 384f);
		settings_get_ammo_ammo_s.add(level_base_coins_t_ammo);
		level_base_coins_you_own_ammo = new Image(new
				TextureRegionDrawable(atlas.findRegion("your life2")));
		level_base_coins_you_own_t_ammo = new Table();		
		level_base_coins_you_own_t_ammo.add(level_base_coins_you_own_ammo)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.35f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.35f * 80f / 256f);
		level_base_coins_you_own_t_ammo
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 5.10f);
		settings_get_ammo_ammo_s.add(level_base_coins_you_own_t_ammo);
        stage_get_bullets_dialog.addActor(get_ammo_ammo_t);
	}        
    
	public void InitCoinsOwned(TextureAtlas atlas, Skin skin){	
		get_bullets_image_money = new Image(new
				TextureRegionDrawable(atlas.findRegion("coins_owned")));
		settings_get_bullets_money_s = new Stack();      
		get_bullets_money_t = new Table();
		get_bullets_money_t.add(settings_get_bullets_money_s)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f * 85f / 384f);
        get_bullets_money_t.setFillParent(true);
        get_bullets_money_t.top().padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 5f);
		get_bullets_money_t.addAction(moveBy(0f,-25f,0f));
		level_base_coins = new TextButton(Integer
				.toString(settings_for_data.settings.number_coins) + " COINS", skin, "coins_have");
		level_base_coins_t = new Table();
		level_base_coins_t.setTransform(true);	
		level_base_coins_t.setFillParent(true);
		level_base_coins_t.add(level_base_coins)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f * 85f / 384f);
		settings_get_bullets_money_s.add(level_base_coins_t);
		level_base_coins_you_own = new Image(new
				TextureRegionDrawable(atlas.findRegion("your coinsa2")));
		level_base_coins_you_own_t = new Table();		
		level_base_coins_you_own_t.add(level_base_coins_you_own)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.35f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.35f * 80f / 256f);
		level_base_coins_you_own_t.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 5.25f);
		settings_get_bullets_money_s.add(level_base_coins_you_own_t);
        stage_get_bullets_dialog.addActor(get_bullets_money_t);
	}
    
	public void InitMainElements(Skin skin){
    	settings_get_bullets_buy_style = skin
				.get("buy_button2", ImageButton.ImageButtonStyle.class);
    	settings_get_bullets_stack = new Stack();
        get_bullets_dialog = new Table();
        get_bullets_dialog.setTransform(true);
        get_bullets_dialog.addAction(Actions.fadeOut(0f));
        get_bullets_dialog.add(settings_get_bullets_stack);
        get_bullets_dialog.addAction(Actions.moveBy(0f, -50f, 0f));
        get_bullets_dialog.addAction(Actions.sizeTo(width, height));      
        get_bullets_dialog.setPosition(ex01Types.VIRTUAL_SCREEN_WIDTH * 0.5f - width/2,
				ex01Types.VIRTUAL_SCREEN_HEIGHT * 0.5f - height/1.80f);
        //setup all bullets   
        get_bullets_table_back = new Table();
        get_bullets_buybullets1 = new Table();
        get_bullets_buybullets2 = new Table();
        get_bullets_buybullets3 = new Table();
        get_bullets_buybullets4 = new Table();
        get_bullets_table_buy_use = new Table();
        get_bullets_buybullets1.setTransform(true);
        get_bullets_buybullets2.setTransform(true);
        get_bullets_buybullets3.setTransform(true);
        get_bullets_buybullets4.setTransform(true);
        get_bullets_image_back = new Image(skin.getDrawable("replayh"));        
        get_bullets_table_back.add(get_bullets_image_back)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.1f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.1f * 339f / 512f);
        get_bullets_buybullets1.add(spaceship_buys.get(0).image)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f * 126f / 256f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 130f);
        get_bullets_buybullets2.add(spaceship_buys.get(1).image)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f * 126f / 256f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 130f);
        get_bullets_buybullets3.add(spaceship_buys.get(2).image)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f * 126f / 256f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 130f);
        get_bullets_buybullets4.add(spaceship_buys.get(3).image)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f * 126f / 256f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 130f);
        settings_get_bullets_stack.add(get_bullets_table_back);
        settings_get_bullets_stack.add(get_bullets_buybullets1);
        settings_get_bullets_stack.add(get_bullets_buybullets2);
        settings_get_bullets_stack.add(get_bullets_buybullets3);
        settings_get_bullets_stack.add(get_bullets_buybullets4);
        get_bullets_buybullets1.addAction(Actions.alpha(0f));
        get_bullets_buybullets2.addAction(Actions.alpha(0f));
        get_bullets_buybullets3.addAction(Actions.alpha(0f));
        get_bullets_buybullets4.addAction(Actions.alpha(0f));
        LoadBulletsBuys(SelectedSpaceship.one);       
        //setup left right buttons
        settings_get_bullets_left = new ImageButton(skin, "left_button");
        settings_get_bullets_left.pad(0f);
        settings_get_bullets_right = new ImageButton(skin, "right_button");
        settings_get_bullets_right.pad(0f);       
        get_bullets_table_left_right = new Table();
        get_bullets_table_left_right.add(settings_get_bullets_left)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f * 310f / 128f)
				.padRight(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f);
        get_bullets_table_left_right.add(settings_get_bullets_right)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f * 310f / 128f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f);
        //setup buy use buttons
        get_bullets_table_buy_use = new Table();
        settings_get_bullets_buy = new ImageButton(settings_get_bullets_buy_style);
        settings_get_bullets_buy.pad(0f);      
        get_bullets_table_buy_use.add(settings_get_bullets_buy)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.6f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.6f * 103f / 256f)
				.center()
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 105.00f)
				.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 35f);
        get_bullets_table_buy_use.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/-1.60f);
        //setup done button
        settings_get_bullets_done = new ImageButton(skin, "done_button");
        settings_get_bullets_done.pad(0f);
        settings_get_bullets_done.padBottom(10f);       
        get_bullets_table_done = new Table();
        get_bullets_table_done.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/1.60f);
        get_bullets_table_done.add(settings_get_bullets_done)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.4f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.4f * 106f / 256f)
				.colspan(2)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 250.00f)
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 10f);
        get_bullets_table_done.bottom();
        //set bounds and stuff 
        settings_get_bullets_stack.add(get_bullets_table_done);
        settings_get_bullets_stack.add(get_bullets_table_buy_use);
        settings_get_bullets_stack.add(get_bullets_table_left_right);
        stage_get_bullets_dialog.addActor(get_bullets_dialog);
        get_bullets_buybullets1.pack();
        get_bullets_buybullets2.pack();
        get_bullets_buybullets3.pack();
        get_bullets_buybullets4.pack();
        settings_get_bullets_stack.pack();
        get_bullets_dialog.pack();
        get_bullets_buybullets1.setOrigin(get_bullets_buybullets1.getWidth() / 2f,
				get_bullets_buybullets1.getHeight() / 2f);
        get_bullets_buybullets2.setOrigin(get_bullets_buybullets2.getWidth() / 2f,
				get_bullets_buybullets2.getHeight() / 2f);
        get_bullets_buybullets3.setOrigin(get_bullets_buybullets3.getWidth() / 2f,
				get_bullets_buybullets3.getHeight() / 2f);
        get_bullets_buybullets4.setOrigin(get_bullets_buybullets4.getWidth() / 2f,
				get_bullets_buybullets4.getHeight() / 2f);
        get_bullets_dialog.setOrigin(get_bullets_dialog.getWidth() / 2f,
				get_bullets_dialog.getHeight() / 2f);
        
        settings_get_bullets_done.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!still_working_on_done_press){
					still_working_on_done_press = true;
					if(base_menu.settings.settings.sounds_on)
						base_menu.done_button.play(SND_VOL.DONE_BUTTON_VOLUME);
			        settings_get_bullets_done.addAction(sequence(moveBy(0f, 12f, 0.1f),
							moveBy(0f, -12f, 0.2f)));
			        get_bullets_money_t.addAction(parallel(fadeOut(1.25f),
							moveBy(0f,-25f,1.25f,Swing.swingOut)));
			        get_ammo_ammo_t.addAction(parallel(fadeOut(1.25f),
							moveBy(0f,-25f,1.25f,Swing.swingOut)));
					get_bullets_dialog.addAction(sequence(parallel(fadeOut(1.25f),
						moveBy(0f,-50f,1.25f,swingIn)),
						run(new Runnable(){
							public void run(){
								base_menu.FadeInSettingsDialog(1.5f);
								base_menu.grayscale_shader_active_settings = true;
								base_menu.grayscale_shader_active_get_bullets = false;
								Gdx.input.setInputProcessor(
										base_menu.settings_dialog_screen.stage_settings_dialog);
								still_working_on_done_press = false;
								TransitErrorCancel();
							}
					})));
				}
			}	
		});	
        
        settings_get_bullets_left.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(base_menu.settings.settings.sounds_on)
					base_menu.level_left_right_button.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
		        settings_get_bullets_left.addAction(sequence(moveBy(-12f, 0f, 0.1f),
						moveBy(12f, 0f, 0.2f)));
		        PrevAmmoBuy();
			}	
		});	   
        
        settings_get_bullets_right.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(base_menu.settings.settings.sounds_on)
					base_menu.level_left_right_button.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
		        settings_get_bullets_right.addAction(sequence(moveBy(12f, 0f, 0.1f),
						moveBy(-12f, 0f, 0.2f)));
		        NextAmmoBuy();
			}	
		});	   		
	}

	public void InitBulletsBought(TextureAtlas atlas, Skin skin){		
    	//INIT the owned tables
		get_bullets_table_bought = new Table();
		//this stack contains the owned stuff
		settings_get_bullets_bought = new Stack();		
		get_bullets_table_bought.add(settings_get_bullets_bought)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f * 79f / 384f);
		get_bullets_table_bought.setFillParent(true);
		get_bullets_table_bought.setTransform(true);
		get_bullets_table_bought.bottom().padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 5.4f);
		get_bullets_table_bought.addAction(moveBy(0f,-25f,0f)); 
		level_base_bullets_t_bought = new Table();
		level_base_bullets_t_bought.setTransform(true);
		level_base_bullets_bought = new TextButton("whatever", skin, "ammo_have2");	
		level_base_bullets_t_bought.setFillParent(true);
		level_base_bullets_t_bought.add(level_base_bullets_bought)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f * 79f / 384f);
		level_base_bullets_t_bought.bottom();
		settings_get_bullets_bought.add(level_base_bullets_t_bought);
		level_base_bullets_you_own_t_bought = new Table();
		level_base_bullets_you_own_t_bought.setFillParent(true);
		level_base_bullets_you_own_bought = new Image(new
				TextureRegionDrawable(atlas.findRegion("your coinsa2bought")));
		level_base_bullets_you_own_t_bought.add(level_base_bullets_you_own_bought)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 3f * 80f / 256f);
		level_base_bullets_you_own_t_bought.bottom()
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 8.75f);
		settings_get_bullets_bought.add(level_base_bullets_you_own_t_bought);
		level_base_bullets_you_own_bought.addAction(Actions.alpha(0f));
		level_base_bullets_bought.addAction(Actions.alpha(0f));
        stage_get_bullets_dialog.addActor(get_bullets_table_bought);
		settings_get_bullets_buy.setTransform(true);
		settings_get_bullets_buy.setOrigin(Align.center);

		settings_get_bullets_buy.setTransform(true);
		settings_get_bullets_buy.setOrigin(Align.center);
		level_base_bullets_bought.setTransform(true);
    	level_base_bullets_bought.setOrigin(Align.center);
		level_base_bullets_you_own_bought.setOrigin(Align.center);
		level_base_coins.setTransform(true);
		level_base_coins.setOrigin(Align.center);
		level_base_coins_you_own.setOrigin(Align.center);
		
        settings_get_bullets_buy.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){	
			//we pressed the buy buy button that we can buy
			if(!current_bought && CanBuy()){
				if(!still_working_buying){
					if(base_menu.settings.settings.sounds_on)
						base_menu.ammo_gun_load.play(SND_VOL.SOUND_BUY_AMMO);
					switch(selected_spaceship){
					case one:
						level_base_bullets_bought.getLabel().setText("-100 Ammo-");
						settings_for_data.settings.number_coins -=
								settings_for_data.settings.BULLETS100_PRICE;
						settings_for_data.settings.number_ammo += 100;
						settings_for_data.settings.were_currently_limited_on_ammo = false;
						settings_for_data.settings.counter_ammo_base += 100;
						break;
					case two:
						level_base_bullets_bought.getLabel().setText("-250 Ammo-");
						settings_for_data.settings.number_coins -=
								settings_for_data.settings.BULLETS250_PRICE;
						settings_for_data.settings.number_ammo += 250;
						settings_for_data.settings.were_currently_limited_on_ammo = false;
						settings_for_data.settings.counter_ammo_base += 250;
						break;
					case three:
						level_base_bullets_bought.getLabel().setText("-500 Ammo-");
						settings_for_data.settings.number_coins -=
								settings_for_data.settings.BULLETS500_PRICE;
						settings_for_data.settings.number_ammo += 500;
						settings_for_data.settings.were_currently_limited_on_ammo = false;
						settings_for_data.settings.counter_ammo_base += 500;
						break;
					case four:
						level_base_bullets_bought.getLabel().setText("-1000 Ammo-");
						settings_for_data.settings.number_coins -=
								settings_for_data.settings.BULLETS1000_PRICE;
						settings_for_data.settings.number_ammo += 1000;
						settings_for_data.settings.were_currently_limited_on_ammo = false;
						settings_for_data.settings.counter_ammo_base += 1000;
						break;
					default:
						break;
					}
					still_working_buying = true;
					settings_for_data.settings.were_currently_limited_on_ammo = false;
					settings_get_bullets_buy.addAction(sequence(Actions.scaleTo(1.5f, 1.5f, 1.0f),
							Actions.scaleTo(1.0f, 1.0f, 6.0f)));
					settings_get_bullets_buy.addAction(sequence(moveBy(0f, -12f, 0.6f),
							moveBy(0f, 12f, 0.4f)));
					get_bullets_table_bought.clearActions();
					settings_get_bullets_buy.addAction(sequence(Actions.fadeOut(8.7f),
							Actions.fadeIn(5.0f), Actions.run(new Runnable(){
							public void run(){

							}
					})));
					if(DoneBuying()){
						get_ammo_ammo_t.addAction(fadeOut(0.45f));
						level_base_coins.getLabel().setText(Integer
								.toString(settings_for_data.settings.number_coins)
								+ " COINS");
						level_base_coins_ammo.getLabel().setText(Integer
								.toString(settings_for_data.settings.counter_ammo_base)
								+ " Bullet-Slots");
						level_base_bullets_bought.clearActions();
						level_base_bullets_you_own_bought.clearActions();
						level_base_coins.addAction(sequence(
								Actions.scaleTo(0.0f, 1f, 1.5f, Swing.swingIn),
								Actions.scaleTo(1.0f, 1f, 2.5f, Swing.swingOut)));
						level_base_coins_you_own.addAction(sequence(
								Actions.scaleTo(0.0f, 1f, 1.5f, Swing.swingIn),
								Actions.scaleTo(1.0f, 1f, 2.5f, Swing.swingOut)));
						level_base_bullets_bought.addAction(parallel(
								Actions.fadeIn(1.0f),
								Actions.scaleTo(1.3f, 1.3f, 1.0f),
								sequence(moveBy(0f, 12f, 0.3f),
										Actions.rotateBy(360f, 2.4f, Swing.circleOut),
										moveBy(0f, -12f, 0.3f),
										run(new Runnable(){
											public void run(){
												get_ammo_ammo_t.addAction(sequence(
														Actions.delay(2f),
														Actions.fadeIn(2.25f),
														Actions.run(new Runnable(){
															public void run(){
																still_working_buying = false;
															}
												})));
												level_base_bullets_bought.addAction(parallel(
														Actions.scaleTo(1.0f, 1.0f, 1.0f),
														sequence(Actions.fadeOut(5.25f),
														Actions.run(new Runnable(){
															public void run(){

															}
												}))));
											}
						}))));
						level_base_bullets_you_own_bought.addAction(parallel(
								Actions.fadeIn(1.0f),
								Actions.scaleTo(1.3f, 1.3f, 1.0f),
								sequence(moveBy(0f, 12f, 0.3f),
									Actions.rotateBy(360f, 2.4f, Swing.circleOut),
									moveBy(0f, -12f, 0.3f), run(new Runnable(){
										public void run(){
											level_base_bullets_you_own_bought.addAction(parallel(
													Actions.scaleTo(1.0f, 1.0f, 1.0f),
													Actions.fadeOut(5.25f)));
										}
						}))));
						settings_for_data.WriteJson();
						base_menu.cryo_game.SAVE_GAME_TO_CLOUD();
					}
				} else {
				}
			//we pressed the buy buy button that we CAN'T but because of not enough coins
			} else if(!current_bought && !CanBuy()){
				if(!still_working_buying){
					if(base_menu.settings.settings.sounds_on)
						base_menu.error_sound_no.play(SND_VOL.ERROR_VOL);
					still_working_buying = true;
					settings_get_bullets_buy.addAction(sequence(moveBy(0f, -12f, 0.6f),
							moveBy(0f, 12f, 0.4f)));
					get_ammo_ammo_t.addAction(sequence(Actions.fadeOut(0.50f),
							Actions.delay(4f),
							Actions.fadeIn(6.00f),
							Actions.run(new Runnable(){
								public void run(){
									still_working_buying = false;
								}
					})));
					TransitError("You need more coins!", true);
				}
			//we pressed the buy buy button but we already bought the spaceship
			} else if(current_bought){
				if(base_menu.settings.settings.sounds_on)
					base_menu.green_already_have.play(SND_VOL.GREEN_HAVE_VOL);
				settings_get_bullets_buy.addAction(sequence(moveBy(0f, -12f, 0.6f),
						moveBy(0f, 12f, 0.4f)));
				TransitError("Already bought it!", true);
			}
			}	
		});              
	} 
	
	public void InitBulletsError(TextureAtlas atlas, Skin skin){		
    	//INIT the owned tables
		get_bullets_table_error = new Table();
		//this stack contains the owned stuff
		settings_get_bullets_error = new Stack();		
		get_bullets_table_error.add(settings_get_bullets_error)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f * 85f / 384f);
		get_bullets_table_error.setFillParent(true);
		get_bullets_table_error.setTransform(true);
		get_bullets_table_error.bottom().padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/5.41f);
		get_bullets_table_error.addAction(moveBy(0f,-25f,0f)); 
		level_base_bullets_t_error = new Table();
		level_base_bullets_t_error.setTransform(true);
		level_base_bullets_error = new TextButton("5545 bullets", skin, "space_error2");	
		level_base_bullets_t_error.setFillParent(true);
		level_base_bullets_t_error.add(level_base_bullets_error)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.15f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.15f * 68f / 400f);
		level_base_bullets_t_error.bottom();
		settings_get_bullets_error.add(level_base_bullets_t_error);
		level_base_bullets_you_own_t_error = new Table();
		level_base_bullets_you_own_t_error.setFillParent(true);
		level_base_bullets_you_own_error = new Image(new
				TextureRegionDrawable(atlas.findRegion("error")));
		level_base_bullets_you_own_t_error.add(level_base_bullets_you_own_error)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f * 80f / 256f)
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 50f);
		level_base_bullets_you_own_t_error.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/6.55f);
		settings_get_bullets_error.add(level_base_bullets_you_own_t_error);
		level_base_bullets_you_own_error.addAction(Actions.alpha(0f));
		level_base_bullets_error.addAction(Actions.alpha(0f));
        stage_get_bullets_dialog.addActor(get_bullets_table_error);  
	} 	

	public void TransitError(String string, boolean error){
		level_base_bullets_error.getLabel().setColor(Color.GREEN);
		level_base_bullets_error.getLabel().setText(string);
		level_base_bullets_error.setTransform(true);
		get_bullets_table_error.clearActions();
		level_base_bullets_error.setOrigin(Align.center);
		level_base_bullets_you_own_error.setOrigin(Align.center);		
		level_base_bullets_error.clearActions();
		level_base_bullets_you_own_error.clearActions();
		
		level_base_bullets_error.addAction(parallel(
				Actions.fadeIn(1.0f),
				Actions.scaleTo(1.3f, 1.3f, 1.0f),
				sequence(moveBy(0f, 12f, 0.3f),
						Actions.rotateBy(360f, 2.4f, Swing.circleOut),
						moveBy(0f, -12f, 0.3f),
						run(new Runnable(){
							public void run(){
								level_base_bullets_error.addAction(parallel(
										Actions.scaleTo(1.0f, 1.0f, 1.0f),
										sequence( Actions.fadeOut(6.0f),
												Actions.run(new Runnable(){
													public void run(){
														//still_working_buying = false;
													}
								}))));
							}
		}))));
		
		level_base_bullets_you_own_error.addAction(parallel(
				Actions.fadeIn(1.0f),
				Actions.scaleTo(1.3f, 1.3f, 1.0f),
				sequence(moveBy(0f, 12f, 0.3f),
						Actions.rotateBy(360f, 2.4f, Swing.circleOut),
						moveBy(0f, -12f, 0.3f),
						run(new Runnable(){
							public void run(){
								level_base_bullets_you_own_error.addAction(parallel(
										Actions.scaleTo(1.0f, 1.0f, 1.0f),
										Actions.fadeOut(6.0f)));
							}
		}))));		
	}

	public void TransitErrorCancel(){
		level_base_bullets_error.clearActions();
		level_base_bullets_you_own_error.clearActions();
		level_base_bullets_error.invalidateHierarchy();
		level_base_bullets_you_own_error.invalidateHierarchy();
		level_base_bullets_error.setRotation(0f);
		level_base_bullets_you_own_error.setRotation(0f);
		level_base_bullets_error.addAction(fadeOut(0f));
		level_base_bullets_you_own_error.addAction(fadeOut(0f));
		still_working_buying = false;

		level_base_coins_you_own.clearActions();
		level_base_bullets_bought.clearActions();
		get_ammo_ammo_t.clearActions();
		level_base_bullets_bought.clearActions();
		level_base_bullets_you_own_bought.clearActions();
		level_base_bullets_you_own_bought.clearActions();
	}

	public boolean CanBuy(){
    	switch(selected_spaceship){
    	case one:
    		if(settings_for_data.settings.number_coins >= settings_for_data
					.settings.BULLETS100_PRICE)
    		{
    			return true;
    		}
    		break;
    	case two:
    		if(settings_for_data.settings.number_coins >= settings_for_data
					.settings.BULLETS250_PRICE)
    		{
    			return true;
    		}   		
    		break;
    	case three:
    		if(settings_for_data.settings.number_coins >= settings_for_data
					.settings.BULLETS500_PRICE)
    		{
    			return true;
    		}   		 		
    		break;  
    	case four:
    		if(settings_for_data.settings.number_coins >= settings_for_data
					.settings.BULLETS1000_PRICE)
    		{
    			return true;
    		}   		 		
    		break;     		
		default:
			break;
    	}				
		return false;
	}	

	public boolean DoneBuying(){
		return true;
	}
	
	public void UpdateCoinsHave(){
		level_base_coins.getLabel().setText(Integer.toString(settings_for_data
				.settings.number_coins) + " COINS");
	}

	public void UpdateAmmoSlotsHave(){
		level_base_coins_ammo.getLabel().setText(Integer
				.toString(settings_for_data.settings.counter_ammo_base)
				+ " Ammo");
	}

    public void LoadBulletsBuys(SelectedSpaceship space_selected){
    	selected_spaceship = space_selected;
    	switch(space_selected){
    	case one:
            get_bullets_buybullets1.addAction(Actions.alpha(1f));
            get_bullets_buybullets2.addAction(Actions.alpha(0f));
            get_bullets_buybullets3.addAction(Actions.alpha(0f));
            get_bullets_buybullets4.addAction(Actions.alpha(0f));
    		break;
    	case two:
            get_bullets_buybullets1.addAction(Actions.alpha(0f));
            get_bullets_buybullets2.addAction(Actions.alpha(1f));
            get_bullets_buybullets3.addAction(Actions.alpha(0f));
            get_bullets_buybullets4.addAction(Actions.alpha(0f));
    		break;
    	case three:
            get_bullets_buybullets1.addAction(Actions.alpha(0f));
            get_bullets_buybullets2.addAction(Actions.alpha(0f));
            get_bullets_buybullets3.addAction(Actions.alpha(1f));
            get_bullets_buybullets4.addAction(Actions.alpha(0f));
    		break;
    	case four:
            get_bullets_buybullets1.addAction(Actions.alpha(0f));
            get_bullets_buybullets2.addAction(Actions.alpha(0f));
            get_bullets_buybullets3.addAction(Actions.alpha(0f));     
            get_bullets_buybullets4.addAction(Actions.alpha(1f));
    		break;      		
		default:
			break;
    	}
    }
    
    public void PrevAmmoBuy(){
        get_bullets_dialog.addAction(sequence(Actions.scaleTo(1.05f, 1.05f, 0.2f),
				Actions.scaleTo(1.0f, 1.0f, 0.3f)));
    	switch(selected_spaceship){
    	case one:
            get_bullets_buybullets4.addAction(Actions.fadeIn(2f));
            get_bullets_buybullets1.addAction(Actions.fadeOut(2f));
            get_bullets_buybullets1.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.four;
    		break;
    	case two:
            get_bullets_buybullets1.addAction(Actions.fadeIn(2f));
            get_bullets_buybullets2.addAction(Actions.fadeOut(2f));
            get_bullets_buybullets2.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.one;
    		break;
    	case three:
            get_bullets_buybullets2.addAction(Actions.fadeIn(2f));
            get_bullets_buybullets3.addAction(Actions.fadeOut(2f));
            get_bullets_buybullets3.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.two;
    		break; 
    	case four:
            get_bullets_buybullets3.addAction(Actions.fadeIn(2f));
            get_bullets_buybullets4.addAction(Actions.fadeOut(2f));
            get_bullets_buybullets4.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.three;
    		break;    	    		
    	default:
    		break;
    	}
    }
    
    public void NextAmmoBuy(){
        get_bullets_dialog.addAction(sequence(Actions.scaleTo(1.05f, 1.05f, 0.2f),
				Actions.scaleTo(1.0f, 1.0f, 0.3f)));
    	switch(selected_spaceship){
    	case one:
            get_bullets_buybullets2.addAction(Actions.fadeIn(2f));
            get_bullets_buybullets1.addAction(Actions.fadeOut(2f));
            get_bullets_buybullets1.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.two;
    		break;
    	case two:
            get_bullets_buybullets3.addAction(Actions.fadeIn(2f));
            get_bullets_buybullets2.addAction(Actions.fadeOut(2f));
            get_bullets_buybullets2.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.three;
    		break;
    	case three:
            get_bullets_buybullets4.addAction(Actions.fadeIn(2f));
            get_bullets_buybullets3.addAction(Actions.fadeOut(2f));
            get_bullets_buybullets3.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.four;
    		break;
    	case four:
            get_bullets_buybullets1.addAction(Actions.fadeIn(2f));
            get_bullets_buybullets4.addAction(Actions.fadeOut(2f));
            get_bullets_buybullets4.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.one;
    		break;      		
    	default:
    		break;
    	}
    }    
    
	public void InitBulletsBuys(TextureAtlas atlas){
		spaceship_buys = new ArrayList<Spaceship>();
		Spaceship ammo100 = new Spaceship(true, true, true);
		ammo100.image = new Image(new TextureRegionDrawable(atlas.findRegion("100_ammo")));
		ammo100.image.setOrigin(Align.center);
		Spaceship ammo250 = new Spaceship(false, true, false);
		ammo250.image = new Image(new TextureRegionDrawable(atlas.findRegion("250_ammo")));
		ammo250.image.setOrigin(Align.center);
		Spaceship ammo500 = new Spaceship(true, true, false);
		ammo500.image = new Image(new TextureRegionDrawable(atlas.findRegion("500_ammo")));
		ammo500.image.setOrigin(Align.center);
		Spaceship ammo1000 = new Spaceship(true, true, false);
		ammo1000.image = new Image(new TextureRegionDrawable(atlas.findRegion("1000_ammo")));
		ammo1000.image.setOrigin(Align.center);
		spaceship_buys.add(ammo100);
		spaceship_buys.add(ammo250);
		spaceship_buys.add(ammo500);
		spaceship_buys.add(ammo1000);
	}    
    
    public void Render(float delta){
	    stage_get_bullets_dialog.act(delta*SPEED_BULLETS);
	    stage_get_bullets_dialog.draw();
    }

    public class Spaceship{
    	public boolean bought;
    	public boolean can_buy;
    	public boolean selected;
    	public Image image;
    	public Spaceship(boolean bought_can, boolean can_buy_can, boolean selected_can){
    		this.bought = bought_can;
    		this.can_buy = can_buy_can;
    		this.selected = selected_can;
    	}
    }

	public void Dispose(){
		if(get_bullets_dialog!=null)
			get_bullets_dialog.clear();
		if(get_bullets_table_done!=null)
			get_bullets_table_done.clear();
		if(get_bullets_table_left_right!=null)
			get_bullets_table_left_right.clear();
		if(get_bullets_table_buy_use!=null)
			get_bullets_table_buy_use.clear();
		if(get_bullets_table_back!=null)
			get_bullets_table_back.clear();
		if(get_bullets_buybullets1!=null)
			get_bullets_buybullets1.clear();
		if(get_bullets_buybullets2!=null)
			get_bullets_buybullets2.clear();
		if(get_bullets_buybullets3!=null)
			get_bullets_buybullets3.clear();
		if(get_bullets_buybullets4!=null)
			get_bullets_buybullets4.clear();
		if(get_bullets_money_t!=null)
			get_bullets_money_t.clear();
		if(level_base_coins_t!=null)
			level_base_coins_t.clear();
		if(level_base_coins_you_own_t!=null)
			level_base_coins_you_own_t.clear();
		if(get_bullets_table_bought!=null)
			get_bullets_table_bought.clear();
		if(level_base_bullets_t_bought!=null)
			level_base_bullets_t_bought.clear();
		if(level_base_bullets_you_own_t_bought!=null)
			level_base_bullets_you_own_t_bought.clear();
		if(get_bullets_table_error!=null)
			get_bullets_table_error.clear();
		if(level_base_bullets_t_error!=null)
			level_base_bullets_t_error.clear();
		if(level_base_bullets_you_own_t_error!=null)
			level_base_bullets_you_own_t_error.clear();
		if(get_ammo_ammo_t!=null)
			get_ammo_ammo_t.clear();
		if(level_base_coins_t_ammo!=null)
			level_base_coins_t_ammo.clear();
		if(level_base_coins_you_own_t_ammo!=null)
			level_base_coins_you_own_t_ammo.clear();
		if(get_bullets_image_back!=null)
			get_bullets_image_back.clear();
		if(get_bullets_image_spacehip1!=null)
			get_bullets_image_spacehip1.clear();
		if(get_bullets_image_spacehip2!=null)
			get_bullets_image_spacehip2.clear();
		if(get_bullets_image_spacehip3!=null)
			get_bullets_image_spacehip3.clear();
		if(get_bullets_image_spacehip4!=null)
			get_bullets_image_spacehip4.clear();
		if(get_bullets_image_money!=null)
			get_bullets_image_money.clear();
		if(level_base_coins_you_own!=null)
			level_base_coins_you_own.clear();
		if(level_base_bullets_you_own_bought!=null)
			level_base_bullets_you_own_bought.clear();
		if(level_base_bullets_you_own_error!=null)
			level_base_bullets_you_own_error.clear();
		if(level_base_bullets_you_own_info!=null)
			level_base_bullets_you_own_info.clear();
		if(get_ammo_image_ammo!=null)
			get_ammo_image_ammo.clear();
		if(level_base_coins_you_own_ammo!=null)
			level_base_coins_you_own_ammo.clear();
		if(settings_get_bullets_done!=null)
			settings_get_bullets_done.clear();
		if(settings_get_bullets_left!=null)
			settings_get_bullets_left.clear();
		if(settings_get_bullets_right!=null)
			settings_get_bullets_right.clear();
		if(settings_get_bullets_buy!=null)
			settings_get_bullets_buy.clear();
		if(settings_get_bullets_use!=null)
			settings_get_bullets_use.clear();
		if(level_base_coins!=null)
			level_base_coins.clear();
		if(level_base_bullets_bought!=null)
			level_base_bullets_bought.clear();
		if(level_base_bullets_error!=null)
			level_base_bullets_error.clear();
		if(level_base_coins_ammo!=null)
			level_base_coins_ammo.clear();
		if(settings_get_bullets_money_s!=null)
			settings_get_bullets_money_s.clear();
		if(settings_get_bullets_stack!=null)
			settings_get_bullets_stack.clear();
		if(settings_get_ammo_ammo_s!=null)
			settings_get_ammo_ammo_s.clear();
		if(settings_get_ammo_money_s_ammo!=null)
			settings_get_ammo_money_s_ammo.clear();
		if(settings_get_bullets_bought!=null)
			settings_get_bullets_bought.clear();
		if(settings_get_bullets_error!=null)
			settings_get_bullets_error.clear();

		get_bullets_dialog = null;
		get_bullets_table_done = null;
		get_bullets_table_left_right = null;
		get_bullets_table_buy_use = null;
		get_bullets_table_back = null;
		get_bullets_buybullets1 = null;
		get_bullets_buybullets2 = null;
		get_bullets_buybullets3 = null;
		get_bullets_buybullets4 = null;
		get_bullets_money_t = null;
		level_base_coins_t = null;
		level_base_coins_you_own_t = null;
		get_bullets_table_bought = null;
		level_base_bullets_t_bought = null;
		level_base_bullets_you_own_t_bought = null;
		get_bullets_table_error = null;
		level_base_bullets_t_error = null;
		level_base_bullets_you_own_t_error = null;
		get_ammo_ammo_t = null;
		level_base_coins_t_ammo = null;
		level_base_coins_you_own_t_ammo = null;
		get_bullets_image_back = null;
		get_bullets_image_spacehip1 = null;
		get_bullets_image_spacehip2 = null;
		get_bullets_image_spacehip3 = null;
		get_bullets_image_spacehip4 = null;
		get_bullets_image_money = null;
		level_base_coins_you_own = null;
		level_base_bullets_you_own_bought = null;
		level_base_bullets_you_own_error = null;
		level_base_bullets_you_own_info = null;
		get_ammo_image_ammo = null;
		level_base_coins_you_own_ammo = null;
		settings_get_bullets_done = null;
		settings_get_bullets_left = null;
		settings_get_bullets_right = null;
		settings_get_bullets_buy = null;
		settings_get_bullets_use = null;
		level_base_coins = null;
		level_base_bullets_bought = null;
		level_base_bullets_error = null;
		level_base_coins_ammo = null;
		settings_get_bullets_money_s = null;
		settings_get_bullets_stack = null;
		settings_get_ammo_ammo_s = null;
		settings_get_ammo_money_s_ammo = null;
		settings_get_bullets_bought = null;
		settings_get_bullets_error = null;

		settings_get_bullets_buy_style = null;
		settings_get_bullets_bought_style = null;
		settings_get_bullets_use_style = null;
		settings_get_bullets_use_noactive_style = null;
		settings_get_bullets_use_selected_style = null;

		if(stage_get_bullets_dialog!=null){
			stage_get_bullets_dialog.clear();
			stage_get_bullets_dialog.dispose();
			stage_get_bullets_dialog = null;
		}
		base_menu = null;
		selected_spaceship = null;
		settings_for_data = null;
		if(spaceship_buys!=null){
			for(int i=0; i<spaceship_buys.size(); i++){
				spaceship_buys.get(i).image = null;
			}
			spaceship_buys.clear();
			spaceship_buys = null;
		}
	}
}
