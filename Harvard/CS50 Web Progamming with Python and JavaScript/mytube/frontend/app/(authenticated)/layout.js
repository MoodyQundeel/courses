import TopNav from "@/components/navigation/topNav";

export default function RootLayout({ children }) {
    return (
        <>
            <TopNav />
            <div className="max-w-screen flex pt-20 px-10 md:pl-20">
                {children}
            </div>
        </>
    );
}
