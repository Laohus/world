const NUMBER_OF_LEAVES = 15;
const width = window.screen.width;
function init() {
    const container = document.getElementById('leafContainer');
    for (let i = 0; i < NUMBER_OF_LEAVES; i++) {
        container.appendChild(createALeaf());
    }
}
function randomInteger(low, high) {
    return low + Math.floor(Math.random() * (high - low));
}
function randomFloat(low, high) {
    return low + Math.random() * (high - low);
}
function pixelValue(value) {
    return value + 'px';
}
function durationValue(value) {
    return value + 's';
}
function createALeaf() {
    const leafDiv = document.createElement('div');
    const image = document.createElement('img');
    image.src = '../third/money/image/cash' + randomInteger(3, 7) + '.png';
    leafDiv.style.top = "-100px";
    leafDiv.style.left = pixelValue(randomInteger(0, width));
    const spinAnimationName = (Math.random() < 0.5) ? 'clockwiseSpin' : 'counterclockwiseSpinAndFlip';
    leafDiv.style.webkitAnimationName = 'fade, drop';
    image.style.webkitAnimationName = spinAnimationName;
    const fadeAndDropDuration = durationValue(randomFloat(3, 9));
    const spinDuration = durationValue(randomFloat(3, 7));
    leafDiv.style.webkitAnimationDuration = fadeAndDropDuration + ', ' + fadeAndDropDuration;
    const leafDelay = durationValue(randomFloat(0, 0));
    leafDiv.style.webkitAnimationDelay = leafDelay + ', ' + leafDelay;
    image.style.webkitAnimationDuration = spinDuration;
    leafDiv.appendChild(image);
    return leafDiv;
}
    window.addEventListener('load', init, false);