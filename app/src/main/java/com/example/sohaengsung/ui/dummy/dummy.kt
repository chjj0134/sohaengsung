package com.example.sohaengsung.ui.dummy

import com.example.sohaengsung.data.model.Hashtag
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.data.model.PlaceDetail
import com.example.sohaengsung.data.model.Review

val HashtagListExample01 = listOf(
    Hashtag(
        tagId = "h001",
        name = "카공",
        useCount = 125
    ),
    Hashtag(
        tagId = "h002",
        name = "따뜻한",
        useCount = 98
    ),
    Hashtag(
        tagId = "h003",
        name = "노트북",
        useCount = 75
    ),
    Hashtag(
        tagId = "h004",
        name = "콘센트",
        useCount = 52
    ),
    Hashtag(
        tagId = "h005",
        name = "레트로",
        useCount = 125
    ),
    Hashtag(
        tagId = "h007",
        name = "주차장",
        useCount = 75
    ),
)

val HashtagListExample02 = listOf(
    Hashtag(
        tagId = "h005",
        name = "레트로",
        useCount = 125
    ),
    Hashtag(
        tagId = "h006",
        name = "일본풍",
        useCount = 98
    ),
    Hashtag(
        tagId = "h007",
        name = "주차장",
        useCount = 75
    ),
    Hashtag(
        tagId = "h008",
        name = "독서",
        useCount = 52
    ),
    Hashtag(
        tagId = "h002",
        name = "따뜻한",
        useCount = 98
    ),
    Hashtag(
        tagId = "h001",
        name = "카공",
        useCount = 125
    ),
)


val placeExample = listOf(
    Place(placeId = "C001", name = "올웨이즈어거스트 제작소", address = "서울 마포구 연남로 71 1층", latitude = 37.5051, longitude = 127.0507, hashtags = listOf("카공", "노트북", "콘센트"), rating = 4.5, reviewCount = 53, details = PlaceDetail(wifi = true, parking = true, kidsZone = false, signatureMenu = "흑임자 크림 라떼")),
    Place(placeId = "R005", name = "너드커피", address = "서울 용산구 청파로27길 제1호 내제1층호", latitude = 37.5145, longitude = 127.1050, hashtags = listOf("시즌음료", "테이크아웃전문"), rating = 4.8, reviewCount = 132, details = PlaceDetail(wifi = false, parking = false, kidsZone = false, signatureMenu = "말차밀크티")),
    Place(placeId = "M012", name = "책방죄책감", address = "서울 용산구 청파로47길 8 2층", latitude = 37.5760, longitude = 126.9800, hashtags = listOf("다양한책", "아늑한분위기", "북카페"), rating = 4.2, reviewCount = 28, details = PlaceDetail(wifi = true, parking = false, kidsZone = false, signatureMenu = null))
)

val reviewExample = listOf(
    Review(reviewId = "R101", userId = "카공탐험가", placeId = "C001", rating = 3.0, content = "조용한 분위기의 카페예요. 노트북 사용도 가능하고 눈치 보지 않고 타이핑할 수 있어 과제 할 때 종종 들르고 있어요.", tags = listOf("조용한 분위기", "노트북", "카공")),
    Review(reviewId = "R102", userId = "아이러브커피", placeId = "C001", rating = 3.0, content = "테이크아웃 하면 1500원 할인받을 수 있어요. 카페의 시그니처 음료인 크림라떼 맛집입니다. 사람이 많아 주말엔 웨이팅이 길게 늘어서기도 하니 방문 시 참고하세요!", tags = listOf("시그니처음료", "커피맛집", "트렌디한")),
    Review(reviewId = "R103", userId = "청파동원주민", placeId = "A023", rating = 5.0, content = "조용하게 책 읽고 싶거나 책 선물할 때 들리는 저의 최애 책방이에요. 사장님도 친절하시고, 선물 포장도 예쁘게 해주세요. 방문할때마다 항상 기분이 좋아지는 곳이에요.", tags = listOf("선물포장", "아늑한분위기", "북카페"))
)
