const img = document.querySelector(".internet-image");
const width = img.style.width;
img.addEventListener('mouseenter', function () {
    img.style.width = "70vw";
});

img.addEventListener('mouseleave', function () {
    img.style.width = width;
});