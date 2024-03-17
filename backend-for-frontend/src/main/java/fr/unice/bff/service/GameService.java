package fr.unice.bff.service;
import java.util.Collections;

import fr.unice.bff.controller.dto.GameMoveDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameService {
    private final Logger LOG = LoggerFactory.getLogger(GameService.class);

    public final CacheManager cacheManager;

    private Map<Integer, List<GameMoveDTO>> gameMoves = new HashMap<>();

    public GameService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void playMove(GameMoveDTO gameMoveDTO) {
        LOG.info("Received request to play move: service game");
        if (gameMoves.get(gameMoveDTO.getTableNumber()) == null) {
            gameMoves.put(gameMoveDTO.getTableNumber(), new ArrayList<>());
        }
        LOG.info(gameMoves.get(gameMoveDTO.getTableNumber()).toString());
        gameMoves.get(gameMoveDTO.getTableNumber()).add(gameMoveDTO);
    }


    public List<GameMoveDTO> getChanges(int tableNumber) {
        List<GameMoveDTO> res = gameMoves.get(tableNumber);
        if (res != null) {
            gameMoves.put(tableNumber, new ArrayList<>()); // Réinitialisez la liste après l'avoir récupérée
        }
        return res != null ? new ArrayList<>(res) : Collections.emptyList(); // Renvoie une copie pour éviter les modifications ultérieures
    }


}