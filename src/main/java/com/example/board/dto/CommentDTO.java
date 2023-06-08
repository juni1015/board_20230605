package com.example.board.dto;

import com.example.board.entity.CommentEntity;
import com.example.board.util.UtilClass;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private String createdAt;
    private String updateAt;

    public static CommentDTO toDTO(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setBoardId(commentEntity.getBoardEntity().getId());  // CommentEntity에는 boardId가 없고 boardEntity에 있기 때문에 BoardEntity의 getId로 boardId 값을 가져와야 함
        commentDTO.setCreatedAt(UtilClass.dateFormat(commentEntity.getCreatedAt()));
        commentDTO.setUpdateAt(UtilClass.dateFormat(commentEntity.getUpdatedAt()));
        return commentDTO;
    }
}
