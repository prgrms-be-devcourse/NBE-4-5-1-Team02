"use client";

import { useState } from "react";

interface SearchInputProps {
  onSearch: (keyword: string) => void;
}

export default function SearchInput({ onSearch }: SearchInputProps) {
  const [keyword, setKeyword] = useState<string>("");
  return (
    <>
      <input
        type="text"
        value={keyword}
        onChange={(e) => {
          e.preventDefault();
          setKeyword(e.target.value);
        }}
      ></input>
      <input
        type="button"
        value={"search"}
        onClick={(e) => {
          e.preventDefault();
          onSearch(keyword);
        }}
      />
    </>
  );
}
