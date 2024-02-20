from django.db import models
from django.contrib.auth.models import AbstractUser
import os


class User(AbstractUser):
    subscriptions = models.ManyToManyField('User', related_name="subscribers")
    is_active = models.BooleanField(default=True)


class Comment(models.Model):
    text = models.TextField()
    user = models.ForeignKey(
        'User', related_name="comments", on_delete=models.CASCADE)
    video = models.ForeignKey(
        'Video', related_name="comments", on_delete=models.CASCADE)


class Video(models.Model):
    user = models.ForeignKey(
        'User', related_name="videos", on_delete=models.CASCADE)
    title = models.CharField(max_length=100)
    description = models.TextField()
    video = models.FileField(upload_to="media/videos")
    thumbnail = models.ImageField(upload_to="media/thumbnails")
    views = models.ManyToManyField(
        'User', related_name="views")
    likes = models.ForeignKey(
        'User', related_name="likes", on_delete=models.CASCADE, null=True, blank=True)

    def details(self):
        return {
            "id": self.id,
            "user": self.user.username,
            "title": self.title,
            "video": os.path.basename(self.video.name),
            "thumbnail": self.thumbnail.url,
            "description": self.description
        }
