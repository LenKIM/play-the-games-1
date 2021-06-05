package org.slipp.masil.games.domains.highrow;


public class HighLowPlayService {

    private HighLowJudge judge;
    private final HighLowPlayingContextFactory contextFactory;
    private final HighLowPlayingContextRepository contextRepository;

    public HighLowPlayService(HighLowJudge judge, HighLowPlayingContextRepository contextRepository) {
        this(judge, HighLowPlayingContextFactory.DEFAULT, contextRepository);
    }

    public HighLowPlayService(HighLowJudge judge, HighLowPlayingContextFactory contextFactory, HighLowPlayingContextRepository contextRepository) {
        this.judge = judge;
        this.contextFactory = contextFactory;
        this.contextRepository = contextRepository;
    }

    public Long start(HighLowPlayStart highLowStart) {
        HighLowPlayingContext context = contextFactory.create(highLowStart);
        context.start();
        contextRepository.save(context);
        return context.getId();
    }

    public void stop(HighLowPlayStop highLowPlayStop) {
        HighLowPlayingContext context = contextRepository.findById(highLowPlayStop.getContextId());
        context.stop();
        contextRepository.save(context);
    }

    public HighLowPlayingResult play(HighLowNumberGuess guess) {
        HighLowPlayingContext context = contextRepository.findById(guess.getContextId());
        HighLowJudgement judgement = this.judge.judge(guess.getGuessNumber());
        if (judgement == HighLowJudgement.MATCH) {
            context.match();
            contextRepository.save(context);
        }
        return new HighLowPlayingResult(guess.getContextId(), judgement);
    }

}
