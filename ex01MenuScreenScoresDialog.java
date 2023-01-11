//WMS3 2015

package com.cryotrax.game;
import java.lang.Character;
import static com.badlogic.gdx.math.Interpolation.swingIn;
import static com.badlogic.gdx.math.Interpolation.swingOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class ex01MenuScreenScoresDialog {
	public static final float SPEED_SCORES = 3f;
    public static final float width =
			ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f;
    public static final float height =
			ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f * 278f/430f;
	public Table scores_dialog;
	public Table scores_table_back;
	public Table login_table_back;
	public Table ranking_table_back;
	public Table scores_table_done;
	public Table scores_table_done_button;
	public Table scores_table_login;
    public Stack scores_stack_login;
    public Stack scores_dialog_stack;
    public Stage stage_scores_dialog;
    public Image scores_image_back;
    public Image ranking_image_back;
    public Image scores_image_settings;
    public ImageButton scores_done;
    public Table scores_table_settings;
    public TextButton ranking_top;
    public SelectBox<String> scores_select_box;
	public List<String> list;
    public Label.LabelStyle label_password_style;
    public Label.LabelStyle label_user_style;
    public Label label_password;
    public Label label_user;    
	public ex01MenuScreen base_menu;
	public OrthographicCamera camera;
	public InputProcessor backProcessor;
	public InputMultiplexer multiplexer;
	public Stack scores_stack_ranking;
	public Table ranking_table;
	public Table scores_table_ranking;
	public Table scores_table_rank;
	public ImageButton scores_refresh;
    public boolean still_working_on_done_press = false;
	public boolean get_social_score = true;
	public int selected_lev;

    //scores - combo for level selection, and list box
    public ex01MenuScreenScoresDialog(Skin skin,
									  ex01MenuScreen bmenu,
									  Viewport viewport,
									  OrthographicCamera cam) {
    	camera = cam;
    	stage_scores_dialog = new Stage(viewport);
    	base_menu = bmenu;
    	SelectBoxInit(skin);
		RankingTableInit(skin);
		MaxAndTries(skin);
    	scores_dialog_stack = new Stack();
    	scores_dialog_stack.setTransform(true);
    	scores_dialog = new Table();
    	scores_table_back = new Table();
    	scores_dialog.setTransform(true);
    	scores_image_back = new Image(skin.getDrawable("replay"));
    	scores_table_back.add(scores_image_back)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.05f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.05f * 896f / 512f);
    	scores_dialog_stack.add(scores_table_back);
    	scores_table_settings = new Table();
        scores_image_settings = new Image(skin.getDrawable("scoreslisting2"));     
        scores_table_settings
				.add(scores_image_settings)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.75f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.75f * 59f / 256f).top();
		scores_table_settings.top().padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 11f);
		scores_table_settings.addAction(Actions.alpha(0.80f));
        scores_dialog_stack.add(scores_table_settings);
    	scores_done = new ImageButton(skin, "done_button");
    	scores_done.pad(0f);        
    	scores_table_done = new Table();
    	scores_table_done.setTransform(true);
    	scores_table_done_button = new Table();
    	scores_table_done_button.row();
		scores_table_done_button.add(scores_done)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.1f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.1f * 106f / 256f)
				.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH * 1.4445f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 456f);
    	scores_table_done.add(scores_select_box)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.3f * 134f / 256f)
				.center()
				.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 4.55f)
				.padBottom(-ex01Types.VIRTUAL_SCREEN_WIDTH / 50f);
    	scores_table_done.row();
    	scores_table_done.add(scores_table_ranking)
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 480f)
				.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 100f);
    	scores_table_done.row();
		scores_table_done.add(max_and_tries_all)
				.padTop(-ex01Types.VIRTUAL_SCREEN_WIDTH / 120f);
		scores_table_done.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 6.45f);
    	scores_table_done.setTransform(true);
    	scores_dialog_stack.add(scores_table_done);
		scores_dialog_stack.add(scores_table_done_button);
		scores_dialog.addAction(Actions.moveBy(0f, -50f, 0f));
    	scores_dialog.setTransform(true);
		scores_dialog.addAction(Actions.sizeTo(width, height));
		scores_dialog.setPosition(ex01Types.VIRTUAL_SCREEN_WIDTH * 0.5f - width / 2,
				ex01Types.VIRTUAL_SCREEN_HEIGHT * 0.5f - height / 2);
		scores_dialog.addAction(Actions.fadeOut(0f));
    	scores_dialog.add(scores_dialog_stack);
		stage_scores_dialog.addActor(scores_dialog);
		stage_scores_dialog.act();

        scores_done.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!still_working_on_done_press){
					still_working_on_done_press = true;
					if(base_menu.settings.settings.sounds_on)
						base_menu.done_button.play(SND_VOL.DONE_BUTTON_VOLUME);
					base_menu.menus.scores_text.addAction(parallel(
							Actions.alpha(0.7f,3f),
							scaleTo(1.0f, 1.0f, 0.8f),
							sequence(moveBy(0f, 15f, 0.15f),
									moveBy(0f, -15f, 1f, swingOut))));
					scores_done.addAction(sequence(
							moveBy(0f, 12f, 0.1f),
							moveBy(0f, -12f, 0.2f)));
					scores_dialog.addAction(sequence(parallel(
							fadeOut(1.25f),
							moveBy(0f,-50f,1.25f,swingIn)),
							run(new Runnable(){
								public void run(){
									base_menu.menus.FadeIn_ExtraButtons();
									base_menu.menus.FadeInMenuButtons(1.5f);
									scores_dialog.addAction(Actions.fadeOut(0f));
									base_menu.grayscale_shader_active_scores = false;
									base_menu.menus.stage.getBatch().setShader(null);
									Gdx.input.setInputProcessor(base_menu.menus.stage);
									still_working_on_done_press = false;
								}
					})));
				}
			}	
		});		
        
    	backProcessor = new InputAdapter(){
    		@Override
    		public boolean keyDown(int keycode){
    			if(keycode == Keys.ESCAPE || keycode == Keys.BACK){
    				scores_done.addAction(sequence(
							moveBy(0f, 12f, 0.1f),
							moveBy(0f, -12f, 0.2f)));
    				camera.zoom += 0.2f;
    				camera.position.y += 250f;
    				camera.update();
    			}
    			return false;
    		}
    	};
    	multiplexer = new InputMultiplexer(stage_scores_dialog, backProcessor);
    }

    public void FadeInScoresDialog(float fade){
    	scores_dialog.addAction(Actions.fadeIn(fade));
    }
    
    public void SelectBoxInit(Skin skin){
        TextureRegion scroll_horizontal = skin.getRegion("scroll_horizontal");
        TextureRegion knob_scroll = skin.getRegion("knob_scroll");
        List.ListStyle listS = new List.ListStyle();
		listS.font = skin.getFont("list-selector-font2");
        listS.fontColorSelected = Color.GREEN;
        listS.fontColorUnselected = Color.NAVY;
        listS.selection = new TextureRegionDrawable(skin.getRegion("scores_list_bg_select_list"));
        listS.selection.setLeftWidth(ex01Types.VIRTUAL_SCREEN_WIDTH/5f);
		list = new List<String>(listS);
		Array<String> items = new Array<String>();
		items.add(" LEVEL - 01");
        items.add(" LEVEL - 02");
        items.add(" LEVEL - 03");
        items.add(" LEVEL - 04");
        items.add(" LEVEL - 05");
        items.add(" LEVEL - 06");
        items.add(" LEVEL - 07");
        items.add(" LEVEL - 08");
        items.add(" LEVEL - 09");
        items.add(" LEVEL - 10");
        items.add(" LEVEL - 11");
        items.add(" LEVEL - 12");
        items.add(" LEVEL - 13");
        items.add(" LEVEL - 14");
        items.add(" LEVEL - 15");
        items.add(" LEVEL - 16");
        items.add(" LEVEL - 17");
        items.add(" LEVEL - 18");
        items.add(" LEVEL - 19");
        items.add(" LEVEL - 20");
        items.add(" LEVEL - 21");
        items.add(" LEVEL - 22");
        items.add(" LEVEL - 23");
        items.add(" LEVEL - 24");
        items.add(" LEVEL - 25");
        items.add(" LEVEL - 26");
        items.add(" LEVEL - 27");
        items.add(" LEVEL - 28");
        items.add(" LEVEL - 29");
        items.add(" LEVEL - 30");
        items.add(" LEVEL - 31");
        items.add(" LEVEL - 32");
        items.add(" LEVEL - 33");
        items.add(" LEVEL - 34");
        items.add(" LEVEL - 35");
        items.add(" LEVEL - 36");
        items.add(" LEVEL - 37");
        items.add(" LEVEL - 38");
        items.add(" LEVEL - 39");
        items.add(" LEVEL - 40");
        items.add(" LEVEL - 41");
        items.add(" LEVEL - 42");
        items.add(" LEVEL - 43");
        items.add(" LEVEL - 44");
        items.add(" LEVEL - 45");
        items.add(" LEVEL - 46");
        items.add(" LEVEL - 47");
        items.add(" LEVEL - 48");
        items.add(" LEVEL - 49");
        items.add(" LEVEL - 50");
        items.add(" LEVEL - 51");
        items.add(" LEVEL - 52");
        items.add(" LEVEL - 53");
        items.add(" LEVEL - 54");
        items.add(" LEVEL - 55");
        items.add(" LEVEL - 56");
        items.add(" LEVEL - 57");
        items.add(" LEVEL - 58");
        items.add(" LEVEL - 59");
        items.add(" LEVEL - 60");
        items.add(" LEVEL - 61");
        items.add(" LEVEL - 62");
        items.add(" LEVEL - 63");
        items.add(" LEVEL - 64");
        ScrollPane.ScrollPaneStyle sps = new ScrollPane.ScrollPaneStyle();
        sps.background = new TextureRegionDrawable(skin.getRegion("scores_list_bg"));   
        sps.vScroll = new TextureRegionDrawable(new TextureRegion(scroll_horizontal));
        sps.vScrollKnob = new TextureRegionDrawable(new TextureRegion(knob_scroll));
        SelectBox.SelectBoxStyle sbs = new SelectBox.SelectBoxStyle();
        sbs.listStyle = listS;    
        sbs.scrollStyle = sps;
        sbs.background = new TextureRegionDrawable(skin.getRegion("combo_scores_closed"));
			sbs.background.setLeftWidth(ex01Types.VIRTUAL_SCREEN_WIDTH/10.250f);
        sbs.background.setTopHeight(ex01Types.VIRTUAL_SCREEN_WIDTH/16.5f);
		sbs.font = skin.getFont("list-selector-font2");
        Color colorize = new Color(1, 0.78f, 0,0.875f);
        sbs.fontColor.set(colorize);
        sbs.backgroundOpen = new TextureRegionDrawable(skin.getRegion("combo_scores_open"));
        sbs.backgroundOpen.setLeftWidth(ex01Types.VIRTUAL_SCREEN_WIDTH/10.250f);
        sbs.backgroundOpen.setTopHeight(ex01Types.VIRTUAL_SCREEN_WIDTH/16.5f);
        listS.selection.setLeftWidth(ex01Types.VIRTUAL_SCREEN_WIDTH/10.250f);
        scores_select_box = new SelectBox<String>(sbs);
      	scores_select_box.setItems(items); 
        scores_select_box.setMaxListCount(6);
        scores_select_box.pack();
        scores_select_box.pack();
		scores_select_box.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(!base_menu.settings.settings.is_this_for_free) {
					GetScoreRankGeneral();
				} else {
					if(ranking_top!=null) {
						ranking_top.setText(ex01Types.PREMIUM_ERROR);
					}
				}
			}
		});
	}

	public void GetScoreRankGeneral(){
		selected_level = scores_select_box.getSelectedIndex();
		GetScoreRankFromGoogleServices(selected_level+1);
		ProcessMaxAndTriesGetFromSettings();
	}

	public void  DisplayLeaderboard(int selected_lev){
		/*
		this.selected_lev = selected_lev;
		switch(selected_lev){
			case 1: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD01); } break;
			case 2: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD02); } break;
			case 3: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD03); } break;
			case 4: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD04); } break;
			case 5: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD05); } break;
			case 6: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD06); } break;
			case 7: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD07); } break;
			case 8: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD08); } break;
			case 9: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD09); } break;
			case 10: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD10); } break;
			case 11: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD11); } break;
			case 12: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD12); } break;
			case 13: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD13); } break;
			case 14: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD14); } break;
			case 15: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD15); } break;
			case 16: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD16); } break;
			case 17: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD17); } break;
			case 18: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD18); } break;
			case 19: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD19); } break;
			case 20: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD20); } break;
			case 21: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD21); } break;
			case 22: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD22); } break;
			case 23: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD23); } break;
			case 24: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD24); } break;
			case 25: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD25); } break;
			case 26: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD26); } break;
			case 27: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD27); } break;
			case 28: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD28); } break;
			case 29: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD29); } break;
			case 30: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD30); } break;
			case 31: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD31); } break;
			case 32: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD32); } break;
			case 33: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD33); } break;
			case 34: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD34); } break;
			case 35: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD35); } break;
			case 36: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD36); } break;
			case 37: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD37); } break;
			case 38: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD38); } break;
			case 39: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD39); } break;
			case 40: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD40); } break;
			case 41: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD41); } break;
			case 42: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD42); } break;
			case 43: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD43); } break;
			case 44: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD44); } break;
			case 45: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD45); } break;
			case 46: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD46); } break;
			case 47: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD47); } break;
			case 48: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD48); } break;
			case 49: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD49); } break;
			case 50: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD50); } break;
			case 51: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD51); } break;
			case 52: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD52); } break;
			case 53: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD53); } break;
			case 54: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD54); } break;
			case 55: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD55); } break;
			case 56: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD56); } break;
			case 57: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD57); } break;
			case 58: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD58); } break;
			case 59: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD59); } break;
			case 60: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD60); } break;
			case 61: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD61); } break;
			case 62: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD62); } break;
			case 63: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD63); } break;
			case 64: { base_menu.cryo_game.google_facebook_services
					.onShowLeaderboardsRequested(ex01Types.LEADERBOARD64); } break;
			default: { } break;
		}
		*/
	}

	public void  GetScoreRankFromGoogleServices(int selected_lev){
		/*
		this.selected_lev = selected_lev;
		switch(selected_lev){
			case 1: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD01,
							selected_lev, get_social_score, base_menu); } break;
			case 2: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD02,
							selected_lev, get_social_score, base_menu); } break;
			case 3: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD03,
							selected_lev, get_social_score, base_menu); } break;
			case 4: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD04,
							selected_lev, get_social_score, base_menu); } break;
			case 5: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD05,
							selected_lev, get_social_score, base_menu); } break;
			case 6: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD06,
							selected_lev, get_social_score, base_menu); } break;
			case 7: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD07,
							selected_lev, get_social_score, base_menu); } break;
			case 8: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD08,
							selected_lev, get_social_score, base_menu); } break;
			case 9: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD09,
							selected_lev, get_social_score, base_menu); } break;
			case 10: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD10,
							selected_lev, get_social_score, base_menu); } break;
			case 11: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD11,
							selected_lev, get_social_score, base_menu); } break;
			case 12: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD12,
							selected_lev, get_social_score, base_menu); } break;
			case 13: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD13,
							selected_lev, get_social_score, base_menu); } break;
			case 14: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD14,
							selected_lev, get_social_score, base_menu); } break;
			case 15: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD15,
							selected_lev, get_social_score, base_menu); } break;
			case 16: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD16,
							selected_lev, get_social_score, base_menu); } break;
			case 17: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD17,
							selected_lev, get_social_score, base_menu); } break;
			case 18: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD18,
							selected_lev, get_social_score, base_menu); } break;
			case 19: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD19,
							selected_lev, get_social_score, base_menu); } break;
			case 20: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD20,
							selected_lev, get_social_score, base_menu); } break;
			case 21: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD21,
							selected_lev, get_social_score, base_menu); } break;
			case 22: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD22,
							selected_lev, get_social_score, base_menu); } break;
			case 23: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD23,
							selected_lev, get_social_score, base_menu); } break;
			case 24: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD24,
							selected_lev, get_social_score, base_menu); } break;
			case 25: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD25,
							selected_lev, get_social_score, base_menu); } break;
			case 26: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD26,
							selected_lev, get_social_score, base_menu); } break;
			case 27: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD27,
							selected_lev, get_social_score, base_menu); } break;
			case 28: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD28,
							selected_lev, get_social_score, base_menu); } break;
			case 29: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD29,
							selected_lev, get_social_score, base_menu); } break;
			case 30: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD30,
							selected_lev, get_social_score, base_menu); } break;
			case 31: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD31,
							selected_lev, get_social_score, base_menu); } break;
			case 32: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD32,
							selected_lev, get_social_score, base_menu); } break;
			case 33: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD33,
							selected_lev, get_social_score, base_menu); } break;
			case 34: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD34,
							selected_lev, get_social_score, base_menu); } break;
			case 35: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD35,
							selected_lev, get_social_score, base_menu); } break;
			case 36: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD36,
							selected_lev, get_social_score, base_menu); } break;
			case 37: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD37,
							selected_lev, get_social_score, base_menu); } break;
			case 38: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD38,
							selected_lev, get_social_score, base_menu); } break;
			case 39: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD39,
							selected_lev, get_social_score, base_menu); } break;
			case 40: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD40,
							selected_lev, get_social_score, base_menu); } break;
			case 41: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD41,
							selected_lev, get_social_score, base_menu); } break;
			case 42: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD42,
							selected_lev, get_social_score, base_menu); } break;
			case 43: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD43,
							selected_lev, get_social_score, base_menu); } break;
			case 44: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD44,
							selected_lev, get_social_score, base_menu); } break;
			case 45: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD45,
							selected_lev, get_social_score, base_menu); } break;
			case 46: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD46,
							selected_lev, get_social_score, base_menu); } break;
			case 47: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD47,
							selected_lev, get_social_score, base_menu); } break;
			case 48: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD48,
							selected_lev, get_social_score, base_menu); } break;
			case 49: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD49,
							selected_lev, get_social_score, base_menu); } break;
			case 50: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD50,
							selected_lev, get_social_score, base_menu); } break;
			case 51: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD51,
							selected_lev, get_social_score, base_menu); } break;
			case 52: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD52,
							selected_lev, get_social_score, base_menu); } break;
			case 53: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD53,
							selected_lev, get_social_score, base_menu); } break;
			case 54: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD54,
							selected_lev, get_social_score, base_menu); } break;
			case 55: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD55,
							selected_lev, get_social_score, base_menu); } break;
			case 56: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD56,
							selected_lev, get_social_score, base_menu); } break;
			case 57: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD57,
							selected_lev, get_social_score, base_menu); } break;
			case 58: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD58,
							selected_lev, get_social_score, base_menu); } break;
			case 59: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD59,
							selected_lev, get_social_score, base_menu); } break;
			case 60: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD60,
							selected_lev, get_social_score, base_menu); } break;
			case 61: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD61,
							selected_lev, get_social_score, base_menu); } break;
			case 62: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD62,
							selected_lev, get_social_score, base_menu); } break;
			case 63: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD63,
							selected_lev, get_social_score, base_menu); } break;
			case 64: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD64,
							selected_lev, get_social_score, base_menu); } break;
			default: { } break;
		}
		*/
	}

	public void  GetScoreRankFromGoogleServicesSelector(
			int selected_levo,
			boolean get_social_score_selector){
		/*
		switch(selected_levo){
			case 1: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD01,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 2: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD02,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 3: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD03,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 4: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD04,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 5: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD05,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 6: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD06,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 7: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD07,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 8: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD08,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 9: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD09,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 10: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD10,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 11: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD11,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 12: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD12,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 13: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD13,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 14: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD14,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 15: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD15,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 16: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD16,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 17: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD17,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 18: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD18,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 19: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD19,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 20: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD20,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 21: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD21,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 22: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD22,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 23: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD23,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 24: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD24,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 25: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD25,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 26: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD26,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 27: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD27,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 28: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD28,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 29: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD29,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 30: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD30,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 31: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD31,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 32: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD32,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 33: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD33,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 34: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD34,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 35: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD35,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 36: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD36,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 37: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD37,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 38: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD38,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 39: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD39,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 40: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD40,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 41: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD41,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 42: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD42,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 43: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD43,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 44: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD44,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 45: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD45,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 46: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD46,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 47: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD47,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 48: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD48,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 49: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD49,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 50: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD50,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 51: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD51,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 52: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD52,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 53: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD53,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 54: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD54,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 55: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD55,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 56: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD56,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 57: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD57,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 58: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD58,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 59: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD59,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 60: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD60,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 61: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD61,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 62: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD62,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 63: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD63,
							selected_levo, get_social_score_selector, base_menu); } break;
			case 64: { base_menu.cryo_game.google_facebook_services
					.onGetRankLeaderboardsRequested(ex01Types.LEADERBOARD64,
							selected_levo, get_social_score_selector, base_menu); } break;
			default: { } break;
		}
		*/
	}

	public Table max_and_tries_table;
	public Table max_and_tries_back_table;
	public Table max_and_tries_all;
	public TextButton max_angle_button;
	public TextButton no_tries_button;
	public Image max_tries_image_back;
	public Stack max_tries_stack;
	public int selected_level = 1;

	public void MaxAndTries(Skin skin){
		max_and_tries_table = new Table();
		max_and_tries_all = new Table();
		max_and_tries_back_table = new Table();
		max_tries_stack = new Stack();
		TextButton.TextButtonStyle tbs1 = new TextButton.TextButtonStyle();
		tbs1.font = skin.getFont("scorescombo3-font170");
		tbs1.fontColor = Color.MAROON;
		tbs1.up = new TextureRegionDrawable(skin.getRegion("maxangle2"));
		TextButton.TextButtonStyle tbs2 = new TextButton.TextButtonStyle();
		tbs2.font = skin.getFont("scorescombo3-font170");
		tbs2.fontColor = Color.MAROON;
		tbs2.up = new TextureRegionDrawable(skin.getRegion("notries2"));
		max_angle_button = new TextButton("23.20 deg", tbs1);
		max_angle_button.getLabel().setAlignment(Align.center);
		max_angle_button.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 24f);
		max_angle_button.getLabelCell().padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 28f);
		no_tries_button = new TextButton("123", tbs2);
		no_tries_button.getLabel().setAlignment(Align.center);
		no_tries_button.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 24f);
		no_tries_button.getLabelCell().padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 28f);
		max_and_tries_table.add(max_angle_button)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.95f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.95f * 119f / 256f)
				.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 24f);
		max_and_tries_table.row();
		max_and_tries_table.add(no_tries_button)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.95f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.95f * 119f / 256f)
				.padTop(-ex01Types.VIRTUAL_SCREEN_WIDTH / 48f);
		max_tries_image_back = new Image(skin.getDrawable("back_login2"));
		max_and_tries_back_table.add(max_tries_image_back)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.80f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.80f * 170f / 256f);
		max_tries_stack.add(max_and_tries_back_table);
		max_and_tries_table.padTop(-ex01Types.VIRTUAL_SCREEN_WIDTH / 64f);
		max_tries_stack.add(max_and_tries_table);
		max_and_tries_all.add(max_tries_stack);
		ProcessMaxAndTriesGetFromSettings();
	}

	public void ProcessMaxAndTriesGetFromSettings(){
		try {
			max_angle_button.setText(Double
					.toString(base_menu.settings.settings.level_angles[selected_level]) + " deg");
		} catch (Exception ex) {
		}

		try {
			no_tries_button.setText(Short
					.toString(base_menu.settings.settings.level_notries[selected_level]) + " tries");
		} catch (Exception ex) {
		}
	}

	public ImageButton.ImageButtonStyle refresh_on_STYLE;
	public TextureRegion refresh_on_TEX;

    public void RankingTableInit(Skin skin){
    	scores_table_ranking = new Table();
    	scores_table_rank = new Table();
        ranking_table = new Table();
    	scores_stack_ranking = new Stack();
    	TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
		tbs.font = skin.getFont("rankfonter");
    	tbs.fontColor = Color.GREEN;
    	tbs.up = new TextureRegionDrawable(skin.getRegion("ranking_top"));
    	ranking_top = new TextButton("N/A", tbs);
		//----------
		selected_level = scores_select_box.getSelectedIndex();
		GetScoreRankFromGoogleServices(selected_level + 1);
		//----------
		ranking_top.getLabel().setAlignment(Align.center);
		ranking_top.getLabelCell().padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 24f);
    	scores_table_rank.add(ranking_top)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.95f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.95f * 119f / 256f);
		ranking_top.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(base_menu.settings.settings.sounds_on)
					base_menu.level_left_right_button.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
				ranking_top.addAction(sequence(
						moveBy(0f, 12f, 0.1f),
						moveBy(0f, -12f, 0.2f),
						Actions.run(new Runnable() {
							public void run() {
								if(!base_menu.settings.settings.is_this_for_free) {
									if (base_menu.cryo_game.exit_error.isWiFiConnected()) {
										if (base_menu.menus.bIsLoggedIn) {
											//----------
											selected_level = scores_select_box.getSelectedIndex();
											get_social_score = !get_social_score;
											GetScoreRankFromGoogleServices(selected_level + 1);
											//----------
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
				})));
			}
		});
    	scores_table_rank.row();
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
				if(base_menu.settings.settings.sounds_on)
					base_menu.level_left_right_button.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
				scores_refresh.addAction(sequence(
						moveBy(0f, 12f, 0.2f),
						moveBy(0f, -12f, 0.3f),
						Actions.run(new Runnable() {
							public void run() {
								if(!base_menu.settings.settings.is_this_for_free) {
									if (base_menu.cryo_game.exit_error.isWiFiConnected()) {
										if (base_menu.menus.bIsLoggedIn) {
											DisplayLeaderboard(selected_level + 1);
										} else {
											ranking_top.setText(ex01Types.NOT_LOGGED_IN_ERROR);
										}
									} else {
										ranking_top.setText(ex01Types.NO_INTERNET_ERROR);
									}
								} else {
									ranking_top.setText(ex01Types.PREMIUM_ERROR);
									base_menu.cryo_game.premium_interface.ShowPremium();
								}
							}
				})));
			}
		});	   
        
        scores_table_rank.row();
        scores_table_rank.add(scores_refresh)
				.colspan(1)
				.pad(0f)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.3f * 185f / 256f)
				.padTop(-ex01Types.VIRTUAL_SCREEN_WIDTH / 16.5f);
		scores_table_rank.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 80f);
    }
    
    public void Render(float delta){
		stage_scores_dialog.act(delta*SPEED_SCORES);
		stage_scores_dialog.draw();
    }

	public void Dispose(){
		if(scores_dialog!=null)
			scores_dialog.clear();
		if(scores_table_back!=null)
			scores_table_back.clear();
		if(login_table_back!=null)
			login_table_back.clear();
		if(ranking_table_back!=null)
			ranking_table_back.clear();
		if(scores_table_done!=null)
			scores_table_done.clear();
		if(scores_table_done_button!=null)
			scores_table_done_button.clear();
		if(scores_table_login!=null)
			scores_table_login.clear();
		if(scores_table_settings!=null)
			scores_table_settings.clear();
		if(ranking_table!=null)
			ranking_table.clear();
		if(scores_table_ranking!=null)
			scores_table_ranking.clear();
		if(scores_table_rank!=null)
			scores_table_rank.clear();
		if(scores_stack_login!=null)
			scores_stack_login.clear();
		if(scores_dialog_stack!=null)
			scores_dialog_stack.clear();
		if(scores_stack_ranking!=null)
			scores_stack_ranking.clear();
		if(scores_image_back!=null)
			scores_image_back.clear();
		if(ranking_image_back!=null)
			ranking_image_back.clear();
		if(scores_image_settings!=null)
			scores_image_settings.clear();
		if(scores_done!=null)
			scores_done.clear();
		if(scores_refresh!=null)
			scores_refresh.clear();
		if(ranking_top!=null)
			ranking_top.clear();
		if(label_password!=null)
			label_password.clear();
		if(label_user!=null)
			label_user.clear();

		scores_dialog = null;;
		scores_table_back = null;;
		login_table_back = null;;
		ranking_table_back = null;;
		scores_table_done = null;;
		scores_table_done_button = null;;
		scores_table_login = null;;
		scores_table_settings = null;;
		ranking_table = null;;
		scores_table_ranking = null;;
		scores_table_rank = null;;
		scores_stack_login = null;;
		scores_dialog_stack = null;
		scores_stack_ranking = null;;
		scores_image_back = null;;
		ranking_image_back = null;;
		scores_image_settings = null;;
		scores_done = null;;
		scores_refresh = null;;
		ranking_top = null;;
		label_password = null;;
		label_user = null;;

		label_password_style = null;
		label_user_style = null;

		if(stage_scores_dialog!=null){
			stage_scores_dialog.clear();
			stage_scores_dialog = null;
		}
		if(scores_select_box!=null){
			scores_select_box.clearItems();
			scores_select_box.clear();
			scores_select_box = null;
		}
		base_menu = null;
		camera = null;
		backProcessor = null;
		if(multiplexer!=null){
			multiplexer.clear();
			multiplexer = null;
		}

		refresh_on_STYLE = null;
		refresh_on_TEX = null;
	}
}
