from django.contrib.auth.models import AbstractUser
from django.db import models


class User(AbstractUser):
    watchlist = models.ManyToManyField("Listing", related_name="users")
    pass


class Listing(models.Model):
    title = models.CharField(max_length=64)
    description = models.TextField(max_length=500)
    user = models.ForeignKey(
        User, on_delete=models.CASCADE, related_name="listings")
    photo = models.URLField(blank=True)
    category = models.ForeignKey(
        "Category", related_name="listings", null=True, on_delete=models.SET_NULL)
    starting_bid = models.IntegerField()
    winning_bid = models.OneToOneField(
        "Bid", on_delete=models.CASCADE, null=True, related_name="winning_listing")
    open = models.BooleanField(default=True)


class Category(models.Model):
    name = models.CharField(max_length=64, unique=True)
    
    class Meta:
        verbose_name_plural = "categories"

class Comment(models.Model):
    text = models.TextField()
    user = models.ForeignKey(
        User, on_delete=models.CASCADE, related_name="comments")
    listing = models.ForeignKey(
        Listing, on_delete=models.CASCADE, related_name="comments")


class Bid(models.Model):
    value = models.IntegerField()
    user = models.ForeignKey(
        User, on_delete=models.CASCADE, related_name="bids")
    listing = models.ForeignKey(
        Listing, on_delete=models.CASCADE, related_name="bids")
