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

public class ex01MenuScreenGetLifeslots {
	public static final float SPEED_LIFESLOTS = 3f;
    public static final float width = ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f;
    public static final float height = ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f * 278f/430f;
	public Table get_lifeslots_dialog;
	public Table get_lifeslots_table_done;
	public Table get_lifeslots_table_left_right;
	public Table get_lifeslots_table_buy_use;
	public Table get_lifeslots_table_back;
	public Table get_lifeslots_buylifeslots1;
	public Table get_lifeslots_buylifeslots2;
	public Table get_lifeslots_buylifeslots3;
	public Table get_lifeslots_buylifeslots4;
	public Table get_lifeslots_money_t;
	public Table level_base_coins_t;
	public Table level_base_coins_you_own_t;
    public Image get_lifeslots_image_back;   
    public Image get_lifeslots_image_spacehip1;
    public Image get_lifeslots_image_spacehip2;
    public Image get_lifeslots_image_spacehip3;
    public Image get_lifeslots_image_spacehip4;
    public Image get_lifeslots_image_money;
	public Image level_base_coins_you_own;
    public ImageButton settings_get_lifeslots_done;
    public ImageButton settings_get_lifeslots_left;
    public ImageButton settings_get_lifeslots_right;
    public ImageButton settings_get_lifeslots_buy;
    public ImageButton settings_get_lifeslots_use;
    public ex01JSONSettingsLoader settings_for_data;
    public ImageButton.ImageButtonStyle settings_get_lifeslots_buy_style;
    public ImageButton.ImageButtonStyle settings_get_lifeslots_bought_style;
    public ImageButton.ImageButtonStyle settings_get_lifeslots_use_style;
    public ImageButton.ImageButtonStyle settings_get_lifeslots_use_noactive_style;
    public ImageButton.ImageButtonStyle settings_get_lifeslots_use_selected_style;
    public TextButton level_base_coins;
	public Stage stage_get_lifeslots_dialog;   
	public Stack settings_get_lifeslots_money_s;
    public Stack settings_get_lifeslots_stack;
	public ex01MenuScreen base_menu;
	public ArrayList<Spaceship> spaceship_buys;
    public enum SelectedSpaceship {one, two, three, four};
    public SelectedSpaceship selected_spaceship = SelectedSpaceship.one;
	//bought stuff
	public Stack settings_get_lifeslots_bought;
	public Table get_lifeslots_table_bought;
	public Table level_base_lifeslots_t_bought;
	public TextButton level_base_lifeslots_bought;
	public Table level_base_lifeslots_you_own_t_bought;
	public Image level_base_lifeslots_you_own_bought;
	public boolean still_working_buying = false;
	public boolean current_bought = false;
	//error stuff
	public Stack settings_get_lifeslots_error;
	public Table get_lifeslots_table_error;
	public Table level_base_lifeslots_t_error;
	public TextButton level_base_lifeslots_error;
	public Table level_base_lifeslots_you_own_t_error;
	public Image level_base_lifeslots_you_own_error;
	public Image level_base_lifeslots_you_own_info;
	//owned life-slots
    public Image get_lifeslots_image_lifeslots;
    public Image level_base_coins_you_own_lifeslots;
    public Stack settings_get_lifeslots_lifeslots_s;
    public Stack settings_get_lifeslots_money_s_lifeslots;
    public Table get_lifeslots_lifeslots_t;
    public Table level_base_coins_t_lifeslots;
    public Table level_base_coins_you_own_t_lifeslots;
    public TextButton level_base_coins_lifeslots;
    public boolean still_working_on_done_press = false;
	
    public ex01MenuScreenGetLifeslots(ex01JSONSettingsLoader settings,
									  TextureAtlas atlas,
									  Skin skin,
									  ex01MenuScreen bmenu,
									  Viewport viewport) {
    	stage_get_lifeslots_dialog = new Stage(viewport);
    	base_menu = bmenu;
    	settings_for_data = settings;
    	InitLifeslotsBuys(atlas, skin);
    	InitMainElements(atlas, skin);
    	InitCoinsOwned(atlas, skin);
    	InitLifeslotsBought(atlas, skin); 	
       	InitLifeslotsError(atlas, skin); 
       	InitLifeslotsOwned(atlas, skin);
    }  
    
	public void InitLifeslotsOwned(TextureAtlas atlas, Skin skin){	
		get_lifeslots_image_lifeslots = new Image(new
				TextureRegionDrawable(atlas.findRegion("lifeslotshave")));
		settings_get_lifeslots_lifeslots_s = new Stack();      
		get_lifeslots_lifeslots_t = new Table();
		get_lifeslots_lifeslots_t.add(settings_get_lifeslots_lifeslots_s)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f * 85f / 384f);
        get_lifeslots_lifeslots_t.setFillParent(true);
        get_lifeslots_lifeslots_t.bottom().padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 7.30f);
		get_lifeslots_lifeslots_t.addAction(moveBy(0f,-25f,0f));
		level_base_coins_lifeslots = new TextButton(Integer
				.toString(settings_for_data.settings.counter_life_base) +
				" LIFESLOTS", skin, "life_have");
		level_base_coins_t_lifeslots = new Table();
		level_base_coins_t_lifeslots.setTransform(true);	
		level_base_coins_t_lifeslots.setFillParent(true);
		level_base_coins_t_lifeslots.add(level_base_coins_lifeslots)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f * 85f / 384f);
		settings_get_lifeslots_lifeslots_s.add(level_base_coins_t_lifeslots);
		level_base_coins_you_own_lifeslots = new Image(new
				TextureRegionDrawable(atlas.findRegion("your life2")));
		level_base_coins_you_own_t_lifeslots = new Table();		
		level_base_coins_you_own_t_lifeslots.add(level_base_coins_you_own_lifeslots)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.35f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.35f * 80f / 256f);
		level_base_coins_you_own_t_lifeslots.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 5.10f);
		settings_get_lifeslots_lifeslots_s.add(level_base_coins_you_own_t_lifeslots);
        stage_get_lifeslots_dialog.addActor(get_lifeslots_lifeslots_t);
	}    
    
	public void InitCoinsOwned(TextureAtlas atlas, Skin skin){	
		get_lifeslots_image_money = new Image(new
				TextureRegionDrawable(atlas.findRegion("coins_owned")));
		settings_get_lifeslots_money_s = new Stack();      
		get_lifeslots_money_t = new Table();
		get_lifeslots_money_t.add(settings_get_lifeslots_money_s)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f * 85f / 384f);
        get_lifeslots_money_t.setFillParent(true);
        get_lifeslots_money_t.top().padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 5f);
		get_lifeslots_money_t.addAction(moveBy(0f,-25f,0f));
		level_base_coins = new TextButton(Integer
				.toString(settings_for_data.settings.number_coins) + " COINS", skin, "coins_have");
		level_base_coins_t = new Table();
		level_base_coins_t.setTransform(true);	
		level_base_coins_t.setFillParent(true);
		level_base_coins_t.add(level_base_coins)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f * 85f / 384f);
		settings_get_lifeslots_money_s.add(level_base_coins_t);
		level_base_coins_you_own = new Image(new
				TextureRegionDrawable(atlas.findRegion("your coinsa2")));
		level_base_coins_you_own_t = new Table();		
		level_base_coins_you_own_t.add(level_base_coins_you_own)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.35f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.35f * 80f / 256f);
		level_base_coins_you_own_t.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/5.25f);
		settings_get_lifeslots_money_s.add(level_base_coins_you_own_t);
        stage_get_lifeslots_dialog.addActor(get_lifeslots_money_t);
	}
    
	public void InitMainElements(TextureAtlas atlas, Skin skin){		
    	settings_get_lifeslots_buy_style = skin
				.get("buy_button2", ImageButton.ImageButtonStyle.class);
    	settings_get_lifeslots_stack = new Stack();
        get_lifeslots_dialog = new Table();
        get_lifeslots_dialog.addAction(Actions.moveBy(0f, -50f, 0f));
        get_lifeslots_dialog.setTransform(true);
        get_lifeslots_dialog.addAction(Actions.sizeTo(width, height));
        get_lifeslots_dialog.setPosition(ex01Types.VIRTUAL_SCREEN_WIDTH * 0.5f - width / 2,
				ex01Types.VIRTUAL_SCREEN_HEIGHT * 0.5f - height / 1.8f);
        get_lifeslots_dialog.addAction(Actions.fadeOut(0f));
        get_lifeslots_dialog.add(settings_get_lifeslots_stack);
        //setup all space-crafts
        get_lifeslots_table_back = new Table();
        get_lifeslots_buylifeslots1 = new Table();
        get_lifeslots_buylifeslots2 = new Table();
        get_lifeslots_buylifeslots3 = new Table();
        get_lifeslots_buylifeslots4 = new Table();
        get_lifeslots_table_buy_use = new Table();
        get_lifeslots_buylifeslots1.setTransform(true);
        get_lifeslots_buylifeslots2.setTransform(true);
        get_lifeslots_buylifeslots3.setTransform(true);
        get_lifeslots_buylifeslots4.setTransform(true);
        get_lifeslots_image_back = new Image(skin.getDrawable("replayh")); 
        get_lifeslots_table_back.add(get_lifeslots_image_back)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.1f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.1f * 339f / 512f);
        get_lifeslots_buylifeslots1.add(spaceship_buys.get(0).image)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f * 126f / 256f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 130f);
        get_lifeslots_buylifeslots2.add(spaceship_buys.get(1).image)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f * 126f / 256f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 130f);
        get_lifeslots_buylifeslots3.add(spaceship_buys.get(2).image)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f * 126f / 256f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 130f);
        get_lifeslots_buylifeslots4.add(spaceship_buys.get(3).image)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f * 126f / 256f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 130f);
        settings_get_lifeslots_stack.add(get_lifeslots_table_back);
        settings_get_lifeslots_stack.add(get_lifeslots_buylifeslots1);
        settings_get_lifeslots_stack.add(get_lifeslots_buylifeslots2);
        settings_get_lifeslots_stack.add(get_lifeslots_buylifeslots3);
        settings_get_lifeslots_stack.add(get_lifeslots_buylifeslots4);
        get_lifeslots_buylifeslots1.addAction(Actions.alpha(0f));
        get_lifeslots_buylifeslots2.addAction(Actions.alpha(0f));
        get_lifeslots_buylifeslots3.addAction(Actions.alpha(0f));
        get_lifeslots_buylifeslots4.addAction(Actions.alpha(0f));              
        LoadLifeslotsBuys(SelectedSpaceship.one);
        //setup left right buttons
        settings_get_lifeslots_left = new ImageButton(skin, "left_button");
        settings_get_lifeslots_left.pad(0f);
        settings_get_lifeslots_right = new ImageButton(skin, "right_button");
        settings_get_lifeslots_right.pad(0f);
        get_lifeslots_table_left_right = new Table();
        get_lifeslots_table_left_right.add(settings_get_lifeslots_left)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f * 310f / 128f)
				.padRight(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f);
        get_lifeslots_table_left_right.add(settings_get_lifeslots_right)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/10.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH/10.3f * 310f/128f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH/2.8f);
        //setup buy use buttons  
        settings_get_lifeslots_buy = new ImageButton(settings_get_lifeslots_buy_style);
        get_lifeslots_table_buy_use.add(settings_get_lifeslots_buy)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/3.6f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH/3.6f * 103f/256f)
				.center()
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 105.00f)
				.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 35f);
        get_lifeslots_table_buy_use.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/-1.60f);
        settings_get_lifeslots_buy.pad(0f);      
        //setup done button
        settings_get_lifeslots_done = new ImageButton(skin, "done_button");
        settings_get_lifeslots_done.pad(0f);
        settings_get_lifeslots_done.padBottom(10f);
        get_lifeslots_table_done = new Table();
        get_lifeslots_table_done.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/1.60f);
        get_lifeslots_table_done.add(settings_get_lifeslots_done)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.4f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.4f * 106f / 256f)
				.colspan(2)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 250.00f)
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 10f);
        get_lifeslots_table_done.bottom();
        //setup bounds so we achieve middle for all tables
        settings_get_lifeslots_stack.add(get_lifeslots_table_done);
        settings_get_lifeslots_stack.add(get_lifeslots_table_buy_use);
        settings_get_lifeslots_stack.add(get_lifeslots_table_left_right);
        stage_get_lifeslots_dialog.addActor(get_lifeslots_dialog);
        get_lifeslots_buylifeslots1.pack();
        get_lifeslots_buylifeslots2.pack();
        get_lifeslots_buylifeslots3.pack();
        settings_get_lifeslots_stack.pack();
        get_lifeslots_dialog.pack();
        get_lifeslots_buylifeslots1.setOrigin(get_lifeslots_buylifeslots1.getWidth() / 2f,
				get_lifeslots_buylifeslots1.getHeight() / 2f);
        get_lifeslots_buylifeslots2.setOrigin(get_lifeslots_buylifeslots2.getWidth() / 2f,
				get_lifeslots_buylifeslots2.getHeight() / 2f);
        get_lifeslots_buylifeslots3.setOrigin(get_lifeslots_buylifeslots3.getWidth() / 2f,
				get_lifeslots_buylifeslots3.getHeight() / 2f);
        get_lifeslots_buylifeslots4.setOrigin(get_lifeslots_buylifeslots4.getWidth() / 2f,
				get_lifeslots_buylifeslots4.getHeight() / 2f);
        get_lifeslots_dialog.setOrigin(get_lifeslots_dialog.getWidth() / 2f,
				get_lifeslots_dialog.getHeight() / 2f);
        
        settings_get_lifeslots_done.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!still_working_on_done_press){
					still_working_on_done_press = true;
					if(base_menu.settings.settings.sounds_on)
						base_menu.done_button.play(SND_VOL.DONE_BUTTON_VOLUME);
			        settings_get_lifeslots_done.addAction(sequence(
							moveBy(0f, 12f, 0.1f),
							moveBy(0f, -12f, 0.2f)));
			        get_lifeslots_money_t.addAction(parallel(
							fadeOut(1.25f),
							moveBy(0f,-25f,1.25f,Swing.swingOut)));
			        get_lifeslots_lifeslots_t.addAction(parallel(
							fadeOut(1.25f),
							moveBy(0f,-25f,1.25f,Swing.swingOut)));
					get_lifeslots_dialog.addAction(sequence(
							parallel(fadeOut(1.25f),
									moveBy(0f, -50f, 1.25f, swingIn)),
							run(new Runnable() {
								public void run() {
									base_menu.FadeInSettingsDialog(1.5f);
									base_menu.grayscale_shader_active_settings = true;
									base_menu.grayscale_shader_active_get_lifeslots = false;
									Gdx.input.setInputProcessor(base_menu
											.settings_dialog_screen.stage_settings_dialog);
									still_working_on_done_press = false;
									TransitErrorCancel();
								}
							})));
				}
			}	
		});	
        
        settings_get_lifeslots_left.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(base_menu.settings.settings.sounds_on)
					base_menu.level_left_right_button.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
		        settings_get_lifeslots_left.addAction(sequence(
						moveBy(-12f, 0f, 0.1f),
						moveBy(12f, 0f, 0.2f)));
		        PrevSpaceshipBuy();
			}	
		});	   
        
        settings_get_lifeslots_right.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(base_menu.settings.settings.sounds_on)
					base_menu.level_left_right_button.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
		        settings_get_lifeslots_right.addAction(sequence(
						moveBy(12f, 0f, 0.1f),
						moveBy(-12f, 0f, 0.2f)));
		        NextSpaceshipBuy();
			}	
		});	    		
	}

	public void InitLifeslotsBought(TextureAtlas atlas, Skin skin){		
    	//INIT the owned tables
		get_lifeslots_table_bought = new Table();
		//this stack contains the owned stuff
		settings_get_lifeslots_bought = new Stack();		
		get_lifeslots_table_bought.add(settings_get_lifeslots_bought)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f * 79f / 384f);
		get_lifeslots_table_bought.setFillParent(true);
		get_lifeslots_table_bought.setTransform(true);
		get_lifeslots_table_bought.bottom().padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/5.4f);
		get_lifeslots_table_bought.addAction(moveBy(0f,-25f,0f)); 
		level_base_lifeslots_t_bought = new Table();
		level_base_lifeslots_t_bought.setTransform(true);
		level_base_lifeslots_bought = new TextButton("whatever", skin, "life_have3");
		level_base_lifeslots_t_bought.setFillParent(true);
		level_base_lifeslots_t_bought.add(level_base_lifeslots_bought)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f * 79f / 384f);
		level_base_lifeslots_t_bought.bottom();
		settings_get_lifeslots_bought.add(level_base_lifeslots_t_bought);
		level_base_lifeslots_you_own_t_bought = new Table();
		level_base_lifeslots_you_own_t_bought.setFillParent(true);
		level_base_lifeslots_you_own_bought = new Image(new
				TextureRegionDrawable(atlas.findRegion("your coinsa2bought")));
		level_base_lifeslots_you_own_t_bought.add(level_base_lifeslots_you_own_bought)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 3f * 80f / 256f);
		level_base_lifeslots_you_own_t_bought.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/5.10f);
		settings_get_lifeslots_bought.add(level_base_lifeslots_you_own_t_bought);
		level_base_lifeslots_you_own_bought.addAction(Actions.alpha(0f));
		level_base_lifeslots_bought.addAction(Actions.alpha(0f));
        stage_get_lifeslots_dialog.addActor(get_lifeslots_table_bought);
		settings_get_lifeslots_buy.setTransform(true);
		settings_get_lifeslots_buy.setOrigin(Align.center);

		settings_get_lifeslots_buy.setTransform(true);
		settings_get_lifeslots_buy.setOrigin(Align.center);
		level_base_lifeslots_bought.setTransform(true);
		level_base_lifeslots_bought.setOrigin(Align.center);
		level_base_lifeslots_you_own_bought.setOrigin(Align.center);
		level_base_coins.setTransform(true);
		level_base_coins.setOrigin(Align.center);
		level_base_coins_you_own.setOrigin(Align.center);

		settings_get_lifeslots_buy.addListener( new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y){
			//we pressed the buy buy button that we can buy
			if(!current_bought && CanBuy()){
				if(!still_working_buying){
					if(base_menu.settings.settings.sounds_on)
						base_menu.lifeslots_heartpump.play(SND_VOL.SOUND_BUY_LIFESLOTS);
					switch(selected_spaceship){
					case one:
						level_base_lifeslots_bought.getLabel().setText("-100 Lifeslots-");
						settings_for_data.settings.number_coins -=
								settings_for_data.settings.HEALTH100_PRICE;
						settings_for_data.settings.number_lifeslots += 100;
						settings_for_data.settings.counter_life_base += 100;
						break;
					case two:
						level_base_lifeslots_bought.getLabel().setText("-250 Lifeslots-");
						settings_for_data.settings.number_coins -=
								settings_for_data.settings.HEALTH250_PRICE;
						settings_for_data.settings.number_lifeslots += 250;
						settings_for_data.settings.counter_life_base += 250;
						break;
					case three:
						level_base_lifeslots_bought.getLabel().setText("-500 Lifeslots-");
						settings_for_data.settings.number_coins -=
								settings_for_data.settings.HEALTH500_PRICE;
						settings_for_data.settings.number_lifeslots += 500;
						settings_for_data.settings.counter_life_base += 500;
						break;
					case four:
						level_base_lifeslots_bought.getLabel().setText("-1000 Lifeslots-");
						settings_for_data.settings.number_coins -=
								settings_for_data.settings.HEALTH1000_PRICE;
						settings_for_data.settings.number_lifeslots += 1000;
						settings_for_data.settings.counter_life_base += 1000;
						break;
					default:
						break;
					}
					still_working_buying = true;
					settings_for_data.settings.were_currently_limited_on_life = false;
					settings_get_lifeslots_buy.addAction(sequence(
							Actions.scaleTo(1.5f, 1.5f, 1.0f),
							Actions.scaleTo(1.0f, 1.0f, 6.0f)));
					settings_get_lifeslots_buy.addAction(sequence(
							moveBy(0f, -12f, 0.6f),
							moveBy(0f, 12f, 0.4f)));
					get_lifeslots_table_bought.clearActions();
					settings_get_lifeslots_buy.addAction(sequence(
							Actions.fadeOut(8.7f),
							Actions.fadeIn(5.0f),
							Actions.run(new Runnable(){
						public void run(){

						}
					})));
					if(DoneBuying()){
						get_lifeslots_lifeslots_t.addAction(fadeOut(0.45f));
						level_base_coins.getLabel().setText(Integer
								.toString(settings_for_data.settings.number_coins) + " COINS");
						level_base_coins_lifeslots.getLabel().setText(Integer
								.toString(settings_for_data.settings.counter_life_base) +
								" LIFESLOTS");
						level_base_lifeslots_bought.clearActions();
						level_base_lifeslots_you_own_bought.clearActions();
						level_base_coins.addAction(sequence(
								Actions.scaleTo(0.0f, 1f, 1.5f, Swing.swingIn),
								Actions.scaleTo(1.0f, 1f, 2.5f, Swing.swingOut)));
						level_base_coins_you_own.addAction(sequence(
								Actions.scaleTo(0.0f, 1f, 1.5f, Swing.swingIn),
								Actions.scaleTo(1.0f, 1f, 2.5f, Swing.swingOut)));
						level_base_lifeslots_bought.addAction(parallel(
								Actions.fadeIn(1.0f),
								Actions.scaleTo(1.3f, 1.3f, 1.0f),
								sequence(moveBy(0f, 12f, 0.3f),
										Actions.rotateBy(360f, 2.4f, Swing.circleOut),
										moveBy(0f, -12f, 0.3f),
										run(new Runnable(){
											public void run(){
												get_lifeslots_lifeslots_t.addAction(sequence(
														Actions.delay(2f),
														Actions.fadeIn(2.25f),
														Actions.run(new Runnable(){
															public void run(){
																still_working_buying = false;
															}
												})));
												level_base_lifeslots_bought.addAction(parallel(
														Actions.scaleTo(1.0f, 1.0f, 1.0f),
														sequence(Actions.fadeOut(5.25f),
																Actions.run(new Runnable() {
																	public void run() {
																	}
																}))));
											}
						}))));
						level_base_lifeslots_you_own_bought.addAction(parallel(
								Actions.fadeIn(1.0f),
								Actions.scaleTo(1.3f, 1.3f, 1.0f),
								sequence(moveBy(0f, 12f, 0.3f),
										Actions.rotateBy(360f, 2.4f, Swing.circleOut),
										moveBy(0f, -12f, 0.3f),
										run(new Runnable(){
									public void run(){
										level_base_lifeslots_you_own_bought.addAction(parallel(
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
					settings_get_lifeslots_buy.addAction(sequence(
							moveBy(0f, -12f, 0.6f),
							moveBy(0f, 12f, 0.4f)));
					get_lifeslots_lifeslots_t.addAction(sequence(
							Actions.fadeOut(0.50f),
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
				settings_get_lifeslots_buy.addAction(sequence(
						moveBy(0f, -12f, 0.6f),
						moveBy(0f, 12f, 0.4f)));
				TransitError("Already bought it!", true);
			}
			}	
		});
	} 
	
	public void InitLifeslotsError(TextureAtlas atlas, Skin skin){		
    	//INIT the owned tables
		get_lifeslots_table_error = new Table();
		//this stack contains the owned stuff
		settings_get_lifeslots_error = new Stack();		
		get_lifeslots_table_error.add(settings_get_lifeslots_error)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f * 85f / 384f);
		get_lifeslots_table_error.setFillParent(true);
		get_lifeslots_table_error.setTransform(true);
		get_lifeslots_table_error.bottom().padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/5.41f);
		get_lifeslots_table_error.addAction(moveBy(0f,-25f,0f)); 
		level_base_lifeslots_t_error = new Table();
		level_base_lifeslots_t_error.setTransform(true);
		level_base_lifeslots_error = new TextButton("5545 LIFESLOTS", skin, "space_error2");
		level_base_lifeslots_t_error.setFillParent(true);
		level_base_lifeslots_t_error.add(level_base_lifeslots_error)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.15f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.15f * 68f / 400f);
		level_base_lifeslots_t_error.bottom();
		settings_get_lifeslots_error.add(level_base_lifeslots_t_error);
		level_base_lifeslots_you_own_t_error = new Table();
		level_base_lifeslots_you_own_t_error.setFillParent(true);
		level_base_lifeslots_you_own_error = new Image(new
				TextureRegionDrawable(atlas.findRegion("error")));
		level_base_lifeslots_you_own_t_error.add(level_base_lifeslots_you_own_error)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f * 80f / 256f)
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 50f);
		level_base_lifeslots_you_own_t_error.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/6.55f);
		settings_get_lifeslots_error.add(level_base_lifeslots_you_own_t_error);
		level_base_lifeslots_you_own_error.addAction(Actions.alpha(0f));
		level_base_lifeslots_error.addAction(Actions.alpha(0f));
        stage_get_lifeslots_dialog.addActor(get_lifeslots_table_error);
	} 	

	public void TransitError(String string, boolean error){
		level_base_lifeslots_error.getLabel().setColor(Color.GREEN);
		level_base_lifeslots_error.getLabel().setText(string);
		level_base_lifeslots_error.setTransform(true);
		get_lifeslots_table_error.clearActions();
		level_base_lifeslots_error.setOrigin(Align.center);
		level_base_lifeslots_you_own_error.setOrigin(Align.center);		
		level_base_lifeslots_error.clearActions();
		level_base_lifeslots_you_own_error.clearActions();
		
		level_base_lifeslots_error.addAction(parallel(
				Actions.fadeIn(1.0f),
				Actions.scaleTo(1.3f, 1.3f, 1.0f),
				sequence(moveBy(0f, 12f, 0.3f),
						Actions.rotateBy(360f, 2.4f, Swing.circleOut),
						moveBy(0f, -12f, 0.3f),
						run(new Runnable(){
							public void run(){
								level_base_lifeslots_error.addAction(parallel(
										Actions.scaleTo(1.0f, 1.0f, 1.0f),
										sequence( Actions.fadeOut(6.0f),
												Actions.run(new Runnable(){
													public void run(){
														//still_working_buying = false;
													}
								}))));
							}
		}))));
		
		level_base_lifeslots_you_own_error.addAction(parallel(
				Actions.fadeIn(1.0f),
				Actions.scaleTo(1.3f, 1.3f, 1.0f),
				sequence(moveBy(0f, 12f, 0.3f),
						Actions.rotateBy(360f, 2.4f, Swing.circleOut),
						moveBy(0f, -12f, 0.3f),
						run(new Runnable(){
							public void run(){
								level_base_lifeslots_you_own_error.addAction(parallel(
										Actions.scaleTo(1.0f, 1.0f, 1.0f),
										Actions.fadeOut(6.0f)));
							}
		}))));		
	}

	public void TransitErrorCancel(){
		level_base_lifeslots_error.clearActions();
		level_base_lifeslots_you_own_error.clearActions();
		level_base_lifeslots_error.invalidateHierarchy();
		level_base_lifeslots_you_own_error.invalidateHierarchy();
		level_base_lifeslots_error.setRotation(0f);
		level_base_lifeslots_you_own_error.setRotation(0f);
		level_base_lifeslots_error.addAction(fadeOut(0f));
		level_base_lifeslots_you_own_error.addAction(fadeOut(0f));
		still_working_buying = false;

		level_base_coins_you_own.clearActions();
		level_base_lifeslots_bought.clearActions();
		get_lifeslots_lifeslots_t.clearActions();
		level_base_lifeslots_bought.clearActions();
		level_base_lifeslots_you_own_bought.clearActions();
		level_base_lifeslots_you_own_bought.clearActions();
	}

	public boolean CanBuy(){
    	switch(selected_spaceship){
    	case one:
    		if(settings_for_data.settings.number_coins >=
					settings_for_data.settings.HEALTH100_PRICE)
    		{
    			return true;
    		}
    		break;
    	case two:
    		if(settings_for_data.settings.number_coins >=
					settings_for_data.settings.HEALTH250_PRICE)
    		{
    			return true;
    		}   		
    		break;
    	case three:
    		if(settings_for_data.settings.number_coins >=
					settings_for_data.settings.HEALTH500_PRICE)
    		{
    			return true;
    		}   		 		
    		break;  
    	case four:
    		if(settings_for_data.settings.number_coins >=
					settings_for_data.settings.HEALTH1000_PRICE)
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
	
    public void LoadLifeslotsBuys(SelectedSpaceship space_selected){
    	selected_spaceship = space_selected;
    	switch(space_selected){
    	case one:
            get_lifeslots_buylifeslots1.addAction(Actions.alpha(1f));
            get_lifeslots_buylifeslots2.addAction(Actions.alpha(0f));
            get_lifeslots_buylifeslots3.addAction(Actions.alpha(0f));
            get_lifeslots_buylifeslots4.addAction(Actions.alpha(0f));  	
    		break;
    	case two:
            get_lifeslots_buylifeslots1.addAction(Actions.alpha(0f));
            get_lifeslots_buylifeslots2.addAction(Actions.alpha(1f));
            get_lifeslots_buylifeslots3.addAction(Actions.alpha(0f));
            get_lifeslots_buylifeslots4.addAction(Actions.alpha(0f));
    		break;
    	case three:
            get_lifeslots_buylifeslots1.addAction(Actions.alpha(0f));
            get_lifeslots_buylifeslots2.addAction(Actions.alpha(0f));
            get_lifeslots_buylifeslots3.addAction(Actions.alpha(1f));
            get_lifeslots_buylifeslots4.addAction(Actions.alpha(0f));
    		break;  
    	case four:
            get_lifeslots_buylifeslots1.addAction(Actions.alpha(0f));
            get_lifeslots_buylifeslots2.addAction(Actions.alpha(0f));
            get_lifeslots_buylifeslots3.addAction(Actions.alpha(0f));
            get_lifeslots_buylifeslots4.addAction(Actions.alpha(1f));
    		break;      		
		default:
			break;
    	}
    }
    
    public void PrevSpaceshipBuy(){
        get_lifeslots_dialog.addAction(sequence(
				Actions.scaleTo(1.05f, 1.05f, 0.2f),
				Actions.scaleTo(1.0f, 1.0f, 0.3f)));
    	switch(selected_spaceship){
    	case one:
            get_lifeslots_buylifeslots4.addAction(Actions.fadeIn(2f));
            get_lifeslots_buylifeslots1.addAction(Actions.fadeOut(2f));
            get_lifeslots_buylifeslots1.addAction(sequence(
					Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.four;
    		break;
    	case two:
            get_lifeslots_buylifeslots1.addAction(Actions.fadeIn(2f));
            get_lifeslots_buylifeslots2.addAction(Actions.fadeOut(2f));
            get_lifeslots_buylifeslots2.addAction(sequence(
					Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.one;
    		break;
    	case three:
            get_lifeslots_buylifeslots2.addAction(Actions.fadeIn(2f));
            get_lifeslots_buylifeslots3.addAction(Actions.fadeOut(2f));
            get_lifeslots_buylifeslots3.addAction(sequence(
					Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.two;
    		break;    	
    	case four:
            get_lifeslots_buylifeslots3.addAction(Actions.fadeIn(2f));
            get_lifeslots_buylifeslots4.addAction(Actions.fadeOut(2f));
            get_lifeslots_buylifeslots4.addAction(sequence(
					Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.three;
    		break;     		
    	default:
    		break;
    	}
    }
    
    public void NextSpaceshipBuy(){
        get_lifeslots_dialog.addAction(sequence(
				Actions.scaleTo(1.05f, 1.05f, 0.2f),
				Actions.scaleTo(1.0f, 1.0f, 0.3f)));
    	switch(selected_spaceship){
    	case one:
            get_lifeslots_buylifeslots2.addAction(Actions.fadeIn(2f));
            get_lifeslots_buylifeslots1.addAction(Actions.fadeOut(2f));
            get_lifeslots_buylifeslots1.addAction(sequence(
					Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.two;
    		break;
    	case two:
            get_lifeslots_buylifeslots3.addAction(Actions.fadeIn(2f));
            get_lifeslots_buylifeslots2.addAction(Actions.fadeOut(2f));
            get_lifeslots_buylifeslots2.addAction(sequence(
					Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.three;
    		break;
    	case three:
            get_lifeslots_buylifeslots4.addAction(Actions.fadeIn(2f));
            get_lifeslots_buylifeslots3.addAction(Actions.fadeOut(2f));
            get_lifeslots_buylifeslots3.addAction(sequence(
					Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.four;
    		break;
    	case four:
            get_lifeslots_buylifeslots1.addAction(Actions.fadeIn(2f));
            get_lifeslots_buylifeslots4.addAction(Actions.fadeOut(2f));
            get_lifeslots_buylifeslots4.addAction(sequence(
					Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.one;
    		break;   
    	default:
    		break;
    	}
    }    
    
	public void UpdateCoinsHave(){
		level_base_coins.getLabel().setText(Integer
				.toString(settings_for_data.settings.number_coins) + " COINS");
	}

	public void UpdateLifeSlotsHave(){
		level_base_coins_lifeslots.getLabel().setText(Integer
				.toString(settings_for_data.settings.counter_life_base) +
				" Life");
	}
    
	public void InitLifeslotsBuys(TextureAtlas atlas, Skin skin){
		spaceship_buys = new ArrayList<Spaceship>();
		Spaceship life1 = new Spaceship(true, true, true);
		life1.image = new Image(new TextureRegionDrawable(atlas.findRegion("100_lifeslots")));
		life1.image.setOrigin(Align.center);
		Spaceship life2 = new Spaceship(false, true, false);
		life2.image = new Image(new TextureRegionDrawable(atlas.findRegion("250_lifeslots")));
		life2.image.setOrigin(Align.center);
		Spaceship life3 = new Spaceship(true, true, false);
		life3.image = new Image(new TextureRegionDrawable(atlas.findRegion("500_lifeslots")));
		life3.image.setOrigin(Align.center);
		Spaceship life4 = new Spaceship(true, true, false);
		life4.image = new Image(new TextureRegionDrawable(atlas.findRegion("1000_lifeslots")));
		life4.image.setOrigin(Align.center);
		spaceship_buys.add(life1);
		spaceship_buys.add(life2);
		spaceship_buys.add(life3);
		spaceship_buys.add(life4);
	}
    
    public void Render(float delta){
		stage_get_lifeslots_dialog.act(delta*SPEED_LIFESLOTS);
		stage_get_lifeslots_dialog.draw();
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
		if(get_lifeslots_dialog!=null)
			get_lifeslots_dialog.clear();
		if(get_lifeslots_table_done!=null)
			get_lifeslots_table_done.clear();
		if(get_lifeslots_table_left_right!=null)
			get_lifeslots_table_left_right.clear();
		if(get_lifeslots_table_buy_use!=null)
			get_lifeslots_table_buy_use.clear();
		if(get_lifeslots_table_back!=null)
			get_lifeslots_table_back.clear();
		if(get_lifeslots_buylifeslots1!=null)
			get_lifeslots_buylifeslots1.clear();
		if(get_lifeslots_buylifeslots2!=null)
			get_lifeslots_buylifeslots2.clear();
		if(get_lifeslots_buylifeslots3!=null)
			get_lifeslots_buylifeslots3.clear();
		if(get_lifeslots_buylifeslots4!=null)
			get_lifeslots_buylifeslots4.clear();
		if(get_lifeslots_money_t!=null)
			get_lifeslots_money_t.clear();
		if(level_base_coins_t!=null)
			level_base_coins_t.clear();
		if(level_base_coins_you_own_t!=null)
			level_base_coins_you_own_t.clear();
		if(level_base_lifeslots_you_own_t_bought!=null)
			level_base_lifeslots_you_own_t_bought.clear();
		if(get_lifeslots_table_bought!=null)
			get_lifeslots_table_bought.clear();
		if(level_base_lifeslots_t_bought!=null)
			level_base_lifeslots_t_bought.clear();
		if(get_lifeslots_table_error!=null)
			get_lifeslots_table_error.clear();
		if(level_base_lifeslots_t_error!=null)
			level_base_lifeslots_t_error.clear();
		if(level_base_lifeslots_you_own_t_error!=null)
			level_base_lifeslots_you_own_t_error.clear();
		if(get_lifeslots_lifeslots_t!=null)
			get_lifeslots_lifeslots_t.clear();
		if(level_base_coins_t_lifeslots!=null)
			level_base_coins_t_lifeslots.clear();
		if(level_base_coins_you_own_t_lifeslots!=null)
			level_base_coins_you_own_t_lifeslots.clear();
		if(get_lifeslots_image_back!=null
				) get_lifeslots_image_back.clear();
		if(get_lifeslots_image_spacehip1!=null)
			get_lifeslots_image_spacehip1.clear();
		if(get_lifeslots_image_spacehip2!=null)
			get_lifeslots_image_spacehip2.clear();
		if(get_lifeslots_image_spacehip3!=null)
			get_lifeslots_image_spacehip3.clear();
		if(get_lifeslots_image_spacehip4!=null)
			get_lifeslots_image_spacehip4.clear();
		if(get_lifeslots_image_money!=null)
			get_lifeslots_image_money.clear();
		if(level_base_coins_you_own!=null)
			level_base_coins_you_own.clear();
		if(level_base_lifeslots_you_own_bought!=null)
			level_base_lifeslots_you_own_bought.clear();
		if(level_base_lifeslots_you_own_error!=null)
			level_base_lifeslots_you_own_error.clear();
		if(level_base_lifeslots_you_own_info!=null)
			level_base_lifeslots_you_own_info.clear();
		if(get_lifeslots_image_lifeslots!=null)
			get_lifeslots_image_lifeslots.clear();
		if(level_base_coins_you_own_lifeslots!=null)
			level_base_coins_you_own_lifeslots.clear();
		if(settings_get_lifeslots_done!=null)
			settings_get_lifeslots_done.clear();
		if(settings_get_lifeslots_left!=null)
			settings_get_lifeslots_left.clear();
		if(settings_get_lifeslots_right!=null)
			settings_get_lifeslots_right.clear();
		if(settings_get_lifeslots_buy!=null)
			settings_get_lifeslots_buy.clear();
		if(settings_get_lifeslots_use!=null)
			settings_get_lifeslots_use.clear();
		if(level_base_lifeslots_bought!=null)
			level_base_lifeslots_bought.clear();
		if(level_base_lifeslots_error!=null)
			level_base_lifeslots_error.clear();
		if(level_base_coins_lifeslots!=null)
			level_base_coins_lifeslots.clear();
		if(level_base_coins!=null)
			level_base_coins.clear();
		if(settings_get_lifeslots_money_s!=null)
			settings_get_lifeslots_money_s.clear();
		if(settings_get_lifeslots_stack!=null)
			settings_get_lifeslots_stack.clear();
		if(settings_get_lifeslots_bought!=null)
			settings_get_lifeslots_bought.clear();
		if(settings_get_lifeslots_error!=null)
			settings_get_lifeslots_error.clear();
		if(settings_get_lifeslots_lifeslots_s!=null)
			settings_get_lifeslots_lifeslots_s.clear();
		if(settings_get_lifeslots_money_s_lifeslots!=null)
			settings_get_lifeslots_money_s_lifeslots.clear();

		get_lifeslots_dialog = null;
		get_lifeslots_table_done = null;
		get_lifeslots_table_left_right = null;
		get_lifeslots_table_buy_use = null;
		get_lifeslots_table_back = null;
		get_lifeslots_buylifeslots1 = null;
		get_lifeslots_buylifeslots2 = null;
		get_lifeslots_buylifeslots3 = null;
		get_lifeslots_buylifeslots4 = null;
		get_lifeslots_money_t = null;
		level_base_coins_t = null;
		level_base_coins_you_own_t = null;
		level_base_lifeslots_you_own_t_bought = null;
		get_lifeslots_table_bought = null;
		level_base_lifeslots_t_bought = null;
		get_lifeslots_table_error = null;
		level_base_lifeslots_t_error = null;
		level_base_lifeslots_you_own_t_error = null;
		get_lifeslots_lifeslots_t = null;
		level_base_coins_t_lifeslots = null;
		level_base_coins_you_own_t_lifeslots = null;
		get_lifeslots_image_back = null;
		get_lifeslots_image_spacehip1 = null;
		get_lifeslots_image_spacehip2 = null;
		get_lifeslots_image_spacehip3 = null;
		get_lifeslots_image_spacehip4 = null;
		get_lifeslots_image_money = null;
		level_base_coins_you_own = null;
		level_base_lifeslots_you_own_bought = null;
		level_base_lifeslots_you_own_error = null;
		level_base_lifeslots_you_own_info = null;
		get_lifeslots_image_lifeslots = null;
		level_base_coins_you_own_lifeslots = null;
		settings_get_lifeslots_done = null;
		settings_get_lifeslots_left = null;
		settings_get_lifeslots_right = null;
		settings_get_lifeslots_buy = null;
		settings_get_lifeslots_use = null;
		level_base_lifeslots_bought = null;
		level_base_lifeslots_error = null;
		level_base_coins_lifeslots = null;
		level_base_coins = null;
		settings_get_lifeslots_money_s = null;
		settings_get_lifeslots_stack = null;
		settings_get_lifeslots_bought = null;
		settings_get_lifeslots_error = null;
		settings_get_lifeslots_lifeslots_s = null;
		settings_get_lifeslots_money_s_lifeslots = null;		

		settings_get_lifeslots_buy_style  = null;
		settings_get_lifeslots_bought_style  = null;
		settings_get_lifeslots_use_style  = null;
		settings_get_lifeslots_use_noactive_style  = null;
		settings_get_lifeslots_use_selected_style  = null;

		if(stage_get_lifeslots_dialog!=null){
			stage_get_lifeslots_dialog.clear();
			stage_get_lifeslots_dialog.dispose();
			stage_get_lifeslots_dialog = null;
		}
		settings_for_data = null;
		base_menu = null;
		selected_spaceship = null;
		if(spaceship_buys!=null){
			for(int i=0; i<spaceship_buys.size(); i++){
				spaceship_buys.get(i).image = null;
			}
			spaceship_buys.clear();
			spaceship_buys = null;
		}
	}
}
