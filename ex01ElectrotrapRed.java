//WMS3 2015

package com.cryotrax.game;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

//----------------------------------------------------------------

public class ex01ElectrotrapRed extends Sprite{
	public static final float BLOCKER_W = 2.08f;
	public static final float BLOCKER_H = 2.08f;
	public static final float BLOCKER_RW = -2.08f;
	public static final float BLOCKER_RH = 2.08f;
	public static final float DIST_MIN_TO_ELECTROSHOCK_Y = 5.95f; // 85% din 7f
	public boolean angle_expanded_once = false;
	public boolean started_expansion = false;
	// StartAndEvolvePowerups need only to evolve once
	public boolean started_evolve_pups = false;
	// world coordinates (picked up from settings file)
	public float world_Y;
	// --||--
	public float world_X;
	// world coordinates for right electrotrap
	public float world_XR;
	// --||--
	public float world_YR;
	// size of electrotrap in world units
	public float width;
	// size of electrotrap in world units
	public float height;
	// distance between the left and the right electrotrap
	public float electrotrap_Width;
	// Red Color for lightning bolt1
	public float R1;
	// Green Color for lightning bolt1
	public float G1;
	public float B1;   					          // --||--
	public float A1;     				          // --||--
	public float R2;   					          // --||--
	public float G2;       					      // --||--
	public float B2;     				          // --||--
	public float A2;           					  // --||--
	public float R3;           					  // --||--
	public float G3;           					  // --||--
	public float B3;           					  // --||--
	public float A3;           					  // --||--
	public float middle_etrap;
	public float lightning_change_delta;
	public float DISTANCE_MIN_TO_ELECTROSHOCK_Y;		
	public boolean give_electro_shock = false;
	public boolean give_electro_shock_activated = false;
	// call GenerateLightningBolt1(curr_etrap); after FreeTrapLeftOrRight -
	public boolean give_electro_need_to_change_delegate;
	// we set to true once first electrocution starts than we keep following it until no
	// mather where the middle lies
	public boolean given_electrocution_once = false;
	// we set to true once first electrocution starts than we keep following it until no
	// mather where the middle lies
	public boolean given_electrocution_once_left_was = false;
	// we set to true once first electrocution starts than we keep following it until no
	// mather where the middle lies
	public boolean given_electrocution_once_right_was = false;
	public boolean give_electro_need_to_change_delegate_notfree;
	public boolean already_generated_powerups = false;
	public boolean free_to_right = false;
	public boolean free_to_left = false;	
	public boolean electrocuted_right = false;
	public boolean electrocuted_left = false;
	// for the else in NeedToChangeStart
	public boolean give_electro_shock_activated_denier = false;
	public boolean render_this_frame = true;
	public int give_electro_need_to_change_delegate_notfreeC = 0;
	// power (the more the power the more more it kills and the bigger the distance it can get you
	public int erPower;
	public double angle_expansion; 
	public long startTime;
	public Sprite right;
	public ex01BlockerYellow blocker;
	public ex01LightningBolt lbolt;
	public Rectangle left_collision_rectangle;
	public Rectangle right_collision_rectangle;			
	public Vector2 bolt_1_start; 				  // start point for lightning bolt FOR THE LEFT
	public Vector2 bolt_1_end;   				  // end point for lightning bolt   FOR THE LEFT
	public Vector2 bolt_2_start;				  // --||--
	public Vector2 bolt_2_end;   				  // --||--
	public Vector2 bolt_3_start;				  // --||--
	public Vector2 bolt_3_end;   				  // --||--
	public Vector2 bolt_1_starto;				  // start point for lightning bolt ORIGINAL
	public Vector2 bolt_1_startoo;				  // start point for lightning bolt ORIGINAL	
	public Vector2 bolt_1_endo;  				  // end point for lightning bolt ORIGINAL
	public Vector2 bolt_1_endoo; 				  // end point for lightning bolt ORIGINAL
	public Vector2 bolt_2_starto;			 	  // --||--
	public Vector2 bolt_2_startoo;				  // --||--
	public Vector2 bolt_2_endo;  				  // --||--
	public Vector2 bolt_2_endoo; 				  // --||--
	public Vector2 bolt_3_starto;				  // --||--
	public Vector2 bolt_3_startoo;				  // --||--
	public Vector2 bolt_3_endo; 				  // --||--
	public Vector2 bolt_3_endoo;			      // --||--
	public Vector2 bolt_1_startR; 				  // start point for lightning bolt FOR THE LEFT
	public Vector2 bolt_1_endR;   				  // end point for lightning bolt   FOR THE LEFT
	public Vector2 bolt_2_startR;				  // --||--
	public Vector2 bolt_2_endR;   				  // --||--
	public Vector2 bolt_3_startR;				  // --||--
	public Vector2 bolt_3_endR;   				  // --||--
	public Vector2 bolt_1_startoR;				  // start point for lightning bolt ORIGINAL
	public Vector2 bolt_1_startooR;				  // start point for lightning bolt ORIGINAL	
	public Vector2 bolt_1_endoR;  				  // end point for lightning bolt ORIGINAL
	public Vector2 bolt_1_endooR; 				  // end point for lightning bolt ORIGINAL
	public Vector2 bolt_2_startoR;			 	  // --||--
	public Vector2 bolt_2_startooR;				  // --||--
	public Vector2 bolt_2_endoR;  				  // --||--
	public Vector2 bolt_2_endooR; 				  // --||--
	public Vector2 bolt_3_startoR;				  // --||--
	public Vector2 bolt_3_startooR;				  // --||--
	public Vector2 bolt_3_endoR; 				  // --||--
	public Vector2 bolt_3_endooR;			      // --||--
	// electrotrap main sequence
	public ArrayList<ex01ElectrotrapSegment> lightning_bolt_prinicipal;
	// rarer, second sequence
	public ArrayList<ex01ElectrotrapSegment> lightning_bolt_secondary;
	// rarer, third sequence
	public ArrayList<ex01ElectrotrapSegment> lightning_bolt_tertiary;
	public float collision_electrotrap_min_check = 0f;
	public float collision_electrotrap_max_check = 0f;

	public ex01ElectrotrapRed(
			TextureAtlas atlas,
			TextureRegion tex,
			TextureRegion tex_blocker,
			float change_delta){

		super(tex);			
		right = new Sprite(tex);
		blocker = new ex01BlockerYellow(atlas, tex_blocker);
		blocker.width = BLOCKER_W;
		blocker.height = BLOCKER_H;
		blocker.setSize(BLOCKER_W, BLOCKER_H);
		blocker.right.setSize(BLOCKER_RW, BLOCKER_RH);		
		lightning_change_delta = change_delta; // 75000000f in constr in ex01ElectrotrapRedList
		DISTANCE_MIN_TO_ELECTROSHOCK_Y = DIST_MIN_TO_ELECTROSHOCK_Y;
		give_electro_need_to_change_delegate = true;
		give_electro_need_to_change_delegate_notfree = true;	
		lightning_bolt_prinicipal = new ArrayList<ex01ElectrotrapSegment>();		
	}
	
	public void ResetElectrotrap(){
		started_expansion = false;
		angle_expanded_once = false;
		started_evolve_pups = false;  
		give_electro_shock = false;
		give_electro_shock_activated = false;
		give_electro_need_to_change_delegate = true;          
		given_electrocution_once = false;              
		given_electrocution_once_left_was = false;     
		given_electrocution_once_right_was = false;    
		give_electro_need_to_change_delegate_notfree = true;
		give_electro_need_to_change_delegate_notfreeC = 0;
		give_electro_shock_activated_denier = false;
		already_generated_powerups = false;
		free_to_right = false;
		free_to_left = false;	
		electrocuted_right = false;
		electrocuted_left = false;		   
		render_this_frame = true;
	}
	
	public void Dispose(){
		right = null;
		if(lbolt!=null){
			lbolt.Dispose();
			lbolt = null;
		}

		left_collision_rectangle = null;
		right_collision_rectangle = null;
		bolt_1_start = null; 				
		bolt_1_end = null;   				
		bolt_2_start = null;				
		bolt_2_end = null;   				 
		bolt_3_start = null;				
		bolt_3_end = null;   				 
		bolt_1_starto = null;				 
		bolt_1_startoo = null;				 
		bolt_1_endo = null;  				
		bolt_1_endoo = null; 			
		bolt_2_starto = null;			 
		bolt_2_startoo = null;			
		bolt_2_endo = null;  				
		bolt_2_endoo = null; 			
		bolt_3_starto = null;			
		bolt_3_startoo = null;				
		bolt_3_endo = null; 				  
		bolt_3_endoo = null;			
		bolt_1_startR = null; 			
		bolt_1_endR = null;   				
		bolt_2_startR = null;				
		bolt_2_endR = null;   				
		bolt_3_startR = null;				
		bolt_3_endR = null;   			
		bolt_1_startoR = null;			
		bolt_1_startooR = null;			
		bolt_1_endoR = null;  		
		bolt_1_endooR = null; 			
		bolt_2_startoR = null;			 	
		bolt_2_startooR = null;			
		bolt_2_endoR = null;  			
		bolt_2_endooR = null; 		
		bolt_3_startoR = null;				 
		bolt_3_startooR = null;				
		bolt_3_endoR = null; 				
		bolt_3_endooR = null;			 

		if(lightning_bolt_prinicipal!=null) {
			for (int i = 0; i < lightning_bolt_prinicipal.size(); i++) {
				lightning_bolt_prinicipal.get(i).end = null;
				lightning_bolt_prinicipal.get(i).start = null;
			}
			lightning_bolt_prinicipal.clear();
			lightning_bolt_prinicipal = null;
		}
		if(lightning_bolt_secondary!=null) {
			for (int i = 0; i < lightning_bolt_secondary.size(); i++) {
				lightning_bolt_secondary.get(i).end = null;
				lightning_bolt_secondary.get(i).start = null;
			}
			lightning_bolt_secondary.clear();
			lightning_bolt_secondary = null;
		}
		if(lightning_bolt_secondary!=null) {
			for (int i = 0; i < lightning_bolt_secondary.size(); i++) {
				lightning_bolt_secondary.get(i).end = null;
				lightning_bolt_secondary.get(i).start = null;
			}
			lightning_bolt_secondary.clear();
			lightning_bolt_secondary = null;
		}
	}
}
