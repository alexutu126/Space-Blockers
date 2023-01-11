//WMS3 2015

package com.cryotrax.game;

public interface ExitError {
	public void loadVALUELongFull();
	public void loadVALUEShortFull();
	public void showVALUEye(Runnable then);
	public void showVALUEyeShort(Runnable then);
	public void LoadInMidGameVALUE();
	public void LoadInMidGameVALUEShort();
	public boolean IsLoadedVALUEMainGame();
	public boolean IsLoadedVALUEMainGameShort();
	public boolean isWiFiConnected();
}