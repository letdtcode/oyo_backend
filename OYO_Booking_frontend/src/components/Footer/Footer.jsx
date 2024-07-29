import { Link } from 'react-router-dom';
import Logo from '~/assets/logo.svg';
import twitterIcon from '~/assets/imageMaster/twitter.png';
import youtubeicon from '~/assets/imageMaster/youtube-play--v2.png';
import facebookIcon from '~/assets/imageMaster/facebook-footer.png';
import instagramIcon from '~/assets/imageMaster/instagram-new.png';
import './Footer.scss';
import { t } from 'i18next';

export default function Footer() {
    return (
        <div className="footer">
            <div className="footer__inner">
                <div className="grid wide">
                    <div className="row" style={{ marginTop: 10 }}>
                        <div className="col l-6 m-12 c-12">
                            <img src={Logo} alt="" className="footer__logo" />
                            <div className="footer__intro">
                                <span className="footer__intro-company">Công ty cổ phần du lịch Việt Nam VNTravel</span>
                                <span>Tổng đài chăm sóc: 1900 2083</span>
                                <span>Email: hotro@mytour.vn</span>
                                <span>Văn phòng Hà Nội: Tầng 11, Tòa Peakview, 36 Hoàng Cầu, Đống Đa</span>
                                <span>Văn phòng HCM: Tầng 3, Tòa nhà ACM, 96 Cao Thắng, Quận 3</span>
                            </div>

                            <div className="footer__intro-contact">
                                <img
                                    src="https://staticproxy.mytourcdn.com/0x0,q90/themes/images/logo-dathongbao-bocongthuong-w165.png"
                                    alt=""
                                />
                                <img
                                    src="https://staticproxy.mytourcdn.com/0x0,q90/themes/images/logo-congthuong-w165.png"
                                    alt=""
                                />
                                <img
                                    src="https://gcs.tripi.vn/tripi-assets/mytour/icons/logo_IATA.png"
                                    alt=""
                                    style={{ height: 65 }}
                                ></img>
                            </div>
                        </div>

                        <div className=" col l-6 m-8 c-12">
                            <div className="row">
                                <div className="col l-4 m-4 c-12">
                                    <ul className="footer__list">
                                        <h3 className="footer__list-heading">{t('footer.contact_1')}</h3>
                                        <li className="footer__item">
                                            <i className="footer__item-icon "></i>
                                            <Link to="/" className="footer__item-link">
                                                {t('footer.contact_link_1')}
                                            </Link>
                                        </li>
                                        <li className="footer__item">
                                            <i className="footer__item-icon "></i>
                                            <Link to="/contact" className="footer__item-link">
                                                {t('footer.contact_link_2')}
                                            </Link>
                                        </li>
                                        <li className="footer__item">
                                            <i className="footer__item-icon "></i>
                                            <Link to="#" className="footer__item-link">
                                                {t('footer.contact_link_3')}
                                            </Link>
                                        </li>
                                        <li className="footer__item">
                                            <i className="footer__item-icon "></i>
                                            <Link to="#" className="footer__item-link">
                                                {t('footer.contact_link_4')}
                                            </Link>
                                        </li>
                                        <li className="footer__item">
                                            <i className="footer__item-icon "></i>
                                            <Link to="#" className="footer__item-link">
                                                {t('footer.contact_link_5')}
                                            </Link>
                                        </li>
                                    </ul>
                                </div>
                                <div className="col l-4 m-4 c-12">
                                    <ul className="footer__list">
                                        <h3 className="footer__list-heading">{t('footer.contact_2')}</h3>
                                        <li className="footer__item">
                                            <i className="footer__item-icon "></i>
                                            <Link to="https://www.booking.com/" className="footer__item-link">
                                                {t('footer.contact_link_6')}
                                            </Link>
                                        </li>
                                        <li className="footer__item">
                                            <i className="footer__item-icon "></i>
                                            <Link to="https://www.agoda.com/vi-vn/" className="footer__item-link">
                                                {t('footer.contact_link_7')}
                                            </Link>
                                        </li>
                                        <li className="footer__item">
                                            <i className="footer__item-icon "></i>
                                            <Link to="https://www.traveloka.com/vi-vn/" className="footer__item-link">
                                                {t('footer.contact_link_8')}
                                            </Link>
                                        </li>
                                        <li className="footer__item">
                                            <i className="footer__item-icon "></i>
                                            <Link to="https://www.trivago.vn/vi" className="footer__item-link">
                                                {t('footer.contact_link_9')}
                                            </Link>
                                        </li>
                                        <li className="footer__item">
                                            <i className="footer__item-icon "></i>
                                            <Link to="https://www.airbnb.com.vn/" className="footer__item-link">
                                                {t('footer.contact_link_10')}
                                            </Link>
                                        </li>
                                    </ul>
                                </div>
                                <div className="col l-4 m-4 c-12">
                                    <ul className="footer__list">
                                        <h3 className="footer__list-heading">{t('footer.contact_3')}</h3>
                                        <li className="footer__item">
                                            <i className="footer__item-icon "></i>
                                            <Link to="#" className="footer__item-link">
                                                {t('footer.contact_link_11')}
                                            </Link>
                                        </li>
                                        <li className="footer__item">
                                            <i className="footer__item-icon "></i>
                                            <Link to="#" className="footer__item-link">
                                                {t('footer.contact_link_12')}
                                            </Link>
                                        </li>
                                        <li className="footer__item">
                                            <i className="footer__item-icon "></i>
                                            <Link to="#" className="footer__item-link">
                                                {t('footer.contact_link_13')}
                                            </Link>
                                        </li>
                                        <li className="footer__item">
                                            <i className="footer__item-icon "></i>
                                            <Link to="#" className="footer__item-link">
                                                {t('footer.contact_link_14')}
                                            </Link>
                                        </li>
                                        <li className="footer__item">
                                            <i className="footer__item-icon "></i>
                                            <Link to="#" className="footer__item-link">
                                                {t('footer.contact_link_15')}
                                            </Link>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div className="footer__inner-contact">
                                <Link to="#" className="footer__contact-logo ">
                                    <img src={twitterIcon} alt="alt-t" />
                                </Link>
                                <Link
                                    to="https://www.youtube.com/channel/UCUf6O2OojuB4nMBmoL3jy-Q"
                                    className="footer__contact-logo "
                                >
                                    <img src={youtubeicon} alt="alt-y" />
                                </Link>
                                <Link
                                    to="https://www.facebook.com/maibaohuy.070620021303mbBank/"
                                    className="footer__contact-logo "
                                >
                                    <img src={facebookIcon} alt="alt-f" />
                                </Link>
                                <Link to="#" className="footer__contact-logo ">
                                    <img src={instagramIcon} alt="alt-i" />
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
