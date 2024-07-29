import { Link, useNavigate } from 'react-router-dom';
import Scrollspy from 'react-scrollspy';
import './Scrollspy.scss';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';

const ScrollspyComponent = (props) => {
    let navigate = useNavigate();
    return (
        <div className="scroll-spy">
            {props.backUrl && (
                <p
                    onClick={() => navigate('/host')}
                    style={{
                        top: '90px',
                        margin: '0px',
                        fontSize: '16px',
                        paddingLeft: '150px',
                        position: 'fixed',
                        color: 'black',
                        cursor: 'pointer'
                    }}
                >
                    <ArrowBackIosIcon />
                    Quay lại trang
                </p>
            )}
            <div>
                <div>
                    {props.children?.map((child, index) => (
                        <section id={child.to} key={index} style={{ marginLeft: '28%', paddingTop: '20px' }}>
                            {child?.comp}
                        </section>
                    ))}

                    <Scrollspy
                        items={props.item}
                        currentClassName="is-current"
                        style={{
                            position: 'fixed',
                            height: '100%',
                            marginTop: '130px',
                            top: '0',
                            listStyle: 'none',
                            color: 'var(--border-scroll)',
                            fontSize: '1rem',
                            fontWeight: '600',
                            paddingLeft: '150px'
                        }}
                    >
                        <Link className="link-sidebar">{props.infoLink.name}</Link>
                        {props.children?.map((child, index) => (
                            <li key={index}>
                                <a href={child.id}>{child.info}</a>
                            </li>
                        ))}
                    </Scrollspy>
                </div>
            </div>
        </div>
    );
};

export default ScrollspyComponent;
