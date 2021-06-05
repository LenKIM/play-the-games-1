package org.slipp.masil.games.domains.highrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

//TODO start command 가 널 일때
//TODO stop 시 context 를 찾지 못했을때
//TODO 이미 stop 된 것을 stop 하려 할경우
// TODO contextId 를 아무나 넘길수 있다... 개똥이가 플레이하고 길동이 가 개똥이 컨택스트 아이디로 하면 어까지?
@ExtendWith(MockitoExtension.class)
class HighLowPlayServiceStartTest {

    public static final long ANY_CTX_ID = 1L;

    @Mock
    HighLowPlayingContextRepository repository;

    @Mock
    HighLowPlayingContext context;

    @Mock
    HighLowJudge judge;

    @Mock
    HighLowPlayingContextFactory contextFactory;

    HighLowPlayService sut;

    @BeforeEach
    void setUp() {
        sut = new HighLowPlayService(judge, contextFactory, repository);
    }

    HighLowPlayStart command = new HighLowPlayStart("Foo");

    @Test
    void start() {
        given(contextFactory.create(command)).willReturn(context);
        sut.start(command);

        verify(context).start();
        verify(repository).save(context);
    }

    @Test
    void stop() {
        given(repository.findById(1L)).willReturn(context);

        HighLowPlayStop command = new HighLowPlayStop(ANY_CTX_ID);
        sut.stop(command);

        InOrder inOrder = inOrder(context, repository);
        inOrder.verify(repository).findById(command.getContextId());
        inOrder.verify(context).stop();
        inOrder.verify(repository).save(context);
    }
}
