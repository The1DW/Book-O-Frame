package tk.ironbanes.bookoframe;

import org.apache.tools.ant.types.resources.selectors.InstanceOf;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class EventHandler implements Listener {

    @org.bukkit.event.EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Bukkit.broadcastMessage("You're a Wizard " + player.getPlayerListName());

    }

    /*@org.bukkit.event.EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent event){
        ItemStack item = event.getItemInHand();
        ItemStack frame = new ItemStack(Material.ITEM_FRAME);
        if(item == frame){

        }

    }*/

    @org.bukkit.event.EventHandler
    public void onPlayerEntityInteract(PlayerInteractEntityEvent event){
        Player p = event.getPlayer();
        Entity entity = event.getRightClicked();
        if(entity instanceof ItemFrame){
            ItemStack ItemInFrame = ((ItemFrame) entity).getItem();
            if (ItemInFrame.getType() == Material.WRITTEN_BOOK){
                p.getInventory().addItem(ItemInFrame);
                p.updateInventory();
            }
        }




        /*if( == Action.RIGHT_CLICK_BLOCK){
            Entity e = event.get;
            if(e InstanceOf ItemFrame){
                //ItemStack frame = Material.ITEM_FRAME;
            }

        }*/

    }



}
