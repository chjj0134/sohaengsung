package com.example.sohaengsung.ui.dummy

import com.example.sohaengsung.data.model.Hashtag
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.data.model.PlaceDetail

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

val placeList = listOf(
    Place("01", "카페 A", "주소 A", hashtags = listOf("조용함","따뜻한"), details = PlaceDetail(true, false, true, "아메리카노")),
    Place("02", "식당 B", "주소 B", hashtags = listOf("따뜻한"), details = PlaceDetail(true, true, false, "크림 라떼"))
)