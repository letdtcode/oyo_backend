import './BannerOwner.scss';
import PropTypes from 'prop-types';

export default function BannerOwner({ title, subtitle, imgSrc }) {

    return (
        <div className="banner-owner">
            <div className="welcome-card">
                <div className="welcome-content row">
                    <div className="welcome-content__left col l-7 m-7 c-7">
                        <h1>{title}</h1>
                        <p>{subtitle}</p>
                    </div>
                    <div className="welcome-content__right col l-5 m-5 c-5">
                        <img src={imgSrc} alt="Welcome" className="img" />
                    </div>
                </div>
            </div>
        </div>
    );
}

BannerOwner.propTypes = {
    title: PropTypes.string.isRequired,
    subtitle: PropTypes.string.isRequired,
    imgSrc: PropTypes.string.isRequired
};
