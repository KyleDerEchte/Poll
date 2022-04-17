package de.kyleonaut.poll.service;

import de.kyleonaut.poll.model.User;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author kyleonaut
 */
@RequiredArgsConstructor
public class UserService implements Listener {
    private final List<User> users;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final User user = new User();
        user.setName(event.getPlayer().getName());
        user.setUuid(event.getPlayer().getUniqueId());
        this.users.add(user);
    }


    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        this.users.stream()
                .filter(user -> user.getUuid().equals(event.getPlayer().getUniqueId()))
                .findFirst()
                .ifPresent(users::remove);
    }

    public User getUserByUUID(UUID uuid) {
        final Optional<User> optionalUser = this.users.stream()
                .filter(user -> user.getUuid().equals(uuid))
                .findFirst();
        return optionalUser.orElse(null);
    }

    public User getUserByName(String name) {
        final Optional<User> optionalUser = this.users.stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .findFirst();
        return optionalUser.orElse(null);
    }
}
