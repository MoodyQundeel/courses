from django.http import FileResponse, JsonResponse
from api.models import User, Video
from django.core.paginator import Paginator
import json
import os


def register_or_login(request):
    username = request.POST["username"]
    password = request.POST["password"]

    try:
        user = User.objects.get(username=username)
        if password == user.password:
            return JsonResponse({"message": 'User logged in.'})
        else:
            return JsonResponse({"message": 'Wrong password!'})

    except:
        username = request.POST['username']
        password = request.POST['password']
        user = User(username=username, password=password)

        try:
            user.full_clean()
            user.save()
            return JsonResponse({"message": 'User registered.'})

        except:
            return JsonResponse({"message": 'Invalid form!'})


def file(request, path: str):
    response = FileResponse(open(path, "rb"))

    if ".mp4" in path:
        length = os.path.getsize(path)
        start = 0
        end = length-1
        response.status_code = 206
        response['Content-Type'] = "video/mp4"
        response['Content-Range'] = f'bytes {start}-{end}/{length}'

    return response


def view(request):
    user = User.objects.get(username=request.COOKIES['username'])
    body = json.loads(request.body)
    video = Video.objects.get(id=body['id'])
    video.views.add(user)
    response = JsonResponse(
        {"message": "View registered!", "video": video.video.url})
    response['Access-Control-Allow-Credentials'] = 'true'
    return response


def upload(request):
    if request.method == "POST":
        username = request.POST['username']
        user = User.objects.get(username=username)
        video = Video(user=user, title=request.POST['title'], description=request.POST['description'],
                      video=request.FILES['video'], thumbnail=request.FILES['thumbnail'])

        try:
            video.full_clean()
            video.save()
            response = JsonResponse({'message': 'File uploaded.'})

        except Exception as e:
            print(e)
            response = JsonResponse({'message': 'Invalid form!'})

    return response


def videos(request):
    page = request.GET['page']
    # user = User.objects.get(request.COOKIES['username'])
    videos = Video.objects.all()
    paginator = Paginator(videos, 8)
    page = paginator.get_page(page)
    details = []
    details.append({"hasNext": page.has_next()})
    for video in page:
        details.append(video.details())
    response = JsonResponse(json.dumps(details), safe=False)
    response['Access-Control-Allow-Credentials'] = 'true'
    return response
