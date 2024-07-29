import Box from '@mui/material/Box';
import Skeleton from '@mui/material/Skeleton';

const Array = [1, 2, 3, 4, 5, 6, 7, 8];

export default function SkeletonProvince() {
    return (
        <>
            {Array.map((data, index) => (
                <div className="col l-3 m-6 c-12" key={index}>
                    <Box>
                        <Skeleton
                            variant="rectangular"
                            width={'100%'}
                            height={154}
                            sx={{ marginTop: '10px', borderRadius: '5px' }}
                        />
                    </Box>
                </div>
            ))}
        </>
    );
}
