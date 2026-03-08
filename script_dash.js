// You can customize the user name here
const userName = 'Sarah';

// Set the user name in the greeting
document.getElementById('userName').textContent = userName;

// Add click event listeners to feature boxes (optional - add your navigation logic here)
const featureBoxes = document.querySelectorAll('.feature-box');
featureBoxes.forEach((box, index) => {
    box.addEventListener('click', function() {
        const title = this.querySelector('h3').textContent;
        console.log(`Clicked on: ${title}`);
        // Add your navigation or action logic here
        // Example: window.location.href = '/tracker';
    });
});
