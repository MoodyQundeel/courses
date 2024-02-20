export default function Button(props) {
    return (
        <button
            onClick={props.onClick}
            type={props.type}
            className={`${
                props.className ? props.className + " " : ""
            }cursor-pointer text-black px-4 py-2 bg-blue-400 hover:bg-blue-500 font-semibold rounded mt-3`}
        >
            {props.text}
        </button>
    );
}
