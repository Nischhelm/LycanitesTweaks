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

public class AltarList extends GuiScrollingList {
	public enum Type {
		CHALLENGE((byte)0), BOSS((byte)1), NONEVENT((byte)2), EVENT((byte)3);
		public final byte id;
		Type(byte i) { id = i; }
	}

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
	private final AltarFilterList filterList;
	private final Map<Integer, AltarInfo> altarList = new HashMap<>();

	/**
	 * Constructor
	 * @param listType The type of contents to show in this list.
	 * @param parentGui The Beastiary GUI using this list.
	 * @param filterList A creature filter list to restrict this list by, if null every creature is listed.
	 * @param width The width of the list.
	 * @param height The height of the list.
	 * @param top The y position that the list starts at.
	 * @param bottom The y position that the list stops at.
	 * @param x The x position of the list.
	 */
	public AltarList(Type listType, BeastiaryScreen parentGui, AltarFilterList filterList, int width, int height, int top, int bottom, int x) {
		super(Minecraft.getMinecraft(), width, height, top, bottom, x, 24, width, height);
		this.listType = listType;
		this.parentGui = parentGui;
		this.filterList = filterList;
		if(this.filterList != null) {
			this.filterList.addFilteredList(this);
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

//		List<String> altars = new ArrayList<>(AltarInfo.altars.keySet());
//		altars.sort(Collator.getInstance(new Locale("US")));

		// Creature Knowledge List:
		if(this.listType == Type.CHALLENGE) {
			for(String altarName : this.challengeAltarBlockPatterns.keySet()) this.altarList.put(altarIndex++, AltarInfo.getAltar(altarName));
		}
		// Pet List:
		else if(this.listType == Type.BOSS) {
			for(String altarName : this.bossAltarSoulcubes.keySet()) this.altarList.put(altarIndex++, AltarInfo.getAltar(altarName));
		}
		else if(this.listType == Type.NONEVENT) {
			for(AltarInfo altarInfo : AltarInfo.altars.values()){
				if(altarInfo.mobEventTrigger == null && altarInfo instanceof IAltarBeastiaryRender)
					this.altarList.put(altarIndex++, altarInfo);
			}
		}
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
		// Knowledge Slot:
		if(this.listType != null) {
			// Name:
			String altarName = this.getAltarTitle(index);
			if(altarName.isEmpty()) return;
			int nameY = boxTop + 6;

			// TODO lang keys
			this.parentGui.getFontRenderer().drawString(altarName, this.left + 20, nameY, 0xFFFFFF);

			// Level:
//			if (this.listType == Type.CHALLENGE) {
//				this.parentGui.getFontRenderer().drawString(altarInfo.name, this.left + 20, nameY, 0xFFFFFF);
//				this.parentGui.drawLevel(altarInfo, AssetManager.getTexture("GUIPetLevel"), this.left + 18, boxTop + 10);
//			}

			// Icon:
//			if (altarInfo.getIcon() != null) {
//				this.parentGui.drawTexture(altarInfo.getIcon(), this.left + 2, boxTop + 2, 0, 1, 1, 16, 16);
//			}
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

	// TODO lang keys
	private String getAltarTitle(int index){
		AltarInfo altarInfo = this.altarList.get(index);
		if (altarInfo != null) {
			if(this.listType == Type.CHALLENGE || this.listType == Type.NONEVENT){
				return I18n.format(altarInfo.name);
			}
			if(this.listType == Type.BOSS || this.listType == Type.EVENT){
				return I18n.format(altarInfo.mobEventTrigger.mobEvent.name);
			}
		}
		return "";
	}

	public String getSelectedAltarTitle(){
		return this.getAltarTitle(this.selectedIndex);
	}

	/**
	 * Changes the type of creatures that this list should display. Also refreshes this list.
	 * @param listType The new list type to use.
	 */
	public void changeType(Type listType) {
		this.listType = listType;
		this.refreshList();
	}
}
