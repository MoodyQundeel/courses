from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required
from django.db import IntegrityError
from django.http import HttpResponseRedirect
from django.shortcuts import render
from django.urls import reverse
from .forms import *
from .models import *


def index(request):
    return render(request, "auctions/index.html", {
        "listings": Listing.objects.filter(open=True)
    })


def categories(request):
    return render(request, "auctions/categories.html", {
        "categories": Category.objects.all()
    })


def category(request, category):
    category = Category.objects.get(name=category)
    listings = Listing.objects.filter(category=category, open=True)
    return render(request, "auctions/index.html", {
        "listings": listings
    })


@login_required(login_url="/login")
def watchlist(request, id=None):
    user = request.user
    if request.method == "POST":
        listing = Listing.objects.get(pk=id)
        if user.watchlist.all().contains(listing):
            user.watchlist.remove(listing)
        else:
            user.watchlist.add(listing)
        return HttpResponseRedirect(reverse('watchlist'))
    else:
        return render(request, "auctions/watchlist.html", {
            "listings": user.watchlist.all()
        })


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
            return render(request, "auctions/login.html", {
                "message": "Invalid username and/or password."
            })
    else:
        return render(request, "auctions/login.html")


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
            return render(request, "auctions/register.html", {
                "message": "Passwords must match."
            })

        # Attempt to create new user
        try:
            user = User.objects.create_user(username, email, password)
            user.save()
        except IntegrityError:
            return render(request, "auctions/register.html", {
                "message": "Username already taken."
            })
        login(request, user)
        return HttpResponseRedirect(reverse("index"))
    else:
        return render(request, "auctions/register.html")


def listing_view(request, id):
    listing = Listing.objects.get(pk=id)
    if listing.open:
        if request.method == "POST":
            if request.user.is_authenticated:
                form = placeBidForm(request.POST)
                if form.is_valid():
                    user = request.user
                    bid = form.cleaned_data['bid']
                    if ((listing.winning_bid == None and bid > listing.starting_bid) or bid > listing.winning_bid.value):
                        newBid = Bid.objects.create(
                            user=user, value=bid, listing=listing)
                        listing.winning_bid = newBid
                        listing.save()
                        user.watchlist.add(listing)
                        return HttpResponseRedirect(reverse('index'))
                    else:
                        form.add_error(
                            'bid', "must be higher than current bid")
                        return render(request, "auctions/listing.html", {
                            "listing": listing,
                            "placeBidForm": form,
                            "commentForm": commentForm
                        })
                return render(request, "auctions/listing.html", {
                    "listing": listing,
                    "placeBidForm": form,
                    "commentForm": commentForm
                })

            else:
                return HttpResponseRedirect(reverse('login'))

        else:
            return render(request, "auctions/listing.html", {
                "listing": listing,
                "placeBidForm": placeBidForm,
                "commentForm": commentForm
            })
    else:
        return render(request, "auctions/listing.html", {
            "listing": listing
        })


@login_required(login_url="/login")
def create(request):
    if (request.method == "POST"):
        form = newListingForm(request.POST)
        if form.is_valid():
            user = request.user
            title = form.cleaned_data['title']
            description = form.cleaned_data['description']
            starting_bid = form.cleaned_data['starting_bid']
            photo = form.cleaned_data['photo']
            category = form.cleaned_data['category']
            listing = Listing.objects.create(
                title=title, description=description, starting_bid=starting_bid, user=user)
            if (category):
                category = Category.objects.get_or_create(name=category)[0]
                listing.category = category
                listing.save()
            if (photo):
                listing.photo = photo
                listing.save()
            user.watchlist.add(listing)
            return HttpResponseRedirect(reverse('index'))
        return render(request, "auctions/create.html", {
            "form": form
        })
    return render(request, "auctions/create.html", {
        "form": newListingForm
    })


@ login_required(login_url="/login")
def close(request, id):
    if request.method == "POST":
        listing = Listing.objects.get(pk=id)
        listing.open = False
        listing.save()
        if (listing.winning_bid != None):
            winner = listing.winning_bid.user
            if not winner.watchlist.all().contains(listing):
                winner.watchlist.add(listing)
        return HttpResponseRedirect(reverse('index'))


@ login_required(login_url="/login")
def comment(request, id):
    if request.method == "POST":
        form = commentForm(request.POST)
        if form.is_valid():
            user = request.user
            listing = Listing.objects.get(pk=id)
            text = request.POST['text']
            Comment.objects.create(user=user, listing=listing, text=text)
            return HttpResponseRedirect(reverse('listing', args=(id,)))
        else:
            return render(request, "auctions/listing.html", {
                "listing": listing,
                "placeBidForm": placeBidForm,
                "commentForm": form
            })
