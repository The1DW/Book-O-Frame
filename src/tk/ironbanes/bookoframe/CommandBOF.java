package tk.ironbanes.bookoframe;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CommandBOF implements CommandExecutor {
    List<String> Lore = new ArrayList<>();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("bookoframe")){
            if(sender instanceof Player){
                Player p = (Player) sender;
                ItemStack itemframe = new ItemStack(Material.ITEM_FRAME);
                ItemMeta itemmeta = itemframe.getItemMeta();
                Lore.add(ChatColor.BLUE + ("This item frame allows for the copying of books"));
                itemmeta.setLore(Lore);
                itemframe.setItemMeta(itemmeta);
                itemframe.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
                p.getInventory().addItem(itemframe);
                p.updateInventory();
                Lore.clear();
            }
        }
        return false;
    }

}
