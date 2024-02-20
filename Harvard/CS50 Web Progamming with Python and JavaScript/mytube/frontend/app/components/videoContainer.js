"use client";

import Video from "./video";
import { useEffect, useState } from "react";
import { useSearchParams, usePathname } from "next/navigation";
import SideNav from "./navigation/sideNav";

export default function VideoContainer() {
    const params = useSearchParams();
    const [feed, setFeed] = useState("Home");
    const path = usePathname();

    let page = 1;
    let hasNext = true;

    const [videos, setVideos] = useState([]);

    const fetchVideos = async () => {
        const response = await fetch(
            `http://localhost:8000/api/videos?page=${page}&feed=${feed}`,
            { credentials: "include" }
        );
        const json = await response.json();
        const parsed = await JSON.parse(json);
        hasNext = parsed.shift().hasNext;
        page = page + 1;

        const newVideos = parsed.map((video) => (
            <Video
                key={video.id}
                user={video.user}
                title={video.title}
                video={video.id}
                thumbnail={`http://localhost:8000/api${video.thumbnail}`}
            />
        ));
        setVideos((videos) => [...videos, ...newVideos]);
    };

    useEffect(() => {
        fetchVideos()
            .then(() => fetchVideos())
            .then(
                () =>
                    (window.onscroll = () => {
                        if (
                            window.innerHeight + window.scrollY >=
                            document.body.offsetHeight
                        ) {
                            console.log(hasNext);
                            if (hasNext) {
                                fetchVideos();
                            }
                        }
                    })
            );
    }, []);

    return (
        <>
            <SideNav
                path={path}
                params={params}
                feed={feed}
                setFeed={setFeed}
                setVideos={setVideos}
                fetchVideos={fetchVideos}
            />
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
                {videos}
            </div>
        </>
    );
}
