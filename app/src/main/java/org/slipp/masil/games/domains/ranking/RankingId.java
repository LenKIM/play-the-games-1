package org.slipp.masil.games.domains.ranking;

import lombok.Value;

@Value(staticConstructor = "of")
public class RankingId {
    GameRef gameRef;
}