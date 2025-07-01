package lycanitestweaks.client.gui.beastiary.lists;

import com.lycanitesmobs.ObjectManager;
import com.lycanitesmobs.client.gui.beastiary.BeastiaryScreen;
import com.lycanitesmobs.core.info.AltarInfo;
import lycanitestweaks.info.altar.IAltarBeastiaryRender;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.GuiScrollingList;

import java.util.HashMap;
import java.util.Map;

// Based on CreatureList.class
public class AltarList extends GuiScrollingList {

	public enum Type {
		CHALLENGE((byte)0), BOSS((byte)1), NONEVENT((byte)2), EVENT((byte)3);
		public final byte id;
		Type(byte i) { id = i; }
	}

	// To the hopeful future that vanilla altars are clear and exposed
	public static final String[] CORE_BLOCK_PATTERN_STRINGS = new String[]{
			"#",
			"#",
			"^",
			"#",
			"#"
	};
	public static final String[] PILLAR_BLOCK_PATTERN_STRINGS = new String[]{
			"^",
			"#",
			"#",
			"#"
	};
	public final HashMap<String, String[]> challengeAltarBlockPatterns = new HashMap<>();
	public final HashMap<String, String> bossAltarSoulcubes = new HashMap<>();

	private Type listType;
	private final BeastiaryScreen parentGui;
    private final Map<Integer, AltarInfo> altarList = new HashMap<>();

	/**
	 * Constructor
	 * @param listType The type of contents to show in this list.
	 * @param parentGui The Beastiary GUI using this list.
	 * @param filterList A altar filter list to restrict this list by, if null every altar is listed.
	 * @param width The width of the list.
	 * @param height The height of the list.
	 * @param top The y position that the list starts at.
	 * @param bottom The y position that the list stops at.
	 * @param x The x position of the list.
	 */
	public AltarList(Type listType, BeastiaryScreen parentGui, AltarFilterList filterList, int width, int height, int top, int bottom, int x) {
		super(Minecraft.getMinecraft(), width, height, top, bottom, x, 16, width, height);
		this.listType = listType;
		this.parentGui = parentGui;
        if(filterList != null) {
			filterList.addFilteredList(this);
		}
		this.loadVanillaAltars();
		this.refreshList();
	}

	private void loadVanillaAltars(){
		this.challengeAltarBlockPatterns.putIfAbsent("CelestialGeonachAltar", new String[]{
			"~~###~~",
			"~#####~",
			"###^###",
			"~~###~~",
			"~~~#~~~"
		});
		this.challengeAltarBlockPatterns.putIfAbsent("CrimsonEpion", new String[]{
			"~#~#~",
			"#####",
			"~#^#~",
			"~~#~~"
		});
		this.challengeAltarBlockPatterns.putIfAbsent("EbonCacodemonAltar", new String[]{
			"~###~",
			"#####",
			"##^##",
			"#####",
			"~###~"
		});
		this.challengeAltarBlockPatterns.putIfAbsent("LunarGrueAltar", new String[]{
			"#####",
			"#~#~#",
			"~~^~~",
			"~~#~~",
			"~~#~~",
			"~~#~~",
			"~~#~~"
		});
		this.challengeAltarBlockPatterns.putIfAbsent("MottledAbaia", new String[]{
			"~#~",
			"###",
			"###",
			"~^~",
			"~#~",
			"~#~"
		});
		this.challengeAltarBlockPatterns.putIfAbsent("PhosphorescentChupacabra", new String[]{
			"~~#~~",
			"##^##",
			"~###~",
			"##~##"
		});
		this.challengeAltarBlockPatterns.putIfAbsent("RoyalArchvile", new String[]{
			"#####",
			"#~#~#",
			"~~^~~",
			"#####",
			"#~~~#"
		});
		this.challengeAltarBlockPatterns.putIfAbsent("UmberLobberAltar", new String[]{
			"#####",
			"#~#~#",
			"~~^~~",
			"~###~",
			"##~##"
		});

		this.bossAltarSoulcubes.putIfAbsent("AmalgalichAltar", "soulcubeundead");
		this.bossAltarSoulcubes.putIfAbsent("AsmodeusAltar", "soulcubeaberrant");
		this.bossAltarSoulcubes.putIfAbsent("RahovartAltar", "soulcubedemonic");
	}


	/**
	 * Reloads all items in this list.
	 */
	public void refreshList() {
		// Clear:
		this.altarList.clear();
		int altarIndex = 0;

		// Vanilla Rare Altars
		if(this.listType == Type.CHALLENGE) {
			for(String altarName : this.challengeAltarBlockPatterns.keySet()) this.altarList.put(altarIndex++, AltarInfo.getAltar(altarName));
		}
		// Vanilla Boss Altars
		else if(this.listType == Type.BOSS) {
			for(String altarName : this.bossAltarSoulcubes.keySet()) this.altarList.put(altarIndex++, AltarInfo.getAltar(altarName));
		}
		// Most other should be non-event
		else if(this.listType == Type.NONEVENT) {
			for(AltarInfo altarInfo : AltarInfo.altars.values()){
				if(altarInfo.mobEventTrigger == null && altarInfo instanceof IAltarBeastiaryRender)
					this.altarList.put(altarIndex++, altarInfo);
			}
		}
		// Few remaining will correctly link an event
		else if(this.listType == Type.EVENT) {
			for(AltarInfo altarInfo : AltarInfo.altars.values()){
				if(altarInfo.mobEventTrigger != null && altarInfo instanceof IAltarBeastiaryRender)
					this.altarList.put(altarIndex++, altarInfo);
			}
		}
	}


	@Override
	protected int getSize() {
		if(this.listType != null) {
			return altarList.size();
		}
		return 0;
	}

	@Override
	protected void elementClicked(int index, boolean doubleClick) {
		this.selectedIndex = index;
	}


	@Override
	protected boolean isSelected(int index) {
		return this.selectedIndex == index;
	}
	

	@Override
	protected void drawBackground() {

	}

    @Override
    protected int getContentHeight() {
        return this.getSize() * this.slotHeight;
    }


	@Override
	protected void drawSlot(int index, int boxRight, int boxTop, int boxBottom, Tessellator tessellator) {
		if(this.listType != null) {
			// Name:
			String altarName = this.getAltarTitle(index);
			if(altarName.isEmpty()) return;
			int nameY = boxTop + 2;

			this.parentGui.getFontRenderer().drawString(altarName, this.left + 2, nameY, 0xFFFFFF);
		}
	}

	public String[] getSelectedChallengeBlockPatternStrings(){
		if(this.listType == Type.CHALLENGE) {
			if(this.altarList.containsKey(this.selectedIndex))
				if(this.challengeAltarBlockPatterns.containsKey(this.altarList.get(this.selectedIndex).name))
					return this.challengeAltarBlockPatterns.get(this.altarList.get(this.selectedIndex).name);
		}
		return null;
	}

	public Block getSelectedBossSoulcube(){
		if(this.listType == Type.BOSS) {
			if(this.altarList.containsKey(this.selectedIndex))
				if(this.bossAltarSoulcubes.containsKey(this.altarList.get(this.selectedIndex).name))
					return ObjectManager.getBlock(this.bossAltarSoulcubes.get(this.altarList.get(this.selectedIndex).name));
		}
		return null;
	}

	public AltarInfo getSelectedCustomAltar(){
		if(this.listType == Type.EVENT || this.listType == Type.NONEVENT){
			if(this.altarList.containsKey(this.selectedIndex))
				if(AltarInfo.altars.containsKey(this.altarList.get(this.selectedIndex).name))
					return AltarInfo.getAltar(this.altarList.get(this.selectedIndex).name);

		}
		return null;
	}

	private String getAltarTitle(int index){
		AltarInfo altarInfo = this.altarList.get(index);
		if (altarInfo != null) return I18n.format("altars.altarinfo." + altarInfo.name + ".name");

		return "";
	}

	/**
	 *
	 * @return Translated string of an Altar's relatively short title
	 */
	public String getSelectedAltarTitle(){
		return this.getAltarTitle(this.selectedIndex);
	}

	private String getAltarDescription(int index){
		AltarInfo altarInfo = this.altarList.get(index);
		if (altarInfo != null) {
			if(this.listType == Type.CHALLENGE || this.listType == Type.NONEVENT){
				return I18n.format("altars.altarinfo." + altarInfo.name + ".description");
			}
			if(this.listType == Type.BOSS || this.listType == Type.EVENT){
				return I18n.format("mobevent." + altarInfo.mobEventTrigger.mobEvent.name + ".name");
			}
		}
		return "";
	}

	/**
	 *
	 * @return Translated string of an Altar's longer description
	 */
	public String getSelectedAltarDescription(){
		return this.getAltarDescription(this.selectedIndex);
	}

	/**
	 * Changes the type of altars that this list should display. Also refreshes this list.
	 * @param listType The new list type to use.
	 */
	public void changeType(Type listType) {
		this.listType = listType;
		this.refreshList();
	}
}
