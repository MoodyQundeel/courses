"use server";

import { redirect } from "next/navigation";
import { cookies } from "next/headers";

export async function registerOrLogin(formData) {
    formData.set("username", formData.get("username").trim());
    formData.set("password", formData.get("password").trim());

    const response = await fetch("http://localhost:8000/api/register", {
        method: "POST",
        body: formData,
    });

    const json = await response.json();
    const message = json["message"];
    console.log(message);

    if (message === "User registered." || message === "User logged in.") {
        cookies().set("username", formData.get("username"), {
            maxAge: 60 * 60 * 24 * 7,
        });
        redirect("/", "push");
    }

    redirect("/login");
}

export async function logout() {
    cookies().delete("username");
}

export async function upload(formData) {
    const username = cookies().get("username").value;
    formData.set("username", username);
    fetch("http://127.0.0.1:8000/api/upload", {
        method: "post",
        body: formData,
        enctype: "multipart/form-data",
    });
}
