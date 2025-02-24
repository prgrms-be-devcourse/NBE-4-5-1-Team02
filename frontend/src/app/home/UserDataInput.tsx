"use client";

import { Input } from "@/components/ui/input";

export default function UserDataInput({
  emailStatus,
  addressStatus,
  zipCodeStatus,
}: {
  emailStatus: [string, React.Dispatch<React.SetStateAction<string>>];
  addressStatus: [string, React.Dispatch<React.SetStateAction<string>>];
  zipCodeStatus: [string, React.Dispatch<React.SetStateAction<string>>];
}) {
  return (
    <div className="h-[35%] my-3">
      <label htmlFor="email">
        <div className="w-[100%] py-1">
          <span className="text-lg font-semibold">email</span>
        </div>
      </label>
      <Input
        className="bg-white h-[2rem] mb-3"
        id="email"
        value={emailStatus[0]}
        onChange={(e) => {
          e.preventDefault();
          emailStatus[1](e.target.value);
        }}
      ></Input>
      <label htmlFor="address">
        <div className="w-[100%] py-1">
          <span className="text-lg font-semibold">주소</span>
        </div>
      </label>
      <Input
        id="address"
        className="bg-white h-[2rem] mb-3"
        value={addressStatus[0]}
        onChange={(e) => {
          e.preventDefault();
          addressStatus[1](e.target.value);
        }}
      ></Input>
      <label htmlFor="zipcode">
        <div className="w-[100%] py-1">
          <span className="text-lg font-semibold">우편번호</span>
        </div>
      </label>
      <Input
        id="zipcode"
        className="bg-white h-[2rem]"
        value={zipCodeStatus[0]}
        onChange={(e) => {
          e.preventDefault();
          zipCodeStatus[1](e.target.value);
        }}
      />
    </div>
  );
}
