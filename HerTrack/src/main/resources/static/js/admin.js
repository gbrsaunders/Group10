// ========== ADMIN PAGE ==========
// Client-side search filter for the users table.

document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('user-search');
    if (!searchInput) return;

    searchInput.addEventListener('input', () => {
        const term = searchInput.value.toLowerCase();
        const rows = document.querySelectorAll('#users-table-body tr');

        rows.forEach(row => {
            const text = row.textContent.toLowerCase();
            row.style.display = text.includes(term) ? '' : 'none';
        });
    });
});
