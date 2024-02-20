import Link from "next/link";

export default function TopNavIcon({ children, className, href, onClick }) {
    const styles = `${
        className ? className + " " : ""
    }cursor-pointer rounded-full w-10 h-10 mr-5 hover:bg-gray-400/25 flex items-center justify-center`;

    if (href)
        return (
            <Link className={styles} href={href}>
                {children}
            </Link>
        );
    else
        return (
            <div onClick={onClick} className={styles}>
                {children}
            </div>
        );
}
