package com.example.sohaengsung.ui.features.event.components

import com.example.sohaengsung.data.model.Event

// 최근 업데이트된 행사 더미 데이터
val recentUpdatedEvents = listOf(
    Event(
        eventId = "recent_1",
        title = "인상파, 찬란한 순간들: 모네, 르누아르, 반 고흐 그리고 세잔",
        tags = listOf("전시", "예술", "문화"),
        seasonInfo = "2025.12.19-2026.5.31",
        imageUrl = "https://nowonarts.kr/attaches/file/editor/x1hHwywdubvY0MqTaYw06wh0afG45UICpuqBUhDh.jpg",
        externalLink = "https://nowonarts.kr/channels/exhibition/programs/946",
        countdown = "D-3"
    ),
    Event(
        eventId = "recent_2",
        title = "서울 국제도서전",
        tags = listOf("책", "박람회", "도서"),
        seasonInfo = "2026.06.24-2026.06.28",
        imageUrl = "https://sibf.or.kr/theme/sibf/include/img/main_visual.jpg", // SVG 대신 JPG 사용 시도, 없으면 placeholder 표시됨
        externalLink = "https://sibf.or.kr/?utm_source=chatgpt.com",
        countdown = "D-196"
    ),
    Event(
        eventId = "recent_3",
        title = "인상주의에서 초기 모더니즘까지",
        tags = listOf("예술", "전시", "박물관"),
        seasonInfo = "2025.11.14-2026.03.14",
        imageUrl = "https://og-data.s3.amazonaws.com/media/exhibitions/image/14588/ei_14588.jpg",
        externalLink = "https://www.museum.go.kr/MUSEUM/contents/M0202010000.do?menuId=current&schM=view&act=current&exhiSpThemId=3126913&listType=list",
        countdown = "진행 중"
    )
)

// 겨울 감성에 딱 맞는 행사 더미 데이터
val winterEvents = listOf(
    Event(
        eventId = "winter_1",
        title = "2025 광화문 마켓",
        tags = listOf("크리스마스", "마켓", "가족"),
        seasonInfo = "2025.12.12~12.31",
        imageUrl = "https://www.stolantern.com/img/mm02_01_01.jpg",
        externalLink = "https://www.stolantern.com/bbs/page.php?hid=m02_01",
        countdown = "진행 중"
    ),
    Event(
        eventId = "winter_2",
        title = "크리스마스 빌리지 부산 2025",
        tags = listOf("크리스마스", "가족", "마켓"),
        seasonInfo = "2025.11.27-2025.12.25",
        imageUrl = "https://oopy.lazyrockets.com/api/v2/notion/image?src=attachment%3A326128e6-3f42-43ff-ade1-66c57396418c%3Aimage.png&blockId=2b7085a2-3ce2-800c-a316-e8d19a2ec86b",
        externalLink = "https://www.marketchango.com/",
        countdown = "진행 중"
    ),
    Event(
        eventId = "winter_3",
        title = "2025 겨울, 청계천의 빛",
        tags = listOf("축제", "가족", "야외"),
        seasonInfo = "2025.12.12~12.31",
        imageUrl = "https://www.seoulcl.kr/wysiwyg/PEG/se2_17641336876801.jpg", // HTTP를 HTTPS로 변경
        externalLink = "http://www.seoulcl.kr/main/page.html?pid=1",
        countdown = "진행 중"
    )
)

