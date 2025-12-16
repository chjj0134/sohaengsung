const admin = require("firebase-admin");
const axios = require("axios");

// Firebase 초기화
admin.initializeApp({
    credential: admin.credential.cert(require("./firebase-admin-key.json")),
});

const db = admin.firestore();

// 설정값
const GOOGLE_API_KEY = "AIzaSyDQ-xH7X3Ni-MpssDOMWj5ycviAn6aQZRU";
const CENTER_LAT = 37.5459;   // 숙명여대
const CENTER_LNG = 126.9649;
const RADIUS = 2000;


// 카테고리 정의
const QUERIES = [
    { category: "cafe", type: "cafe" },
    { category: "bookstore", type: "book_store" },
    { category: "select_shop", type: "clothing_store" },
    { category: "select_shop", type: "shoe_store" },
    { category: "select_shop", type: "jewelry_store" },
    { category: "select_shop", type: "home_goods_store" },
    {
        category: "select_shop",
        type: "store",
        keyword: "편집샵 빈티지 쇼룸 의류",
    },
    { category: "gallery", type: "art_gallery" },
    { category: "gallery", type: "museum" },
];


// 제외 필터
const EXCLUDE_NAME_KEYWORDS = [
    "학교", "대학교", "초등", "중학교", "고등", "도서관",
    "백화점", "쇼핑몰",
    "이마트", "롯데", "현대", "신세계",
    "무신사", "ZARA", "H&M", "UNIQLO", "유니클로",
    "ABC마트", "올리브영", "아이파크",
];

const EXCLUDE_TYPES = [
    "school", "university", "library",
    "shopping_mall", "department_store"
];

function shouldExclude(place) {
    const name = place.name || "";
    const types = place.types || [];

    if (EXCLUDE_NAME_KEYWORDS.some(k => name.includes(k))) return true;
    if (types.some(t => EXCLUDE_TYPES.includes(t))) return true;

    return false;
}


// 해시태그 규칙
const TAG_RULES = {
    "레트로": [
        "레트로", "빈티지", "옛날", "옛날책", "헌책", "중고", "중고책", "고서",
        "추억", "감성적인", "아날로그", "클래식", "낡은", "오래된", "옛감성"
    ],

    "카공": [
        "카공", "공부", "작업", "노트북", "피씨", "PC", "과제", "리포트", "독서실",
        "콘센트", "충전", "와이파이", "wifi", "테이블", "좌석", "의자", "자리",
        "오래", "머무", "혼자", "혼카페", "조용히", "집중", "집중하기", "업무", "미팅"
    ],

    "조용한": [
        "조용", "조용한", "한적", "차분", "잔잔", "고요", "정적", "북적이지", "사람없",
        "시끄럽지", "소음", "분위기 좋", "편하게", "평온", "잔잔한", "정숙", "혼자오기"
    ],

    "데이트": [
        "데이트", "연인", "커플", "남친", "여친", "기념일", "분위기", "무드", "로맨틱",
        "사진", "포토", "인생샷", "예쁘", "감성", "데이트코스", "산책", "둘이", "같이"
    ],

    "독서/책": [
        "책", "서적", "도서", "독서", "읽기", "읽을", "베스트셀러", "신간", "신작",
        "중고책", "헌책", "교과서", "문제집", "참고서", "문학", "소설", "에세이",
        "인문", "철학", "역사", "만화", "잡지", "매거진", "서점", "책방", "도서관",
        "추천", "구경", "고르", "찾아", "서가", "책장", "책정리", "구매", "판매", "대여"
    ],

    "아늑한": [
        "아늑", "포근", "편안", "편안한", "따뜻", "따뜻한", "아담", "작고", "소박",
        "조명", "인테리어", "감성", "분위기", "인테리어 좋", "자리 편", "휴식", "힐링",
        "조용히", "정리되어", "깨끗", "쾌적", "은은", "잔잔"
    ],

    "트랜디": [
        "트렌디", "세련", "감각", "감각적", "모던", "깔끔", "미니멀", "미니멀한",
        "인테리어 예쁘", "감성적", "핫플", "핫플레이스", "요즘", "신상", "신상느낌",
        "브랜딩", "디자인", "컨셉", "컨셉있", "사진잘나", "무드", "힙"
    ],

    "힙한": [
        "힙", "힙한", "힙스터", "감성", "감성카페", "무드", "분위기", "바이브",
        "로컬", "로컬느낌", "숨은", "숨은맛집", "숨은명소", "취향", "취향저격",
        "개성", "개성있", "유니크", "독특", "특이", "간지", "멋있", "핫플"
    ],

    "전시": [
        "전시", "전시회", "작품", "관람", "관람하", "갤러리", "미술", "아트", "예술",
        "박물관", "큐레이션", "도슨트", "포토존", "체험", "전시장", "전시공간",
        "작가", "작품들", "그림", "사진전", "조각", "설치", "문화", "예매", "입장"
    ],
};


// 텍스트 정규화
function normalizeText(text = "") {
    return String(text)
        .toLowerCase()
        .replace(/\s+/g, " ")
        .replace(/[^\p{L}\p{N}\s]/gu, " ");
}


// 리뷰 기반 해시태그 추출
function extractHashtagsFromReviews(reviews = []) {
    const score = {};

    reviews.forEach(r => {
        const text = normalizeText(r.content || "");

        Object.entries(TAG_RULES).forEach(([tag, keywords]) => {
            keywords.forEach(keyword => {
                if (text.includes(normalizeText(keyword))) {
                    score[tag] = (score[tag] || 0) + 1;
                }
            });
        });
    });

    return Object.entries(score)
        .sort((a, b) => b[1] - a[1])
        .slice(0, 3)
        .map(([tag]) => tag);
}

// 기본 태그
const DEFAULT_TAGS = {
    cafe: ["카공", "아늑한"],
    bookstore: ["독서/책", "조용한"],
    select_shop: ["힙한", "트랜디"],
    gallery: ["전시", "조용한"],
};

// 사진 랜덤 선택
function finalizeTags(tags, category) {
    return tags.length > 0 ? tags : (DEFAULT_TAGS[category] || []);
}

function pickRandomPhotos(photos = [], min = 1, max = 3) {
    if (!photos.length) return [];

    const shuffled = [...photos].sort(() => 0.5 - Math.random());
    const count = Math.min(
        photos.length,
        Math.floor(Math.random() * (max - min + 1)) + min
    );

    return shuffled.slice(0, count);
}


// Google Places API
async function fetchNearbyPlaces({ type, keyword }) {
    const url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";

    const params = {
        location: `${CENTER_LAT},${CENTER_LNG}`,
        radius: RADIUS,
        type,
        language: "ko",
        key: GOOGLE_API_KEY,
    };

    if (keyword) params.keyword = keyword;

    const res = await axios.get(url, { params });
    return res.data;
}

async function fetchPlaceDetails(placeId) {
    const url = "https://maps.googleapis.com/maps/api/place/details/json";

    const params = {
        place_id: placeId,
        language: "ko",
        key: GOOGLE_API_KEY,
        fields: [
            "place_id",
            "name",
            "formatted_address",
            "geometry/location",
            "rating",
            "user_ratings_total",
            "photos",
            "opening_hours",
            "formatted_phone_number",
            "website",
            "reviews",
        ].join(","),
    };

    const res = await axios.get(url, { params });
    return res.data;
}

function buildPhotoUrl(photoRef) {
    return `https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&photo_reference=${photoRef}&key=${GOOGLE_API_KEY}`;
}


// Firestore 저장
async function savePlace(place, category) {
    
    const ref = db.collection("places").doc(place.place_id);

    const reviews =
        Array.isArray(place.reviews)
            ? place.reviews.slice(0, 5).map(r => ({
                author: r.author_name || "",
                rating: r.rating || 0,
                content: r.text || "",
            }))
            : [];

    const rawTags = extractHashtagsFromReviews(reviews);
    const hashtags = finalizeTags(rawTags, category);

    const selectedPhotos = pickRandomPhotos(place.photos, 1, 3);

    const photoUrls = selectedPhotos
        .map(p => p.photo_reference)
        .filter(Boolean)
        .map(buildPhotoUrl)

    await ref.set({
        placeId: place.place_id,
        name: place.name || "",
        address: place.formatted_address || "",
        latitude: place.geometry?.location?.lat ?? 0,
        longitude: place.geometry?.location?.lng ?? 0,
        category,
        rating: place.rating ?? 0,
        reviewCount: place.user_ratings_total ?? 0,
        hashtags,
        photoUrls,
        details: {
            phone: place.formatted_phone_number || null,
            website: place.website || null,
            openingHours: place.opening_hours?.weekday_text ?? [],
            reviews,
        },
    }, { merge: true });
}


// 실행
async function run() {
    console.log("▶ Place seeding START");

    const seen = new Set();

    for (const q of QUERIES) {
        const data = await fetchNearbyPlaces(q);
        if (data.status !== "OK") continue;

        const results = data.results.filter(p => !shouldExclude(p));

        for (const p of results) {
            if (!p.place_id || seen.has(p.place_id)) continue;

            const details = await fetchPlaceDetails(p.place_id);
            if (details.status !== "OK") continue;

            const d = details.result;
            if (!d.user_ratings_total || !d.reviews?.length) continue;

            seen.add(p.place_id);
            await savePlace(d, q.category);

            console.log(`✔ 저장됨: [${q.category}] ${d.name}`);
        }
    }

    console.log("✅ place seeding 완료");
    process.exit(0);
}

run();
