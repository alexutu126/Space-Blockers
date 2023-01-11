//WMS3 2015

package com.cryotrax.game;
import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;

//----------------------------------------------------------------

public class ex01StarsLayers {
	public static final float screen_hW = 800f / 480f * 25f;
	public static final float screen_hW2 = 800f / 480f * 25f / 2f;
	public static final float YMIN_SCREENHW_MIN10p2 = screen_hW - 10f / 2;
	public static final float Y_SCREENHW2_10 = screen_hW2 + 10f;
	public float world_h;
	public float world_w;
	public float world_h_min_half_screener;
	public float world_h_plus_half_screener;
	public float ratio;
	public float Y;
	public float size_star;
	public float alpha;
	public float randomValue;
	public int start_type;
	public int i;
	public OrthographicCamera star_camera;
	public TextureRegion starType1;
	public ArrayList<Sprite> stars;
	public Texture old_texture;
	public TextureRegion loader;
	public Sprite star;
	public Random rand;
	
	public void ex01StarsLayersReload(TextureAtlas atlas){
		if(start_type == 1){
			starType1 = atlas.findRegion("small_star");
			ratio = 1f;
		} else if(start_type == 3){
			starType1 = atlas.findRegion("small_star2");
			ratio = 1f;				
		} else if(start_type == 2){
			starType1 = atlas.findRegion("smaller_star");
			ratio = 1f;
		} 		
		atlas.getTextures().first().bind();
		for(i = 0; i < stars.size(); i++){
			stars.get(i).setRegion((TextureRegion)(starType1));
		}
	}
	
	public ex01StarsLayers(
			TextureAtlas atlas,
			int numberStars,
			float w_left,
			float w_width,
			float h_bottom,
			float h_height,
			float min_alpha,
			float max_alpha,
			float type_min,
			float type_max,
			int STAR_TYPE){
		ratio = 1f;
		start_type = STAR_TYPE;
		if(STAR_TYPE == 1){
			starType1 = atlas.findRegion("small_star");
			ratio = 1f;
		} else if(STAR_TYPE == 3){
			starType1 = atlas.findRegion("small_star2");
			ratio = 1f;				
		} else if(STAR_TYPE == 2){
			starType1 = atlas.findRegion("smaller_star");
			ratio = 1f;
		} 		
		stars = new ArrayList<Sprite>();			
		star_camera = new OrthographicCamera(25f, screen_hW);
		star_camera.position.set(15f, screen_hW2, 0f);
		star_camera.update();			
		GenerateStars(
				numberStars,
				w_left,
				w_width,
				h_bottom,
				h_height,
				min_alpha,
				max_alpha,
				type_min,
				type_max,
				ratio);
		world_h = h_height;
		world_w = w_width;
	}
	
	public void DrawStars(SpriteBatch batch){
		Y = star_camera.position.y;
		for(int i = 0; i < stars.size(); i++){
			if(stars.get(i).getY() > Y - YMIN_SCREENHW_MIN10p2
			&& stars.get(i).getY() < Y + Y_SCREENHW2_10){
				stars.get(i).draw(batch);
			}
		}
	}
	
	public void GenerateStars(
			int numberStars,
			float w_left,
			float w_width,
			float h_bottom,
			float h_height,
			float min_alpha,
			float max_alpha,
			float type_min,
			float type_max,
			float ratio){
		rand = new Random();		
		for(int i = 0; i < numberStars; i++){
			star = new Sprite(starType1);
			size_star = RandomFloatBetween(rand, type_min, type_max);
			alpha = RandomFloatBetween(rand, min_alpha, max_alpha);
			star.setSize(size_star, size_star * ratio);
			star.setPosition(
					RandomFloatBetween(rand, w_left, w_left + w_width),
					RandomFloatBetween(rand, h_bottom, h_bottom + h_height));
			star.setAlpha(alpha);
			stars.add(star);
		}
	}
	
	public float RandomFloatBetween(
			Random r,
			float rangeMin,
			float rangeMax){
		randomValue = rangeMin + (rangeMax - rangeMin) * r.nextFloat();
		return randomValue;
	}
	
	public void Dispose() {
		if(stars != null) {
			stars.clear();
		}
		star_camera = null;
		starType1 = null;
		if(old_texture!=null){
			old_texture.dispose();
			old_texture = null;
		}
		loader = null;
		star = null;
		rand = null;
	}
}