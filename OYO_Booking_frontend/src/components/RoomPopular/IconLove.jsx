import { useEffect, useState } from 'react';
import { t } from 'i18next';
import { useSnackbar } from 'notistack';
import FavoriteOutlinedIcon from '@mui/icons-material/FavoriteOutlined';
import wishAPI from '~/services/apis/clientAPI/clientWishAPI';
import './RoomPopular.scss';

const IconLove = (props) => {
    const [like, setLike] = useState(props.isFavorite);
    const [loading, setLoading] = useState(false);
    const { enqueueSnackbar } = useSnackbar();
    useEffect(() => {
        setLike(props.isFavorite);
    }, [props.isFavorite]);

    const handleFavorite = () => {
        wishAPI.likeFavoriteRoom(props.idHome).then((res) => {
            setLike(!like);
            if(res.data.message === 'Add wish item success')
            {
                enqueueSnackbar(t('message.love'), { variant: 'success' });
            }
            else{
                enqueueSnackbar(t('message.unlove'), { variant: 'success' });
            }
          
        });
    };

    return (
        <div className="love_room">
            <FavoriteOutlinedIcon className={like ? 'icon_love__true' : 'icon_love'} onClick={handleFavorite} />
        </div>
    );
};

export default IconLove;
