package prospectpyxis.worsesolars.recipe;

import com.google.gson.JsonObject;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import prospectpyxis.worsesolars.ModSettings;
import prospectpyxis.worsesolars.WorseSolars;

import java.util.function.BooleanSupplier;

public class InfiniteConditionFactory implements IConditionFactory {

    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {
        boolean value = JsonUtils.getBoolean(json , "value", true);
        String key = JsonUtils.getString(json, "type");

        if (key.equals(WorseSolars.modid + ":infinite_enabled")) {
            return () -> ModSettings.infiniteSolars.enabled == value;
        }

        return null;
    }
}
