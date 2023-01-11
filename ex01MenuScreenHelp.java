//WMS3 2015

package com.cryotrax.game;
import static com.badlogic.gdx.math.Interpolation.swingIn;
import static com.badlogic.gdx.math.Interpolation.swingOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ex01MenuScreenHelp {
	public static final float SPEED_HELP = 3f;
    public ImageButton help_left;
    public ImageButton help_right;
    public Image[] help_images;
	public boolean still_working_on_done_press = false;
    public Table table_left_right;
	public Table help_table_done;
    public Stack stack_left_right;
    public Stage stage_help;
    public ImageButton help_done;
	public ex01MenuScreen base_menu;
	public TextureAtlas help_images_atlas;
	public Image image1_helprules;
	public Image image2_help;
	public Image image3_helpcontrols;
	public Table image1;
	public Table image2;
	public Table image3;
	public int selected_image = 1;
	
	public ex01MenuScreenHelp(
			Skin skin,
			ex01MenuScreen bmenu,
			Viewport viewport,
			TextureAtlas atlas) {
		stage_help = new Stage(viewport);
    	base_menu = bmenu;
		help_images_atlas = atlas;
        help_left = new ImageButton(skin, "left_button");
        help_right = new ImageButton(skin, "right_button");
        table_left_right = new Table();
        table_left_right.add(help_left)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f * 310f / 128f)
				.padRight(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f);
        table_left_right.add(help_right)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f * 310f / 128f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 2.8f);
        table_left_right.padTop(ex01Types.VIRTUAL_SCREEN_WIDTH / 10.3f * 310f / 128f / 2f);
        stack_left_right = new Stack();
        stack_left_right.setFillParent(true);
        table_left_right.center();
        help_done = new ImageButton(skin, "done_button");
        help_done.pad(0f);   
        help_table_done = new Table();
        help_table_done.add(help_done)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.1f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 3.1f * 106f / 256f)
				.padRight(ex01Types.VIRTUAL_SCREEN_WIDTH / 256f)
				.padBottom(-ex01Types.VIRTUAL_SCREEN_WIDTH / 35.75f);
        help_table_done.bottom().padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 14.175f);
        
        help_left.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (base_menu.settings.settings.sounds_on)
					base_menu.level_left_right_button.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
				help_left.addAction(sequence(
						moveBy(-12f, 0f, 0.1f),
						moveBy(12f, 0f, 0.2f)));
				switch(selected_image){
					case 1:{
						image1_helprules.addAction(Actions.fadeOut(2f));
						image3_helpcontrols.addAction(Actions.fadeIn(2f));
						selected_image = 3;
					}break;
					case 2:{
						image2_help.addAction(Actions.fadeOut(2f));
						image1_helprules.addAction(Actions.fadeIn(2f));
						selected_image = 1;
					}break;
					case 3:{
						image3_helpcontrols.addAction(Actions.fadeOut(2f));
						image2_help.addAction(Actions.fadeIn(2f));
						selected_image = 2;
					}break;
				}
			}
		});	   
        
        help_right.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (base_menu.settings.settings.sounds_on)
					base_menu.level_left_right_button.play(SND_VOL.SOUND_LEFT_RIGHT_VOLUME);
				help_right.addAction(sequence(
						moveBy(12f, 0f, 0.1f),
						moveBy(-12f, 0f, 0.2f)));
				switch(selected_image){
					case 1:{
						image1_helprules.addAction(Actions.fadeOut(2f));
						image2_help.addAction(Actions.fadeIn(2f));
						selected_image = 2;
					}break;
					case 2:{
						image2_help.addAction(Actions.fadeOut(2f));
						image3_helpcontrols.addAction(Actions.fadeIn(2f));
						selected_image = 3;
					}break;
					case 3:{
						image3_helpcontrols.addAction(Actions.fadeOut(2f));
						image1_helprules.addAction(Actions.fadeIn(2f));
						selected_image = 1;
					}break;
				}
			}
		});	 
        
        help_done.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!still_working_on_done_press) {
					still_working_on_done_press = true;
					base_menu.menus.FadeIn_ExtraButtons();
					if (base_menu.settings.settings.sounds_on)
						base_menu.done_button.play(SND_VOL.DONE_BUTTON_VOLUME);
					base_menu.menus.help_text.addAction(parallel(
							Actions.alpha(0.7f, 3f),
							scaleTo(1.0f, 1.0f, 0.8f),
							sequence(moveBy(0f, 15f, 0.15f),
									moveBy(0f, -15f, 1f, swingOut))));
					help_done.addAction(parallel(sequence(
									moveBy(0f, 12f, 0.1f),
									moveBy(0f, -12f, 0.2f)),
							fadeOut(0.55f)));
					image1.addAction(parallel(
							fadeOut(1.25f),
							moveBy(0f, -50f, 1.25f, swingIn)));
					image2.addAction(parallel(
							fadeOut(1.25f),
							moveBy(0f, -50f, 1.25f, swingIn)));
					image3.addAction(parallel(
							fadeOut(1.25f),
							moveBy(0f, -50f, 1.25f, swingIn)));
					table_left_right.addAction(sequence(parallel(
									fadeOut(1.25f),
									moveBy(0f, -50f, 1.25f, swingIn)),
							run(new Runnable() {
								public void run() {
									base_menu.menus.FadeInMenuButtons(1.25f);
									base_menu.grayscale_shader_active_help = false;
									base_menu.menus.stage.getBatch().setShader(null);
									Gdx.input.setInputProcessor(base_menu.menus.stage);
									still_working_on_done_press = false;
								}
							})));
				}
			}
		});	        
        
        table_left_right.addAction(Actions.moveBy(0f, -50f, 0f));

		//add images to stage
		image1_helprules = new Image(help_images_atlas.findRegion("helprules"));
		image2_help = new Image(help_images_atlas.findRegion("help"));
		image3_helpcontrols = new Image(help_images_atlas.findRegion("helpcontrol"));
		image1_helprules.addAction(Actions.alpha(1f));
		image2_help.addAction(Actions.alpha(0f));
		image3_helpcontrols.addAction(Actions.alpha(0f));
		image1 = new Table();
		image2 = new Table();
		image3 = new Table();
		image1.setFillParent(true);
		image2.setFillParent(true);
		image3.setFillParent(true);
		image1.add(image1_helprules)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.3f*ex01Types.RESIZER)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.3f * 900f / 500f)
				.padRight(ex01Types.VIRTUAL_SCREEN_WIDTH/400f);
		image2.add(image2_help)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.3f * ex01Types.RESIZER)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.3f * 900f / 500f)
				.padRight(ex01Types.VIRTUAL_SCREEN_WIDTH / 400f);
		image3.add(image3_helpcontrols)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.3f * ex01Types.RESIZER)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.3f * 900f / 500f)
				.padRight(ex01Types.VIRTUAL_SCREEN_WIDTH / 400f);
		stack_left_right.add(image1);
		stack_left_right.add(image2);
		stack_left_right.add(image3);
		stack_left_right.add(help_table_done);
		stack_left_right.add(table_left_right);

		//add to stage
        stage_help.addActor(stack_left_right);
	}
	
    public void Render(float delta){
		stage_help.act(delta*SPEED_HELP);
    	stage_help.draw();
    }

	public void Dispose(){
		if(help_left!=null)
			help_left.clear();
		if(help_right!=null)
			help_right.clear();
		if(help_done!=null)
			help_done.clear();
		if(help_table_done!=null)
			help_table_done.clear();
		if(table_left_right!=null)
			table_left_right.clear();
		if(stack_left_right!=null)
			stack_left_right.clear();

		help_left = null;;
		help_right = null;;
		help_done = null;;
		help_table_done = null;;
		table_left_right = null;;
		stack_left_right = null;;

		if(stage_help!=null) {
			stage_help.dispose();
			stage_help = null;
		}
		if(help_images!=null){
			for(int i=0; i<help_images.length; i++){
				help_images[i] = null;
			}
		}
		base_menu = null;
		if(help_images_atlas!=null) {
			help_images_atlas.dispose();
			help_images_atlas = null;
		}

		if(image1_helprules!=null){
			image1_helprules.clear();
			image1_helprules = null;
		}
		if(image2_help!=null){
			image2_help.clear();
			image2_help = null;
		}
		if(image3_helpcontrols!=null){
			image3_helpcontrols.clear();
			image3_helpcontrols = null;
		}
		if(image1!=null){
			image1.clear();
			image1 = null;
		}
		if(image2!=null){
			image2.clear();
			image2 = null;
		}
		if(image3!=null){
			image3.clear();
			image3 = null;
		}
	}
}
