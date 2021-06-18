package org.slipp.masil.games.web;

import org.slipp.masil.games.application.HighLowApplicationService;
import org.slipp.masil.games.domains.highrow.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StartController {

    private final HighLowApplicationService applicationService;

    public StartController(HighLowApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping(value = "/input/name")
    public ModelAndView inputName(@RequestParam(value = "name") String name){
        // service passed name
        Long contextId = applicationService.start(new StartHighLowPlay(name));
        ModelAndView mv = new ModelAndView();
        mv.setViewName("start");
        mv.addObject("name", name);
        mv.addObject("contextId", contextId); // for test
        return mv;
    }

    @PostMapping(value = "/guess")
    public ModelAndView start(
            @RequestParam(value = "number") String number,
            @RequestParam(value = "contextId") Long contextId
    ){
        // target 1;
        long guess = Long.parseLong(number);
        GuessHighLowNumber guessNumber = GuessHighLowNumber.of(contextId, guess);

        HighLowPlayingResult play = applicationService.getHighLowPlayService().play(guessNumber);
        HighLowJudgement judgement = play.getJudgement();

        String result = "";
        if (HighLowJudgement.LOW.equals(judgement)){
            result ="down";
        } else if (HighLowJudgement.HIGH.equals(judgement)){
            result = "up";
        } else if(HighLowJudgement.MATCH.equals(judgement)){
            result = "match";
        }
        ModelAndView mv = new ModelAndView();
        if (result.equals("match")){
            mv.setViewName("ranking");
            return mv;
        }

        mv.setViewName("start");
        mv.addObject("result", result);
        return mv;
    }
}
