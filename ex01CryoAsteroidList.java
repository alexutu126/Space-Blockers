//WMS3 2015

package com.cryotrax.game;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class ex01CryoAsteroidList {
	public static final double ASTEROID_Y_SPACE = 25f;
	public static final float ASTEROID_LEFT_DIRECTION_CORRECTION = +65f;
	public static final float ASTEROID_RIGHT_DIRECTION_CORRECTION = +130f;
	public static final float LEFT_MARGIN = 2.5f;
	public static final float RIGHT_MARGIN = 27.5f;
	public static final float DISTANCER = 2f;
	public static final float ASTEROID_HEIGHT_WIDTH1 = 2.5f;
	public static final float ASTEROID_HEIGHT_WIDTH2 = 3.5f;
	public static final float ASTEROID_HEIGHT_WIDTH3 = 4.5f;
	public static final float ASTEROID_HEIGHT_WIDTH4 = 5.5f;
	public static final float hscreenadd10 = 800f / 480f * 25f / 2f /*+ 10f*/;
	public static final float hscreendif10 = 800f / 480f * 25f      /*- 10f*/;
	public static final float hscreenXDIFFER_LEFT =
			LEFT_MARGIN - ASTEROID_HEIGHT_WIDTH4 * 1.5f;
	public static final float hscreenXDIFFER_RIGHT =
			RIGHT_MARGIN + ASTEROID_HEIGHT_WIDTH4* 1.5f;
	public float current_size_wh; 
	public float asteroid_posY;
	public float asteroid_posX;
	public float angle;
	public int random_int;
	public int i;	
	public double number_asteroids; 
	public ArrayList<Float>	asteroid_speedX;
	public ArrayList<Float>	asteroid_speedY;
	public ArrayList<Float> asteroid_angle;
	public ArrayList<Float>	rotation_speed;
	public ArrayList<Sprite> asteroid_sprite;
	public TextureRegion asteroid_region1;
	public TextureRegion asteroid_region2;
	public TextureRegion asteroid_region3;
	public TextureRegion asteroid_region4;
	public TextureRegion region;
	public Vector2 direction_start;
	public Vector2 direction_end;
	public Vector2 direction;
	public Sprite sprite;
	public Random randomizer; 

	public ex01CryoAsteroidList(float base_Y, float lengthY, TextureAtlas atlas){
		asteroid_speedX = new ArrayList<Float>();
		asteroid_speedY = new ArrayList<Float>();
		asteroid_angle = new ArrayList<Float>();
		rotation_speed = new ArrayList<Float>();
		asteroid_sprite = new ArrayList<Sprite>();
		asteroid_region1 = atlas.findRegion("asteroid1");
		asteroid_region2 = atlas.findRegion("asteroid2");
		asteroid_region3 = atlas.findRegion("asteroid3");
		asteroid_region4 = atlas.findRegion("asteroid4");
		randomizer = new Random();
		direction_start = new Vector2();
		direction_end = new Vector2();
		number_asteroids = Math.ceil((double)lengthY / ASTEROID_Y_SPACE * 1.3f );	
		for(i = 0; i < number_asteroids; i++){
			// what POSITION should it be? left or right? AND what angle
			// do we have for the direction?
			direction_start.x = 0f; direction_start.y = 0f;
			direction_end.x = 1f; direction_end.y = 0;
			direction = direction_end.sub(direction_start);
			random_int = randInt(-1, 0, randomizer);
			if(random_int==0){
				// lets put it on the left
				asteroid_posX = LEFT_MARGIN - current_size_wh - DISTANCER;
				asteroid_posY =
						RandomFloatBetween(randomizer, base_Y, lengthY*1.1f);
				direction.rotate(
						RandomFloatBetween(randomizer, 0, -90) +
								ASTEROID_LEFT_DIRECTION_CORRECTION);
			} else {					
				// lets put it on the right
				asteroid_posX = RIGHT_MARGIN + DISTANCER;
				asteroid_posY = RandomFloatBetween(randomizer, base_Y, lengthY*1.1f);
				direction.rotate(RandomFloatBetween(randomizer, 0, 90) +
						ASTEROID_RIGHT_DIRECTION_CORRECTION);
			}					
			asteroid_speedY.add(RandomFloatBetween(randomizer, 1f, 7f) *
					(float)MathUtils.sin(MathUtils.degreesToRadians*direction.angle()));
			asteroid_speedX.add(RandomFloatBetween(randomizer, 1f, 7f) *
					(float)MathUtils.cos(MathUtils.degreesToRadians*direction.angle()));

			// let's choose a random TextureRegion
			random_int = randInt(1, 4, randomizer);
			if(random_int==1){
				region = asteroid_region1;
			} else if(random_int==2){
				region = asteroid_region2;
			} else if(random_int==3){
				region = asteroid_region3;
			} else if(random_int==4){
				region = asteroid_region4;
			}
			// what SIZE should it be?
			random_int = randInt(1, 4, randomizer);
			if(random_int==1){
				sprite = new Sprite(region);
				sprite.setSize(ASTEROID_HEIGHT_WIDTH1, ASTEROID_HEIGHT_WIDTH1);
				sprite.setPosition(asteroid_posX, asteroid_posY);
				sprite.setOrigin(sprite.getWidth()/2f, sprite.getHeight()/2f);
				asteroid_sprite.add(sprite);
				current_size_wh = ASTEROID_HEIGHT_WIDTH1;
			} else if(random_int==2){
				sprite = new Sprite(region);
				sprite.setSize(ASTEROID_HEIGHT_WIDTH2, ASTEROID_HEIGHT_WIDTH2);
				sprite.setPosition(asteroid_posX, asteroid_posY);
				sprite.setOrigin(sprite.getWidth()/2f, sprite.getHeight()/2f);
				asteroid_sprite.add(sprite);
				current_size_wh = ASTEROID_HEIGHT_WIDTH2;
			} else if(random_int==3){
				sprite = new Sprite(region);
				sprite.setSize(ASTEROID_HEIGHT_WIDTH3, ASTEROID_HEIGHT_WIDTH3);
				sprite.setPosition(asteroid_posX, asteroid_posY);
				sprite.setOrigin(sprite.getWidth()/2f, sprite.getHeight()/2f);
				asteroid_sprite.add(sprite);
				current_size_wh = ASTEROID_HEIGHT_WIDTH3;
			} else if(random_int==4){
				sprite = new Sprite(region);
				sprite.setSize(ASTEROID_HEIGHT_WIDTH4, ASTEROID_HEIGHT_WIDTH4);
				sprite.setPosition(asteroid_posX, asteroid_posY);
				sprite.setOrigin(sprite.getWidth()/2f, sprite.getHeight()/2f);
				asteroid_sprite.add(sprite);
				current_size_wh = ASTEROID_HEIGHT_WIDTH4;
			}
			// what is the angular speed
			rotation_speed.add(RandomFloatBetween(randomizer, -155f, 155f));
		}
	}
	
	public void ex01CryoAsteroidListReloadPositions(float base_Y, float lengthY){
		randomizer = new Random();
		for(i = 0; i < number_asteroids; i++){
			// what POSITION should it be? left or right? AND what angle
			// do we have for the direction?
			direction_start.x = 0f; direction_start.y = 0f;
			direction_end.x = 1f; direction_end.y = 0;
			direction = direction_end.sub(direction_start);
			random_int = randInt(-1, 0, randomizer);
			if(random_int==0){
				// lets put it on the left
				asteroid_posX = LEFT_MARGIN - current_size_wh - DISTANCER;
				asteroid_posY = RandomFloatBetween(randomizer, base_Y, lengthY*1.1f);
				direction.rotate(RandomFloatBetween(randomizer, 0, -90) +
						ASTEROID_LEFT_DIRECTION_CORRECTION);
			} else {					
				// lets put it on the right
				asteroid_posX = RIGHT_MARGIN + DISTANCER;
				asteroid_posY = RandomFloatBetween(randomizer, base_Y, lengthY*1.1f);
				direction.rotate(RandomFloatBetween(randomizer, 0, 90) +
						ASTEROID_RIGHT_DIRECTION_CORRECTION);
			}					
			asteroid_speedY.set(i, new Float(RandomFloatBetween(randomizer, 1f, 7f) *
					(float)MathUtils.sin(MathUtils.degreesToRadians*direction.angle())));
			asteroid_speedX.set(i, new Float(RandomFloatBetween(randomizer, 1f, 7f) *
					(float)MathUtils.cos(MathUtils.degreesToRadians*direction.angle())));
			// position
			asteroid_sprite.get(i).setPosition(asteroid_posX, asteroid_posY);
			// what is the angular speed
			rotation_speed.set(i, RandomFloatBetween(randomizer, -155f, 155f)); 
		}
	}
	
	public void update(float delta, float Y){
		for(i=0; i<asteroid_sprite.size(); i++){
			sprite = asteroid_sprite.get(i);
			if((sprite.getY()>Y-hscreendif10 && sprite.getY()<Y+hscreenadd10) && 
			   (sprite.getX()>hscreenXDIFFER_LEFT && sprite.getX()<hscreenXDIFFER_RIGHT) ){
				sprite.setX(sprite.getX() + asteroid_speedX.get(i) * delta);
				sprite.setY(sprite.getY() + asteroid_speedY.get(i) * delta);
				sprite.rotate(rotation_speed.get(i) * delta);
			}
		}
	}
	
	public void Render(SpriteBatch batch, float Y){
		for(i=0; i<asteroid_sprite.size(); i++){
			sprite = asteroid_sprite.get(i);
			if((sprite.getY()>Y-hscreendif10 && sprite.getY()<Y+hscreenadd10) && 
			   (sprite.getX()>hscreenXDIFFER_LEFT && sprite.getX()<hscreenXDIFFER_RIGHT) ){
				sprite.draw(batch);
			}
		}		
	}
	
	public static int randInt(int min, int max, Random rand) {
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	public static float RandomFloatBetween(Random r, float rangeMin, float rangeMax){
		float randomFloatValueXY = rangeMin + (rangeMax - rangeMin) * r.nextFloat();
		return randomFloatValueXY;
	}
	
	public void Dispose(){
		if(asteroid_speedX!=null) {
			asteroid_speedX.clear();
			asteroid_speedX = null;
		}
		if(asteroid_speedY!=null) {
			asteroid_speedY.clear();
			asteroid_speedY = null;
		}
		if(asteroid_angle!=null) {
			asteroid_angle.clear();
			asteroid_angle = null;
		}
		if(rotation_speed!=null) {
			rotation_speed.clear();
			rotation_speed = null;
		}
		if(asteroid_sprite!=null) {
			asteroid_sprite.clear();
			asteroid_sprite = null;
		}
		asteroid_region1 = null;
		asteroid_region2 = null;
		asteroid_region3 = null;
		asteroid_region4 = null;
		region = null;
		direction_start = null;
		direction_end = null;
		direction = null;
		sprite = null;
		randomizer = null;
	}
}
