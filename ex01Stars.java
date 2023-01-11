//WMS3 2015

package com.cryotrax.game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//----------------------------------------------------------------

public class ex01Stars {
	public boolean stars_layers_on = true;
	public boolean stars_layers_cont_on = false;
	public boolean stars_layers_small_on = true;
	public boolean stars_layers_small_cont_on = false;
	public boolean stars_layers_big_on = true;
	public boolean stars_layers_big_cont_on = false;
	public ex01StarsLayers stars_layers;
	public ex01StarsLayers stars_layers_cont;	
	public ex01StarsLayers stars_layers_small;
	public ex01StarsLayers stars_layers_small_cont;
	public ex01StarsLayers stars_layers_big;
	public ex01StarsLayers stars_layers_big_cont;
	public static final float HALF_SCREENER = 800f / 480f * 25f / 2f;
	
	public void ex01StarsReload(TextureAtlas atlas){
		atlas.findRegion("smaller_star").getTexture().bind();
		stars_layers.ex01StarsLayersReload(atlas);	
		stars_layers_cont.ex01StarsLayersReload(atlas);
		stars_layers_small.ex01StarsLayersReload(atlas);	
		stars_layers_small_cont.ex01StarsLayersReload(atlas);
		stars_layers_big.ex01StarsLayersReload(atlas);	
		stars_layers_big_cont.ex01StarsLayersReload(atlas);
	}
	
	public ex01Stars(TextureAtlas atlas){
		stars_layers = new
				ex01StarsLayers(atlas, 160, 2.5f, 25f, 0f, 155f, 0.225f, 0.925f, 0.75f, 2.10f, 1);
		stars_layers_cont = new
				ex01StarsLayers(atlas, 160, 2.5f, 25f, 0f, 155f, 0.225f, 0.925f, 0.75f, 2.10f, 1);
		stars_layers_cont.star_camera.position
				.set(15f, 800f / 480f * 25f / 2f - 800f / 480f * 25f, 0f);

		stars_layers_small = new
				ex01StarsLayers(atlas, 200, 2.5f, 25f, 0f, 280f, 0.525f, 0.925f, 0.75f, 1.90f, 2);
		stars_layers_small_cont = new
				ex01StarsLayers(atlas, 200, 2.5f, 25f, 0f, 280f, 0.525f, 0.925f, 0.75f, 1.90f, 2);
		stars_layers_small_cont.star_camera.position
				.set(15f, 800f / 480f * 25f / 2f - 800f / 480f * 25f, 0f);

		stars_layers_big = new
				ex01StarsLayers(atlas, 175, 2.5f, 25f, 0f, 1030f, 0.725f, 0.925f, 1.05f, 2.55f, 2);
		stars_layers_big_cont = new
				ex01StarsLayers(atlas, 175, 2.5f, 25f, 0f, 1030f, 0.725f, 0.925f, 1.05f, 2.55f, 2);
		stars_layers_big_cont.star_camera.position
				.set(15f, 800f / 480f * 25f / 2f - 800f / 480f * 25f, 0f);

		stars_layers.world_h_min_half_screener =
				stars_layers.world_h - HALF_SCREENER;
		stars_layers.world_h_plus_half_screener =
				stars_layers.world_h + HALF_SCREENER;
		stars_layers_cont.world_h_min_half_screener =
				stars_layers_cont.world_h - HALF_SCREENER;
		stars_layers_cont.world_h_plus_half_screener =
				stars_layers_cont.world_h + HALF_SCREENER;
		stars_layers_small.world_h_min_half_screener =
				stars_layers_small.world_h - HALF_SCREENER;
		stars_layers_small.world_h_plus_half_screener =
				stars_layers_small.world_h + HALF_SCREENER;
		stars_layers_small_cont.world_h_min_half_screener =
				stars_layers_small_cont.world_h - HALF_SCREENER;
		stars_layers_small_cont.world_h_plus_half_screener =
				stars_layers_small_cont.world_h + HALF_SCREENER;
		stars_layers_big.world_h_min_half_screener =
				stars_layers_big.world_h - HALF_SCREENER;
		stars_layers_big.world_h_plus_half_screener =
				stars_layers_big.world_h + HALF_SCREENER;
		stars_layers_big_cont.world_h_min_half_screener =
				stars_layers_big_cont.world_h - HALF_SCREENER;
		stars_layers_big_cont.world_h_plus_half_screener =
				stars_layers_big_cont.world_h + HALF_SCREENER;
	}
	
	public void AdvanceWorldUpdateStarsBase(float velo){
		if(stars_layers_on){
			//activate the continuation camera (we'll have both now)
			if( stars_layers.star_camera.position.y > stars_layers.world_h_min_half_screener ){
				stars_layers_cont_on = true;
			} 
			//advance camera if we on screen otherwise if we use
			// the continuation camera and reset this one
			if( stars_layers.star_camera.position.y < stars_layers.world_h_plus_half_screener ){
				stars_layers.star_camera.position.y += velo / 0.75f;
			} else {
				stars_layers.star_camera.position.y  = -HALF_SCREENER;
				stars_layers_on = false;
			}
			stars_layers.star_camera.update();
		}
		if(stars_layers_cont_on){
			//activate the base camera (we'll have both now)			
			if( stars_layers_cont.star_camera.position.y >
					stars_layers.world_h_min_half_screener )
			{
				stars_layers_on = true;
			} 			
			//advance camera if we on screen otherwise if we use
			// the continuation camera and reset this one
			if( stars_layers_cont.star_camera.position.y <
					stars_layers.world_h_plus_half_screener )
			{
				stars_layers_cont.star_camera.position.y += velo / 0.75f;
			} else {
				stars_layers_cont.star_camera.position.y  = -HALF_SCREENER;
				stars_layers_cont_on = false;
			}			
			stars_layers_cont.star_camera.update();
		}		
	}
	
	public void AdvanceWorldUpdateStarsSmall(float velo_original){
		if(stars_layers_small_on){
			//activate the continuation camera (we'll have both now)			
			if( stars_layers_small.star_camera.position.y >
					stars_layers_small.world_h_min_half_screener )
			{
				stars_layers_small_cont_on = true;
			} 
			//advance camera if we on screen otherwise if we use
			// the continuation camera and reset this one
			if( stars_layers_small.star_camera.position.y <
					stars_layers_small.world_h_plus_half_screener )
			{
				stars_layers_small.star_camera.position.y += velo_original / 1.1;
			} else {
				stars_layers_small.star_camera.position.y  = -HALF_SCREENER;
				stars_layers_small_on = false;
			}
			stars_layers_small.star_camera.update();
		}
		if(stars_layers_small_cont_on){
			//activate the base camera (we'll have both now)			
			if( stars_layers_small_cont.star_camera.position.y >
					stars_layers_small_cont.world_h_min_half_screener )
			{
				stars_layers_small_on = true;
			} 			
			//advance camera if we on screen otherwise if we use
			// the continuation camera and reset this one
			if( stars_layers_small_cont.star_camera.position.y <
					stars_layers_small_cont.world_h_plus_half_screener )
			{
				stars_layers_small_cont.star_camera.position.y += velo_original / 1.1;
			} else {
				stars_layers_small_cont.star_camera.position.y  = -HALF_SCREENER;
				stars_layers_small_cont_on = false;
			}			
			stars_layers_small_cont.star_camera.update();
		}				
	}
	
	public void AdvanceWorldUpdateStarsBig(float velo){
		if(stars_layers_big_on){
			//activate the continuation camera (we'll have both now)		
			if( stars_layers_big.star_camera.position.y >
					stars_layers_big.world_h_min_half_screener )
			{
				stars_layers_big_cont_on = true;
			} 
			//advance camera if we on screen otherwise if we use
			// the continuation camera and reset this one
			if( stars_layers_big.star_camera.position.y <
					stars_layers_big.world_h_plus_half_screener )
			{
				stars_layers_big.star_camera.position.y += velo * 4;
			} else {
				stars_layers_big.star_camera.position.y  = -HALF_SCREENER;
				stars_layers_big_on = false;
			}
			stars_layers_big.star_camera.update();
		}
		if(stars_layers_big_cont_on){
			//activate the base camera (we'll have both now)		
			if( stars_layers_big_cont.star_camera.position.y >
					stars_layers_big_cont.world_h_min_half_screener )
			{
				stars_layers_big_on = true;
			} 			
			//advance camera if we on screen otherwise if we use
			// the continuation camera and reset this one
			if( stars_layers_big_cont.star_camera.position.y <
					stars_layers_big_cont.world_h_plus_half_screener )
			{
				stars_layers_big_cont.star_camera.position.y += velo * 4;
			} else {
				stars_layers_big_cont.star_camera.position.y  = -HALF_SCREENER;
				stars_layers_big_cont_on = false;
			}	
			stars_layers_big_cont.star_camera.update();
		}				
	}
	
	public void AdvanceWorldUpdateStars(float velo, float velo_original){
		AdvanceWorldUpdateStarsBase(velo);
		AdvanceWorldUpdateStarsSmall(velo_original);
		AdvanceWorldUpdateStarsBig(velo);
	}

	public void Draw(SpriteBatch batch){
		if(stars_layers_on){
			batch.setProjectionMatrix(stars_layers.star_camera.combined);
			stars_layers.DrawStars(batch);
		}
		if(stars_layers_cont_on){
			batch.setProjectionMatrix(stars_layers_cont.star_camera.combined);
			stars_layers_cont.DrawStars(batch);				
		}	
		if(stars_layers_small_on){
			batch.setProjectionMatrix(stars_layers_small.star_camera.combined);
			stars_layers_small.DrawStars(batch);
		}
		if(stars_layers_small_cont_on){
			batch.setProjectionMatrix(stars_layers_small_cont.star_camera.combined);
			stars_layers_small_cont.DrawStars(batch);				
		}		
		if(stars_layers_big_on){
			batch.setProjectionMatrix(stars_layers_big.star_camera.combined);
			stars_layers_big.DrawStars(batch);
		}
		if(stars_layers_big_cont_on){
			batch.setProjectionMatrix(stars_layers_big_cont.star_camera.combined);
			stars_layers_big_cont.DrawStars(batch);				
		}				
	}
	
	public void Dispose(){
		if(stars_layers!=null){
			stars_layers.Dispose();
			stars_layers = null;
		}
		if(stars_layers_cont!=null){
			stars_layers_cont.Dispose();
			stars_layers_cont = null;
		}
		if(stars_layers_small!=null){
			stars_layers_small.Dispose();
			stars_layers_small = null;
		}
		if(stars_layers_small_cont!=null){
			stars_layers_small_cont.Dispose();
			stars_layers_small_cont = null;
		}
		if(stars_layers_big!=null){
			stars_layers_big.Dispose();
			stars_layers_big = null;
		}
		if(stars_layers_big_cont!=null){
			stars_layers_big_cont.Dispose();
			stars_layers_big_cont = null;
		}
	}
}
