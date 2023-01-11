//WMS3 2015

package com.cryotrax.game;
import static com.badlogic.gdx.math.Interpolation.swingIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.Array;

public class ex01MenuScreenListerPos {
	public static final float SPEED_SPACECRAFT = 3f;
	public List<String> list_position;
	public ScrollPane list_position_spane;
	public Table list_table;
	public Stage list_stage;
    public ImageButton lister_done;
    public Table lister_table_done;
    public Stack stack_lister;
	public ex01MenuScreen base_menu;
	public Image image_back;
	public Table table_image_back;
	public Table table_lister;
	public boolean still_working_on_done_press;
	public Table listername_table_settings;
	public Image listername_image_settings;
	public Table list_and_tries_back_table;
	public Image list_tries_image_back;
	public Stack list_tries_stack;
	public ImageButton profile_button;
	public Table profile_button_table;
	
	public ex01MenuScreenListerPos(Skin skin,
								   ex01MenuScreen bmenu,
								   Viewport viewport) {
        list_stage = new Stage(viewport);
        base_menu = bmenu;
        image_back  = new Image(skin.getDrawable("replay"));
        table_image_back = new Table();
        table_image_back
				.add(image_back)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/1.05f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.05f * 896f / 512f);
        TextureRegion scroll_horizontal = skin.getRegion("scroll_horizontal");
        TextureRegion knob_scroll = skin.getRegion("knob_scroll");
		List.ListStyle listS = new List.ListStyle();
		listS.font = skin.getFont("list-selector-font2");
		TextureRegionDrawable selecter = new
				TextureRegionDrawable(skin.getRegion("scores_list_bg_select_list"));
		table_image_back.center();
		listS.selection = selecter;
		listS.fontColorSelected = Color.WHITE;
		listS.fontColorUnselected = Color.BLUE;
		listS.selection.setRightWidth(ex01Types.VIRTUAL_SCREEN_WIDTH / 50f);
		list_position = new List<String>(listS);
		Array<String> items = new Array<String>();
		items.add("  .....");
		items.add("  item2");
		items.add("  item3");
		items.add("  item4");
		items.add("  item5");
		items.add("  item6");
		items.add("  item7");
		items.add("  item8");
		items.add("  item9");
		items.add("  item51");
		items.add("  item61");
		items.add("  item71");
		items.add("  item81");
		items.add("  .....");
		list_position.setItems(items);
		list_position.pack();
        ScrollPane.ScrollPaneStyle sps = new
				ScrollPane.ScrollPaneStyle();
        sps.background = new
				TextureRegionDrawable(skin.getRegion("scores_list_bg"));
        sps.vScroll = new
				TextureRegionDrawable(new TextureRegion(scroll_horizontal));
        sps.vScrollKnob = new
				TextureRegionDrawable(new TextureRegion(knob_scroll));
        list_position_spane = new
				ScrollPane(list_position,sps);
        list_table = new Table();

		list_and_tries_back_table = new Table();
		list_tries_image_back = new Image(skin.getDrawable("back_login2"));
		list_and_tries_back_table
				.add(list_tries_image_back)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.80f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.80f * 170f / 256f);
		list_and_tries_back_table
				.top()
				.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.725f);
        list_table
				.add(list_position_spane)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.85f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.75f * 118f / 256f * 1.6f)
				.center()
				.padTop(-ex01Types.VIRTUAL_SCREEN_WIDTH / 2.250f)
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 45.00f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 70.0f);
		list_table.setTransform(true);
		list_table.addAction(Actions.alpha(0.85f));
        list_table.setFillParent(true);

		lister_table_done = new Table();
        lister_done = new ImageButton(skin, "done_button");
        lister_done.pad(0f);
        lister_table_done
				.add(lister_done)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.1f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.1f * 106f / 256f)
				.colspan(2);
		lister_table_done.bottom().padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 24f);

		lister_done.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!still_working_on_done_press) {
					still_working_on_done_press = true;
					if (base_menu.settings.settings.sounds_on)
						base_menu.done_button.play(SND_VOL.DONE_BUTTON_VOLUME);
					lister_done.addAction(sequence(
							moveBy(0f, 12f, 0.1f),
							moveBy(0f, -12f, 0.2f)));
					table_lister.addAction(sequence(
							parallel(fadeOut(1.25f),
									moveBy(0f, -25f, 1.25f, swingIn)),
							run(new Runnable() {
								public void run() {
									base_menu.scores_dialog_screen.FadeInScoresDialog(1.5f);
									base_menu.grayscale_shader_active_scores = true;
									base_menu.grayscale_shader_active_scores_lister = false;
									Gdx.input.setInputProcessor(base_menu
											.scores_dialog_screen.stage_scores_dialog);
									still_working_on_done_press = false;
								}
					})));
				}
			}
		});

		listername_table_settings = new Table();
		listername_image_settings = new Image(skin.getDrawable("leaderboard2"));
		listername_table_settings.add(listername_image_settings)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.75f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.75f * 59f / 256f).top();
		listername_table_settings.top().padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 11f);
		listername_table_settings.addAction(Actions.alpha(0.80f));

		MaxAndTries(skin);

		stack_lister = new Stack();
        stack_lister.add(table_image_back);
		stack_lister.add(listername_table_settings);
		stack_lister.add(list_and_tries_back_table);
        stack_lister.add(list_table);
		stack_lister.add(max_and_tries_all);
        stack_lister.add(lister_table_done);
		table_lister = new Table();
		table_lister.add(stack_lister);
		table_lister.setFillParent(true);
		list_stage.addAction(Actions.moveBy(0f, -25f, 0f));
		list_stage.addActor(table_lister);
        list_position_spane.addAction(Actions.alpha(0.8f));
	}

	public Table max_and_tries_table;
	public Table max_and_tries_back_table;
	public Table max_and_tries_all;
	public TextButton max_angle_button;
	public TextButton no_tries_button;
	public Image max_tries_image_back;
	public Stack max_tries_stack;

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
		max_angle_button.getLabelCell().padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 24f);
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
		max_and_tries_all.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH/2.35f);
	}

	public void Render(float delta){
		list_stage.act(delta*SPEED_SPACECRAFT);
		list_stage.draw();
	}
	
	public void Dispose(){
		if(list_table!= null)
			list_table.clear();
		if(lister_table_done!= null)
			lister_table_done.clear();
		if(table_image_back!= null)
			table_image_back.clear();
		if(table_lister!= null)
			table_lister.clear();
		if(listername_table_settings!= null)
			listername_table_settings.clear();
		if(list_and_tries_back_table!= null)
			list_and_tries_back_table.clear();
		if(profile_button_table!= null)
			profile_button_table.clear();
		if(lister_done!= null)
			lister_done.clear();
		if(profile_button!= null)
			profile_button.clear();
		if(stack_lister!= null)
			stack_lister.clear();
		if(list_tries_stack!= null)
			list_tries_stack.clear();
		if(image_back!= null)
			image_back.clear();
		if(listername_image_settings!= null)
			listername_image_settings.clear();
		if(list_tries_image_back!= null)
			list_tries_image_back.clear();

		list_table = null;
		lister_table_done = null;
		table_image_back = null;
		table_lister = null;
		listername_table_settings = null;
		list_and_tries_back_table = null;
		profile_button_table = null;
		lister_done = null;
		profile_button = null;
		stack_lister = null;
		list_tries_stack = null;
		image_back = null;
		listername_image_settings = null;
		list_tries_image_back = null;

		if(list_stage!=null){
			list_stage.clear();
			list_stage.dispose();
			list_stage = null;
		}
		base_menu = null;
		list_position_spane = null;
		if(list_position!=null){
			list_position.clearItems();
			list_position.clear();
			list_position = null;
		}
	}
}
