addEventListener("DOMContentLoaded", () => {
    let editButton = document.querySelector("#edit-button");

    if (editButton) {
        editButton.onclick = (e) => {
            let postText = e.target.parentElement.parentElement.children[1];
            if (editButton.innerHTML == "edit_note") {
                fetch("/edit", {
                    method: "POST",
                    body: JSON.stringify({
                        text: postText.innerHTML,
                        id: e.target.dataset.postId,
                    }),
                }).then(() => {
                    postText.setAttribute("contentEditable", "false");
                    e.target.innerHTML = "edit";
                });
            } else {
                postText.setAttribute("contentEditable", "true");
                e.target.innerHTML = "edit_note";
            }
        };
    }

    let likeButtons = document.querySelectorAll("#like-button");

    if (likeButtons.length > 0)
        likeButtons.forEach((likeButton) => {
            likeButton.onclick = (e) => {
                button = e.target;
                fetch("/like", {
                    method: "POST",
                    body: JSON.stringify({
                        id: button.dataset.postId,
                    }),
                })
                    .then((response) => response.json())
                    .then((data) => {
                        if (button.innerHTML.trim() == "thumb_up") {
                            button.innerHTML = "thumb_down";
                        } else {
                            button.innerHTML = "thumb_up";
                        }
                        button.parentElement.children[1].innerHTML =
                            data["likes"];
                    });
            };
        });
});
