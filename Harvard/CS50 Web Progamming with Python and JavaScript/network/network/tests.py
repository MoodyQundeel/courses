from django.test import TestCase
from .models import User, Post

# Create your tests here.
class PostTestCase(TestCase): 
    def setUp(self): 
        moody = User.objects.create(username="Moody", password="moody123")
        omar = User.objects.create(username="Omar", password="omar123")
        reem = User.objects.create(username="Reem", password="reem123")
        Post.objects.create(user=moody, text="Hello, World!")
        Post.objects.create(user=omar, text="Hello, People!")
        moody.followers.add(omar)
        
    def test_user_count(self):
        self.assertEqual(User.objects.count(), 3)

    def test_post_count(self):
        self.assertEqual(Post.objects.count(), 2)

    def test_post_content(self):
        self.assertEqual(Post.objects.first().text, "Hello, World!")
        self.assertEqual(Post.objects.all()[1].text, "Hello, People!")

    def test_follwer_count(self):
        moody = User.objects.get(username="Moody")
        self.assertEqual(moody.followers.count(), 1)
        User.objects.get(username="Reem").following.add(moody)
        self.assertEqual(moody.followers.count(), 2)