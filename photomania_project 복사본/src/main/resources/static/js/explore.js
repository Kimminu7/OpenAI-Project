document.querySelectorAll('.photo-card').forEach((card) => {
    const textarea = card.querySelector('textarea');
    const commentList = document.createElement('ul');
    commentList.className = 'comment-list';
    card.appendChild(commentList);

    textarea.addEventListener('keypress', (event) => {
        if (event.key === 'Enter' && textarea.value.trim() !== '') {
            event.preventDefault();
            const comment = document.createElement('li');
            comment.textContent = textarea.value.trim();
            commentList.appendChild(comment);
            textarea.value = ''; // 입력창 초기화
        }
    });
});

document.querySelectorAll('.like-btn').forEach((button) => {
    let liked = false; // 좋아요 상태
    button.addEventListener('click', () => {
        liked = !liked;
        button.textContent = liked ? '🩷 Liked' : '🩷 Like'; // 상태에 따라 텍스트 변경
    });
});

document.querySelectorAll('.good-btn').forEach((button) => {
    let good = false; // 굿 상태
    button.addEventListener('click', () => {
        good = !good;
        button.textContent = good ? '👍🏻 Good!' : '👍🏻 Good'; // 상태에 따라 텍스트 변경
    });
});