import { Link } from 'react-router-dom';
import React, { useEffect, useState } from 'react';
import { Route, Routes , useParams} from 'react-router-dom';
import EditInfo from './EditAccount/EditAccount';
import PasswordSecurity from './passwordSecurity/passwordSecurity';
import { useSelector } from 'react-redux';
import CardInfo from './CardInfo/CardInfo';
import FramePage from '~/components/FramePage/FramePage';
import './InfoUserPage.scss';
import { t } from 'i18next';

export default function InfoUserPage() {
    const user = useSelector((state) => state.user.current);
    const params = useParams();
    const [selectedItem, setSelectedItem] = useState(params['*']);
    useEffect(() => {
        setSelectedItem(params['*'])
    }, [params['*']])
    return (
        <FramePage>
            <div className="content info-user">
                <div className="row">
                    <CardInfo />
                    <div className="col l-9">
                        <div className="col">
                            <h1 className="heading">{t('common.setting')} </h1>
                            <div className="options">
                                <Link
                                    to="/account/profile"
                                    className={`option ${selectedItem === 'profile' ? 'selected-option' : ''}`}
                                >
                                    {t('navbar.profile')}
                                </Link>
                                <Link
                                    to="/account/password&Security"
                                    className={`option ${
                                        selectedItem === 'password&Security' ? 'selected-option' : ''
                                    }`}
                                >
                                    {t('navbar.passwordAndSecurity')}
                                </Link>
                                {/* <Link
                                    to="/account/payment"
                                    className={`option ${selectedItem === 'billing' ? 'selected-option' : ''}`}
                                    onClick={() => handleItemClick('billing')}
                                >
                                    {t('navbar.billingInformation')}
                                </Link> */}
                            </div>
                            <div className="paper card-content">
                                <Routes>
                                    <Route path="/profile" element={<EditInfo />} />
                                    <Route path="/password&Security" element={<PasswordSecurity />} />
                                </Routes>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </FramePage>
    );
}
