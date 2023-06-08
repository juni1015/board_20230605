package com.example.board;

import com.example.board.dto.BoardDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.repository.BoardRepository;
import com.example.board.service.BoardService;
import com.example.board.util.UtilClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardTest {
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;

    @Test
    @Transactional
    @DisplayName("참조관계 확인")
    public void test1() {
        BoardEntity boardEntity = boardRepository.findById(5L).get();
        // boardEntity로 첨부된 파일의 이름 조회하기(부모엔티티에서 자식엔티티를 조회하는 경우 Transactional 필요)
        System.out.println("첨부파일이름" + boardEntity.getBoardFileEntityList().get(0).getStoredFileName());
    }

    @Test
    @Transactional
    @DisplayName("엔티티 클래스 ToString")
    public void entityToString() {  // 엔티티에는 ToString으로 찍을 수 없음 / 무한궤도에 빠져서 멈춤
        BoardEntity boardEntity = boardRepository.findById(1L).get();
        System.out.println("boardEntity = " + boardEntity);
    }

    @Test
    @Transactional
    @DisplayName("findAll 할 때 정렬해서 가져오기")
    public void findAllOrderBy() {
        List<BoardEntity> boardEntityList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));    // id를 기준으로 내림차순 정렬하여 가져옴
        boardEntityList.forEach(boardEntity -> {
            System.out.println(BoardDTO.toDTO(boardEntity));
        });
    }

    private BoardDTO newBoard(int i) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardTitle("title" + i);
        boardDTO.setBoardWriter("writer" + i);
        boardDTO.setBoardContents("contents" + i);
        boardDTO.setBoardPass("pass" + i);
        return boardDTO;
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("DB에 데이터 붓기")
    public void saveList() {
        IntStream.rangeClosed(1, 50).forEach(i -> {
//            try {
//                boardService.save(newBoard(i));
                boardRepository.save(BoardEntity.toSaveEntity(newBoard(i)));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        });
    }

    @Test
    @Transactional
    @DisplayName("페이징 객체 메서드 확인")
    public void pagingMethod() {
        int page = 0;   // 요청한 페이지 번호   /
        int pageLimit = 3;  // 한 페이지당 보여줄 글 갯수
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        // Page 객체가 제공해주는 메서드 확인
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫페이지인지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막페이지인지 여부
        // Page<BoardEntity> -> Page<BoardDTO>
        Page<BoardDTO> boardList = boardEntities.map(boardEntity ->
            BoardDTO.builder()
                    .id(boardEntity.getId())
                    .boardTitle(boardEntity.getBoardTitle())
                    .boardWriter(boardEntity.getBoardWriter())
                    .createdAt(UtilClass.dateFormat(boardEntity.getCreatedAt()))
                    .boardHits(boardEntity.getBoardHits())
                    .build()
        );
        // Page 객체가 제공해주는 메서드 확인
        System.out.println("boardList.getContent() = " + boardList.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardList.getTotalElements() = " + boardList.getTotalElements()); // 전체 글갯수
        System.out.println("boardList.getNumber() = " + boardList.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardList.getTotalPages() = " + boardList.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardList.getSize() = " + boardList.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardList.hasPrevious() = " + boardList.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardList.isFirst() = " + boardList.isFirst()); // 첫페이지인지 여부
        System.out.println("boardList.isLast() = " + boardList.isLast()); // 마지막페이지인지 여부
    }
}
