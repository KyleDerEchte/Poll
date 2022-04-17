package de.kyleonaut.poll;

import com.google.common.collect.Lists;
import de.kyleonaut.poll.commands.PollCommand;
import de.kyleonaut.poll.service.PollService;
import de.kyleonaut.poll.service.UserService;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author kyleonaut
 */
@Getter
public class PollPlugin extends JavaPlugin {
    private UserService userService;
    private PollService pollService;

    @Override
    public void onEnable() {
        this.userService = new UserService(Lists.newArrayList());
        this.pollService = new PollService(this.userService);

        getCommand("poll").setExecutor(new PollCommand(this.pollService));
        Bukkit.getPluginManager().registerEvents(this.userService, this);
    }

}
