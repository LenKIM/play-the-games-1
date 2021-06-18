package org.slipp.masil.games.domains.highrow;


import org.slipp.masil.games.domains.Judge;

public class HighLowPlayService {

    private final Judge judge;
    private final HighLowPlayingContextFactory contextFactory;
    private final HighLowPlayingContextRepository contextRepository;

    public HighLowPlayService(Judge judge, HighLowPlayingContextRepository contextRepository) {
        this(judge, HighLowPlayingContextFactory.DEFAULT, contextRepository);
    }

    public HighLowPlayService(Judge judge, HighLowPlayingContextFactory contextFactory, HighLowPlayingContextRepository contextRepository) {
        this.judge = judge;
        this.contextFactory = contextFactory;
        this.contextRepository = contextRepository;
    }

    public Long start(StartHighLowPlay command) {
        HighLowPlayingContext context = contextFactory.create(command);
        context.start();
        HighLowPlayingContext save = contextRepository.save(context);
        return save.getId();
    }

    public void stop(StopHighLowPlay command) {
        HighLowPlayingContext context = contextRepository.findById(command.getContextId());
        context.stop();
        contextRepository.save(context);
    }

    public HighLowPlayingResult play(GuessHighLowNumber command) {
        HighLowPlayingContext context = contextRepository.findById(command.getContextId());
        context.tryPlay();
        HighLowJudgement judgement = this.judge.judge(command.getGuessNumber());
        if (judgement == HighLowJudgement.MATCH) {
            context.match();
            contextRepository.save(context);
        }
        return new HighLowPlayingResult(command.getContextId(), judgement);
    }
}
