export default function InputText(props) {
    return (
        <input
            className={`${
                props.className ? props.className + " " : ""
            }mb-5 h-12 w-60 rounded-xl px-5 text-white border border-stone-800 focus:outline-none focus:border-blue-600 bg-neutral-950`}
            type={props.type ? props.type : "text"}
            name={props.name}
            id={props.id}
            placeholder={props.placeholder}
        />
    );
}
