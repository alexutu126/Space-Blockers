//WMS3 2015

package com.cryotrax.game;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.math.Interpolation.swing;
import static com.badlogic.gdx.math.Interpolation.*;
import java.text.DecimalFormat;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Interpolation.Swing;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.cryotrax.game.GameState;

public class ex01MenuLevelSelector extends GdxSceneBase implements Screen {
	public final CryotraxGame cryo_game;
	public static final float SPEED_LEVEL_SELECTOR = 3f;
	public static final float SPEED_RANK_SELECTOR = 3f;
	public static final float TRANSITION_IN_TIME = 1.15f;
	public static final int VIRTUAL_PAGE_WIDTH_PACE = 528;
	public boolean camefromgame = false;
	public boolean finished_transition = false;
	public boolean still_working_back_press = false;
	public boolean still_working_play_press = false;
	public boolean displaying_level_no = true;
	public boolean still_working_on_done_press = false;
	public boolean process_clicked_level_display_change = false;
	public boolean bCanGetBackwards = true;
	public float time;
	public float resolution[];
	public float radius;	
    public float scrool_position = 0;
    public int screen_sizew;
    public int screen_sizeh;
    public int angle1;
    public int angle2;
    public int angle3;
	public int level = 1;
    public int c = 0;
	public int scrool_position_no = 1;
    public CheckBox chk1;
    public CheckBox chk2;
    public CheckBox chk3;
    public CheckBox.CheckBoxStyle chk_style;
	public ex01JSONSettingsLoader settings_for_data;
	// this is the Level Table that contains all the Stack Levels (back, stars, lock images)
    public ex01MenuLevelSelectorBase level_selector;
	// this is the Table which contains the Stack with the play, stars, level selection stuff
    public Table table_level_selector;
	public Table table;	
	public Table table_menu;
    public Table chk_table;
    public Table table_back_play;
    public Table table_back_play_general;
    public Table image_level_selector_closed_table;
	public Table scores_table_ranking;
	public Table scores_table_rank;
	public Table ranking_table_back;
    public Table level_avg_angle_table;
    public Table level_go_left_right;
    public Table level_no_table;
	public Table cryo_image_table;
	public Table get_money_table_bought;
	public Table level_base_coins_t_bought;
	public Table level_base_coins_you_own_t_bought;
    public Image backgroundImg;
    public Image scores_image_back;
    public Image login_image_back;
    public Image ranking_image_back;
    public Image image_level_selector;
    public Image image_level_selector_closed;
	public Image cryo_image;
	public Image level_base_coins_you_own_bought;
    public ImageButton imageb_play;
    public ImageButton imageb_back_general;
    public ImageButton imageb_play_general;
    public ImageButton image_left_general;
    public ImageButton image_right_general;
    public ImageButton level_go_left;
    public ImageButton level_go_right;
    public ImageButton scores_done;
    public ImageButton login_button;
	public ImageButton scores_refresh;
    public ImageTextButton level_no_button;
    public ImageTextButton level_avg_angle;
	public ImageTextButton.ImageTextButtonStyle ibs =
			new ImageTextButton.ImageTextButtonStyle();
	public ImageTextButton.ImageTextButtonStyle ibs_ang =
			new ImageTextButton.ImageTextButtonStyle();
    public TextButton ranking_top;
	public TextButton level_base_coins_bought;
	public TextureRegion backgroundTexR;
	public TextureRegion cryo_image_tr;
	public TextureAtlas texture_atlas;
    public Stack backgroundStack;
	public Stack stack_stage;
	public Table table_stage_ranking;
    public Stack stack_level_selector;
	public Stack scores_stack_ranking;
	public Stack settings_get_money_bought;
	public Stage stage;
	public Stage stage_ranking;
	public Skin skin;
    public Viewport viewport;
	public OrthographicCamera camera;
	public ShaderProgram shader;
    public ShapeRenderer debug_shaper;
	public State state;
    public BitmapFont level_font;
	public DecimalFormat decimal_transform = new DecimalFormat("00.00");
	public enum State { TransitionIn, TransitionOut, Picture }
	//page no
	public Image page_no_graphicxs;
	public TextureRegion page_no_graphicxsTR1;
	public TextureRegion page_no_graphicxsTR2;
	public TextureRegion page_no_graphicxsTR3;
	public TextureRegion page_no_graphicxsTR4;
	public TextureRegionDrawable page_no_graphicxsTRD1;
	public TextureRegionDrawable page_no_graphicxsTRD2;
	public TextureRegionDrawable page_no_graphicxsTRD3;
	public TextureRegionDrawable page_no_graphicxsTRD4;
	public Table page_no_graphicxs_table;
	public int page_no = 1;
	public static final float ALPHA_PAGE = 0.75f;
	//ranking
	public int selected_level;
	public boolean get_social_score = false;
	//	public boolean rank_command_came_from_selector = false;
	public ImageButton.ImageButtonStyle refresh_on_STYLE;
	public TextureRegion refresh_on_TEX;

    public ex01MenuLevelSelector(ex01JSONSettingsLoader settings,
								 TextureAtlas atlas,
								 final CryotraxGame game) {
    	cryo_game = game;
    	cryo_game.game_state = GameState.LEVELS_SCREEN;
    	cryo_game.levels_screen = this;
    	settings_for_data = settings;
    	screen_sizew = ex01Types.VIRTUAL_SCREEN_WIDTH;
    	screen_sizeh = ex01Types.VIRTUAL_SCREEN_HEIGHT;
		if(cryo_game.screen_type==1){
			skin = new Skin(Gdx.files.internal(ex01Types.
					DATA_GRAPHICS_SPRITE_MENU_JSON01big));
		} else if(cryo_game.screen_type==2){
			skin = new Skin(Gdx.files.internal(ex01Types.
					DATA_GRAPHICS_SPRITE_MENU_JSON02medium));
		} else {
			skin = new Skin(Gdx.files.internal(ex01Types.
					DATA_GRAPHICS_SPRITE_MENU_JSON03small));
		}

		level_font = skin.getFont("default-font");
    	level_selector = new ex01MenuLevelSelectorBase(this,
													   settings,
													   skin);
		level_selector.selector_parent_pane = this;
    	debug_shaper = new ShapeRenderer();    // this will be deleted once we're in gplay
    	debug_shaper.setAutoShapeType(true);   // this will be deleted once we're in gplay
    	MenuScreenInitBack(atlas);
    	MenuLevelSelector();
    	RankingTableInit(skin);
    	InitCoinsBought(atlas, skin);
		InitLevelGraphics(atlas);
    	InitShaderTransition();
    }

	public void NextLevelGraphicsProcess(){
		switch(page_no){
			case 1: {
				page_no_graphicxs.setDrawable(page_no_graphicxsTRD2);
				page_no = 2; } break;
			case 2:
			{
				page_no_graphicxs.setDrawable(page_no_graphicxsTRD3);
				page_no = 3; } break;
			case 3:
			{
				page_no_graphicxs.setDrawable(page_no_graphicxsTRD4);
				page_no = 4; } break;
		}
	}

	public void PrevLevelGraphicsProcess(){
		switch(page_no){
			case 2:
			{
				page_no_graphicxs.setDrawable(page_no_graphicxsTRD1);
				page_no = 1; } break;
			case 3:
			{
				page_no_graphicxs.setDrawable(page_no_graphicxsTRD2);
				page_no = 2; } break;
			case 4:
			{
				page_no_graphicxs.setDrawable(page_no_graphicxsTRD3);
				page_no = 3; } break;
		}
	}

	public void LevelGraphicsProcess(){
		switch(page_no){
			case 1:
			{
				page_no_graphicxs.setDrawable(page_no_graphicxsTRD1);
				page_no = 1; } break;
			case 2:
			{
				page_no_graphicxs.setDrawable(page_no_graphicxsTRD2);
				page_no = 2; } break;
			case 3:
			{
				page_no_graphicxs.setDrawable(page_no_graphicxsTRD3);
				page_no = 3; } break;
			case 4:
			{
				page_no_graphicxs.setDrawable(page_no_graphicxsTRD4);
				page_no = 4; } break;
		}
	}

	public void MenuScreenInitBack(TextureAtlas atlas) { // background image - simple and easy
    	texture_atlas = atlas;
		camera = new OrthographicCamera();    	
		viewport = new StretchViewport(ex01Types.VIRTUAL_SCREEN_WIDTH,
									   ex01Types.VIRTUAL_SCREEN_HEIGHT,
									   camera);
		stage = new Stage(viewport);
		stage_ranking = new Stage(viewport);
		backgroundTexR = texture_atlas.findRegion("back");
		backgroundImg = new Image(new TextureRegionDrawable(backgroundTexR));
		backgroundStack = new Stack();
		table = new Table();	
		table.row();
		table.add(backgroundImg)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH)
				.height(ex01Types.VIRTUAL_SCREEN_HEIGHT);
		table.setFillParent(true);
		backgroundStack.add(table);
		backgroundStack.setFillParent(true);	
		stage.addActor(backgroundStack);		
		cryo_image_table = new Table();
		cryo_image_tr = texture_atlas.findRegion("cryotrax");
		cryo_image = new Image(new TextureRegionDrawable(cryo_image_tr));
		cryo_image.addAction(Actions.alpha(0.85f));
		cryo_image_table.add(cryo_image).width(ex01Types.VIRTUAL_SCREEN_WIDTH/1.65f)
										.height(ex01Types.VIRTUAL_SCREEN_WIDTH/1.65f*280f/420f)
										.top()
										.padTop(-ex01Types.VIRTUAL_SCREEN_WIDTH/1.210f);
		cryo_image.addAction(Actions.alpha(0.0f));
		stage.addActor(cryo_image_table);
		cryo_image_table.setFillParent(true);
    }
    
    public void MenuLevelSelector(){ // levels selection dialog
    	image_level_selector_closed_table = new Table();
		// add   level_selector   inside it (i.e. levels 1-16)
    	table_menu = new Table();
		// add   stack_level_selector   in it > then
		// add   image_level_selector_closed, play, chk, etc   in it
    	table_level_selector = new Table();
		// add   level_selector(levels), table_level_selector(level selector), etc
    	stack_stage = new Stack();
		// add   back selector image + play + chk + etc
    	stack_level_selector = new Stack();
		// ex01MenuLevelSelectorBase level_selector ScrollPane
		table_menu.add(level_selector).center().padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 7.90f);
    	stack_stage.add(table_menu);
    	// CHECK boxes
    	chk_table = new Table();
    	chk1 = new CheckBox("", skin);
    	chk2 = new CheckBox("", skin);
    	chk3 = new CheckBox("", skin);
		chk1.pad(0f);
		chk_table.center();
		// BACK image and play 
		image_level_selector = new Image(skin.getDrawable("hud-level55"));
		image_level_selector.setOrigin(Align.center);
		image_level_selector.addAction(Actions.hide());
		image_level_selector_closed = new Image(skin.getDrawable("hud-level555"));
		image_level_selector_closed_table.setTransform(true);
		image_level_selector_closed_table.setFillParent(true);
		image_level_selector_closed_table.add(image_level_selector_closed)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH * 1.1f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH * 1.1f * 400f / 580f);
		imageb_play = new ImageButton(skin, "play_selector");
		imageb_back_general = new ImageButton(skin, "back_general");
		imageb_play_general = new ImageButton(skin, "play_selector");
		table_back_play = new Table();
		table_back_play.row();		
		imageb_play.addAction(Actions.alpha(1f));
		if(cryo_game.screen_type!=3) {
			imageb_play.setTransform(true);
		}
		table_back_play
				.add(imageb_play)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.25f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.25f * 115f / 256f)
				.padTop(ex01Types.VIRTUAL_SCREEN_HEIGHT / 3.15f);
		table_back_play.padTop(ex01Types.VIRTUAL_SCREEN_HEIGHT / 6.2f);
		// add all of these (check-boxes, levels, play buttons) to the stack
		stack_level_selector.add(image_level_selector);
		stack_level_selector.add(image_level_selector_closed_table);
		stack_level_selector.add(chk_table);
		stack_level_selector.add(table_back_play);
		table_level_selector.add(stack_level_selector).center();
		stack_stage.add(table_level_selector);
		table_back_play_general = new Table();
		image_left_general = new ImageButton(skin, "left_general");
		image_right_general = new ImageButton(skin, "right_general");
		table_back_play_general.add(image_left_general)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 4.5f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 4.5f * 128f / 150f)
				.bottom().space(0f)
				.padRight(ex01Types.VIRTUAL_SCREEN_WIDTH / 6.75f);
		table_back_play_general.add(imageb_back_general)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.0f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH/3.0f * 106f/256f)
				.bottom().space(0f).uniformX();
		table_back_play_general.add(image_right_general)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 4.5f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH/4.5f * 128f/150f)
				.bottom().space(0f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 6.75f);
		image_left_general.toFront();
		image_right_general.toFront();
		table_back_play_general.addAction(Actions.alpha(0.915f));
		table_back_play_general.bottom().padBottom(+15f);
		backgroundStack.add(table_back_play_general);
    	table_menu.setOrigin(table_menu.getWidth() / 2,
				table_menu.getHeight() / 2);
//**********************************************************************************
		if(cryo_game.screen_type!=3) {
			table_level_selector.setTransform(true);
			stack_stage.setTransform(true);
		}
//**********************************************************************************
		table_level_selector.setFillParent(true);
		stack_stage.setFillParent(true);
    	stage.addActor(stack_stage);
    	imageb_back_general.pack();
		if(cryo_game.screen_type!=3) {
			imageb_back_general.setTransform(true);
		}
		imageb_back_general.setOrigin(imageb_back_general.getWidth()/2,
									  imageb_back_general.getHeight()/2);
		imageb_back_general.setOrigin(imageb_back_general.getWidth() / 2,
									  imageb_back_general.getHeight() / 2);
		
		imageb_back_general.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!still_working_back_press && bCanGetBackwards){
					still_working_back_press = true;
					if(cryo_game.menu_screen.settings.settings.sounds_on)
						cryo_game.menu_screen.back_button.play(SND_VOL.SOUND_BACK_VOLUME);
					if(cryo_game.game_state == GameState.LEVEL_ENTRY){
						table_level_selector.toBack();
						level_selector.toFront();
						table_level_selector.addAction(sequence(parallel(Actions.fadeOut(1.25f),
								Actions.moveBy(0f,-50f,1.25f,swingIn))));
						scores_table_ranking.addAction(sequence(parallel(Actions.fadeOut(1.25f),
								Actions.moveBy(0f,-80f,1.25f,swingIn))));
						imageb_back_general.addAction(sequence(Actions.parallel(
										Actions.sequence(Actions.moveBy(0f, 10f, 0.2f),
										Actions.moveBy(0f, -10f, 0.2f, swingOut)),
										Actions.scaleTo(1.2f, 1.2f, 0.2f)),
								        Actions.run(new Runnable() {
							public void run() {
								cryo_image_table.addAction(Actions.alpha(1f, 2f));
								level_selector.image_cryo.addAction(Actions.alpha(0.85f, 1.5f));
								table_menu.addAction(scaleTo(1f, 1f, 2f));
								table_menu.addAction(fadeIn(1.25f));
								level_selector.initializeReset();
								scores_table_ranking.addAction(sequence(
									Actions.scaleTo(0.1f, 0.1f, 2f)));
								table_level_selector.addAction(sequence(
									Actions.scaleTo(0.1f, 0.1f, 2f),
									Actions.run(new Runnable() {
										public void run() {
											imageb_play_general.addAction(sequence(
													Actions.scaleTo(1f, 1f, 1f)));
											table_level_selector.addAction(Actions.fadeOut(1f));
											table_level_selector.addAction(moveBy(0f, 50f, 1f));
											scores_table_ranking.addAction(Actions.fadeOut(1f));
											scores_table_ranking.addAction(moveBy(0f, 80f, 1f));
											cryo_game.game_state = GameState.LEVELS_SCREEN;
											EnableLeftRight();
											still_working_back_press = false;
											level_selector.bCanClickAnotherLevel = true;
										}
									})));
								imageb_back_general.addAction(Actions.scaleTo(1.0f, 1.0f, 0.4f));
							}
						})));
					}
					else if (cryo_game.game_state == GameState.LEVELS_SCREEN){
						cryo_game.game_state = GameState.MENU_SCREEN;
						Gdx.input.setInputProcessor(cryo_game.menu_screen.menus.stage);
						imageb_back_general.addAction(Actions.sequence(
							Actions.parallel(Actions.sequence(Actions.moveBy(0f, 10f, 0.2f),
							Actions.moveBy(0f, -10f, 0.2f, swingOut)),
							Actions.scaleTo(1.2f, 1.2f, 0.2f)),
							Actions.run(new Runnable() {
							public void run() {
								cryo_image.clearActions();
								cryo_image.addAction(Actions.fadeOut(0f));
								imageb_back_general.addAction(Actions.scaleTo(1.0f, 1.0f, 0.4f));
								cryo_game.menu_screen.menus.new_game_text
										.addAction(parallel(Actions.alpha(0.7f, 3f),
										Actions.scaleTo(1.0f, 1.0f, 0.8f),
										Actions.sequence(moveBy(0f, 15f, 0.15f),
												moveBy(0f, -15f, 1f, swingOut))));
								cryo_game.game_state = GameState.MENU_SCREEN;
								cryo_game.menu_screen.InactivateShaderBooleans();
								if(!cryo_game.menu_screen.settings.settings.is_this_for_free) {
									cryo_game.menu_screen.menus.FadeInAdLoginButtons();
								}
								cryo_game.setScreen(cryo_game.menu_screen);
								still_working_back_press = false;
							}
						})));
					} 
					imageb_play_general.addAction(sequence(Actions.scaleTo(1f, 1f, 1.5f),
														   Actions.run(new Runnable() {
						public void run(){

						}
					})));
				}
			}
		});	
		level_go_left = new ImageButton(skin, "left_button");
		level_go_left.pad(0f);
		level_go_right = new ImageButton(skin, "right_button");
		level_go_right.pad(0f);
		level_go_left_right = new Table();
		level_go_left_right.add(level_go_left)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 8.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 8.3f * 310f / 128f)
				.padRight(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.0f)
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 13.125f);
		level_go_left_right.add(level_go_right)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 8.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 8.3f * 310f / 128f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.0f)
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 13.125f);
		stack_level_selector.add(level_go_left_right);
		ibs.font = skin.getFont("scorescombo3-font160");
		ibs.fontColor = new Color(1.0f, 1.0f, 1.0f, 0.750f);
		ibs.up = new TextureRegionDrawable(skin.getRegion("level_name"));		
		ibs_ang.font = skin.getFont("scorescombo3-font160");
		ibs_ang.fontColor = new Color(1, 0.78f, 0, 0.85f);
		ibs_ang.up = new TextureRegionDrawable(skin.getRegion("level_name_ang"));
		level_no_button = new ImageTextButton("button", ibs);
		level_no_table = new Table();
		level_no_table.setFillParent(true);
		level_no_table.add(level_no_button)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.3f * 230f / 525f)
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.95f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 250.0f)
				.expandX();
		stack_level_selector.add(level_no_table);
		level_no_button.getLabelCell().padLeft(-ex01Types.VIRTUAL_SCREEN_WIDTH/50f)
									  .padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 19.85f);
		level_selector.image_cryo = cryo_image;
		
		level_no_button.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!process_clicked_level_display_change){
					process_clicked_level_display_change = true;
					if(cryo_game.menu_screen.settings.settings.sounds_on)
						cryo_game.menu_screen.level_left_right_button
								.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
					if(displaying_level_no){
						displaying_level_no = false;
						level_no_button.addAction(sequence(
							moveBy(0f, 12f, 0.3f),
							moveBy(0f, -12f, 0.3f),
							run(new Runnable(){
								public void run(){
									level_no_button.setText(decimal_transform.format(
											settings_for_data
													.settings.level_angles[level_selector
													.received_open_from_level_no]) + " DEG");
									level_no_button.getLabelCell()
											.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 19.85f);
									process_clicked_level_display_change = false;
									level_no_button.setStyle(ibs_ang);
								}
						})));	
					} else {
						displaying_level_no = true;
						ProcessLeftRight();
						level_no_button.addAction(sequence(
								moveBy(0f, 12f, 0.3f),
								moveBy(0f, -12f, 0.3f),
								run(new Runnable(){
							public void run(){
						    	if(level < 10) {
						    		level_no_button.setText("LEVEL 0" + Integer.toString(level));
						    	} else {
						    		level_no_button.setText("LEVEL " + Integer.toString(level));
						    	} 
						    	level_no_button.getLabelCell()
										.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 19.85f);
						    	process_clicked_level_display_change = false;
						    	level_no_button.setStyle(ibs);
							}					
						})));
					}
				}	
			}
		});	   
		
		level_go_left.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(cryo_game.menu_screen.settings.settings.sounds_on)
					cryo_game.menu_screen.level_left_right_button
							.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
				came_from_left_right = true;
				ProcessRanksUponLevelOpen(ex01Types.DONT_CHANGE_SOCIAL);
				table_level_selector.addAction(sequence(
						Actions.scaleTo(1.05f, 1.05f, 0.4f),
						Actions.scaleTo(1.0f, 1.0f, 0.6f)));
				level_go_left.addAction(sequence(
						moveBy(-12f, 0f, 0.1f),
						moveBy(12f, 0f, 0.2f),
						run(new Runnable(){
							public void run(){
								if(level_selector.received_open_from_level_no-1>=0){
									level_selector.received_open_from_level_no--;
								} else {
									level_selector.received_open_from_level_no = settings_for_data
											.settings.no_level_unlocked - 1;
								}
								ProcessLeftRight();
								ProcessClickLevelLeftRight();
								cryo_game.game_state = GameState.LEVEL_ENTRY;
							}
				})));						
			}	
		});	   
		
		level_go_right.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(cryo_game.menu_screen.settings.settings.sounds_on)
					cryo_game.menu_screen.level_left_right_button
							.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
				came_from_left_right = true;
				ProcessRanksUponLevelOpen(ex01Types.DONT_CHANGE_SOCIAL);
				table_level_selector.addAction(sequence(
					Actions.scaleTo(1.05f, 1.05f, 0.4f),
					Actions.scaleTo(1.0f, 1.0f, 0.6f)));
				level_go_right.addAction(sequence(
					moveBy(12f, 0f, 0.1f),
					moveBy(-12f, 0f, 0.2f),
					run(new Runnable(){
						public void run(){
							if(level_selector.received_open_from_level_no+1<=(settings_for_data
									.settings.no_level_unlocked - 1)){
								level_selector.received_open_from_level_no++;
							} else {
								level_selector.received_open_from_level_no = 0;
							}
							ProcessLeftRight();
							ProcessClickLevelLeftRight();
							cryo_game.game_state = GameState.LEVEL_ENTRY;
						}
				})));	
			}	
		});	 
		
		image_left_general.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				if(cryo_game.menu_screen.settings.settings.sounds_on)
					cryo_game.menu_screen.level_left_right_button
							.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
				image_left_general.addAction(sequence(
						moveBy(-12f, 0f, 0.1f),
						moveBy(12f, 0f, 0.2f),
						run(new Runnable(){
							public void run(){
								scrool_position_no = GetPageScroolPositionNo(level_selector);
								scrool_position = scrool_position_no * VIRTUAL_PAGE_WIDTH_PACE;
								if((scrool_position-ex01Types.VIRTUAL_SCREEN_WIDTH)>=0f)
								{
									scrool_position-=ex01Types.VIRTUAL_SCREEN_WIDTH +
											ex01Types.VIRTUAL_SCREEN_WIDTH/10f;
									scrool_position_no--;
									PrevLevelGraphicsProcess();
								}
								level_selector.setScrollX(scrool_position);
							}
				})));						
			}	
		});	   
		
		image_right_general.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (cryo_game.menu_screen.settings.settings.sounds_on)
					cryo_game.menu_screen.level_left_right_button
							.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
				image_right_general.addAction(sequence(
						moveBy(-12f, 0f, 0.1f),
						moveBy(12f, 0f, 0.2f),
						run(new Runnable() {
							public void run() {
								scrool_position_no = GetPageScroolPositionNo(level_selector);
								scrool_position = scrool_position_no * VIRTUAL_PAGE_WIDTH_PACE;
								if ((scrool_position + ex01Types.VIRTUAL_SCREEN_WIDTH) <=
													   ex01Types.VIRTUAL_SCREEN_WIDTH * 4)
								{
									scrool_position += ex01Types.VIRTUAL_SCREEN_WIDTH +
													   ex01Types.VIRTUAL_SCREEN_WIDTH / 10f;
									scrool_position_no++;
									NextLevelGraphicsProcess();
								}
								level_selector.setScrollX(scrool_position);
							}
				})));
			}
		});
		// *** M A K E    S U R E    P A C K    I S    A T    T H E    E N D    of    function
		table_level_selector.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 4.05f);
		stack_stage.pack();
		table_level_selector.pack();
		imageb_play.pack();
		table_back_play.pack();
		imageb_play.setOrigin(imageb_play.getWidth() / 2,
				imageb_play.getHeight() / 2);
		table_back_play.setOrigin(table_back_play.getWidth() / 2,
				table_back_play.getHeight() / 2);
		table_level_selector.setOrigin(Align.center);
		table_level_selector.addAction(parallel(Actions.scaleTo(0.1f, 0.1f, 0f),
				Actions.fadeOut(0f)));

		chk1.setDisabled(true);
		chk2.setDisabled(true);
		chk3.setDisabled(true);
		chk1.getImage().setSize(
				ex01Types.VIRTUAL_SCREEN_WIDTH / 6.9f,
				ex01Types.VIRTUAL_SCREEN_WIDTH / 6.9f * 145f / 96f);
		chk2.getImage().setSize(
				ex01Types.VIRTUAL_SCREEN_WIDTH / 6.9f,
				ex01Types.VIRTUAL_SCREEN_WIDTH / 6.9f * 145f / 96f);
		chk3.getImage().setSize(
				ex01Types.VIRTUAL_SCREEN_WIDTH / 6.9f,
				ex01Types.VIRTUAL_SCREEN_WIDTH / 6.9f * 145f / 96f);
		chk_table.add(chk1)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 6.9f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 6.9f * 145f / 96f)
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 12.9f);
		chk_table.add(chk2)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 6.9f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 6.9f * 145f / 96f)
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 12.9f);
		chk_table.add(chk3)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 6.9f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 6.9f * 145f / 96f)
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 12.9f);
		chk1.getImageCell().reset();
		chk2.getImageCell().reset();
		chk3.getImageCell().reset();
    }

	public int GetPageScroolPositionNo(ex01MenuLevelSelectorBase page){
		return (int)page.getScrollX()/VIRTUAL_PAGE_WIDTH_PACE;
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
		get_money_table_bought.top().padTop(ex01Types.VIRTUAL_SCREEN_WIDTH/7.25f);
		get_money_table_bought.addAction(moveBy(0f,-25f,0f)); 
		level_base_coins_t_bought = new Table();
		level_base_coins_t_bought.setTransform(true);
		level_base_coins_bought = new TextButton("50 COINS", skin, "won_have2");	
		level_base_coins_t_bought.setFillParent(true);
		level_base_coins_t_bought
				.add(level_base_coins_bought)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.65f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.65f * 85f / 384f);
		level_base_coins_t_bought.bottom();
		settings_get_money_bought.add(level_base_coins_t_bought);
		level_base_coins_you_own_t_bought = new Table();
		level_base_coins_you_own_t_bought.setFillParent(true);
		level_base_coins_you_own_bought = new Image(new TextureRegionDrawable(
				atlas.findRegion("youwon")));
		level_base_coins_you_own_t_bought
				.add(level_base_coins_you_own_bought)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 3f * 80f / 256f);
		level_base_coins_you_own_t_bought.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/6.55f);
		settings_get_money_bought.add(level_base_coins_you_own_t_bought);
		level_base_coins_you_own_bought.addAction(Actions.alpha(0f));
		level_base_coins_bought.addAction(Actions.alpha(0f));
		stage.addActor(get_money_table_bought);
	}  
	
	public void EnterWonCoinsPhase(){
		if(settings_for_data.settings.sounds_on)
			cryo_game.menu_screen.cash_register_coins.play(SND_VOL.SOUND_BUY_COINS);
		level_base_coins_bought.setTransform(true);
		get_money_table_bought.clearActions();
		level_base_coins_bought.setOrigin(Align.center);
		level_base_coins_you_own_bought.setOrigin(Align.center);		
		settings_for_data.settings.number_coins += 50;
		cryo_game.menu_screen.get_spacecraft_dialog_screen.level_base_coins
				.getLabel().setText(Integer
				.toString(settings_for_data.settings.number_coins) + " Coins");
		cryo_game.menu_screen.get_lifeslots_screen.level_base_coins
				.getLabel().setText(Integer
				.toString(settings_for_data.settings.number_coins) + " Coins");
		cryo_game.menu_screen.get_bullets_screen.level_base_coins
				.getLabel().setText(Integer
				.toString(settings_for_data.settings.number_coins) + " Coins");
		level_base_coins_bought.clearActions();
		level_base_coins_you_own_bought.clearActions();
		level_base_coins_bought.addAction(parallel(
				Actions.fadeIn(1.0f),
				Actions.scaleTo(1.3f, 1.3f, 1.0f),
				Actions.sequence(Actions.moveBy(0f, 12f, 0.3f),
				Actions.rotateBy(360f, 2.4f, Swing.circleOut),
				Actions.moveBy(0f, -12f, 0.3f),
				Actions.run(new Runnable() {
			    public void run() {
				    level_base_coins_bought.addAction(parallel(
						Actions.scaleTo(1.0f, 1.0f, 1.0f),
						Actions.sequence(Actions.fadeOut(6.0f),
						Actions.run(new Runnable() {
					    public void run() {
						    cryo_image.addAction(Actions.alpha(0.85f, 3f));
					    }
				   }))));
			    }
		   }))));
		level_base_coins_you_own_bought.addAction(parallel(
				Actions.fadeIn(1.0f),
				Actions.scaleTo(1.3f, 1.3f, 1.0f),
				Actions.sequence(Actions.moveBy(0f, 12f, 0.3f),
				Actions.rotateBy(360f, 2.4f, Swing.circleOut),
				Actions.moveBy(0f, -12f, 0.3f), run(new Runnable() {
			    public void run() {
				    level_base_coins_you_own_bought.addAction(parallel(
						    Actions.scaleTo(1.0f, 1.0f, 1.0f),
						    Actions.fadeOut(6.0f)));
			    }
		   }))));
		settings_for_data.WriteJson();	
	}

    public void RankingTableInit(Skin skin){
		scores_table_ranking = new Table();
		scores_table_rank = new Table();
		ranking_table_back = new Table();
		scores_stack_ranking = new Stack();
		table_stage_ranking = new Table();
		TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
		tbs.font = skin.getFont("rankfonter");
		tbs.fontColor = Color.GREEN;
		tbs.up = new TextureRegionDrawable(skin.getRegion("ranking_top"));
		ranking_top = new TextButton("N/A", tbs);
		//----------
		selected_level = level_selector.received_open_from_level_no;
		cryo_game.menu_screen.rank_command_came_from_selector = true;
		if(!cryo_game.menu_screen.settings.settings.is_this_for_free) {
			cryo_game.menu_screen.scores_dialog_screen.GetScoreRankFromGoogleServicesSelector(
					level_selector.received_open_from_level_no + 1,
					get_social_score);
		}
		//----------
		ranking_top.getLabel().setAlignment(Align.center);
		ranking_top.getLabelCell().padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 24f);
		scores_table_rank.add(ranking_top)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.95f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.95f * 119f / 256f)
				.center();
		ranking_top.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(cryo_game.menu_screen.settings.settings.sounds_on)
					cryo_game.menu_screen.level_left_right_button
							.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
				ranking_top.addAction(sequence(
						Actions.moveBy(0f, 12f, 0.1f),
						Actions.moveBy(0f, -12f, 0.2f),
						Actions.run(new Runnable() {
							public void run() {
								ProcessRanksUponLevelOpen(ex01Types.CHANGE_SOCIAL);
							}
						})));
			}
		});
		scores_table_rank.pack();
		ranking_table_back = new Table();
		ranking_image_back = new Image(skin.getDrawable("back_login2"));
		ranking_image_back.addAction(Actions.alpha(0.95f));
		ranking_table_back.add(ranking_image_back)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.80f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.80f * 170f / 256f);
		scores_stack_ranking.add(ranking_table_back);
		scores_table_rank.padTop(-ex01Types.VIRTUAL_SCREEN_WIDTH / 64f);
		scores_stack_ranking.add(scores_table_rank);
		scores_table_ranking.add(scores_stack_ranking);
		refresh_on_STYLE = new ImageButton.ImageButtonStyle();
		refresh_on_TEX = skin.getRegion("refresh11");
		refresh_on_STYLE.up = new TextureRegionDrawable(new
				TextureRegionDrawable(refresh_on_TEX));
		scores_refresh = new ImageButton(refresh_on_STYLE);
		scores_refresh.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(cryo_game.menu_screen.settings.settings.sounds_on)
					cryo_game.menu_screen.level_left_right_button
							.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
				scores_refresh.addAction(sequence(
						Actions.moveBy(0f, 12f, 0.2f),
						Actions.moveBy(0f, -12f, 0.3f),
						Actions.run(new Runnable() {
							public void run() {
								if(!cryo_game.menu_screen.settings.settings.is_this_for_free) {
									if (cryo_game.exit_error.isWiFiConnected()) {
										if (cryo_game.menu_screen.menus.bIsLoggedIn) {
											DisplayLeaderboard(level_selector
													.received_open_from_level_no + 1);
										} else {
											ranking_top.setText(ex01Types.NOT_LOGGED_IN_ERROR);
										}
									} else {
										ranking_top.setText(ex01Types.NO_INTERNET_ERROR);
									}
								} else {
									ranking_top.setText(ex01Types.PREMIUM_ERROR);
									cryo_game.premium_interface.ShowPremium();
								}
							}
						})));
			}
		});
		scores_table_rank.row();
		scores_table_rank.add(scores_refresh)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.3f * 185f / 256f)
				.padTop(-ex01Types.VIRTUAL_SCREEN_WIDTH / 16.5f);
		scores_table_rank.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 80f);
		scores_table_ranking.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH * 1.055f)
							.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 100f);
		scores_table_ranking.addAction(parallel(Actions.scaleTo(0.1f, 0.1f, 0f),
											    Actions.fadeOut(0f)));
		scores_table_ranking.setFillParent(true);
		stage_ranking.addActor(scores_table_ranking);
	}

	public boolean came_from_left_right = false;

	public void ProcessRanksUponLevelOpen(boolean bChangeSocial){
		if(!cryo_game.menu_screen.settings.settings.is_this_for_free) {
			scores_table_ranking.invalidateHierarchy();
			if (cryo_game.exit_error.isWiFiConnected()) {
				if (cryo_game.menu_screen.menus.bIsLoggedIn) {
					if (!came_from_left_right) {
						//----------
						cryo_game.menu_screen.rank_command_came_from_selector = true;
						selected_level = level_selector.received_open_from_level_no;
						if (bChangeSocial) get_social_score = !get_social_score;
						cryo_game.menu_screen
								.scores_dialog_screen.GetScoreRankFromGoogleServicesSelector(
								level_selector.received_open_from_level_no + 1,
								get_social_score);
						//----------
					} else {
						if (cryo_game.menu_screen.menus.bIsLoggedIn) {
							ranking_top.setText(ex01Types.RANKING_REFRESH);
							came_from_left_right = false;
						}
					}
				} else {
					ranking_top.setText(ex01Types.NOT_LOGGED_IN_ERROR);
				}
			} else {
				ranking_top.setText(ex01Types.NO_INTERNET_ERROR);
			}
		} else {
			ranking_top.setText(ex01Types.PREMIUM_ERROR);
		}
	}

	public void  DisplayLeaderboard(int selected_lev){

	}

	public void DisableLeftRight(){
		page_no_graphicxs_table.clearActions();
		page_no_graphicxs_table.addAction(Actions.fadeOut(0.45f));
    	image_left_general.addAction(Actions.fadeOut(1.5f));
    	image_right_general.addAction(Actions.fadeOut(1.5f));
    	image_left_general.setDisabled(false);
    	image_right_general.setDisabled(false);
    	image_left_general.toBack();
    	image_right_general.toBack();
    	
    }
    
    public void EnableLeftRight(){
		page_no_graphicxs_table.addAction(Actions.alpha(ALPHA_PAGE, 1.5f));
    	image_left_general.addAction(Actions.fadeIn(1.5f));
    	image_right_general.addAction(Actions.fadeIn(1.5f));
    	image_left_general.setDisabled(false);
    	image_right_general.setDisabled(false);
    	image_left_general.toFront();
    	image_right_general.toFront();  	
    }
    
    public void CameFromGameScreenSetTo(int setLevel){
		level_selector.received_open_from_level_no = setLevel;
		ProcessLeftRight();
		ProcessClickLevelLeftRight();    	
    }
    
    public void ProcessLeftRight(){
    	level = level_selector.received_open_from_level_no+1;
		if(displaying_level_no){
	    	if(level < 10) {
	    		level_no_button.setText("LEVEL 0" + Integer.toString(level));
	    	} else {
	    		level_no_button.setText("LEVEL " + Integer.toString(level));
	    	}   			
		} else {
			level_no_button.setText(decimal_transform.format(
					settings_for_data.settings.level_angles[level-1]) + " DEG");
		}
    }   
    
    public void InitShaderTransition(){
		shader = new ShaderProgram(Gdx.files.internal("data/shaders/vignette.vert"),
								   Gdx.files.internal("data/shaders/vignette.frag"));
		resolution = new float[2];
		camera.position.set(ex01Types.VIRTUAL_SCREEN_WIDTH * 0.5f,
							ex01Types.VIRTUAL_SCREEN_HEIGHT * 0.5f,
							0.0f);
		stage.getBatch().setShader(shader);
		if (!shader.isCompiled()) {
			Gdx.app.error("Shader", shader.getLog());
		}
		state = State.TransitionIn;
		time = 0.0f;    	
    }
    
	public void ProcessAngleStars(int ang1, int ang2, int ang3){
		level = level_selector.received_open_from_level_no+1;
	  	switch(ang1) //smallest angle
    	{
        	case 35:
    			chk_style = skin.get("chk35", CheckBox.CheckBoxStyle.class);
    			chk1.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_1_achieved){
        			chk1.setChecked(true);
        		} else {
        			chk1.setChecked(false);
        		}        		
        		break;
        	case 30:
    			chk_style = skin.get("chk30", CheckBox.CheckBoxStyle.class);
    			chk1.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_1_achieved){
        			chk1.setChecked(true);
        		} else {
        			chk1.setChecked(false);
        		}        		
        		break;    
        	case 25:
    			chk_style = skin.get("chk25", CheckBox.CheckBoxStyle.class);
    			chk1.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_1_achieved){
        			chk1.setChecked(true);
        		} else {
        			chk1.setChecked(false);
        		}        		
        		break; 
        	case 20:
    			chk_style = skin.get("chk20", CheckBox.CheckBoxStyle.class);
    			chk1.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_1_achieved){
        			chk1.setChecked(true);
        		} else {
        			chk1.setChecked(false);
        		}        		
        		break; 
        	case 15:
    			chk_style = skin.get("chk15", CheckBox.CheckBoxStyle.class);
    			chk1.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_1_achieved){
        			chk1.setChecked(true);
        		} else {
        			chk1.setChecked(false);
        		}        		
        		break; 
        	case 10:
    			chk_style = skin.get("chk10", CheckBox.CheckBoxStyle.class);
    			chk1.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_1_achieved){
        			chk1.setChecked(true);
        		} else {
        			chk1.setChecked(false);
        		}        		
        		break; 
        	case 5:
    			chk_style = skin.get("chk5", CheckBox.CheckBoxStyle.class);
    			chk1.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_1_achieved){
        			chk1.setChecked(true);
        		} else {
        			chk1.setChecked(false);
        		}        		
        		break; 	        		
        	default:
        		break;
    	}
       	switch(ang2) //middle angle
    	{
        	case 35:
    			chk_style = skin.get("chk35", CheckBox.CheckBoxStyle.class);
    			chk2.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_2_achieved){
        			chk2.setChecked(true);
        		} else {
        			chk2.setChecked(false);
        		}        		
        		break;
        	case 30:
    			chk_style = skin.get("chk30", CheckBox.CheckBoxStyle.class);
    			chk2.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_2_achieved){
        			chk2.setChecked(true);
        		} else {
        			chk2.setChecked(false);
        		}        		
        		break;    
        	case 25:
    			chk_style = skin.get("chk25", CheckBox.CheckBoxStyle.class);
    			chk2.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_2_achieved){
        			chk2.setChecked(true);
        		} else {
        			chk2.setChecked(false);
        		}        		
        		break; 
        	case 20:
    			chk_style = skin.get("chk20", CheckBox.CheckBoxStyle.class);
    			chk2.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_2_achieved){
        			chk2.setChecked(true);
        		} else {
        			chk2.setChecked(false);
        		}        		
        		break; 
        	case 15:
    			chk_style = skin.get("chk15", CheckBox.CheckBoxStyle.class);
    			chk2.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_2_achieved){
        			chk2.setChecked(true);
        		} else {
        			chk2.setChecked(false);
        		}        		
        		break; 
        	case 10:
    			chk_style = skin.get("chk10", CheckBox.CheckBoxStyle.class);
    			chk2.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_2_achieved){
        			chk2.setChecked(true);
        		} else {
        			chk2.setChecked(false);
        		}        		
        		break; 
        	case 5:
    			chk_style = skin.get("chk5", CheckBox.CheckBoxStyle.class);
    			chk2.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_2_achieved){
        			chk2.setChecked(true);
        		} else {
        			chk2.setChecked(false);
        		}        		
        		break; 	        		
        	default:
        		break;
    	}
       	switch(ang3) //biggest most easy angle
    	{
        	case 35:
    			chk_style = skin.get("chk35", CheckBox.CheckBoxStyle.class);
    			chk3.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_3_achieved){
        			chk3.setChecked(true);
        		} else {
        			chk3.setChecked(false);
        		}        		
        		break;
        	case 30:
    			chk_style = skin.get("chk30", CheckBox.CheckBoxStyle.class);
    			chk3.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_3_achieved){
        			chk3.setChecked(true);
        		} else {
        			chk3.setChecked(false);
        		}        		
        		break;    
        	case 25:
    			chk_style = skin.get("chk25", CheckBox.CheckBoxStyle.class);
    			chk3.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_3_achieved){
        			chk3.setChecked(true);
        		} else {
        			chk3.setChecked(false);
        		}        		
        		break; 
        	case 20:
    			chk_style = skin.get("chk20", CheckBox.CheckBoxStyle.class);
    			chk3.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_3_achieved){
        			chk3.setChecked(true);
        		} else {
        			chk3.setChecked(false);
        		}        		
        		break; 
        	case 15:
    			chk_style = skin.get("chk15", CheckBox.CheckBoxStyle.class);
    			chk3.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_3_achieved){
        			chk3.setChecked(true);
        		} else {
        			chk3.setChecked(false);
        		}        		
        		break; 
        	case 10:
    			chk_style = skin.get("chk10", CheckBox.CheckBoxStyle.class);
    			chk3.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_3_achieved){
        			chk3.setChecked(true);
        		} else {
        			chk3.setChecked(false);
        		}        		
        		break; 
        	case 5:
    			chk_style = skin.get("chk5", CheckBox.CheckBoxStyle.class);
    			chk3.setStyle(chk_style);
    			if(level_selector.levels.get(level_selector.received_open_from_level_no)
						.level_3_achieved){
        			chk3.setChecked(true);
        		} else {
        			chk3.setChecked(false);
        		}        		
        		break; 	        		
        	default:
        		break;
    	}          			
	}
	
	public void ProcessClickLevel(){
		table_menu.addAction(scaleTo(0.3f, 0.3f, 2f));
		table_menu.addAction(fadeOut(1.25f));
    	scores_table_ranking.addAction(Actions.fadeIn(2.25f));
    	scores_table_ranking.addAction(sequence(
				Actions.scaleTo(1f, 1f, 2f, swingIn),
				Actions.run(new Runnable() {
					public void run() {
						bCanGetBackwards = true;
						ProcessRanksUponLevelOpen(ex01Types.DONT_CHANGE_SOCIAL);
					}})));

    	table_level_selector.addAction(Actions.fadeIn(1.25f));
    	table_level_selector.addAction(Actions.scaleTo(1f, 1f, 2f, swingOut));
    	chk1.setText("");
    	chk2.setText("");
    	chk3.setText("");
    	ProcessAngleStars(
				level_selector.levels
						.get(level_selector.received_open_from_level_no).level1_angle,
				level_selector.levels
						.get(level_selector.received_open_from_level_no).level2_angle,
				level_selector.levels
						.get(level_selector.received_open_from_level_no).level3_angle);
	}


	public void ProcessClickLevelLeftRight(){
    	ProcessAngleStars(
				level_selector.levels
						.get(level_selector.received_open_from_level_no).level1_angle,
				level_selector.levels
						.get(level_selector.received_open_from_level_no).level2_angle,
				level_selector.levels
						.get(level_selector.received_open_from_level_no).level3_angle);
	}
	
    public void RenderLevelDialogInit(){
    	imageb_play.clearListeners();
		imageb_play.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!still_working_play_press) {
					still_working_play_press = true;
					cryo_game.menu_screen.menu_music.pause();
					if (cryo_game.menu_screen.settings.settings.sounds_on)
						cryo_game.menu_screen.enter_play_game.play(SND_VOL.SOUND_ENTER_GAME);
					imageb_play.addAction(
							sequence(Actions.parallel(
									 Actions.sequence(
											Actions.moveBy(0f, 10f, 0.4f),
											Actions.moveBy(0f, -10f, 0.4f, swing)),
									 Actions.scaleTo(1.2f, 1.2f, 0.4f)),
									 Actions.run(new Runnable() {
						public void run() {
							//prepare in-game level data
							cryo_game.level_credentials.already_unlocked_next_level =
									settings_for_data.settings.levels.get(level).is_unlocked;
							cryo_game.level_credentials.passed_mission1_already =
									settings_for_data.settings.levels.get(level - 1)
											.is_mission_1_achieved;
							cryo_game.level_credentials.passed_mission2_already =
									settings_for_data.settings.levels.get(level - 1)
											.is_mission_2_achieved;
							cryo_game.level_credentials.passed_mission3_already =
									settings_for_data.settings.levels.get(level - 1)
											.is_mission_3_achieved;
							cryo_game.level_credentials.angle_to_beat =
									settings_for_data.settings.levels.get(level - 1).angle_to_beat;
							//prepare level load data
							angle1 = settings_for_data.settings.levels.get(level - 1).level1_angle;
							angle2 = settings_for_data.settings.levels.get(level - 1).level2_angle;
							angle3 = settings_for_data.settings.levels.get(level - 1).level3_angle;
							if (!cryo_game.already_loaded_loader_once) {
								cryo_game.setScreen(new ex01MenuLoadingScreen(settings_for_data,
										cryo_game,
										level,
										settings_for_data.settings.index_spaceship_selected,
										angle1,
										angle2,
										angle3));
							} else {
								cryo_game.loader.ex01MenuLoadingScreenReload(settings_for_data,
										cryo_game,
										level,
										settings_for_data.settings.index_spaceship_selected,
										angle1,
										angle2,
										angle3);
								cryo_game.setScreen(cryo_game.loader);
							}
							cryo_game.game_state = GameState.GAME_LOADING_SCREEN;
						}
					})));
				}
			}
		});
    }

	public void InitLevelGraphics(TextureAtlas atlas){
		page_no_graphicxsTR1 = atlas.findRegion("levelno1");
		page_no_graphicxsTR2 = atlas.findRegion("levelno2");
		page_no_graphicxsTR3 = atlas.findRegion("levelno3");
		page_no_graphicxsTR4 = atlas.findRegion("levelno4");
		page_no_graphicxsTRD1 = new TextureRegionDrawable(page_no_graphicxsTR1);
		page_no_graphicxsTRD2 = new TextureRegionDrawable(page_no_graphicxsTR2);
		page_no_graphicxsTRD3 = new TextureRegionDrawable(page_no_graphicxsTR3);
		page_no_graphicxsTRD4 = new TextureRegionDrawable(page_no_graphicxsTR4);
		page_no_graphicxs = new Image(new TextureRegionDrawable(page_no_graphicxsTRD1));
		page_no_graphicxs_table = new Table();
		page_no_graphicxs_table.add(page_no_graphicxs)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 6f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 6f * 42f / 96f);
		page_no_graphicxs_table.bottom().padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 5.25f);
		page_no_graphicxs_table.setFillParent(true);
		stage.addActor(page_no_graphicxs_table);
		page_no_graphicxs_table.addAction(Actions.alpha(ALPHA_PAGE));
	}

	public void DisposePageNo(){
		if(page_no_graphicxs!=null){
			page_no_graphicxs.clear();
			page_no_graphicxs = null;
		}
		page_no_graphicxsTR1 = null;
		page_no_graphicxsTR2 = null;
		page_no_graphicxsTR3 = null;
		page_no_graphicxsTR4 = null;
		page_no_graphicxsTRD1 = null;
		page_no_graphicxsTRD2 = null;
		page_no_graphicxsTRD3 = null;
		page_no_graphicxsTRD4 = null;
		if(page_no_graphicxs_table!=null){
			page_no_graphicxs_table.clear();
			page_no_graphicxs_table = null;
		}
	}

    @Override
	public void render(float delta) {
        if(!finished_transition) renderTransition(delta);
		stage.setDebugAll(false);
		stage.act(delta * SPEED_LEVEL_SELECTOR);
		stage.draw();
		stage_ranking.act(delta * SPEED_RANK_SELECTOR);
      	stage_ranking.draw();
    }

    public void renderTransition(float delta){
		radius = time / TRANSITION_IN_TIME;	
		if (time > TRANSITION_IN_TIME) {
			finished_transition = true;
			time = 0.0f;
			state = State.Picture;
		}
		radius = MathUtils.clamp(radius, 0.0f, 1.0f);
		time += delta;
		stage.getBatch().setProjectionMatrix(camera.combined);
		stage.getBatch().begin();
        shader.setUniform2fv("resolution", resolution, 0, 2);
        shader.setUniformf("radius", radius);
        stage.getBatch().end();

		stage_ranking.getBatch().setProjectionMatrix(camera.combined);
		stage_ranking.getBatch().begin();
        shader.setUniform2fv("resolution", resolution, 0, 2);
        shader.setUniformf("radius", radius);
        stage_ranking.getBatch().end();
    }
    
    public void InitShaderTimers(){
		finished_transition = false;
		stage.getBatch().setShader(shader);
		stage_ranking.getBatch().setShader(shader);
		state = State.TransitionIn;
		time = 0.0f;       	
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
		InitShaderTimers();
	}
	
	@Override
	public void dispose(){
		Dispose();
	}

	public boolean allow_only_from_menu_write_pass_flag = true;

	@Override
	public void show(){
		cryo_game.menu_screen.menus.bAlreadyPressedNewGame = false;
		InitShaderTimers();
		if(camefromgame){
			if(cryo_game.menu_screen.settings.settings.sounds_on) {
				cryo_game.menu_screen.menu_music.setVolume(ex01Types.MUSIC_MENU_LEVEL);
			} else {
				cryo_game.menu_screen.menu_music.setVolume(ex01Types.MUSIC_MENU_LEVEL_NULL);
			}
			cryo_game.menu_screen.menu_music.play();
			camefromgame = false; // only when came from game we do it otherwise it's non-sense
			allow_only_from_menu_write_pass_flag = false;
			cryo_game.game_screen.inputMultiplexer.removeProcessor(cryo_game.game_screen);
			cryo_game.game_screen.inputMultiplexer.clear();
		} else {
			if(!cryo_game.menu_screen.settings.settings.is_this_for_free) { //FREE
				if (!cryo_game.menu_screen.delay_achievements_for_next_restart) {
					cryo_game.menu_screen.CheckForAchievements();
				}
			}
		}
		if(allow_only_from_menu_write_pass_flag){
			settings_for_data.WriteJson();
		} else {
			allow_only_from_menu_write_pass_flag = true;
		}
		if(cryo_game.menu_screen.VALUE_screen.has_just_played_ye){
			EnterWonCoinsPhase();
			cryo_game.menu_screen.VALUE_screen.has_just_played_ye = false;
		} else {
			cryo_image.addAction(Actions.alpha(0.85f, 3f));
		}
	}

	public void Dispose(){
		if(table_level_selector!=null)
			table_level_selector.clear();
		if(table!=null)
			table.clear();
		if(table_menu!=null)
			table_menu.clear();
		if(chk_table!=null)
			chk_table.clear();
		if(table_back_play!=null)
			table_back_play.clear();
		if(table_back_play_general!=null)
			table_back_play_general.clear();
		if(image_level_selector_closed_table!=null)
			image_level_selector_closed_table.clear();
		if(scores_table_ranking!=null)
			scores_table_ranking.clear();
		if(scores_table_rank!=null)
			scores_table_rank.clear();
		if(ranking_table_back!=null)
			ranking_table_back.clear();
		if(level_avg_angle_table!=null)
			level_avg_angle_table.clear();
		if(level_go_left_right!=null)
			level_go_left_right.clear();
		if(level_no_table!=null)
			level_no_table.clear();
		if(cryo_image_table!=null)
			cryo_image_table.clear();
		if(backgroundImg!=null)
			backgroundImg.clear();
		if(scores_image_back!=null)
			scores_image_back.clear();
		if(login_image_back!=null)
			login_image_back.clear();
		if(ranking_image_back!=null)
			ranking_image_back.clear();
		if(image_level_selector!=null)
			image_level_selector.clear();
		if(image_level_selector_closed!=null)
			image_level_selector_closed.clear();
		if(cryo_image!=null)
			cryo_image.clear();
		if(imageb_play!=null)
			imageb_play.clear();
		if(imageb_back_general!=null)
			imageb_back_general.clear();
		if(imageb_play_general!=null)
			imageb_play_general.clear();
		if(image_left_general!=null)
			image_left_general.clear();
		if(image_right_general!=null)
			image_right_general.clear();
		if(level_go_left!=null)
			level_go_left.clear();
		if(level_go_right!=null)
			level_go_right.clear();
		if(scores_done!=null)
			scores_done.clear();
		if(login_button!=null)
			login_button.clear();
		if(scores_refresh!=null)
			scores_refresh.clear();
		if(ranking_top!=null)
			ranking_top.clear();
		if(level_no_button!=null)
			level_no_button.clear();
		if(level_avg_angle!=null)
			level_avg_angle.clear();
		if(chk1!=null)
			chk1.clear();
		if(chk2!=null)
			chk2.clear();
		if(chk3!=null)
			chk3.clear();
		if(backgroundStack!=null)
			backgroundStack.clear();
		if(stack_stage!=null)
			stack_stage.clear();
		if(table_stage_ranking!=null)
			table_stage_ranking.clear();
		if(stack_level_selector!=null)
			stack_level_selector.clear();
		if(scores_stack_ranking!=null)
			scores_stack_ranking.clear();

		table_level_selector = null;
		table = null;
		table_menu = null;
		chk_table = null;
		table_back_play = null;
		table_back_play_general = null;
		image_level_selector_closed_table = null;
		scores_table_ranking = null;
		scores_table_rank = null;
		ranking_table_back = null;
		level_avg_angle_table = null;
		level_go_left_right = null;
		level_no_table = null;
		cryo_image_table = null;
		backgroundImg = null;
		scores_image_back = null;
		login_image_back = null;
		ranking_image_back = null;
		image_level_selector = null;
		image_level_selector_closed = null;
		cryo_image = null;
		imageb_play = null;
		imageb_back_general = null;
		imageb_play_general = null;
		image_left_general = null;
		image_right_general = null;
		level_go_left = null;
		level_go_right = null;
		scores_done = null;
		login_button = null;
		scores_refresh = null;
		ranking_top = null;
		level_no_button = null;
		level_avg_angle = null;
		chk1 = null;
		chk2 = null;
		chk3 = null;
		backgroundStack = null;
		stack_stage = null;
		table_stage_ranking = null;
		stack_level_selector = null;
		scores_stack_ranking = null;
		chk_style = null;
		settings_for_data = null;
		ibs = null;
		ibs_ang = null;

		if(level_selector!=null){
			level_selector.Dispose();
			level_selector = null;
		}
		if(texture_atlas!=null) {
			texture_atlas.dispose();
			texture_atlas = null;
		}

		backgroundTexR = null;
		cryo_image_tr = null;

		if(stage!=null){
			stage.clear();
			stage = null;
		}
		if(stage_ranking!=null){
			stage_ranking.clear();
			stage_ranking = null;
		}
		if(skin!=null){
			skin.dispose();
			skin = null;
		}

		viewport = null;
		camera = null;

		if(shader!=null) {
			shader.end();
			shader.dispose();
		}
		if(debug_shaper!=null){
			debug_shaper.dispose();
			debug_shaper = null;
		}

		state = null;

		if(level_font!=null){
			level_font.dispose();
			level_font = null;
		}

		decimal_transform = null;

		if(level_base_coins_t_bought!=null) {
			level_base_coins_t_bought.clear();
			level_base_coins_t_bought = null;
		}
		if(level_base_coins_you_own_t_bought!=null) {
			level_base_coins_you_own_t_bought.clear();
			level_base_coins_you_own_t_bought = null;
		}
		if(level_base_coins_you_own_bought!=null) {
			level_base_coins_you_own_bought.clear();
			level_base_coins_you_own_bought = null;
		}
		if(level_base_coins_bought!=null) {
			level_base_coins_bought.clear();
			level_base_coins_bought = null;
		}
		if(get_money_table_bought!=null) {
			get_money_table_bought.clear();
			get_money_table_bought = null;
		}
		if(settings_get_money_bought!=null) {
			settings_get_money_bought.clear();
			settings_get_money_bought = null;
		}
		DisposePageNo();
		refresh_on_STYLE = null;
		refresh_on_TEX = null;
	}
}