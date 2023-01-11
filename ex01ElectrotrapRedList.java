//WMS3 2015

package com.cryotrax.game;

import static com.badlogic.gdx.math.Intersector.*;
import java.util.ArrayList;
import java.util.Random;
import java.lang.String;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.audio.Sound;

//----------------------------------------------------------------

//& ET world_Y, world_X, width, height, electrotrap_Width, erPower(int) ...
//& ET ... RGBA1, RGBA2, RGBA3 all int
//& ---
//& ETBLRR (round robin) , BLRO (rotative), BLBO (bouncer)
//& ETBLRR relativeX, relativeY, blocker length when expanded, expansion speed,
// 				robin radius, trigger angle, rotation speed ...
//& ETBLRR ... angular speedP1, speedP2, speedP3, speedP4 (calculate acceleration
// 				based on robin R.), transX, timeforQuadrant
//& ---
//& ETBLRO relativeX, relativeY, blocker length when expanded, expansion
// 				speed, trigger angle, rotation speed ...
//& ETBLRO ... angular speedP1, speedP2, speedP3, speedP4 (calculate acceleration
// 				based on robin R.), transX, timeforQuadrant
//& ---
//& ETBLBO relativeX, relativeY, blocker length when expanded, expansion speed, trigger angle, ...
//& ETBLBO ... topSpeed, middleSpeed, bottomSpeed (you got to calculate acceleration), transX
//&
//&
//&

@SuppressWarnings("unused")
public class ex01ElectrotrapRedList {
	public float INTERSECTOR_SPEED = 0.90f;
	public static final float CENTER_CAMERA_X = 15f;
	public static final float CENTER_CAMERA_W2 = 25f / 2;
	public static final float BLOCKER_EXPANSION_SPEED = 3f;
	// how much to wait between electroshocks
	public static final float min_delta_between_eshocks = 200000000f;
	public static final float hscreenadd10 = 800f / 480f * 25f / 2f + 10f;
	public static final float hscreendif10 = 800f / 480f * 25f      - 10f;
	// this needs testing -rotative robin;
	public static final float COLLISION_ADJUSTER_RRO = 2.50f;
	// this needs testing -round robin
	public static final float COLLISION_ADJUSTER = 0.65f;
	// also needs testing -the rest
	public static final float COLLISION_ADJUSTER_BL = 0.20f;
	public static final float ANGLE0_RESETER = 0f;
	public static final float ANGLE0_THRES = 0f;
	public static final float ANGLE45_THRES = 45f;
	public static final float ANGLE90_THRES = 90f;
	public static final float ANGLE135_THRES = 135f;
	public static final float ANGLE180_THRES = 180f;
	public static final float ANGLE225_THRES = 225f;
	public static final float ANGLE270_THRES = 270f;
	public static final float ANGLE315_THRES = 315f;
	public static final float ANGLE360_THRES = 360f;
	public static final float ANGLE180_SETTER = 180.28316f;
	public static final float ANGLE360_SETTER = 359.70294f;
	public static final float ANGLE0_RESETTER = 0.28316f;
	public static final float ANGLE180_RESETTER = 179.70294f;
	public static final float ANGLE0_RESETERNeg = 0f;
	public static final float ANGLE0_THRESNeg = 0f;
	public static final float ANGLE45_THRESNeg = -45f;
	public static final float ANGLE90_THRESNeg = -90f;
	public static final float ANGLE135_THRESNeg = -135f;
	public static final float ANGLE180_THRESNeg = -180f;
	public static final float ANGLE225_THRESNeg = -225f;
	public static final float ANGLE270_THRESNeg = -270f;
	public static final float ANGLE315_THRESNeg = -315f;
	public static final float ANGLE360_THRESNeg = -360f;
	public static final float ANGLE180_SETTERNeg = 180.28316f;
	public static final float ANGLE360_SETTERNeg = 359.70294f;
	public static final float ANGLE0_RESETTERNeg = -0.28316f;
	public static final float ANGLE180_RESETTERNeg = -179.70294f;
	public static final float ANGLE270_RESETER = 270f;
	public static final float ANGLE256_SETTER = 256.98584f;
	public static final float ANGLE282_SETTER = 282.4238f;
	public static final float ANGLE256_SETTERNeg = -256.98584f;
	public static final float ANGLE282_SETTERNeg = -282.4238f;
	public static final float ANGLE76_RESETTER = 76.98584f;
	public static final float ANGLE102_RESETTER = 102.4238f;
	public static final float ANGLE76_RESETTERNeg = -76.98584f;
	public static final float ANGLE102_RESETTERNeg = -102.4238f;
	public static final float SPEED_COMPARER = 0f;
	public static final float ANGLE_OVERCATCHER = 0.001f;
	// at this robin_radius circle resets are 256.98584f(left), 282.4238f(right)
	public static final float ROBIN_RADIUS_BASE = 3.5f;
	public static final float CIRCLE_LEFT_RIGHT_BASE = 270f;
	public static final float CIRCLE_LEFT_RADIUS_BASE = 256.98584f;
	public static final float CIRCLE_LEFT_RADIUS_BASE_DELTA =
			CIRCLE_LEFT_RIGHT_BASE - CIRCLE_LEFT_RADIUS_BASE;
	public static final float CIRCLE_RIGHT_RADIUS_BASE = 282.4238f;
	public static final float CIRCLE_RIGHT_RADIUS_BASE_DELTA =
			CIRCLE_LEFT_RIGHT_BASE - CIRCLE_RIGHT_RADIUS_BASE;
	public static final float BOLT_NORMAL_WIDTH_ETRAP = 25f;
	public static final float BOLT_NORMAL_HEIGHT_ETRAP = 5.5f;
	public static final float BOLT_NORMAL_H_W = BOLT_NORMAL_HEIGHT_ETRAP / BOLT_NORMAL_WIDTH_ETRAP;
	// CIRCLE_LEFT_RADIUS_BASE_DELTA at 3.5f radius ROBIN_RADIUS_BASE
	// another_delta                 at x.yf radius
	// ... so -> another_delta_left =  CIRCLE_LEFT_RADIUS_BASE_DELTA * robin_radius /
	//       ROBIN_RADIUS_BASE = CIRCLE_LEFT_RADIUS_BASE_DELTA/ROBIN_RADIUS_BASE * robin_radius
	// ... so -> another_delta_left =  CIRCLE_RIGHT_RADIUS_BASE_DELTA * robin_radius /
	//       ROBIN_RADIUS_BASE = CIRCLE_RIGHT_RADIUS_BASE_DELTA/ROBIN_RADIUS_BASE * robin_radius
	// new_delta_left = CIRCLE_LEFT_RIGHT_BASE - another_delta_left;
	// new_delta_right = CIRCLE_LEFT_RIGHT_BASE - another_delta_right;
	// -----------------------------------------------------------------
	public static final float COLLISION_BARRIER_DELTA = 15f;
	public static final float COLLISION_BARRIER_XS = CENTER_CAMERA_X - COLLISION_BARRIER_DELTA;
	public static final float COLLISION_BARRIER_XE = CENTER_CAMERA_X + COLLISION_BARRIER_DELTA;
	public static final float POWERUP_ANGLE_POSITION_COSXER_MULTIPLIER = 20f;
	public static final float DELTA_POWERUP_EXPAND_POSITIONER = 3f;
	public static final float COLLISION_BOUNDARY = 2f;
	public static final float ROBIN_ROTATOR_Y_CORRECTION_INDX = 1.195f;  // la 2.5f raza
	public static final float ROBIN_ROTATOR_Y_CORRECTION_BASE = 2.5f;    //
	public static final float ROBIN_ROTATOR_Y_CORRECTION =
			ROBIN_ROTATOR_Y_CORRECTION_INDX / ROBIN_ROTATOR_Y_CORRECTION_BASE;
	public static final double angleToVertical(float dx, float dy){
		return (90f - MathUtils.atan2(dy, dx)*MathUtils.radiansToDegrees); }
	public static final String free_etrap_left_right_string = "FreeTrapLeftOrRight 222";
	public float width1;
	public float width2;
	public float width3;
	public float height1;
	public float height2;
	public float height3;
	public float rects_widthp2;
	public float rects_heightp2;
	public float spaces_es1getwidthp2;
	public float spaces_es2getwidthp2;
	public float spaces_es3getwidthp2;
	public float spaces_es1getheightp2;
	public float spaces_es2getheightp2;
	public float spaces_es3getheightp2;
	public float eshock_widthp2f1;
	public float eshock_widthp2f2;
	public float eshock_widthp2f3;
	public float eshock_heightp2f1;
	public float eshock_heightp2f2;
	public float eshock_heightp2f3;
	public float RaccelQ1;
	public float RaccelQ2;
	public float RaccelQ3;
	public float RaccelQ4;
	public float rot;
	public float TaccelI1;
	public float TaccelI2;
	public float TaccelIB1;
	public float TaccelIB2;
	public float depl_count;
	public float depl_dist;
	public float depl_dist2;
	public float middle_between_x;
	public float middle_between_y;
	public float colrect_xleft;
	public float colrect_xright;
	public float startX;
	public float endX;
	public float wither;
	public float timer_electrochange;
	public float rects_x_width_2;
	public float spaces_width;
	public float middle;
	public float cosXer;
	public float sinXer;
	public float[] vertsB  = new float[8];
	public float[] vertsBO  = new float[8];
	public float dist;
	public float middle_etrap;
	public float x;
	public float y;
	public float w;
	public float h;
	public float xr;
	public float timeforQuadrant;
	public float min_delta_eshocks_counter = 0f;
	public short pup_h1 = 0;
	public short pup_h2 = 0;
	public short pup_h3 = 0;
	public short pup_b1 = 0;
	public short pup_b2 = 0;
	public short pup_b3 = 0;
	public int health_n;
	public int bullet_n;
	public int cloak_n;
	public int bolt_end_type;
	public int angles1;
	public int angles2;
	public int angles3;
	public int m, i;
	public double angle;
	public double number_counter = 0;
	public long endTime;
	public long testTime = TimeUtils.millis();
	public long startTime;
	public long startTimeFlash = TimeUtils.millis();
	public boolean startTimeFlashB = false;
	public boolean returner = false;
	public boolean booler = false;
	public boolean left = false, right = false;
	public boolean can_be_electrocuted_still = false;
	public boolean can_powerup = true;
	public boolean need_to_change_start = false;
	public boolean need_to_change_start_end = false;
	public boolean need_to_change_end = false;
	// list of ex01ElectrotrapRed (electrotraps practically)
	public ArrayList<ex01ElectrotrapRed> electro_red_list;
	// list of electrotrap lights OFF on left
	public ArrayList<Sprite> electro_red_lightoff_list;
	// list of electrotrap lights ON on left
	public ArrayList<Sprite> electro_red_lighton_list;
	// list of electrotrap lights OFF on right
	public ArrayList<Sprite> electro_red_lightoff_listR;
	// list of electrotrap lights ON on right
	public ArrayList<Sprite> electro_red_lighton_listR;
	// texture of electrotrap
	public TextureRegion electrotrap_tex;
	// texture of electrotrap light off
	public TextureRegion electrotrap_tex_lightoff;
	// texture of electrotrap light on
	public TextureRegion electrotrap_tex_lighton;
	// texture of blocker
	public TextureRegion blocker_tex;
	public TextureAtlas texture_atlas_here;
	public Random rand = new Random();
	// SHADERS THAT WERE TOOK OUT OF THE ex01BlockerYellow.java class - ex01BlockerYellow
	public ShaderProgram shader;		   // shader program
	public ShaderProgram shader_base;      // shader base shader;
	public ShaderProgram shaderGrey;       // shader grey program;
	public ShaderProgram shaderSepia;      // sepia
	public ShaderProgram shaderSepiaPause; // sepia pause
	public Vector2 left_orig_v = new Vector2();
	public Vector2 left_blocker_orig = new Vector2();
	public Vector2 right_orig_v =  new Vector2();
	public Vector2 right_blocker_orig =  new Vector2();
	public Vector2 distV = new Vector2();
	public Vector2 start = new Vector2();
	public Vector2 end = new Vector2();
	public Vector2 CEB_startLine = new Vector2();
	public Vector2 CEB_endLine = new Vector2();
	public Vector2 CEB2_startLine = new Vector2();
	public Vector2 CEB2_endLine = new Vector2();
	public Vector2 CollisionBarrierTestStart = new Vector2();
	public Vector2 CollisionBarrierTestEnd = new Vector2();
	public Circle block_lcircle;
	public Circle block_rcircle;
	public OrthographicCamera camera_hud;
	public ex01CryoHUD hud;
	public _ex01CryotraxGame game_screen;
	public ex01ElectrotrapSegment seg;
	public ex01ElectrotrapRed etrap;
	public ex01ElectrotrapRed curr_etrap;
	public ex01ElectrotrapRed etrp;
	public ex01ElectrotrapRed electrotrap_reset;
	public ex01ElectrotrapRed prev_etrap;
	public ex01ElectrotrapRed next_etrap;
	public ArrayList<Sprite> etrap_list_off;
	public ArrayList<Sprite> etrap_list_offR;
	public ArrayList<Sprite> etrap_list_on;
	public ArrayList<Sprite> etrap_list_onR;
	public ArrayList<ex01ElectrotrapRed> etrap_list;
	public String[] attrib;
	public int no_etraps_per_line = 1;
	public int biggest_etrap_no_so_far = 1;
	public float current_y_etrap_counter = -1000f;
	public static final float ETRAP_DISTANCE = 35f;
	public float boltCounter = 0f;
	public int temp;
	public float temp_y_quadrant;
	public int[][] etrap_drawing_filter;
	public float[][] etrap_drawing_filter_pos;
	// screen delta filter - we calculate the min/max update at each 5f step
	public static final float ETRAP_DRAWING_FILTER_DELTA = 5f;
	// one screen size
	public static final float hscreendif10X = 800f / 480f * 25f;

	public ex01ElectrotrapRedList(TextureAtlas atlas,
								  ArrayList<String> electro_red_strings,
								  ArrayList<String> electro_red_blocker_strings,
								  ArrayList<CRBlockertType> electro_red_blocker_types,
								  int angle1,
								  int angle2,
								  int angle3){
		angles1 = angle1; angles2 = angle2; angles3 = angle3;
		texture_atlas_here = atlas;
		electro_red_list = new ArrayList<ex01ElectrotrapRed>();
		electro_red_lightoff_list = new ArrayList<Sprite>();
		electro_red_lighton_list = new ArrayList<Sprite>();
		electro_red_lightoff_listR = new ArrayList<Sprite>();
		electro_red_lighton_listR = new ArrayList<Sprite>();
		electrotrap_tex = atlas.findRegion("electrotrap");
		electrotrap_tex_lightoff = atlas.findRegion("orbotron lights off");
		electrotrap_tex_lighton = atlas.findRegion("orbotron lights on");
		blocker_tex = atlas.findRegion("BLOCKER32");
		for(int i=0; i<electro_red_strings.size(); i++){
			//create a new electrotrap, load it, set its size and position
			ex01ElectrotrapRed new_electrotrap = new
					ex01ElectrotrapRed(atlas, electrotrap_tex, blocker_tex, 100000000f);
			LoadElectrotrap(new_electrotrap, electro_red_strings.get(i));
			new_electrotrap.setSize(new_electrotrap.width, new_electrotrap.height);
			new_electrotrap.right.setSize(-new_electrotrap.width, new_electrotrap.height);
			new_electrotrap.setPosition(new_electrotrap.world_X, new_electrotrap.world_Y);
			new_electrotrap.right.setPosition(new_electrotrap.world_XR, new_electrotrap.world_YR);
			new_electrotrap.middle_etrap =
					(new_electrotrap.getX() + new_electrotrap.right.getX())/2f;
			new_electrotrap.blocker.GetBarrierMeshFromLine(
					new Vector2(new_electrotrap.getX() + new_electrotrap.getWidth() / 2,
								new_electrotrap.getY() + 2.8f),
					new Vector2(new_electrotrap.right.getX() - 1f/2f * new_electrotrap.getWidth(),
							    new_electrotrap.getY() + 2.8f),
					205,
					0,
					0,
					175);
			new_electrotrap.blocker.GetBarrierOMeshFromLine(
					new Vector2(new_electrotrap.getX() + new_electrotrap.getWidth() / 2,
								new_electrotrap.getY() + 2.8f),
					new Vector2(new_electrotrap.right.getX() - 1f/2f * new_electrotrap.getWidth(),
								new_electrotrap.getY() + 2.8f), 255, 128, 0, 175);
			new_electrotrap.startTime = TimeUtils.nanoTime();
			new_electrotrap.left_collision_rectangle = new
					Rectangle(new_electrotrap.world_X + 0.4f,
							  new_electrotrap.world_Y + 0.8f,
							  new_electrotrap.width - 2.65f,
						      new_electrotrap.height - 1.6f);
			new_electrotrap.right_collision_rectangle = new
					Rectangle(new_electrotrap.world_XR - new_electrotrap.width + 2.25f,
							  new_electrotrap.world_YR + 0.8f,
							  new_electrotrap.width -2.65f,
							  new_electrotrap.height - 1.6f);
			//after we load the electrotrap, we load the blocker based on its types
	        switch (electro_red_blocker_types.get(i)) {
            case RoundRobin:
		            { LoadElectrotrapBlocker(
							new_electrotrap,
							electro_red_blocker_strings.get(i),
							CRBlockertType.RoundRobin,
							atlas); }
                    break;
            case Rotative:
		            { LoadElectrotrapBlocker(
							new_electrotrap,
							electro_red_blocker_strings.get(i),
							CRBlockertType.Rotative,
							atlas); }
		            break;
            case Bouncer:
		            { LoadElectrotrapBlocker(
							new_electrotrap,
							electro_red_blocker_strings.get(i),
							CRBlockertType.Bouncer,
							atlas); }
		            break;
            case RotativeBouncer:
            		{ LoadElectrotrapBlocker(
							new_electrotrap,
							electro_red_blocker_strings.get(i),
							CRBlockertType.RotativeBouncer,
							atlas); }
            		break;
            case RotativeRobin:
    				{ LoadElectrotrapBlocker(
							new_electrotrap,
							electro_red_blocker_strings.get(i),
							CRBlockertType.RotativeRobin,
							atlas); }
    				break;
            case RobinBouncer:
    				{ LoadElectrotrapBlocker(
							new_electrotrap,
							electro_red_blocker_strings.get(i),
							CRBlockertType.RobinBouncer,
							atlas); }
    				break;
            default: { } break;
	        }
	        // add the above created electrotrap(including the blocker it contains)
			// to the list of electrotraps
			electro_red_list.add(new_electrotrap);
			// create lights for the electrotrap - LEFT
			Sprite new_off = new Sprite(electrotrap_tex_lightoff);
			//new_off.setAlpha(ALPHA_LIGHT);
			Sprite new_on = new Sprite(electrotrap_tex_lighton);
			new_on.setAlpha(ALPHA_LIGHT);
			new_off.setSize(SIZE_LIGHT*ex01Types.RESIZER, SIZE_LIGHT);
			new_off.setPosition(new_electrotrap.bolt_1_start.x - SIZE_LIGHT/2.15f,
					new_electrotrap.bolt_1_start.y - SIZE_LIGHT/1.85f);
			new_on.setSize(SIZE_LIGHT*ex01Types.RESIZER, SIZE_LIGHT);
			new_on.setPosition(new_electrotrap.bolt_1_start.x - SIZE_LIGHT / 2.15f,
					new_electrotrap.bolt_1_start.y - SIZE_LIGHT / 1.85f);
			electro_red_lightoff_list.add(new_off);
			electro_red_lighton_list.add(new_on);
			// create lights for the electrotrap - RIGHT
			new_off = new Sprite(electrotrap_tex_lightoff);
			//new_off.setAlpha(ALPHA_LIGHT);
			new_on = new Sprite(electrotrap_tex_lighton);
			new_on.setAlpha(ALPHA_LIGHT);
			new_off.setSize(SIZE_LIGHT*ex01Types.RESIZER, SIZE_LIGHT);
			new_off.setPosition(new_electrotrap.bolt_1_end.x - SIZE_LIGHT/1.75f,
					new_electrotrap.bolt_1_end.y - SIZE_LIGHT/1.85f);
			new_on.setSize(SIZE_LIGHT*ex01Types.RESIZER, SIZE_LIGHT);
			new_on.setPosition(new_electrotrap.bolt_1_end.x - SIZE_LIGHT/1.75f,
					new_electrotrap.bolt_1_end.y - SIZE_LIGHT/1.85f);
			electro_red_lightoff_listR.add(new_off);
			electro_red_lighton_listR.add(new_on);
			switch (electro_red_blocker_types.get(i)) {
            case RoundRobin:
		           break;
            case Rotative:
		           break;
            case Bouncer:
	            {
	    			if(new_electrotrap.blocker.currentDir == +1){
	    				new_electrotrap.blocker.currentSpeed =
								new_electrotrap.blocker.leftSpeed;
	    				ResetBlockerPositionWithDistanceLast(new_electrotrap.blocker);
	    			} else {
	    				new_electrotrap.blocker.currentSpeed =
								-new_electrotrap.blocker.rightSpeed;
	    				ResetBlockerPositionWithDistanceFirst(new_electrotrap.blocker);
	    			}
	            }
	            break;
            case RotativeBouncer:
            {
    			if(new_electrotrap.blocker.currentDirSec == +1){
    				new_electrotrap.blocker.currentSpeedSec =
							new_electrotrap.blocker.leftSpeedSec;
    				ResetBlockerPositionWithDistanceLastSec(new_electrotrap.blocker);
    			} else {
    				new_electrotrap.blocker.currentSpeedSec =
							-new_electrotrap.blocker.rightSpeedSec;
    				ResetBlockerPositionWithDistanceFirstSec(new_electrotrap.blocker);
    			}
            }
            break;
            case RotativeRobin:
            {

            }
            break;
            //THIS IS INIT
            case RobinBouncer:
            {
    			if(new_electrotrap.blocker.currentDirSec == +1){
    				new_electrotrap.blocker.currentSpeedSec =
							new_electrotrap.blocker.leftSpeedSec;
    				ResetBlockerPositionWithDistanceLastSecWPivot(new_electrotrap.blocker);
    			} else {
    				new_electrotrap.blocker.currentSpeedSec =
							-new_electrotrap.blocker.rightSpeedSec;
    				ResetBlockerPositionWithDistanceFirstSecWPivotOriginal(new_electrotrap.blocker);
    			}
            }
            break;
            default: { } break;
	        }

	        if(Math.abs(new_electrotrap.getY()-current_y_etrap_counter) < ETRAP_DISTANCE){
	        	no_etraps_per_line++;
	        	if(no_etraps_per_line>biggest_etrap_no_so_far)
					biggest_etrap_no_so_far = no_etraps_per_line;
	        } else {
	        	no_etraps_per_line = 1;
	        	current_y_etrap_counter = new_electrotrap.getY();
	        }
		}
		//setting up which electro-traps will be updated/drawn at
		// each ETRAP_DRAWING_FILTER_DELTA
		temp =  (int) (((int)electro_red_list.get(electro_red_list.size()-1).getY() +
				hscreendif10X)/ETRAP_DRAWING_FILTER_DELTA);
        etrap_drawing_filter = new int[temp][2];
        etrap_drawing_filter_pos = new float[temp][2];
		for(int mer = 0; mer < temp; mer++){
			temp_y_quadrant = mer * ETRAP_DRAWING_FILTER_DELTA;
			for(int i=0; i<electro_red_strings.size(); i++){
				if(electro_red_list.get(i).getY()>temp_y_quadrant-hscreendif10
				&& electro_red_list.get(i).getY()<temp_y_quadrant+hscreendif10){
					etrap_drawing_filter[mer][0] = i;
					etrap_drawing_filter_pos[mer][0] =
							temp_y_quadrant - ETRAP_DRAWING_FILTER_DELTA/2 + 15f;
					etrap_drawing_filter_pos[mer][1] =
							temp_y_quadrant + ETRAP_DRAWING_FILTER_DELTA/2 + 15f;
					break;
				}
			}
			for(int i=electro_red_strings.size()-1; i>=0; i--){
				if(electro_red_list.get(i).getY()>temp_y_quadrant-hscreendif10
			    && electro_red_list.get(i).getY()<temp_y_quadrant+hscreendif10){
					etrap_drawing_filter[mer][1] = i;
					etrap_drawing_filter_pos[mer][0] =
							temp_y_quadrant - ETRAP_DRAWING_FILTER_DELTA/2 + 15f;
					etrap_drawing_filter_pos[mer][1] =
							temp_y_quadrant + ETRAP_DRAWING_FILTER_DELTA/2 + 15f;
					break;
				}
			}

		}
		LoadShaders();
		angleToVertical(10f,10f); // we call this in the constructor so there is
		// no jolt at the beginning of the game because of 65K bytes allocation
	}

	public static final float SIZE_LIGHT = 1.075f;
	public static final float ALPHA_LIGHT = 0.75f;

	public void ResetElectrotrap(TextureAtlas atlas,
								 ArrayList<String> electro_red_strings,
								 ArrayList<String> electro_red_blocker_strings,
								 ArrayList<CRBlockertType> electro_red_blocker_types){
		min_delta_eshocks_counter = 0f;
		testTime = TimeUtils.millis();
		startTimeFlash = TimeUtils.millis();
		ResetElectrotrapListBooleans();
		for(int i=0; i<electro_red_strings.size(); i++){
			electrotrap_reset = electro_red_list.get(i);
			//create a new electrotrap, load it, set its size and position
	        electrotrap_reset.ResetElectrotrap();
	        electrotrap_reset.blocker.ResetBlocker();
			LoadElectrotrap(electrotrap_reset, electro_red_strings.get(i));
			electrotrap_reset.blocker.GetBarrierMeshFromLine(
					new Vector2(electrotrap_reset.getX() + electrotrap_reset.getWidth() / 2,
							    electrotrap_reset.getY() + 2.8f),
					new Vector2(electrotrap_reset.right.getX() - 1f/2f*electrotrap_reset.getWidth(),
								electrotrap_reset.getY() + 2.8f), 205, 0, 0, 175);
			electrotrap_reset.blocker.GetBarrierOMeshFromLine(
					new Vector2(electrotrap_reset.getX() + electrotrap_reset.getWidth() / 2,
							    electrotrap_reset.getY() + 2.8f),
					new Vector2(electrotrap_reset.right.getX() - 1f/2f*electrotrap_reset.getWidth(),
								electrotrap_reset.getY() + 2.8f), 255, 128, 0, 175);
			electrotrap_reset.startTime = TimeUtils.nanoTime();
			electrotrap_reset.left_collision_rectangle = new
					Rectangle(electrotrap_reset.world_X + 0.4f,
							  electrotrap_reset.world_Y + 0.8f,
							  electrotrap_reset.width - 2.65f,
							  electrotrap_reset.height - 1.6f);
			electrotrap_reset.right_collision_rectangle = new
					Rectangle(electrotrap_reset.world_XR - electrotrap_reset.width + 2.25f,
							  electrotrap_reset.world_YR + 0.8f,
							  electrotrap_reset.width -2.65f,
							  electrotrap_reset.height - 1.6f);
			//after we load the electrotrap, we load the blocker based on its types
	        switch (electro_red_blocker_types.get(i)) {
            case RoundRobin:
		            { LoadElectrotrapBlocker(
							electrotrap_reset,
							electro_red_blocker_strings.get(i),
							CRBlockertType.RoundRobin,
							atlas); }
                    break;
            case Rotative:
		            { LoadElectrotrapBlocker(
							electrotrap_reset,
							electro_red_blocker_strings.get(i),
							CRBlockertType.Rotative,
							atlas); }
		            break;
            case Bouncer:
		            { LoadElectrotrapBlocker(
							electrotrap_reset,
							electro_red_blocker_strings.get(i),
							CRBlockertType.Bouncer,
							atlas); }
		            break;
            case RotativeBouncer:
    				{ LoadElectrotrapBlocker(
							electrotrap_reset,
							electro_red_blocker_strings.get(i),
							CRBlockertType.RotativeBouncer,
							atlas); }
		    break;
		    case RotativeRobin:
					{ LoadElectrotrapBlocker(
							electrotrap_reset,
							electro_red_blocker_strings.get(i),
							CRBlockertType.RotativeRobin,
							atlas); }
			break;
		    case RobinBouncer:
					{ LoadElectrotrapBlocker(
							electrotrap_reset,
							electro_red_blocker_strings.get(i),
							CRBlockertType.RobinBouncer,
							atlas); }
			break;
            default: { } break;
	        }
	        switch (electro_red_blocker_types.get(i)) {
            case RoundRobin:
		           break;
            case Rotative:
		           break;
            case Bouncer:
	            {
	    			if(electrotrap_reset.blocker.currentDir == +1){
	    				electrotrap_reset.blocker.currentSpeed =
								electrotrap_reset.blocker.leftSpeed;
	    				ResetBlockerPositionWithDistanceLast(electrotrap_reset.blocker);
	    			} else {
	    				electrotrap_reset.blocker.currentSpeed =
								-electrotrap_reset.blocker.rightSpeed;
	    				ResetBlockerPositionWithDistanceFirst(electrotrap_reset.blocker);
	    			}
	            }
	            break;
            case RotativeBouncer:
            {
    			if(electrotrap_reset.blocker.currentDirSec == +1){
    				electrotrap_reset.blocker.currentSpeedSec =
							electrotrap_reset.blocker.leftSpeedSec;
    				ResetBlockerPositionWithDistanceLastSec(electrotrap_reset.blocker);
    			} else {
    				electrotrap_reset.blocker.currentSpeedSec =
							-electrotrap_reset.blocker.rightSpeedSec;
    				ResetBlockerPositionWithDistanceFirstSec(electrotrap_reset.blocker);
    			}
            }
            break;
            case RobinBouncer:
            {
    			if(electrotrap_reset.blocker.currentDirSec == +1){
    				electrotrap_reset.blocker.currentSpeedSec =
							electrotrap_reset.blocker.leftSpeedSec;
    				ResetBlockerPositionWithDistanceLastSecWPivot(electrotrap_reset.blocker);
    			} else {
    				electrotrap_reset.blocker.currentSpeedSec =
							-electrotrap_reset.blocker.rightSpeedSec;
    				ResetBlockerPositionWithDistanceFirstSecWPivot(electrotrap_reset.blocker);
    			}
            }
            default: { } break;
	        }
		}
		ResetNoBlockersDeblockCounter();
	}

	public void ResetElectrotrapListBooleans(){
		need_to_change_start_end = false;
		startTimeFlashB = false;
		returner = false;
		booler = false;
		left = false;
		right = false;
		can_be_electrocuted_still = false;
		can_powerup = true;
		need_to_change_start = false;
		need_to_change_start_end = false;
		need_to_change_end = false;
	}

	public void LoadSpaceshipData(ex01CryoshipPrincipial spaces){
		rects_widthp2 = spaces.spaceship_rectangle_collision.radius;
		rects_heightp2 = spaces.spaceship_rectangle_collision.radius;
		eshock_widthp2f1 = spaces.eShock1.getWidth()/2f;
		eshock_widthp2f2 = spaces.eShock2.getWidth()/2f;
		eshock_widthp2f3 = spaces.eShock3.getWidth()/2f;
		eshock_heightp2f1 = spaces.eShock1.getHeight()/2f;
		eshock_heightp2f2 = spaces.eShock2.getHeight()/2f;
		eshock_heightp2f3 = spaces.eShock3.getHeight()/2f;
	}

	public void LoadShaders(){
		// was in >>> public ex01BlockerYellow(TextureAtlas atlas, TextureRegion tex){
		createShader();
	}

	//create and link the shader programs computers view matrix and textures
	public void createShader()
    {
        // this shader tells opengl where to put things
        String vertexShader = "attribute vec4 a_position;    \n"
                + "attribute vec4 a_color;       \n"
                + "attribute vec2 a_texCoords;   \n"
                + "uniform mat4 u_worldView;     \n"
                + "varying vec4 v_color;         \n"
                + "varying vec2 v_texCoords;     \n"
                + "void main()                   \n"
                + "{                             \n"
                + "   v_color = a_color;         \n"
                + "   v_texCoords = a_texCoords; \n"
                + "   gl_Position = u_worldView * vec4(a_position.xy, 0, 1); \n "
                + "}                             \n";

        // this one tells it what goes in between the points (i.e
        // colour/texture)
        String fragmentShader = "#ifdef GL_ES                \n"
                + "precision mediump float;    \n"
                + "#endif                      \n"
                + "varying vec4 v_color;       \n"
                + "varying vec2 v_texCoords;   \n"
                + "uniform sampler2D u_texture;\n"
                + "void main()                 \n"
                + "{                           \n"
                + "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);   \n"
                + "}                           \n";

        shader = new ShaderProgram(vertexShader, fragmentShader);
        shader_base = shader;
        shaderGrey = new ShaderProgram(
				Gdx.files.internal(ex01Types.SHADER_GRAYSCALEL_VERT01big),
				Gdx.files.internal(ex01Types.SHADER_GRAYSCALEL_FRAG01big));
        shaderSepia = new ShaderProgram(
				Gdx.files.internal(ex01Types.SHADER_SEPIA_VERT01big),
				Gdx.files.internal(ex01Types.SHADER_SEPIA_FRAG01big));
        shaderSepiaPause = new ShaderProgram(
				Gdx.files.internal(ex01Types.SHADER_SEPIA2_VERT01big),
				Gdx.files.internal(ex01Types.SHADER_SEPIA2_FRAG01big));
    }

	// called by LoadElectrotrapBlocker(ex01ElectrotrapRed new_etrp.... to init the bolts
	public void ComputeLightningBoltEndsAndRightTrap(ex01ElectrotrapRed new_etrp){
		x = new_etrp.world_X;
		y = new_etrp.world_Y;
		w = new_etrp.width;
		h = new_etrp.height;
		// electrotrap_Width 25 for largest width
		new_etrp.world_XR = x + new_etrp.electrotrap_Width;
		new_etrp.world_YR = y;
		xr = new_etrp.world_XR;
		new_etrp.bolt_1_start = new Vector2(x + w / 2, y + h / 2);
		new_etrp.bolt_2_start = new Vector2(new_etrp.bolt_1_start.x - 0.3f,
											new_etrp.bolt_1_start.y + new_etrp.height / 3.3f);
		new_etrp.bolt_3_start = new Vector2(new_etrp.bolt_1_start.x - 0.3f,
											new_etrp.bolt_1_start.y - new_etrp.height / 3.3f);
		new_etrp.bolt_1_end = new Vector2(xr - w / 2, y + h / 2);
		new_etrp.bolt_2_end = new Vector2(new_etrp.bolt_1_end.x + 0.3f,
										  new_etrp.bolt_1_end.y + new_etrp.height / 3.3f);
		new_etrp.bolt_3_end = new Vector2(new_etrp.bolt_1_end.x + 0.3f,
										  new_etrp.bolt_1_end.y - new_etrp.height / 3.3f);
		new_etrp.bolt_1_starto = new_etrp.bolt_1_start;
		new_etrp.bolt_2_starto = new_etrp.bolt_2_start;
		new_etrp.bolt_3_starto = new_etrp.bolt_3_start;
		new_etrp.bolt_1_endo = new_etrp.bolt_1_end;
		new_etrp.bolt_2_endo = new_etrp.bolt_2_end;
		new_etrp.bolt_3_endo = new_etrp.bolt_3_end;
		new_etrp.bolt_1_startoo = new_etrp.bolt_1_start;
		new_etrp.bolt_2_startoo = new_etrp.bolt_2_start;
		new_etrp.bolt_3_startoo = new_etrp.bolt_3_start;
		new_etrp.bolt_1_endoo = new_etrp.bolt_1_end;
		new_etrp.bolt_2_endoo = new_etrp.bolt_2_end;
		new_etrp.bolt_3_endoo = new_etrp.bolt_3_end;
		new_etrp.bolt_1_startR = new Vector2(x + w / 2, y + h / 2);
		new_etrp.bolt_2_startR = new Vector2(new_etrp.bolt_1_start.x - 0.3f,
											 new_etrp.bolt_1_start.y + new_etrp.height / 3.3f);
		new_etrp.bolt_3_startR = new Vector2(new_etrp.bolt_1_start.x - 0.3f,
											 new_etrp.bolt_1_start.y - new_etrp.height / 3.3f);
		new_etrp.bolt_1_endR = new Vector2(xr - w / 2, y + h / 2);
		new_etrp.bolt_2_endR = new Vector2(new_etrp.bolt_1_end.x + 0.3f,
										   new_etrp.bolt_1_end.y + new_etrp.height / 3.3f);
		new_etrp.bolt_3_endR = new Vector2(new_etrp.bolt_1_end.x + 0.3f,
										   new_etrp.bolt_1_end.y - new_etrp.height / 3.3f);
		new_etrp.bolt_1_startoR = new_etrp.bolt_1_start;
		new_etrp.bolt_2_startoR = new_etrp.bolt_2_start;
		new_etrp.bolt_3_startoR = new_etrp.bolt_3_start;
		new_etrp.bolt_1_endoR = new_etrp.bolt_1_end;
		new_etrp.bolt_2_endoR = new_etrp.bolt_2_end;
		new_etrp.bolt_3_endoR = new_etrp.bolt_3_end;
		new_etrp.bolt_1_startooR = new_etrp.bolt_1_start;
		new_etrp.bolt_2_startooR = new_etrp.bolt_2_start;
		new_etrp.bolt_3_startooR = new_etrp.bolt_3_start;
		new_etrp.bolt_1_endooR = new_etrp.bolt_1_end;
		new_etrp.bolt_2_endooR = new_etrp.bolt_2_end;
		new_etrp.bolt_3_endooR = new_etrp.bolt_3_end;
	}

	// called in electrotrap constructor  - public ex01ElectrotrapRedList(...
	// it practically reads the settings obtain from world_data and inits the
	// blocker with that data
	public void LoadElectrotrapBlocker(ex01ElectrotrapRed new_etrp,
									   String data,
									   CRBlockertType type,
									   TextureAtlas atlas){

		attrib = data.split(",");
		switch(type){
			case RoundRobin:
			{
				new_etrp.blocker.relativeY = Float.parseFloat(attrib[0]);
				new_etrp.blocker.relativeX = Float.parseFloat(attrib[1]);
				new_etrp.blocker.bl_length_exp = Float.parseFloat(attrib[2]);
				new_etrp.blocker.exp_speed = Float.parseFloat(attrib[3]);
				new_etrp.blocker.robin_radius = Float.parseFloat(attrib[4]);
				if(new_etrp.blocker.robin_radius>5f)new_etrp.blocker.bl_length_exp *= 1.5f;
				new_etrp.blocker.trigger_angle = Float.parseFloat(attrib[5]);
				new_etrp.blocker.rotation_speed = Float.parseFloat(attrib[6]);
				new_etrp.blocker.speedP1 = Float.parseFloat(attrib[7]);
				new_etrp.blocker.speedP2 = Float.parseFloat(attrib[8]);
				new_etrp.blocker.speedP3 = Float.parseFloat(attrib[9]);
				new_etrp.blocker.speedP4 = Float.parseFloat(attrib[10]);
				new_etrp.blocker.transX = Float.parseFloat(attrib[11]);
				new_etrp.blocker.timeforQuadrant = Float.parseFloat(attrib[12]);
				new_etrp.blocker.still_forward_count = Integer.parseInt(attrib[13]);
				new_etrp.blocker.rotation_sense = Short.parseShort(attrib[14]);
				new_etrp.blocker.can_generate_powerups = Short.parseShort(attrib[15]);
				new_etrp.blocker.stage_values_powerups = Short.parseShort(attrib[16]);
				if(attrib.length == 19) new_etrp.blocker.relXpower = Float.parseFloat(attrib[17]);
				if(attrib.length == 19) new_etrp.blocker.relYpower = Float.parseFloat(attrib[18]);
				new_etrp.blocker.type = CRBlockertType.RoundRobin;
				new_etrp.blocker.rotation_speed = new_etrp.blocker.speedP1;
				ComputeBlockerCoords(new_etrp, atlas);
				// may need adjustment for relative Y
				new_etrp.collision_electrotrap_min_check = new_etrp.getY() -
						(new_etrp.blocker.robin_radius +
						 new_etrp.blocker.getHeight())*COLLISION_ADJUSTER;
				// may need adjustment for relative Y
				new_etrp.collision_electrotrap_max_check = new_etrp.getY() +
						new_etrp.getHeight() + (new_etrp.blocker.robin_radius +
						new_etrp.blocker.getHeight())*COLLISION_ADJUSTER;
			}
			break;
			case Rotative:
			{
				new_etrp.blocker.relativeY = Float.parseFloat(attrib[0]);
				new_etrp.blocker.relativeX = Float.parseFloat(attrib[1]);
				new_etrp.blocker.bl_length_exp = Float.parseFloat(attrib[2]);
				new_etrp.blocker.exp_speed = Float.parseFloat(attrib[3]);
				new_etrp.blocker.trigger_angle = Float.parseFloat(attrib[4]);
				new_etrp.blocker.rotation_speed = Float.parseFloat(attrib[5]);
				new_etrp.blocker.speedP1 = Float.parseFloat(attrib[6]);
				new_etrp.blocker.speedP2 = Float.parseFloat(attrib[7]);
				new_etrp.blocker.speedP3 = Float.parseFloat(attrib[8]);
				new_etrp.blocker.speedP4 = Float.parseFloat(attrib[9]);
				new_etrp.blocker.transX = Float.parseFloat(attrib[10]);
				new_etrp.blocker.timeforQuadrant = Float.parseFloat(attrib[11]);
				new_etrp.blocker.rotation_sense = Short.parseShort(attrib[12]);
				new_etrp.blocker.can_generate_powerups = Short.parseShort(attrib[13]);
				new_etrp.blocker.stage_values_powerups = Short.parseShort(attrib[14]);
				new_etrp.blocker.type = CRBlockertType.Rotative;
				new_etrp.blocker.rotation_speed = new_etrp.blocker.speedP1;
				ComputeBlockerCoords(new_etrp, atlas);
				// may need adjustment for relative Y
				new_etrp.collision_electrotrap_min_check = new_etrp.getY() -
						(new_etrp.blocker.getHeight())*COLLISION_ADJUSTER_BL;
				// may need adjustment for relative Y
				new_etrp.collision_electrotrap_max_check = new_etrp.getY() +
						new_etrp.getHeight() + (new_etrp.blocker.getHeight())*COLLISION_ADJUSTER_BL;
			}
			break;
			case Bouncer:
			{
				new_etrp.blocker.relativeY = Float.parseFloat(attrib[0]);
				new_etrp.blocker.relativeX = Float.parseFloat(attrib[1]);
				new_etrp.blocker.bl_length_exp = Float.parseFloat(attrib[2]);
				new_etrp.blocker.exp_speed = Float.parseFloat(attrib[3]);
				new_etrp.blocker.trigger_angle = Float.parseFloat(attrib[4]);
				new_etrp.blocker.leftSpeed = Float.parseFloat(attrib[5]);
				new_etrp.blocker.middleSpeed = Float.parseFloat(attrib[6]);
				new_etrp.blocker.rightSpeed = Float.parseFloat(attrib[7]);
				new_etrp.blocker.timeforQuadrant = Float.parseFloat(attrib[8]);
				new_etrp.blocker.deployDistance = Float.parseFloat(attrib[9]);
				new_etrp.blocker.deployCounter = 0f;
				new_etrp.blocker.currentDir = Short.parseShort(attrib[10]);
				new_etrp.blocker.can_generate_powerups = Short.parseShort(attrib[11]);
				new_etrp.blocker.stage_values_powerups = Short.parseShort(attrib[12]);
				if(new_etrp.blocker.currentDir == +1){
					new_etrp.blocker.currentSpeed = new_etrp.blocker.leftSpeed;
				} else {
					new_etrp.blocker.currentSpeed = -new_etrp.blocker.rightSpeed;
				}
				new_etrp.blocker.type = CRBlockertType.Bouncer;
				ComputeBlockerCoords(new_etrp, atlas);
				// may need adjustment for relative Y
				new_etrp.collision_electrotrap_min_check = new_etrp.getY() -
						(new_etrp.blocker.getHeight())*COLLISION_ADJUSTER_BL;
				// may need adjustment for relative Y
				new_etrp.collision_electrotrap_max_check = new_etrp.getY() +
						new_etrp.getHeight() + (new_etrp.blocker.getHeight())*COLLISION_ADJUSTER_BL;
			}
			break;
			case RotativeBouncer:
			{
				new_etrp.blocker.relativeY = Float.parseFloat(attrib[0]);
				new_etrp.blocker.relativeX = Float.parseFloat(attrib[1]);
				new_etrp.blocker.bl_length_exp = Float.parseFloat(attrib[2]);
				new_etrp.blocker.exp_speed = Float.parseFloat(attrib[3]);
				new_etrp.blocker.trigger_angle = Float.parseFloat(attrib[4]);
				new_etrp.blocker.rotation_speed = Float.parseFloat(attrib[5]);
				new_etrp.blocker.speedP1 = Float.parseFloat(attrib[6]);
				new_etrp.blocker.speedP2 = Float.parseFloat(attrib[7]);
				new_etrp.blocker.speedP3 = Float.parseFloat(attrib[8]);
				new_etrp.blocker.speedP4 = Float.parseFloat(attrib[9]);
				new_etrp.blocker.transX = Float.parseFloat(attrib[10]);
				new_etrp.blocker.timeforQuadrant = Float.parseFloat(attrib[11]);
				new_etrp.blocker.rotation_sense = Short.parseShort(attrib[12]);
				new_etrp.blocker.can_generate_powerups = Short.parseShort(attrib[13]);
				new_etrp.blocker.stage_values_powerups = Short.parseShort(attrib[14]);
				new_etrp.blocker.rotation_speed = new_etrp.blocker.speedP1;
//				ComputeBlockerCoords(new_etrp, atlas);
				new_etrp.blocker.leftSpeedSec = Float.parseFloat(attrib[15]);
				new_etrp.blocker.middleSpeedSec = Float.parseFloat(attrib[16]);
				new_etrp.blocker.rightSpeedSec = Float.parseFloat(attrib[17]);
				new_etrp.blocker.timeforQuadrantSec = Float.parseFloat(attrib[18]);
				new_etrp.blocker.deployDistanceSec = Float.parseFloat(attrib[19]);
				new_etrp.blocker.deployCounterSec = 0f;
				new_etrp.blocker.currentDirSec = Short.parseShort(attrib[20]);
				new_etrp.blocker.can_generate_powerupsSec = Short.parseShort(attrib[21]);
				new_etrp.blocker.stage_values_powerupsSec = Short.parseShort(attrib[22]);
				if(new_etrp.blocker.currentDirSec == +1){
					new_etrp.blocker.currentSpeedSec = new_etrp.blocker.leftSpeedSec;
				} else {
					new_etrp.blocker.currentSpeedSec = -new_etrp.blocker.rightSpeedSec;
				}
				new_etrp.blocker.type = CRBlockertType.RotativeBouncer;
				ComputeBlockerCoords(new_etrp, atlas);
				// may need adjustment for relative Y
				new_etrp.collision_electrotrap_min_check = new_etrp.getY() -
						(new_etrp.blocker.getHeight())*COLLISION_ADJUSTER_BL;
				// may need adjustment for relative Y
				new_etrp.collision_electrotrap_max_check = new_etrp.getY() +
						new_etrp.getHeight() + (new_etrp.blocker.getHeight())*COLLISION_ADJUSTER_BL;
			}
			break;
			case RotativeRobin:
			{
				new_etrp.blocker.relativeY = Float.parseFloat(attrib[0]);
				new_etrp.blocker.relativeX = Float.parseFloat(attrib[1]);
				new_etrp.blocker.bl_length_exp = Float.parseFloat(attrib[2]);
				new_etrp.blocker.exp_speed = Float.parseFloat(attrib[3]);
				new_etrp.blocker.trigger_angle = Float.parseFloat(attrib[4]);
				new_etrp.blocker.rotation_speed = Float.parseFloat(attrib[5]);
				new_etrp.blocker.speedP1 = Float.parseFloat(attrib[6]);
				new_etrp.blocker.speedP2 = Float.parseFloat(attrib[7]);
				new_etrp.blocker.speedP3 = Float.parseFloat(attrib[8]);
				new_etrp.blocker.speedP4 = Float.parseFloat(attrib[9]);
				new_etrp.blocker.transX = Float.parseFloat(attrib[10]);
				new_etrp.blocker.timeforQuadrant = Float.parseFloat(attrib[11]);
				new_etrp.blocker.rotation_sense = Short.parseShort(attrib[12]);
				new_etrp.blocker.can_generate_powerups = Short.parseShort(attrib[13]);
				new_etrp.blocker.stage_values_powerups = Short.parseShort(attrib[14]);
				new_etrp.blocker.rotation_speed = new_etrp.blocker.speedP1;
//				ComputeBlockerCoords(new_etrp, atlas);
				new_etrp.blocker.robin_radiusSec = Float.parseFloat(attrib[15]);
				if(new_etrp.blocker.robin_radiusSec>=7.0f){
					new_etrp.blocker.bl_length_exp *= 2.0f;
				} else new_etrp.blocker.bl_length_exp *= 1.50f;
				new_etrp.blocker.speedP1Sec = Float.parseFloat(attrib[16]);
				new_etrp.blocker.speedP2Sec = Float.parseFloat(attrib[17]);
				new_etrp.blocker.speedP3Sec = Float.parseFloat(attrib[18]);
				new_etrp.blocker.speedP4Sec = Float.parseFloat(attrib[19]);
				new_etrp.blocker.transXSec = Float.parseFloat(attrib[20]);
				new_etrp.blocker.timeforQuadrantSec = Float.parseFloat(attrib[21]);
				new_etrp.blocker.still_forward_countSec = Integer.parseInt(attrib[22]);
				new_etrp.blocker.rotation_senseSec = Short.parseShort(attrib[23]);
				new_etrp.blocker.can_generate_powerupsSec = Short.parseShort(attrib[24]);
				new_etrp.blocker.stage_values_powerupsSec = Short.parseShort(attrib[25]);
				new_etrp.blocker.type = CRBlockertType.RotativeRobin;
				new_etrp.blocker.rotation_speedSec = new_etrp.blocker.speedP1Sec;
				ComputeBlockerCoords(new_etrp, atlas);
				// may need adjustment for relative Y
				new_etrp.collision_electrotrap_min_check = new_etrp.getY() -
						(new_etrp.blocker.robin_radius +
						 new_etrp.blocker.getHeight())*COLLISION_ADJUSTER_RRO;
				// may need adjustment for relative Y
				new_etrp.collision_electrotrap_max_check = new_etrp.getY() +
						new_etrp.getHeight() + (new_etrp.blocker.robin_radius +
						new_etrp.blocker.getHeight())*COLLISION_ADJUSTER_RRO;
			}
			break;
			case RobinBouncer:
			{
				new_etrp.blocker.relativeY = Float.parseFloat(attrib[0]);
				new_etrp.blocker.relativeX = Float.parseFloat(attrib[1]);
				new_etrp.blocker.bl_length_exp = Float.parseFloat(attrib[2]);
				new_etrp.blocker.exp_speed = Float.parseFloat(attrib[3]);
				new_etrp.blocker.robin_radius = Float.parseFloat(attrib[4]);
				new_etrp.blocker.trigger_angle = Float.parseFloat(attrib[5]);
				new_etrp.blocker.rotation_speed = Float.parseFloat(attrib[6]);
				new_etrp.blocker.speedP1 = Float.parseFloat(attrib[7]);
				new_etrp.blocker.speedP2 = Float.parseFloat(attrib[8]);
				new_etrp.blocker.speedP3 = Float.parseFloat(attrib[9]);
				new_etrp.blocker.speedP4 = Float.parseFloat(attrib[10]);
				new_etrp.blocker.transX = Float.parseFloat(attrib[11]);
				new_etrp.blocker.timeforQuadrant = Float.parseFloat(attrib[12]);
				new_etrp.blocker.still_forward_count = Integer.parseInt(attrib[13]);
				new_etrp.blocker.rotation_sense = Short.parseShort(attrib[14]);
				new_etrp.blocker.can_generate_powerups = Short.parseShort(attrib[15]);
				new_etrp.blocker.stage_values_powerups = Short.parseShort(attrib[16]);
				new_etrp.blocker.rotation_speed = new_etrp.blocker.speedP1;
//				ComputeBlockerCoords(new_etrp, atlas);
				new_etrp.blocker.leftSpeedSec = Float.parseFloat(attrib[17]);
				new_etrp.blocker.middleSpeedSec = Float.parseFloat(attrib[18]);
				new_etrp.blocker.rightSpeedSec = Float.parseFloat(attrib[19]);
				new_etrp.blocker.timeforQuadrantSec = Float.parseFloat(attrib[20]);
				new_etrp.blocker.deployDistanceSec = Float.parseFloat(attrib[21]);
				new_etrp.blocker.deployCounterSec = 0f;
				new_etrp.blocker.currentDirSec = Short.parseShort(attrib[22]);
				new_etrp.blocker.can_generate_powerupsSec = Short.parseShort(attrib[23]);
				new_etrp.blocker.stage_values_powerupsSec = Short.parseShort(attrib[24]);
				if(new_etrp.blocker.currentDirSec == +1){
					new_etrp.blocker.currentSpeedSec = new_etrp.blocker.leftSpeedSec;
				} else {
					new_etrp.blocker.currentSpeedSec = -new_etrp.blocker.rightSpeedSec;
				}
				new_etrp.blocker.type = CRBlockertType.RobinBouncer;
				ComputeBlockerCoords(new_etrp, atlas);
				// may need adjustment for relative Y
				new_etrp.collision_electrotrap_min_check = new_etrp.getY() -
						(new_etrp.blocker.robin_radius +
						 new_etrp.blocker.getHeight())*COLLISION_ADJUSTER;
				// may need adjustment for relative Y
				new_etrp.collision_electrotrap_max_check = new_etrp.getY() +
						new_etrp.getHeight() + (new_etrp.blocker.robin_radius +
						new_etrp.blocker.getHeight())*COLLISION_ADJUSTER;
			}
			break;
			default: { } break;
		}
	}

	// called in the electrotrap constructor - public ex01ElectrotrapRedList(ArrayList<String>
	// elec...
	// we basically load the electrotrap with the data obtained from the settings file (world_data)
	public void LoadElectrotrap(ex01ElectrotrapRed new_etrp, String data){
		attrib = data.split(",");
		new_etrp.world_Y = Float.parseFloat(attrib[0]);
		new_etrp.world_X = Float.parseFloat(attrib[1]) - 2.5f + CENTER_CAMERA_X - CENTER_CAMERA_W2;
		new_etrp.width = Float.parseFloat(attrib[2]);
		new_etrp.height = Float.parseFloat(attrib[3]);
		new_etrp.electrotrap_Width = Float.parseFloat(attrib[4]);
		new_etrp.erPower = Integer.parseInt(attrib[5]);
		new_etrp.R1 = Float.parseFloat(attrib[6]);
		new_etrp.G1 = Float.parseFloat(attrib[7]);
		new_etrp.B1 = Float.parseFloat(attrib[8]);
		new_etrp.A1 = Float.parseFloat(attrib[9]);
		new_etrp.R2 = Float.parseFloat(attrib[10]);
		new_etrp.G2 = Float.parseFloat(attrib[11]);
		new_etrp.B2 = Float.parseFloat(attrib[12]);
		new_etrp.A2 = Float.parseFloat(attrib[13]);
		new_etrp.R3 = Float.parseFloat(attrib[14]);
		new_etrp.G3 = Float.parseFloat(attrib[15]);
		new_etrp.B3 = Float.parseFloat(attrib[16]);
		new_etrp.A3 = Float.parseFloat(attrib[17]);
		ComputeLightningBoltEndsAndRightTrap(new_etrp);
	}

	// generates a new bolt and puts each and every segment of that bolt into an array
	public void GenerateLightningBoltChange(ex01ElectrotrapRed elem_etrp,
											String generate){
		elem_etrp.lbolt.PositionAndScaleBoltToSELeft(
				elem_etrp.bolt_1_start,
				elem_etrp.bolt_1_end,
				2f);
		elem_etrp.lbolt.PositionAndScaleBoltToSERight(
				elem_etrp.bolt_1_startR,
				elem_etrp.bolt_1_endR,
				2f);
	}

	// generates a new bolt and puts each and every segment of that bolt into an array
	public void RotateLeftRightLightningBolts(ex01ElectrotrapRed elem_etrp,
											  String generate){
		if(elem_etrp.electrocuted_left)
			elem_etrp.lbolt.PositionAndScaleBoltToSELeft(
					elem_etrp.bolt_1_start,
					elem_etrp.bolt_1_end,
					2f);
		if(elem_etrp.electrocuted_right)
			elem_etrp.lbolt.PositionAndScaleBoltToSERight(
					elem_etrp.bolt_1_startR,
					elem_etrp.bolt_1_endR,
					2f);
	}

	// generates a new bolt and puts each and every segment of that bolt into an array
	public void GenerateLightningBolt2(ex01ElectrotrapRed elem_etrp){
		elem_etrp.lightning_bolt_secondary = GenerateBolt(
				elem_etrp.bolt_2_start,
				elem_etrp.bolt_2_end,
				3.0f,
				6,
				15f,
				0.7f,
				-2,
				8);
	}

	// generates a new bolt and puts each and every segment of that bolt into an array
	public void GenerateLightningBolt3(ex01ElectrotrapRed elem_etrp){
		elem_etrp.lightning_bolt_tertiary = GenerateBolt(
				elem_etrp.bolt_3_start,
				elem_etrp.bolt_3_end,
				3.0f,
				6,
				15f,
				0.7f,
				-2,
				8);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ex01ElectrotrapSegment> GenerateBolt(Vector2 startPoint,
														  Vector2 endPoint,
														  float maxOffset,
														  int no_generation,
														  float randomSmallAngle,
														  float lengthScale,
														  int minSplit,
														  int maxSplit ){
		ArrayList<ex01ElectrotrapSegment> bolt = new ArrayList<ex01ElectrotrapSegment>();
		ArrayList<ex01ElectrotrapSegment> temp_bolt = new ArrayList<ex01ElectrotrapSegment>();
		Vector2 midPoint;               // calculates the midpoint vector between two vectors
		Vector2 normalToMidpoint;       // calculates the normal to the midpoint vector
		temp_bolt.add(new ex01ElectrotrapSegment(startPoint, endPoint, false));
		// how much to offset the new vertices up or down on the normal
		// to the average of the two directions of the combining lines
		float offsetAmount = maxOffset;
		Random r = new Random();
		//-offsetAmount;   // max amount to translate against the midpoint normal downwards
		//+offsetAmount;   // max amount to translate against the midpoint normal upwards
		Vector2 start, end;
		for(int i = 0; i < no_generation; i++){
			bolt.clear();
			bolt = (ArrayList<ex01ElectrotrapSegment>)temp_bolt.clone();
			temp_bolt.clear();
		    Random randI = new Random();
			for (int j = 0; j < bolt.size(); j++){
				start = bolt.get(j).start;
				end = bolt.get(j).end;
				midPoint = midPointFunc(start, end);
				Vector2 endP = new Vector2(end);
				normalToMidpoint = endP.sub(midPoint).rotate(90f).nor();
				Vector2 pn = new Vector2(midPoint.add(normalToMidpoint
						.scl(-offsetAmount + (r.nextFloat() * (2 * offsetAmount)))));
				temp_bolt.add(new ex01ElectrotrapSegment(start, pn, false));
				temp_bolt.add(new ex01ElectrotrapSegment(pn, end, false));
				//occasionally we split a segment into 3 segments not two so we have continuation
				//increasing the negative/positive ratio will increase the chances of a 3 seg split
				if(randInt(minSplit,maxSplit, randI) < 0){
					Vector2 mid = new Vector2(midPoint);
					Vector2 direction = mid.sub(start);
					// lengthScale is, for best results, < 1.  0.7 is a good value.
					Vector2 splitEnd = direction.rotate(
							((randInt(minSplit,maxSplit, randI) < 0)?1:(-1) ) *  randomSmallAngle )
							 .scl(lengthScale).add(midPoint);
					temp_bolt.add(new ex01ElectrotrapSegment(midPoint, splitEnd, true));
				}
			}
			offsetAmount /= 2;
		}
		return temp_bolt;
	}

	// calculates the midpoint vector between two vectors
	public Vector2 midPointFunc(Vector2 a, Vector2 b){
		Vector2 aa = new Vector2(a);
		Vector2 bb = new Vector2(b);
		return ( aa.add(bb.sub(aa).scl(0.5f)) );
	}

	// generates random integer number between two integers
	public static int randInt(int min, int max, Random rand) {
	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}

	// rotate the rotative blocker
	public void RotateBlockerRO(ex01BlockerYellow blocker, float delta){
		RaccelQ1 = blocker.rotationAccelQ1 * delta;
		RaccelQ2 = blocker.rotationAccelQ2 * delta;
		RaccelQ3 = blocker.rotationAccelQ3 * delta;
		RaccelQ4 = blocker.rotationAccelQ4 * delta;
		if(blocker.sprite_angle >= ANGLE360_THRES){
			blocker.sprite_angle = ANGLE0_RESETER;
			blocker.rotation_speed = blocker.speedP1;
			blocker.setRotation(ANGLE0_RESETER);
			blocker.right.setRotation(ANGLE0_RESETER);
			blocker.left_circle_origin_circle.setAngle(ANGLE180_SETTER);
			blocker.right_circle_origin_circle.setAngle(ANGLE360_SETTER);
		}
		if(blocker.sprite_angle < ANGLE45_THRES
		&& blocker.sprite_angle >= ANGLE0_THRES){
			blocker.rotation_speed += RaccelQ1;
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotate(blocker.rotation_speed);
			blocker.right.rotate(blocker.rotation_speed);
			blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
			blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
		} else if (blocker.sprite_angle < ANGLE90_THRES
				&& blocker.sprite_angle >= ANGLE45_THRES){
			blocker.rotation_speed += RaccelQ2;
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotate(blocker.rotation_speed);
			blocker.right.rotate(blocker.rotation_speed);
			blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
			blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
		} else if (blocker.sprite_angle < ANGLE135_THRES
				&& blocker.sprite_angle >= ANGLE90_THRES){
			blocker.rotation_speed += RaccelQ3;
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotate(blocker.rotation_speed);
			blocker.right.rotate(blocker.rotation_speed);
			blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
			blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
		} else if (blocker.sprite_angle < ANGLE180_THRES
				&& blocker.sprite_angle >= ANGLE135_THRES){
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotation_speed += RaccelQ4;
			if( (blocker.rotation_speed) < SPEED_COMPARER ){
				blocker.rotate(ANGLE180_THRES - blocker.sprite_angle);
				blocker.right.rotate(ANGLE180_THRES - blocker.sprite_angle);
				blocker.sprite_angle = ANGLE180_THRES;
				blocker.left_circle_origin_circle.setAngle(ANGLE0_RESETTER);
				blocker.right_circle_origin_circle.setAngle(ANGLE180_RESETTER);
			} else {
				blocker.rotate(blocker.rotation_speed);
				blocker.right.rotate(blocker.rotation_speed);
				blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
				blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
			}
		} else if( blocker.sprite_angle < ANGLE225_THRES
				&& blocker.sprite_angle >= ANGLE180_THRES){
			blocker.rotation_speed += RaccelQ1;
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotate(blocker.rotation_speed);
			blocker.right.rotate(blocker.rotation_speed);
			blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
			blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
		} else if (blocker.sprite_angle < ANGLE270_THRES
				&& blocker.sprite_angle >= ANGLE225_THRES){
			blocker.rotation_speed += RaccelQ2;
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotate(blocker.rotation_speed);
			blocker.right.rotate(blocker.rotation_speed);
			blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
			blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
		} else if (blocker.sprite_angle < ANGLE315_THRES
				&& blocker.sprite_angle >= ANGLE270_THRES){
			blocker.rotation_speed += RaccelQ3;
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotate(blocker.rotation_speed);
			blocker.right.rotate(blocker.rotation_speed);
			blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
			blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
		} else if (blocker.sprite_angle < ANGLE360_THRES
				&& blocker.sprite_angle >= ANGLE315_THRES){
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotation_speed += RaccelQ4;
			if( (blocker.rotation_speed) < SPEED_COMPARER ){
				blocker.rotate(ANGLE360_THRES - blocker.sprite_angle);
				blocker.right.rotate(ANGLE360_THRES - blocker.sprite_angle);
				blocker.startLine.rotate(ANGLE360_THRES - blocker.sprite_angle);
				blocker.sprite_angle = ANGLE360_THRES;
				blocker.left_circle_origin_circle.setAngle(ANGLE360_SETTER);
				blocker.right_circle_origin_circle.setAngle(ANGLE360_SETTER);
			} else {
				blocker.rotate(blocker.rotation_speed);
				blocker.right.rotate(blocker.rotation_speed);
				blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
				blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
			}
		}
		left_orig_v.x = blocker.left_circle_origin_circle.x;
		left_orig_v.y = blocker.left_circle_origin_circle.y;
		left_blocker_orig.x = blocker.endLine.x;
		left_blocker_orig.y = blocker.endLine.y;
		right_orig_v.x = blocker.right_circle_origin_circle.x;
		right_orig_v.y = blocker.right_circle_origin_circle.y;
		right_blocker_orig.x = blocker.endLine.x;
		right_blocker_orig.y = blocker.endLine.y;
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	// rotate the rotative blocker
	public void RotateBlockerROSec(ex01BlockerYellow blocker, float delta){
		RaccelQ1 = blocker.rotationAccelQ1 * delta;
		RaccelQ2 = blocker.rotationAccelQ2 * delta;
		RaccelQ3 = blocker.rotationAccelQ3 * delta;
		RaccelQ4 = blocker.rotationAccelQ4 * delta;
		if(blocker.sprite_angle >= ANGLE360_THRES){
			blocker.sprite_angle = ANGLE0_RESETER;
			blocker.rotation_speed = blocker.speedP1;
			blocker.setRotation(ANGLE0_RESETER);
			blocker.right.setRotation(ANGLE0_RESETER);
			blocker.left_circle_origin_circle.setAngle(ANGLE180_SETTER);
			blocker.right_circle_origin_circle.setAngle(ANGLE360_SETTER);
		}
		if(blocker.sprite_angle < ANGLE45_THRES
	    && blocker.sprite_angle >= ANGLE0_THRES){
			blocker.rotation_speed += RaccelQ1;
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotate(blocker.rotation_speed);
			blocker.right.rotate(blocker.rotation_speed);
			blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
			blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
		} else if (blocker.sprite_angle < ANGLE90_THRES
				&& blocker.sprite_angle >= ANGLE45_THRES){
			blocker.rotation_speed += RaccelQ2;
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotate(blocker.rotation_speed);
			blocker.right.rotate(blocker.rotation_speed);
			blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
			blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
		} else if (blocker.sprite_angle < ANGLE135_THRES
				&& blocker.sprite_angle >= ANGLE90_THRES){
			blocker.rotation_speed += RaccelQ3;
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotate(blocker.rotation_speed);
			blocker.right.rotate(blocker.rotation_speed);
			blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
			blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
		} else if (blocker.sprite_angle < ANGLE180_THRES
				&& blocker.sprite_angle >= ANGLE135_THRES){
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotation_speed += RaccelQ4;
			if( (blocker.rotation_speed) < SPEED_COMPARER ){
				blocker.rotate(ANGLE180_THRES - blocker.sprite_angle);
				blocker.right.rotate(ANGLE180_THRES - blocker.sprite_angle);
				blocker.sprite_angle = ANGLE180_THRES;
				blocker.left_circle_origin_circle.setAngle(ANGLE0_RESETTER);
				blocker.right_circle_origin_circle.setAngle(ANGLE180_RESETTER);
			} else {
				blocker.rotate(blocker.rotation_speed);
				blocker.right.rotate(blocker.rotation_speed);
				blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
				blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
			}
		} else if(blocker.sprite_angle < ANGLE225_THRES
			   && blocker.sprite_angle >= ANGLE180_THRES){
			blocker.rotation_speed += RaccelQ1;
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotate(blocker.rotation_speed);
			blocker.right.rotate(blocker.rotation_speed);
			blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
			blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
		} else if (blocker.sprite_angle < ANGLE270_THRES
				&& blocker.sprite_angle >= ANGLE225_THRES){
			blocker.rotation_speed += RaccelQ2;
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotate(blocker.rotation_speed);
			blocker.right.rotate(blocker.rotation_speed);
			blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
			blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
		} else if (blocker.sprite_angle < ANGLE315_THRES
				&& blocker.sprite_angle >= ANGLE270_THRES){
			blocker.rotation_speed += RaccelQ3;
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotate(blocker.rotation_speed);
			blocker.right.rotate(blocker.rotation_speed);
			blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
			blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
		} else if (blocker.sprite_angle < ANGLE360_THRES
				&& blocker.sprite_angle >= ANGLE315_THRES){
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotation_speed += RaccelQ4;
			if( (blocker.rotation_speed) < SPEED_COMPARER ){
				blocker.rotate(ANGLE360_SETTER - blocker.sprite_angle);
				blocker.right.rotate(ANGLE360_SETTER - blocker.sprite_angle);
				blocker.startLine.rotate(ANGLE360_SETTER - blocker.sprite_angle);
				blocker.sprite_angle = ANGLE360_THRES;
				blocker.left_circle_origin_circle.setAngle(ANGLE360_SETTER);
				blocker.right_circle_origin_circle.setAngle(ANGLE360_SETTER);
			} else {
				blocker.rotate(blocker.rotation_speed);
				blocker.right.rotate(blocker.rotation_speed);
				blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
				blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
			}
		}
	}

	// rotate the rotative blocker
	public void RotateBlockerRONeg(ex01BlockerYellow blocker, float delta){
		RaccelQ1 = blocker.rotationAccelQ1 * delta;
		RaccelQ2 = blocker.rotationAccelQ2 * delta;
		RaccelQ3 = blocker.rotationAccelQ3 * delta;
		RaccelQ4 = blocker.rotationAccelQ4 * delta;
		rot = blocker.rotation_sense;
		if(blocker.sprite_angle <= ANGLE360_THRESNeg){
			blocker.sprite_angle = ANGLE0_RESETERNeg;
			blocker.rotation_speed = blocker.speedP1;
			blocker.setRotation(ANGLE0_RESETERNeg);
			blocker.right.setRotation(ANGLE0_RESETERNeg);
			blocker.left_circle_origin_circle.setAngle(ANGLE180_SETTERNeg);
			blocker.right_circle_origin_circle.setAngle(ANGLE360_SETTERNeg);
		}
		if(blocker.sprite_angle > ANGLE45_THRESNeg
	    && blocker.sprite_angle <= ANGLE0_THRESNeg){
			blocker.rotation_speed += RaccelQ1;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			blocker.setRotation(blocker.sprite_angle);
			blocker.right.setRotation(blocker.sprite_angle);
			blocker.left_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE180_SETTERNeg);
			blocker.right_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE360_SETTERNeg);
		} else if (blocker.sprite_angle > ANGLE90_THRESNeg
				&& blocker.sprite_angle <= ANGLE45_THRESNeg){
			blocker.rotation_speed += RaccelQ2;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			blocker.setRotation(blocker.sprite_angle);
			blocker.right.setRotation(blocker.sprite_angle);
			blocker.left_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE180_SETTERNeg);
			blocker.right_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE360_SETTERNeg);
		} else if (blocker.sprite_angle > ANGLE135_THRESNeg
				&& blocker.sprite_angle <= ANGLE90_THRESNeg){
			blocker.rotation_speed += RaccelQ3;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			blocker.setRotation(blocker.sprite_angle);
			blocker.right.setRotation(blocker.sprite_angle);
			blocker.left_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE180_SETTERNeg);
			blocker.right_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE360_SETTERNeg);
		} else if (blocker.sprite_angle > ANGLE180_THRESNeg
				&& blocker.sprite_angle <= ANGLE135_THRESNeg){
			blocker.rotation_speed += RaccelQ4;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			if( (blocker.rotation_speed) < SPEED_COMPARER ){
				blocker.rotate(ANGLE180_THRES - blocker.sprite_angle);
				blocker.right.rotate(ANGLE180_THRES - blocker.sprite_angle);
				blocker.sprite_angle = ANGLE180_THRESNeg;
				blocker.left_circle_origin_circle.setAngle(ANGLE0_RESETTERNeg);
				blocker.right_circle_origin_circle.setAngle(ANGLE180_RESETTERNeg);
			} else {
				blocker.setRotation(blocker.sprite_angle);
				blocker.right.setRotation(blocker.sprite_angle);
				blocker.left_circle_origin_circle
						.setAngle(blocker.sprite_angle + ANGLE180_SETTERNeg);
				blocker.right_circle_origin_circle
						.setAngle(blocker.sprite_angle + ANGLE360_SETTERNeg);
			}
		} else if(blocker.sprite_angle > ANGLE225_THRESNeg
			   && blocker.sprite_angle <= ANGLE180_THRESNeg){
			blocker.rotation_speed += RaccelQ1;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			blocker.setRotation(blocker.sprite_angle);
			blocker.right.setRotation(blocker.sprite_angle);
			blocker.left_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE180_SETTERNeg);
			blocker.right_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE360_SETTERNeg);
		} else if (blocker.sprite_angle > ANGLE270_THRESNeg
				&& blocker.sprite_angle <= ANGLE225_THRESNeg){
			blocker.rotation_speed += RaccelQ2;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			blocker.setRotation(blocker.sprite_angle);
			blocker.right.setRotation(blocker.sprite_angle);
			blocker.left_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE180_SETTERNeg);
			blocker.right_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE360_SETTERNeg);
		} else if (blocker.sprite_angle > ANGLE315_THRESNeg
				&& blocker.sprite_angle <= ANGLE270_THRESNeg){
			blocker.rotation_speed += RaccelQ3;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			blocker.setRotation(blocker.sprite_angle);
			blocker.right.setRotation(blocker.sprite_angle);
			blocker.left_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE180_SETTERNeg);
			blocker.right_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE360_SETTERNeg);
		} else if (blocker.sprite_angle > ANGLE360_THRESNeg
				&& blocker.sprite_angle <= ANGLE315_THRESNeg){
			blocker.rotation_speed += RaccelQ4;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			if( (blocker.rotation_speed) < SPEED_COMPARER ){
				blocker.rotate(ANGLE360_THRESNeg - blocker.sprite_angle);
				blocker.right.rotate(ANGLE360_THRESNeg - blocker.sprite_angle);
				blocker.startLine.rotate(ANGLE360_THRESNeg - blocker.sprite_angle);
				blocker.sprite_angle = ANGLE360_THRESNeg;
				blocker.left_circle_origin_circle.setAngle(-ANGLE360_SETTERNeg);
				blocker.right_circle_origin_circle.setAngle(-ANGLE360_SETTERNeg);
			} else {
				blocker.setRotation(blocker.sprite_angle);
				blocker.right.setRotation(blocker.sprite_angle);
				blocker.left_circle_origin_circle
						.setAngle(blocker.sprite_angle + ANGLE180_SETTERNeg);
				blocker.right_circle_origin_circle
						.setAngle(blocker.sprite_angle + ANGLE360_SETTERNeg);
			}
		}
		left_orig_v.x = blocker.left_circle_origin_circle.x;
		left_orig_v.y = blocker.left_circle_origin_circle.y;
		left_blocker_orig.x = blocker.endLine.x;
		left_blocker_orig.y = blocker.endLine.y;
		right_orig_v.x = blocker.right_circle_origin_circle.x;
		right_orig_v.y = blocker.right_circle_origin_circle.y;
		right_blocker_orig.x = blocker.endLine.x;
		right_blocker_orig.y = blocker.endLine.y;
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	// rotate the rotative blocker
	public void RotateBlockerRONegSec(ex01BlockerYellow blocker, float delta){
		RaccelQ1 = blocker.rotationAccelQ1 * delta;
		RaccelQ2 = blocker.rotationAccelQ2 * delta;
		RaccelQ3 = blocker.rotationAccelQ3 * delta;
		RaccelQ4 = blocker.rotationAccelQ4 * delta;
		rot = blocker.rotation_sense;
		if(blocker.sprite_angle <= ANGLE360_THRESNeg){
			blocker.sprite_angle = ANGLE0_RESETERNeg;
			blocker.rotation_speed = blocker.speedP1;
			blocker.setRotation(ANGLE0_RESETERNeg);
			blocker.right.setRotation(ANGLE0_RESETERNeg);
			blocker.left_circle_origin_circle.setAngle(ANGLE180_SETTERNeg);
			blocker.right_circle_origin_circle.setAngle(ANGLE360_SETTERNeg);
		}
		if(blocker.sprite_angle > ANGLE45_THRESNeg
	    && blocker.sprite_angle <= ANGLE0_THRESNeg){
			blocker.rotation_speed += RaccelQ1;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			blocker.setRotation(blocker.sprite_angle);
			blocker.right.setRotation(blocker.sprite_angle);
			blocker.left_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE180_SETTERNeg);
			blocker.right_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE360_SETTERNeg);
		} else if (blocker.sprite_angle > ANGLE90_THRESNeg
				&& blocker.sprite_angle <= ANGLE45_THRESNeg){
			blocker.rotation_speed += RaccelQ2;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			blocker.setRotation(blocker.sprite_angle);
			blocker.right.setRotation(blocker.sprite_angle);
			blocker.left_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE180_SETTERNeg);
			blocker.right_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE360_SETTERNeg);
		} else if (blocker.sprite_angle > ANGLE135_THRESNeg
				&& blocker.sprite_angle <= ANGLE90_THRESNeg){
			blocker.rotation_speed += RaccelQ3;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			blocker.setRotation(blocker.sprite_angle);
			blocker.right.setRotation(blocker.sprite_angle);
			blocker.left_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE180_SETTERNeg);
			blocker.right_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE360_SETTERNeg);
		} else if (blocker.sprite_angle > ANGLE180_THRESNeg
				&& blocker.sprite_angle <= ANGLE135_THRESNeg){
			blocker.rotation_speed += RaccelQ4;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			if( (blocker.rotation_speed) < SPEED_COMPARER ){
				blocker.rotate(ANGLE180_THRES - blocker.sprite_angle);
				blocker.right.rotate(ANGLE180_THRES - blocker.sprite_angle);
				blocker.sprite_angle = ANGLE180_THRESNeg;
				blocker.left_circle_origin_circle.setAngle(ANGLE0_RESETTERNeg);
				blocker.right_circle_origin_circle.setAngle(ANGLE180_RESETTERNeg);
			} else {
				blocker.setRotation(blocker.sprite_angle);
				blocker.right.setRotation(blocker.sprite_angle);
				blocker.left_circle_origin_circle
						.setAngle(blocker.sprite_angle + ANGLE180_SETTERNeg);
				blocker.right_circle_origin_circle
						.setAngle(blocker.sprite_angle + ANGLE360_SETTERNeg);
			}
		} else if(blocker.sprite_angle > ANGLE225_THRESNeg
			   && blocker.sprite_angle <= ANGLE180_THRESNeg){
			blocker.rotation_speed += RaccelQ1;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			blocker.setRotation(blocker.sprite_angle);
			blocker.right.setRotation(blocker.sprite_angle);
			blocker.left_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE180_SETTERNeg);
			blocker.right_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE360_SETTERNeg);
		} else if (blocker.sprite_angle > ANGLE270_THRESNeg
				&& blocker.sprite_angle <= ANGLE225_THRESNeg){
			blocker.rotation_speed += RaccelQ2;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			blocker.setRotation(blocker.sprite_angle);
			blocker.right.setRotation(blocker.sprite_angle);
			blocker.left_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE180_SETTERNeg);
			blocker.right_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE360_SETTERNeg);
		} else if (blocker.sprite_angle > ANGLE315_THRESNeg
				&& blocker.sprite_angle <= ANGLE270_THRESNeg){
			blocker.rotation_speed += RaccelQ3;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			blocker.setRotation(blocker.sprite_angle);
			blocker.right.setRotation(blocker.sprite_angle);
			blocker.left_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE180_SETTERNeg);
			blocker.right_circle_origin_circle
					.setAngle(blocker.sprite_angle + ANGLE360_SETTERNeg);
		} else if (blocker.sprite_angle > ANGLE360_THRESNeg
				&& blocker.sprite_angle <= ANGLE315_THRESNeg){
			blocker.rotation_speed += RaccelQ4;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			if( (blocker.rotation_speed) < SPEED_COMPARER ){
				blocker.rotate(ANGLE360_THRESNeg - blocker.sprite_angle);
				blocker.right.rotate(ANGLE360_THRESNeg - blocker.sprite_angle);
				blocker.startLine.rotate(ANGLE360_THRESNeg - blocker.sprite_angle);
				blocker.sprite_angle = ANGLE360_THRESNeg;
				blocker.left_circle_origin_circle.setAngle(-ANGLE360_SETTERNeg);
				blocker.right_circle_origin_circle.setAngle(-ANGLE360_SETTERNeg);
			} else {
				blocker.setRotation(blocker.sprite_angle);
				blocker.right.setRotation(blocker.sprite_angle);
				blocker.left_circle_origin_circle
						.setAngle(blocker.sprite_angle + ANGLE180_SETTERNeg);
				blocker.right_circle_origin_circle
						.setAngle(blocker.sprite_angle + ANGLE360_SETTERNeg);
			}
		}
	}

	// rotate the Round Robin blocker
	public void RotateBlockerRR(ex01BlockerYellow blocker, float delta){
		RaccelQ1 = blocker.rotationAccelQ1 * delta;
		RaccelQ2 = blocker.rotationAccelQ2 * delta;
		RaccelQ3 = blocker.rotationAccelQ3 * delta;
		RaccelQ4 = blocker.rotationAccelQ4 * delta;
		if(blocker.sprite_angle >= ANGLE360_THRES){
			blocker.sprite_angle = ANGLE0_RESETER;
			blocker.rotation_speed = blocker.speedP1;
			blocker.setRotation(ANGLE0_RESETER);
			blocker.right.setRotation(ANGLE0_RESETER);
			blocker.startLine.setAngle(ANGLE270_RESETER);
			blocker.ResetCircles();
		}
		if(blocker.sprite_angle < ANGLE45_THRES
	    && blocker.sprite_angle >= ANGLE0_THRES){
			blocker.rotation_speed += RaccelQ1;
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotate(blocker.rotation_speed);
			blocker.right.rotate(blocker.rotation_speed);
			blocker.startLine.rotate(blocker.rotation_speed);
			blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
			blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
		} else if (blocker.sprite_angle < ANGLE90_THRES
				&& blocker.sprite_angle >= ANGLE45_THRES){
			blocker.rotation_speed += RaccelQ2;
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotate(blocker.rotation_speed);
			blocker.right.rotate(blocker.rotation_speed);
			blocker.startLine.rotate(blocker.rotation_speed);
			blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
			blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
		} else if (blocker.sprite_angle < ANGLE135_THRES
				&& blocker.sprite_angle >= ANGLE90_THRES){
			blocker.rotation_speed += RaccelQ3;
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotate(blocker.rotation_speed);
			blocker.right.rotate(blocker.rotation_speed);
			blocker.startLine.rotate(blocker.rotation_speed);
			blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
			blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
		} else if (blocker.sprite_angle < ANGLE180_THRES
				&& blocker.sprite_angle >= ANGLE135_THRES){
			blocker.rotation_speed += RaccelQ4;
			blocker.sprite_angle += blocker.rotation_speed;
			if( (blocker.rotation_speed) < 0f ){
				blocker.rotate(ANGLE180_THRES - blocker.sprite_angle);
				blocker.right.rotate(ANGLE180_THRES - blocker.sprite_angle);
				blocker.sprite_angle = ANGLE180_THRES;
				blocker.startLine.setAngle(ANGLE90_THRES);
				blocker.left_circle_origin_circle.setAngle(ANGLE76_RESETTER);
				blocker.right_circle_origin_circle.setAngle(ANGLE102_RESETTER);
			} else {
				blocker.rotate(blocker.rotation_speed + ANGLE_OVERCATCHER);
				blocker.right.rotate(blocker.rotation_speed + ANGLE_OVERCATCHER);
				blocker.startLine.rotate(blocker.rotation_speed);
				blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
				blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
			}
		} else if(blocker.sprite_angle < ANGLE225_THRES
			   && blocker.sprite_angle >= ANGLE180_THRES){
			blocker.rotation_speed += RaccelQ1;
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotate(blocker.rotation_speed);
			blocker.right.rotate(blocker.rotation_speed);
			blocker.startLine.rotate(blocker.rotation_speed);
			blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
			blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
		} else if (blocker.sprite_angle < ANGLE270_THRES
				&& blocker.sprite_angle >= ANGLE225_THRES){
			blocker.rotation_speed += RaccelQ2;
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotate(blocker.rotation_speed);
			blocker.right.rotate(blocker.rotation_speed);
			blocker.startLine.rotate(blocker.rotation_speed);
			blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
			blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
		} else if (blocker.sprite_angle < ANGLE315_THRES
				&& blocker.sprite_angle >= ANGLE270_THRES){
			blocker.rotation_speed += RaccelQ3;
			blocker.sprite_angle += blocker.rotation_speed;
			blocker.rotate(blocker.rotation_speed);
			blocker.right.rotate(blocker.rotation_speed);
			blocker.startLine.rotate(blocker.rotation_speed);
			blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
			blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
		} else if (blocker.sprite_angle < ANGLE360_THRES
				&& blocker.sprite_angle >= ANGLE315_THRES){
			blocker.rotation_speed += RaccelQ4;
			blocker.sprite_angle += blocker.rotation_speed;
			if( (blocker.rotation_speed) < SPEED_COMPARER ){
				blocker.rotate(ANGLE360_THRES - blocker.sprite_angle);
				blocker.right.rotate(ANGLE360_THRES - blocker.sprite_angle);
				blocker.sprite_angle = ANGLE360_THRES;
				blocker.startLine.setAngle(ANGLE270_THRES);
				blocker.left_circle_origin_circle.setAngle(ANGLE256_SETTER);
				blocker.right_circle_origin_circle.setAngle(ANGLE282_SETTER);
				blocker.ResetCircles();
			} else {
				blocker.rotate(blocker.rotation_speed + ANGLE_OVERCATCHER);
				blocker.right.rotate(blocker.rotation_speed + ANGLE_OVERCATCHER);
				blocker.startLine.rotate(blocker.rotation_speed);
				blocker.left_circle_origin_circle.rotate(blocker.rotation_speed);
				blocker.right_circle_origin_circle.rotate(blocker.rotation_speed);
			}
		}
		left_orig_v.x = blocker.left_circle_origin_circle.x;
		left_orig_v.y = blocker.left_circle_origin_circle.y;
		left_blocker_orig.x = blocker.endLine.x;
		left_blocker_orig.y = blocker.endLine.y;
		right_orig_v.x = blocker.right_circle_origin_circle.x;
		right_orig_v.y = blocker.right_circle_origin_circle.y;
		right_blocker_orig.x = blocker.endLine.x;
		right_blocker_orig.y = blocker.endLine.y;
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	// rotate the Round Robin blocker
	public void RotateBlockerRRNeg(ex01BlockerYellow blocker, float delta){
		RaccelQ1 = blocker.rotationAccelQ1 * delta;
		RaccelQ2 = blocker.rotationAccelQ2 * delta;
		RaccelQ3 = blocker.rotationAccelQ3 * delta;
		RaccelQ4 = blocker.rotationAccelQ4 * delta;
		rot = blocker.rotation_sense;
		if(blocker.sprite_angle <= ANGLE360_THRESNeg){
			blocker.sprite_angle = ANGLE0_RESETERNeg;
			blocker.rotation_speed = blocker.speedP1;
			blocker.setRotation(ANGLE0_RESETERNeg);
			blocker.right.setRotation(ANGLE0_RESETERNeg);
			blocker.startLine.setAngle(ANGLE270_RESETER);
			blocker.ResetCircles();
		}
		if(blocker.sprite_angle > ANGLE45_THRESNeg
	    && blocker.sprite_angle <= ANGLE0_RESETERNeg){
			blocker.rotation_speed += RaccelQ1;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			blocker.rotate(rot);
			blocker.right.rotate(rot);
			blocker.startLine.rotate(rot);
			blocker.left_circle_origin_circle.rotate(rot);
			blocker.right_circle_origin_circle.rotate(rot);
		} else if (blocker.sprite_angle > ANGLE90_THRESNeg
				&& blocker.sprite_angle <= ANGLE45_THRESNeg){
			blocker.rotation_speed += RaccelQ2;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			blocker.rotate(rot);
			blocker.right.rotate(rot);
			blocker.startLine.rotate(rot);
			blocker.left_circle_origin_circle.rotate(rot);
			blocker.right_circle_origin_circle.rotate(rot);
		} else if (blocker.sprite_angle > ANGLE135_THRESNeg
				&& blocker.sprite_angle <= ANGLE90_THRESNeg){
			blocker.rotation_speed += RaccelQ3;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			blocker.rotate(rot);
			blocker.right.rotate(rot);
			blocker.startLine.rotate(rot);
			blocker.left_circle_origin_circle.rotate(rot);
			blocker.right_circle_origin_circle.rotate(rot);
		} else if (blocker.sprite_angle > ANGLE180_THRESNeg
				&& blocker.sprite_angle <= ANGLE135_THRESNeg){
			blocker.rotation_speed += RaccelQ4;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			if( (blocker.rotation_speed) < SPEED_COMPARER ){
				blocker.rotate(ANGLE180_THRES - blocker.sprite_angle);
				blocker.right.rotate(ANGLE180_THRES - blocker.sprite_angle);
				blocker.sprite_angle = ANGLE180_THRESNeg;
				blocker.startLine.setAngle(ANGLE90_THRESNeg);
				blocker.left_circle_origin_circle.setAngle(ANGLE76_RESETTERNeg);
				blocker.right_circle_origin_circle.setAngle(ANGLE102_RESETTERNeg);
			} else {
				blocker.rotate(rot + 0.001f);
				blocker.right.rotate(rot + 0.001f);
				blocker.startLine.rotate(rot);
				blocker.left_circle_origin_circle.rotate(rot);
				blocker.right_circle_origin_circle.rotate(rot);
			}
		} else if(blocker.sprite_angle > ANGLE225_THRESNeg
			   && blocker.sprite_angle <= ANGLE180_THRESNeg){
			blocker.rotation_speed += RaccelQ1;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			blocker.rotate(rot);
			blocker.right.rotate(rot);
			blocker.startLine.rotate(rot);
			blocker.left_circle_origin_circle.rotate(rot);
			blocker.right_circle_origin_circle.rotate(rot);
		} else if (blocker.sprite_angle > ANGLE270_THRESNeg
				&& blocker.sprite_angle <= ANGLE225_THRESNeg){
			blocker.rotation_speed += RaccelQ2;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			blocker.rotate(rot);
			blocker.right.rotate(rot);
			blocker.startLine.rotate(rot);
			blocker.left_circle_origin_circle.rotate(rot);
			blocker.right_circle_origin_circle.rotate(rot);
		} else if (blocker.sprite_angle > ANGLE315_THRESNeg
				&& blocker.sprite_angle <= ANGLE270_THRESNeg){
			blocker.rotation_speed += RaccelQ3;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			blocker.rotate(rot);
			blocker.right.rotate(rot);
			blocker.startLine.rotate(rot);
			blocker.left_circle_origin_circle.rotate(rot);
			blocker.right_circle_origin_circle.rotate(rot);
		} else if (blocker.sprite_angle > ANGLE360_THRESNeg
				&& blocker.sprite_angle <= ANGLE315_THRESNeg){
			blocker.rotation_speed += RaccelQ4;
			rot = blocker.rotation_speed * rot;
			blocker.sprite_angle += rot;
			if( (blocker.rotation_speed) < SPEED_COMPARER ){
				blocker.rotate(ANGLE360_THRES - blocker.sprite_angle);
				blocker.right.rotate(ANGLE360_THRES - blocker.sprite_angle);
				blocker.sprite_angle = ANGLE360_THRESNeg;
				blocker.startLine.setAngle(ANGLE270_THRESNeg);
				blocker.left_circle_origin_circle.setAngle(ANGLE256_SETTERNeg);
				blocker.right_circle_origin_circle.setAngle(ANGLE282_SETTERNeg);
				blocker.ResetCircles();
			} else {
				blocker.rotate(rot + ANGLE_OVERCATCHER);
				blocker.right.rotate(rot + ANGLE_OVERCATCHER);
				blocker.startLine.rotate(rot);
				blocker.left_circle_origin_circle.rotate(rot);
				blocker.right_circle_origin_circle.rotate(rot);
			}
		}
		left_orig_v.x = blocker.left_circle_origin_circle.x;
		left_orig_v.y = blocker.left_circle_origin_circle.y;
		left_blocker_orig.x = blocker.endLine.x;
		left_blocker_orig.y = blocker.endLine.y;
		right_orig_v.x = blocker.right_circle_origin_circle.x;
		right_orig_v.y = blocker.right_circle_origin_circle.y;
		right_blocker_orig.x = blocker.endLine.x;
		right_blocker_orig.y = blocker.endLine.y;
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	// rotate the Round Robin blocker
	public void RotateBlockerRRSec(ex01BlockerYellow blocker, float delta){
		RaccelQ1 = blocker.rotationAccelQ1Sec * delta;
		RaccelQ2 = blocker.rotationAccelQ2Sec * delta;
		RaccelQ3 = blocker.rotationAccelQ3Sec * delta;
		RaccelQ4 = blocker.rotationAccelQ4Sec * delta;
		if(blocker.sprite_angleSec >= ANGLE360_THRES){
			blocker.sprite_angleSec = ANGLE0_RESETER;
			blocker.rotation_speedSec = blocker.speedP1Sec;
			blocker.setRotation(ANGLE0_RESETER);
			blocker.right.setRotation(ANGLE0_RESETER);
			blocker.startLineSec.setAngle(ANGLE270_RESETER);
			blocker.left_circle_origin_circleSec.setAngle(ANGLE256_SETTER);
			blocker.right_circle_origin_circleSec.setAngle(ANGLE282_SETTER);
		}
		if(blocker.sprite_angleSec < ANGLE45_THRES
	    && blocker.sprite_angleSec >= ANGLE0_THRES){
			blocker.rotation_speedSec += RaccelQ1;
			blocker.sprite_angleSec += blocker.rotation_speedSec;
			blocker.rotate(blocker.rotation_speedSec);
			blocker.right.rotate(blocker.rotation_speedSec);
			blocker.startLineSec.rotate(blocker.rotation_speedSec);
			blocker.left_circle_origin_circleSec.rotate(blocker.rotation_speedSec);
			blocker.right_circle_origin_circleSec.rotate(blocker.rotation_speedSec);
		} else if (blocker.sprite_angleSec < ANGLE90_THRES
				&& blocker.sprite_angleSec >= ANGLE45_THRES){
			blocker.rotation_speedSec += RaccelQ2;
			blocker.sprite_angleSec += blocker.rotation_speedSec;
			blocker.rotate(blocker.rotation_speedSec);
			blocker.right.rotate(blocker.rotation_speedSec);
			blocker.startLineSec.rotate(blocker.rotation_speedSec);
			blocker.left_circle_origin_circleSec.rotate(blocker.rotation_speedSec);
			blocker.right_circle_origin_circleSec.rotate(blocker.rotation_speedSec);
		} else if (blocker.sprite_angleSec < ANGLE135_THRES
				&& blocker.sprite_angleSec >= ANGLE90_THRES){
			blocker.rotation_speedSec += RaccelQ3;
			blocker.sprite_angleSec += blocker.rotation_speedSec;
			blocker.rotate(blocker.rotation_speedSec);
			blocker.right.rotate(blocker.rotation_speedSec);
			blocker.startLineSec.rotate(blocker.rotation_speedSec);
			blocker.left_circle_origin_circleSec.rotate(blocker.rotation_speedSec);
			blocker.right_circle_origin_circleSec.rotate(blocker.rotation_speedSec);
		} else if (blocker.sprite_angleSec < ANGLE180_THRES
				&& blocker.sprite_angleSec >= ANGLE135_THRES){
			blocker.rotation_speedSec += RaccelQ4;
			blocker.sprite_angleSec += blocker.rotation_speedSec;
			if( (blocker.rotation_speedSec) < SPEED_COMPARER ){
				blocker.rotate(ANGLE180_THRES - blocker.sprite_angleSec);
				blocker.right.rotate(ANGLE180_THRES - blocker.sprite_angleSec);
				blocker.sprite_angleSec = ANGLE180_THRES;
				blocker.startLineSec.setAngle(ANGLE90_THRES);
				blocker.left_circle_origin_circleSec.setAngle(ANGLE76_RESETTER);
				blocker.right_circle_origin_circleSec.setAngle(ANGLE102_RESETTER);
			} else {
				blocker.rotate(blocker.rotation_speedSec + ANGLE_OVERCATCHER);
				blocker.right.rotate(blocker.rotation_speedSec + ANGLE_OVERCATCHER);
				blocker.startLineSec.rotate(blocker.rotation_speedSec);
				blocker.left_circle_origin_circleSec.rotate(blocker.rotation_speedSec);
				blocker.right_circle_origin_circleSec.rotate(blocker.rotation_speedSec);
			}
		} else if(blocker.sprite_angleSec < ANGLE225_THRES
				&& blocker.sprite_angleSec >= ANGLE180_THRES){
			blocker.rotation_speedSec += RaccelQ1;
			blocker.sprite_angleSec += blocker.rotation_speedSec;
			blocker.rotate(blocker.rotation_speedSec);
			blocker.right.rotate(blocker.rotation_speedSec);
			blocker.startLineSec.rotate(blocker.rotation_speedSec);
			blocker.left_circle_origin_circleSec.rotate(blocker.rotation_speedSec);
			blocker.right_circle_origin_circleSec.rotate(blocker.rotation_speedSec);
		} else if (blocker.sprite_angleSec < ANGLE270_THRES
				&& blocker.sprite_angleSec >= ANGLE225_THRES){
			blocker.rotation_speedSec += RaccelQ2;
			blocker.sprite_angleSec += blocker.rotation_speedSec;
			blocker.rotate(blocker.rotation_speedSec);
			blocker.right.rotate(blocker.rotation_speedSec);
			blocker.startLineSec.rotate(blocker.rotation_speedSec);
			blocker.left_circle_origin_circleSec.rotate(blocker.rotation_speedSec);
			blocker.right_circle_origin_circleSec.rotate(blocker.rotation_speedSec);
		} else if (blocker.sprite_angleSec < ANGLE315_THRES
				&& blocker.sprite_angleSec >= ANGLE270_THRES){
			blocker.rotation_speedSec += RaccelQ3;
			blocker.sprite_angleSec += blocker.rotation_speedSec;
			blocker.rotate(blocker.rotation_speedSec);
			blocker.right.rotate(blocker.rotation_speedSec);
			blocker.startLineSec.rotate(blocker.rotation_speedSec);
			blocker.left_circle_origin_circleSec.rotate(blocker.rotation_speedSec);
			blocker.right_circle_origin_circleSec.rotate(blocker.rotation_speedSec);
		} else if (blocker.sprite_angleSec < ANGLE360_THRES
				&& blocker.sprite_angleSec >= ANGLE315_THRES){
			blocker.rotation_speedSec += RaccelQ4;
			blocker.sprite_angleSec += blocker.rotation_speedSec;
			if( (blocker.rotation_speedSec) < SPEED_COMPARER ){
				blocker.rotate(ANGLE360_THRES - blocker.sprite_angleSec);
				blocker.right.rotate(ANGLE360_THRES - blocker.sprite_angleSec);
				blocker.sprite_angleSec = ANGLE360_THRES;
				blocker.startLineSec.setAngle(ANGLE270_THRES);
				blocker.left_circle_origin_circleSec.setAngle(ANGLE256_SETTER);
				blocker.right_circle_origin_circleSec.setAngle(ANGLE282_SETTER);
			} else {
				blocker.rotate(blocker.rotation_speedSec + ANGLE_OVERCATCHER);
				blocker.right.rotate(blocker.rotation_speedSec + ANGLE_OVERCATCHER);
				blocker.startLineSec.rotate(blocker.rotation_speedSec);
				blocker.left_circle_origin_circleSec.rotate(blocker.rotation_speedSec);
				blocker.right_circle_origin_circleSec.rotate(blocker.rotation_speedSec);
			}
		}
		left_orig_v.x = blocker.left_circle_origin_circle.x;
		left_orig_v.y = blocker.left_circle_origin_circleSec.y +
				blocker.left_circle_origin_circle.y;
		left_blocker_orig.x = blocker.endLineSec.x;
		left_blocker_orig.y = blocker.endLineSec.y;
		right_orig_v.x = blocker.right_circle_origin_circle.x;
		right_orig_v.y = blocker.right_circle_origin_circleSec.y +
				blocker.right_circle_origin_circle.y;
		right_blocker_orig.x = blocker.endLineSec.x;
		right_blocker_orig.y = blocker.endLineSec.y;
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	public void RotateBlockerRRSecTranslation(ex01BlockerYellow blocker, float delta){
		RaccelQ1 = blocker.rotationAccelQ1Sec * delta;
		RaccelQ2 = blocker.rotationAccelQ2Sec * delta;
		RaccelQ3 = blocker.rotationAccelQ3Sec * delta;
		RaccelQ4 = blocker.rotationAccelQ4Sec * delta;
		if(blocker.sprite_angleSec >= ANGLE360_THRES){
			blocker.sprite_angleSec = ANGLE0_RESETER;
			blocker.rotation_speedSec = blocker.speedP1Sec;
			blocker.startLineSec.setAngle(ANGLE270_RESETER);
		}
		if(blocker.sprite_angleSec < ANGLE45_THRES
	    && blocker.sprite_angleSec >= ANGLE0_THRES){
			blocker.rotation_speedSec += RaccelQ1;
			blocker.sprite_angleSec += blocker.rotation_speedSec;
			blocker.startLineSec.rotate(blocker.rotation_speedSec);
		} else if (blocker.sprite_angleSec < ANGLE90_THRES
				&& blocker.sprite_angleSec >= ANGLE45_THRES){
			blocker.rotation_speedSec += RaccelQ2;
			blocker.sprite_angleSec += blocker.rotation_speedSec;
			blocker.startLineSec.rotate(blocker.rotation_speedSec);
		} else if (blocker.sprite_angleSec < ANGLE135_THRES
				&& blocker.sprite_angleSec >= ANGLE90_THRES){
			blocker.rotation_speedSec += RaccelQ3;
			blocker.sprite_angleSec += blocker.rotation_speedSec;
			blocker.startLineSec.rotate(blocker.rotation_speedSec);
		} else if (blocker.sprite_angleSec < ANGLE180_THRES
				&& blocker.sprite_angleSec >= ANGLE135_THRES){
			blocker.rotation_speedSec += RaccelQ4;
			blocker.sprite_angleSec += blocker.rotation_speedSec;
			if( (blocker.rotation_speedSec) < SPEED_COMPARER ){
				blocker.sprite_angleSec = ANGLE180_THRES;
				blocker.startLineSec.setAngle(ANGLE90_THRES);
			} else {
				blocker.startLineSec.rotate(blocker.rotation_speedSec);
			}
		} else if(blocker.sprite_angleSec < ANGLE225_THRES
			   && blocker.sprite_angleSec >= ANGLE180_THRES){
			blocker.rotation_speedSec += RaccelQ1;
			blocker.sprite_angleSec += blocker.rotation_speedSec;
			blocker.startLineSec.rotate(blocker.rotation_speedSec);
		} else if (blocker.sprite_angleSec < ANGLE270_THRES
				&& blocker.sprite_angleSec >= ANGLE225_THRES){
			blocker.rotation_speedSec += RaccelQ2;
			blocker.sprite_angleSec += blocker.rotation_speedSec;
			blocker.startLineSec.rotate(blocker.rotation_speedSec);
		} else if (blocker.sprite_angleSec < ANGLE315_THRES
				&& blocker.sprite_angleSec >= ANGLE270_THRES){
			blocker.rotation_speedSec += RaccelQ3;
			blocker.sprite_angleSec += blocker.rotation_speedSec;
			blocker.startLineSec.rotate(blocker.rotation_speedSec);
		} else if (blocker.sprite_angleSec < ANGLE360_THRES
				&& blocker.sprite_angleSec >= ANGLE315_THRES){
			blocker.rotation_speedSec += RaccelQ4;
			blocker.sprite_angleSec += blocker.rotation_speedSec;
			if( (blocker.rotation_speedSec) < SPEED_COMPARER ){
				blocker.sprite_angleSec = ANGLE360_THRES;
				blocker.startLineSec.setAngle(ANGLE270_THRES);
			} else {
				blocker.startLineSec.rotate(blocker.rotation_speedSec);
			}
		}
		blocker.setPosition(blocker.world_XO + blocker.startLineSec.x,
							blocker.world_YO + blocker.startLineSec.y);
		blocker.right.setPosition(blocker.world_XOR + blocker.startLineSec.x,
								  blocker.world_YOR + blocker.startLineSec.y);
		left_orig_v.x = blocker.left_circle_origin_circle.x + blocker.startLineSec.x;
		left_orig_v.y = blocker.left_circle_origin_circle.y +
						blocker.startLineSec.y +
						blocker.height * ROBIN_ROTATOR_Y_CORRECTION * blocker.robin_radiusSec;
		left_blocker_orig.x = blocker.endLine.x;
		left_blocker_orig.y = blocker.endLine.y;
		right_orig_v.x = blocker.right_circle_origin_circle.x +
						 blocker.startLineSec.x;
		right_orig_v.y = blocker.right_circle_origin_circle.y +
						 blocker.startLineSec.y +
						 blocker.height * ROBIN_ROTATOR_Y_CORRECTION * blocker.robin_radiusSec;
		right_blocker_orig.x = blocker.endLine.x;
		right_blocker_orig.y = blocker.endLine.y;
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	// rotate the Round Robin blocker
	public void RotateBlockerRRNegSec(ex01BlockerYellow blocker, float delta){
		RaccelQ1 = blocker.rotationAccelQ1Sec * delta;
		RaccelQ2 = blocker.rotationAccelQ2Sec * delta;
		RaccelQ3 = blocker.rotationAccelQ3Sec * delta;
		RaccelQ4 = blocker.rotationAccelQ4Sec * delta;
		rot = blocker.rotation_senseSec;
		if(blocker.sprite_angleSec <= ANGLE360_THRESNeg){
			blocker.sprite_angleSec = ANGLE0_RESETERNeg;
			blocker.rotation_speedSec = blocker.speedP1Sec;
			blocker.startLineSec.setAngle(ANGLE270_RESETER);
			blocker.left_circle_origin_circleSec.setAngle(ANGLE256_SETTER);
			blocker.right_circle_origin_circleSec.setAngle(ANGLE282_SETTER);
		}
		if(blocker.sprite_angleSec > ANGLE45_THRESNeg
	    && blocker.sprite_angleSec <= ANGLE0_THRESNeg){
			blocker.rotation_speedSec += RaccelQ1;
			rot = blocker.rotation_speedSec * rot;
			blocker.sprite_angleSec += rot;
			blocker.startLineSec.rotate(rot);
			blocker.left_circle_origin_circleSec.rotate(rot);
			blocker.right_circle_origin_circleSec.rotate(rot);
		} else if (blocker.sprite_angleSec > ANGLE90_THRESNeg
				&& blocker.sprite_angleSec <= ANGLE45_THRESNeg){
			blocker.rotation_speedSec += RaccelQ2;
			rot = blocker.rotation_speedSec * rot;
			blocker.sprite_angleSec += rot;
			blocker.startLineSec.rotate(rot);
			blocker.left_circle_origin_circleSec.rotate(rot);
			blocker.right_circle_origin_circleSec.rotate(rot);
		} else if (blocker.sprite_angleSec > ANGLE135_THRESNeg
				&& blocker.sprite_angleSec <= ANGLE90_THRESNeg){
			blocker.rotation_speedSec += RaccelQ3;
			rot = blocker.rotation_speedSec * rot;
			blocker.sprite_angleSec += rot;
			blocker.startLineSec.rotate(rot);
			blocker.left_circle_origin_circleSec.rotate(rot);
			blocker.right_circle_origin_circleSec.rotate(rot);
		} else if (blocker.sprite_angleSec > ANGLE180_THRESNeg
				&& blocker.sprite_angleSec <= ANGLE135_THRESNeg){
			blocker.rotation_speedSec += RaccelQ4;
			rot = blocker.rotation_speedSec * rot;
			blocker.sprite_angleSec += rot;
			if( (blocker.rotation_speedSec) < SPEED_COMPARER ){
				blocker.sprite_angleSec = ANGLE180_THRESNeg;
				blocker.startLineSec.setAngle(ANGLE90_THRESNeg);
				blocker.left_circle_origin_circleSec.setAngle(ANGLE76_RESETTERNeg);
				blocker.right_circle_origin_circleSec.setAngle(ANGLE102_RESETTERNeg);
			} else {
				blocker.startLineSec.rotate(rot);
				blocker.left_circle_origin_circleSec.rotate(rot);
				blocker.right_circle_origin_circleSec.rotate(rot);
			}
		} else if(blocker.sprite_angleSec > ANGLE225_THRESNeg
			   && blocker.sprite_angleSec <= ANGLE180_THRESNeg){
			blocker.rotation_speedSec += RaccelQ1;
			rot = blocker.rotation_speedSec * rot;
			blocker.sprite_angleSec += rot;
			blocker.startLineSec.rotate(rot);
			blocker.left_circle_origin_circleSec.rotate(rot);
			blocker.right_circle_origin_circleSec.rotate(rot);
		} else if (blocker.sprite_angleSec > ANGLE270_THRESNeg
				&& blocker.sprite_angleSec <= ANGLE225_THRESNeg){
			blocker.rotation_speedSec += RaccelQ2;
			rot = blocker.rotation_speedSec * rot;
			blocker.sprite_angleSec += rot;
			blocker.startLineSec.rotate(rot);
			blocker.left_circle_origin_circleSec.rotate(rot);
			blocker.right_circle_origin_circleSec.rotate(rot);
		} else if (blocker.sprite_angleSec > ANGLE315_THRESNeg
				&& blocker.sprite_angleSec <= ANGLE270_THRESNeg){
			blocker.rotation_speedSec += RaccelQ3;
			rot = blocker.rotation_speedSec * rot;
			blocker.sprite_angleSec += rot;
			blocker.startLineSec.rotate(rot);
			blocker.left_circle_origin_circleSec.rotate(rot);
			blocker.right_circle_origin_circleSec.rotate(rot);
		} else if (blocker.sprite_angleSec > ANGLE360_THRESNeg
				&& blocker.sprite_angleSec <= ANGLE315_THRESNeg){
			blocker.rotation_speedSec += RaccelQ4;
			rot = blocker.rotation_speedSec * rot;
			blocker.sprite_angleSec += rot;
			if( (blocker.rotation_speedSec) < SPEED_COMPARER ){
				blocker.sprite_angleSec = ANGLE360_THRESNeg;
				blocker.startLineSec.setAngle(ANGLE270_THRESNeg);
				blocker.left_circle_origin_circleSec.setAngle(ANGLE256_SETTERNeg);
				blocker.right_circle_origin_circleSec.setAngle(ANGLE282_SETTERNeg);
			} else {
				blocker.startLineSec.rotate(rot);
				blocker.left_circle_origin_circleSec.rotate(rot);
				blocker.right_circle_origin_circleSec.rotate(rot);
			}
		}
		left_orig_v.x = blocker.left_circle_origin_circleSec.x;
		left_orig_v.y = blocker.left_circle_origin_circleSec.y;
		left_blocker_orig.x = blocker.endLineSec.x;
		left_blocker_orig.y = blocker.endLineSec.y;
		right_orig_v.x = blocker.right_circle_origin_circleSec.x;
		right_orig_v.y = blocker.right_circle_origin_circleSec.y;
		right_blocker_orig.x = blocker.endLineSec.x;
		right_blocker_orig.y = blocker.endLineSec.y;
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	// rotate the Round Robin blocker
	public void RotateBlockerRRNegSecTranslation(ex01BlockerYellow blocker, float delta){
		RaccelQ1 = blocker.rotationAccelQ1Sec * delta;
		RaccelQ2 = blocker.rotationAccelQ2Sec * delta;
		RaccelQ3 = blocker.rotationAccelQ3Sec * delta;
		RaccelQ4 = blocker.rotationAccelQ4Sec * delta;
		rot = blocker.rotation_senseSec;
		if(blocker.sprite_angleSec <= ANGLE360_THRESNeg){
			blocker.sprite_angleSec = ANGLE0_RESETERNeg;
			blocker.rotation_speedSec = blocker.speedP1Sec;
			blocker.startLineSec.setAngle(ANGLE270_RESETER);
		}
		if(blocker.sprite_angleSec > ANGLE45_THRESNeg
		&& blocker.sprite_angleSec <= ANGLE0_THRESNeg){
			blocker.rotation_speedSec += RaccelQ1;
			rot = blocker.rotation_speedSec * rot;
			blocker.sprite_angleSec += rot;
			blocker.startLineSec.rotate(rot);
		} else if (blocker.sprite_angleSec > ANGLE90_THRESNeg
				&& blocker.sprite_angleSec <= ANGLE45_THRESNeg){
			blocker.rotation_speedSec += RaccelQ2;
			rot = blocker.rotation_speedSec * rot;
			blocker.sprite_angleSec += rot;
			blocker.startLineSec.rotate(rot);
		} else if (blocker.sprite_angleSec > ANGLE135_THRESNeg
				&& blocker.sprite_angleSec <= ANGLE90_THRESNeg){
			blocker.rotation_speedSec += RaccelQ3;
			rot = blocker.rotation_speedSec * rot;
			blocker.sprite_angleSec += rot;
			blocker.startLineSec.rotate(rot);
		} else if (blocker.sprite_angleSec > ANGLE180_THRESNeg
				&& blocker.sprite_angleSec <= ANGLE135_THRESNeg){
			blocker.rotation_speedSec += RaccelQ4;
			rot = blocker.rotation_speedSec * rot;
			blocker.sprite_angleSec += rot;
			if( (blocker.rotation_speedSec) < SPEED_COMPARER ){
				blocker.sprite_angleSec = ANGLE180_THRESNeg;
				blocker.startLineSec.setAngle(ANGLE90_THRESNeg);
			} else {
				blocker.startLineSec.rotate(rot);
			}
		} else if(blocker.sprite_angleSec > ANGLE225_THRESNeg
				&& blocker.sprite_angleSec <= ANGLE180_THRESNeg){
			blocker.rotation_speedSec += RaccelQ1;
			rot = blocker.rotation_speedSec * rot;
			blocker.sprite_angleSec += rot;
			blocker.startLineSec.rotate(rot);
		} else if (blocker.sprite_angleSec > ANGLE270_THRESNeg
				&& blocker.sprite_angleSec <= ANGLE225_THRESNeg){
			blocker.rotation_speedSec += RaccelQ2;
			rot = blocker.rotation_speedSec * rot;
			blocker.sprite_angleSec += rot;
			blocker.startLineSec.rotate(rot);
		} else if (blocker.sprite_angleSec > ANGLE315_THRESNeg
				&& blocker.sprite_angleSec <= ANGLE270_THRESNeg){
			blocker.rotation_speedSec += RaccelQ3;
			rot = blocker.rotation_speedSec * rot;
			blocker.sprite_angleSec += rot;
			blocker.startLineSec.rotate(rot);
		} else if (blocker.sprite_angleSec > ANGLE360_THRESNeg
				&& blocker.sprite_angleSec <= ANGLE315_THRESNeg){
			blocker.rotation_speedSec += RaccelQ4;
			rot = blocker.rotation_speedSec * rot;
			blocker.sprite_angleSec += rot;
			if( (blocker.rotation_speedSec) < SPEED_COMPARER ){
				blocker.sprite_angleSec = ANGLE360_THRESNeg;
				blocker.startLineSec.setAngle(ANGLE270_THRESNeg);
			} else {
				blocker.startLineSec.rotate(rot);
			}
		}
		blocker.setPosition(blocker.world_XO + blocker.startLineSec.x,
							blocker.world_YO + blocker.startLineSec.y);
		blocker.right.setPosition(blocker.world_XOR + blocker.startLineSec.x,
								  blocker.world_YOR + blocker.startLineSec.y);
		left_orig_v.x = blocker.left_circle_origin_circle.x + blocker.startLineSec.x;
		left_orig_v.y = blocker.left_circle_origin_circle.y + blocker.startLineSec.y +
						blocker.height * ROBIN_ROTATOR_Y_CORRECTION * blocker.robin_radiusSec;
		left_blocker_orig.x = blocker.endLine.x;
		left_blocker_orig.y = blocker.endLine.y;
		right_orig_v.x = blocker.right_circle_origin_circle.x + blocker.startLineSec.x;
		right_orig_v.y = blocker.right_circle_origin_circle.y + blocker.startLineSec.y +
						 blocker.height * ROBIN_ROTATOR_Y_CORRECTION * blocker.robin_radiusSec;
		right_blocker_orig.x = blocker.endLine.x;
		right_blocker_orig.y = blocker.endLine.y;
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	// translate the still blocker
	public void TranslateBlocker(ex01BlockerYellow blocker, float delta){
		booler = false;
		TaccelI1 = blocker.translationAccelI1 * delta;
		TaccelI2 = blocker.translationAccelI2 * delta;
		TaccelIB1 = blocker.translationAccelIB1 * delta;
		TaccelIB2 = blocker.translationAccelIB2 * delta;
		depl_count = Math.abs(blocker.deployCounter);
		depl_dist = blocker.deployDistance;
		depl_dist2 = blocker.deployDistance/2f;
		if(depl_count<=depl_dist2){
			if(blocker.currentDir == +1) {blocker.currentSpeed += TaccelI1;}
			else {blocker.currentSpeed -= TaccelIB1;}
		} else if (depl_count>depl_dist2 && depl_count<=depl_dist){
			if(blocker.currentDir == +1) {blocker.currentSpeed += TaccelI2;}
			else {blocker.currentSpeed -= TaccelIB2;}
		} else if (depl_count>depl_dist){
			blocker.currentDir *= -1;
			if(blocker.currentDir == 1) {
				ResetBlockerPositionWithDistanceLast(blocker);
				blocker.currentSpeed = blocker.leftSpeed;
			} else {
				ResetBlockerPositionWithDistanceFirst(blocker);
				blocker.currentSpeed = -blocker.rightSpeed;
			}
			blocker.deployCounter = 0f;
			booler = true;
		}
		if(!booler){
			if(blocker.currentDir == 1){
				AdvanceBlockerWithDistanceForward(blocker, blocker.currentSpeed);
			} else {
				AdvanceBlockerWithDistanceBackward(blocker, blocker.currentSpeed);
			}
		}
	}

	// translate the still blocker
	public void TranslateBlockerSec(ex01BlockerYellow blocker, float delta){
		booler = false;
		TaccelI1 = blocker.translationAccelI1 * delta;
		TaccelI2 = blocker.translationAccelI2 * delta;
		TaccelIB1 = blocker.translationAccelIB1 * delta;
		TaccelIB2 = blocker.translationAccelIB2 * delta;
		depl_count = Math.abs(blocker.deployCounterSec);
		depl_dist = blocker.deployDistanceSec;
		depl_dist2 = blocker.deployDistanceSec/2f;
		if(depl_count<=depl_dist2){
			if(blocker.currentDirSec == +1) {blocker.currentSpeedSec += TaccelI1;}
			else {blocker.currentSpeedSec -= TaccelIB1;}
		} else if (depl_count>depl_dist2 && depl_count<=depl_dist){
			if(blocker.currentDirSec == +1) {blocker.currentSpeedSec += TaccelI2;}
			else {blocker.currentSpeedSec -= TaccelIB2;}
		} else if (depl_count>depl_dist){
			blocker.currentDirSec *= -1;
			if(blocker.currentDirSec == 1) {
				ResetBlockerPositionWithDistanceLastSec(blocker);
				blocker.currentSpeedSec = blocker.leftSpeedSec;
			} else {
				ResetBlockerPositionWithDistanceFirstSec(blocker);
				blocker.currentSpeedSec = -blocker.rightSpeedSec;
			}
			blocker.deployCounterSec = 0f;
			booler = true;
		}
		if(!booler){
			if(blocker.currentDirSec == 1){
				AdvanceBlockerWithDistanceForwardSec(blocker, blocker.currentSpeedSec);
			} else {
				AdvanceBlockerWithDistanceBackwardSec(blocker, blocker.currentSpeedSec);
			}
		}
	}

	// translate the still blocker
	public void TranslateBlockerSecWPivot(ex01BlockerYellow blocker, float delta){
		booler = false;
		TaccelI1 = blocker.translationAccelI1 * delta;
		TaccelI2 = blocker.translationAccelI2 * delta;
		TaccelIB1 = blocker.translationAccelIB1 * delta;
		TaccelIB2 = blocker.translationAccelIB2 * delta;
		depl_count = Math.abs(blocker.deployCounterSec);
		depl_dist = blocker.deployDistanceSec;
		depl_dist2 = blocker.deployDistanceSec/2f;
		if(depl_count<=depl_dist2){
			if(blocker.currentDirSec == +1) {
				blocker.currentSpeedSec += TaccelI1;
				blocker.startLine_endLine_X_COUNTER += TaccelI1;
			}
			else {
				blocker.currentSpeedSec -= TaccelIB1;
				blocker.startLine_endLine_X_COUNTER += TaccelIB1;
			}
		} else if (depl_count>depl_dist2
				&& depl_count<=depl_dist){
			if(blocker.currentDirSec == +1) {
				blocker.currentSpeedSec += TaccelI2;
				blocker.startLine_endLine_X_COUNTER -= TaccelI2;
			}
			else {
				blocker.currentSpeedSec -= TaccelIB2;
				blocker.startLine_endLine_X_COUNTER -= TaccelIB2;
			}
		} else if (depl_count>depl_dist){
			blocker.currentDirSec *= -1;
			if(blocker.currentDirSec == 1) {
				blocker.startLine_endLine_X_COUNTER -= blocker.startLine_endLine_X_COUNTER;
				blocker.currentSpeedSec = blocker.leftSpeedSec;
			} else {
				blocker.currentSpeedSec = -blocker.rightSpeedSec;
			}
			blocker.startLine_endLine_X_COUNTER = 0f;
			booler = true;
			blocker.deployCounterSec = 0f;
		}
		if(!booler){
			if(blocker.currentDirSec == 1){
				AdvanceBlockerWithDistanceForwardSecWPivot(blocker, blocker.currentSpeedSec);
			} else {
				AdvanceBlockerWithDistanceBackwardSecWPivot(blocker, blocker.currentSpeedSec);
			}
		}
	}

	public void ResetBlockerPositionWithDistanceFirst(ex01BlockerYellow blocker){
		blocker.setPosition(
				blocker.world_X + blocker.deployDistance,
				blocker.getY());
		blocker.right.setPosition(
				blocker.world_XR + blocker.deployDistance,
				blocker.right.getY());
		blocker.startLine.x =
				blocker.world_X +
				blocker.deployDistance +
				blocker.rotationPivotX;
		blocker.endLine.x =
				blocker.world_X +
				blocker.deployDistance +
				blocker.rotationPivotX;
		Vector2 left_orig_v = new Vector2(blocker.left_circle_origin_circle);
		Vector2 left_blocker_orig = new Vector2(
				blocker.endLine.x,
				blocker.endLine.y);
		Vector2 right_orig_v = new Vector2(blocker.right_circle_origin_circle);
		Vector2 right_blocker_orig = new Vector2(
				blocker.endLine.x,
				blocker.endLine.y);
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	public void ResetBlockerPositionWithDistanceFirstSec(ex01BlockerYellow blocker){
		blocker.setPosition(
				blocker.world_X + blocker.deployDistanceSec,
				blocker.getY());
		blocker.right.setPosition(
				blocker.world_XR + blocker.deployDistanceSec,
				blocker.right.getY());
		blocker.startLine.x =
				blocker.world_X +
				blocker.deployDistanceSec +
				blocker.rotationPivotX;
		blocker.endLine.x =
				blocker.world_X +
				blocker.deployDistanceSec +
				blocker.rotationPivotX;
		Vector2 left_orig_v = new Vector2(blocker.left_circle_origin_circle);
		Vector2 left_blocker_orig = new Vector2(
				blocker.endLine.x,
				blocker.endLine.y);
		Vector2 right_orig_v = new Vector2(blocker.right_circle_origin_circle);
		Vector2 right_blocker_orig = new Vector2(
				blocker.endLine.x,
				blocker.endLine.y);
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	public void ResetBlockerPositionWithDistanceFirstSecWPivot(ex01BlockerYellow blocker){
		blocker.setPosition(
				blocker.getX() - blocker.startLine_endLine_X_COUNTER,
				blocker.getY());
		blocker.right.setPosition(
				blocker.right.getX() - blocker.startLine_endLine_X_COUNTER,
				blocker.getY());
		blocker.endLine.x -= blocker.startLine_endLine_X_COUNTER;
		Vector2 left_orig_v = new Vector2(blocker.left_circle_origin_circle);
		Vector2 left_blocker_orig = new Vector2(
				blocker.endLine.x,
				blocker.endLine.y);
		Vector2 right_orig_v = new Vector2(blocker.right_circle_origin_circle);
		Vector2 right_blocker_orig = new Vector2(
				blocker.endLine.x,
				blocker.endLine.y);
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	public void ResetBlockerPositionWithDistanceFirstSecWPivotOriginal(ex01BlockerYellow blocker){
		blocker.setPosition(
				blocker.getX() + blocker.deployCounterSec - blocker.startLine_endLine_X_COUNTER,
				blocker.getY());
		blocker.right.setPosition(
				blocker.right.getX() +
						blocker.deployCounterSec -
						blocker.startLine_endLine_X_COUNTER,
				blocker.getY());
		blocker.endLine.x -= blocker.startLine_endLine_X_COUNTER + blocker.deployCounterSec;
		Vector2 left_orig_v = new Vector2(blocker.left_circle_origin_circle);
		Vector2 left_blocker_orig = new Vector2(
				blocker.endLine.x,
				blocker.endLine.y);
		Vector2 right_orig_v = new Vector2(blocker.right_circle_origin_circle);
		Vector2 right_blocker_orig = new Vector2(
				blocker.endLine.x,
				blocker.endLine.y);
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	public void ResetBlockerPositionWithDistanceLast(ex01BlockerYellow blocker){
		blocker.setPosition(blocker.world_X, blocker.getY());
		blocker.right.setPosition(blocker.world_XR, blocker.right.getY());
		blocker.startLine.x = blocker.world_X + blocker.rotationPivotX;
		blocker.endLine.x = blocker.world_X + blocker.rotationPivotX;
		left_orig_v.x = blocker.left_circle_origin_circle.x;
		left_orig_v.y = blocker.left_circle_origin_circle.y;
		left_blocker_orig.x = blocker.endLine.x;
		left_blocker_orig.y = blocker.endLine.y;
		right_orig_v.x = blocker.right_circle_origin_circle.x;
		right_orig_v.y = blocker.right_circle_origin_circle.y;
		right_blocker_orig.x = blocker.endLine.x;
		right_blocker_orig.y = blocker.endLine.y;
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	public void ResetBlockerPositionWithDistanceLastSec(ex01BlockerYellow blocker){
		blocker.setPosition(blocker.world_X, blocker.getY());
		blocker.right.setPosition(blocker.world_XR, blocker.right.getY());
		blocker.startLine.x = blocker.world_X + blocker.rotationPivotX;
		blocker.endLine.x = blocker.world_X + blocker.rotationPivotX;
		left_orig_v.x = blocker.left_circle_origin_circle.x;
		left_orig_v.y = blocker.left_circle_origin_circle.y;
		left_blocker_orig.x = blocker.endLine.x;
		left_blocker_orig.y = blocker.endLine.y;
		right_orig_v.x = blocker.right_circle_origin_circle.x;
		right_orig_v.y = blocker.right_circle_origin_circle.y;
		right_blocker_orig.x = blocker.endLine.x;
		right_blocker_orig.y = blocker.endLine.y;
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	public void ResetBlockerPositionWithDistanceLastSecWPivot(ex01BlockerYellow blocker){
		blocker.setPosition(
				blocker.getX() - blocker.startLine_endLine_X_COUNTER,
				blocker.getY());
		blocker.right.setPosition(
				blocker.right.getX() - blocker.startLine_endLine_X_COUNTER,
				blocker.getY());
		blocker.endLine.x -= blocker.startLine_endLine_X_COUNTER;
		left_orig_v.x = blocker.left_circle_origin_circle.x;
		left_orig_v.y = blocker.left_circle_origin_circle.y;
		left_blocker_orig.x = blocker.endLine.x;
		left_blocker_orig.y = blocker.endLine.y;
		right_orig_v.x = blocker.right_circle_origin_circle.x;
		right_orig_v.y = blocker.right_circle_origin_circle.y;
		right_blocker_orig.x = blocker.endLine.x;
		right_blocker_orig.y = blocker.endLine.y;
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	//accel = blocker.currentSpeed
	// #  blocker.currentSpeed += TaccelI1;
	// #  blocker.currentSpeed -= TaccelIB1;
	public void AdvanceBlockerWithDistanceForward(ex01BlockerYellow blocker, float accel){
		blocker.deployCounter += accel;
		blocker.setPosition(blocker.deployCounter + blocker.world_X, blocker.getY());
		blocker.right.setPosition(blocker.deployCounter + blocker.world_XR, blocker.right.getY());
		blocker.startLine.x += accel;
		blocker.endLine.x += accel;
		left_orig_v.x = blocker.left_circle_origin_circle.x;
		left_orig_v.y = blocker.left_circle_origin_circle.y;
		left_blocker_orig.x = blocker.endLine.x;
		left_blocker_orig.y = blocker.endLine.y;
		right_orig_v.x = blocker.right_circle_origin_circle.x;
		right_orig_v.y = blocker.right_circle_origin_circle.y;
		right_blocker_orig.x = blocker.endLine.x;
		right_blocker_orig.y = blocker.endLine.y;
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	//accel = blocker.currentSpeed
	// #  blocker.currentSpeed += TaccelI1;
	// #  blocker.currentSpeed -= TaccelIB1;
	public void AdvanceBlockerWithDistanceForwardSec(ex01BlockerYellow blocker, float accel){
		blocker.deployCounterSec += accel;
		blocker.setPosition(blocker.deployCounterSec + blocker.world_X, blocker.getY());
		blocker.right.setPosition(blocker.deployCounterSec + blocker.world_XR, blocker.right.getY());
		blocker.startLine.x += accel;
		blocker.endLine.x += accel;
		left_orig_v.x = blocker.left_circle_origin_circle.x;
		left_orig_v.y = blocker.left_circle_origin_circle.y;
		left_blocker_orig.x = blocker.endLine.x;
		left_blocker_orig.y = blocker.endLine.y;
		right_orig_v.x = blocker.right_circle_origin_circle.x;
		right_orig_v.y = blocker.right_circle_origin_circle.y;
		right_blocker_orig.x = blocker.endLine.x;
		right_blocker_orig.y = blocker.endLine.y;
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	//accel = blocker.currentSpeed
	// #  blocker.currentSpeed += TaccelI1;
	// #  blocker.currentSpeed -= TaccelIB1;
	public void AdvanceBlockerWithDistanceForwardSecWPivot(ex01BlockerYellow blocker, float accel){
		blocker.deployCounterSec += accel;
		blocker.setPosition(
				blocker.deployCounterSec + blocker.world_X,
				blocker.getY());
		blocker.right.setPosition(
				blocker.deployCounterSec + blocker.world_XR,
				blocker.right.getY());
		blocker.endLine.x += accel;
		left_orig_v.x = blocker.left_circle_origin_circle.x;
		left_orig_v.y = blocker.left_circle_origin_circle.y;
		left_blocker_orig.x = blocker.endLine.x;
		left_blocker_orig.y = blocker.endLine.y;
		right_orig_v.x = blocker.right_circle_origin_circle.x;
		right_orig_v.y = blocker.right_circle_origin_circle.y;
		right_blocker_orig.x = blocker.endLine.x;
		right_blocker_orig.y = blocker.endLine.y;
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	public void AdvanceBlockerWithDistanceBackward(ex01BlockerYellow blocker, float accel){
		blocker.deployCounter += accel;
		blocker.setPosition(
				blocker.deployDistance+blocker.deployCounter + blocker.world_X,
				blocker.getY());
		blocker.right.setPosition(
				blocker.deployDistance+blocker.deployCounter + blocker.world_XR,
				blocker.right.getY());
		blocker.startLine.x += accel;
		blocker.endLine.x += accel;
		left_orig_v.x = blocker.left_circle_origin_circle.x;
		left_orig_v.y = blocker.left_circle_origin_circle.y;
		left_blocker_orig.x = blocker.endLine.x;
		left_blocker_orig.y = blocker.endLine.y;
		right_orig_v.x = blocker.right_circle_origin_circle.x;
		right_orig_v.y = blocker.right_circle_origin_circle.y;
		right_blocker_orig.x = blocker.endLine.x;
		right_blocker_orig.y = blocker.endLine.y;
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	public void AdvanceBlockerWithDistanceBackwardSec(ex01BlockerYellow blocker, float accel){
		blocker.deployCounterSec += accel;
		blocker.setPosition(
				blocker.deployDistanceSec+blocker.deployCounterSec + blocker.world_X,
				blocker.getY());
		blocker.right.setPosition(
				blocker.deployDistanceSec+blocker.deployCounterSec + blocker.world_XR,
				blocker.right.getY());
		blocker.startLine.x += accel;
		blocker.endLine.x += accel;
		left_orig_v.x = blocker.left_circle_origin_circle.x;
		left_orig_v.y = blocker.left_circle_origin_circle.y;
		left_blocker_orig.x = blocker.endLine.x;
		left_blocker_orig.y = blocker.endLine.y;
		right_orig_v.x = blocker.right_circle_origin_circle.x;
		right_orig_v.y = blocker.right_circle_origin_circle.y;
		right_blocker_orig.x = blocker.endLine.x;
		right_blocker_orig.y = blocker.endLine.y;
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	public void AdvanceBlockerWithDistanceBackwardSecWPivot(ex01BlockerYellow blocker,
															float accel){
		blocker.deployCounterSec += accel;
		blocker.setPosition(
				blocker.deployDistanceSec+blocker.deployCounterSec + blocker.world_X,
				blocker.getY());
		blocker.right.setPosition(
				blocker.deployDistanceSec+blocker.deployCounterSec + blocker.world_XR,
				blocker.right.getY());
		blocker.endLine.x += accel;
		left_orig_v.x = blocker.left_circle_origin_circle.x;
		left_orig_v.y = blocker.left_circle_origin_circle.y;
		left_blocker_orig.x = blocker.endLine.x;
		left_blocker_orig.y = blocker.endLine.y;
		right_orig_v.x = blocker.right_circle_origin_circle.x;
		right_orig_v.y = blocker.right_circle_origin_circle.y;
		right_blocker_orig.x = blocker.endLine.x;
		right_blocker_orig.y = blocker.endLine.y;
		blocker.left_circle_collision.setPosition(left_orig_v.add(left_blocker_orig));
		blocker.right_circle_collision.setPosition(right_orig_v.add(right_blocker_orig));
	}

	// This is the magic init function called in LoadElectrotrapBlocker(ex01ElectrotrapRed
	// new_etrp...
	// ... it practically inits the rotation pivot points , laser start and end vectors,...
	// ... based on the type of blocker used by the electrotrap
	public void ComputeBlockerCoords(ex01ElectrotrapRed electrotrap_red, TextureAtlas atlas){
		// time that the quadrant needs to be completed based on start and end speeds
		timeforQuadrant = electrotrap_red.blocker.timeforQuadrant;
		// blocker x is in the middle of the distance between the left and right
		// electrotrap + - relative X
		electrotrap_red.blocker.world_XO =
				electrotrap_red.world_X +
			( - electrotrap_red.world_X + electrotrap_red.world_XR-electrotrap_red.width/1.55f)/2 +
				electrotrap_red.blocker.relativeX;
		// blocker y is in the middle of the height between the electrotrap heights + - relative Y
		electrotrap_red.blocker.world_YO =
				electrotrap_red.world_Y +
			   (electrotrap_red.height-electrotrap_red.blocker.height)/2 +
				electrotrap_red.blocker.relativeY;
		// because blockers are joined the right x for (the second blocker) is easily computed
		electrotrap_red.blocker.world_XOR =
				electrotrap_red.blocker.world_XO +
				electrotrap_red.blocker.width * 2 - 0.5f;
		// right blocker has the same y coord as the left blocker
		electrotrap_red.blocker.world_YOR = electrotrap_red.blocker.world_YO;
		electrotrap_red.blocker.circle_leftX_origin =
				electrotrap_red.blocker.world_X +
				electrotrap_red.blocker.width / 2.1f;
		electrotrap_red.blocker.circle_leftY_origin =
				electrotrap_red.blocker.world_Y +
				electrotrap_red.blocker.height / 2.1f;
		electrotrap_red.blocker.circle_rightX_origin =
				electrotrap_red.blocker.world_XR +
				electrotrap_red.blocker.width / 2.1f;
		electrotrap_red.blocker.circle_rightY_origin =
				electrotrap_red.blocker.world_YR +
				electrotrap_red.blocker.height / 2.1f;
		// blocker x is in the middle of the distance between the left and right
		// electrotrap + - relative X
		electrotrap_red.blocker.world_X =
				electrotrap_red.world_X +
	        ( - electrotrap_red.world_X + electrotrap_red.world_XR-electrotrap_red.width/1.55f)/2 +
				electrotrap_red.blocker.relativeX;
		// blocker y is in the middle of the height between the electrotrap heights + - relative Y
		electrotrap_red.blocker.world_Y =
				electrotrap_red.world_Y +
			   (electrotrap_red.height-electrotrap_red.blocker.height)/2 -
				electrotrap_red.blocker.robin_radius + electrotrap_red.blocker.relativeY;
		// because blockers are joined the right x for (the second blocker) is easily computed
		electrotrap_red.blocker.world_XR =
				electrotrap_red.blocker.world_X +
				electrotrap_red.blocker.width * 2 - 0.5f;
		// right blocker has the same y coord as the left blocker
		electrotrap_red.blocker.world_YR = electrotrap_red.blocker.world_Y;
		electrotrap_red.blocker.setPosition(
				electrotrap_red.blocker.world_X,
				electrotrap_red.blocker.world_Y);
		electrotrap_red.blocker.left_circle_collision = new Circle(
				electrotrap_red.blocker.world_X + electrotrap_red.blocker.width / 2.1f,
				electrotrap_red.blocker.world_Y + electrotrap_red.blocker.height / 2.1f,
				electrotrap_red.blocker.width / 2.1f);
		// set the positions xy right
		electrotrap_red.blocker.right.setPosition(
				electrotrap_red.blocker.world_XR,
				electrotrap_red.blocker.world_YR);
		electrotrap_red.blocker.right_circle_collision = new Circle(
				electrotrap_red.blocker.world_XR - electrotrap_red.blocker.width / 2.1f,
				electrotrap_red.blocker.world_YR + electrotrap_red.blocker.height / 2.1f,
				electrotrap_red.blocker.width / 2.1f);
		// init the start and end vectors of the lasers that will be used to
		// generate the vertices for the mesh
		electrotrap_red.blocker.startLine = new Vector2();
		electrotrap_red.blocker.endLine = new Vector2();
		electrotrap_red.blocker.new_delta_left =
				ex01BlockerYellow.CIRCLE_LEFT_RIGHT_BASE -
				ex01BlockerYellow.CIRCLE_CORRECTION_LEFT / electrotrap_red.blocker.robin_radius;
		electrotrap_red.blocker.new_delta_right =
				ex01BlockerYellow.CIRCLE_LEFT_RIGHT_BASE +
				ex01BlockerYellow.CIRCLE_CORRECTION_RIGHT / electrotrap_red.blocker.robin_radius;

		// what type of blocker do we have
		switch(electrotrap_red.blocker.type){
		//ROBIN STANDARD
			case RoundRobin:
			{
				// rotation pivot is relative to the lower left corner of the blocker + robin
				// radius on the UPSIDE
				// we subtract 0.23105f because the right blocker is overlapped on the first one
				electrotrap_red.blocker.rotationPivotX =
						(electrotrap_red.blocker.width) - 0.23105f;
				electrotrap_red.blocker.rotationPivotY =
						(electrotrap_red.blocker.height) / 2 + 0.004f +
						 electrotrap_red.blocker.robin_radius;
				// the startLine vector of the laser is somewhere around the blocker using the
				// world coordinates - below the electrotrap
 				electrotrap_red.blocker.startLine.x =
						(electrotrap_red.blocker.width) - 0.23105f +
						 electrotrap_red.blocker.getX();
				electrotrap_red.blocker.startLine.y =
						(electrotrap_red.blocker.height) / 2 + 0.004f +
						 electrotrap_red.blocker.getY();
				// ORIGIN should be the middle of distance between electrotraps and should
				// be at the middle...
				// ...of the height of electrotrap height + the added relative on Y ; around
				// this origin we...
				// ...rotate the startLine (originator of laser beam)
				Vector2 origin = new Vector2(
						electrotrap_red.blocker.startLine.x,
						electrotrap_red.getY() +
								electrotrap_red.blocker.relativeY +
								electrotrap_red.height /2 );
				// we init the endLine as the origin (the pivot of rotation or origin) ; is the
				// same as origin vector ...
				// ...because>>>>>because we need to update the startLine, when rotation ends,
				// with this origin so we ...
				// ...get the real world coordinates of the rotated originator laser point
				electrotrap_red.blocker.endLine.x =
						electrotrap_red.blocker.startLine.x;
				electrotrap_red.blocker.endLine.y =
						electrotrap_red.getY() +
						electrotrap_red.blocker.relativeY + electrotrap_red.height / 2;
				// we get a vector (direction and length) from origin to startLine (what is to
				// be the future originator...
				// ...after the blockers end their rotation)
				electrotrap_red.blocker.startLine = electrotrap_red.blocker.startLine.sub(origin);
				// set blocker origin - > the point around which the rotation will be done
				electrotrap_red.blocker.setOrigin(
						electrotrap_red.blocker.rotationPivotX,
						electrotrap_red.blocker.rotationPivotY);
				electrotrap_red.blocker.left_circle_origin_circle = new Vector2(
						electrotrap_red.blocker.left_circle_collision.x,
						electrotrap_red.blocker.left_circle_collision.y)
						.sub(new Vector2(
								electrotrap_red.blocker.endLine.x,
								electrotrap_red.blocker.endLine.y));
				electrotrap_red.blocker.right.setOrigin(
						electrotrap_red.blocker.rotationPivotX-2.08f*2 + 0.5f,
						electrotrap_red.blocker.rotationPivotY);
				electrotrap_red.blocker.right_circle_origin_circle = new Vector2(
						electrotrap_red.blocker.right_circle_collision.x,
						electrotrap_red.blocker.right_circle_collision.y)
						.sub(new Vector2(
								electrotrap_red.blocker.endLine.x,
								electrotrap_red.blocker.endLine.y));
				// calculate the quadrant acceleration (or decelerations)
				electrotrap_red.blocker.rotationAccelQ1 =
						(electrotrap_red.blocker.speedP2 - electrotrap_red.blocker.speedP1)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ2 =
						(electrotrap_red.blocker.speedP3 - electrotrap_red.blocker.speedP2)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ3 =
						(electrotrap_red.blocker.speedP4 - electrotrap_red.blocker.speedP3)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ4 =
						(electrotrap_red.blocker.speedP1 - electrotrap_red.blocker.speedP4)
								/ 2f / timeforQuadrant;
			}
			break;
			// for rotative blockers we don't need to rotate the startLine endLine ORIGINATOR
			// because that originator...
			// ... is rotated around itself
			case Rotative:
			{
				// rotation pivot is relative to the lower left corner of the blocker
				// we subtract 0.23105f because the right blocker is overlapped on the first one
				electrotrap_red.blocker.rotationPivotX =
						(electrotrap_red.blocker.width) - 0.23105f;
				electrotrap_red.blocker.rotationPivotY =
						(electrotrap_red.blocker.height) / 2 + 0.004f;
				// we get the start vector of the laser by adding the world coordinates of the blocker
				electrotrap_red.blocker.startLine.x =
						electrotrap_red.blocker.rotationPivotX + electrotrap_red.blocker.getX();
				electrotrap_red.blocker.startLine.y =
						electrotrap_red.blocker.rotationPivotY + electrotrap_red.blocker.getY();
				// at this time we make the end vector the same as the start vector because
				// they're both alomost in the middle
				electrotrap_red.blocker.endLine.x =
						electrotrap_red.blocker.rotationPivotX + electrotrap_red.blocker.getX();
				electrotrap_red.blocker.endLine.y =
						electrotrap_red.blocker.rotationPivotY + electrotrap_red.blocker.getY();
				// set blocker origin - > the point around which the rotation will be done
				electrotrap_red.blocker.setOrigin(
						electrotrap_red.blocker.rotationPivotX,
						electrotrap_red.blocker.rotationPivotY);
				electrotrap_red.blocker.left_circle_origin_circle = new Vector2(
						electrotrap_red.blocker.left_circle_collision.x,
						electrotrap_red.blocker.left_circle_collision.y)
						.sub(new Vector2(
								electrotrap_red.blocker.endLine.x,
								electrotrap_red.blocker.endLine.y));
				electrotrap_red.blocker.right.setOrigin(
						electrotrap_red.blocker.rotationPivotX-2.08f*2 + 0.5f,
						electrotrap_red.blocker.rotationPivotY);
				electrotrap_red.blocker.right_circle_origin_circle = new Vector2(
						electrotrap_red.blocker.right_circle_collision.x,
						electrotrap_red.blocker.right_circle_collision.y)
						.sub(new Vector2(
								electrotrap_red.blocker.endLine.x,
								electrotrap_red.blocker.endLine.y));
				// calculate the quadrant acceleration (or decelerations)
				electrotrap_red.blocker.rotationAccelQ1 =
						(electrotrap_red.blocker.speedP2 - electrotrap_red.blocker.speedP1)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ2 =
						(electrotrap_red.blocker.speedP3 - electrotrap_red.blocker.speedP2)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ3 =
						(electrotrap_red.blocker.speedP4 - electrotrap_red.blocker.speedP3)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ4 =
						(electrotrap_red.blocker.speedP1 - electrotrap_red.blocker.speedP4)
								/ 2f / timeforQuadrant;
			}
			break;
			case Bouncer:
			{
				// rotation pivot is relative to the lower left corner of the blocker
				// we subtract 0.23105f because the right blocker is overlapped on the first one
				electrotrap_red.blocker.rotationPivotX =
						(electrotrap_red.blocker.width) - 0.23105f;
				electrotrap_red.blocker.rotationPivotY =
						(electrotrap_red.blocker.height) / 2 + 0.004f;
				// we get the start vector of the laser by adding the world coordinates
				// of the blocker
				electrotrap_red.blocker.startLine.x =
						electrotrap_red.blocker.rotationPivotX + electrotrap_red.blocker.getX();
				electrotrap_red.blocker.startLine.y =
						electrotrap_red.blocker.rotationPivotY + electrotrap_red.blocker.getY();
				// at this time we make the end vector the same as the start vector because
				// they're both alomost in the middle
				electrotrap_red.blocker.endLine.x =
						electrotrap_red.blocker.rotationPivotX + electrotrap_red.blocker.getX();
				electrotrap_red.blocker.endLine.y =
						electrotrap_red.blocker.rotationPivotY + electrotrap_red.blocker.getY();
				// set blocker origin - > the point around which the rotation will be done
				electrotrap_red.blocker.setOrigin(
						electrotrap_red.blocker.rotationPivotX,
						electrotrap_red.blocker.rotationPivotY);
				electrotrap_red.blocker.left_circle_origin_circle = new Vector2(
						electrotrap_red.blocker.left_circle_collision.x,
						electrotrap_red.blocker.left_circle_collision.y)
						.sub(new Vector2(
								electrotrap_red.blocker.endLine.x,
								electrotrap_red.blocker.endLine.y));
				electrotrap_red.blocker.right.setOrigin(
						electrotrap_red.blocker.rotationPivotX-2.08f*2 + 0.5f,
						electrotrap_red.blocker.rotationPivotY);
				electrotrap_red.blocker.right_circle_origin_circle = new Vector2(
						electrotrap_red.blocker.right_circle_collision.x,
						electrotrap_red.blocker.right_circle_collision.y)
						.sub(new Vector2(
								electrotrap_red.blocker.endLine.x,
								electrotrap_red.blocker.endLine.y));
				// calculate the quadrant acceleration (or decelerations)
				electrotrap_red.blocker.rotationAccelQ1 =
						(electrotrap_red.blocker.speedP2 - electrotrap_red.blocker.speedP1)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ2 =
						(electrotrap_red.blocker.speedP3 - electrotrap_red.blocker.speedP2)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ3 =
						(electrotrap_red.blocker.speedP4 - electrotrap_red.blocker.speedP3)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ4 =
						(electrotrap_red.blocker.speedP1 - electrotrap_red.blocker.speedP4)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.sprite_angle = 90f;
				electrotrap_red.blocker.setRotation(90f);
				electrotrap_red.blocker.right.setRotation(90f);
				electrotrap_red.blocker.left_circle_origin_circle.setAngle(270.28316f);
				electrotrap_red.blocker.right_circle_origin_circle.setAngle(89.70294f);
				Vector2 left_orig_v = new
						Vector2(electrotrap_red.blocker.left_circle_origin_circle);
				Vector2 left_blocker_orig = new Vector2(
						electrotrap_red.blocker.endLine.x,
						electrotrap_red.blocker.endLine.y);
				Vector2 right_orig_v = new
						Vector2(electrotrap_red.blocker.right_circle_origin_circle);
				Vector2 right_blocker_orig = new Vector2(
						electrotrap_red.blocker.endLine.x,
						electrotrap_red.blocker.endLine.y);
				electrotrap_red.blocker.left_circle_collision
						.setPosition(left_orig_v.add(left_blocker_orig));
				electrotrap_red.blocker.right_circle_collision
						.setPosition(right_orig_v.add(right_blocker_orig));
				electrotrap_red.blocker.translationAccelI1 =
						(electrotrap_red.blocker.middleSpeed - electrotrap_red.blocker.leftSpeed)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.translationAccelI2 =
						(electrotrap_red.blocker.rightSpeed - electrotrap_red.blocker.middleSpeed)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.translationAccelIB1 =
						(electrotrap_red.blocker.middleSpeed - electrotrap_red.blocker.rightSpeed)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.translationAccelIB2 =
						(electrotrap_red.blocker.leftSpeed - electrotrap_red.blocker.middleSpeed)
								/ 2f / timeforQuadrant;
			}
			break;
			case RotativeBouncer:
			{
				/* ROTATIVE PART ***********************************************************/
				// rotation pivot is relative to the lower left corner of the blocker
				// we subtract 0.23105f because the right blocker is overlapped on the first one
				electrotrap_red.blocker.rotationPivotX =
						(electrotrap_red.blocker.width) - 0.23105f;
				electrotrap_red.blocker.rotationPivotY =
						(electrotrap_red.blocker.height) / 2 + 0.004f;
				// we get the start vector of the laser by adding the world
				// coordinates of the blocker
				electrotrap_red.blocker.startLine.x =
						electrotrap_red.blocker.rotationPivotX + electrotrap_red.blocker.getX();
				electrotrap_red.blocker.startLine.y =
						electrotrap_red.blocker.rotationPivotY + electrotrap_red.blocker.getY();
				// at this time we make the end vector the same as the start vector
				// because they're both alomost in the middle
				electrotrap_red.blocker.endLine.x =
						electrotrap_red.blocker.rotationPivotX + electrotrap_red.blocker.getX();
				electrotrap_red.blocker.endLine.y =
						electrotrap_red.blocker.rotationPivotY + electrotrap_red.blocker.getY();
				// set blocker origin - > the point around which the rotation will be done
				electrotrap_red.blocker.setOrigin(
						electrotrap_red.blocker.rotationPivotX,
						electrotrap_red.blocker.rotationPivotY);
				electrotrap_red.blocker.left_circle_origin_circle = new Vector2(
						electrotrap_red.blocker.left_circle_collision.x,
						electrotrap_red.blocker.left_circle_collision.y)
						.sub(new Vector2(
								electrotrap_red.blocker.endLine.x,
								electrotrap_red.blocker.endLine.y));
				electrotrap_red.blocker.right.setOrigin(
						electrotrap_red.blocker.rotationPivotX-2.08f*2 + 0.5f,
						electrotrap_red.blocker.rotationPivotY);
				electrotrap_red.blocker.right_circle_origin_circle = new Vector2(
						electrotrap_red.blocker.right_circle_collision.x,
						electrotrap_red.blocker.right_circle_collision.y)
						.sub(new Vector2(
								electrotrap_red.blocker.endLine.x,
								electrotrap_red.blocker.endLine.y));
				// calculate the quadrant acceleration (or decelerations)
				electrotrap_red.blocker.rotationAccelQ1 =
						(electrotrap_red.blocker.speedP2 - electrotrap_red.blocker.speedP1)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ2 =
						(electrotrap_red.blocker.speedP3 - electrotrap_red.blocker.speedP2)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ3 =
						(electrotrap_red.blocker.speedP4 - electrotrap_red.blocker.speedP3)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ4 =
						(electrotrap_red.blocker.speedP1 - electrotrap_red.blocker.speedP4)
								/ 2f / timeforQuadrant;

				/* BOUNCER PART *****************************************************/
				timeforQuadrant = electrotrap_red.blocker.timeforQuadrantSec;
				if(electrotrap_red.blocker.currentDir == 1){
					electrotrap_red.blocker.sprite_angle = 90f;
					electrotrap_red.blocker.setRotation(90f);
					electrotrap_red.blocker.right.setRotation(90f);
					electrotrap_red.blocker.left_circle_origin_circle.setAngle(270.28316f);
					electrotrap_red.blocker.right_circle_origin_circle.setAngle(89.70294f);
				} else if(electrotrap_red.blocker.currentDir == -1){
					electrotrap_red.blocker.sprite_angle = -90f;
					electrotrap_red.blocker.setRotation(-90f);
					electrotrap_red.blocker.right.setRotation(-90f);
					electrotrap_red.blocker.left_circle_origin_circle.setAngle(-270.28316f);
					electrotrap_red.blocker.right_circle_origin_circle.setAngle(-89.70294f);
				}
				Vector2 left_orig_v = new
						Vector2(electrotrap_red.blocker.left_circle_origin_circle);
				Vector2 left_blocker_orig = new Vector2(
						electrotrap_red.blocker.endLine.x,
						electrotrap_red.blocker.endLine.y);
				Vector2 right_orig_v = new
						Vector2(electrotrap_red.blocker.right_circle_origin_circle);
				Vector2 right_blocker_orig = new Vector2(
						electrotrap_red.blocker.endLine.x,
						electrotrap_red.blocker.endLine.y);
				electrotrap_red.blocker.left_circle_collision
						.setPosition(left_orig_v.add(left_blocker_orig));
				electrotrap_red.blocker.right_circle_collision
						.setPosition(right_orig_v.add(right_blocker_orig));
				electrotrap_red.blocker.translationAccelI1 =
						(electrotrap_red.blocker.middleSpeedSec -
								electrotrap_red.blocker.leftSpeedSec) / 2f / timeforQuadrant;
				electrotrap_red.blocker.translationAccelI2 =
						(electrotrap_red.blocker.rightSpeedSec -
								electrotrap_red.blocker.middleSpeedSec) / 2f / timeforQuadrant;
				electrotrap_red.blocker.translationAccelIB1 =
						(electrotrap_red.blocker.middleSpeedSec -
								electrotrap_red.blocker.rightSpeedSec) / 2f / timeforQuadrant;
				electrotrap_red.blocker.translationAccelIB2 =
						(electrotrap_red.blocker.leftSpeedSec -
								electrotrap_red.blocker.middleSpeedSec) / 2f / timeforQuadrant;
			}
			break;
			case RotativeRobin:
			{

				/* ROUND ROBIN PART ***************************************************/
				timeforQuadrant = electrotrap_red.blocker.timeforQuadrantSec;
				// blocker x is in the middle of the distance between the left and
				// right electrotrap + - relative X
				electrotrap_red.blocker.world_X =
						electrotrap_red.world_X +
					( - electrotrap_red.world_X + electrotrap_red.world_XR -
						electrotrap_red.width / 1.55f) / 2 + electrotrap_red.blocker.relativeX;
				// blocker y is in the middle of the height between the
				// electrotrap heights + - relative Y
				electrotrap_red.blocker.world_Y =
						electrotrap_red.world_Y +
					   (electrotrap_red.height - electrotrap_red.blocker.height) / 2 -
						electrotrap_red.blocker.robin_radiusSec + electrotrap_red.blocker.relativeY;
				// because blockers are joined the right x for (the second blocker) is
				// easily computed
				electrotrap_red.blocker.world_XR =
						electrotrap_red.blocker.world_X +
						electrotrap_red.blocker.width * 2 - 0.5f;
				// right blocker has the same y coord as the left blocker
				electrotrap_red.blocker.world_YR = electrotrap_red.blocker.world_Y;
				// set the positions xy left
				electrotrap_red.blocker.setPosition(
						electrotrap_red.blocker.world_X,
						electrotrap_red.blocker.world_Y);
				electrotrap_red.blocker.left_circle_collision = new Circle(
						electrotrap_red.blocker.world_X + electrotrap_red.blocker.width / 2.1f,
						electrotrap_red.blocker.world_Y + electrotrap_red.blocker.height / 2.1f,
						electrotrap_red.blocker.width / 2.1f);
				// set the positions xy right
				electrotrap_red.blocker.right.setPosition(
						electrotrap_red.blocker.world_XR,
						electrotrap_red.blocker.world_YR);
				electrotrap_red.blocker.right_circle_collision = new Circle(
						electrotrap_red.blocker.world_XR - electrotrap_red.blocker.width / 2.1f,
						electrotrap_red.blocker.world_YR + electrotrap_red.blocker.height / 2.1f,
						electrotrap_red.blocker.width / 2.1f);
				// init the start and end vectors of the lasers that will be used to generate the
				// vertices for the mesh
				electrotrap_red.blocker.startLineSec = new Vector2();
				electrotrap_red.blocker.endLineSec = new Vector2();

				// rotation pivot is relative to the lower left corner of the blocker + robin
				// radius on the UPSIDE
				// we subtract 0.23105f because the right blocker is overlapped on the first one
				electrotrap_red.blocker.rotationPivotXSec =
						(electrotrap_red.blocker.width) - 0.23105f;
				electrotrap_red.blocker.rotationPivotYSec =
						(electrotrap_red.blocker.height) / 2 + 0.004f +
						 electrotrap_red.blocker.robin_radiusSec;
				// the startLine vector of the laser is somewhere around the blocker using
				// the world coordinates - below the electrotrap
 				electrotrap_red.blocker.startLineSec.x =
						electrotrap_red.blocker.rotationPivotXSec +
						electrotrap_red.blocker.getX();
				electrotrap_red.blocker.startLineSec.y =
						(electrotrap_red.blocker.height) / 2 + 0.004f +
						 electrotrap_red.blocker.getY();
				// ORIGIN should be the middle of distance between electrotraps and should be
				// at the middle...
				// ...of the height of electrotrap height + the added relative on Y ; around
				// this origin we...
				// ...rotate the startLine (originator of laser beam)
				Vector2 origin = new Vector2(
						electrotrap_red.blocker.startLineSec.x,
						electrotrap_red.getY() +
								electrotrap_red.blocker.relativeY +
								electrotrap_red.height /2 );
				// we init the endLine as the origin (the pivot of rotation or origin) ; is the
				// same as origin vector ...
				// ...because>>>>>because we need to update the startLine, when rotation ends,
				// with this origin so we ...
				// ...get the real world coordinates of the rotated originator laser point - END
				// LINE IS PRACTICALLY THE ORIGIN AT THIS POINT
				electrotrap_red.blocker.endLineSec.x =
						electrotrap_red.blocker.startLineSec.x;
				electrotrap_red.blocker.endLineSec.y =
						electrotrap_red.getY() +
								electrotrap_red.blocker.relativeY +
								electrotrap_red.height / 2;
				// we get a vector (direction and length) from origin to startLine (what is
				// to be the future originator...
				// ...after the blockers end their rotation)
				electrotrap_red.blocker.startLineSec =
						electrotrap_red.blocker.startLineSec.sub(origin);
				// set blocker origin - > the point around which the rotation will be done
				electrotrap_red.blocker.OriginPivot2X =
						electrotrap_red.blocker.rotationPivotXSec;
				electrotrap_red.blocker.OriginPivot2Y =
						electrotrap_red.blocker.rotationPivotYSec;
				electrotrap_red.blocker.OriginPivot2XR =
						electrotrap_red.blocker.rotationPivotXSec-2.08f*2 + 0.5f;
				electrotrap_red.blocker.OriginPivot2YR =
						electrotrap_red.blocker.rotationPivotYSec;
				electrotrap_red.blocker.setOrigin(
						electrotrap_red.blocker.OriginPivot2X,
						electrotrap_red.blocker.OriginPivot2Y);
				electrotrap_red.blocker.left_circle_origin_circleSec = new Vector2(
						electrotrap_red.blocker.left_circle_collision.x,
						electrotrap_red.blocker.left_circle_collision.y)
						.sub(new Vector2(
								electrotrap_red.blocker.endLine.x,
								electrotrap_red.blocker.endLine.y));
				electrotrap_red.blocker.right.setOrigin(
						electrotrap_red.blocker.OriginPivot2XR,
						electrotrap_red.blocker.OriginPivot2YR);
				electrotrap_red.blocker.right_circle_origin_circleSec = new Vector2(
						electrotrap_red.blocker.right_circle_collision.x,
						electrotrap_red.blocker.right_circle_collision.y)
						.sub(new Vector2(
								electrotrap_red.blocker.endLine.x,
								electrotrap_red.blocker.endLine.y));
				// calculate the quadrant acceleration (or decelerations)
				electrotrap_red.blocker.rotationAccelQ1Sec =
						(electrotrap_red.blocker.speedP2Sec - electrotrap_red.blocker.speedP1Sec)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ2Sec =
						(electrotrap_red.blocker.speedP3Sec - electrotrap_red.blocker.speedP2Sec)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ3Sec =
						(electrotrap_red.blocker.speedP4Sec - electrotrap_red.blocker.speedP3Sec)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ4Sec =
						(electrotrap_red.blocker.speedP1Sec - electrotrap_red.blocker.speedP4Sec)
								/ 2f / timeforQuadrant;

				/* ROTATIVE PART *********************************************************/
				// rotation pivot is relative to the lower left corner of the blocker
				// we subtract 0.23105f because the right blocker is overlapped on the first one
				electrotrap_red.blocker.rotationPivotX =
						(electrotrap_red.blocker.width) - 0.23105f;
				electrotrap_red.blocker.rotationPivotY =
						(electrotrap_red.blocker.height) / 2 + 0.004f;
				// we get the start vector of the laser by adding the world coordinates
				// of the blocker
				electrotrap_red.blocker.startLine.x =
						electrotrap_red.blocker.rotationPivotX + electrotrap_red.blocker.getX();
				electrotrap_red.blocker.startLine.y =
						electrotrap_red.blocker.rotationPivotY + electrotrap_red.blocker.getY();
				// at this time we make the end vector the same as the start vector because
				// they're both alomost in the middle
				electrotrap_red.blocker.endLine.x =
						electrotrap_red.blocker.rotationPivotX + electrotrap_red.blocker.getX();
				electrotrap_red.blocker.endLine.y =
						electrotrap_red.blocker.rotationPivotY + electrotrap_red.blocker.getY();
				// set blocker origin - > the point around which the rotation will be done
				electrotrap_red.blocker.OriginPivot1X =
						electrotrap_red.blocker.rotationPivotX;
				electrotrap_red.blocker.OriginPivot1Y =
						electrotrap_red.blocker.rotationPivotY;
				electrotrap_red.blocker.OriginPivot1XR =
						electrotrap_red.blocker.rotationPivotX-2.08f*2 + 0.5f;
				electrotrap_red.blocker.OriginPivot1YR =
						electrotrap_red.blocker.rotationPivotY;
				electrotrap_red.blocker.setOrigin(
						electrotrap_red.blocker.OriginPivot1X,
						electrotrap_red.blocker.OriginPivot1Y);
				electrotrap_red.blocker.left_circle_origin_circle = new Vector2(
						electrotrap_red.blocker.left_circle_collision.x,
						electrotrap_red.blocker.left_circle_collision.y)
						.sub(new Vector2(
								electrotrap_red.blocker.endLine.x,
								electrotrap_red.blocker.endLine.y));
				electrotrap_red.blocker.right.setOrigin(
						electrotrap_red.blocker.OriginPivot1XR,
						electrotrap_red.blocker.OriginPivot1YR);
				electrotrap_red.blocker.right_circle_origin_circle = new Vector2(
						electrotrap_red.blocker.right_circle_collision.x,
						electrotrap_red.blocker.right_circle_collision.y)
						.sub(new Vector2(
								electrotrap_red.blocker.endLine.x,
								electrotrap_red.blocker.endLine.y));
				// calculate the quadrant acceleration (or decelerations)
				electrotrap_red.blocker.rotationAccelQ1 =
						(electrotrap_red.blocker.speedP2 - electrotrap_red.blocker.speedP1)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ2 =
						(electrotrap_red.blocker.speedP3 - electrotrap_red.blocker.speedP2)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ3 =
						(electrotrap_red.blocker.speedP4 - electrotrap_red.blocker.speedP3)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ4 =
						(electrotrap_red.blocker.speedP1 - electrotrap_red.blocker.speedP4)
								/ 2f / timeforQuadrant;
			}
			break;
			//ROBIN BOUNCER LOAD VARS
			case RobinBouncer:
			{
				// rotation pivot is relative to the lower left corner of the blocker + robin
				// radius on the UPSIDE
				// we subtract 0.23105f because the right blocker is overlapped on the first one
				electrotrap_red.blocker.rotationPivotX =
						(electrotrap_red.blocker.width) - 0.23105f;
				electrotrap_red.blocker.rotationPivotY =
						(electrotrap_red.blocker.height) / 2 + 0.004f +
						 electrotrap_red.blocker.robin_radius;
				// the startLine vector of the laser is somewhere around the blocker using
				// the world coordinates - below the electrotrap
 				electrotrap_red.blocker.startLine.x =
						(electrotrap_red.blocker.width) - 0.23105f + electrotrap_red.blocker.getX();
				electrotrap_red.blocker.startLine.y =
						(electrotrap_red.blocker.height) / 2 + electrotrap_red.blocker.getY();
				// ORIGIN should be the middle of distance between electrotraps and should
				// be at the middle...
				// ...of the height of electrotrap height + the added relative on Y ; around
				// this origin we...
				// ...rotate the startLine (originator of laser beam)
				Vector2 origin = new Vector2(
						electrotrap_red.blocker.startLine.x,
						electrotrap_red.getY() +
								electrotrap_red.blocker.relativeY +
								electrotrap_red.height /2 );
				// we init the endLine as the origin (the pivot of rotation or origin) ; is the
				// same as origin vector ...
				// ...because>>>>>because we need to update the startLine, when rotation
				// ends, with this origin so we ...
				// ...get the real world coordinates of the rotated originator laser point
				electrotrap_red.blocker.endLine.x =
						electrotrap_red.blocker.startLine.x;
				electrotrap_red.blocker.endLine.y =
						electrotrap_red.getY() +
								electrotrap_red.blocker.relativeY +
								electrotrap_red.height / 2;
				// we get a vector (direction and length) from origin to startLine (what is
				// to be the future originator...
				// ...after the blockers end their rotation)
				electrotrap_red.blocker.startLine =
						electrotrap_red.blocker.startLine.sub(origin);
				// set blocker origin - > the point around which the rotation will be done
				electrotrap_red.blocker.setOrigin(
						electrotrap_red.blocker.rotationPivotX,
						electrotrap_red.blocker.rotationPivotY);
				electrotrap_red.blocker.left_circle_origin_circle = new Vector2(
						electrotrap_red.blocker.left_circle_collision.x,
						electrotrap_red.blocker.left_circle_collision.y)
						.sub(new Vector2(
								electrotrap_red.blocker.endLine.x,
								electrotrap_red.blocker.endLine.y));
				electrotrap_red.blocker.right.setOrigin(
						electrotrap_red.blocker.rotationPivotX-2.08f*2 + 0.5f,
						electrotrap_red.blocker.rotationPivotY);
				electrotrap_red.blocker.right_circle_origin_circle = new Vector2(
						electrotrap_red.blocker.right_circle_collision.x,
						electrotrap_red.blocker.right_circle_collision.y)
						.sub(new Vector2(
								electrotrap_red.blocker.endLine.x,
								electrotrap_red.blocker.endLine.y));
				// calculate the quadrant acceleration (or decelerations)
				electrotrap_red.blocker.rotationAccelQ1 =
						(electrotrap_red.blocker.speedP2 - electrotrap_red.blocker.speedP1)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ2 =
						(electrotrap_red.blocker.speedP3 - electrotrap_red.blocker.speedP2)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ3 =
						(electrotrap_red.blocker.speedP4 - electrotrap_red.blocker.speedP3)
								/ 2f / timeforQuadrant;
				electrotrap_red.blocker.rotationAccelQ4 =
						(electrotrap_red.blocker.speedP1 - electrotrap_red.blocker.speedP4)
								/ 2f / timeforQuadrant;

				if(electrotrap_red.blocker.currentDir == 1){
					electrotrap_red.blocker.left_circle_origin_circle.setAngle(270.28316f);
					electrotrap_red.blocker.right_circle_origin_circle.setAngle(89.70294f);
				} else if(electrotrap_red.blocker.currentDir == -1){
					electrotrap_red.blocker.left_circle_origin_circle.setAngle(-270.28316f);
					electrotrap_red.blocker.right_circle_origin_circle.setAngle(-89.70294f);
				}

				/* BOUNCER PART *************************************************************/
				timeforQuadrant = electrotrap_red.blocker.timeforQuadrantSec;
				Vector2 left_orig_v = new
						Vector2(electrotrap_red.blocker.left_circle_origin_circle);
				Vector2 left_blocker_orig = new Vector2(
						electrotrap_red.blocker.endLine.x,
						electrotrap_red.blocker.endLine.y);
				Vector2 right_orig_v = new
						Vector2(electrotrap_red.blocker.right_circle_origin_circle);
				Vector2 right_blocker_orig = new Vector2(
						electrotrap_red.blocker.endLine.x,
						electrotrap_red.blocker.endLine.y);
				electrotrap_red.blocker.left_circle_collision
						.setPosition(left_orig_v.add(left_blocker_orig));
				electrotrap_red.blocker.right_circle_collision
						.setPosition(right_orig_v.add(right_blocker_orig));
				electrotrap_red.blocker.translationAccelI1 =
						(electrotrap_red.blocker.middleSpeedSec -
								electrotrap_red.blocker.leftSpeedSec) / 2f / timeforQuadrant;
				electrotrap_red.blocker.translationAccelI2 =
						(electrotrap_red.blocker.rightSpeedSec -
								electrotrap_red.blocker.middleSpeedSec) / 2f / timeforQuadrant;
				electrotrap_red.blocker.translationAccelIB1 =
						(electrotrap_red.blocker.middleSpeedSec -
								electrotrap_red.blocker.rightSpeedSec) / 2f / timeforQuadrant;
				electrotrap_red.blocker.translationAccelIB2 =
						(electrotrap_red.blocker.leftSpeedSec -
								electrotrap_red.blocker.middleSpeedSec) / 2f / timeforQuadrant;
				if(electrotrap_red.blocker.currentDirSec == 1){
				} else if(electrotrap_red.blocker.currentDirSec == -1){
					electrotrap_red.blocker.endLine.x += electrotrap_red.blocker.deployDistanceSec;
				}
			}
			break;
			default:
			break;
		}
		electrotrap_red.blocker.startv = new Vector2(electrotrap_red.blocker.startLine);
		electrotrap_red.blocker.endv = new Vector2(electrotrap_red.blocker.endLine);
		electrotrap_red.lbolt = new ex01LightningBolt(
				atlas,
				new Vector2(electrotrap_red.getX() + electrotrap_red.getWidth()/2f,
						    electrotrap_red.getY() + electrotrap_red.height / 2f),
				new Vector2(electrotrap_red.right.getX(),
						    electrotrap_red.getY() + electrotrap_red.height / 2f),
				BOLT_NORMAL_H_W * electrotrap_red.electrotrap_Width);
	}

	public boolean check_collision(float delta,
								   Circle rect,
								   float Y,
								   ArrayList<ex01CryoshipLaserShoot> laser_shoots,
								   ArrayList<Sprite> explosion_laser_blocker,
								   Animation animation,
								   Sound sound_explode,
								   int min_etrap_i,
								   int max_etrap_i){
		returner = false;
		etrap_list = this.electro_red_list;
		for(i=min_etrap_i; i<max_etrap_i; i++){
			etrp = etrap_list.get(i);
			//check lateral electrotraps
			block_lcircle = etrp.blocker.left_circle_collision;
			block_rcircle = etrp.blocker.right_circle_collision;
			// we are in collision etrap RADIUS	- further optimisation to be achieved
			if(  (rect.y) > etrp.collision_electrotrap_min_check
			  && (rect.y) < etrp.collision_electrotrap_max_check  ){
				//check etrap collision
				if(overlaps(rect, etrp.left_collision_rectangle) ||
				   overlaps(rect, etrp.right_collision_rectangle)) {
					returner = true;
					break;
				}
				//check blockers collision
				if(overlaps(block_lcircle, rect)  || overlaps(block_rcircle, rect)) {
					returner = true;
					break;
				}
			}
			//check if blocker was hit
			for(m = laser_shoots.size() - 1; m >= 0; m--){
				if(!(laser_shoots.get(m).can_explode_now == true)
				&& !(laser_shoots.get(m).blocked_after_first_explosion_not_repositioned)){
					if(overlaps(block_lcircle, laser_shoots.get(m).collision_rect) ||
					   overlaps(block_rcircle, laser_shoots.get(m).collision_rect) ){
							etrp.blocker.can_initiate_expansion = true;
							laser_shoots.get(m).can_explode_now = true;
							laser_shoots.get(m)
									.blocked_after_first_explosion_not_repositioned = true;
							sound_explode.play(ex01Types.BLOCKER_HIT);
					}
				} else {
					if(laser_shoots.get(m).can_explode_now){
						laser_shoots.get(m).UpdateExplosionMicro(delta, animation);
					}
				}
			}
		}
//		return false;
		return returner;
	}

	public void FreeTrapLeftOrRight(ex01CryoshipPrincipial spaces,
									ex01ElectrotrapRed curr_etrap,
									Circle rects,
									boolean left,
									boolean right){

		if(curr_etrap.give_electro_shock){
			if(left){ // are we electrochocked on the left
				curr_etrap.bolt_1_endo.x = rects.x - rects.radius/1.75f;
				curr_etrap.bolt_1_endo.y = rects.y;
				curr_etrap.bolt_2_endo.x = rects.x + rects.radius/1.75f;
				curr_etrap.bolt_2_endo.y = rects.y;
				curr_etrap.bolt_3_endo.x = rects.x + rects_widthp2;
				curr_etrap.bolt_3_endo.y = rects.y + rects_heightp2;
				curr_etrap.bolt_1_end.x = curr_etrap.bolt_1_endo.x;
				curr_etrap.bolt_1_end.y = curr_etrap.bolt_1_endo.y;
				curr_etrap.bolt_2_end.x = curr_etrap.bolt_2_endo.x;
				curr_etrap.bolt_2_end.y = curr_etrap.bolt_2_endo.y;
				curr_etrap.bolt_3_end.x = curr_etrap.bolt_3_endo.x;
				curr_etrap.bolt_3_end.y = curr_etrap.bolt_3_endo.y;
				spaces.eShock1.setPosition(
						curr_etrap.bolt_1_endo.x - eshock_widthp2f1,
						curr_etrap.bolt_1_endo.y - eshock_heightp2f1);
				spaces.eShock2.setPosition(
						curr_etrap.bolt_2_endo.x - eshock_widthp2f2,
						curr_etrap.bolt_2_endo.y - eshock_heightp2f2);
				spaces.eShock3.setPosition(
						curr_etrap.bolt_3_endo.x - eshock_widthp2f3,
						curr_etrap.bolt_3_endo.y - eshock_heightp2f3);
			} else if(right){ // are we electrochocked on the right
				curr_etrap.bolt_1_startoR.x = rects.x - rects.radius/1.75f;
				curr_etrap.bolt_1_startoR.y = rects.y;
				curr_etrap.bolt_2_startoR.x = rects.x + rects.radius/1.75f;
				curr_etrap.bolt_2_startoR.y = rects.y;
				curr_etrap.bolt_3_startoR.x = rects.x + rects_widthp2;
				curr_etrap.bolt_3_startoR.y = rects.y + rects_heightp2;
				curr_etrap.bolt_1_startR.x = curr_etrap.bolt_1_startoR.x;
				curr_etrap.bolt_1_startR.y = curr_etrap.bolt_1_startoR.y;
				curr_etrap.bolt_2_startR.x = curr_etrap.bolt_2_startoR.x;
				curr_etrap.bolt_2_startR.y = curr_etrap.bolt_2_startoR.y;
				curr_etrap.bolt_3_startR.x = curr_etrap.bolt_3_startoR.x;
				curr_etrap.bolt_3_startR.y = curr_etrap.bolt_3_startoR.y;
				spaces.eShock1.setPosition(
						curr_etrap.bolt_1_startoR.x - eshock_widthp2f1,
						curr_etrap.bolt_1_startoR.y - eshock_heightp2f1);
				spaces.eShock2.setPosition(
						curr_etrap.bolt_2_startoR.x - eshock_widthp2f2,
						curr_etrap.bolt_2_startoR.y - eshock_heightp2f2);
				spaces.eShock3.setPosition(
						curr_etrap.bolt_3_startoR.x - eshock_widthp2f3,
						curr_etrap.bolt_3_startoR.y - eshock_heightp2f3);
			}
		} else {
			if( curr_etrap.blocker.start_expansion ){
				if(curr_etrap.angle_expansion < 40f){
					if(curr_etrap.give_electro_need_to_change_delegate_notfree){
						curr_etrap.bolt_1_end.x = curr_etrap.bolt_1_endo.x;
						curr_etrap.bolt_1_end.y = curr_etrap.bolt_1_endo.y;
						curr_etrap.bolt_1_startR.x = curr_etrap.bolt_1_startoR.x;
						curr_etrap.bolt_1_startR.y = curr_etrap.bolt_1_startoR.y;
						GenerateLightningBoltChange(curr_etrap, free_etrap_left_right_string);
						curr_etrap.give_electro_need_to_change_delegate_notfree = false;
					}
				}
			}
		}
	}

	// this is the max angle <= in order to unlock the next level - all blockers must be
	// unlocked at least by 40f degrees
	public static final float MAX_ANGLE_TO_UNLOCK_NEXT_LEVEL = 40f;
	// number of blockers de-blocked - used for next level de-blocking - all levels
	// must be de-blocked
	public int blocked_deblocked_counter = 0;

	public void StartAndEvolveExpansion(ex01ElectrotrapRed curr_etrap,
										float cosXer,
										float sinXer){
		// so we don't reset the laser vectors when expanding
		curr_etrap.blocker.start_expansion = true;
		if(!curr_etrap.started_expansion){
			if(curr_etrap.angle_expansion < MAX_ANGLE_TO_UNLOCK_NEXT_LEVEL){
				// number of blockers de-blocked - used for next level de-blocking - all
				// levels must be de-blocked
				blocked_deblocked_counter += 1;
			}
			curr_etrap.blocker.startLine.x = curr_etrap.blocker.endLine.x;
			curr_etrap.blocker.startLine.y = curr_etrap.blocker.endLine.y;
			curr_etrap.started_expansion = true;
			if(  (curr_etrap.getY()+curr_etrap.getHeight()/2 + COLLISION_BOUNDARY) >=
					curr_etrap.blocker.startLine.y  )
			{
				if(curr_etrap.blocker.type == CRBlockertType.RotativeRobin){
					curr_etrap.collision_electrotrap_min_check -=
							curr_etrap.blocker.bl_length_exp;
				} else curr_etrap.collision_electrotrap_min_check -=
						curr_etrap.blocker.bl_length_exp/2;
			}
			if(  (curr_etrap.getY()+curr_etrap.getHeight()/2 - COLLISION_BOUNDARY) <=
					curr_etrap.blocker.startLine.y  )
			{
				if(curr_etrap.blocker.type == CRBlockertType.RotativeRobin){
					curr_etrap.collision_electrotrap_max_check +=
							curr_etrap.blocker.bl_length_exp;
				} else curr_etrap.collision_electrotrap_max_check +=
						curr_etrap.blocker.bl_length_exp/2;
			}
		}
		distV.x = curr_etrap.blocker.startLine.x;
		distV.y = curr_etrap.blocker.startLine.y;
		dist = distV.dst(curr_etrap.blocker.endLine);
		if(dist < curr_etrap.blocker.bl_length_exp){
			curr_etrap.blocker.setPosition(
					curr_etrap.blocker.getX() - cosXer*BLOCKER_EXPANSION_SPEED,
					curr_etrap.blocker.getY() - sinXer*BLOCKER_EXPANSION_SPEED);
			curr_etrap.blocker.right.setPosition(
					curr_etrap.blocker.right.getX() + cosXer*BLOCKER_EXPANSION_SPEED,
					curr_etrap.blocker.right.getY() + sinXer*BLOCKER_EXPANSION_SPEED);
			curr_etrap.blocker.startLine.x -= cosXer*BLOCKER_EXPANSION_SPEED;
			curr_etrap.blocker.startLine.y -= sinXer*BLOCKER_EXPANSION_SPEED;
			curr_etrap.blocker.endLine.x += cosXer*BLOCKER_EXPANSION_SPEED;
			curr_etrap.blocker.endLine.y += sinXer*BLOCKER_EXPANSION_SPEED;
			//the vector doesn't count anymore because we don't rotate anymore - so
			// we only translate the circles
			curr_etrap.blocker.left_circle_collision.x -= cosXer*BLOCKER_EXPANSION_SPEED;
			curr_etrap.blocker.left_circle_collision.y -= sinXer*BLOCKER_EXPANSION_SPEED;
			curr_etrap.blocker.right_circle_collision.x += cosXer*BLOCKER_EXPANSION_SPEED;
			curr_etrap.blocker.right_circle_collision.y += sinXer*BLOCKER_EXPANSION_SPEED;
			//what angle is the line blocked at
			if(!curr_etrap.angle_expanded_once){
				curr_etrap.angle_expansion = angleToVertical(
						Math.abs(curr_etrap.blocker.startLine.x - curr_etrap.blocker.endLine.x),
						Math.abs(curr_etrap.blocker.startLine.y - curr_etrap.blocker.endLine.y));
				curr_etrap.angle_expanded_once = true;
			}
			curr_etrap.blocker.angle = curr_etrap.angle_expansion;
			//do we just do our usual stuff?
			if(!curr_etrap.give_electro_shock){
				if(curr_etrap.angle_expansion < MAX_ANGLE_TO_UNLOCK_NEXT_LEVEL){
					curr_etrap.blocker.angle_small = true;
					//we need to calculate the bolts
					//..change end bolts - free pass is on the right
					if( (curr_etrap.blocker.startLine.x + curr_etrap.blocker.endLine.x)/2f <=
							curr_etrap.middle_etrap){
						curr_etrap.bolt_1_endo.x = curr_etrap.blocker.endLine.x;
						curr_etrap.bolt_1_endo.y = curr_etrap.blocker.endLine.y;
						curr_etrap.bolt_2_endo.x = curr_etrap.blocker.startLine.x;
						curr_etrap.bolt_2_endo.y = curr_etrap.blocker.startLine.y;
						curr_etrap.bolt_3_endo.x = (curr_etrap.blocker.startLine.x +
													curr_etrap.blocker.endLine.x)/2f;
						curr_etrap.bolt_3_endo.y = (curr_etrap.blocker.startLine.y +
													curr_etrap.blocker.endLine.y)/2f;
						curr_etrap.free_to_right = true;
						curr_etrap.free_to_left = false;
					} else {
					//..change start bolts - free pass is on the left
						curr_etrap.bolt_1_startoR.x = curr_etrap.blocker.endLine.x;
						curr_etrap.bolt_1_startoR.y = curr_etrap.blocker.endLine.y;
						curr_etrap.bolt_2_startoR.x = curr_etrap.blocker.startLine.x;
						curr_etrap.bolt_2_startoR.y = curr_etrap.blocker.startLine.y;
						curr_etrap.bolt_3_startoR.x = (curr_etrap.blocker.startLine.x +
													   curr_etrap.blocker.endLine.x)/2f;
						curr_etrap.bolt_3_startoR.y = (curr_etrap.blocker.startLine.y +
													   curr_etrap.blocker.endLine.y)/2f;
						curr_etrap.free_to_left = true;
						curr_etrap.free_to_right = false;
					}
				} else { curr_etrap.blocker.angle_small = false; }
			//or do we electroshock the spacecraft?
			}
		}
	}

	public void StartAndEvolveExpansionSec(ex01ElectrotrapRed curr_etrap,
										   float cosXer,
										   float sinXer){
		// so we don't reset the laser vectors when expanding
		curr_etrap.blocker.start_expansion = true;
		if(!curr_etrap.started_expansion){
			if(curr_etrap.angle_expansion < MAX_ANGLE_TO_UNLOCK_NEXT_LEVEL){
				// number of blockers de-blocked - used for next level de-blocking - all
				// levels must be de-blocked
				blocked_deblocked_counter += 1;
			}
			curr_etrap.blocker.startLine.x = curr_etrap.blocker.endLine.x;
			curr_etrap.blocker.startLine.y = curr_etrap.blocker.endLine.y;
			curr_etrap.started_expansion = true;
			if(  (curr_etrap.getY()+curr_etrap.getHeight()/2 + COLLISION_BOUNDARY) >=
					curr_etrap.blocker.startLine.y  )
			{
				if(curr_etrap.blocker.type == CRBlockertType.RotativeRobin){
					curr_etrap.collision_electrotrap_min_check -=
							curr_etrap.blocker.bl_length_exp;
				} else curr_etrap.collision_electrotrap_min_check -=
						curr_etrap.blocker.bl_length_exp/2;
			}
			if(  (curr_etrap.getY()+curr_etrap.getHeight()/2 - COLLISION_BOUNDARY) <=
					curr_etrap.blocker.startLine.y  )
			{
				if(curr_etrap.blocker.type == CRBlockertType.RotativeRobin){
					curr_etrap.collision_electrotrap_max_check +=
							curr_etrap.blocker.bl_length_exp;
				} else curr_etrap.collision_electrotrap_max_check +=
						curr_etrap.blocker.bl_length_exp/2;
			}
		}
		distV.x = curr_etrap.blocker.startLineSec.x;
		distV.y = curr_etrap.blocker.startLineSec.y;
		dist = distV.dst(curr_etrap.blocker.endLineSec);
		if(dist < curr_etrap.blocker.bl_length_exp){
			curr_etrap.blocker.setPosition(
					curr_etrap.blocker.getX() - cosXer*BLOCKER_EXPANSION_SPEED,
					curr_etrap.blocker.getY() - sinXer*BLOCKER_EXPANSION_SPEED);
			curr_etrap.blocker.right.setPosition(
					curr_etrap.blocker.right.getX() + cosXer*BLOCKER_EXPANSION_SPEED,
					curr_etrap.blocker.right.getY() + sinXer*BLOCKER_EXPANSION_SPEED);
			curr_etrap.blocker.startLineSec.x -= cosXer*BLOCKER_EXPANSION_SPEED;
			curr_etrap.blocker.startLineSec.y -= sinXer*BLOCKER_EXPANSION_SPEED;
			curr_etrap.blocker.endLineSec.x += cosXer*BLOCKER_EXPANSION_SPEED;
			curr_etrap.blocker.endLineSec.y += sinXer*BLOCKER_EXPANSION_SPEED;
			curr_etrap.blocker.startLine.x = curr_etrap.blocker.startLineSec.x;
			curr_etrap.blocker.startLine.y = curr_etrap.blocker.startLineSec.y;
			curr_etrap.blocker.endLine.x = curr_etrap.blocker.endLineSec.x;
			curr_etrap.blocker.endLine.y = curr_etrap.blocker.endLineSec.y;
			//the vector doesn't count anymore because we don't rotate anymore - so we only
			// translate the circles
			curr_etrap.blocker.left_circle_collision.x -= cosXer*BLOCKER_EXPANSION_SPEED;
			curr_etrap.blocker.left_circle_collision.y -= sinXer*BLOCKER_EXPANSION_SPEED;
			curr_etrap.blocker.right_circle_collision.x += cosXer*BLOCKER_EXPANSION_SPEED;
			curr_etrap.blocker.right_circle_collision.y += sinXer*BLOCKER_EXPANSION_SPEED;
			//what angle is the line blocked at
			if(!curr_etrap.angle_expanded_once){
				curr_etrap.angle_expansion = angleToVertical(
					Math.abs(curr_etrap.blocker.startLineSec.x - curr_etrap.blocker.endLineSec.x),
					Math.abs(curr_etrap.blocker.startLineSec.y - curr_etrap.blocker.endLineSec.y));
				curr_etrap.angle_expanded_once = true;
			}
			curr_etrap.blocker.angle = curr_etrap.angle_expansion;
			//do we just do our usual stuff?
			if(!curr_etrap.give_electro_shock){
				if(curr_etrap.angle_expansion < MAX_ANGLE_TO_UNLOCK_NEXT_LEVEL){
					curr_etrap.blocker.angle_small = true;
					//we need to calculate the bolts
					//..change end bolts - free pass is on the right
					if( (curr_etrap.blocker.startLineSec.x + curr_etrap.blocker.endLineSec.x)/2f <=
							curr_etrap.middle_etrap){
						curr_etrap.bolt_1_endo.x = curr_etrap.blocker.endLineSec.x;
						curr_etrap.bolt_1_endo.y = curr_etrap.blocker.endLineSec.y;
						curr_etrap.bolt_2_endo.x = curr_etrap.blocker.startLineSec.x;
						curr_etrap.bolt_2_endo.y = curr_etrap.blocker.startLineSec.y;
						curr_etrap.bolt_3_endo.x = (curr_etrap.blocker.startLineSec.x +
													curr_etrap.blocker.endLineSec.x)/2f;
						curr_etrap.bolt_3_endo.y = (curr_etrap.blocker.startLineSec.y +
													curr_etrap.blocker.endLineSec.y)/2f;
						curr_etrap.free_to_right = true;
						curr_etrap.free_to_left = false;
					} else {
					//..change start bolts - free pass is on the left
						curr_etrap.bolt_1_startoR.x = curr_etrap.blocker.endLineSec.x;
						curr_etrap.bolt_1_startoR.y = curr_etrap.blocker.endLineSec.y;
						curr_etrap.bolt_2_startoR.x = curr_etrap.blocker.startLineSec.x;
						curr_etrap.bolt_2_startoR.y = curr_etrap.blocker.startLineSec.y;
						curr_etrap.bolt_3_startoR.x = (curr_etrap.blocker.startLineSec.x +
													   curr_etrap.blocker.endLineSec.x)/2f;
						curr_etrap.bolt_3_startoR.y = (curr_etrap.blocker.startLineSec.y +
													   curr_etrap.blocker.endLineSec.y)/2f;
						curr_etrap.free_to_left = true;
						curr_etrap.free_to_right = false;
					}
				} else { curr_etrap.blocker.angle_small = false; }
			//or do we electroshock the spacecraft?
			}
		}
	}

	public void ResetNoBlockersDeblockCounter(){
		blocked_deblocked_counter = 0;
	}

	// if there is an electroshock it will be activated <give_electro_shock boolean> in
	// update( in function to ...
	// ... check relative distance to middle and distance to electrontrap on vertical, in
	// here we only set the ...
	// ... main bolts (bolt_1_start, bolt_1_end) to (bolt_1_start, bolt_1_endo) which were
	// set into the ...
	// ... FreeTrapLeftOrRight function
	// this changes the start for the bolt on the right only bolt_1_startR
	public void NeedToChangeStart(Random rand,
								  ex01CryoshipPrincipial spaces,
								  ex01CryoHUDDisplay hud_display,
								  int min_etrap_i,
								  int max_etrap_i){

		bolt_end_type = randInt(-1, 1, rand);
		if(bolt_end_type == -1){
			for(i=min_etrap_i; i<max_etrap_i; i++){
				etrap_list.get(i).give_electro_shock_activated_denier = false;
				// changed in FreeTrapLeftOrRight
				etrap_list.get(i).bolt_1_startR = etrap_list.get(i).bolt_1_startoR;
				if(etrap_list.get(i).give_electro_shock && !spaces.give_electro_shock_activated) {
					spaces.shock_active1 = true;
					spaces.shock_active2 = false;
					spaces.shock_active3 = false;
					spaces.give_electro_shock_activated = true;
					spaces.counterElectroShock1 = 0f;
					spaces.sShock1.play(0.01f);
					if(!game_screen.spaces_can_explode) hud_display.RecedeHealthCounter();
					spaces.sShock1Explode.play(0.03f);
				}
			}
		} else if(bolt_end_type == 0){
			for(i=min_etrap_i; i<max_etrap_i; i++){
				etrap_list.get(i).give_electro_shock_activated_denier = true;
				etrap_list.get(i).bolt_1_startR = etrap_list.get(i).bolt_2_startoR;
				if(etrap_list.get(i).give_electro_shock && !spaces.give_electro_shock_activated) {
					spaces.shock_active1 = false;
					spaces.shock_active2 = true;
					spaces.shock_active3 = false;
					spaces.give_electro_shock_activated = true;
					spaces.counterElectroShock2 = 0f;
					spaces.sShock2.play(0.01f);
					if(!game_screen.spaces_can_explode) hud_display.RecedeHealthCounter();
					spaces.sShock2Explode.play(0.03f);
				}
			}
		} else {
			for(i=min_etrap_i; i<max_etrap_i; i++){
				etrap_list.get(i).give_electro_shock_activated_denier = true;
				etrap_list.get(i).bolt_1_startR = etrap_list.get(i).bolt_3_startoR;
				if(etrap_list.get(i).give_electro_shock && !spaces.give_electro_shock_activated) {
					spaces.shock_active1 = false;
					spaces.shock_active2 = false;
					spaces.shock_active3 = true;
					spaces.give_electro_shock_activated = true;
					spaces.counterElectroShock3 = 0f;
					spaces.sShock3.play(0.01f);
					if(!game_screen.spaces_can_explode) hud_display.RecedeHealthCounter();
					spaces.sShock3Explode.play(0.03f);
				}
			}
		}
	}

	public void NeedToChangeStartF(Random rand,
								   ex01CryoshipPrincipial spaces,
								   ex01CryoHUDDisplay hud_display,
								   int min_etrap_i,
								   int max_etrap_i){
		etrap_list = electro_red_list;
		bolt_end_type = randInt(-1, 1, rand);
		if(bolt_end_type == -1){
			for(i=0; i<etrap_list.size(); i++){
				etrap_list.get(i).give_electro_shock_activated_denier = false;
				//changed in FreeTrapLeftOrRight
				//etrap_list.get(i).bolt_1_startR = etrap_list.get(i).bolt_1_startoR;
				if(etrap_list.get(i).give_electro_shock && !spaces.give_electro_shock_activated) {
					spaces.shock_active1 = true;
					spaces.shock_active2 = false;
					spaces.shock_active3 = false;
					spaces.give_electro_shock_activated = true;
					spaces.counterElectroShock1 = 0f;
					spaces.sShock1.play(0.01f);
					if(!game_screen.spaces_can_explode) hud_display.RecedeHealthCounter();
					spaces.sShock1Explode.play(0.03f);
				}
			}
		} else if(bolt_end_type == 0){
			for(i=0; i<etrap_list.size(); i++){
				etrap_list.get(i).give_electro_shock_activated_denier = true;
				//etrap_list.get(i).bolt_1_startR = etrap_list.get(i).bolt_2_startoR;
				if(etrap_list.get(i).give_electro_shock && !spaces.give_electro_shock_activated) {
					spaces.shock_active1 = false;
					spaces.shock_active2 = true;
					spaces.shock_active3 = false;
					spaces.give_electro_shock_activated = true;
					spaces.counterElectroShock2 = 0f;
					spaces.sShock2.play(0.01f);
					if(!game_screen.spaces_can_explode) hud_display.RecedeHealthCounter();
					spaces.sShock2Explode.play(0.03f);
				}
			}
		} else {
			for(int i=0; i<etrap_list.size(); i++){
				etrap_list.get(i).give_electro_shock_activated_denier = true;
				//etrap_list.get(i).bolt_1_startR = etrap_list.get(i).bolt_3_startoR;
				if(etrap_list.get(i).give_electro_shock && !spaces.give_electro_shock_activated) {
					spaces.shock_active1 = false;
					spaces.shock_active2 = false;
					spaces.shock_active3 = true;
					spaces.give_electro_shock_activated = true;
					spaces.counterElectroShock3 = 0f;
					spaces.sShock3.play(0.01f);
					if(!game_screen.spaces_can_explode) hud_display.RecedeHealthCounter();
					spaces.sShock3Explode.play(0.03f);
				}
			}
		}
	}

	// if there is an electroshock it will be activated <give_electro_shock boolean> in
	// update( in function to ...
	// ... check relative distance to middle and distance to electrontrap on vertical, in
	// here we only set the ...
	// ... main bolts (bolt_1_start, bolt_1_end) to (bolt_1_start, bolt_1_endo) which were
	// set into the ...
	// ... FreeTrapLeftOrRight function
	// this changes the end for the bolt on the left only bolt_1_end
	public void NeedToChangeEnd(Random rand,
								ex01CryoshipPrincipial spaces,
								ex01CryoHUDDisplay hud_display,
								int min_etrap_i,
								int max_etrap_i){

		etrap_list = electro_red_list;
		bolt_end_type = randInt(-1, 1, rand);
		if(bolt_end_type == -1){
			for(i=min_etrap_i; i<max_etrap_i; i++){
				etrap_list.get(i).give_electro_shock_activated_denier = false;
				//gets changed in FreeTrapLeftOrRight
				//etrap_list.get(i).bolt_1_end = etrap_list.get(i).bolt_1_endo;
				if(etrap_list.get(i).give_electro_shock && !spaces.give_electro_shock_activated) {
					spaces.shock_active1 = true;
					spaces.shock_active2 = false;
					spaces.shock_active3 = false;
					spaces.give_electro_shock_activated = true;
					spaces.counterElectroShock1 = 0f;
					spaces.sShock1.play(0.01f);
					if(!game_screen.spaces_can_explode) hud_display.RecedeHealthCounter();
					spaces.sShock1Explode.play(0.03f);
				}
			}
		} else if(bolt_end_type == 0){
			for(i=min_etrap_i; i<max_etrap_i; i++){
				etrap_list.get(i).give_electro_shock_activated_denier = true;
				//etrap_list.get(i).bolt_1_end = etrap_list.get(i).bolt_2_endo;
				if(etrap_list.get(i).give_electro_shock && !spaces.give_electro_shock_activated) {
					spaces.shock_active1 = false;
					spaces.shock_active2 = true;
					spaces.shock_active3 = false;
					spaces.give_electro_shock_activated = true;
					spaces.counterElectroShock2 = 0f;
					spaces.sShock2.play(0.01f);
					if(!game_screen.spaces_can_explode) hud_display.RecedeHealthCounter();
					spaces.sShock2Explode.play(0.03f);
				}
			}
		} else {
			for(i=min_etrap_i; i<max_etrap_i; i++){
				etrap_list.get(i).give_electro_shock_activated_denier = true;
				//etrap_list.get(i).bolt_1_end = etrap_list.get(i).bolt_3_endo;
				if(etrap_list.get(i).give_electro_shock && !spaces.give_electro_shock_activated) {
					spaces.shock_active1 = false;
					spaces.shock_active2 = false;
					spaces.shock_active3 = true;
					spaces.give_electro_shock_activated = true;
					spaces.counterElectroShock3 = 0f;
					spaces.sShock3.play(0.01f);
					if(!game_screen.spaces_can_explode) hud_display.RecedeHealthCounter();
					spaces.sShock3Explode.play(0.03f);
				}
			}
		}
	}

	public void NeedToChangeEndF(Random rand,
								 ex01CryoshipPrincipial spaces,
								 ex01CryoHUDDisplay hud_display,
								 int min_etrap_i,
								 int max_etrap_i){

		etrap_list = electro_red_list;
		bolt_end_type = randInt(-1, 1, rand);
		if(bolt_end_type == -1){
			for(i=0; i<etrap_list.size(); i++){
				etrap_list.get(i).give_electro_shock_activated_denier = false;
				//gets changed in FreeTrapLeftOrRight
				//etrap_list.get(i).bolt_1_end = etrap_list.get(i).bolt_1_endo;
				if(etrap_list.get(i).give_electro_shock && !spaces.give_electro_shock_activated) {
					spaces.shock_active1 = true;
					spaces.shock_active2 = false;
					spaces.shock_active3 = false;
					spaces.give_electro_shock_activated = true;
					spaces.counterElectroShock1 = 0f;
					spaces.sShock1.play(0.01f);
					if(!game_screen.spaces_can_explode) hud_display.RecedeHealthCounter();
					spaces.sShock1Explode.play(0.03f);
				}
			}
		} else if(bolt_end_type == 0){
			for(int i=0; i<etrap_list.size(); i++){
				etrap_list.get(i).give_electro_shock_activated_denier = true;
				//etrap_list.get(i).bolt_1_end = etrap_list.get(i).bolt_2_endo;
				if(etrap_list.get(i).give_electro_shock && !spaces.give_electro_shock_activated) {
					spaces.shock_active1 = false;
					spaces.shock_active2 = true;
					spaces.shock_active3 = false;
					spaces.give_electro_shock_activated = true;
					spaces.counterElectroShock2 = 0f;
					spaces.sShock2.play(0.01f);
					if(!game_screen.spaces_can_explode) hud_display.RecedeHealthCounter();
					spaces.sShock2Explode.play(0.03f);
				}
			}
		} else {
			for(i=0; i<etrap_list.size(); i++){
				etrap_list.get(i).give_electro_shock_activated_denier = true;
				//etrap_list.get(i).bolt_1_end = etrap_list.get(i).bolt_3_endo;
				if(etrap_list.get(i).give_electro_shock && !spaces.give_electro_shock_activated) {
					spaces.shock_active1 = false;
					spaces.shock_active2 = false;
					spaces.shock_active3 = true;
					spaces.give_electro_shock_activated = true;
					spaces.counterElectroShock3 = 0f;
					spaces.sShock3.play(0.01f);
					if(!game_screen.spaces_can_explode) hud_display.RecedeHealthCounter();
					spaces.sShock3Explode.play(0.03f);
				}
			}
		}
	}

	public void StartAndEvolvePowerups(ex01ElectrotrapRed curr_etrap,
									   ex01PowerupList powerups_list,
									   double angle,
									   float middle_pos){
		curr_etrap.started_evolve_pups = true;
		middle_between_x = -100f;
		middle_between_y = curr_etrap.getY();
		if(angle<=angles3){ // unghiul cel mai mic
			switch(curr_etrap.blocker.stage_values_powerups){
				// 3 x 1 = 3 lol health
				case 0: { pup_h1=3;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break;
				// 3 x 1 = 3 lol bullets
				case 1: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=3;pup_b2=0;pup_b3=0; }break;
				// 2 x 1 = 2 lol health
				case 2: { pup_h1=2;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break;
				// 2 x 1 = 2 lol bullets
				case 3: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=2;pup_b2=0;pup_b3=0; }break;
				// 2 x 2 = 4 lol health + 2 x 2 = 4 lol bullets
				case 4: { pup_h1=2;pup_h2=0;pup_h3=0; pup_b1=2;pup_b2=0;pup_b3=0; }break;
				// 1 x 2 = 2 lol health + 1 x 2 = 2 lol bullets
				case 5: { pup_h1=1;pup_h2=0;pup_h3=0; pup_b1=1;pup_b2=0;pup_b3=0; }break;
				case 6: { pup_h1=1;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 7: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=1;pup_b2=0;pup_b3=0; }break; //
				//only give this stuff to top players - they deserve it
				case 8: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=3;pup_b2=0;pup_b3=0; }break; // bullet
				case 9: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=2;pup_b2=0;pup_b3=0; }break; // bullet
				case 10:{ pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=1;pup_b2=0;pup_b3=0; }break; // bullet
				case 11:{ pup_h1=3;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; // health
				case 12:{ pup_h1=2;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; // health
				case 13:{ pup_h1=1;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; // health
				//only give this stuff to top and middle players - they deserve it
				case 14: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=3;pup_b2=0;pup_b3=0; }break;//
				case 15: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=2;pup_b2=0;pup_b3=0; }break;//
				case 16:{ pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=1;pup_b2=0;pup_b3=0; }break; //
				case 17:{ pup_h1=3;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 18:{ pup_h1=2;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 19:{ pup_h1=1;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
			}
		} else if(angle>angles3 && angle<=angles2){	// unghiul mediu
			switch(curr_etrap.blocker.stage_values_powerups){
				// 3 x 2 = 6 middle health
				case 0: { pup_h1=0;pup_h2=3;pup_h3=0; pup_b1=1;pup_b2=0;pup_b3=0; }break;
				// 3 x 2 = 6 middle bullets
				case 1: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=1;pup_b2=3;pup_b3=0; }break;
				// 2 x 2 = 4 middle health
				case 2: { pup_h1=0;pup_h2=2;pup_h3=0; pup_b1=1;pup_b2=0;pup_b3=0; }break;
				// 2 x 2 = 4 middle bullets
				case 3: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=1;pup_b2=2;pup_b3=0; }break;
				// 2 x 2 = 4 middle health + 2 x 2 = 4 middle bullets
				case 4: { pup_h1=0;pup_h2=2;pup_h3=0; pup_b1=1;pup_b2=2;pup_b3=0; }break;
				// 1 x 2 = 2 middle health + 1 x 2 = 2 middle bullets
				case 5: { pup_h1=0;pup_h2=1;pup_h3=0; pup_b1=1;pup_b2=1;pup_b3=0; }break;
				case 6: { pup_h1=0;pup_h2=1;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 7: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=1;pup_b3=0; }break; //
				//only give this stuff to top players - they deserve it
				case 8: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 9: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 10:{ pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 11:{ pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 12:{ pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 13:{ pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				//only give this stuff to top and middle players - they deserve it
				case 14: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=3;pup_b3=0; }break; //
				case 15: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=2;pup_b3=0; }break; //
				case 16:{ pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=1;pup_b3=0; }break; //
				case 17:{ pup_h1=0;pup_h2=3;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 18:{ pup_h1=0;pup_h2=2;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 19:{ pup_h1=0;pup_h2=1;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
			}
		} else if(angle>angles2 && angle<=angles1){ // unghiul cel mai mare
			switch(curr_etrap.blocker.stage_values_powerups){
				// 3 x 3 = 9 super health
				case 0: { pup_h1=0;pup_h2=0;pup_h3=3; pup_b1=0;pup_b2=0;pup_b3=0; }break;
				// 3 x 3 = 9 super bullets
				case 1: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=3; }break;
				// 2 x 3 = 6 super health
				case 2: { pup_h1=0;pup_h2=0;pup_h3=2; pup_b1=0;pup_b2=0;pup_b3=0; }break;
				// 2 x 3 = 6 super bullets
				case 3: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=2; }break;
				// 2 x 3 = 6 super health + 2 x 3 = 6 super bullets
				case 4: { pup_h1=0;pup_h2=0;pup_h3=2; pup_b1=0;pup_b2=0;pup_b3=2; }break;
				// 1 x 3 = 3 super health + 1 x 3 = 3 super bullets
				case 5: { pup_h1=0;pup_h2=0;pup_h3=1; pup_b1=0;pup_b2=0;pup_b3=1; }break;
				case 6: { pup_h1=0;pup_h2=0;pup_h3=1; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 7: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=1; }break; //
				//only give this stuff to top players - they deserve it
				case 8: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 9: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 10:{ pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 11:{ pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 12:{ pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 13:{ pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				//only give this stuff to top and middle players - they deserve it
				case 14: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 15: { pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 16:{ pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 17:{ pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 18:{ pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
				case 19:{ pup_h1=0;pup_h2=0;pup_h3=0; pup_b1=0;pup_b2=0;pup_b3=0; }break; //
			}
		} else {
			can_powerup = false;
		}
		colrect_xleft = curr_etrap.left_collision_rectangle.x +
					    curr_etrap.left_collision_rectangle.width;
		colrect_xright =  curr_etrap.right_collision_rectangle.x;
		startX = curr_etrap.blocker.startLine.x;
		endX = curr_etrap.blocker.endLine.x;
		if(!curr_etrap.already_generated_powerups){
			if(curr_etrap.free_to_left){
				wither = (startX>endX)?(endX):(startX);
				middle_between_x = (wither - DELTA_POWERUP_EXPAND_POSITIONER -
											 curr_etrap.blocker.delta_powerup_appear);
			} else if(curr_etrap.free_to_right){
				wither = (startX>endX)?(startX):(endX);
				middle_between_x = (wither + DELTA_POWERUP_EXPAND_POSITIONER +
											 curr_etrap.blocker.delta_powerup_appear);
			}
			powerups_list.GenerateF(
					pup_h1,
					pup_h2,
					pup_h3,
					pup_b1,
					pup_b2,
					pup_b3,
					middle_between_x + curr_etrap.blocker.relXpower,
					middle_between_y + curr_etrap.blocker.relYpower);
			curr_etrap.already_generated_powerups = true;
		} else {

		}
	}

	public void updateLightningBolts(float delta, int min_etrap_i, int max_etrap_i){
		for(i=min_etrap_i; i<max_etrap_i + biggest_etrap_no_so_far*2; i++) {
			if(etrap_list.get(i) != null){
				//etrap_list.get(i).lbolt.UpdateLightningBoltAnimation(delta);
			}
		}
	}

	float counterAnimationUp = 0f;

	//>>>this is what we call before the rendering function
	//>>>update the the electrotrap: lightning bolts, lights, blockers, lasers, etc
	public void update(ex01CryoHUDDisplay hud_display,
					   float delta,
					   float Y,
					   Circle rects,
					   ex01CryoshipPrincipial spaces,
					   ex01PowerupList powerups_list,
					   ex01CryoHUDDisplay hud_disp,
					   int min_etrap_i,
					   int max_etrap_i){
		//used to FreeTrapLeftOrRight /* 1,000,000,000ns == one second */
		left = false; right = false;
		can_be_electrocuted_still = false;
		timer_electrochange = (spaces.can_be_electrocuted == true) ? 850 : 350;
		// seed new randomizer
		// need to change bolts start point? - THIS IS FOR when we get electrochocked for the
		// explosions to appear on the spacecraft - lightning change goes on in renderBody when
		// we change it there
		if (TimeUtils.nanosToMillis(TimeUtils.nanoTime()) - startTime > timer_electrochange) {
			startTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
			need_to_change_start = true;
			need_to_change_start_end = true;
		}

		// we need to reset the bolt_1_start if the blockers are expanding or are finished
		// expanding...
		// ...so if the center of the line between the bolts is near the left we change
		// bolt_1_end,...
		// ...and if the center of the line between the bolts is near the right we change
		// bolt_1_start...
		// ...so the spacecraft can go the way that there is more space
		// one bolt at start line, one bolt at end line, one at middle

		// do we need to change the bolts' start point
		// currently it checks all bolts we need to optimise it so we change only bolts on
		// the screen
		if(need_to_change_start_end)
		{
			// if electroshocked in update() then we change start
			NeedToChangeStartF(rand, spaces, hud_display, min_etrap_i, max_etrap_i);
			NeedToChangeEndF(rand, spaces, hud_display, min_etrap_i, max_etrap_i);
			need_to_change_start_end = false;
		}

		// update blockers rotation and expansion
		etrap_list = electro_red_list;
		rects_x_width_2 = rects.x;
		spaces_width = spaces.sprite.getWidth();
		counterAnimationUp+=delta;
		for(i=min_etrap_i; i<max_etrap_i; i++){
			curr_etrap = etrap_list.get(i);
			UpdateEtrap(curr_etrap, spaces, rects, delta, hud_disp, powerups_list);
		}
		spaces.can_be_electrocuted = can_be_electrocuted_still;
		//this is what we add in the int type returner, the rest is not modified
		if(etrap_list.size()>hud_display.counter_electrotrap && spaces.sprite.getY() >
				((ex01ElectrotrapRed)etrap_list.get(hud_display.counter_electrotrap)).getY()){
			hud_display.counter_electrotrap += 1;
			hud_display.need_to_change_hud_data = true;
			hud_display.need_to_change_electrocounter = true;
		}
	}

	public void UpdateEtrap(ex01ElectrotrapRed curr_etrap,
							ex01CryoshipPrincipial spaces,
							Circle rects,
							float delta,
							ex01CryoHUDDisplay hud_disp,
							ex01PowerupList powerups_list){

		curr_etrap.lbolt.UpdateLightningBoltAnimation(counterAnimationUp, delta);
		if(Math.abs(curr_etrap.getY()-rects.y) < curr_etrap.DISTANCE_MIN_TO_ELECTROSHOCK_Y){
			/* rare enough?  */
			if(TimeUtils.nanoTime() - min_delta_eshocks_counter > min_delta_between_eshocks){
				// if we have the right electrobolt active - SET IN StartAndEvolveExpansion
				if(curr_etrap.free_to_left){
					/* in middle enough? */
					if(!curr_etrap.given_electrocution_once){
						/* in middle enough? */
						if( (rects_x_width_2 > curr_etrap.bolt_1_startR.x)
						 && (rects_x_width_2 < curr_etrap.bolt_1_endR.x)){
							min_delta_eshocks_counter = TimeUtils.nanoTime();
							curr_etrap.give_electro_shock = true;
							can_be_electrocuted_still = true;
							left=false; right=true;
							curr_etrap.electrocuted_left = false;
							curr_etrap.electrocuted_right = true;
							curr_etrap.given_electrocution_once_left_was = false;
							curr_etrap.given_electrocution_once_right_was = true;
							curr_etrap.given_electrocution_once = true;
						} else {curr_etrap.give_electro_shock=false;}
					} else {
						min_delta_eshocks_counter = TimeUtils.nanoTime();
						curr_etrap.give_electro_shock = true;
						can_be_electrocuted_still = true;
						// SET FOR NeedToChangeStart and NeedToChangeEnd
						left = curr_etrap.given_electrocution_once_left_was;
						// SET FOR NeedToChangeStart and NeedToChangeEnd
						right = curr_etrap.given_electrocution_once_right_was;
					}
				// if we have the left electrobolt active - SET IN StartAndEvolveExpansion
				} else if (curr_etrap.free_to_right){
					if(!curr_etrap.given_electrocution_once){
						/* in middle enough? */
						if( (rects_x_width_2 > curr_etrap.bolt_1_start.x)
						 && (rects_x_width_2 < curr_etrap.bolt_1_end.x)){
							min_delta_eshocks_counter = TimeUtils.nanoTime();
							curr_etrap.give_electro_shock = true;
							can_be_electrocuted_still = true;
							left=true; right=false;
							curr_etrap.electrocuted_left = true;
							curr_etrap.electrocuted_right = false;
							curr_etrap.given_electrocution_once_left_was = true;
							curr_etrap.given_electrocution_once_right_was = false;
							curr_etrap.given_electrocution_once = true;
						} else {curr_etrap.give_electro_shock=false;}
					} else {
						min_delta_eshocks_counter = TimeUtils.nanoTime();
						curr_etrap.give_electro_shock = true;
						can_be_electrocuted_still = true;
						// SET FOR NeedToChangeStart and NeedToChangeEnd
						left = curr_etrap.given_electrocution_once_left_was;
						// SET FOR NeedToChangeStart and NeedToChangeEnd
						right = curr_etrap.given_electrocution_once_right_was;
					}
					// if we have the left and the right electonbolts active - from left to right
				} else {
					if(!curr_etrap.given_electrocution_once){
						/* in middle enough? */
						if( (rects_x_width_2 > curr_etrap.bolt_1_start.x)
						 && (rects_x_width_2 < curr_etrap.bolt_1_end.x)){
							min_delta_eshocks_counter = TimeUtils.nanoTime();
							curr_etrap.give_electro_shock = true;
							can_be_electrocuted_still = true;
							middle = (curr_etrap.bolt_1_start.x + curr_etrap.bolt_1_end.x)/2f;
							if(rects_x_width_2>middle){
								left=false; right=true;
								curr_etrap.electrocuted_left = false;
								curr_etrap.electrocuted_right = true;
								curr_etrap.given_electrocution_once_left_was = false;
								curr_etrap.given_electrocution_once_right_was = true;
							} else {
								left=true; right=false; // this talks about electrocution
								curr_etrap.electrocuted_left = true;
								curr_etrap.electrocuted_right = false;
								curr_etrap.given_electrocution_once_left_was = true;
								curr_etrap.given_electrocution_once_right_was = false;
							}
							curr_etrap.given_electrocution_once = true;
						} else {curr_etrap.give_electro_shock=false;}
					} else {
						min_delta_eshocks_counter = TimeUtils.nanoTime();
						curr_etrap.give_electro_shock = true;
						can_be_electrocuted_still = true;
						left = curr_etrap.given_electrocution_once_left_was;
						right = curr_etrap.given_electrocution_once_right_was;
					}
				}
			}
		} else {curr_etrap.give_electro_shock = false;}

		//expanderth them all
		cosXer = (float)MathUtils.cos(MathUtils.degreesToRadians *
				curr_etrap.blocker.sprite_angle) * delta * 2.050f;
		sinXer = (float)MathUtils.sin(MathUtils.degreesToRadians *
				curr_etrap.blocker.sprite_angle) * delta * 2.050f;

		switch(curr_etrap.blocker.type){
			case RoundRobin:
			{
				UpdateRoundRobin(spaces, rects, delta, hud_disp, powerups_list);
			}
			break;
			case Rotative:
			{
				UpdateRotative(spaces, rects, delta, hud_disp, powerups_list);
			}
			break;
			case Bouncer:
			{
				UpdateBouncer(spaces, rects, delta, hud_disp, powerups_list);
			}
			break;
			case RotativeBouncer:
			{
				UpdateRotativeBouncer(spaces, rects, delta, hud_disp, powerups_list);
			}
			break;
			case RotativeRobin:
			{
				UpdateRotativeRobin(spaces, rects, delta, hud_disp, powerups_list);
			}
			break;
			case RobinBouncer:
			{
				UpdateRobinBouncer(spaces, rects, delta, hud_disp, powerups_list);
			}
			break;
		}
	}

	public void UpdateRobinBouncer(ex01CryoshipPrincipial spaces,
								   Circle rects,
								   float delta,
								   ex01CryoHUDDisplay hud_disp,
								   ex01PowerupList powerups_list){

		FreeTrapLeftOrRight(spaces, curr_etrap, rects, left, right);
		if( !curr_etrap.blocker.can_initiate_expansion){ //shot was not on spot
			//if we did not hit the target blockers we still rotate it
			if(curr_etrap.blocker.rotation_sense == 1) {
				RotateBlockerRR(curr_etrap.blocker, delta);
				// to vertical translation stuff here
				TranslateBlockerSecWPivot(curr_etrap.blocker, delta);
			} else {
				RotateBlockerRRNeg(curr_etrap.blocker, delta);
				// to vertical translation stuff here
				TranslateBlockerSecWPivot(curr_etrap.blocker, delta);
			}
		} else { //shot was on target
			//we init the startLine here so we get the robin rotation, startLine only needs
			// to be INIT ONCE
			if(!curr_etrap.blocker.start_expansion){
				//endLine was at origin (pivot rotation point in World Coordinates) so the
				// rotated...
				//...startLine is added to that origin so we get the left-right blocker laser
				// start...
				//...point in world coordinates
				curr_etrap.blocker.startLine.add(curr_etrap.blocker.endLine);
				//we make the end line the start line because the originator is the same almost
				curr_etrap.blocker.endLine.x = curr_etrap.blocker.startLine.x;
				curr_etrap.blocker.endLine.y = curr_etrap.blocker.startLine.y;
			}
			if(!curr_etrap.blocker.expanded) StartAndEvolveExpansion(curr_etrap, cosXer, sinXer);
			if(!curr_etrap.blocker.drawnangle) {
				hud_disp.ProcessHUDAngles(
						curr_etrap.blocker.getY(),
						curr_etrap.blocker.getX(),
						curr_etrap.blocker.angle);
				curr_etrap.blocker.drawnangle = true;
				curr_etrap.blocker.delta_powerup_appear
						= curr_etrap.blocker.bl_length_exp *
						  Math.abs(cosXer) *
						  POWERUP_ANGLE_POSITION_COSXER_MULTIPLIER;
			}
			if(curr_etrap.blocker.angle < 40f){
				if(!curr_etrap.blocker.recolorized)curr_etrap.blocker
						.Colorize(255, 255, 0, 100, 255, 235, 115, 200);
				if(curr_etrap.blocker.can_generate_powerups==1){
					if(!curr_etrap.started_evolve_pups) {
						if(i-1>=0) {
							prev_etrap = etrap_list.get(i-1);
						} else prev_etrap = null;
						if(i+1<etrap_list.size()) {
							next_etrap = etrap_list.get(i+1);
						} else next_etrap = null;
						StartAndEvolvePowerups(
								curr_etrap,
								powerups_list,
								curr_etrap.blocker.angle,
								spaces.sprite.getX() + spaces.sprite.getWidth()/2f);
					}
				}
				CEB_startLine.x = curr_etrap.blocker.startLine.x;
				CEB_startLine.y = curr_etrap.blocker.startLine.y;
				CEB_endLine.x = curr_etrap.blocker.endLine.x;
				CEB_endLine.y = curr_etrap.blocker.endLine.y;
				CEB2_startLine.x = curr_etrap.blocker.startLine.x;
				CEB2_startLine.y = curr_etrap.blocker.startLine.y;
				CEB2_endLine.x = curr_etrap.blocker.endLine.x;
				CEB2_endLine.y = curr_etrap.blocker.endLine.y;
				if(curr_etrap.free_to_right){
					curr_etrap.blocker.ModifyBarrierMeshFromLineRight(
							CEB_startLine,
							CEB_endLine);
					curr_etrap.blocker.ModifyBarrierMeshFromLineRightO(
							CEB2_startLine,
							CEB2_endLine);
					curr_etrap.blocker.ModifyBarrierMeshFromLineRightDetach(
							-INTERSECTOR_SPEED,
							-INTERSECTOR_SPEED);
//					if(!curr_etrap.blocker.recolorized)curr_etrap.blocker
//							.Colorize(255, 255, 0, 100, 255, 235, 115, 200);
					curr_etrap.blocker.expanded_and_lined = true;
				} else if(curr_etrap.free_to_left){
					curr_etrap.blocker.ModifyBarrierMeshFromLineLeft(
							CEB_startLine,
							CEB_endLine);
					curr_etrap.blocker.ModifyBarrierMeshFromLineLeftO(
							CEB2_startLine,
							CEB2_endLine);
					curr_etrap.blocker.ModifyBarrierMeshFromLineLeftDetach(
							INTERSECTOR_SPEED,
							INTERSECTOR_SPEED);
//					if(!curr_etrap.blocker.recolorized)curr_etrap.blocker
//							.Colorize(255, 255, 0, 100, 255, 235, 115, 200);
					curr_etrap.blocker.expanded_and_lined = true;
				}
			}
		}
	}

	public void UpdateRotativeRobin(ex01CryoshipPrincipial spaces,
									Circle rects,
									float delta,
									ex01CryoHUDDisplay hud_disp,
									ex01PowerupList powerups_list){

		FreeTrapLeftOrRight(spaces, curr_etrap, rects, left, right);
		if( !curr_etrap.blocker.can_initiate_expansion){ //shot was not on spot
			//if we did not hit the target blockers we still rotate it
			if(curr_etrap.blocker.rotation_sense == 1) {
				//ROTATIVE
				curr_etrap.blocker.setOrigin(
						curr_etrap.blocker.OriginPivot1X,
						curr_etrap.blocker.OriginPivot1Y);
				curr_etrap.blocker.right.setOrigin(
						curr_etrap.blocker.OriginPivot1XR,
						curr_etrap.blocker.OriginPivot1YR);
				RotateBlockerROSec(curr_etrap.blocker, delta);

			} else if (curr_etrap.blocker.rotation_sense == -1){
				//ROTATIVE
				curr_etrap.blocker.setOrigin(
						curr_etrap.blocker.OriginPivot1X,
						curr_etrap.blocker.OriginPivot1Y);
				curr_etrap.blocker.right.setOrigin(
						curr_etrap.blocker.OriginPivot1XR,
						curr_etrap.blocker.OriginPivot1YR);
				RotateBlockerRONegSec(curr_etrap.blocker, delta);
			}
			if(curr_etrap.blocker.rotation_senseSec == 1) {
				RotateBlockerRRSecTranslation(curr_etrap.blocker, delta);

			} else if(curr_etrap.blocker.rotation_senseSec == -1) {
				RotateBlockerRRNegSecTranslation(curr_etrap.blocker, delta);
			}
		//shot was on target
		} else {
			//we init the startLine here so we get the robin rotation, startLine only
			// needs to be INIT ONCE
			if(!curr_etrap.blocker.start_expansion){
				//endLine was at origin (pivot rotation point in World Coordinates) so
				// the rotated...
				//...startLine is added to that origin so we get the left-right blocker
				// laser start...
				//...point in world coordinates
				curr_etrap.blocker.startLineSec.add(curr_etrap.blocker.endLineSec);
				//we make the end line the start line because the originator is the same almost
				curr_etrap.blocker.endLineSec.x = curr_etrap.blocker.startLineSec.x;
				curr_etrap.blocker.endLineSec.y = curr_etrap.blocker.startLineSec.y;
			}
			if(!curr_etrap.blocker.expanded) StartAndEvolveExpansionSec(curr_etrap, cosXer, sinXer);
			if(!curr_etrap.blocker.drawnangle) {
				hud_disp.ProcessHUDAngles(
						curr_etrap.blocker.getY(),
						curr_etrap.blocker.getX(),
						curr_etrap.blocker.angle);
				curr_etrap.blocker.drawnangle = true;
				curr_etrap.blocker.delta_powerup_appear  =
								curr_etrap.blocker.bl_length_exp *
								Math.abs(cosXer) *
								POWERUP_ANGLE_POSITION_COSXER_MULTIPLIER;
			}
			if(curr_etrap.blocker.angle < 40f){
				if(!curr_etrap.blocker.recolorized)curr_etrap.blocker
						.Colorize(255, 255, 0, 100, 255, 235, 115, 200);
				if(curr_etrap.blocker.can_generate_powerups==1){
					if(!curr_etrap.started_evolve_pups) {
						if(i-1>=0) {
							prev_etrap = etrap_list.get(i-1);
						} else prev_etrap = null;
						if(i+1<etrap_list.size()) {
							next_etrap = etrap_list.get(i+1);
						} else next_etrap = null;
						StartAndEvolvePowerups(
								curr_etrap,
								powerups_list,
								curr_etrap.blocker.angle,
								spaces.sprite.getX() + spaces.sprite.getWidth()/2f);
					}
				}
				CEB_startLine.x = curr_etrap.blocker.startLineSec.x;
				CEB_startLine.y = curr_etrap.blocker.startLineSec.y;
				CEB_endLine.x = curr_etrap.blocker.endLineSec.x;
				CEB_endLine.y = curr_etrap.blocker.endLineSec.y;
				CEB2_startLine.x = curr_etrap.blocker.startLineSec.x;
				CEB2_startLine.y = curr_etrap.blocker.startLineSec.y;
				CEB2_endLine.x = curr_etrap.blocker.endLineSec.x;
				CEB2_endLine.y = curr_etrap.blocker.endLineSec.y;
				if(curr_etrap.free_to_right){
					curr_etrap.blocker.ModifyBarrierMeshFromLineRight(
							new Vector2(curr_etrap.blocker.startLineSec),
							new Vector2(curr_etrap.blocker.endLineSec));
					curr_etrap.blocker.ModifyBarrierMeshFromLineRightO(
							new Vector2(curr_etrap.blocker.startLineSec),
							new Vector2(curr_etrap.blocker.endLineSec));
					curr_etrap.blocker.ModifyBarrierMeshFromLineRightDetach(
							-INTERSECTOR_SPEED,
							-INTERSECTOR_SPEED);
//					if(!curr_etrap.blocker.recolorized)curr_etrap.blocker
//							.Colorize(255, 255, 0, 100, 255, 235, 115, 200);
					curr_etrap.blocker.expanded_and_lined = true;
				} else if(curr_etrap.free_to_left){
					curr_etrap.blocker.ModifyBarrierMeshFromLineLeft(
							new Vector2(curr_etrap.blocker.startLineSec),
							new Vector2(curr_etrap.blocker.endLineSec));
					curr_etrap.blocker.ModifyBarrierMeshFromLineLeftO(
							new Vector2(curr_etrap.blocker.startLineSec),
							new Vector2(curr_etrap.blocker.endLineSec));
					curr_etrap.blocker.ModifyBarrierMeshFromLineLeftDetach(
							INTERSECTOR_SPEED,
							INTERSECTOR_SPEED);
//					if(!curr_etrap.blocker.recolorized)curr_etrap.blocker
//							.Colorize(255, 255, 0, 100, 255, 235, 115, 200);
					curr_etrap.blocker.expanded_and_lined = true;
				}
			}
		}
	}

	public void UpdateRotativeBouncer(ex01CryoshipPrincipial spaces,
									  Circle rects,
									  float delta,
									  ex01CryoHUDDisplay hud_disp,
									  ex01PowerupList powerups_list){

		FreeTrapLeftOrRight(spaces, curr_etrap, rects, left, right);
		if( !curr_etrap.blocker.can_initiate_expansion){ //shot was on spot not on spot
			//if we did not hit the target blockers we still rotate it
			if(curr_etrap.blocker.rotation_sense == 1) {
				RotateBlockerRO(curr_etrap.blocker, delta);
				// to vertical translation stuff here
				TranslateBlockerSec(curr_etrap.blocker, delta);
			} else {
				RotateBlockerRONeg(curr_etrap.blocker, delta);
				// to vertical translation stuff here
				TranslateBlockerSec(curr_etrap.blocker, delta);
			}
		} else { //shot was on target
			if(!curr_etrap.blocker.expanded) StartAndEvolveExpansion(curr_etrap, cosXer, sinXer);
			if(!curr_etrap.blocker.drawnangle) {
				hud_disp.ProcessHUDAngles(
						curr_etrap.blocker.getY(),
						curr_etrap.blocker.getX(),
						curr_etrap.blocker.angle);
				curr_etrap.blocker.drawnangle = true;
				curr_etrap.blocker.delta_powerup_appear  =
								curr_etrap.blocker.bl_length_exp *
								Math.abs(cosXer) *
								POWERUP_ANGLE_POSITION_COSXER_MULTIPLIER;
			}
			if(curr_etrap.blocker.angle < 40f){
				if(!curr_etrap.blocker.recolorized)curr_etrap.blocker
						.Colorize(255, 255, 0, 100, 255, 235, 115, 200);
				if(curr_etrap.blocker.can_generate_powerups==1){
					if(!curr_etrap.started_evolve_pups) {
						if(i-1>=0) {
							prev_etrap = etrap_list.get(i-1);
						} else prev_etrap = null;
						if(i+1<etrap_list.size()) {
							next_etrap = etrap_list.get(i+1);
						} else next_etrap = null;
						StartAndEvolvePowerups(
								curr_etrap,
								powerups_list,
								curr_etrap.blocker.angle,
								spaces.sprite.getX() + spaces.sprite.getWidth()/2f);
					}
				}
				CEB_startLine.x = curr_etrap.blocker.startLine.x;
				CEB_startLine.y = curr_etrap.blocker.startLine.y;
				CEB_endLine.x = curr_etrap.blocker.endLine.x;
				CEB_endLine.y = curr_etrap.blocker.endLine.y;
				CEB2_startLine.x = curr_etrap.blocker.startLine.x;
				CEB2_startLine.y = curr_etrap.blocker.startLine.y;
				CEB2_endLine.x = curr_etrap.blocker.endLine.x;
				CEB2_endLine.y = curr_etrap.blocker.endLine.y;
				if(curr_etrap.free_to_right){
					curr_etrap.blocker.ModifyBarrierMeshFromLineRight(
							CEB_startLine,
							CEB_endLine);
					curr_etrap.blocker.ModifyBarrierMeshFromLineRightO(
							CEB2_startLine,
							CEB2_endLine);
					curr_etrap.blocker.ModifyBarrierMeshFromLineRightDetach(
							-INTERSECTOR_SPEED,
							-INTERSECTOR_SPEED);
//					if(!curr_etrap.blocker.recolorized)curr_etrap.blocker
//							.Colorize(255, 255, 0, 100, 255, 235, 115, 200);
					curr_etrap.blocker.expanded_and_lined = true;
				} else if(curr_etrap.free_to_left){
					curr_etrap.blocker.ModifyBarrierMeshFromLineLeft(
							CEB_startLine,
							CEB_endLine);
					curr_etrap.blocker.ModifyBarrierMeshFromLineLeftO(
							CEB2_startLine,
							CEB2_endLine);
					curr_etrap.blocker.ModifyBarrierMeshFromLineLeftDetach(
							INTERSECTOR_SPEED,
							INTERSECTOR_SPEED);
//					if(!curr_etrap.blocker.recolorized)curr_etrap.blocker
//							.Colorize(255, 255, 0, 100, 255, 235, 115, 200);
					curr_etrap.blocker.expanded_and_lined = true;
				}
			}
		}
	}

	public void UpdateBouncer(ex01CryoshipPrincipial spaces,
							  Circle rects,
							  float delta,
							  ex01CryoHUDDisplay hud_disp,
							  ex01PowerupList powerups_list){
		FreeTrapLeftOrRight(spaces, curr_etrap, rects, left, right);

		//shot was on spot not on spot
		if( !curr_etrap.blocker.can_initiate_expansion){
			TranslateBlocker(curr_etrap.blocker, delta); // to vertical translation stuff here
			//shot was on target
		} else {
			if(!curr_etrap.blocker.expanded) StartAndEvolveExpansion(curr_etrap, cosXer, sinXer);
			if(!curr_etrap.blocker.drawnangle) {
				hud_disp.ProcessHUDAngles(
						curr_etrap.blocker.getY(),
						curr_etrap.blocker.getX(),
						curr_etrap.blocker.angle);
				curr_etrap.blocker.drawnangle = true;
				curr_etrap.blocker.delta_powerup_appear  =
								curr_etrap.blocker.bl_length_exp *
								Math.abs(cosXer) *
								POWERUP_ANGLE_POSITION_COSXER_MULTIPLIER;
			}
			if(curr_etrap.blocker.angle < 40f){
				if(!curr_etrap.blocker.recolorized)curr_etrap.blocker
						.Colorize(255, 255, 0, 100, 255, 235, 115, 200);
				if(curr_etrap.blocker.can_generate_powerups==1){
					if(!curr_etrap.started_evolve_pups) {
						if(i-1>=0) {
							prev_etrap = etrap_list.get(i-1);
						} else prev_etrap = null;
						if(i+1<etrap_list.size()) {
							next_etrap = etrap_list.get(i+1);
						} else next_etrap = null;
						StartAndEvolvePowerups(
								curr_etrap,
								powerups_list,
								curr_etrap.blocker.angle,
								spaces.sprite.getX() + spaces.sprite.getWidth()/2f);
					}
				}
				CEB_startLine.x = curr_etrap.blocker.startLine.x;
				CEB_startLine.y = curr_etrap.blocker.startLine.y;
				CEB_endLine.x = curr_etrap.blocker.endLine.x;
				CEB_endLine.y = curr_etrap.blocker.endLine.y;
				CEB2_startLine.x = curr_etrap.blocker.startLine.x;
				CEB2_startLine.y = curr_etrap.blocker.startLine.y;
				CEB2_endLine.x = curr_etrap.blocker.endLine.x;
				CEB2_endLine.y = curr_etrap.blocker.endLine.y;
				if(curr_etrap.free_to_right){
					curr_etrap.blocker.ModifyBarrierMeshFromLineRight(
							CEB_startLine,
							CEB_endLine);
					curr_etrap.blocker.ModifyBarrierMeshFromLineRightO(
							CEB2_startLine,
							CEB2_endLine);
					curr_etrap.blocker.ModifyBarrierMeshFromLineRightDetach(
							-INTERSECTOR_SPEED,
							-INTERSECTOR_SPEED);
//					if(!curr_etrap.blocker.recolorized)curr_etrap.blocker
//							.Colorize(255, 255, 0, 100, 255, 235, 115, 200);
					curr_etrap.blocker.expanded_and_lined = true;
				} else if(curr_etrap.free_to_left){
					curr_etrap.blocker.ModifyBarrierMeshFromLineLeft(
							CEB_startLine,
							CEB_endLine);
					curr_etrap.blocker.ModifyBarrierMeshFromLineLeftO(
							CEB2_startLine,
							CEB2_endLine);
					curr_etrap.blocker.ModifyBarrierMeshFromLineLeftDetach(
							INTERSECTOR_SPEED,
							INTERSECTOR_SPEED);
//					if(!curr_etrap.blocker.recolorized)curr_etrap.blocker
//							.Colorize(255, 255, 0, 100, 255, 235, 115, 200);
					curr_etrap.blocker.expanded_and_lined = true;
				}
			}
		}
	}

	public void UpdateRotative(ex01CryoshipPrincipial spaces,
							   Circle rects,
							   float delta,
							   ex01CryoHUDDisplay hud_disp,
							   ex01PowerupList powerups_list){

		FreeTrapLeftOrRight(spaces, curr_etrap, rects, left, right);
		if( !curr_etrap.blocker.can_initiate_expansion){ //shot was on spot not on spot
			//if we did not hit the target blockers we still rotate it
			if(curr_etrap.blocker.rotation_sense == 1) {
				RotateBlockerRO(curr_etrap.blocker, delta);
			} else {
				RotateBlockerRONeg(curr_etrap.blocker, delta);
			}
		} else { //shot was on target
			if(!curr_etrap.blocker.expanded)
				StartAndEvolveExpansion(curr_etrap, cosXer, sinXer);
			if(!curr_etrap.blocker.drawnangle) {
				hud_disp.ProcessHUDAngles(
						curr_etrap.blocker.getY(),
						curr_etrap.blocker.getX(),
						curr_etrap.blocker.angle);
				curr_etrap.blocker.drawnangle = true;
				curr_etrap.blocker.delta_powerup_appear  =
								curr_etrap.blocker.bl_length_exp *
								Math.abs(cosXer) *
								POWERUP_ANGLE_POSITION_COSXER_MULTIPLIER;
			}
			if(curr_etrap.blocker.angle < 40f){
				if(!curr_etrap.blocker.recolorized)curr_etrap.blocker
						.Colorize(255, 255, 0, 100, 255, 235, 115, 200);
				if(curr_etrap.blocker.can_generate_powerups==1){
					if(!curr_etrap.started_evolve_pups) {
						if(i-1>=0) {
							prev_etrap = etrap_list.get(i-1);
						} else prev_etrap = null;
						if(i+1<etrap_list.size()) {
							next_etrap = etrap_list.get(i+1);
						} else next_etrap = null;
						StartAndEvolvePowerups(
								curr_etrap,
								powerups_list,
								curr_etrap.blocker.angle,
								spaces.sprite.getX() + spaces.sprite.getWidth()/2f);
					}
				}
				CEB_startLine.x = curr_etrap.blocker.startLine.x;
				CEB_startLine.y = curr_etrap.blocker.startLine.y;
				CEB_endLine.x = curr_etrap.blocker.endLine.x;
				CEB_endLine.y = curr_etrap.blocker.endLine.y;
				CEB2_startLine.x = curr_etrap.blocker.startLine.x;
				CEB2_startLine.y = curr_etrap.blocker.startLine.y;
				CEB2_endLine.x = curr_etrap.blocker.endLine.x;
				CEB2_endLine.y = curr_etrap.blocker.endLine.y;
				if(curr_etrap.free_to_right){
					curr_etrap.blocker.ModifyBarrierMeshFromLineRight(
							CEB_startLine,
							CEB_endLine);
					curr_etrap.blocker.ModifyBarrierMeshFromLineRightO(
							CEB2_startLine,
							CEB2_endLine);
					curr_etrap.blocker.ModifyBarrierMeshFromLineRightDetach(
							-INTERSECTOR_SPEED,
							-INTERSECTOR_SPEED);
//					if(!curr_etrap.blocker.recolorized)curr_etrap.blocker
//							.Colorize(255, 255, 0, 100, 255, 235, 115, 200);
					curr_etrap.blocker.expanded_and_lined = true;
				} else if(curr_etrap.free_to_left){
					curr_etrap.blocker.ModifyBarrierMeshFromLineLeft(
							CEB_startLine,
							CEB_endLine);
					curr_etrap.blocker.ModifyBarrierMeshFromLineLeftO(
							CEB2_startLine,
							CEB2_endLine);
					curr_etrap.blocker.ModifyBarrierMeshFromLineLeftDetach(
							INTERSECTOR_SPEED,
							INTERSECTOR_SPEED);
//					if(!curr_etrap.blocker.recolorized)curr_etrap.blocker
//							.Colorize(255, 255, 0, 100, 255, 235, 115, 200);
					curr_etrap.blocker.expanded_and_lined = true;
				}
			}
		}
	}

	public void UpdateRoundRobin(ex01CryoshipPrincipial spaces,
								 Circle rects,
								 float delta,
								 ex01CryoHUDDisplay hud_disp,
								 ex01PowerupList powerups_list){

		FreeTrapLeftOrRight(spaces, curr_etrap, rects, left, right);
		if( !curr_etrap.blocker.can_initiate_expansion){ //shot was not on spot
			//if we did not hit the target blockers we still rotate it
			if(curr_etrap.blocker.rotation_sense == 1) {
				RotateBlockerRR(curr_etrap.blocker, delta);
			} else {
				RotateBlockerRRNeg(curr_etrap.blocker, delta);
			}
		} else { //shot was on target
			//we init the startLine here so we get the robin rotation, startLine
			// only needs to be INIT ONCE
			if(!curr_etrap.blocker.start_expansion){
				//endLine was at origin (pivot rotation point in World Coordinates) so the
				// rotated...
				//...startLine is added to that origin so we get the left-right blocker laser
				// start...
				//...point in world coordinates
				curr_etrap.blocker.startLine.add(curr_etrap.blocker.endLine);
				//we make the end line the start line because the originator is the same almost
				curr_etrap.blocker.endLine.x = curr_etrap.blocker.startLine.x;
				curr_etrap.blocker.endLine.y = curr_etrap.blocker.startLine.y;
			}
			if(!curr_etrap.blocker.expanded)
				StartAndEvolveExpansion(curr_etrap, cosXer, sinXer);
			if(!curr_etrap.blocker.drawnangle) {
				hud_disp.ProcessHUDAngles(
						curr_etrap.blocker.getY(),
						curr_etrap.blocker.getX(),
						curr_etrap.blocker.angle);
				curr_etrap.blocker.drawnangle = true;
				curr_etrap.blocker.delta_powerup_appear  =
								curr_etrap.blocker.bl_length_exp *
								Math.abs(cosXer) *
								POWERUP_ANGLE_POSITION_COSXER_MULTIPLIER;
			}
			if(curr_etrap.blocker.angle < 40f){
				if(!curr_etrap.blocker.recolorized)curr_etrap.blocker
						.Colorize(255, 255, 0, 100, 255, 235, 115, 200);
				if(curr_etrap.blocker.can_generate_powerups==1){
					if(!curr_etrap.started_evolve_pups) {
						if(i-1>=0) {
							prev_etrap = etrap_list.get(i-1);
						} else prev_etrap = null;
						if(i+1<etrap_list.size()) {
							next_etrap = etrap_list.get(i+1);
						} else next_etrap = null;
						StartAndEvolvePowerups(
								curr_etrap,
								powerups_list,
								curr_etrap.blocker.angle,
								spaces.sprite.getX() + spaces.sprite.getWidth()/2f);
					}
				}
				CEB_startLine.x = curr_etrap.blocker.startLine.x;
				CEB_startLine.y = curr_etrap.blocker.startLine.y;
				CEB_endLine.x = curr_etrap.blocker.endLine.x;
				CEB_endLine.y = curr_etrap.blocker.endLine.y;
				CEB2_startLine.x = curr_etrap.blocker.startLine.x;
				CEB2_startLine.y = curr_etrap.blocker.startLine.y;
				CEB2_endLine.x = curr_etrap.blocker.endLine.x;
				CEB2_endLine.y = curr_etrap.blocker.endLine.y;
				if(curr_etrap.free_to_right){
					curr_etrap.blocker.ModifyBarrierMeshFromLineRight(
							new Vector2(curr_etrap.blocker.startLine),
							new Vector2(curr_etrap.blocker.endLine));
					curr_etrap.blocker.ModifyBarrierMeshFromLineRightO(
							new Vector2(curr_etrap.blocker.startLine),
							new Vector2(curr_etrap.blocker.endLine));
					curr_etrap.blocker.ModifyBarrierMeshFromLineRightDetach(
							-INTERSECTOR_SPEED,
							-INTERSECTOR_SPEED);
//					if(!curr_etrap.blocker.recolorized)curr_etrap.blocker
//							.Colorize(255, 255, 0, 100, 255, 235, 115, 200);
					curr_etrap.blocker.expanded_and_lined = true;
				} else if(curr_etrap.free_to_left){
					curr_etrap.blocker.ModifyBarrierMeshFromLineLeft(
							new Vector2(curr_etrap.blocker.startLine),
							new Vector2(curr_etrap.blocker.endLine));
					curr_etrap.blocker.ModifyBarrierMeshFromLineLeftO(
							new Vector2(curr_etrap.blocker.startLine),
							new Vector2(curr_etrap.blocker.endLine));
					curr_etrap.blocker.ModifyBarrierMeshFromLineLeftDetach(
							INTERSECTOR_SPEED,
							INTERSECTOR_SPEED);
//					if(!curr_etrap.blocker.recolorized)curr_etrap.blocker
//							.Colorize(255, 255, 0, 100, 255, 235, 115, 200);
					curr_etrap.blocker.expanded_and_lined = true;
				}
			}
		}
	}

	// renders the laser mesh (base mesh and laser line mesh)
	// we need to optimize so we only render the blocker lasers on the screen
	public void renderBlockerLaser(SpriteBatch batch,
								   float Y,
								   OrthographicCamera camera,
								   ex01CryoshipPrincipial spaces,
								   int min_etrap_i,
								   int max_etrap_i){
		etrap_list = this.electro_red_list;
	    Gdx.gl20.glEnable(GL20.GL_BLEND);
	    Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    this.shader.begin();
	    this.shader.setUniformMatrix("u_worldView", camera.combined);
	    this.shader.setUniformi("u_texture", 0);
	    etrap_list.get(0).blocker.textureAtlas_laser.getTexture().bind(0);
		for(int i=min_etrap_i; i<max_etrap_i; i++){
			etrap = etrap_list.get(i);
//			if(etrap.getY()>Y-hscreendif10 && etrap.getY()<Y+hscreenadd10){
				etrap.blocker.meshB.setVertices(etrap.blocker.vertB);
				etrap.blocker.meshB.setIndices(etrap.blocker.indiB);
			    etrap.blocker.meshB.render(this.shader, GL20.GL_TRIANGLES);
				etrap.blocker.meshBO.setVertices(etrap.blocker.vertBO);
				etrap.blocker.meshBO.setIndices(etrap.blocker.indiBO);
				etrap.blocker.meshBO.render(this.shader, GL20.GL_TRIANGLES);
				//draw laser
				if(etrap.blocker.start_expansion){
					start.x = etrap.blocker.startLine.x;
					start.y = etrap.blocker.startLine.y;
					end.x = etrap.blocker.endLine.x;
					end.y = etrap.blocker.endLine.y;
					if(etrap.blocker.angle_small == false){
						etrap.blocker.GetMeshFromLine(start, end, 75, 0, 130, 155);
						etrap.blocker.GetMeshFromLineO(start, end, 147, 112, 219, 250);
					} else {
						etrap.blocker.GetMeshFromLine(start, end, 34, 139, 34, 155);
						etrap.blocker.GetMeshFromLineO(start, end, 124, 252, 0, 250);
					}
					etrap.blocker.mesh.setVertices(etrap.blocker.vert);
					etrap.blocker.mesh.setIndices(etrap.blocker.indi);
					etrap.blocker.meshO.setVertices(etrap.blocker.vertO);
					etrap.blocker.meshO.setIndices(etrap.blocker.indiO);
				    etrap.blocker.mesh.render(this.shader, GL20.GL_TRIANGLES);
				    etrap.blocker.meshO.render(this.shader, GL20.GL_TRIANGLES);
				}
//			}
		}
		this.shader.end();
	}

	// renders the lightning bolt for each and every electrotrap body on the list of
	// electrotraps
	// we will need to optimise so we don't check for all electrotraps but just for those
	// on the screen
	public void renderBody(SpriteBatch batch,
						   float Y,
						   OrthographicCamera camera,
						   int min_etrap_i,
						   int max_etrap_i){
		etrap_list = this.electro_red_list;
		etrap_list_off = this.electro_red_lightoff_list;
		etrap_list_offR = this.electro_red_lightoff_listR;
		etrap_list_on = this.electro_red_lighton_list;
		etrap_list_onR = this.electro_red_lighton_listR;
		for(int i=min_etrap_i; i<max_etrap_i; i++){
			etrap = etrap_list.get(i);
			// lightning_change_delta = change_delta; // 75000000f in
			// constructor in ex01ElectrotrapRedList
			if(TimeUtils.nanoTime() - etrap.startTime > etrap.lightning_change_delta){
				etrap.startTime = TimeUtils.nanoTime();
				RotateLeftRightLightningBolts(etrap, "renderBody RotateLeftRightLightningBolts");
			}
		}
		//draw the left and right body and lights and blockers
		for(int i=min_etrap_i; i<max_etrap_i; i++){
			etrap = etrap_list.get(i);
			if(etrap.getY()>Y-hscreendif10 && etrap.getY()<Y+hscreenadd10){
				etrap.draw(batch);
				etrap.right.draw(batch);
				if(TimeUtils.millis() - startTimeFlash > 500){
					startTimeFlash = TimeUtils.millis();
					startTimeFlashB = !startTimeFlashB;
				} else { }
				if(startTimeFlashB){
							etrap_list_off.get(i).draw(batch);
							etrap_list_offR.get(i).draw(batch);
				} else {
							etrap_list_on.get(i).draw(batch);
							etrap_list_onR.get(i).draw(batch);
				}
				//draw the blockers
				etrap.blocker.draw(batch);
				etrap.blocker.right.draw(batch);
			}
		}
	}

	public void renderLightningVariantArt(SpriteBatch batch,
										  float Y,
										  int min_etrap_i,
										  int max_etrap_i){
		etrap_list = electro_red_list;
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		for(i=min_etrap_i; i<max_etrap_i; i++){
			etrap = etrap_list.get(i);
				if(!etrap.free_to_left) { etrap.lbolt.boltSprite.draw(batch); }
				if(!etrap.free_to_right) { etrap.lbolt.boltSpriteR.draw(batch); }
		}
	}

	// renders the lightning bolt for each and every bolt on the list of electrotraps
	// we will need to optimise so we don't check for all electrotraps but just for those
	// on the screen
	public void renderLightningRectCirclesOld(ShapeRenderer shaper,
											  OrthographicCamera camera,
											  float Y,
											  Circle spaces_rect,
											  ArrayList<ex01CryoshipLaserShoot> laser_shoots,
											  ex01PowerupList world_powerup_list){

		//draw the principal bolt and - for testing purposes - the collision rects,
		// polys and circles
		etrap_list = electro_red_list;
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shaper.setProjectionMatrix(camera.combined);
		shaper.begin(ShapeType.Line);
		for(int i=0; i<etrap_list.size(); i++){
			etrap = etrap_list.get(i);
			if(etrap.getY()>Y-hscreendif10 && etrap.getY()<Y+hscreenadd10){
				DrawMinMaxCollisionBarriers(
						etrap_list.get(i),
						shaper);
				DrawMinMaxCollisionBarriersHealth(
						shaper,
						world_powerup_list.min_powerup_collision_h,
						world_powerup_list.max_powerup_collision_h);
				DrawMinMaxCollisionBarriersBullets(
						shaper,
						world_powerup_list.min_powerup_collision_b,
						world_powerup_list.max_powerup_collision_b);
				DrawLightningBolts(etrap_list.get(i), shaper);
				DrawEtrapAndBlockersCollision(etrap_list.get(i), shaper);
				DrawMeshPolygonBaseRed(etrap_list.get(i), shaper);
				DrawMeshPolygonOverlayBlue(etrap_list.get(i), shaper);
				DrawStartEndIntersectionLineYellow(etrap_list.get(i), shaper);
				DrawBaseMeshUpDownIntersectionLineGreenLeft(etrap_list.get(i), shaper);
				DrawBaseMeshUpDownIntersectionLineGreenRight(etrap_list.get(i), shaper);
				DrawOverlayMeshUpDownIntersectionLineOrangeLeft(etrap_list.get(i), shaper);
				DrawOverlayMeshUpDownIntersectionLineOrangeRight(etrap_list.get(i), shaper);
			}
		}
		DrawSpaceshipCollisionRectsCirclesLasers(shaper, spaces_rect, laser_shoots);
		DrawPowerupsCollisionCircles(shaper, world_powerup_list);
		DrawShootingRectangle(shaper);
		shaper.end();
	}

	public void	DrawShootingRectangle(ShapeRenderer shaper){
		shaper.setProjectionMatrix(camera_hud.combined);
		if(hud!=null){
		shaper.rect(
				hud.spaceship_bounding_rectangle_shoot_hud.x,
				hud.spaceship_bounding_rectangle_shoot_hud.y,
				hud.spaceship_bounding_rectangle_shoot_hud.width,
				hud.spaceship_bounding_rectangle_shoot_hud.height);
		}
		shaper.rect(
				hud.slider_bounding_rectangle.x,
				hud.slider_bounding_rectangle.y,
				hud.slider_bounding_rectangle.width,
				hud.slider_bounding_rectangle.height);
		shaper.rect(
				hud.spaceship_bounding_rectangle_upper.x,
				hud.spaceship_bounding_rectangle_upper.y,
				hud.spaceship_bounding_rectangle_upper.width,
				hud.spaceship_bounding_rectangle_upper.height);
	}

	public void DrawMinMaxCollisionBarriers(ex01ElectrotrapRed etrap, ShapeRenderer shaper){
		shaper.setColor(0f, 0.3f, 1f, 1f);
		CollisionBarrierTestStart.x = COLLISION_BARRIER_XS;
		CollisionBarrierTestStart.y = etrap.collision_electrotrap_min_check;
		CollisionBarrierTestEnd.x = COLLISION_BARRIER_XE;
		CollisionBarrierTestEnd.y = etrap.collision_electrotrap_min_check;
		shaper.line(CollisionBarrierTestStart, CollisionBarrierTestEnd);
		CollisionBarrierTestStart.y = etrap.collision_electrotrap_max_check;
		CollisionBarrierTestEnd.y = etrap.collision_electrotrap_max_check;
		shaper.line(CollisionBarrierTestStart, CollisionBarrierTestEnd);
	}

	public void DrawMinMaxCollisionBarriersHealth(ShapeRenderer shaper, float min, float max){
		shaper.setColor(0f, 0.3f, 1f, 1f);
		CollisionBarrierTestStart.x = COLLISION_BARRIER_XS;
		CollisionBarrierTestStart.y = min;
		CollisionBarrierTestEnd.x = COLLISION_BARRIER_XE;
		CollisionBarrierTestEnd.y = min;
		shaper.line(CollisionBarrierTestStart, CollisionBarrierTestEnd);
		CollisionBarrierTestStart.y = max;
		CollisionBarrierTestEnd.y = max;
		shaper.line(CollisionBarrierTestStart, CollisionBarrierTestEnd);
	}

	public void DrawMinMaxCollisionBarriersBullets(ShapeRenderer shaper, float min, float max){
		shaper.setColor(0f, 0.3f, 1f, 1f);
		CollisionBarrierTestStart.x = COLLISION_BARRIER_XS;
		CollisionBarrierTestStart.y = min;
		CollisionBarrierTestEnd.x = COLLISION_BARRIER_XE;
		CollisionBarrierTestEnd.y = min;
		shaper.line(CollisionBarrierTestStart, CollisionBarrierTestEnd);
		CollisionBarrierTestStart.y = max;
		CollisionBarrierTestEnd.y = max;
		shaper.line(CollisionBarrierTestStart, CollisionBarrierTestEnd);
	}

	public void DrawLightningBolts(ex01ElectrotrapRed etrap, ShapeRenderer shaper){
		shaper.setColor(etrap.R1, etrap.G1, etrap.B1, etrap.A1);
		for(int j = 0; j < etrap.lightning_bolt_prinicipal.size(); j++){
			seg = etrap.lightning_bolt_prinicipal.get(j);
			shaper.line(seg.start, seg.end);
		}
		shaper.setColor(etrap.R2, etrap.G2, etrap.B2, etrap.A2);
		for(int j = 0; j < etrap.lightning_bolt_prinicipal.size(); j++){
			seg = etrap.lightning_bolt_prinicipal.get(j);
			shaper.line(
					seg.start.x + 0.10f,
					seg.start.y + 0.10f,
					seg.end.x + 0.10f,
					seg.end.y + 0.10f);
		}
		shaper.setColor(etrap.R3, etrap.G3, etrap.B3, etrap.A3);
		for(int j = 0; j < etrap.lightning_bolt_prinicipal.size(); j++){
			seg = etrap.lightning_bolt_prinicipal.get(j);
			shaper.line(
					seg.start.x - 0.13f,
					seg.start.y - 0.13f,
					seg.end.x - 0.13f,
					seg.end.y - 0.13f);
		}
	}

	public void DrawMeshPolygonBaseRed(ex01ElectrotrapRed etrap, ShapeRenderer shaper){
		vertsB[0] = etrap.blocker.vertB[0];
		vertsB[1] = etrap.blocker.vertB[1];
		vertsB[2] = etrap.blocker.vertB[5];
		vertsB[3] = etrap.blocker.vertB[6];
		vertsB[4] = etrap.blocker.vertB[10];
		vertsB[5] = etrap.blocker.vertB[11];
		vertsB[6] = etrap.blocker.vertB[15];
		vertsB[7] = etrap.blocker.vertB[16];
		shaper.setColor(new Color(1.0f, 0.0f, 0.0f, 1f));
		shaper.polygon(vertsB, 0,8);
	}

	public void DrawMeshPolygonOverlayBlue(ex01ElectrotrapRed etrap, ShapeRenderer shaper){
		vertsBO[0] = etrap.blocker.vertBO[0];
		vertsBO[1] = etrap.blocker.vertBO[1];
		vertsBO[2] = etrap.blocker.vertBO[5];
		vertsBO[3] = etrap.blocker.vertBO[6];
		vertsBO[4] = etrap.blocker.vertBO[10];
		vertsBO[5] = etrap.blocker.vertBO[11];
		vertsBO[6] = etrap.blocker.vertBO[15];
		vertsBO[7] = etrap.blocker.vertBO[16];
		shaper.setColor(new Color(0.0f, 0.0f, 1.0f, 1f));
		shaper.polygon(vertsBO, 0,8);
	}

	public void DrawStartEndIntersectionLineYellow(ex01ElectrotrapRed etrap,
												   ShapeRenderer shaper){
		Vector2 starter = new Vector2(etrap.blocker.startv);
		Vector2 ender = new Vector2(etrap.blocker.endv);
		shaper.setColor(new Color(1.0f, 1.0f, 0.0f, 1f));
		shaper.line(starter, ender);
	}

	public void DrawBaseMeshUpDownIntersectionLineGreenLeft(ex01ElectrotrapRed etrap,
															ShapeRenderer shaper){
		shaper.setColor(new Color(0.0f, 1.0f, 0.0f, 1f));
		// the segment is for the left and is 5f in length
		shaper.line(
				new Vector2(etrap.blocker.vertB[5], etrap.blocker.vertB[6]-0f),
				new Vector2(etrap.blocker.vertB[5]-1000f, etrap.blocker.vertB[6]-0f));
		shaper.line(
				new Vector2(etrap.blocker.vertB[10], etrap.blocker.vertB[11]+0f),
				new Vector2(etrap.blocker.vertB[10]-1000f, etrap.blocker.vertB[11]+0f));
	}

	public void DrawBaseMeshUpDownIntersectionLineGreenRight(ex01ElectrotrapRed etrap,
															 ShapeRenderer shaper){
		shaper.setColor(new Color(0.0f, 1.0f, 0.0f, 1f));
		// the segment is for the right and is 5f in length
		shaper.line(
				new Vector2(etrap.blocker.vertB[0], etrap.blocker.vertB[1]-0f),
				new Vector2(etrap.blocker.vertB[0]+1000f, etrap.blocker.vertB[1]-0f));
		shaper.line(
				new Vector2(etrap.blocker.vertB[15], etrap.blocker.vertB[16]+0f),
				new Vector2(etrap.blocker.vertB[15]+1000f, etrap.blocker.vertB[16]+0f));
	}

	public void DrawOverlayMeshUpDownIntersectionLineOrangeLeft(ex01ElectrotrapRed etrap,
																ShapeRenderer shaper){
		shaper.setColor(new Color(1.0f, 0.549f, 0.0f, 1f));
		// the segment is for the left and is 5f in length
		shaper.line(
				new Vector2(etrap.blocker.vertBO[5], etrap.blocker.vertBO[6]-0f),
				new Vector2(etrap.blocker.vertBO[5]-1000f, etrap.blocker.vertBO[6]-0f));
		shaper.line(
				new Vector2(etrap.blocker.vertBO[10], etrap.blocker.vertBO[11]+0f),
				new Vector2(etrap.blocker.vertBO[10]-1000f, etrap.blocker.vertBO[11]+0f));
	}

	public void DrawOverlayMeshUpDownIntersectionLineOrangeRight(ex01ElectrotrapRed etrap,
																 ShapeRenderer shaper){
		shaper.setColor(new Color(1.0f, 0.549f, 0.0f, 1f));
		// the segment is for the right and is 5f in length
		shaper.line(
				new Vector2(etrap.blocker.vertBO[0], etrap.blocker.vertBO[1]-0f),
				new Vector2(etrap.blocker.vertBO[0]+1000f, etrap.blocker.vertBO[1]-0f));
		shaper.line(
				new Vector2(etrap.blocker.vertBO[15], etrap.blocker.vertBO[16]+0f),
				new Vector2(etrap.blocker.vertBO[15]+1000f, etrap.blocker.vertBO[16]+0f));
	}

	public void DrawSpaceshipCollisionRectsCirclesLasers(
			ShapeRenderer shaper,
			Circle spaces_rect,
			ArrayList<ex01CryoshipLaserShoot> laser_shoots){
		shaper.setColor(new Color(1.000f, 0.000f, 0.000f, 1f));
		shaper.circle(spaces_rect.x, spaces_rect.y, spaces_rect.radius,10);
		for(int i = 0; i < laser_shoots.size(); i++){
			shaper.rect(
					laser_shoots.get(i).collision_rect.x,
					laser_shoots.get(i).collision_rect.y,
					laser_shoots.get(i).collision_rect.width,
					laser_shoots.get(i).collision_rect.height);
		}
	}

	public void DrawPowerupsCollisionCircles(ShapeRenderer shaper,
											 ex01PowerupList world_powerup_list){
		for(int i = 0; i < world_powerup_list.list_health_sprites.size(); i++){
			shaper.circle(
					world_powerup_list.list_health_sprites.get(i).collision_circle.x,
					world_powerup_list.list_health_sprites.get(i).collision_circle.y,
					world_powerup_list.list_health_sprites.get(i).collision_circle.radius, 10);
		}
		for(int i = 0; i < world_powerup_list.list_bullets_sprites.size(); i++){
			shaper.circle(
					world_powerup_list.list_bullets_sprites.get(i).collision_circle.x,
					world_powerup_list.list_bullets_sprites.get(i).collision_circle.y,
					world_powerup_list.list_bullets_sprites.get(i).collision_circle.radius, 10);
		}
	}

	public void DrawEtrapAndBlockersCollision(ex01ElectrotrapRed etrap, ShapeRenderer shaper){
		//draw the collision detection circles and rectangles - used for testing only, will
		// be disabled on release
		shaper.setColor(new Color(1.000f, 0.000f, 0.000f, 1f));
		//blocker
		shaper.circle(
				etrap.blocker.left_circle_collision.x,
				etrap.blocker.left_circle_collision.y,
				etrap.blocker.left_circle_collision.radius,
				10);
		shaper.circle(
				etrap.blocker.right_circle_collision.x,
				etrap.blocker.right_circle_collision.y,
				etrap.blocker.right_circle_collision.radius,
				10);
		//etrap
		shaper.rect(
				etrap.left_collision_rectangle.x,
				etrap.left_collision_rectangle.y,
				etrap.left_collision_rectangle.width,
				etrap.left_collision_rectangle.height);
		shaper.rect(
				etrap.right_collision_rectangle.x,
				etrap.right_collision_rectangle.y,
				etrap.right_collision_rectangle.width,
				etrap.right_collision_rectangle.height);
		//bolt connections
		shaper.setColor(new Color(1.000f, 0.000f, 0.000f, 1f));
		shaper.rect(etrap.bolt_1_start.x, etrap.bolt_1_start.y, 0.4f, 0.4f);
		shaper.rect(etrap.bolt_1_end.x, etrap.bolt_1_end.y, 0.4f, 0.4f);
	}

	// maybe we'll work on this not sure what I'm doing here
	public void Dispose(){
		vertsB = null;
		vertsBO = null;
		if(electro_red_list!=null) {
			for (int i = 0; i < electro_red_list.size(); i++) {
				electro_red_list.get(i).blocker.Dispose();
				electro_red_list.get(i).Dispose();
			}
			electro_red_list.clear();
			electro_red_list = null;
		}
		if(electro_red_lightoff_list!=null){
			electro_red_lightoff_list.clear();
			electro_red_lightoff_list = null;
		}
		if(electro_red_lighton_list!=null){
			electro_red_lighton_list.clear();
			electro_red_lighton_list = null;
		}
		if(electro_red_lightoff_listR!=null){
			electro_red_lightoff_listR.clear();
			electro_red_lightoff_listR = null;
		}
		if(electro_red_lighton_listR!=null){
			electro_red_lighton_listR.clear();
			electro_red_lighton_listR = null;
		}

		electrotrap_tex = null;
		electrotrap_tex_lightoff = null;
		electrotrap_tex_lighton = null;
		blocker_tex = null;
		rand = null;

//		DON'T DELETE THIS COMMENT ***
//		if(texture_atlas_here!=null){
//			texture_atlas_here.dispose();
//			texture_atlas_here = null;
//		}
//		DON'T DELETE THIS COMMENT ***

		if(shader!=null) {
			shader.end();
			shader.dispose();
		}
		if(shader_base!=null) {
			shader_base.end();
			shader_base.dispose();
		}
		if(shaderGrey!=null) {
			shaderGrey.end();
			shaderGrey.dispose();
		}
		if(shaderSepia!=null) {
			shaderSepia.end();
			shaderSepia.dispose();
		}
		if(shaderSepiaPause!=null) {
			shaderSepiaPause.end();
			shaderSepiaPause.dispose();
		}

		if(etrap_list_off!=null){
			etrap_list_off.clear();
			etrap_list_off = null;
		}
		if(etrap_list_offR!=null){
			etrap_list_offR.clear();
			etrap_list_offR = null;
		}
		if(etrap_list_on!=null){
			etrap_list_on.clear();
			etrap_list_on = null;
		}
		if(etrap_list_onR!=null){
			etrap_list_onR.clear();
			etrap_list_onR = null;
		}
		if(etrap_list!=null) {
			for (int i = 0; i < etrap_list.size(); i++) {
				etrap_list.get(i).blocker.Dispose();
				etrap_list.get(i).Dispose();
			}
			etrap_list.clear();
			etrap_list = null;
		}

		attrib = null;
		etrap_drawing_filter = null;
		etrap_drawing_filter_pos = null;
		camera_hud = null;
		hud = null;
		game_screen = null;
		if(seg!=null) {
			seg.start = null;
			seg.end = null;
			seg = null;
		}
		etrap = null;
		curr_etrap = null;
		etrp = null;
		electrotrap_reset = null;
		prev_etrap = null;
		next_etrap = null;

		left_orig_v = null;
		left_blocker_orig = null;
		right_orig_v = null;
		right_blocker_orig = null;
		distV = null;
		start = null;
		end = null;
		CEB_startLine = null;
		CEB_endLine = null;
		CEB2_startLine = null;
		CEB2_endLine = null;
		CollisionBarrierTestStart = null;
		CollisionBarrierTestEnd = null;
		block_lcircle = null;
		block_rcircle = null;
	}
}
