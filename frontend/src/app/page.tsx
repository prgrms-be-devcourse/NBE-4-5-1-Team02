import Image from "next/image";

export default function Home() {
  return (
    <div className="w-screen h-screen flex ">
      <div className="w-[75%] h-screen p-[4rem]">
        <div className=" flex-grow h-[100%] bg-blue-300">
          사진
        </div>
      </div>
      <div className="w-[35%] h-screen bg-gray-400"></div>
    </div>
  );
}
