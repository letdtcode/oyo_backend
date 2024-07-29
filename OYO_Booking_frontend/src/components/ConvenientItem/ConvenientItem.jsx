import './ConvenientItem.scss';

import CheckButton from '../CheckButton/CheckButton';

const ConvenientItem = (props) => {
    return (
        <div className="container__setting__convenien" key={props.key}>
            <div className="title-item">
                <h1> {props?.name} </h1>
            </div>
            <div className="container__convenienItem">
                {props.dataConveni?.map((item, index) => (
                    <div key={index} className="item">
                        <CheckButton
                            active={props.data.includes(item.facilityCode)}
                            code={item.facilityCode}
                            data={props.data}
                            setData={props.setData}
                        />
                        <img src={item.imageUrl} />
                        <p>{item.facilityName}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default ConvenientItem;
