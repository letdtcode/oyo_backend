import { useState, useEffect } from 'react';
import TableEmpty from '../../assets/img/empty.png';
import './Table.scss';

const Table = (props) => {
    const initDataShow = props.limit && props.bodyData ? props.bodyData.slice(0, Number(props.limit)) : props.bodyData;
    // initDataShow
    const [dataShow, setDataShow] = useState(initDataShow);
    const [currPage, setCurrPage] = useState(0);
    const [initNum, setInitNum] = useState(0);

    useEffect(() => {
        setDataShow(initDataShow);
    }, [props.bodyData]);

    let pages = 1;
    let range = [];

    if (props.limit !== undefined) {
        let page = Math.floor(props?.bodyData?.length / Number(props.limit));
        pages = props?.bodyData?.length % Number(props.limit) === 0 ? page : page + 1;
        for (let i = 0; i < pages; ++i) {
            range.push(i);
        }
    }

    const selectPage = (page) => {
        const start = Number(props.limit) * page;
        const end = start + Number(props.limit);
        setDataShow(props.bodyData.slice(start, end));
        setCurrPage(page);
        setInitNum(page * Number(props.limit));
    };
    return (
        <div>
            <div className="table-wrapper">
                <table>
                    {props.headData && props.renderHead ? (
                        <thead>
                            <tr>{props.headData.map((item, index) => props.renderHead(item, index))}</tr>
                        </thead>
                    ) : null}
                    {props?.bodyData && props?.renderBody && dataShow.length !== 0 ? (
                        <tbody>{dataShow?.map((item, index) => props.renderBody(item, index, initNum))}</tbody>
                    ) : (
                        <tbody>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <div className="table-empty">
                                <img src={TableEmpty} alt="icon-empty" />
                            </div>
                        </tbody>
                    )}
                </table>
            </div>
            {pages > 1 ? (
                <div className="table__pagination">
                    {range.map((item, index) => (
                        <div
                            key={index}
                            className={`table__pagination-item ${currPage === index ? 'active' : ''}`}
                            onClick={() => selectPage(index)}
                        >
                            {item + 1}
                        </div>
                    ))}
                </div>
            ) : null}
        </div>
    );
};

export default Table;
