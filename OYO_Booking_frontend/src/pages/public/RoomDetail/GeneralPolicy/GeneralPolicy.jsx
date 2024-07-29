import './GeneralPolicy.scss';
import { t } from 'i18next';
import { showRefundPolicy } from '~/utils/showRefundPolicy';
export default function GeneralPolicy({ cancellationPolicy, cancellationFeeRate }) {
    return (
        <div className="general-policy">
            <div className="general-policy__content-item">
                <div className="general-policy__title">Chính sách chung</div>
                <p className="general-policy__content">Không cho phép hút thuốc</p>
                <p className="general-policy__content">Không cho phép thú cưng</p>
                <p className="general-policy__content">Không cho phép tổ chức tiệc / sự kiện</p>
            </div>
            <div className="general-policy__content-item">
                <div className="general-policy__title">Chính sách trẻ em</div>
                <p className="general-policy__content">Trẻ em từ 6 tuổi sẽ được xem như người lớn</p>
                <p className="general-policy__content">
                    Quý khách hàng vui lòng nhập đúng số lượng khách và tuổi để có giá chính xác.
                </p>
            </div>
            <div className="general-policy__content-item">
                <div className="general-policy__title">Chính sách hủy trả</div>
                <p className="general-policy__content">{showRefundPolicy({ cancellationPolicy, cancellationFeeRate })}</p>
            </div>
        </div>
    );
}
