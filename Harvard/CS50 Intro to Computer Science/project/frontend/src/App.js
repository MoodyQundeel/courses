import { React } from 'react'
import JoinPage from './components/JoinPage';
import ChatPage from './components/ChatPage'

const App = (props) => {
    if (sessionStorage.getItem("user")) return <ChatPage />
    else return <JoinPage />;
}

export default App;