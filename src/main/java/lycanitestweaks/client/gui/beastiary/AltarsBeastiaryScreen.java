package lycanitestweaks.client.gui.beastiary;

import com.lycanitesmobs.GuiHandler;
import com.lycanitesmobs.LycanitesMobs;
import com.lycanitesmobs.client.gui.beastiary.BeastiaryScreen;
import com.lycanitesmobs.core.info.AltarInfo;
import lycanitestweaks.client.gui.beastiary.lists.AltarFilterList;
import lycanitestweaks.client.gui.beastiary.lists.AltarList;
import lycanitestweaks.client.gui.beastiary.lists.AltarTypeList;
import lycanitestweaks.info.altar.IAltarBeastiaryRender;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class AltarsBeastiaryScreen extends BeastiaryScreen {
	public AltarFilterList altarTypeList;
	public AltarList altarList;

	public static final byte BEASTIARY_ALTAR_ID = 5;

	/**
	 * Opens this GUI up to the provided player.
	 * @param player The player to open the GUI to.
	 */
	public static void openToPlayer(EntityPlayer player) {
		if(player != null) {
			player.openGui(LycanitesMobs.instance, GuiHandler.GuiType.BEASTIARY.id, player.getEntityWorld(), AltarsBeastiaryScreen.BEASTIARY_ALTAR_ID, 0, 0);
		}
	}


	public AltarsBeastiaryScreen(EntityPlayer player) {
		super(player);
	}


	@Override
	public String getTitle() {
		if(!AltarInfo.altars.isEmpty()){
			String altarTitle = this.altarList.getSelectedAltarTitle();
			if(altarTitle.isEmpty()) return "Select an Altar";
			return altarTitle;
		}
		else {
			return "Altars Disabled";
		}
	}


	@Override
	public void initControls() {
		super.initControls();

		int petTypeListHeight = Math.round((float)this.colLeftHeight * 0.225F);
		int petTypeListY = this.colLeftY;
		this.altarTypeList = new AltarTypeList(this, this.colLeftWidth, petTypeListHeight, petTypeListY, petTypeListY + petTypeListHeight, this.colLeftX);

		int petListHeight = Math.round((float)this.colLeftHeight * 0.7F);
		int petListY = petTypeListY + petTypeListHeight + Math.round((float)this.colLeftHeight * 0.025F);
		this.altarList = new AltarList(AltarList.Type.CHALLENGE, this, this.altarTypeList, this.colLeftWidth, petListHeight, petListY, petListY + petListHeight, this.colLeftX);
	}


	@Override
	public void drawBackground(int mouseX, int mouseY, float partialTicks) {
		super.drawBackground(mouseX, mouseY, partialTicks);
	}


	@Override
	protected void updateControls(int mouseX, int mouseY, float partialTicks) {
		super.updateControls(mouseX, mouseY, partialTicks);

		if(AltarInfo.altarsEnabled) {
			this.altarTypeList.drawScreen(mouseX, mouseY, partialTicks);
			this.altarList.drawScreen(mouseX, mouseY, partialTicks);
		}
	}


	@Override
	public void drawForeground(int mouseX, int mouseY, float partialTicks) {
		super.drawForeground(mouseX, mouseY, partialTicks);

		GlStateManager.pushMatrix();
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-180.0F, 0.0F, 0.0F, 1.0F);

		this.renderChallengeAltar(
				this.altarList.getSelectedChallengeBlockPatternStrings(),
				this.colRightX + (this.colRightWidth / 2),
				this.colRightY + Math.round((float) this.colRightHeight / 2)
		);
		this.renderBossAltar(
				this.altarList.getSelectedBossSoulcube(),
				this.colRightX + (this.colRightWidth / 2),
				this.colRightY + Math.round((float) this.colRightHeight / 3)
		);
		if(this.altarList.getSelectedCustomAltar() instanceof IAltarBeastiaryRender) ((IAltarBeastiaryRender) this.altarList.getSelectedCustomAltar()).getBeastiaryRender(this, mouseX, mouseY, partialTicks);

		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
	}

	public void renderChallengeAltar(String[] blockPatternStrings, int xPos, int yPos) {
		 Clear:
		if(blockPatternStrings == null) {
			return;
		}

		// Render:
		float scale = (this.mc.displayWidth / 1920F) * 4.0F;
		int drawOffsetX = (int) (12 * scale);
		int drawOffsetY = (int) (12 * scale);
		int drawOffsetZ = (int) (4 * scale);
		int startX = xPos - (drawOffsetX * blockPatternStrings[0].length() / 2);
		int startY = yPos - (drawOffsetY * blockPatternStrings.length / 4);
		int drawX;
		int drawY = startY;

		for (int yIndex = 0; yIndex < blockPatternStrings.length; yIndex++) {
			drawX = startX;
            for (int xIndex = 0; xIndex < blockPatternStrings[yIndex].length(); xIndex++) {
                switch (blockPatternStrings[yIndex].charAt(xIndex)) {
                    case '#':
                        this.drawItemStack(new ItemStack(Blocks.OBSIDIAN), drawX, drawY - (xIndex * drawOffsetY / 2), (xIndex + yIndex) * drawOffsetZ, scale);
                        break;
                    case '^':
                        this.drawItemStack(new ItemStack(Blocks.DIAMOND_BLOCK), drawX, drawY - (xIndex * drawOffsetY / 2), (xIndex + yIndex) * drawOffsetZ, scale);
                        break;
                }
                drawX += drawOffsetX;
            }
            drawY += drawOffsetY;
        }
    }
	public void renderBossAltar(Block coreBlock, int xPos, int yPos) {
		Clear:
		if(coreBlock == null) {
			return;
		}

		// Render:
		float scale = (this.mc.displayWidth / 1920F) * 4.5F;
		int drawOffsetX = (int) (10 * scale);
		int drawOffsetY = (int) (10 * scale);
		int drawOffsetZ = (int) (10 * scale);
		int rotationOffset = (int) (1 * scale);
		int startX = xPos - (drawOffsetX * 3 / 4);
		int startY = yPos + (drawOffsetY * 3 / 4);
		int drawX = startX - (drawOffsetX * 5 / 2);;
		int drawY = startY - (drawOffsetY * 3 / 2);

		// TODO Render bottom up instead of top down
		for (int sidePillar = 0; sidePillar < 2; sidePillar++) {
			String[] blockPattern = AltarList.PILLAR_BLOCK_PATTERN_STRINGS;
			for (int yIndex = 0; yIndex < blockPattern.length; yIndex++) {
				switch (blockPattern[yIndex].charAt(0)) {
					case '#':
						this.drawItemStack(new ItemStack(Blocks.OBSIDIAN), drawX, drawY + (yIndex * drawOffsetY), (yIndex) * drawOffsetZ, scale);
						break;
					case '^':
						this.drawItemStack(new ItemStack(Blocks.DIAMOND_BLOCK), drawX, drawY + (yIndex * drawOffsetY), (yIndex) * drawOffsetZ, scale);
						break;
				}
			}
			drawX += drawOffsetX * 5;
			drawY += rotationOffset * 2;
		}

		drawX = startX - (rotationOffset * 2);
		drawY = startY;
		for (int centerPillar = 0; centerPillar < 2; centerPillar++) {
			String[] blockPattern = AltarList.PILLAR_BLOCK_PATTERN_STRINGS;
			for (int yIndex = 0; yIndex < blockPattern.length; yIndex++) {
				switch (blockPattern[yIndex].charAt(0)) {
					case '#':
						this.drawItemStack(new ItemStack(Blocks.OBSIDIAN), drawX, drawY + (yIndex * drawOffsetY), (yIndex + centerPillar) * drawOffsetZ, scale);
						break;
					case '^':
						this.drawItemStack(new ItemStack(Blocks.DIAMOND_BLOCK), drawX, drawY + (yIndex * drawOffsetY), (yIndex + centerPillar) * drawOffsetZ, scale);
						break;
				}
			}
			drawX += rotationOffset * 4;
			drawY -= (drawOffsetY * 3);
		}

		drawX = startX;
		drawY = startY - (drawOffsetY * 5 / 2);
		String[] blockPattern = AltarList.CORE_BLOCK_PATTERN_STRINGS;
		for (int yIndex = 0; yIndex < blockPattern.length; yIndex++) {
			switch (blockPattern[yIndex].charAt(0)) {
				case '#':
					this.drawItemStack(new ItemStack(Blocks.OBSIDIAN), drawX, drawY + (yIndex * drawOffsetY), (yIndex) * drawOffsetZ, scale);
					break;
				case '^':
					this.drawItemStack(new ItemStack(coreBlock), drawX, drawY + (yIndex * drawOffsetY), (yIndex) * drawOffsetZ, scale);
					break;
			}
		}
	}

	// From ice and fire bestiary
	public void drawItemStack(ItemStack stack, int x, int y, int z, float scale) {
		int xScaled = (int)(x / scale);
		int yScaled = (int)(y / scale);
		GlStateManager.pushMatrix();
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.translate(0, 0, -512.0F);
		this.zLevel = 512.0F - z;
		this.itemRender.zLevel = 512.0F - z;
		net.minecraft.client.gui.FontRenderer font = null;
		if (!stack.isEmpty()) font = stack.getItem().getFontRenderer(stack);
		if (font == null) font = this.getFontRenderer();
		this.itemRender.renderItemAndEffectIntoGUI(stack, xScaled, yScaled);
		this.itemRender.renderItemOverlayIntoGUI(font, stack, xScaled, yScaled, null);
		this.zLevel = 0.0F;
		this.itemRender.zLevel = 0.0F;
		GlStateManager.popMatrix();
	}
}
