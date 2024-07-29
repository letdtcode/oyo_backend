import './ListImageInSetting.scss';

const ListImageInSetting = (props) => {
    return (
        <div className="list-image-in-setting">
            {props?.listImage?.map((img, index) => (
                <div className="list-image-in-setting__item" key={index}>
                    <img src={`${img}`} alt="room_hot" className="list-image-in-setting__image" />
                </div>
            ))}
        </div>
    );
};

export default ListImageInSetting;
