import Box from '@mui/material/Box';
import LinearProgress from '@mui/material/LinearProgress';
import './LoadingAdmin.scss';

export default function LoadingAdmin() {
    return (
        <div className="loading-admin">
            <Box sx={{ width: '100%' }}>
                <LinearProgress />
            </Box>
        </div>
    );
}
