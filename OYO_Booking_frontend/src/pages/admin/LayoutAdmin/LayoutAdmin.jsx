import Sidebar from '~/components/Admin/Sidebar/Sidebar';
import RoutesAdmin from '~/routes/RouterAdmin';
import './LayoutAdmin.scss';
import NavbarAdmin from '~/components/Admin/NavbarAdmin/NavbarAdmin';
import './boxicons-2.0.7/css/boxicons.min.css';
const LayoutAdmin = () => {
    return (
        <div className="layout-admin">
            <div className={`layout`}>
                <Sidebar />
                <div className="layout__content">
                    <NavbarAdmin />
                    <div className="layout__content-main">
                        <RoutesAdmin />
                    </div>
                </div>
            </div>
        </div>
    );
};
export default LayoutAdmin;
