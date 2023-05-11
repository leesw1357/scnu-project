package com.example.soleproject.api;

import com.example.soleproject.dto.PlayerForm;
import com.example.soleproject.entity.Player;
import com.example.soleproject.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
@Slf4j
@RestController // REST API 용 컨트롤러 ! 데이터를 반환

public class PlayerApiController {
    @Autowired
    private PlayerRepository playerRepository;
    // GET
    @GetMapping("/api/players")
    public List<Player> index(){
        return playerRepository.findAll();
    }

    @GetMapping("/api/player/{id}")
    public Player show(@PathVariable Long id){
        return playerRepository.findById(id).orElse(null);
    }
    // POST
    @PostMapping("/api/player/create")
    // @RequestBody = JSON데이터 받기
    public Player create(@RequestBody PlayerForm dto){
        Player player = dto.toEntity();
        return playerRepository.save(player);
    }
    // PATCH
    @PatchMapping("/api/player/{id}/update")
    // ResponseEntity를 사용하면 상태코드를 같이 보낼 수 있다.
    public ResponseEntity<Player> update(@RequestBody PlayerForm dto, @PathVariable Long id){
        Player player = dto.toEntity();
        Player target = playerRepository.findById(id).orElse(null);
        // 잘못된 요청 처리
        if(target == null || id != player.getId()){
             log.info("잘못된 요청입니다.");
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 업데이트
        target.patch(player);
        Player updated = playerRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
    // DELETE
    @DeleteMapping("/api/player/{id}/delete")
    public Player delete(@PathVariable Long id){
        Player player = playerRepository.findById(id).orElse(null);
       playerRepository.delete(player);
        return null;
    }
}
