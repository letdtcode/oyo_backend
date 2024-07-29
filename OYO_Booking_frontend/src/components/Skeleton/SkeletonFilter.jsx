import Box from '@mui/material/Box';
import Skeleton from '@mui/material/Skeleton';

export default function SkeletonFilter() {
    return (
        <Box sx={{ paddingLeft: '50px', paddingRight: '50px', zIndex: '99' }}>
            <Skeleton width="100%" sx={{ height: '100px' }} />
        </Box>
    );
}
