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

public class ex01MenuScreenGetCoinsDialog {
	public static final float SPEED_COINS = 3f;
    public static final float width = ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f;
    public static final float height = ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f * 278f/430f;
	public Table get_money_dialog;
	public Table get_money_table_done;
	public Table get_money_table_left_right;
	public Table get_money_table_buy_use;
	public Table get_money_table_back;
	public Table get_money_buy1;
	public Table get_money_buy2;
	public Table get_money_buy3;
	public Table get_money_buy4;
	public Table get_money_buy5;
	public Table get_money_buy6;
	public Table table_general;
    public Image get_money_image_back;   
    public ImageButton settings_get_coins_done;
    public ImageButton settings_get_coins_left;
    public ImageButton settings_get_coins_right;
    public ImageButton settings_get_coins_buy;
    public ImageButton.ImageButtonStyle settings_get_coins_buy_style;
	public Stage stage_get_coins_dialog;   
    public Stack settings_get_coins_stack;
    public enum SelectedSpaceship {one, two, three, four, five, six};
    public SelectedSpaceship selected_spaceship = SelectedSpaceship.one;
    public boolean still_working_buying = false;
	public ex01MenuScreen base_menu;
    public ex01JSONSettingsLoader settings_for_data;
    public int what_amount_bought;
	//public void InitCoinsOwned(TextureAtlas atlas, Skin skin){
	public Table level_base_coins_t;
	public Table level_base_coins_you_own_t;
	public Image level_base_coins_you_own;
    public TextButton level_base_coins;
	public Table get_money_table_owned;
    public Image get_money_image_owned;
	public Stack settings_get_money_stack;
	//public void InitCoinsBuys(TextureAtlas atlas, Skin skin){
	public ArrayList<Coins> coins_buys;  
	//public void InitCoinsBought(TextureAtlas atlas, Skin skin){
	public Table level_base_coins_t_bought;
	public Table level_base_coins_you_own_t_bought;
	public Image level_base_coins_you_own_bought;
    public TextButton level_base_coins_bought;
	public Table get_money_table_bought;
    public Image get_money_image_bought;
    public Stack settings_get_money_bought;
    public boolean still_working_on_done_press = false;
	//error and info stuff
	public Stack settings_get_coins_error;
	public Table get_coins_table_error;
	public Table level_base_coins_t_error;
	public TextButton level_base_coins_error;
	public TextButton.TextButtonStyle textb1;
	public TextButton.TextButtonStyle textb2ok;
	public Table level_base_coins_you_own_t_error;
	public Image level_base_coins_you_own_error;
	public Image level_base_coins_you_own_info;
	public boolean still_working_button_standard = false;
	public static final int RESULT_OK = 1;
	int result;
    
    public ex01MenuScreenGetCoinsDialog(ex01JSONSettingsLoader settings,
										TextureAtlas atlas,
										Skin skin,
										ex01MenuScreen bmenu,
										Viewport viewport) {
    	stage_get_coins_dialog = new Stage(viewport);
    	settings_for_data = settings;
    	base_menu = bmenu;
    	table_general = new Table();
    	InitCoinsBuys(atlas);
    	InitMainElements(skin);
    	InitCoinsOwned(atlas, skin);
    	InitCoinsBought(atlas, skin);
		InitCoinsError(atlas, skin);
    }

	public void InitCoinsError(TextureAtlas atlas, Skin skin){
		//INIT the owned tables
		get_coins_table_error = new Table();
		textb1 = new TextButton.TextButtonStyle();
		textb2ok = new TextButton.TextButtonStyle();
		TextureRegion style1 = skin.getRegion("money");
		TextureRegion style2 = skin.getRegion("moneyok");
		textb1.font = skin.getFont("errors-font");
		textb1.up = new TextureRegionDrawable(style1);
		textb2ok.font = skin.getFont("errors-font");
		textb2ok.up = new TextureRegionDrawable(style2);
		//this stack contains the owned stuff
		settings_get_coins_error = new Stack();
		get_coins_table_error.add(settings_get_coins_error)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f * 85f / 384f);
		get_coins_table_error.setFillParent(true);
		get_coins_table_error.setTransform(true);
		get_coins_table_error.bottom().padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/5.41f);
		get_coins_table_error.addAction(moveBy(0f,-25f,0f));
		level_base_coins_t_error = new Table();
		level_base_coins_t_error.setTransform(true);
		level_base_coins_error = new TextButton("tare", textb1);
		level_base_coins_t_error.setFillParent(true);
		level_base_coins_t_error.add(level_base_coins_error)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.15f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.15f * 68f / 400f);
		level_base_coins_t_error.bottom();
		settings_get_coins_error.add(level_base_coins_t_error);
		level_base_coins_you_own_t_error = new Table();
		level_base_coins_you_own_t_error.setFillParent(true);
		level_base_coins_you_own_error = new Image(new
				TextureRegionDrawable(atlas.findRegion("error")));
		level_base_coins_you_own_t_error.add(level_base_coins_you_own_error)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f * 80f / 256f)
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 50f);
		level_base_coins_you_own_t_error.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/6.55f);
		settings_get_coins_error.add(level_base_coins_you_own_t_error);
		level_base_coins_you_own_error.addAction(Actions.alpha(0f));
		level_base_coins_error.addAction(Actions.alpha(0f));
		stage_get_coins_dialog.addActor(get_coins_table_error);
	}

	public void TransitError(String string, boolean error){
		level_base_coins_error.getLabel().setColor(Color.GREEN);
		level_base_coins_error.getLabel().setText(string);
		level_base_coins_error.setTransform(true);
		get_coins_table_error.clearActions();
		level_base_coins_error.setOrigin(Align.center);
		level_base_coins_you_own_error.setOrigin(Align.center);
		level_base_coins_error.clearActions();
		level_base_coins_you_own_error.clearActions();

		level_base_coins_error.addAction(parallel(
				Actions.fadeIn(1.0f),
				Actions.scaleTo(1.3f, 1.3f, 1.0f),
				sequence(moveBy(0f, 12f, 0.3f),
						Actions.rotateBy(360f, 2.4f, Swing.circleOut),
						moveBy(0f, -12f, 0.3f),
						run(new Runnable() {
							public void run() {
								level_base_coins_error.addAction(parallel(
										Actions.scaleTo(1.0f, 1.0f, 1.0f),
										sequence(Actions.fadeOut(6.0f),
												Actions.run(new Runnable() {
													public void run() {
														still_working_button_standard = false;
														still_working_buying = false;
													}
												}))));
							}
						}))));
		level_base_coins_you_own_error.addAction(parallel(
				Actions.fadeIn(1.0f),
				Actions.scaleTo(1.3f, 1.3f, 1.0f),
				sequence(moveBy(0f, 12f, 0.3f),
						Actions.rotateBy(360f, 2.4f, Swing.circleOut),
						moveBy(0f, -12f, 0.3f),
						run(new Runnable() {
							public void run() {
								level_base_coins_you_own_error.addAction(parallel(
										Actions.scaleTo(1.0f, 1.0f, 1.0f),
										Actions.fadeOut(6.0f)));
							}
						}))));
	}

	public void TransitErrorCancel(){
		level_base_coins_error.clearActions();
		level_base_coins_you_own_error.clearActions();
		level_base_coins_error.invalidateHierarchy();
		level_base_coins_you_own_error.invalidateHierarchy();
		level_base_coins_error.setRotation(0f);
		level_base_coins_you_own_error.setRotation(0f);
		level_base_coins_error.addAction(fadeOut(0f));
		level_base_coins_you_own_error.addAction(fadeOut(0f));
		still_working_buying = false;
		still_working_button_standard = false;
	}

	public void InitMainElements(Skin skin){
    	//INIT the whole dialog
    	get_money_dialog = new Table();
        get_money_dialog.setTransform(true);
        get_money_dialog.addAction(Actions.moveBy(0f, -50f, 0f));
        get_money_dialog.addAction(Actions.sizeTo(width, height));
        get_money_dialog.addAction(Actions.fadeOut(0f));
        get_money_dialog.setPosition(ex01Types.VIRTUAL_SCREEN_WIDTH * 0.5f - width/2,
				ex01Types.VIRTUAL_SCREEN_HEIGHT * 0.5f - height/1.80f);
    	//get_money_buy1,2,3,4,5,6 is the Table that contains the images with the coins offer 
    	get_money_buy1 = new Table();
    	get_money_buy2 = new Table();
    	get_money_buy3 = new Table();
    	get_money_buy4 = new Table();
    	get_money_buy5 = new Table();
        get_money_buy6 = new Table();
        get_money_buy1.setTransform(true);
        get_money_buy2.setTransform(true);
        get_money_buy3.setTransform(true);
        get_money_buy4.setTransform(true);
        get_money_buy5.setTransform(true);
        get_money_buy6.setTransform(true);
        get_money_buy1.add(coins_buys.get(0).image)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f * 126f / 256f).pad(0f);
        get_money_buy2.add(coins_buys.get(1).image)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f * 126f / 256f).pad(0f);
        get_money_buy3.add(coins_buys.get(2).image)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f * 126f / 256f).pad(0f);
        get_money_buy4.add(coins_buys.get(3).image)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f * 126f / 256f).pad(0f);
        get_money_buy5.add(coins_buys.get(4).image)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f * 126f / 256f).pad(0f);
        get_money_buy6.add(coins_buys.get(5).image)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.45f * 126f / 256f).pad(0f);
    	settings_get_coins_stack = new Stack();
    	//add the background image first
    	get_money_table_back = new Table();
    	get_money_image_back = new Image(skin.getDrawable("replayh"));
    	get_money_table_back.add(get_money_image_back)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.1f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.1f * 339f / 512f);
        settings_get_coins_stack.add(get_money_table_back);
        //then add the coins offers
        settings_get_coins_stack.add(get_money_buy1);
        settings_get_coins_stack.add(get_money_buy2);
        settings_get_coins_stack.add(get_money_buy3);
        settings_get_coins_stack.add(get_money_buy4);
        settings_get_coins_stack.add(get_money_buy5);
        settings_get_coins_stack.add(get_money_buy6);        
        get_money_buy1.addAction(Actions.alpha(0f));
        get_money_buy2.addAction(Actions.alpha(0f));
        get_money_buy3.addAction(Actions.alpha(0f));
        get_money_buy4.addAction(Actions.alpha(0f));
        get_money_buy5.addAction(Actions.alpha(0f));
        get_money_buy6.addAction(Actions.alpha(0f));
        //fade out or fade in the coin offers based on which one is selected
        LoadSpaceshipBuys(SelectedSpaceship.one);      
        //setup the done button
        settings_get_coins_done = new ImageButton(skin, "done_button");
        settings_get_coins_done.pad(0f);     
        settings_get_coins_done.padBottom(10f);
        get_money_table_done = new Table();
        get_money_table_done.bottom();
        get_money_table_done.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/1.60f);
        get_money_table_done.add(settings_get_coins_done)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.4f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.4f * 106f / 256f)
				.colspan(2)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 250.00f)
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 10f);
        //setup the buy coins button        
    	settings_get_coins_buy_style = skin
				.get("buy_button2", ImageButton.ImageButtonStyle.class);
        settings_get_coins_buy = new ImageButton(settings_get_coins_buy_style);
        settings_get_coins_buy.pad(0f);      
        get_money_table_buy_use = new Table();  	
        get_money_table_buy_use.add(settings_get_coins_buy)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.6f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.6f * 103f / 256f)
				.center()
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 105.00f)
				.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 35f);
        get_money_table_buy_use.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/-1.60f);
        //setup the left right buttons
        settings_get_coins_left = new ImageButton(skin, "left_button");
        settings_get_coins_left.pad(0f);
        settings_get_coins_right = new ImageButton(skin, "right_button");
        settings_get_coins_right.pad(0f);      
        get_money_table_left_right = new Table();
        get_money_table_left_right.add(settings_get_coins_left)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f * 310f / 128f)
				.padRight(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f);
        get_money_table_left_right.add(settings_get_coins_right)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f * 310f / 128f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f);
        //add the stack to the main dialog
        get_money_dialog.add(settings_get_coins_stack);
        //add all the tables to the stack
        settings_get_coins_stack.add(get_money_table_done);
        settings_get_coins_stack.add(get_money_table_buy_use);
        settings_get_coins_stack.add(get_money_table_left_right);
        //add the main dialog to the stage
        stage_get_coins_dialog.addActor(get_money_dialog);
        //calculate the bounds and sizes and then set the middle for resizing
        settings_get_coins_stack.pack();
        get_money_dialog.pack();
        get_money_buy1.setOrigin(get_money_buy1.getWidth() / 2f,
				get_money_buy1.getHeight() / 2f);
        get_money_buy2.setOrigin(get_money_buy2.getWidth() / 2f,
				get_money_buy2.getHeight() / 2f);
        get_money_buy3.setOrigin(get_money_buy3.getWidth() / 2f,
				get_money_buy3.getHeight() / 2f);
        get_money_buy4.setOrigin(get_money_buy4.getWidth() / 2f,
				get_money_buy4.getHeight() / 2f);
        get_money_buy5.setOrigin(get_money_buy5.getWidth() / 2f,
				get_money_buy5.getHeight() / 2f);
        get_money_buy6.setOrigin(get_money_buy6.getWidth() / 2f,
				get_money_buy6.getHeight() / 2f);
        get_money_dialog.setOrigin(get_money_dialog.getWidth() / 2f,
				get_money_dialog.getHeight() / 2f);
        
        settings_get_coins_done.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!still_working_on_done_press){
					still_working_on_done_press = true;
					if(base_menu.settings.settings.sounds_on)
						base_menu.done_button.play(SND_VOL.DONE_BUTTON_VOLUME);
			        settings_get_coins_done.addAction(sequence(
							moveBy(0f, 12f, 0.1f),
							moveBy(0f, -12f, 0.2f)));
			        get_money_table_owned.addAction(parallel(
							fadeOut(1.25f),
							moveBy(0f,-25f,1.25f,Swing.swingOut)));
			        get_money_dialog.addAction(sequence(parallel(
							fadeOut(1.25f),
							moveBy(0f,-50f,1.25f,swingIn)),
							run(new Runnable(){
								public void run(){
									base_menu.FadeInSettingsDialog(1.5f);
									base_menu.grayscale_shader_active_settings = true;
									base_menu.grayscale_shader_active_get_spacecraft = false;
									Gdx.input.setInputProcessor(base_menu
											.settings_dialog_screen.stage_settings_dialog);
									still_working_on_done_press = false;
									TransitErrorCancel();
								}
					})));
				}
			}	
		});	
        
        settings_get_coins_left.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(base_menu.settings.settings.sounds_on)
					base_menu.level_left_right_button.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
		        settings_get_coins_left.addAction(sequence(
						moveBy(-12f, 0f, 0.1f),
						moveBy(12f, 0f, 0.2f)));
		        PrevSpaceshipBuy();
			}	
		});	  
        
        settings_get_coins_right.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(base_menu.settings.settings.sounds_on)
					base_menu.level_left_right_button.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
		        settings_get_coins_right.addAction(sequence(
						moveBy(12f, 0f, 0.1f),
						moveBy(-12f, 0f, 0.2f)));
		        NextSpaceshipBuy();
			}	
		});	          
    }
	public void InitCoinsBuys(TextureAtlas atlas){
		coins_buys = new ArrayList<Coins>();
		Coins get100Coins = new Coins();
		get100Coins.image = new Image(new
				TextureRegionDrawable(atlas.findRegion("get100_coins")));
		get100Coins.image.setOrigin(Align.center);
		Coins get250Coins = new Coins();
		get250Coins.image = new Image(new
				TextureRegionDrawable(atlas.findRegion("get250_coins")));
		get250Coins.image.setOrigin(Align.center);
		Coins get500Coins = new Coins();
		get500Coins.image = new Image(new
				TextureRegionDrawable(atlas.findRegion("get500_coins")));
		get500Coins.image.setOrigin(Align.center);
		Coins get750Coins = new Coins();
		get750Coins.image = new Image(new
				TextureRegionDrawable(atlas.findRegion("get750_coins")));
		get750Coins.image.setOrigin(Align.center);
		Coins get1000Coins = new Coins();
		get1000Coins.image = new Image(new
				TextureRegionDrawable(atlas.findRegion("get1000_coins")));
		get1000Coins.image.setOrigin(Align.center);
		Coins get1500Coins = new Coins();
		get1500Coins.image = new Image(new
				TextureRegionDrawable(atlas.findRegion("get1500_coins")));
		get1500Coins.image.setOrigin(Align.center);		
		coins_buys.add(get100Coins);
		coins_buys.add(get250Coins);
		coins_buys.add(get500Coins);
		coins_buys.add(get750Coins);
		coins_buys.add(get1000Coins);
		coins_buys.add(get1500Coins);
	}
	
	public void InitCoinsOwned(TextureAtlas atlas, Skin skin){
    	//INIT the owned tables
		get_money_table_owned = new Table();
		//this stack contains the owned stuff
		settings_get_money_stack = new Stack();
		get_money_table_owned.add(settings_get_money_stack)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f * 85f / 384f);
        get_money_table_owned.setFillParent(true);
        get_money_table_owned.setTransform(true);
        get_money_table_owned.top().padTop(ex01Types.VIRTUAL_SCREEN_WIDTH/5f);
        get_money_table_owned.addAction(moveBy(0f, -25f, 0f));
		level_base_coins_t = new Table();
		level_base_coins_t.setTransform(true);
		level_base_coins = new TextButton(Integer
				.toString(settings_for_data.settings.number_coins) + " COINS", skin, "coins_have");
		level_base_coins_t.setFillParent(true);
		level_base_coins_t.add(level_base_coins)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f * 85f / 384f);
		settings_get_money_stack.add(level_base_coins_t);
		level_base_coins_you_own_t = new Table();
		level_base_coins_you_own = new Image(new
				TextureRegionDrawable(atlas.findRegion("your coinsa2")));
		level_base_coins_you_own_t.add(level_base_coins_you_own)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.35f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.35f * 80f / 256f);
		level_base_coins_you_own_t.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 5.25f);
		settings_get_money_stack.add(level_base_coins_you_own_t);	
        stage_get_coins_dialog.addActor(get_money_table_owned);
	}   
	
	public void InitCoinsBought(TextureAtlas atlas, Skin skin){
    	//INIT the owned tables
		get_money_table_bought = new Table();
		//this stack contains the owned stuff
		settings_get_money_bought = new Stack();		
		get_money_table_bought.add(settings_get_money_bought)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.6f * 85f / 384f);
		get_money_table_bought.setFillParent(true);
		get_money_table_bought.setTransform(true);
		get_money_table_bought.bottom().padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/5f);
		get_money_table_bought.addAction(moveBy(0f,-25f,0f)); 
		level_base_coins_t_bought = new Table();
		level_base_coins_t_bought.setTransform(true);
		level_base_coins_bought = new TextButton("5545 COINS", skin, "coins_have2");	
		level_base_coins_t_bought.setFillParent(true);
		level_base_coins_t_bought.add(level_base_coins_bought)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.25f * 85f / 384f);
		level_base_coins_t_bought.bottom();
		settings_get_money_bought.add(level_base_coins_t_bought);
		level_base_coins_you_own_t_bought = new Table();
		level_base_coins_you_own_t_bought.setFillParent(true);
		level_base_coins_you_own_bought = new Image(new
				TextureRegionDrawable(atlas.findRegion("your coinsa2bought")));
		level_base_coins_you_own_t_bought.add(level_base_coins_you_own_bought)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 3f * 80f / 256f);
		level_base_coins_you_own_t_bought.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/4.55f);
		settings_get_money_bought.add(level_base_coins_you_own_t_bought);
		level_base_coins_you_own_bought.addAction(Actions.alpha(0f));
		level_base_coins_bought.addAction(Actions.alpha(0f));
        stage_get_coins_dialog.addActor(get_money_table_bought);
		settings_get_coins_buy.setTransform(true);
		settings_get_coins_buy.setOrigin(Align.center);

		settings_get_coins_buy.setTransform(true);
		settings_get_coins_buy.setOrigin(Align.center);
		level_base_coins_bought.setTransform(true);
		level_base_coins_bought.setOrigin(Align.center);
		level_base_coins_you_own_bought.setOrigin(Align.center);
		level_base_coins.setTransform(true);
		level_base_coins.setOrigin(Align.center);
		level_base_coins_you_own.setOrigin(Align.center);

		settings_get_coins_buy.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!still_working_buying){
					still_working_buying = true;
					if(base_menu.settings.settings.sounds_on)
						base_menu.level_left_right_button.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
					settings_get_coins_buy.addAction(sequence(
							moveBy(0f, -12f, 0.6f),
							moveBy(0f, 12f, 0.4f),
							Actions.run(new Runnable() {
						public void run() {
							switch(selected_spaceship){
								case one:
									result = base_menu.cryo_game.inappInterfaceResolver.
											requestIabPurchase(IActionResolver.PRODUCT_COINS100,
													base_menu.menus.callback_function);
									if(result==RESULT_OK){
										Gdx.app.log("RETURNED FROM PURCHASE",
												Integer.toString(result));
									} else { // something went wrong
										Gdx.app.log("NOT RETURNED FROM PURCHASE",
												Integer.toString(result));
									}
									break;
								case two:
									result = base_menu.cryo_game.inappInterfaceResolver
											.requestIabPurchase(IActionResolver.PRODUCT_COINS250,
													base_menu.menus.callback_function);
									if(result==RESULT_OK){
										Gdx.app.log("RETURNED FROM PURCHASE",
												Integer.toString(result));
									} else { // something went wrong
										Gdx.app.log("NOT RETURNED FROM PURCHASE",
												Integer.toString(result));
									}
									break;
								case three:
									result = base_menu.cryo_game.inappInterfaceResolver
											.requestIabPurchase(IActionResolver.PRODUCT_COINS500,
													base_menu.menus.callback_function);
									if(result==RESULT_OK){
										Gdx.app.log("RETURNED FROM PURCHASE",
												Integer.toString(result));
									} else { // something went wrong
										Gdx.app.log("NOT RETURNED FROM PURCHASE",
												Integer.toString(result));
									}
									break;
								case four:
									result = base_menu.cryo_game.inappInterfaceResolver
											.requestIabPurchase(IActionResolver.PRODUCT_COINS750,
													base_menu.menus.callback_function);
									if(result==RESULT_OK){
										Gdx.app.log("RETURNED FROM PURCHASE",
												Integer.toString(result));
									} else { // something went wrong
										Gdx.app.log("NOT RETURNED FROM PURCHASE",
												Integer.toString(result));
									}
									break;
								case five:
									result = base_menu.cryo_game.inappInterfaceResolver
											.requestIabPurchase(IActionResolver.PRODUCT_COINS1000,
													base_menu.menus.callback_function);
									if(result==RESULT_OK){
										Gdx.app.log("RETURNED FROM PURCHASE",
												Integer.toString(result));
									} else { // something went wrong
										Gdx.app.log("NOT RETURNED FROM PURCHASE",
												Integer.toString(result));
									}
									break;
								case six:
									result = base_menu.cryo_game.inappInterfaceResolver
											.requestIabPurchase(IActionResolver.PRODUCT_COINS1500,
													base_menu.menus.callback_function);
									if(result==RESULT_OK){
										Gdx.app.log("RETURNED FROM PURCHASE",
												Integer.toString(result));
									} else { // something went wrong
										Gdx.app.log("NOT RETURNED FROM PURCHASE",
												Integer.toString(result));
									}
									break;
								default:
									break;
							}
						}
					})));
				} else {
				}
			}
		});
	}

	public void ProcessGotCoinsResult(){
		if(base_menu.settings.settings.sounds_on)
			base_menu.cash_register_coins.play(SND_VOL.SOUND_BUY_COINS);
		switch(selected_spaceship){
			case one:
				level_base_coins_bought.getLabel().setText("100 COINS");
				what_amount_bought = 100;
				break;
			case two:
				level_base_coins_bought.getLabel().setText("250 COINS");
				what_amount_bought = 250;
				break;
			case three:
				level_base_coins_bought.getLabel().setText("500 COINS");
				what_amount_bought = 500;
				break;
			case four:
				level_base_coins_bought.getLabel().setText("750 COINS");
				what_amount_bought = 750;
				break;
			case five:
				level_base_coins_bought.getLabel().setText("1000 COINS");
				what_amount_bought = 1000;
				break;
			case six:
				level_base_coins_bought.getLabel().setText("1500 COINS");
				what_amount_bought = 1500;
				break;
			default:
				break;
		}
		get_money_table_bought.clearActions();
		settings_get_coins_buy.addAction(sequence(
				Actions.scaleTo(1.5f, 1.5f, 1.0f),
				Actions.scaleTo(1.0f, 1.0f, 6.0f)));
		settings_get_coins_buy.addAction(sequence(
				Actions.fadeOut(8.7f),
				Actions.fadeIn(5.0f),
				Actions.run(new Runnable() {
			public void run() {
				still_working_buying = false;
			}
		})));
		if(DoneBuying()) {
			settings_for_data.settings.number_coins += what_amount_bought;
			level_base_coins.getLabel()
					.setText(Integer.toString(settings_for_data.settings.number_coins) + " Coins");
			base_menu.get_spacecraft_dialog_screen.level_base_coins.getLabel()
					.setText(Integer.toString(settings_for_data.settings.number_coins) + " Coins");
			base_menu.get_lifeslots_screen.level_base_coins.getLabel()
					.setText(Integer.toString(settings_for_data.settings.number_coins) + " Coins");
			base_menu.get_bullets_screen.level_base_coins.getLabel()
					.setText(Integer.toString(settings_for_data.settings.number_coins) + " Coins");
			level_base_coins_bought.clearActions();
			level_base_coins_you_own_bought.clearActions();
			level_base_coins.addAction(sequence(
					Actions.scaleTo(0.0f, 1f, 1.5f, Swing.swingIn),
					Actions.scaleTo(1.0f, 1f, 2.5f, Swing.swingOut)));
			level_base_coins_you_own.addAction(sequence(
					Actions.scaleTo(0.0f, 1f, 1.5f, Swing.swingIn),
					Actions.scaleTo(1.0f, 1f, 2.5f, Swing.swingOut)));
			level_base_coins_bought.addAction(parallel(
					Actions.fadeIn(1.0f),
					Actions.scaleTo(1.3f, 1.3f, 1.0f),
					sequence(moveBy(0f, 12f, 0.3f),
							Actions.rotateBy(360f, 2.4f, Swing.circleOut),
							moveBy(0f, -12f, 0.3f),
							run(new Runnable() {
								public void run() {
									level_base_coins_bought.addAction(parallel(
											Actions.scaleTo(1.0f, 1.0f, 1.0f),
											sequence(Actions.fadeOut(6.0f),
													Actions.run(new Runnable() {
														public void run() {
															still_working_buying = false;
														}
									}))));
				}
			}))));
			level_base_coins_you_own_bought.addAction(parallel(
					Actions.fadeIn(1.0f),
					Actions.scaleTo(1.3f, 1.3f, 1.0f),
					sequence(moveBy(0f, 12f, 0.3f),
							Actions.rotateBy(360f, 2.4f, Swing.circleOut),
							moveBy(0f, -12f, 0.3f),
							run(new Runnable() {
								public void run() {
									level_base_coins_you_own_bought.addAction(parallel(
											Actions.scaleTo(1.0f, 1.0f, 1.0f),
											Actions.fadeOut(6.0f)));
								}
			}))));
			settings_for_data.WriteJson();
			base_menu.cryo_game.SAVE_GAME_TO_CLOUD();
		}
	}

	public void ProcessErrorOnBought(String errors){
		level_base_coins_error.setStyle(textb1);
		if(base_menu.settings.settings.sounds_on)
			base_menu.error_sound_no.play(SND_VOL.ERROR_VOL);
		TransitError(errors, true);
	}

	public void UpdateCoinsHave(){
		level_base_coins.getLabel().setText(Integer
				.toString(settings_for_data.settings.number_coins) + " COINS");
	}
	
	public boolean DoneBuying(){
		return true;
	}
    
    public void LoadSpaceshipBuys(SelectedSpaceship space_selected){
    	selected_spaceship = space_selected;
    	switch(space_selected){
    	case one:
    		get_money_buy1.addAction(Actions.alpha(1f));
            get_money_buy2.addAction(Actions.alpha(0f));
            get_money_buy3.addAction(Actions.alpha(0f));
            get_money_buy4.addAction(Actions.alpha(0f));
            get_money_buy5.addAction(Actions.alpha(0f));  	
            get_money_buy6.addAction(Actions.alpha(0f));
    		break;
    	case two:
    		get_money_buy1.addAction(Actions.alpha(0f));
            get_money_buy2.addAction(Actions.alpha(1f));
            get_money_buy3.addAction(Actions.alpha(0f));
            get_money_buy4.addAction(Actions.alpha(0f));
            get_money_buy5.addAction(Actions.alpha(0f));  	
            get_money_buy6.addAction(Actions.alpha(0f));  		
    		break;
    	case three:
    		get_money_buy1.addAction(Actions.alpha(0f));
            get_money_buy2.addAction(Actions.alpha(0f));
            get_money_buy3.addAction(Actions.alpha(1f));
            get_money_buy4.addAction(Actions.alpha(0f));
            get_money_buy5.addAction(Actions.alpha(0f));  	
            get_money_buy6.addAction(Actions.alpha(0f));    		
    		break;  
    	case four:
    		get_money_buy1.addAction(Actions.alpha(0f));
            get_money_buy2.addAction(Actions.alpha(0f));
            get_money_buy3.addAction(Actions.alpha(0f));
            get_money_buy4.addAction(Actions.alpha(1f));
            get_money_buy5.addAction(Actions.alpha(0f));  	
            get_money_buy6.addAction(Actions.alpha(0f));		
    		break;
    	case five:
    		get_money_buy1.addAction(Actions.alpha(0f));
            get_money_buy2.addAction(Actions.alpha(0f));
            get_money_buy3.addAction(Actions.alpha(0f));
            get_money_buy4.addAction(Actions.alpha(0f));
            get_money_buy5.addAction(Actions.alpha(1f));  	
            get_money_buy6.addAction(Actions.alpha(0f));   		
    		break;
    	case six:
    		get_money_buy1.addAction(Actions.alpha(0f));
            get_money_buy2.addAction(Actions.alpha(0f));
            get_money_buy3.addAction(Actions.alpha(0f));
            get_money_buy4.addAction(Actions.alpha(0f));
            get_money_buy5.addAction(Actions.alpha(0f));  	
            get_money_buy6.addAction(Actions.alpha(1f)); 		
    		break;      		
		default:
			break;
    	}
    }
    
    public void PrevSpaceshipBuy(){
    	get_money_dialog.addAction(sequence(Actions.scaleTo(1.05f, 1.05f, 0.2f),
				Actions.scaleTo(1.0f, 1.0f, 0.3f)));
    	switch(selected_spaceship){
    	case one:
            get_money_buy6.addAction(Actions.fadeIn(2f));
            get_money_buy1.addAction(Actions.fadeOut(2f));
            get_money_buy1.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.six;
    		break;
    	case two:
    		get_money_buy1.addAction(Actions.fadeIn(2f));
            get_money_buy2.addAction(Actions.fadeOut(2f));
            get_money_buy2.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.one;
    		break;
    	case three:
            get_money_buy2.addAction(Actions.fadeIn(2f));
            get_money_buy3.addAction(Actions.fadeOut(2f));
            get_money_buy3.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.two;
    		break;   
    	case four:
            get_money_buy3.addAction(Actions.fadeIn(2f));
            get_money_buy4.addAction(Actions.fadeOut(2f));
            get_money_buy4.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.three;
    		break;   
    	case five:
            get_money_buy4.addAction(Actions.fadeIn(2f));
            get_money_buy5.addAction(Actions.fadeOut(2f));
            get_money_buy5.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.four;
    		break;   
    	case six:
            get_money_buy5.addAction(Actions.fadeIn(2f));
            get_money_buy6.addAction(Actions.fadeOut(2f));
            get_money_buy6.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.five;
    		break;       		
    	default:
    		break;
    	}
    }
    
    public void NextSpaceshipBuy(){
    	get_money_dialog.addAction(sequence(Actions.scaleTo(1.05f, 1.05f, 0.2f),
				Actions.scaleTo(1.0f, 1.0f, 0.3f)));
    	switch(selected_spaceship){
    	case one:
            get_money_buy2.addAction(Actions.fadeIn(2f));
            get_money_buy1.addAction(Actions.fadeOut(2f));
            get_money_buy1.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.two;
    		break;
    	case two:
            get_money_buy3.addAction(Actions.fadeIn(2f));
            get_money_buy2.addAction(Actions.fadeOut(2f));
            get_money_buy2.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.three;
    		break;
    	case three:
            get_money_buy4.addAction(Actions.fadeIn(2f));
            get_money_buy3.addAction(Actions.fadeOut(2f));
            get_money_buy3.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.four;
    		break;
    	case four:
            get_money_buy5.addAction(Actions.fadeIn(2f));
            get_money_buy4.addAction(Actions.fadeOut(2f));
            get_money_buy4.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.five;
    		break;    
    	case five:
            get_money_buy6.addAction(Actions.fadeIn(2f));
            get_money_buy5.addAction(Actions.fadeOut(2f));
            get_money_buy5.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.six;
    		break;    
    	case six:
    		get_money_buy1.addAction(Actions.fadeIn(2f));
            get_money_buy6.addAction(Actions.fadeOut(2f));
            get_money_buy6.addAction(sequence(Actions.scaleTo(1.2f, 1.2f, 0.5f),
					Actions.scaleTo(1.0f, 1.0f, 0.5f)));
            selected_spaceship = SelectedSpaceship.one;
    		break;        		
    	default:
    		break;
    	}
    }    
	
    public void Render(float delta){
    	stage_get_coins_dialog.act(delta*SPEED_COINS);
    	stage_get_coins_dialog.draw();
    }

    public class Coins{
    	public Image image;
    	public Coins(){
    	}
    }

	public void Dispose(){
		if(get_money_dialog!=null)
			get_money_dialog.clear();
		if(get_money_table_done!=null)
			get_money_table_done.clear();
		if(get_money_table_left_right!=null)
			get_money_table_left_right.clear();
		if(get_money_table_buy_use!=null)
			get_money_table_buy_use.clear();
		if(get_money_table_back!=null)
			get_money_table_back.clear();
		if(get_money_buy1!=null)
			get_money_buy1.clear();
		if(get_money_buy2!=null)
			get_money_buy2.clear();
		if(get_money_buy3!=null)
			get_money_buy3.clear();
		if(get_money_buy4!=null)
			get_money_buy4.clear();
		if(get_money_buy5!=null)
			get_money_buy5.clear();
		if(get_money_buy6!=null)
			get_money_buy6.clear();
		if(table_general!=null)
			table_general.clear();
		if(level_base_coins_t!=null)
			level_base_coins_t.clear();
		if(level_base_coins_you_own_t!=null)
			level_base_coins_you_own_t.clear();
		if(get_money_table_owned!=null)
			get_money_table_owned.clear();
		if(level_base_coins_t_bought!=null)
			level_base_coins_t_bought.clear();
		if(level_base_coins_you_own_t_bought!=null)
			level_base_coins_you_own_t_bought.clear();
		if(get_money_table_bought!=null)
			get_money_table_bought.clear();
		if(settings_get_coins_stack!=null)
			settings_get_coins_stack.clear();
		if(settings_get_money_stack!=null)
			settings_get_money_stack.clear();
		if(settings_get_money_bought!=null)
			settings_get_money_bought.clear();
		if(level_base_coins_you_own_bought!=null)
			level_base_coins_you_own_bought.clear();
		if(level_base_coins_you_own!=null)
			level_base_coins_you_own.clear();
		if(get_money_image_back!=null)
			get_money_image_back.clear();
		if(get_money_image_bought!=null)
			get_money_image_bought.clear();
		if(get_money_image_owned!=null)
			get_money_image_owned.clear();
		if(settings_get_coins_done!=null)
			settings_get_coins_done.clear();
		if(settings_get_coins_left!=null)
			settings_get_coins_left.clear();
		if(settings_get_coins_right!=null)
			settings_get_coins_right.clear();
		if(settings_get_coins_buy!=null)
			settings_get_coins_buy.clear();
		if(level_base_coins!=null)
			level_base_coins.clear();
		if(level_base_coins_bought!=null)
			level_base_coins_bought.clear();

		get_money_dialog = null;
		get_money_table_done = null;
		get_money_table_left_right = null;
		get_money_table_buy_use = null;
		get_money_table_back = null;
		get_money_buy1 = null;
		get_money_buy2 = null;
		get_money_buy3 = null;
		get_money_buy4 = null;
		get_money_buy5 = null;
		get_money_buy6 = null;
		table_general = null;
		level_base_coins_t = null;
		level_base_coins_you_own_t = null;
		get_money_table_owned = null;
		level_base_coins_t_bought = null;
		level_base_coins_you_own_t_bought = null;
		get_money_table_bought = null;
		settings_get_coins_stack = null;
		settings_get_money_stack = null;
		settings_get_money_bought = null;
		level_base_coins_you_own_bought = null;
		level_base_coins_you_own = null;
		get_money_image_back = null;
		get_money_image_bought = null;
		get_money_image_owned = null;
		settings_get_coins_done = null;
		settings_get_coins_left = null;
		settings_get_coins_right = null;
		settings_get_coins_buy = null;
		level_base_coins = null;
		level_base_coins_bought = null;
		
		settings_get_coins_buy_style = null;
		settings_for_data = null;
		base_menu = null;
		selected_spaceship = null;
		if(coins_buys!=null) {
			for (int i = 0; i < coins_buys.size(); i++) {
				coins_buys.get(i).image = null;
			}
			coins_buys.clear();
			coins_buys = null;
		}
		if(stage_get_coins_dialog!=null){
			stage_get_coins_dialog.clear();
			stage_get_coins_dialog.dispose();
			stage_get_coins_dialog = null;
		}

		if(settings_get_coins_error!=null){
			settings_get_coins_error.clear();
			settings_get_coins_error = null;
		}
		if(get_coins_table_error!=null){
			get_coins_table_error.clear();
			get_coins_table_error = null;
		}
		if(level_base_coins_t_error!=null){
			level_base_coins_t_error.clear();
			level_base_coins_t_error = null;
		}
		if(level_base_coins_error!=null){
			level_base_coins_error.clear();
			level_base_coins_error = null;
		}
		if(level_base_coins_you_own_t_error!=null){
			level_base_coins_you_own_t_error.clear();
			level_base_coins_you_own_t_error = null;
		}
		if(level_base_coins_you_own_error!=null){
			level_base_coins_you_own_error.clear();
			level_base_coins_you_own_error = null;
		}
		if(level_base_coins_you_own_info!=null){
			level_base_coins_you_own_info.clear();
			level_base_coins_you_own_info = null;
		}
		textb1 = null;
		textb2ok = null;
	}
}
