package lycanitestweaks.compat;

import mcjty.tools.typed.Key;
import mcjty.tools.typed.Type;

public class InControlCompat {

    public static final Key<Integer> MIN_LEVEL = Key.create(Type.INTEGER, "lycanites_minlevel");
    public static final Key<Integer> MAX_LEVEL = Key.create(Type.INTEGER, "lycanites_maxlevel");
    public static final Key<Integer> IS_SUBSPECIES = Key.create(Type.INTEGER, "lycanites_issubspecies");

    public static final Key<Boolean> IS_UNCOMMON = Key.create(Type.BOOLEAN, "lycanites_isuncommon");
    public static final Key<Boolean> IS_RARE = Key.create(Type.BOOLEAN, "lycanites_israre");
    public static final Key<Boolean> IS_SPAWNEDASBOSS = Key.create(Type.BOOLEAN, "lycanites_isspawnedasboss");

    public static final Key<Integer> ADD_LEVEL = Key.create(Type.INTEGER, "lycanites_addlevel");
    public static final Key<Integer> SET_LEVEL = Key.create(Type.INTEGER, "lycanites_setlevel");
    public static final Key<Integer> SET_SUBSPECIES = Key.create(Type.INTEGER, "lycanites_setsubspecies");
    public static final Key<Integer> SET_VARIANT = Key.create(Type.INTEGER, "lycanites_setvariant");

    public static final Key<Boolean> SET_SPAWNEDASBOSS = Key.create(Type.BOOLEAN, "lycanites_setspawnedasboss");
    public static final Key<Boolean> SET_BOSSDAMAGELIMIT = Key.create(Type.BOOLEAN, "lycanites_setbossdamagelimit");
}
