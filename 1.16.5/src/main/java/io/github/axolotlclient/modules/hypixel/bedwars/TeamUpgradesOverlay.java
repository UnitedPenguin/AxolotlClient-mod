/*
 * Copyright © 2021-2023 moehreag <moehreag@gmail.com> & Contributors
 *
 * This file is part of AxolotlClient.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * For more information, see the LICENSE file.
 */

package io.github.axolotlclient.modules.hypixel.bedwars;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.axolotlclient.AxolotlClientConfig.options.BooleanOption;
import io.github.axolotlclient.AxolotlClientConfig.options.Option;
import io.github.axolotlclient.modules.hud.gui.entry.BoxHudEntry;
import io.github.axolotlclient.modules.hud.util.DrawPosition;
import io.github.axolotlclient.modules.hypixel.bedwars.upgrades.BedwarsTeamUpgrades;
import io.github.axolotlclient.modules.hypixel.bedwars.upgrades.TeamUpgrade;
import io.github.axolotlclient.modules.hypixel.bedwars.upgrades.TextureInfo;
import io.github.axolotlclient.modules.hypixel.bedwars.upgrades.TrapUpgrade;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

/**
 * @author DarkKronicle
 */

public class TeamUpgradesOverlay extends BoxHudEntry {

	public final static Identifier ID = new Identifier("axolotlclient", "bedwars_teamupgrades");

	private final BooleanOption renderWhenRelevant = new BooleanOption(ID.getPath()+".renderWhenRelevant", true);

	private BedwarsTeamUpgrades upgrades = null;
	private final BedwarsMod mod;
	private final MinecraftClient mc;
	private final static TextureInfo[] trapEdit = {TrapUpgrade.TrapType.MINER_FATIGUE.getTexInfo(), TrapUpgrade.TrapType.ITS_A_TRAP.getTexInfo()};

	public TeamUpgradesOverlay(BedwarsMod mod) {
		super(60, 40, true);
		this.mod = mod;
		this.mc = MinecraftClient.getInstance();
	}

	public void onStart(BedwarsTeamUpgrades newUpgrades) {
		upgrades = newUpgrades;
	}

	public void onEnd() {
		upgrades = null;
	}

	@Override
	public void render(MatrixStack matrices, float delta) {
		if (!renderWhenRelevant.get() || mod.inGame()) {
			super.render(matrices, delta);
		}
	}

	public void drawOverlay(MatrixStack stack, DrawPosition position, boolean editMode) {
		if (upgrades == null && !editMode) {
			return;
		}

		int x = position.x() + 1;
		int y = position.y() + 2;
		RenderSystem.enableAlphaTest();
		RenderSystem.enableBlend();
		RenderSystem.color4f(1, 1, 1, 1);
		boolean normalUpgrades = false;
		if (upgrades != null) {
			for (TeamUpgrade u : upgrades.upgrades) {
				if (!u.isPurchased()) {
					continue;
				}
				if (u instanceof TrapUpgrade) {
					continue;
				}
				TextureInfo texture;
				texture = u.getTexture()[0];
				RenderSystem.color4f(texture.getColor().getAlpha(), texture.getColor().getRed(), texture.getColor().getBlue(), texture.getColor().getGreen());
				mc.getTextureManager().bindTexture(new Identifier("minecraft", texture.getTexture()));
				DrawableHelper.drawTexture(stack, x, y, 16, 16, texture.getU(), texture.getV(), texture.getRegionWidth(), texture.getRegionHeight(), texture.getWidth(), texture.getHeight());
				RenderSystem.color4f(1, 1, 1, 1);
				x += 17;
				normalUpgrades = true;
			}
		}
		x = position.x() + 1;
		if (normalUpgrades) {
			y += 17;
		}
		for (TextureInfo texture : (editMode ? trapEdit : upgrades.trap.getTexture())) {
			RenderSystem.color4f(texture.getColor().getAlpha(), texture.getColor().getRed(), texture.getColor().getBlue(), texture.getColor().getGreen());
			mc.getTextureManager().bindTexture(new Identifier("minecraft", texture.getTexture()));
			DrawableHelper.drawTexture(stack, x, y, 16, 16, texture.getU(), texture.getV(), texture.getRegionWidth(), texture.getRegionHeight(), texture.getWidth(), texture.getHeight());
			RenderSystem.color4f(1, 1, 1, 1);
			x += 17;
		}
	}

	@Override
	public void renderComponent(MatrixStack stack, float delta) {
		drawOverlay(stack, getPos(), false);
	}

	@Override
	public void renderPlaceholderComponent(MatrixStack stack, float delta) {
		drawOverlay(stack, getPos(), true);
	}

	@Override
	public Identifier getId() {
		return ID;
	}

	@Override
	public List<Option<?>> getConfigurationOptions() {
		List<Option<?>> options = super.getConfigurationOptions();
		options.add(renderWhenRelevant);
		return options;
	}
}
