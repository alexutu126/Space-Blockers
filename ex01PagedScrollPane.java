//WMS3 2015

package com.cryotrax.game;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.Array;

public class ex01PagedScrollPane extends ScrollPane {
	private boolean wasPanDragFling = false;
	public Stack base_content;
	public Table content;
	public ex01MenuLevelSelector selector_parent_pane;
	
	public ex01PagedScrollPane () {
		super(null);
		setup();
	}
	
	public ex01PagedScrollPane (Skin skin) {
		super(null, skin);
		setup();
	}
	
	public ex01PagedScrollPane (Skin skin, String styleName) {
		super(null, skin, styleName);
		setup();
	}
	
	public ex01PagedScrollPane (Actor widget, ScrollPaneStyle style) {
		super(null, style);
		setup();
	}
	
	private void setup() {
		content = new Table();
		base_content = new Stack();
		content.defaults().space(50f);
		super.setWidget(content);
	}
	
	public void addPages (Actor... pages) {
		for (Actor page : pages) {
			content.add(page).expandY().fillY();
		}
	}
	
	public void addPage (Actor page) {
		content.add(page).expandY().fillY();
	}
	
	@Override
	public void act (float delta) {
		super.act(delta);
		if (wasPanDragFling && !isPanning() && !isDragging() && !isFlinging()) {
			wasPanDragFling = false;
			scrollToPage();
		} else {
			if (isPanning() || isDragging() || isFlinging()) {
				wasPanDragFling = true;
			}
		}
	}
	
	@Override
	public void setWidget (Actor widget) {
	}
	
	@Override
	public void setWidth (float width) {
		super.setWidth(width);
		if (content != null) {
			for (Cell<?> cell : content.getCells()) {
				cell.width(width);
			}
			content.invalidate();
		}
	}
	
	public void setPageSpacing (float pageSpacing) {
		if (content != null) {
			content.defaults().space(pageSpacing);
			for (Cell<?> cell : content.getCells()) {
				cell.space(pageSpacing);
			}
			content.invalidate();
		}
	}

	int page_counter = 0;

	private void scrollToPage () {
		final float width = getWidth();
		final float scrollX = getScrollX();
		final float maxX = getMaxX();
		// if (scrollX >= maxX || scrollX <= 0) return;
		// daca decomentez asta nu mai merge scrool page no graphics - time minte poate
		// s-a stricat ceva
		Array<Actor> pages = content.getChildren();
		float pageX = 0;
		float pageWidth = 0;
		if (pages.size > 0) {
			for (Actor a : pages) {
				page_counter++;
				pageX = a.getX();
				pageWidth = a.getWidth();
				if (scrollX < (pageX + pageWidth * 0.5)) {
					selector_parent_pane.page_no = page_counter;
					selector_parent_pane.LevelGraphicsProcess();
					page_counter = 0;
					break;
				}
			}
			setScrollX(MathUtils.clamp(pageX - (width - pageWidth) / 2, 0, maxX));
		}
	}

	public void Dispose(){
		if(base_content!=null){
			base_content.clear();
			base_content = null;
		}
		if(content!=null){
			content.clear();
			content = null;
		}
		selector_parent_pane = null;
	}
}