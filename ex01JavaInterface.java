//WMS3 2015

package com.cryotrax.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.math.Interpolation.swingIn;
import static com.badlogic.gdx.math.Interpolation.swingOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class ex01JavaInterface {
    public static final float SPEED_VALUE = 3f;
    public static final float width = ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f;
    public static final float height = ex01Types.VIRTUAL_SCREEN_WIDTH/1.125f * 278f/430f;
    public Image exit_image_back;
    public Image exit_image_text;
    public ImageButton exit_text;
    public ImageButton exit_ok;
    public ImageButton exit_cancel;
    public Table exit_dialog;
    public Table exit_table_ok_cancel;
    public Sound done_button;
    public Stack exit_dialog_stack;
    public Stage stage_exit_dialog;
    public ex01MenuScreen base_menu;
    public CryotraxGame cryo_game;
    public boolean still_working_on_done_press = false;
    public boolean has_just_played_ye = false;
    public long how_much_time_passed;
    public Table exit_image_back_t;

    //exit dialog - pretty cool looking
    public ex01JavaInterface(CryotraxGame game,
                             Skin skin,
                             ex01MenuScreen bmenu,
                             Viewport viewport) {
        this.cryo_game = game;
        stage_exit_dialog = new Stage(viewport);
        base_menu = bmenu;
        exit_dialog_stack = new Stack();
        exit_dialog = new Table();
        exit_dialog.setTransform(true);
        exit_dialog.addAction(Actions.fadeOut(0f));
        exit_dialog.add(exit_dialog_stack);
        exit_image_back = new Image(skin.getDrawable(ex01Types.VALUE_EXIT_DIALOG_BACK));
        exit_image_back_t = new Table();
        exit_image_back_t
                .add(exit_image_back)
                .width(ex01Types.VIRTUAL_SCREEN_WIDTH/1.13f)
                .height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.13f * 303f / 512f);
        exit_image_text = new Image(skin.getDrawable(ex01Types.VALUE_TEXT));
        exit_image_text.addAction(Actions.alpha(0.975f));
        exit_dialog_stack.add(exit_image_back_t);
        exit_ok = new ImageButton(skin, ex01Types.VALUE_OK);
        exit_ok.pad(0f);
        exit_cancel = new ImageButton(skin, ex01Types.VALUE_NO);
        exit_cancel.pad(0f);
        exit_table_ok_cancel = new Table();
        exit_table_ok_cancel
                .add(exit_image_text)
                .colspan(2)
                .width(ex01Types.VIRTUAL_SCREEN_WIDTH/2.05f)
                .height(ex01Types.VIRTUAL_SCREEN_WIDTH/2.05f * 84f/256f)
                .padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 8.725f);
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
                if(!still_working_on_done_press){
                    still_working_on_done_press = true;
                    if(base_menu.settings.settings.sounds_on)
                        base_menu.done_button.play(SND_VOL.DONE_BUTTON_VOLUME);
                    exit_ok.addAction(sequence(
                            Actions.moveBy(0f, 12f, 0.1f),
                            Actions.moveBy(0f, -12f, 0.2f),
                            Actions.run(new Runnable() {
                                public void run(){
                                    if(WiFiCon()){
                                        base_menu.cryo_game.exit_error
                                                .LoadInMidGameVALUE();
                                        Wait(base_menu.cryo_game.exit_error
                                                .IsLoadedVALUEMainGame());

                                        if(LoadInterIs()) {
                                            base_menu.cryo_game.exit_error
                                                .showVALUEye(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ProcessResetStuff(ex01Types.VIEWED_AD_YES);
                                                    }
                                                });
                                        } else {
                                            ProcessResetStuff(ex01Types.VIEWED_AD_NO);
                                        }
                                    } else {
                                        ProcessResetStuff(ex01Types.VIEWED_AD_NO);
                                    }
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
                    base_menu.menus.new_game_text.addAction(parallel(
                            Actions.alpha(0.7f,3f),
                            Actions.scaleTo(1.0f, 1.0f, 0.8f),
                            Actions.sequence(moveBy(0f, 15f, 0.15f),
                                             moveBy(0f, -15f, 1f, swingOut))));
                    exit_cancel.addAction(sequence(
                            moveBy(0f, 12f, 0.1f),
                            moveBy(0f, -12f, 0.2f)));
                    exit_dialog.addAction(sequence(
                            Actions.parallel(Actions.fadeOut(1.25f),
                                    Actions.moveBy(0f,-50f,1.25f,swingIn)),
                            Actions.run(new Runnable(){
                                public void run(){
                                    if(base_menu.menus.add_free_version!=null){
                                        base_menu.menus.add_free_version
                                                .addAction(Actions.fadeIn(2f));
                                    }
                                    still_working_on_done_press = false;
                                    cryo_game.menu_screen.menus.bAlreadyPressedNewGame = false;
                                    exit_dialog.addAction(Actions.fadeOut(0f));
                                    base_menu.menus.FadeInMenuButtons(1.5f);
                                    base_menu.menus.stage.getBatch().setShader(null);
                                    base_menu.grayscale_shader_active_VALUE = false;
                                    //now enter the level screen
                                    base_menu.menus.StartNewGameInVALUE();
                                }
                    })));
                }
            }
        });
    }

    public void ProcessResetStuff(boolean bPlayedye){
        has_just_played_ye = bPlayedye;
        ProcessResetStuffAfterUserViewsye(has_just_played_ye);
        still_working_on_done_press = false;
    }

    public boolean WiFiCon() {
        return base_menu.cryo_game.exit_error.isWiFiConnected();
    }

    public boolean LoadInterIs() {
        return base_menu.cryo_game .exit_error.IsLoadedVALUEMainGame();
    }

    public void Wait(boolean while_wait){
        how_much_time_passed = TimeUtils.millis();
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (TimeUtils.millis() - how_much_time_passed >
                    ex01Types.AD_LOAD_DELAY_PERMITED)
                break;
        }
        while (!while_wait);
    }

    public void ShowShortVALUEMenu(){
        if(base_menu.cryo_game.exit_error.isWiFiConnected()) {
            base_menu.cryo_game.exit_error.LoadInMidGameVALUEShort();
            Wait(base_menu.cryo_game.exit_error.IsLoadedVALUEMainGameShort());
            if (base_menu.cryo_game.exit_error.IsLoadedVALUEMainGameShort()) {
                base_menu.cryo_game.exit_error.showVALUEyeShort(new Runnable() {
                    @Override
                    public void run() {
                        ShowShortMenu();
                    }
                });
            } else {
                ShowShortMenu();
            }
        } else {
            ShowShortMenu();
        }
    }

    public void ShowShortMenu(){
        base_menu.menus.StartNewGameInVALUE();
        still_working_on_done_press = false;
    }

    public void ShowShortVALUEHUD(){
        if(base_menu.cryo_game.exit_error.isWiFiConnected()) {
            base_menu.cryo_game.exit_error.LoadInMidGameVALUEShort();
            Wait(base_menu.cryo_game.exit_error.IsLoadedVALUEMainGameShort());
            if (base_menu.cryo_game.exit_error.IsLoadedVALUEMainGameShort()) {
                base_menu.cryo_game.exit_error.showVALUEyeShort(new Runnable() {
                    @Override
                    public void run() {
                        ShowShortHUD();
                    }
                });
            } else {
                ShowShortHUD();
            }
        } else {
            ShowShortHUD();
        }
    }

    public void ShowShortVALUEHUD_MainMenu(){
        if(base_menu.cryo_game.exit_error.isWiFiConnected()) {
            base_menu.cryo_game.exit_error.LoadInMidGameVALUEShort();
            Wait(base_menu.cryo_game.exit_error.IsLoadedVALUEMainGameShort());
            if (base_menu.cryo_game.exit_error.IsLoadedVALUEMainGameShort()) {
                base_menu.cryo_game.exit_error.showVALUEyeShort(new Runnable() {
                    @Override
                    public void run() {
                        ShowShortHUD_MainMenu();
                    }
                });
            } else {
                ShowShortHUD_MainMenu();
            }
        } else {
            ShowShortHUD_MainMenu();
        }
    }

    public void ShowShortHUD(){
        base_menu.cryo_game.game_screen.hud_display.ProcessRestartNoNeedVALUE();
        still_working_on_done_press = false;
    }

    public void ShowShortHUD_MainMenu(){
        base_menu.cryo_game.game_screen.hud_display.MainMenuProcedureClickAfter();
        still_working_on_done_press = false;
    }

    public ex01JavaInterface(CryotraxGame game, Skin skin, Viewport viewport) {
        this.cryo_game = game;
        stage_exit_dialog = new Stage(viewport);
        exit_dialog_stack = new Stack();
        exit_dialog = new Table();
        exit_dialog.setTransform(true);
        exit_dialog.addAction(Actions.fadeOut(0f));
        exit_dialog.add(exit_dialog_stack);
        exit_image_back = new Image(skin.getDrawable(ex01Types.VALUE_EXIT_DIALOG_BACK));
        exit_image_text = new Image(skin.getDrawable(ex01Types.VALUE_TEXT));
        exit_image_text.addAction(Actions.alpha(0.975f));
        exit_dialog_stack.add(exit_image_back);
        exit_ok = new ImageButton(skin, ex01Types.VALUE_OK);
        exit_ok.pad(0f);
        exit_cancel = new ImageButton(skin, ex01Types.VALUE_NO);
        exit_cancel.pad(0f); 
        exit_table_ok_cancel = new Table();
        exit_table_ok_cancel.add(exit_image_text).colspan(2)
                .width(ex01Types.VIRTUAL_SCREEN_WIDTH/1.950f)
                .height(ex01Types.VIRTUAL_SCREEN_WIDTH / 1.950f * 105f / 320f)
                .padBottom(ex01Types.VIRTUAL_SCREEN_WIDTH / 7.45f);
        exit_table_ok_cancel.row();
        exit_table_ok_cancel.add(exit_ok).width(ex01Types.VIRTUAL_SCREEN_WIDTH/4.4f).
                height(ex01Types.VIRTUAL_SCREEN_WIDTH / 4.4f * 106f / 256f).
                padRight(ex01Types.VIRTUAL_SCREEN_WIDTH / 100f);
        exit_table_ok_cancel.add(exit_cancel).width(ex01Types.VIRTUAL_SCREEN_WIDTH/4.4f).
                height(ex01Types.VIRTUAL_SCREEN_WIDTH / 4.4f * 106f / 256f).
                padLeft(ex01Types.VIRTUAL_SCREEN_WIDTH / 100f);
        exit_table_ok_cancel.padBottom(-ex01Types.VIRTUAL_SCREEN_WIDTH/35.75f);
        exit_dialog_stack.add(exit_table_ok_cancel);
        exit_table_ok_cancel.bottom();
        exit_dialog.addAction(Actions.moveBy(0f,-50f,0f));
        exit_dialog.setTransform(true);
        exit_dialog.addAction(Actions.sizeTo(width, height));
        exit_dialog.setPosition(ex01Types.VIRTUAL_SCREEN_WIDTH * 0.5f - width / 2,
                ex01Types.VIRTUAL_SCREEN_HEIGHT * 0.5f - height/2);
        stage_exit_dialog.addActor(exit_dialog);

        exit_ok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!still_working_on_done_press) {
                    still_working_on_done_press = true;
                    if (base_menu.settings.settings.sounds_on)
                        done_button.play(SND_VOL.DONE_BUTTON_VOLUME);
                    exit_ok.addAction(Actions.sequence(
                            moveBy(0f, 12f, 0.1f),
                            moveBy(0f, -12f, 0.2f),
                            Actions.run(new Runnable() {
                                public void run() {
                                    if(WiFiCon()) {
                                        base_menu.cryo_game.exit_error
                                                .LoadInMidGameVALUE();
                                        Wait(base_menu.cryo_game.exit_error
                                                .IsLoadedVALUEMainGame());

                                        if (LoadInterIs()) {
                                            base_menu.cryo_game.exit_error
                                                .showVALUEye(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ProcessResetStuffHUD(ex01Types.VIEWED_AD_YES);
                                                    }
                                                });
                                        } else {
                                            ProcessResetStuffHUD(ex01Types.VIEWED_AD_NO);
                                        }
                                    } else {
                                        ProcessResetStuffHUD(ex01Types.VIEWED_AD_NO);
                                    }
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
                        done_button.play(SND_VOL.DONE_BUTTON_VOLUME);
                    base_menu.menus.new_game_text.addAction(parallel(
                            Actions.alpha(0.7f,3f),
                            Actions.scaleTo(1.0f, 1.0f, 0.8f),
                            Actions.sequence(moveBy(0f, 15f, 0.15f),
                                    moveBy(0f, -15f, 1f, swingOut))));
                    exit_cancel.addAction(sequence(
                            moveBy(0f, 12f, 0.1f),
                            moveBy(0f, -12f, 0.2f)));
                    exit_dialog.addAction(sequence(
                            Actions.parallel(
                                    Actions.fadeOut(1.25f),
                                    Actions.moveBy(0f, -50f, 1.25f, swingIn)),
                            Actions.run(new Runnable() {
                                public void run() {
                                    exit_dialog.addAction(Actions.fadeOut(0f));
                                    still_working_on_done_press = false;
                                    cryo_game.game_screen.hud_display
                                            .ProcessRestartNoNeedVALUE();
                                    cryo_game.game_screen.hud_display
                                            .stage.getBatch().setShader(null);
                                    cryo_game.game_screen.inputMultiplexer
                                            .removeProcessor(stage_exit_dialog);
                                }
                    })));
                }
            }
        });
    }

    public void ProcessResetStuffHUD(boolean bPlayedye){
        has_just_played_ye = bPlayedye;
        ProcessResetStuffAfterUserViewsyeHUD(has_just_played_ye);
        still_working_on_done_press = false;
    }

    public void ProcessResetStuffAfterUserViewsyeHUD(boolean yes_no){
        // VALUE ye not yet loaded
        ProcessResetStuffAfterUserViewsyeInGame(yes_no);
        if(!yes_no) {
            cryo_game.game_screen.hud_display.ProcessRestartNoNeedVALUE();
        } else {
            cryo_game.game_screen.hud_display.EnterWonCoinsPhase();
        }
        cryo_game.game_screen.hud_display.stage.getBatch().setShader(null);
        cryo_game.game_screen.inputMultiplexer.removeProcessor(cryo_game
                .game_screen.hud_display.VALUE_screen.stage_exit_dialog);
        still_working_on_done_press = false;
    }
    
    public void ProcessResetStuffAfterUserViewsye(boolean yes_no){
        if(base_menu.settings.settings.sounds_on) {
            base_menu.done_button.play(SND_VOL.DONE_BUTTON_VOLUME);
        }
        base_menu.menus.new_game_text.addAction(parallel(
                Actions.alpha(0.7f,3f),
                Actions.scaleTo(1.0f, 1.0f, 0.8f),
                Actions.sequence(Actions.moveBy(0f, 15f, 0.15f),
                        Actions.moveBy(0f, -15f, 1f, swingOut))));
        if(yes_no) {
            exit_ok.addAction(sequence(
                    moveBy(0f, 12f, 0.1f),
                    moveBy(0f, -12f, 0.2f)));
        } else {
            exit_cancel.addAction(sequence(
                    moveBy(0f, 12f, 0.1f),
                    moveBy(0f, -12f, 0.2f)));
        }
        exit_dialog.addAction(sequence(
                Actions.parallel(
                        Actions.fadeOut(1.25f),
                        Actions.moveBy(0f, -50f, 1.25f, swingIn)),
                Actions.run(new Runnable() {
                       public void run() {
                           exit_dialog.addAction(Actions.fadeOut(0f));
                           base_menu.grayscale_shader_active_VALUE = false;
                           base_menu.menus.add_free_version.addAction(Actions.fadeIn(2f));
                           base_menu.menus.FadeInMenuButtons(1.5f);
                           base_menu.menus.StartNewGameInVALUE();
                           base_menu.menus.stage.getBatch().setShader(null);
                           Gdx.input.setInputProcessor(base_menu.menus.multiplexer);
                           still_working_on_done_press = false;
                           //now enter the level screen
                       }
                   })));
    }
    
    public void ProcessResetStuffAfterUserViewsyeInGame(boolean yes_no){
        if(base_menu.settings.settings.sounds_on)
            base_menu.done_button.play(SND_VOL.DONE_BUTTON_VOLUME);
        if(yes_no) {
            exit_ok.addAction(sequence(
                    moveBy(0f, 12f, 0.1f),
                    moveBy(0f, -12f, 0.2f)));
        } else {
            exit_cancel.addAction(sequence(
                    moveBy(0f, 12f, 0.1f),
                    moveBy(0f, -12f, 0.2f)));
        }
        exit_dialog.addAction(sequence(
                Actions.parallel(
                        Actions.fadeOut(1.25f),
                        Actions.moveBy(0f, -50f, 1.25f, swingIn)),
                Actions.run(new Runnable() {
                       public void run() {
                           exit_dialog.addAction(Actions.fadeOut(0f));
                       }
                   })));
    }
    
    public void Render(float delta){
        stage_exit_dialog.act(delta*SPEED_VALUE);
        stage_exit_dialog.draw();
    }

    public void Dispose(){
        if(exit_image_back!=null)
            exit_image_back.clear();
        if(exit_image_text!=null)
            exit_image_text.clear();
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
        if(exit_dialog_stack!=null)
            exit_dialog_stack.clear();

        exit_image_back = null;
        exit_image_text = null;
        exit_dialog = null;
        exit_table_ok_cancel = null;
        exit_text = null;
        exit_ok = null;
        exit_cancel = null;
        exit_dialog_stack = null;

        if(stage_exit_dialog!=null){
            stage_exit_dialog.clear();
            stage_exit_dialog.dispose();
            stage_exit_dialog = null;
        }

        base_menu = null;
        cryo_game = null;

        if(done_button!=null){
            done_button.stop();
            done_button.dispose();
            done_button = null;
        }

        if(exit_image_back_t!=null){
            exit_image_back_t.clear();
            exit_image_back_t = null;
        }
    }
}
