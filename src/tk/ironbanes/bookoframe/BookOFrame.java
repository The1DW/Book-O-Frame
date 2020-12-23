package tk.ironbanes.bookoframe;

import org.bukkit.plugin.java.JavaPlugin;

public class BookOFrame extends JavaPlugin {

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(new EventHandler(), this);
        //getServer().getPluginManager().registerEvents(new EventHandler().onPlayerEntityInteract();, this);


    }



    @Override
    public void onDisable() {

    }
}
