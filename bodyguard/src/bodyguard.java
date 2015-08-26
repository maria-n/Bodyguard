import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created by Maria on 8/5/2015.
 */
public class bodyguard extends JavaPlugin
{
    public static Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onLoad()
    {
        log.info("[BodyguardPlugin] Loading...");
    }

    @Override
    public void onEnable()
    {
        log.info("[BodyguardPlugin] Starting up....");

    }

    @Override
    public void onDisable()
    {
        log.info("[BodyguardPlugin] Shutting Down....");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if(!(sender instanceof Player))
        {
            sender.sendMessage("You can't use that command!");
        }  else if (cmd.getName().equalsIgnoreCase("bodyguard"))
        {
            Player guarded = getServer().getPlayer(args[0]);
            if(args.length == 0)
            {
                sender.sendMessage("Please specify a Player to be guarded.");
            }else if(guarded == null)
            {
                    sender.sendMessage("Couldn't find that Player. Sorry..");
            }else
            {
                    setGuard(guarded);
            }
        }
        return true;
    }

    public void setGuard(Player guarded)
    {
        Location loc = guarded.getLocation();
        IronGolem guard = guarded.getWorld().spawn(loc, IronGolem.class);
        guard.isLeashed();
        guard.setLeashHolder(guarded);
        guard.isPlayerCreated();
        EntityDamageEvent target = guarded.getLastDamageCause();
        if(target instanceof LivingEntity)
        {
            guard.setTarget((LivingEntity) target);
        }
        if(guard.getLastDamageCause() == guarded)
        {
            guard.setLastDamage(00.00);
            guard.setLastDamageCause(target);
        }
    }
}
