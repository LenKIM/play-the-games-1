package org.slipp.masil.games.application;

import lombok.AccessLevel;
import lombok.Getter;
import org.slipp.masil.games.domains.game.DifficultyLevel;
import org.slipp.masil.games.domains.highrow.HighLowJudge;
import org.slipp.masil.games.domains.highrow.HighLowPlayService;
import org.slipp.masil.games.domains.highrow.HighLowPlayingContextRepository;
import org.slipp.masil.games.domains.highrow.StartHighLowPlay;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HighLowApplicationService {

    @Getter(value = AccessLevel.PUBLIC)
    final HighLowPlayService highLowPlayService;

    public HighLowApplicationService(HighLowPlayingContextRepository contextRepository) {
        DifficultyLevel easy = DifficultyLevel.EASY;
        HighLowJudge highLowJudge = new HighLowJudge(easy);
        this.highLowPlayService = new HighLowPlayService(highLowJudge, contextRepository);
    }

    @Transactional
    public Long start(StartHighLowPlay command) {
        return getHighLowPlayService().start(command);
    }

    @Transactional
    public void exit() {
        StartHighLowPlay command = new StartHighLowPlay("Foo");
        getHighLowPlayService().start(command);
    }
}
