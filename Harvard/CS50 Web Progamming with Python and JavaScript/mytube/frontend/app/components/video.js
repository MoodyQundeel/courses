import Image from "next/image";
import Link from "next/link";
export default function Video(props) {
    return (
        <div className="col-span-1 mb-10 mx-2">
            <Link href={`/watch?id=${props.video}`}>
                <div>
                    <Image
                        src={props.thumbnail}
                        width={500}
                        height={300}
                        alt="Video thumbnail"
                        className="rounded-xl mb-2 w-full h-auto"
                    ></Image>
                </div>

                <div className="mb-1">{props.title}</div>
            </Link>
            <div className="text-xs text-[#AAAAAA]">
                <div className="mb-1">{props.user}</div>
            </div>
        </div>
    );
}
