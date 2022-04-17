package de.kyleonaut.poll.commands;

import de.kyleonaut.poll.model.Poll;
import de.kyleonaut.poll.service.PollService;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * @author kyleonaut
 */
@RequiredArgsConstructor
public class PollCommand implements CommandExecutor {
    private final PollService pollService;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length == 0) {
            player.sendMessage("§7[§6Abstimmung§7] §7Nutze /poll <Text> um eine Abstimmung zu starten!");
            return true;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(args).forEach(s -> stringBuilder.append(s).append(" "));
        player.sendMessage("§7[§6Abstimmung§7] §aDeine Abstimmung wurde gestartet!");
        final Poll poll = pollService.create(player.getUniqueId(), stringBuilder.toString());
        pollService.send(poll);
        return false;
    }
}
