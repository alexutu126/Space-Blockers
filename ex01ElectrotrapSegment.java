//WMS3 2015

package com.cryotrax.game;

import com.badlogic.gdx.math.Vector2;

//----------------------------------------------------------------

public class ex01ElectrotrapSegment{
	public Vector2 start;  // origin vector
	public Vector2 end;    // length vector
	public boolean faded;  // are branches drawn with alpha ?
	
	public ex01ElectrotrapSegment(Vector2 x, Vector2 y, boolean fade){
		this.start = x;
		this.end = y;
		this.faded = fade;
	}
}
