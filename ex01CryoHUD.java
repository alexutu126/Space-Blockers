//WMS3 2015

package com.cryotrax.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//----------------------------------------------------------------

public class ex01CryoHUD {
    public static final int VIRTUAL_SCREEN_WIDTH = 480;
    public static final int VIRTUAL_SCREEN_HEIGHT = 800;
	public static final int screen_sizew = Gdx.graphics.getWidth();
	public static final int screen_sizeh = Gdx.graphics.getHeight();
	public static final float resizer =
			((float)VIRTUAL_SCREEN_WIDTH/(float)VIRTUAL_SCREEN_HEIGHT) /
			((float)screen_sizew/(float)screen_sizeh);
	public static final float CENTER_CAMERA_X = 15f;
	public static final float CENTER_CAMERA_Y = 800f/480f * 15f / 2f;
	public static final float ACCELL1 = 0.32f;
	public static final float ACCELR1 = 0.32f;
	public static final float VELOCITYL1 = 0f;
	public static final float VELOCITYR1 = 0f;
	public static final float ACCEL3 = 0.32f;
	public static final float ACCEL2 = 0.23f;
	public static final float ACCEL1 = 0.15f;
	public static final float VELOCITY3 = 0.0f;
	public static final float VELOCITY2 = 0.0f;
	public static final float VELOCITY1 = 0.0f;
	public static final float ACCEL_SHOOT = 1.25f;
	public static final float VELOCITY_SHOOT = 1.25f;
	public static final float INITIAL_VELOCITY_V = 0f;
	public static final float HUD_SHOOT_SPRITE_W = 11f;
	public static final float HUD_SHOOT_SPRITE_H = 5.729166666666667f;
	public static final float HUD_SHOOT_SPRITE_X = CENTER_CAMERA_X - HUD_SHOOT_SPRITE_W / 2f;
	public static final float HUD_SHOOT_SPRITE_Y = -10.25f;
	public static final float HUD_SHOOT_SMALL_SPRITE_W = 10.5f;
	public static final float HUD_SHOOT_SMALL_SPRITE_H = 4.2109375f;
	public static final float HUD_SHOOT_SMALL_SPRITE_X =
			CENTER_CAMERA_X - HUD_SHOOT_SMALL_SPRITE_W / 2f +
					(HUD_SHOOT_SMALL_SPRITE_W - HUD_SHOOT_SMALL_SPRITE_W*0.8f)/2;
	public static final float HUD_SHOOT_SMALL_SPRITE_Y = -6.25f;
	public static final float ALPHA_SHOOT = 0.85f;
	public static final float ORIGINALA_SHOOT = 0.85f;
	public static final float TRAVEL_SHOOT = 1f;
	public static final float HUD_ACCEL_3_SPRITE_W = 16f;
	public static final float HUD_ACCEL_3_SPRITE_H = 4f;
	public static final float HUD_ACCEL_3_SPRITE_X =
			CENTER_CAMERA_X - HUD_ACCEL_3_SPRITE_W / 2;
	public static final float HUD_ACCEL_2_SPRITE_W = 16f;
	public static final float HUD_ACCEL_2_SPRITE_H = 4f;
	public static final float HUD_ACCEL_2_SPRITE_X =
			CENTER_CAMERA_X - HUD_ACCEL_2_SPRITE_W / 2;
	public static final float HUD_ACCEL_1_SPRITE_W = 16f;
	public static final float HUD_ACCEL_1_SPRITE_H = 4f;
	public static final float HUD_ACCEL_1_SPRITE_X =
			CENTER_CAMERA_X - HUD_ACCEL_1_SPRITE_W / 2;
	public static final float HUD_LEFT1_SPRITE_W = 4f;
	public static final float HUD_LEFT1_SPRITE_H = 12f;
	public static final float HUD_LATERAL_DISTANCER = 9f;
	public static final float HUD_LEFT1_SPRITE_X =
			CENTER_CAMERA_X - HUD_LATERAL_DISTANCER;
	public static final float HUD_LEFT1_SPRITE_Y = 10f;
	public static final float HUD_RIGHT1_SPRITE_W = -4f;
	public static final float HUD_RIGHT1_SPRITE_H = 12f;
	public static final float HUD_RIGHT1_SPRITE_X =
			CENTER_CAMERA_X + HUD_LATERAL_DISTANCER;
	public static final float HUD_RIGHT1_SPRITE_Y = 10f;
	public static final float HUD_ARROWUP_W = 4f;
	public static final float HUD_ARROWUP_H = 4f;
	public static final float HUD_ARROWUP_X =
			CENTER_CAMERA_X - HUD_ARROWUP_W / 2f * resizer;
	public static final float HUD_ARROWUP_Y =
			CENTER_CAMERA_Y - HUD_ARROWUP_H / 2f - 13.75f;
	public static final float HUD_ARROWUP_WL = 7.03125f * resizer;   // w = 90
	public static final float HUD_ARROWUP_HL = 10.000000f;  		 // h = 128
	public static final float HUD_ARROWUP_WLp2 = HUD_ARROWUP_WL/2f;
	public static final float HUD_ARROWUP_HLp2 = HUD_ARROWUP_HL/2f;
	public static final float HUD_ARROWUP_XL = 2.5f;
	public static final float HUD_ARROWUP_YL = CENTER_CAMERA_Y - HUD_ARROWUP_H / 2f - 9f;
	public static final float HUD_ARROWUP_WR = 7.03125f * resizer;   // w = 90
	public static final float HUD_ARROWUP_HR = 10.000000f;  		 // h = 128
	public static final float HUD_ARROWUP_WRp2 = HUD_ARROWUP_WR/2f;
	public static final float HUD_ARROWUP_HRp2 = HUD_ARROWUP_HR/2f;
	public static final float HUD_CAMERA_WIDTH = 25f;
	public static final float HUD_CAMERA_LEFT_MARGIN = 2.5f;
	public static final float HUD_ARROWUP_XR =
			HUD_CAMERA_WIDTH - HUD_ARROWUP_WR + HUD_CAMERA_LEFT_MARGIN;
	public static final float HUD_ARROWUP_YR =
			CENTER_CAMERA_Y - HUD_ARROWUP_H / 2f - 9f;
	public static final float R = 0.902f;
	public static final float G = 0.902f;
	public static final float B = 0.980f;
	public static final float A = 1f;
	public float alpha_arrow_up = 1.000f;
	public static final float alpha_arrow_up_original = 0.425f;
	public static final float alpha_arrow_up_original_boot = 1.000f;
	public float alpha_arrow_left = 1.000f;
	public static final float alpha_arrow_left_original = 0.295f;
	public static final float alpha_arrow_left_original_boot = 1.000f;
	public float alpha_arrow_right = 1.000f;
	public static final float alpha_arrow_right_original = 0.295f;
	public static final float alpha_arrow_right_original_boot = 1.000f;
	public float alphaShoot;
	public float originalaShoot;
	public float alpha2;
	public float originala2;
	public float alpha1;
	public float originala1;
	public float alphaL1;
	public float orginalL1;
	public float finalDestL1;
	public float originalXL1;	
	public float alpha3;
	public float originala3;	
	public float alphaR1;
	public float orginalR1;
	public float finalDestR1;
	public float originalXR1;		
	public float finalDest3;
	public float finalDest2;
	public float finalDest1;
	public float finalDestShoot;
	public float originalY3;
	public float originalY2;
	public float originalY1;
	public float originalYShoot;
	public float r = R;
	public float g = G;
	public float b = B;
	public float a = A;
	public float camera_height_in_world_ui;	
	public float accelL1 = ACCELL1;
	public float accelR1 = ACCELR1;
	public float velocityL1 = VELOCITYL1;
	public float velocityR1 = VELOCITYR1;
	// not good this way because the distance covered is frame-rate dependent
	public float accel3 = ACCEL3;
	// as above so we will modify these vectors with others calculated...
	public float accel2 = ACCEL2;
	// ...based on initial an final destinations
	public float accel1 = ACCEL1;
	// ...based on initial an final destinations
	public float accelShoot = ACCEL_SHOOT;
	// this will be OK initial velocities and we will use the accel. calculated...
	public float velocity3 = VELOCITY3;
	// ...above for the accel. to the desired destination
	public float velocity2 = VELOCITY2;
	public float velocity1 = VELOCITY1;
	public float velocityShoot = VELOCITY_SHOOT;
	public float initialVelocityV = INITIAL_VELOCITY_V;
	public float spaces_maxleftplus;
	public float spaces_maxrightminus;
	public float screen_resizer;
	public float delta_64_srsz;
	public int FUD_accel_count = 0;
	public int FUD_shoot_count = 0;
	public int FUD_left_count = 0;
	public int FUD_right_count = 0;	
	public boolean pressed_shoot_once = false;
	public boolean pressed_left_hud = false;
	public boolean pressed_right_hud = false;
	public boolean pressed_left = false;
	public boolean pressed_right = false;
	public boolean pressed_middle = false;
	public boolean pressed_middle_shoot = false;
	public boolean pressed_accel = false;
	public boolean pressed_shoot = false;
	// used for FUD decelerating
	public boolean still_decelerating = false;
	// used for stoping FUD accel when enough is done
	public boolean enough_accel_done = false;
	public boolean finished_started = false;
	public Sprite HUDAssistCenterSprite;
	public Sprite HUDshootSprite;
	public Sprite HUDshootOverlaySmallSprite;
	public Sprite HUDaccel3Sprite;
	public Sprite HUDaccel2Sprite;
	public Sprite HUDaccel1Sprite;
	public Sprite HUDleft1Sprite;	
	public Sprite HUDright1Sprite;
	public Sprite HUD_ArrowUP;
	public Sprite HUD_ArrowLEFT;
	public Sprite HUD_ArrowRIGHT;
	public TextureRegion HUDArrowUPTR;
	public TextureRegion HUDArrowLEFTTR;
	public TextureRegion HUDArrowRIGHTTR;
	public TextureRegion HUDAssistCenterTR;
	public TextureRegion HUDshootTR;
	public TextureRegion HUDshootOverlaySmallTR;
	public TextureRegion HUDaccel3TR;	
	public TextureRegion HUDaccel2TR;
	public TextureRegion HUDaccel1TR;
	public TextureRegion HUDleft1TR;
	public TextureRegion HUDright1TR;
	public Rectangle spaceship_bounding_rectangle_shoot_hud;
	public Rectangle spaceship_bounding_rectangle_lefter;
	public Rectangle spaceship_bounding_rectangle_upper;
	public Rectangle spaceship_bounding_rectangle_righter;
	public Vector3 touchPoint=new Vector3();
	public Vector3 touchSpace=new Vector3();
	public Color FUD_color = new Color(r,g,b,a);
	public static final float RESIZER =
			((float)VIRTUAL_SCREEN_WIDTH/(float)VIRTUAL_SCREEN_HEIGHT) /
					((float)screen_sizew/(float)screen_sizeh);
	public Sprite slider;
	public TextureRegion sliderTR;
	public static final float HUD_SLIDER_X = 15f - 2.25f/2f * RESIZER;
	public static final float HUD_SLIDER_Y =-5.70f;
	public static final float HUD_SLIDER_W =2.25f * RESIZER;
	public static final float HUD_SLIDER_H =2.25f;
	public static final float ALPHA_SLIDER = 1.0f;
	public static final float CENTER_STAGE = 15f;
	public Rectangle slider_bounding_rectangle;
	public float rectangle_slider_width;
	
	public ex01CryoHUD(ex01CryoshipPrincipial spaces,
					   TextureAtlas atlas,
					   float y3,
					   float y2,
					   float y1,
					   float dest3,
					   float dest2,
					   float dest1,
					   float a3,
					   float a2,
					   float a1,
					   OrthographicCamera camera){
		camera_height_in_world_ui = camera.viewportHeight;
		HUDArrowsInit(atlas);
		HUDShootInit(atlas);
		HUDAccelInit(atlas, y3, y2, y1, dest3, dest2, dest1, a1, a2, a3);
		HUDLeftInit(atlas, a1);
		HUDRightInit(atlas, a1);
		spaces_maxleftplus = spaces.maxLeft + 0.15f;
		spaces_maxrightminus = spaces.maxRight - 0.15f;
	}
	
	public void ex01CryoHUDReload(ex01CryoshipPrincipial spaces,
								  TextureAtlas atlas,
								  float y3, float y2,
								  float y1,
								  float dest3,
								  float dest2,
								  float dest1,
								  float a3,
								  float a2,
								  float a1){
		HUDArrowsReload(atlas);
		HUDShootReload(atlas);
		HUDAccelReload(atlas, y3, y2, y1, dest3, dest2, dest1, a1, a2, a3);
		HUDLeftReload(atlas, a1);
		HUDRightReload(atlas, a1);
		spaces_maxleftplus = spaces.maxLeft + 0.15f;
		spaces_maxrightminus = spaces.maxRight - 0.15f;		
	}
	
	public void HUDReset(){ //used for level reset and replay
		HUDShootReset();
		HUDAccelReset();
		HUDLeftReset();
		HUDRightReset();
		HUDResetBooleans();
		HUDResetOtherVectors();
		HUDArrowReset();
	}
	
	public void DisappearHUDOnFail(){
		finished_started = false;
		HUDshootOverlaySmallSprite.setAlpha(originalaShoot);  
		HUDshootSprite.setAlpha(1.0f);
		slider.setAlpha(1.0f);
	}

	public void HUDArrowReset(){
		alpha_arrow_up = 1.000f;
		alpha_arrow_left = 1.000f;
		alpha_arrow_right = 1.000f;
	}
	
	public void PressedReset(){
		pressed_left = false;
		pressed_right = false;
		pressed_middle = false;
		pressed_middle_shoot = false;
		pressed_accel = false;
		pressed_shoot = false;		
	}

	public void HUDArrowsReload(TextureAtlas atlas){
		HUDArrowUPTR = atlas.findRegion("arrows");
		HUD_ArrowUP.setRegion(HUDArrowUPTR);
		HUD_ArrowUP.setPosition(HUD_ARROWUP_X, HUD_ARROWUP_Y);
		HUD_ArrowUP.setSize(HUD_ARROWUP_W * resizer, HUD_ARROWUP_H);
		HUD_ArrowUP.setAlpha(alpha_arrow_up_original_boot);
		HUDArrowLEFTTR = atlas.findRegion("arrowlr");
		HUD_ArrowLEFT.setRegion(HUDArrowLEFTTR);
		HUD_ArrowLEFT.setPosition(HUD_ARROWUP_XL, HUD_ARROWUP_YL);
		HUD_ArrowLEFT.setSize(HUD_ARROWUP_WL, HUD_ARROWUP_HL);
		HUD_ArrowLEFT.setOrigin(HUD_ARROWUP_WLp2, HUD_ARROWUP_HLp2);
		HUD_ArrowLEFT.setAlpha(alpha_arrow_left_original_boot);
		HUDArrowRIGHTTR = atlas.findRegion("arrowlr");
		HUD_ArrowRIGHT.setRegion(HUDArrowRIGHTTR);
		HUD_ArrowRIGHT.setPosition(HUD_ARROWUP_XR, HUD_ARROWUP_YR);
		HUD_ArrowRIGHT.setSize(HUD_ARROWUP_WR, HUD_ARROWUP_HR);
		HUD_ArrowRIGHT.setOrigin(HUD_ARROWUP_WRp2, HUD_ARROWUP_HRp2);
		HUD_ArrowRIGHT.setAlpha(alpha_arrow_right_original_boot);
	}		
	
	public void HUDArrowsInit(TextureAtlas atlas){
		HUDArrowUPTR = atlas.findRegion("arrows");
		HUD_ArrowUP = new Sprite(HUDArrowUPTR);
		HUD_ArrowUP.setPosition(HUD_ARROWUP_X, HUD_ARROWUP_Y);
		HUD_ArrowUP.setSize(HUD_ARROWUP_W * resizer, HUD_ARROWUP_H);
		HUD_ArrowUP.setAlpha(alpha_arrow_up_original_boot);
		HUDArrowLEFTTR = atlas.findRegion("arrowlr");
		HUD_ArrowLEFT = new Sprite(HUDArrowLEFTTR);
		HUD_ArrowLEFT.setSize(HUD_ARROWUP_WL, HUD_ARROWUP_HL);
		HUD_ArrowLEFT.setOrigin(HUD_ARROWUP_WLp2, HUD_ARROWUP_HLp2);
		HUD_ArrowLEFT.setPosition(HUD_ARROWUP_XL, HUD_ARROWUP_YL);
		HUD_ArrowLEFT.rotate(180f);
		HUD_ArrowLEFT.setAlpha(alpha_arrow_left_original_boot);
		HUDArrowRIGHTTR = atlas.findRegion("arrowlr");
		HUD_ArrowRIGHT = new Sprite(HUDArrowRIGHTTR);
		HUD_ArrowRIGHT.setPosition(HUD_ARROWUP_XR, HUD_ARROWUP_YR);
		HUD_ArrowRIGHT.setSize(HUD_ARROWUP_WR, HUD_ARROWUP_HR);
		HUD_ArrowRIGHT.setOrigin(HUD_ARROWUP_WRp2, HUD_ARROWUP_HRp2);
		HUD_ArrowRIGHT.setAlpha(alpha_arrow_right_original_boot);
	}	
	
	// used when we finish the level or run out of bullets to relax the listening buffers
	public void HudSpecialReset(){
		pressed_accel = false;
		pressed_middle = false;
		pressed_shoot = false;		
	}
	
	//init the two shoot sprites at the bottom of the screen 
	public void HUDShootReload(TextureAtlas atlas){
		HUDshootTR = atlas.findRegion("hud_shoot_base");
		HUDshootSprite.setRegion(HUDshootTR);
		HUDshootSprite.setPosition(HUD_SHOOT_SPRITE_X, HUD_SHOOT_SPRITE_Y);
		HUDshootSprite.setSize(HUD_SHOOT_SPRITE_W, HUD_SHOOT_SPRITE_H);
		HUDshootOverlaySmallTR = atlas.findRegion("HUD SHOOT over2");
		HUDshootOverlaySmallSprite.setRegion(HUDshootOverlaySmallTR);
		HUDshootOverlaySmallSprite.setPosition(
				HUD_SHOOT_SMALL_SPRITE_X,
				HUD_SHOOT_SMALL_SPRITE_Y);
		HUDshootOverlaySmallSprite.setSize(
				HUD_SHOOT_SMALL_SPRITE_W*0.8f,
				HUD_SHOOT_SMALL_SPRITE_H*0.8f);
		//grow alpha as shoot sprite is going up
		alphaShoot = ALPHA_SHOOT;
		//reset alpha value when we finish shoot sprite travel
		originalaShoot = ORIGINALA_SHOOT;
		//how much to go up with shoot sprite
		finalDestShoot = HUDshootOverlaySmallSprite.getY() + TRAVEL_SHOOT;
		//reset Y position of shot sprite
		originalYShoot = HUDshootOverlaySmallSprite.getY();
		//reset alpha value when we finish shoot sprite travel
		HUDshootOverlaySmallSprite.setAlpha(originalaShoot);

		sliderTR = atlas.findRegion("slide_slider");
		slider.setRegion(sliderTR);
		slider.setPosition(
				HUD_SLIDER_X,
				HUD_SLIDER_Y);
		slider.setSize(
				HUD_SLIDER_W,
				HUD_SLIDER_H);
		slider.setAlpha(ALPHA_SLIDER);
	}	
	
	//init the two shoot sprites at the bottom of the screen 
	public void HUDShootInit(TextureAtlas atlas){
		HUDshootTR = atlas.findRegion("hud_shoot_base");
		HUDshootSprite = new Sprite(HUDshootTR);
		HUDshootSprite.setPosition(
				HUD_SHOOT_SPRITE_X,
				HUD_SHOOT_SPRITE_Y);
		HUDshootSprite.setSize(
				HUD_SHOOT_SPRITE_W,
				HUD_SHOOT_SPRITE_H);
		HUDshootOverlaySmallTR = atlas.findRegion("HUD SHOOT over2");
		HUDshootOverlaySmallSprite = new Sprite(HUDshootOverlaySmallTR);
		HUDshootOverlaySmallSprite.setPosition(
				HUD_SHOOT_SMALL_SPRITE_X,
				HUD_SHOOT_SMALL_SPRITE_Y);
		HUDshootOverlaySmallSprite.setSize(
				HUD_SHOOT_SMALL_SPRITE_W*0.8f,
				HUD_SHOOT_SMALL_SPRITE_H*0.8f);
		//grow alpha as shoot sprite is going up
		alphaShoot = ALPHA_SHOOT;
		//reset alpha value when we finish shoot sprite travel
		originalaShoot = ORIGINALA_SHOOT;
		//how much to go up with shoot sprite
		finalDestShoot = HUDshootOverlaySmallSprite.getY() + TRAVEL_SHOOT;
		//reset Y position of shot sprite
		originalYShoot = HUDshootOverlaySmallSprite.getY();
		//reset alpha value when we finish shoot sprite travel
		HUDshootOverlaySmallSprite.setAlpha(originalaShoot);

		//slider
		sliderTR = atlas.findRegion("slide_slider");
		slider = new Sprite(sliderTR);
		slider.setPosition(
				HUD_SLIDER_X,
				HUD_SLIDER_Y);
		slider.setSize(
				HUD_SLIDER_W,
				HUD_SLIDER_H);
		slider.setAlpha(ALPHA_SLIDER);
	}
	
	public void HUDShootReset(){
		HUDshootSprite.setPosition(
				HUD_SHOOT_SPRITE_X,
				HUD_SHOOT_SPRITE_Y
		);
		HUDshootOverlaySmallSprite.setPosition(
				HUD_SHOOT_SMALL_SPRITE_X,
				HUD_SHOOT_SMALL_SPRITE_Y
		);
		slider.setPosition(
				HUD_SLIDER_X,
				HUD_SLIDER_Y
		);
		slider_bounding_rectangle = slider.getBoundingRectangle();
		slider_bounding_rectangle.x -= 1f;
		slider_bounding_rectangle.y -= 2.5f;
		slider_bounding_rectangle.width += 2f;
		slider_bounding_rectangle.height += 3f;
		//grow alpha as shoot sprite is going up
		alphaShoot = ALPHA_SHOOT;
		//reset alpha value when we finish shoot sprite travel
		originalaShoot = ORIGINALA_SHOOT;
		//how much to go up with shoot sprite
		finalDestShoot = HUDshootOverlaySmallSprite.getY() + TRAVEL_SHOOT;
		//reset Y position of shot sprite
		originalYShoot = HUDshootOverlaySmallSprite.getY();
		//reset alpha value when we finish shoot sprite travel
		HUDshootOverlaySmallSprite.setAlpha(originalaShoot);
	}	
	
	//init the three sprites used for acceleration HUD
	public void HUDAccelReload(TextureAtlas atlas,
							   float y3,
							   float y2,
							   float y1,
							   float dest3,
							   float dest2,
							   float dest1,
							   float a1,
							   float a2,
							   float a3){
		HUDaccel3TR = atlas.findRegion("hudaccelA");
		HUDaccel3Sprite.setRegion(HUDaccel3TR);
		HUDaccel3Sprite.setPosition(
				HUD_ACCEL_3_SPRITE_X,
				y3);
		HUDaccel3Sprite.setSize(
				HUD_ACCEL_3_SPRITE_W,
				HUD_ACCEL_3_SPRITE_H);
		HUDaccel3Sprite.setAlpha(a3);
		alpha3 = a3;          //grow alpha as acceleration sprite is going up 
		originala3 = a3; 	  //reset alpha value when we finish acceleration sprite travel
		finalDest3 = dest3;   //how much to go up with acceleration sprite	
		originalY3 = y3;	  //reset Y position of acceleration sprite					
		HUDaccel2TR = atlas.findRegion("hudaccelB");
		HUDaccel2Sprite.setRegion(HUDaccel2TR);
		HUDaccel2Sprite.setPosition(
				HUD_ACCEL_2_SPRITE_X,
				y2);
		HUDaccel2Sprite.setSize(
				HUD_ACCEL_2_SPRITE_W,
				HUD_ACCEL_2_SPRITE_H);
		HUDaccel2Sprite.setAlpha(a2);
		alpha2 = a2;          //grow alpha as acceleration sprite is going up 
		originala2 = a2; 	  //reset alpha value when we finish acceleration sprite travel
		finalDest2 = dest2;   //how much to go up with acceleration sprite	
		originalY2 = y2;	  //reset Y position of acceleration sprite				
		HUDaccel1TR = atlas.findRegion("hudaccelC");
		HUDaccel1Sprite.setRegion(HUDaccel1TR);
		HUDaccel1Sprite.setPosition(
				HUD_ACCEL_1_SPRITE_X,
				y1);
		HUDaccel1Sprite.setSize(
				HUD_ACCEL_2_SPRITE_W,
				HUD_ACCEL_1_SPRITE_H);
		HUDaccel1Sprite.setAlpha(a1);
		alpha1 = a1;          //grow alpha as acceleration sprite is going up 
		originala1 = a1; 	  //reset alpha value when we finish acceleration sprite travel
		finalDest1 = dest1;   //how much to go up with acceleration sprite
		originalY1 = y1;	  //reset Y position of acceleration sprite		
	}	
	
	//init the three sprites used for acceleration HUD
	public void HUDAccelInit(TextureAtlas atlas,
							 float y3,
							 float y2,
							 float y1,
							 float dest3,
							 float dest2,
							 float dest1,
							 float a1,
							 float a2,
							 float a3){
		HUDaccel3TR = atlas.findRegion("hudaccelA");
		HUDaccel3Sprite = new Sprite(HUDaccel3TR);
		HUDaccel3Sprite.setPosition(
				HUD_ACCEL_3_SPRITE_X,
				y3);
		HUDaccel3Sprite.setSize(
				HUD_ACCEL_3_SPRITE_W,
				HUD_ACCEL_3_SPRITE_H);
		HUDaccel3Sprite.setAlpha(a3);
		alpha3 = a3;          //grow alpha as acceleration sprite is going up 
		originala3 = a3; 	  //reset alpha value when we finish acceleration sprite travel
		finalDest3 = dest3;   //how much to go up with acceleration sprite	
		originalY3 = y3;	  //reset Y position of acceleration sprite					
		HUDaccel2TR =atlas.findRegion("hudaccelB");
		HUDaccel2Sprite = new Sprite(HUDaccel2TR);
		HUDaccel2Sprite.setPosition(
				HUD_ACCEL_2_SPRITE_X,
				y2);
		HUDaccel2Sprite.setSize(
				HUD_ACCEL_2_SPRITE_W,
				HUD_ACCEL_2_SPRITE_H);
		HUDaccel2Sprite.setAlpha(a2);
		alpha2 = a2;          //grow alpha as acceleration sprite is going up 
		originala2 = a2; 	  //reset alpha value when we finish acceleration sprite travel
		finalDest2 = dest2;   //how much to go up with acceleration sprite	
		originalY2 = y2;	  //reset Y position of acceleration sprite				
		HUDaccel1TR = atlas.findRegion("hudaccelC");
		HUDaccel1Sprite = new Sprite(HUDaccel1TR);
		HUDaccel1Sprite.setPosition(
				HUD_ACCEL_1_SPRITE_X,
				y1);
		HUDaccel1Sprite.setSize(
				HUD_ACCEL_2_SPRITE_W,
				HUD_ACCEL_1_SPRITE_H);
		HUDaccel1Sprite.setAlpha(a1);
		alpha1 = a1;          //grow alpha as acceleration sprite is going up 
		originala1 = a1; 	  //reset alpha value when we finish acceleration sprite travel
		finalDest1 = dest1;   //how much to go up with acceleration sprite
		originalY1 = y1;	  //reset Y position of acceleration sprite		
	}
	
	public void HUDAccelReset(){
		alpha3 = originala3;
		alpha2 = originala2;
		alpha1 = originala1;
		HUDaccel3Sprite.setPosition(HUD_ACCEL_3_SPRITE_X, originalY3);
		HUDaccel3Sprite.setAlpha(originala3);				
		HUDaccel2Sprite.setPosition(HUD_ACCEL_2_SPRITE_X, originalY2);	
		HUDaccel2Sprite.setAlpha(originala2);	
		HUDaccel1Sprite.setPosition(HUD_ACCEL_1_SPRITE_X, originalY1);			
		HUDaccel1Sprite.setAlpha(originala1);
	}
	
	//init the left HUD sprite
	public void HUDLeftInit(TextureAtlas atlas, float a1){
		HUDleft1TR = atlas.findRegion("hud_left_right");
		HUDleft1Sprite = new Sprite(HUDleft1TR);
		HUDleft1Sprite.setPosition(
				HUD_LEFT1_SPRITE_X,
				HUD_LEFT1_SPRITE_Y);
		HUDleft1Sprite.setSize(
				HUD_LEFT1_SPRITE_W,
				HUD_LEFT1_SPRITE_H);
		HUDleft1Sprite.setAlpha(a1);
		//grow alpha as we go left
		alphaL1 = a1;
		//reset alpha value when we finish left travel
		orginalL1 = a1;
		finalDestL1 = HUDleft1Sprite.getX() - 2f;
		originalXL1 = HUDleft1Sprite.getX();		
	}
	
	//init the left HUD sprite
	public void HUDLeftReload(TextureAtlas atlas, float a1){
		HUDleft1TR = atlas.findRegion("hud_left_right");
		HUDleft1Sprite.setRegion(HUDleft1TR);
		HUDleft1Sprite.setPosition(
				HUD_LEFT1_SPRITE_X,
				HUD_LEFT1_SPRITE_Y);
		HUDleft1Sprite.setSize(
				HUD_LEFT1_SPRITE_W,
				HUD_LEFT1_SPRITE_H);
		HUDleft1Sprite.setAlpha(a1);
		//grow alpha as we go left
		alphaL1 = a1;
		//reset alpha value when we finish left travel
		orginalL1 = a1;
		finalDestL1 = HUDleft1Sprite.getX() - 2f;
		originalXL1 = HUDleft1Sprite.getX();		
	}
	
	public void HUDLeftReset(){
		HUDleft1Sprite.setPosition(
				HUD_LEFT1_SPRITE_X,
				HUD_LEFT1_SPRITE_Y);
		HUDleft1Sprite.setAlpha(orginalL1);
		alphaL1 = orginalL1;
		finalDestL1 = HUDleft1Sprite.getX() - 2f;
		originalXL1 = HUDleft1Sprite.getX();		
	}	
	
	//init the right HUD sprite
	public void HUDRightInit(TextureAtlas atlas, float a1){
		HUDright1TR = atlas.findRegion("hud_left_right");
		HUDright1Sprite = new Sprite(HUDright1TR);
		HUDright1Sprite.setPosition(
				HUD_RIGHT1_SPRITE_X,
				HUD_RIGHT1_SPRITE_Y);
		HUDright1Sprite.setSize(
				HUD_RIGHT1_SPRITE_W,
				HUD_RIGHT1_SPRITE_H);
		HUDright1Sprite.setAlpha(a1);
		//grow alpha as we go right
		alphaR1 = a1;
		//reset alpha value when we finish right travel
		orginalR1 = a1;
		finalDestR1 = HUDright1Sprite.getX() + 2f;
		originalXR1 = HUDright1Sprite.getX();		
	}
	
	//init the right HUD sprite
	public void HUDRightReload(TextureAtlas atlas, float a1){
		HUDright1TR = atlas.findRegion("hud_left_right");
		HUDright1Sprite.setRegion(HUDright1TR);
		HUDright1Sprite.setPosition(
				HUD_RIGHT1_SPRITE_X,
				HUD_RIGHT1_SPRITE_Y);
		HUDright1Sprite.setSize(
				HUD_RIGHT1_SPRITE_W,
				HUD_RIGHT1_SPRITE_H);
		HUDright1Sprite.setAlpha(a1);
		//grow alpha as we go right
		alphaR1 = a1;
		//reset alpha value when we finish right travel
		orginalR1 = a1;
		finalDestR1 = HUDright1Sprite.getX() + 2f;
		originalXR1 = HUDright1Sprite.getX();		
	}
	
	public void HUDRightReset(){
		HUDright1Sprite.setPosition(
				HUD_RIGHT1_SPRITE_X,
				HUD_RIGHT1_SPRITE_Y);
		HUDright1Sprite.setAlpha(orginalR1);
		alphaR1 = orginalR1;
		finalDestR1 = HUDright1Sprite.getX() + 2f;
		originalXR1 = HUDright1Sprite.getX();		
	}	
	
	public void HUDResetBooleans(){
		pressed_shoot_once = false;
		pressed_left_hud = false;
		pressed_right_hud = false;
		pressed_left = false;
		pressed_right = false;
		pressed_middle = false;
		pressed_middle_shoot = false;
		pressed_accel = false;
		pressed_shoot = false;
		still_decelerating = false; // used for FUD decelerating
		enough_accel_done = false;  // used for stoping FUD accel when enough is done		
	}
	
	public void HUDResetOtherVectors(){
		FUD_accel_count = 0;
		FUD_shoot_count = 0;
		FUD_left_count = 0;
		FUD_right_count = 0;		
		accelL1 = ACCELL1;
		accelR1 = ACCELR1;
		velocityL1 = VELOCITYL1;
		velocityR1 = VELOCITYR1;
		// not good this way because the distance covered is frame-rate dependent
		accel3 = ACCEL3;
		// as above so we will modify these vectors with others calculated...
		accel2 = ACCEL2;
		// ...based on initial an final destinations
		accel1 = ACCEL1;
		// ...based on initial an final destinations
		accelShoot = ACCEL_SHOOT;
		// this will be OK initial velocities and we will use the accel. calculated...
		velocity3 = VELOCITY3;
		// ...above for the accel. to the desired destination
		velocity2 = VELOCITY2;
		velocity1 = VELOCITY1;
		velocityShoot = VELOCITY_SHOOT;
		initialVelocityV = INITIAL_VELOCITY_V;		
	}

	//determines what was touched : shoot, acceleration, left or right
	public void touchDownProccess(int x,
								  int y,
								  int pointer,
								  ex01CryoshipPrincipial spaces,
								  OrthographicCamera hud_cam){
		spaces.game_upper.camera.unproject(touchSpace.set(x, y, 0f));
		hud_cam.unproject(touchPoint.set(x, y, 0));
		if(!spaces.game_upper.touchDownCamerFromDragged) {
			pressed_middle_shoot =
					spaceship_bounding_rectangle_shoot_hud.contains(touchPoint.x, touchPoint.y);
		}
		is_on_slider = true;
		if(!(pointer>=1) && spaces.touch_upped){
			pressed_middle = spaceship_bounding_rectangle_upper
					.contains(touchPoint.x, touchPoint.y);
			is_on_slider = !pressed_middle;
			if(!spaces.reversed_rotation ){
				if(pressed_middle){
					pressed_shoot = pressed_middle_shoot;
					spaces.pressed_shoot = pressed_middle_shoot;
					pressed_accel = !pressed_shoot;
					spaces.pressed_accel = !pressed_shoot;
				}
				else if(pressed_left && !pressed_right && is_on_slider){
					pressed_accel = false;
					if(spaces.sprite.getX() > spaces_maxleftplus){
						spaces.touchDOWN(pressed_left);
					}
				} else if (pressed_right && !pressed_left && is_on_slider){
					pressed_accel = false;
					if(spaces.sprite.getX() < spaces_maxrightminus){
						spaces.touchDOWN(pressed_left);
					}
				}
			}
		}
		if(!spaces.game_upper.touchDownCamerFromDragged) {
			pressed_shoot_once = pressed_middle_shoot;
		}
	}

	public boolean is_on_slider = false;
	public boolean finished_in_middle_ready = false;

	public void HUD_LeftRightProcess(int x,
									 int y,
									 int pointer,
									 ex01CryoshipPrincipial spaces,
									 OrthographicCamera hud_cam){

		hud_cam.unproject(touchPoint.set(x, y, 0));
		is_on_slider = true;
		if(!spaces.reversed_rotation && !pressed_middle_shoot){
			if(pressed_left && !pressed_right){
				if(spaces.sprite.getX() > spaces_maxleftplus){
					spaces.touchDOWN(pressed_left);
				}
			} else if (pressed_right && !pressed_left){
				if(spaces.sprite.getX() < spaces_maxrightminus){
					spaces.touchDOWN(pressed_left);
				}
			}
			else
			{
			}
		}
	}

	//cancel all pressed flags as they might cause errors and unexpected behaviour
	public void touchUP(ex01CryoshipPrincipial spaces){
		pressed_accel = false;
		pressed_middle = false;
		pressed_shoot = false;
		pressed_middle_shoot = false;
		pressed_left_hud = false;
		pressed_right_hud = false;
		spaces.pressed_accel = false;
		spaces.pressed_shoot = false;
		is_on_slider = false;
	}	
	
	//update the HUD sprites that dance up and down when we shoot 
	public void UpdateHUDShoot(float delta, float delta_64_srsz){
		if(pressed_middle_shoot && (HUDshootOverlaySmallSprite.getY() < finalDestShoot)){
			velocityShoot += accelShoot * delta;
			HUDshootOverlaySmallSprite.setPosition(
					HUDshootOverlaySmallSprite.getX(),
					HUDshootOverlaySmallSprite.getY() + velocityShoot * delta_64_srsz);
			alphaShoot += 0.4f * delta;
			r -= 0.4f * delta;
			if(r<0)r = 0f;
			FUD_color.r = r;
			if(alphaShoot > 1) alphaShoot = 1f;
			HUDshootOverlaySmallSprite.setAlpha(alphaShoot);
			FUD_shoot_count++;
		} else if(pressed_middle_shoot && (HUDshootOverlaySmallSprite.getY() >= finalDestShoot)){
			r = 0.902f;
			FUD_color.r = r;
			HUDshootOverlaySmallSprite
					.setAlpha(originalaShoot);
			HUDshootOverlaySmallSprite
					.setPosition(HUDshootOverlaySmallSprite.getX(), originalYShoot);
			velocityShoot  = 0f;
			FUD_shoot_count = 0;
		} else if (!pressed_middle_shoot){
			if (FUD_shoot_count > 0) {
				HUDshootOverlaySmallSprite.setPosition(
						HUDshootOverlaySmallSprite.getX(),
						HUDshootOverlaySmallSprite.getY() - velocityShoot * delta_64_srsz);
				velocityShoot -= accelShoot * delta;
				r += 0.4f * delta;
				if(r>1)r = 1f;
				FUD_color.r = r;
				alphaShoot -= 0.4f * delta;
				if(alphaShoot < 0) alphaShoot = 0;
				HUDshootOverlaySmallSprite.setAlpha(alphaShoot);
				FUD_shoot_count--;
			} else {
				r = 0.902f;
				FUD_color.r = r;
				HUDshootOverlaySmallSprite.setAlpha(originalaShoot);
				HUDshootOverlaySmallSprite.setPosition(
						HUDshootOverlaySmallSprite.getX(),
						originalYShoot);
				velocityShoot = 0f;
			}
		}
	}
	
	//update the HUD sprites for acceleration
	public void UpdateHUDAccel(float delta, float delta_64_srsz){
		if(pressed_accel && (HUDaccel3Sprite.getY() < finalDest3)){
			velocity3 += accel3 * delta; HUDaccel3Sprite.setPosition(
					HUDaccel3Sprite.getX(),
					HUDaccel3Sprite.getY() + velocity3 * delta_64_srsz);
			alpha3 += 0.9f * delta;
			if(alpha3 > 1) alpha3 = 1f;
			HUDaccel3Sprite.setAlpha(alpha3);
			velocity2 += accel2 * delta; HUDaccel2Sprite.setPosition(
					HUDaccel2Sprite.getX(),
					HUDaccel2Sprite.getY() + velocity2 * delta_64_srsz);
			alpha2 += 0.9f * delta;
			if(alpha2 > 1) alpha2 = 1f;
			HUDaccel2Sprite.setAlpha(alpha2);
			velocity1 += accel1 * delta; HUDaccel1Sprite.setPosition(
					HUDaccel1Sprite.getX(),
					HUDaccel1Sprite.getY() + velocity1 * delta_64_srsz);
			alpha1 += 0.9f * delta;
			if(alpha1 > 1) alpha1 = 1f;
			HUDaccel1Sprite.setAlpha(alpha1);
			FUD_accel_count++;
		} else if (!pressed_accel){
			if (FUD_accel_count > 0) {
				HUDaccel3Sprite.setPosition(
						HUDaccel3Sprite.getX(),
						HUDaccel3Sprite.getY() - velocity3 * delta_64_srsz);
				velocity3 -= accel3 * delta;
				alpha3 -= 0.9f * delta;
				if(alpha3 < 0) alpha3 = 0;
				HUDaccel3Sprite.setAlpha(alpha3);
				HUDaccel2Sprite.setPosition(
						HUDaccel2Sprite.getX(),
						HUDaccel2Sprite.getY() - velocity2 * delta_64_srsz);
				velocity2 -= accel2 * delta;
				alpha2 -= 0.9f * delta;
				if(alpha2 < 0) alpha2 = 0;
				HUDaccel2Sprite.setAlpha(alpha2);
				HUDaccel1Sprite.setPosition(
						HUDaccel1Sprite.getX(),
						HUDaccel1Sprite.getY() - velocity1 * delta_64_srsz);
				velocity1 -= accel1 * delta;
				alpha1 -= 0.9f * delta;
				if(alpha1 < 0) alpha1 = 0;
				HUDaccel1Sprite.setAlpha(alpha1);
				FUD_accel_count--;
			} else {
				HUDaccel1Sprite.setAlpha(originala1);
				HUDaccel2Sprite.setAlpha(originala2);
				HUDaccel3Sprite.setAlpha(originala3);
				HUDaccel3Sprite.setPosition(HUDaccel3Sprite.getX(), originalY3);
				HUDaccel2Sprite.setPosition(HUDaccel2Sprite.getX(), originalY2);
				HUDaccel1Sprite.setPosition(HUDaccel1Sprite.getX(), originalY1);
				velocity3 = 0f;
				velocity2 = 0f;
				velocity1 = 0f;
			}
		}
	}

	public void UpdateHUDArrows(){
		if(pressed_accel && (HUDaccel3Sprite.getY() < finalDest3)){
			alpha_arrow_up -= 0.004f;
			if(alpha_arrow_up<0f) alpha_arrow_up = 0f;
			HUD_ArrowUP.setAlpha(alpha_arrow_up);
		} else if (!pressed_accel){
			if (FUD_accel_count > 0) {
				alpha_arrow_up += 0.004f;
				if(alpha_arrow_up>alpha_arrow_up_original)
					alpha_arrow_up = alpha_arrow_up_original;
				HUD_ArrowUP.setAlpha(alpha_arrow_up);
			} else {
				alpha_arrow_up = alpha_arrow_up_original;
				HUD_ArrowUP.setAlpha(alpha_arrow_up);
			}
		}				
	}
	
	public void UpdateHUDArrowsLeft(){
		if(pressed_left_hud && (HUDleft1Sprite.getX() > finalDestL1)){
			alpha_arrow_left -= 0.012f;
			if(alpha_arrow_left<0f) alpha_arrow_left = 0f;
			HUD_ArrowLEFT.setAlpha(alpha_arrow_left);
		} else if (!pressed_left_hud){
			if (FUD_left_count > 0) {
				alpha_arrow_left += 0.012f;
				if(alpha_arrow_left>alpha_arrow_left_original)
					alpha_arrow_left = alpha_arrow_left_original;
				HUD_ArrowLEFT.setAlpha(alpha_arrow_left);
			} else {
				alpha_arrow_left = alpha_arrow_left_original;
				HUD_ArrowLEFT.setAlpha(alpha_arrow_left);
			}
		}				
	}
	
	public void UpdateHUDArrowsRight(){
		if(pressed_right_hud && (HUDright1Sprite.getX() < finalDestR1)){
			alpha_arrow_right -= 0.012f;
			if(alpha_arrow_right<0f) alpha_arrow_right = 0f;
			HUD_ArrowRIGHT.setAlpha(alpha_arrow_right);
		} else if (!pressed_right_hud){
			if (FUD_right_count > 0) {
				alpha_arrow_right += 0.012f;
				if(alpha_arrow_right>alpha_arrow_right_original)
					alpha_arrow_right = alpha_arrow_right_original;
				HUD_ArrowRIGHT.setAlpha(alpha_arrow_right);
			} else {
				alpha_arrow_right = alpha_arrow_right_original;
				HUD_ArrowRIGHT.setAlpha(alpha_arrow_right);
			}
		}				
	}
	
	//update the left HUD sprite when we turn left
	public void UpdateHUDLeft(float delta, float delta_64_srsz){
		if(pressed_left_hud && (HUDleft1Sprite.getX() > finalDestL1)){
			velocityL1 += accelL1 * delta; HUDleft1Sprite.setPosition(
					HUDleft1Sprite.getX() - velocityL1 * delta_64_srsz,
					HUDleft1Sprite.getY());
			alphaL1 += 1.9f * delta;
			if(alphaL1 > 1) alphaL1 = 1f;
			HUDleft1Sprite.setAlpha(alphaL1);
			FUD_left_count++;
		} else if (!pressed_left_hud){
			if (FUD_left_count > 0) {
				HUDleft1Sprite.setPosition(
						HUDleft1Sprite.getX() + velocityL1 * delta_64_srsz,
						HUDleft1Sprite.getY()); velocityL1 -= accelL1 * delta;
				alphaL1 -= 1.9f * delta;
				if(alphaL1 < 0) alphaL1 = 0;
				HUDleft1Sprite.setAlpha(alphaL1);
				FUD_left_count--;
			} else {
				HUDleft1Sprite.setAlpha(orginalL1);
				HUDleft1Sprite.setPosition(originalXL1, HUDleft1Sprite.getY());
				velocityL1 = 0f;			
			}
		}				
	}

	//update the right HUD sprite when we turn right
	public void UpdateHUDRight(float delta, float delta_64_srsz){
		if(pressed_right_hud && (HUDright1Sprite.getX() < finalDestR1)){
			velocityR1 += accelR1 * delta; HUDright1Sprite.setPosition(
					HUDright1Sprite.getX() + velocityR1 * delta_64_srsz,
					HUDright1Sprite.getY());
			alphaR1 += 1.9f * delta;
			if(alphaR1 > 1) alphaR1 = 1f;
			HUDright1Sprite.setAlpha(alphaR1);
			FUD_right_count++;
		} else if (!pressed_right_hud){
			if (FUD_right_count > 0) {
				HUDright1Sprite.setPosition(
						HUDright1Sprite.getX() - velocityR1 * delta_64_srsz,
						HUDright1Sprite.getY()); velocityR1 -= accelR1 * delta;
				alphaR1 -= 1.9f * delta;
				if(alphaR1 < 0) alphaR1 = 0;
				HUDright1Sprite.setAlpha(alphaR1);
				FUD_right_count--;
			} else {
				HUDright1Sprite.setAlpha(orginalR1);
				HUDright1Sprite.setPosition(originalXR1, HUDright1Sprite.getY());
				velocityR1 = 0f;		
			}
		}				
	}
	
	public void ProcessAlphaTransition(){
		if(alpha_arrow_up_original <= alpha_arrow_up)
			alpha_arrow_up -= 0.02f;
		if(alpha_arrow_left_original <= alpha_arrow_left)
			alpha_arrow_left -= 0.02f;
		if(alpha_arrow_right_original <= alpha_arrow_right)
			alpha_arrow_right -= 0.02f;
		HUD_ArrowUP.setAlpha(alpha_arrow_up);
		HUD_ArrowLEFT.setAlpha(alpha_arrow_left);
		HUD_ArrowRIGHT.setAlpha(alpha_arrow_right);
	}	
	
	public void HideShotForSimplyPaused(){
		HUDshootOverlaySmallSprite.setAlpha(0.3f);
		HUDshootSprite.setAlpha(0.3f);
		slider.setAlpha(0.3f);
	}
	
	//update the HUD acceleration, lateral, and shoot sprites 
	public void UpdateHUD(float delta){	
		screen_resizer = screen_sizeh / 800f;
		delta_64_srsz = delta * 64f * screen_resizer;
		UpdateHUDAccel(delta, delta_64_srsz);
		if(!finished_started)UpdateHUDShoot(delta, delta_64_srsz);
		UpdateHUDLeft(delta, delta_64_srsz);
		UpdateHUDRight(delta, delta_64_srsz);
		UpdateHUDArrows();
		UpdateHUDArrowsLeft();
		UpdateHUDArrowsRight();
	}

	//render the acceleration HUD sprites and the base shoot sprite (the one that doesn't move)
	public void RenderHUDAccelAndShot(SpriteBatch batch){
		HUD_ArrowUP.draw(batch);
		HUDaccel1Sprite.draw(batch);	
		HUDaccel2Sprite.draw(batch);
		HUDaccel3Sprite.draw(batch);
		HUDshootSprite.draw(batch);
		slider.draw(batch);
	}
	
	//render the left and right HUD, + the overlay shoot sprite (the one that moves up and down)
	public void RenderHUDLateralAccelAndShot(SpriteBatch batch){
		HUDshootOverlaySmallSprite.draw(batch);
		HUDleft1Sprite.draw(batch);
		HUDright1Sprite.draw(batch);			
	}
	
	public void Dispose(){
		HUDAssistCenterSprite = null;
		HUDshootSprite = null;
		HUDshootOverlaySmallSprite = null;
		HUDaccel3Sprite = null;
		HUDaccel2Sprite = null;
		HUDaccel1Sprite = null;
		HUDleft1Sprite = null;
		HUDright1Sprite = null;
		HUD_ArrowUP = null;
		HUD_ArrowLEFT = null;
		HUD_ArrowRIGHT = null;
		slider = null;
		HUDArrowUPTR = null;
		HUDArrowLEFTTR = null;
		HUDArrowRIGHTTR = null;
		HUDAssistCenterTR = null;
		HUDshootTR = null;
		HUDshootOverlaySmallTR = null;
		HUDaccel3TR = null;
		HUDaccel2TR = null;
		HUDaccel1TR = null;
		HUDleft1TR = null;
		HUDright1TR = null;
		sliderTR = null;
		spaceship_bounding_rectangle_shoot_hud = null;
		spaceship_bounding_rectangle_lefter = null;
		spaceship_bounding_rectangle_upper = null;
		spaceship_bounding_rectangle_righter = null;
		slider_bounding_rectangle = null;
		touchPoint = null;
		FUD_color = null;
	}
}