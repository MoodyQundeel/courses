"use client";

import Image from "next/image";
import youtubeLogo from "/public/yt-logo.png";
import TopNavIcon from "./topNavIcon";
import { Menu, Videocam, AccountCircle, Search } from "@mui/icons-material";
import Link from "next/link";
import Overlay from "../overlay";
import { useState } from "react";
import { logout } from "@/lib/utils";

export default function TopNav() {
    const [currentStep, setCurrentStep] = useState("video");
    const [hidden, setHidden] = useState("hidden ");

    const toggleOverlay = () => {
        hidden != "" ? setHidden("") : setHidden("hidden ");
    };

    const handleLogout = async () => {
        await logout();
        window.location.href = "/login";
    };

    return (
        <>
            <Overlay
                hidden={hidden}
                toggleOverlay={toggleOverlay}
                currentStep={currentStep}
                setCurrentStep={setCurrentStep}
            />

            <nav className="fixed flex w-screen h-16 items-center px-5 mb-5 bg-black">
                <div className="flex items-center">
                    <TopNavIcon>
                        <Menu></Menu>
                    </TopNavIcon>
                    <Link href="/" className="mr-5 min-w-40">
                        <Image
                            src={youtubeLogo}
                            alt="Youtube Logo"
                            className="w-[100px]"
                        ></Image>
                    </Link>
                </div>
                <div className="w-full hidden sm:flex shrink justify-center">
                    <input
                        className="w-2/3 h-10 rounded-3xl px-5 text-white placeholder:text-neutral-400/70 border border-stone-800 focus:outline-none focus:border-blue-600 bg-neutral-800/30"
                        type="text"
                        name="Search"
                        id="Search"
                        placeholder="Search"
                    />
                </div>
                <div className="w-full sm:w-40 sm:min-w-40 flex items-center justify-end">
                    <TopNavIcon className="sm:hidden">
                        <Search></Search>
                    </TopNavIcon>
                    <TopNavIcon onClick={toggleOverlay}>
                        <Videocam></Videocam>
                    </TopNavIcon>
                    <TopNavIcon onClick={handleLogout}>
                        <AccountCircle></AccountCircle>
                    </TopNavIcon>
                </div>
            </nav>
        </>
    );
}
