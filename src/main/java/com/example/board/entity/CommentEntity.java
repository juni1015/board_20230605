package com.example.board.entity;

import com.example.board.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column(length = 200, nullable = false)
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)  // 자식은 ManyToOne / FetchType.LAZY는 부모 데이터를 조회할 때 자식 데이터는 필요할 때 호출하여 가져옴, EAGER은 부모 데이터를 조회할 때 자식 데이터를 무조건 같이 호출하여 가져옴
    @JoinColumn(name = "board_id")  // 실제 DB에 생성되는 참조 컬럼의 이름
    private BoardEntity boardEntity;    // 반드시 부모 Entity 타입으로 정의해야 함

    public static CommentEntity toSaveEntity(BoardEntity boardEntity, CommentDTO commentDTO) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);
        return commentEntity;
    }
}
