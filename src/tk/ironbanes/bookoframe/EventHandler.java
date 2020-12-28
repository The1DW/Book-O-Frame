package tk.ironbanes.bookoframe;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventHandler implements Listener
{
    BookOFrame plugin = BookOFrame.getPlugin(BookOFrame.class);

    //Checks if the entity the player is clicking on is an Item Frame
    @org.bukkit.event.EventHandler
    public void onPlayerEntityInteract(PlayerInteractEntityEvent event)
    {
        Player p = event.getPlayer();
        if (p.hasPermission("bookoframe.use")){
            Entity entity = event.getRightClicked();

            //checks to see if the Right Clicked entity is an item frame
            //if the entity was an item frame it will go throught the if
            //otherwise it passes
            if(entity instanceof ItemFrame)
            {

                //if the entity was an item frame it will create a copy of the book in the item frame.
                ItemStack ItemInFrame = ((ItemFrame) entity).getItem();
                if (ItemInFrame.getType() == Material.WRITTEN_BOOK)
                {

                    //gets the values needed for checkSQLforBlock()
                    World world = entity.getLocation().getBlock().getWorld();
                    double x = entity.getLocation().getX();
                    double y = entity.getLocation().getY();
                    double z = entity.getLocation().getZ();
                    String blockname = world.getName()+ x +","+ y +","+ z;

                    //checks if the itemframe is in the list of copy frames
                    if(checkSQLForBlock(blockname)){
                        //adds the item and updates the inventory of the player
                        p.getInventory().addItem(ItemInFrame);
                        p.updateInventory();
                    }

                }
            }
        }

    }
    @org.bukkit.event.EventHandler
    public void onPlaceFrame(HangingPlaceEvent event)
    {
        Player player = event.getPlayer();

        //checks if the player has permission to place an item frame that can copy
        if(player.hasPermission("bookoframe.place")){
            Entity entity = event.getEntity();

            //checks if the entity was an item frame
            if (entity instanceof ItemFrame) {
                ItemStack itemframe = player.getInventory().getItemInMainHand();//gets the item fram data from the player
                //checks for itemmeta data on the item frame
                if (itemframe.hasItemMeta()) {
                    //compares the items meta data to the desired itemmeta/lore
                    ItemMeta itemMeta = itemframe.getItemMeta();
                    List<String> Lore = new ArrayList<>();
                    Lore.add(ChatColor.BLUE + (plugin.item_lore));
                    if (itemMeta != null) {

                        if (Lore.equals(itemMeta.getLore())) {
                            //if the item meta matches it get the entitys location
                            World world = entity.getLocation().getBlock().getWorld();
                            double x = entity.getLocation().getX();
                            double y = entity.getLocation().getY();
                            double z = entity.getLocation().getZ();
                            //blockname acts as our identification key for database
                            String blockname = world.getName() + x + "," + y + "," + z;
                            //with the blocks location you pass it to addBlockToSQL to add it to the
                            //sql database to keep track of blocks that can copy
                            addBlockToSQL(blockname, world, x, y, z);
                        }
                    }
                    //clears lore to prevent it from compounding
                    Lore.clear();
                }
            }
        }
    }
    @org.bukkit.event.EventHandler
    public void onBreakFrame(HangingBreakEvent event)
    {
        Entity entity = event.getEntity();
        //check if item frame
        if (entity instanceof ItemFrame){
            //gets entity location
            World world = entity.getLocation().getBlock().getWorld();
            double x = entity.getLocation().getX();
            double y = entity.getLocation().getY();
            double z = entity.getLocation().getZ();
            String blockname = world.getName()+ x +","+ y +","+ z;//identification key for sql
            //checks if the block is in the database
            if(checkSQLForBlock(blockname)){
                //if the block is in the database the item frame is removed from the database
                removeBlockFromSQL(blockname);
            }
        }
    }

    public void addBlockToSQL(String blockname,World world, double x,double y,double z)
    {
        try{
            //creates a table if the database does not already have one
            Statement create = plugin.getConnection().createStatement();
            create.executeUpdate("CREATE TABLE IF NOT EXISTS "+plugin.table+"(`BLOCKNAME` VARCHAR(900),`WORLD` VARCHAR(30),`X` DOUBLE(30,5),`Y` DOUBLE(30,5), `Z` DOUBLE(30,5))");
            //checks if the block being placed is already in the database
            //this check is to make sure that the block is not duplicated in the database
            if (!checkSQLForBlock(blockname)){
                //adds the block to the table if the block was not already in the table
                PreparedStatement insert = plugin.getConnection()
                        .prepareStatement("INSERT INTO "+plugin.table+"(BLOCKNAME,WORLD,X,Y,Z) VALUE (?,?,?,?,?)");
                //inputs all the values passed to the sub to the table
                insert.setString(1,blockname);
                insert.setString(2,world.getName());
                insert.setDouble(3,x);
                insert.setDouble(4,y);
                insert.setDouble(5,z);
                insert.executeUpdate();
            }
        }catch(Exception e){
            e.printStackTrace();

        }
    }


    public boolean checkSQLForBlock(String blockname)
    {
        try{
            //makes a statement to check the database for the identification key
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("SELECT * FROM "+plugin.table+" WHERE `BLOCKNAME`=?");
            statement.setString(1,blockname);
            ResultSet results = statement.executeQuery();
            //if the statement find a match to the blockname id key then it returns true
            if (results.next()){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void removeBlockFromSQL(String blockname)
    {
        try{
            //deletes the row that has the id key blockname to remove the block so it can't copy books
            PreparedStatement remove = plugin.getConnection()
                    .prepareStatement("DELETE FROM "+plugin.table + " WHERE `BLOCKNAME`= ?");
            remove.setString(1,blockname);
            remove.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }








}
