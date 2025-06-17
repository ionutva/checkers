package com.example.checkers.controller;

import com.example.checkers.model.Game;
import com.example.checkers.model.GameStateDto;
import com.example.checkers.model.Move;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkers")
@CrossOrigin(origins = "*")
public class CheckersController {

    private Game game = new Game();

    @GetMapping("/state")
    public ResponseEntity<GameStateDto> getGameState() {
        return ResponseEntity.ok(new GameStateDto(game));
    }

    @PostMapping("/move")
    public ResponseEntity<String> makeMove(@RequestBody Move move) {
        String result = game.makeMove(move);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetGame() {
        game = new Game();
        return ResponseEntity.ok("Game reset.");
    }
}