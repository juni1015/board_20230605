package com.example.board.controller;

import com.example.board.dto.CommentDTO;
import com.example.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody CommentDTO commentDTO) {
        try {
            commentService.save(commentDTO);
            List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
            System.out.println("commentDTOList = " + commentDTOList);
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
