import { Button, TextField } from "@mui/material"
import "../css/JoinPage.css"
import axios from 'axios'

const JoinPage = (props) => {
    return (
        <form method="POST" id="joinform" onSubmit={e => handleSubmit(e)}>
            <TextField required InputLabelProps={{required: false}} sx={{ mb: 2 }} fullWidth={true} variant="outlined" label="Name" id="name"></TextField>
            <TextField required InputLabelProps={{required: false}} fullWidth={true} wvariant="outlined" label="Room" id="room"></TextField>
            <Button sx={{ mt: 3 }} variant="contained" type="submit">Join</Button>
        </form>
    )
}

const handleSubmit = (e) => {
    e.preventDefault()
    const name = document.getElementById("name").value
    const room = document.getElementById("room").value
    axios.post('http://localhost:4000/join', {
        name: name,
        room: room
      }).then(res => {
        sessionStorage.setItem('user', name)
        sessionStorage.setItem('room', room)
      }).then(() => {
          window.location.reload()
      })
    
}

export default JoinPage