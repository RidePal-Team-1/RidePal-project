// const body = document.querySelector("body"),
//     sidebar = body.querySelector(".sidebar"),
//     toggle = body.querySelector(".toggle"),
//     // searchBtn = body.querySelector(".search-box"),
//     modeSwitch = body.querySelector(".toggle-switch"),
//     modeText = body.querySelector(".mode-text");

// toggle.addEventListener("click", () => {
//     sidebar.classList.toggle("close");
//     body.classList.toggle("sidebar-closed")
// });

// searchBtn.addEventListener("click", () => {
//     sidebar.classList.remove("close");
//     body.classList.remove("sidebar-closed")
// });

document.addEventListener('DOMContentLoaded', (event) => {
    const body = document.body;

    // Check if the user has a preference for dark mode
    const prefersDarkMode = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;

    // Check if it's the user's first visit (you can use local storage for this)
    const isFirstVisit = localStorage.getItem('visited') === null;

    // Set dark mode by default if it's the first visit or the user prefers dark mode
    if (isFirstVisit || prefersDarkMode) {
        body.classList.add('dark');
        // Save the visited status to local storage
        localStorage.setItem('visited', 'true');
    }

    const sidebar = body.querySelector(".sidebar");
    const toggle = body.querySelector(".toggle");
    const modeSwitch = body.querySelector(".toggle-switch");
    const modeText = body.querySelector(".mode-text");

    toggle.addEventListener("click", () => {
        sidebar.classList.toggle("close");
        body.classList.toggle("sidebar-closed");
    });

    modeSwitch.addEventListener("click", () => {
        body.classList.toggle("dark");

        if (body.classList.contains("dark")) {
            modeText.innerText = "Light Mode";
        } else {
            modeText.innerText = "Dark Mode";
        }
    });

    // ... rest of your existing script
});
// modeSwitch.addEventListener("click", () => {
//     body.classList.toggle("dark");
//
//     if(body.classList.contains("dark")) {
//         modeText.innerText = "Light Mode"
//     } else {
//         modeText.innerText = "Dark Mode"
//
//     }
// });

let subMenu = document.getElementById("subMenu");

function toggleMenu() {
    subMenu.classList.toggle("open-menu");
}
const initSlider = () => {
    const imageList = document.querySelector(".container-playlists .slider-wrapper .image-list");
    const slideButtons = document.querySelectorAll(".container-playlists .slider-wrapper .slide-button");
    const sliderScrollbar = document.querySelector(".container-playlists .slider-scrollbar");
    const scrollbarThumb = sliderScrollbar.querySelector(".container-playlists .scrollbar-thumb");
    const maxScrollLeft = imageList.scrollWidth - imageList.clientWidth;

    // Handle scrollbar thumb drag
    scrollbarThumb.addEventListener("mousedown", (e) => {
        const startX = e.clientX;
        const thumbPosition = scrollbarThumb.offsetLeft;
        const maxThumbPosition = sliderScrollbar.getBoundingClientRect().width - scrollbarThumb.offsetWidth;

        // Update thumb position on mouse move
        const handleMouseMove = (e) => {
            const deltaX = e.clientX - startX;
            const newThumbPosition = thumbPosition + deltaX;
            // Ensure the scrollbar thumb stays within bounds
            const boundedPosition = Math.max(0, Math.min(maxThumbPosition, newThumbPosition));
            const scrollPosition = (boundedPosition / maxThumbPosition) * maxScrollLeft;

            scrollbarThumb.style.left = `${boundedPosition}px`;
            imageList.scrollLeft = scrollPosition;
        }
        // Remove event listeners on mouse up
        const handleMouseUp = () => {
            document.removeEventListener("mousemove", handleMouseMove);
            document.removeEventListener("mouseup", handleMouseUp);
        }
        // Add event listeners for drag interaction
        document.addEventListener("mousemove", handleMouseMove);
        document.addEventListener("mouseup", handleMouseUp);
    });
    // Slide images according to the slide button clicks
    slideButtons.forEach(button => {
        button.addEventListener("click", () => {
            const direction = button.id === "prev-slide-playlists" ? -1 : 1;
            const scrollAmount = imageList.clientWidth * direction;
            imageList.scrollBy({ left: scrollAmount, behavior: "smooth" });
        });
    });
    // Show or hide slide buttons based on scroll position
    const handleSlideButtons = () => {
        slideButtons[0].style.display = imageList.scrollLeft <= 0 ? "none" : "flex";
        slideButtons[1].style.display = imageList.scrollLeft >= maxScrollLeft ? "none" : "flex";
    }
    // Update scrollbar thumb position based on image scroll
    const updateScrollThumbPosition = () => {
        const scrollPosition = imageList.scrollLeft;
        const thumbPosition = (scrollPosition / maxScrollLeft) * (sliderScrollbar.clientWidth - scrollbarThumb.offsetWidth);
        scrollbarThumb.style.left = `${thumbPosition}px`;
    }
    // Call these two functions when image list scrolls
    imageList.addEventListener("scroll", () => {
        updateScrollThumbPosition();
        handleSlideButtons();
    });
}
window.addEventListener("resize", initSlider);
window.addEventListener("load", initSlider);

const initSliderGenres = () => {
    const imageListGenres = document.querySelector(".container-genres .slider-wrapper .image-list");
    const slideButtonsGenres = document.querySelectorAll(".container-genres .slider-wrapper .slide-button");
    const sliderScrollbarGenres = document.querySelector(".container-genres .slider-scrollbar");
    const scrollbarThumbGenres = sliderScrollbarGenres.querySelector(".container-genres .scrollbar-thumb");
    const maxScrollLeftGenres = imageListGenres.scrollWidth - imageListGenres.clientWidth;

    // Handle scrollbar thumb drag
    scrollbarThumbGenres.addEventListener("mousedown", (e) => {
        const startXGenres = e.clientX;
        const thumbPositionGenres = scrollbarThumbGenres.offsetLeft;
        const maxThumbPositionGenres = sliderScrollbarGenres.getBoundingClientRect().width - scrollbarThumbGenres.offsetWidth;

        // Update thumb position on mouse move
        const handleMouseMoveGenres = (e) => {
            const deltaXGenres = e.clientX - startXGenres;
            const newThumbPositionGenres = thumbPositionGenres + deltaXGenres;
            // Ensure the scrollbar thumb stays within bounds
            const boundedPositionGenres = Math.max(0, Math.min(maxThumbPositionGenres, newThumbPositionGenres));
            const scrollPositionGenres = (boundedPositionGenres / maxThumbPositionGenres) * maxScrollLeftGenres;

            scrollbarThumbGenres.style.left = `${boundedPositionGenres}px`;
            imageListGenres.scrollLeft = scrollPositionGenres;
        }
        // Remove event listeners on mouse up
        const handleMouseUpGenres = () => {
            document.removeEventListener("mousemove", handleMouseMoveGenres);
            document.removeEventListener("mouseup", handleMouseUpGenres);
        }
        // Add event listeners for drag interaction
        document.addEventListener("mousemove", handleMouseMoveGenres);
        document.addEventListener("mouseup", handleMouseUpGenres);
    });
    // Slide images according to the slide button clicks
    slideButtonsGenres.forEach(button => {
        button.addEventListener("click", () => {
            const directionGenres = button.id === "prev-slide-genres" ? -1 : 1;
            const scrollAmountGenres = imageListGenres.clientWidth * directionGenres;
            imageListGenres.scrollBy({ left: scrollAmountGenres, behavior: "smooth" });
        });
    });
    // Show or hide slide buttons based on scroll position
    const handleSlideButtonsGenres = () => {
        slideButtonsGenres[0].style.display = imageListGenres.scrollLeft <= 0 ? "none" : "flex";
        slideButtonsGenres[1].style.display = imageListGenres.scrollLeft >= maxScrollLeftGenres ? "none" : "flex";
    }
    // Update scrollbar thumb position based on image scroll
    const updateScrollThumbPositionGenres = () => {
        const scrollPosition = imageListGenres.scrollLeft;
        const thumbPosition = (scrollPosition / maxScrollLeftGenres) * (sliderScrollbarGenres.clientWidth - scrollbarThumbGenres.offsetWidth);
        scrollbarThumbGenres.style.left = `${thumbPosition}px`;
    }
    // Call these two functions when image list scrolls
    imageListGenres.addEventListener("scroll", () => {
        updateScrollThumbPositionGenres();
        handleSlideButtonsGenres();
    });
}
window.addEventListener("resize", initSliderGenres);
window.addEventListener("load", initSliderGenres);