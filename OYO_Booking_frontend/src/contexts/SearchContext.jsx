import { createContext, useState } from 'react';

const SearchContext = createContext(null);

const SearchProvider = ({ children }) => {
    const [searchText, setSearchText] = useState('');
    const [hanldSearch, setHanldSearch] = useState(false);

    const value = {
        searchText,
        setSearchText,
        hanldSearch,
        setHanldSearch
    };

    return <SearchContext.Provider value={value}>{children}</SearchContext.Provider>;
};


export { SearchContext, SearchProvider };