//WMS3 2015

package com.cryotrax.game;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//----------------------------------------------------------------

public class ex01PowerupList {
    public static final float hscreenadd =
			800f / 480f * 25f / 2f;
    public static final float hscreendif10 =
			800f / 480f * 25f      - 10f;
    public static final float XER = 15f;
    public static final float YER = -100f;
	public static final float CAMERA_X_ER = 15f;
    public static final int TOTAL_POWERUPS_HEALTH = 3;
    public static final int TOTAL_POWERUPS_BULLETS = 9;
    public static final int VIRTUAL_SCREEN_WIDTH = 480;
    public static final int VIRTUAL_SCREEN_HEIGHT = 800;
	public int i, j;
	public float y_expander;
	public float y_expander_rot;
	public float x_expander;
    public float xx1powerup;
    public float yy1powerup;
    public float zz1powerup;
    public float xx2powerup;
    public float yy2powerup;
    public float zz2powerup;
    public float xx3powerup;
    public float yy3powerup;
    public float zz3powerup;
    public float xx4powerup;
    public float yy4powerup;
    public float zz4powerup;
    public float xx5powerup;
    public float yy5powerup;
    public float zz5powerup;
    public float xx6powerup;
    public float yy6powerup;
    public float zz6powerup;
    public float xx7powerup;
    public float yy7powerup;
    public float zz7powerup;
    public float xx8powerup;
    public float yy8powerup;
    public float zz8powerup;
    public float xx9powerup;
    public float yy9powerup;
    public float zz9powerup; 
    public float xx1powerupH;
    public float yy1powerupH;
    public float zz1powerupH;
    public float xx2powerupH;
    public float yy2powerupH;
    public float zz2powerupH;
    public float xx3powerupH;
    public float yy3powerupH;
    public float zz3powerupH;  
    public boolean lefter_is = false;    
    public boolean is_poweup_busy[] = new boolean[9];
    public boolean is_poweup_eaten[] = new boolean[9];
    public boolean is_poweup_busyH[] = new boolean[3];
    public boolean is_poweup_eatenH[] = new boolean[3];
    public boolean lefter_isp[] = new boolean[9];
    public boolean lefter_ispH[] = new boolean[3];
	public boolean rotate = false;
	public ArrayList<ex01PowerupSingle> list_health_sprites;
	public ArrayList<ex01PowerupSingle> list_bullets_sprites;	
	public ArrayList<ex01PowerupSingle> list_health_bullets_sprites;	
	public Animation PowerupAnimation;	
	public Animation PowerupAnimationExplode;
	public Animation PowerupAnimationExplodeG;
	public TextureAtlas texture_atlas_here;
	public TextureRegion[] PowerupExpandAnim = new TextureRegion[12];
	public TextureRegion[] PowerupExpandAnimExplode = new TextureRegion[12];
	public TextureRegion[] PowerupExpandAnimExplodeG = new TextureRegion[12];
	public TextureRegion powerup_expland;
	public TextureRegion powerup_explode;
	public TextureRegion powerup_explodeG;
	public TextureRegion healthr1;
	public TextureRegion healthr2;
	public TextureRegion healthr3;
	public TextureRegion bulletsr1;
	public TextureRegion bulletsr2;
	public TextureRegion bulletsr3;
	public TextureRegion s3erP_TR;
	public TextureRegion s2erP_TR;
	public TextureRegion s1erP_TR;
	public TextureRegion s3erH_TR;
	public TextureRegion s2erH_TR;
	public TextureRegion s1erH_TR;
	public ex01PowerupSingle new_powerup;
	public ex01PowerupSingle pwr_health;
	public ex01PowerupSingle pwr_bullets; 
	public ex01PowerupSingle healths;
	public ex01PowerupSingle bullets;
	public ex01PowerupSingle pwr_up;
    public Color green = new Color(0.678f, 1.000f, 0.184f, 1.000f);
    public float min_powerup_collision_h = -1000f;
    public float max_powerup_collision_h = -1000f;
    public float min_powerup_collision_b = -1000f;
    public float max_powerup_collision_b = -1000f;
	public static final float POWERUPS1_SIZEWH = 4f;
	public static final float OVERLAYER1_SIZEWH = 3.2f;
	public static final float POWERUPS1_SIZEWHp2 =
			POWERUPS1_SIZEWH/2f;
	public static final float OVERLAYER1_SIZEWHp2 =
			OVERLAYER1_SIZEWH/2f;
	public static final float ROTATOR_DELTA_Y = 10.5f;
	public static final float ROTATOR_DELTA_Xp =
			+(POWERUPS1_SIZEWH + 2f);
	public static final float ROTATOR_DELTA_Xm =
			+(POWERUPS1_SIZEWH + 1f) * 2;
	public final float Y_EXPANDER_DELTA = 3f;
	public final float Y_EXPANDER_DELTA_ROT = 3.75f;
	public float y_expander_delta = 0;
    
	public void ex01PowerupListReload(TextureAtlas atlas){
		for(i=0; i<is_poweup_busyH.length; i++){
			is_poweup_busyH[i] = false;
			lefter_ispH[i] = false;
			is_poweup_eatenH[i] = false;
		}
		for(i=0; i<is_poweup_busy.length; i++){
			is_poweup_busy[i] = false;
			lefter_isp[i] = false;
			is_poweup_eaten[i] = false;
		}	
		for(j = list_health_sprites.size() - 1; j >= 0; j--){
			healths = list_health_sprites.get(j);
			healths.current_angle_rotator = 0f; 
		}	
		for(j = list_bullets_sprites.size() - 1; j >= 0; j--){
			bullets = list_bullets_sprites.get(j);
			bullets.current_angle_rotator = 0f;
		}
		rotate = false; lefter_is = false;
		ex01PowerupListReloadAnim(atlas);
		
	    min_powerup_collision_h = -1000f;
	    max_powerup_collision_h = -1000f;
	    min_powerup_collision_b = -1000f;
	    max_powerup_collision_b = -1000f;
	}
	
	public void ex01PowerupListReload(){
		for(i=0; i<is_poweup_busyH.length; i++){
			is_poweup_busyH[i] = false;
			lefter_ispH[i] = false;
			is_poweup_eatenH[i] = false;
		}
		for(i=0; i<is_poweup_busy.length; i++){
			is_poweup_busy[i] = false;
			lefter_isp[i] = false;
			is_poweup_eaten[i] = false;
		}	
		for(j = list_health_sprites.size() - 1; j >= 0; j--){
			healths = list_health_sprites.get(j);
			healths.current_angle_rotator = 0f; 
		}	
		for(j = list_bullets_sprites.size() - 1; j >= 0; j--){
			bullets = list_bullets_sprites.get(j);
			bullets.current_angle_rotator = 0f;
		}
		rotate = false; lefter_is = false;
		y_expander_delta = 0;
		
	    min_powerup_collision_h = -1000f;
	    max_powerup_collision_h = -1000f;
	    min_powerup_collision_b = -1000f;
	    max_powerup_collision_b = -1000f;
	}

	public ex01PowerupList(TextureAtlas atlas){
		texture_atlas_here = atlas;
		list_health_sprites = new ArrayList<ex01PowerupSingle>();
		list_bullets_sprites = new ArrayList<ex01PowerupSingle>();
	    PowerupExpandAnim[0] = texture_atlas_here
				.findRegion("PowerupExpandAnim0000");
	    PowerupExpandAnim[1] = texture_atlas_here
				.findRegion("PowerupExpandAnim0002");
	    PowerupExpandAnim[2] = texture_atlas_here
				.findRegion("PowerupExpandAnim0004");
	    PowerupExpandAnim[3] = texture_atlas_here
				.findRegion("PowerupExpandAnim0006");
	    PowerupExpandAnim[4] = texture_atlas_here
				.findRegion("PowerupExpandAnim0008");
	    PowerupExpandAnim[5] = texture_atlas_here
				.findRegion("PowerupExpandAnim0010");
	    PowerupExpandAnim[6] = texture_atlas_here
				.findRegion("PowerupExpandAnim0012");
	    PowerupExpandAnim[7] = texture_atlas_here
				.findRegion("PowerupExpandAnim0014");
	    PowerupExpandAnim[8] = texture_atlas_here
				.findRegion("PowerupExpandAnim0016");
	    PowerupExpandAnim[9] = texture_atlas_here
				.findRegion("PowerupExpandAnim0018");
	    PowerupExpandAnim[10] = texture_atlas_here
				.findRegion("PowerupExpandAnim0020");
	    PowerupExpandAnim[11] = texture_atlas_here
				.findRegion("PowerupExpandAnim0022");
	    PowerupExpandAnimExplode[0] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0000");
	    PowerupExpandAnimExplode[1] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0002");
	    PowerupExpandAnimExplode[2] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0004");
	    PowerupExpandAnimExplode[3] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0006");
	    PowerupExpandAnimExplode[4] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0008");
	    PowerupExpandAnimExplode[5] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0010");
	    PowerupExpandAnimExplode[6] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0012");
	    PowerupExpandAnimExplode[7] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0014");
	    PowerupExpandAnimExplode[8] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0016");
	    PowerupExpandAnimExplode[9] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0018");
	    PowerupExpandAnimExplode[10] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0020");
	    PowerupExpandAnimExplode[11] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0022");
	    PowerupExpandAnimExplodeG[0] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0000G");
	    PowerupExpandAnimExplodeG[1] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0002G");
	    PowerupExpandAnimExplodeG[2] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0004G");
	    PowerupExpandAnimExplodeG[3] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0006G");
	    PowerupExpandAnimExplodeG[4] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0008G");
	    PowerupExpandAnimExplodeG[5] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0010G");
	    PowerupExpandAnimExplodeG[6] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0012G");
	    PowerupExpandAnimExplodeG[7] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0014G");
	    PowerupExpandAnimExplodeG[8] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0016G");
	    PowerupExpandAnimExplodeG[9] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0018G");
	    PowerupExpandAnimExplodeG[10] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0020G");
	    PowerupExpandAnimExplodeG[11] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0022G");
	    PowerupAnimation = new
				Animation(0.075f, PowerupExpandAnim);
	    PowerupAnimationExplode = new
				Animation(0.025f, PowerupExpandAnimExplode);
	    PowerupAnimationExplodeG = new
				Animation(0.025f, PowerupExpandAnimExplodeG);

		powerup_expland = PowerupExpandAnim[0];
		powerup_explodeG = PowerupExpandAnimExplodeG[0];		
		powerup_explode = PowerupExpandAnimExplode[0];

		healthr1 = atlas
				.findRegion("health00S");
		healthr2 = atlas
				.findRegion("health01S");
		healthr3 = atlas
				.findRegion("health02S");
		bulletsr1 = atlas
				.findRegion("bulletss01");
		bulletsr2 = atlas
				.findRegion("bulletss02");
		bulletsr3 = atlas
				.findRegion("bulletss03");
		s1erP_TR = atlas
				.findRegion("startupcircle_powerup111");
		s2erP_TR = atlas
				.findRegion("startupcircle_powerup222");
		s3erP_TR = atlas
				.findRegion("startupcircle_powerup333");
		s1erH_TR = atlas
				.findRegion("startupcircle_health111");
		s2erH_TR = atlas
				.findRegion("startupcircle_health222");
		s3erH_TR = atlas
				.findRegion("startupcircle_health333");
		GenerateListInit(atlas);
	}
	
	public void ex01PowerupListReloadAnim(TextureAtlas atlas){
		texture_atlas_here = atlas;
	    PowerupExpandAnim[0] = texture_atlas_here
				.findRegion("PowerupExpandAnim0000");
	    PowerupExpandAnim[1] = texture_atlas_here
				.findRegion("PowerupExpandAnim0002");
	    PowerupExpandAnim[2] = texture_atlas_here
				.findRegion("PowerupExpandAnim0004");
	    PowerupExpandAnim[3] = texture_atlas_here
				.findRegion("PowerupExpandAnim0006");
	    PowerupExpandAnim[4] = texture_atlas_here
				.findRegion("PowerupExpandAnim0008");
	    PowerupExpandAnim[5] = texture_atlas_here
				.findRegion("PowerupExpandAnim0010");
	    PowerupExpandAnim[6] = texture_atlas_here
				.findRegion("PowerupExpandAnim0012");
	    PowerupExpandAnim[7] = texture_atlas_here
				.findRegion("PowerupExpandAnim0014");
	    PowerupExpandAnim[8] = texture_atlas_here
				.findRegion("PowerupExpandAnim0016");
	    PowerupExpandAnim[9] = texture_atlas_here
				.findRegion("PowerupExpandAnim0018");
	    PowerupExpandAnim[10] = texture_atlas_here
				.findRegion("PowerupExpandAnim0020");
	    PowerupExpandAnim[11] = texture_atlas_here
				.findRegion("PowerupExpandAnim0022");
	    PowerupExpandAnimExplode[0] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0000");
	    PowerupExpandAnimExplode[1] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0002");
	    PowerupExpandAnimExplode[2] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0004");
	    PowerupExpandAnimExplode[3] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0006");
	    PowerupExpandAnimExplode[4] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0008");
	    PowerupExpandAnimExplode[5] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0010");
	    PowerupExpandAnimExplode[6] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0012");
	    PowerupExpandAnimExplode[7] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0014");
	    PowerupExpandAnimExplode[8] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0016");
	    PowerupExpandAnimExplode[9] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0018");
	    PowerupExpandAnimExplode[10] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0020");
	    PowerupExpandAnimExplode[11] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0022");
	    PowerupExpandAnimExplodeG[0] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0000G");
	    PowerupExpandAnimExplodeG[1] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0002G");
	    PowerupExpandAnimExplodeG[2] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0004G");
	    PowerupExpandAnimExplodeG[3] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0006G");
	    PowerupExpandAnimExplodeG[4] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0008G");
	    PowerupExpandAnimExplodeG[5] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0010G");
	    PowerupExpandAnimExplodeG[6] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0012G");
	    PowerupExpandAnimExplodeG[7] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0014G");
	    PowerupExpandAnimExplodeG[8] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0016G");
	    PowerupExpandAnimExplodeG[9] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0018G");
	    PowerupExpandAnimExplodeG[10] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0020G");
	    PowerupExpandAnimExplodeG[11] = texture_atlas_here
				.findRegion("PowerupExpandAnimExplode0022G");
	    PowerupAnimation = new
				Animation(0.075f, PowerupExpandAnim);
	    PowerupAnimationExplode = new
				Animation(0.025f, PowerupExpandAnimExplode);
	    PowerupAnimationExplodeG = new
				Animation(0.025f, PowerupExpandAnimExplodeG);

		powerup_expland = PowerupExpandAnim[0];
		powerup_explodeG = PowerupExpandAnimExplodeG[0];		
		powerup_explode = PowerupExpandAnimExplode[0];

		healthr1 = atlas
				.findRegion("health00S");
		healthr2 = atlas
				.findRegion("health01S");
		healthr3 = atlas
				.findRegion("health02S");
		bulletsr1 = atlas
				.findRegion("bulletss01");
		bulletsr2 = atlas
				.findRegion("bulletss02");
		bulletsr3 = atlas
				.findRegion("bulletss03");
		s1erP_TR = atlas
				.findRegion("startupcircle_powerup111");
		s2erP_TR = atlas
				.findRegion("startupcircle_powerup222");
		s3erP_TR = atlas
				.findRegion("startupcircle_powerup333");
		s1erH_TR = atlas
				.findRegion("startupcircle_health111");
		s2erH_TR = atlas
				.findRegion("startupcircle_health222");
		s3erH_TR = atlas
				.findRegion("startupcircle_health333");
		GenerateListInitReloadTex(atlas);		
	}
	
    public void GenerateListInit(TextureAtlas atlas){
		for(i = 0; i < TOTAL_POWERUPS_HEALTH; i++){
			new_powerup = new ex01PowerupSingle(
					Color.ORANGE,
					PowerupType.Health,
					powerup_expland,
					healthr1,
					healthr2,
					healthr3,
					powerup_explode,
					powerup_explodeG,
					s1erH_TR,
					s2erH_TR,
					s3erH_TR);
			new_powerup.collision_circle = new Circle(
					XER,
					YER,
					ex01PowerupSingle.COLLISION_CIRCLE);
			list_health_sprites.add(new_powerup);
		}
		for(j = 0; j < TOTAL_POWERUPS_BULLETS; j++){
			new_powerup = new ex01PowerupSingle(
					Color.ORANGE,
					PowerupType.Bullets,
					powerup_expland,
					bulletsr1,
					bulletsr2,
					bulletsr3,
					powerup_explode,
					powerup_explodeG,
					s1erP_TR,
					s2erP_TR,
					s3erP_TR);
			new_powerup.collision_circle = new Circle(
					XER,
					YER,
					ex01PowerupSingle.COLLISION_CIRCLE);
			list_bullets_sprites.add(new_powerup);
		}	
    }
    
    public void GenerateListInitReloadTex(TextureAtlas atlas){
		for(i = 0; i < TOTAL_POWERUPS_HEALTH; i++){
			list_health_sprites.get(i).ex01PowerupSingleReload(
					PowerupType.Health,
					powerup_expland,
					healthr1,
					healthr2,
					healthr3,
					powerup_explode,
					powerup_explodeG,
					s1erH_TR,
					s2erH_TR,
					s3erH_TR);
		}
		for(j = 0; j < TOTAL_POWERUPS_BULLETS; j++){
			list_bullets_sprites.get(j).ex01PowerupSingleReload(
					PowerupType.Bullets,
					powerup_expland,
					bulletsr1,
					bulletsr2,
					bulletsr3,
					powerup_explode,
					powerup_explodeG,
					s1erP_TR,
					s2erP_TR,
					s3erP_TR);
		}	
    }    
    
    public void ChangeGeneralSpriteTextureHealth(
			Color color,
			HealthType typer,
			int index,
			float y,
			float y_rot,
			float x){
		switch(typer){
			case Worst:
				list_health_sprites.get(index).overlayer_general =
						list_health_sprites.get(index).overlayer1;
				list_health_sprites.get(index).exploder_general =
						list_health_sprites.get(index).exploder1;
				list_health_sprites.get(index).value_rotator_general =
						list_health_sprites.get(index).value_rotator1;
				list_health_sprites.get(index).val_pup = 1;
				break;
			case Middle:
				list_health_sprites.get(index).overlayer_general =
						list_health_sprites.get(index).overlayer2;
				list_health_sprites.get(index).exploder_general =
						list_health_sprites.get(index).exploder1;
				list_health_sprites.get(index).value_rotator_general =
						list_health_sprites.get(index).value_rotator2;
				list_health_sprites.get(index).val_pup = 2;
				break;
			case Best:
				list_health_sprites.get(index).overlayer_general =
						list_health_sprites.get(index).overlayer3;
				list_health_sprites.get(index).exploder_general =
						list_health_sprites.get(index).exploder1;
				list_health_sprites.get(index).value_rotator_general =
						list_health_sprites.get(index).value_rotator3;
				list_health_sprites.get(index).val_pup = 3;
				break;
			default: { } break;
		}
		list_health_sprites.get(index).powerups1
				.setPosition(x - POWERUPS1_SIZEWHp2, y - POWERUPS1_SIZEWHp2);
		list_health_sprites.get(index).overlayer_general
				.setPosition(x - OVERLAYER1_SIZEWHp2, y - OVERLAYER1_SIZEWHp2);
		list_health_sprites.get(index).exploder_general
				.setPosition(x - OVERLAYER1_SIZEWHp2, y - OVERLAYER1_SIZEWHp2);
		list_health_sprites.get(index).exploder_general.
				setColor(color);
		list_health_sprites.get(index).collision_circle.x = x;
		list_health_sprites.get(index).collision_circle.y = y;
		list_health_sprites.get(index).health_type = typer;
		list_health_sprites.get(index).current_anim_time = 0f; 
		list_health_sprites.get(index).current_angle = 0f;
		list_health_sprites.get(index).current_anim_alpha = 1;
		list_health_sprites.get(index).current_anim_time_expl = 0;
		list_health_sprites.get(index).current_anim_time_explO = 0;
		list_health_sprites.get(index).exploder_alpha = 1f;
		list_health_sprites.get(index).exploder_general.setScale(1f);
		list_health_sprites.get(index).can_explode_powerup = false;
		list_health_sprites.get(index).already_captured_powerup = false;		 
		if(lefter_ispH[index]){
			list_health_sprites.get(index).value_rotator_general
					.setPosition(x + ROTATOR_DELTA_Xp, y_rot + ROTATOR_DELTA_Y);
		} else {
			list_health_sprites.get(index).value_rotator_general
					.setPosition(x - ROTATOR_DELTA_Xm, y_rot + ROTATOR_DELTA_Y);
		}
    }
    
    public void ChangeGeneralSpriteTextureBullets(
			Color color,
			BulletType typer,
			int index,
			float y,
			float y_rot,
			float x){
		switch(typer){
			case Worst:
				list_bullets_sprites.get(index).overlayer_general =
						list_bullets_sprites.get(index).overlayer1;
				list_bullets_sprites.get(index).exploder_general =
						list_bullets_sprites.get(index).exploder1;
				list_bullets_sprites.get(index).value_rotator_general =
						list_bullets_sprites.get(index).value_rotator1;
				list_bullets_sprites.get(index).val_pup = 1;
				break;
			case Middle:
				list_bullets_sprites.get(index).overlayer_general =
						list_bullets_sprites.get(index).overlayer2;
				list_bullets_sprites.get(index).exploder_general =
						list_bullets_sprites.get(index).exploder1;
				list_bullets_sprites.get(index).value_rotator_general =
						list_bullets_sprites.get(index).value_rotator2;
				list_bullets_sprites.get(index).val_pup = 2;
				break;
			case Best:
				list_bullets_sprites.get(index).overlayer_general =
						list_bullets_sprites.get(index).overlayer3;
				list_bullets_sprites.get(index).exploder_general =
						list_bullets_sprites.get(index).exploder2;
				list_bullets_sprites.get(index).value_rotator_general =
						list_bullets_sprites.get(index).value_rotator3;
				list_bullets_sprites.get(index).val_pup = 3;
				break;
			default: { } break;
		}
		list_bullets_sprites.get(index).powerups1
				.setPosition(x - POWERUPS1_SIZEWHp2, y - POWERUPS1_SIZEWHp2);
		list_bullets_sprites.get(index).overlayer_general
				.setPosition(x - OVERLAYER1_SIZEWHp2, y - OVERLAYER1_SIZEWHp2);
		list_bullets_sprites.get(index).exploder_general
				.setPosition(x - OVERLAYER1_SIZEWHp2, y - OVERLAYER1_SIZEWHp2);
		list_bullets_sprites.get(index).exploder_general
				.setColor(color);
		list_bullets_sprites.get(index).collision_circle.x = x;
		list_bullets_sprites.get(index).collision_circle.y = y;
		list_bullets_sprites.get(index).bullet_type = typer;
		list_bullets_sprites.get(index).current_anim_time = 0f; 
		list_bullets_sprites.get(index).current_angle = 0f;
		list_bullets_sprites.get(index).current_anim_alpha = 1;
		list_bullets_sprites.get(index).current_anim_time_expl = 0;
		list_bullets_sprites.get(index).current_anim_time_explO = 0;
		list_bullets_sprites.get(index).exploder_alpha = 1f;	
		list_bullets_sprites.get(index).exploder_general.setScale(1f);
		list_bullets_sprites.get(index).can_explode_powerup = false;
		list_bullets_sprites.get(index).already_captured_powerup = false;
		if (lefter_isp[index]){
			list_bullets_sprites.get(index).value_rotator_general
					.setPosition(x + ROTATOR_DELTA_Xp, y_rot + ROTATOR_DELTA_Y);
		} else {
			list_bullets_sprites.get(index).value_rotator_general
					.setPosition(x - ROTATOR_DELTA_Xm, y_rot + ROTATOR_DELTA_Y);
		}
    }    
	
    // this selects the health power-up that is available and repositions it
	public void ex01PowerupSingleHealth(
			Color color,
			HealthType typer,
			float y,
			float y_rot,
			float x){
		if(!is_poweup_busyH[0]){
			lefter_ispH[0] = lefter_is;
			xx1powerupH = x;
			yy1powerupH = y;
			zz1powerupH = 0f;
			is_poweup_busyH[0] = true;
			ChangeGeneralSpriteTextureHealth(
					color,
					typer,
					0,
					y,
					y_rot,
					x);
		} else if(!is_poweup_busyH[1]){
			lefter_ispH[1] = lefter_is;
			xx2powerupH = x;
			yy2powerupH = y;
			zz2powerupH = 0f;
			is_poweup_busyH[1] = true;
			ChangeGeneralSpriteTextureHealth(
					color,
					typer,
					1,
					y,
					y_rot,
					x);
		} else if(!is_poweup_busyH[2]){
			lefter_ispH[2] = lefter_is;
			xx3powerupH = x;
			yy3powerupH = y;
			zz3powerupH = 0f;
			is_poweup_busyH[2] = true;
			ChangeGeneralSpriteTextureHealth(
					color,
					typer,
					2,
					y,
					y_rot,
					x);
		} 	
	}	
	
	// this selects the bullets power-up that is available and repositions it 
	public void ex01PowerupSingleBullets(
			Color color,
			BulletType typer,
			float y,
			float y_rot,
			float x){
		if(!is_poweup_busy[0]){
			lefter_isp[0] = lefter_is;
			xx1powerup = x;
			yy1powerup = y;
			zz1powerup = 0f;
			is_poweup_busy[0] = true;	
			ChangeGeneralSpriteTextureBullets(
					color,
					typer,
					0,
					y,
					y_rot,
					x);
		} else if(!is_poweup_busy[1]){
			lefter_isp[1] = lefter_is;
			xx2powerup = x;
			yy2powerup = y;
			zz2powerup = 0f;
			is_poweup_busy[1] = true;
			ChangeGeneralSpriteTextureBullets(
					color,
					typer,
					1,
					y,
					y_rot,
					x);
		} else if(!is_poweup_busy[2]){
			lefter_isp[2] = lefter_is;
			xx3powerup = x;
			yy3powerup = y;
			zz3powerup = 0f;
			is_poweup_busy[2] = true;
			ChangeGeneralSpriteTextureBullets(
					color,
					typer,
					2,
					y,
					y_rot,
					x);
		} else if(!is_poweup_busy[3]){
			lefter_isp[3] = lefter_is;
			xx4powerup = x;
			yy4powerup = y;
			zz4powerup = 0f;
			is_poweup_busy[3] = true;
			ChangeGeneralSpriteTextureBullets(
					color,
					typer,
					3,
					y,
					y_rot,
					x);
		} else if(!is_poweup_busy[4]){
			lefter_isp[4] = lefter_is;
			xx5powerup = x;
			yy5powerup = y;
			zz5powerup = 0f;
			is_poweup_busy[4] = true;
			ChangeGeneralSpriteTextureBullets(
					color,
					typer,
					4,
					y,
					y_rot,
					x);
		} else if(!is_poweup_busy[5]){
			lefter_isp[5] = lefter_is;
			xx6powerup = x;
			yy6powerup = y;
			zz6powerup = 0f;
			is_poweup_busy[5] = true;
			ChangeGeneralSpriteTextureBullets(
					color,
					typer,
					5,
					y,
					y_rot,
					x);
		} else if(!is_poweup_busy[6]){
			lefter_isp[6] = lefter_is;
			xx7powerup = x;
			yy7powerup = y;
			zz7powerup = 0f;
			is_poweup_busy[6] = true;
			ChangeGeneralSpriteTextureBullets(
					color,
					typer,
					6,
					y,
					y_rot,
					x);
		} else if(!is_poweup_busy[7]){
			lefter_isp[7] = lefter_is;
			xx8powerup = x;
			yy8powerup = y;
			zz8powerup = 0f;
			is_poweup_busy[7] = true;
			ChangeGeneralSpriteTextureBullets(
					color,
					typer,
					7,
					y,
					y_rot,
					x);
		} else if(!is_poweup_busy[8]){
			lefter_isp[8] = lefter_is;
			xx9powerup = x;
			yy9powerup = y;
			zz9powerup = 0f;
			is_poweup_busy[8] = true;
			ChangeGeneralSpriteTextureBullets(
					color,
					typer,
					8,
					y,
					y_rot,
					x);
		}		
	}
	
	// used in StartAndEvolvePowerups in ex01ElectrotrapRedList 
	public void GenerateF(
			short pup_h1,
			short pup_h2,
			short pup_h3,
			short pup_b1,
			short pup_b2,
			short pup_b3,
			float x,
			float y){
		y_expander = y;
		y_expander_rot = y;
		x_expander = x;
		if(x<=CAMERA_X_ER) {
			lefter_is = true;
		} else {
			lefter_is = false;
		}       
		min_powerup_collision_h = y_expander - ex01PowerupSingle.POWERUPS1_SIZEWH/2;
		for(i = 0; i < pup_h1; i++){
			ex01PowerupSingleHealth(
					Color.ORANGE,
					HealthType.Best,
					y_expander,
					y_expander_rot,
					x_expander);
			y_expander += Y_EXPANDER_DELTA;
			y_expander_rot += Y_EXPANDER_DELTA_ROT;
		}
		for(i = 0; i < pup_h2; i++){
			ex01PowerupSingleHealth(
					Color.OLIVE,
					HealthType.Middle,
					y_expander,
					y_expander_rot,
					x_expander);
			y_expander += Y_EXPANDER_DELTA;
			y_expander_rot += Y_EXPANDER_DELTA_ROT;
		}	
		for(i = 0; i < pup_h3; i++){
			ex01PowerupSingleHealth(
					Color.RED,
					HealthType.Worst,
					y_expander,
					y_expander_rot,
					x_expander);
			y_expander += Y_EXPANDER_DELTA;
			y_expander_rot += Y_EXPANDER_DELTA_ROT;
		}	
		max_powerup_collision_h = y_expander - ex01PowerupSingle.POWERUPS1_SIZEWH/4; 
		min_powerup_collision_b = y_expander - ex01PowerupSingle.POWERUPS1_SIZEWH/2;
		for(j = 0; j < pup_b1; j++){
			ex01PowerupSingleBullets(
					Color.GREEN,
					BulletType.Best,
					y_expander,
					y_expander_rot,
					x_expander);
			y_expander += Y_EXPANDER_DELTA;
			y_expander_rot += Y_EXPANDER_DELTA_ROT;
		}	
		for(j = 0; j < pup_b2; j++){
			ex01PowerupSingleBullets(
					Color.ORANGE,
					BulletType.Middle,
					y_expander,
					y_expander_rot,
					x_expander);
			y_expander += Y_EXPANDER_DELTA;
			y_expander_rot += Y_EXPANDER_DELTA_ROT;
		}
		for(j = 0; j < pup_b3; j++){
			ex01PowerupSingleBullets(
					Color.ORANGE,
					BulletType.Worst,
					y_expander,
					y_expander_rot,
					x_expander);
			y_expander += Y_EXPANDER_DELTA;
			y_expander_rot += Y_EXPANDER_DELTA_ROT;
		}
		max_powerup_collision_b = y_expander - ex01PowerupSingle.POWERUPS1_SIZEWH/4;
	}
	
	public void check_collision(
			Circle rect,
			float Y,
			ex01CryoHUDDisplay hud_display,
			ex01CryoshipPrincipial spaces,
			float blocker_collision_min_check_h,
			float blocker_collision_max_check_h,
			float blocker_collision_min_check_b,
			float blocker_collision_max_check_b){
		for(i=0; i<list_health_sprites.size(); i++){
			pwr_health = list_health_sprites.get(i);
			if(!pwr_health.already_captured_powerup){
				if(  (rect.y) > blocker_collision_min_check_h
				  && (rect.y) < blocker_collision_max_check_h  ){
					if(  pwr_health.collision_circle.y>Y-hscreendif10
					  && pwr_health.collision_circle.y<Y+hscreenadd){
						if(Intersector.overlaps(pwr_health.collision_circle, rect) )
						{	
							pwr_health.already_captured_powerup = true;
							pwr_health.can_explode_powerup = true;
							hud_display.StartHealthRotationProcedure(spaces, pwr_health.val_pup);
							is_poweup_eatenH[i] = true;
							pwr_health.v_rotator_alpha = 1f;
							pwr_health.value_rotator_general.setAlpha(1f);						
						}					
					}
				}
			}
		}
		for(i=0; i<list_bullets_sprites.size(); i++){
			pwr_bullets = list_bullets_sprites.get(i);
			if(!pwr_bullets.already_captured_powerup){
				if(  (rect.y) > blocker_collision_min_check_b
				  && (rect.y) < blocker_collision_max_check_b  ){
					if(   pwr_bullets.collision_circle.y>Y-hscreendif10
					   && pwr_bullets.collision_circle.y<Y+hscreenadd){
						if(Intersector.overlaps(pwr_bullets.collision_circle, rect))
						{	
							pwr_bullets.already_captured_powerup = true;
							pwr_bullets.can_explode_powerup = true;
							hud_display.StartBulletsRotationProcedure(spaces, pwr_bullets.val_pup);
							is_poweup_eaten[i] = true;
							pwr_bullets.v_rotator_alpha = 1f;
							pwr_bullets.value_rotator_general.setAlpha(1f);
						}					
					}
				}
			}
		}		
	}
	
	public void Update(float delta, float camera_y){		
		for(j = list_health_sprites.size() - 1; j >= 0; j--){
			if(is_poweup_busyH[j]){
				healths = list_health_sprites.get(j);
				if(is_poweup_eatenH[j]) rotate = true;
				healths.update(delta, PowerupAnimation, PowerupAnimationExplode, rotate);
				if(healths.collision_circle.y<camera_y-hscreenadd){
					is_poweup_busyH[j] = false;
					is_poweup_eatenH[j] = false;
					healths.current_angle_rotator = 0f; 
					healths.value_rotator_general.setRotation(0f);
				}
			}
		}	
		for(j = list_bullets_sprites.size() - 1; j >= 0; j--){
			if(is_poweup_busy[j]){
				bullets = list_bullets_sprites.get(j);
				if(is_poweup_eaten[j]) rotate = true;
				if(bullets.bullet_type == BulletType.Best) { 
					bullets.update(delta, PowerupAnimation, PowerupAnimationExplodeG, rotate);
				} else { 
					bullets.update(delta, PowerupAnimation, PowerupAnimationExplode, rotate); 
				}
				if(bullets.collision_circle.y<camera_y-hscreenadd){
					is_poweup_busy[j] = false;
					is_poweup_eaten[j] = false;
					bullets.current_angle_rotator = 0f;
					bullets.value_rotator_general.setRotation(0f);
				}
			}
		}		
	}
	
	public void Render(SpriteBatch batch, float Y){
		for(j = list_health_sprites.size() - 1; j >= 0; j--){
			pwr_up = list_health_sprites.get(j);
			if(is_poweup_busyH[j] && pwr_up.powerups1.getY()>Y-hscreendif10
								  && pwr_up.powerups1.getY()<Y+hscreenadd){
				if(!pwr_up.can_explode_powerup){
					pwr_up.powerups1.draw(batch);
					pwr_up.overlayer_general.draw(batch);
				} else {
					pwr_up.exploder_general.draw(batch);
					if(is_poweup_eatenH[j]) pwr_up.value_rotator_general.draw(batch);
				}
			}			
		}	
		for(j = list_bullets_sprites.size() - 1; j >= 0; j--){
			pwr_up = list_bullets_sprites.get(j);
			if(is_poweup_busy[j] && pwr_up.powerups1.getY()>Y-hscreendif10
							     && pwr_up.powerups1.getY()<Y+hscreenadd){
				if(!pwr_up.can_explode_powerup){
					pwr_up.powerups1.draw(batch);
					pwr_up.overlayer_general.draw(batch);					
				} else {
					pwr_up.exploder_general.draw(batch);
					if(is_poweup_eaten[j]) pwr_up.value_rotator_general.draw(batch);
				}
			}			
		}		
	}
	
	public void Dispose(){
		if(list_health_sprites!=null){
			for(int i=0; i<list_health_sprites.size(); i++){
				list_health_sprites.get(i).Dispose();
			}
			list_health_sprites.clear();
			list_health_sprites = null;
		}
		if(list_bullets_sprites!=null){
			for(int i=0; i<list_bullets_sprites.size(); i++){
				list_bullets_sprites.get(i).Dispose();
			}
			list_bullets_sprites.clear();
			list_bullets_sprites = null;
		}
		if(list_health_bullets_sprites!=null) {
			for (int i = 0; i < list_health_bullets_sprites.size(); i++) {
				list_health_bullets_sprites.get(i).Dispose();
			}
			list_health_bullets_sprites.clear();
			list_health_bullets_sprites = null;
		}
		PowerupAnimation = null;
		PowerupAnimationExplode = null;
		PowerupAnimationExplodeG = null;
		if(new_powerup!=null){
			new_powerup.Dispose();
			new_powerup = null;
		}
		if(pwr_health!=null){
			pwr_health.Dispose();
			pwr_health = null;
		}
		if(pwr_bullets!=null){
			pwr_bullets.Dispose();
			pwr_bullets = null;
		}
		if(healths!=null){
			healths.Dispose();
			healths = null;
		}
		if(bullets!=null){
			bullets.Dispose();
			bullets = null;
		}
		if(pwr_up!=null){
			pwr_up.Dispose();
			pwr_up = null;
		}
		if(texture_atlas_here!=null){
			texture_atlas_here.dispose();
			texture_atlas_here = null;
		}
		for(int i=0; i< PowerupExpandAnim.length; i++){
			PowerupExpandAnim[i] = null;
		}
		for(int i=0; i< PowerupExpandAnimExplode.length; i++){
			PowerupExpandAnimExplode[i] = null;
		}
		for(int i=0; i< PowerupExpandAnimExplodeG.length; i++){
			PowerupExpandAnimExplodeG[i] = null;
		}
		powerup_expland = null;
		powerup_explode = null;
		powerup_explodeG = null;
		healthr1 = null;
		healthr2 = null;
		healthr3 = null;
		bulletsr1 = null;
		bulletsr2 = null;
		bulletsr3 = null;
		s3erP_TR = null;
		s2erP_TR = null;
		s1erP_TR = null;
		s3erH_TR = null;
		s2erH_TR = null;
		s1erH_TR = null;
		green = null;
	}
}
