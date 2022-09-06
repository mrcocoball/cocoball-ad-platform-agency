package com.agencyplatformclonecoding.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("비즈니스 로직 - 페이지네이션")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = PaginationService.class) // 테스트의 무게가 줄어듬
class PaginationServiceTest {

    private final PaginationService sut;

    public PaginationServiceTest(@Autowired PaginationService paginationService) {
           this.sut = paginationService;
    }
    @DisplayName("현재 페이지 번호와 총 페이지 수를 주면, 페이지 바 리스트를 만들어줌")
    @MethodSource // 메소드에 소스를 주는 방식
    @ParameterizedTest(name = "[{index}] 현재 페이지 : {0}, 총 페이지 : {1} => {2}") // 파라미터 테스트
    void givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnsPaginationBarNumbers(int currentPageNumber, int totalPages, List<Integer> expected) {
        // Given

        // When
        List<Integer> actual = sut.getPaginationBarNumbers(currentPageNumber, totalPages);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

       //@MethodSource를 클릭하여 생성
       static Stream<Arguments> givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnsPaginationBarNumbers() {

           return Stream.of(
                   // arguments = Arguments.arguments
                   arguments(0, 13, List.of(0,1,2,3,4)), // pagenumber, page의 index는 0부터 시작됨
                   arguments(1, 13, List.of(0,1,2,3,4)),
                   arguments(2, 13, List.of(0,1,2,3,4)),
                   arguments(3, 13, List.of(1,2,3,4,5)),
                   arguments(4, 13, List.of(2,3,4,5,6)),
                   arguments(5, 13, List.of(3,4,5,6,7)),
                   arguments(6, 13, List.of(4,5,6,7,8)),
                   arguments(10, 13, List.of(8,9,10,11,12)),
                   arguments(11, 13, List.of(9,10,11,12)),
                   arguments(12, 13, List.of(10,11,12)) // 13페이지까지이므로 index 기준은 12가 끝
                   );
    }
    @DisplayName("현재 설정되어 있는 페이지네이션 바의 길이를 알려줌")
    @Test
    void givenNothing_whenCalling_thenReturnsCurrentBarLength() {
        // Given

        // When
        int barLength = sut.currentBarLength();

        // Then
        assertThat(barLength).isEqualTo(5); // 상수 처리한 바 길이 5를 명시적으로 알려주기 위해 해당 테스트 작성

    }

}