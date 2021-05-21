package org.slipp.masil.games.domains.ranking;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.slipp.masil.games.domains.ranking.RankingItem.NONE_RANK_ITEM;


@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_=@PersistenceConstructor)
public class Ranking {

    public static final Long INIT_VERSION = null;

    public static Ranking of(RankingId id, int sizeOfTop) {
        return new Ranking(id, sizeOfTop, initItems(sizeOfTop), INIT_VERSION);
    }

    private static ArrayList<RankingItem> initItems(int sizeOfTop) {
        ArrayList<RankingItem> items = new ArrayList<>();
        for (int i = 0; i < sizeOfTop; i++) {
            items.add(NONE_RANK_ITEM);
        }
        return items;
    }

    @Id
    private RankingId id;

    private int sizeOfTop;

    @Column("RANKING_ID")
    private List<RankingItem> items;

    @Version
    private Long version;

    public void refresh(RankingItem newInfo) {
        List<RankingItem> newRanks = new ArrayList<>(items);
        newRanks.add(newInfo);
        List<RankingItem> sorted = newRanks.stream().sorted().collect(Collectors.toList());

        items = sorted.subList(0, sizeOfTop);
    }

    public RankingItem top(int topN) {
        return items.get(arrayIndex(topN));
    }

    private int arrayIndex(int topN) {
        return topN - 1;
    }
}
