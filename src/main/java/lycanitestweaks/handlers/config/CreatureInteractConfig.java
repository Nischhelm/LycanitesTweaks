package lycanitestweaks.handlers.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;

public class CreatureInteractConfig {

    @Config.Comment("Giving an Enchanted Golden Apple to a tamed creature will turn it into a baby")
    @Config.Name("Baby Age Gapple")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuretamedbabygapple.json")
    public boolean babyAgeGapple = true;

    @Config.Comment("Allow mounts to be use vanilla saddles based on levels")
    @Config.Name("Mount with Vanilla Saddles")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurelimitedvanillasaddle.json")
    public boolean mountVanillaSaddleLimited = true;

    @Config.Comment("In order to use a vanilla saddle, the mount must be at least this level")
    @Config.Name("Mount with Vanilla Saddles - Level Requirement")
    public int vanillaSaddleLevelRequirement = 16;

    @Config.Comment("Allow flying creatures use the vanilla saddle")
    @Config.Name("Mount with Vanilla Saddles - Allow Flying")
    public boolean vanillaSaddleAllowFlying = false;

    @Config.Comment("Allow the pet perch position to be modifiable")
    @Config.Name("Perch Position Modifiable")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureperchposition.json")
    public boolean perchPositionModifiable = true;

    @Config.Comment("Pet Perch Angle In Radians, Default Lycanites is 90")
    @Config.Name("Perch Position Modifiable - Angle Radians")
    @Config.RangeDouble(min = -360, max = 360)
    public double perchAngle = 90.0D;

    @Config.Comment("Pet Perch Distance Scale, based on ridden entity, Default Lycanites is 0.7")
    @Config.Name("Perch Position Modifiable - Distance")
    @Config.RangeDouble(min = 0)
    public double perchDistance = 1.0D;

    @Config.Comment("Modify distance checks to pickup mobs teleporting victims")
    @Config.Name("Pickup Checks Distances")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureentitypickupfix.json")
    public boolean pickupChecksDistance = true;

    @Config.Comment("Additionally have Darklings run the check for latch target")
    @Config.Name("Pickup Checks Distances - Darkling")
    public boolean pickupChecksDarkling = true;

    @Config.Comment("Distance between entities to trigger auto pickup drop, Default Lycanites is 32.")
    @Config.Name("Pickup Checks Distances - Value")
    @Config.RangeDouble(min = 0)
    public double pickUpDistance = 8.0D;

    @Config.Comment("Feeding tamed creatures Burritos and Risottos will increase/decrease size scale")
    @Config.Name("Size Change Foods")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuretamedsizechangefood.json")
    public boolean sizeChangeFoods = true;

    @Config.Comment("Max size change amount based on lycanitesmobs config range, default is 0.1, or 10% of config range")
    @Config.Name("Size Change Foods - Maximum Amount")
    @Config.RangeDouble(min = 0.0)
    public double sizeChangeDegree = 0.1D;

    @Config.Comment("Make Soul Gazing a creature riding an entity dismount and attack the player")
    @Config.Name("Soul Gazer Dismounts")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresoulgazerdismounts.json")
    public boolean soulGazerDismounts = true;

    @Config.Comment("Enable setting owned creature and animal variant status with Soul Keys")
    @Config.Name("Soulkeys Set Variant")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresoulkeyvariantset.json")
    public boolean soulkeysSetVariant = true;

    @Config.Comment("Allow creatures to be tamed/studied with their healing foods")
    @Config.Name("Tame Creatures with Diet")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuretamewithhealingfood.json")
    public boolean tameWithHealingFood = true;

    @Config.Comment("Chance for a successful attempt to tame with healing food")
    @Config.Name("Tame Creatures with Diet - Chance")
    @Config.RangeDouble(min = 0.0, max = 1.0)
    public float tameWithFoodChance = 0.3F;

    @Config.Comment("Allow flying creatures to be tamed with healing food")
    @Config.Name("Tame Creatures with Diet - Allow Flying")
    public boolean tamedWithFoodAllowFlying = false;

    @Config.Comment("Feeding Treats will prevent natural despawning, set temporary duration will still despawn")
    @Config.Name("Treat Sets Persistence")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuretameabletreatpersistence.json")
    public boolean treatSetsPersistence = true;
}
