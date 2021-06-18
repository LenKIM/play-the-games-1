package org.slipp.masil.games.domains.highrow;

import lombok.Value;
import org.slipp.masil.games.domains.game.GameId;

import java.util.concurrent.atomic.AtomicLong;

@Value
public class StartHighLowPlay {
    String username;
    AtomicLong atomicLong = new AtomicLong();

    public GameId getGameId() {
        return GameId.of(atomicLong.getAndIncrement());
    }

    public String getUsername() {
        return username;
    }
}
