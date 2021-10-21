import json
from flask import Flask, request
from flask_sqlalchemy import SQLAlchemy
from flask_socketio import SocketIO
from flask_cors import CORS

app = Flask(__name__)
CORS(app)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///main.db'
db = SQLAlchemy(app)
socketio = SocketIO(app)
socketio.init_app(app, cors_allowed_origins="*")


class Message(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    message = db.Column(db.Text, nullable=False)
    user = db.Column(db.Text, nullable=False)
    room = db.Column(db.Integer)


@app.route('/join', methods=["POST"])
def join():
    name = request.json['name']
    room = request.json['room']
    if (name != None and room != None):
        return "joined"


@app.route('/message', methods=["POST"])
def message():
    message = request.json["message"]
    user = request.json["user"]
    room = request.json["room"]
    if (message != None and message != "" and user != None):
        newMessage = Message(
            message=message, user=user, room=room)
        db.session.add(newMessage)
        db.session.commit()
        socketio.emit('messageReceived')
        return 'message recieved'
    return 'invalid message'


@app.route('/messages', methods=["POST"])
def fetch_messages():
    messages = Message.query.filter_by(room=request.json["room"]).all()
    messagesArray = []
    i = 0
    for msg in messages:
        i += 1
        msgDict = dict()
        msgDict['id'] = i
        msgDict['message'] = msg.message
        msgDict['user'] = msg.user
        messagesArray.append(msgDict)

    return json.dumps(messagesArray)


@socketio.on('connect')
def connected():
    print('connected')


@socketio.on('disconnect')
def disconnected():
    print("disconnected")


if __name__ == '__main__':
    socketio.run(app, host="0.0.0.0", debug=False, port=4000)
