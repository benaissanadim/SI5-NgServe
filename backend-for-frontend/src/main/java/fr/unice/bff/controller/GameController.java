package fr.unice.bff.controller;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import fr.unice.bff.service.GameService;
import java.time.Duration;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import fr.unice.bff.controller.dto.GameMoveDTO;
import org.springframework.web.bind.annotation.*;
@RestController
public class GameController {
    private final Logger LOG = LoggerFactory.getLogger(GameController.class);
    @Autowired
    private GameService gameService;
    @GetMapping(value = "/changes/{tableNumber}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<List<GameMoveDTO>>> getChangesSSE(@PathVariable int tableNumber) {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<List<GameMoveDTO>>builder()
                        .id(String.valueOf(sequence))
                        .data(gameService.getChanges(tableNumber))
                        .build());
    }
    @PostMapping("/paint")
    public void playMove(@RequestBody GameMoveDTO gameMoveDTO) {
        LOG.info("Received request to play move: " );
        gameService.playMove(gameMoveDTO);
    }

}