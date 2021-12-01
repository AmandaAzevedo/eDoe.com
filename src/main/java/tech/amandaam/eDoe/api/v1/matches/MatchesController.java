package tech.amandaam.eDoe.api.v1.matches;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.amandaam.eDoe.api.v1.item.ItemDTO;


import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/matches")
public class MatchesController {
    @Autowired
    private MatchesService matchesService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ItemDTO> matches(@RequestBody MatchesDTO matchesDTO, @RequestHeader("Authorization") String token){
        return matchesService.matches(matchesDTO, token);
    }

}
