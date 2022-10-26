from random import Random
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.shortcuts import render, redirect
from markdown2 import markdown
from . import util

def index(request):   
    return render(request, "encyclopedia/index.html", {
        "entries": util.list_entries()
    })

def entry(request, entry):
    if entry in util.list_entries():
        return render(request, "encyclopedia/entry.html", {
            "entry": entry,
            "entry_content": markdown(util.get_entry(entry))
        })
    else: return HttpResponse("No entry with that name.")    

def search(request):
    query = str(request.GET.get("q"))
    if query in util.list_entries():
        return render(request, "encyclopedia/entry.html", {
            "entry": query,
            "entry_content": markdown(util.get_entry(query))
        })
    else:
        query = query.lower()
        results = []
        for entry in util.list_entries():
            if query in entry.lower():
                results.append(entry)
        empty = not bool(len(results))
        return render(request, "encyclopedia/search.html", {
            "results": results,
            "empty": empty
        })

@csrf_exempt
def create(request):
    if request.method == "GET":
        return render(request, "encyclopedia/create.html")
    else: 
        util.save_entry(request.POST.get("title"), request.POST.get("content"))
        return redirect('/')

        
@csrf_exempt
def edit(request):
    if request.method == "GET":
        title = request.GET.get("title")
        return render(request, "encyclopedia/edit.html", {
            "title": title,
            "content": util.get_entry(title)
        })
        
    else: 
        util.save_entry(request.POST.get("title"), request.POST.get("content"))
        return redirect('/')

def random(request):
    random = Random()
    entry = random.choice(util.list_entries())
    return render(request, "encyclopedia/entry.html", {
            "entry": entry,
            "entry_content": markdown(util.get_entry(entry))
        })


        


