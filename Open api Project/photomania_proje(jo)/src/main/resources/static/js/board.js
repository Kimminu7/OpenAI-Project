// 게시글 리스트를 저장할 배열
let posts = [];

// 게시글 작성 폼 이벤트 처리
document.getElementById("createPostForm").addEventListener("submit", (e) => {
  e.preventDefault();

  // 입력된 데이터 가져오기
  const title = document.getElementById("postTitle").value;
  const author = document.getElementById("postAuthor").value;
  const content = document.getElementById("postContent").value;

  // 새로운 게시글 객체 생성
  const newPost = {
    id: posts.length + 1,   // 게시글 ID는 현재 배열 길이에 1을 더한 값
    title: title,
    author: author,
    date: new Date().toISOString().split('T')[0],  // 오늘 날짜 (YYYY-MM-DD)
    views: 0,   // 초기 조회수
    likes: 0    // 초기 추천수
  };

  // 배열에 새 게시글 추가
  posts.push(newPost);

  // 게시글 렌더링
  renderPosts();

  // 폼 초기화
  document.getElementById("createPostForm").reset();
});

// 게시글 렌더링 함수
function renderPosts(page = 1, searchType = "all", query = "") {
  const postsPerPage = 10;
  const start = (page - 1) * postsPerPage;
  const end = start + postsPerPage;

  // 검색 기능
  const filteredPosts = posts.filter(post => {
    if (searchType === "title") return post.title.includes(query);
    if (searchType === "author") return post.author.includes(query);
    return true;
  });

  const paginatedPosts = filteredPosts.slice(start, end);

  const boardContent = document.getElementById("boardContent");
  boardContent.innerHTML = "";

  paginatedPosts.forEach(post => {
    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${post.id}</td>
      <td>${post.title}</td>
      <td>${post.author}</td>
      <td>${post.date}</td>
      <td>${post.views}</td>
      <td>${post.likes}</td>
    `;
    boardContent.appendChild(row);
  });

  // 페이지네이션 처리
  const pagination = document.getElementById("pagination");
  pagination.innerHTML = "";

  const totalPages = Math.ceil(filteredPosts.length / postsPerPage);
  for (let i = 1; i <= totalPages; i++) {
    const pageLink = document.createElement("a");
    pageLink.href = "#";
    pageLink.textContent = i;
    pageLink.classList.toggle("active", i === page);
    pageLink.addEventListener("click", () => renderPosts(i, searchType, query));
    pagination.appendChild(pageLink);
  }
}

// 검색 폼 이벤트 처리
document.getElementById("searchForm").addEventListener("submit", (e) => {
  e.preventDefault();
  const searchType = document.getElementById("searchType").value;
  const query = document.getElementById("searchQuery").value.trim();
  renderPosts(1, searchType, query);
});

// 초기 게시글 렌더링
renderPosts();

document.querySelector('#searchForm').addEventListener('submit', (e) => {
    e.preventDefault();

    const searchType = document.querySelector('#searchType').value;
    const searchQuery = document.querySelector('#searchQuery').value;

    if (!searchQuery.trim()) {
        alert('검색어를 입력하세요.');
        return;
    }

    window.location.href = `/board?searchType=${searchType}&searchQuery=${searchQuery}`;
});