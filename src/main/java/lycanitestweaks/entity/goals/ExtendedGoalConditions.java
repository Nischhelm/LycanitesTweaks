package lycanitestweaks.entity.goals;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.goals.GoalConditions;

public class ExtendedGoalConditions extends GoalConditions{

    protected int minimumBattlePhase = -1;

    public ExtendedGoalConditions setMinimumBattlePhase(int minimumBattlePhase) {
        this.minimumBattlePhase = minimumBattlePhase;
        return this;
    }

    @Override
    public boolean isMet(BaseCreatureEntity creatureEntity) {
        if (this.rareVariantOnly && !creatureEntity.isRareVariant())
            return false;
        else if(this.minimumBattlePhase > 0)
            return creatureEntity.getBattlePhase() >= this.minimumBattlePhase;
        else
            return this.battlePhase < 0 || creatureEntity.getBattlePhase() == this.battlePhase;
    }
}