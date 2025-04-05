package lycanitestweaks.entity.goals.actions.abilities;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.goals.BaseGoal;
import com.lycanitesmobs.core.info.CreatureInfo;
import com.lycanitesmobs.core.info.CreatureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class SummonLeveledMinionsGoal extends BaseGoal {
    // Properties:
	protected int summonTime = 0;
	protected int summonRate = 60;
	protected int summonCap = 5;
	protected int subSpeciesIndex = -1;
	protected int variantIndex = -1;
	protected CreatureInfo minionInfo;
	protected boolean perPlayer = false;
	protected boolean antiFlight = false;
	protected double sizeScale = -1;

	/**
	 * Constructor
	 * @param setHost The creature using this goal.
	 */
	public SummonLeveledMinionsGoal(BaseCreatureEntity setHost) {
		super(setHost);
    }

	/**
	 * Sets the rate of summoning (in ticks).
	 * @param summonRate The summoning tick rate.
	 * @return This goal for chaining.
	 */
	public SummonLeveledMinionsGoal setSummonRate(int summonRate) {
    	this.summonRate = summonRate;
    	return this;
    }

	/**
	 * Sets the minion count cap for summoning.
	 * @param summonCap The summoning cap.
	 * @return This goal for chaining.
	 */
	public SummonLeveledMinionsGoal setSummonCap(int summonCap) {
		this.summonCap = summonCap;
		return this;
	}

	/**
	 * Sets the sub species index for summoning.
	 * @param subSpeciesIndex The sub species index.
	 * @return This goal for chaining.
	 */
	public SummonLeveledMinionsGoal setSubSpeciesIndex(int subSpeciesIndex) {
		this.subSpeciesIndex = subSpeciesIndex;
		return this;
	}

	/**
	 * Sets the variant index for summoning.
	 * @param variantIndex The variant index.
	 * @return This goal for chaining.
	 */
	public SummonLeveledMinionsGoal setVariantIndex(int variantIndex) {
		this.variantIndex = variantIndex;
		return this;
	}

	/**
	 * If true, the cap is scaled per players detected.
	 * @param perPlayer True to enable.
	 * @return This goal for chaining.
	 */
	public SummonLeveledMinionsGoal setPerPlayer(boolean perPlayer) {
		this.perPlayer = perPlayer;
		return this;
	}

	/**
	 * Sets anti flight summoning where minions are summoned at any player targets that are flying.
	 * @param antiFlight True to enable.
	 * @return This goal for chaining.
	 */
	public SummonLeveledMinionsGoal setAntiFlight(boolean antiFlight) {
    	this.antiFlight = antiFlight;
    	return this;
    }

	/**
	 * Sets the creature to summon.
	 * @param creatureName The creature name to summon.
	 * @return This goal for chaining.
	 */
	public SummonLeveledMinionsGoal setMinionInfo(String creatureName) {
    	this.minionInfo = CreatureManager.getInstance().getCreature(creatureName);
    	return this;
    }

	/**
	 * Sets the scale to multiple the minion's size by.
	 * @param sizeScale The scale to multiple the creature's size by.
	 * @return This goal for chaining.
	 */
	public SummonLeveledMinionsGoal setSizeScale(double sizeScale) {
		this.sizeScale = sizeScale;
		return this;
	}

	@Override
    public boolean shouldExecute() {
		if(this.host.isPetType("familiar")) {
			return false;
		}
		return super.shouldExecute() && this.minionInfo != null;
    }

	@Override
    public void startExecuting() {
		this.summonTime = 1;
	}

	@Override
    public void updateTask() {
		if(this.summonTime++ % this.summonRate != 0) {
			return;
		}

		if(this.host.getMinions(this.minionInfo.getEntityClass()).size() >= this.summonCap) {
			return;
		}

		// Anti Flight Mode:
		if(this.antiFlight) {
			for (EntityPlayer target : this.host.playerTargets) {
				if(target.isCreative() || target.isSpectator())
					continue;
				if (CreatureManager.getInstance().config.bossAntiFlight > 0 && target.posY > this.host.posY + CreatureManager.getInstance().config.bossAntiFlight + 1) {
					this.summonMinion(target);
				}
			}
			return;
		}

		this.summonMinion(this.host.getAttackTarget());
    }

    protected void summonMinion(EntityLivingBase target) {
		EntityLivingBase minion = this.minionInfo.createEntity(this.host.getEntityWorld());
		this.host.summonMinion(minion, this.host.getRNG().nextDouble() * 360, this.host.width + 1);
		if(minion instanceof BaseCreatureEntity) {
			BaseCreatureEntity minionCreature = (BaseCreatureEntity)minion;
			minionCreature.setAttackTarget(target);
			minionCreature.enablePersistence();

			if((this.sizeScale == -1)) minionCreature.setSizeScale(minionCreature.sizeScale * this.host.sizeScale);
			else minionCreature.setSizeScale(minionCreature.sizeScale * this.sizeScale);

			if((this.subSpeciesIndex == -1)) minionCreature.setSubspecies(this.host.getSubspeciesIndex());
			else minionCreature.setSubspecies(this.subSpeciesIndex);

            if((this.variantIndex == -1)) minionCreature.setVariant(this.host.getVariantIndex());
			else minionCreature.setVariant(this.variantIndex);

			minionCreature.applyLevel(host.getLevel()); // refresh stats
        }
	}
}
