//WMS3 2015

package com.cryotrax.game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ex01MenuLevelSelectorBase extends ex01PagedScrollPane{
	public ex01MenuLevelSelectorBaseStyle style;
	public ex01MenuLevelSelector selector_parent;
	public Image buttonLeft, buttonRight;
	public Image image_level_selector_closed;
	public Image image_cryo;
	public Array<Level> levels;                  
	public Table table_level_selector_closed;
	public Table[] table_array_fiver = new Table[6];
	public static final int VIRTUAL_SCREEN_WIDTH = 480;	
	public static int screen_sizew;
	public static int screen_sizew5;
	public int received_open_from_level_no = 0;
	public int table_array_counter = 0;
	Level rotator;
	// solves ranking table layout error on levels screen when clicking levels too
	// fast one after another
	public boolean bCanClickAnotherLevel = true;
	public Level level_object;
	public ex01PagedScrollPane pane;

	public ex01MenuLevelSelectorBase(ex01MenuLevelSelector parent,
									 ex01JSONSettingsLoader settings,
									 Skin skin){
		initialize();
		setSize(getPrefWidth(), getPrefHeight());
		selector_parent = parent;
		screen_sizew = VIRTUAL_SCREEN_WIDTH;	
		screen_sizew5 = screen_sizew / 5;
		table_level_selector_closed = new Table();
		image_level_selector_closed = new Image(skin.getDrawable("hud-level55"));
		image_level_selector_closed.setColor(1f, 1f, 1f, 0f);
		table_level_selector_closed.add(image_level_selector_closed);	
		table_level_selector_closed.getCell(image_level_selector_closed)
				.size(384f, 384f * 262f / 512f);
		this.setFlingTime(1.0f);
		this.setPageSpacing(VIRTUAL_SCREEN_WIDTH / 10f);
		pane = this;
		levels = new Array<Level>();
    	int c = 1;
    	for(int i = 0; i < 4; i++){ // 6 pages
    		Table page_levels = new Table();
    		for(int j = 0; j < 4; j++){
    			page_levels.row();
    			for(int m = 0; m < 4; m++){
    				Level level = new Level(Integer.toString(c), c++,
							settings.settings.levels.get(c-2).is_mission_1_achieved,
							settings.settings.levels.get(c-2).is_mission_2_achieved,
							settings.settings.levels.get(c-2).is_mission_3_achieved,
							skin);
    				level.count = c-1;
    				level.level_1_achieved = settings.settings.levels.get(c-2)
							.is_mission_1_achieved;
    				level.level_2_achieved = settings.settings.levels.get(c-2)
							.is_mission_2_achieved;
    				level.level_3_achieved = settings.settings.levels.get(c-2)
							.is_mission_3_achieved;
    				level.level1_angle = settings.settings.levels.get(c-2).level1_angle;
    				level.level2_angle = settings.settings.levels.get(c-2).level2_angle;
    				level.level3_angle = settings.settings.levels.get(c-2).level3_angle;
    				level.is_unlocked = settings.settings.levels.get(c-2).is_unlocked;
    				level.average_angle = settings.settings.levels.get(c-2).angle_to_beat;
    				level.points_achieved = settings.settings.levels.get(c-2).points_achieved;   
    				level.is_closed = settings.settings.levels.get(c-2).is_closed;
    				levels.add(level);  				
    				page_levels.add(level.getLevelButton(Integer.toString(c-2)))
							.size(screen_sizew / 5f, screen_sizew / 5f * 309f/256f).pad(2f);
					level_object = level;
    				level.level_base.addListener( new ClickListener() {
    					@Override
    					public void clicked(InputEvent event, float x, float y){
							if(bCanClickAnotherLevel && selector_parent
									.settings_for_data.settings.levels
									.get(Integer.parseInt(event.getListenerActor().getName()))
									.is_unlocked) {
								bCanClickAnotherLevel = false;
								selector_parent.bCanGetBackwards = false;
								if (selector_parent.cryo_game
										.menu_screen.settings.settings.sounds_on)
									selector_parent.cryo_game
											.menu_screen.level_opener_selector_button
											.play(SND_VOL.LEVEL_OPENER_VOLUME);
								selector_parent.table_level_selector.clearActions();
								received_open_from_level_no = Integer.parseInt(
										event.getListenerActor().getName());
								image_cryo.clearActions();
								image_cryo.addAction(Actions.fadeOut(0.85f));
								selector_parent.ProcessClickLevel();
								selector_parent.ProcessLeftRight();
								selector_parent.DisableLeftRight();
								selector_parent.cryo_game.game_state = GameState.LEVEL_ENTRY;
							}
    					}
    				});
    			}
    		}
    		page_levels.pack(); // we need to pack otherwise we get zero
    		page_levels.padLeft((screen_sizew - page_levels.getWidth()) / 2f);
    		page_levels.padRight((screen_sizew - page_levels.getWidth()) / 2f);
    		this.addPage(page_levels);
    		table_array_fiver[table_array_counter++] = page_levels;
    	}		
	}

	public void ResetLevelsDrawkingsOnChangeAlsoLevels(ex01JSONSettingsLoader settins_from_google){
		int c = 1;
		for(int i = 0; i < 4; i++){ // 4 pages
			for(int j = 0; j < 4; j++){
				for(int m = 0; m < 4; m++){
					rotator = levels.get(c-1);
					rotator.count = c-1;
					rotator.level_1_achieved = settins_from_google.settings.levels.get(c-1)
							.is_mission_1_achieved;
					rotator.level_2_achieved = settins_from_google.settings.levels.get(c-1)
							.is_mission_2_achieved;
					rotator.level_3_achieved = settins_from_google.settings.levels.get(c-1)
							.is_mission_3_achieved;
					rotator.level1_angle = settins_from_google.settings.levels.get(c-1)
							.level1_angle;
					rotator.level2_angle = settins_from_google.settings.levels.get(c-1)
							.level2_angle;
					rotator.level3_angle = settins_from_google.settings.levels.get(c-1)
							.level3_angle;
					rotator.is_unlocked = settins_from_google.settings.levels.get(c-1)
							.is_unlocked;
					rotator.average_angle = settins_from_google.settings.levels.get(c-1)
							.angle_to_beat;
					rotator.points_achieved = settins_from_google.settings.levels.get(c-1)
							.points_achieved;
					rotator.is_closed = settins_from_google.settings.levels.get(c-1)
							.is_closed;
					c++;
				}
			}
		}
	}

	public void ResetLevelsDrawkingsOnChange(){
    	int c = 1;
    	for(int i = 0; i < 4; i++){ // 4 pages
    		for(int j = 0; j < 4; j++){
    			for(int m = 0; m < 4; m++){
    				c++;
    				levels.get(c-2).setLevelButtonOnChange(Integer.toString(c-1));
    			}
    		}
    	}	
	}
	
	@Override
	public void act(float delta){		
		super.act(delta);
	}

	private void initialize(){
		setTouchable(Touchable.enabled);
		levels = new Array<Level>();		
	}
	
	public void initializeReset(){
		setTouchable(Touchable.enabled);	
	}
	
	public static class Level {
		public boolean was_closed = true;
		public boolean was_unlocked = true;
		public boolean level_1_achieved = false;
		public boolean level_2_achieved = false;
		public boolean level_3_achieved = false;
		public boolean is_unlocked;
		public boolean is_closed;
		public int level1_angle;
		public int level2_angle;
		public int level3_angle;
		public int points_achieved;
		public int count;
		public float average_angle;
		public TextButton level_base;
		public TextButton.TextButtonStyle style;
		public TextureRegion tr_level_region;
		public Image level_base_achievement1;
		public Image level_base_achievement2;
		public Image level_base_achievement3;
		public Image level_lock;
		public Skin skin_level;
		public Stack stack = new Stack();
		public Table starTable = new Table();
		public Table lockTable = new Table();
		public Level(String level_number, int cnt, boolean l1, boolean l2, boolean l3, Skin skin){
			skin_level = skin;
			style = new TextButton.TextButtonStyle();
			level_base = new TextButton(level_number, skin);
			level_lock = new Image(skin, "lock-2");
			level_base.getLabel().setColor(new Color(0.0f, 0.0f, 0f, 0.804f));
			level_base.getLabelCell().padBottom(16f);		
			level_1_achieved = l1;
			level_2_achieved = l2;
			level_3_achieved = l3;
			if(level_1_achieved) {
				level_base_achievement1 = new Image(skin, "staronn");
			} else {
				level_base_achievement1 = new Image(skin, "staroff");
			}
			if(level_2_achieved) {
				level_base_achievement2 = new Image(skin, "staronn");		
			} else {
				level_base_achievement2 = new Image(skin, "staroff");
			}
			if(level_3_achieved) {
				level_base_achievement3 = new Image(skin, "staronn");		
			} else {
				level_base_achievement3 = new Image(skin, "staroff");
			}	
		}

		public Image getLevelBaseAchievement1Img(){
			return level_base_achievement1;
		}
		
		public Image getLevelBaseAchievement2Img(){
			return level_base_achievement2;
		}
		
		public Image getLevelBaseAchievement3Img(){
			return level_base_achievement3;
		}	
		
		public void setLevelButtonOnChange(String name){
			if(is_closed) {
				if(was_closed){
				} else { }
			} else {
				if(was_closed){
					style = skin_level.get("opened", TextButton.TextButtonStyle.class);
					level_base.setStyle(style);
					level_base.setText(name);
					level_base.getLabel().addAction(Actions.show());
					was_closed = false;
				} else { }
			}
			if(is_unlocked){
				if(was_unlocked){
					if(level_1_achieved) {
						level_base_achievement1.setDrawable(skin_level.getDrawable("staronn"));
					} else {
						level_base_achievement1.setDrawable(skin_level.getDrawable("staroff")); }
					if(level_2_achieved) {
						level_base_achievement2.setDrawable(skin_level.getDrawable("staronn"));
					} else {
						level_base_achievement2.setDrawable(skin_level.getDrawable("staroff")); }
					if(level_3_achieved) {
						level_base_achievement3.setDrawable(skin_level.getDrawable("staronn"));
					} else {
						level_base_achievement3.setDrawable(skin_level.getDrawable("staroff")); }
				} else {
					if(level_1_achieved) {
						level_base_achievement1.setDrawable(skin_level.getDrawable("staronn"));
					} else {
						level_base_achievement1.setDrawable(skin_level.getDrawable("staroff")); }
					if(level_2_achieved) {
						level_base_achievement2.setDrawable(skin_level.getDrawable("staronn"));
					} else {
						level_base_achievement2.setDrawable(skin_level.getDrawable("staroff")); }
					if(level_3_achieved) {
						level_base_achievement3.setDrawable(skin_level.getDrawable("staronn"));
					} else {
						level_base_achievement3.setDrawable(skin_level.getDrawable("staroff"));
					}
					stack.removeActor(lockTable);
					starTable.add(getLevelBaseAchievement1Img())
							.width(screen_sizew5/3.3f).height(screen_sizew5/3.3f ).pad(0f);
					starTable.add(getLevelBaseAchievement2Img())
							.width(screen_sizew5/3.3f).height(screen_sizew5/3.3f ).pad(0f);
					starTable.add(getLevelBaseAchievement3Img())
							.width(screen_sizew5/3.3f).height(screen_sizew5/3.3f ).pad(0f);
					stack.add(starTable.padBottom(screen_sizew5/10));
					stack.add(starTable.bottom());
					was_unlocked = true;
				}
			} else {
				
			}			
		}
		
		public Stack getLevelButton(String name){
			if(is_closed) {
				stack.add(level_base);
				style = skin_level.get("closed", TextButton.TextButtonStyle.class);
				level_base.setStyle(style); 
				was_closed = true;
			} else {
				stack.add(level_base);
				style = skin_level.get("opened", TextButton.TextButtonStyle.class);
				level_base.setStyle(style);
				was_closed = false;
			}
			if (is_unlocked) {
				was_unlocked = true;
				starTable.add(getLevelBaseAchievement1Img())
						.width(screen_sizew5/3.3f).height(screen_sizew5/3.3f ).pad(0f);
				starTable.add(getLevelBaseAchievement2Img())
						.width(screen_sizew5/3.3f).height(screen_sizew5/3.3f ).pad(0f);
				starTable.add(getLevelBaseAchievement3Img())
						.width(screen_sizew5/3.3f).height(screen_sizew5/3.3f ).pad(0f);
				stack.add(starTable.padBottom(screen_sizew5/10));
				stack.add(starTable.bottom());
			} else {
				was_unlocked = false;
				level_base.getLabel().addAction(Actions.hide());
				lockTable.add(level_lock)
						.width(screen_sizew5/1.5f).height(screen_sizew5/1.5f).pad(0f).center();
				lockTable.addAction(Actions.alpha(0.85f));
				stack.add(lockTable);
			}
			level_base.setName(name);
			return stack;
		}
	}
	
	static public class ex01MenuLevelSelectorBaseStyle {
		public Drawable leftArrow, rightArrow;
	}

	public void Dispose(){
		super.Dispose();
		if(buttonLeft!=null)
			buttonLeft.clear();
		if(buttonRight!=null)
			buttonRight.clear();
		if(image_level_selector_closed!=null)
			image_level_selector_closed.clear();
		if(image_cryo!=null)
			image_cryo.clear();
		if(table_level_selector_closed!=null)
			table_level_selector_closed.clear();
		style = null;
		selector_parent = null;
		buttonLeft = null;
		buttonRight = null;
		image_level_selector_closed = null;
		image_cryo = null;
		table_level_selector_closed = null;
		for(int i=0; i<table_array_fiver.length; i++){
			table_array_fiver[i] = null;
		}

		if(levels!=null) {
			for (int j = 0; j < levels.size; j++) {
				levels.get(j).level_base = null;
				levels.get(j).style = null;
				levels.get(j).level_base_achievement1 = null;
				levels.get(j).level_base_achievement2 = null;
				levels.get(j).level_base_achievement3 = null;
				levels.get(j).level_lock = null;
				levels.get(j).stack = null;
				levels.get(j).starTable = null;
				levels.get(j).lockTable = null;
				levels.get(j).tr_level_region = null;
				levels.get(j).skin_level = null;
			}
			levels.clear();
			levels = null;
		}
		level_object = null;
	}
}
