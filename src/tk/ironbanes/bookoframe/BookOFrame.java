package tk.ironbanes.bookoframe;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BookOFrame extends JavaPlugin {
    private Connection connection;
    public String host, database, username, password, table,item_name,item_lore;
    public int port;



    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(new EventHandler(), this);
        this.getCommand("bookoframe").setExecutor(new CommandBOF());
        loadconfig();
        mysqlSetup();
        item_name = this.getConfig().getString("item-name");
        item_lore = this.getConfig().getString("item-lore");




    }
    public void loadconfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();

    }

    public void mysqlSetup(){
        host = this.getConfig().getString("host");
        port = this.getConfig().getInt("port");
        database = this.getConfig().getString("database");
        username = this.getConfig().getString("username");
        password = this.getConfig().getString("password");
        table = this.getConfig().getString("table");
        try{
            synchronized (this){
                if(getConnection() != null && !getConnection().isClosed()){
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://"+this.host+":"+ this.port+"/"
                        + this.database, this.username,this.password));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"===========================================================");
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"MYSQL CONNECTED");
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"===========================================================");
            }

        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }


    }
    public synchronized void closeConnection(){
        try{
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onDisable() {
        closeConnection();

    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
