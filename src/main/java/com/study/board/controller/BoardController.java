package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController { //깃

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8080/board/write
    public String boardWriteForm() {

        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception { //매개변수를 entity형식 그대로 받아줄 수 있음

        boardService.write(board, file);

        model.addAttribute("message","글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list" );

        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword) {
         Page<Board> list =null;

        if(searchKeyword ==null) {
             list = boardService.boardList(pageable);
        } else {
             list = boardService.boardSearchList(searchKeyword, pageable);
        }


        int nowPage = list.getPageable().getPageNumber() + 1; //페이지는 0부터 시작하기 떄문에 1을 더해서 넘겨줌
        int startPage = Math.max(nowPage - 4, 1); //매개 변수 둘 중에 더 큰 값을 반환함
        int endPage = Math.min(nowPage + 5, list.getTotalPages()); //토탈 페이지를 넘어버리면 토탈 페이지를 반환

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";
    }

    @GetMapping("/board/view") //localhost:8080/board/view?id=1 파라미터 겟방식
    public String boardView(Model model, Integer id) {

        model.addAttribute("board", boardService.boardView(id));
        return "boardView";
    }

    @GetMapping("/board/modify/{id}") //pv는 {id}가 인식이 돼서 인티저 형태의 아이디로 들어옴을 의미
    public String boardModify(@PathVariable("id") Integer id,
                              Model model) {

        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, MultipartFile file) throws Exception {
        Board boardTemp = boardService.boardView(id);//기존 객체를 불러온 다음 새로 작성한 객체의 내용을 덮어씌워서 저장하ㅁ면 업데이트제
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file);

        return "redirect:/board/list";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id) {
        boardService.boardDelete(id);

        return "redirect:/board/list"; //작성 완료
    }
}