import { cookies } from "next/headers";
import { NextResponse } from "next/server";

export function middleware(request) {
    const currentUser = cookies().get("username");
    if (!currentUser && request.url != "/login") {
        return NextResponse.redirect(new URL("/login", request.url));
    }
}

export const config = {
    matcher: ["/:path"],
};
