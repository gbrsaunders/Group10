// Adds the cycle tags once added, however all placeholders have been replaced.

document.addEventListener('DOMContentLoaded', () => {
    const daysEl    = document.getElementById('prediction-days');
    const dateEl    = document.getElementById('prediction-date');
    const lengthEl  = document.getElementById('prediction-length');

    // If the server already populated the prediction, nothing to do.
    if (daysEl && daysEl.textContent.trim() !== '-') return;

    // Attempt to derive a prediction from the first period card in the DOM.
    const firstPeriodValue = document.querySelector('.period-value');
    if (!firstPeriodValue) return;

    const latestDateStr = firstPeriodValue.textContent.trim();
    const latestDate = new Date(latestDateStr);
    if (isNaN(latestDate.getTime())) return;

    const nextDate = new Date(latestDate);
    nextDate.setDate(latestDate.getDate() + 28);

    const today = new Date();
    const diffTime = nextDate.getTime() - today.getTime();
    const daysUntil = Math.max(0, Math.ceil(diffTime / (1000 * 60 * 60 * 24)));

    if (daysEl)   daysEl.textContent   = daysUntil;
    if (dateEl)   dateEl.textContent   = formatDate(nextDate.toISOString().split('T')[0]);
    if (lengthEl) {
        // Read length from the first duration badge
        const badge = document.querySelector('.period-duration-badge');
        if (badge) lengthEl.textContent = badge.textContent.trim();
    }
});
