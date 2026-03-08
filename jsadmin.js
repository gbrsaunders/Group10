// Mock user data
const mockUsers = [
    {
        id: 1,
        username: "sarah_chen",
        email: "sarah.chen@example.com",
        role: "admin",
        dateOfBirth: "1995-04-12",
        daysJoined: 543
    },
    {
        id: 2,
        username: "emma_wilson",
        email: "emma.wilson@example.com",
        role: "user",
        dateOfBirth: "1998-08-23",
        daysJoined: 421
    },
    {
        id: 3,
        username: "olivia_martinez",
        email: "olivia.m@example.com",
        role: "moderator",
        dateOfBirth: "1992-11-05",
        daysJoined: 687
    },
    {
        id: 4,
        username: "sophia_anderson",
        email: "sophia.and@example.com",
        role: "user",
        dateOfBirth: "2000-02-14",
        daysJoined: 234
    },
    {
        id: 5,
        username: "ava_thompson",
        email: "ava.thompson@example.com",
        role: "user",
        dateOfBirth: "1997-06-30",
        daysJoined: 156
    },
    {
        id: 6,
        username: "mia_garcia",
        email: "mia.garcia@example.com",
        role: "moderator",
        dateOfBirth: "1994-09-18",
        daysJoined: 789
    },
    {
        id: 7,
        username: "isabella_rodriguez",
        email: "isabella.r@example.com",
        role: "user",
        dateOfBirth: "1999-12-25",
        daysJoined: 98
    },
    {
        id: 8,
        username: "charlotte_lee",
        email: "charlotte.lee@example.com",
        role: "admin",
        dateOfBirth: "1993-03-07",
        daysJoined: 912
    }
];

// Function to get badge class based on role
function getRoleBadgeClass(role) {
    switch (role) {
        case "admin":
            return "badge-admin";
        case "moderator":
            return "badge-moderator";
        default:
            return "badge-user";
    }
}

// Function to create a table row
function createTableRow(user) {
    const tr = document.createElement('tr');
    
    tr.innerHTML = `
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>${user.email}</td>
        <td>
            <span class="badge ${getRoleBadgeClass(user.role)}">
                ${user.role}
            </span>
        </td>
        <td>${user.dateOfBirth}</td>
        <td>${user.daysJoined} days</td>
    `;
    
    return tr;
}

// Function to populate the table
function populateTable() {
    const tableBody = document.getElementById('userTableBody');
    
    // Clear existing rows
    tableBody.innerHTML = '';
    
    // Add rows for each user
    mockUsers.forEach(user => {
        const row = createTableRow(user);
        tableBody.appendChild(row);
    });
}

// Initialize the table when the DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    populateTable();
    
    // Add event listeners to buttons (optional - add functionality as needed)
    const buttons = document.querySelectorAll('.btn');
    buttons.forEach(button => {
        button.addEventListener('click', (e) => {
            console.log(`${e.target.textContent} button clicked`);
            // Add your button functionality here
        });
    });
});

// Optional: Function to add a new user (can be called from the Add User button)
function addUser(user) {
    mockUsers.push(user);
    populateTable();
}

// Optional: Function to export data (can be called from the Export Data button)
function exportData() {
    const dataStr = JSON.stringify(mockUsers, null, 2);
    const dataBlob = new Blob([dataStr], { type: 'application/json' });
    const url = URL.createObjectURL(dataBlob);
    const link = document.createElement('a');
    link.href = url;
    link.download = 'hertracker-users.json';
    link.click();
    URL.revokeObjectURL(url);
}
