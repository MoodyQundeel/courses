from django.shortcuts import render
import random as r
from django.http import HttpResponseRedirect
from django.urls import reverse
from markdown import markdown
from . import util


def index(request):
    return render(request, "encyclopedia/index.html", {
        "entries": util.list_entries()
    })


def entry(request, entry):
    isSearch = False
    if (entry == "search"):
        entry = request.GET['q']
        isSearch = True
    if entry in util.list_entries():
        return render(request, "encyclopedia/entry.html", {
            "entry": entry,
            "entry_content": markdown(util.get_entry(entry))
        })
    elif isSearch:
        entriesList = []
        for e in util.list_entries():
            if e.find(entry) != -1:
                entriesList.append(e)
        if (len(entriesList) != 0):
            return render(request, "encyclopedia/index.html", {
                "entries": entriesList
            })

    return render(request, "encyclopedia/index.html", {
        "error": "No entry with that name.",
        "entries": util.list_entries()
    })



def new(request):
    if (request.method == "GET"):
        return render(request, "encyclopedia/new.html")
    else:
        title = request.POST['title']
        content = request.POST['content']
        if (title in util.list_entries()):
            return render(request, "encyclopedia/new.html", {
                "error": "There is already an entry with that name"
            })
        util.save_entry(title, content)
        return HttpResponseRedirect(reverse("entry", args=[title]))


def edit(request, entry):
    if (request.method == "GET"):
        entry_content = util.get_entry(entry)
        return render(request, "encyclopedia/edit.html", {
            "title": entry,
            "entry": entry_content
        })
    else:
        util.save_entry(entry, request.POST['content'])
        return HttpResponseRedirect(reverse('entry', args=[entry]))


def random(request):
    entry = r.choice(util.list_entries())
    return HttpResponseRedirect(reverse('entry', args=[entry]))
