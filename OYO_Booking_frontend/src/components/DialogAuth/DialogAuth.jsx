import Button from '@mui/material/Button';
import DialogTitle from '@mui/material/DialogTitle';
import Dialog from '@mui/material/Dialog';
import CloseIcon from '@mui/icons-material/Close';
import Slide from '@mui/material/Slide';

import SignIn from './SignIn/SignIn';
import Register from './Register/Register';
import ForgotPassword from './ForgotPassword/ForgotPassword';

import { useState, forwardRef } from 'react';
import { t } from 'i18next';
import './DialogAuth.scss';

export default function DialogAuth({setOpen, open}) {
    const [position, setPosition] = useState('');
    const [email, setEmail] = useState('');
    const handleClose = () => {
        setOpen(false);
    };

    let dialogContent;
    let title;
    let amination = true;
    switch (position) {
        case 'SignUp':
            title = `${t('title.accountSignUp')}`;
            amination = false;
            dialogContent = (
                <Register email={email} setEmail={setEmail} setPosition={setPosition} handleClose={handleClose} />
            );
            break;
        case 'ForgotPassword':
            title = `${t('link.forgotpassword')}`;
            amination = false;
            dialogContent = (
                <ForgotPassword email={email} setEmail={setEmail} setPosition={setPosition} handleClose={handleClose} />
            );
            break;
        default:
            title = `${t('title.signin')}/ ${t('title.signup')}`;
            amination = true;
            dialogContent = <SignIn setEmail={setEmail} setPosition={setPosition} handleClose={handleClose} />;
    }
    const TransitionComponent = forwardRef(function (props, ref) {
        if (amination) return <Slide direction="up" {...props} />;
        else {
            return <>{props.children}</>;
        }
    });
    return (
        <>
            <Dialog onClose={handleClose} open={open} TransitionComponent={TransitionComponent} keepMounted>
                <DialogTitle className="paper form-dialog">
                    <header>{title}</header>

                    <Button
                        className="closeDialog"
                        style={{ position: 'absolute', right: '8px', top: '8px' }}
                        onClick={handleClose}
                    >
                        <CloseIcon />
                    </Button>
                    {dialogContent}
                </DialogTitle>
            </Dialog>
        </>
    );
}
