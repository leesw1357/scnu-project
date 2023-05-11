package com.example.soleproject.controller;

import com.example.soleproject.dto.PlayerForm;
import com.example.soleproject.dto.ScnuForm;
import com.example.soleproject.entity.Player;
import com.example.soleproject.entity.Scnu;
import com.example.soleproject.repository.PlayerRepository;
import com.example.soleproject.repository.ScnuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class PlayContorller {

    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 가져다가 자동연결 해준다.
    private PlayerRepository playerRepository;
    @Autowired
    private ScnuRepository scnuRepository;

    @GetMapping("/scnu/write")
    public String scnuNew(){
        return "/scnu/write";
    }

    @PostMapping("/scnu/create")
    public String scnuCreate(ScnuForm form){
        Scnu scnu = form.toEntity();
        log.info(form.toString());
        Scnu saved =scnuRepository.save(scnu);
        log.info(saved.toString());
        return "redirect:/scnu/";
    }

    @GetMapping("/scnu")
    public String scnuIndex(Model model){
        List<Scnu> scnuEntityList = scnuRepository.findAll();
        model.addAttribute("scnu",scnuEntityList);
        return "/scnu/album";
    }

    @GetMapping("/scnu/sidebar")
    public String scnusidebar(){
        return "/scnu/sidebar";
    }


    @GetMapping("/scnu/map")
    public String map1(){
        return "/scnu/map";
    }

    @GetMapping("/player/new")
    public String playerNew(){
        return "/player/new";
    }


    // new.mustache에서 보낸 파일을 받는 메소드
    // dto를 통해서 받아야함으로 dto 패키지를 만들어아햠
    @PostMapping("/player/create")
    public String playerCreate(PlayerForm form) {
        log.info(form.toString());
        // 1. DTO를 Entity로 변환
        Player player = form.toEntity();
       log.info(player.toString());
        // 2. Repository에게 Entity를 DB안에 저장하게 한다.
        Player saved = playerRepository.save(player);
        log.info(saved.toString());
        return "redirect:/player/" + saved.getId();
    }

    @GetMapping("scnu/{id}")
    public String scnuView(@PathVariable Long id,Model model){
        Scnu scnuEntity = scnuRepository.findById(id).orElse(null);
        model.addAttribute("scnu",scnuEntity);
        return "/scnu/view";

    }

    @GetMapping("/player/{id}")
    public String playershow(@PathVariable Long id, Model model){
        log.info("id =" + id);
        // 1: id로 데이터를 가져옴!
        Player playerEntity = playerRepository.findById(id).orElse(null);
        // 2: 가져온 데이터를 모델에 등록!
        model.addAttribute("player",playerEntity);
        // 3: 보여줄 페이지를 설졍
        return "player/show";
    }


    @GetMapping("/players")
    public String index(Model model) {
        // 1: 모든 player을 가져온다.
        List<Player> playerEntityList = playerRepository.findAll();
        // 2: 가져온 player 묶음을 뷰로 전달한다.
        model.addAttribute("playerList",playerEntityList);
        // 3: 뷰 페이지를 설정!
        return "player/index";
    }
    @GetMapping("/player/{id}/edit")
    public String edit(@PathVariable Long id,Model model){
        // 1. 데이터를 가져온다.
        Player playerEntity = playerRepository.findById(id).orElse(null);
        // 2. 모델에 데이터를 등록한다.
        model.addAttribute("player",playerEntity);
        //뷰 페이지를 가져온다.
        return "player/edit";
    }
    @PostMapping("/player/update")
    public String update(PlayerForm form){
        log.info(form.toString());
        // 1: DTO를 엔티티로 변환한다.
        Player playerEntity = form.toEntity();
        log.info(playerEntity.toString());

        // 2:엔티티를 DB에 저장한다.
        // 2-1 : DB에서 기존 데이터를 가져온다.
        Player target= playerRepository.findById(playerEntity.getId()).orElse(null);

        // 2-2: 기존 데이터의 값을 갱신한다.
        if(target != null) {
            playerRepository.save(playerEntity);
        }
        return "redirect:/player/"+ playerEntity.getId();
    }

    @GetMapping("/player/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 왔습니다.");
        // 1: 삭제 대상을 가져온다.
        Player target = playerRepository.findById(id).orElse(null);
        // 2: 대상을 삭제한다
        if(target != null){
            playerRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제가 완료되었습니다.");
        }
        // 3: 결과페이지를 보여준다.
        return "redirect:/players/";
    }
}
