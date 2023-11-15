from django.contrib.auth.models import AbstractUser
from django.db import models

class User(AbstractUser):
    followers = models.ManyToManyField("User", related_name="following")
    
class Post(models.Model):
    user = models.ForeignKey("User", on_delete=models.CASCADE, related_name="posts")
    text = models.TextField(max_length=280)
    likes = models.ManyToManyField("User", related_name="likes", null=True)

    def serialize(self):
        return {
            "id": self.id,
            "user": self.user,
            "text": self.text,
            "like": self.like
        }