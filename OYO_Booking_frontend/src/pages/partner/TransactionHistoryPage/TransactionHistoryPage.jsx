import NavbarOwner from '~/components/NavbarOwner/NavbarOwner';
import TableHistoryOwner from './TableHistoryOwner';
import Footer from '~/components/Footer/Footer';
import './TransactionHistoryPage.scss';
import FramePage from '~/components/FramePage/FramePage';

const TransactionHistoryPage = () => {
    const bannerData = {
        title: 'Quản lý lịch sử giao dịch',
        subtitle: 'Kiểm tra và quản lý thông tin lịch sử giao dịch một cách dễ dàng.',
        imgSrc: 'https://quickbooks.intuit.com/oidam/intuit/sbseg/en_us/Blog/Graphic/how-to-write-a-business-plan-header-image-us-en.png'  
    };
    return (
        <FramePage ownerPage={true} bannerData={bannerData}>
            <div className="transaction-history-page">
                <h2 className="title-transaction-history">Lịch sử giao dịch</h2>
                <div className="tab-content">
                    <TableHistoryOwner />
                </div>
            </div>
        </FramePage>
    );
};

export default TransactionHistoryPage;
