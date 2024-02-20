import SideNavIcon from "./sideNavIcon";
import { Home, Subscriptions, History } from "@mui/icons-material";
import { useRouter } from "next/navigation";

export default function SideNav(props) {
    const path = props.path;
    const router = useRouter();
    router.push(path + "?feed=" + newFeed);
    const changeFeed = (e) => {
        const newFeed = e.target.dataset.text;
        if (newFeed && props.feed != newFeed) {
            props.setFeed(newFeed);
            props.setVideos([]);
            router.push(path + "?feed=" + newFeed);
        }
    };

    return (
        <div className="fixed left-0 hidden pl-2 md:flex flex-col items-center">
            <SideNavIcon text="Home" onClick={changeFeed}>
                <Home></Home>
            </SideNavIcon>
            <SideNavIcon text="History" onClick={changeFeed}>
                <History></History>
            </SideNavIcon>
            <SideNavIcon text="Subscriptions" onClick={changeFeed}>
                <Subscriptions></Subscriptions>
            </SideNavIcon>
        </div>
    );
}
