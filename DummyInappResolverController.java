//WMS3 2015

package com.cryotrax.game;

public class DummyInappResolverController implements IActionResolver {
    @Override
    public int requestIabPurchase(int product, PurchaseCallback callback) {
        return 0;
    }

    @Override
    public boolean isThisPremiumUpdated() {
        return false;
    }

    @Override
    public boolean isThisLevelsUnlocked() {
        return false;
    }

    @Override
    public void ActAsIfWeJustPurchasedPremium(PurchaseCallback purchase_call) {

    }
}
