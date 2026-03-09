// ========== FORUM PAGE ==========
// Voting and commenting are handled server-side via form POST.
// This file only handles the client-side expand/collapse of post details.

document.addEventListener('DOMContentLoaded', () => {
    // Expand a post when clicking on its main body area.
    // Navigates to the post detail URL (which the server renders with expandedPostId set).
    document.querySelectorAll('.post-main').forEach(postMain => {
        postMain.addEventListener('click', () => {
            const card   = postMain.closest('.post-card');
            const postId = card ? card.dataset.postId : null;
            if (postId) {
                window.location.href = `/app/forum/${postId}`;
            }
        });
        postMain.style.cursor = 'pointer';
    });
});
