package org.slipp.masil.games.domains.ranking;


import java.util.List;

public interface RankingRepository {
    Ranking findById(RankingId id);

    Ranking save(Ranking ranking);

    List<Ranking> findAll();

}
