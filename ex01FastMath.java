//WMS3 2015

package com.cryotrax.game;

public class ex01FastMath {
	private static final int 	BIG_ENOUGH_INT = 16 * 1024;
	private static final double BIG_ENOUGH_FLOOR = BIG_ENOUGH_INT;
	private static final double BIG_ENOUGH_ROUND = BIG_ENOUGH_INT + 0.5;
	
	public static int fastFloor(float x){
		return (int)(x + BIG_ENOUGH_FLOOR) - BIG_ENOUGH_INT;		
	}
	
	public static int fastRound(float x){
		return (int)(x + BIG_ENOUGH_ROUND) - BIG_ENOUGH_INT;		
	}	
	
	public static int fastCeil(float x){
		return BIG_ENOUGH_INT - (int)(BIG_ENOUGH_FLOOR-x); 		
	}
}
