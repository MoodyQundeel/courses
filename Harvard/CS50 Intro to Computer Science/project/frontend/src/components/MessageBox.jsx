import { Button, TextField, Icon } from "@mui/material"
import "../css/MessageBox.css"
import axios from 'axios'


const MessageBox = (props) => {
    return (
        <form method="POST" id="messageForm" onSubmit={e => handleSubmit(e)}>
            <TextField fullWidth={true} autoComplete="off" variant="filled" label="Message" id="message"></TextField>
            <Button sx={{ ml: 2, borderRadius: "50%", minHeight: "40px", minWidth: "40px", maxHeight: "40px", maxWidth: "40px"}} variant="contained" type="submit" className="sendBtn"><Icon sx={{ fontSize: "22px"}}>send</Icon></Button>
        </form>
    )
}

const handleSubmit = (e) => {
    e.preventDefault()
    const message = document.getElementById("message").value
    axios.post('http://localhost:4000/message', {
        message: message,
        user: sessionStorage.getItem("user"),
        room: sessionStorage.getItem("room")
      }).then(() => {
            document.getElementById('message').value = "";
      })
    
}

export default MessageBox