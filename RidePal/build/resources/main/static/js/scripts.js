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