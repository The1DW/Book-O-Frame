package tk.ironbanes.bookoframe;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBOF implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("bookoframe")){
            if(sender instanceof Player){
                Player p = (Player) sender;
                p.sendMessage(p.getName());
            }
        }
        return false;
    }

}
