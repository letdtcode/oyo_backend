import './GoogleMap.scss';
import { useEffect, useState, memo } from 'react';
import GoogleMapReact from 'google-map-react';
import HotelIcon from '@mui/icons-material/Hotel';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import Skeleton from '@mui/material/Skeleton';

// Định nghĩa các component Hotel và LocationCurrent
const Hotel = () => <HotelIcon style={{ color: 'red', fontSize: 'xx-large' }} />;
const LocationCurrent = () => <LocationOnIcon style={{ color: 'blue', fontSize: 'xx-large' }} />;

export default function GoogleMap({ data }) {
    const [currentPosition, setCurrentPosition] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(true);
        const fetchCurrentPosition = () => {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition((position) => {
                    setCurrentPosition({
                        lat: position.coords.latitude,
                        lng: position.coords.longitude
                    });
                });
            }
        };
        setLoading(false);

        fetchCurrentPosition();
    }, [data]);

    if (loading) {
        return <Skeleton variant="rectangular" width="100%" height={400} />;
    }

    if (!currentPosition) {
        return <div></div>;
    }

    const defaultProps = {
        center: {
            lat: data.latitude,
            lng: data.longitude
        },
        zoom: 14
    };

    return (
        <div className="container__google-map">
            <GoogleMapReact
                bootstrapURLKeys={{ key: import.meta.env.VITE_API_KEY_GOOGLE }}
                defaultCenter={defaultProps.center}
                defaultZoom={defaultProps.zoom}
                yesIWantToUseGoogleMapApiInternals
            >
                <Hotel lat={data.latitude} lng={data.longitude} key={1}  />
                <LocationCurrent lat={currentPosition.lat} lng={currentPosition.lng} />
            </GoogleMapReact>
        </div>
    );
}
