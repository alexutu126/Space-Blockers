//WMS3 2015

package com.cryotrax.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

//----------------------------------------------------------------

public class ex01BlockerYellow extends Sprite{
	public static final float MOD_BARRIER_MESH_LEFT_RIGHT_FACTORL = 5000f;
	public static final float MOD_BARRIER_MESH_LEFT_RIGHT_FACTORR = 5000f;
	public static final float MOD_BARRIER_MESH_INTERSECTOR = 5000f;
	// the right hand sprite
	public Sprite right;
	// RoundRobin, Rotative, Bouncer
	public CRBlockertType type;
	public float rotation_sense;
	// world coordinates (based on relativeY from settings file)
	public float world_Y;
	// --||--
	public float world_X;
	// world coordinates for right blocker
	public float world_XR;
	// --||--
	public float world_YR;
	public float world_XO;
	public float world_YO;
	public float world_XOR;
	public float world_YOR;
	public float circle_leftX_origin;
	public float circle_leftY_origin;
	public float circle_rightX_origin;
	public float circle_rightY_origin;
	// world coordinates relative to the left electrotrap relative to the middle distance (R-L)/2
	public float relativeX;
	// --||--
	public float relativeY;
	// size of electrotrap in world units
	public float width;
	// size of electrotrap in world units
	public float height;
	public float delta_powerup_appear;
	// distance between the left and the right blocker when expanded ... ... ("blocker length
	// when expanded" in settings file)
	public float bl_length_exp;
	// "expansion speed" in world_data - not used yet
	public float exp_speed;
	// "robin radius" in world_data - describes the radius of system rotation
	public float robin_radius;
	// "trigger angle" in world_data - describes the max  to the ... ... vertical at which
	// the blockers can start expanding
	public float trigger_angle;
	// "roatation speed" in world_data - used for testing purposes
	public float rotation_speed;
	// speed at starting quadrant 1
	public float speedP1;
	// speed at starting quadrant 2
	public float speedP2;
	// speed at starting quadrant 3
	public float speedP3;
	// speed at starting quadrant 4
	public float speedP4;
	// "transX" on translation speed on X axis
	public float transX;
	// bouncer speed at top
	public float leftSpeed;
	// bouncer speed at middle
	public float middleSpeed;
	// bouncer speed at bottom
	public float rightSpeed;
	// bouncer speed at top
	public float leftSpeedSec;
	// bouncer speed at middle
	public float middleSpeedSec;
	// bouncer speed at bottom
	public float rightSpeedSec;
	// current speed of the blocker from left to right(negative means right to left)
	public float currentSpeed;
	// current speed of the blocker from left to right(negative means right to left)
	public float currentSpeedSec;
	// pivot coordinates relative to the bottom-left corner of the blocker
	public float rotationPivotX;
	// pivot coordinates relative to the bottom-left corner of the blocker
	public float rotationPivotY;
	// pivot coordinates relative to the bottom-left corner of the blocker
	public float rotationPivotXSec;
	// pivot coordinates relative to the bottom-left corner of the blocker
	public float rotationPivotYSec;
	public float OriginPivot1X;
	public float OriginPivot1Y;
	public float OriginPivot1XR;
	public float OriginPivot1YR;
	public float OriginPivot2X;
	public float OriginPivot2Y;
	public float OriginPivot2XR;
	public float OriginPivot2YR;
	public float rotationAccelQ1;		   // (speedP2 - speedP1) / 2 / timeforQuadrant
	public float rotationAccelQ2;		   // (speedP3 - speedP2) / 2 / timeforQuadrant
	public float rotationAccelQ3;		   // (speedP4 - speedP3) / 2 / timeforQuadrant
	public float rotationAccelQ4;		   // (speedP1 - speedP4) / 2 / timeforQuadrant
	public float rotationAccelQ1Sec;	   // (speedP2 - speedP1) / 2 / timeforQuadrant
	public float rotationAccelQ2Sec;	   // (speedP3 - speedP2) / 2 / timeforQuadrant
	public float rotationAccelQ3Sec;	   // (speedP4 - speedP3) / 2 / timeforQuadrant
	public float rotationAccelQ4Sec;	   // (speedP1 - speedP4) / 2 / timeforQuadrant
	public float translationAccelI1;       // intermediate accel1
	public float translationAccelI2;       // intermediate accel2
	public float translationAccelIB1;      // intermediate accel1
	public float translationAccelIB2;      // intermediate accel1	
	public float sprite_angle;   		   // used in RotateBlockerRR and RotateBlockerRO
	public float sprite_angleSec;   	   // used in RotateBlockerRR and RotateBlockerRO
	public float timeforQuadrant;	       // time to complete the quadrant (used for acceleration
										   // calculation)
	public float timeforQuadrantSec;	   // time to complete the quadrant (used for accel calc)
										   // used for RotativeBouncers and RobinBouncers
	public float deployDistance;           // how much the blocker should travel
	public float deployCounter;			   // count how much distance has passed
	public float deployDistanceSec;        // how much the blocker should travel
	public float deployCounterSec;		   // count how much distance has passed
	public float startLine_endLine_X_COUNTER = 0;
	public float relXpower = 0f;
	public float relYpower = 0f;
	public float robin_radiusSec;
	public float speedP1Sec;
	public float speedP2Sec;
	public float speedP3Sec;
	public float speedP4Sec;
	public float transXSec;
	public float still_forward_countSec;
	public float rotation_senseSec;
	public float rotation_speedSec;
	public float new_delta_left;
	public float new_delta_right;
	public boolean already_calculated_startendy_endstartx = false;
	// first expansion operation from cycle;
	public boolean can_initiate_expansion;
	// expansion is completed?
	public boolean expanded;
	public boolean expanded_and_lined;
	public boolean expanded_still_deploy_mesh_back_up;
	public boolean expanded_still_deploy_mesh_back_down;
	public boolean expanded_still_deploy_mesh_over_up;
	public boolean expanded_still_deploy_mesh_over_down;
	// can we start expansion? I.E. shot was on target
	public boolean start_expansion;
	// used with rotation_speed for testing purposes
	public boolean use_constant_rotation;
	// wtf is this? rotate based on no of rotations completed maybe
	public boolean still_forward;
	public boolean angle_small;
	public boolean recolorized = false;
	public boolean drawnangle = false;
	public short currentDir;     		   // current direction of blocker
	public short currentDirSec;     	   // current direction of blocker
	public short can_generate_powerups;
	public short stage_values_powerups;
	public short can_generate_powerupsSec;
	public short stage_values_powerupsSec;
	public float[] vert;         		   // vertices for the base mesh 
	public short[] indi;       			   // the indices {0,1,2,2,3,0}
	public float[] vertB;         		   // vertices for the base mesh 
	public short[] indiB;       		   // the indices {0,1,2,2,3,0}
	public float[] vertBO;         		   // vertices for the base mesh 
	public short[] indiBO;       		   // the indices {0,1,2,2,3,0}
	public float[] vertO;       		   // vertices for the laser line 
	public short[] indiO;		  		   // the indices {0,1,2,2,3,0}
	public float startendy;
	public float endstarty;
	public float startendx;
	public float endstartx;	
	public double angle = 90f;
	public int index = 0;       		   // array index for base mesh vertices
	public int indexi = 0;      		   // array index for base mesh indices
	public int indexB = 0;       		   // array index for base mesh vertices
	public int indexiB = 0;      		   // array index for base mesh indices
	public int indexBO = 0;       		   // array index for base mesh vertices
	public int indexiBO = 0;      		   // array index for base mesh indices	
	public int indexO = 0;      		   // array index for laser line vertices
	public int indexiO = 0;		  	       // array index for laser line indices
	// wtf is this? rotate based on no of rotations completed maybe
	public int still_forward_count;
	public Mesh mesh;         			   // base mesh
	public Mesh meshB;         			   // base mesh
	public Mesh meshBO;           	       // base mesh
	public Mesh meshO;	        		   // laser mesh
	public ShaderProgram shader;		   // shader program
	public ShaderProgram shader_base;      // shader base shader;
	public ShaderProgram shaderGrey;       // shader grey program;
	public ShaderProgram shaderSepia;
	public ShaderProgram shaderSepiaPause;
	// texture used for laser base mesh
	public TextureRegion textureAtlas_laser;
	// texture used for laser mesh
	public TextureRegion textureAtlas_laserOver;
	public TextureRegion texture_reg_barrier;
	public TextureRegion texture_reg_barrierO;
	// when expansion starts this will be the START vector for the laser
	public Vector2 startLine;
	// when expansion starts this will be the END vector for the laser
	public Vector2 endLine;
	// when expansion starts this will be the START vector for the laser
	public Vector2 startLineSec;
	// when expansion starts this will be the END vector for the laser
	public Vector2 endLineSec;
	public Vector2 startv = new Vector2();
	public Vector2 endv = new Vector2();
	public Vector2 startvO = new Vector2();
	public Vector2 endvO = new Vector2();
	public Vector2 startv1 = new Vector2();
	public Vector2 startv2 = new Vector2();
	public Vector2 startv3 = new Vector2();
	public Vector2 startv4 = new Vector2();
	public Vector2 endv1 = new Vector2();
	public Vector2 endv2 = new Vector2();
	public Vector2 endv3 = new Vector2();
	public Vector2 endv4 = new Vector2();
	public Vector2 aa = new Vector2();
	public Vector2 bb = new Vector2();
	public Vector2 direction0 = new Vector2();
	public Vector2 direction = new Vector2();
	public Vector2 up = new Vector2();
	public Vector2 down = new Vector2();
	public Vector2 up2 = new Vector2();
	public Vector2 down2 = new Vector2();	
	public Vector2 v1 = new Vector2();
	public Vector2 v2 = new Vector2();
	public Vector2 v3 = new Vector2();
	public Vector2 v4 = new Vector2();		
	public Vector2 Intersector5_6M0 = new Vector2();
	public Vector2 Intersector5M1000_6M0 = new Vector2();
	public Vector2 Intersector10_11P0 = new Vector2();
	public Vector2 Intersector10M1000_11P0 = new Vector2();
	public Vector2 OIntersector5_6M0 = new Vector2();
	public Vector2 OIntersector5M1000_6M0 = new Vector2();
	public Vector2 OIntersector10_11P0 = new Vector2();
	public Vector2 OIntersector10M1000_11P0 = new Vector2();	
	public Vector2 Intersector0_1M0 = new Vector2();
	public Vector2 Intersector0P1000_1M0 = new Vector2();
	public Vector2 Intersector15_16P0 = new Vector2();
	public Vector2 Intersector15P1000_16P0 = new Vector2();
	public Vector2 OIntersector0_1M0 = new Vector2();
	public Vector2 OIntersector0P1000_1M0 = new Vector2();
	public Vector2 OIntersector15_16P0 = new Vector2();
	public Vector2 OIntersector15P1000_16P0 = new Vector2();
	// the left vector with origin (0,0) - > we'll add the world origin after we rotate, when
	// we calc. collision
	public Vector2 left_circle_origin_circle;
	// --||--
	public Vector2 right_circle_origin_circle;
	// the left vector with origin (0,0) - > we'll add the world origin after we rotate, when
	// we calc. collision
	public Vector2 left_circle_origin_circleSec;
	// --||--
	public Vector2 right_circle_origin_circleSec;
	// the left circle used for collision detection  - ex01ElectrotrapRedList >
	// LoadElectrotrapBlocker
	public Circle left_circle_collision;
	// the right circle used for collision detection - ex01ElectrotrapRedList >
	// LoadElectrotrapBlocker
	public Circle right_circle_collision;
	// at this robin_radius circle resets are 256.98584f(left), 282.4238f(right)
	public static final float ROBIN_RADIUS_BASE = 3.5f; 
	public static final float CIRCLE_LEFT_RIGHT_BASE = 270f;
	public static final float CIRCLE_LEFT_RADIUS_BASE = 256.98584f;
	public static final float CIRCLE_RIGHT_RADIUS_BASE = 282.4238f;
	public static final float CIRCLE_LEFT_RADIUS_BASE_DELTA =
			CIRCLE_LEFT_RIGHT_BASE - CIRCLE_LEFT_RADIUS_BASE;
	public static final float CIRCLE_RIGHT_RADIUS_BASE_DELTA =
			CIRCLE_RIGHT_RADIUS_BASE - CIRCLE_LEFT_RIGHT_BASE;
	public static final float CIRCLE_CORRECTION_LEFT =
			CIRCLE_LEFT_RADIUS_BASE_DELTA * ROBIN_RADIUS_BASE;
	public static final float CIRCLE_CORRECTION_RIGHT =
			CIRCLE_RIGHT_RADIUS_BASE_DELTA * ROBIN_RADIUS_BASE;

	// CIRCLE_LEFT_RADIUS_BASE_DELTA at 3.5f radius ROBIN_RADIUS_BASE
	// another_delta                 at x.yf radius 
	// ... so -> another_delta_left =  CIRCLE_LEFT_RADIUS_BASE_DELTA * 1/robin_radius /
	// 		1/ROBIN_RADIUS_BASE = CIRCLE_LEFT_RADIUS_BASE_DELTA*ROBIN_RADIUS_BASE / robin_radius
	// ... so -> another_delta_left =  CIRCLE_RIGHT_RADIUS_BASE_DELTA * 1/robin_radius /
	// 		1/ROBIN_RADIUS_BASE = CIRCLE_RIGHT_RADIUS_BASE_DELTA*ROBIN_RADIUS_BASE / robin_radius
	// new_delta_left = CIRCLE_LEFT_RIGHT_BASE - another_delta_left;
	// new_delta_right = CIRCLE_LEFT_RIGHT_BASE - another_delta_right;

	public void ResetCircles(){		
		left_circle_origin_circle.setAngle(new_delta_left);
		right_circle_origin_circle.setAngle(new_delta_right);
	}
	
	public ex01BlockerYellow(TextureAtlas atlas, TextureRegion tex){
		super(tex);		
		right = new Sprite(tex);           // the right hand sprite
		expanded = false;                  // expansion is completed?
		expanded_and_lined = false;
		start_expansion = false;           // can we start expansion? I.E. shot was on target
		can_initiate_expansion = false;    // can we trigger the first step?
		expanded_still_deploy_mesh_back_up = true;
		expanded_still_deploy_mesh_back_down = true;
		expanded_still_deploy_mesh_over_up = true;
		expanded_still_deploy_mesh_over_down = true;
		use_constant_rotation = false;     // used with rotation_speed for testing purposes
		// used in RotateBlockerRR and RotateBlockerRO in order to correctly account ...
		// ... for the angle of the sprite
		sprite_angle = 0f;
		// used in RotateBlockerRR and RotateBlockerRO in order to correctly account ...
		// ... for the angle of the sprite
		sprite_angleSec = 0f;
		// wtf is this? rotate based on no of rotations completed maybe
		still_forward = true;
		// wtf is this? rotate based on no of rotations completed maybe
		still_forward_count = 0;
		// 4 vertices (x,y,(r,g,b,a),u,v) x>1,y>2,(r,g,b,a)>3,u>4,v>5
		vert = new float[4 * 1 * 5];
		// 4 vertices (x,y,(r,g,b,a),u,v) x>1,y>2,(r,g,b,a)>3,u>4,v>5
		indi = new short[6 * 1];
		// 4 vertices (x,y,(r,g,b,a),u,v) x>1,y>2,(r,g,b,a)>3,u>4,v>5
		vertO = new float[4 * 1 * 5];
		// 4 vertices (x,y,(r,g,b,a),u,v) x>1,y>2,(r,g,b,a)>3,u>4,v>5
		indiO = new short[6 * 1];
		// 4 vertices (x,y,(r,g,b,a),u,v) x>1,y>2,(r,g,b,a)>3,u>4,v>5
		vertB = new float[4 * 1 * 5];
		// 4 vertices (x,y,(r,g,b,a),u,v) x>1,y>2,(r,g,b,a)>3,u>4,v>5
		indiB = new short[6 * 1];
		// 4 vertices (x,y,(r,g,b,a),u,v) x>1,y>2,(r,g,b,a)>3,u>4,v>5
		vertBO = new float[4 * 1 * 5];
		// 4 vertices (x,y,(r,g,b,a),u,v) x>1,y>2,(r,g,b,a)>3,u>4,v>5
		indiBO = new short[6 * 1];
		
		mesh = new Mesh(true, 4, 6, 
                new VertexAttribute(Usage.Position, 2, "a_position"),
                new VertexAttribute(Usage.ColorPacked, 4, "a_color"),
                new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
		
		meshB = new Mesh(true, 4, 6, 
                new VertexAttribute(Usage.Position, 2, "a_position"),
                new VertexAttribute(Usage.ColorPacked, 4, "a_color"),
                new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
		
		meshBO = new Mesh(true, 4, 6, 
                new VertexAttribute(Usage.Position, 2, "a_position"),
                new VertexAttribute(Usage.ColorPacked, 4, "a_color"),
                new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));		
		
		meshO = new Mesh(true, 4, 6, 
                new VertexAttribute(Usage.Position, 2, "a_position"),
                new VertexAttribute(Usage.ColorPacked, 4, "a_color"),
                new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));				
		
		//texture used for laser base mesh  
		textureAtlas_laser = atlas.findRegion("laser_base");
		// texture used for laser mesh 
		textureAtlas_laserOver = atlas.findRegion("laser_overlay");
		// texture for the barrier
		texture_reg_barrier = atlas.findRegion("lbolt_barrier");
		texture_reg_barrierO = atlas.findRegion("laser_overlay_barrier");
		//create and link the shader programs computers view matrix and textures
	}
	
	public void ResetBlocker(){
		drawnangle = false;
		expanded = false;                  // expansion is completed?
		expanded_and_lined = false;
		start_expansion = false;           // can we start expansion? I.E. shot was on target
		can_initiate_expansion = false;    // can we trigger the first step?
		expanded_still_deploy_mesh_back_up = true;
		expanded_still_deploy_mesh_back_down = true;
		expanded_still_deploy_mesh_over_up = true;
		expanded_still_deploy_mesh_over_down = true;
		// used with rotation_speed for testing purposes
		use_constant_rotation = false;
		// used in RotateBlockerRR and RotateBlockerRO in order to correctly account ...
		// ... for the angle of the sprite
		sprite_angle = 0f;
		// used in RotateBlockerRR and RotateBlockerRO in order to correctly account ...
		// ... for the angle of the sprite
		sprite_angleSec = 0f;
		// wtf is this? rotate based on no of rotations completed maybe
		still_forward = true;
		angle = 90f;
		// wtf is this? rotate based on no of rotations completed maybe
		still_forward_count = 0;
		recolorized = false;
		angle_small = false;

		switch(type){
		case Rotative:
			{
				sprite_angle = 0f;
				rotation_speed = speedP1;
				setRotation(0f);
				right.setRotation(0f);		
				left_circle_origin_circle.setAngle(180.28316f);  
				right_circle_origin_circle.setAngle(359.70294f); 
			}
			break;
		case RoundRobin:
			{
				sprite_angle = 0f;
				rotation_speed = speedP1;
				setRotation(0f);
				right.setRotation(0f);
				startLine.setAngle(270f);
				left_circle_origin_circle.setAngle(256.98584f); 
				right_circle_origin_circle.setAngle(282.4238f);			
			}
			break;
		case Bouncer:
			{
				//nothing needs to be done ?
			}
			break;
		default:
			break;
		}
	}
	
	public void ModifyBarrierMeshFromLineLeft(Vector2 start, Vector2 end){
		if(!expanded_and_lined){
			if(!already_calculated_startendy_endstartx) {
				startendy = (start.y - end.y) * MOD_BARRIER_MESH_LEFT_RIGHT_FACTORL;
				endstarty = (end.y - start.y) * MOD_BARRIER_MESH_LEFT_RIGHT_FACTORL;
				startendx = (start.x - end.x) * MOD_BARRIER_MESH_LEFT_RIGHT_FACTORL;
				endstartx = (end.x - start.x) * MOD_BARRIER_MESH_LEFT_RIGHT_FACTORL;
				already_calculated_startendy_endstartx = true;
			}
			if(start.y > end.y){
				start.y += startendy;
				end.y -= startendy;
					if(start.x > end.x){
						start.x += startendx;
						end.x -= startendx;
					} else if(start.x < end.x){
						start.x -= endstartx;
						end.x += endstartx;
					}
			} else if(start.y < end.y){
				end.y += endstarty;
				start.y -= endstarty;
					if(start.x > end.x){
						start.x += startendx;
						end.x -= startendx;
					} else if(start.x < end.x){
						start.x -= endstartx;
						end.x += endstartx;
					}
			}
			startv.x = start.x;
			startv.y = start.y;
			endv.x = end.x;
			endv.y = end.y;
		}
	}
	
	public void ModifyBarrierMeshFromLineLeftO(Vector2 start, Vector2 end){
		if(!expanded_and_lined){
			if(!already_calculated_startendy_endstartx) {
				startendy = (start.y - end.y) * MOD_BARRIER_MESH_LEFT_RIGHT_FACTORL;
				endstarty = (end.y - start.y) * MOD_BARRIER_MESH_LEFT_RIGHT_FACTORL;
				startendx = (start.x - end.x) * MOD_BARRIER_MESH_LEFT_RIGHT_FACTORL;
				endstartx = (end.x - start.x) * MOD_BARRIER_MESH_LEFT_RIGHT_FACTORL;
				already_calculated_startendy_endstartx = true;
			}
			if(start.y > end.y){
				start.y += startendy;
				end.y -= startendy;
					if(start.x > end.x){
						start.x += startendx;
						end.x -= startendx;
					} else if(start.x < end.x){
						start.x -= endstartx;
						end.x += endstartx;
					}
			} else if(start.y < end.y){
				end.y += endstarty;
				start.y -= endstarty;
					if(start.x > end.x){
						start.x += startendx;
						end.x -= startendx;
					} else if(start.x < end.x){
						start.x -= endstartx;
						end.x += endstartx;
					}
			}
			startvO.x = start.x;
			startvO.y = start.y;
			endvO.x = end.x;
			endvO.y = end.y;
		}
	}	

	public void ModifyBarrierMeshFromLineLeftDetach(float x_up, float x_down){		
		startv1.x = startv.x;
		startv1.y = startv.y;
		startv2.x = startv.x;
		startv2.y = startv.y;
		startv3.x = startvO.x;
		startv3.y = startvO.y;
		startv4.x = startvO.x;
		startv4.y = startvO.y;
		endv1.x = endv.x;
		endv1.y = endv.y;
		endv2.x = endv.x;
		endv2.y = endv.y;
		endv3.x = endvO.x;
		endv3.y = endvO.y;			
		endv4.x = endvO.x;
		endv4.y = endvO.y;
		
		if(expanded_still_deploy_mesh_back_up){
			vertB[5] += x_up;
			if(vertB[0]<vertB[5]){
				vertB[0] = vertB[5];
			}
		}
		
		if(expanded_still_deploy_mesh_back_down){
			vertB[10] += x_down;
			if(vertB[15]<vertB[10]){
				vertB[15] = vertB[10];
			}
		}
		
		if(expanded_still_deploy_mesh_over_up){
			vertBO[5] += x_up;
			if(vertBO[0]<vertBO[5]){
				vertBO[0] = vertBO[5];
			}
		}
		
		if(expanded_still_deploy_mesh_over_down){
			vertBO[10] += x_down;
			if(vertBO[15]<vertBO[10]){
				vertBO[15] = vertBO[10];
			}
		}			
		
		Intersector5_6M0.x = vertB[5];
		Intersector5_6M0.y = vertB[6]-0f;
		Intersector5M1000_6M0.x = vertB[5]-MOD_BARRIER_MESH_INTERSECTOR;
		Intersector5M1000_6M0.y = vertB[6]-0f;
		Intersector10_11P0.x = vertB[10];
		Intersector10_11P0.y = vertB[11]+0f;
		Intersector10M1000_11P0.x = vertB[10]-MOD_BARRIER_MESH_INTERSECTOR;
		Intersector10M1000_11P0.y = vertB[11]+0f;		
		OIntersector5_6M0.x = vertBO[5];
		OIntersector5_6M0.y = vertBO[6]-0f;
		OIntersector5M1000_6M0.x = vertBO[5]-MOD_BARRIER_MESH_INTERSECTOR;
		OIntersector5M1000_6M0.y = vertBO[6]-0f;
		OIntersector10_11P0.x = vertBO[10];
		OIntersector10_11P0.y = vertBO[11]+0f;
		OIntersector10M1000_11P0.x = vertBO[10]-MOD_BARRIER_MESH_INTERSECTOR;
		OIntersector10M1000_11P0.y = vertBO[11]+0f;

		if(expanded_still_deploy_mesh_back_up && Intersector
				.intersectSegments(startv1, endv1, Intersector5_6M0,
						Intersector5M1000_6M0, v1)){
			expanded_still_deploy_mesh_back_up = false;
			vertB[5] = v1.x;
		}
		
		if(expanded_still_deploy_mesh_back_down && Intersector
				.intersectSegments(startv2, endv2, Intersector10_11P0,
						Intersector10M1000_11P0, v2)) {
			expanded_still_deploy_mesh_back_down = false;
			vertB[10] = v2.x;
		}
		
		if(expanded_still_deploy_mesh_over_up && Intersector
				.intersectSegments(startv3, endv3, OIntersector5_6M0,
						OIntersector5M1000_6M0, v3)) {
			expanded_still_deploy_mesh_over_up = false;
			vertBO[5] = v3.x;
		}
		
		if(expanded_still_deploy_mesh_over_down && Intersector
				.intersectSegments(startv4, endv4, OIntersector10_11P0,
						OIntersector10M1000_11P0, v4)){
			expanded_still_deploy_mesh_over_down = false;
			vertBO[10] = v4.x;
		}	
	}
	
	public void Colorize(int r1, int g1, int b1, int a1, int r2, int g2, int b2, int a2){	
		vertB[2] = Color.toFloatBits(r1, g1, b1, a1);     
		vertB[7] = Color.toFloatBits(r1, g1, b1, a1);       
		vertB[12] = Color.toFloatBits(r1, g1, b1, a1);  
		vertB[17] = Color.toFloatBits(r1, g1, b1, a1);
		vertBO[2] = Color.toFloatBits(r2, g2, b2, a2);     
		vertBO[7] = Color.toFloatBits(r2, g2, b2, a2);       
		vertBO[12] = Color.toFloatBits(r2, g2, b2, a2);  
		vertBO[17] = Color.toFloatBits(r2, g2, b2, a2);	
		recolorized = true;
	}
	
	public void ModifyBarrierMeshFromLineRightDetach(float x_up, float x_down){	
		startv1.x = startv.x;
		startv1.y = startv.y;
		startv2.x = startv.x;
		startv2.y = startv.y;
		startv3.x = startvO.x;
		startv3.y = startvO.y;
		startv4.x = startvO.x;
		startv4.y = startvO.y;
		endv1.x = endv.x;
		endv1.y = endv.y;
		endv2.x = endv.x;
		endv2.y = endv.y;
		endv3.x = endvO.x;
		endv3.y = endvO.y;			
		endv4.x = endvO.x;
		endv4.y = endvO.y;		
	
		if(expanded_still_deploy_mesh_back_up){
			vertB[0] += x_up;
			if(vertB[0]<vertB[5]){
				vertB[5] = vertB[0];
			}
		}
		
		if(expanded_still_deploy_mesh_back_down){
			vertB[15] += x_down;
			if(vertB[15]<vertB[10]){
				vertB[10] = vertB[15];
			}
		}
		
		if(expanded_still_deploy_mesh_over_up){
			vertBO[0] += x_up;
			if(vertBO[0]<vertBO[5]){
				vertBO[5] = vertBO[0];
			}
		}
		
		if(expanded_still_deploy_mesh_over_down){
			vertBO[15] += x_down;
			if(vertBO[15]<vertBO[10]){
				vertBO[10] = vertBO[15];
			}
		}
		
		Intersector0_1M0.x = vertB[0];
		Intersector0_1M0.y = vertB[1]-0f;
		Intersector0P1000_1M0.x = vertB[0]+MOD_BARRIER_MESH_INTERSECTOR;
		Intersector0P1000_1M0.y = vertB[1]-0f;
		Intersector15_16P0.x = vertB[15];
		Intersector15_16P0.y = vertB[16]+0f;
		Intersector15P1000_16P0.x = vertB[15]+MOD_BARRIER_MESH_INTERSECTOR;
		Intersector15P1000_16P0.y = vertB[16]+0f;
		OIntersector0_1M0.x = vertBO[0];
		OIntersector0_1M0.y = vertBO[1]-0f;
		OIntersector0P1000_1M0.x = vertBO[0]+MOD_BARRIER_MESH_INTERSECTOR;
		OIntersector0P1000_1M0.y = vertBO[1]-0f;
		OIntersector15_16P0.x = vertBO[15];
		OIntersector15_16P0.y = vertBO[16]+0f;
		OIntersector15P1000_16P0.x = vertBO[15]+MOD_BARRIER_MESH_INTERSECTOR;
		OIntersector15P1000_16P0.y = vertBO[16]+0f;
		 
		if(expanded_still_deploy_mesh_back_up && Intersector
				.intersectSegments(startv1, endv1, Intersector0_1M0,
						Intersector0P1000_1M0, v1)){
			expanded_still_deploy_mesh_back_up = false;
			vertB[0] = v1.x;
		}
		
		if(expanded_still_deploy_mesh_back_down && Intersector
				.intersectSegments(startv2, endv2, Intersector15_16P0,
						Intersector15P1000_16P0, v2)) {
			expanded_still_deploy_mesh_back_down = false;
			vertB[15] = v2.x;
		}
		
		if(expanded_still_deploy_mesh_over_up && Intersector
				.intersectSegments(startv3, endv3, OIntersector0_1M0,
						OIntersector0P1000_1M0, v3)) {
			expanded_still_deploy_mesh_over_up = false;
			vertBO[0] = v3.x;
		}
		
		if(expanded_still_deploy_mesh_over_down && Intersector
				.intersectSegments(startv4, endv4, OIntersector15_16P0,
						OIntersector15P1000_16P0, v4)) {
			expanded_still_deploy_mesh_over_down = false;
			vertBO[15] = v4.x;
		}	
	}

	public void ModifyBarrierMeshFromLineRight(Vector2 start, Vector2 end){
		if(!expanded_and_lined){
			if(!already_calculated_startendy_endstartx) {
				startendy = (start.y - end.y) * MOD_BARRIER_MESH_LEFT_RIGHT_FACTORR;
				endstarty = (end.y - start.y) * MOD_BARRIER_MESH_LEFT_RIGHT_FACTORR;
				startendx = (start.x - end.x) * MOD_BARRIER_MESH_LEFT_RIGHT_FACTORR;
				endstartx = (end.x - start.x) * MOD_BARRIER_MESH_LEFT_RIGHT_FACTORR;
				already_calculated_startendy_endstartx = true;
			}
			if(start.y > end.y){
				start.y += startendy;
				end.y -= startendy;
				if(start.x > end.x){
					start.x += startendx;
					end.x -= startendx;
				} else if(start.x < end.x){
					start.x -= endstartx;
					end.x += endstartx;
				}
			} else if(start.y < end.y){
				end.y += endstarty;
				start.y -= endstarty;
				if(start.x > end.x){
					start.x += startendx;
					end.x -= startendx;
				} else if(start.x < end.x){
					start.x -= endstartx;
					end.x += endstartx;
				}
			}
			startv.x = start.x;
			startv.y = start.y;
			endv.x = end.x;
			endv.y = end.y;
		}
	}
	
	public void ModifyBarrierMeshFromLineRightO(Vector2 start, Vector2 end){
		if(!expanded_and_lined){
			if(!already_calculated_startendy_endstartx) {
				startendy = (start.y - end.y) * MOD_BARRIER_MESH_LEFT_RIGHT_FACTORR;
				endstarty = (end.y - start.y) * MOD_BARRIER_MESH_LEFT_RIGHT_FACTORR;
				startendx = (start.x - end.x) * MOD_BARRIER_MESH_LEFT_RIGHT_FACTORR;
				endstartx = (end.x - start.x) * MOD_BARRIER_MESH_LEFT_RIGHT_FACTORR;
				already_calculated_startendy_endstartx = true;
			}
			if(start.y > end.y){
				start.y += startendy;
				end.y -= startendy;
				if(start.x > end.x){
					start.x += startendx;
					end.x -= startendx;
				} else if(start.x < end.x){
					start.x -= endstartx;
					end.x += endstartx;
				}
			} else if(start.y < end.y){
				end.y += endstarty;
				start.y -= endstarty;
				if(start.x > end.x){
					start.x += startendx;
					end.x -= startendx;
				} else if(start.x < end.x){
					start.x -= endstartx;
					end.x += endstartx;
				}
			}
			startvO.x = start.x;
			startvO.y = start.y;
			endvO.x = end.x;
			endvO.y = end.y;
		}
	}	
	
	// create mesh for base of the laser
	public void GetBarrierMeshFromLine(Vector2 a,
									   Vector2 b,
									   int rc,
									   int gc,
									   int bc,
									   int alpha){
		indexB = 0;     					  		// array index for base mesh vertices
		indexiB = 0;	  				      		// array index for base mesh indices	   	
		aa.x = a.x;          						// create a brand new copy of a
		aa.y = a.y;          						// create a brand new copy of a		
		bb.x = b.x;          						// create a brand new copy of b
		bb.y = b.y;          						// create a brand new copy of b		
		direction0 = bb.sub(aa);      				// get the direction from aa to bb
		direction.x = direction0.x;
		direction.y = direction0.y;
		direction.nor().scl(1f);
		up.x = direction.x;
		up.y = direction.y;
		up.rotate(90f).scl(4.2f).sub(0.025f,0.025f);// rotate up
		down.x = direction.x;
		down.y = direction.y;
		down.rotate(-90f).scl(4.2f).sub(0.025f,0.025f);           
		up2.x = up.x;
		up2.y = up.y;		
		up2.add(direction0).add(0.025f,0.025f);     // translate the left top
		down2.x = down.x;
		down2.y = down.y;
		down2.add(direction0).add(0.025f,0.025f);   // translate the left bottom	
		// we add a because all vectors are based on (0,0) 
		vertB[indexB++] = up2.add(a).x; 
		vertB[indexB++] = up2.y+0.12f;		
		vertB[indexB++] = Color.toFloatBits(rc, gc, bc, alpha);
		vertB[indexB++] = textureAtlas_laser.getU();
		vertB[indexB++] = textureAtlas_laser.getV();
		vertB[indexB++] = up.add(a).x;
		vertB[indexB++] = up.y+0.12f;        
		vertB[indexB++] = Color.toFloatBits(rc, gc, bc, alpha);
		vertB[indexB++] = textureAtlas_laser.getU();
		vertB[indexB++] = textureAtlas_laser.getV2();	
		vertB[indexB++] = down.add(a).x; 		
		vertB[indexB++] = down.y+0.12f;        
		vertB[indexB++] = Color.toFloatBits(rc, gc, bc, alpha);
		vertB[indexB++] = textureAtlas_laser.getU2();
		vertB[indexB++] = textureAtlas_laser.getV2();
		vertB[indexB++] = down2.add(a).x;        
		vertB[indexB++] = down2.y+0.12f;       
		vertB[indexB++] = Color.toFloatBits(rc, gc, bc, alpha);
		vertB[indexB++] = textureAtlas_laser.getU2();
		vertB[indexB++] = textureAtlas_laser.getV();	
		indiB[indexiB++] = (short) ((0) + 0);
		indiB[indexiB++] = (short) ((0) + 1);
		indiB[indexiB++] = (short) ((0) + 2);
		indiB[indexiB++] = (short) ((0) + 2);
		indiB[indexiB++] = (short) ((0) + 3);
		indiB[indexiB++] = (short) ((0) + 0);	
	}		
	
	// create mesh for base of the laser
	public void GetBarrierOMeshFromLine(Vector2 a,
										Vector2 b,
										int rc,
										int gc,
										int bc,
										int alpha){
		indexBO = 0;     					  		// array index for base mesh vertices
		indexiBO = 0;	  				      		// array index for base mesh indices	   		
		aa.x = a.x;          						// create a brand new copy of a
		aa.y = a.y;          						// create a brand new copy of a		
		bb.x = b.x;          						// create a brand new copy of b
		bb.y = b.y;          						// create a brand new copy of b		
		direction0 = bb.sub(aa);      				// get the direction from aa to bb
		direction.x = direction0.x;
		direction.y = direction0.y;
		direction.nor().scl(1f);
		up.x = direction.x;
		up.y = direction.y;
		up.rotate(90f).scl(1.6f).sub(0.025f,0.025f);// rotate up
		down.x = direction.x;
		down.y = direction.y;
		down.rotate(-90f).scl(1.6f).sub(0.025f,0.025f);           
		up2.x = up.x;
		up2.y = up.y;		
		up2.add(direction0).add(0.025f,0.025f);     // translate the left top
		down2.x = down.x;
		down2.y = down.y;
		down2.add(direction0).add(0.025f,0.025f);   // translate the left bottom			
		// we add a because all vectors are based on (0,0) 
		vertBO[indexBO++] = up2.add(a).x; 
		vertBO[indexBO++] = up2.y+0.12f;		
		vertBO[indexBO++] = Color.toFloatBits(rc, gc, bc, alpha);
		vertBO[indexBO++] = texture_reg_barrierO.getU();
		vertBO[indexBO++] = texture_reg_barrierO.getV();
		vertBO[indexBO++] = up.add(a).x;
		vertBO[indexBO++] = up.y+0.12f;        
		vertBO[indexBO++] = Color.toFloatBits(rc, gc, bc, alpha);
		vertBO[indexBO++] = texture_reg_barrierO.getU();
		vertBO[indexBO++] = texture_reg_barrierO.getV2();	
		vertBO[indexBO++] = down.add(a).x; 		
		vertBO[indexBO++] = down.y+0.12f;        
		vertBO[indexBO++] = Color.toFloatBits(rc, gc, bc, alpha);
		vertBO[indexBO++] = texture_reg_barrierO.getU2();
		vertBO[indexBO++] = texture_reg_barrierO.getV2();
		vertBO[indexBO++] = down2.add(a).x;        
		vertBO[indexBO++] = down2.y+0.12f;       
		vertBO[indexBO++] = Color.toFloatBits(rc, gc, bc, alpha);
		vertBO[indexBO++] = texture_reg_barrierO.getU2();
		vertBO[indexBO++] = texture_reg_barrierO.getV();	
		indiBO[indexiBO++] = (short) ((0) + 0);
		indiBO[indexiBO++] = (short) ((0) + 1);
		indiBO[indexiBO++] = (short) ((0) + 2);
		indiBO[indexiBO++] = (short) ((0) + 2);
		indiBO[indexiBO++] = (short) ((0) + 3);
		indiBO[indexiBO++] = (short) ((0) + 0);	
	}			
	
	// create mesh for base of the laser
	public void GetMeshFromLine(Vector2 a,
								Vector2 b,
								int rc,
								int gc,
								int bc,
								int alpha){
		index = 0;     						  		// array index for base mesh vertices
		indexi = 0;	  						  		// array index for base mesh indices	   	
		aa.x = a.x;          						// create a brand new copy of a
		aa.y = a.y;          						// create a brand new copy of a		
		bb.x = b.x;          						// create a brand new copy of b
		bb.y = b.y;          						// create a brand new copy of b		
		direction0 = bb.sub(aa);      				// get the direction from aa to bb
		direction.x = direction0.x;
		direction.y = direction0.y;
		direction.nor().scl(1f);
		up.x = direction.x;
		up.y = direction.y;
		up.rotate(90f).scl(1.6f).sub(0.025f,0.025f);// rotate up
		down.x = direction.x;
		down.y = direction.y;
		down.rotate(-90f).scl(1.6f).sub(0.025f,0.025f);           
		up2.x = up.x;
		up2.y = up.y;		
		up2.add(direction0).add(0.025f,0.025f);     // translate the left top
		down2.x = down.x;
		down2.y = down.y;
		down2.add(direction0).add(0.025f,0.025f);   // translate the left bottom			
		// we add a because all vectors are based on (0,0) 
		vert[index++] = up2.add(a).x; 
		vert[index++] = up2.y;		
		vert[index++] = Color.toFloatBits(rc, gc, bc, alpha);
		vert[index++] = textureAtlas_laser.getU();
		vert[index++] = textureAtlas_laser.getV();
		vert[index++] = up.add(a).x;
		vert[index++] = up.y;        
		vert[index++] = Color.toFloatBits(rc, gc, bc, alpha);
		vert[index++] = textureAtlas_laser.getU();
		vert[index++] = textureAtlas_laser.getV2();	
		vert[index++] = down.add(a).x; 		
		vert[index++] = down.y;        
		vert[index++] = Color.toFloatBits(rc, gc, bc, alpha);
		vert[index++] = textureAtlas_laser.getU2();
		vert[index++] = textureAtlas_laser.getV2();
		vert[index++] = down2.add(a).x;        
		vert[index++] = down2.y;       
		vert[index++] = Color.toFloatBits(rc, gc, bc, alpha);
		vert[index++] = textureAtlas_laser.getU2();
		vert[index++] = textureAtlas_laser.getV();	
		indi[indexi++] = (short) ((0) + 0);
		indi[indexi++] = (short) ((0) + 1);
		indi[indexi++] = (short) ((0) + 2);
		indi[indexi++] = (short) ((0) + 2);
		indi[indexi++] = (short) ((0) + 3);
		indi[indexi++] = (short) ((0) + 0);	
	}		
	
	// create mesh for laser line
	public void GetMeshFromLineO(Vector2 a,
								 Vector2 b,
								 int rc,
								 int gc,
								 int bc,
								 int alpha){
		indexO = 0;  		 						// array index for laser line vertices
		indexiO = 0;		 						// array index for laser line indices 			
		aa.x = a.x;          						// create a brand new copy of a
		aa.y = a.y;          						// create a brand new copy of a		
		bb.x = b.x;          						// create a brand new copy of b
		bb.y = b.y;          						// create a brand new copy of b		
		direction0 = bb.sub(aa);      				// get the direction from aa to bb
		direction.x = direction0.x;
		direction.y = direction0.y;
		direction.nor();
		up.x = direction.x;
		up.y = direction.y;
		up.rotate(90f).scl(1.50f);                  // rotate up
		down.x = direction.x;
		down.y = direction.y;
		down.rotate(-90f).scl(1.50f);               // rotate down
		up2.x = up.x;
		up2.y = up.y;		
		up2.add(direction0);                        // translate the left top
		down2.x = down.x;
		down2.y = down.y;
		down2.add(direction0);	                    // translate the left bottom	
		// we add a because all vectors are based on (0,0) 
		vertO[indexO++] = up2.add(a).x; 
		vertO[indexO++] = up2.y;		  
		vertO[indexO++] = Color.toFloatBits(rc, gc, bc, alpha);
		vertO[indexO++] = textureAtlas_laserOver.getU();
		vertO[indexO++] = textureAtlas_laserOver.getV();
		vertO[indexO++] = up.add(a).x; 
		vertO[indexO++] = up.y;      
		vertO[indexO++] = Color.toFloatBits(rc, gc, bc, alpha);
		vertO[indexO++] = textureAtlas_laserOver.getU();
		vertO[indexO++] = textureAtlas_laserOver.getV2();
		vertO[indexO++] = down.add(a).x; 		  
		vertO[indexO++] = down.y;         
		vertO[indexO++] = Color.toFloatBits(rc, gc, bc, alpha);
		vertO[indexO++] = textureAtlas_laserOver.getU2();
		vertO[indexO++] = textureAtlas_laserOver.getV2();
		vertO[indexO++] = down2.add(a).x;    
		vertO[indexO++] = down2.y;       
		vertO[indexO++] = Color.toFloatBits(rc, gc, bc, alpha);
		vertO[indexO++] = textureAtlas_laserOver.getU2();
		vertO[indexO++] = textureAtlas_laserOver.getV();
		indiO[indexiO++] = (short) ((0) + 0);
		indiO[indexiO++] = (short) ((0) + 1);
		indiO[indexiO++] = (short) ((0) + 2);
		indiO[indexiO++] = (short) ((0) + 2);
		indiO[indexiO++] = (short) ((0) + 3);
		indiO[indexiO++] = (short) ((0) + 0);
	}		
	
	public void Dispose(){
		right = null;
		type = null;
		
		vert = null;         	
		indi = null;       		
		vertB = null;         		  
		indiB = null;       			
		vertBO = null;         		
		indiBO = null;       		
		vertO = null;       		
		indiO = null;

		if(mesh!=null)mesh.dispose(); mesh = null;
		if(meshB!=null)meshB.dispose(); meshB = null;
		if(meshBO!=null)meshBO.dispose(); meshBO = null;
		if(meshO!=null)meshO.dispose(); meshO = null;

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
		
		textureAtlas_laser = null;     
		textureAtlas_laserOver = null; 
		texture_reg_barrier = null;
		texture_reg_barrierO = null;
		
		startLine = null;
		endLine = null;
		startLineSec = null;
		endLineSec = null;
		startv = null;
		endv = null;
		startvO = null;
		endvO = null;
		startv1 = null;
		startv2 = null;
		startv3 = null;
		startv4 = null;
		endv1 = null;
		endv2 = null;
		endv3 = null;
		endv4 = null;
		aa = null;
		bb = null;
		direction0 = null;
		direction = null;
		up = null;
		down = null;
		up2 = null;
		down2 = null;
		v1 = null;
		v2 = null;
		v3 = null;
		v4 = null;
		Intersector5_6M0 = null;
		Intersector5M1000_6M0 = null;
		Intersector10_11P0 = null;
		Intersector10M1000_11P0 = null;
		OIntersector5_6M0 = null;
		OIntersector5M1000_6M0 = null;
		OIntersector10_11P0 = null;
		OIntersector10M1000_11P0 = null;
		Intersector0_1M0 = null;
		Intersector0P1000_1M0 = null;
		Intersector15_16P0 = null;
		Intersector15P1000_16P0 = null;
		OIntersector0_1M0 = null;
		OIntersector0P1000_1M0 = null;
		OIntersector15_16P0 = null;
		OIntersector15P1000_16P0 = null;
		left_circle_origin_circle = null;
		right_circle_origin_circle = null;
		left_circle_origin_circleSec = null;
		right_circle_origin_circleSec = null;
		left_circle_collision = null;
		right_circle_collision = null;
	}
}
