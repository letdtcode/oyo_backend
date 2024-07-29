import { Stack, CircularProgress } from '@mui/material';

const Loading = (props) => {
    return (
        <div className="loading-comp">
            {props.loading ? (
                <Stack sx={{ color: 'grey.500' }} spacing={2} direction="row">
                    <CircularProgress color="success" />
                </Stack>
            ) : (
                null
            )}
        </div>
    );
};

export default Loading;
