document.addEventListener('DOMContentLoaded', () => {
    const buttons = document.querySelectorAll('.but-group button');
    const imageGroups = document.querySelectorAll('.images');

    // 기본값으로 동물 사진 활성화
    const defaultCategory = 'animal';
    const defaultGroup = document.querySelector(`.images.${defaultCategory}`);
    if (defaultGroup) {
        defaultGroup.classList.add('active');
    } else {
        console.error(`Default category "${defaultCategory}"  not exist.`);
    }

    // 버튼 클릭 이벤트 설정
    buttons.forEach(button => {
        button.addEventListener('click', () => {
            const category = button.getAttribute('data-category');
            if (!category) {
                console.warn('No category.');
                return;
            }

            // 모든 이미지 그룹 숨김
            imageGroups.forEach(group => {
                group.classList.remove('active');
            });

            // 선택한 카테고리 활성화
            const activeGroup = document.querySelector(`.images.${category}`);
            if (activeGroup) {
                activeGroup.classList.add('active');
            } else {
                console.warn(`No image group found for category: "${category}"`);
            }
        });
    });
});