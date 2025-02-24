"use client";

import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "@/components/ui/command"
import { Search } from "lucide-react"
import { useState, useEffect } from "react"

interface SearchInputProps {
  onSearch: (keyword: string) => void;
}

export default function SearchInput({ onSearch }: SearchInputProps) {
  const [isOpen, setIsOpen] = useState(false);
  const [inputValue, setInputValue] = useState("");
  const [recentSearches, setRecentSearches] = useState<string[]>([]);
  
  // 예시: 인기 검색어
  const popularSearches = ["맥북", "아이패드", "에어팟"];

  // 컴포넌트 마운트 시 sessionStorage에서 최근 검색어 불러오기
  useEffect(() => {
    const savedSearches = sessionStorage.getItem('recentSearches');
    if (savedSearches) {
      setRecentSearches(JSON.parse(savedSearches));
    }
  }, []);

  // 새로운 검색어를 최근 검색어에 추가하는 함수
  const addToRecentSearches = (keyword: string) => {
    if (!keyword.trim()) return;
    
    const newRecentSearches = [
      keyword,
      ...recentSearches.filter(item => item !== keyword)
    ].slice(0, 5);
    
    setRecentSearches(newRecentSearches);
    sessionStorage.setItem('recentSearches', JSON.stringify(newRecentSearches));
  };

  // 검색 실행 함수
  const handleSearch = (keyword: string) => {
    setInputValue(keyword);
    onSearch(keyword);
    addToRecentSearches(keyword);
  };

  // 입력값 변경 시 호출되는 함수
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setInputValue(value);
    onSearch(value);
    
    // 엔터키를 누르거나 검색어가 의미있는 길이일 때 최근 검색어에 추가
    if (value.trim().length >= 2) {  // 2글자 이상일 때
      addToRecentSearches(value);
    }
  };

  // 최근 검색어 삭제 함수
  const removeRecentSearch = (keyword: string, e: React.MouseEvent) => {
    e.stopPropagation();
    const newRecentSearches = recentSearches.filter(item => item !== keyword);
    setRecentSearches(newRecentSearches);
    sessionStorage.setItem('recentSearches', JSON.stringify(newRecentSearches));
  };

  return (
    <div className="flex w-[70%] justify-end items-center relative">
      <Command className="rounded-lg border shadow-md w-full max-w-lg">
        <div className="flex items-center px-3">
          <Search className="mr-2 h-4 w-4 shrink-0 opacity-50" />
          <input
            className="flex h-9 w-full rounded-md bg-transparent py-3 text-sm outline-none placeholder:text-muted-foreground disabled:cursor-not-allowed disabled:opacity-50"
            placeholder="상품 검색..."
            value={inputValue}
            onChange={handleInputChange}
            onKeyDown={(e) => {
              if (e.key === 'Enter' && inputValue.trim()) {
                addToRecentSearches(inputValue);
              }
            }}
            onFocus={() => setIsOpen(true)}
            onBlur={() => {
              setTimeout(() => setIsOpen(false), 200)
            }}
          />
        </div>
        {isOpen && (
          <div className="relative">
            <div className="absolute top-0 left-0 w-full bg-white border rounded-b-lg shadow-lg z-50">
              {inputValue.length === 0 ? (
                <>
                  <div className="p-2 text-sm font-medium text-muted-foreground">최근 검색어</div>
                  {recentSearches.map((search) => (
                    <div
                      key={search}
                      className="flex items-center px-2 py-1.5 text-sm cursor-pointer hover:bg-accent hover:text-accent-foreground"
                      onClick={(e) => {
                        setInputValue(search);
                        handleSearch(search);
                        removeRecentSearch(search, e);
                      }}
                    >
                      <Search className="mr-2 h-4 w-4" />
                      {search}
                    </div>
                  ))}
                  <div className="p-2 text-sm font-medium text-muted-foreground">인기 검색어</div>
                  {popularSearches.map((search) => (
                    <div
                      key={search}
                      className="flex items-center px-2 py-1.5 text-sm cursor-pointer hover:bg-accent hover:text-accent-foreground"
                      onClick={(e) => {
                        setInputValue(search);
                        handleSearch(search);
                      }}
                    >
                      <Search className="mr-2 h-4 w-4" />
                      {search}
                    </div>
                  ))}
                </>
              ) : (
                <div className="p-2 text-sm text-center text-muted-foreground">
                  검색 결과가 없습니다.
                </div>
              )}
            </div>
          </div>
        )}
      </Command>
    </div>
  );
}
