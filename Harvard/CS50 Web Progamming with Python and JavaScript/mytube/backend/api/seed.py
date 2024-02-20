import os
from django.core.files import File
from api.models import Video, User
path = "../Example Videos/"
videos = path + "Videos/"
thumbnails = path + "Thumbnails/"
descriptions = path + "descriptions/"

try:
    user = User.objects.get(username="GMM")
except:
    user = User.objects.create(
        username="GMM", password="rhettandlink", is_active=True)


def seed():
    for title in os.listdir(videos):
        title = title.split(".mp4")[0]
        description = open(descriptions+title+".txt", encoding="utf8").read()
        video = File(open(videos+title+".mp4", "rb"), name=title+".mp4")
        thumbnail = File(open(thumbnails+title+".jpg", "rb"),
                         name=title+".jpg")
        Video.objects.create(
            user=user, title=title, description=description, video=video, thumbnail=thumbnail)
    for title in os.listdir(videos):
        title = title.split(".mp4")[0]
        description = open(descriptions+title+".txt", encoding="utf8").read()
        video = File(open(videos+title+".mp4", "rb"), name=title+".mp4")
        thumbnail = File(open(thumbnails+title+".jpg", "rb"),
                         name=title+".jpg")
        Video.objects.create(
            user=user, title=title, description=description, video=video, thumbnail=thumbnail)
