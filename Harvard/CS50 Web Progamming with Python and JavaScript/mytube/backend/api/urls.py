from django.urls import path
from api import views


urlpatterns = [
    path("register", views.register_or_login, name="register"),
    path("upload", views.upload, name="upload"),
    path("videos", views.videos, name="videos"),
    path('view', views.view, name="view"),
    path("<path:path>", views.file, name="file")
]
