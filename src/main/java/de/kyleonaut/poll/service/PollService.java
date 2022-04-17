package de.kyleonaut.poll.service;

import de.kyleonaut.clickablemessage.api.ClickableMessageApi;
import de.kyleonaut.poll.PollPlugin;
import de.kyleonaut.poll.factory.PollFactory;
import de.kyleonaut.poll.model.Poll;
import de.kyleonaut.poll.model.User;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author kyleonaut
 */
@RequiredArgsConstructor
public class PollService {
    private final UserService userService;

    public Poll create(UUID uuid, String text) {
        return new PollFactory().create().setUser(this.userService.getUserByUUID(uuid)).setText(text).build();
    }

    public void send(Poll poll) {
        final List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player player : players) {
            player.sendMessage("§7[§6Abstimmung§7]");
            player.sendMessage(" ");
            player.sendMessage("§6Abstimmungstext: " + poll.getText());
            player.sendMessage("§6Abstimmungsersteller: " + poll.getPollCreator().getName());

            final TextComponent approve = ClickableMessageApi.getAPI().getText(new TextComponent("§7[§a\uD83D\uDC4D§7] "), p -> { //👍
                final User user = this.userService.getUserByUUID(p.getUniqueId());
                if (poll.getApproves().contains(user)) {
                    p.sendMessage("§7[§6Abstimmung§7] §cDu hast bereits dafür gestimmt.");
                    return;
                }
                poll.getAbstentions().remove(user);
                poll.getRejects().remove(user);
                poll.getApproves().add(user);
                p.sendMessage("§7[§6Abstimmung§7] §aDu hast dafür gestimmt.");
            });

            final TextComponent reject = ClickableMessageApi.getAPI().getText(new TextComponent("§7[§c\uD83D\uDC4E§7] "), p -> {
                final User user = this.userService.getUserByUUID(p.getUniqueId());
                if (poll.getRejects().contains(user)) {
                    p.sendMessage("§7[§6Abstimmung§7] §cDu hast bereits dagegen gestimmt.");
                    return;
                }
                poll.getAbstentions().remove(user);
                poll.getApproves().remove(user);
                poll.getRejects().add(user);
                p.sendMessage("§7[§6Abstimmung§7] §aDu hast dagegen gestimmt.");
            });

            final TextComponent abstention = ClickableMessageApi.getAPI().getText(new TextComponent("§7[=]"), p -> {
                final User user = this.userService.getUserByUUID(p.getUniqueId());
                if (poll.getAbstentions().contains(user)) {
                    p.sendMessage("§7[§6Abstimmung§7] §cDu enthälst dich bereits.");
                    return;
                }
                poll.getAbstentions().add(user);
                poll.getApproves().remove(user);
                poll.getRejects().remove(user);
                p.sendMessage("§7[§6Abstimmung§7] §aDu enthälst dich.");
            });
            player.spigot().sendMessage(approve, reject, abstention);
            player.sendMessage(" ");
            player.sendMessage("§7Die Abstimmung endet in §630 Sekunden§7.");
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(PollPlugin.getPlugin(PollPlugin.class), () -> {
            final List<Player> currentPlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
            for (Player currentPlayer : currentPlayers) {
                currentPlayer.sendMessage("§7[§6Abstimmung§7]");
                currentPlayer.sendMessage(" ");
                currentPlayer.sendMessage("§7Die Abstimmung von" + poll.getPollCreator().getName() + " endet und das Ergebnis ist: ");
                currentPlayer.sendMessage("§7" + poll.getApproves().size() + " Spieler haben dafür gestimmt.");
                currentPlayer.sendMessage("§7" + poll.getRejects().size() + " Spieler haben dagegen gestimmt.");
                currentPlayer.sendMessage("§7" + poll.getAbstentions().size() + " Spieler haben sich enthalten.");
            }
        }, 20 * 30);
    }
}
