import { Clear, Upload } from "@mui/icons-material";
import { CircularProgress } from "@mui/material";
import InputText from "./inputText";
import Button from "./button";
import { upload } from "@/lib/utils";

export default function Overlay({
    hidden,
    toggleOverlay,
    currentStep,
    setCurrentStep,
}) {
    const handleVideoSelected = () => {
        setCurrentStep("thumbnail");
    };

    const handleThumbnailSelected = () => {
        setCurrentStep("details");
    };

    function handleUpload(formData) {
        setCurrentStep("uploading");
        upload(formData).then(() => {
            window.location.href = "/";
        });
    }

    return (
        <div
            className={`${hidden}fixed flex justify-center items-center h-screen w-screen bg-black/50`}
        >
            <div className="relative w-[80%] h-[80%] max-w-[800px] min-h-[350px] bg-neutral-800 rounded-md flex flex-col justify-center items-center">
                <div className="absolute top-0 left-0 w-full p-4 flex border-b border-neutral-700">
                    <div className="text-xl font-semibold">Upload Video</div>
                    <div className="grow flex items-center justify-end">
                        <button onClick={toggleOverlay}>
                            <Clear className="cursor-pointer text-neutral-400 hover:text-white" />
                        </button>
                    </div>
                </div>
                <form
                    className="flex flex-col items-center justify-center w-full"
                    action={handleUpload}
                >
                    <label
                        htmlFor={currentStep}
                        className={`${
                            currentStep == "details" ||
                            currentStep == "uploading"
                                ? "hidden "
                                : ""
                        }cursor-pointer rounded-full size-32 bg-neutral-900/50 hover:bg-neutral-900/75 flex justify-center items-center`}
                    >
                        <Upload className="size-20 text-neutral-500"></Upload>
                    </label>

                    <label
                        htmlFor={currentStep}
                        className={`${
                            currentStep == "details" ||
                            currentStep == "uploading"
                                ? "hidden "
                                : ""
                        }cursor-pointer text-black px-4 py-2 bg-blue-400 hover:bg-blue-500 font-semibold rounded mt-5`}
                    >
                        Select {currentStep}
                    </label>

                    <input
                        onChange={handleVideoSelected}
                        hidden
                        type="file"
                        name="video"
                        id="video"
                        accept="video/mp4"
                    />

                    <input
                        onChange={handleThumbnailSelected}
                        hidden
                        type="file"
                        name="thumbnail"
                        id="thumbnail"
                        accept="image/png, image/jpeg"
                    />

                    <InputText
                        className={currentStep == "details" ? "" : "hidden"}
                        type="text"
                        name="title"
                        id="title"
                        placeholder="Title"
                    />

                    <textarea
                        className={`${
                            currentStep == "details" ? "" : "hidden "
                        }mb-5 h-12 w-3/4 max-h-[450px] rounded-xl px-5 pt-2 text-white border border-stone-800 focus:outline-none focus:border-blue-600 bg-neutral-950`}
                        type="text"
                        name="description"
                        id="description"
                        placeholder="Description"
                    />

                    <Button
                        type="submit"
                        className={currentStep == "details" ? "" : "hidden "}
                        text="Upload"
                    />

                    <CircularProgress
                        className={`${
                            currentStep == "uploading" ? "" : "hidden "
                        }`}
                    ></CircularProgress>
                </form>
            </div>
        </div>
    );
}
