from django.urls import path

from . import views

urlpatterns = [
    path("", views.index, name="index"),
    path("login", views.login_view, name="login"),
    path("logout", views.logout_view, name="logout"),
    path("register", views.register, name="register"),
    path("listing/<int:id>", views.listing_view, name="listing"),
    path("create", views.create, name="create"),
    path("close <int:id>", views.close, name="close"),
    path("categories", views.categories, name="categories"),
    path("category/<str:category>", views.category, name="category"),
    path("comment <int:id>", views.comment, name="comment"),
    path("watchlist", views.watchlist, name="watchlist"),
    path("watchlist <int:id>", views.watchlist, name="watchlist_modify")
]
