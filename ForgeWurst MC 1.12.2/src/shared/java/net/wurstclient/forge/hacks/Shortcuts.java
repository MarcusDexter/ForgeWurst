package net.wurstclient.forge.hacks;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.wurstclient.fmlevents.WGuiInventoryButtonEvent;
import net.wurstclient.forge.Hack;
import net.wurstclient.forge.Command;
import net.wurstclient.forge.utils.ChatUtils;

public final class Shortcuts extends Hack
{
    public Shortcuts()
    {
        super("Shortcusts", "");
        MinecraftForge.EVENT_BUS.register(new InventoryButtonAdder());
    }

    public final class InventoryButtonAdder
    {
        String[] commandArray = {
                "ewb","ec","island","home", "spawn","tpaccept","marry home","fix all", "sell",
                "top", "jump", "fly on", "fly off", "money", "back"
        };
        int buttonIdBase = 8600;

        @SubscribeEvent
        public void onGuiInventoryInit(WGuiInventoryButtonEvent.Init event)
        {
            ChatUtils.message(mc.currentScreen.width + "X" + mc.currentScreen.height);

            int width = 9;
            int height = 2;
            int baseX = mc.currentScreen.width / 2 - 235;
            int baseY = mc.currentScreen.height / 2 + 84;
            int offset = 3;
            int widthIn = 48;
            int heightIn = 20;

            for (int i = 0; i < height; i++) {
                for (int j = 0; i * width + j < commandArray.length && j < width; j++) {
                    event.getButtonList().add(new GuiButton(buttonIdBase + i * width + j, baseX + j * (widthIn + offset),
                            baseY + i * (heightIn + offset), widthIn, heightIn, commandArray[i * width + j]));
                }
            }

        }

        @SubscribeEvent
        public void onGuiInventoryButtonPress(
                WGuiInventoryButtonEvent.Press event)
        {
            int buttonId = event.getButton().id;
            if(buttonId >= buttonIdBase && buttonId < buttonIdBase + commandArray.length)
                mc.getConnection().sendPacket(new CPacketChatMessage("/" + commandArray[buttonId - buttonIdBase]));
        }
    }
}