import Skeleton from '@mui/material/Skeleton';

export default function SkeletonRoomDetail() {
    return (
        <div style={{ margin: '0 180px', marginTop: '-15px' }}>
            <Skeleton width="60%" sx={{ height: '42px', marginTop: '2px' }} />
            <Skeleton width="100%" sx={{ height: '40px', marginTop: '-5px' }} />
            <div className="row">
                <div className="col l-6">
                    <Skeleton variant="rectangular" width={'100%'} height={432.5} />
                </div>
                <div className="col l-6">
                    <div className="row">
                        <div className="col l-6">
                            <Skeleton variant="rectangular" width={'100%'} height={214} sx={{paddingRight: '3px'}}/>
                        </div>
                        <div className="col l-6">
                            <Skeleton variant="rectangular" width={'100%'} height={214} sx={{paddingLeft: '3px'}}/>
                        </div>
                        <div className="col l-6" style={{marginTop: '3px'}}>
                            <Skeleton variant="rectangular" width={'100%'} height={214} sx={{paddingRight: '3px'}}/>
                        </div>
                        <div className="col l-6" style={{marginTop: '3px'}}>
                            <Skeleton variant="rectangular" width={'100%'} height={214} sx={{paddingLeft: '3px'}}/>
                        </div>
                    </div>
                </div>
            </div>
            <div className="row">
                <div className='col l-8 m-7 c-12' style={{ marginTop : '25px' }}>
                    <Skeleton width="100%" sx={{ height: '40px' }} />
                    <Skeleton width="100%" sx={{ height: '35px' }} />
                    <Skeleton variant="rectangular" width={'100%'} height={176} />
                </div>
                <div className="col l-4 m-5 c-12">
                    <Skeleton variant="rectangular" width={'100%'} height={400} sx={{ marginTop : '30px' }}/>
                </div>
            </div>
        </div>
    );
}
