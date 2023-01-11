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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ex01MenuScreenExitDialog {
	public static final float SPEED_EXIT = 3f;
    public static final float width = ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f;
    public static final float height = ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f * 278f/430f;
    public Image exit_image_back;
    public Image exit_image_text;   
	public Table exit_dialog;
    public Table exit_table_ok_cancel;
	public ImageButton exit_text;
	public ImageButton exit_ok;
    public ImageButton exit_cancel;
    public Stack exit_dialog_stack;      
	public Stage stage_exit_dialog;    
	public ex01MenuScreen base_menu;
    public boolean still_working_on_done_press = false;
	public Table exit_image_back_t;

    //exit dialog - pretty cool looking
    public ex01MenuScreenExitDialog(Skin skin, ex01MenuScreen bmenu, Viewport viewport) {
		stage_exit_dialog = new Stage(viewport);
    	base_menu = bmenu;
        exit_dialog_stack = new Stack();
        exit_dialog = new Table();
		exit_dialog.setTransform(true);
		exit_dialog.addAction(Actions.fadeOut(0f));
		exit_dialog.add(exit_dialog_stack);
		exit_image_back_t = new Table();
        exit_image_back = new Image(skin.getDrawable("hud-level55"));
		exit_image_back_t
				.add(exit_image_back)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/1.13f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.13f * 303f / 512f);
        exit_image_text = new Image(skin.getDrawable("exit"));
        exit_image_text.addAction(Actions.alpha(0.825f));
        exit_dialog_stack.add(exit_image_back_t);
        exit_ok = new ImageButton(skin, "ok_button");
        exit_ok.pad(0f);
        exit_cancel = new ImageButton(skin, "cancel_button");
        exit_cancel.pad(0f); 
        exit_table_ok_cancel = new Table();
        exit_table_ok_cancel
				.add(exit_image_text)
				.colspan(2)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/3.3f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH/3.3f * 105f/160f)
				.padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH/10.325f);
        exit_table_ok_cancel.row();
        exit_table_ok_cancel
				.add(exit_ok)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/4.425f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH/4.425f * 106f/256f)
				.padRight(ex01Types.VIRTUAL_SCREEN_WIDTH/100f);
        exit_table_ok_cancel
				.add(exit_cancel)
				.width(ex01Types.VIRTUAL_SCREEN_WIDTH/4.425f)
				.height(ex01Types.VIRTUAL_SCREEN_WIDTH/4.425f * 106f/256f)
				.padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH/100f);
        exit_table_ok_cancel.padBottom(-ex01Types.VIRTUAL_SCREEN_WIDTH/35.75f);
		exit_dialog_stack.add(exit_table_ok_cancel);        
		exit_table_ok_cancel.bottom();
        exit_dialog.addAction(Actions.moveBy(0f,-50f,0f));
        exit_dialog.setTransform(true);
        exit_dialog.addAction(Actions.sizeTo(width, height));
        exit_dialog.setPosition(ex01Types.VIRTUAL_SCREEN_WIDTH * 0.5f - width/2,
				ex01Types.VIRTUAL_SCREEN_HEIGHT * 0.5f - height/2);
        stage_exit_dialog.addActor(exit_dialog);
        
        exit_ok.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!still_working_on_done_press && base_menu.can_exit_game_or_do_other_write){
					still_working_on_done_press = true;
					if(base_menu.settings.settings.sounds_on)
						base_menu.done_button.play(SND_VOL.DONE_BUTTON_VOLUME);
					exit_ok.addAction(sequence(moveBy(0f, 12f, 0.1f),
							moveBy(0f, -12f, 0.2f),
							run(new Runnable(){
								public void run(){
									Gdx.app.exit();
								}
					})));					
				} else {
					still_working_on_done_press = true;
					if(base_menu.settings.settings.sounds_on)
						base_menu.done_button.play(SND_VOL.DONE_BUTTON_VOLUME);
					exit_ok.addAction(sequence(moveBy(0f, 12f, 0.1f),
							moveBy(0f, -12f, 0.2f),
							run(new Runnable(){
								public void run(){
									still_working_on_done_press = false;
								}
							})));
				}
			}
		});        
   
        exit_cancel.addListener( new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!still_working_on_done_press){
					still_working_on_done_press = true;
					if(base_menu.settings.settings.sounds_on)
						base_menu.done_button.play(SND_VOL.DONE_BUTTON_VOLUME);
					exit_text.addAction(parallel(Actions.alpha(0.7f,3f),
							scaleTo(1.0f, 1.0f, 0.8f),
							sequence(moveBy(0f, 15f, 0.15f),
									 moveBy(0f, -15f, 1f, swingOut))));
					exit_cancel.addAction(sequence(moveBy(0f, 12f, 0.1f),
							moveBy(0f, -12f, 0.2f)));
					exit_dialog.addAction(sequence(parallel(fadeOut(1.25f),
							moveBy(0f,-50f,1.25f,swingIn)),
							run(new Runnable(){
								public void run(){
									base_menu.menus.FadeIn_ExtraButtons();
									base_menu.menus.FadeInMenuButtons(1.5f);
									exit_dialog.addAction(Actions.fadeOut(0f));
									base_menu.grayscale_shader_active_exit = false;
									base_menu.menus.stage.getBatch().setShader(null);
									Gdx.input.setInputProcessor(base_menu.menus.stage);
									still_working_on_done_press = false;
								}
					})));					
				}
			}
		});			   
    }	
    
    public void Render(float delta){
		stage_exit_dialog.act(delta*SPEED_EXIT);
		stage_exit_dialog.draw();        
    }

	public void Dispose(){
		if(exit_image_back!=null)
			exit_image_back .clear();
		if(exit_image_text!=null)
			exit_image_text .clear();
		if(exit_dialog!=null)
			exit_dialog.clear();
		if(exit_table_ok_cancel!=null)
			exit_table_ok_cancel.clear();
		if(exit_text!=null)
			exit_text.clear();
		if(exit_ok!=null)
			exit_ok.clear();
		if(exit_cancel!=null)
			exit_cancel.clear();

		exit_image_back = null;
		exit_image_text = null;
		exit_dialog = null;
		exit_table_ok_cancel = null;
		exit_text = null;
		exit_ok = null;
		exit_cancel = null;
		
		if(exit_dialog_stack!=null)
			exit_dialog_stack.clear();
		exit_dialog_stack = null;
		if(stage_exit_dialog!=null){
			stage_exit_dialog.clear();
			stage_exit_dialog = null;
		}
		base_menu = null;

		if(exit_image_back_t!=null){
			exit_image_back_t.clear();
			exit_image_back_t = null;
		}
	}
}
