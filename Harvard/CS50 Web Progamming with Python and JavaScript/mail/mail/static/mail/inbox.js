document.addEventListener("DOMContentLoaded", function () {
    // Use buttons to toggle between views
    document
        .querySelector("#inbox")
        .addEventListener("click", () => load_mailbox("inbox"));
    document
        .querySelector("#sent")
        .addEventListener("click", () => load_mailbox("sent"));
    document
        .querySelector("#archived")
        .addEventListener("click", () => load_mailbox("archive"));
    document.querySelector("#compose").addEventListener("click", compose_email);

    // By default, load the inbox
    load_mailbox("inbox");

    const compose_form = document.querySelector("#compose-form");

    compose_form.addEventListener("submit", (e) => {
        e.preventDefault();
        let recipients = document.querySelector("#compose-recipients").value;
        let subject = document.querySelector("#compose-subject").value;
        let body = document.querySelector("#compose-body").value;
        fetch("/emails", {
            method: "POST",
            body: JSON.stringify({
                recipients: recipients,
                subject: subject,
                body: body,
            }),
        })
            .then((response) => response.json())
            .then((result) => {
                console.log(result);
            });
        load_mailbox("sent");
    });
});

function compose_email() {
    // Show compose view and hide other views
    document.querySelector("#emails-view").style.display = "none";
    document.querySelector("#email-view").style.display = "none";
    document.querySelector("#compose-view").style.display = "block";

    // Clear out composition fields
    document.querySelector("#compose-recipients").value = "";
    document.querySelector("#compose-subject").value = "";
    document.querySelector("#compose-body").value = "";
}

function reply(recipient, subject, body) {
    // Show compose view and hide other views
    document.querySelector("#emails-view").style.display = "none";
    document.querySelector("#email-view").style.display = "none";
    document.querySelector("#compose-view").style.display = "block";

    // Clear out composition fields
    document.querySelector("#compose-recipients").value = recipient;
    document.querySelector("#compose-subject").value = subject;
    document.querySelector("#compose-body").value = body;
}

function load_email(id, type) {
    document.querySelector("#emails-view").style.display = "none";
    document.querySelector("#compose-view").style.display = "none";
    document.querySelector("#email-view").style.display = "block";

    fetch(`emails/${id}`)
        .then((response) => response.json())
        .then((email) => {
            document.querySelector("#email-view").innerHTML = `
              <div class="d-flex" id="sender"> <h5 class="mr-2"> From: </h5> ${
                  email.sender
              } </div>
              <div class="d-flex" id="sender"> <h5 class="mr-2"> To: </h5> ${
                  email.recipients
              } </div>
              <div class="d-flex" id="subject"> <h5 class="mr-2"> Subject: </h5> ${
                  email.subject
              } </div>
              <div class="d-flex" id="timestamp"> <h5 class="mr-2"> Time: </h5> ${
                  email.timestamp
              } </div>
              <div class="d-flex">
                <button class="mr-2 btn btn-outline-primary" id="reply-button">Reply</button>
                ${
                    type == "inbox"
                        ? `<button class="btn btn-outline-primary" id="archive-button">Archive</button>`
                        : ""
                }
                ${
                    type == "archive"
                        ? `<button class="btn btn-outline-primary" id="unarchive-button">Unarchive</button>`
                        : ""
                }
              </div>
              <hr>
              <div id="body"> ${email.body} </div>
            `;
            return email;
        })
        .then((email) => {
            if (type != "archive") {
                document
                    .querySelector("#archive-button")
                    .addEventListener("click", () => {
                        fetch(`emails/${id}`, {
                            method: "PUT",
                            body: JSON.stringify({
                                archived: true,
                            }),
                        });
                        load_mailbox("inbox");
                    });
            } else {
                document
                    .querySelector("#unarchive-button")
                    .addEventListener("click", () => {
                        fetch(`emails/${id}`, {
                            method: "PUT",
                            body: JSON.stringify({
                                archived: false,
                            }),
                        });
                        load_mailbox("inbox");
                    });
            }
            document
                .querySelector("#reply-button")
                .addEventListener("click", () => {
                    let recipient = email.sender;
                    let subject = `Re: ${email.subject}`;
                    let body = `On ${email.timestamp} ${email.sender} wrote:\n${email.body}`;
                    reply(recipient, subject, body);
                });
        });

    fetch(`emails/${id}`, {
        method: "PUT",
        body: JSON.stringify({
            read: true,
        }),
    });
}

function load_mailbox(mailbox) {
    // Show the mailbox and hide other views
    document.querySelector("#emails-view").style.display = "block";
    document.querySelector("#email-view").style.display = "none";
    document.querySelector("#compose-view").style.display = "none";

    // Show the mailbox name
    document.querySelector("#emails-view").innerHTML = `<h3>${
        mailbox.charAt(0).toUpperCase() + mailbox.slice(1)
    }</h3>`;

    fetch(`/emails/${mailbox}`)
        .then((response) => response.json())
        .then((emails) => {
            emails.forEach((email) => {
                const newEmail = document.createElement("div");
                newEmail.classList.add("email", "mt-3");
                if (email.read) newEmail.classList.add("read");
                newEmail.innerHTML = `
                    <div class="email-sender"> ${email.sender} </div>
                    <div class="email-subject"> ${email.subject} </div>
                    <div class="email-time"> ${email.timestamp} </div>
                `;
                newEmail.addEventListener("click", function () {
                    load_email(email.id, mailbox);
                });
                document.querySelector("#emails-view").append(newEmail);
            });
        });
}
