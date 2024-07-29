import './NecessaryInformation.scss';

export default function NecessaryInformation() {
    return (
        <div className="necessary-information">
            <div className="necessary-information__title">
                <h2>Thông tin cần biết</h2>
                <div className="necessary-information__content">
                    <div className="necessary-information__content-item">
                        <p className='title'>Trả phòng phòng</p>
                        <p className='time'>Từ 14:00</p>
                    </div>
                    <div className=''>

                    </div>
                    <div className="necessary-information__content-item">
                        <p className='title'>Nhận phòng</p>
                        <p className='time'>Trước 12:00</p>
                    </div>
                </div>
            </div>
        </div>
    );
}
