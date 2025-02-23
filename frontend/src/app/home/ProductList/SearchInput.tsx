"use client";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { useState } from "react";

interface SearchInputProps {
  onSearch: (keyword: string) => void;
}

export default function SearchInput({ onSearch }: SearchInputProps) {
  const [keyword, setKeyword] = useState<string>("");
  return (
    <div className="flex w-[70%] justify-end">
      <Input
        className="w-[75%] mx-4 border-0 border-b-4"
        type="text"
        value={keyword}
        onChange={(e) => {
          e.preventDefault();
          setKeyword(e.target.value);
        }}
      ></Input>
      <Button
        value={"search"}
        onClick={(e) => {
          e.preventDefault();
          onSearch(keyword);
        }}
      >
        search
      </Button>
    </div>
  );
}
