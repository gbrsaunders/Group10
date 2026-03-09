// ========== CHAT PAGE ==========

const chatMessages = {
    general: [
        { sender: 'Anon_User22', text: 'Has anyone tried using a heating pad at work? Do people notice?', isSelf: false, time: '10:32 AM' },
        { sender: 'Me',          text: 'I use one! I found a small portable one that hides under my sweater easily.', isSelf: true,  time: '10:35 AM' },
        { sender: 'Anon_Sarah',  text: 'I highly recommend those stick-on heat patches, they are a lifesaver.', isSelf: false, time: '10:40 AM' }
    ],
    beauty: [
        { sender: 'SkincareLover', text: 'Any tips for hormonal breakouts along the jawline?', isSelf: false, time: '9:00 AM' },
        { sender: 'Me',            text: 'Salicylic acid cleansers and hydrocolloid patches help me a lot!', isSelf: true,  time: '9:15 AM' }
    ],
    relationships: [
        { sender: 'ConfusedAnon', text: 'How do you communicate mood swings to your partner without starting an argument?', isSelf: false, time: 'Yesterday' }
    ],
    pcos: [
        { sender: 'PCOSWarrior', text: 'Just got diagnosed today. Feeling a bit overwhelmed. Any advice on where to start with diet changes?', isSelf: false, time: '11:20 AM' },
        { sender: 'Anon_Mia',    text: 'Take it one step at a time! Focusing on protein-heavy breakfasts really helped my energy levels.', isSelf: false, time: '11:25 AM' }
    ],
    ai: [
        { sender: 'AI Assistant', text: "Hi there! I'm your HerTrack wellness assistant. How can I help you today?", isSelf: false, time: 'Now' }
    ]
};

let activeTab  = 'anonymous';
let activeRoom = 'general';

const roomNames = {
    general:       'General',
    beauty:        'Beauty and Wellness',
    relationships: 'Relationships',
    pcos:          'PCOS and Endometriosis'
};

function renderMessages() {
    const messagesEl = document.getElementById('chat-messages');
    const labelEl    = document.getElementById('room-label');
    const inputEl    = document.getElementById('chat-input');
    const sidebarEl  = document.getElementById('chat-sidebar');

    const msgs = activeTab === 'ai' ? chatMessages.ai : (chatMessages[activeRoom] || []);
    const label = activeTab === 'ai' ? 'AI Wellness Chat' : `Room: ${roomNames[activeRoom] || activeRoom}`;

    if (labelEl) labelEl.textContent = label;
    if (inputEl) inputEl.placeholder = activeTab === 'ai'
        ? 'Ask the AI assistant...'
        : `Message #${roomNames[activeRoom] || activeRoom}...`;

    // Show/hide sidebar
    if (sidebarEl) sidebarEl.style.display = activeTab === 'anonymous' ? '' : 'none';

    if (!messagesEl) return;

    const roomLabelHtml = `<div class="chat-room-label"><span class="room-label-tag">${label}</span></div>`;
    const msgsHtml = msgs.map(msg => `
        <div class="message ${msg.isSelf ? 'self' : 'other'}">
            <div class="message-header">
                ${!msg.isSelf ? `
                    <div class="message-avatar ${activeTab === 'ai' ? 'ai' : 'user'}">
                        <i data-lucide="${activeTab === 'ai' ? 'bot' : 'user'}"></i>
                    </div>
                ` : ''}
                <span class="message-meta">${msg.sender} • ${msg.time}</span>
            </div>
            <div class="message-bubble">${msg.text}</div>
        </div>
    `).join('');

    messagesEl.innerHTML = roomLabelHtml + msgsHtml;
    lucide.createIcons();

    // Scroll to bottom
    messagesEl.scrollTop = messagesEl.scrollHeight;
}

document.addEventListener('DOMContentLoaded', () => {
    // Tab switching
    document.querySelectorAll('.chat-tab').forEach(tab => {
        tab.addEventListener('click', () => {
            activeTab = tab.dataset.tab;
            document.querySelectorAll('.chat-tab').forEach(t => t.classList.remove('active'));
            tab.classList.add('active');
            renderMessages();
        });
    });

    // Room switching
    document.querySelectorAll('.room-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            activeRoom = btn.dataset.room;
            document.querySelectorAll('.room-btn').forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
            renderMessages();
        });
    });

    // Send message (client-side demo only)
    const form = document.getElementById('chat-input-form');
    if (form) {
        form.addEventListener('submit', e => {
            e.preventDefault();
            const input = document.getElementById('chat-input');
            const text  = input.value.trim();
            if (!text) return;

            const roomKey = activeTab === 'ai' ? 'ai' : activeRoom;
            chatMessages[roomKey].push({
                sender: 'Me',
                text:   text,
                isSelf: true,
                time:   new Date().toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' })
            });

            input.value = '';
            renderMessages();
        });
    }

    renderMessages();
});
