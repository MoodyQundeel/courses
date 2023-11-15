addEventListener("DOMContentLoaded", () => {
    let followButton = document.querySelector("#follow-button");
    let currentProfile = document.querySelector("h1").innerText;
    let followerCount = document.querySelector("#follower-count");
    if (followButton) {
        followButton.onclick = () => {
            fetch("/follow", {
                method: "POST",
                body: JSON.stringify({
                    currentProfile: currentProfile,
                }),
            })
                .then((response) => response.json())
                .then((data) => {
                    followerCount.innerHTML = `Followers: ${data["followers"]}`;
                    if (followButton.innerHTML === "Follow") {
                        followButton.innerHTML = "UnFollow";
                        followButton.classList.remove("btn-success");
                        followButton.classList.add("btn-danger");
                    } else {
                        followButton.innerHTML = "Follow";
                        followButton.classList.remove("btn-danger");
                        followButton.classList.add("btn-success");
                    }
                });
        };
    }
});
