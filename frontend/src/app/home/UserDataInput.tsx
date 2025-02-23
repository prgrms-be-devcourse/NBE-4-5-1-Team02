"use client";

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
    <>
      <label htmlFor="email">email</label>
      <input
        id="email"
        value={emailStatus[0]}
        onChange={(e) => {
          e.preventDefault();
          emailStatus[1](e.target.value);
        }}
      ></input>
      <label htmlFor="address">주소</label>
      <input
        id="address"
        value={addressStatus[0]}
        onChange={(e) => {
          e.preventDefault();
          addressStatus[1](e.target.value);
        }}
      ></input>
      <label htmlFor="zipcode">우편번호</label>
      <input
        id="zipcode"
        value={zipCodeStatus[0]}
        onChange={(e) => {
          e.preventDefault();
          zipCodeStatus[1](e.target.value);
        }}
      />
    </>
  );
}
