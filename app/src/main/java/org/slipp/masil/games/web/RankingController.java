package org.slipp.masil.games.web;

import org.slipp.masil.games.domains.ranking.Ranking;
import org.slipp.masil.games.domains.ranking.RankingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RankingController {

    private final RankingRepository repository;

    public RankingController(RankingRepository repository) {
        this.repository = repository;
    }


    @GetMapping(value = "ranking")
    public String getRanking(){
        List<Ranking> all = repository.findAll();
        System.out.println(all.size());
        return "ranking";
    }
}
