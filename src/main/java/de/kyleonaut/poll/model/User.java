package de.kyleonaut.poll.model;

import lombok.Data;

import java.util.UUID;

/**
 * @author kyleonaut
 */
@Data
public class User {
    private UUID uuid;
    private String name;
}
