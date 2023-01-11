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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

public class ex01MenuScreenGetSpacecraftDialog {
	public static final float SPEED_SPACECRAFT = 3f;
    public static final float width = ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f;
    public static final float height = ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f * 278f/430f;
	public Table get_spacecraft_dialog;
	public Table get_spacecraft_table_done;
	public Table get_spacecraft_table_left_right;
	public Table get_spacecraft_table_buy_use;
	public Table get_spacecraft_table_back;
	public Table get_spacecraft_buyspacecraft1;
	public Table get_spacecraft_buyspacecraft2;
	public Table get_spacecraft_buyspacecraft3;
	public Table get_spacecraft_money_t;
	public Table level_base_coins_t;
	public Table level_base_coins_you_own_t;
    public Image get_spacecraft_image_back;   
    public Image get_spacecraft_image_spacehip1;
    public Image get_spacecraft_image_spacehip2;
    public Image get_spacecraft_image_spacehip3;
    public Image get_spacecraft_image_money;
	public Image level_base_coins_you_own;
    public ImageButton settings_get_spacecraft_done;
    public ImageButton settings_get_spacecraft_left;
    public ImageButton settings_get_spacecraft_right;
    public ImageButton settings_get_spacecraft_buy;
    public ImageButton settings_get_spacecraft_use;
    public ImageButton.ImageButtonStyle settings_get_spacecraft_buy_style;
    public ImageButton.ImageButtonStyle settings_get_spacecraft_bought_style;
    public ImageButton.ImageButtonStyle settings_get_spacecraft_use_style;
    public ImageButton.ImageButtonStyle settings_get_spacecraft_use_noactive_style;
    public ImageButton.ImageButtonStyle settings_get_spacecraft_use_selected_style;
    public TextButton level_base_coins;
	public Stage stage_get_spacecraft_dialog;   
	public Stack settings_get_spacecraft_money_s;
    public Stack settings_get_spacecraft_stack;
	public ex01MenuScreen base_menu;
	public ArrayList<Spaceship> spaceship_buys;
    public enum SelectedSpaceship {one, two, three};
    public SelectedSpaceship selected_spaceship = SelectedSpaceship.one;
    public ex01JSONSettingsLoader settings_for_data;
    //bought stuff
	public Stack settings_get_spacecraft_bought;
	public Table get_spacecraft_table_bought;
	public Table level_base_spacecraft_t_bought;
	public TextButton level_base_spacecraft_bought;
	public Table level_base_spacecraft_you_own_t_bought;
	public Image level_base_spacecraft_you_own_bought;
	public boolean still_working_buying = false;
	public boolean current_bought = false;
	public boolean current_using = false;
	public boolean current_cannot_use = false;
	//error and info stuff
	public Stack settings_get_spacecraft_error;
	public Table get_spacecraft_table_error;
	public Table level_base_spacecraft_t_error;
	public TextButton level_base_spacecraft_error;
	public TextButton.TextButtonStyle textb1;
	public TextButton.TextButtonStyle textb2ok;
	public Table level_base_spacecraft_you_own_t_error;
	public Image level_base_spacecraft_you_own_error;
	public Image level_base_spacecraft_you_own_info;
	public boolean still_working_button_standard = false;
    public boolean still_working_on_done_press = false;
	
    public ex01MenuScreenGetSpacecraftDialog(ex01JSONSettingsLoader settings,
											 TextureAtlas atlas,
											 Skin skin,
											 ex01MenuScreen bmenu,
											 Viewport viewport) {
    	stage_get_spacecraft_dialog = new Stage(viewport);
    	base_menu = bmenu;
    	settings_for_data = settings;  	
    	InitSpaceshipBuys(atlas);
    	InitMainElements(skin);
    	InitCoinsOwned(atlas, skin);
    	InitSpaceshipBought(atlas, skin); 	
       	InitSpaceshipError(atlas, skin); 	
    }
    
	public void InitSpaceshipBuys(TextureAtlas atlas){
		spaceship_buys = new ArrayList<Spaceship>();
		Spaceship cryo10 = new Spaceship(
				settings_for_data.settings.bought_cryozl10,
				settings_for_data.settings.can_buy_cryozl10,
				settings_for_data.settings.selected_cryozl10);
		cryo10.image = new Image(new TextureRegionDrawable(atlas.findRegion("spacecraft00")));
		cryo10.image.setOrigin(Align.center);
		Spaceship cryo12 = new Spaceship(
				settings_for_data.settings.bought_cryozl12,
				settings_for_data.settings.can_buy_cryozl12,
				settings_for_data.settings.selected_cryozl12);
		cryo12.image = new Image(new TextureRegionDrawable(atlas.findRegion("spacecraft01")));
		cryo12.image.setOrigin(Align.center);
		Spaceship cryo14 = new Spaceship(
				settings_for_data.settings.bought_cryozl14,
				settings_for_data.settings.can_buy_cryozl14,
				settings_for_data.settings.selected_cryozl14);
		cryo14.image = new Image(new TextureRegionDrawable(atlas.findRegion("spacecraft02")));
		cryo14.image.setOrigin(Align.center);
		spaceship_buys.add(cryo10);
		spaceship_buys.add(cryo12);
		spaceship_buys.add(cryo14);
	}
	
	public void InitMainElements(Skin skin){
    	settings_get_spacecraft_stack = new Stack();
    	get_spacecraft_dialog = new Table();
    	get_spacecraft_table_back = new Table();
        get_spacecraft_dialog.setTransform(true);
        get_spacecraft_dialog.addAction(Actions.fadeOut(0f));
        get_spacecraft_dialog.add(settings_get_spacecraft_stack);
        get_spacecraft_dialog.addAction(Actions.moveBy(0f,-50f,0f));
        get_spacecraft_dialog.setTransform(true);
        get_spacecraft_dialog.addAction(Actions.sizeTo(width, height));
        get_spacecraft_dialog.setPosition(ex01Types.VIRTUAL_SCREEN_WIDTH * 0.5f - width/2,
				ex01Types.VIRTUAL_SCREEN_HEIGHT * 0.5f - height/1.8f);
        //multiple styles for buy, bought and other buttons
    	settings_get_spacecraft_buy_style = skin
				.get("buy_button", ImageButton.ImageButtonStyle.class);
    	settings_get_spacecraft_bought_style = skin
				.get("bought_button", ImageButton.ImageButtonStyle.class);
    	settings_get_spacecraft_use_style = skin
				.get("use_button", ImageButton.ImageButtonStyle.class);
    	settings_get_spacecraft_use_noactive_style = skin
				.get("use_button_noactive", ImageButton.ImageButtonStyle.class);
    	settings_get_spacecraft_use_selected_style = skin
				.get("use_button_selected", ImageButton.ImageButtonStyle.class);
        //setup all space-crafts
        get_spacecraft_buyspacecraft1 = new Table();
        get_spacecraft_buyspacecraft2 = new Table();
        get_spacecraft_buyspacecraft3 = new Table();
        get_spacecraft_buyspacecraft1.setTransform(true);
        get_spacecraft_buyspacecraft2.setTransform(true);
        get_spacecraft_buyspacecraft3.setTransform(true);
        get_spacecraft_buyspacecraft1.add(spaceship_buys.get(0).image)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f * 126f / 256f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 130f);
        get_spacecraft_buyspacecraft2.add(spaceship_buys.get(1).image)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f * 126f / 256f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 130f);
        get_spacecraft_buyspacecraft3.add(spaceship_buys.get(2).image)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f * 126f / 256f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 130f);
        get_spacecraft_image_back = new Image(skin.getDrawable("replayh"));        
        get_spacecraft_table_back.add(get_spacecraft_image_back)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.1f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.1f * 339f / 512f);
        settings_get_spacecraft_stack.add(get_spacecraft_table_back);
        settings_get_spacecraft_stack.add(get_spacecraft_buyspacecraft1);
        settings_get_spacecraft_stack.add(get_spacecraft_buyspacecraft2);
        settings_get_spacecraft_stack.add(get_spacecraft_buyspacecraft3);
        get_spacecraft_buyspacecraft1.addAction(Actions.alpha(0f));
        get_spacecraft_buyspacecraft2.addAction(Actions.alpha(0f));
        get_spacecraft_buyspacecraft3.addAction(Actions.alpha(0f));    
        LoadSpaceshipBuys(SelectedSpaceship.one);
        //setup left right buttons
        settings_get_spacecraft_left = new ImageButton(skin, "left_button");
        settings_get_spacecraft_left.pad(0f);
        settings_get_spacecraft_right = new ImageButton(skin, "right_button");
        settings_get_spacecraft_right.pad(0f);
        get_spacecraft_table_left_right = new Table();
        get_spacecraft_table_left_right.add(settings_get_spacecraft_left)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f * 310f / 128f)
				.padRight(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f);
        get_spacecraft_table_left_right.add(settings_get_spacecraft_right)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f * 310f / 128f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f);
        //setup buy use buttons   
        settings_get_spacecraft_buy = new ImageButton(settings_get_spacecraft_buy_style);
        settings_get_spacecraft_buy.pad(0f);
        settings_get_spacecraft_use = new ImageButton(settings_get_spacecraft_use_style);
        settings_get_spacecraft_use.pad(0f);
        get_spacecraft_table_buy_use = new Table();
        get_spacecraft_table_buy_use.add(settings_get_spacecraft_buy)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.1f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.1f * 103f / 256f).left();
        get_spacecraft_table_buy_use.add(settings_get_spacecraft_use)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.1f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.1f * 103f / 256f).right();
        get_spacecraft_table_buy_use.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/-1.60f);
        get_spacecraft_table_buy_use.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH/125f);
        get_spacecraft_table_buy_use.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH/17.50f);
        //setup done button
        settings_get_spacecraft_done = new ImageButton(skin, "done_button");
        settings_get_spacecraft_done.pad(0f);
        settings_get_spacecraft_done.padBottom(10f);        
        get_spacecraft_table_done = new Table();
        get_spacecraft_table_done.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/1.60f);
        get_spacecraft_table_done.add(settings_get_spacecraft_done)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/3.4f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH/3.4f * 106f/256f)
				.colspan(2)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 250.00f)
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 10f);
        get_spacecraft_table_done.bottom();
        //process the stacks and stages        
        settings_get_spacecraft_stack.add(get_spacecraft_table_done);
        settings_get_spacecraft_stack.add(get_spacecraft_table_buy_use);
        settings_get_spacecraft_stack.add(get_spacecraft_table_left_right);        
        stage_get_spacecraft_dialog.addActor(get_spacecraft_dialog);
        //calculate the bounds so we achieve middle for all tables
        get_spacecraft_buyspacecraft1.pack();
        get_spacecraft_buyspacecraft2.pack();
        get_spacecraft_buyspacecraft3.pack();
        get_spacecraft_dialog.pack();
        get_spacecraft_dialog.setOrigin(get_spacecraft_dialog.getWidth() / 2f,
				get_spacecraft_dialog.getHeight() / 2f);
        get_spacecraft_buyspacecraft1.setOrigin(get_spacecraft_buyspacecraft1.getWidth() / 2f,
				get_spacecraft_buyspacecraft1.getHeight() / 2f);
        get_spacecraft_buyspacecraft2.setOrigin(get_spacecraft_buyspacecraft2.getWidth() / 2f,
				get_spacecraft_buyspacecraft2.getHeight() / 2f);
        get_spacecraft_buyspacecraft3.setOrigin(get_spacecraft_buyspacecraft3.getWidth() / 2f,
				get_spacecraft_buyspacecraft3.getHeight() / 2f);
        
        settings_get_spacecraft_done.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!still_working_on_done_press){
					still_working_on_done_press = true;
					if(base_menu.settings.settings.sounds_on)
						base_menu.done_button.play(SND_VOL.DONE_BUTTON_VOLUME);
			        settings_get_spacecraft_done.addAction(sequence(
							moveBy(0f, 12f, 0.1f),
							moveBy(0f, -12f, 0.2f)));
			        get_spacecraft_money_t.addAction(parallel(
							fadeOut(1.25f),
							moveBy(0f,-25f,1.25f,Swing.swingOut)));
					get_spacecraft_dialog.addAction(sequence(
							parallel(fadeOut(1.25f),
									moveBy(0f,-50f,1.25f,swingIn)),
							run(new Runnable() {
								public void run() {
									base_menu.FadeInSettingsDialog(1.5f);
									base_menu.grayscale_shader_active_settings = true;
									base_menu.grayscale_shader_active_get_spacecraft = false;
									Gdx.input.setInputProcessor(
										base_menu.settings_dialog_screen.stage_settings_dialog);
									still_working_on_done_press = false;
									TransitErrorCancel();
								}
							})));
				}
			}	
		});	
        
        settings_get_spacecraft_left.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(base_menu.settings.settings.sounds_on)
					base_menu.level_left_right_button.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
		        settings_get_spacecraft_left.addAction(sequence(
						moveBy(-12f, 0f, 0.1f),
						moveBy(12f, 0f, 0.2f)));
		        PrevSpaceshipBuy();
		        ProcessBoughtUseButtons();
			}	
		});	   
        
        settings_get_spacecraft_right.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(base_menu.settings.settings.sounds_on)
					base_menu.level_left_right_button.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
		        settings_get_spacecraft_right.addAction(sequence(
						moveBy(12f, 0f, 0.1f),
						moveBy(-12f, 0f, 0.2f)));
		        NextSpaceshipBuy();
		        ProcessBoughtUseButtons();
			}	
		});	  
        
        ProcessBoughtUseButtons();		
	}
	
	public void InitCoinsOwned(TextureAtlas atlas, Skin skin){		
		get_spacecraft_image_money = new Image(new
				TextureRegionDrawable(atlas.findRegion("coins_owned")));
		settings_get_spacecraft_money_s = new Stack();      
		get_spacecraft_money_t = new Table();
		get_spacecraft_money_t.add(settings_get_spacecraft_money_s)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f * 85f / 384f);
        get_spacecraft_money_t.setFillParent(true);
        get_spacecraft_money_t.top().padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 5f);
		get_spacecraft_money_t.addAction(moveBy(0f,-25f,0f));
		level_base_coins = new TextButton(Integer
				.toString(settings_for_data.settings.number_coins) + " COINS", skin, "coins_have");
		level_base_coins_t = new Table();
		level_base_coins_t.setTransform(true);	
		level_base_coins_t.setFillParent(true);
		level_base_coins_t.add(level_base_coins)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/1.25f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f * 85f / 384f);
		settings_get_spacecraft_money_s.add(level_base_coins_t);
		level_base_coins_you_own = new Image(new
				TextureRegionDrawable(atlas.findRegion("your coinsa2")));
		level_base_coins_you_own_t = new Table();		
		level_base_coins_you_own_t.add(level_base_coins_you_own)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.35f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.35f * 80f / 256f);
		level_base_coins_you_own_t.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/5.25f);
		settings_get_spacecraft_money_s.add(level_base_coins_you_own_t);
        stage_get_spacecraft_dialog.addActor(get_spacecraft_money_t);
	}
	
	public void InitSpaceshipBought(TextureAtlas atlas, Skin skin){		
    	//INIT the owned tables
		get_spacecraft_table_bought = new Table();
		//this stack contains the owned stuff
		settings_get_spacecraft_bought = new Stack();		
		get_spacecraft_table_bought.add(settings_get_spacecraft_bought)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f * 85f / 384f);
		get_spacecraft_table_bought.setFillParent(true);
		get_spacecraft_table_bought.setTransform(true);
		get_spacecraft_table_bought.bottom().padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/5f);
		get_spacecraft_table_bought.addAction(moveBy(0f,-25f,0f)); 
		level_base_spacecraft_t_bought = new Table();
		level_base_spacecraft_t_bought.setTransform(true);
		level_base_spacecraft_bought = new TextButton("whatever", skin, "space_have2");	
		level_base_spacecraft_t_bought.setFillParent(true);
		level_base_spacecraft_t_bought.add(level_base_spacecraft_bought)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f * 85f / 384f);
		level_base_spacecraft_t_bought.bottom();
		settings_get_spacecraft_bought.add(level_base_spacecraft_t_bought);
		level_base_spacecraft_you_own_t_bought = new Table();
		level_base_spacecraft_you_own_t_bought.setFillParent(true);
		level_base_spacecraft_you_own_bought = new Image(new
				TextureRegionDrawable(atlas.findRegion("your coinsa2bought")));
		level_base_spacecraft_you_own_t_bought.add(level_base_spacecraft_you_own_bought)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 3f * 80f / 256f);
		level_base_spacecraft_you_own_t_bought.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/5.10f);
		settings_get_spacecraft_bought.add(level_base_spacecraft_you_own_t_bought);
		level_base_spacecraft_you_own_bought.addAction(Actions.alpha(0f));
		level_base_spacecraft_bought.addAction(Actions.alpha(0f));
        stage_get_spacecraft_dialog.addActor(get_spacecraft_table_bought);
		settings_get_spacecraft_buy.setTransform(true);
		settings_get_spacecraft_buy.setOrigin(Align.center);

		settings_get_spacecraft_buy.setTransform(true);
		settings_get_spacecraft_buy.setOrigin(Align.center);
		level_base_spacecraft_bought.setTransform(true);
		level_base_spacecraft_bought.setOrigin(Align.center);
		level_base_spacecraft_you_own_bought.setOrigin(Align.center);
		level_base_coins.setTransform(true);
		level_base_coins.setOrigin(Align.center);
		level_base_coins_you_own.setOrigin(Align.center);

        settings_get_spacecraft_buy.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){	
				//we pressed the buy buy button that we can buy
				if(!current_bought && CanBuy()){
					if(!still_working_buying){
						if(base_menu.settings.settings.sounds_on)
							base_menu.level_left_right_button.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
				    	switch(selected_spaceship){
				    	case one:
				    		level_base_spacecraft_bought.getLabel().setText("-ZFL10-");
				    		break;
				    	case two:
				    		level_base_spacecraft_bought.getLabel().setText("-ZFL12-");
				    		settings_for_data.settings.number_coins -=
									settings_for_data.settings.CRYO12_PRICE;
				    		settings_for_data.settings.bought_cryozl12 = true;
				    		settings_for_data.settings.can_select_cryozl12 = true;
				    		spaceship_buys.get(1).bought = true;
				    		break;
				    	case three:
				    		level_base_spacecraft_bought.getLabel().setText("-ZFL14-");
				    		settings_for_data.settings.number_coins -=
									settings_for_data.settings.CRYO14_PRICE;
				    		settings_for_data.settings.bought_cryozl14 = true;
				    		settings_for_data.settings.can_select_cryozl14 = true;
				    		spaceship_buys.get(2).bought = true;
				    		break;     		
						default:
							break;
				    	}						
						still_working_buying = true;
						settings_get_spacecraft_buy.addAction(sequence(
								Actions.scaleTo(1.5f, 1.5f, 1.0f),
								Actions.scaleTo(1.0f, 1.0f, 6.0f)));
						settings_get_spacecraft_buy.addAction(sequence(
								moveBy(0f, -12f, 0.6f),
								moveBy(0f, 12f, 0.4f)));
						get_spacecraft_table_bought.clearActions();
						settings_get_spacecraft_buy.addAction(sequence(
								Actions.fadeOut(8.7f),
								Actions.fadeIn(5.0f),
								Actions.run(new Runnable(){
									public void run(){
										still_working_buying = false;
									}
						})));
						if(DoneBuying()){
							if(base_menu.settings.settings.is_this_in_need_for_load_saved) {
								base_menu.settings.settings.bought_when_in_need_load_game = true;
							}
							level_base_coins.getLabel().setText(Integer
									.toString(settings_for_data.settings.number_coins) + " COINS");
							level_base_spacecraft_bought.clearActions();
							level_base_spacecraft_you_own_bought.clearActions();
							level_base_coins.addAction(sequence(
									Actions.scaleTo(0.0f, 1f, 1.5f, Swing.swingIn),
									Actions.scaleTo(1.0f, 1f, 2.5f, Swing.swingOut)));
							level_base_coins_you_own.addAction(sequence(
									Actions.scaleTo(0.0f, 1f, 1.5f, Swing.swingIn),
									Actions.scaleTo(1.0f, 1f, 2.5f, Swing.swingOut)));
							level_base_spacecraft_bought.addAction(parallel(
									Actions.fadeIn(1.0f),
									Actions.scaleTo(1.3f, 1.3f, 1.0f),
									sequence(moveBy(0f, 12f, 0.3f),
											Actions.rotateBy(360f, 2.4f, Swing.circleOut),
											moveBy(0f, -12f, 0.3f),
											run(new Runnable() {
												public void run() {
													level_base_spacecraft_bought.addAction(
															parallel(
															Actions.scaleTo(1.0f, 1.0f, 1.0f),
															sequence(Actions.fadeOut(5.25f),
																	Actions.run(new Runnable() {
																		public void run() {
																		}
																	}))));
												}
											}))));
							level_base_spacecraft_you_own_bought.addAction(parallel(
									Actions.fadeIn(1.0f),
									Actions.scaleTo(1.3f, 1.3f, 1.0f),
									sequence(moveBy(0f, 12f, 0.3f),
											Actions.rotateBy(360f, 2.4f, Swing.circleOut),
											moveBy(0f, -12f, 0.3f),
											run(new Runnable(){
												public void run(){
													level_base_spacecraft_you_own_bought
														.addAction(parallel(
																Actions.scaleTo(1.0f, 1.0f, 1.0f),
																Actions.fadeOut(5.25f)));
												}
							}))));		
							settings_for_data.WriteJson();
							base_menu.cryo_game.SAVE_GAME_TO_CLOUD();
							ProcessBoughtUseButtons();
						}
					} else {				
					}
				//we pressed the buy buy button that we CAN'T but because of not enough coins	
				} else if(!current_bought && !CanBuy()){
					if(!still_working_button_standard && !still_working_buying){
						if(base_menu.settings.settings.sounds_on)
							base_menu.error_sound_no.play(SND_VOL.ERROR_VOL);
						still_working_button_standard = true;
						settings_get_spacecraft_buy.addAction(sequence(
								moveBy(0f, -12f, 0.6f),
								moveBy(0f, 12f, 0.4f)));
						level_base_spacecraft_error.setStyle(textb1);
						TransitError("You need more coins!", true);
					}
				//we pressed the buy buy button but we already bought the spaceship	
				} else if(current_bought){
					if(!still_working_button_standard && !still_working_buying){
						if(base_menu.settings.settings.sounds_on)
							base_menu.green_already_have.play(SND_VOL.GREEN_HAVE_VOL);
						still_working_button_standard = true;
						settings_get_spacecraft_buy.addAction(sequence(
								moveBy(0f, -12f, 0.6f),
								moveBy(0f, 12f, 0.4f)));
						level_base_spacecraft_error.setStyle(textb2ok);
						TransitError("Already bought it!", true);
					}
				}
			}	
		});              
        settings_get_spacecraft_use.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!still_working_buying && !still_working_button_standard){
					if(base_menu.settings.settings.sounds_on)
						base_menu.green_already_have.play(SND_VOL.GREEN_HAVE_VOL);
					still_working_button_standard = true;
					settings_get_spacecraft_use.addAction(sequence(
							moveBy(0f, -12f, 0.1f),
							moveBy(0f, 12f, 0.2f)));
					//you need to buy the space-craft first 
					if(!current_bought){
						level_base_spacecraft_error.setStyle(textb1);
						TransitError("Buy it first!", true);
					//already selected buddy	
					} else if(current_bought && current_using){
						level_base_spacecraft_error.setStyle(textb2ok);
						TransitError("Already in use now!", false);					
					//ok we select it now	
					} else if(current_bought && !current_using){
						level_base_spacecraft_error.setStyle(textb2ok);
						TransitError("SELECTED for use!", false);
				    	switch(selected_spaceship){
				    	case one:
							spaceship_buys.get(0).selected = true;
							spaceship_buys.get(1).selected = false;
							spaceship_buys.get(2).selected = false;
							settings_for_data.settings.selected_cryozl10 = true;
							settings_for_data.settings.selected_cryozl12 = false;
							settings_for_data.settings.selected_cryozl14 = false;
							settings_for_data.settings.index_spaceship_selected = 0;
				    		break;
				    	case two:
							spaceship_buys.get(0).selected = false;
							spaceship_buys.get(1).selected = true;
							spaceship_buys.get(2).selected = false;
							settings_for_data.settings.selected_cryozl10 = false;
							settings_for_data.settings.selected_cryozl12 = true;
							settings_for_data.settings.selected_cryozl14 = false;
							settings_for_data.settings.index_spaceship_selected = 1;
				    		break;
				    	case three:
							spaceship_buys.get(0).selected = false;
							spaceship_buys.get(1).selected = false;
							spaceship_buys.get(2).selected = true;
							settings_for_data.settings.selected_cryozl10 = false;
							settings_for_data.settings.selected_cryozl12 = false;
							settings_for_data.settings.selected_cryozl14 = true;
							settings_for_data.settings.index_spaceship_selected = 2;
				    		break;     		
						default:
							break;
				    	}				
						ProcessBoughtUseButtons();
						settings_for_data.WriteJson();
						base_menu.cryo_game.SAVE_GAME_TO_CLOUD();
					}
				}
			}
		});        
	} 
	
	public void UpdateCoinsHave(){
		level_base_coins.getLabel().setText(Integer
				.toString(settings_for_data.settings.number_coins) + " COINS");
	}

	public void UpdateSpacecraftOptions(){
		spaceship_buys.get(0).can_buy = true;
		spaceship_buys.get(0).bought = true;
		spaceship_buys.get(1).can_buy = true;
		spaceship_buys.get(1).bought = true;
		spaceship_buys.get(2).can_buy = true;
		spaceship_buys.get(2).bought = true;
		ProcessBoughtUseButtons();
	}
	
	public void InitSpaceshipError(TextureAtlas atlas, Skin skin){		
    	//INIT the owned tables
		get_spacecraft_table_error = new Table();
		textb1 = new TextButton.TextButtonStyle();
		textb2ok = new TextButton.TextButtonStyle();
		TextureRegion style1 = skin.getRegion("money");
		TextureRegion style2 = skin.getRegion("moneyok");
		textb1.font = skin.getFont("errors-font");
		textb1.up = new TextureRegionDrawable(style1);
		textb2ok.font = skin.getFont("errors-font");
		textb2ok.up = new TextureRegionDrawable(style2);
		//this stack contains the owned stuff
		settings_get_spacecraft_error = new Stack();		
		get_spacecraft_table_error.add(settings_get_spacecraft_error)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f * 85f / 384f);
		get_spacecraft_table_error.setFillParent(true);
		get_spacecraft_table_error.setTransform(true);
		get_spacecraft_table_error.bottom().padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/5.41f);
		get_spacecraft_table_error.addAction(moveBy(0f,-25f,0f)); 
		level_base_spacecraft_t_error = new Table();
		level_base_spacecraft_t_error.setTransform(true);
		level_base_spacecraft_error = new TextButton("tare", textb1);	
		level_base_spacecraft_t_error.setFillParent(true);
		level_base_spacecraft_t_error.add(level_base_spacecraft_error)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.15f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.15f * 68f / 400f);
		level_base_spacecraft_t_error.bottom();
		settings_get_spacecraft_error.add(level_base_spacecraft_t_error);
		level_base_spacecraft_you_own_t_error = new Table();
		level_base_spacecraft_you_own_t_error.setFillParent(true);
		level_base_spacecraft_you_own_error = new Image(new
				TextureRegionDrawable(atlas.findRegion("error")));
		level_base_spacecraft_you_own_t_error.add(level_base_spacecraft_you_own_error)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f * 80f / 256f)
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 50f);
		level_base_spacecraft_you_own_t_error.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/6.55f);
		settings_get_spacecraft_error.add(level_base_spacecraft_you_own_t_error);
		level_base_spacecraft_you_own_error.addAction(Actions.alpha(0f));
		level_base_spacecraft_error.addAction(Actions.alpha(0f));
        stage_get_spacecraft_dialog.addActor(get_spacecraft_table_error);
	} 	
	
	public void TransitError(String string, boolean error){
		level_base_spacecraft_error.getLabel().setColor(Color.GREEN);
		level_base_spacecraft_error.getLabel().setText(string);
		level_base_spacecraft_error.setTransform(true);
		get_spacecraft_table_error.clearActions();
		level_base_spacecraft_error.setOrigin(Align.center);
		level_base_spacecraft_you_own_error.setOrigin(Align.center);		
		level_base_spacecraft_error.clearActions();
		level_base_spacecraft_you_own_error.clearActions();
		
		level_base_spacecraft_error.addAction(parallel(
				Actions.fadeIn(1.0f),
				Actions.scaleTo(1.3f, 1.3f, 1.0f),
				sequence(moveBy(0f, 12f, 0.3f),
						Actions.rotateBy(360f, 2.4f, Swing.circleOut),
						moveBy(0f, -12f, 0.3f),
						run(new Runnable(){
							public void run(){
								level_base_spacecraft_error.addAction(parallel(
										Actions.scaleTo(1.0f, 1.0f, 1.0f),
										sequence(Actions.fadeOut(6.0f),
												Actions.run(new Runnable(){
													public void run(){
														still_working_button_standard = false;
													}
								}))));
							}
		}))));
		level_base_spacecraft_you_own_error.addAction(parallel(
				Actions.fadeIn(1.0f),
				Actions.scaleTo(1.3f, 1.3f, 1.0f),
				sequence(moveBy(0f, 12f, 0.3f),
						Actions.rotateBy(360f, 2.4f, Swing.circleOut),
						moveBy(0f, -12f, 0.3f),
						run(new Runnable(){
							public void run(){
								level_base_spacecraft_you_own_error.addAction(parallel(
										Actions.scaleTo(1.0f, 1.0f, 1.0f),
										Actions.fadeOut(6.0f)));
							}
		}))));		
	}

	public void TransitErrorCancel(){
		level_base_spacecraft_error.clearActions();
		level_base_spacecraft_you_own_error.clearActions();
		level_base_spacecraft_error.invalidateHierarchy();
		level_base_spacecraft_you_own_error.invalidateHierarchy();
		level_base_spacecraft_error.setRotation(0f);
		level_base_spacecraft_you_own_error.setRotation(0f);
		level_base_spacecraft_error.addAction(fadeOut(0f));
		level_base_spacecraft_you_own_error.addAction(fadeOut(0f));
		still_working_buying = false;
		still_working_button_standard = false;
	}

	public boolean DoneBuying(){
		return true;
	}
	
	public boolean CanBuy(){
    	switch(selected_spaceship){
    	case one:
    		if(settings_for_data.settings.number_coins >= settings_for_data.settings.CRYO10_PRICE)
    		{
    			return true;
    		}
    		break;
    	case two:
    		if(settings_for_data.settings.number_coins >= settings_for_data.settings.CRYO12_PRICE)
    		{
    			return true;
    		}   		
    		break;
    	case three:
    		if(settings_for_data.settings.number_coins >= settings_for_data.settings.CRYO14_PRICE)
    		{
    			return true;
    		}   		 		
    		break;  
		default:
			break;
    	}				
		return false;
	}
	
    public void LoadSpaceshipBuys(SelectedSpaceship space_selected){
    	selected_spaceship = space_selected;
    	switch(space_selected){
    	case one:
            get_spacecraft_buyspacecraft1.addAction(Actions.alpha(1f));
            get_spacecraft_buyspacecraft2.addAction(Actions.alpha(0f));
            get_spacecraft_buyspacecraft3.addAction(Actions.alpha(0f));  		
    		break;
    	case two:
            get_spacecraft_buyspacecraft1.addAction(Actions.alpha(0f));
            get_spacecraft_buyspacecraft2.addAction(Actions.alpha(1f));
            get_spacecraft_buyspacecraft3.addAction(Actions.alpha(0f));     		
    		break;
    	case three:
            get_spacecraft_buyspacecraft1.addAction(Actions.alpha(0f));
            get_spacecraft_buyspacecraft2.addAction(Actions.alpha(0f));
            get_spacecraft_buyspacecraft3.addAction(Actions.alpha(1f));     		
    		break;  
		default:
			break;
    	}
    }
    
    public void PrevSpaceshipBuy(){
        get_spacecraft_dialog.addAction(sequence(Actions.scaleTo(1.05f, 1.05f, 0.2f),
				Actions.scaleTo(1.0f, 1.0f, 0.3f)));
    	switch(selected_spaceship){
    	case one:
            get_spacecraft_buyspacecraft3.addAction(Actions.fadeIn(2f));
            get_spacecraft_buyspacecraft1.addAction(Actions.fadeOut(2f));
            get_spacecraft_buyspacecraft1.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.three;
    		break;
    	case two:
            get_spacecraft_buyspacecraft1.addAction(Actions.fadeIn(2f));
            get_spacecraft_buyspacecraft2.addAction(Actions.fadeOut(2f));
            get_spacecraft_buyspacecraft2.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.one;
    		break;
    	case three:
            get_spacecraft_buyspacecraft2.addAction(Actions.fadeIn(2f));
            get_spacecraft_buyspacecraft3.addAction(Actions.fadeOut(2f));
            get_spacecraft_buyspacecraft3.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.two;
    		break;    		
    	default:
    		break;
    	}
    	ProcessBoughtUseButtons();
    }
    
    public void NextSpaceshipBuy(){
        get_spacecraft_dialog.addAction(sequence(Actions.scaleTo(1.05f, 1.05f, 0.2f),
				Actions.scaleTo(1.0f, 1.0f, 0.3f)));
    	switch(selected_spaceship){
    	case one:
            get_spacecraft_buyspacecraft2.addAction(Actions.fadeIn(2f));
            get_spacecraft_buyspacecraft1.addAction(Actions.fadeOut(2f));
            get_spacecraft_buyspacecraft1.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.two;
    		break;
    	case two:
            get_spacecraft_buyspacecraft3.addAction(Actions.fadeIn(2f));
            get_spacecraft_buyspacecraft2.addAction(Actions.fadeOut(2f));
            get_spacecraft_buyspacecraft2.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.three;
    		break;
    	case three:
            get_spacecraft_buyspacecraft1.addAction(Actions.fadeIn(2f));
            get_spacecraft_buyspacecraft3.addAction(Actions.fadeOut(2f));
            get_spacecraft_buyspacecraft3.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.one;
    		break;    		
    	default:
    		break;
    	}
    	ProcessBoughtUseButtons();
    }    
	
    //process buying, bought and other stuff 
    public void ProcessBoughtUseButtons(){
    	switch(selected_spaceship){
    	case one:
    		if(spaceship_buys.get(0).bought){
    			settings_get_spacecraft_buy
						.setStyle(settings_get_spacecraft_bought_style);
    			current_bought = true;
    			if(spaceship_buys.get(0).selected){
    				settings_get_spacecraft_use
							.setStyle(settings_get_spacecraft_use_selected_style);
    				current_using = true;
    			} else {
    				settings_get_spacecraft_use
							.setStyle(settings_get_spacecraft_use_style);
    				current_using = false;
    				current_cannot_use = false;
    			}
    		} else {
    			current_bought = false;
    			current_using = false;
    			current_cannot_use = true;
    			settings_get_spacecraft_buy.setStyle(settings_get_spacecraft_buy_style);
    			settings_get_spacecraft_use.setStyle(settings_get_spacecraft_use_noactive_style);
    		}
    		break;
    	case two:	
    		if(spaceship_buys.get(1).bought){
    			settings_get_spacecraft_buy.setStyle(settings_get_spacecraft_bought_style);
    			current_bought = true;
    			if(spaceship_buys.get(1).selected){
    				current_using = true;
    				settings_get_spacecraft_use
							.setStyle(settings_get_spacecraft_use_selected_style);
    			} else {
    				settings_get_spacecraft_use.setStyle(settings_get_spacecraft_use_style);
    				current_using = false;
    				current_cannot_use = false;
    			}
    		} else {
    			current_bought = false;
    			current_using = false;
    			current_cannot_use = true;
    			settings_get_spacecraft_buy.setStyle(settings_get_spacecraft_buy_style);
    			settings_get_spacecraft_use.setStyle(settings_get_spacecraft_use_noactive_style);
    		}   		
    		break;
    	case three:  
    		if(spaceship_buys.get(2).bought){
    			current_bought = true;
    			settings_get_spacecraft_buy.setStyle(settings_get_spacecraft_bought_style);
    			if(spaceship_buys.get(2).selected){
    				current_using = true;
    				settings_get_spacecraft_use
							.setStyle(settings_get_spacecraft_use_selected_style);
    			} else {
    				settings_get_spacecraft_use.setStyle(settings_get_spacecraft_use_style);
    				current_using = false;
    				current_cannot_use = false;
    			}
    		} else {
    			current_bought = false;
    			current_using = false;
    			current_cannot_use = true;
    			settings_get_spacecraft_buy.setStyle(settings_get_spacecraft_buy_style);
    			settings_get_spacecraft_use.setStyle(settings_get_spacecraft_use_noactive_style);
    		}   		
    		break;  
		default:
			break;
    	}    	
    }
    
    public void Render(float delta){
		stage_get_spacecraft_dialog.act(delta*SPEED_SPACECRAFT);
		stage_get_spacecraft_dialog.draw();   
    }

    public class Spaceship{
    	public boolean bought;
    	public boolean can_buy;
    	public boolean selected;
    	public Image image;
    	public Spaceship(boolean bought_can, boolean can_buy_can, boolean selected){
    		this.bought = bought_can;
    		this.can_buy = can_buy_can;
    		this.selected = selected;
    	}
    }

	public void Dispose(){
		base_menu = null;
		selected_spaceship = null;
		settings_for_data = null;

		if(get_spacecraft_dialog!=null)
			get_spacecraft_dialog.clear();
		if(get_spacecraft_table_done!=null)
			get_spacecraft_table_done.clear();
		if(get_spacecraft_table_left_right!=null)
			get_spacecraft_table_left_right.clear();
		if(get_spacecraft_table_buy_use!=null)
			get_spacecraft_table_buy_use.clear();
		if(get_spacecraft_table_back!=null)
			get_spacecraft_table_back.clear();
		if(get_spacecraft_buyspacecraft1!=null)
			get_spacecraft_buyspacecraft1.clear();
		if(get_spacecraft_buyspacecraft2!=null)
			get_spacecraft_buyspacecraft2.clear();
		if(get_spacecraft_buyspacecraft3!=null)
			get_spacecraft_buyspacecraft3.clear();
		if(get_spacecraft_money_t!=null)
			get_spacecraft_money_t.clear();
		if(level_base_coins_t!=null)
			level_base_coins_t.clear();
		if(level_base_coins_you_own_t!=null)
			level_base_coins_you_own_t.clear();
		if(get_spacecraft_table_bought!=null)
			get_spacecraft_table_bought.clear();
		if(level_base_spacecraft_t_bought!=null)
			level_base_spacecraft_t_bought.clear();
		if(level_base_spacecraft_you_own_t_bought!=null)
			level_base_spacecraft_you_own_t_bought.clear();
		if(get_spacecraft_table_error!=null)
			get_spacecraft_table_error.clear();
		if(level_base_spacecraft_t_error!=null)
			level_base_spacecraft_t_error.clear();
		if(level_base_spacecraft_you_own_t_error!=null)
			level_base_spacecraft_you_own_t_error.clear();
		get_spacecraft_dialog = null;
		get_spacecraft_table_done = null;
		get_spacecraft_table_left_right = null;
		get_spacecraft_table_buy_use = null;
		get_spacecraft_table_back = null;
		get_spacecraft_buyspacecraft1 = null;
		get_spacecraft_buyspacecraft2 = null;
		get_spacecraft_buyspacecraft3 = null;
		get_spacecraft_money_t = null;
		level_base_coins_t = null;
		level_base_coins_you_own_t = null;
		get_spacecraft_table_bought = null;
		level_base_spacecraft_t_bought = null;
		level_base_spacecraft_you_own_t_bought = null;
		get_spacecraft_table_error = null;
		level_base_spacecraft_t_error = null;
		level_base_spacecraft_you_own_t_error = null;

		if(get_spacecraft_image_back!=null)
			get_spacecraft_image_back.clear();
		if(get_spacecraft_image_spacehip1!=null)
			get_spacecraft_image_spacehip1.clear();
		if(get_spacecraft_image_spacehip2!=null)
			get_spacecraft_image_spacehip2.clear();
		if(get_spacecraft_image_spacehip3!=null)
			get_spacecraft_image_spacehip3.clear();
		if(get_spacecraft_image_money!=null)
			get_spacecraft_image_money.clear();
		if(level_base_coins_you_own!=null)
			level_base_coins_you_own.clear();
		if(settings_get_spacecraft_done!=null)
			settings_get_spacecraft_done.clear();
		if(settings_get_spacecraft_left!=null)
			settings_get_spacecraft_left.clear();
		if(settings_get_spacecraft_right!=null)
			settings_get_spacecraft_right.clear();
		if(settings_get_spacecraft_buy!=null)
			settings_get_spacecraft_buy.clear();
		if(settings_get_spacecraft_use!=null)
			settings_get_spacecraft_use.clear();
		if(settings_get_spacecraft_money_s!=null)
			settings_get_spacecraft_money_s.clear();
		if(settings_get_spacecraft_stack!=null)
			settings_get_spacecraft_stack.clear();
		if(settings_get_spacecraft_bought!=null)
			settings_get_spacecraft_bought.clear();
		if(settings_get_spacecraft_error!=null)
			settings_get_spacecraft_error.clear();
		if(level_base_coins!=null) level_base_coins.clear();
		if(level_base_spacecraft_bought!=null)
			level_base_spacecraft_bought.clear();
		if(level_base_spacecraft_error!=null)
			level_base_spacecraft_error.clear();
		if(level_base_spacecraft_you_own_bought!=null)
			level_base_spacecraft_you_own_bought.clear();
		if(level_base_spacecraft_you_own_error!=null)
			level_base_spacecraft_you_own_error.clear();
		if(level_base_spacecraft_you_own_info!=null)
			level_base_spacecraft_you_own_info.clear();
		get_spacecraft_image_back = null;
		get_spacecraft_image_spacehip1 = null;
		get_spacecraft_image_spacehip2 = null;
		get_spacecraft_image_spacehip3 = null;
		get_spacecraft_image_money = null;
		level_base_coins_you_own = null;
		settings_get_spacecraft_done = null;
		settings_get_spacecraft_left = null;
		settings_get_spacecraft_right = null;
		settings_get_spacecraft_buy = null;
		settings_get_spacecraft_use = null;
		settings_get_spacecraft_money_s = null;
		settings_get_spacecraft_stack = null;
		settings_get_spacecraft_bought = null;
		settings_get_spacecraft_error = null;
		level_base_coins = null;
		level_base_spacecraft_bought = null;
		level_base_spacecraft_error = null;
		level_base_spacecraft_you_own_bought = null;
		level_base_spacecraft_you_own_error = null;
		level_base_spacecraft_you_own_info = null;

		settings_get_spacecraft_buy_style = null;
		settings_get_spacecraft_bought_style = null;
		settings_get_spacecraft_use_style = null;
		settings_get_spacecraft_use_noactive_style = null;
		settings_get_spacecraft_use_selected_style = null;
		textb1 = null;
		textb2ok = null;
		if(stage_get_spacecraft_dialog!=null){
			stage_get_spacecraft_dialog.clear();
			stage_get_spacecraft_dialog.dispose();
			stage_get_spacecraft_dialog = null;
		}
		if(spaceship_buys!=null){
			for(int i=0; i<spaceship_buys.size(); i++){
				spaceship_buys.get(i).image = null;
			}
			spaceship_buys.clear();
			spaceship_buys = null;
		}
	}
}
