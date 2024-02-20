import Link from "next/link";

export default function SideNavIcon({
    children,
    className,
    text,
    href,
    onClick,
}) {
    const styles = `${
        className ? className + " " : ""
    }cursor-pointer rounded-lg w-16 h-20 hover:bg-gray-400/25 flex flex-col items-center justify-center`;
    if (href)
        return (
            <Link className={styles} href={href}>
                {children}
                <div className="text-[9px] mt-2">{text}</div>
            </Link>
        );
    else
        return (
            <div className={styles} onClick={onClick} data-text={text}>
                {children}
                <div className="text-[9px] mt-2">{text}</div>
            </div>
        );
}
