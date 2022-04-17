package de.kyleonaut.poll.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * @author kyleonaut
 */
@Data
public class Poll {
    private UUID pollID;
    private User pollCreator;
    private String text;
    private List<User> approves;
    private List<User> rejects;
    private List<User> abstentions;
}
