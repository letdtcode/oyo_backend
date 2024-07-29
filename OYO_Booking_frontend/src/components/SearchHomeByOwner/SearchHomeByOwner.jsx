import { useState } from 'react';
import SearchIcon from '@mui/icons-material/Search';
import removeVietnameseTones from '../../utils/convertStringVietNamese';
import './SearchHomeByOwner.scss';

function SearchHomeByOwner({ placeholder, handleSearchByHomeName }) {
    const [wordEntered, setWordEntered] = useState('');
    const [textSearch, setTextSearch] = useState('');

    const handleFilter = (event) => {
        const searchWord = event.target.value;
        setWordEntered(searchWord);

        const convertStringToEnglish = removeVietnameseTones(searchWord);
        const text = convertStringToEnglish.replace(' ', '%20');

        setTextSearch(text);
    };

    const handleSearch = (event) => {
        if (event.key === 'Enter') {
            handleSearchByHomeName(textSearch);
        }
    };

    return (
        <div className="search-home-by-owner">
            <div className="searchInputs-home">
                <div className="searchIcon" onClick={handleSearch}>
                    <SearchIcon />
                </div>

                <input
                    className="search-owner"
                    type="text"
                    placeholder={placeholder}
                    value={wordEntered}
                    onChange={handleFilter}
                    onKeyDown={handleSearch}
                />
            </div>
        </div>
    );
}

export default SearchHomeByOwner;
