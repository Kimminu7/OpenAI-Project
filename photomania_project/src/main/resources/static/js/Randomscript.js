document.getElementById('generateBtn').addEventListener('click', function() {
    generateRandomImage();
});

function generateRandomImage() {
    const gridContainer = document.getElementById('gridContainer');
    const randomImageUrl = generateRandomImageUrl();
    console.log(Math.random() * 1000+1)
    const imgElement = document.createElement('img');
    imgElement.src = randomImageUrl;
    gridContainer.appendChild(imgElement);
}

function generateRandomImageUrl() {
    const imageUrlBase = 'https://picsum.photos/500?random=1';
    const randomNumber = Math.floor(Math.random() * 1000); // 0부터 999 사이의 랜덤한 숫자 생성
    return `${imageUrlBase}?random=${randomNumber}`;
}
