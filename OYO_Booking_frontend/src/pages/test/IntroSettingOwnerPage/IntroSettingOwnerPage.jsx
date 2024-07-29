import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import FramePage from "~/components/FramePage/FramePage";
import Logo from '~/assets/svg/logo.svg';
import './IntroSettingOwnerPage.scss';
import { t } from 'i18next';

const IntroSettingOwnerPage = () => {
    const userLogin = useSelector((state) => state.user);
    const navigate = useNavigate();

    const backHome = () => {
        navigate('/host');
    };

    const nextPage = () => {
        navigate('/stepsetupowner');
    };

    useEffect(() => {
        if (userLogin.current === undefined) {
            navigate('/signin');
        }
    }, [userLogin, navigate]);

    return (
            <div className="introsettingowner-page">
                <div className="row">
                    <div className="col l-6">
                        {/* <div className="sidebar__logo" onClick={backHome}>
                            <img src={Logo} alt="company logo" className="logo-bg" />
                        </div> */}
                    </div>
                    <div className="col l-6">
                        <div className="text-thanks">
                            <h1>{t('setupOwner.title')}</h1>
                            <p>{t('setupOwner.commit')}</p>
                            <h2>-- OYO --</h2>
                        </div>
                        <p className="btn-out" onClick={backHome}>
                            {t('common.close')}
                        </p>
                        <button type="submit" onClick={nextPage}>
                            {t('common.continue')}
                        </button>
                    </div>
                </div>
            </div>
    );
};

export default IntroSettingOwnerPage;
