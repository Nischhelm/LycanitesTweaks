package lycanitestweaks.client.gui.beastiary;

import com.lycanitesmobs.GuiHandler;
import com.lycanitesmobs.LycanitesMobs;
import com.lycanitesmobs.client.AssetManager;
import com.lycanitesmobs.client.gui.beastiary.BeastiaryScreen;
import com.lycanitesmobs.client.gui.beastiary.lists.CreatureList;
import com.lycanitesmobs.client.gui.beastiary.lists.CreatureTypeList;
import com.lycanitesmobs.client.gui.beastiary.lists.SubspeciesList;
import com.lycanitesmobs.client.localisation.LanguageManager;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.info.CreatureInfo;
import com.lycanitesmobs.core.info.CreatureKnowledge;
import com.lycanitesmobs.core.info.CreatureManager;
import com.lycanitesmobs.core.info.Subspecies;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.capability.PlayerMobLevel.IPlayerMobLevelCapability;
import lycanitestweaks.capability.PlayerMobLevel.PlayerMobLevelCapability;
import lycanitestweaks.client.gui.GuiNumberField;
import lycanitestweaks.handlers.ForgeConfigProvider;
import lycanitestweaks.handlers.config.major.PlayerMobLevelsConfig;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.HashMap;

public class PMLBeastiaryScreen extends BeastiaryScreen {

	public IPlayerMobLevelCapability pml;
	private final HashMap<PlayerMobLevelsConfig.BonusCategory, Integer> pmlBonusCateogories = new HashMap<>();

	public CreatureTypeList creatureTypeList;
	public CreatureList creatureList;
	public SubspeciesList subspeciesList;
	private GuiNumberField commandTextField;
	private GuiButton setOneButton;
	private GuiButton setAllButton;

	// bc modifying com.lycanitesmobs.GuiHandler.Beastiary is stupid
	public static final byte BEASTIARY_PML_ID = 6;
	public static final int NUMBER_FIELD_ID = 1337;
	// Lyca Beastiary ID that is unique and also not caught by default handling
	public static final int ALL_BUTTON_ID = -69;
	public static final int ONE_BUTTON_ID = -420;

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
		this.pml = PlayerMobLevelCapability.getForPlayer(this.player);
	}


	@Override
	public String getTitle() {
		if(this.creatureList != null && this.playerExt.selectedCreature != null) {
			return "";
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

		this.commandTextField = new GuiNumberField(PMLBeastiaryScreen.NUMBER_FIELD_ID, this.getFontRenderer(), this.colRightX + selectionListsWidth, subspeciesListY, selectionListsWidth, 20);
		this.commandTextField.setValidator(s -> s.matches("((0(\\.\\d*)?)|(1(\\.0?)?))|()"));
		this.commandTextField.setMaxStringLength(8);
		this.commandTextField.setVisible(false);
		this.commandTextField.setFocused(true);

		this.setOneButton = new GuiButton(PMLBeastiaryScreen.ONE_BUTTON_ID, this.colRightX + selectionListsWidth, subspeciesListY + this.commandTextField.height , (int) (selectionListsWidth * 1.5F), 20, I18n.format("gui.beastiary.pml.button.one"));
		this.setOneButton.visible = false;
		this.buttonList.add(setOneButton);

		this.setAllButton = new GuiButton(PMLBeastiaryScreen.ALL_BUTTON_ID, this.colRightX + selectionListsWidth, subspeciesListY + subspeciesListHeight - 20, (int) (selectionListsWidth * 1.5F), 20, I18n.format("gui.beastiary.pml.button.all"));
		this.setAllButton.visible = false;
		this.buttonList.add(setAllButton);
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
			if(this.creaturePreviewEntity instanceof BaseCreatureEntity) {
				BaseCreatureEntity creature = (BaseCreatureEntity) this.creaturePreviewEntity;
				nextY += 4 + this.getFontRenderer().getWordWrappedHeight(text, colRightWidth);
				int rhNextX = (int) (this.colRightX + this.colRightWidth * 0.75F);
				int rhNextY = nextY;

				if (this.playerExt.getBeastiary().hasKnowledgeRank(creatureInfo.getName(), 2)) {
					if (CreatureManager.getInstance().config.startingLevelMax > CreatureManager.getInstance().config.startingLevelMin) {
						text = I18n.format("gui.beastiary.pml.creatures.range",
								CreatureManager.getInstance().config.startingLevelMin,
								CreatureManager.getInstance().config.startingLevelMax);
					}
					else {
						int timeLevel = (int) (CreatureManager.getInstance().config.levelPerLocalDifficulty * 6.75D);
						if (CreatureManager.getInstance().config.levelPerDay > 0)
							timeLevel += CreatureManager.getInstance().config.levelPerDayMax;
						text = I18n.format("gui.beastiary.pml.creatures.time", timeLevel);
					}
					nextY += 4 + this.getFontRenderer().getWordWrappedHeight(text, colRightWidth);
					this.getFontRenderer().drawString(text, nextX, nextY, 0xFFFFFF, true);
				}
				IPlayerMobLevelCapability pml = PlayerMobLevelCapability.getForPlayer(this.player);
				if (pml != null) {
					if(this.player.ticksExisted % 20 == 0) PlayerMobLevelsConfig.getPmlBonusCategories().keySet()
							.forEach(bonusCategory -> this.pmlBonusCateogories
									.put(bonusCategory, pml.getTotalLevelsForCategory(bonusCategory, creature, true)));

					text = I18n.format("gui.beastiary.pml.creatures.elements", pml.getCurrentLevelBestiary(creatureInfo.elements));
					nextY += 4 + this.getFontRenderer().getWordWrappedHeight(text, colRightWidth);
					this.getFontRenderer().drawString(text, nextX, nextY, 0xFFFFFF, true);

					text = I18n.format("gui.beastiary.pml.creatures.pet", pml.getHighestLevelPetActive());
					nextY += 4 + this.getFontRenderer().getWordWrappedHeight(text, colRightWidth);
					this.getFontRenderer().drawString(text, nextX, nextY, 0xFFFFFF, true);

					text = I18n.format("gui.beastiary.pml.creatures.enchantments", pml.getTotalEnchantmentLevels());
					nextY += 4 + this.getFontRenderer().getWordWrappedHeight(text, colRightWidth);
					this.getFontRenderer().drawString(text, nextX, nextY, 0xFFFFFF, true);

					text = I18n.format("gui.beastiary.pml.creatures.modifier", 100 * pml.getPMLModifierForCreature(creature));
					nextY += 2 * (4 + this.getFontRenderer().getWordWrappedHeight(text, colRightWidth));
					this.getFontRenderer().drawString(text, nextX, nextY, 0xFFFFFF, true);

					if(pml.getDeathCooldown() > 0){
						text = I18n.format("gui.beastiary.pml.creatures.death");
						nextY += 4 + this.getFontRenderer().getWordWrappedHeight(text, colRightWidth);
						this.getFontRenderer().drawString(text, nextX, nextY, 0xFFFFFF, true);
					}

					for(PlayerMobLevelsConfig.BonusCategory category : ForgeConfigProvider.getPmlBonusCategoryClientRenderOrder()){
						if(pmlBonusCateogories.containsKey(category)){
							rhNextY += 4 + this.getFontRenderer().getWordWrappedHeight("", this.colLeftWidth);
							if(PlayerMobLevelsConfig.getPmlBonusCategorySoulgazer().contains(category))
								this.drawTexture(new ResourceLocation(LycanitesMobs.modid, "textures/items/soulgazer.png"),rhNextX - 20, rhNextY - 4, 0, 1, 1, 16 ,16);
							text = I18n.format("gui.beastiary.pml.category." + category.name(), pmlBonusCateogories.get(category));
							this.getFontRenderer().drawString(text, rhNextX, rhNextY, 0xFFFFFF, true);
						}
					}
					text = I18n.format("gui.beastiary.pml.category.deathcooldown", pml.getDeathCooldown() / 20);
					rhNextY += 4 + this.getFontRenderer().getWordWrappedHeight("", this.colLeftWidth);
					this.getFontRenderer().drawString(text, rhNextX, rhNextY, 0xFFFFFF, true);
				}
			}
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
	}

	@Override
	protected void actionPerformed(GuiButton guiButton) throws IOException {
		super.actionPerformed(guiButton);
		if (guiButton != null) {
			IPlayerMobLevelCapability pml = PlayerMobLevelCapability.getForPlayer(this.player);
			if(pml != null && this.creaturePreviewEntity instanceof BaseCreatureEntity) {
				if (guiButton.id == PMLBeastiaryScreen.ONE_BUTTON_ID) {
					try {
						pml.setPMLModifierForCreature((BaseCreatureEntity)this.creaturePreviewEntity, Float.parseFloat(this.commandTextField.getText()));
					} catch (Exception exception) {
						LycanitesTweaks.LOGGER.error(exception);
					}
					this.commandTextField.setText(String.valueOf(pml.getPMLModifierForCreature((BaseCreatureEntity)this.creaturePreviewEntity)));
				}
				if (guiButton.id == PMLBeastiaryScreen.ALL_BUTTON_ID) {
					try {
						pml.setPMLModifierForAll(Float.parseFloat(this.commandTextField.getText()));
					} catch (Exception exception) {
						LycanitesTweaks.LOGGER.error(exception);
					}
					this.commandTextField.setText(String.valueOf(pml.getPMLModifierForCreature((BaseCreatureEntity)this.creaturePreviewEntity)));
				}
			}
		}
	}

	@Override
	public void onCreateDisplayEntity(CreatureInfo creatureInfo, EntityLivingBase entity) {
		super.onCreateDisplayEntity(creatureInfo, entity);
		IPlayerMobLevelCapability pml = PlayerMobLevelCapability.getForPlayer(this.player);
		if(pml != null && entity instanceof BaseCreatureEntity){
			this.commandTextField.setText(String.valueOf(pml.getPMLModifierForCreature((BaseCreatureEntity) entity)));
			this.commandTextField.setVisible(true);
			this.setOneButton.visible = true;
			this.setAllButton.visible = true;
		}
		else {
			this.commandTextField.setVisible(false);
			this.setOneButton.visible = false;
			this.setAllButton.visible = false;
		}
	}
}
