import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from "react";
import FramePage from '~/components/FramePage/FramePage';
import Logo from '~/assets/logo.svg'
import './Congratulation.scss';
import { t } from 'i18next';

const CongratulationPage = () => {
    const user = useSelector((state) => state.user.current)
    const navigate = useNavigate();

    const backHome = () => {
        navigate('/');
    };

    const nextPage = () => {
        navigate('/host');
    };

    useEffect(() => {
        for (let i = 0; i < 100; i++) {
            const randomRotation = Math.floor(Math.random() * 360);
            const randomScale = Math.random() * 1;
            const randomWidth = Math.floor(Math.random() * Math.max(document.documentElement.clientWidth, window.innerWidth || 0));
            const randomHeight = Math.floor(Math.random() * Math.max(document.documentElement.clientHeight, window.innerHeight || 500));
            const randomAnimationDelay = Math.floor(Math.random() * 15);

            const colors = ['#0CD977', '#FF1C1C', '#FF93DE', '#5767ED', '#FFC61C', '#8497B0'];
            const randomColor = colors[Math.floor(Math.random() * colors.length)];

            const confetti = document.createElement('div');
            confetti.className = 'confetti';
            confetti.style.top = randomHeight + 'px';
            confetti.style.right = randomWidth + 'px';
            confetti.style.backgroundColor = randomColor;
            confetti.style.opacity = randomScale;
            confetti.style.transform = 'skew(15deg) rotate(' + randomRotation + 'deg)';
            confetti.style.animationDelay = randomAnimationDelay + 's';
            // document.getElementById("confetti-wrapper").appendChild(confetti);
        }
    }, []);
    return (
        <FramePage>
            <div className="congratulation-page">
                <div className="row">
                    <div className="col l-6">
                        <div className="background-left"></div>
                    </div>
                    <div className="col l-6">
                        <div className="text-thanks">
                            <h1>{`${t('setupOwner.welcome')} ${user.userName}!`}</h1>
                            <p>{t('setupOwner.beforePostHome')}</p>
                            <div style={{ display: 'flex', width: '100%', justifyContent: 'center' }}>
                                <p style={{ color: 'red', padding: 0, fontWeight: 'bold' }}>{t('setupOwner.note')}</p>
                                <p style={{ padding: 0, marginLeft: '8px' }}>{t('setupOwner.rose')}</p>
                            </div>
                            <h2>-- OYO --</h2>
                        </div>
                        <button type="submit" onClick={nextPage}>
                            {t('common.continue')}
                        </button>
                    </div>
                </div>
            </div>
        </FramePage>
    );
};

export default CongratulationPage;
