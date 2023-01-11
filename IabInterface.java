//WMS3 2015

package com.cryotrax.game;

public interface IabInterface {
	public String SKU_PREMIUM_VERSION = "premium_game_product";
	public String SKU_UNLOCK_LEVELS = "unlock_levels";
	public String SKU_COINS_100  = "coins_100_managed";
	public String SKU_COINS_250  = "coins_250_managed";
	public String SKU_COINS_500  = "coins_500_managed";
	public String SKU_COINS_750  = "coins_750_managed";
	public String SKU_COINS_1000 = "coins_1000_managed";
	public String SKU_COINS_1500 = "coins_1500_managed";
	public String SKU_AMMO9000 = "ingame_ammo9000";
	public String SKU_LIFE9000 = "ingame_life9000";
	public String SKU_STATIC_PURCHASED = "android.test.purchased";
	public void getPremiumVersion();
	public void getUnlockLevels();
	public void getCoins100();
	public void getCoins250();
	public void getCoins500();
	public void getCoins750();
	public void getCoins1000();
	public void getCoins1500();
	public void getAmmo9000();
	public void getLife9000();
}
