import { Avatar } from "@mui/material"
import "../css/Message.css"

const Message = (props) => {
    return (
        <div className="message">
            <Avatar className="message-avatar" sx={{width:35, height:35, mr:1}}>{props.data.user[0] + props.data.user[1]}</Avatar>
            <p>{props.data.message}</p>
        </div>
    );    
}


export default Message