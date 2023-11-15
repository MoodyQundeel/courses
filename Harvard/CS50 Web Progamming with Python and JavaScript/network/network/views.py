from django.contrib.auth import authenticate, login, logout
from django.db import IntegrityError
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse
from django.shortcuts import render
from django.urls import reverse
from django.views.decorators.csrf import csrf_exempt
from django.core.paginator import Paginator
import json

from .models import User, Post

def paginate(request, posts):
    p = Paginator(posts, 4)
    page_number = request.GET.get("page") 
    if not page_number:
        page_number = 1
    page_obj = p.get_page(page_number)
    return page_obj

def index(request):
    return render(request, "network/index.html", {"posts": paginate(request, Post.objects.all())})


def login_view(request):
    if request.method == "POST":

        # Attempt to sign user in
        username = request.POST["username"]
        password = request.POST["password"]
        user = authenticate(request, username=username, password=password)

        # Check if authentication successful
        if user is not None:
            login(request, user)
            return HttpResponseRedirect(reverse("index"))
        else:
            return render(request, "network/login.html", {
                "message": "Invalid username and/or password."
            })
    else:
        return render(request, "network/login.html")


def logout_view(request):
    logout(request)
    return HttpResponseRedirect(reverse("index"))


def register(request):
    if request.method == "POST":
        username = request.POST["username"]
        email = request.POST["email"]

        # Ensure password matches confirmation
        password = request.POST["password"]
        confirmation = request.POST["confirmation"]
        if password != confirmation:
            return render(request, "network/register.html", {
                "message": "Passwords must match."
            })

        # Attempt to create new user
        try:
            user = User.objects.create_user(username, email, password)
            user.save()
        except IntegrityError:
            return render(request, "network/register.html", {
                "message": "Username already taken."
            })
        login(request, user)
        return HttpResponseRedirect(reverse("index"))
    else:
        return render(request, "network/register.html")

@csrf_exempt
def new_post(request): 
    text = request.POST["new-post-text"]
    user = request.user
    Post.objects.create(user=user, text=text)
    return HttpResponseRedirect("/")

@csrf_exempt
def like(request):
    user = request.user
    id = json.loads(request.body).get("id")
    post = Post.objects.get(id=id)
    if user.likes.contains(post):
        user.likes.remove(post)
    else: user.likes.add(post)
    return JsonResponse({"likes": post.likes.all().count()})

@csrf_exempt
def edit(request):
    data = json.loads(request.body)
    postId = data.get("id")
    text = data.get("text")
    post = Post.objects.get(id=postId)
    post.text = text
    post.save()
    return HttpResponse()


def following(request): 
    user = request.user
    following = user.following.all()
    posts = []
    for account in following:
        for post in account.posts.all():
            posts.append(post)
    return render(request, "network/index.html", {"posts": paginate(request, posts)})

def profile(request, username):
    user = User.objects.get(username = username)
    posts = user.posts.all()
    return render(request, "network/profile.html", {"profile_user": user, "posts": paginate(request, posts)})

@csrf_exempt
def follow(request): 
    user = request.user
    profileName = json.loads(request.body).get("currentProfile")
    userToFollow = User.objects.get(username=profileName)
    if user.following.contains(userToFollow): 
        user.following.remove(userToFollow)
    else: 
        userToFollow.followers.add(user)
    return JsonResponse({"followers": userToFollow.followers.count()})

