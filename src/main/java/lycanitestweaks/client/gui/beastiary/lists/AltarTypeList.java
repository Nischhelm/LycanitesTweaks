package lycanitestweaks.client.gui.beastiary.lists;

import com.lycanitesmobs.client.gui.beastiary.BeastiaryScreen;
import com.lycanitesmobs.core.info.AltarInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;

import java.util.HashMap;
import java.util.Map;

public class AltarTypeList extends AltarFilterList {
	private final Map<Integer, String> altarTypeList = new HashMap<>();

	/**
	 * Constructor
	 * @param width The width of the list.
	 * @param height The height of the list.
	 * @param top The y position that the list starts at.
	 * @param bottom The y position that the list stops at.
	 * @param x The x position of the list.
	 */
	public AltarTypeList(BeastiaryScreen parentGui, int width, int height, int top, int bottom, int x) {
		super(parentGui, width, height, top, bottom, x, 24);
		this.refreshList();
	}


	@Override
	public void refreshList() {
		this.selectedIndex = this.parentGui.playerExt.selectedPetType;
		this.altarTypeList.clear();

		this.altarTypeList.put(0, "gui.beastiary.altar.challenge");
		this.altarTypeList.put(1, "gui.beastiary.altar.boss");
		this.altarTypeList.put(2, "gui.beastiary.altar.nonevent");
		this.altarTypeList.put(3, "gui.beastiary.altar.event");
	}


	@Override
	protected int getSize() {
		return this.altarTypeList.size();
	}


	@Override
	protected void elementClicked(int index, boolean doubleClick) {
		super.elementClicked(index, doubleClick);

		this.parentGui.playerExt.selectedPetType = index;
		for(AltarList altarList : this.filteredLists) {
			if(altarList != null) {
				this.updateCreatureListType(altarList);
			}
		}
	}


	/**
	 * Updates the list type of the provided Creature List to match this filter list.
	 * @param altarList The Creature List to update.
	 */
	public void updateCreatureListType(AltarList altarList) {
		AltarList.Type listType = null;
		if(this.selectedIndex == 0) {
			listType = AltarList.Type.CHALLENGE;
		}
		else if(this.selectedIndex == 1) {
			listType = AltarList.Type.BOSS;
		}
		else if(this.selectedIndex == 2) {
			listType = AltarList.Type.NONEVENT;
		}
		else if(this.selectedIndex == 3) {
			listType = AltarList.Type.EVENT;
		}

		altarList.changeType(listType);
	}


	@Override
	protected boolean isSelected(int index) {
		return this.selectedIndex == index;
	}
	

	@Override
	protected void drawBackground() {}


	@Override
	protected void drawSlot(int index, int boxRight, int boxTop, int boxBottom, Tessellator tessellator) {
		String altarListType = this.altarTypeList.get(index);
		if(altarListType == null) {
			return;
		}
		this.parentGui.getFontRenderer().drawString(I18n.format(altarListType), this.left + 2 , boxTop + 4, 0xFFFFFF, true);
	}


	@Override
	public void addFilteredList(AltarList altarList) {
		super.addFilteredList(altarList);
		this.updateCreatureListType(altarList);
	}


	@Override
	public boolean canListCreature(AltarInfo altarInfo, AltarList.Type listType) {
		if(altarInfo == null || listType == null) {
			return false;
		}
		if(this.selectedIndex == 0 && listType == AltarList.Type.CHALLENGE) {
			return true;
		}
		if(this.selectedIndex == 1 && listType == AltarList.Type.BOSS) {
			return true;
		}
		if(this.selectedIndex == 2 && listType == AltarList.Type.NONEVENT) {
			return true;
		}
		if(this.selectedIndex == 3 && listType == AltarList.Type.EVENT) {
			return true;
		}
		return false;
	}
}
