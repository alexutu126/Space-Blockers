//WMS3 2015

package com.cryotrax.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

public class ex01LightningBolt {
	public static final float BOLT_ANIMATION_TIMER = 0.175f;
	public static final float MAX_Y_SCALE = 1.6f;
	public static final float MIN_Y_SCALE = 0.7f;
	public boolean scale_bigger = true;
	public boolean scale_biggerR = true;
	public Animation boltAnimation;
	public Animation boltAnimationR;
	public Sprite boltSprite;  //bolts will be middle Y start and end
	public Sprite boltSpriteR; //bolts will be middle Y start and end
	public TextureRegion[] boltTextures;
	public TextureRegion[] boltTexturesR;
	public float scaleY;
	public float scaleX;
	public float rotation;
	public float bolt_alpha = 1f;
	public float counterAnimation;
	public float scaleYR;
	public float scaleXR;
	public float rotationR;
	public float bolt_alphaR = 0.5f;
	public float counterAnimationR;	
	public Vector2 startBolt;
	public Vector2 endBolt;
	public Vector2 startBoltR;
	public Vector2 endBoltR;	
	public float angleRad;
	public float angleDeg;
	public float delta_scale;
	public float delta_alpha;
	
	public ex01LightningBolt(TextureAtlas atlas, Vector2 start, Vector2 end, float height){
		boltTextures = new TextureRegion[3];
		boltTexturesR = new TextureRegion[3];
		boltTextures[0] = atlas.findRegion("lbolt001");
		boltTextures[1] = atlas.findRegion("lbolt002");
		boltTextures[2] = atlas.findRegion("lbolt003");
		boltTexturesR[0] = atlas.findRegion("lbolt003");
		boltTexturesR[1] = atlas.findRegion("lbolt002");
		boltTexturesR[2] = atlas.findRegion("lbolt001");
		boltAnimation = new Animation(BOLT_ANIMATION_TIMER,
									  boltTextures);
		boltAnimationR = new Animation(BOLT_ANIMATION_TIMER,
									   boltTexturesR);
		boltSprite = new Sprite(boltTextures[0]);
		boltSprite.setPosition(start.x,
							   start.y-height/2f);
		boltSprite.setSize(end.x-start.x,
						   height);
		boltSprite.setOrigin(0f,
							 height/2f);
		boltSpriteR = new Sprite(boltTexturesR[0]);
		boltSpriteR.setPosition(start.x + (end.x-start.x) - 2.7f,
							    start.y-height/2f);
		boltSpriteR.setSize(-(end.x-start.x),
						     height);
		boltSpriteR.setOrigin(0f,
				              height/2f);
		rotation = 0f;
		scaleY = 1f;
		scaleX = 1f;
		startBolt = new Vector2(start);
		endBolt = new Vector2(end);
		rotationR = 0f;
		scaleYR = 1f;
		scaleXR = 1f;
		startBoltR = new Vector2(start);
		endBoltR = new Vector2(end);		
	}
	
	public void PositionAndScaleBoltToSELeft(
			Vector2 start,
			Vector2 end,
			float drawer_scaler){

		//we need to scale and rotate BUT we scale after we rotate
		angleRad = MathUtils.atan2(end.y-start.y, end.x-start.x)
				  *MathUtils.radiansToDegrees;
		scaleX = start.dst(end) / startBolt.dst(endBolt);
		scaleY *= scaleX;
		boltSprite.setScale(scaleX*drawer_scaler);		
		rotation = angleDeg;		
		boltSprite.setRotation(angleRad);
	}

	public void PositionAndScaleBoltToSERight(
			Vector2 start,
			Vector2 end,
			float drawer_scaler){

		//we need to scale and rotate BUT we scale after we rotate
		angleRad = MathUtils.atan2(end.y-start.y, end.x-start.x)
				  *MathUtils.radiansToDegrees;
		scaleXR = start.dst(end) / startBoltR.dst(endBoltR);
		scaleYR *= scaleXR;
		boltSpriteR.setScale(scaleXR*drawer_scaler);		
		rotationR = angleDeg;		
		boltSpriteR.setRotation(angleRad);
	}	
	
	public void UpdateLightningBoltAnimationLeft(
			float counterAnimationUp,
			float delta,
			float delta_scale,
			float delta_alpha){

		if(scale_bigger){
			scaleY+=delta_scale; 
		} else { scaleY-=delta_scale; }
		if(scaleY>=MAX_Y_SCALE){
			scale_bigger = false;
			scaleY = MAX_Y_SCALE - 0.001f;
		} else if(scaleY<=MIN_Y_SCALE){
			scale_bigger = true;
			scaleY = MIN_Y_SCALE + 0.001f;
		}
		bolt_alpha -= delta_alpha*2f;
		if(bolt_alpha<0.1f) {
			bolt_alpha = 1f;		
		}	
		boltSprite.setAlpha(bolt_alpha);			
		counterAnimation+=delta;
		boltSprite.setRegion(boltAnimation.getKeyFrame(counterAnimationUp, true));
	}
	
	public void UpdateLightningBoltAnimationRight(
			float counterAnimationUp,
			float delta,
			float delta_scale,
			float delta_alpha){

		if(scale_biggerR){
			scaleYR+=delta_scale; 
		} else { scaleYR-=delta_scale; }
		if(scaleYR>=MAX_Y_SCALE){
			scale_biggerR = false;
			scaleYR = MAX_Y_SCALE - 0.001f;
		} else if(scaleYR<=MIN_Y_SCALE){
			scale_biggerR = true;
			scaleYR = MIN_Y_SCALE + 0.001f;
		}
		bolt_alphaR -= delta_alpha;
		if(bolt_alphaR<0.1f) {
			bolt_alphaR = 1f;
		}
		boltSpriteR.setAlpha(bolt_alphaR);			
		counterAnimationR+=delta;
		boltSpriteR.setRegion(boltAnimationR.getKeyFrame(counterAnimationUp, true));
	}
	
	public void UpdateLightningBoltAnimation(float counterAnimationUp, float delta){
		delta_scale = 0.35f * delta;
		delta_alpha = 0.95f * delta;
		UpdateLightningBoltAnimationLeft(
				counterAnimationUp,
				delta,
				delta_scale,
				delta_alpha);
		UpdateLightningBoltAnimationRight(
				counterAnimationUp,
				delta,
				delta_scale,
				delta_alpha);
	}

	public void Dispose(){
		boltAnimation = null;
		boltAnimationR = null;
		boltSprite = null;
		boltSpriteR = null;
		for(int i=0; i<boltTextures.length; i++){
			boltTextures[i] = null;
		}
		for(int i=0; i<boltTexturesR.length; i++){
			boltTexturesR[i] = null;
		}
		startBolt = null;
		endBolt = null;
		startBoltR = null;
		endBoltR = null;
	}
}
