// ========== UNIVERSITY RESOURCES PAGE ==========
// Client-side search filter for the bathroom list.
// The "Report Empty" button submits a form to the server (no JS needed for that action).

document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('bathroom-search');
    if (!searchInput) return;

    searchInput.addEventListener('input', () => {
        const term  = searchInput.value.toLowerCase();
        const cards = document.querySelectorAll('#bathroom-list .bathroom-card');

        let anyVisible = false;
        cards.forEach(card => {
            const name     = (card.dataset.name     || '').toLowerCase();
            const building = (card.dataset.building || '').toLowerCase();
            const match    = name.includes(term) || building.includes(term);
            card.style.display = match ? '' : 'none';
            if (match) anyVisible = true;
        });

        // Show/hide empty-state message
        let emptyMsg = document.getElementById('bathroom-empty-msg');
        if (!anyVisible) {
            if (!emptyMsg) {
                emptyMsg = document.createElement('div');
                emptyMsg.id = 'bathroom-empty-msg';
                emptyMsg.style.cssText = 'text-align:center;padding:2rem;color:#64748b;';
                emptyMsg.textContent   = 'No bathrooms found matching your search.';
                document.getElementById('bathroom-list').appendChild(emptyMsg);
            }
            emptyMsg.style.display = '';
        } else if (emptyMsg) {
            emptyMsg.style.display = 'none';
        }
    });
});
