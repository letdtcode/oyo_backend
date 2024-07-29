import './FramePage.scss';
import { useSelector } from 'react-redux';
import NavBar from './NavBar/NavBar';
import NavbarOwner from '../NavbarOwner/NavbarOwner';
import ViewImage from '~/components/ViewImage/ViewImage';
import ChatBox from '../ChatBox/ChatBox';
import LoadingDialog from '~/components/LoadingDialog/LoadingDialog';
import { useEffect } from 'react';
import BannerOwner from '../BannerOwner/BannerOwner';

import Footer from '~/components/Footer/Footer';
export default function FramePage({ ownerPage = false, children, bannerData }) {
    const viewImages = useSelector((state) => state.global.viewImages);
    const chatbox = useSelector((state) => state.global.chatbox);
    const loading = useSelector((state) => state.global.loading);

    useEffect(() => {
        window.scrollTo(0, 0);
    }, []);

    return (
        <div className="background__frame-page">
            {ownerPage ? (
                <>
                    <NavbarOwner />
                    {bannerData !== undefined && <BannerOwner {...bannerData} />}

                    <div className="body-page owner">{children}</div>
                </>
            ) : (
                <>
                    <NavBar />
                    <div className="body-page">{children}</div>
                </>
            )}

            {viewImages && <ViewImage viewImages={viewImages} />}
            {chatbox.open === true && <ChatBox />}
            {loading && <LoadingDialog open={loading} />}
            <Footer />
        </div>
    );
}
