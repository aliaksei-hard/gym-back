console.log('user_main_page.js loaded');
document.addEventListener('DOMContentLoaded', () => {
    const toAdminButton = document.getElementById('to-admin-page');
    if (toAdminButton) {
        console.log("HI")
        toAdminButton.addEventListener('click', redirect);
    }
});

const redirect = () => {
    console.log("Trying to go to admin page.")
    window.location.assign("admin.html")
}