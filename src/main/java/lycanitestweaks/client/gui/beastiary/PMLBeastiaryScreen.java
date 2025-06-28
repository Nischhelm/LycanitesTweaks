package lycanitestweaks.client.gui.beastiary;

import com.lycanitesmobs.GuiHandler;
import com.lycanitesmobs.LycanitesMobs;
import com.lycanitesmobs.client.AssetManager;
import com.lycanitesmobs.client.gui.beastiary.BeastiaryScreen;
import com.lycanitesmobs.client.gui.beastiary.lists.CreatureList;
import com.lycanitesmobs.client.gui.beastiary.lists.CreatureTypeList;
import com.lycanitesmobs.client.gui.beastiary.lists.SubspeciesList;
import com.lycanitesmobs.client.localisation.LanguageManager;
import com.lycanitesmobs.core.info.CreatureInfo;
import com.lycanitesmobs.core.info.CreatureKnowledge;
import com.lycanitesmobs.core.info.Subspecies;
import lycanitestweaks.capability.ILycanitesTweaksPlayerCapability;
import lycanitestweaks.capability.LycanitesTweaksPlayerCapability;
import lycanitestweaks.client.gui.GuiNumberField;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

import java.io.IOException;

public class PMLBeastiaryScreen extends BeastiaryScreen {

	public ILycanitesTweaksPlayerCapability ltp;

	public CreatureTypeList creatureTypeList;
	public CreatureList creatureList;
	public SubspeciesList subspeciesList;
	private GuiNumberField commandTextField;

	public static final byte BEASTIARY_PML_ID = 6;

	/**
	 * Opens this GUI up to the provided player.
	 * @param player The player to open the GUI to.
	 */
	public static void openToPlayer(EntityPlayer player) {
		if(player != null) {
			player.openGui(LycanitesMobs.instance, GuiHandler.GuiType.BEASTIARY.id, player.getEntityWorld(), PMLBeastiaryScreen.BEASTIARY_PML_ID, 0, 0);
		}
	}


	public PMLBeastiaryScreen(EntityPlayer player) {
		super(player);
		this.ltp = LycanitesTweaksPlayerCapability.getForPlayer(this.player);
	}


	@Override
	public String getTitle() {
		if(this.creatureList != null && this.playerExt.selectedCreature != null) {
			return "";
			//return this.playerExt.selectedCreature.getTitle();
		}
		if(this.creatureTypeList != null && this.playerExt.selectedCreatureType != null) {
			return this.playerExt.selectedCreatureType.getTitle();
		}
		if(this.playerExt.getBeastiary().creatureKnowledgeList.isEmpty()) {
			LanguageManager.translate("gui.beastiary.creatures.empty.title");
		}
		return LanguageManager.translate("gui.beastiary.creatures");
	}


	@Override
	public void initControls() {
		super.initControls();

		this.creatureTypeList = new CreatureTypeList(this, this.colLeftWidth, this.colLeftHeight, this.colLeftY,this.colLeftY + this.colLeftHeight, this.colLeftX);

		int selectionListsWidth = this.getScaledX(240F / 1920F);

		int creatureListY = this.colRightY;
		int creatureListHeight = Math.round((float)this.colRightHeight * 0.6f);
		this.creatureList = new CreatureList(CreatureList.Type.KNOWLEDGE, this, this.creatureTypeList, selectionListsWidth, creatureListHeight, creatureListY,creatureListY + creatureListHeight, this.colRightX);

		int subspeciesListY = creatureListY + 2 + creatureListHeight;
		int subspeciesListHeight = Math.round((float)this.colRightHeight * 0.4f) - 2;
		this.subspeciesList = new SubspeciesList(this, false, selectionListsWidth, subspeciesListHeight, subspeciesListY,subspeciesListY + subspeciesListHeight, this.colRightX);

//		int newLine = this.getFontRenderer().getWordWrappedHeight("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA", this.colRightWidth - selectionListsWidth) + 2;
//		int descriptionListY = this.colRightY + (newLine * 3);
//		this.descriptionList = new CreatureDescriptionList(this, this.colRightWidth - selectionListsWidth, this.colRightHeight, descriptionListY, this.colRightY + this.colRightHeight, this.colRightX + selectionListsWidth + 2);

		this.commandTextField = new GuiNumberField(33333, this.fontRenderer, this.width / 2 - 150, 50, 300, 20);
		this.commandTextField.setMaxStringLength(32500);
		this.commandTextField.setFocused(true);
	}


	@Override
	public void drawBackground(int mouseX, int mouseY, float partialTicks) {
		super.drawBackground(mouseX, mouseY, partialTicks);
	}


	@Override
	protected void updateControls(int mouseX, int mouseY, float partialTicks) {
		super.updateControls(mouseX, mouseY, partialTicks);

		if(this.playerExt.getBeastiary().creatureKnowledgeList.isEmpty()) {
			return;
		}

		this.creatureTypeList.drawScreen(mouseX, mouseY, partialTicks);
		if(this.playerExt.selectedCreatureType != null) {
			this.creatureList.drawScreen(mouseX, mouseY, partialTicks);
			this.subspeciesList.drawScreen(mouseX, mouseY, partialTicks);
			this.commandTextField.drawTextBox();
		}
	}


	@Override
	public void drawForeground(int mouseX, int mouseY, float partialTicks) {
		super.drawForeground(mouseX, mouseY, partialTicks);

		int marginX = this.getScaledX(240F / 1920F) + 8;
		int nextX = this.colRightX + marginX;
		int nextY = this.colRightY;
		int width = this.colRightWidth - marginX;

		if(this.playerExt.getBeastiary().creatureKnowledgeList.isEmpty()) {
			nextY += 20;
			String text = LanguageManager.translate("gui.beastiary.creatures.empty.info");
			this.drawSplitString(text, this.colRightX, nextY, this.colRightWidth, 0xFFFFFF, true);
			return;
		}

		// Creature Display:
		if(this.playerExt.selectedCreature != null) {
			Subspecies subspecies = this.playerExt.selectedCreature.getSubspecies(this.playerExt.selectedSubspecies);

			// Model:
			this.renderCreature(this.playerExt.selectedCreature, this.colRightX + (marginX / 2) + (this.colRightWidth / 2), this.colRightY + 100, mouseX, mouseY, partialTicks);
			CreatureInfo creatureInfo = this.playerExt.selectedCreature;
			CreatureKnowledge creatureKnowledge = this.playerExt.beastiary.getCreatureKnowledge(this.playerExt.selectedCreature.getName());

			// Element:
			String text = "\u00A7l" + LanguageManager.translate("creature.stat.element") + ": " + "\u00A7r";
			text += creatureInfo.elements != null ? creatureInfo.getElementNames(subspecies) : "None";
			this.getFontRenderer().drawString(text, nextX, nextY, 0xFFFFFF, true);

			// Level:
			nextY += 2 + this.getFontRenderer().getWordWrappedHeight(text, width);
			text = "\u00A7l" + LanguageManager.translate("creature.stat.cost") + ": " + "\u00A7r";
			this.getFontRenderer().drawString(text, nextX, nextY, 0xFFFFFF, true);
			this.drawLevel(creatureInfo, AssetManager.getTexture("GUIPetLevel"),nextX + this.getFontRenderer().getStringWidth(text), nextY);

			// Knowledge Rank:
			nextY += 2 + this.getFontRenderer().getWordWrappedHeight(text, width);
			text = "\u00A7l" + LanguageManager.translate("creature.stat.knowledge") + ": " + "\u00A7r";
			int rankX = nextX + this.getFontRenderer().getStringWidth(text);
			int barX = rankX + 29;
			int barWidth = (256 / 4) + 16;
			int barHeight = (32 / 4) + 2;
			int barCenter = barX + (barWidth / 2);

			this.getFontRenderer().drawString(text, nextX, nextY, 0xFFFFFF, true);
			this.drawBar(AssetManager.getTexture("GUIPetSpiritEmpty"), rankX, nextY, 0, 9, 9, 3, 10);
			this.drawTexture(AssetManager.getTexture("GUIPetBarEmpty"), barX, nextY, 0, 1, 1, barWidth, barHeight);

			if(creatureKnowledge != null) {
				this.drawBar(AssetManager.getTexture("GUIPetSpiritUsed"), rankX, nextY, 0, 9, 9, creatureKnowledge.rank, 10);

				this.drawBar(AssetManager.getTexture("GUIPetSpiritUsed"), rankX, nextY, 0, 9, 9, creatureKnowledge.rank, 10);
				float experienceNormal = 1;
				if (creatureKnowledge.getMaxExperience() > 0) {
					experienceNormal = (float)creatureKnowledge.experience / creatureKnowledge.getMaxExperience();
				}
				this.drawTexture(AssetManager.getTexture("GUIBarExperience"), barX, nextY, 0, experienceNormal, 1, barWidth * experienceNormal, barHeight);
				String experienceText = "100%";
				if (creatureKnowledge.getMaxExperience() > 0) {
					experienceText = creatureKnowledge.experience + "/" + creatureKnowledge.getMaxExperience();
				}
				this.getFontRenderer().drawString(experienceText, Math.round(barCenter - ((float)this.getFontRenderer().getStringWidth(experienceText) / 2)), nextY + 2, 0xFFFFFF);
			}

			// Description:
//			this.descriptionList.creatureKnowledge = creatureKnowledge;
//			this.descriptionList.drawScreen(mouseX, mouseY, partialTicks);
		}

		// Creature Type Display:
		else if(this.playerExt.selectedCreatureType != null) {
			// Descovered:
			nextY += 20;
			String text = LanguageManager.translate("gui.beastiary.creatures.discovered") + ": ";
			text += this.playerExt.getBeastiary().getCreaturesDiscovered(this.playerExt.selectedCreatureType);
			text += "/" + this.playerExt.selectedCreatureType.creatures.size();
			this.getFontRenderer().drawString(text, nextX, nextY, 0xFFFFFF, true);
		}

		// Base Display:
		else {
			nextY += 20;
			String text = LanguageManager.translate("gui.beastiary.creatures.select");
			this.drawSplitString(text, this.colRightX, nextY, this.colRightWidth, 0xFFFFFF, true);
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException{
		super.keyTyped(typedChar, keyCode);
		this.commandTextField.textboxKeyTyped(typedChar, keyCode);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.commandTextField.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void actionPerformed(GuiButton guiButton) throws IOException {
		super.actionPerformed(guiButton);
	}
}
