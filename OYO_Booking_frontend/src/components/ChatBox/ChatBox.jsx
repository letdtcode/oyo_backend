import { useState } from 'react';
import './ChatBox.scss';
import globalSlice from '~/redux/globalSlice';
import { useDispatch, useSelector } from 'react-redux';

const ChatBox = () => {
    const [messages, setMessages] = useState([]);
    const [inputText, setInputText] = useState('');
    const dispatch = useDispatch();
    const chatbox = useSelector((state) => state.global.chatbox);

    const onClose = () => {
        dispatch(globalSlice.actions.setChatbox({ open: false }));
    };

    const sendMessage = () => {
        if (inputText.trim() !== '') {
            setMessages([...messages, { id: messages.length, text: inputText }]);
            setInputText('');
        }
    };

    const handleInputChange = (e) => {
        const text = capitalizeFirstLetter(e.target.value);
        setInputText(text);
    };

    const capitalizeFirstLetter = (string) => {
        return string.charAt(0).toUpperCase() + string.slice(1);
    };

    return (
        <div className="chatbox">
            <div className="chatbox-header">
                <img src={chatbox.avatar} alt="avatar" className="chatbox-header__avatar" />
                <div className="chatbox-header__name">{chatbox.name}</div>
                <div className="chatbox-header__close-btn" onClick={onClose}>
                    ×
                </div>
            </div>
            <div className="chatbox-messages">
                {messages.map((message) => (
                    <div key={message.id} className="message">
                        {message.text}
                    </div>
                ))}
            </div>
            <input
                type="text"
                className="input-message"
                placeholder="Aa"
                value={inputText}
                onChange={handleInputChange}
            />
            <button className="send-btn" onClick={sendMessage}>
                Send
            </button>
        </div>
    );
};

export default ChatBox;
