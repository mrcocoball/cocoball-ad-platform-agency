package com.agencyplatformclonecoding.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {
    private static final int BAR_LENGTH = 5;

    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {
        int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);
        // 기본 골자는 현재 페이지 - (길이 / 2) 겠지만 0보다 작으면 0 반환
        int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);
        // startNumber + 길이가 totalPages를 넘지 않게

        return IntStream.range(startNumber, endNumber).boxed().toList();
    }

    public int currentBarLength() {
        return BAR_LENGTH;
    }

}
