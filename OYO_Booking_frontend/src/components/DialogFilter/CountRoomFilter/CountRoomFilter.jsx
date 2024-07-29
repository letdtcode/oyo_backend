import { useState } from "react";
import { t } from "i18next";
import "./CountRoomFilter.scss";

const DataCount = [
  {
    index: 1,
    number: 1,
  },
  {
    index: 2,
    number: 2,
  },
  {
    index: 3,
    number: 3,
  },
  {
    index: 4,
    number: 4,
  },
  {
    index: 5,
    number: 5,
  },
  {
    index: 6,
    number: 6,
  },
  {
    index: 7,
    number: 7,
  },
  {
    index: 8,
    number: 8,
  },
];
const CountRoomFilter = ({ name, data, setData }) => {
  const [idActive, setIdActive] = useState(data);
  const handleSetActive = (id) => {
    setData(id)
    setIdActive(id);
  };
  return (
    <div className="count-room__filter">
      <p className="title-count__filter">{name}</p>
      <div className="list-number__choose">
        <p
          className={idActive === 0 ? "item-first active" : "item-first"}
          onClick={() => handleSetActive(0)}
        >
          {t("label.any")}
        </p>
        {DataCount.map((count) => (
          <p
            className={
              idActive === count.index ? `item-chose active` : `item-chose`
            }
            key={count.index}
            onClick={() => handleSetActive(count.index)}
          >
            {count.number}
          </p>
        ))}
      </div>
    </div>
  );
};
export default CountRoomFilter;
