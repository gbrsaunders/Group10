// ========== LOG PERIOD PAGE ==========
// Handles symptom toggle buttons and packs selected values into a hidden input
// before the form is submitted to the Spring controller.

document.addEventListener('DOMContentLoaded', () => {
    const selectedSymptoms = new Set();
    const hiddenInput = document.getElementById('symptoms-hidden');
    const form = document.getElementById('period-form');

    // Symptom toggle buttons
    document.querySelectorAll('.symptom-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const symptom = btn.dataset.symptom;
            if (selectedSymptoms.has(symptom)) {
                selectedSymptoms.delete(symptom);
                btn.classList.remove('selected');
            } else {
                selectedSymptoms.add(symptom);
                btn.classList.add('selected');
            }
        });
    });

    // Pack selected symptoms into the hidden input as a comma-separated list
    // so Spring can bind it to a List<String> parameter.
    if (form) {
        form.addEventListener('submit', () => {
            if (hiddenInput) {
                hiddenInput.value = Array.from(selectedSymptoms).join(',');
            }
        });
    }
});
