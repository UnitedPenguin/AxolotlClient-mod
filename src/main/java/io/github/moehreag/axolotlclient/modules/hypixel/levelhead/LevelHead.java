package io.github.moehreag.axolotlclient.modules.hypixel.levelhead;

import io.github.moehreag.axolotlclient.config.options.BooleanOption;
import io.github.moehreag.axolotlclient.config.options.ColorOption;
import io.github.moehreag.axolotlclient.config.options.LevelHeadOption;
import io.github.moehreag.axolotlclient.config.options.OptionCategory;
import io.github.moehreag.axolotlclient.config.Color;
import io.github.moehreag.axolotlclient.modules.hypixel.AbstractHypixelMod;
import net.minecraft.util.Identifier;

public class LevelHead implements AbstractHypixelMod {

    private static final LevelHead Instance = new LevelHead();

    private final OptionCategory category = new OptionCategory(new Identifier("levelhead"), "levelhead");
    public BooleanOption enabled = new BooleanOption("enabled", false);
    public BooleanOption background = new BooleanOption("background", false);
    public ColorOption textColor = new ColorOption("textColor", Color.GOLD);
    public LevelHeadOption mode = new LevelHeadOption("levelHeadMode");

    @Override
    public void init() {

        category.add(enabled);
        category.add(textColor);
        category.add(background);
        category.add(mode);
    }

    public static LevelHead getInstance(){
        return Instance;
    }

    @Override
    public OptionCategory getCategory() {
        return category;
    }
}
