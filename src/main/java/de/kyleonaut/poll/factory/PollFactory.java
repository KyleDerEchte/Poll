package de.kyleonaut.poll.factory;

import com.google.common.collect.Lists;
import de.kyleonaut.poll.model.Poll;
import de.kyleonaut.poll.model.User;

import java.util.UUID;

/**
 * @author kyleonaut
 */
public class PollFactory {
    private Poll poll;

    public PollFactory create() {
        final Poll poll = new Poll();
        poll.setAbstentions(Lists.newArrayList());
        poll.setApproves(Lists.newArrayList());
        poll.setRejects(Lists.newArrayList());
        poll.setPollID(UUID.randomUUID());
        this.poll = poll;
        return this;
    }

    public PollFactory setUser(User user) {
        this.poll.setPollCreator(user);
        return this;
    }

    public PollFactory setText(String text) {
        this.poll.setText(text);
        return this;
    }

    public Poll build() {
        return this.poll;
    }
}
