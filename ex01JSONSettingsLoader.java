//WMS3 2015

package com.cryotrax.game;
import java.io.IOException;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class ex01JSONSettingsLoader {
	private FileHandle handle;
	public Json json;
	public json_settings settings;
	private final static int NUM_LEVELS = 96;
		
	public ex01JSONSettingsLoader() {
		handle = Gdx.files.local("settings/json_settings.json");
		json = new Json();
		json.setElementType(json_settings.class, "levels", level_data.class);
		if(!handle.exists())
		{
			CreateJsonFile();
			CreateBaseJson();
			WriteBaseJson();
		} else {
			ReadJson();
		}
	}

	/** Serializes this SaveGame to an array of bytes. */
	public byte[] toBytes() {
		return json.prettyPrint(settings).getBytes();
	}
	
	public void ReadJson(){
	    settings = json.fromJson(json_settings.class, handle.readString());
	}
	
	public static class json_settings{
		//*************************FOR FREE AND PREMIUM VERSIONS ***********************************
		public static final boolean is_this_for_free = true;

		//---------- ACHIEVEMENTS ----------//
		public boolean is_achievement_16 = false;
		public boolean is_achievement_32 = false;
		public boolean is_achievement_48 = false;
		public boolean is_achievement_64 = false;
		public boolean is_achievement_1 = false;
		public boolean is_achievement_2 = false;
		public boolean is_achievement_3 = false;

		//it will take 3 new game presses before we ask the player to watch the AD in the menus
		public static final int NEW_GAME_ADVIEW_TIME_ASK = 2;
		//we show ads after first 10 start-ups so we don't make the player unhappy
		public static final int NEW_GAME_ADVIEW_DELAY_X_TIMES_STARTER = 0;
		public int new_game_adview_delay_x_counter = 2;
		//it will take 5 level restarts before we are asked the player to watch the AD in game
		public static final int IN_GAME_ADVIEW_TIME_ASK = 2;
		//counter for how many times we passed ad view for newgame
		public int new_game_adview_time_ask_counter = 0;
		//counter for how many times we passed ad view for ingame
		public int in_game_adview_time_ask_counter = 0;
		//currently level unlocked
		public int no_level_unlocked = 1;
		public static final int COUNTER_AMMO_FIVER = 5;
		public static final int COUNTER_LIFE_FIVER = 10;
		public static final int OVER_BASE_MIN_ANG1 = 6;
		public static final int OVER_BASE_MIN_ANG2 = 4;
		public static final int OVER_BASE_MIN_ANG3 = 0;
		//how much coins to buy CRYO..? ups, this one is free
		public final int CRYO10_PRICE = 0;
		//how much coins to buy CRYO12 Space-craft?
		public final int CRYO12_PRICE = 750;
		//how much coins to buy CRYO14 Space-craft?
		public final int CRYO14_PRICE = 1000;
		//how much coins to buy 100 life?
		public final int HEALTH100_PRICE = 100;
		//how much coins to buy 250 life?
		public final int HEALTH250_PRICE = 200;
		//how much coins to buy 500 life?
		public final int HEALTH500_PRICE = 350;
		//how much coins to buy 1000 life?
		public final int HEALTH1000_PRICE = 500;
		//how much coins to buy 100 bullets?
		public final int BULLETS100_PRICE = 100;
		//how much coins to buy 250 bullets?
		public final int BULLETS250_PRICE = 200;
		//how much coins to buy 500 bullets?
		public final int BULLETS500_PRICE = 350;
		//how much coins to buy 1000 bullets?
		public final int BULLETS1000_PRICE = 500;
		//this is the ammo slots when we start up the game for the first time
		public int counter_ammo_base = 25;
		//this is the life slots when we start up the game for the first time
		public int counter_life_base = 25;
		//this is the ammo per-slot-ammo-bullets at level start if we have ammo slots
		public int counter_ammo_fiver = 5;
		//this is the life per-slot-electron-lifes at level start if we have life slots
		public int counter_life_fiver = 10;
		//this is the ammo slots when we start up the game and we previously had no bullets
		public int counter_ammo_base_limited = 0;
		//this is the life slots when we start up the game and we previously had no
		public int counter_life_base_limited = 0;
		//this is the ammo per-slot-ammo-bullets at level start if we previously had no bullets
		public int counter_ammo_fiver_limited = 5;
		//this is the life per-slot-electron-lifes at level start if we previously had no life
		//because we died too many times
		public int counter_life_fiver_limited = 5;
		//used when ammo slots is zero to give the user his last bullets
		public int last_ammo_fiver_no = 0;
		//used when life slots is zero to give the user his last life slots
		public int last_life_fiver_no = 0;
		public boolean sounds_on = true;
		//we're starting level with counter_life_base_limited and counter_life_fiver_limited
		public boolean were_currently_limited_on_life = false;
		//we're starting level with counter_ammo_base_limited and counter_ammo_fiver_limited
		public boolean were_currently_limited_on_ammo = false;
		//can buy means we have enough coins
		public boolean can_buy_cryozl10 = true;
		//can buy means we have enough coins
		public boolean can_buy_cryozl12 = true;
		//can buy means we have enough coins
		public boolean can_buy_cryozl14 = false;
		//means we already bought the spaceship
		public boolean bought_cryozl10 = true;
		//means we already bought the spaceship
		public boolean bought_cryozl12 = true;
		//means we already bought the spaceship
		public boolean bought_cryozl14 = false;
		//this is used for SETTINGS TRANSFER TO LOADED GAME when we buy before we click load saved
		public boolean bought_when_in_need_load_game = false;
		//selected means we bought and then we pressed select(use_craft) to use the space-ship
		public boolean selected_cryozl10 = false;
		//selected means we bought and then we pressed select(use_craft) to use the space-ship
		public boolean selected_cryozl12 = true;
		//selected means we bought and then we pressed select(use_craft) to use the space-ship
		public boolean selected_cryozl14 = false;
		//means the (use_craft) button is active because the space-ship is bought
		public boolean can_select_cryozl10 = true;
		//means the (use_craft) button is active because the space-ship is bought
		public boolean can_select_cryozl12 = true;
		//means the (use_craft) button is active because the space-ship is bought
		public boolean can_select_cryozl14 = false;
		//has the user finished the training session ? - not used currently as
		//level 1 will be training
		public boolean has_finished_help = false;
		//premium updated (all spaceships, 1000 life, 1000 ammo, all spaceships) 1$
		public boolean is_this_premium_updated = false;
		//unlock all levels for 1$
		public boolean is_this_unlocked_all_levels = false;
		//load saved game from cloud
		public boolean is_this_in_need_for_load_saved = false;
		//if the user signs out don't log in each game start
		public boolean user_doesnt_want_signed_in = false;
		public int counter_bullets_fired = 0;
		//What Space-craft do we use currently?
		public int index_spaceship_selected = 1;
		//How much coins do we own?
		public int number_coins = 0;
		//How much life-slots do we own?
		public int number_lifeslots = 0;
		//How much ammo-slots do we own?
		public int number_ammo = 0;
		public double[] level_angles =
				{ 90.0, 90.0, 90.0, 90.0, 90.0, 90.0, 90.0, 90.0,
				  90.0, 90.0, 90.0 ,90.0, 90.0, 90.0, 90.0, 90.0,
				  90.0, 90.0, 90.0, 90.0, 90.0, 90.0, 90.0, 90.0,
				  90.0, 90.0, 90.0 ,90.0, 90.0, 90.0, 90.0, 90.0,
				  90.0, 90.0, 90.0, 90.0, 90.0, 90.0, 90.0, 90.0,
				  90.0, 90.0, 90.0 ,90.0, 90.0, 90.0, 90.0, 90.0,
				  90.0, 90.0, 90.0, 90.0, 90.0, 90.0, 90.0, 90.0,
				  90.0, 90.0, 90.0 ,90.0, 90.0, 90.0, 90.0, 90.0,
		          90.0, 90.0, 90.0, 90.0, 90.0, 90.0, 90.0, 90.0,
				  90.0, 90.0, 90.0 ,90.0, 90.0, 90.0, 90.0, 90.0,
	              90.0, 90.0, 90.0, 90.0, 90.0, 90.0, 90.0, 90.0,
				  90.0, 90.0, 90.0 ,90.0, 90.0, 90.0, 90.0, 90.0 };
		public short[] level_wincoins =
				{  3,  3,  3,  3,  3,  3,  3,  3,  4,  4,  4 , 4,  4,  4,  4,  4,
				   6,  6,  6 , 6,  6,  6,  6,  6,  7,  7,  7,  7,  7,  7,  7,  7,
				   9,  9,  9,  9,  9,  9,  9,  9, 10, 10, 10, 10, 10, 10, 10, 10,
				  12, 12, 12, 12, 12, 12, 12, 12, 13, 13, 13, 13, 13, 13, 13, 13,
				  15, 15, 15, 15, 15, 15, 15, 15, 16, 16, 16, 16, 16, 16, 16, 16,
				  18, 18, 18, 18, 18, 18, 18, 18, 20, 20, 20, 20, 20, 20, 20, 20 };
		public short[] level_notries =
				{ 01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01 ,01, 01, 01, 01, 01,
				  01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01 ,01, 01, 01, 01, 01,
				  01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01 ,01, 01, 01, 01, 01,
				  01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01 ,01, 01, 01, 01, 01,
				  01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01 ,01, 01, 01, 01, 01,
				  01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01 ,01, 01, 01, 01, 01 };
		public int[] level_rank =
				{ 01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01 ,01, 01, 01, 01, 01,
				  01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01 ,01, 01, 01, 01, 01,
				  01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01 ,01, 01, 01, 01, 01,
				  01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01 ,01, 01, 01, 01, 01,
				  01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01 ,01, 01, 01, 01, 01,
				  01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01 ,01, 01, 01, 01, 01 };
		//---	
		public Array<level_data> levels = new Array<level_data>();
		public json_settings(){}
		//--- RATE MY GAME VARIABLES
		public final static int DAYS_UNTIL_PROMPT = 2;
		public final static int LAUNCHES_UNTIL_PROMPT = 10;
		public long launch_count = 0;
		public long date_firstLaunch = 0;
		// If true, both the specified number of days and number of launches must occur before
		// the dialog will be presented to the user. Otherwise, it's just one or the other.
		public final static boolean DAYS_AND_LAUNCHES = true;
		public boolean rate_never = false;
	}

	public void CreateJsonFile(){
		try {
			handle.file().createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	 	}		
	}
	
	public void WriteBaseJson(){
      	handle.writeString(json.prettyPrint(settings), false);
	}
	
	public void WriteJson(){
	    handle.writeString(json.prettyPrint(settings), false);
	}
	
	public void CreateBaseJson(){		
		settings = new json_settings();
		settings.has_finished_help = true;

		settings.is_achievement_16 = false;
		settings.is_achievement_32 = false;
		settings.is_achievement_48 = false;
		settings.is_achievement_64 = false;
		settings.is_achievement_1 = false;
		settings.is_achievement_2 = false;
		settings.is_achievement_3 = false;

		settings.number_coins = 0;

		for(int i = 0; i < NUM_LEVELS; i++){
			level_data level = new level_data();
			level.angle_to_beat = 90f;
			level.is_mission_1_achieved = false;
			level.is_mission_2_achieved = false;
			level.is_mission_3_achieved = false; 
			level.is_closed = true;
			if (i==0) {
				level.is_closed = false;
				level.is_unlocked = true;
			} else {
				level.is_unlocked = false;
			}				
			level.points_achieved = 0;
			if(i < 16){
				level.level1_angle  = 35;
				level.level2_angle  = 30;
				level.level3_angle  = 25;
			} else if (i >= 16 && i < 32){
				level.level1_angle  = 35;
				level.level2_angle  = 30;
				level.level3_angle  = 25;				
			} else if (i >= 32 && i < 48){
				level.level1_angle  = 30;
				level.level2_angle  = 25;
				level.level3_angle  = 20;				
			} else if (i >= 48 && i < 64){
				level.level1_angle  = 30;
				level.level2_angle  = 25;
				level.level3_angle  = 20;				
			} else if (i >= 64 && i < 80){
				level.level1_angle  = 25;
				level.level2_angle  = 20;
				level.level3_angle  = 15;				
			} else if (i >= 80 && i < 96){
				level.level1_angle  = 25;
				level.level2_angle  = 20;
				level.level3_angle  = 15;				
			}
			settings.levels.add(level);
		}
	}	
	
	public static class level_data{
		public boolean is_unlocked;
		public boolean is_mission_1_achieved;
		public boolean is_mission_2_achieved;
		public boolean is_mission_3_achieved;
		public int points_achieved;
		public float angle_to_beat;
		public boolean is_closed;
		public int level1_angle;
		public int level2_angle;
		public int level3_angle;
		public level_data(){
		}
	}

	public void Dispose(){
		handle = null;
		json = null;
		settings.level_angles = null;
		settings.level_wincoins = null;
		if(settings.levels!=null) {
			settings.levels.clear();
			settings.levels = null;
		}
	}
}