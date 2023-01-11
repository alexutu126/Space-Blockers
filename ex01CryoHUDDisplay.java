//WMS3 2015

package com.cryotrax.game;

import static com.badlogic.gdx.math.Interpolation.swing;
import static com.badlogic.gdx.math.Interpolation.swingOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import java.util.ArrayList;
import java.text.DecimalFormat;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation.Swing;
import com.cryotrax.game.ex01JSONSettingsLoader.json_settings;

//----------------------------------------------------------------

public class ex01CryoHUDDisplay {
	public static final long DELTA_INTERSTITIAL = ex01Types.DELTA_INTERSTITIAL;
    public static final int VIRTUAL_SCREEN_WIDTH = 480;
    public static final int VIRTUAL_SCREEN_HEIGHT = 800;
	public static final int screen_sizew  =
			Gdx.graphics.getWidth();
	public static final int screen_sizeh  =
			Gdx.graphics.getHeight();
	public static final float SPEED_HUD = 3f;
	public static final float SPEED_HUD_REPLAY = 3f;
	public static final float SPEED_HUD_ANGLES = 3f;
	public float FULL_SCREEN_LIST_DESTROYER = 0.65f;
	public final CryotraxGame cryo_game;
	public _ex01CryotraxGame the_game_screen;
	public _ex01CryotraxGame game_screen_base;
	public ex01CryoHUDDisplay hud_display;
	public ex01JSONSettingsLoader setts_for_game;
	public TextureAtlas textureAtlasReplay;
	public Skin skin_ad;
	public TimeUtils timer_ad = new TimeUtils();
	public long ad_passed;


	public void ex01CryoHUDDisplayReload(int electrontrap_number,
										 ex01JSONSettingsLoader settings_for_game,
										 TextureAtlas atlas,
										 _ex01CryotraxGame game_screen,
										 Skin skin, Viewport viewport,
										 int ang1,
										 int ang2,
										 int ang3){

		LoadAmmoLifeFromSettings(game_screen, settings_for_game, electrontrap_number);
		// startup circle , 3 2 1 GO                     - DONE
		Hud_Fonts_Display_Reload(skin);
		// the base for the health/bullets sprites	     - DONE
		Hud_Up_Reload(atlas);
		// health/bullets sprites at top that rotate     - DONE
		Hud_Health_Bullets_Icons_Reload(atlas);
		// bullets/health green counters at top          - DONE
		Hud_Counter_Reload(atlas);
		// this is upper life and ammo counters          - DONE
		Hud_StageLifeBulletsReload(skin, viewport);
		// this is upper life and ammo counters          - DONE
		Hud_FPSLeftReload(skin);
		// this is lower counter and max deg			 - DONE
		Hud_StageAngleCounterReload(skin);
		// replay menu									 - DONE
		Hud_Table_Replay_InitReload();
		// no more bullets                    			 - DONE
		Hud_NoMoreBulletsReload();
		// we ran out of bullets dialog       			 - DONE
		InitNoMoreBulletsTableReload();
		// this is angles while shooting      			 - DONE
		Hud_Angles_DisplayReload();
		// this is powerup while shooting     			 - DONE
		Hud_Powerup_DisplayReload(ang1, ang2, ang3);
		hud_display = this;
    	hud_display.stage.addAction(Actions.fadeOut(0f));
    	InitDisplayReload();
	}	
	
	public void ex01CryoHUDDisplayReloadData(int electrontrap_number,
											 ex01JSONSettingsLoader settings_for_game,
											 _ex01CryotraxGame game_screen,
											 Skin skin){

		LoadAmmoLifeFromSettings(game_screen, settings_for_game, electrontrap_number);
		// this is lower counter and max deg			 - DONE
		Hud_StageAngleCounterReload(skin);
		ResetAmmoLifeFromSettings(game_screen, settings_for_game);
	}
	
	public ex01CryoHUDDisplay(int electrontrap_number,
							  ex01JSONSettingsLoader settings_for_game,
							  TextureAtlas atlas,
							  _ex01CryotraxGame game_screen,
							  final CryotraxGame game,
							  Skin skin,
							  Viewport viewport,
							  OrthographicCamera cam,
							  int ang1,
							  int ang2,
							  int ang3){

		setts_for_game = settings_for_game;
		cryo_game = game;
		if(cryo_game.screen_type==1){
			textureAtlasReplay = new
					TextureAtlas(Gdx.files.internal(ex01Types.EXPLOSION_HUD_REPLAY01big));
		} else if(cryo_game.screen_type==2){
			textureAtlasReplay = new
					TextureAtlas(Gdx.files.internal(ex01Types.EXPLOSION_HUD_REPLAY02medium));
		} else {
			textureAtlasReplay = new
					TextureAtlas(Gdx.files.internal(ex01Types.EXPLOSION_HUD_REPLAY03small));
		}
		main_game_camera = cam;
		game_screen_base = game_screen;
		LoadAmmoLifeFromSettings(game_screen, settings_for_game, electrontrap_number);
		// replay menu									 - DONE xx
		Hud_Table_Replay_Init(game_screen);
		// startup circle , 3 2 1 GO                     - DONE
		Hud_Fonts_Display(skin, viewport);
		// the base for the health/bullets SPRITES	     - DONE
		Hud_Up_Init(atlas);
		// health/bullets SPRITES at top that rotate     - DONE
		Hud_Health_Bullets_Icons_Init(atlas);
		// bullets/health green counters at top          - DONE
		Hud_Counter_Init(atlas);
		// this is upper life and ammo counters          - DONE
		Hud_StageLifeBullets(skin, viewport);
		// this is upper life and ammo counters          - DONE
		Hud_FPSLeft(skin, viewport);
		// this is lower counter and max deg			 - DONE
		Hud_StageAngleCounter(skin);
		// coins won at finish in replay menu			 - DONE
		InitCoinsWon(atlas);
		// no more bullets                    			 - DONE
		Hud_NoMoreBullets();
		// we ran out of bullets dialog       			 - DONE
		InitNoMoreBulletsTable(viewport);
		// this is angles while shooting      			 - DONE
		Hud_Angles_Display(viewport);
		// this is power-up while shooting     			 - DONE xx
		Hud_Powerup_Display(ang1, ang2, ang3);
		// this is the angle below in replay/resume dia. - DONE
		// this describes min. angle - > buy life-slots - > buy ammo-slots
		InitSpaceshipError(textureAtlasReplay);
		hud_display = this;
    	r_write_json = new Runnable(){
    		public void run(){
    			setts_for_game.WriteJson();
				cryo_game.SAVE_GAME_TO_CLOUD();
    	}};
    	t_write_json = new Thread(r_write_json);		
    	hud_display.stage.addAction(Actions.fadeOut(0f));
    	AddClickRestartListenerBegin();
    	AddClickResumeListenerBegin();
		if(cryo_game.screen_type==1){
			skin_ad = cryo_game.assets.get(ex01Types.DATA_GRAPHICS_SPRITE_MENU_JSON01big);
		} else if(cryo_game.screen_type==2){
			skin_ad = cryo_game.assets.get(ex01Types.DATA_GRAPHICS_SPRITE_MENU_JSON02medium);
		} else {
			skin_ad = cryo_game.assets.get(ex01Types.DATA_GRAPHICS_SPRITE_MENU_JSON03small);
		}

    	VALUE_screen = new ex01JavaInterface(cryo_game, skin_ad, viewport);
		VALUE_screen.done_button = cryo_game.assets.get(ex01Types.SOUND_MN_DONE_B01big);
		VALUE_screen.base_menu = cryo_game.menu_screen;
    	InitCoinsBought(cryo_game.atlas_menu, skin_ad);
		ad_passed = timer_ad.millis();
	}

	public boolean CanWorkOnAdNow(){
		if((timer_ad.millis() - ad_passed)>DELTA_INTERSTITIAL){
			ad_passed = timer_ad.millis();
			return true;
		}
		return false;
	}

	public void DisposeHUDDisplay_Main(){
		the_game_screen = null;
		game_screen_base = null;
		hud_display = null;
		setts_for_game = null;
		textureAtlasReplay = null;
		skin_ad = null;
	}

	public void DisposeHUDDisplay_All(){
		DisposeHUDDisplay_AnglesScene();
		DisposeHUDDisplay_RunOBullets();
		DisposeHUDDisplay_HudMainScreen();
		DisposeHUDDisplay_ResumeReplay();
		DisposeHUDDisplay_Ads();
		DisposeHUDDisplay_Main();
		DisposeBuyPremium();
	}

	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	// POWERUPS/ANGLES DISPLAY HUD TEXT rolling up and down

	public static final float RAPORT_RELATIV_ECRANE =
			(float)Gdx.graphics.getHeight()/VIRTUAL_SCREEN_HEIGHT;
	public static final float VIRTUAL_SCREEN_HEIGHTpRRE2f =
			screen_sizeh / RAPORT_RELATIV_ECRANE / 2f;
	public static final float DELTA_Y_NEXTER =
			VIRTUAL_SCREEN_HEIGHT/13.25f;
	public float angle_desc1_origX;
	public float angle_desc2_origX;
	public float angle_desc3_origX;
	public float angle_desc4_origX;
	public float angle_desc1_origX_NEWDEST;
	public float angle_desc2_origX_NEWDEST;
	public float angle_desc3_origX_NEWDEST;
	public float angle_desc4_origX_NEWDEST;
	public float VIRTUAL_SCREEN_HEIGHTpRRE2f_DELTA1;
	public float VIRTUAL_SCREEN_HEIGHTpRRE2f_DELTA2;
	public float VIRTUAL_SCREEN_HEIGHTpRRE2f_DELTA3;
	public float VIRTUAL_SCREEN_HEIGHTpRRE2f_DELTA4;
	public float x1;
	public float y1;
	public float z1;
	public float x2;
	public float y2;
	public float z2;
	public float x3;
	public float y3;
	public float z3;
	public float x4;
	public float y4;
	public float z4;
	public float raport_relativ_ecrane;
	public float raport_relativ_ecrane2;
	public Stack stack_angles1;
	public Stack stack_angles2;
	public Stack stack_angles3;
	public Stack stack_angles4;
	public Stage stage_angles;
	public Table table_angles;
	public Table angle_desc1;
	public Table angle_desc2;
	public Table angle_desc3;
	public Table angle_desc4;
	public TextButton angle_desc1B;
	public TextButton angle_desc2B;
	public TextButton angle_desc3B;
	public TextButton angle_desc4B;
	public TextButton angle_desc1B_title;
	public TextButton angle_desc2B_title;
	public TextButton angle_desc3B_title;
	public TextButton angle_desc4B_title;
	public TextButtonStyle angler_display_perfect;
	public TextButtonStyle angler_display_intermediate;
	public TextButtonStyle angler_display_normal;
	public TextButtonStyle angler_display_bad;
	public TextButtonStyle angler_display_ok;
	public TextButtonStyle angler_display_perfect_title;
	public TextButtonStyle angler_display_intermediate_title;
	public TextButtonStyle angler_display_normal_title;
	public TextButtonStyle angler_display_bad_title;
	public TextButtonStyle angler_display_ok_title;
	public boolean moved_to_first1 = false; 			//to fix jolt
	public boolean moved_to_first2 = false;				//to fix jolt
	public boolean moved_to_first3 = false;				//to fix jolt
	public boolean moved_to_first4 = false;				//to fix jolt
	public boolean startedtoalpha_to_first1 = false;	//used to pause draw for 1 second
	public boolean startedtoalpha_to_first2 = false;	//used to pause draw for 1 second
	public boolean startedtoalpha_to_first3 = false;	//used to pause draw for 1 second
	public boolean startedtoalpha_to_first4 = false;	//used to pause draw for 1 second
	public boolean startedtoalpha_to_first11 = false;	//used to pause draw for 1 second
	public boolean startedtoalpha_to_first22 = false;	//used to pause draw for 1 second
	public boolean startedtoalpha_to_first33 = false;	//used to pause draw for 1 second
	public boolean startedtoalpha_to_first44 = false;	//used to pause draw for 1 second
	public boolean is_angle1_busy = false;
	public boolean is_angle2_busy = false;
	public boolean is_angle3_busy = false;
	public boolean is_angle4_busy = false;
	public boolean alpha_downer1 = false;
	public boolean alpha_downer2 = false;
	public boolean alpha_downer3 = false;
	public boolean alpha_downer4 = false;
	public boolean is_generated_already1 = false;
	public boolean is_generated_upper1 = false;
	public boolean is_generated_already2 = false;
	public boolean is_generated_upper2 = false;
	public boolean is_generated_already3 = false;
	public boolean is_generated_upper3 = false;
	public boolean is_generated_already4 = false;
	public boolean is_generated_upper4 = false;
	public double angle1;
	public double angle2;
	public double angle3;
	public double angle4;
	public short upper_stack_number = -1;
	public short downr_stack_number = -1;
	public int angle_interm1;
	public int angle_interm2;
	public int angle_interm3;
	public Vector3 worldCoords1; 
	public Vector3 worldCoords2;
	public Vector3 worldCoords3;
	public Vector3 worldCoords4;
	public OrthographicCamera main_game_camera;
	public DecimalFormat decimal_transform = new DecimalFormat("00.00");

	public void ProcessHUDAngles(float y, float x, double angle){
		if(angle > current_max_angle){
			need_to_change_max_angle = true;
			need_to_change_hud_data = true;
			max_angle_txt = decimal_transform.format(angle);
			current_max_angle = Double.parseDouble(max_angle_txt.replace(",", "."));
		}
		// angle_interim1 is the first lowest angle; angle_interim2 is the second lowest angle;
		if(!is_angle1_busy){
			x1 = x;
			y1 = y;
			z1 = 0f;
			angle1 = angle;
			is_angle1_busy = true;
			if(angle1==ex01Types.ZEROF){
				angle_desc1B_title.setStyle(angler_display_perfect_title);
				angle_desc1B_title.setText(ex01Types.ZERO_ANGLE);
				angle_desc1B.setStyle(angler_display_perfect);
			} else if(angle1 <= angle_interm1){
				angle_desc1B.setStyle(angler_display_intermediate);
				angle_desc1B_title.setStyle(angler_display_perfect_title);
				angle_desc1B_title.setText(ex01Types.AWESOME_ANGLE +
						Float.toString(angle_interm1));
			} else if (angle1 > angle_interm1 && angle1 <= angle_interm2){
				angle_desc1B.setStyle(angler_display_intermediate);
				angle_desc1B_title.setStyle(angler_display_intermediate_title);
				angle_desc1B_title.setText(ex01Types.VGOOD_ANGLE +
						Float.toString(angle_interm2));
			} else if (angle1 > angle_interm2 && angle1 <= angle_interm3){
				angle_desc1B.setStyle(angler_display_normal);
				angle_desc1B_title.setStyle(angler_display_normal_title);
				angle_desc1B_title.setText(ex01Types.GOOD_ANGLE +
						Float.toString(angle_interm3));
			} else if (angle1 > angle_interm3 && angle1 < 40f){
				angle_desc1B.setStyle(angler_display_ok);
				angle_desc1B_title.setStyle(angler_display_ok_title);
				angle_desc1B_title.setText(ex01Types.OK_ANGLE);
			} else {
				angle_desc1B.setStyle(angler_display_bad);
				angle_desc1B_title.setStyle(angler_display_bad_title);
				angle_desc1B_title.setText(ex01Types.BAD_ANGLE);
			}	
		} else if(!is_angle2_busy){
			x2 = x;
			y2 = y;
			z2 = 0f;
			angle2 = angle;
			is_angle2_busy = true;
			if(angle2==ex01Types.ZEROF){
				angle_desc2B_title.setStyle(angler_display_perfect_title);
				angle_desc2B_title.setText(ex01Types.ZERO_ANGLE);
				angle_desc2B.setStyle(angler_display_perfect);
			} else if(angle2 <= angle_interm1){
				angle_desc2B.setStyle(angler_display_intermediate);
				angle_desc2B_title.setStyle(angler_display_perfect_title);
				angle_desc2B_title.setText(ex01Types.AWESOME_ANGLE +
						Float.toString(angle_interm1));
			} else if (angle2 > angle_interm1 && angle2 <= angle_interm2){
				angle_desc2B.setStyle(angler_display_intermediate);
				angle_desc2B_title.setStyle(angler_display_intermediate_title);
				angle_desc2B_title.setText(ex01Types.VGOOD_ANGLE +
						Float.toString(angle_interm2));
			} else if (angle2 > angle_interm2 && angle2 <= angle_interm3){
				angle_desc2B.setStyle(angler_display_normal);
				angle_desc2B_title.setStyle(angler_display_normal_title);
				angle_desc2B_title.setText(ex01Types.GOOD_ANGLE +
						Float.toString(angle_interm3));
			} else if (angle2 > angle_interm3 && angle2 < 40f){
				angle_desc2B.setStyle(angler_display_ok);
				angle_desc2B_title.setStyle(angler_display_ok_title);
				angle_desc2B_title.setText(ex01Types.OK_ANGLE);
			} else {
				angle_desc2B.setStyle(angler_display_bad);
				angle_desc2B_title.setStyle(angler_display_bad_title);
				angle_desc2B_title.setText(ex01Types.BAD_ANGLE);
			}
		} else if(!is_angle3_busy){
			x3 = x;
			y3 = y;
			z3 = 0f;
			angle3 = angle;
			is_angle3_busy = true;
			if(angle3==ex01Types.ZEROF){
				angle_desc3B_title.setStyle(angler_display_perfect_title);
				angle_desc3B_title.setText(ex01Types.ZERO_ANGLE);
				angle_desc3B.setStyle(angler_display_perfect);
			} else if(angle3 <= angle_interm1){
				angle_desc3B.setStyle(angler_display_intermediate);
				angle_desc3B_title.setStyle(angler_display_perfect_title);
				angle_desc3B_title.setText(ex01Types.AWESOME_ANGLE +
						Float.toString(angle_interm1));
			} else if (angle3 > angle_interm1 && angle3 <= angle_interm2){
				angle_desc3B.setStyle(angler_display_intermediate);
				angle_desc3B_title.setStyle(angler_display_intermediate_title);
				angle_desc3B_title.setText(ex01Types.VGOOD_ANGLE +
						Float.toString(angle_interm2));
			} else if (angle3 > angle_interm2 && angle3 <= angle_interm3){
				angle_desc3B.setStyle(angler_display_normal);
				angle_desc3B_title.setStyle(angler_display_normal_title);
				angle_desc3B_title.setText(ex01Types.GOOD_ANGLE +
						Float.toString(angle_interm3));
			} else if (angle3 > angle_interm3 && angle3 < 40f){
				angle_desc3B.setStyle(angler_display_ok);
				angle_desc3B_title.setStyle(angler_display_ok_title);
				angle_desc3B_title.setText(ex01Types.OK_ANGLE);
			} else {
				angle_desc3B.setStyle(angler_display_bad);
				angle_desc3B_title.setStyle(angler_display_bad_title);
				angle_desc3B_title.setText(ex01Types.BAD_ANGLE);
			}			
		} else if(!is_angle4_busy){
			x4 = x;
			y4 = y;
			z4 = 0f;
			angle4 = angle;
			is_angle4_busy = true;
			if(angle4==ex01Types.ZEROF){
				angle_desc4B_title.setStyle(angler_display_perfect_title);
				angle_desc4B_title.setText(ex01Types.ZERO_ANGLE);
				angle_desc4B.setStyle(angler_display_perfect);
			} else if(angle4 <= angle_interm1){
				angle_desc4B.setStyle(angler_display_intermediate);
				angle_desc4B_title.setStyle(angler_display_perfect_title);
				angle_desc4B_title.setText(ex01Types.AWESOME_ANGLE +
						Float.toString(angle_interm1));
			} else if (angle4 > angle_interm1 && angle4 <= angle_interm2){
				angle_desc4B.setStyle(angler_display_intermediate);
				angle_desc4B_title.setStyle(angler_display_intermediate_title);
				angle_desc4B_title.setText(ex01Types.VGOOD_ANGLE +
						Float.toString(angle_interm2));
			} else if (angle4 > angle_interm2 && angle4 <= angle_interm3){
				angle_desc4B.setStyle(angler_display_normal);
				angle_desc4B_title.setStyle(angler_display_normal_title);
				angle_desc4B_title.setText(ex01Types.GOOD_ANGLE +
						Float.toString(angle_interm3));
			} else if (angle4 > angle_interm3 && angle4 < 40f){
				angle_desc4B.setStyle(angler_display_ok);
				angle_desc4B_title.setStyle(angler_display_ok_title);
				angle_desc4B_title.setText(ex01Types.OK_ANGLE);
			} else {
				angle_desc4B.setStyle(angler_display_bad);
				angle_desc4B_title.setStyle(angler_display_bad_title);
				angle_desc4B_title.setText(ex01Types.BAD_ANGLE);
			}
		}
	}
	
	public void Hud_Powerup_Display(int ang1,
									int ang2,
									int ang3){
		angle_interm1 = ang3;
		angle_interm2 = ang2;
		angle_interm3 = ang1;
	}
	
	public void Hud_Powerup_DisplayReload(int ang1,
										  int ang2,
										  int ang3){
		angle_interm1 = ang3;
		angle_interm2 = ang2;
		angle_interm3 = ang1;
	}	
	
	public void ResetDisplayPos(){
		is_angle1_busy = false;
		is_angle2_busy = false;
		is_angle3_busy = false;
		is_angle4_busy = false;
		startedtoalpha_to_first4 = false;
		startedtoalpha_to_first3 = false;
		startedtoalpha_to_first2 = false;
		startedtoalpha_to_first1 = false;
		startedtoalpha_to_first11 = false;
		startedtoalpha_to_first22 = false;
		startedtoalpha_to_first33 = false;
		startedtoalpha_to_first44 = false;
		moved_to_first4 = false;
		moved_to_first3 = false;
		moved_to_first2 = false;
		moved_to_first1 = false;
		angle_desc1.clearActions();
		angle_desc2.clearActions();
		angle_desc3.clearActions();
		angle_desc4.clearActions();
		angle_desc1_origX_NEWDEST = angle_desc1_origX;
		angle_desc2_origX_NEWDEST = angle_desc2_origX;
		angle_desc3_origX_NEWDEST = angle_desc3_origX;
		angle_desc4_origX_NEWDEST = angle_desc4_origX;
		angle_desc4.setPosition(angle_desc4_origX_NEWDEST, + VIRTUAL_SCREEN_HEIGHT);
		angle_desc3.setPosition(angle_desc3_origX_NEWDEST, + VIRTUAL_SCREEN_HEIGHT);
		angle_desc2.setPosition(angle_desc2_origX_NEWDEST, + VIRTUAL_SCREEN_HEIGHT);
		angle_desc1.setPosition(angle_desc1_origX_NEWDEST, + VIRTUAL_SCREEN_HEIGHT);
		alpha_downer1 = false;
		alpha_downer2 = false;
		alpha_downer3 = false;
		alpha_downer4 = false;
		is_generated_already1 = false;
		is_generated_upper1 = false;
		is_generated_already2 = false;
		is_generated_upper2 = false;
		is_generated_already3 = false;
		is_generated_upper3 = false;
		is_generated_already4 = false;
		is_generated_upper4 = false;
		upper_stack_number = -1;
		downr_stack_number = -1;
	}
	
	public void ResetDisplayPosReload(){
		is_angle1_busy = false;
		is_angle2_busy = false;
		is_angle3_busy = false;
		is_angle4_busy = false;
		startedtoalpha_to_first4 = false;
		startedtoalpha_to_first3 = false;
		startedtoalpha_to_first2 = false;
		startedtoalpha_to_first1 = false;
		startedtoalpha_to_first11 = false;
		startedtoalpha_to_first22 = false;
		startedtoalpha_to_first33 = false;
		startedtoalpha_to_first44 = false;
		moved_to_first4 = false;
		moved_to_first3 = false;
		moved_to_first2 = false;
		moved_to_first1 = false;
		angle_desc1.clearActions();
		angle_desc2.clearActions();
		angle_desc3.clearActions();
		angle_desc4.clearActions();
		angle_desc1_origX_NEWDEST = angle_desc1_origX;
		angle_desc2_origX_NEWDEST = angle_desc2_origX;
		angle_desc3_origX_NEWDEST = angle_desc3_origX;
		angle_desc4_origX_NEWDEST = angle_desc4_origX;
		angle_desc4.setPosition(angle_desc4_origX_NEWDEST, + VIRTUAL_SCREEN_HEIGHT);
		angle_desc3.setPosition(angle_desc3_origX_NEWDEST, + VIRTUAL_SCREEN_HEIGHT);
		angle_desc2.setPosition(angle_desc2_origX_NEWDEST, + VIRTUAL_SCREEN_HEIGHT);
		angle_desc1.setPosition(angle_desc1_origX_NEWDEST, + VIRTUAL_SCREEN_HEIGHT);
		alpha_downer1 = false;
		alpha_downer2 = false;
		alpha_downer3 = false;
		alpha_downer4 = false;
		is_generated_already1 = false;
		is_generated_upper1 = false;
		is_generated_already2 = false;
		is_generated_upper2 = false;
		is_generated_already3 = false;
		is_generated_upper3 = false;
		is_generated_already4 = false;
		is_generated_upper4 = false;
		upper_stack_number = -1;
		downr_stack_number = -1;	
		alpha_downer1 = false;
		alpha_downer2 = false;
		alpha_downer3 = false;
		alpha_downer4 = false;
	}
	
	public void HudStageAnglesDisplayRender(float delta){
		UpdateDisplayPosFF();
		stage_angles.act(delta*SPEED_HUD_ANGLES);
		stage_angles.draw();
	}

	public void UpdateDisplayPosFF(){
		if(is_angle1_busy){
			worldCoords1.x = x1;
			worldCoords1.y = y1;
			worldCoords1.z = z1;
			worldCoords1 = main_game_camera.project(worldCoords1);
			worldCoords1.y = worldCoords1.y / raport_relativ_ecrane;
			if(!is_generated_already1){
				is_generated_already1 = true;
				if(worldCoords1.y>VIRTUAL_SCREEN_HEIGHT*3/5){
					downr_stack_number++;
					VIRTUAL_SCREEN_HEIGHTpRRE2f_DELTA1 =
							VIRTUAL_SCREEN_HEIGHTpRRE2f*1.35f +
									DELTA_Y_NEXTER * downr_stack_number;
				} else {
					upper_stack_number++;
					is_generated_upper1 = true;
					VIRTUAL_SCREEN_HEIGHTpRRE2f_DELTA1 =
							VIRTUAL_SCREEN_HEIGHTpRRE2f/1.40f -
									DELTA_Y_NEXTER * upper_stack_number;
				}
			}
			worldCoords1.y -= VIRTUAL_SCREEN_HEIGHTpRRE2f_DELTA1;
			angle_desc1.addAction(moveTo(angle_desc1_origX_NEWDEST, worldCoords1.y, 1f));
			if(startedtoalpha_to_first11){
				angle_desc1.addAction(alpha(ex01Types.HUD_ALPHA085));
				angle_desc1B.addAction(alpha(ex01Types.HUD_ALPHA085));
				startedtoalpha_to_first11 = false;
				angle_desc1B.addAction(rotateBy(360f, 2f));
				angle_desc1.addAction(sequence(
						delay(ex01Types.HUD_AN_DEL2F),
						alpha(0.010f, 3f)));
				alpha_downer1 = true;
				angle_desc1.addAction(sequence(
						delay(ex01Types.HUD_AN_DEL4F),
						run(new Runnable() {
					public void run() {
						angle_desc1_origX_NEWDEST = angle_desc1.getX() - VIRTUAL_SCREEN_WIDTH;
						if (is_generated_upper1) {
							upper_stack_number--;
						} else {
							downr_stack_number--;
						}
					}
				})));
			}
			if(startedtoalpha_to_first1){
				if(angle1==ex01Types.ZEROF){
					angle_desc1B.setText(ex01Types.PERFECT);
				} else if(angle1 <= angle_interm1){
					angle_desc1B.setText(decimal_transform.format(angle1) + ex01Types.degm);
				} else if (angle1 > angle_interm1 && angle1 <= angle_interm2){
					angle_desc1B.setText(decimal_transform.format(angle1) + ex01Types.degm);
				} else if (angle1 > angle_interm2 && angle1 <= angle_interm3){
					angle_desc1B.setText(decimal_transform.format(angle1) + ex01Types.degm);
				} else if (angle1 > angle_interm3){
					angle_desc1B.setText(decimal_transform.format(angle1) + ex01Types.degm);
				}
				startedtoalpha_to_first11 = true;
				startedtoalpha_to_first1 = false;
			}
			if(!moved_to_first1){
				angle_desc1.addAction(alpha(ex01Types.HUD_ALPHA0));
				angle_desc1B.addAction(alpha(ex01Types.HUD_ALPHA0));
				startedtoalpha_to_first1 = true;
				moved_to_first1 = true;
				if(!is_angle2_busy) angle_desc2.addAction(alpha(ex01Types.HUD_ALPHA0));
				if(!is_angle3_busy) angle_desc3.addAction(alpha(ex01Types.HUD_ALPHA0));
				if(!is_angle4_busy) angle_desc4.addAction(alpha(ex01Types.HUD_ALPHA0));
			}
			if(worldCoords1.y < - VIRTUAL_SCREEN_HEIGHT / 1.50f){
				angle_desc1.clearActions();
				angle_desc1.setPosition(angle_desc1_origX, + VIRTUAL_SCREEN_HEIGHT);
				angle_desc1_origX_NEWDEST = angle_desc1_origX;
				is_angle1_busy = false;
				is_generated_already1 = false;
				is_generated_upper1 = false;
				moved_to_first1 = false;
				startedtoalpha_to_first11 = false;
				startedtoalpha_to_first1 = false;
				alpha_downer1 = false;
			}
		}

		if(is_angle2_busy){
			worldCoords2.x = x2;
			worldCoords2.y = y2;
			worldCoords2.z = z2;
			worldCoords2 = main_game_camera.project(worldCoords2);
			worldCoords2.y = worldCoords2.y / raport_relativ_ecrane;
			if(!is_generated_already2){
				is_generated_already2 = true;
				if(worldCoords2.y>VIRTUAL_SCREEN_HEIGHT*3/5){
					downr_stack_number++;
					VIRTUAL_SCREEN_HEIGHTpRRE2f_DELTA2 =
							VIRTUAL_SCREEN_HEIGHTpRRE2f*1.35f +
									DELTA_Y_NEXTER * downr_stack_number;
				} else {
					upper_stack_number++;
					is_generated_upper2 = true;
					VIRTUAL_SCREEN_HEIGHTpRRE2f_DELTA2 =
							VIRTUAL_SCREEN_HEIGHTpRRE2f/1.40f -
									DELTA_Y_NEXTER * upper_stack_number;
				}
			}
			worldCoords2.y -= VIRTUAL_SCREEN_HEIGHTpRRE2f_DELTA2;
			angle_desc2.addAction(moveTo(angle_desc2_origX_NEWDEST, worldCoords2.y, 1f));
			if(startedtoalpha_to_first22){
				angle_desc2.addAction(alpha(ex01Types.HUD_ALPHA085));
				angle_desc2B.addAction(alpha(ex01Types.HUD_ALPHA085));
				startedtoalpha_to_first22 = false;
				angle_desc2B.addAction(rotateBy(360f, 2f));
				angle_desc2.addAction(sequence(
						delay(ex01Types.HUD_AN_DEL2F),
						alpha(0.010f, 3f)));
				alpha_downer2 = true;
				angle_desc2.addAction(sequence(
						delay(ex01Types.HUD_AN_DEL4F),
						run(new Runnable() {
					public void run() {
						angle_desc2_origX_NEWDEST = angle_desc2.getX() - VIRTUAL_SCREEN_WIDTH;
						if (is_generated_upper2) {
							upper_stack_number--;
						} else {
							downr_stack_number--;
						}
					}
				})));
			}
			if(startedtoalpha_to_first2){
				if(angle2==ex01Types.ZEROF){
					angle_desc2B.setText(ex01Types.PERFECT);
				} else if(angle2 <= angle_interm1){
					angle_desc2B.setText(decimal_transform.format(angle2) + ex01Types.degm);
				} else if (angle2 > angle_interm1 && angle2 <= angle_interm2){
					angle_desc2B.setText(decimal_transform.format(angle2) + ex01Types.degm);
				} else if (angle2 > angle_interm2 && angle2 <= angle_interm3){
					angle_desc2B.setText(decimal_transform.format(angle2) + ex01Types.degm);
				} else if (angle2 > angle_interm3){
					angle_desc2B.setText(decimal_transform.format(angle2) + ex01Types.degm);
				}
				startedtoalpha_to_first22 = true;
				startedtoalpha_to_first2 = false;
			}
			if(!moved_to_first2){
				angle_desc2.addAction(alpha(ex01Types.HUD_ALPHA0));
				angle_desc2B.addAction(alpha(ex01Types.HUD_ALPHA0));
				startedtoalpha_to_first2 = true;
				moved_to_first2 = true;
				if(!is_angle1_busy) angle_desc1.addAction(alpha(ex01Types.HUD_ALPHA0));
				if(!is_angle3_busy) angle_desc3.addAction(alpha(ex01Types.HUD_ALPHA0));
				if(!is_angle4_busy) angle_desc4.addAction(alpha(ex01Types.HUD_ALPHA0));
			}
			if(worldCoords2.y < - VIRTUAL_SCREEN_HEIGHT / 1.50f){
				angle_desc2.clearActions();
				angle_desc2.setPosition(angle_desc2_origX, + VIRTUAL_SCREEN_HEIGHT);
				angle_desc2_origX_NEWDEST = angle_desc2_origX;
				is_angle2_busy = false;
				is_generated_already2 = false;
				is_generated_upper2 = false;
				moved_to_first2 = false;
				startedtoalpha_to_first22 = false;
				startedtoalpha_to_first2 = false;
				alpha_downer2 = false;
			}
		}

		if(is_angle3_busy){
			worldCoords3.x = x3;
			worldCoords3.y = y3;
			worldCoords3.z = z3;
			worldCoords3 = main_game_camera.project(worldCoords3);
			worldCoords3.y = worldCoords3.y / raport_relativ_ecrane;
			if(!is_generated_already3){
				is_generated_already3 = true;
				if(worldCoords3.y>VIRTUAL_SCREEN_HEIGHT*3/5){
					downr_stack_number++;
					VIRTUAL_SCREEN_HEIGHTpRRE2f_DELTA3 =
							VIRTUAL_SCREEN_HEIGHTpRRE2f*1.35f +
									DELTA_Y_NEXTER * downr_stack_number;
				} else {
					upper_stack_number++;
					is_generated_upper3 = true;
					VIRTUAL_SCREEN_HEIGHTpRRE2f_DELTA3 =
							VIRTUAL_SCREEN_HEIGHTpRRE2f/1.40f -
									DELTA_Y_NEXTER * upper_stack_number;
				}
			}
			worldCoords3.y -= VIRTUAL_SCREEN_HEIGHTpRRE2f_DELTA3;
			angle_desc3.addAction(moveTo(angle_desc3_origX_NEWDEST, worldCoords3.y, 1f));
			if(startedtoalpha_to_first33){
				angle_desc3.addAction(alpha(ex01Types.HUD_ALPHA085));
				angle_desc3B.addAction(alpha(ex01Types.HUD_ALPHA085));
				startedtoalpha_to_first33 = false;
				angle_desc3B.addAction(rotateBy(360f, 2f));
				angle_desc3.addAction(sequence(
						delay(ex01Types.HUD_AN_DEL2F),
						alpha(0.010f, 3f)));
				alpha_downer3 = true;
				angle_desc3.addAction(sequence(
						delay(ex01Types.HUD_AN_DEL4F),
						run(new Runnable() {
					public void run() {
						angle_desc3_origX_NEWDEST = angle_desc3.getX() - VIRTUAL_SCREEN_WIDTH;

						if (is_generated_upper3) {
							upper_stack_number--;
						} else {
							downr_stack_number--;
						}
					}
				})));
			}
			if(startedtoalpha_to_first3){
				if(angle3==ex01Types.ZEROF){
					angle_desc3B.setText(ex01Types.PERFECT);
				} else if(angle3 <= angle_interm1){
					angle_desc3B.setText(decimal_transform.format(angle3) + ex01Types.degm);
				} else if (angle3 > angle_interm1 && angle3 <= angle_interm2){
					angle_desc3B.setText(decimal_transform.format(angle3) + ex01Types.degm);
				} else if (angle3 > angle_interm2 && angle3 <= angle_interm3){
					angle_desc3B.setText(decimal_transform.format(angle3) + ex01Types.degm);
				} else if (angle3 > angle_interm3){
					angle_desc3B.setText(decimal_transform.format(angle3) + ex01Types.degm);
				}
				startedtoalpha_to_first33 = true;
				startedtoalpha_to_first3 = false;
			}
			if(!moved_to_first3){
				angle_desc3.addAction(alpha(ex01Types.HUD_ALPHA0));
				angle_desc3B.addAction(alpha(ex01Types.HUD_ALPHA0));
				startedtoalpha_to_first3 = true;
				moved_to_first3 = true;
				if(!is_angle1_busy) angle_desc1.addAction(alpha(ex01Types.HUD_ALPHA0));
				if(!is_angle2_busy) angle_desc2.addAction(alpha(ex01Types.HUD_ALPHA0));
				if(!is_angle4_busy) angle_desc4.addAction(alpha(ex01Types.HUD_ALPHA0));
			}
			if(worldCoords3.y < - VIRTUAL_SCREEN_HEIGHT / 1.50f){
				angle_desc3.clearActions();
				angle_desc3.setPosition(angle_desc3_origX, + VIRTUAL_SCREEN_HEIGHT);
				angle_desc3_origX_NEWDEST = angle_desc3_origX;
				is_angle3_busy = false;
				is_generated_already3 = false;
				is_generated_upper3 = false;
				moved_to_first3 = false;
				startedtoalpha_to_first33 = false;
				startedtoalpha_to_first3 = false;
				alpha_downer3 = false;
			}
		}

		if(is_angle4_busy){
			worldCoords4.x = x4;
			worldCoords4.y = y4;
			worldCoords4.z = z4;
			worldCoords4 = main_game_camera.project(worldCoords4);
			worldCoords4.y = worldCoords4.y / raport_relativ_ecrane;
			if(!is_generated_already4){
				is_generated_already4 = true;
				if(worldCoords4.y>VIRTUAL_SCREEN_HEIGHT*3/5){
					downr_stack_number++;
					VIRTUAL_SCREEN_HEIGHTpRRE2f_DELTA4 =
							VIRTUAL_SCREEN_HEIGHTpRRE2f*1.35f +
									DELTA_Y_NEXTER * downr_stack_number;
				} else {
					upper_stack_number++;
					is_generated_upper4 = true;
					VIRTUAL_SCREEN_HEIGHTpRRE2f_DELTA4 =
							VIRTUAL_SCREEN_HEIGHTpRRE2f/1.40f -
									DELTA_Y_NEXTER * upper_stack_number;
				}
			}
			worldCoords4.y -= VIRTUAL_SCREEN_HEIGHTpRRE2f_DELTA4;
			angle_desc4.addAction(moveTo(angle_desc4_origX_NEWDEST, worldCoords4.y, 1f));
			if(startedtoalpha_to_first44){
				angle_desc4.addAction(alpha(ex01Types.HUD_ALPHA085));
				angle_desc4B.addAction(alpha(ex01Types.HUD_ALPHA085));
				startedtoalpha_to_first44 = false;
				angle_desc4B.addAction(rotateBy(360f, 2f));
				angle_desc4.addAction(sequence(
						delay(ex01Types.HUD_AN_DEL2F),
						alpha(0.010f, 3f)));
				alpha_downer4 = true;
				angle_desc4.addAction(sequence(
						delay(ex01Types.HUD_AN_DEL4F),
						run(new Runnable() {
					public void run() {
						angle_desc4_origX_NEWDEST = angle_desc4.getX() - VIRTUAL_SCREEN_WIDTH;

						if (is_generated_upper4) {
							upper_stack_number--;
						} else {
							downr_stack_number--;
						}
					}
				})));
			}
			if(startedtoalpha_to_first4){
				if(angle4==ex01Types.ZEROF){
					angle_desc4B.setText(ex01Types.PERFECT);
				} else if(angle4 <= angle_interm1){
					angle_desc4B.setText(decimal_transform.format(angle4) + ex01Types.degm);
				} else if (angle4 > angle_interm1 && angle4 <= angle_interm2){
					angle_desc4B.setText(decimal_transform.format(angle4) + ex01Types.degm);
				} else if (angle4 > angle_interm2 && angle4 <= angle_interm3){
					angle_desc4B.setText(decimal_transform.format(angle4) + ex01Types.degm);
				} else if (angle4 > angle_interm3){
					angle_desc4B.setText(decimal_transform.format(angle4) + ex01Types.degm);
				}
				startedtoalpha_to_first44 = true;
				startedtoalpha_to_first4 = false;
			}
			if(!moved_to_first4){
				angle_desc4.addAction(alpha(ex01Types.HUD_ALPHA0));
				angle_desc4B.addAction(alpha(ex01Types.HUD_ALPHA0));
				startedtoalpha_to_first4 = true;
				moved_to_first4 = true;
				if(!is_angle1_busy) angle_desc1.addAction(alpha(ex01Types.HUD_ALPHA0));
				if(!is_angle2_busy) angle_desc2.addAction(alpha(ex01Types.HUD_ALPHA0));
				if(!is_angle3_busy) angle_desc3.addAction(alpha(ex01Types.HUD_ALPHA0));
			}
			if(worldCoords4.y < - VIRTUAL_SCREEN_HEIGHT / 1.50f){
				angle_desc4.clearActions();
				angle_desc4.setPosition(angle_desc4_origX, + VIRTUAL_SCREEN_HEIGHT);
				angle_desc4_origX_NEWDEST = angle_desc4_origX;
				is_angle4_busy = false;
				is_generated_already4 = false;
				is_generated_upper4 = false;
				moved_to_first4 = false;
				startedtoalpha_to_first44 = false;
				startedtoalpha_to_first4 = false;
				alpha_downer4 = false;
			}
		}
	}
	
	public void Hud_Angles_Display(Viewport viewport){
		table_angles = new Table();
		worldCoords1 = new Vector3();
		worldCoords2 = new Vector3();
		worldCoords3 = new Vector3();
		worldCoords4 = new Vector3();
		stage_angles = new Stage(viewport);
		angle_desc1 = new Table();
		angle_desc2 = new Table();
		angle_desc3 = new Table();
		angle_desc4 = new Table();
		stack_angles1 = new Stack();
		stack_angles2 = new Stack();
		stack_angles3 = new Stack();
		stack_angles4 = new Stack();

		angler_display_perfect = new TextButtonStyle();
		angler_display_perfect.font = game_screen_base.hud_angler_fontp_bitmap;
		angler_display_intermediate = new TextButtonStyle();
		angler_display_intermediate.font = game_screen_base.hud_angler_font_bitmap;
		angler_display_intermediate.fontColor = Color.ORANGE;
		//angler_display_intermediate.fontColor = new Color(0.400f, 1.000f, 0.400f, 1.000f);
		angler_display_normal = new TextButtonStyle();
		angler_display_normal.font = game_screen_base.hud_angler_font_bitmap;
		angler_display_normal.fontColor = Color.PINK;
		//angler_display_normal.fontColor = new Color(0.200f, 0.800f, 0.800f, 1.000f);
		angler_display_bad = new TextButtonStyle();
		angler_display_bad.font = game_screen_base.hud_angler_font_bitmap;
		angler_display_bad.fontColor = Color.GRAY;
		angler_display_ok = new TextButtonStyle();
		angler_display_ok.font = game_screen_base.hud_angler_font_bitmap;
		angler_display_ok.fontColor = Color.LIGHT_GRAY;
		//angler_display_bad.fontColor = new Color(0.400f, 0.600f, 0.200f, 1.000f);

		angler_display_perfect_title = new TextButtonStyle();
		angler_display_perfect_title.font = game_screen_base.hud_angler_font_title_bitmap;
		angler_display_perfect_title.fontColor = Color.GREEN;
		angler_display_intermediate_title = new TextButtonStyle();
		angler_display_intermediate_title.font = game_screen_base.hud_angler_font_title_bitmap;
		angler_display_intermediate_title.fontColor = Color.ORANGE;
		angler_display_normal_title = new TextButtonStyle();
		angler_display_normal_title.font = game_screen_base.hud_angler_font_title_bitmap;
		angler_display_normal_title.fontColor = Color.PINK;
		angler_display_bad_title = new TextButtonStyle();
		angler_display_bad_title.font = game_screen_base.hud_angler_font_title_bitmap;
		angler_display_bad_title.fontColor = Color.GRAY;
		angler_display_ok_title = new TextButtonStyle();
		angler_display_ok_title.font = game_screen_base.hud_angler_font_title_bitmap;
		angler_display_ok_title.fontColor = Color.LIGHT_GRAY;

		angle_desc1B = new TextButton(" ", angler_display_normal);
		angle_desc2B = new TextButton(" ", angler_display_normal);
		angle_desc3B = new TextButton(" ", angler_display_normal);
		angle_desc4B = new TextButton(" ", angler_display_normal);
		angle_desc1B_title = new TextButton("GOOD SHOT", angler_display_normal_title);
		angle_desc2B_title = new TextButton("GOOD SHOT", angler_display_normal_title);
		angle_desc3B_title = new TextButton("GOOD SHOT", angler_display_normal_title);
		angle_desc4B_title = new TextButton("GOOD SHOT", angler_display_normal_title);
		angle_desc1B.setTransform(true);
		angle_desc1B.setOrigin(Align.center);
		angle_desc2B.setTransform(true);
		angle_desc2B.setOrigin(Align.center);
		angle_desc3B.setTransform(true);
		angle_desc3B.setOrigin(Align.center);
		angle_desc4B.setTransform(true);
		angle_desc4B.setOrigin(Align.center);
		angle_desc1.setTransform(true);
		angle_desc2.setTransform(true);
		angle_desc3.setTransform(true);
		angle_desc4.setTransform(true);
		angle_desc1.add(angle_desc1B_title)
				.width(VIRTUAL_SCREEN_WIDTH / 4f)
				.height(VIRTUAL_SCREEN_WIDTH / 4f).pad(0f);
		angle_desc1.row();
		angle_desc1.add(angle_desc1B)
				.width(VIRTUAL_SCREEN_WIDTH / 4f)
				.height(VIRTUAL_SCREEN_WIDTH / 4f
				).pad(0f)
				.padTop(-VIRTUAL_SCREEN_WIDTH / 5.150f);
		angle_desc2.add(angle_desc2B_title)
				.width(VIRTUAL_SCREEN_WIDTH / 4f)
				.height(VIRTUAL_SCREEN_WIDTH / 4f).pad(0f);
		angle_desc2.row();
		angle_desc2.add(angle_desc2B)
				.width(VIRTUAL_SCREEN_WIDTH / 4f)
				.height(VIRTUAL_SCREEN_WIDTH / 4f)
				.pad(0f)
				.padTop(-VIRTUAL_SCREEN_WIDTH / 5.150f);
		angle_desc3.add(angle_desc3B_title)
				.width(VIRTUAL_SCREEN_WIDTH / 4f)
				.height(VIRTUAL_SCREEN_WIDTH / 4f).pad(0f);
		angle_desc3.row();
		angle_desc3.add(angle_desc3B)
				.width(VIRTUAL_SCREEN_WIDTH / 4f)
				.height(VIRTUAL_SCREEN_WIDTH / 4f)
				.pad(0f)
				.padTop(-VIRTUAL_SCREEN_WIDTH / 5.150f);
		angle_desc4.add(angle_desc4B_title)
				.width(VIRTUAL_SCREEN_WIDTH / 4f)
				.height(VIRTUAL_SCREEN_WIDTH / 4f).pad(0f);
		angle_desc4.row();
		angle_desc4.add(angle_desc4B)
				.width(VIRTUAL_SCREEN_WIDTH / 4f)
				.height(VIRTUAL_SCREEN_WIDTH / 4f)
				.pad(0f)
				.padTop(-VIRTUAL_SCREEN_WIDTH / 5.150f);
		//We add each table in it's own stack so that when we add the moveTo action to the table
		//we don't have a jolt of all the other tables added to the stack - 6 fucking days it took
		//me to solve this god-damned problem
		//But, we also need to alpha to zero the table and the button before we set the text , then
		//we make it 1 again in the next frame
		stack_angles1.add(angle_desc1);
		stack_angles2.add(angle_desc2);
		stack_angles3.add(angle_desc3);
		stack_angles4.add(angle_desc4);
		angle_desc1.setPosition(angle_desc1.getX(), +VIRTUAL_SCREEN_HEIGHT);
		angle_desc2.setPosition(angle_desc2.getX(), + VIRTUAL_SCREEN_HEIGHT);
		angle_desc3.setPosition(angle_desc3.getX(), + VIRTUAL_SCREEN_HEIGHT);
		angle_desc4.setPosition(angle_desc4.getX(), + VIRTUAL_SCREEN_HEIGHT);
		angle_desc1_origX = angle_desc1.getX();
		angle_desc2_origX = angle_desc2.getX();
		angle_desc3_origX = angle_desc3.getX();
		angle_desc4_origX = angle_desc4.getX();
		angle_desc1_origX_NEWDEST = angle_desc1_origX;
		angle_desc2_origX_NEWDEST = angle_desc2_origX;
		angle_desc3_origX_NEWDEST = angle_desc3_origX;
		angle_desc4_origX_NEWDEST = angle_desc4_origX;
		stack_angles1.setFillParent(true);
		stack_angles2.setFillParent(true);
		stack_angles3.setFillParent(true);
		stack_angles4.setFillParent(true);
		stage_angles.addActor(stack_angles1);
		stage_angles.addActor(stack_angles2);
		stage_angles.addActor(stack_angles3);
		stage_angles.addActor(stack_angles4);
		stack_angles1.pack();
		stack_angles2.pack();
		stack_angles3.pack();
		stack_angles4.pack();
		ResetDisplayPos();
	}	
	
	public void Hud_Angles_DisplayReload(){
		angler_display_perfect.font = game_screen_base.hud_angler_fontp_bitmap;
		angler_display_intermediate.font = game_screen_base.hud_angler_font_bitmap;
		angler_display_normal.font = game_screen_base.hud_angler_font_bitmap;
		angler_display_bad.font = game_screen_base.hud_angler_font_bitmap;
		angler_display_ok.font = game_screen_base.hud_angler_font_bitmap;
		angler_display_perfect_title.font = game_screen_base.hud_angler_font_title_bitmap;
		angler_display_intermediate_title.font = game_screen_base.hud_angler_font_title_bitmap;
		angler_display_normal_title.font = game_screen_base.hud_angler_font_title_bitmap;
		angler_display_bad_title.font = game_screen_base.hud_angler_font_title_bitmap;
		angler_display_ok_title.font = game_screen_base.hud_angler_font_title_bitmap;
		angle_desc1B.setStyle(angler_display_normal);
		angle_desc2B.setStyle(angler_display_normal);
		angle_desc3B.setStyle(angler_display_normal);
		angle_desc4B.setStyle(angler_display_normal);
		angle_desc1B_title.setStyle(angler_display_normal_title);
		angle_desc2B_title.setStyle(angler_display_normal_title);
		angle_desc3B_title.setStyle(angler_display_normal_title);
		angle_desc4B_title.setStyle(angler_display_normal_title);
		ResetDisplayPos();
		angle_desc1B.validate();
		angle_desc2B.validate();
		angle_desc3B.validate();
		angle_desc4B.validate();
		angle_desc1B_title.validate();
		angle_desc2B_title.validate();
		angle_desc3B_title.validate();
		angle_desc4B_title.validate();
		stack_angles1.validate();
		stack_angles2.validate();
		stack_angles3.validate();
		stack_angles4.validate();
	}	
	
	public void renderDisplayFUDsTables(boolean paused_game_screen, float delta){
		if(!paused_game_screen){
			HudStageAnglesDisplayRender(delta);
		}
		HudStageRender(delta);
	}
	
	public void DisposeHUDDisplay_AnglesScene(){
		stack_angles1.clear();
		stack_angles2.clear();
		stack_angles3.clear();
		stack_angles4.clear();
		stage_angles.clear();
		table_angles.clear();
		angle_desc1.clear();
		angle_desc2.clear();
		angle_desc3.clear();
		angle_desc4.clear();
		angle_desc1B.clear();
		angle_desc2B.clear();
		angle_desc3B.clear();
		angle_desc4B.clear();
		angle_desc1B_title.clear();
		angle_desc2B_title.clear();
		angle_desc3B_title.clear();
		angle_desc4B_title.clear();

		stack_angles1 = null;
		stack_angles2 = null;
		stack_angles3 = null;
		stack_angles4 = null;
		if(stage_angles!=null) stage_angles.dispose(); stage_angles = null;
		table_angles = null;
		angle_desc1 = null;
		angle_desc2 = null;
		angle_desc3 = null;
		angle_desc4 = null;
		angle_desc1B = null;
		angle_desc2B = null;
		angle_desc3B = null;
		angle_desc4B = null;
		angle_desc1B_title = null;
		angle_desc2B_title = null;
		angle_desc3B_title = null;
		angle_desc4B_title = null;
		angler_display_perfect = null;
		angler_display_intermediate = null;
		angler_display_normal = null;
		angler_display_bad = null;
		angler_display_ok = null;
		angler_display_perfect_title = null;
		angler_display_intermediate_title = null;
		angler_display_normal_title = null;
		angler_display_bad_title = null;
		angler_display_ok_title = null;
		worldCoords1 = null;
		worldCoords2 = null;
		worldCoords3 = null;
		worldCoords4 = null;
		main_game_camera = null;
		decimal_transform = null;
	}
	
	// POWERUPS/ANGLES DISPLAY HUD TEXT rolling up and down
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	
	
	
	//--------------------------------------------------------------------------------------------//
	//--------------------------------------------------------------------------------------------//
	//--------------------------------------------------------------------------------------------//



	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	// RUN OUT OF BULLETS AT THE END
	
	public Stage stage_run_out_of_bullets;
	public TextButton run_out_bullets_main_menu;
	public TextButton run_out_bullets_buy;
	public TextButton level_base_nobulletslife_error;
	public TextButtonStyle run_out_bullets_main_menu_st;
	public TextButtonStyle run_out_bullets_st;
	public TextButtonStyle textb1_nobulletslife;
	public TextButtonStyle textb2_nobulletslife;
	public TextButtonStyle textb3_nobulletslife;
	public Table run_out_bullets_main_menu_t;
	public Table run_out_bullets_buy_t;
	public Table get_nobulletslife_table_error;
	public Table level_base_nobulletslife_t_error;
	public Stack stack_run_out_of_bullets;
	public Stack settings_get_nobulletslife_error;
	public boolean added_nobullets = false;           // added used for Scene2D no bullets dialog
	public boolean no_more_bullets_recorded = false;
	public boolean no_more_bullets_recorded_done = false;
	public Skin skin_replay;
	
	public void InitNoMoreBulletsTable(Viewport viewport){
	   	//INIT the owned tables
		run_out_bullets_main_menu_t = new Table();
		run_out_bullets_buy_t = new Table();
		run_out_bullets_main_menu_st = new TextButtonStyle();
		run_out_bullets_st = new TextButtonStyle();
		stage_run_out_of_bullets = new Stage(viewport);
		stack_run_out_of_bullets = new Stack();
		TextureRegion style1 = skin_replay.getRegion("get_ammos");
		TextureRegion style2_menu = skin_replay.getRegion("back_main_menu");
		run_out_bullets_main_menu_st.font = skin_replay.getFont("errors-font");
		run_out_bullets_main_menu_st.up = new TextureRegionDrawable(style2_menu);
		run_out_bullets_main_menu_st.fontColor = Color.CYAN;
		run_out_bullets_st.font = skin_replay.getFont("errors-font");
		run_out_bullets_st.up = new TextureRegionDrawable(style1);
		run_out_bullets_st.fontColor = Color.CYAN;		
		run_out_bullets_main_menu = new TextButton("", run_out_bullets_main_menu_st);
		run_out_bullets_buy = new TextButton("", run_out_bullets_st);
		run_out_bullets_main_menu_t.add(run_out_bullets_main_menu)
				.width(VIRTUAL_SCREEN_WIDTH / 1.9f)
				.height(VIRTUAL_SCREEN_WIDTH / 1.9f * 168f / 512f);
		run_out_bullets_main_menu_t.setFillParent(true);
		run_out_bullets_main_menu_t.setTransform(true);
		run_out_bullets_main_menu_t.top().padTop(VIRTUAL_SCREEN_WIDTH/8f);
		run_out_bullets_buy_t.add(run_out_bullets_buy)
				.width(VIRTUAL_SCREEN_WIDTH / 1.2f)
				.height(VIRTUAL_SCREEN_WIDTH / 1.2f * 88f / 256f);
		run_out_bullets_buy_t.setFillParent(true);
		run_out_bullets_buy_t.setTransform(true);
		run_out_bullets_buy_t.center().padTop(VIRTUAL_SCREEN_WIDTH/12f);
		stack_run_out_of_bullets.add(run_out_bullets_main_menu_t);
		stack_run_out_of_bullets.add(run_out_bullets_buy_t);
		stack_run_out_of_bullets.setFillParent(true);
		stage_run_out_of_bullets.addActor(stack_run_out_of_bullets);
		stage_run_out_of_bullets.addAction(Actions.fadeOut(0f));
	}
	
	public void InitNoMoreBulletsTableReload(){

	}
	
	public void NoMoreBullets(){
		if(no_more_bullets_recorded){
			if(!no_more_bullets_recorded_done){
				stage_run_out_of_bullets.addAction(Actions.fadeIn(3f));
				no_more_bullets_recorded_done = true;
			}
		}
	}
	
	public void Hud_NoMoreBullets(){
    	//INIT the owned tables
		get_nobulletslife_table_error = new Table();
		textb1_nobulletslife = new TextButtonStyle();
		textb2_nobulletslife = new TextButtonStyle();
		textb3_nobulletslife = new TextButtonStyle();
		TextureRegion style1 = skin_replay.getRegion("get_ammos");
		TextureRegion style2 = skin_replay.getRegion("get_ammos");
		TextureRegion style3 = skin_replay.getRegion("get_ammos");
		textb1_nobulletslife.font = skin_replay.getFont("errors-font");
		textb1_nobulletslife.up = new TextureRegionDrawable(style1);
		textb1_nobulletslife.fontColor = Color.CYAN;
		textb2_nobulletslife.font = skin_replay.getFont("errors-font");
		textb2_nobulletslife.up = new TextureRegionDrawable(style2);
		textb3_nobulletslife.font = skin_replay.getFont("errors-font");
		textb3_nobulletslife.up = new TextureRegionDrawable(style3);
		//this stack contains the owned stuff
		settings_get_nobulletslife_error = new Stack();		
		get_nobulletslife_table_error.add(settings_get_nobulletslife_error)
				.width(VIRTUAL_SCREEN_WIDTH / 1.6f)
				.height(VIRTUAL_SCREEN_WIDTH / 1.6f * 85f / 384f);
		get_nobulletslife_table_error.setFillParent(true);
		get_nobulletslife_table_error.setTransform(true);
		get_nobulletslife_table_error.top().padTop(VIRTUAL_SCREEN_WIDTH/5.500f);
		get_nobulletslife_table_error.addAction(moveBy(0f,-25f,0f));
		level_base_nobulletslife_t_error = new Table();
		level_base_nobulletslife_t_error.setTransform(true);
		level_base_nobulletslife_error = new TextButton("", textb1_nobulletslife);	
		level_base_nobulletslife_error.getLabelCell().padTop(VIRTUAL_SCREEN_WIDTH/20f);
		level_base_nobulletslife_t_error.setFillParent(true);
		level_base_nobulletslife_t_error.add(level_base_nobulletslife_error)
				.width(VIRTUAL_SCREEN_WIDTH / 1.45f)
				.height(VIRTUAL_SCREEN_WIDTH / 1.45f * 155f / 512f);
		level_base_nobulletslife_t_error.bottom();
		settings_get_nobulletslife_error.add(level_base_nobulletslife_t_error);
		stage_life_bullets_text.addActor(get_nobulletslife_table_error);
		get_nobulletslife_table_error.addAction(Actions.fadeOut(0f));
	} 			
	
	public void Hud_NoMoreBulletsReload(){

	}

	public void DisposeHUDDisplay_RunOBullets(){
		stage_run_out_of_bullets.clear();
		run_out_bullets_main_menu.clear();
		run_out_bullets_buy.clear();
		level_base_nobulletslife_error.clear();
		run_out_bullets_main_menu_t.clear();
		run_out_bullets_buy_t.clear();
		get_nobulletslife_table_error.clear();
		level_base_nobulletslife_t_error.clear();
		stack_run_out_of_bullets.clear();
		settings_get_nobulletslife_error.clear();
		//
		if(stage_run_out_of_bullets!=null)
			stage_run_out_of_bullets.dispose(); stage_run_out_of_bullets = null;
		run_out_bullets_main_menu = null;
		run_out_bullets_buy = null;
		level_base_nobulletslife_error = null;
		run_out_bullets_main_menu_st = null;
		run_out_bullets_st = null;
		textb1_nobulletslife = null;
		textb2_nobulletslife = null;
		textb3_nobulletslife = null;
		run_out_bullets_main_menu_t = null;
		run_out_bullets_buy_t = null;
		get_nobulletslife_table_error = null;
		level_base_nobulletslife_t_error = null;
		stack_run_out_of_bullets = null;
		settings_get_nobulletslife_error = null;
		if(skin_replay!=null) skin_replay.dispose(); skin_replay = null;
	}
	
	// RUN OUT OF BULLETS AT THE END
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	
	
	
	//--------------------------------------------------------------------------------------------//
	//--------------------------------------------------------------------------------------------//
	//--------------------------------------------------------------------------------------------//



	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	// HUD ON MAIN SCREEN WHILE GAME IS PLAYING
	public static final float screen_hW2 = 800f / 480f *25f / 2f;
	public static final float CENTER_CAMERA_X = 15f;
    public static final float HUD_PAUSE_W = 430f;
    public static final float HUD_PAUSE_H = 278f;
    public static final float BUTTON_W = 256f;
    public static final float BUTTON_H = 106f;	
    public static final float VIRTUAL_SCREEN_WIDTH775 =
			VIRTUAL_SCREEN_WIDTH / 15.75f;
    public static final float VIRTUAL_SCREEN_WIDTH43 =
			VIRTUAL_SCREEN_WIDTH / 4.3f;
    public static final float VIRTUAL_SCREEN_WIDTH12 =
			VIRTUAL_SCREEN_WIDTH / 1.2f;
    public static final float VIRTUAL_SCREEN_HEIGHT12 =
			VIRTUAL_SCREEN_WIDTH12 * HUD_PAUSE_H / HUD_PAUSE_W;
    public static final float VIRTUAL_SCREEN_HEIGHT43 =
			VIRTUAL_SCREEN_WIDTH43 * BUTTON_H / BUTTON_W;
	public static final float RESIZER =
			((float)VIRTUAL_SCREEN_WIDTH/(float)VIRTUAL_SCREEN_HEIGHT) /
			((float)screen_sizew/(float)screen_sizeh);
	public static final float VIRTUAL_SCREEN_WIDTH235RESIZER =
			VIRTUAL_SCREEN_WIDTH/2.35f * RESIZER;
	// rotation speed for health and bullets sprites
	public static final float HUD_ICON_ROT_SPEED = 512f;
	public static final float HUD_HEALTH_SPRITE_W = 2.8f;
	public static final float HUD_HEALTH_SPRITE_H = 2.8f;
	public static final float HUD_HEALTH_SPRITE_POS_X =
			CENTER_CAMERA_X - 12f;
	public static final float HUD_HEALTH_SPRITE_POS_Y =
			screen_hW2 - 3.3f + 15.65f;
	public static final float HUD_HEALTH_SPRITE_A = 0.8f;
	public static final float HUD_BULLETS_SPRITE_W = 2.3f;
	public static final float HUD_BULLETS_SPRITE_H = 2.3f;	
	public static final float HUD_BULLETS_SPRITE_POS_X =
			CENTER_CAMERA_X + 9.7f;
	public static final float HUD_BULLETS_SPRITE_POS_Y =
			screen_hW2 - 3.3f + 15.9f;
	public static final float HUD_BULLETS_SPRITE_A = 1f;	
	public static final float HUD_DISPLAY_POWERUPS_SPRITE_W = 25f;
	public static final float HUD_DISPLAY_POWERUPS_SPRITE_H = 2.9785f;
	public static final float HUD_DISPLAY_POWERUPS_SPRITE_POS_X =
			CENTER_CAMERA_X - 12.5f;
	public static final float HUD_DISPLAY_POWERUPS_SPRITE_POS_Y =
			screen_hW2 - 2.8f + 15.2f;
	public static final float HUD_DISPLAY_A = 0.80f;
	public static final float HUD_COUNTER_HEALTH_W = 3.0f;
	public static final float HUD_COUNTER_HEALTH_H = 0.78f;
	public static final float HUD_COUNTER_HEALTH_POS_X =
			CENTER_CAMERA_X - 9f;
	public static final float HUD_COUNTER_HEALTH_POS_Y =
			screen_hW2 - 2.960f + 16.35f;
	public static final float HUD_COUNTER_HEALTH_A = 0.86f;
	public static final float HUD_COUNTER_BULLETS_W = 3.0f;
	public static final float HUD_COUNTER_BULLETS_H = 0.78f;
	public static final float HUD_COUNTER_BULLETS_POS_X =
			CENTER_CAMERA_X + 6f;
	public static final float HUD_COUNTER_BULLETS_POS_Y =
			screen_hW2 - 2.960f + 16.35f;
	public static final float HUD_COUNTER_BULLETS_A = 0.86f;
	public static final float BULLET_COUNT_INFLATER_LIMITED = 1.50f;
	public float resizer;
	public float startupCounter_Timer = 0;
	// set the height of the counter text
	public float startupCounter_Y;
	public float size_scaler = 1.0f;
	public float alpha_scaler = 0.9f;
	// angle of the health sprite when poweruping
	public float hud_health_angle;
	// same
	public float hud_bullets_angle;
	public float angle_torot;
	public float destroyer_firster;
	//this is for what the game is into (x/numbertraps) + (biggest_angle-had) + ammo + life
	public int calculator_life;
	public int calculator_life2;
	public int calculator_life3;
	public int calculator_ammo;
	public int calculator_ammo2;
	public int calculator_ammo3;
	public int counter_electrotrap;
	public int counter_maxelectrotrap = 0;
	// in settings we give 10 ammo blocks
	public int counter_ammo_base;
	// is 5 in the beginning
	public int counter_ammo_fiver;
	// in settings we give 10 life slots
	public int counter_life_base;
	// is 10 in the beginning
	public int counter_life_fiver;
	// used to get CDTFS_string from startupCounterList
	public int startupCounter_ArrayCount = 0;
	// how much health we have?
	public int currentHealthCounter = 10;
	// how much bullets we have?
	public int currentBulletsCounter = 5;
	// we must not overextend the TR
	public int currentCounterMaxHealth = 10;
	// we must not overextend the TR
	public int currentCounterMaxBullets = 5;
	public int etrap_counter_for_bullets = 0;
	public int Bscreen_sizew;
	public int Bscreen_sizeh;
	public int screen_sizew2;
	public int screen_sizeh2;
	// string currently displayed by the startup cnt
	public String CDTFS_string;
	public String counter_ammo_txt;
	public String counter_life_txt;
	public String max_angle_txt;
	public String max_counter_txt;
	public boolean died_just_recently_give_me_last_lifeslot = false;
	public boolean need_to_change_hud_data = false;
	public boolean need_to_change_electrocounter = false;
	public boolean need_to_change_max_angle = false;
	public boolean need_to_change_ammo = false;
	public boolean need_to_change_life = false;
	// process only if we haven't finished
	public boolean hasStartupCounter = false;
	// perform rotation only if we're not already doing it
	public boolean not_finished_rotating_health = false;
	// so we don't get rotation well over power-uping
	public boolean not_finished_rotating_bullets = false;
	// used by the upper classes for updates and renders
	public boolean startTimerFinished = false;
	public double current_max_angle = 00.00f;
	public Table rotative_counter_table;
	public Table rotative_counter_table_angle;
	public Table table_counter_angles_text;
	public Table table_life_bullets_text;
	public Table table_fps;
	// texture region with powerups table
	public TextureRegion hud_display_powerupsTR;
	// health sprite icon  > Hud_Health_Bullets_Icons_Init
	public TextureRegion hud_healthTR;
	// bullets sprite icon > Hud_Health_Bullets_Icons_Init
	public TextureRegion hud_bulletsTR;
	public TextureRegionDrawable drawable;
	// texture region with counter for health and bullets
	public TextureRegion[] hud_counter = new TextureRegion[11];
	// texture region with counter for health and bullets
	public TextureRegion[] hud_counterb= new TextureRegion[6];
	public TextureRegion circle_start_tr;
	public TextButton rotative_counter;
	public TextButton rotative_angle40;
	public TextButton life_button;
	public TextButton bullets_button;
	public TextButton counter_button;
	public TextButton angles_button;
	public TextButton fps_button;
	public TextButtonStyle counter_style;
	public TextButtonStyle counter_style_angle;
	public Stage stage_rotative_counter;
	public Stage stage_life_bullets_text;
	public Stage stage_fps;
	// sprite with the above powerup table
	public Sprite hud_display_powerupsSprite;
	// health sprite
	public Sprite hud_healthSprite;
	// bullets sprite
	public Sprite hud_bulletsSprite;
	// the sprite with the health counter
	public Sprite hud_counter_healthSprite;
	// the sprite with the bullets counter
	public Sprite hud_counter_bulletsSprite;
	public Texture circle_start_t;
	// array containing the startup counter texts
	public ArrayList<String> startupCounterList;

	public void Hud_Fonts_Display_Reload(Skin skin){
		counter_style.font = skin.getFont("hud_counter_font");				
		rotative_counter.setStyle(counter_style);
	}
	
	public void Hud_Fonts_Display(Skin skin, Viewport viewport){	
		resizer = ((float)VIRTUAL_SCREEN_WIDTH/(float)VIRTUAL_SCREEN_HEIGHT) /
				  ((float)screen_sizew/(float)screen_sizeh);
		stage_rotative_counter = new Stage(viewport);		
		rotative_counter_table = new Table();
		circle_start_t = new
				Texture(Gdx.files.internal("graphics/size01big/startupcircle.png"));
		circle_start_t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		circle_start_tr = new TextureRegion(circle_start_t);


		counter_style = new TextButtonStyle();
		counter_style.font = skin.getFont("hud_counter_font");
		//hud_life_bullets_font
		counter_style.fontColor = Color.CYAN;
		counter_style.up = new TextureRegionDrawable(circle_start_tr);			
		rotative_counter = new TextButton("3", counter_style);
		rotative_counter.getLabelCell();
		rotative_counter.pack();
		rotative_counter_table.add(rotative_counter)
				.width(VIRTUAL_SCREEN_WIDTH235RESIZER)
				.height(VIRTUAL_SCREEN_WIDTH / 2.35f);
		rotative_counter_table.addAction(Actions.alpha(0.750f));


		counter_style_angle = new TextButtonStyle();
		counter_style_angle.font = skin_replay.getFont("errors-font");
		//hud_life_bullets_font
		counter_style_angle.fontColor = Color.GOLD;
		rotative_angle40 = new TextButton("Unblock all blockers at an \n" +
				"angle smaller than 40 degrees \nto clear the next level !",
				counter_style_angle);
		rotative_angle40.getLabelCell();
		rotative_angle40.pack();
		rotative_counter_table_angle = new Table();
		rotative_counter_table_angle.add(rotative_angle40)
				.width(VIRTUAL_SCREEN_WIDTH235RESIZER/4)
				.height(VIRTUAL_SCREEN_WIDTH / 2.35f).padBottom(VIRTUAL_SCREEN_WIDTH);
		rotative_counter_table_angle.addAction(Actions.alpha(0.700f));


		stage_rotative_counter.addActor(rotative_counter_table);
		stage_rotative_counter.addActor(rotative_counter_table_angle);
		rotative_counter_table.setFillParent(true);
		rotative_counter_table_angle.setFillParent(true);
	}
	
	public void Hud_Health_Bullets_Icons_Init(TextureAtlas atlas){
		hud_healthTR = atlas.findRegion("health00");
		hud_healthSprite = new Sprite(hud_healthTR);
		hud_healthSprite.setSize(HUD_HEALTH_SPRITE_W, HUD_HEALTH_SPRITE_H);
		hud_healthSprite.setPosition(HUD_HEALTH_SPRITE_POS_X, HUD_HEALTH_SPRITE_POS_Y);
		hud_healthSprite.setAlpha(HUD_HEALTH_SPRITE_A);
		hud_healthSprite.setOrigin(
				hud_healthSprite.getWidth() / 2f,
				hud_healthSprite.getHeight() / 2f);
		hud_bulletsTR = atlas.findRegion("bullets00");
		hud_bulletsSprite = new Sprite(hud_bulletsTR);
		hud_bulletsSprite.setSize(HUD_BULLETS_SPRITE_W, HUD_BULLETS_SPRITE_H);
		hud_bulletsSprite.setPosition(HUD_BULLETS_SPRITE_POS_X, HUD_BULLETS_SPRITE_POS_Y);
		hud_bulletsSprite.setAlpha(HUD_BULLETS_SPRITE_A);
		hud_bulletsSprite.setOrigin(
				hud_bulletsSprite.getWidth() / 2f,
				hud_bulletsSprite.getHeight() / 2f);
	}	
	
	public void Hud_Health_Bullets_Icons_Reload(TextureAtlas atlas){
		hud_healthTR = atlas.findRegion("health00");
		hud_healthSprite.setRegion(hud_healthTR);
		hud_bulletsTR = atlas.findRegion("bullets00");
		hud_bulletsSprite.setRegion(hud_bulletsTR);
	}	
	
	//table init used to display the health and bullets (sprite)
	public void Hud_Up_Init(TextureAtlas atlas){
		hud_display_powerupsTR = atlas.findRegion("hudup");
		hud_display_powerupsSprite = new Sprite(hud_display_powerupsTR);
		hud_display_powerupsSprite.setSize(
				HUD_DISPLAY_POWERUPS_SPRITE_W,
				HUD_DISPLAY_POWERUPS_SPRITE_H);
		hud_display_powerupsSprite.setPosition(
				HUD_DISPLAY_POWERUPS_SPRITE_POS_X,
				HUD_DISPLAY_POWERUPS_SPRITE_POS_Y);
		hud_display_powerupsSprite.setAlpha(HUD_DISPLAY_A);
	}

	//table INIT used to display the health and bullets (sprite)
	public void Hud_Up_Reload(TextureAtlas atlas){
		hud_display_powerupsTR = atlas.findRegion("hudup");
		hud_display_powerupsSprite.setRegion(hud_display_powerupsTR);
	}

	//init the bullets and health counters
	public void Hud_Counter_Reload(TextureAtlas atlas){
		hud_counter[0] = atlas.findRegion("hud_counter0");
		hud_counter[1] = atlas.findRegion("hud_counter1");	
		hud_counter[2] = atlas.findRegion("hud_counter2");	
		hud_counter[3] = atlas.findRegion("hud_counter3");	
		hud_counter[4] = atlas.findRegion("hud_counter4");
		hud_counter[5] = atlas.findRegion("hud_counter5");
		hud_counter[6] = atlas.findRegion("hud_counter10");
		hud_counter[7] = atlas.findRegion("hud_counter9");	
		hud_counter[8] = atlas.findRegion("hud_counter8");	
		hud_counter[9] = atlas.findRegion("hud_counter7");	
		hud_counter[10] = atlas.findRegion("hud_counter6");
		hud_counterb[0] = atlas.findRegion("hud_counter0");
		hud_counterb[1] = atlas.findRegion("hud_counter10");
		hud_counterb[2] = atlas.findRegion("hud_counter9");
		hud_counterb[3] = atlas.findRegion("hud_counter8");
		hud_counterb[4] = atlas.findRegion("hud_counter7");
		hud_counterb[5] = atlas.findRegion("hud_counter6");
		hud_counter_healthSprite.setRegion(hud_counter[10]);
		hud_counter_bulletsSprite.setRegion(hud_counterb[5]);		
	}

	// INIT the bullets and health counters
	public void Hud_Counter_Init(TextureAtlas atlas){
		hud_counter[0] = atlas.findRegion("hud_counter0");
		hud_counter[1] = atlas.findRegion("hud_counter1");	
		hud_counter[2] = atlas.findRegion("hud_counter2");	
		hud_counter[3] = atlas.findRegion("hud_counter3");	
		hud_counter[4] = atlas.findRegion("hud_counter4");
		hud_counter[5] = atlas.findRegion("hud_counter5");
		hud_counter[6] = atlas.findRegion("hud_counter10");
		hud_counter[7] = atlas.findRegion("hud_counter9");	
		hud_counter[8] = atlas.findRegion("hud_counter8");	
		hud_counter[9] = atlas.findRegion("hud_counter7");	
		hud_counter[10] = atlas.findRegion("hud_counter6");
		hud_counterb[0] = atlas.findRegion("hud_counter0");
		hud_counterb[1] = atlas.findRegion("hud_counter10");
		hud_counterb[2] = atlas.findRegion("hud_counter9");
		hud_counterb[3] = atlas.findRegion("hud_counter8");
		hud_counterb[4] = atlas.findRegion("hud_counter7");
		hud_counterb[5] = atlas.findRegion("hud_counter6");
		hud_counter_healthSprite = new Sprite(hud_counter[10]);
		hud_counter_healthSprite.setSize(
				HUD_COUNTER_HEALTH_W,
				HUD_COUNTER_HEALTH_H);
		hud_counter_healthSprite.setPosition(
				HUD_COUNTER_HEALTH_POS_X,
				HUD_COUNTER_HEALTH_POS_Y);
		hud_counter_healthSprite.setAlpha(HUD_COUNTER_HEALTH_A);
		hud_counter_bulletsSprite = new Sprite(hud_counterb[5]);
		hud_counter_bulletsSprite.setSize(
				HUD_COUNTER_BULLETS_W,
				HUD_COUNTER_BULLETS_H);
		hud_counter_bulletsSprite.setPosition(
				HUD_COUNTER_BULLETS_POS_X,
				HUD_COUNTER_BULLETS_POS_Y);
		hud_counter_bulletsSprite.setAlpha(HUD_COUNTER_BULLETS_A);				
	}

	// HUD counter for electron-trap and max angle in the current level
	public void Hud_StageAngleCounter(Skin skin){
		table_counter_angles_text = new Table();
		counter_button = new TextButton(max_counter_txt, skin, "counter_have_hud_down");
		angles_button = new TextButton(max_angle_txt, skin, "angles_have_hud_down");
		counter_button.getLabelCell()
				.padTop(VIRTUAL_SCREEN_WIDTH / 14.770f)
				.padLeft(-VIRTUAL_SCREEN_WIDTH / 435f);
		angles_button.getLabelCell()
				.padTop(VIRTUAL_SCREEN_WIDTH / 14.769f)
				.padLeft(-VIRTUAL_SCREEN_WIDTH / 435f);
		table_counter_angles_text.add(counter_button)
				.width(VIRTUAL_SCREEN_WIDTH / 3.9f)
				.height(VIRTUAL_SCREEN_WIDTH / 3.9f * 115 / 128f)
				.padRight(VIRTUAL_SCREEN_WIDTH / 4.35f);
		table_counter_angles_text.add(angles_button)
				.width(VIRTUAL_SCREEN_WIDTH / 3.9f)
				.height(VIRTUAL_SCREEN_WIDTH / 3.9f * 115f / 128f)
				.padLeft(VIRTUAL_SCREEN_WIDTH / 4.35f);
		stage_life_bullets_text.addActor(table_counter_angles_text);
		table_counter_angles_text.bottom().padTop(VIRTUAL_SCREEN_WIDTH / 9.125f);
		table_counter_angles_text.addAction(Actions.alpha(1.0f));
		table_counter_angles_text.setFillParent(true);
		angles_button.setText("0.0" + ex01Types.DEG);
	}

	// HUD counter for electron-trap and max angle in the current level
	public void Hud_StageAngleCounterReload(Skin skin){
		counter_button.setStyle(skin.get("counter_have_hud_down", TextButtonStyle.class));
		angles_button.setStyle(skin.get("angles_have_hud_down", TextButtonStyle.class));
	}	
	
	public void Hud_StageLifeBullets(Skin skin, Viewport viewport){
		stage_life_bullets_text = new Stage(viewport);
		table_life_bullets_text = new Table();
		life_button = new TextButton(counter_life_txt, skin, "life_have_hud");
		bullets_button = new TextButton(counter_ammo_txt, skin, "bullets_have_hud");
		life_button.getLabelCell().expandX().padTop(VIRTUAL_SCREEN_WIDTH/38.475f);
		bullets_button.getLabelCell().expandX().padTop(VIRTUAL_SCREEN_WIDTH/38.475f);
		table_life_bullets_text.add(life_button)
				.width(VIRTUAL_SCREEN_WIDTH / 3.95f)
				.height(VIRTUAL_SCREEN_WIDTH / 4.25f * 69f / 150f)
				.padRight(VIRTUAL_SCREEN_WIDTH / 4.2f);
		table_life_bullets_text.add(bullets_button)
				.width(VIRTUAL_SCREEN_WIDTH / 3.95f)
				.height(VIRTUAL_SCREEN_WIDTH / 4.25f * 69f / 150f)
				.padLeft(VIRTUAL_SCREEN_WIDTH / 4.2f);
		stage_life_bullets_text.addActor(table_life_bullets_text);
		table_life_bullets_text.top().padTop(VIRTUAL_SCREEN_WIDTH/11.100f);
		table_life_bullets_text.addAction(Actions.alpha(0.875f));
		table_life_bullets_text.setFillParent(true);
	}
	
	public void Hud_StageLifeBulletsReload(Skin skin, Viewport viewport){		
		life_button.setStyle(skin.get("life_have_hud", TextButtonStyle.class));
		bullets_button.setStyle(skin.get("bullets_have_hud", TextButtonStyle.class));
	}

	public Texture fps;
	public TextButtonStyle fps_style;
	
	public void Hud_FPSLeft(Skin skin, Viewport viewport){
		stage_fps = new Stage(viewport);
		table_fps = new Table();
		fps = new Texture(Gdx.files.internal("graphics/size01big/fps4mic.png"));
		fps_style = new TextButtonStyle();
		fps_style.up = new TextureRegionDrawable(new TextureRegion(fps));
		fps_style.font = skin.getFont("hud_fps_font");
		fps_button = new TextButton("fps", fps_style);
		fps_button.getLabelCell().padTop(VIRTUAL_SCREEN_WIDTH / 115.500f);
		table_fps.add(fps_button)
				.width(VIRTUAL_SCREEN_WIDTH / 3.75f)
				.height(VIRTUAL_SCREEN_WIDTH / 3.75f * 38f / 96f);
		table_fps.top().left()
				.padTop(VIRTUAL_SCREEN_WIDTH / 3.700f)
				.padLeft(VIRTUAL_SCREEN_WIDTH / 125.0f);
		table_fps.addAction(Actions.alpha(0.575f));
		table_fps.setFillParent(true);
		stage_fps.addActor(table_fps);
	}	
	
	public void Hud_FPSLeftReload(Skin skin){
		fps_style.font = skin.getFont("hud_fps_font");
		fps_button.setStyle(fps_style);
	}

	//set health counter to specific frame
	public void SetHealthCounterTo(int frame){
		hud_counter_healthSprite.setRegion(hud_counter[frame]);
	}

	//set bullets counter to specific frame
	public void SetBulletsCounterTo(int frame){
		hud_counter_bulletsSprite.setRegion(hud_counterb[frame]);
	}

	//advance bullets counter by one
	public void AdvanceBulletsCounter(float val){
		currentBulletsCounter+=val;
		counter_ammo_fiver+=val;
		need_to_change_ammo = true;
		need_to_change_hud_data = true;				
		if(currentBulletsCounter>currentCounterMaxBullets) {
			currentBulletsCounter = currentBulletsCounter - currentCounterMaxBullets;
			counter_ammo_fiver = currentBulletsCounter;
			counter_ammo_base = counter_ammo_base + 1;
		}
		SetBulletsCounterTo(currentBulletsCounter);
	}

	//increase health counter by one
	public void AdvanceHealthCounter(float val){
		currentHealthCounter+=val;
		counter_life_fiver+=val;
		need_to_change_life = true;
		need_to_change_hud_data = true;
		if(currentHealthCounter>currentCounterMaxHealth) {
			currentHealthCounter = currentCounterMaxHealth;
			counter_life_fiver = currentCounterMaxHealth;
		}
		SetHealthCounterTo(currentHealthCounter);
	}

	//recede bullets counter by one
	public void RecedeBulletsCounter(){
		currentBulletsCounter--;
		counter_ammo_fiver--;
		need_to_change_ammo = true;
		need_to_change_hud_data = true;
		if(currentBulletsCounter==0) {
			if(counter_ammo_base>0){
				currentBulletsCounter = 5;
				counter_ammo_fiver = 5;
				counter_ammo_base -= 1;
			} else {
				currentBulletsCounter = 0;
				counter_ammo_fiver = 0;
				the_game_screen.spaces.no_more_bullets = true;
				setts_for_game.settings.were_currently_limited_on_ammo = true;
			}			
		}
		if(currentBulletsCounter>=0)
			SetBulletsCounterTo(currentBulletsCounter);
	}

	//recede health counter by one
	public void RecedeHealthCounter(){
		currentHealthCounter--;
		counter_life_fiver--;
		need_to_change_life = true;
		need_to_change_hud_data = true;
		if(currentHealthCounter==-1) {
			the_game_screen.spaces.can_explode = true;
			currentHealthCounter = 0;
			counter_life_fiver = 0;
			if(counter_life_base>0) counter_life_base -= 1;
		}
		SetHealthCounterTo(currentHealthCounter);	
	}
	
	public void ProcessDeathByCollision(){
		counter_life_fiver = 0;
		currentHealthCounter = 0;
		counter_life_txt = Integer
				.toString(counter_life_base) + "X" + Integer.toString(counter_life_fiver);
		life_button.setText(counter_life_txt);
		SetHealthCounterTo(currentHealthCounter);
		if(counter_life_base>=0) counter_life_base -= 1;
		if(counter_life_base<0){
			setts_for_game.settings.last_ammo_fiver_no = counter_ammo_fiver;
			setts_for_game.settings.were_currently_limited_on_life = true;
		}
	}

	//rotate powerups sprites
	public void StartHealthRotationProcedure(ex01CryoshipPrincipial spaces, float val){
		if(!not_finished_rotating_health){
			not_finished_rotating_health = true;
			hud_health_angle = 0;
		}
		spaces.powerup_health_hit.stop(); spaces.powerup_health_hit.play(0.1f);
		AdvanceHealthCounter(val);
	}

	//rotate powerups sprites
	public void StartBulletsRotationProcedure(ex01CryoshipPrincipial spaces, float val){
		if(!not_finished_rotating_bullets){
			not_finished_rotating_bullets = true;
			hud_bullets_angle = 0;
		}
		spaces.powerup_bullets_hit.stop(); spaces.powerup_bullets_hit.play(0.1f);
		AdvanceBulletsCounter(val);
	}	
	
	//render table , power-ups values (counters) and sprites
	public void Render(SpriteBatch batch, float delta){
		hud_display_powerupsSprite.draw(batch);
		hud_counter_healthSprite.draw(batch);
		hud_counter_bulletsSprite.draw(batch);
		hud_healthSprite.draw(batch);
		hud_bulletsSprite.draw(batch);
	}
	
	//INIT the startup counter variables
	public void InitDisplay(ArrayList<String> Display){
		startupCounterList = new ArrayList<String>(Display);
		startupCounter_Timer = 0;
		startupCounter_ArrayCount = 0;
		hasStartupCounter = true;
		startupCounter_Y = 22f;
	}
	
	//INIT the startup counter variables
	public void InitDisplayReload(){
		startupCounter_Timer = 0;
		startupCounter_ArrayCount = 0;
		hasStartupCounter = true;
	}	
	
	// update the startup counter TEXT from CDTFS_string, and also ...
	public void UpdateStartupCounterFUD(float delta){
		if(hasStartupCounter){
			ProcessCurrentDisplayFullScreen(delta);
		}
	}	
	
	public void DisappearHUDOnFail(){
		table_life_bullets_text.clearActions();
		table_counter_angles_text.clearActions();
		table_fps.clearActions();
		table_life_bullets_text.addAction(Actions.alpha(0.875f));
		table_counter_angles_text.addAction(Actions.fadeIn(0f));
		table_fps.addAction(Actions.alpha(0.575f));
	}
	
	//we get bullets or health we rotate
	public void UpdateHealthBulletsRotation(float delta){
		if(not_finished_rotating_health){
			angle_torot = delta * HUD_ICON_ROT_SPEED;
			hud_health_angle += angle_torot;
			if(hud_health_angle >= 360f){
				hud_health_angle = 0f;
				not_finished_rotating_health = false;
				hud_healthSprite.setRotation(hud_health_angle);
			} else {
				hud_healthSprite.rotate(angle_torot);
			}
		}
		if(not_finished_rotating_bullets){
			angle_torot = delta * HUD_ICON_ROT_SPEED;
			hud_bullets_angle += angle_torot;
			if(hud_bullets_angle >= 360f){
				hud_bullets_angle = 0f;
				not_finished_rotating_bullets = false;
				hud_bulletsSprite.setRotation(hud_bullets_angle);
			} else {
				hud_bulletsSprite.rotate(angle_torot);
			}			
		}		
	}


	//this processes the string counter from startup
	public void ProcessCurrentDisplayFullScreen(float delta){
		startupCounter_Timer += delta;
		CDTFS_string = startupCounterList.get(startupCounter_ArrayCount);	
		alpha_scaler -= 0.02f;
		destroyer_firster = (startupCounter_ArrayCount==0) ?
				            (FULL_SCREEN_LIST_DESTROYER*1f):
				  			(FULL_SCREEN_LIST_DESTROYER);
		if(startupCounter_Timer > destroyer_firster){
			startupCounter_ArrayCount++;
			size_scaler = 1.0f;
			alpha_scaler = 0.9f;
			startupCounter_Timer = 0;
			if(startupCounter_ArrayCount >= startupCounterList.size()){
				hasStartupCounter = false;
				startTimerFinished = true;
			}
		}
	}	
	
	public void LoadAmmoLifePostProcess(){		
		SetHealthCounterTo(currentHealthCounter);
		SetBulletsCounterTo(currentBulletsCounter);		
	}
	
	public void LoadAmmoLifeFromSettings(_ex01CryotraxGame game_screen,
										 ex01JSONSettingsLoader setts,
										 int electrontrap_number){
		//counter_shoot is the counter for each 3xshot=bullet (made of 3 shoots)
		game_screen.spaces.counter_shoot = setts.settings.counter_bullets_fired;
		//need to check for < were_currently_limited_on_life >  and
		if(setts.settings.were_currently_limited_on_ammo){
			etrap_counter_for_bullets =
					game_screen_base.world_electrotrap_list.electro_red_list.size();
			etrap_counter_for_bullets *= BULLET_COUNT_INFLATER_LIMITED;
			counter_ammo_base = etrap_counter_for_bullets/5;
			counter_ammo_fiver = etrap_counter_for_bullets%5;
			if(counter_ammo_fiver == 0){
				counter_ammo_fiver = 5;
				counter_ammo_base -= 1;
			}
			currentBulletsCounter = counter_ammo_fiver;
		} else {
			counter_ammo_base = setts.settings.counter_ammo_base;
			counter_ammo_fiver = currentBulletsCounter = setts.settings.counter_ammo_fiver;
			if(counter_ammo_base>0){
				currentBulletsCounter = json_settings.COUNTER_AMMO_FIVER;
				counter_ammo_fiver = json_settings.COUNTER_AMMO_FIVER;
			} else {
				currentBulletsCounter = setts.settings.last_ammo_fiver_no;
				counter_ammo_fiver = setts.settings.last_ammo_fiver_no;
			}				
		}
		if(setts.settings.were_currently_limited_on_life){
			counter_life_fiver = currentHealthCounter = setts.settings.counter_life_fiver_limited;
			counter_life_base = setts.settings.counter_life_base_limited;
		} else {
			died_just_recently_give_me_last_lifeslot = true;
			counter_life_base = setts.settings.counter_life_base;
			counter_life_fiver = currentHealthCounter = setts.settings.counter_life_fiver;			
			if(counter_life_base>0){
				currentHealthCounter = json_settings.COUNTER_LIFE_FIVER;
				counter_life_fiver = json_settings.COUNTER_LIFE_FIVER;
			} else {
				currentHealthCounter = setts.settings.last_life_fiver_no;
				counter_life_fiver = setts.settings.last_life_fiver_no;
			}				
		}
		counter_electrotrap = 0;
		counter_maxelectrotrap = electrontrap_number;
		max_counter_txt = Integer
				.toString(counter_electrotrap) + ex01Types.OF +
				Integer.toString(counter_maxelectrotrap);
		counter_ammo_txt = Integer
				.toString(counter_ammo_base) + ex01Types.X + Integer.toString(counter_ammo_fiver);
		counter_life_txt = Integer
				.toString(counter_life_base) + ex01Types.X + Integer.toString(counter_life_fiver);
	}

	public void CheckToSeeIfLessAmmoThanMinimum(ex01JSONSettingsLoader setts){
		calculator_ammo = game_screen_base.world_electrotrap_list.electro_red_list.size();
		calculator_ammo *= BULLET_COUNT_INFLATER_LIMITED;
		calculator_ammo2 = calculator_ammo/5;
		calculator_ammo3 = calculator_ammo%5;
		// if what is needed is bigger than what it is 
		if((calculator_ammo2*5 + calculator_ammo3) >
				(setts.settings.counter_ammo_base*5+setts.settings.counter_ammo_fiver)){
			setts.settings.were_currently_limited_on_ammo = true;
		}
	}

	public void CheckToSeeIfLessLifeThanMinimum(ex01JSONSettingsLoader setts){
		calculator_life = game_screen_base.world_electrotrap_list.electro_red_list.size();
		calculator_life *= BULLET_COUNT_INFLATER_LIMITED;
		calculator_life2 = calculator_life/5;
		calculator_life3 = calculator_life%5;
		if((5) > ((counter_life_base+1)*5+counter_life_fiver)){
			setts.settings.were_currently_limited_on_life = true;
		}
	}
	
	public void ResetAmmoLifeFromSettings(_ex01CryotraxGame game_screen,
										  ex01JSONSettingsLoader setts){
		buy_ammo_table.addAction(Actions.alpha(0f));
		hud_display.buy_life_table.addAction(Actions.alpha(0f));
		hud_display.level_base_spacecraft_you_own_error.addAction(Actions.alpha(0f));
		hud_display.level_base_spacecraft_error.addAction(Actions.alpha(0f));
		hud_display.get_coinswon_table_error.addAction(Actions.alpha(0f));
		hud_display.buy_ammo_warning_table.clearActions();
		hud_display.buy_life_warning_table.clearActions();
		hud_display.buy_ammo_warning_table.addAction(Actions.fadeOut(0f));
		hud_display.buy_life_warning_table.addAction(Actions.fadeOut(0f));
		//died_just_recently_give_me_last_lifeslot = false;
		//counter_shoot is the counter for each 3xshot=bullet (made of 3 shoots)
		game_screen.spaces.counter_shoot = setts.settings.counter_bullets_fired;
		
		//calculate if the current ammo is less than the minimum - if yes we
		//   set < setts.settings.were_currently_limited_on_ammo > to true
		CheckToSeeIfLessAmmoThanMinimum(setts);
		CheckToSeeIfLessLifeThanMinimum(setts);

		//need to check for  < were_currently_limited_on_life > and
		//   < were_currently_limited_on_ammo >
		if(setts.settings.were_currently_limited_on_ammo){
			etrap_counter_for_bullets =
					game_screen_base.world_electrotrap_list.electro_red_list.size();
			etrap_counter_for_bullets *= BULLET_COUNT_INFLATER_LIMITED;
			counter_ammo_base = etrap_counter_for_bullets/5;
			counter_ammo_fiver = etrap_counter_for_bullets%5;
			if(counter_ammo_fiver == 0){
				counter_ammo_fiver = 5;
				counter_ammo_base -= 1;
			}
			currentBulletsCounter = counter_ammo_fiver;
		} else {
			counter_ammo_base = setts.settings.counter_ammo_base;
			counter_ammo_fiver = currentBulletsCounter = setts.settings.counter_ammo_fiver;
			if(counter_ammo_base>0){
				currentBulletsCounter = json_settings.COUNTER_AMMO_FIVER;
				counter_ammo_fiver = json_settings.COUNTER_AMMO_FIVER;
			} else {
				currentBulletsCounter = setts.settings.last_ammo_fiver_no;
				counter_ammo_fiver = setts.settings.last_ammo_fiver_no;
			}				
		}
		
		if(setts.settings.were_currently_limited_on_life){
			counter_life_fiver = currentHealthCounter = setts.settings.counter_life_fiver_limited;
			counter_life_base = setts.settings.counter_life_base_limited;
		} else {
			counter_life_base = setts.settings.counter_life_base;
			counter_life_fiver = currentHealthCounter = setts.settings.counter_life_fiver;			
			if(counter_life_base>0){

				currentHealthCounter = json_settings.COUNTER_LIFE_FIVER;
				counter_life_fiver = json_settings.COUNTER_LIFE_FIVER;
			} else {
				if(!died_just_recently_give_me_last_lifeslot){
					currentHealthCounter = setts.settings.last_life_fiver_no;
					counter_life_fiver = setts.settings.last_life_fiver_no;					
				} else {

					currentHealthCounter = json_settings.COUNTER_LIFE_FIVER;
					counter_life_fiver = json_settings.COUNTER_LIFE_FIVER;				
				}
			}				
		}
		counter_electrotrap = 0;
		current_max_angle = 00.00f;
		max_counter_txt = Integer
				.toString(counter_electrotrap) + ex01Types.OF +
				Integer.toString(counter_maxelectrotrap);
		max_angle_txt = Double.toString(current_max_angle);
		counter_ammo_txt = Integer
				.toString(counter_ammo_base) + ex01Types.X + Integer.toString(counter_ammo_fiver);
		counter_life_txt = Integer
				.toString(counter_life_base) + ex01Types.X + Integer.toString(counter_life_fiver);
		bullets_button.setText(counter_ammo_txt);
		life_button.setText(counter_life_txt);
		counter_button.setText(max_counter_txt);
		angles_button.setText(max_angle_txt + ex01Types.DEG);
		SetHealthCounterTo(currentHealthCounter);
		SetBulletsCounterTo(currentBulletsCounter);
	}		
	
	public void ProcessHudData(){
		if(need_to_change_hud_data){
			if(need_to_change_electrocounter){
				max_counter_txt = Integer.toString(counter_electrotrap) + ex01Types.OF +
						Integer.toString(counter_maxelectrotrap);
				counter_button.setText(max_counter_txt);
				need_to_change_electrocounter = false;
			}
			if(need_to_change_max_angle){
				max_angle_txt = decimal_transform.format(current_max_angle);
				angles_button.setText(max_angle_txt + ex01Types.DEG);
				need_to_change_max_angle = false;
			}
			if(need_to_change_ammo){
				counter_ammo_txt = Integer.toString(counter_ammo_base) + ex01Types.X +
						Integer.toString(counter_ammo_fiver);
				bullets_button.setText(counter_ammo_txt);
				need_to_change_ammo = false;
			}
			if(need_to_change_life){
				counter_life_txt = Integer.toString(counter_life_base) + ex01Types.X +
						Integer.toString(counter_life_fiver);
				life_button.setText(counter_life_txt);
				need_to_change_life = false;
			}
			need_to_change_hud_data = false;
		}
	}	

	public void HudStageRender(float delta){
		HudStageUpdate(delta);
		stage_life_bullets_text.draw();		
	}	
	
	public void HudFPSStageRender(String data){
		fps_button.setText(data);
	}
	public void HudStageUpdate(float delta){
		stage_life_bullets_text.act(delta*SPEED_HUD);
	}
	public void Dispose(){
		if(startupCounterList!=null )startupCounterList.clear();	
	}	
	
	//update bullets rotation and replay stage when we die or pause - WHEN WE PAUSE 
	public void UpdateFUDDisplayScene2DFree(float delta){
		UpdateHealthBulletsRotation(delta);
		if(added) { // when we pauses we added the replay/resume pause dialog
			UpdateFUDDisplay(delta);
		}
	}		

	public void DisposeHUDDisplay_HudMainScreen(){
		rotative_counter_table.clear();
		table_counter_angles_text.clear();
		table_life_bullets_text.clear();
		table_fps.clear();
		rotative_counter.clear();
		life_button.clear();
		bullets_button.clear();
		counter_button.clear();
		angles_button.clear();
		fps_button.clear();
		stage_rotative_counter.clear();
		stage_life_bullets_text.clear();
		stage_fps.clear();

		CDTFS_string = null; 	           // string currently displayed by the startup counter
		counter_ammo_txt = null;
		counter_life_txt = null;
		max_angle_txt = null;
		max_counter_txt = null;
		rotative_counter_table = null;
		rotative_counter_table_angle = null;
		table_counter_angles_text = null;
		table_life_bullets_text = null;
		table_fps = null;
		hud_display_powerupsTR = null;     // texture region with powerups table
		hud_healthTR = null;               // health sprite icon  > Hud_Health_Bullets_Icons_Init
		hud_bulletsTR = null;              // bullets sprite icon > Hud_Health_Bullets_Icons_Init
		drawable = null;
		for(int i=0; i<hud_counter.length; i++){
			hud_counter[i] = null;
		}
		for(int i=0; i<hud_counterb.length; i++){
			hud_counterb[i] = null;
		}
		circle_start_tr = null;
		rotative_counter = null;
		rotative_angle40 = null;
		life_button = null;
		bullets_button = null;
		counter_button = null;
		angles_button = null;
		fps_button = null;
		if(fps!=null){
			fps.dispose();
			fps = null;
		}
		fps_style = null;
		counter_style = null;
		counter_style_angle = null;
		if(stage_rotative_counter!=null)
			stage_rotative_counter.dispose(); stage_rotative_counter = null;
		if(stage_life_bullets_text!=null)
			stage_life_bullets_text.dispose(); stage_life_bullets_text = null;
		if(stage_fps!=null) stage_fps.dispose(); stage_fps = null;
		hud_display_powerupsSprite = null;
		hud_healthSprite = null;
		hud_bulletsSprite = null;
		hud_counter_healthSprite = null;
		hud_counter_bulletsSprite = null;
		if(circle_start_t!=null) circle_start_t.dispose(); circle_start_t = null;
		startupCounterList.clear();
	}

	// HUD ON MAIN SCREEN WHILE GAME IS PLAYING		
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/

	
	
	//--------------------------------------------------------------------------------------------//
	//--------------------------------------------------------------------------------------------//
	//--------------------------------------------------------------------------------------------//



	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	// resume/replay dialog - Scene2D - when we die HUD (replay, stars, points, exit, etc)
	// this is the max angle <= in order to unlock the next level - all blockers must be
	// unlocked at least by 40f degrees
	public static final float MAX_ANGLE_TO_UNLOCK_NEXT_LEVEL = 40f;
	public static final float SOUND_LEFT_RIGHT_VOLUME = 0.05f;
	public static final float DONE_BUTTON_VOLUME = 0.025f;
	public Table table_general_pause;
	public Table table_replay_resume;
	public Table table_main_menu;
	public Table image_pause_table;
	public Table get_coinswon_table_error;
	public Table level_base_coinswon_t_error;
	public Table get_spacecraft_table_error;
	public Table level_base_spacecraft_t_error;
	public Table level_go_left_right;
	public Table level_no_table;
	public Table table_general_pause_back;
	public Table table_angles_how_wedoing;
	public Table level_base_spacecraft_you_own_t_error;
	public Table buy_ammo_warning_table;
	public Table buy_life_warning_table;
	public Table buy_ammo_table;
	public Table buy_life_table;
	public Stack table_general_pause_stack;
	public Stack settings_get_coinswon_error;
	public Stack settings_get_spacecraft_error;
	public Stage stage;
	public ImageButton image_general_pause_back;
	public ImageButton restart_button;
	public ImageButton resume_button;
	public ImageButton main_menu_button;
	public ImageButton level_go_left;
	public ImageButton level_go_right;
	public ImageButton buy_ammo_button;
	public ImageButton buy_life_button;
	public ImageTextButton level_no_button;
	public ImageButtonStyle restart_style;
	public ImageButtonStyle resume_style;
	public ImageButtonStyle resumegr_style;
	public ImageButtonStyle buy_ammo_style;
	public ImageButtonStyle buy_life_style;
	public Image level_base_spacecraft_you_own_error;
	public Image buy_ammo_warning;
	public Image buy_life_warning;
	public CheckBox angles_how_wedoing1;
	public CheckBox angles_how_wedoing2;
	public CheckBox angles_how_wedoing3;
	public TextButton level_base_coinswon_error;
	public TextButton level_base_spacecraft_error;
	public TextButtonStyle textb1_coinswon;
	public TextButtonStyle textb2_coinswon;
	public TextButtonStyle textb3_coinswon;
	public TextButtonStyle textb4_coinswon;
	public TextButtonStyle textb1;
	public TextButtonStyle textb2ok;
	public TextButtonStyle textb3level;
	public TextButtonStyle textb4level;
	public TextButtonStyle textb5betterlevel;
	public TextButtonStyle textb6failedangle;
	public TextButtonStyle old_style;
	public CheckBox.CheckBoxStyle chk_style;
	public boolean added = false;                             
	public boolean showed = false;
	public boolean resume_button_is_grey = false;
	public boolean appeared_from_exploded_or_finished_pass = false;
	public boolean just_rendered_flip_flop_buy_ammolife = false;
	public boolean angle_was_better = false;
	public boolean can_rotate1_star_chk = false;
	public boolean can_rotate2_star_chk = false;
	public boolean can_rotate3_star_chk = false;
	public boolean added_render_VALUE = false;
	public int level_no = 1;
	public int primary_level_no = 1;
	// number of blockers de-blocked - used for next level de-blocking - all levels must de-blocked
	public int blocked_deblocked_counter = 0;
	public int current_coins_won = 0;
	public String level_name;
	public String old_level_string_error_info = new String();
    public Viewport viewport;
	public OrthographicCamera camera;
	public Runnable r_write_json;						
	public Thread t_write_json;
	//buy premium version
	public boolean can_switchstyles = false;
	public boolean can_render_unlock = false;
	public boolean still_working_on_menu_press = false;
	public boolean currently_rendering_load = false;
	public boolean currently_rendering_load_reset = false;
	public Table add_free_version_table;
	public TextureRegion add_free_reg;
	public TextureRegion add_free_regOff;
	public ImageButton add_free_version;
	public ImageButtonStyle styleon;
	public ImageButtonStyle styleoff;
	//unlock all levels
	public Table unlock_free_version_table;
	public TextureRegion unlock_free_reg;
	public ImageButton unlock_free_version;
	//other
	public boolean login_next_yes_no;
	public boolean check_next_for_login_success = false;
	public boolean isSomethingWrong = false;
	public boolean about_to_finish_reset = false;
	public boolean stillWorkingOnReset = false;
	public String text1;
	public String text;
	public boolean came_failed_from_HUD = false;
	public boolean bCurrentlyFadedOutDontDraw = false;
	public boolean possible_fade_in_add_free;
	public boolean possible_fade_in_unlock_free;
	public boolean possible_fade_in_saveload_free;
	public boolean login_is_success = false;

	public void DisposeBuyPremium(){
		if(add_free_version_table!=null) {
			add_free_version_table.clear();
			add_free_version_table = null;
		}
		if(add_free_version!=null) {
			add_free_version.clear();
			add_free_version = null;
		}
		styleon = null;
		styleoff = null;
		add_free_reg = null;
		timerForAdd = null;

		if(unlock_free_version_table!=null) {
			unlock_free_version_table.clear();
			unlock_free_version_table = null;
		}
		if(unlock_free_version!=null) {
			unlock_free_version.clear();
			unlock_free_version = null;
		}
		unlock_free_reg = null;

		timerForAdd = null;
		if(savegame_free_version_table!=null) {
			savegame_free_version_table.clear();
			savegame_free_version_table = null;
		}
		if(savegame_free_version!=null) {
			savegame_free_version.clear();
			savegame_free_version = null;
		}
		savegame_free_reg = null;

		//error and info stuff
		if(settings_get_premium_error!=null) {
			settings_get_premium_error.clear();
			settings_get_premium_error = null;
		}
		if(get_premium_table_error!=null) {
			get_premium_table_error.clear();
			get_premium_table_error = null;
		}
		if(level_base_premium_t_error!=null) {
			level_base_premium_t_error.clear();
			level_base_premium_t_error = null;
		}
		if(level_base_premium_error!=null) {
			level_base_premium_error.clear();
			level_base_premium_error = null;
		}
		if(level_base_premium_you_own_t_error!=null) {
			level_base_premium_you_own_t_error.clear();
			level_base_premium_you_own_t_error = null;
		}
		if(level_base_premium_you_own_error!=null) {
			level_base_premium_you_own_error.clear();
			level_base_premium_you_own_error = null;
		}
		if(level_base_premium_you_own_info!=null) {
			level_base_premium_you_own_info.clear();
			level_base_premium_you_own_info = null;
		}
		textb1x = null;
		textb2okx = null;
		text1 = null;
		text = null;
		message = null;
	}

	public void SwitchStyleForBuyPremium(){
		if(add_free_version.getStyle()==styleon){
			add_free_version.setStyle(styleoff);
		} else {
			add_free_version.setStyle(styleon);
		}
	}

	public void InitNotAddFreeVersion(){
		add_free_version_table = new Table();
		styleon = new ImageButtonStyle();
		styleoff = new ImageButtonStyle();
		add_free_reg = skin_replay.getAtlas().findRegion("getpremiumingameon");
		add_free_regOff = skin_replay.getAtlas().findRegion("getpremiumingameoff");
		styleon.up = new TextureRegionDrawable(new TextureRegionDrawable(add_free_reg));
		styleoff.up = new TextureRegionDrawable(new TextureRegionDrawable(add_free_regOff));
		add_free_version = new ImageButton(styleon);
		add_free_version_table.add(add_free_version)
				.width(VIRTUAL_SCREEN_WIDTH / 2.35f)
				.height(VIRTUAL_SCREEN_WIDTH / 2.35f * 105f / 256f)
				.padLeft(VIRTUAL_SCREEN_WIDTH/100f);
		stage.addActor(add_free_version_table);
		add_free_version_table.setFillParent(true);
		add_free_version_table.top().padTop(VIRTUAL_SCREEN_WIDTH / 16.45f);
		add_free_version.pack();
		add_free_version_table.pack();
		add_free_version.setTransform(true);
		add_free_version_table.setOrigin(
				add_free_version_table.getWidth() / 2,
				add_free_version_table.getHeight() / 2);
		add_free_version.setOrigin(
				add_free_version.getWidth() / 2,
				add_free_version.getHeight() / 2);

		add_free_version.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!still_working_on_menu_press) {
					still_working_on_menu_press = true;
					if(setts_for_game.settings.sounds_on)
						cryo_game.menu_screen.basic_button.play(SND_VOL.SOUND_MENU_BASIC);
					add_free_version.addAction(sequence(
							moveBy(0f, 12f, 0.2f),
							moveBy(0f, -12f, 0.3f),
							run(new Runnable() {
								public void run() {
									cryo_game.menu_screen.bCameFromHUD = true;
									bCurrentlyFadedOutDontDraw = true;
									still_working_on_menu_press = true;
									try {
										result = cryo_game.inappInterfaceResolver
												.requestIabPurchase(
												IActionResolver.PRODUCT_PREMIUM,
												cryo_game.menu_screen);
										if (result == RESULT_OK) {
										} else { // something went wrong
										}
									} catch (Exception ex){
										TransformIntoPremiumFailed("Something went bad!",
												ex01Types.LOGIN_NEXT_NO);
									}
								}
					})));
				}
			}
		});
	}


	public void ProcedureFailedAdFree(String text){
		if(text==ex01Types.IAB_FAILED_ACCOUNT_SETUP_PROMPT) {
			TransformIntoPremiumFailed(text, ex01Types.LOGIN_NEXT_YES);
			just_beginning_sign_in = true;
		} else {
			TransformIntoPremiumFailed(text, ex01Types.LOGIN_NEXT_NO);
			just_beginning_sign_in = false;
		}
	}

	public boolean just_beginning_sign_in = false;

	public void TransformIntoPremiumFailed(String string, boolean login_next){
		login_next_yes_no = login_next;
		text = string;
		add_free_version_table.addAction(sequence(
				Actions.fadeOut(1.0f),
				Actions.run(new Runnable() {
			public void run() {
				level_base_premium_error.setStyle(textb1x);
				TransitError(text, true);
			}
		})));
	}

	public void UnlockAllLevelsHUD(){
		cryo_game.menu_screen.UnlockAllLevelsALL();
		can_render_unlock = false;
		cryo_game.menu_screen.menus.can_render_unlock = false;
		unlock_free_version_table.remove();
		setts_for_game.settings.is_this_unlocked_all_levels = true;
		level_base_premium_error.setStyle(textb2ok);
		TransitError(ex01Types.UPDATED_UNLOCKED_LEVELS, true);
		RetractTablesUnlockFromMenus();
	}

	public void RetractTablesUnlockFromMenus(){
		cryo_game.menu_screen.menus.unlock_free_version_table.remove();
	}

	public void TransformIntoPremium(){
		add_free_version_table.remove();
		level_base_premium_error.setStyle(textb2ok);
		RetractTablesPremiumFromMenu();
		TransitError(ex01Types.UPDATED_PREMIUM, true);
		InitUnlock();
		cryo_game.menu_screen.menus.InitUnlock();
	}

	public void RetractTablesPremiumFromMenu(){
		cryo_game.menu_screen.menus.add_free_version_table.remove();
	}

	public void InitUnlockAllLevels(){
		unlock_free_version_table = new Table();
		unlock_free_reg = skin_replay.getAtlas().findRegion("unlocklevels");
		ImageButtonStyle ibs_unlock_free = new ImageButtonStyle();
		ibs_unlock_free.up = new
				TextureRegionDrawable(new TextureRegionDrawable(unlock_free_reg));
		unlock_free_version = new ImageButton(ibs_unlock_free);
		unlock_free_version_table.add(unlock_free_version)
				.width(VIRTUAL_SCREEN_WIDTH / 2.35f)
				.height(VIRTUAL_SCREEN_WIDTH / 2.35f * 105f / 256f)
				.padLeft(VIRTUAL_SCREEN_WIDTH / 100f);
		stage.addActor(unlock_free_version_table);
		unlock_free_version_table.setFillParent(true);
		unlock_free_version_table.top().padTop(VIRTUAL_SCREEN_WIDTH / 16.45f);
		unlock_free_version_table.addAction(fadeOut(0f));
		unlock_free_version.pack();
		unlock_free_version_table.pack();
		unlock_free_version.setTransform(true);
		unlock_free_version_table.setOrigin(
				unlock_free_version_table.getWidth() / 2,
				unlock_free_version_table.getHeight() / 2);
		unlock_free_version.setOrigin(
				unlock_free_version.getWidth() / 2,
				unlock_free_version.getHeight() / 2);
		unlock_free_version.toFront();
		unlock_free_version_table.toFront();

		unlock_free_version.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!still_working_on_menu_press && cryo_game.menu_screen.menus.bIsLoggedIn) {
					still_working_on_menu_press = true;
					if(setts_for_game.settings.sounds_on)
						cryo_game.menu_screen.basic_button.play(SND_VOL.SOUND_MENU_BASIC);
					came_failed_from_HUD = true;
					unlock_free_version.addAction(sequence(
						moveBy(0f, 12f, 0.2f),
						moveBy(0f, -12f, 0.3f),
						run(new Runnable() {
							public void run() {
								cryo_game.menu_screen.bCameFromHUD = true;
								is_still_rolling_reset_premium_unlocked = true;
								result = cryo_game.inappInterfaceResolver
										.requestIabPurchase(
												IActionResolver.PRODUCT_UNLOCK_LEVELS,
												cryo_game.menu_screen);
								if(result==RESULT_OK){
								} else { // something went wrong
								}
							}
					})));
				}
			}
		});
	}

	public static final int RESULT_OK = 1;
	public int result;
	public TimeUtils timerForAdd = new TimeUtils();
	public long timestamptime = timerForAdd.millis();
	public long timestamptime_render_load;
	public boolean is_still_rolling_reset_premium_unlocked = true;
	public boolean is_still_rolling_reset_premium_unlocked_duplex = true;
	public boolean can_render_loadsave = false;
	public boolean can_render_unlock_buy_only_load = false;
	//load saved game stuff
	public Table savegame_free_version_table;
	public TextureRegion savegame_free_reg;
	public ImageButton savegame_free_version;

	public void InitUnlock(){
		InitUnlockAllLevels();
		if(!cryo_game.menu_screen.menus.bIsLoggedIn){
			can_render_unlock = false;
		}
	}

	public void InitBuyPremiumIfNeededAndUnblock(){
		timestamptime_render_load = timerForAdd.millis();
		if(!setts_for_game.settings.is_this_premium_updated){
			InitNotAddFreeVersion();
			timerForAdd = new TimeUtils();
			timestamptime = timerForAdd.millis();
			can_switchstyles = true;
		} else {
			if(!setts_for_game.settings.is_this_unlocked_all_levels){
				InitUnlock();
				//only when we're premium do we need to save (bad luck!)
				if(setts_for_game.settings.is_this_in_need_for_load_saved){
					InitLoadGameFromCloud();
					can_render_loadsave = true;
					is_still_rolling_reset_premium_unlocked_duplex = false;
				} else {
					is_still_rolling_reset_premium_unlocked = false;
				}
			} else {
				if (setts_for_game.settings.is_this_in_need_for_load_saved){
					InitLoadGameFromCloud();
					can_render_loadsave = true;
					is_still_rolling_reset_premium_unlocked_duplex = false;
				} else {
					is_still_rolling_reset_premium_unlocked = false;
				}
			}
		}

		LoginLogoutProperButtons();
	}

	public void LoginLogoutProperButtons(){
		if(setts_for_game.settings.is_this_in_need_for_load_saved
				&& cryo_game.menu_screen.menus.bIsLoggedIn) {
			LoginLoadButton();
		} else {
			LogoutLoadButton();
		}
		if(!setts_for_game.settings.is_this_unlocked_all_levels
				&& cryo_game.menu_screen.menus.bIsLoggedIn){
			LoginUnlockButton();
		} else {
			LogoutUnlockButton();
		}
	}

	public void LogoutUnlockButton(){
		can_render_unlock = false;
	}

	public void LoginUnlockButton(){
		can_render_unlock = true;
	}

	public void LogoutLoadButton(){
		can_render_loadsave = false;
	}

	public void LoginLoadButton(){
		can_render_loadsave = true;
	}

	public void InitLoadGameFromCloud(){
		savegame_free_version_table = new Table();
		savegame_free_reg = skin_replay.getAtlas().findRegion("saved_game3");
		ImageButtonStyle ibs_savegame_free = new ImageButtonStyle();
		ibs_savegame_free.up = new TextureRegionDrawable(new
				TextureRegionDrawable(savegame_free_reg));
		savegame_free_version = new ImageButton(ibs_savegame_free);
		savegame_free_version_table.add(savegame_free_version)
				.width(VIRTUAL_SCREEN_WIDTH / 2.35f)
				.height(VIRTUAL_SCREEN_WIDTH / 2.35f * 105f / 256f)
				.padLeft(VIRTUAL_SCREEN_WIDTH / 100f);
		stage.addActor(savegame_free_version_table);
		savegame_free_version_table.setFillParent(true);
		savegame_free_version_table.top().padTop(VIRTUAL_SCREEN_WIDTH / 16.45f);
		savegame_free_version_table.addAction(fadeOut(0f));
		savegame_free_version.pack();
		savegame_free_version_table.pack();
		savegame_free_version.setTransform(true);
		savegame_free_version_table.setOrigin(
				savegame_free_version_table.getWidth() / 2,
				savegame_free_version_table.getHeight() / 2);
		savegame_free_version.setOrigin(
				savegame_free_version.getWidth() / 2,
				savegame_free_version.getHeight() / 2);

		savegame_free_version_table.addAction(fadeOut(0f));
		savegame_free_version_table.act(Gdx.graphics.getDeltaTime());

		savegame_free_version.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!still_working_on_menu_press && cryo_game.menu_screen.menus.bIsLoggedIn) {
					still_working_on_menu_press = true;
					if(setts_for_game.settings.sounds_on)
						cryo_game.menu_screen.basic_button.play(SND_VOL.SOUND_MENU_BASIC);
					savegame_free_version.addAction(sequence(
						moveBy(0f, 12f, 0.2f),
						moveBy(0f, -12f, 0.3f), run(new Runnable() {
							public void run() {
								still_working_on_menu_press = false;
								ResetHUD_Upon_Load_Game();
							}
					})));
				}
			}
		});
	}

	public void ResetHUD_Upon_Load_Game(){
		savegame_free_version_table.addAction(fadeOut(1f));
		can_render_loadsave = false;
		can_render_unlock = true;
		is_still_rolling_reset_premium_unlocked = false;
		can_render_unlock_buy_only_load = false;
		savegame_free_version_table.toBack();
		savegame_free_version_table.remove();
		if(unlock_free_version_table== null
				&& !setts_for_game.settings.is_this_unlocked_all_levels){
			InitUnlockAllLevels();
			unlock_free_version_table.addAction(fadeOut(0f));
			unlock_free_version_table.act(Gdx.graphics.getDeltaTime());
		}
	}

	public void SignInSuccessInHUD(){
		login_is_success = true;
		cryo_game.menu_screen.settings.settings.user_doesnt_want_signed_in = false;
		cryo_game.menu_screen.settings.WriteJson();
		cryo_game.menu_screen.bNextIsSignOut = true;
		bCurrentlyFadedOutDontDraw = true;
		still_working_on_menu_press = true;
		cryo_game.menu_screen.menus.bIsLoggedIn = true;
		CheckPossibleInNeedToFadeOut();
		FadeOutAllBeforeErrorFinished();
		level_base_premium_error.setStyle(textb2ok);
		if(cryo_game.menu_screen.settings.settings.sounds_on)
			cryo_game.menu_screen.menus.base_menu.login_button.play(SND_VOL.SUCCESS_VOL);
		TransitError(ex01Types.LOGIN_SUCCESSFUL, true);
		cryo_game.menu_screen.ChangeLoginLogout(ex01Types.LOGOUT_NEXT_YES);
	}

	public void SignInResultFailedHUD(boolean canceled){
		bCurrentlyFadedOutDontDraw = true;
		still_working_on_menu_press = true;
		CheckPossibleInNeedToFadeOut();
		FadeOutAllBeforeErrorFinished();
		level_base_premium_error.setStyle(textb1);
		if (cryo_game.menu_screen.settings.settings.sounds_on)
			cryo_game.menu_screen.error_sound_no.play(SND_VOL.ERROR_VOL_M);

		if (cryo_game.exit_error.isWiFiConnected()) {
			if (canceled != ex01Types.SIGNIN_CANCELED_YES) {
				TransitError(ex01Types.LOGIN_ERROR,
						true);
			} else {
				cryo_game.menu_screen.settings.settings.user_doesnt_want_signed_in = true;
				cryo_game.menu_screen.settings.WriteJson();
				TransitError(ex01Types.LOGIN_CANCELLED,
						true);
			}
		} else {
			if (canceled == ex01Types.SIGNIN_CANCELED_YES) {
				cryo_game.menu_screen.settings.settings.user_doesnt_want_signed_in = true;
				cryo_game.menu_screen.settings.WriteJson();
				TransitError(ex01Types.LOGIN_CANCELLED,
						true);
			} else {
				TransitError(ex01Types.NO_INTERNET_ERROR,
						true);
			}
		}
	}

	//error and info stuff
	public Stack settings_get_premium_error;
	public Table get_premium_table_error;
	public Table level_base_premium_t_error;
	public TextButton level_base_premium_error;
	public TextButtonStyle textb1x;
	public TextButtonStyle textb2okx;
	public Table level_base_premium_you_own_t_error;
	public Image level_base_premium_you_own_error;
	public Image level_base_premium_you_own_info;
	public boolean still_working_button_standard = false;
	public boolean diffuse;

	public void InitPremiumError(TextureAtlas atlas){
		//INIT the owned tables
		get_premium_table_error = new Table();
		textb1x = new TextButtonStyle();
		textb2okx = new TextButtonStyle();
		TextureRegion style1 = atlas.findRegion("money");
		TextureRegion style2 = atlas.findRegion("moneyok");
		textb1x.font = cryo_game.menu_screen.skin.getFont("errors-font");
		textb1x.up = new TextureRegionDrawable(style1);
		textb2okx.font = cryo_game.menu_screen.skin.getFont("errors-font");
		textb2okx.up = new TextureRegionDrawable(style2);
		//this stack contains the owned stuff
		settings_get_premium_error = new Stack();
		get_premium_table_error.add(settings_get_premium_error)
				.width(VIRTUAL_SCREEN_WIDTH / 1.6f)
				.height(VIRTUAL_SCREEN_WIDTH / 1.6f * 85f / 384f);
		get_premium_table_error.setFillParent(true);
		get_premium_table_error.setTransform(true);
		get_premium_table_error.top().padTop(VIRTUAL_SCREEN_WIDTH / 7.5f);
		get_premium_table_error.addAction(moveBy(0f, -25f, 0f));
		level_base_premium_t_error = new Table();
		level_base_premium_t_error.setTransform(true);
		level_base_premium_error = new TextButton("tare", textb1x);
		level_base_premium_t_error.setFillParent(true);
		level_base_premium_t_error.add(level_base_premium_error)
				.width(VIRTUAL_SCREEN_WIDTH / 1.15f)
				.height(VIRTUAL_SCREEN_WIDTH / 1.15f * 68f / 400f);
		level_base_premium_t_error.top();
		settings_get_premium_error.add(level_base_premium_t_error);
		level_base_premium_you_own_t_error = new Table();
		level_base_premium_you_own_t_error.setFillParent(true);
		level_base_premium_you_own_error = new Image(new
				TextureRegionDrawable(atlas.findRegion("error")));
		level_base_premium_you_own_t_error.add(level_base_premium_you_own_error)
				.width(VIRTUAL_SCREEN_WIDTH / 2.8f)
				.height(VIRTUAL_SCREEN_WIDTH / 2.8f * 80f / 256f)
				.padBottom(VIRTUAL_SCREEN_WIDTH / 50f);
		level_base_premium_you_own_t_error.padBottom(VIRTUAL_SCREEN_WIDTH / 6.55f);
		settings_get_premium_error.add(level_base_premium_you_own_t_error);
		level_base_premium_you_own_error.addAction(Actions.alpha(0f));
		level_base_premium_error.addAction(Actions.alpha(0f));
		get_premium_table_error.act(Gdx.graphics.getDeltaTime());
		stage.addActor(get_premium_table_error);
	}

	public String message;

	public void TransitError(String string, boolean error){
		message = string;
		diffuse = error;
		level_base_premium_error.getLabel().setColor(Color.GREEN);
		level_base_premium_error.getLabel().setText(string);
		level_base_premium_error.setTransform(true);
		get_premium_table_error.clearActions();
		level_base_premium_error.setOrigin(Align.center);
		level_base_premium_you_own_error.setOrigin(Align.center);
		level_base_premium_error.clearActions();
		level_base_premium_you_own_error.clearActions();

		level_base_premium_error.addAction(parallel(
			Actions.fadeIn(1.0f),
			Actions.scaleTo(1.3f, 1.3f, 1.0f),
			sequence(moveBy(0f, 12f, 0.3f),
				Actions.rotateBy(360f, 2.4f, Swing.circleOut),
				moveBy(0f, -12f, 0.3f),
				run(new Runnable() {
					public void run() {
						level_base_premium_error.addAction(parallel(
							Actions.scaleTo(1.0f, 1.0f, 1.0f),
							sequence(Actions.fadeOut(6.0f),
								Actions.run(new Runnable() {
									public void run() {
										TransitErrorFinish();
									}
								}))));
					}
				}))));
		level_base_premium_you_own_error.addAction(parallel(
				Actions.fadeIn(1.0f),
				Actions.scaleTo(1.3f, 1.3f, 1.0f),
				sequence(moveBy(0f, 12f, 0.3f),
						Actions.rotateBy(360f, 2.4f, Swing.circleOut),
						moveBy(0f, -12f, 0.3f),
						run(new Runnable() {
							public void run() {
								level_base_premium_you_own_error.addAction(parallel(
									Actions.scaleTo(1.0f, 1.0f, 1.0f),
									Actions.fadeOut(6.0f)));
							}
						}))));
	}

	public void TransitErrorFinish(){
		if (diffuse) {
			if(!login_next_yes_no && !just_beginning_sign_in)
				ResetRollingAddUnlockVariables();
			if(check_next_for_login_success){
				check_next_for_login_success = false;
				if(login_is_success){
					ProcedureLoginSuccessHUD();
					login_is_success = false;
				}
			}
			if(login_next_yes_no){
				if(cryo_game.exit_error.isWiFiConnected()) {
					bCurrentlyFadedOutDontDraw = true;
					still_working_on_menu_press = true;

					cryo_game.menu_screen.menus.bJustWantedToLogout = false;
					cryo_game.menu_screen.bCameFromHUD = true;
					cryo_game.menu_screen.StartLoginProceduresFromHUD();
					just_beginning_sign_in = false;

					login_next_yes_no = false;
					check_next_for_login_success = true;
				} else {
					TransitError(ex01Types.NO_INTERNET_ERROR, ex01Types.LOGIN_NEXT_NO);
				}
			} else {

			}
			if(message==ex01Types.UPDATED_UNLOCKED_LEVELS){
				if (setts_for_game.settings.is_this_in_need_for_load_saved) {
					LoginLoadButton();
					currently_rendering_load_reset = false;
					can_render_unlock_buy_only_load = true;
					cryo_game.menu_screen.menus.LoginLoadButton();
					cryo_game.menu_screen.menus.currently_rendering_load_reset = false;
					message = null;
				}
			}
		} else {
			still_working_button_standard = false;
			still_working_on_menu_press = false;
			AddFreeFadeIn();
		}
	}

	// this is TransitErrorFinish in ex01MenuScreen.java
	public void ProcedureLoginSuccessHUD(){
		still_working_on_menu_press = false;
		is_still_rolling_reset_premium_unlocked = false;
		is_still_rolling_reset_premium_unlocked_duplex = false;
		if (bCurrentlyFadedOutDontDraw) {
			cryo_game.menu_screen.bCameFromHUD = true;
			CheckInAppData(); // ActAsIfWeJustPurchasedPremium to Android
			CheckWithGoogleSoWeDrawCorrectly();
			// need to reset to premium or levels?
			if(isSomethingWrong){
				//if above yes then we don't draw until we finish reset TransitError
				if(about_to_finish_reset){
					bCurrentlyFadedOutDontDraw = false;
					about_to_finish_reset = false;
				}
			} else {
				bCurrentlyFadedOutDontDraw = false;
			}
		}
	}

	public void AddFreeFadeIn(){
		if(add_free_version_table!=null && !cryo_game.menu_screen.menus.bIsLoggedIn) {
			add_free_version_table.addAction(Actions.fadeIn(1f));
			bCurrentlyFadedOutDontDraw = false;
		}
	}

	public void FadeOutAllBeforeErrorFinished(){
		if(possible_fade_in_add_free){
			add_free_version_table.addAction(Actions.fadeOut(0f));
		}
		if(possible_fade_in_unlock_free){
			unlock_free_version_table.addAction(Actions.fadeOut(0f));
		}
		if(possible_fade_in_saveload_free){
			savegame_free_version_table.addAction(Actions.fadeOut(0f));
		}
	}

	public void CheckInAppData(){
		if(cryo_game.inappInterfaceResolver.isThisPremiumUpdated()
				&& !setts_for_game.settings.is_this_premium_updated){
			if(cryo_game.inappInterfaceResolver.isThisLevelsUnlocked()
					&& !setts_for_game.settings.is_this_unlocked_all_levels) {
				cryo_game.menu_screen.also_unlock_levels = true;
			}
			cryo_game.menu_screen.this_came_from_reset_purchase = true;
			isSomethingWrong = true;
			cryo_game.inappInterfaceResolver
					.ActAsIfWeJustPurchasedPremium(cryo_game.menu_screen);
		} else {
			bCurrentlyFadedOutDontDraw = false;
		}
	}

	public void CheckWithGoogleSoWeDrawCorrectly(){
		timestamptime_render_load = timerForAdd.millis();
		if(!setts_for_game.settings.is_this_premium_updated){
			if(add_free_version_table==null) InitNotAddFreeVersion();
			possible_fade_in_add_free = true;
			timerForAdd = new TimeUtils();
			timestamptime = timerForAdd.millis();
			can_switchstyles = true;
		} else {
			if(!setts_for_game.settings.is_this_unlocked_all_levels){
				InitUnlock();
				//only when we're premium do we need to save (bad luck!)
				if(setts_for_game.settings.is_this_in_need_for_load_saved){
					if(savegame_free_version_table==null) InitLoadGameFromCloud();
					possible_fade_in_saveload_free = true;
					can_render_loadsave = true;
					is_still_rolling_reset_premium_unlocked_duplex = false;
				} else {
					is_still_rolling_reset_premium_unlocked = false;
				}
			}
		}
	}

	public void ResetIntoPremium(){
		if(cryo_game.menu_screen.menus.add_free_version_table!=null)
			cryo_game.menu_screen.menus.add_free_version_table.remove();
		setts_for_game.settings.is_this_premium_updated = true;
		cryo_game.menu_screen.menus.level_base_premium_error
				.setStyle(cryo_game.menu_screen.menus.textb2ok);
		if(cryo_game.menu_screen.settings.settings.sounds_on)
			cryo_game.menu_screen.menus.base_menu.success_button.play(SND_VOL.SUCCESS_VOL);
		cryo_game.menu_screen.menus.can_render_unlock = true;
		cryo_game.menu_screen.menus.can_render_unlock_buy_only_load = false;

		// this redraws stars in page levels
		cryo_game.levels_screen.level_selector
				.ResetLevelsDrawkingsOnChange();

		//initiate load save button procedure
		if(!setts_for_game.settings.is_this_in_need_for_load_saved){
			cryo_game.menu_screen.menus.InitLoadGameFromCloud();
			cryo_game.menu_screen.menus.InitUnlock();
			cryo_game.menu_screen.menus.savegame_free_version_table.addAction(
					sequence(
							Actions.delay(6f),
							Actions.fadeIn(1f)));
			setts_for_game.settings.is_this_in_need_for_load_saved = true;
			cryo_game.menu_screen.menus.can_render_loadsave = true;
			cryo_game.menu_screen.menus.can_render_unlock = true;
			cryo_game.menu_screen.menus.can_render_unlock_buy_only_load = false;
			cryo_game.menu_screen.is_still_rolling_reset_premium_unlocked_duplex = false;
			cryo_game.menu_screen.is_still_rolling_reset_premium_unlocked = false;

			//HUD ALSO
			cryo_game.game_screen.hud_display.InitLoadGameFromCloud();
			cryo_game.game_screen.hud_display.InitUnlock();
			can_render_loadsave = true;
			can_render_unlock = true;
			can_render_unlock_buy_only_load = false;
			is_still_rolling_reset_premium_unlocked_duplex = false;
			is_still_rolling_reset_premium_unlocked = false;
		}

		// ##### ##### ##### HUD display part
		if(cryo_game.game_screen!=null){
			if(cryo_game.game_screen.hud_display.add_free_version_table!=null){
				cryo_game.game_screen.hud_display.add_free_version_table.remove();
			}
			cryo_game.game_screen.hud_display.level_base_premium_error
					.setStyle(cryo_game.game_screen.hud_display.textb2ok);
			TransitError(
					ex01Types.RESET_PREMIUM,
					true);
		}

		timestamptime_render_load = timerForAdd.millis();
		cryo_game.menu_screen.settings.WriteJson();
		cryo_game.SAVE_GAME_TO_CLOUD();
	}

	public void ResetIntoPremiumAlsoUnlock(){
		cryo_game.menu_screen.settings.settings.no_level_unlocked = 64;
		if(cryo_game.menu_screen.menus.add_free_version_table!=null)
			cryo_game.menu_screen.menus.add_free_version_table.remove();
		setts_for_game.settings.is_this_premium_updated = true;
		if(cryo_game.menu_screen.menus.unlock_free_version_table!=null)
			cryo_game.menu_screen.menus.unlock_free_version_table.remove();
		setts_for_game.settings.is_this_unlocked_all_levels = true;

		for(ex01MenuLevelSelectorBase.Level level:
				cryo_game.menu_screen.menus.menu_init.level_selector.levels)
		{
			level.is_closed = false;
			level.is_unlocked = true;
		}
		for(ex01JSONSettingsLoader.level_data leveld:
				setts_for_game.settings.levels)
		{
			leveld.is_closed = false;
			leveld.is_unlocked = true;
		}
		cryo_game.menu_screen.menus.can_render_unlock_buy_only_load = true;

		// this redraws stars in page levels
		cryo_game.levels_screen.level_selector
				.ResetLevelsDrawkingsOnChange();
		cryo_game.menu_screen.menus.level_base_premium_error
				.setStyle(cryo_game.menu_screen.menus.textb2ok);
		if(cryo_game.menu_screen.settings.settings.sounds_on)
			cryo_game.menu_screen.menus.base_menu.success_button.play(SND_VOL.SUCCESS_VOL);

		//initiate load save button procedure
		if(!setts_for_game.settings.is_this_in_need_for_load_saved){
			cryo_game.menu_screen.menus.InitLoadGameFromCloud();
			cryo_game.menu_screen.menus.savegame_free_version_table
					.addAction(sequence(
							Actions.delay(6f),
							Actions.fadeIn(2f)));

			setts_for_game.settings.is_this_in_need_for_load_saved = true;
			cryo_game.menu_screen.menus.can_render_loadsave = true;

			//HUD ALSO
			cryo_game.game_screen.hud_display.InitLoadGameFromCloud();
			cryo_game.game_screen.hud_display.savegame_free_version_table
					.addAction(sequence(
							Actions.delay(6f),
							Actions.fadeIn(1f)));
			cryo_game.game_screen.hud_display.can_render_loadsave = true;
		}

		// ##### ##### ##### HUD display part
		if(cryo_game.game_screen!=null){
			if(cryo_game.game_screen.hud_display.add_free_version_table!=null){
				cryo_game.game_screen.hud_display.add_free_version_table.remove();
			}
			if(cryo_game.game_screen.hud_display.unlock_free_version_table!=null){
				cryo_game.game_screen.hud_display.unlock_free_version_table.remove();
			}
			cryo_game.game_screen.hud_display.level_base_premium_error
					.setStyle(cryo_game.game_screen.hud_display.textb2ok);
			cryo_game.game_screen.hud_display.can_render_unlock_buy_only_load = true;

			TransitError(ex01Types.RESET_PREMIUM_UNLOCKED,
					true);
		}

		cryo_game.menu_screen.settings.WriteJson();
		cryo_game.SAVE_GAME_TO_CLOUD();
	}

	public void ROTATOR_RESET_ExitLevelSoResetErrorInfoHUD(){
		level_base_premium_error.clearActions();
		level_base_premium_you_own_error.clearActions();
		level_base_premium_error.invalidateHierarchy();
		level_base_premium_you_own_error.invalidateHierarchy();
		level_base_premium_error.addAction(Actions.alpha(ex01Types.BASE_ALPHA_ZERO));
		level_base_premium_you_own_error.addAction(Actions.alpha(ex01Types.BASE_ALPHA_ZERO));
		level_base_premium_error.setRotation(ex01Types.BASE_ROTATION);
		level_base_premium_you_own_error.setRotation(ex01Types.BASE_ROTATION);
		ResetRollingAddUnlockVariables(); // ce era in TransitError la sfarsit
	}

	public void ResetRollingAddUnlockVariables(){
		is_still_rolling_reset_premium_unlocked = false;
		is_still_rolling_reset_premium_unlocked_duplex = false;
		if(!check_next_for_login_success)
			bCurrentlyFadedOutDontDraw = false;
		still_working_on_menu_press = false;
		still_working_button_standard = false;
		possible_fade_in_add_free = false;
		possible_fade_in_unlock_free = false;
		possible_fade_in_saveload_free = false;
		cryo_game.menu_screen.bCameFromHUD = false;
	}

	public void UnlockAllLevelsFailedHUD(String text){
		text1 = text;
		unlock_free_version_table.clearActions();
		is_still_rolling_reset_premium_unlocked_duplex = true;
		is_still_rolling_reset_premium_unlocked = true;
		unlock_free_version_table.addAction(sequence(
				Actions.fadeOut(1.0f),
				Actions.run(new Runnable() {
					public void run() {
						level_base_premium_error.setStyle(textb1x);
						TransitError(text1, true);
						came_failed_from_HUD = false;
					}
		})));
	}

	public void RenderExtra(float delta){
		if(!bCurrentlyFadedOutDontDraw) {
			if (timerForAdd.millis() - timestamptime > 1000 && can_switchstyles) {
				timestamptime = TimeUtils.millis();
				SwitchStyleForBuyPremium();
				if(add_free_version_table.getColor().a == 0f)
					add_free_version_table.addAction(fadeIn(1f));
			}
			//this situation is when we have unlock levels button and load saved game button
			// can_render_unlock_buy_only_load > we show UNLOCK and LOAD GAME
			if (can_render_unlock && !can_render_unlock_buy_only_load) { // RESET PREMIUM ONLY
				if (can_render_loadsave) {
					if (!is_still_rolling_reset_premium_unlocked_duplex) {
						if (timerForAdd.millis() - timestamptime_render_load > 2500) {
							timestamptime_render_load = timerForAdd.millis();
							currently_rendering_load_reset = false;
							currently_rendering_load = !currently_rendering_load;
						}
						if (currently_rendering_load && !currently_rendering_load_reset) {
							if(savegame_free_version_table!=null) {
								savegame_free_version_table.clearActions();
								savegame_free_version_table.addAction(Actions.fadeIn(0f));
								savegame_free_version.addAction(sequence(
										Actions.scaleTo(1.045f, 1.045f, 2f),
										Actions.scaleTo(1f, 1f, 2f)));
								savegame_free_version_table.toFront();
							}
							if (unlock_free_version_table != null) {
								unlock_free_version_table.clearActions();
								unlock_free_version_table.addAction(Actions.fadeOut(0f));
							}
							currently_rendering_load_reset = true;
						} else if (!currently_rendering_load && !currently_rendering_load_reset) {
							if(savegame_free_version_table!=null) {
								savegame_free_version_table.clearActions();
								savegame_free_version_table.addAction(Actions.fadeOut(0f));
							}
							if (unlock_free_version_table != null) {
								unlock_free_version_table.clearActions();
								unlock_free_version_table.addAction(Actions.fadeIn(0f));
								unlock_free_version_table.toFront();
								unlock_free_version.addAction(sequence(
										Actions.scaleTo(1.045f, 1.045f, 2f),
										Actions.scaleTo(1f, 1f, 2f)));
							}
							currently_rendering_load_reset = true;
						}
						if (unlock_free_version_table != null) {
						}
					}
				} else {
					if (unlock_free_version_table != null
					 && unlock_free_version_table.getColor().a == 0f) {
						if (!is_still_rolling_reset_premium_unlocked) {
							unlock_free_version_table.addAction(sequence(
									Actions.delay(1f),
									fadeIn(2f)));
							if (unlock_free_version_table != null) {
							}
						}
					}

				}
			// can_render_unlock_buy_only_load > we only show LOAD GAME when we're first entering
			// the game after reset
			} else if (can_render_unlock) {
				if (can_render_loadsave) {
					if (!is_still_rolling_reset_premium_unlocked) {
						if (timerForAdd.millis() - timestamptime_render_load > 2500) {
							timestamptime_render_load = timerForAdd.millis();
							currently_rendering_load_reset = false;
							if(savegame_free_version_table!=null) {
								savegame_free_version_table.addAction(Actions.fadeIn(1f));
								savegame_free_version_table.toFront();
							}
						}
						if (!currently_rendering_load_reset) {
							if(savegame_free_version_table!=null) {
								savegame_free_version_table.addAction(sequence(
										Actions.scaleTo(1.085f, 1.085f, 2f),
										Actions.scaleTo(1f, 1f, 2f)));
							}
							currently_rendering_load_reset = true;
						}
					}
				}
			} else {
				if (can_render_loadsave) {
					if (!currently_rendering_load_reset) {
						if(cryo_game.menu_screen.menus.bIsLoggedIn) {
							if(savegame_free_version_table!=null) {
								savegame_free_version_table.addAction(fadeIn(1f));
								savegame_free_version_table.addAction(sequence(
										Actions.scaleTo(1.085f, 1.085f, 2f),
										Actions.scaleTo(1f, 1f, 2f)));
							}
							currently_rendering_load_reset = true;
						}
					}
				}
			}
		}
	}

	public void InitCoinsWon(TextureAtlas atlas){		
    	//INIT the owned tables
		get_coinswon_table_error = new Table();
		textb1_coinswon = new TextButtonStyle();
		textb2_coinswon = new TextButtonStyle();
		textb3_coinswon = new TextButtonStyle();
		textb4_coinswon = new TextButtonStyle();
		TextureRegion style1 = skin_replay.getRegion("buyergold_gold");     // YOU WON
		TextureRegion style2 = skin_replay.getRegion("buyergold_goldsemi"); // YOU WON 
		TextureRegion style3 = skin_replay.getRegion("buyergold_silver");   // YOU WON
		TextureRegion style4 = skin_replay.getRegion("buyergold_nothing");  // INFO
		textb1_coinswon.font = skin_replay.getFont("errors-font");
		textb1_coinswon.up = new TextureRegionDrawable(style1);
		textb1_coinswon.fontColor = Color.CYAN;
		textb2_coinswon.font = skin_replay.getFont("errors-font");
		textb2_coinswon.up = new TextureRegionDrawable(style2);
		textb3_coinswon.font = skin_replay.getFont("errors-font");
		textb3_coinswon.up = new TextureRegionDrawable(style3);
		textb4_coinswon.font = skin_replay.getFont("errors-font");
		textb4_coinswon.up = new TextureRegionDrawable(style4);
		//this stack contains the owned stuff
		settings_get_coinswon_error = new Stack();		
		get_coinswon_table_error.add(settings_get_coinswon_error)
				.width(VIRTUAL_SCREEN_WIDTH/1.6f)
				.height(VIRTUAL_SCREEN_WIDTH/1.6f * 85f/384f)
				.expandX().padLeft(VIRTUAL_SCREEN_WIDTH/100f);
		get_coinswon_table_error.setFillParent(true);
		get_coinswon_table_error.setTransform(true);
		get_coinswon_table_error.bottom().padBottom(VIRTUAL_SCREEN_WIDTH/9.5f);
		get_coinswon_table_error.addAction(moveBy(0f,-25f,0f));
		level_base_coinswon_t_error = new Table();
		level_base_coinswon_t_error.setTransform(true);
		level_base_coinswon_error = new TextButton("132 COINS", textb1_coinswon);
		level_base_coinswon_error.setTransform(true);
		level_base_coinswon_error.getLabelCell().padTop(VIRTUAL_SCREEN_WIDTH/20f);
		level_base_coinswon_t_error.setFillParent(true);
		level_base_coinswon_t_error.add(level_base_coinswon_error)
				.width(VIRTUAL_SCREEN_WIDTH/1.45f)
				.height(VIRTUAL_SCREEN_WIDTH/1.45f * 155f/512f).bottom();
		level_base_coinswon_t_error.bottom();
		settings_get_coinswon_error.add(level_base_coinswon_t_error);
		stage.addActor(get_coinswon_table_error);
		get_coinswon_table_error.addAction(Actions.fadeOut(0f));
		get_coinswon_table_error.pack();
		level_base_coinswon_error.setOrigin(
				level_base_coinswon_error.getWidth()/2,
				level_base_coinswon_error.getHeight()/2);
	}
	
	public void Hud_Table_Replay_InitReload(){
		HudLevelInitIntString();
	}
	
	public void HudLevelInitIntString(){
		if(level_no < 10) {
			level_name = "LEVEL 0" + Integer.toString(level_no);
		} else {
			level_name = "LEVEL " + Integer.toString(level_no);
		}		
		level_no_button.setText(level_name);
	}
	
	public void HudLevelReplayResume(){
		if(primary_level_no!=level_no){
			restart_button.setStyle(skin_replay.get("play_button", ImageButtonStyle.class));
		} else restart_button.setStyle(skin_replay.get("restart_button", ImageButtonStyle.class));
	}

	public void HudLevelAnglesMaxAngle(){
		if(primary_level_no!=level_no){
			level_base_spacecraft_error.setStyle(textb3level);
			level_base_spacecraft_error.setText("Angle to beat: " +
					decimal_transform.format(setts_for_game
							.settings.level_angles[level_no - 1]));
		} else {
			level_base_spacecraft_error.setStyle(old_style);
			if(!the_game_screen.spaces.exploded && !the_game_screen.finished_the_job){
				level_base_spacecraft_error.setText(old_level_string_error_info);
			} else {
				level_base_spacecraft_error.setText("Angle to beat: " +
						decimal_transform.format(setts_for_game
								.settings.level_angles[level_no-1]));
			}
		}
	}
	
	public void Hud_Table_Replay_Init(_ex01CryotraxGame game_screen){
		the_game_screen = game_screen;
    	Bscreen_sizew = VIRTUAL_SCREEN_WIDTH;
    	Bscreen_sizeh = VIRTUAL_SCREEN_HEIGHT;
		screen_sizew2 = Gdx.graphics.getWidth();
		screen_sizeh2 = Gdx.graphics.getHeight();
		raport_relativ_ecrane = ((float)screen_sizeh2/Bscreen_sizeh);
		raport_relativ_ecrane2 = ((float)screen_sizew2/Bscreen_sizew);
		camera = new OrthographicCamera();    
		viewport = new StretchViewport((float)Bscreen_sizew, (float)Bscreen_sizeh, camera);
		stage = new Stage(viewport);
		if(cryo_game.screen_type==1){
			skin_replay = new Skin(Gdx.files.internal(ex01Types.REPLAY_JSON01big));
		} else if(cryo_game.screen_type==2){
			skin_replay = new Skin(Gdx.files.internal(ex01Types.REPLAY_JSON02medium));
		} else {
			skin_replay = new Skin(Gdx.files.internal(ex01Types.REPLAY_JSON03small));
		}
		table_general_pause = new Table();
		table_general_pause_stack = new Stack();
		table_general_pause_back = new Table();
		table_general_pause_back.setTransform(true);
		table_main_menu = new Table();
		main_menu_button = new ImageButton(skin_replay, "back_main_menu");
		main_menu_button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!bCurrentlyFadedOutDontDraw && !is_stop_click_for_finish) {
					MainMenuProcedureClick();
				} else {
					main_menu_button.addAction(sequence(
							moveBy(0f, 12f, 0.1f),
							moveBy(0f, -12f, 0.2f)));
				}
			}
		});
		table_main_menu.add(main_menu_button)
				.width(VIRTUAL_SCREEN_WIDTH / 2.5f)
				.height(VIRTUAL_SCREEN_WIDTH / 2.5f * 88f / 256f)
				.padLeft(VIRTUAL_SCREEN_WIDTH / 150.0f)
				.padBottom(VIRTUAL_SCREEN_WIDTH / 20f)
				.padBottom(VIRTUAL_SCREEN_WIDTH / 1.20f);
		image_general_pause_back = new ImageButton(skin_replay, "background_pause");
		table_general_pause_back.add(image_general_pause_back)
				.width(VIRTUAL_SCREEN_WIDTH / 0.975f)
				.height(VIRTUAL_SCREEN_WIDTH / 0.975f * 400f / 580f)
				.padTop(VIRTUAL_SCREEN_WIDTH / 6.95f);
		restart_button = new ImageButton(skin_replay, "restart_button");
		restart_button.setTransform(true);
		resume_button = new ImageButton(skin_replay, "resume_button");
		restart_style = new ImageButtonStyle();
		restart_style.up = new TextureRegionDrawable(skin_replay.getRegion("replayb"));
		resume_style = new ImageButtonStyle();
		resume_style.up = new TextureRegionDrawable(skin_replay.getRegion("resumeb"));
		resumegr_style = new ImageButtonStyle();
		resumegr_style.up = new TextureRegionDrawable(skin_replay.getRegion("resumebgrey"));
		restart_button.setStyle(restart_style);
		resume_button.setStyle(resume_style);
		resume_button.setTransform(true);
		table_replay_resume = new Table();
		table_replay_resume.setFillParent(true);
		table_replay_resume.setTransform(true);
		table_replay_resume.padBottom(VIRTUAL_SCREEN_WIDTH775 / 2f);
		table_replay_resume.add(resume_button)
				.width(VIRTUAL_SCREEN_WIDTH43)
				.height(VIRTUAL_SCREEN_HEIGHT43)
				.padBottom(-VIRTUAL_SCREEN_WIDTH / 2.5f)
				.padRight(VIRTUAL_SCREEN_WIDTH / 40f)
				.padRight(-VIRTUAL_SCREEN_WIDTH / 95f);
		table_replay_resume.add(restart_button)
				.width(VIRTUAL_SCREEN_WIDTH43)
				.height(VIRTUAL_SCREEN_HEIGHT43)
				.padBottom(-VIRTUAL_SCREEN_WIDTH / 2.5f)
				.padLeft(VIRTUAL_SCREEN_WIDTH / 30f);
		table_replay_resume.padLeft(VIRTUAL_SCREEN_WIDTH / 120.5f);
		table_general_pause_stack.add(table_general_pause_back);
		table_general_pause_stack.add(table_replay_resume);
		table_replay_resume.bottom();
		table_general_pause.add(table_general_pause_stack)
				.width(VIRTUAL_SCREEN_WIDTH12)
				.height(VIRTUAL_SCREEN_HEIGHT12);
		table_general_pause.center();
		table_general_pause.setFillParent(true);
		image_pause_table = new Table();
		stage.addActor(image_pause_table);
		image_pause_table.setFillParent(true);
		image_pause_table.addAction(Actions.alpha(1.000f));
		table_general_pause_stack.add(image_pause_table);		
		stage.addActor(table_general_pause);
		table_general_pause.setTransform(true);
		table_general_pause_stack.setTransform(true);
		table_general_pause.align(Align.center);
		level_go_left = new ImageButton(skin_replay, "left_button");
		level_go_left.pad(0f);
		level_go_right = new ImageButton(skin_replay, "right_button");
		level_go_right.pad(0f);
		level_go_left_right = new Table();
		level_go_left_right.add(level_go_left)
				.width(VIRTUAL_SCREEN_WIDTH / 8.3f)
				.height(VIRTUAL_SCREEN_WIDTH / 8.3f * 310f / 128f)
				.padRight(VIRTUAL_SCREEN_WIDTH / 3.0f)
				.padBottom(VIRTUAL_SCREEN_WIDTH / 14.225f);
		level_go_left_right.add(level_go_right)
				.width(VIRTUAL_SCREEN_WIDTH / 8.3f)
				.height(VIRTUAL_SCREEN_WIDTH / 8.3f * 310f / 128f)
				.padLeft(VIRTUAL_SCREEN_WIDTH / 3.0f)
				.padBottom(VIRTUAL_SCREEN_WIDTH / 14.225f);
		level_go_left_right.padTop(VIRTUAL_SCREEN_WIDTH / 7.250f);
		table_general_pause_stack.add(level_go_left_right);
		table_general_pause_stack.setOrigin(Align.center);
		level_go_left.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!is_stop_click_for_finish) {
					if (cryo_game.menu_screen.settings.settings.sounds_on)
						cryo_game.menu_screen.level_left_right_button
								.play(SOUND_LEFT_RIGHT_VOLUME);
					table_general_pause.addAction(sequence(
							Actions.scaleTo(1.05f, 1.05f, 0.4f),
							Actions.scaleTo(1.0f, 1.0f, 0.6f)));
					level_go_left.addAction(sequence(
							moveBy(-12f, 0f, 0.1f),
							moveBy(12f, 0f, 0.2f),
							run(new Runnable() {
								public void run() {
									if (level_no - 1 >= 1) {
										level_no--;
									} else {
										level_no = setts_for_game.settings.no_level_unlocked;
									}
									HudLevelInitIntString();
									HudLevelReplayResume();
									HudLevelAnglesMaxAngle();
									ProcessAngleStars(
											setts_for_game.settings.levels.get(level_no - 1)
													.level1_angle,
											setts_for_game.settings.levels.get(level_no - 1)
													.level2_angle,
											setts_for_game.settings.levels.get(level_no - 1)
													.level3_angle);
								}
							})));
				} else {
					level_go_left.addAction(sequence(
							moveBy(-12f, 0f, 0.1f),
							moveBy(12f, 0f, 0.2f)));
				}
			}
		});	   
		level_go_right.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!is_stop_click_for_finish) {
					if (cryo_game.menu_screen.settings.settings.sounds_on)
						cryo_game.menu_screen.level_left_right_button
								.play(SOUND_LEFT_RIGHT_VOLUME);
					table_general_pause.addAction(sequence(
							Actions.scaleTo(1.05f, 1.05f, 0.4f),
							Actions.scaleTo(1.0f, 1.0f, 0.6f)));
					level_go_right.addAction(sequence(
							moveBy(12f, 0f, 0.1f),
							moveBy(-12f, 0f, 0.2f),
							run(new Runnable() {
								public void run() {
									if (level_no + 1 <= setts_for_game.settings.no_level_unlocked)
									{
										level_no++;
									} else {
										level_no = 1;
									}
									HudLevelInitIntString();
									HudLevelReplayResume();
									HudLevelAnglesMaxAngle();
									ProcessAngleStars(
											setts_for_game.settings.levels.get(level_no - 1)
													.level1_angle,
											setts_for_game.settings.levels.get(level_no - 1)
													.level2_angle,
											setts_for_game.settings.levels.get(level_no - 1)
													.level3_angle);
								}
							})));
				} else {
					level_go_right.addAction(sequence(
							moveBy(12f, 0f, 0.1f),
							moveBy(-12f, 0f, 0.2f)));
				}
			}
		});	 
		
		ImageTextButton.ImageTextButtonStyle ibs = new
				ImageTextButton.ImageTextButtonStyle();
		ibs.font = skin_replay.getFont("scorescombo3-font160");
		ibs.fontColor = new Color(1.0f, 1.0f, 1.0f, 0.50f);
		ibs.up = new TextureRegionDrawable(skin_replay.getRegion("level_name"));
		level_no_button = new ImageTextButton("LEVEL 01", ibs);
		level_no_table = new Table();
		level_no_table.setFillParent(true);
		level_no_table.add(level_no_button)
				.width(VIRTUAL_SCREEN_WIDTH / 2.43f)
				.height(VIRTUAL_SCREEN_WIDTH / 2.43f * 134f / 256f)
				.padBottom(VIRTUAL_SCREEN_WIDTH / 2.85f)
				.padLeft(VIRTUAL_SCREEN_WIDTH / 150.0f);
		table_general_pause_stack.add(level_no_table);
		level_no_button.getLabelCell()
				.padLeft(-VIRTUAL_SCREEN_WIDTH / 50f)
				.padTop(VIRTUAL_SCREEN_WIDTH / 17.4f);
		table_angles_how_wedoing = new Table();
		angles_how_wedoing1 = new CheckBox("", skin_replay);
		angles_how_wedoing1.setDisabled(true);
		angles_how_wedoing2 = new CheckBox("", skin_replay);
		angles_how_wedoing2.setDisabled(true);
		angles_how_wedoing3 = new CheckBox("", skin_replay);
		angles_how_wedoing3.setDisabled(true);
		angles_how_wedoing1.setTransform(true);
		angles_how_wedoing2.setTransform(true);
		angles_how_wedoing3.setTransform(true);
		angles_how_wedoing1.getImage().setSize(
				ex01Types.VIRTUAL_SCREEN_WIDTH / 7.2f,
				ex01Types.VIRTUAL_SCREEN_WIDTH / 7.2f * 145f / 96f);
		angles_how_wedoing2.getImage().setSize(
				ex01Types.VIRTUAL_SCREEN_WIDTH / 7.2f,
				ex01Types.VIRTUAL_SCREEN_WIDTH / 7.2f * 145f / 96f);
		angles_how_wedoing3.getImage().setSize(
				ex01Types.VIRTUAL_SCREEN_WIDTH / 7.2f,
				ex01Types.VIRTUAL_SCREEN_WIDTH / 7.2f * 145f / 96f);
		table_angles_how_wedoing.add(angles_how_wedoing1)
				.width(VIRTUAL_SCREEN_WIDTH / 7.2f)
				.height(VIRTUAL_SCREEN_WIDTH / 7.2f * 145f / 96f);
		table_angles_how_wedoing.add(angles_how_wedoing2)
				.width(VIRTUAL_SCREEN_WIDTH / 7.2f)
				.height(VIRTUAL_SCREEN_WIDTH / 7.2f * 145f / 96f);
		table_angles_how_wedoing.add(angles_how_wedoing3)
				.width(VIRTUAL_SCREEN_WIDTH / 7.2f)
				.height(VIRTUAL_SCREEN_WIDTH / 7.2f * 145f / 96f);
		angles_how_wedoing1.getImageCell().reset();
		angles_how_wedoing2.getImageCell().reset();
		angles_how_wedoing3.getImageCell().reset();
		table_angles_how_wedoing.setTransform(true);
		table_general_pause_stack.add(table_angles_how_wedoing);
		table_angles_how_wedoing.padTop(VIRTUAL_SCREEN_WIDTH / 13.250f);
		table_main_menu.setFillParent(true);
		table_general_pause.pack();
		angles_how_wedoing1.setOrigin(
				angles_how_wedoing1.getWidth() / 2,
				angles_how_wedoing1.getHeight() / 2);
		angles_how_wedoing2.setOrigin(
				angles_how_wedoing2.getWidth() / 2,
				angles_how_wedoing2.getHeight() / 2);
		angles_how_wedoing3.setOrigin(
				angles_how_wedoing3.getWidth() / 2,
				angles_how_wedoing3.getHeight() / 2);
		table_general_pause.setOrigin(
				table_general_pause.getWidth() / 2f,
				table_general_pause.getHeight() / 2f);
		stage.addActor(table_main_menu);

		main_menu_button.setTransform(true);
		table_main_menu.pack();
		main_menu_button.setOrigin(
				main_menu_button.getWidth() / 2,
				main_menu_button.getHeight() / 2);

		HudLevelInitIntString();
		InitPremiumError(textureAtlasReplay);
		if(!cryo_game.menu_screen.settings.settings.is_this_for_free) {
			InitBuyPremiumIfNeededAndUnblock(); //extra stuff like buy premium and unlock levels
		}
	}

	public void MainMenuProcedureClick(){
		if (cryo_game.menu_screen.settings.settings.sounds_on)
			cryo_game.menu_screen.level_opener_selector_button
					.play(SOUND_LEFT_RIGHT_VOLUME);
		the_game_screen.mloader.loading_now = true;
		the_game_screen.mloader.can_change_screen = false;
		the_game_screen.mloader.started_procedure = false;
		the_game_screen.mloader.loadingLoaderCount = 0.000f;
		main_menu_button.addAction(sequence(
				moveBy(0f, 12f, 0.1f),
				Actions.rotateBy(360f, 0.4f, Swing.circleOut),
				moveBy(0f, -12f, 0.2f),
				run(new Runnable() {
					public void run() {
						if (added) {
							if (setts_for_game.settings.is_this_premium_updated) {
								MainMenuProcedureClickAfter();
							} else {
								if (CanWorkOnAdNow()) {
									added_render_VALUE = false;
									ProcessNeedToGoVALUE_MainMenu();
								} else {
									MainMenuProcedureClickAfter();
								}
							}
						}
					}
				})));
	}

	public void MainMenuProcedureClickAfter(){
		cryo_game.menu_screen.menus.menu_init
				.cryo_image.clearActions();
		cryo_game.menu_screen.menus.menu_init
				.cryo_image_table.clearActions();
		cryo_game.menu_screen.menus.menu_init
				.cryo_image.addAction(Actions.alpha(0f));
		cryo_game.menu_screen.menus.menu_init
				.cryo_image_table.addAction(Actions.alpha(0f));
		// we use this in render otherwise it will crash
		// MOFO when we Main Menu from game pause
		cryo_game.game_screen.is_still_in_game = false;
		cryo_game.already_loaded_game_once = true;
		cryo_game.game_state = GameState.LEVEL_ENTRY;
		cryo_game.levels_screen.camefromgame = true;
		cryo_game.levels_screen.imageb_play
				.addAction(Actions.scaleTo(1.0f, 1.0f, 0.4f));
		cryo_game.setScreen(cryo_game.levels_screen);
		cryo_game.levels_screen
				.CameFromGameScreenSetTo(level_no - 1);
		// this redraws stars in page levels
		cryo_game.levels_screen.level_selector
				.ResetLevelsDrawkingsOnChange();
		Gdx.input.setInputProcessor(
				cryo_game.menu_screen.menus.multiplexer);
		still_working_on_menu_press = false;
		ROTATOR_RESET_ExitLevelSoResetErrorInfoHUD();
		ResetAllMaxAngleWonInfoTables();
		cryo_game.game_screen.spaces.AlsoKillMusic();
	}

	public void InitCheckBoxesStars(){
		ProcessAngleStars(setts_for_game.settings.levels.get(level_no - 1).level1_angle,
				setts_for_game.settings.levels.get(level_no - 1).level2_angle,
				setts_for_game.settings.levels.get(level_no - 1).level3_angle);
	}
	
	// sets up check-box checked unchecked values
	public void ProcessAngleStars(int ang1, int ang2, int ang3){
		//smallest angle
	  	switch(ang1)
    	{
        	case 35:
    			chk_style = skin_replay.get("chk35", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing1.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_1_achieved){
        			angles_how_wedoing1.setChecked(true);
        		} else {
        			angles_how_wedoing1.setChecked(false);
        		}        		
        		break;
        	case 30:
    			chk_style = skin_replay.get("chk30", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing1.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_1_achieved){
        			angles_how_wedoing1.setChecked(true);
        		} else {
        			angles_how_wedoing1.setChecked(false);
        		}        		
        		break;    
        	case 25:
    			chk_style = skin_replay.get("chk25", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing1.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_1_achieved){
        			angles_how_wedoing1.setChecked(true);
        		} else {
        			angles_how_wedoing1.setChecked(false);
        		}        		
        		break; 
        	case 20:
    			chk_style = skin_replay.get("chk20", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing1.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_1_achieved){
        			angles_how_wedoing1.setChecked(true);
        		} else {
        			angles_how_wedoing1.setChecked(false);
        		}        		
        		break; 
        	case 15:
    			chk_style = skin_replay.get("chk15", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing1.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_1_achieved){
        			angles_how_wedoing1.setChecked(true);
        		} else {
        			angles_how_wedoing1.setChecked(false);
        		}        		
        		break; 
        	case 10:
    			chk_style = skin_replay.get("chk10", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing1.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_1_achieved){
        			angles_how_wedoing1.setChecked(true);
        		} else {
        			angles_how_wedoing1.setChecked(false);
        		}        		
        		break; 
        	case 5:
    			chk_style = skin_replay.get("chk5", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing1.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_1_achieved){
        			angles_how_wedoing1.setChecked(true);
        		} else {
        			angles_how_wedoing1.setChecked(false);
        		}        		
        		break; 	        		
        	default:
        		break;
    	}
    	//middle angle
       	switch(ang2)
    	{
        	case 35:
    			chk_style = skin_replay.get("chk35", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing2.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_2_achieved){
        			angles_how_wedoing2.setChecked(true);
        		} else {
        			angles_how_wedoing2.setChecked(false);
        		}        		
        		break;
        	case 30:
    			chk_style = skin_replay.get("chk30", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing2.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_2_achieved){
        			angles_how_wedoing2.setChecked(true);
        		} else {
        			angles_how_wedoing2.setChecked(false);
        		}        		
        		break;    
        	case 25:
    			chk_style = skin_replay.get("chk25", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing2.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_2_achieved){
        			angles_how_wedoing2.setChecked(true);
        		} else {
        			angles_how_wedoing2.setChecked(false);
        		}        		
        		break; 
        	case 20:
    			chk_style = skin_replay.get("chk20", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing2.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_2_achieved){
        			angles_how_wedoing2.setChecked(true);
        		} else {
        			angles_how_wedoing2.setChecked(false);
        		}        		
        		break; 
        	case 15:
    			chk_style = skin_replay.get("chk15", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing2.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_2_achieved){
        			angles_how_wedoing2.setChecked(true);
        		} else {
        			angles_how_wedoing2.setChecked(false);
        		}        		
        		break; 
        	case 10:
    			chk_style = skin_replay.get("chk10", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing2.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_2_achieved){
        			angles_how_wedoing2.setChecked(true);
        		} else {
        			angles_how_wedoing2.setChecked(false);
        		}        		
        		break; 
        	case 5:
    			chk_style = skin_replay.get("chk5", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing2.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_2_achieved){
        			angles_how_wedoing2.setChecked(true);
        		} else {
        			angles_how_wedoing2.setChecked(false);
        		}        		
        		break; 	        		
        	default:
        		break;
    	}
       	//biggest most easy angle
       	switch(ang3)
    	{
        	case 35:
    			chk_style = skin_replay.get("chk35", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing3.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_3_achieved){
        			angles_how_wedoing3.setChecked(true);
        		} else {
        			angles_how_wedoing3.setChecked(false);
        		}        		
        		break;
        	case 30:
    			chk_style = skin_replay.get("chk30", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing3.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_3_achieved){
        			angles_how_wedoing3.setChecked(true);
        		} else {
        			angles_how_wedoing3.setChecked(false);
        		}        		
        		break;    
        	case 25:
    			chk_style = skin_replay.get("chk25", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing3.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_3_achieved){
        			angles_how_wedoing3.setChecked(true);
        		} else {
        			angles_how_wedoing3.setChecked(false);
        		}        		
        		break; 
        	case 20:
    			chk_style = skin_replay.get("chk20", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing3.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_3_achieved){
        			angles_how_wedoing3.setChecked(true);
        		} else {
        			angles_how_wedoing3.setChecked(false);
        		}        		
        		break; 
        	case 15:
    			chk_style = skin_replay.get("chk15", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing3.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_3_achieved){
        			angles_how_wedoing3.setChecked(true);
        		} else {
        			angles_how_wedoing3.setChecked(false);
        		}        		
        		break; 
        	case 10:
    			chk_style = skin_replay.get("chk10", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing3.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_3_achieved){
        			angles_how_wedoing3.setChecked(true);
        		} else {
        			angles_how_wedoing3.setChecked(false);
        		}        		
        		break; 
        	case 5:
    			chk_style = skin_replay.get("chk5", CheckBox.CheckBoxStyle.class);
    			angles_how_wedoing3.setStyle(chk_style);
        		if(setts_for_game.settings.levels.get(level_no-1).is_mission_3_achieved){
        			angles_how_wedoing3.setChecked(true);
        		} else {
        			angles_how_wedoing3.setChecked(false);
        		}        		
        		break; 	        		
        	default:
        		break;
    	}          			
	}
	
	// this describes min. angle - > buy life-slots - > buy ammo-slots 
	public void InitSpaceshipError(TextureAtlas atlas){		
		//MIN. ANGLE --------------------------------------------------------
		get_spacecraft_table_error = new Table();
		textb1 = new TextButtonStyle();
		textb2ok = new TextButtonStyle();
		textb3level = new TextButtonStyle();
		textb4level = new TextButtonStyle();
		textb5betterlevel = new TextButtonStyle();
		textb6failedangle = new TextButtonStyle();
		TextureRegion style1 = skin_replay.getRegion("angles");
		TextureRegion style2 = skin_replay.getRegion("angles2");
		TextureRegion style3 = skin_replay.getRegion("angles3");
		TextureRegion style4 = skin_replay.getRegion("angles4");
		TextureRegion style5 = skin_replay.getRegion("angles5");
		TextureRegion style6 = skin_replay.getRegion("angles6");
		textb1.font = skin_replay.getFont("errors-font");
		textb1.fontColor = Color.GREEN;
		textb1.up = new TextureRegionDrawable(style1);
		textb2ok.font = skin_replay.getFont("errors-font");
		textb2ok.fontColor = Color.GREEN;
		textb2ok.up = new TextureRegionDrawable(style2);
		textb3level.font = skin_replay.getFont("errors-font");
		textb3level.fontColor = Color.YELLOW;
		textb3level.up = new TextureRegionDrawable(style3);		
		textb4level.font = skin_replay.getFont("errors-font");
		textb4level.fontColor = Color.NAVY;;
		textb4level.up = new TextureRegionDrawable(style4);		
		textb5betterlevel.font = skin_replay.getFont("errors-font");
		textb5betterlevel.fontColor = Color.YELLOW;;
		textb5betterlevel.up = new TextureRegionDrawable(style5);
		textb6failedangle.font = skin_replay.getFont("errors-font");
		textb6failedangle.fontColor = Color.ORANGE;;
		textb6failedangle.up = new TextureRegionDrawable(style6);
		//this stack contains the owned stuff
		settings_get_spacecraft_error = new Stack();		
		get_spacecraft_table_error.add(settings_get_spacecraft_error)
				.width(VIRTUAL_SCREEN_WIDTH / 1.6f)
				.height(VIRTUAL_SCREEN_WIDTH / 1.6f * 85f / 384f);
		get_spacecraft_table_error.setFillParent(true);
		get_spacecraft_table_error.setTransform(true);
		get_spacecraft_table_error.bottom().padBottom(VIRTUAL_SCREEN_WIDTH/10f);
		get_spacecraft_table_error.addAction(moveBy(0f,-25f,0f)); 
		level_base_spacecraft_t_error = new Table();
		level_base_spacecraft_t_error.setTransform(true);
		level_base_spacecraft_error = new TextButton("max.angle : 90.00", textb1);
		level_base_spacecraft_error.setTransform(true);
		level_base_spacecraft_error.pack();
		level_base_spacecraft_error.setOrigin(
				level_base_spacecraft_error.getWidth()/2,
				level_base_spacecraft_error.getHeight()/2);
		old_style = level_base_spacecraft_error.getStyle();
		level_base_spacecraft_t_error.setFillParent(true);
		level_base_spacecraft_t_error.add(level_base_spacecraft_error)
				.width(VIRTUAL_SCREEN_WIDTH / 1.15f)
				.height(VIRTUAL_SCREEN_WIDTH / 1.15f * 68f / 400f);
		level_base_spacecraft_t_error.bottom();
		settings_get_spacecraft_error.add(level_base_spacecraft_t_error);
		level_base_spacecraft_you_own_t_error = new Table();
		level_base_spacecraft_you_own_t_error.setFillParent(true);
		level_base_spacecraft_you_own_error = new
				Image(new TextureRegionDrawable(atlas.findRegion("error")));
		level_base_spacecraft_you_own_t_error.add(level_base_spacecraft_you_own_error)
				.width(VIRTUAL_SCREEN_WIDTH / 2.8f)
				.height(VIRTUAL_SCREEN_WIDTH / 2.8f * 80f / 256f)
				.padBottom(VIRTUAL_SCREEN_WIDTH / 50f);
		level_base_spacecraft_you_own_t_error.padBottom(VIRTUAL_SCREEN_WIDTH/6.55f);
		settings_get_spacecraft_error.add(level_base_spacecraft_you_own_t_error);
		level_base_spacecraft_you_own_error.addAction(Actions.alpha(0f));
		level_base_spacecraft_error.addAction(Actions.alpha(0f));
		stage.addActor(get_spacecraft_table_error);
		//BUY AMMO-SLOTS --------------------------------------------------------
		TextureRegion ammo_button_tr = skin_replay.getRegion("get_ammos");
		buy_ammo_style = new ImageButtonStyle();
		buy_ammo_style.up = new TextureRegionDrawable(ammo_button_tr);
		buy_ammo_button = new ImageButton(buy_ammo_style);
		buy_ammo_table = new Table();
		buy_ammo_table.add(buy_ammo_button)
				.width(VIRTUAL_SCREEN_WIDTH / 1.3f)
				.height(VIRTUAL_SCREEN_WIDTH / 1.3f * 168f / 512f)
				.padBottom(VIRTUAL_SCREEN_WIDTH / 8f);
		buy_ammo_table.addAction(Actions.alpha(0f));
		buy_ammo_warning_table = new Table();
		buy_ammo_warning = new Image(new
				TextureRegionDrawable(skin_replay.getRegion("warningammoenterX")));
		buy_ammo_warning_table.add(buy_ammo_warning)
				.width(VIRTUAL_SCREEN_WIDTH / 1.7f * resizer)
				.height(VIRTUAL_SCREEN_WIDTH / 1.7f * 120f / 275f)
				.padBottom(VIRTUAL_SCREEN_WIDTH / 9.5f);
		buy_ammo_warning_table.addAction(Actions.alpha(0f));
		settings_get_spacecraft_error.add(buy_ammo_table);
		settings_get_spacecraft_error.add(buy_ammo_warning_table);
		buy_ammo_warning_table.pack();
		buy_ammo_warning_table.setTransform(true);
		buy_ammo_warning_table.setOrigin(Align.center);
		buy_ammo_button.addListener( new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y){
			buy_ammo_button.addAction(sequence(
					moveBy(0f, 12f, 0.2f),
					moveBy(0f, -12f, 0.2f),
					run(new Runnable(){
						public void run(){
							if(!cryo_game.menu_screen.settings.settings.is_this_for_free) {
								if (!still_working_on_menu_press) {
									still_working_on_menu_press = true;
									ProcessBuyAmmoLife(IActionResolver.PRODUCT_AMMO9000);
								}
							} else {
								cryo_game.premium_interface.ShowPremium();
							}
						}})));
			}
		});
		//BUY LIFE-SLOTS --------------------------------------------------------
		TextureRegion life_button_tr = skin_replay.getRegion("get_lifeslots");
		buy_life_style = new ImageButtonStyle();
		buy_life_style.up = new TextureRegionDrawable(life_button_tr);
		buy_life_button = new ImageButton(buy_life_style);
		buy_life_table = new Table();
		buy_life_table.add(buy_life_button)
				.width(VIRTUAL_SCREEN_WIDTH / 1.3f)
				.height(VIRTUAL_SCREEN_WIDTH / 1.3f * 168f / 512f)
				.padBottom(VIRTUAL_SCREEN_WIDTH / 8f);
		buy_life_table.addAction(Actions.alpha(0f));
		buy_life_warning_table = new Table();
		buy_life_warning = new Image(new
				TextureRegionDrawable(skin_replay.getRegion("warninglifeenterX")));
		buy_life_warning_table.add(buy_life_warning)
				.width(VIRTUAL_SCREEN_WIDTH / 1.7f * resizer)
				.height(VIRTUAL_SCREEN_WIDTH / 1.7f * 120f / 275f)
				.padBottom(VIRTUAL_SCREEN_WIDTH / 9.5f);
		buy_life_warning_table.addAction(Actions.alpha(0f));
		buy_life_warning_table.pack();
		buy_life_warning_table.setTransform(true);
		buy_life_warning_table.setOrigin(Align.center);
		settings_get_spacecraft_error.add(buy_life_table);
		settings_get_spacecraft_error.add(buy_life_warning_table);		
		buy_life_button.addListener( new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y){
			buy_life_button.addAction(sequence(
					moveBy(0f, 12f, 0.2f),
					moveBy(0f, -12f, 0.2f),
					run(new Runnable(){
						public void run(){
							if(!cryo_game.menu_screen.settings.settings.is_this_for_free) {
								if (!still_working_on_menu_press) {
									still_working_on_menu_press = true;
									ProcessBuyAmmoLife(IActionResolver.PRODUCT_LIFE9000);
								}
							} else {
								cryo_game.premium_interface.ShowPremium();
							}
						}})));
			}
		});	
		level_base_spacecraft_t_error.pack();
		level_base_spacecraft_error.pack();
	}

	public void CheckPossibleInNeedToFadeOut(){
		if(!setts_for_game.settings.is_this_premium_updated){
			possible_fade_in_add_free = true;
		} else {
			if(!setts_for_game.settings.is_this_unlocked_all_levels){
				possible_fade_in_unlock_free = true;
			}
			if(setts_for_game.settings.is_this_in_need_for_load_saved){
				possible_fade_in_saveload_free = true;
			}
		}
	}

	public void PurchaseAmmo9000FailedHUD(String text){
		text1 = text;
		FadeOutAllBeforeErrorFinished();
		level_base_premium_error.setStyle(textb1x);
		TransitError(text1, true);
		came_failed_from_HUD = false;
	}

	public void PurchaseLife9000FailedHUD(String text){
		text1 = text;
		FadeOutAllBeforeErrorFinished();
		level_base_premium_error.setStyle(textb1x);
		TransitError(text1, true);
		came_failed_from_HUD = false;
	}

	public void PurchaseAmmo9000AllLevelsHUD(){
		ProcessSettingsAndHUDPurchased9000Ammo();
		FadeOutAllBeforeErrorFinished();
		buy_ammo_table.remove();
		level_base_premium_error.setStyle(textb2ok);
		TransitError(ex01Types.PURCHASED_AMMO9000, true);
	}

	public void ProcessSettingsAndHUDPurchased9000Ammo(){
		setts_for_game.settings.counter_ammo_base += 9000;
		setts_for_game.settings.were_currently_limited_on_ammo = false;
		the_game_screen.spaces.no_more_bullets = false;
		currentBulletsCounter = 5;
		counter_ammo_fiver = 5;
		counter_ammo_base += 9000;
		need_to_change_ammo = true;
		need_to_change_hud_data = true;
		SetBulletsCounterTo(currentBulletsCounter);
		setts_for_game.WriteJson();
		cryo_game.menu_screen.get_bullets_screen.UpdateAmmoSlotsHave();
		cryo_game.SAVE_GAME_TO_CLOUD();
	}

	public void PurchaseLife9000AllLevelsHUD(){
		ProcessSettingsAndHUDPurchased9000Life();
		FadeOutAllBeforeErrorFinished();
		buy_life_table.remove();
		level_base_premium_error.setStyle(textb2ok);
		TransitError(ex01Types.PURCHASED_LIFE9000, true);
	}

	public void ProcessSettingsAndHUDPurchased9000Life(){
		setts_for_game.settings.counter_life_base += 9000;
		setts_for_game.settings.were_currently_limited_on_life = false;
		currentHealthCounter = 10;
		counter_life_fiver = 10;
		counter_life_base += 9000;

		need_to_change_life = true;
		need_to_change_hud_data = true;
		SetHealthCounterTo(currentHealthCounter);

		setts_for_game.WriteJson();
		cryo_game.menu_screen.get_lifeslots_screen.UpdateLifeSlotsHave();
		cryo_game.SAVE_GAME_TO_CLOUD();
	}

	public void ProcessBuyAmmoLife(int product){
		still_working_on_menu_press = false;
		bCurrentlyFadedOutDontDraw = true;
		still_working_on_menu_press = true;

		CheckPossibleInNeedToFadeOut();
		result = cryo_game.inappInterfaceResolver.requestIabPurchase(
				product,
				cryo_game.menu_screen.menus.callback_function);
		if(result==RESULT_OK){
		} else { // something went wrong
			still_working_on_menu_press = false;
		}
	}

	public void MaxAngleSetStyleRed(){
		level_base_spacecraft_error.setStyle(textb1);
		old_style = textb1;
	}

	public void MaxAngleSetStyleMaroon(){
		level_base_spacecraft_error.setStyle(textb6failedangle);
		old_style = textb6failedangle;
	}
	
	public void MaxAngleSetStyleGreen(){
		level_base_spacecraft_error.setStyle(textb2ok);
		old_style = textb2ok;
	}	
	
	public void MaxAngleSetStyleBlue(){
		level_base_spacecraft_error.setStyle(textb3level);
		old_style = textb3level;		
	}

	public void MaxAngleSetStyleYellow(){
		level_base_spacecraft_error.setStyle(textb4level);
		old_style = textb4level;		
	}
	
	public void MaxAngleSetStyleBetterAngle(){
		level_base_spacecraft_error.setStyle(textb5betterlevel);
		old_style = textb5betterlevel;		
	}

	public void MaxAngleSetStyleTryAgainBad(){
		level_base_spacecraft_error.setStyle(textb6failedangle);
		old_style = textb6failedangle;
	}
	
	// warning and buy tables and angles - a TABLE containing a STACK CONTAINING ALL
	public void MaxAngleWarningBuyDissapear(float deltatime){
		get_spacecraft_table_error.addAction(Actions.alpha(0f, deltatime));
	}
	
	// warning and buy tables and angles - a TABLE containing a STACK CONTAINING ALL
	public void MaxAngleWarningBuyAppear(float deltatime){
		// warning and buy tables and angles - a TABLE containing a STACK CONTAINING ALL
		get_spacecraft_table_error.addAction(Actions.alpha(1f, deltatime));
		level_base_spacecraft_you_own_t_error.addAction(Actions.alpha(1f, deltatime));
		level_base_spacecraft_you_own_error.addAction(Actions.alpha(1f, deltatime));
		// angles, angles2, angles3 max angle tables - JUST THE BUTTON
		level_base_spacecraft_error.addAction(Actions.alpha(1f,deltatime));
		level_base_spacecraft_t_error.addAction(Actions.fadeIn(deltatime)); 
		settings_get_spacecraft_error.addAction(Actions.fadeIn(deltatime));
		is_stop_click_for_finish = false;
	}
	
	// display life warning and then display the buy button
	public void LifeWarningPlusBuyDisplay(){
		just_rendered_flip_flop_buy_ammolife = true;
		hud_display.buy_life_warning_table.addAction(sequence(
				parallel(sequence(Actions.scaleTo(1.1f, 1.1f, 0.9f),
								Actions.scaleTo(1f, 1f, 0.9f, swing)),
						sequence(Actions.fadeIn(0.9f), Actions.fadeOut(0.9f))),
				parallel(sequence(Actions.scaleTo(1.1f, 1.1f, 0.9f),
								Actions.scaleTo(1f, 1f, 0.9f, swing)),
						sequence(Actions.fadeIn(0.9f), Actions.fadeOut(0.9f))),
				parallel(sequence(Actions.scaleTo(1.1f, 1.1f, 0.9f),
								Actions.scaleTo(1f, 1f, 0.9f, swing)),
						sequence(Actions.fadeIn(0.9f), Actions.fadeOut(0.9f))),
				parallel(sequence(Actions.scaleTo(1.1f, 1.1f, 0.9f),
								Actions.scaleTo(1f, 1f, 0.9f, swing)),
						sequence(Actions.fadeIn(0.9f), Actions.fadeOut(0.9f))),
				run(new Runnable() {
					public void run() {
						hud_display.buy_life_table.addAction(Actions.alpha(1f, 1f));
						hud_display.buy_life_table.toFront();
					}
				})));
	}
	
	public void LifeWarnningPlusBuyDisplaySimple(){
		hud_display.buy_life_table.addAction(Actions.alpha(1f, 1f));
		hud_display.buy_life_table.toFront();		
	}
	
	public void AmmoWarningPlusBuyDisplay(){
		just_rendered_flip_flop_buy_ammolife = true;
		hud_display.resume_button.setStyle(hud_display.resumegr_style);
		hud_display.resume_button_is_grey = true;	
		hud_display.buy_ammo_warning_table.addAction(sequence(parallel(
						sequence(Actions.scaleTo(1.1f, 1.1f, 0.9f),
								Actions.scaleTo(1f, 1f, 0.9f, swing)),
						sequence(Actions.fadeIn(0.9f), Actions.fadeOut(0.9f))),
				parallel(sequence(Actions.scaleTo(1.1f, 1.1f, 0.9f),
								Actions.scaleTo(1f, 1f, 0.9f, swing)),
						sequence(Actions.fadeIn(0.9f), Actions.fadeOut(0.9f))),
				parallel(sequence(Actions.scaleTo(1.1f, 1.1f, 0.9f),
								Actions.scaleTo(1f, 1f, 0.9f, swing)),
						sequence(Actions.fadeIn(0.9f), Actions.fadeOut(0.9f))),
				parallel(sequence(Actions.scaleTo(1.1f, 1.1f, 0.9f),
								Actions.scaleTo(1f, 1f, 0.9f, swing)),
						sequence(Actions.fadeIn(0.9f), Actions.fadeOut(0.9f))),
				run(new Runnable() {
					public void run() {
						hud_display.buy_ammo_table.addAction(Actions.alpha(1f, 1f));
						hud_display.buy_ammo_table.toFront();
					}
				})));
	}
	
	public void MakeResumeGrey(){
		hud_display.resume_button.setStyle(hud_display.resumegr_style);
		hud_display.resume_button_is_grey = true;		
	}
	
	public String ProcessCoinsReward(int coins){
		return Integer.toString(coins) + " Coins!!!";
	}

	// all blockers have been hit and all of them < MAX_ANGLE_TO_UNLOCK_NEXT_LEVEL degree
	public boolean AreAllBlockersDeblocked(){
		return (blocked_deblocked_counter ==
				the_game_screen.world_electrotrap_list.electro_red_list.size()
				&& (current_max_angle<=MAX_ANGLE_TO_UNLOCK_NEXT_LEVEL) );
	}
	
	public boolean OldAngleIsGood(){
		if (setts_for_game.settings.level_angles[level_no-1]<=MAX_ANGLE_TO_UNLOCK_NEXT_LEVEL)
			return true;
		return false;
	}
	
	public void ProcessFinishAngleOnExploded(){
		MaxAngleSetStyleBlue();
		hud_display.level_base_spacecraft_error.setText("Angle to beat: " +
				decimal_transform.format(setts_for_game.settings.level_angles[level_no - 1]));
		MaxAngleWarningBuyAppear(1f);		
	}
	
	public void AppearCoinsWon(){
		settings_get_coinswon_error.addAction(Actions.fadeIn(2f)); 				
		level_base_coinswon_t_error.addAction(Actions.fadeIn(2f)); 		
		level_base_coinswon_error.addAction(Actions.fadeIn(2f)); 		
		//
		get_coinswon_table_error.addAction(Actions.fadeIn(2f)); 		
		level_base_coinswon_error.addAction(Actions.rotateBy(-360f, 3f, Swing.swing));		
	}
	
	public void ResetAllMaxAngleWonInfoTables(){
		hud_display.buy_ammo_table.clearActions();
		hud_display.buy_ammo_table.toBack();
		hud_display.buy_ammo_table.addAction(Actions.alpha(0f, 0f));
		hud_display.buy_life_table.clearActions();
		hud_display.buy_life_table.toBack();
		hud_display.buy_life_table.addAction(Actions.alpha(0f, 0f));

		level_base_spacecraft_error.clearActions();	
		get_spacecraft_table_error.clearActions();    
		level_base_spacecraft_you_own_t_error.clearActions();
		level_base_spacecraft_you_own_error.clearActions();
		level_base_coinswon_error.clearActions(); 
		settings_get_spacecraft_error.clearActions();
		settings_get_coinswon_error.clearActions(); 				
		level_base_coinswon_t_error.clearActions(); 		
		level_base_coinswon_error.clearActions(); 		
		get_coinswon_table_error.clearActions(); 		
		level_base_coinswon_error.clearActions();
		//
		level_base_spacecraft_error.setRotation(0f);
		get_spacecraft_table_error.setRotation(0f);
		level_base_spacecraft_you_own_t_error.setRotation(0f);
		level_base_coinswon_error.setRotation(0f);
		level_base_coinswon_t_error.setRotation(0f);
		level_base_coinswon_error.setRotation(0f);
		get_coinswon_table_error.setRotation(0f);
		level_base_coinswon_error.setRotation(0f);
		//
		get_spacecraft_table_error.addAction(Actions.alpha(1f));
		settings_get_spacecraft_error.addAction(Actions.alpha(1f));
		level_base_spacecraft_t_error.addAction(Actions.alpha(1f));
		level_base_spacecraft_error.addAction(Actions.alpha(1f));
		level_base_spacecraft_you_own_t_error.addAction(Actions.alpha(1f));
		level_base_spacecraft_you_own_error.addAction(Actions.alpha(1f));
	}

	public boolean is_stop_click_for_finish = false;

	// if all blockers are de-blocked then we set the COINS_WON style or
	// grey if angle is > ang1(the biggest angle)
	// angle3 is min, angle1 is max - type_level_finished = 0 the smallest angle
	public void ProcessFinishAngleData(int ang1, int ang2, int ang3){
		is_stop_click_for_finish = true;
		if( AreAllBlockersDeblocked() ){
			//*************************************************************************************
			if(current_max_angle<=ang3){
				ProcessSettingsAndUIAng3();
				ProcessStarRotation();
				//setup check-boxes
				ProcessAngleStars(
						setts_for_game.settings.levels.get(level_no - 1).level1_angle,
						setts_for_game.settings.levels.get(level_no - 1).level2_angle,
						setts_for_game.settings.levels.get(level_no - 1).level3_angle);
				ProcessNewMinAngle();

				//process graphics
				ProcessFinishGraphics(json_settings.OVER_BASE_MIN_ANG1, textb1_coinswon);
			//*************************************************************************************
			} else if(current_max_angle<=ang2 && current_max_angle>ang3) {
				ProcessSettingsAndUIAng23();
				ProcessStarRotation();
				//setup check-boxes
				ProcessAngleStars(
						setts_for_game.settings.levels.get(level_no-1).level1_angle,
						setts_for_game.settings.levels.get(level_no-1).level2_angle,
						setts_for_game.settings.levels.get(level_no-1).level3_angle);
				ProcessNewMinAngle();

				//process graphics
				ProcessFinishGraphics(json_settings.OVER_BASE_MIN_ANG2, textb2_coinswon);

			//*************************************************************************************
			} else if(current_max_angle<=ang1 && current_max_angle>ang2) {
				ProcessSettingsAndUIAng12();
				ProcessStarRotation();
				//setup check-boxes
				ProcessAngleStars(
						setts_for_game.settings.levels.get(level_no-1).level1_angle,
						setts_for_game.settings.levels.get(level_no-1).level2_angle,
						setts_for_game.settings.levels.get(level_no-1).level3_angle);
				ProcessNewMinAngle();

				//process graphics
				ProcessFinishGraphics(json_settings.OVER_BASE_MIN_ANG3, textb3_coinswon);

			//*************************************************************************************
			} else if(current_max_angle>ang1) {
				ProcessSettingsAndUIAng1();
				ProcessStarsInflateGraphics();
				//setup check-boxes
				ProcessAngleStars(
						setts_for_game.settings.levels.get(level_no-1).level1_angle,
						setts_for_game.settings.levels.get(level_no-1).level2_angle,
						setts_for_game.settings.levels.get(level_no-1).level3_angle);
				ProcessNewMinAngle();

				//process graphics
				ProcessFinishGraphicsWorst();
			}
			if(level_no<ex01Types.LEVEL_MAX) {
				ProcessClearNextLevel();
				ProcessGreyNextPrevButtonsOnLevelUnlock();
			}
			// may need to put outside of this IF cl. if we modify something else like max angle
			t_write_json.run();
		} else { // if( AreAllBlockersDeblocked() ) 					
			CheckToSeeIfLessAmmoThanMinimum(setts_for_game);
			CheckToSeeIfLessLifeThanMinimum(setts_for_game);
			if(!just_rendered_flip_flop_buy_ammolife){
				MaxAngleSetStyleRed();
				MaxAngleWarningBuyAppear(1f);
				hud_display.level_base_spacecraft_error.setText("Unlock all blockers!");
			}
			t_write_json.run(); // needed for tries
		}
		angle_was_better = false;

		if(cryo_game.menu_screen.menus.bIsLoggedIn) {
			// level_no = 1 (la init)
			SubmitScoreToGoogleLeaderboard(
					level_no,
					(long) (setts_for_game.settings.level_angles[level_no - 1] * 100));
		}
	}

	public void ProcessFinishGraphics(int min_angle123, TextButtonStyle style){
		if(level_no<ex01Types.LEVEL_MAX) {
			if (!(cryo_game.levels_screen.level_selector.levels.get(level_no).is_unlocked)) {
				ProcessCurrentCoinsWon(min_angle123);
				MaxAngleSetStyleYellow();
				ProcessClearNextLevelGraphics();
				ProcessAppearCoinsWon(style);
			} else {
				if (angle_was_better) {
					ProcessCurrentCoinsWon(min_angle123);
					MaxAngleSetStyleBetterAngle();
					ProcessNewBestAngleGraphics();
					ProcessAppearCoinsWon(style);
				} else {
					ProcessCurrentCoinsWon(min_angle123);
					ProcessAppearCoinsWonUI(style);
				}
			}
		} else {
			if (angle_was_better) {
				ProcessCurrentCoinsWon(min_angle123);
				MaxAngleSetStyleBetterAngle();
				ProcessNewBestAngleGraphics();
				ProcessAppearCoinsWon(style);
			} else {
				ProcessCurrentCoinsWon(min_angle123);
				ProcessAppearCoinsWonUI(style);
			}
		}
	}

	public void ProcessFinishGraphicsWorst(){
		if(level_no<ex01Types.LEVEL_MAX) {
			if (!(cryo_game.levels_screen.level_selector.levels.get(level_no).is_unlocked)) {
				MaxAngleSetStyleYellow();
				ProcessClearNextLevelGraphics();
				ProcessAppearTryAgain(
						ex01Types.DELAY_PROC_APPEAR,
						ex01Types.DELAY_PROC_APPEAR);
			} else {
				if (angle_was_better) {
					MaxAngleSetStyleBetterAngle();
					ProcessNewBestAngleGraphics();
					ProcessAppearTryAgain(
							ex01Types.DELAY_PROC_APPEAR,
							ex01Types.DELAY_PROC_APPEAR);
				} else {
					MaxAngleSetStyleTryAgainBad();
					ProcessAppearTryAgain(
							ex01Types.DELAY_PROC_APPEAR,
							ex01Types.DELAY_PROC_APPEAR);
				}
			}
		} else {
			if (angle_was_better) {
				MaxAngleSetStyleBetterAngle();
				ProcessNewBestAngleGraphics();
				ProcessAppearTryAgain(
						ex01Types.DELAY_PROC_APPEAR,
						ex01Types.DELAY_PROC_APPEAR);
			} else {
				MaxAngleSetStyleTryAgainBad();
				ProcessAppearTryAgain(
						ex01Types.DELAY_PROC_APPEAR,
						ex01Types.DELAY_PROC_APPEAR);
			}
		}
	}

	public void SubmitScoreToGoogleLeaderboard(int level_number, long score){
	}

	public void ProcessStarsInflateGraphics(){
		angles_how_wedoing3.addAction(sequence(
				Actions.scaleTo(1.1f, 1.1f, 2f),
				Actions.scaleTo(1f, 1f, 2f)));
		angles_how_wedoing2.addAction(sequence(
				Actions.scaleTo(1.1f, 1.1f, 2f),
				Actions.scaleTo(1f, 1f, 2f)));
		angles_how_wedoing1.addAction(sequence(
				Actions.scaleTo(1.1f, 1.1f, 2f),
				Actions.scaleTo(1f, 1f, 2f)));
	}

	public void ProcessStarRotation(){
		if(can_rotate3_star_chk){
			angles_how_wedoing3.addAction(Actions.rotateBy(360f, 4f, Swing.swing));
		} else {
			angles_how_wedoing3.addAction(sequence(
					Actions.scaleTo(1.1f, 1.1f, 2f),
					Actions.scaleTo(1f, 1f, 2f)));
		}
		if(can_rotate2_star_chk){
			angles_how_wedoing2.addAction(Actions.rotateBy(360f, 4f, Swing.swing));
		} else {
			angles_how_wedoing2.addAction(sequence(
					Actions.scaleTo(1.1f, 1.1f, 2f),
					Actions.scaleTo(1f, 1f, 2f)));
		}
		if(can_rotate1_star_chk){
			angles_how_wedoing1.addAction(Actions.rotateBy(360f, 4f, Swing.swing));
		} else {
			angles_how_wedoing1.addAction(sequence(
					Actions.scaleTo(1.1f, 1.1f, 2f),
					Actions.scaleTo(1f, 1f, 2f)));
		}
	}

	public void ProcessSettingsAndUIAng3(){
		can_rotate3_star_chk = false;
		can_rotate2_star_chk = false;
		can_rotate1_star_chk = false;
		if(!setts_for_game.settings.levels.get(level_no-1).is_mission_1_achieved)
			can_rotate1_star_chk = true;
		if(!setts_for_game.settings.levels.get(level_no-1).is_mission_2_achieved)
			can_rotate2_star_chk = true;
		if(!setts_for_game.settings.levels.get(level_no-1).is_mission_3_achieved)
			can_rotate3_star_chk = true;
		setts_for_game.settings.levels.get(level_no-1).is_mission_3_achieved = true;
		setts_for_game.settings.levels.get(level_no-1).is_mission_2_achieved = true;
		setts_for_game.settings.levels.get(level_no-1).is_mission_1_achieved = true;
		cryo_game.levels_screen.level_selector.levels.get(level_no-1).level_3_achieved = true;
		cryo_game.levels_screen.level_selector.levels.get(level_no-1).level_2_achieved = true;
		cryo_game.levels_screen.level_selector.levels.get(level_no-1).level_1_achieved = true;
		cryo_game.levels_screen.level_selector.levels.get(level_no-1).is_closed = false;
		cryo_game.levels_screen.level_selector.levels.get(level_no-1).is_unlocked = true;
	}

	public void ProcessSettingsAndUIAng23(){
		can_rotate3_star_chk = false;
		can_rotate2_star_chk = false;
		can_rotate1_star_chk = false;
		if(!setts_for_game.settings.levels.get(level_no-1).is_mission_1_achieved)
			can_rotate1_star_chk = true;
		if(!setts_for_game.settings.levels.get(level_no-1).is_mission_2_achieved)
			can_rotate2_star_chk = true;
		setts_for_game.settings.levels.get(level_no-1).is_mission_2_achieved = true;
		setts_for_game.settings.levels.get(level_no-1).is_mission_1_achieved = true;
		cryo_game.levels_screen.level_selector.levels.get(level_no-1).level_2_achieved = true;
		cryo_game.levels_screen.level_selector.levels.get(level_no-1).level_1_achieved = true;
		cryo_game.levels_screen.level_selector.levels.get(level_no-1).is_closed = false;
		cryo_game.levels_screen.level_selector.levels.get(level_no-1).is_unlocked = true;
	}

	public void ProcessSettingsAndUIAng12(){
		can_rotate3_star_chk = false;
		can_rotate2_star_chk = false;
		can_rotate1_star_chk = false;
		if(!setts_for_game.settings.levels.get(level_no-1).is_mission_1_achieved)
			can_rotate1_star_chk = true;
		setts_for_game.settings.levels.get(level_no-1).is_mission_1_achieved = true;
		cryo_game.levels_screen.level_selector.levels.get(level_no-1).level_1_achieved = true;
		cryo_game.levels_screen.level_selector.levels.get(level_no-1).is_closed = false;
		cryo_game.levels_screen.level_selector.levels.get(level_no-1).is_unlocked = true;
	}

	public void ProcessSettingsAndUIAng1(){
		can_rotate3_star_chk = false;
		can_rotate2_star_chk = false;
		can_rotate1_star_chk = false;
		cryo_game.levels_screen.level_selector.levels.get(level_no-1).is_closed = false;
		cryo_game.levels_screen.level_selector.levels.get(level_no-1).is_unlocked = true;
	}

	public void ProcessClearNextLevel(){
		// unlock next level
		setts_for_game.settings.levels.get(level_no).is_unlocked = true;
		setts_for_game.settings.levels.get(level_no).is_closed = false;
		// set last level no unlocked / used for level navigation
		if(!setts_for_game.settings.is_this_premium_updated)
			setts_for_game.settings.no_level_unlocked = level_no+1;
		cryo_game.levels_screen.level_selector.levels.get(level_no).is_unlocked = true;
		cryo_game.levels_screen.level_selector.levels.get(level_no).is_closed = false;
	}

	public void ProcessClearNextLevelGraphics(){
		level_base_spacecraft_error.addAction(Actions.rotateBy(-360f, 3f, Swing.swing));
		level_base_spacecraft_error.setText("Next Level Cleared!");
		get_spacecraft_table_error.addAction(Actions.alpha(1f, 1f));
		level_base_spacecraft_you_own_t_error.addAction(Actions.alpha(1f, 1f));
		level_base_spacecraft_you_own_error.addAction(Actions.alpha(1f, 1f));
	}

	//get_spacecraft_table_error contains all of the below (practically max angle)
	public void ProcessNewBestAngleGraphics(){
		level_base_spacecraft_error.addAction(Actions.rotateBy(-360f, 3f, Swing.swing));
		level_base_spacecraft_error.setText("New best angle: " +
				decimal_transform.format(setts_for_game.settings.level_angles[level_no-1]));
		get_spacecraft_table_error.addAction(Actions.alpha(1f, 1f));
		level_base_spacecraft_you_own_t_error.addAction(Actions.alpha(1f, 1f));
		level_base_spacecraft_you_own_error.addAction(Actions.alpha(1f, 1f));
	}

	public void ProcessCurrentCoinsWon(int add_coins){
		current_coins_won = setts_for_game.settings.level_wincoins[level_no-1] + add_coins;
		setts_for_game.settings.number_coins += current_coins_won;
		if(current_max_angle<setts_for_game.settings.level_angles[level_no-1]){
			setts_for_game.settings.level_angles[level_no-1] = current_max_angle;
		}
	}

	public void ProcessNewMinAngle(){
		if(current_max_angle<setts_for_game.settings.level_angles[level_no-1] &&
		   current_max_angle<=MAX_ANGLE_TO_UNLOCK_NEXT_LEVEL	){
			angle_was_better = true;
			setts_for_game.settings.level_angles[level_no-1] = current_max_angle;
		}
	}

	public void ProcessAppearCoinsWonUI(TextButtonStyle style){
		MaxAngleWarningBuyDissapear(3f);
		AppearCoinsWon();
		hud_display.level_base_coinswon_error.setStyle(style);
		hud_display.level_base_coinswon_error.setText(ProcessCoinsReward(current_coins_won));
		hud_display.level_base_coinswon_error.addAction(sequence(
			Actions.delay(3f),
			Actions.fadeOut(5f),
			Actions.run(new Runnable() {
				public void run() {
					MaxAngleSetStyleBlue();
					hud_display.level_base_spacecraft_error.setText("Angle to beat: " +
							decimal_transform.format(setts_for_game.settings
									.level_angles[level_no - 1]));
					MaxAngleWarningBuyAppear(1f);
				}
			})));
	}

	public void ProcessAppearTryAgainUI(float delay){
		MaxAngleWarningBuyDissapear(3f);
		AppearCoinsWon();
		hud_display.level_base_coinswon_error.setStyle(textb4_coinswon);
		hud_display.level_base_coinswon_error.setText("Try again!");
		hud_display.level_base_coinswon_error.addAction(sequence(
				Actions.delay(delay),
				Actions.fadeOut(5f),
				Actions.run(new Runnable() {
					public void run() {
						if (OldAngleIsGood()) {
							MaxAngleSetStyleBlue();
							hud_display.level_base_spacecraft_error
									.setText("Angle to beat: " + decimal_transform.format(
											setts_for_game.settings.level_angles[level_no - 1]));
						} else {
							MaxAngleSetStyleRed();
							hud_display.level_base_spacecraft_error
									.setText("Angle to beat: " + decimal_transform.format(
											setts_for_game.settings.level_angles[level_no - 1]));
						}
						MaxAngleWarningBuyAppear(1f);
					}
				})));
	}

	public TextButtonStyle stylein;
	public float delay22;

	public void ProcessAppearTryAgain(float delay1, float delay2){
		delay22 = delay2;
		level_base_spacecraft_error.addAction(sequence(
				Actions.alpha(1f,1f),
				Actions.delay(delay1),
				run(new Runnable(){
					public void run(){
						ProcessAppearTryAgainUI(delay22);
					}
				})));
	}

	public void ProcessAppearCoinsWon(TextButtonStyle style){
		stylein = style;
		level_base_spacecraft_error.addAction(sequence(
				Actions.alpha(1f, 1f),
				Actions.delay(4f),
				run(new Runnable() {
					public void run() {
						ProcessAppearCoinsWonUI(stylein);
					}
				})));
	}

	//angle3 is min, angle1 is max - type_level_finished = 0 the smallest angle
	public void ProcessPartialAngleData(int ang1, int ang2, int ang3){
		if(current_max_angle<=ang3 || (current_max_angle<=ang2 && current_max_angle>ang3)
				   				   || (current_max_angle<=ang1 && current_max_angle>ang2) ) {
			if(current_max_angle<setts_for_game.settings.level_angles[level_no-1]){
				MaxAngleSetStyleGreen();
			} else {
				MaxAngleSetStyleMaroon();
			}
		} else if(current_max_angle>ang1) {
			MaxAngleSetStyleRed();
		}
		hud_display.level_base_spacecraft_error
				.setText("Angle to beat: " + decimal_transform
						.format(setts_for_game.settings.level_angles[level_no - 1]));
	}
	
	public void ProcessGreyNextPrevButtonsOnLevelUnlock(){
		
	}
	
	public void ShowInfoForSimplyPaused(){
		hud_display.table_life_bullets_text.addAction(Actions.alpha(0.3f,3f));
		hud_display.table_counter_angles_text.addAction(Actions.alpha(0.3f,3f));
		hud_display.table_fps.addAction(Actions.alpha(0.3f,6f));
		hud_display.level_base_spacecraft_you_own_error.addAction(Actions.alpha(1f));
		hud_display.level_base_spacecraft_error.addAction(Actions.alpha(1f));
	}

	public void AddClickRestartListenerBegin(){
		restart_button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!bCurrentlyFadedOutDontDraw && !is_stop_click_for_finish) {
					added_render_VALUE = false;
					still_working_on_menu_press = false;
					if (cryo_game.menu_screen.settings.settings.sounds_on)
						cryo_game.menu_screen.done_button.play(DONE_BUTTON_VOLUME);
					ROTATOR_RESET_ExitLevelSoResetErrorInfoHUD();
					restart_button.clearActions();
					restart_button.addAction(sequence(
							moveBy(0f, 12f, 0.1f),
							moveBy(0f, -12f, 0.2f),
							run(new Runnable() {
								public void run() {
									if (added) {
										if (setts_for_game.settings.is_this_premium_updated) {
											ProcessRestartNoNeedVALUE();
										} else {
											if (CanWorkOnAdNow()) {
												added_render_VALUE = false;
												ProcessNeedToGoVALUE();
											} else {
												ProcessRestartNoNeedVALUE();
											}
										}
									}
								}
							})));
				} else {
					restart_button.addAction(sequence(
							moveBy(0f, 12f, 0.1f),
							moveBy(0f, -12f, 0.2f)));
				}
			}
		});	
	}
	
    public void ProcessNeedToGoVALUE(){
    	cryo_game.menu_screen.settings.settings.in_game_adview_time_ask_counter++;    	
    	if(cryo_game.menu_screen.settings.settings.in_game_adview_time_ask_counter ==
				    json_settings.IN_GAME_ADVIEW_TIME_ASK
			     	&& !ex01Types.BLOCK_VIEW_REWARD){
			added_render_VALUE = true;
    		cryo_game.menu_screen.settings.settings.in_game_adview_time_ask_counter = 0;
			stage.addAction(Actions.fadeOut(2f));
			VALUE_screen.exit_dialog.addAction(parallel(
					fadeIn(1.25f),
					moveBy(0f, 50f, 1.25f, swingOut)));
			VALUE_screen.stage_exit_dialog.addAction(Actions.alpha(1f));
			stage.getBatch().setShader(cryo_game.menu_screen.shader_grayscale);		
			the_game_screen.inputMultiplexer.removeProcessor(stage);
			the_game_screen.inputMultiplexer.addProcessor(VALUE_screen.stage_exit_dialog);
    	} else {
			VALUE_screen.ShowShortVALUEHUD();
    	}
		cryo_game.menu_screen.settings.WriteJson();
    }

	public void ProcessNeedToGoVALUE_MainMenu(){
		cryo_game.menu_screen.settings.settings.in_game_adview_time_ask_counter++;
		if(cryo_game.menu_screen.settings.settings.in_game_adview_time_ask_counter ==
				json_settings.IN_GAME_ADVIEW_TIME_ASK
				&& !ex01Types.BLOCK_VIEW_REWARD){

		} else {
			VALUE_screen.ShowShortVALUEHUD_MainMenu();
		}
		//cryo_game.menu_screen.settings.WriteJson();
	}

	public void UpdateTries(){
		cryo_game.menu_screen.settings.settings.level_notries[level_no-1]+=1;
	}

	public void UpdateTriesHUD(){
		cryo_game.menu_screen.scores_dialog_screen.ProcessMaxAndTriesGetFromSettings();
	}

    public void ProcessRestartNoNeedVALUE(){
		can_rotate1_star_chk = false;
		can_rotate2_star_chk = false;
		can_rotate3_star_chk = false;
		the_game_screen.mloader.loading_now = true;
		the_game_screen.mloader.can_change_screen = false;
		the_game_screen.mloader.started_procedure = false;
		the_game_screen.mloader.loadingLoaderCount = 0.000f;
		the_game_screen.inputMultiplexer.removeProcessor(stage);
		the_game_screen.grayness_increase_more_reversed = true;
		the_game_screen.grayness_increase_more_enough = false;
		stage.addAction(sequence(Actions.fadeOut(2f), Actions.run(new Runnable() {
			public void run() {
				ResetAllMaxAngleWonInfoTables();
				if (primary_level_no != level_no) {
					angle1 = setts_for_game.settings.levels.get(level_no).level1_angle;
					angle2 = setts_for_game.settings.levels.get(level_no).level2_angle;
					angle3 = setts_for_game.settings.levels.get(level_no).level3_angle;
					cryo_game.game_state = GameState.GAME_LOADING_SCREEN;
					cryo_game.loader.ex01MenuLoadingScreenReload(
							setts_for_game,
							cryo_game,
							level_no,
							setts_for_game.settings.index_spaceship_selected,
							(int) angle1,
							(int) angle2,
							(int) angle3);
					cryo_game.setScreen(cryo_game.loader);
					the_game_screen.came_from_within_game = true;
					just_rendered_flip_flop_buy_ammolife = false;
				} else {
					stage.act(Gdx.graphics.getDeltaTime()*SPEED_HUD);
					the_game_screen.just_rendered_pause = false;
					the_game_screen.paused_game_screen = false;
					the_game_screen.ResetHudSpaceshipAndVariables();
					ResetAmmoLifeFromSettings(the_game_screen, setts_for_game);
					showed = false;
					added = false;
					the_game_screen.already_added_replay = false;
					the_game_screen.MakeGreyShadersNoActive();
					the_game_screen.spaces.ResetSoundsFromPaused();
					just_rendered_flip_flop_buy_ammolife = false;
				}
				hud_display.added_render_VALUE = false;
			}
		})));	    	
    }
    
	public void AddClickRestartListener(){
		if(!added){
			the_game_screen.inputMultiplexer.addProcessor(stage);
			added = true;
		}
	}

	public void AddClickResumeListenerBegin(){
		resume_button.addListener( new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y){
			if(!resume_button_is_grey && !bCurrentlyFadedOutDontDraw
									  && !is_stop_click_for_finish){
				if(cryo_game.menu_screen.settings.settings.sounds_on)
					cryo_game.menu_screen.done_button.play(DONE_BUTTON_VOLUME);
				resume_button.clearActions();
				resume_button.addAction(sequence(
						moveBy(0f, 12f, 0.1f),
						moveBy(0f, -12f, 0.2f),
						run(new Runnable(){
							public void run(){
								if(added){
									the_game_screen.inputMultiplexer.removeProcessor(stage);
									the_game_screen.grayness_increase_more_reversed = true;
									the_game_screen.grayness_increase_more_enough = false;
									stage.addAction(sequence(
											Actions.fadeOut(2f),
											Actions.run(new Runnable(){
											public void run(){
												stage.addAction(Actions.fadeOut(2f));
												table_main_menu.addAction(Actions.fadeOut(2f));
												stage.act(Gdx.graphics.getDeltaTime()*SPEED_HUD);
												the_game_screen.just_rendered_pause = false;
												the_game_screen.paused_game_screen = false;
												showed = false; added = false;
												the_game_screen.already_added_replay = false;
												the_game_screen.MakeGreyShadersNoActive();
												the_game_screen.spaces
														.ResetPauseNotFinishedRotation();
												the_game_screen.DisappearHUDOnFail();
												the_game_screen.spaces.ResumeSoundsFromPaused();
												level_base_spacecraft_you_own_error
														.addAction(Actions.alpha(0f));
												level_base_spacecraft_error
														.addAction(Actions.alpha(0f));
												level_no = primary_level_no;
												ResetBecauseOfResume();
											}
									})));
								}
							}
				})));					
				} else {
				resume_button.addAction(sequence(
						moveBy(0f, 12f, 0.1f),
						moveBy(0f, -12f, 0.2f)));
				}
			}
		});	
	}	
	
	public void ResetBecauseOfResume() {
		ROTATOR_RESET_ExitLevelSoResetErrorInfoHUD();
		HudLevelInitIntString();
		HudLevelReplayResume();
		ProcessAngleStars(
				setts_for_game.settings.levels.get(level_no - 1).level1_angle,
				setts_for_game.settings.levels.get(level_no - 1).level2_angle,
				setts_for_game.settings.levels.get(level_no - 1).level3_angle);
	}
		
	public void AddClickResumeListener(){
		if(!added){
			the_game_screen.inputMultiplexer.addProcessor(stage);
			added = true;
		}
	}	

	public void ResetHudDisplayForInGameReset(){
		no_more_bullets_recorded = false;
		no_more_bullets_recorded_done = false;
		appeared_from_exploded_or_finished_pass = false;
		resume_button.setStyle(hud_display.resume_style);
		resume_button_is_grey = false;
		get_coinswon_table_error.addAction(Actions.fadeOut(0f));
		startupCounter_Timer = 0;
		startupCounter_ArrayCount = 0;
		hasStartupCounter = true;
		startTimerFinished = false;
		ResetDisplayPosReload(); 	
		added = false; showed = false;
		// added used for Scene2D no bullets dialog
		added_nobullets = false;
		no_more_bullets_recorded = false;
		no_more_bullets_recorded_done = false;		
	}
	
	//update bullets rotation and replay stage when we die or pause
	public void UpdateFUDDisplay(float delta){
		stage.act(delta*SPEED_HUD_REPLAY);
	}
    
	public void PropagateAmmoLifeFromToSettings(){
		if(counter_ammo_base==0){
		}
		if(counter_life_base==0){
		}
		setts_for_game.settings.counter_ammo_base = counter_ammo_base;
		setts_for_game.settings.counter_life_base = counter_life_base;
		setts_for_game.settings.last_ammo_fiver_no = counter_ammo_fiver;
		setts_for_game.settings.last_life_fiver_no = counter_life_fiver;
		setts_for_game.settings.counter_bullets_fired = the_game_screen.spaces.counter_shoot;
		t_write_json.run();
	}    
    
	//render FPS string and replay Scene2D stage
	public void renderDisplayFUDs(boolean exploded,
								  boolean exploded_finished,
								  float delta,
								  ex01CryoHUD hud){
		if(hasStartupCounter){
			rotative_counter.setText(CDTFS_string);
			stage_rotative_counter.act(delta*SPEED_HUD);
			stage_rotative_counter.draw();
			hud.ProcessAlphaTransition();
		}	
		if(exploded){
			if(!no_more_bullets_recorded){
				if(!showed) {
					table_main_menu.addAction(Actions.fadeIn(2f));
					stage.addAction(Actions.fadeIn(2f));
					showed = true;
				}
				stage.act(delta*SPEED_HUD_REPLAY);
				stage.draw();				
			} else {
				if(!showed) {
					table_main_menu.addAction(Actions.fadeIn(2f));
					stage.addAction(Actions.fadeIn(2f));
					showed = true;
				}
				stage.act(delta*SPEED_HUD_REPLAY);
				stage.draw();	
			}
		} else if (exploded_finished){
			if(!showed) {
				table_main_menu.addAction(Actions.fadeIn(2f));
				stage.addAction(Actions.fadeIn(2f));
				showed = true;
			}
			stage.act(delta*SPEED_HUD_REPLAY);
			stage.draw(); 		
		};
		if(!cryo_game.menu_screen.settings.settings.is_this_for_free) {
			RenderExtra(delta);
		}
	}

	public void DisposeHUDDisplay_ResumeReplay(){
		table_general_pause.clear();
		table_replay_resume.clear();
		table_main_menu.clear();
		image_pause_table.clear();
		get_coinswon_table_error.clear();
		level_base_coinswon_t_error.clear();
		get_spacecraft_table_error.clear();
		level_base_spacecraft_t_error.clear();
		level_go_left_right.clear();
		level_no_table.clear();
		table_general_pause_back.clear();
		table_angles_how_wedoing.clear();
		level_base_spacecraft_you_own_t_error.clear();
		buy_ammo_warning_table.clear();
		buy_life_warning_table.clear();
		buy_ammo_table.clear();
		buy_life_table.clear();
		table_general_pause_stack.clear();
		settings_get_coinswon_error.clear();
		settings_get_spacecraft_error.clear();
		stage.clear();
		image_general_pause_back.clear();
		restart_button.clear();
		resume_button.clear();
		main_menu_button.clear();
		level_go_left.clear();
		level_go_right.clear();
		buy_ammo_button.clear();
		buy_life_button.clear();
		level_no_button.clear();
		level_base_spacecraft_you_own_error.clear();
		buy_ammo_warning.clear();
		buy_life_warning.clear();
		angles_how_wedoing1.clear();
		angles_how_wedoing2.clear();
		angles_how_wedoing3.clear();
		level_base_coinswon_error.clear();
		level_base_spacecraft_error.clear();

		table_general_pause = null;
		table_replay_resume = null;
		table_main_menu = null;
		image_pause_table = null;
		get_coinswon_table_error = null;
		level_base_coinswon_t_error = null;
		get_spacecraft_table_error = null;
		level_base_spacecraft_t_error = null;
		level_go_left_right = null;
		level_no_table = null;
		table_general_pause_back = null;
		table_angles_how_wedoing = null;
		level_base_spacecraft_you_own_t_error = null;
		buy_ammo_warning_table = null;
		buy_life_warning_table = null;
		buy_ammo_table = null;
		buy_life_table = null;
		table_general_pause_stack = null;
		settings_get_coinswon_error = null;
		settings_get_spacecraft_error = null;
		if(stage!=null) stage.dispose(); stage = null;
		image_general_pause_back = null;
		restart_button = null;
		resume_button = null;
		main_menu_button = null;
		level_go_left = null;
		level_go_right = null;
		buy_ammo_button = null;
		buy_life_button = null;
		level_no_button = null;
		restart_style = null;
		resume_style = null;
		resumegr_style = null;
		buy_ammo_style = null;
		buy_life_style = null;
		level_base_spacecraft_you_own_error = null;
		buy_ammo_warning = null;
		buy_life_warning = null;
		angles_how_wedoing1 = null;
		angles_how_wedoing2 = null;
		angles_how_wedoing3 = null;
		level_base_coinswon_error = null;
		level_base_spacecraft_error = null;
		textb1_coinswon = null;
		textb2_coinswon = null;
		textb3_coinswon = null;
		textb4_coinswon = null;
		textb1 = null;
		textb2ok = null;
		textb3level = null;
		textb4level = null;
		textb5betterlevel = null;
		textb6failedangle = null;
		old_style = null;
		chk_style = null;
		level_name = null;
		old_level_string_error_info = null;
		viewport = null;
		camera = null;
		r_write_json = null;
		t_write_json = null;
		stylein = null;
	}
	
	// resume/replay dialog
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/



	//--------------------------------------------------------------------------------------------//
	//--------------------------------------------------------------------------------------------//
	//--------------------------------------------------------------------------------------------//



	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/

	public ex01JavaInterface VALUE_screen;
	public Table level_base_coins_t_bought;
	public Table level_base_coins_you_own_t_bought;
	public Table get_money_table_bought;
	public Table ShareGoogleFacebookTable;
	public Image level_base_coins_you_own_bought;
    public TextButton level_base_coins_bought;
    public Stack settings_get_money_bought;
	public ImageButton ShareFacebookButton;
	public ImageButton ShareGoogleButton;
	public ImageButtonStyle StyleFacebook;
	public ImageButtonStyle StyleGoogle;
	public boolean still_working_on_done_press = false;
	public boolean share_pressed = false;
	public boolean clicked_google_screencap = false;
	public boolean clicked_facebook_screencap = false;
    
	public void InitCoinsBought(TextureAtlas atlas, Skin skin){
    	//INIT the owned tables
		get_money_table_bought = new Table();
		//this stack contains the owned stuff
		settings_get_money_bought = new Stack();		
		get_money_table_bought.add(settings_get_money_bought)
				.width(VIRTUAL_SCREEN_WIDTH/1.6f)
				.height(VIRTUAL_SCREEN_WIDTH / 1.6f * 85f / 384f);
		get_money_table_bought.setFillParent(true);
		get_money_table_bought.setTransform(true);
		get_money_table_bought.top().padTop(VIRTUAL_SCREEN_WIDTH/7.25f);
		get_money_table_bought.addAction(moveBy(0f,-25f,0f)); 
		level_base_coins_t_bought = new Table();
		level_base_coins_t_bought.setTransform(true);
		level_base_coins_bought = new TextButton("50 COINS", skin, "won_have2");	
		level_base_coins_t_bought.setFillParent(true);
		level_base_coins_t_bought.add(level_base_coins_bought)
				.width(VIRTUAL_SCREEN_WIDTH/1.65f)
				.height(VIRTUAL_SCREEN_WIDTH / 1.65f * 85f / 384f);
		level_base_coins_t_bought.bottom();
		settings_get_money_bought.add(level_base_coins_t_bought);
		level_base_coins_you_own_t_bought = new Table();
		level_base_coins_you_own_t_bought.setFillParent(true);
		level_base_coins_you_own_bought = new
				Image(new TextureRegionDrawable(atlas.findRegion("youwon")));
		level_base_coins_you_own_t_bought.add(level_base_coins_you_own_bought)
										 .width(VIRTUAL_SCREEN_WIDTH / 3f)
										 .height(VIRTUAL_SCREEN_WIDTH / 3f * 80f / 256f);
		level_base_coins_you_own_t_bought.padBottom(VIRTUAL_SCREEN_WIDTH/6.55f);
		settings_get_money_bought.add(level_base_coins_you_own_t_bought);
		level_base_coins_you_own_bought.addAction(Actions.alpha(0f));
		level_base_coins_bought.addAction(Actions.alpha(0f));
		VALUE_screen.stage_exit_dialog.addActor(get_money_table_bought);
	}  
	
	public void EnterWonCoinsPhase(){
		if(setts_for_game.settings.sounds_on)
			cryo_game.menu_screen.cash_register_coins.play(SND_VOL.SOUND_BUY_COINS);
		level_base_coins_bought.setTransform(true);
		get_money_table_bought.clearActions();
		level_base_coins_bought.setOrigin(Align.center);
		level_base_coins_you_own_bought.setOrigin(Align.center);		
		setts_for_game.settings.number_coins += 50;
		cryo_game.menu_screen.get_spacecraft_dialog_screen.level_base_coins
				.getLabel().setText(Integer.toString(setts_for_game.settings.number_coins) +
				" Coins");
		cryo_game.menu_screen.get_lifeslots_screen.level_base_coins
				.getLabel().setText(Integer.toString(setts_for_game.settings.number_coins) +
				" Coins");
		cryo_game.menu_screen.get_bullets_screen.level_base_coins
				.getLabel().setText(Integer.toString(setts_for_game.settings.number_coins) +
				" Coins");
		level_base_coins_bought.clearActions();
		level_base_coins_you_own_bought.clearActions();

		level_base_coins_bought.addAction(parallel(
			Actions.fadeIn(1.0f),
			Actions.scaleTo(1.3f, 1.3f, 1.0f),
			Actions.sequence(Actions.moveBy(0f, 12f, 0.3f),
				Actions.rotateBy(360f, 2.5f, Swing.circleOut),
				Actions.moveBy(0f, -12f, 0.3f),
				Actions.run(new Runnable() {
					public void run() {
						level_base_coins_bought.addAction(parallel(
							Actions.scaleTo(1.0f, 1.0f, 1.0f),
							Actions.sequence(Actions.fadeOut(8.0f),
								Actions.run(new Runnable() {
									public void run() {
										can_rotate1_star_chk = false;
										can_rotate2_star_chk = false;
										can_rotate3_star_chk = false;
										the_game_screen.mloader.loading_now = true;
										the_game_screen.mloader.can_change_screen = false;
										the_game_screen.mloader.started_procedure = false;
										the_game_screen.mloader.loadingLoaderCount = 0.000f;
										the_game_screen.inputMultiplexer.removeProcessor(stage);
										the_game_screen.grayness_increase_more_reversed = true;
										the_game_screen.grayness_increase_more_enough = false;
										stage.addAction(sequence(
											Actions.fadeOut(2f),
											Actions.run(new Runnable() {
												public void run() {
													EnterCoinsWonPhaseFinish();
												}
											})));
									}
								}))));
					}
				}))));
		level_base_coins_you_own_bought.addAction(parallel(
				Actions.fadeIn(1.0f),
				Actions.scaleTo(1.3f, 1.0f, 1.0f),
				Actions.sequence(Actions.moveBy(0f, 12f, 0.3f),
						Actions.rotateBy(360f, 2.5f, Swing.circleOut),
						Actions.moveBy(0f, -12f, 0.3f),
						Actions.run(new Runnable() {
							public void run() {
								level_base_coins_you_own_bought.addAction(parallel(
										Actions.scaleTo(1.0f, 1.0f, 1.0f),
										Actions.fadeOut(8.0f)));
							}
						}))));
		setts_for_game.WriteJson();
		cryo_game.SAVE_GAME_TO_CLOUD();
	}

	public void EnterCoinsWonPhaseFinish(){
		ResetAllMaxAngleWonInfoTables();
		if (primary_level_no != level_no) {
			angle1 = setts_for_game.settings.levels
					.get(level_no).level1_angle;
			angle2 = setts_for_game.settings.levels
					.get(level_no).level2_angle;
			angle3 = setts_for_game.settings.levels
					.get(level_no).level3_angle;
			cryo_game.game_state =
					GameState.GAME_LOADING_SCREEN;
			cryo_game.loader
					.ex01MenuLoadingScreenReload(
							setts_for_game,
							cryo_game,
							level_no,
							setts_for_game.settings
									.index_spaceship_selected,
							(int) angle1,
							(int) angle2,
							(int) angle3);
			cryo_game.setScreen(cryo_game.loader);
			the_game_screen.came_from_within_game = true;
			just_rendered_flip_flop_buy_ammolife = false;
		} else {
			stage.act(Gdx.graphics.getDeltaTime() *
					SPEED_HUD_REPLAY);
			the_game_screen.just_rendered_pause = false;
			the_game_screen.paused_game_screen = false;
			the_game_screen
					.ResetHudSpaceshipAndVariables();
			ResetAmmoLifeFromSettings(
					the_game_screen,
					setts_for_game);
			showed = false;
			added = false;
			the_game_screen.already_added_replay = false;
			the_game_screen.MakeGreyShadersNoActive();
			the_game_screen.spaces
					.ResetSoundsFromPaused();
			just_rendered_flip_flop_buy_ammolife = false;
		}
		hud_display.added_render_VALUE = false;
	}

	public void InitShareGooglePlusFacebook(){
		ShareGoogleFacebookTable = new Table();
		StyleGoogle = new ImageButtonStyle();
		StyleGoogle.up = new TextureRegionDrawable(skin_replay.getRegion("sharegooglez"));
		ShareGoogleButton = new ImageButton(StyleGoogle);
		ShareGoogleButton.addAction(Actions.alpha(0.675f));
		StyleFacebook = new ImageButtonStyle();
		StyleFacebook.up = new TextureRegionDrawable(skin_replay.getRegion("sharefacebookz"));
		ShareFacebookButton = new ImageButton(StyleFacebook);
		ShareFacebookButton.addAction(Actions.alpha(0.675f));
		ShareGoogleFacebookTable.add(ShareFacebookButton)
				.width(VIRTUAL_SCREEN_WIDTH/5f)
				.height(VIRTUAL_SCREEN_WIDTH / 5f * 95f / 150f)
				.padRight(VIRTUAL_SCREEN_WIDTH / 4.250f);
		ShareGoogleFacebookTable.add(ShareGoogleButton)
				.width(VIRTUAL_SCREEN_WIDTH/5f)
				.height(VIRTUAL_SCREEN_WIDTH / 5f * 95f / 150f)
				.padLeft(VIRTUAL_SCREEN_WIDTH / 3.675f);
		ShareGoogleFacebookTable.setFillParent(true);
		ShareGoogleFacebookTable.center().top().padTop(VIRTUAL_SCREEN_WIDTH / 10.5f);
		
		ShareGoogleButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!share_pressed && !bCurrentlyFadedOutDontDraw
								   && !is_stop_click_for_finish
								   &&  cryo_game.menu_screen.menus.bIsLoggedIn) {
					share_pressed = true;
					if (cryo_game.menu_screen.settings.settings.sounds_on)
						cryo_game.menu_screen.done_button.play(DONE_BUTTON_VOLUME);
					ShareGoogleButton.clearActions();
					ShareGoogleButton.addAction(sequence(
							Actions.moveBy(0f, 12f, 0.1f),
							Actions.moveBy(0f, -12f, 0.2f),
							Actions.run(new Runnable() {
								public void run() {
								    clicked_google_screencap = true;
									share_pressed = false;
								}
							})));
				} else {
					ShareGoogleButton.addAction(sequence(
							moveBy(0f, 12f, 0.1f),
							moveBy(0f, -12f, 0.2f)));
				}
			}
		});		
		
		ShareFacebookButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!share_pressed && !bCurrentlyFadedOutDontDraw
								   && !is_stop_click_for_finish
								   &&  cryo_game.menu_screen.menus.bIsLoggedIn) {
					share_pressed = true;
					if (cryo_game.menu_screen.settings.settings.sounds_on)
						cryo_game.menu_screen.done_button.play(DONE_BUTTON_VOLUME);
					ShareFacebookButton.clearActions();
					ShareFacebookButton.addAction(sequence(
							Actions.moveBy(0f, 12f, 0.1f),
							Actions.moveBy(0f, -12f, 0.2f),
							Actions.run(new Runnable() {
							    public void run() {
								    clicked_facebook_screencap = true;
								    share_pressed = false;
							    }
						    })));
				} else {
					ShareFacebookButton.addAction(sequence(
							moveBy(0f, 12f, 0.1f),
							moveBy(0f, -12f, 0.2f)));
				}
			}
		});
//		stage.addActor(ShareGoogleFacebookTable);
	}
	
	public void DisposeHUDDisplay_Ads(){
		level_base_coins_t_bought.clear();
		level_base_coins_you_own_t_bought.clear();
		get_money_table_bought.clear();
		if(ShareGoogleFacebookTable!=null) {
			ShareGoogleFacebookTable.clear();
		}
		level_base_coins_you_own_bought.clear();
		level_base_coins_bought.clear();
		settings_get_money_bought.clear();
		if(ShareFacebookButton!=null) {
			ShareFacebookButton.clear();
		}
		if(ShareGoogleButton!=null) {
			ShareGoogleButton.clear();
		}

		if(VALUE_screen!=null) {
			VALUE_screen.Dispose();
			VALUE_screen = null;
		}
		level_base_coins_t_bought = null;
		level_base_coins_you_own_t_bought = null;
		get_money_table_bought = null;
		ShareGoogleFacebookTable = null;
		level_base_coins_you_own_bought = null;
		level_base_coins_bought = null;
		settings_get_money_bought = null;
		ShareFacebookButton = null;
		ShareGoogleButton = null;
		StyleFacebook = null;
		StyleGoogle = null;
	}
}
