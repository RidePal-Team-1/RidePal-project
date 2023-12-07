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
let subMenuEdit = document.getElementById("subMenu-edit");

function toggleMenu() {
    subMenu.classList.toggle("open-menu");
}
function toggleMenuEdit() {
    subMenuEdit.classList.toggle("open-menu");
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
window.addEventListener("load", initSlider)
window.addEventListener('DOMContentLoaded', event => {
    const profilePicture = document.getElementById('profilePicture');
    const profilePictureContainer = document.getElementById('profilePictureContainer');

    if (profilePictureContainer) {
        profilePictureContainer.addEventListener('click', function () {
            const fileInput = document.createElement('input');
            fileInput.type = 'file';
            fileInput.accept = 'image/*';
            fileInput.style.display = 'none';

            fileInput.addEventListener('change', function (event) {
                const file = event.target.files[0];
                if (file) {
                    const reader = new FileReader();

                    reader.onload = function (event) {
                        profilePicture.src = event.target.result;
                        // Update hidden input value to include the file as a base64 data URL
                        const hiddenInput = document.getElementById('hiddenPhotoInput');
                        hiddenInput.value = event.target.result;

                        // Submit the form only after the file has been read
                        const form = document.getElementById('playlistForm');
                        form.submit();
                    };

                    reader.readAsDataURL(file);
                }
            });
            fileInput.click(); // Trigger file input when the container is clicked
        });
    }
});

// JavaScript functions to open and close the modal
function openModal() {
    document.getElementById('syncModal').style.display = 'block';
}

function closeModal() {
    document.getElementById('syncModal').style.display = 'none';
}

// Close the modal if the user clicks outside of it
window.onclick = function(event) {
    var modal = document.getElementById('syncModal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
}
// const form = document.getElementById('playlistForm');
//
// form.submit();

// Function to open the modal
function openModal2() {
    document.getElementById("editModal").style.display = "block";
}

// Function to close the modal
function closeModal2() {
    document.getElementById("editModal").style.display = "none";
}

// Close the modal if the user clicks outside of it
window.onclick = function (event) {
    var modal = document.getElementById("editModal");
    if (event.target == modal) {
        modal.style.display = "none";
    }
};

function confirmDelete() {
    // Display a confirmation dialog
    return confirm('Are you sure you want to delete this user?');
}


    function openModalGenerateTrip() {
    document.getElementById('modal-container').style.display = 'flex';
}

    function closeModalGenerateTrip() {
    document.getElementById('modal-container').style.display = 'none';
}

    // Close modal if the user clicks outside the modal content
    window.onclick = function (event) {
    var modalContainer = document.getElementById('modal-container');
    if (event.target == modalContainer) {
    modalContainer.style.display = 'none';
}
}

    function calculateTotal() {
    var checkedCheckboxes = document.querySelectorAll('.genre-checkbox:checked');
    var total = 0;

    checkedCheckboxes.forEach(function (checkbox) {
    var inputField = checkbox.closest('.select-genres').querySelector('input[type="number"]');
    total += parseInt(inputField.value);
});

    return total;
}

    function validateTotal() {
    var total = calculateTotal();
    var errorMessage = document.getElementById('error-message');

    if (total > 100) {
    errorMessage.textContent = 'Total cannot exceed 100. Please adjust your selections.';
    return false;
} else {
    errorMessage.textContent = ''; // Clear the error message if total is within limits
}

    return true;
}

    function toggleInput(checkbox) {
    var inputField = checkbox.closest('.select-genres').querySelector('input[type="number"]');
    var genreKey = checkbox.value;
    var mapField = document.getElementById('genres[' + genreKey + ']');

    if (checkbox.checked) {
    inputField.disabled = false;
    if (inputField.value === '') {
    // If the field is blank, calculate and set the value in the map
    var checkedCheckboxes = document.querySelectorAll('.genre-checkbox:checked');
    var numberOfBlankFields = checkedCheckboxes.length - 1; // excluding the current field
    var valueToSet = numberOfBlankFields > 0 ? Math.floor((100 - inputField.value) / numberOfBlankFields) : 0;
    mapField.value = valueToSet;
} else {
    // If the field has a value, set it directly in the map
    mapField.value = parseInt(inputField.value);
}
} else {
    inputField.disabled = true;
    inputField.value = 0; // Reset the value when unchecked
    mapField.value = 0; // Reset the value in the map when unchecked
}
}


    // Add an event listener for the checkboxes
    document.addEventListener('change', function (event) {
    if (event.target.classList.contains('genre-checkbox')) {
    toggleInput(event.target);
}
});

    // Add an event listener for the form submission
    document.getElementById('modal').addEventListener('submit', function (event) {
    if (!validateTotal()) {
    event.preventDefault(); // Prevent form submission if validation fails
}
});





