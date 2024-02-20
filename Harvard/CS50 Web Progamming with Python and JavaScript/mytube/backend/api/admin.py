from django.contrib import admin
from api.models import User, Video, Comment
# Register your models here.
admin.site.register(User)
admin.site.register(Video)
admin.site.register(Comment)
