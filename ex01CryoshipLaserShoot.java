//WMS3 2015

package com.cryotrax.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;

public class ex01CryoshipLaserShoot {
	private static final float SPRITE_EXPLOSION_MICRO_WEIGHT = 6.0f;
	private static final float SPRITE_EXPLOSION_MICRO_HEIGHT = 6.0f;
	public static final float COLLISION_WIDTH = 20.1895f;
	public static final float COLLISION_HEIGHT = 8f;
	public float[] vert;         	// vertices for the base mesh 
	public short[] indi;         	// the indices {0,1,2,2,3,0}
	public float[] vertO;        	// vertices for the laser line 
	public short[] indiO;		 	// the indices {0,1,2,2,3,0}
	public int index = 0;        	// array index for base mesh vertices
	public int indexi = 0;       	// array index for base mesh indices
	public int indexO = 0;       	// array index for laser line vertices
	public int indexiO = 0;	     	// array index for laser line indices
	public int nomber_in_laser_list;
	public boolean can_explode_now = false;
	public boolean finished_exploding = false;	
	public boolean blocked_after_first_explosion_not_repositioned = false;
	public float xer;
	public float yer;
	public float difference_x;
	public float difference_xO;
	public float counter_explosion = 0f;
	public float y_difference;
	public float x_difference;
	public float y_differenceO;
	public float x_differenceO;
	public float x_original;
	public float x_originalO;
	public ex01CryoshipPrincipial spaceship;
	// rectangle used for collision detection and out of screen deletion
	public Rectangle collision_rect;
	public TextureRegion regionLaserShoot;
	public Sprite explosion;
	// when expansion starts this will be the START vector for the laser
	public Vector2 startLine;
	// when expansion starts this will be the END vector for the laser
	public Vector2 endLine;
	public Vector2 aa = new Vector2();
	public Vector2 bb = new Vector2();
	public Vector2 direction0 = new Vector2();
	public Vector2 direction = new Vector2();
	public Vector2 up = new Vector2();
	public Vector2 down = new Vector2();
	public Vector2 up2 = new Vector2();
	public Vector2 down2 = new Vector2();
	
	public ex01CryoshipLaserShoot(int no,
								  ex01CryoshipPrincipial ship,
								  Vector2 start,
								  Vector2 end,
								  TextureRegion region,
								  TextureRegion base,
								  TextureRegion overlay){
		// 4 vertices (x,y,(r,g,b,a),u,v) x>1,y>2,(r,g,b,a)>3,u>4,v>5
		vert = new float[4 * 1 * 5];
		// 4 vertices (x,y,(r,g,b,a),u,v) x>1,y>2,(r,g,b,a)>3,u>4,v>5
		indi = new short[6 * 1];
		// 4 vertices (x,y,(r,g,b,a),u,v) x>1,y>2,(r,g,b,a)>3,u>4,v>5
		vertO = new float[4 * 1 * 5];
		// 4 vertices (x,y,(r,g,b,a),u,v) x>1,y>2,(r,g,b,a)>3,u>4,v>5
		indiO = new short[6 * 1];
		GetMeshFromLineBase(start, end, 255, 255, 0, 100, base);
		GetMeshFromLineOverlay(start, end, 255, 235, 115, 255, overlay);
		explosion = new Sprite(region);
		explosion.setSize(SPRITE_EXPLOSION_MICRO_WEIGHT, SPRITE_EXPLOSION_MICRO_HEIGHT);
		regionLaserShoot = region;
		spaceship = ship;
		nomber_in_laser_list = no;
	}
	
	public void ex01CryoshipLaserShootReload(Vector2 start,
											 Vector2 end,
											 TextureRegion region,
											 TextureRegion base,
											 TextureRegion overlay){

		GetMeshFromLineBase(start, end, 255, 255, 0, 100, base);
		GetMeshFromLineOverlay(start, end, 255, 235, 115, 255, overlay);
		explosion.setRegion(region);
		regionLaserShoot = region;
		can_explode_now = false;
		finished_exploding = false;	
		blocked_after_first_explosion_not_repositioned = false;
		counter_explosion = 0f;
	}	
	
	public void PositionLaserShootAt(Vector2 start, Vector2 end){
		x_original = start.x;
		x_originalO = start.x;
		difference_x = start.x - x_original;
		difference_xO = start.x - x_originalO;
		vert[1] = start.y + y_difference;
		vert[0] = start.x + difference_x - x_difference;
		vertO[1] = start.y + y_differenceO;
		vertO[0] = start.x + difference_x - x_differenceO;
		vert[6] = start.y;
		vert[5] = start.x + difference_x - x_difference;
		vertO[6] = start.y;		
		vertO[5] = start.x + difference_x - x_differenceO;
		vert[11] = start.y;
		vert[10] = start.x + difference_x + x_difference;
		vertO[11] = start.y;		
		vertO[10] = start.x + difference_x + x_differenceO;
		vert[16] = start.y + y_difference;
		vert[15] = start.x + difference_x + x_difference;
		vertO[16] = start.y + y_differenceO;
		vertO[15] = start.x + difference_x + x_differenceO;
		collision_rect.y = start.y;
		collision_rect.x = start.x + difference_x;
		collision_rect.y = vert[6];
		collision_rect.width = (vert[10] - vert[5])/COLLISION_WIDTH;
		collision_rect.height = vert[16] - vert[11];
		collision_rect.x = spaceship.spaceship_rectangle_collision.x - collision_rect.width/2f;
	}
	
	public void SetPosition(){
		if(!can_explode_now) {
			xer = collision_rect.getX() - (explosion.getWidth()-collision_rect.width)/2f;
			yer = collision_rect.getY() - (explosion.getHeight()-collision_rect.height)/2f;
			explosion.setPosition(xer, yer);				
		}
	}

	public void UpdateExplosionMicro(float delta, Animation animation){
		counter_explosion += delta;		
		if(!animation.isAnimationFinished(counter_explosion)){
			explosion.setRegion(animation.getKeyFrame(counter_explosion, true));
		} else {
			finished_exploding = false;
			spaceship.is_bullet_busy[nomber_in_laser_list] = false;
			can_explode_now = false;
			counter_explosion = 0f;
		}				
	}
		
	// create mesh for base of the laser
	public void GetMeshFromLineBase(Vector2 a,
									Vector2 b,
									int rc,
									int gc,
									int bc,
									int alpha,
									TextureRegion base){

		index = 0;		      			      		// array index for base mesh vertices
		indexi = 0;	   					      		// array index for base mesh indices	   		   			
		x_original = a.x;
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
		up.rotate(90f).scl(1.35f);					// rotate up
		down.x = direction.x;
		down.y = direction.y;
		down.rotate(-90f).scl(1.35f);           
		up2.x = up.x;
		up2.y = up.y;		
		up2.add(direction0);				        // translate the left top
		down2.x = down.x;
		down2.y = down.y;
		down2.add(direction0);  				    // translate the left bottom				
		
		// we add a because all vectors are based on (0,0) 
		/*00*/vert[index++] = up2.add(a).x; 
		/*01*/vert[index++] = up2.y;		
		/*02*/vert[index++] = Color.toFloatBits(rc, gc, bc, alpha);
		/*03*/vert[index++] = base.getU();
		/*04*/vert[index++] = base.getV();
		/*05*/vert[index++] = up.add(a).x;
		/*06*/vert[index++] = up.y;        
		/*07*/vert[index++] = Color.toFloatBits(rc, gc, bc, alpha);
		/*08*/vert[index++] = base.getU();
		/*09*/vert[index++] = base.getV2();	
		/*10*/vert[index++] = down.add(a).x; 		
		/*11*/vert[index++] = down.y;        
		/*12*/vert[index++] = Color.toFloatBits(rc, gc, bc, alpha);
		/*13*/vert[index++] = base.getU2();
		/*14*/vert[index++] = base.getV2();
		/*15*/vert[index++] = down2.add(a).x;        
		/*16*/vert[index++] = down2.y;       
		/*17*/vert[index++] = Color.toFloatBits(rc, gc, bc, alpha);
		/*18*/vert[index++] = base.getU2();
		/*19*/vert[index++] = base.getV();
		/*00*/indi[indexi++] = (short) ((0) + 0);
		/*01*/indi[indexi++] = (short) ((0) + 1);
		/*02*/indi[indexi++] = (short) ((0) + 2);
		/*03*/indi[indexi++] = (short) ((0) + 2);
		/*04*/indi[indexi++] = (short) ((0) + 3);
		/*05*/indi[indexi++] = (short) ((0) + 0);
		collision_rect = new Rectangle(
				vert[5] + (vert[10]-vert[5])/COLLISION_WIDTH + (vert[10]-vert[5])/COLLISION_HEIGHT,
				vert[6],
			   (vert[10] - vert[5])/COLLISION_WIDTH,
				vert[16] - vert[11]);
		y_difference = vert[1] - vert[6];
		x_difference = (vert[15] - vert[0])/2f;
	}		

	
	// create mesh for laser line
	public void GetMeshFromLineOverlay(Vector2 a,
									   Vector2 b,
									   int rc,
									   int gc,
									   int bc,
									   int alpha,
									   TextureRegion overlay){

		indexO = 0;     					  		// array index for laser line vertices
		indexiO = 0;						  		// array index for laser line indices	
		x_originalO = a.x;		
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
		up.rotate(90f).scl(1.25f);					// rotate up
		down.x = direction.x;
		down.y = direction.y;      
		down.rotate(-90f).scl(1.25f);
		up2.x = up.x;
		up2.y = up.y;		
		up2.add(direction0);				        // translate the left top
		down2.x = down.x;
		down2.y = down.y;
		down2.add(direction0);  				    // translate the left bottom			
		
		// we add a because all vectors are based on (0,0) 
		/*00*/vertO[indexO++] = up2.add(a).x; 
		/*01*/vertO[indexO++] = up2.y;		  
		/*02*/vertO[indexO++] = Color.toFloatBits(rc, gc, bc, alpha);
		/*03*/vertO[indexO++] = overlay.getU();
		/*04*/vertO[indexO++] = overlay.getV();
		/*05*/vertO[indexO++] = up.add(a).x; 
		/*06*/vertO[indexO++] = up.y;      
		/*07*/vertO[indexO++] = Color.toFloatBits(rc, gc, bc, alpha);
		/*08*/vertO[indexO++] = overlay.getU();
		/*09*/vertO[indexO++] = overlay.getV2();	
		/*10*/vertO[indexO++] = down.add(a).x; 		  
		/*11*/vertO[indexO++] = down.y;         
		/*12*/vertO[indexO++] = Color.toFloatBits(rc, gc, bc, alpha);
		/*13*/vertO[indexO++] = overlay.getU2();
		/*14*/vertO[indexO++] = overlay.getV2();
		/*15*/vertO[indexO++] = down2.add(a).x;    
		/*16*/vertO[indexO++] = down2.y;       
		/*17*/vertO[indexO++] = Color.toFloatBits(rc, gc, bc, alpha);
		/*18*/vertO[indexO++] = overlay.getU2();
		/*19*/vertO[indexO++] = overlay.getV();
		/*00*/indiO[indexiO++] = (short) ((0) + 0);
		/*01*/indiO[indexiO++] = (short) ((0) + 1);
		/*02*/indiO[indexiO++] = (short) ((0) + 2);
		/*03*/indiO[indexiO++] = (short) ((0) + 2);
		/*04*/indiO[indexiO++] = (short) ((0) + 3);
		/*05*/indiO[indexiO++] = (short) ((0) + 0);
		y_differenceO = vertO[1] - vertO[6];
		x_differenceO = (vertO[15] - vertO[0])/2f;
	}	
	
	public void AdvanceLaserShoot(float velocity){
		vert[1] += velocity;
		vertO[1] += velocity;		
		vert[6] += velocity;
		vertO[6] += velocity;		
		vert[11] += velocity;
		vertO[11] += velocity;		
		vert[16] += velocity;
		vertO[16] += velocity;	
		collision_rect.y += velocity;
	}

	public void Dispose(){
		vert = null;
		indi = null;
		vertO = null;
		indiO = null;
		spaceship = null;
		collision_rect = null;
		regionLaserShoot = null;
		explosion = null;
		startLine = null;
		endLine = null;
		aa = null;
		bb = null;
		direction0 = null;
		direction = null;
		up = null;
		down = null;
		up2 = null;
		down2 = null;
	}
}
