// Swiper 초기화
var swiper = new Swiper('.swiper-container', {
  loop: true,  // 슬라이드가 끝나면 처음으로 돌아가도록 설정
  slidesPerView: 1, // 한 번에 보일 슬라이드 수
  spaceBetween: 10, // 슬라이드 간 간격
  autoplay: {
    delay: 3000,  // 3초마다 자동으로 슬라이드 넘어감
    disableOnInteraction: false,  // 사용자가 인터랙션을 해도 자동 슬라이드 계속
  },
  pagination: {
    el: '.swiper-pagination',  // 페이지네이션을 표시할 곳
    clickable: true,  // 페이지네이션 클릭 가능
  },
  navigation: {
    nextEl: '.swiper-button-next',  // '다음' 버튼
    prevEl: '.swiper-button-prev',  // '이전' 버튼
  },
  on: {
    init: function () {
      this.update(); // swiper 초기화 시 크기 조정
    },
    resize: function () {
      this.update(); // 리사이즈 시 크기 조정
    }
  }
});