// Features data
const features = [
    "Keep track of your menstrual cycle dates",
    "Log moods and symptoms",
    "Receive help from trusted specialists",
    "Access anonymised help chats",
    "Learn about online resources"
];

// Check icon SVG
function createCheckIcon() {
    const svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
    svg.setAttribute("viewBox", "0 0 24 24");
    svg.setAttribute("fill", "none");
    svg.setAttribute("stroke", "currentColor");
    svg.setAttribute("stroke-width", "2");
    svg.setAttribute("stroke-linecap", "round");
    svg.setAttribute("stroke-linejoin", "round");

    const polyline = document.createElementNS("http://www.w3.org/2000/svg", "polyline");
    polyline.setAttribute("points", "20 6 9 17 4 12");

    svg.appendChild(polyline);
    return svg;
}

// Render features
function renderFeatures() {
    const featuresList = document.getElementById('featuresList');

    features.forEach(feature => {
        const featureItem = document.createElement('div');
        featureItem.className = 'feature-item';

        const checkIcon = document.createElement('div');
        checkIcon.className = 'check-icon';
        checkIcon.appendChild(createCheckIcon());

        const featureText = document.createElement('p');
        featureText.className = 'feature-text';
        featureText.textContent = feature;

        featureItem.appendChild(checkIcon);
        featureItem.appendChild(featureText);

        featuresList.appendChild(featureItem);
    });
}

// Initialize the app
document.addEventListener('DOMContentLoaded', () => {
    renderFeatures();
});