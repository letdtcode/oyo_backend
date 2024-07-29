import Box from '@mui/material/Box';
import Skeleton from '@mui/material/Skeleton';

const Array = [1, 2, 3];

export default function SkeletonAdvertisement() {
    return (
        <div className='row'>
            {Array.map((data, index) => (
                <div className="col l-4 m-6 c-12" key={index}>
                    <Box>
                        <Skeleton
                            variant="rectangular"
                            width={'100%'}
                            height={180}
                            sx={{ marginTop: '10px', borderRadius: '5px' }}
                        />
                    </Box>
                </div>
            ))}
        </div>
    );
}
