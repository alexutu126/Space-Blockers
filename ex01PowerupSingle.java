//WMS3 2015

package com.cryotrax.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.graphics.g2d.Animation;

//----------------------------------------------------------------

public class ex01PowerupSingle {
    public static final int VIRTUAL_SCREEN_WIDTH = 480;
    public static final int VIRTUAL_SCREEN_HEIGHT = 800;
	public static final int screen_sizew =
			Gdx.graphics.getWidth();
	public static final int screen_sizeh =
			Gdx.graphics.getHeight();
	public Sprite overlayer_general;
	public Sprite powerups1;
	public Sprite overlayer1;
	public Sprite overlayer2;
	public Sprite overlayer3;
	public Sprite exploder1;
	public Sprite exploder2;
	public Sprite exploder_general;
	public Sprite value_rotator1;
	public Sprite value_rotator2;
	public Sprite value_rotator3;
	public Sprite value_rotator_general;
	public int val_pup = 0;
	public float v_rotator_alpha = 0f;
	public float speed;
	public float rotation_speed = 64f;
	public float current_angle = 0f;
	public float current_angle_rotator = 0f;
	public float current_anim_time = 0;
	public float current_anim_alpha = 1;
	public float current_anim_time_expl = 0;
	public float current_anim_time_explO = 0;
	public float exploder_alpha = 1f;
	public float resizer;
	public boolean can_explode_powerup = false;
	public boolean already_captured_powerup = false;  // we don't check for collision if we hit it once
	public Circle collision_circle;     			  // circle used for collision and out of screen deletion
	public PowerupType type;                          // PowerupType.Bullets, PowerupType.Health,
	public BulletType bullet_type;
	public HealthType health_type;
	public static final float POWERUPS1_SIZEWH = 4f;
	public static final float OVERLAYER1_SIZEWH = 3.2f;
	public static final float ROTATOR1_SIZEWH = 4f;
	public static final float COLLISION_CIRCLE = 1.15f;
	
	public ex01PowerupSingle(
			Color color,
			PowerupType typer,
			TextureRegion tex_powerup,
			TextureRegion tex_overlayer,
			TextureRegion tex_overlayer2,
			TextureRegion tex_overlayer3,
			TextureRegion tex_exploder,
			TextureRegion tex_exploder2,
			TextureRegion hp1,
			TextureRegion hp2,
			TextureRegion hp3){
		resizer = ((float)VIRTUAL_SCREEN_WIDTH/(float)VIRTUAL_SCREEN_HEIGHT) /
				  ((float)screen_sizew/(float)screen_sizeh);
		switch(typer){
		case Health:
			{		
				powerups1 = new Sprite(tex_powerup);
				powerups1.setSize(POWERUPS1_SIZEWH,
								  POWERUPS1_SIZEWH);
				powerups1.setOrigin(powerups1.getWidth()/2,
									powerups1.getHeight()/2);
				overlayer1 = new Sprite(tex_overlayer);
				overlayer1.setSize(OVERLAYER1_SIZEWH,
								   OVERLAYER1_SIZEWH);
				overlayer1.setOrigin(overlayer1.getWidth()/2,
									 overlayer1.getHeight()/2);
				overlayer2 = new Sprite(tex_overlayer2);
				overlayer2.setSize(OVERLAYER1_SIZEWH,
								   OVERLAYER1_SIZEWH);
				overlayer2.setOrigin(overlayer2.getWidth()/2,
									 overlayer2.getHeight()/2);
				overlayer3 = new Sprite(tex_overlayer3);
				overlayer3.setSize(OVERLAYER1_SIZEWH,
								   OVERLAYER1_SIZEWH);
				overlayer3.setOrigin(overlayer3.getWidth()/2,
									 overlayer3.getHeight()/2);
				exploder1 = new Sprite(tex_exploder);
				exploder1.setSize(OVERLAYER1_SIZEWH,
								  OVERLAYER1_SIZEWH);
				exploder1.setOrigin(exploder1.getWidth()/2,
									exploder1.getHeight()/2);
				exploder1.setColor(color);
				value_rotator1 = new Sprite(hp1);
				value_rotator1.setSize(ROTATOR1_SIZEWH,
									   ROTATOR1_SIZEWH);
				value_rotator1.setOrigin(value_rotator1.getWidth()/2,
										 value_rotator1.getHeight()/2);
				value_rotator2 = new Sprite(hp2);
				value_rotator2.setSize(ROTATOR1_SIZEWH,
									   ROTATOR1_SIZEWH);
				value_rotator2.setOrigin(value_rotator2.getWidth()/2,
										 value_rotator2.getHeight()/2);
				value_rotator3 = new Sprite(hp3);
				value_rotator3.setSize(ROTATOR1_SIZEWH,
									   ROTATOR1_SIZEWH);
				value_rotator3.setOrigin(value_rotator3.getWidth()/2,
										 value_rotator3.getHeight()/2);
				this.type = typer;
			} break;
		case Bullets:
			{			
				powerups1 = new Sprite(tex_powerup);
				powerups1.setSize(POWERUPS1_SIZEWH,
								  POWERUPS1_SIZEWH);
				powerups1.setOrigin(powerups1.getWidth()/2,
									powerups1.getHeight()/2);
				overlayer1 = new Sprite(tex_overlayer);
				overlayer1.setSize(OVERLAYER1_SIZEWH,
								   OVERLAYER1_SIZEWH);
				overlayer1.setOrigin(overlayer1.getWidth()/2,
									 overlayer1.getHeight()/2);
				overlayer2 = new Sprite(tex_overlayer2);
				overlayer2.setSize(OVERLAYER1_SIZEWH,
								   OVERLAYER1_SIZEWH);
				overlayer2.setOrigin(overlayer2.getWidth()/2,
									 overlayer2.getHeight()/2);
				overlayer3 = new Sprite(tex_overlayer3);
				overlayer3.setSize(OVERLAYER1_SIZEWH,
								   OVERLAYER1_SIZEWH);
				overlayer3.setOrigin(overlayer3.getWidth()/2,
									 overlayer3.getHeight()/2);
				exploder1 = new Sprite(tex_exploder);
				exploder1.setSize(OVERLAYER1_SIZEWH,
								  OVERLAYER1_SIZEWH);
				exploder1.setOrigin(exploder1.getWidth()/2,
									exploder1.getHeight()/2);
				exploder1.setColor(color);
				exploder2 = new Sprite(tex_exploder2);
				exploder2.setSize(OVERLAYER1_SIZEWH,
								  OVERLAYER1_SIZEWH);
				exploder2.setOrigin(exploder2.getWidth()/2,
									exploder2.getHeight()/2);
				exploder2.setColor(color);
				value_rotator1 = new Sprite(hp1);
				value_rotator1.setSize(ROTATOR1_SIZEWH,
									   ROTATOR1_SIZEWH);
				value_rotator1.setOrigin(value_rotator1.getWidth()/2,
										 value_rotator1.getHeight()/2);
				value_rotator2 = new Sprite(hp2);
				value_rotator2.setSize(ROTATOR1_SIZEWH,
									   ROTATOR1_SIZEWH);
				value_rotator2.setOrigin(value_rotator2.getWidth()/2,
										 value_rotator2.getHeight()/2);
				value_rotator3 = new Sprite(hp3);
				value_rotator3.setSize(ROTATOR1_SIZEWH,
									   ROTATOR1_SIZEWH);
				value_rotator3.setOrigin(value_rotator3.getWidth()/2,
									     value_rotator3.getHeight()/2);
				this.type = typer;
			} break;
		default:
			break;
		}
	}
	
	public void ex01PowerupSingleReload(
			PowerupType typer,
			TextureRegion tex_powerup,
			TextureRegion tex_overlayer,
			TextureRegion tex_overlayer2,
			TextureRegion tex_overlayer3,
			TextureRegion tex_exploder,
			TextureRegion tex_exploder2,
			TextureRegion hp1,
			TextureRegion hp2,
			TextureRegion hp3){
		switch(typer){
		case Health:
			{		
				powerups1.setRegion(tex_powerup);
				overlayer1.setRegion(tex_overlayer);
				overlayer2.setRegion(tex_overlayer2);
				overlayer3.setRegion(tex_overlayer3);
				exploder1.setRegion(tex_exploder);
				value_rotator1.setRegion(hp1);
				value_rotator2.setRegion(hp2);
				value_rotator3.setRegion(hp3);						
				this.type = typer;
			}
		break;
		case Bullets:
			{			
				powerups1.setRegion(tex_powerup);
				overlayer1.setRegion(tex_overlayer);
				overlayer2.setRegion(tex_overlayer2);
				overlayer3.setRegion(tex_overlayer3);
				exploder1.setRegion(tex_exploder);
				exploder2.setRegion(tex_exploder2);
				value_rotator1.setRegion(hp1);
				value_rotator2.setRegion(hp2);
				value_rotator3.setRegion(hp3);
				this.type = typer;
			}
		break;
		default:
			break;
		}
	}
	
	public void update(
			float delta,
			Animation PowerupAnimation,
			Animation PowerupAnimationExplode,
			boolean rotate){
		speed = rotation_speed * delta;
		current_angle += speed;
		if(rotate){
			v_rotator_alpha -= 0.01f;
			value_rotator_general.setRotation(current_angle_rotator);
			if(v_rotator_alpha>=0f)
				value_rotator_general.setAlpha(v_rotator_alpha);
			current_angle_rotator += speed*1.5f;
		}
		powerups1.setRotation(current_angle);
		overlayer_general.setRotation(current_angle);
		current_anim_time += delta;
		if(!PowerupAnimation.isAnimationFinished(current_anim_time)){
			powerups1.setRegion(PowerupAnimation
					.getKeyFrame(current_anim_time, true));
			current_anim_alpha -= 0.015f;
			powerups1.setAlpha(current_anim_alpha);
		}
		if(can_explode_powerup){
			current_anim_time_expl += delta;
			current_anim_time_explO += delta;
			if(!PowerupAnimationExplode.isAnimationFinished(current_anim_time_expl)){
				exploder_general.setRegion(PowerupAnimationExplode
						.getKeyFrame(current_anim_time_expl, true));
				exploder_general.scale(0.2f);
				exploder_alpha -= 0.025f;
				exploder_general.setAlpha(exploder_alpha);		
			}
		}
	}
	
	public void Dispose(){
		overlayer_general = null;
		powerups1 = null;
		overlayer1 = null;
		overlayer2 = null;
		overlayer3 = null;
		exploder1 = null;
		exploder2 = null;
		exploder_general = null;
		value_rotator1 = null;
		value_rotator2 = null;
		value_rotator3 = null;
		value_rotator_general = null;
		collision_circle = null;
		type = null;
		bullet_type = null;
		health_type = null;
	}
}
