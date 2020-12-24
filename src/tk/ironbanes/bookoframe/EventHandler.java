package tk.ironbanes.bookoframe;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

public class EventHandler implements Listener
{
    //Checks if the entity the player is clicking on is an Item Frame
    @org.bukkit.event.EventHandler
    public void onPlayerEntityInteract(PlayerInteractEntityEvent event)
    {
        Player p = event.getPlayer();
        Entity entity = event.getRightClicked();
        p.sendMessage("entity message");
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

    @org.bukkit.event.EventHandler
    public void onPlaceFrame(HangingPlaceEvent event){
        Player player = event.getPlayer();
        Entity entity = event.getEntity();
        player.sendMessage("Hello it is working");
        if (entity instanceof ItemFrame){
            ItemStack itemstack = player.getInventory().getItemInMainHand();
            if (itemstack.hasItemMeta()){
                player.sendMessage("IT HAS META");
                ItemMeta itemMeta = itemstack.getItemMeta();
                List<String> Lore = new ArrayList<>();
                Lore.add(ChatColor.BLUE + ("This item frame allows for the copying of books"));
                if (Lore.equals(itemMeta.getLore())){

                }
                Lore.clear();




            }



        }
    }

    /*@org.bukkit.event.EventHandler
    public void onPlayerEntityPlacement(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

    }*/



}
