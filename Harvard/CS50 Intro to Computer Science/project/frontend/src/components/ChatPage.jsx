import ChatBox from "./ChatBox"
import MessageBox from "./MessageBox"
import "../css/ChatPage.css"



const ChatPage = (props) => {
    return (
        <div className="chatPage">
            <ChatBox />
            <MessageBox />
        </div>
    );             
}

export default ChatPage