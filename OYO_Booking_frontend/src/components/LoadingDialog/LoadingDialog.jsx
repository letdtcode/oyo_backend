import LoadingIcon from '~/assets/imageMaster/loading.gif';
import './LoadingDialog.scss';
import FramePage from '~/components/FramePage/FramePage';
import Dialog from '@mui/material/Dialog';
const LoadingDialog = (props) => {
    return (
        <Dialog
            open={props.open}
            className="loading__dialog"
            PaperProps={{ style: { background: 'transparent', boxShadow: 'none' } }}
        >
            <img src={LoadingIcon} alt="" />
        </Dialog>
    );
};

export default LoadingDialog;
