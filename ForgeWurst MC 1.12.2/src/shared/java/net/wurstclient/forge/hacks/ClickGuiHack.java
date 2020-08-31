/*
 * Copyright (C) 2017 - 2019 | Wurst-Imperium | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.forge.hacks;

import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.wurstclient.fmlevents.WGuiInventoryButtonEvent;
import net.wurstclient.forge.Hack;
import net.wurstclient.forge.clickgui.ClickGuiScreen;
import net.wurstclient.forge.settings.CheckboxSetting;
import net.wurstclient.forge.settings.SliderSetting;
import net.wurstclient.forge.settings.SliderSetting.ValueDisplay;

@Hack.DontSaveState
public final class ClickGuiHack extends Hack
{
	private final SliderSetting opacity = new SliderSetting("不透明度", 0.5,
		0.15, 0.85, 0.01, ValueDisplay.PERCENTAGE);
	private final SliderSetting maxHeight = new SliderSetting("最大高度",
		"最大窗高度\n" + "0 = 不限制", 200, 0, 1000, 25,
		ValueDisplay.INTEGER);
	
	private final SliderSetting bgRed = new SliderSetting("背景 红",
		"背景 红", 64, 0, 255, 1, ValueDisplay.INTEGER);
	private final SliderSetting bgGreen = new SliderSetting("背景 黄",
		"背景 黄", 64, 0, 255, 1, ValueDisplay.INTEGER);
	private final SliderSetting bgBlue = new SliderSetting("背景 蓝",
		"背景 蓝", 64, 0, 255, 1, ValueDisplay.INTEGER);
	
	private final SliderSetting acRed = new SliderSetting("线条 红",
		"线条 红", 16, 0, 255, 1, ValueDisplay.INTEGER);
	private final SliderSetting acGreen = new SliderSetting("线条 黄",
		"线条 黄", 16, 0, 255, 1, ValueDisplay.INTEGER);
	private final SliderSetting acBlue = new SliderSetting("线条 蓝",
		"线条 蓝", 16, 0, 255, 1, ValueDisplay.INTEGER);
	
	private final CheckboxSetting inventoryButton =
		new CheckboxSetting("背包按钮",
			"一个按钮，允许您从背包界面打开控制界面。\n"
				+ "这样就可以不用绑定快捷键",
			true);
	
	public ClickGuiHack()
	{
		super("ClickGUI", "");
		addSetting(opacity);
		addSetting(maxHeight);
		addSetting(bgRed);
		addSetting(bgGreen);
		addSetting(bgBlue);
		addSetting(acRed);
		addSetting(acGreen);
		addSetting(acBlue);
		addSetting(inventoryButton);
		
		MinecraftForge.EVENT_BUS.register(new InventoryButtonAdder());
	}
	
	@Override
	protected void onEnable()
	{
		mc.displayGuiScreen(new ClickGuiScreen(wurst.getGui()));
		setEnabled(false);
	}
	
	public float getOpacity()
	{
		return opacity.getValueF();
	}
	
	public int getMaxHeight()
	{
		return maxHeight.getValueI();
	}
	
	public void setMaxHeight(int maxHeight)
	{
		this.maxHeight.setValue(maxHeight);
	}
	
	public float[] getBgColor()
	{
		return new float[]{bgRed.getValueI() / 255F, bgGreen.getValueI() / 255F,
			bgBlue.getValueI() / 255F};
	}
	
	public float[] getAcColor()
	{
		return new float[]{acRed.getValueI() / 255F, acGreen.getValueI() / 255F,
			acBlue.getValueI() / 255F};
	}
	
	public boolean isInventoryButton()
	{
		return inventoryButton.isChecked();
	}
	
	public void setInventoryButton(boolean checked)
	{
		inventoryButton.setChecked(checked);
	}
	
	public final class InventoryButtonAdder
	{
		@SubscribeEvent
		public void onGuiInventoryInit(WGuiInventoryButtonEvent.Init event)
		{
			if(!inventoryButton.isChecked())
				return;
			
			event.getButtonList()
				.add(new GuiButton(-1, mc.currentScreen.width / 2 - 50,
					mc.currentScreen.height / 2 - 120, 100, 20, "ForgeWurst"));
		}
		
		@SubscribeEvent
		public void onGuiInventoryButtonPress(
			WGuiInventoryButtonEvent.Press event)
		{
			if(event.getButton().id != -1)
				return;
			
			setEnabled(true);
		}
	}
}
