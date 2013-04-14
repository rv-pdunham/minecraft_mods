package pdunham.weird.objects;

import pdunham.weird.common.StandardLogger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.entity.player.PlayerEvent;

@Cancelable
public class PebbleLooseEvent extends PlayerEvent
{
    public final ItemStack sling;
    public int charge;
	private static StandardLogger logger;
    
    public PebbleLooseEvent(EntityPlayer player, ItemStack sling, int charge)
    {
        super(player);
        this.sling= sling;
        this.charge = charge;

        logger = new StandardLogger("PebbleLooseEvent");
        logger.info("c'tor() complete player: " + player + ", sling " + sling + ", charge " + charge);
    }
}