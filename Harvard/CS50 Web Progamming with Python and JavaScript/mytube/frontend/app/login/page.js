import { registerOrLogin } from "@/lib/utils";
import InputText from "@/components/inputText";
import Button from "@/components/button";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";

export default function Page() {
    if (cookies().get("username")) redirect("/");
    return (
        <form
            className="flex flex-col items-center justify-center w-screen h-screen"
            action={registerOrLogin}
        >
            <InputText name="username" id="username" placeholder="Username" />
            <InputText
                type="password"
                name="password"
                id="password"
                placeholder="Password"
            />
            <Button type="submit" text="Login" />
        </form>
    );
}
