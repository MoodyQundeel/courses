"use client";

import { useSearchParams } from "next/navigation";
import { useEffect } from "react";
import { useState } from "react";

export default function Page() {
    const params = useSearchParams();
    const id = params.get("id");

    async function fetchVideoURL(id) {
        const response = await fetch("http://localhost:8000/api/view", {
            method: "POST",
            credentials: "include",
            "Content-Type": "application/json",
            body: JSON.stringify({
                id: id,
            }),
        });
        const json = await response.json();
        return json["video"];
    }

    const [video, setVideo] = useState();

    useEffect(() => {
        fetchVideoURL(id).then((url) => {
            setVideo(
                <video
                    src={`http://localhost:8000/api${url}`}
                    controls
                    type="video/mp4"
                    className="rounded-xl"
                ></video>
            );
        });
    }, [id]);

    return (
        <div className="grid-cols-6 w-screen px-10 md:px-32">
            <div className="col-span-6 rounded">{video}</div>
        </div>
    );
}
