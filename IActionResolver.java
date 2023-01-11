//WMS3 2015

package com.cryotrax.game;

public interface IActionResolver {
    public int requestIabPurchase(int product, PurchaseCallback callback);
    public boolean isThisPremiumUpdated();
    public boolean isThisLevelsUnlocked();
    public void ActAsIfWeJustPurchasedPremium(PurchaseCallback purchase_call);
    public static final int FAILED_PURCHASE = 0;
    public static final int SUCCESS_PURCHASE = 100;
    public static final int PRODUCT_PREMIUM = 1;
    public static final int PRODUCT_UNLOCK_LEVELS = 2;
    public static final int PRODUCT_COINS100 = 3;
    public static final int PRODUCT_COINS250 = 4;
    public static final int PRODUCT_COINS500 = 5;
    public static final int PRODUCT_COINS750 = 6;
    public static final int PRODUCT_COINS1000 = 7;
    public static final int PRODUCT_COINS1500 = 8;
    public static final int PRODUCT_AMMO9000 = 9;
    public static final int PRODUCT_LIFE9000 = 10;
}
