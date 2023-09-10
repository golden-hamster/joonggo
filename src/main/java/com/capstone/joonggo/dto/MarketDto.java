package com.capstone.joonggo.dto;

import com.capstone.joonggo.domain.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MarketDto {

    public Member member;

    public LocalDateTime createdTime;
}
