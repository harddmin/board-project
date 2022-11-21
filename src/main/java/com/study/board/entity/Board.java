package com.study.board.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity //jpa에서 이 어노테이션은 이 클래스가 디비에 있는 테이블을 의미함
@Data
public class Board {

    @Id//primary key 의미
    @GeneratedValue(strategy = GenerationType.IDENTITY) //아이덴티티가 마리아 db용
    private Integer id;
    private String title;
    private String content;
    private String filename;
    private String filepath;

}
