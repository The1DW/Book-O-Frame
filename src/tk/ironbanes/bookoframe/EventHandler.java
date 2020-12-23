package tk.ironbanes.bookoframe;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class EventHandler implements Listener
{
    //Checks if the entity the player is clicking on is an Item Frame
    @org.bukkit.event.EventHandler
    public void onPlayerEntityInteract(PlayerInteractEntityEvent event)
    {
        Player p = event.getPlayer();
        Entity entity = event.getRightClicked();
        if(entity instanceof ItemFrame)
        {
            //if the entity was an item frame it will create a copy of the book in the item frame.
            ItemStack ItemInFrame = ((ItemFrame) entity).getItem();
            if (ItemInFrame.getType() == Material.WRITTEN_BOOK)
            {
                //adds the item and updates the inventory of the player
                p.getInventory().addItem(ItemInFrame);
                p.updateInventory();
            }
        }
    }



}
